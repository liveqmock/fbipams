package pams.view.report;

import org.joda.time.DateTime;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.OdsbStlcrdBal;
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
 * ����������ϸ��ѯ .
 * User: zhanrui
 * Date: 13-3-28
 * Time: ����10:29
 */
@ManagedBean
@ViewScoped
public class RptA13V1Action implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private BasePagedQryParamBean paramBean;
    private OdsbStlcrdBal selectedRecord;
    private boolean isBizBranch; //�Ƿ�ҵ������

    private String custBasePopWinUrl;
    private String title;

    private LazyDataModel<OdsbStlcrdBal> lazyDataModel;
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
        if (branchList.size() == 1) {
            isBizBranch = true;
        }
        this.paramBean = new BasePagedQryParamBean();
        this.paramBean.setBranchId(branchid);
        this.paramBean.setStartDate(new DateTime().dayOfMonth().withMinimumValue().toString("yyyy-MM-dd"));
        this.paramBean.setEndDate(new DateTime().toString("yyyy-MM-dd"));

    }

    public String onQuery() {
        try {
            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("RptA13V1_onQuery");
            oplog.setActionName("����ͨ�������ϸ��:��ѯ");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            oplog.setOpDataStartdate(this.paramBean.getStartDate());
            oplog.setOpDataEnddate(this.paramBean.getEndDate());
            platformService.insertNewOperationLog(oplog);

            this.lazyDataModel = new RptA13V1LazyDataModel(odsbRptService.getOdsbRptMapper(), this.paramBean);
        } catch (Exception e) {
            logger.error("��ѯ����ʱ���ִ���", e);
            MessageUtil.addWarn("��ѯ����ʱ���ִ���" + e.getMessage());
        }
        return null;
    }


    //=============================================
    public BasePagedQryParamBean getParamBean() {
        return paramBean;
    }

    public void setParamBean(BasePagedQryParamBean paramBean) {
        this.paramBean = paramBean;
    }

    public OdsbStlcrdBal getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(OdsbStlcrdBal selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public String getCustBasePopWinUrl() {
        return custBasePopWinUrl;
    }

    public void setCustBasePopWinUrl(String custBasePopWinUrl) {
        this.custBasePopWinUrl = custBasePopWinUrl;
    }

    public LazyDataModel<OdsbStlcrdBal> getLazyDataModel() {
        return lazyDataModel;
    }

    public void setLazyDataModel(LazyDataModel<OdsbStlcrdBal> lazyDataModel) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isBizBranch() {
        return isBizBranch;
    }

    public void setBizBranch(boolean bizBranch) {
        isBizBranch = bizBranch;
    }
}
