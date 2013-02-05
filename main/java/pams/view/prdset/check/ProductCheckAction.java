package pams.view.prdset.check;

import org.apache.commons.lang.StringUtils;
import pams.batch.productsetcheck.CronMainHandler;
import pams.common.utils.MessageUtil;
import pams.service.prdset.PsBatchCheckService;
import pams.service.prdset.PsSalesInfoService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-5-24
 * Time: 下午9:28
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
public class ProductCheckAction {

    private String prdid;


    @ManagedProperty(value = "#{prdsetCheck}")
    private CronMainHandler cron;

    @ManagedProperty(value = "#{psSalesInfoService}")
    private PsSalesInfoService psSalesInfoService;

    @ManagedProperty(value = "#{psBatchCheckService}")
    private PsBatchCheckService psBatchCheckService;

    public String getPrdid() {
        return prdid;
    }

    public void setPrdid(String prdid) {
        this.prdid = prdid;
    }

    public void setCron(CronMainHandler cron) {
        this.cron = cron;
    }

    public void setPsSalesInfoService(PsSalesInfoService psSalesInfoService) {
        this.psSalesInfoService = psSalesInfoService;
    }

    public void setPsBatchCheckService(PsBatchCheckService psBatchCheckService) {
        this.psBatchCheckService = psBatchCheckService;
    }

    //==============================================================================
    public String onStartCheck() {
        try {
            if (StringUtils.isEmpty(prdid)) {
                cron.run();
            } else {
                cron.run(prdid);
            }
            MessageUtil.addInfo("检核程序已完成...");
        } catch (Exception e) {
            MessageUtil.addError(e.getMessage());
        }
        return null;
    }

    public String onStartForceCheck() {
        try {
            if (StringUtils.isEmpty(prdid)) {
                psBatchCheckService.updateAllProductSetCheckFlagToFail();
                cron.run();
            } else {
                cron.run(prdid);
            }
            MessageUtil.addInfo("检核程序已完成...");
        } catch (Exception e) {
            MessageUtil.addError(e.getMessage());
        }
        return null;
    }
}
