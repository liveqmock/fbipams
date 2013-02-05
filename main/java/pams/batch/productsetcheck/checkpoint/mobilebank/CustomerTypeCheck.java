package pams.batch.productsetcheck.checkpoint.mobilebank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pams.batch.productsetcheck.checkpoint.AbstractCheckPointECTIP;
import pams.repository.model.prdset.PsSalesPrdInfoBean;

import java.util.List;

/**
 * 网上银行检核指标：是否高级客户  批量检核.
 * User: zhanrui
 * Date: 11-5-3
 * Time: 下午2:00
 * To change this template use File | Settings | File Templates.
 */
@Component("mobilebank.CustomerTypeCheck")
public class CustomerTypeCheck extends AbstractCheckPointECTIP {
    private static final Logger logger = LoggerFactory.getLogger(CustomerTypeCheck.class);

    private static final String PROGNAME = "mobilebank.CustomerTypeCheck";

    @Transactional
    public void process(String prdid) {
        //根据检核指标处理程序名查找ID
        String ckptprogguid = selectCheckPointRuleGuid(PROGNAME);

        //检索指定产品ID及某检核指标未通过的记录
        List<PsSalesPrdInfoBean> prdinfoList = super.selectNeedCheckPrdinfoList(prdid, ckptprogguid);

        for (PsSalesPrdInfoBean record : prdinfoList) {
            this.certtype = record.getCerttype();
            this.certno = record.getCertno();

            String custno = selectCustnoFromODSB();
            String checklog = "";
            String checkflag = "0";

            if (custno == null) {
                checklog = "未检索到对应的客户号";
            } else {
                checklog = "客户号:" + custno;
                //检索签约状态
                String signStatus = getCustomerSignStatus(custno, CHANNEL_NO_MOBILEBANK);
                if (signStatus == null) {
                    checklog += " 的渠道签约状态不存在。";
                } else {
                    if ("2".equals(signStatus.trim())) {
                        String sign_dt = selectBusinessSignDate(custno, CHANNEL_NO_MOBILEBANK);
                        if (sign_dt == null) {
                            checklog = checklog + " 的客户级别检核未通过, 开通签约服务日期不存在。";
                        } else {
                            if (sign_dt.compareTo(record.getTxndate()) < 0) {
                                checklog = checklog + " 的客户级别检核未通过, 实际开通签约服务日期为:" + sign_dt;
                            } else {
                                checklog = checklog + " 的客户级别检核通过";
                                checkflag = "1";
                            }
                        }
                    } else {
                        checklog = checklog + " 的客户级别检核未通过" + getCustomerSignStatusName(signStatus);
                    }
                }
            }
            addOrUpdateCheckpointDetailRecord(record.getGuid(), ckptprogguid, checkflag, checklog);
            logger.info("手机银行产品检核：是否高级客户:" + certtype + "-" + certno + checklog);
        }

    }

    @Override
    protected void beforeProcess() {
        logger.info("手机银行产品检核开始：是否高级客户");
    }

    @Override
    protected void postProcess() {
        logger.info("手机银行产品检核完成：是否高级客户");
    }

    //==========================================================

}
