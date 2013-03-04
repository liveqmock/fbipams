package pams.view.saledata;

import org.joda.time.DateTime;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.Ptenudetail;
import pams.repository.model.Ptoplog;
import pams.repository.model.saledata.SaleDataQryParamBean;
import pams.repository.model.saledata.SaleDataVO;
import pams.service.saledata.SaleDataService;
import pub.platform.security.OperatorManager;
import skyline.service.PlatformService;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 营销数据管理
 * User: zhanrui
 * Date: 13-3-3
 * Time: 上午11:20
 */

@ManagedBean
@ViewScoped
public class SaleDataAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SaleDataQryParamBean paramBean;

    private SaleDataVO selectedRecord;
    private LazyDataModel<SaleDataVO> lazyDataModel;

    private List<SelectItem> branchList;
    private List<SelectItem> prdTypeList;

    private String branchId;
    private String operId;


    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;

    @ManagedProperty(value = "#{saleDataService}")
    private SaleDataService saleDataService;

    public SaleDataAction() {
    }

    @PostConstruct
    public void init() {
        OperatorManager om = SystemService.getOperatorManager();
        branchId = om.getOperator().getDeptid();
        operId = om.getOperatorId();

        this.branchList = toolsService.selectBranchList(branchId);
        this.paramBean = new SaleDataQryParamBean();

        this.paramBean.setStartDate(new DateTime().dayOfMonth().withMinimumValue().toString("yyyy-MM-dd"));
        this.paramBean.setEndDate(new DateTime().toString("yyyy-MM-dd"));

        initPrdTypeList();

    }

    public String onQuerySaleDeptData() {
        try {
            this.lazyDataModel = new SaleDataQryModel(saleDataService.getSaleDataMapper(), this.paramBean);

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("SaleDataAction_onQuerySaleDeptDatay");
            oplog.setActionName("营销历史管理:机构信息查询");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            platformService.insertNewOperationLog(oplog);
        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。" + e.getMessage());
        }
        return null;
    }
    public String onQuerySaleTellerData() {
        try {
            this.paramBean.setBranchId(branchId);
            this.paramBean.setTellerId(operId);
            this.lazyDataModel = new SaleDataQryModel(saleDataService.getSaleDataMapper(), this.paramBean);
            //this.paramBean.setTellerId(null);

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("SaleDataAction_onQuerySaleTellerData");
            oplog.setActionName("营销历史管理:柜员信息查询");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            platformService.insertNewOperationLog(oplog);
        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。" + e.getMessage());
        }
        return null;
    }

    public String onDeleteRecord() {

        try {
            //custMngService.deleteSaleDetailInfo(this.selectedSale);
            //salesVOList = custMngService.selectsaleDetails(selectedCust.getGuid());
        } catch (Exception e) {
            logger.error("数据删除错误！", e);
            MessageUtil.addError("数据删除错误！");
        }
        return null;
    }

    private void initPrdTypeList() {
        List<Ptenudetail> records = platformService.selectEnuDetail("SVTPRDTYPE");
        this.prdTypeList = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem("", "全部产品");
        this.prdTypeList.add(item);
        for (Ptenudetail record : records) {
            item = new SelectItem(record.getEnuitemvalue(), record.getEnuitemlabel());
            this.prdTypeList.add(item);
        }
    }


    //====================================================


    public SaleDataQryParamBean getParamBean() {
        return paramBean;
    }

    public void setParamBean(SaleDataQryParamBean paramBean) {
        this.paramBean = paramBean;
    }

    public SaleDataVO getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(SaleDataVO selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public LazyDataModel<SaleDataVO> getLazyDataModel() {
        return lazyDataModel;
    }

    public void setLazyDataModel(LazyDataModel<SaleDataVO> lazyDataModel) {
        this.lazyDataModel = lazyDataModel;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
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

    public SaleDataService getSaleDataService() {
        return saleDataService;
    }

    public void setSaleDataService(SaleDataService saleDataService) {
        this.saleDataService = saleDataService;
    }

    public List<SelectItem> getPrdTypeList() {
        return prdTypeList;
    }

    public void setPrdTypeList(List<SelectItem> prdTypeList) {
        this.prdTypeList = prdTypeList;
    }
}

