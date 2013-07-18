package pams.view.custmng;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.Ptoplog;
import pams.repository.model.SvClsCustinfo;
import pams.repository.model.SvCmsCustbase;
import pams.repository.model.custlist.CustMngParam;
import skyline.service.PlatformService;
import skyline.service.ToolsService;
import pams.service.custlist.importdata.CustlistService;
import pams.service.custmng.CustMngService;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 客户信息管理 基本信息修改 业务信息查询.
 * User: zhanrui
 * Date: 13-1-24
 * Time: 上午11:00
 */
@ManagedBean
@ViewScoped
public class CustBizDataAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private CustMngParam paramBean;

    private SvCmsCustbase selectedCust;
    private SvClsCustinfo selectedBiz;

    private List<SvCmsCustbase> detlCustList;
    private List<SvClsCustinfo> detlBizList;

    private List<SelectItem> branchList;
    private Map<String, String> rptNameMap;

    private String branchId;
    private String operId;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{custlistService}")
    private CustlistService custlistService;

    @ManagedProperty(value = "#{custMngService}")
    private CustMngService custMngService;

    @PostConstruct
    public void init() {
        OperatorManager om = SystemService.getOperatorManager();
        branchId = om.getOperator().getDeptid();
        operId = om.getOperatorId();

        this.branchList = toolsService.selectBranchList(branchId);
        this.paramBean = new CustMngParam();
        rptNameMap = custlistService.selectPtEnuDeatilItemsExpandMap("CUST_LIST_RPT_TYPE");

        initList();
    }

    private void  initList() {
        if (branchList.size() > 1) {
            MessageUtil.addError("此功能只对基层网点开放.");
            this.detlCustList = new ArrayList<>();
        }else{
            detlCustList = custMngService.selectCustBaseByCustMgr(branchId, operId);

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("CustBizData_initList");
            oplog.setActionName("客户业务信息:查询");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            platformService.insertNewOperationLog(oplog);
        }
    }
    public String onQuery() {
        try {
            detlCustList = custMngService.selectCustBaseByCustMgr(branchId, operId, paramBean.getCustName(),paramBean.getCustNo());

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("CustBizData_onQuery");
            oplog.setActionName("客户业务信息:查询");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            platformService.insertNewOperationLog(oplog);
        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。" + e.getMessage());
        }
        return null;
    }
    public void onCustRowSelect(SelectEvent event) {
        try {
            this.detlBizList = custlistService.selectCustlistRecordsByCertInfo(selectedCust.getCertType(), selectedCust.getCertNo());
        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。" + e.getMessage());
        }
    }
    public void onCustRowUnSelect(UnselectEvent event) {
        logger.debug("unselect" + event.toString());
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        org.primefaces.component.api.UIColumn uic = event.getColumn();
        if (newValue != null && !newValue.equals(oldValue)) {
            Object source = event.getSource();
            DataTable dt = (DataTable) source;
            String guid = (String) dt.getRowKey();
            String id = uic.getCellEditor().getId();
            if ("contractExt".equals(id)) {
                custMngService.updateCustBaseContractExtInfo(guid, (String) newValue);
            }else if ("remark".equals(id)) {
                custMngService.updateCustBaseRemark(guid, (String) newValue);
            }

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("CustBizData_onCellEdit");
            oplog.setActionName("客户业务信息:修改客户基本信息");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            platformService.insertNewOperationLog(oplog);

        }
    }


    public String onQryCustChnSign() {
        if (this.selectedCust == null) {
            MessageUtil.addError("请先点击选择列表中的记录...");
            return null;
        }
        String custBasePopWinUrl = "window.open('" + "../common/custChnInfo.xhtml?certNo=" + this.selectedCust.getCertNo() + "'"
                + ",'', 'height=400,width=800,left=200,top=100,toolbar=no,menubar=no,scrollbars=yes,location=no'"
                + ")";
        RequestContext.getCurrentInstance().execute(custBasePopWinUrl);
        return null;
    }

    //===================================================================


    public CustMngParam getParamBean() {
        return paramBean;
    }

    public void setParamBean(CustMngParam paramBean) {
        this.paramBean = paramBean;
    }

    public SvCmsCustbase getSelectedCust() {
        return selectedCust;
    }

    public void setSelectedCust(SvCmsCustbase selectedCust) {
        this.selectedCust = selectedCust;
    }

    public SvClsCustinfo getSelectedBiz() {
        return selectedBiz;
    }

    public void setSelectedBiz(SvClsCustinfo selectedBiz) {
        this.selectedBiz = selectedBiz;
    }

    public List<SvCmsCustbase> getDetlCustList() {
        return detlCustList;
    }

    public void setDetlCustList(List<SvCmsCustbase> detlCustList) {
        this.detlCustList = detlCustList;
    }

    public List<SvClsCustinfo> getDetlBizList() {
        return detlBizList;
    }

    public void setDetlBizList(List<SvClsCustinfo> detlBizList) {
        this.detlBizList = detlBizList;
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

    public CustlistService getCustlistService() {
        return custlistService;
    }

    public void setCustlistService(CustlistService custlistService) {
        this.custlistService = custlistService;
    }

    public CustMngService getCustMngService() {
        return custMngService;
    }

    public void setCustMngService(CustMngService custMngService) {
        this.custMngService = custMngService;
    }

    public Map<String, String> getRptNameMap() {
        return rptNameMap;
    }

    public void setRptNameMap(Map<String, String> rptNameMap) {
        this.rptNameMap = rptNameMap;
    }
}