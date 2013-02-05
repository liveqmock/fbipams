package pams.batch.productsetcheck.checkpoint.netbank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pams.batch.productsetcheck.checkpoint.AbstractCheckPointECTIP;
import pams.repository.model.prdset.PsSalesPrdInfoBean;

import java.util.List;

/**
 * 通过网上银行渠道 帐务性交易笔数检核.
 * User: zhanrui
 * Date: 11-5-3
 * Time: 下午2:00
 * To change this template use File | Settings | File Templates.
 */
@Component("netbank.TxnCountCheck")
public class TxnCountCheck extends AbstractCheckPointECTIP {
    private static final Logger logger = LoggerFactory.getLogger(TxnCountCheck.class);

    private static final String PROGNAME = "netbank.TxnCountCheck";

    @Transactional
    public void process(String prdid) {
        //根据检核指标处理程序名查找ID
        String ckptprogguid = selectCheckPointRuleGuid(PROGNAME);

        //检索指定产品ID及某检核指标未通过的记录
        List<PsSalesPrdInfoBean> prdinfoList = super.selectNeedCheckPrdinfoList(prdid, ckptprogguid);

        String checklog = "";
        for (PsSalesPrdInfoBean record : prdinfoList) {
            this.certtype = record.getCerttype();
            this.certno = record.getCertno();

            String checkflag = "0";
            String custno = selectCustnoFromODSB();
            if (custno == null) {
                checklog ="未检索到对应的客户号";
                addOrUpdateCheckpointDetailRecord(record.getGuid(), ckptprogguid, "0", checklog);
            } else {
                int txncount = selectTxnCount(custno, CHANNEL_NO_NETBANK);
                checklog = "客户号:" + custno + " 的帐务类交易笔数为 " + txncount;
                if (txncount == 0) {
                    checkflag = "0";
                } else {
                    checkflag = "1";
                }
            }
            addOrUpdateCheckpointDetailRecord(record.getGuid(), ckptprogguid, checkflag, checklog);
            logger.info("网上银行产品检核：帐务类交易笔数 "+ certtype + "-" + certno + checklog);
        }

    }

    @Override
    protected void beforeProcess() {
        logger.info("网上银行产品检核开始：帐务类交易笔数须大于0");
    }

    @Override
    protected void postProcess() {
        logger.info("网上银行产品检核完成：帐务类交易笔数须大于0");
    }

    //==========================================================
}
