package pams.batch.productsetcheck.checkpoint.netbank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pams.batch.productsetcheck.checkpoint.AbstractCheckPointECTIP;
import pams.repository.model.prdset.PsSalesPrdInfoBean;

import java.util.List;

/**
 * �������м��ָ�꣺�Ƿ�߼��ͻ�  �������.
 * User: zhanrui
 * Date: 11-5-3
 * Time: ����2:00
 * To change this template use File | Settings | File Templates.
 */
@Component("netbank.CustomerTypeCheck")
public class CustomerTypeCheck extends AbstractCheckPointECTIP {
    private static final Logger logger = LoggerFactory.getLogger(CustomerTypeCheck.class);

    private static final String PROGNAME = "netbank.CustomerTypeCheck";

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
                //����ǩԼ״̬  "01" ��������
                String signStatus = getCustomerSignStatus(custno, CHANNEL_NO_NETBANK);
                if (signStatus == null) {
                    checklog += " ������ǩԼ״̬�����ڡ�";
                } else {
                    if ("2".equals(signStatus.trim())) {
                        String sign_dt = selectBusinessSignDate(custno, CHANNEL_NO_NETBANK);
                        if (sign_dt == null) {
                            checklog = checklog + " �Ŀͻ�������δͨ��, ��ͨǩԼ�������ڲ����ڡ�";
                        } else {
                            if (sign_dt.compareTo(record.getTxndate()) < 0) {
                                checklog = checklog + " �Ŀͻ�������δͨ��, ʵ�ʿ�ͨǩԼ��������Ϊ:" + sign_dt;
                            } else {
                                checklog = checklog + " �Ŀͻ�������ͨ��";
                                checkflag = "1";
                            }
                        }
                    } else {
                        checklog = checklog + " �Ŀͻ�������δͨ��" + getCustomerSignStatusName(signStatus);
                    }
                }
            }
            addOrUpdateCheckpointDetailRecord(record.getGuid(), ckptprogguid, checkflag, checklog);
            logger.info("�������в�Ʒ��ˣ��Ƿ�߼��ͻ�:" + certtype + "-" + certno + checklog);
        }

    }



    @Override
    protected void beforeProcess() {
        logger.info("�������в�Ʒ��˿�ʼ���Ƿ�߼��ͻ�");
    }

    @Override
    protected void postProcess() {
        logger.info("�������в�Ʒ�����ɣ��Ƿ�߼��ͻ�");
    }

    //==========================================================

}
