package pams.batch.productsetcheck.checkpoint.netbank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pams.batch.productsetcheck.checkpoint.AbstractCheckPointECTIP;
import pams.repository.model.prdset.PsSalesPrdInfoBean;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-5-3
 * Time: ����2:00
 * To change this template use File | Settings | File Templates.
 */
@Component("netbank.SecurityTypeCheck")
public class SecurityTypeCheck extends AbstractCheckPointECTIP {
    private static final Logger logger = LoggerFactory.getLogger(SecurityTypeCheck.class);

    private static final String PROGNAME = "netbank.SecurityTypeCheck";

    @Transactional
    public void process(String prdid) {
        //���ݼ��ָ�괦�����������ID
        String ckptprogguid = selectCheckPointRuleGuid(PROGNAME);

        //����ָ����ƷID��ĳ���ָ��δͨ���ļ�¼
        List<PsSalesPrdInfoBean> prdinfoList = super.selectNeedCheckPrdinfoList(prdid, ckptprogguid);

        String checklog = "";
        for (PsSalesPrdInfoBean record : prdinfoList) {
            this.certtype = record.getCerttype();
            this.certno = record.getCertno();

            String checkflag = "0";
            String custno = selectCustnoFromODSB();
            if (custno == null) {
                checklog = "δ��������Ӧ�Ŀͻ���";
            } else {
                checklog = "�ͻ���:" + custno;
                //�����ͻ�����  "01" ��������
                String securityType = selectSecurityType(custno, CHANNEL_NO_NETBANK);
                if (securityType == null) {
                    checklog += " ���û���֤���Ͳ�����";
                } else {
                    securityType = securityType.trim();
                    if ("C".equals(securityType)
                            || "E".equals(securityType)
                            || "U".equals(securityType)
                            || "F".equals(securityType)
                            || "H".equals(securityType)
                            ) {
                        checklog += " ���û���֤���ͼ��ͨ��";
                        checkflag = "1";
                    } else {
                        checklog += " ���û���֤���Ͳ����ϼ��Ҫ��";
                    }
                }
            }
            addOrUpdateCheckpointDetailRecord(record.getGuid(), ckptprogguid, checkflag, checklog);
            logger.info("�������в�Ʒ��ˣ��û���֤���� "+ certtype + "-" + certno + checklog);
        }

    }

    @Override
    protected void beforeProcess() {
        logger.info("�������в�Ʒ��˿�ʼ���û���֤����Ϊʹ�������ܡ���̬���������ʹ���ļ�֤��ĸ��������ͻ�");
    }

    @Override
    protected void postProcess() {
        logger.info("�������в�Ʒ�����ɣ��û���֤����Ϊʹ�������ܡ���̬���������ʹ���ļ�֤��ĸ��������ͻ�");
    }

    //==============================================

    /**
     * @param custno
     * @param channel
     * @return C��֤�鷽ʽ��
     *         E����̬���
     *         U��Ukey
     *         F��Ukey����̬���
     *         L����̬����
     *         H��Ukey����̬����
     *         T���㶫��CCI��CTS�ͻ�
     *         M:�߶˰�
     */
    private String selectSecurityType(String custno, String channel) {
        try {
            Map map = simpleJdbcTemplate.queryForMap(
                    "select auth_type from bf_agt_chn_chnp_cust  where ectip_cust_no = ? and  chn_no = ?"
                    , custno, channel);
            return (String) map.get("auth_type");
        } catch (DataAccessException e) {
            return null;
        }
    }

}
