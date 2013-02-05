package pams.view.prdset.archive;

import org.joda.time.DateTime;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import skyline.service.PlatformService;
import skyline.service.ToolsService;
import pams.service.prdset.OdsbQryService;
import pams.service.prdset.PsSalesInfoService;
import pub.platform.security.OperatorManager;

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
public class PrdsetArchiveAction {

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{psSalesInfoService}")
    private PsSalesInfoService psSalesInfoService;
    @ManagedProperty(value = "#{odsbQryService}")
    private OdsbQryService odsbQryService;

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    public void setPsSalesInfoService(PsSalesInfoService psSalesInfoService) {
        this.psSalesInfoService = psSalesInfoService;
    }

    public void setOdsbQryService(OdsbQryService odsbQryService) {
        this.odsbQryService = odsbQryService;
    }

    //==============================================================================
    public String onStart() {

        //上一个月
        DateTime dateTime = new DateTime().minusMonths(1);

        String year = dateTime.toString("yyyy");
        String month = dateTime.toString("MM");

        if (psSalesInfoService.isPrdsetArchived(year, month)) {
            MessageUtil.addError(year + "年" + month + "月已做过考核处理。");
            return null;
        }

        OperatorManager om = SystemService.getOperatorManager();
        String operid = om.getOperatorId();

        int count = psSalesInfoService.updatePrdsetArchiveFlagAndControlInfo(year, month, operid);

        MessageUtil.addInfo("考核程序已完成...共处理 " + count +  " 条记录。");
        return null;
    }
}
