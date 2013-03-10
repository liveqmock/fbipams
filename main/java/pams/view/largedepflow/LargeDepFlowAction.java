package pams.view.largedepflow;

import org.joda.time.DateTime;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.OdsbLargedepFlow;
import pams.repository.model.Ptoplog;
import pams.repository.model.largedepflow.LargeDepFlowVO;
import skyline.service.PlatformService;
import skyline.service.ToolsService;
import pams.service.largedepflow.LargeDepFlowService;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;

/**
 * �����������.
 * User: zhanrui
 * Date: 13-1-24
 * Time: ����11:00
 */
@ManagedBean
@ViewScoped
public class LargeDepFlowAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private LargeDepFlowVO paramBean;
    private OdsbLargedepFlow selectedRecord;

    private boolean isBizBranch; //�Ƿ�ҵ������

    private LazyDataModel<OdsbLargedepFlow> lazyDataModel;
    private List<SelectItem> branchList;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{largeDepFlowService}")
    private LargeDepFlowService largeDepFlowService;

    @PostConstruct
    public void init() {
        OperatorManager om = SystemService.getOperatorManager();
        String operid = om.getOperatorId();
        String branchid = om.getOperator().getDeptid();

        this.branchList = toolsService.selectBranchList(branchid);
        if (branchList.size() == 1) {
            isBizBranch = true;
        }

        this.paramBean = new LargeDepFlowVO();

        this.paramBean.setStartDate(new DateTime().dayOfMonth().withMinimumValue().toString("yyyy-MM-dd"));
        this.paramBean.setEndDate(new DateTime().minusDays(1).toString("yyyy-MM-dd"));
    }

    public String onQuery() {
        try {
            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("LargeDepFlow_onQuery");
            oplog.setActionName("����ʽ�����:��ѯ");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            oplog.setOpDataStartdate(this.paramBean.getStartDate());
            oplog.setOpDataEnddate(this.paramBean.getEndDate());
            platformService.insertNewOperationLog(oplog);

            this.lazyDataModel = new LargeDepFlowLazyDataModel(largeDepFlowService.getLargeDepFlowMapper(), this.paramBean);
        } catch (Exception e) {
            logger.error("��ѯ����ʱ���ִ���", e);
            MessageUtil.addWarn("��ѯ����ʱ���ִ���" + e.getMessage());
        }
        return null;
    }

    //===================================================================

    public LargeDepFlowVO getParamBean() {
        return paramBean;
    }

    public void setParamBean(LargeDepFlowVO paramBean) {
        this.paramBean = paramBean;
    }

    public OdsbLargedepFlow getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(OdsbLargedepFlow selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public LazyDataModel<OdsbLargedepFlow> getLazyDataModel() {
        return lazyDataModel;
    }

    public void setLazyDataModel(LazyDataModel<OdsbLargedepFlow> lazyDataModel) {
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

    public LargeDepFlowService getLargeDepFlowService() {
        return largeDepFlowService;
    }

    public void setLargeDepFlowService(LargeDepFlowService largeDepFlowService) {
        this.largeDepFlowService = largeDepFlowService;
    }

    public boolean isBizBranch() {
        return isBizBranch;
    }

    public void setBizBranch(boolean bizBranch) {
        isBizBranch = bizBranch;
    }
}
