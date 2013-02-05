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
 * Time: 下午2:00
 * To change this template use File | Settings | File Templates.
 */
@Component("netbank.SecurityTypeCheck")
public class SecurityTypeCheck extends AbstractCheckPointECTIP {
    private static final Logger logger = LoggerFactory.getLogger(SecurityTypeCheck.class);

    private static final String PROGNAME = "netbank.SecurityTypeCheck";

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
                checklog = "未检索到对应的客户号";
            } else {
                checklog = "客户号:" + custno;
                //检索客户级别  "01" 网上银行
                String securityType = selectSecurityType(custno, CHANNEL_NO_NETBANK);
                if (securityType == null) {
                    checklog += " 的用户验证类型不存在";
                } else {
                    securityType = securityType.trim();
                    if ("C".equals(securityType)
                            || "E".equals(securityType)
                            || "U".equals(securityType)
                            || "F".equals(securityType)
                            || "H".equals(securityType)
                            ) {
                        checklog += " 的用户验证类型检核通过";
                        checkflag = "1";
                    } else {
                        checklog += " 的用户验证类型不符合检核要求";
                    }
                }
            }
            addOrUpdateCheckpointDetailRecord(record.getGuid(), ckptprogguid, checkflag, checklog);
            logger.info("网上银行产品检核：用户验证类型 "+ certtype + "-" + certno + checklog);
        }

    }

    @Override
    protected void beforeProcess() {
        logger.info("网上银行产品检核开始：用户验证类型为使用网银盾、动态口令卡，或者使用文件证书的个人网银客户");
    }

    @Override
    protected void postProcess() {
        logger.info("网上银行产品检核完成：用户验证类型为使用网银盾、动态口令卡，或者使用文件证书的个人网银客户");
    }

    //==============================================

    /**
     * @param custno
     * @param channel
     * @return C：证书方式、
     *         E：动态口令卡
     *         U：Ukey
     *         F：Ukey＋动态口令卡
     *         L：动态令牌
     *         H：Ukey＋动态令牌
     *         T：广东行CCI的CTS客户
     *         M:高端版
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
