package pams.batch.productsetcheck.checkpoint.bankcard;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pams.batch.productsetcheck.checkpoint.AbstractCheckPointCCBS;
import pams.repository.model.prdset.PsSalesPrdInfoBean;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 银行卡  账户余额检查.
 * User: zhanrui
 * Date: 11-5-3
 * Time: 下午2:00
 * To change this template use File | Settings | File Templates.
 */
@Component("bankcard.ActBalanceCheck")
public class ActBalanceCheck extends AbstractCheckPointCCBS {
    private static final Logger logger = LoggerFactory.getLogger(ActBalanceCheck.class);

    private static final String PROGNAME = "bankcard.ActBalanceCheck";

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd");


    //    @Transactional
    public void process(String prdid) {
        //根据检核指标处理程序名查找ID
        String ckptprogguid = selectCheckPointRuleGuid(PROGNAME);

        //检索指定产品ID及某检核指标未通过的记录
        List<PsSalesPrdInfoBean> prdinfoList = super.selectNeedCheckPrdinfoList(prdid, ckptprogguid);

        Map<String, String> certTypeMap = platformService.selectEnuItemValueToExpandValueMap("CERTTYPE");

        BigDecimal zero = new BigDecimal(0);
        for (PsSalesPrdInfoBean record : prdinfoList) {
            this.certtype = record.getCerttype();
            this.certno = record.getCertno();

            String custno = generateCustnoByCertInfo(certTypeMap);

            String checklog = "";
            String checkflag = "0";

            List<String> actnoList = selectActnoListByCustno(custno, record.getTxndate());
            if (actnoList.size() == 0) {
                checklog = "客户号:" + custno + " 无对应的银行卡号（开卡日期需在签约日期之后）。";
            } else {
                checklog = "客户号:" + custno + "本年度日均余额为0";
                for (String actno : actnoList) {
                    if (isExistBalance(actno)) {
                        checkflag = "1";
                        checklog = "客户号:" + custno + "本年度日均余额大于0";
                        break;
                    }
                }
            }

            addOrUpdateCheckpointDetailRecord(record.getGuid(), ckptprogguid, checkflag, checklog);
            logger.info("银行卡产品检核：年日均余额 " + certtype + "-" + certno + checklog);
        }
    }


    @Override
    protected void beforeProcess() {
        logger.info("银行卡产品检核开始：年日均余额大于0");
    }

    @Override
    protected void postProcess() {
        logger.info("银行卡产品检核完成：年日均余额大于0");
    }

    /**
     * 客户号直接根据证件信息生成
     * 身份证的 在客户号后边追加“0”，其他追加“#0”
     *
     * @param certTypeMap 新旧证件类型对照
     * @return custno
     */
    private String generateCustnoByCertInfo(Map<String, String> certTypeMap) {
        String ccbsCertType = certTypeMap.get(this.certtype);
        if (StringUtils.isEmpty(ccbsCertType)) {
            throw new RuntimeException("新旧证件类型对应失败！" + this.certtype + "-" + this.certno);
        }

        String custno = ccbsCertType + this.certno;
        if (ccbsCertType.equals("A")) {
            custno = custno + "0";
        } else {
            custno = custno + "#0";
        }
        return custno;
    }

    //==========================================================

    /**
     * 在“活期个人存款事件”表中统计该客户号发生事件的记录数   暂不用
     *
     * @param custno
     * @return
     */
    private int selectEventCountOfCustno(String custno) {
        return simpleJdbcTemplate.queryForInt("select count(*) from bf_evt_dep_sap where cust_no = ?", custno);
    }

    private int selectTotalCreditAmt(String custno) {
        String curryear = new SimpleDateFormat("yyyy-MM").format(new Date()) + "-01";
        return simpleJdbcTemplate.queryForInt("select sum(cmount) from bf_evt_dep_sap where cust_no = ? and sa_tx_dt >= ?", custno, curryear);
    }

