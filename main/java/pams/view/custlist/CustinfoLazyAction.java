package pams.view.custlist;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.ClsRptdata;
import pams.repository.model.Ptoplog;
import pams.repository.model.custlist.CustListParam;
import pams.service.custlist.importdata.CustlistService;
import pub.platform.security.OperatorManager;
import skyline.service.PlatformService;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 客户名单制报表查询
 * User: zhanrui
 * Date: 12-12-11
 * Time: 下午12:15
 */
@ManagedBean
@ViewScoped
public class CustinfoLazyAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String operid;
    private String branchid;

    //private ClsRptdata paramBean;
    private CustListParam paramBean;
    private ClsRptdata selectedRecord;
    private int totalcount = 0;
    private BigDecimal totalamt = new BigDecimal("0.00");

    private String panelTitle;
    private String rptType;
    private boolean isBizBranch; //是否业务网点

    private LazyDataModel<ClsRptdata> lazyDataModel;

    private List<SelectItem> branchList;
    private List<SelectItem> prdsetList;
    private List<SelectItem> certTypeList;

    private Map<String, String> rptNameMap;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{custlistService}")
    private CustlistService custlistService;


    //============================================================
    @PostConstruct
    public void init() {
        OperatorManager om = SystemService.getOperatorManager();
        this.operid = om.getOperatorId();
        this.branchid = om.getOperator().getDeptid();

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        this.rptType = request.getParameter("rpt");
        if (StringUtils.isEmpty(this.rptType)) {
            logger.error("报表参数错误！");
            MessageUtil.addError("报表参数错误！");
            return;
        }
        this.panelTitle = getRptName(rptType);
        rptNameMap = custlistService.selectPtEnuDeatilItemsExpandMap("CUST_LIST_RPT_TYPE");

        this.branchList = toolsService.selectBranchList(this.branchid);
        if (branchList.size() == 1) {
            isBizBranch = true;
        }
        this.paramBean = new CustListParam();
        this.paramBean.setRptType(this.rptType);

        //DateTime lastmonth = new DateTime().minusMonths(1).dayOfMonth().withMaximumValue();
        //this.paramBean.setRptDate(lastmonth.toString("yyyyMMdd"));
        this.paramBean.setRptDate(custlistService.selectMaxCurrMaxRptdate());

    }

    public String onQuery() {
        try {
            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("CustInfoLazy_onQuery");
            oplog.setActionName("客户名单制报表查询:" + this.panelTitle);
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            oplog.setOpDataStartdate(this.paramBean.getRptDate());
            platformService.insertNewOperationLog(oplog);

            this.lazyDataModel = new LazyRptDataModel(custlistService.getCustlistMapper(), this.paramBean);
        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。" + e.getMessage());
        }
        return null;
    }

    private String getRptName(String rptType) {
        return (String)custlistService.selectPtEnuDeatilItemsLabelMap("CUST_LIST_RPT_TYPE").get(rptType);
    }

    //==================

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public CustListParam getParamBean() {
        return paramBean;
    }

    public void setParamBean(CustListParam paramBean) {
        this.paramBean = paramBean;
    }

    public ClsRptdata getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(ClsRptdata selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public BigDecimal getTotalamt() {
        return totalamt;
    }

    public void setTotalamt(BigDecimal totalamt) {
        this.totalamt = totalamt;
    }

    public String getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(String panelTitle) {
        this.panelTitle = panelTitle;
    }



    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public List<SelectItem> getPrdsetList() {
        return prdsetList;
    }

    public void setPrdsetList(List<SelectItem> prdsetList) {
        this.prdsetList = prdsetList;
    }

    public List<SelectItem> getCertTypeList() {
        return certTypeList;
    }

    public void setCertTypeList(List<SelectItem> certTypeList) {
        this.certTypeList = certTypeList;
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

    public CustlistService getCustlistService() {
        return custlistService;
    }

    public void setCustlistService(CustlistService custlistService) {
        this.custlistService = custlistService;
    }

    public String getRptType() {
        return rptType;
    }

    public void setRptType(String rptType) {
        this.rptType = rptType;
    }

    public LazyDataModel<ClsRptdata> getLazyDataModel() {
        return lazyDataModel;
    }

    public void setLazyDataModel(LazyDataModel<ClsRptdata> detlList) {
        this.lazyDataModel = detlList;
    }

    public boolean isBizBranch() {
        return isBizBranch;
    }

    public void setBizBranch(boolean bizBranch) {
        isBizBranch = bizBranch;
    }
}
