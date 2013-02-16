package pams.view.report;

import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.OdsbStTconfirmTrad;
import pams.repository.model.Ptoplog;
import pams.repository.model.report.BasePagedQryParamBean;
import pams.service.report.OdsbRptService;
import pub.platform.security.OperatorManager;
import skyline.service.PlatformService;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;

/**
 * 基金成交客户明细 .
 * User: zhanrui
 * Date: 13-2-16
 * Time: 上午10:29
 */
@ManagedBean
@ViewScoped
public class RptA07V1Action implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private BasePagedQryParamBean paramBean;
    private OdsbStTconfirmTrad selectedRecord;

    private String custBasePopWinUrl;

    private LazyDataModel<OdsbStTconfirmTrad> lazyDataModel;
    private List<SelectItem> branchList;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{odsbRptService}")
    private OdsbRptService odsbRptService;

    @PostConstruct
    public void init() {
        OperatorManager om = SystemService.getOperatorManager();
        String operid = om.getOperatorId();
        String branchid = om.getOperator().getDeptid();

        this.branchList = toolsService.selectBranchList(branchid);
        this.paramBean = new BasePagedQryParamBean();

        this.paramBean.setStartDate(new DateTime().dayOfMonth().withMinimumValue().toString("yyyy-MM-dd"));
        this.paramBean.setEndDate(new DateTime().toString("yyyy-MM-dd"));
    }

    public String onQuery() {
        try {
            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("RptA07V1_onQuery");
            oplog.setActionName("基金成交客户明细:查询");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            oplog.setOpDataStartdate(this.paramBean.getStartDate());
            oplog.setOpDataEnddate(this.paramBean.getEndDate());
            platformService.insertNewOperationLog(oplog);

            this.lazyDataModel = new RptA07V1LazyDataModel(odsbRptService.getOdsbRptMapper(), this.paramBean);
        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。" + e.getMessage());
        }
        return null;
    }


    public String onQryCustChnSign() {
        if (this.selectedRecord == null) {
            MessageUtil.addError("请先点击选择列表中的记录...");
            return null;
        }
        this.custBasePopWinUrl = "window.open('" + "../common/custChnInfo.xhtml?certNo=" + this.selectedRecord.getCertNo() + "'"
                + ",'', 'height=400,width=800,left=200,top=100,toolbar=no,menubar=no,scrollbars=yes,location=no'"
                + ")";
        RequestContext.getCurrentInstance().execute(custBasePopWinUrl);
        return null;
    }

    //=============================================
    public BasePagedQryParamBean getParamBean() {
        return paramBean;
    }

    public void setParamBean(BasePagedQryParamBean paramBean) {
        this.paramBean = paramBean;
    }

    public OdsbStTconfirmTrad getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(OdsbStTconfirmTrad selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public String getCustBasePopWinUrl() {
        return custBasePopWinUrl;
    }

    public void setCustBasePopWinUrl(String custBasePopWinUrl) {
        this.custBasePopWinUrl = custBasePopWinUrl;
    }

    public LazyDataModel<OdsbStTconfirmTrad> getLazyDataModel() {
        return lazyDataModel;
    }

    public void setLazyDataModel(LazyDataModel<OdsbStTconfirmTrad> lazyDataModel) {
        this.lazyDataModel = lazyDataModel;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public ToolsService getToolsService() {
        return toolsService;
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public PlatformService getPlatformService() {
        return platformService;
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    public OdsbRptService getOdsbRptService() {
        return odsbRptService;
    }

    public void setOdsbRptService(OdsbRptService odsbRptService) {
        this.odsbRptService = odsbRptService;
    }
}
