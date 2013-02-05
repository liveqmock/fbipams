package pams.batch.productsetcheck.checkpoint;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * �й�ECTIPϵͳ�ĳ�����.
 * User: zhanrui
 * Date: 11-5-13
 * Time: ����10:57
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractCheckPointECTIP extends AbstractCheckPoint {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AbstractCheckPointECTIP.class);

    protected static final String CHANNEL_NO_NETBANK = "01";
    protected static final String CHANNEL_NO_MOBILEBANK = "02";
    protected static final String CHANNEL_NO_TELEBANK = "03";
    protected static final String CHANNEL_NO_SMSSERVICE = "05";

    /**
     * ���÷���������֤����Ϣ��ѯODSB�ͻ�ID
     * �˷�����ECTIPϵͳ�ĸ��˿ͻ�������Ϣ��bf_pr_ind_info_ectip���л�ȡ����,���Ǹ����еķ���
     *
     * @return
     */
    @Override
    protected String selectCustnoFromODSB() {
        String ectip_cert_type = platformService.selectEnuExpandValue("CERTTYPE", certtype);
        if (StringUtils.isEmpty(ectip_cert_type)) {
            throw new RuntimeException("֤������ö�ٴ���");
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
     * �����ͻ����� (�߼��ͻ��������̨ǩԼ)
     * 0���棬1��̨��2������3�ֻ����У�4 CSR��Ĭ��Ϊ0
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
     * �����ͻ�ǩԼ����
     *
     * @param signAppr
     * @return
     */

    protected String getCustomerSignApproachName(String signAppr) {
        String signApprName = "����";
        if (signAppr.equals("0")) signApprName = "����";
        else if (signAppr.equals("1")) signApprName = "��̨";
        else if (signAppr.equals("2")) signApprName = "����";
        else if (signAppr.equals("3")) signApprName = "�ֻ�����";
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
     * 0 �Ǽǣ�CallCenter�ķ�ǩԼ�ͻ���
        1 δǩԼ�������ķ�ǩԼ�ͻ���
        2 ǩԼ���ͻ�������ǩ��Э�飬�������ʻ�ǩԼȷ��������
        3 ǩԼδ����
     * @param signStatus
     * @return
     */

    protected String getCustomerSignStatusName(String signStatus) {
        String signApprName = "����";
        if (signStatus.equals("0")) signApprName = " �Ǽǣ�CallCenter�ķ�ǩԼ�ͻ�";
        else if (signStatus.equals("1")) signApprName = "δǩԼ�������ķ�ǩԼ�ͻ�";
        else if (signStatus.equals("2")) signApprName = "ǩԼ���ͻ�������ǩ��Э�飬�������ʻ�ǩԼȷ������";
        else if (signStatus.equals("3")) signApprName = "ǩԼδ����";
        return signApprName;
    }


    /**
     * ���������ཻ�ױ���
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
                        "   and tx_flg = '00' " +    //���׳ɹ���־
                        "   and (amt1 + amt2 + amt3 + f_fare > 0)"  //����ж� TODO ��
                , custno, channel);
    }

    /**
     * ���ͻ��ż�����ͨ���ʺ��б�   �ݲ���
     *
     * @param custno  �ͻ���
     * @param channel
     * @return δ�ҵ�ʱ���ؿ�LIST
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
     * ����ҵ��ǩԼ��ͨ����
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
            logger.error("����ǩԼ���ڳ��ִ��󣡿ͻ��ţ�" + custno + "�������ţ�" + channel);
            return null;
        }
    }

}