    private boolean isExistBalance(String actno) {
        String sql = "select t.start_date, decode(t.acct_bal,0,'0','1') as balflag from bf_agt_dep_acct_sap_bal_h t " +
                " where t.end_date = '2999-12-31'  and t.acct_no = ? " ;
        List<Map<String,Object>> records = simpleJdbcTemplate.queryForList(sql, actno);
        if (records.size()==0) {
            return false;
        }
        if (records.size() > 1) {
            logger.error("银行卡账户余额历史记录大于1！");
        }
        String balflag = (String)records.get(0).get("balflag");
        if ("1".equals(balflag)) {
            return true;
        } else {
            String startdate = (String)records.get(0).get("start_date");
            String currYearFirstDay = new SimpleDateFormat("yyyy").format(new Date())+"-01-01";
            if (startdate.compareTo(currYearFirstDay) >= 0) {
                return true;
            }else{
                return false;
            }
        }
    }


    /**
     * 检索客户号对应的所有卡账户积数 ( PDT = P+DT = Product date 日期积数)
     *
     * @param custno
     * @return
     */
    private List<Map<String, Object>> selectTotalPDT(String custno) {
        String currdate = simpleDateFormat.format(new Date());
        String curryear = currdate.substring(0, 4);
        String sql = "select t.curr_cd, " +
                "  sum(t.acct_bal * (to_date(decode(substr(t.end_date,1,4), ?, t.end_date, ?), 'yyyy-MM-dd') - " +
                "                    to_date(decode(substr(t.start_date,1,4),?, t.start_date, ?), 'yyyy-MM-dd') + 1)) as pdt" +
                "  from bf_agt_dep_acct_sap_bal_h t " +
                " where substr(t.end_date,1,4) >= ? " +
                "       and t.acct_no in (select rsv_acct_no from bf_agt_crd_dbt  where cust_no = ?) " +
                " group by t.curr_cd";
        //System.out.println(sql);
        return simpleJdbcTemplate.queryForList(sql, curryear, currdate, curryear, (curryear + "-01-01"), curryear, custno);
    }

    /**
     * 从odsb的银行卡信息表中检索客户号对应的帐户号
     *
     * @param custno
     * @param txndate   产品套餐中的签约日期
     * @return
     */
    private List<String> selectActnoListByCustno(String custno, String txndate) {
        List<String> actnoList = new ArrayList<String>();
        List<Map<String, Object>> records = simpleJdbcTemplate.queryForList(
                "select rsv_acct_no from bf_agt_crd_dbt  where cust_no = ? and open_card_dt is not null and open_card_dt >= ?", custno, txndate);
        for (Map<String, Object> record : records) {
            actnoList.add((String) record.get("rsv_acct_no"));
        }
        return actnoList;
    }


    /**
     * 暂不用
     *
     * @param actno
     * @return
     */
    private double getCurrentYearBalance(String actno) {
        //TODO try
        Map<String, Object> yearbalance = simpleJdbcTemplate.queryForMap(
                "select ods_logic_dt, gl_yr_accum_dr_bal, gl_yr_accum_cr_bal from bf_agt_inn_ccbs_aca where ccbs_acct_no = ?"
                , actno);
        String logicDate = (String) yearbalance.get("ods_logic_dt");
        double debitBal = (Double) yearbalance.get("gl_yr_accum_dr_bal");
        double creditBal = (Double) yearbalance.get("gl_yr_accum_cr_bal");

        DateTime dt = dateTimeFormat.parseDateTime(logicDate);
        int dayOfYear = dt.getDayOfYear();
        //DecimalFormat df = new DecimalFormat("#0.00");
        logger.info("借方年度余额:" + debitBal + " 借方年度余额:" + creditBal + " 业务日期:" + logicDate + " 天数:" + dayOfYear);
        double result = creditBal - debitBal;
        if (result == 0) {
            return result;
        } else {
            return (((int) result * 100) / dayOfYear) / 100;
        }
    }
}
