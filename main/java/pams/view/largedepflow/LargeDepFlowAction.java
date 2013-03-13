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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * 大额存款流向监控.
 * User: zhanrui
 * Date: 13-1-24
 * Time: 上午11:00
 */
@ManagedBean
@ViewScoped
public class LargeDepFlowAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private LargeDepFlowVO paramBean;
    private OdsbLargedepFlow selectedRecord;

    private boolean isBizBranch; //是否业务网点
    private String currCd;   //币种  CNY：人民币   XXX：外币
    private String title = "...";

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

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        this.currCd = request.getParameter("curr");
        if (currCd == null) {
            MessageUtil.addError("模式参数错误");
        }

        if ("CNY".equals(currCd)) {
            this.title = "人民币大额存款资金流向查询(交易金额>=50000)";
        }
        if ("XXX".equals(currCd)) {
            this.title = "外币存款资金流向查询(交易金额>=100)";
        }

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
            oplog.setActionName("大额资金流向:查询");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            oplog.setOpDataStartdate(this.paramBean.getStartDate());
            oplog.setOpDataEnddate(this.paramBean.getEndDate());
            platformService.insertNewOperationLog(oplog);

            this.paramBean.setCurrCd(this.currCd);
            this.lazyDataModel = new LargeDepFlowLazyDataModel(largeDepFlowService.getLargeDepFlowMapper(), this.paramBean);
        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。" + e.getMessage());
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

    public String getCurrCd() {
        return currCd;
    }

    public void setCurrCd(String currCd) {
        this.currCd = currCd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
