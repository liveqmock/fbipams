package pams.batch.productsetcheck.checkpoint;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 有关ECTIP系统的抽象类.
 * User: zhanrui
 * Date: 11-5-13
 * Time: 下午10:57
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractCheckPointECTIP extends AbstractCheckPoint {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AbstractCheckPointECTIP.class);

    protected static final String CHANNEL_NO_NETBANK = "01";
    protected static final String CHANNEL_NO_MOBILEBANK = "02";
    protected static final String CHANNEL_NO_TELEBANK = "03";
    protected static final String CHANNEL_NO_SMSSERVICE = "05";

    /**
     * 公用方法：根据证件信息查询ODSB客户ID
     * 此方法自ECTIP系统的个人客户基本信息表（bf_pr_ind_info_ectip）中获取数据,覆盖父类中的方法
     *
     * @return
     */
    @Override
    protected String selectCustnoFromODSB() {
        String ectip_cert_type = platformService.selectEnuExpandValue("CERTTYPE", certtype);
        if (StringUtils.isEmpty(ectip_cert_type)) {
            throw new RuntimeException("证件类型枚举错误！");
        }
        try {
            Map map = simpleJdbcTemplate.queryForMap(
                    "select ectip_cust_no from bf_pr_ind_info_ectip where cert_type = ? and cert_no = ? "
                    , ectip_cert_type, certno);
            return (String) map.get("ectip_cust_no");
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * 检索客户级别 (高级客户：必须柜台签约)
     * 0柜面，1后台，2网银，3手机银行，4 CSR。默认为0
     *
     * @param custno
     * @param channel
     * @return
     */
    protected String selectCustomerGrade(String custno, String channel) {
        try {
            Map map = simpleJdbcTemplate.queryForMap(
                    "select sign_appr from bf_agt_chn_chnp_acct  where ectip_cust_no = ? and  chn_no = ?"
                    , custno, channel);
            return (String) map.get("sign_appr");
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * 检索客户签约渠道
     *
     * @param signAppr
     * @return
     */

    protected String getCustomerSignApproachName(String signAppr) {
        String signApprName = "不明";
        if (signAppr.equals("0")) signApprName = "柜面";
        else if (signAppr.equals("1")) signApprName = "后台";
        else if (signAppr.equals("2")) signApprName = "网银";
        else if (signAppr.equals("3")) signApprName = "手机银行";
        else if (signAppr.equals("4")) signApprName = "CSR";
        return signApprName;
    }

    protected String getCustomerSignStatus(String custno, String channel) {
        try {
            Map map = simpleJdbcTemplate.queryForMap(
                    "select sign_sts from bf_agt_chn_chnp_cust  where ectip_cust_no = ? and  chn_no = ?"
                    , custno, channel);
            return (String) map.get("sign_sts");
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * 0 登记：CallCenter的非签约客户。
        1 未签约：网银的非签约客户。
        2 签约：客户与银行签订协议，办理了帐户签约确认手续。
        3 签约未激活
     * @param signStatus
     * @return
     */

    protected String getCustomerSignStatusName(String signStatus) {
        String signApprName = "不明";
        if (signStatus.equals("0")) signApprName = " 登记：CallCenter的非签约客户";
        else if (signStatus.equals("1")) signApprName = "未签约：网银的非签约客户";
        else if (signStatus.equals("2")) signApprName = "签约：客户与银行签订协议，办理了帐户签约确认手续";
        else if (signStatus.equals("3")) signApprName = "签约未激活";
        return signApprName;
    }


    /**
     * 检索帐务类交易笔数
     *
     * @param custno
     * @param channel
     * @return
     */
    protected int selectTxnCount(String custno, String channel) {
        return simpleJdbcTemplate.queryForInt(
                "select count(*) from bf_evt_chn_chn_trad  " +
                        " where ectip_cust_no = ? " +
                        "   and  chn_no = ? " +
                        "   and tx_flg = '00' " +    //交易成功标志
                        "   and (amt1 + amt2 + amt3 + f_fare > 0)"  //金额判断 TODO ？
                , custno, channel);
    }

    /**
     * 按客户号检索开通的帐号列表   暂不用
     *
     * @param custno  客户号
     * @param channel
     * @return 未找到时返回空LIST
     */
    protected List<String> selectAccountnoList(String custno, String channel) {
        List<String> acctnoList = new ArrayList<String>();
        List<Map<String, Object>> records = simpleJdbcTemplate.queryForList(
                "select acct_no from bf_agt_chn_chnp_acct  where ectip_cust_no = ? and  chn_no = ?"
                , custno, channel);
        for (Map<String, Object> record : records) {
            acctnoList.add((String) record.get("acct_no"));
        }
        return acctnoList;
    }

    /**
     * 检索业务签约开通日期
     *
     * @param custno
     * @param channel
     * @return
     */
    protected String selectBusinessSignDate(String custno, String channel) {
        try {
            Map map = simpleJdbcTemplate.queryForMap(
                    "select sign_dt from bf_agt_chn_chnp_cust  where ectip_cust_no = ? and  chn_no = ?"
                    , custno, channel);
            return (String) map.get("sign_dt");
        } catch (DataAccessException e) {
            logger.error("检索签约日期出现错误！客户号：" + custno + "渠道代号：" + channel);
            return null;
        }
    }

}
