package pams.batch.productsetcheck.checkpoint.smsservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pams.batch.productsetcheck.checkpoint.AbstractCheckPointECTIP;
import pams.repository.model.prdset.PsSalesPrdInfoBean;

import java.util.List;

/**
 * ���ŷ����Ʒ��ˣ��Ƿ�ǩԼ�ͻ�.
 * User: zhanrui
 * Date: 11-5-3
 * Time: ����2:00
 * To change this template use File | Settings | File Templates.
 */
@Component("smsservice.CustomerTypeCheck")
public class CustomerTypeCheck extends AbstractCheckPointECTIP {
    private static final Logger logger = LoggerFactory.getLogger(CustomerTypeCheck.class);

    private static final String PROGNAME = "smsservice.CustomerTypeCheck";

    @Transactional
    public void process(String prdid) {
        //���ݼ��ָ�괦�����������ID
        String ckptprogguid = selectCheckPointRuleGuid(PROGNAME);

        //����ָ����ƷID��ĳ���ָ��δͨ���ļ�¼
        List<PsSalesPrdInfoBean> prdinfoList = super.selectNeedCheckPrdinfoList(prdid, ckptprogguid);

        for (PsSalesPrdInfoBean record : prdinfoList) {
            this.certtype = record.getCerttype();
            this.certno = record.getCertno();

            String custno = selectCustnoFromODSB();
            String checklog = "";
            String checkflag = "0";
            if (custno == null) {
                checklog = "δ��������Ӧ�Ŀͻ���";
            } else {
                checklog = "�ͻ���:" + custno;
                //����ǩԼ״̬
                String signStatus = getCustomerSignStatus(custno, CHANNEL_NO_SMSSERVICE);
                if (signStatus == null) {
                    checklog += " ������ǩԼ״̬�����ڡ�";
                } else {
                    if ("2".equals(signStatus.trim())) {
                        String sign_dt = selectBusinessSignDate(custno, CHANNEL_NO_SMSSERVICE);
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
            logger.info("���ŷ����Ʒ��ˣ��Ƿ�ǩԼ�ͻ� "+ certtype + "-" + certno + checklog);
        }

    }

    @Override
    protected void beforeProcess() {
        logger.info("���ŷ����Ʒ��˿�ʼ���Ƿ�ǩԼ�ͻ�");
    }

    @Override
    protected void postProcess() {
        logger.info("���ŷ����Ʒ�����ɣ��Ƿ�ǩԼ�ͻ�");
    }

    //==========================================================

}
