package pams.batch.productsetcheck.checkpoint.creditcard;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pams.batch.productsetcheck.checkpoint.AbstractCheckPointCCBS;
import pams.repository.model.prdset.PsSalesPrdInfoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 银行卡  账户余额检查.
 * User: zhanrui
 * Date: 11-5-3
 * Time: 下午2:00
 * To change this template use File | Settings | File Templates.
 */
@Component("creditcard.TxnCountCheck")
public class TxnCountCheck extends AbstractCheckPointCCBS {
    private static final Logger logger = LoggerFactory.getLogger(TxnCountCheck.class);

    private static final String PROGNAME = "creditcard.TxnCountCheck";

    private DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd");

    //贷记卡申请书编号
    private String appid;

    //    @Transactional
    public void process(String prdid) {
        //根据检核指标处理程序名查找ID
        String ckptprogguid = selectCheckPointRuleGuid(PROGNAME);

        //检索指定产品ID及某检核指标未通过的记录
        List<PsSalesPrdInfoBean> prdinfoList = super.selectNeedCheckPrdinfoList(prdid, ckptprogguid);

        for (PsSalesPrdInfoBean record : prdinfoList) {
            this.certtype = record.getCerttype();
            this.certno = record.getCertno();

            String checklog = "";
            String checkflag = "0";

            boolean isExistTxnRecord = isExistTxnRecordByFirstConsumeDate(this.certno, record.getTxndate());

            if (isExistTxnRecord) {
                checklog = "证件号" + this.certno + " 180天内存在交易记录";
                checkflag = "1";
            } else {
                checklog = "证件号" + this.certno + " 180天内不存在交易记录";
            }
            addOrUpdateCheckpointDetailRecord(record.getGuid(), ckptprogguid, checkflag, checklog);
            logger.info("信用卡产品检核：180天交易笔数 "+ certtype + "-" + certno + checklog);

        }
    }

    @Override
    protected void beforeProcess() {
        logger.info("信用卡产品检核开始：180天交易笔数大于0");
    }

    @Override
    protected void postProcess() {
        logger.info("信用卡产品检核完成：180天交易笔数大于0");
    }

    //==========================================================

    private boolean isExistTxnRecordByFirstConsumeDate(String certno, String txndate){
        List<Map<String, Object>> records = simpleJdbcTemplate.queryForList("select t.last_consume_dt from BF_AGT_CRD_CRT t " +
                "where  (sysdate - to_date(t.last_consume_dt,'yyyy-MM-dd')) <= 180 " +
                "and  t.embosser_name3= ?  and open_card_dt is not null and open_card_dt >= ? ", certno, txndate);
        if (records.size() == 0) {
            return false;
        }else{
           return true;
        }
    }


    private int getCurrentYearTotalConsumerTimesForAllCard() {
        List<String> appidList = selectAppidListByCertInfo();
        //此客户对应的所有银行卡的本年余额合计(未作日均处理)
        int consumerTimes = 0;
        for (String appid : appidList) {
            consumerTimes += selectCurrentYearConsumerTimes(appid);
        }
        return consumerTimes;
    }

    /**
     * 根据证件信息检索申请单列表
     *
     * @return
     */
    private List<String> selectAppidListByCertInfo() {
        List<String> appidList = new ArrayList<String>();
        List<Map<String, Object>> records = simpleJdbcTemplate.queryForList(
                "select appid " +
                        " from bf_agt_crd_crt_apply " +
                        " where cert_type = ? and  cert_no = ?)", certtype, certno);
        for (Map<String, Object> record : records) {
            appidList.add((String) record.get("appid"));
        }
        return appidList;
    }


    private int selectCurrentYearConsumerTimes(String appid) {
        //TODO  null?
        return simpleJdbcTemplate.queryForInt(
                "select nmbr_rtl_auth_ytd from bf_agt_crd_crt where appid = ?", appid
        );
    }
}
