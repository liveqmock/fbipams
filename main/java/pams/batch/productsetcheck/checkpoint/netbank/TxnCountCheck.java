package pams.batch.productsetcheck.checkpoint.netbank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pams.batch.productsetcheck.checkpoint.AbstractCheckPointECTIP;
import pams.repository.model.prdset.PsSalesPrdInfoBean;

import java.util.List;

/**
 * ͨ�������������� �����Խ��ױ������.
 * User: zhanrui
 * Date: 11-5-3
 * Time: ����2:00
 * To change this template use File | Settings | File Templates.
 */
@Component("netbank.TxnCountCheck")
public class TxnCountCheck extends AbstractCheckPointECTIP {
    private static final Logger logger = LoggerFactory.getLogger(TxnCountCheck.class);

    private static final String PROGNAME = "netbank.TxnCountCheck";

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
                checklog ="δ��������Ӧ�Ŀͻ���";
                addOrUpdateCheckpointDetailRecord(record.getGuid(), ckptprogguid, "0", checklog);
            } else {
                int txncount = selectTxnCount(custno, CHANNEL_NO_NETBANK);
                checklog = "�ͻ���:" + custno + " �������ཻ�ױ���Ϊ " + txncount;
                if (txncount == 0) {
                    checkflag = "0";
                } else {
                    checkflag = "1";
                }
            }
            addOrUpdateCheckpointDetailRecord(record.getGuid(), ckptprogguid, checkflag, checklog);
            logger.info("�������в�Ʒ��ˣ������ཻ�ױ��� "+ certtype + "-" + certno + checklog);
        }

    }

    @Override
    protected void beforeProcess() {
        logger.info("�������в�Ʒ��˿�ʼ�������ཻ�ױ��������0");
    }

    @Override
    protected void postProcess() {
        logger.info("�������в�Ʒ�����ɣ������ཻ�ױ��������0");
    }

    //==========================================================
}
