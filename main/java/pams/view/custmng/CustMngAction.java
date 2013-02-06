package pams.view.custmng;

import org.apache.commons.lang.StringUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.Ptoper;
import pams.repository.model.Ptoplog;
import pams.repository.model.custlist.CustMngParam;
import pams.repository.model.custlist.CustMngVO;
import pams.service.custmng.CustMngService;
import pub.platform.security.OperatorManager;
import skyline.service.PlatformService;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户信息管理.
 * User: zhanrui
 * Date: 13-1-24
 * Time: 上午11:00
 */
@ManagedBean
@ViewScoped
public class CustMngAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private CustMngParam paramBean;
    private CustMngVO selectedRecord;
    private CustMngVO[] selectedRecords;


    private LazyDataModel<CustMngVO> lazyDataModel;
    private List<SelectItem> branchList;
    private List<SelectItem> custmgrList;
    private String[] custmgrs;
    private List<SelectItem> rptTypeList;
    private String custmgrId;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{custMngService}")
    private CustMngService custMngService;

    @PostConstruct
    public void init() {
        OperatorManager om = SystemService.getOperatorManager();
        String operid = om.getOperatorId();
        String branchid = om.getOperator().getDeptid();

        this.branchList = toolsService.selectBranchList(branchid);
        this.rptTypeList = toolsService.getEnuSelectItemList4Expand("CUST_LIST_RPT_TYPE", false);
        this.custmgrList = selectOperList(branchid);
        this.custmgrs = new String[custmgrList.size()];
        int i = 0;
        for (SelectItem selectItem : this.custmgrList) {
            this.custmgrs[i] = selectItem.getLabel();
            i++;
        }

        this.paramBean = new CustMngParam();

        //DateTime lastmonth = new DateTime().minusMonths(1).dayOfMonth().withMaximumValue();
        this.paramBean.setRptDate(custMngService.selectMaxCurrMaxRptdate());
        //this.paramBean.setHideDispatchedFlag(true);
    }

    public String onQuery() {
        try {
            if (StringUtils.isNotEmpty(this.paramBean.getCustName())) {
                this.paramBean.setCustNameList(this.paramBean.getCustName().split("\\|"));
            }
            this.lazyDataModel = new CustMngDataModel(custMngService.getCustlistMapper(), this.paramBean);

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("CustMng_onQuery");
            oplog.setActionName("客户营销管理:客户名单制信息查询");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            oplog.setOpDataStartdate(this.paramBean.getRptDate());
            oplog.setOpLog(this.paramBean.getRptType());
            platformService.insertNewOperationLog(oplog);
        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。" + e.getMessage());
        }
        return null;
    }

    public String onQueryCustBase() {
        try {
            String custName = this.paramBean.getCustName();
            if (StringUtils.isNotEmpty(custName)) {
                this.paramBean.setCustNameList(custName.split("\\|"));
            }
            this.lazyDataModel = new CustBaseDataModel(custMngService.getCustlistMapper(), this.paramBean);

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("CustMng_onQueryCustBase");
            oplog.setActionName("客户营销管理:客户基本信息查询");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            if (!custName.isEmpty()) {
                if (custName.length() >= 200) {
                    custName = custName.substring(0, 200);
                }
                oplog.setOpLog(custName);
            }
            platformService.insertNewOperationLog(oplog);
        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。" + e.getMessage());
        }
        return null;
    }

    public String onDispatch() {
        if (selectedRecords.length == 0) {
            MessageUtil.addError("请选择客户信息（点击单选、按住 Shift+点击 连续多选、按住 Ctrl+点击 挑选）...");
            return null;
        }

        try {
            for (CustMngVO record : selectedRecords) {
                custMngService.dispatchCustMgr(record.getGuid(), custmgrId);
            }
            this.lazyDataModel = new CustMngDataModel(custMngService.getCustlistMapper(), this.paramBean);

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("CustMng_onDispatch");
            oplog.setActionName("客户营销管理:客户分配");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            platformService.insertNewOperationLog(oplog);

            if (StringUtils.isEmpty(custmgrId)) {
                MessageUtil.addInfo("清除分配操作成功...");
            } else {
                MessageUtil.addInfo("分配操作成功...");
            }
        } catch (Exception e) {
            logger.error("分派客户经理时出现错误...", e);
            MessageUtil.addWarn("分派客户经理时出现错误。" + e.getMessage());
        }
        return null;
    }

    public String onDispatchInCustBase() {
        if (selectedRecords.length == 0) {
            MessageUtil.addError("请选择客户信息（点击单选、按住 Shift+点击 连续多选、按住 Ctrl+点击 挑选）...");
            return null;
        }

        try {
            for (CustMngVO record : selectedRecords) {
                custMngService.dispatchCustMgr(record.getGuid(), custmgrId);
            }
            this.lazyDataModel = new CustBaseDataModel(custMngService.getCustlistMapper(), this.paramBean);

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("CustMng_onDispatchInCustBase");
            oplog.setActionName("客户营销管理:客户分配");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            platformService.insertNewOperationLog(oplog);

            if (StringUtils.isEmpty(custmgrId)) {
                MessageUtil.addInfo("清除分配操作成功...");
            } else {
                MessageUtil.addInfo("分配操作成功...");
            }
        } catch (Exception e) {
            logger.error("分派客户经理时出现错误。", e);
            MessageUtil.addWarn("分派客户经理时出现错误。" + e.getMessage());
        }
        return null;
    }


    private List<SelectItem> selectOperList(String branchId) {
        List<Ptoper> records = custMngService.selectCustMgr(branchId);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        selectItems.add(new SelectItem("", ""));
        for (Ptoper record : records) {
            SelectItem item = new SelectItem(record.getOperid(), record.getOpername());
            selectItems.add(item);
        }
        return selectItems;
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        org.primefaces.component.api.UIColumn uic = event.getColumn();
        if (newValue != null && !newValue.equals(oldValue)) {
            if ("custmgr".equals(uic.getCellEditor().getId())) {
                Object source = event.getSource();
                DataTable dt = (DataTable) source;
                String guid = (String) dt.getRowKey();
                custMngService.dispatchCustMgr(guid, (String) newValue);
            }
        }
    }
    //===================================================================
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Car Selected", ((CustMngVO) event.getObject()).getCustName());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Car Unselected", ((CustMngVO) event.getObject()).getCustName());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //===================================================================
    public CustMngParam getParamBean() {
        return paramBean;
    }

    public void setParamBean(CustMngParam paramBean) {
        this.paramBean = paramBean;
    }

    public CustMngVO getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(CustMngVO selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public LazyDataModel<CustMngVO> getLazyDataModel() {
        return lazyDataModel;
    }

    public void setLazyDataModel(LazyDataModel<CustMngVO> lazyDataModel) {
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

    public CustMngService getCustMngService() {
        return custMngService;
    }

    public void setCustMngService(CustMngService custMngService) {
        this.custMngService = custMngService;
    }

    public CustMngVO[] getSelectedRecords() {
        return selectedRecords;
    }

    public void setSelectedRecords(CustMngVO[] selectedRecords) {
        this.selectedRecords = selectedRecords;
    }

    public List<SelectItem> getCustmgrList() {
        return custmgrList;
    }

    public String getCustmgrId() {
        return custmgrId;
    }

    public void setCustmgrId(String custmgrId) {
        this.custmgrId = custmgrId;
    }

    public void setCustmgrList(List<SelectItem> custmgrList) {

        this.custmgrList = custmgrList;
    }

    public List<SelectItem> getRptTypeList() {
        return rptTypeList;
    }

    public void setRptTypeList(List<SelectItem> rptTypeList) {
        this.rptTypeList = rptTypeList;
    }

    public String[] getCustmgrs() {
        return custmgrs;
    }

    public void setCustmgrs(String[] custmgrs) {
        this.custmgrs = custmgrs;
    }
}