package pams.view.custmng;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.*;
import pams.repository.model.custlist.CustMngParam;
import pams.service.custmng.CustMngService;
import pub.platform.security.OperatorManager;
import skyline.service.PlatformService;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-2-18
 * Time: 上午11:20
 */

@ManagedBean
@ViewScoped
public class CustSaleDataInputAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private CustMngParam paramBean;

    private SvCmsCustbase selectedCust;
    private List<SvCmsCustbase> detlCustList;
    private List<SvClsCustinfo> detlBizList;

    private List<SelectItem> branchList;
    private Map<String, String> rptNameMap;

    private String branchId;
    private String operId;

    //========================
    private SvSaleDetail vo;
    private String txnDate;
    private String custName;
    private String certType;
    private String certNo;
    private String prdName;
    private String subPrdName;
    private boolean isSubprdidShow = true;
    private List<Svprdsalinf> salesList;
    private List<Ptoper> ptoperList;
    private List<Ptenudetail> prdTypeList;
    private List<Ptenudetail> insureTypeList;
    private List<Ptenudetail> fpTypeList;
    private List<Ptenudetail> foundTypeList;
    private List<Ptenudetail> depositTypeList;
    private List<Ptenudetail> creditcardTypeList;
    private List<Ptenudetail> ebankTypeList;
    private List<Ptenudetail> goldTypeList;
    private List<SelectItem> certTypeList;

    //=============================
    private List<SvSaleDetail> salesVOList;
    private SvSaleDetail selectedSale;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;

    @ManagedProperty(value = "#{custMngService}")
    private CustMngService custMngService;

    public CustSaleDataInputAction() {
    }

    @PostConstruct
    public void init() {
        OperatorManager om = SystemService.getOperatorManager();
        branchId = om.getOperator().getDeptid();
        operId = om.getOperatorId();

        this.branchList = toolsService.selectBranchList(branchId);
        this.paramBean = new CustMngParam();
        initCustList();

        //===========
        this.vo = new SvSaleDetail();
        this.vo.setSalesamt1(new BigDecimal(0));
        this.vo.setSalesnum1((long) 0);

        this.txnDate = new DateTime().toString("yyyy-MM-dd");
        this.vo.setTxndate(this.txnDate);

        this.vo.setTellerid(this.operId);
        this.ptoperList = platformService.selectBranchTellers(this.operId);
        this.prdTypeList = platformService.selectEnuDetail("SVTPRDTYPE");
        this.foundTypeList = platformService.selectEnuDetail("SVTFUNDTYPE");
        this.insureTypeList = platformService.selectEnuDetail("SVTINSURETYPE");
        this.fpTypeList = platformService.selectEnuDetail("SVTFPTYPE");
        this.depositTypeList = platformService.selectEnuDetail("SVTDEPOSITTYPE");
        this.creditcardTypeList = platformService.selectEnuDetail("SVTCREDITCARDTYPE");
        this.ebankTypeList = platformService.selectEnuDetail("SVTEBANKTYPE");
        this.goldTypeList = platformService.selectEnuDetail("SVTGOLDTYPE");

        this.certTypeList = toolsService.getEnuSelectItemList("CERTTYPE", false, true);

    }

    private void initCustList() {
        if (branchList.size() > 1) {
            MessageUtil.addError("此功能只对基层网点开放.");
            this.detlCustList = new ArrayList<>();
        }else{
            detlCustList = custMngService.selectCustBaseByCustMgr(branchId, operId);

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("CustSaleDataInput_initList");
            oplog.setActionName("客户营销记录反馈:初始化客户清单");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            platformService.insertNewOperationLog(oplog);
        }
    }

    public String onQuery() {
        try {
            detlCustList = custMngService.selectCustBaseByCustMgr(branchId, operId, paramBean.getCustName(),paramBean.getCustNo());

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("CustSaleDataInput_onQuery");
            oplog.setActionName("客户营销记录反馈:客户信息查询");
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
            this.custName = selectedCust.getCustName();
            this.certType = selectedCust.getCertType();
            this.certNo = selectedCust.getCertNo();
            this.vo.setCustguid(selectedCust.getGuid());
            salesVOList = custMngService.selectsaleDetails(selectedCust.getGuid());
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
            oplog.setActionId("CusSaleDataInput_onCellEdit");
            oplog.setActionName("客户营销信息管理:修改客户基本信息");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            platformService.insertNewOperationLog(oplog);

        }
    }


    //====营销信息录入====================================================
    public String onSaveBtnClick() {
        //输入内容检查
        if (searchTellerName() == null) {
            MessageUtil.addError("柜员输入错误");
            return null;
        }
        if (searchPrdName() == null) {
            MessageUtil.addError("产品种类输入错误");
            return null;
        }

        String prdid = this.vo.getPrdid();
        String subprdid = this.vo.getSubprdid();
        //产品子类必输
        if (prdid.equals("01")
                || prdid.equals("02")
                || prdid.equals("03")
                || prdid.equals("04")
                || prdid.equals("06")
                || prdid.equals("08")
                || prdid.equals("09")
                ) {
            if (searchSubprdName() == null) {
                MessageUtil.addError("产品子类输入错误");
                return null;
            }
        } else {
            this.vo.setSubprdid("");
            this.subPrdName = "";
        }

        //成交金额或数量必输(除去 05 06 08 09 10)
        if (prdid.equals("01") || prdid.equals("02") || prdid.equals("03")
                || prdid.equals("04") || prdid.equals("07")
                || prdid.equals("11")) {
            if (this.vo.getSalesamt1().doubleValue() <= 0) {
                MessageUtil.addError("成交金额输入错误");
                return null;
            }
            this.vo.setSalesnum1((long) 0);
        } else {
            if (this.vo.getSalesnum1() <= 0) {
                MessageUtil.addError("成交数量输入错误");
                return null;
            }
            this.vo.setSalesamt1(new BigDecimal(0));
        }


        //==
        Date date = new Date();

        if (this.vo.getTxntime() == null) {
            this.vo.setTxntime(new SimpleDateFormat("HH:mm:ss").format(date));
        }
        this.vo.setOperid(this.operId);
        this.vo.setOperdate(date);
        this.vo.setRecversion((long) 0);

        SvSaleDetail saleDetail;
        try {
            saleDetail = new SvSaleDetail();
            PropertyUtils.copyProperties(saleDetail, this.vo);
            custMngService.insertSaleDetailInfo(saleDetail);
            salesVOList = custMngService.selectsaleDetails(selectedCust.getGuid());
        } catch (Exception e) {
            MessageUtil.addError("数据转换错误！");
            logger.error("数据转换错误！", e);
            return null;
        }

        MessageUtil.addInfo("数据已成功保存...");

        //重置VO
        this.vo.setSubprdid("");
        this.subPrdName = "";
        this.vo.setSalesamt1(new BigDecimal(0));
        this.vo.setSalesnum1((long) 0);
        return null;
    }


    /**
     * 产品ID输入完后 提示产品名称
     */
    public void onPrdidListener() {
        String name = searchPrdName();
        if (name == null) {
            name = "未找到对应的产品种类名称。";
        }
        this.subPrdName = name;
    }

    public void onSubprdidListener() {
        if (StringUtils.isEmpty(this.vo.getSubprdid())) {
            return;
        }
        String name = searchSubprdName();
        if (name == null) {
            name = "未找到对应的产品子类名称。";
        }
        this.subPrdName = name;
    }

    /**
     * For UI remotecommand  检查产品种类输入值
     *
     * @param actionEvent
     */
    public void onCheckprdid(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (searchPrdName() == null) {
            requestContext.addCallbackParam("isValid", false);
        } else
            requestContext.addCallbackParam("isValid", true);
    }

    /**
     * For UI remotecommand  检查产品子类输入值
     *
     * @param actionEvent
     */
    public void onChecksubprdid(ActionEvent actionEvent) {
        if (StringUtils.isEmpty(this.vo.getSubprdid())) {
            MessageUtil.addError("产品子类不能为空！");
            return;
        }
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (searchSubprdName() == null) {
            requestContext.addCallbackParam("isValid", false);
        } else
            requestContext.addCallbackParam("isValid", true);
    }

    public String searchTellerName() {
        for (Ptoper ptoper : this.ptoperList) {
            if (this.vo.getTellerid().equals(ptoper.getOperid())) {
                return (ptoper.getOpername());
            }
        }
        return null;
    }

    public String searchPrdName() {
        for (Ptenudetail ptenudetail : this.prdTypeList) {
            if (this.vo.getPrdid().equals(ptenudetail.getEnuitemvalue())) {
                return (ptenudetail.getEnuitemlabel());
            }
        }
        return null;
    }

    private String searchSubprdName() {
        if (this.vo.getPrdid().equals("01")) {
            for (Ptenudetail ptenudetail : this.foundTypeList) {
                if (this.vo.getSubprdid().equals(ptenudetail.getEnuitemvalue())) {
                    return (ptenudetail.getEnuitemlabel());
                }
            }
        } else if (this.vo.getPrdid().equals("02")) {
            for (Ptenudetail ptenudetail : this.insureTypeList) {
                if (this.vo.getSubprdid().equals(ptenudetail.getEnuitemvalue())) {
                    return (ptenudetail.getEnuitemlabel());
                }
            }
        } else if (this.vo.getPrdid().equals("03")) {
            for (Ptenudetail ptenudetail : this.fpTypeList) {
                if (this.vo.getSubprdid().equals(ptenudetail.getEnuitemvalue())) {
                    return (ptenudetail.getEnuitemlabel());
                }
            }
        } else if (this.vo.getPrdid().equals("04")) {
            for (Ptenudetail ptenudetail : this.depositTypeList) {
                if (this.vo.getSubprdid().equals(ptenudetail.getEnuitemvalue())) {
                    return (ptenudetail.getEnuitemlabel());
                }
            }
        } else if (this.vo.getPrdid().equals("06")) {
            for (Ptenudetail ptenudetail : this.creditcardTypeList) {
                if (this.vo.getSubprdid().equals(ptenudetail.getEnuitemvalue())) {
                    return (ptenudetail.getEnuitemlabel());
                }
            }
        } else if (this.vo.getPrdid().equals("08")) {
            for (Ptenudetail ptenudetail : this.ebankTypeList) {
                if (this.vo.getSubprdid().equals(ptenudetail.getEnuitemvalue())) {
                    return (ptenudetail.getEnuitemlabel());
                }
            }
        } else if (this.vo.getPrdid().equals("09")) {
            for (Ptenudetail ptenudetail : this.goldTypeList) {
                if (this.vo.getSubprdid().equals(ptenudetail.getEnuitemvalue())) {
                    return (ptenudetail.getEnuitemlabel());
                }
            }
        }
        return null;
    }

    //===营销历史=================================================
    public String onQuerySales() {
        try {
            //检查查询参数
            //String start = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            //String end = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
//                selectRecords4Svprdsalinf(start, end);

        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。");
        }
        return null;
    }

    public String onDeleteRecord() {

        try {
            custMngService.deleteSaleDetailInfo(this.selectedSale);
            salesVOList = custMngService.selectsaleDetails(selectedCust.getGuid());
        } catch (Exception e) {
            logger.error("数据删除错误！", e);
            MessageUtil.addError("数据删除错误！");
        }
        return null;
    }


    //====================================================

    public SvSaleDetail getVo() {
        return vo;
    }

    public void setVo(SvSaleDetail vo) {
        this.vo = vo;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public boolean isSubprdidShow() {
        return isSubprdidShow;
    }

    public void setSubprdidShow(boolean subprdidShow) {
        isSubprdidShow = subprdidShow;
    }

    public List<Svprdsalinf> getSalesList() {
        return salesList;
    }

    public void setSalesList(List<Svprdsalinf> salesList) {
        this.salesList = salesList;
    }

    public List<Ptoper> getPtoperList() {
        return ptoperList;
    }

    public void setPtoperList(List<Ptoper> ptoperList) {
        this.ptoperList = ptoperList;
    }

    public List<Ptenudetail> getPrdTypeList() {
        return prdTypeList;
    }

    public void setPrdTypeList(List<Ptenudetail> prdTypeList) {
        this.prdTypeList = prdTypeList;
    }

    public List<Ptenudetail> getInsureTypeList() {
        return insureTypeList;
    }

    public void setInsureTypeList(List<Ptenudetail> insureTypeList) {
        this.insureTypeList = insureTypeList;
    }

    public List<Ptenudetail> getFpTypeList() {
        return fpTypeList;
    }

    public void setFpTypeList(List<Ptenudetail> fpTypeList) {
        this.fpTypeList = fpTypeList;
    }

    public List<Ptenudetail> getFoundTypeList() {
        return foundTypeList;
    }

    public void setFoundTypeList(List<Ptenudetail> foundTypeList) {
        this.foundTypeList = foundTypeList;
    }

    public List<Ptenudetail> getCreditcardTypeList() {
        return creditcardTypeList;
    }

    public void setCreditcardTypeList(List<Ptenudetail> creditcardTypeList) {
        this.creditcardTypeList = creditcardTypeList;
    }

    public List<Ptenudetail> getEbankTypeList() {
        return ebankTypeList;
    }

    public void setEbankTypeList(List<Ptenudetail> ebankTypeList) {
        this.ebankTypeList = ebankTypeList;
    }

    public List<Ptenudetail> getGoldTypeList() {
        return goldTypeList;
    }

    public void setGoldTypeList(List<Ptenudetail> goldTypeList) {
        this.goldTypeList = goldTypeList;
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

    public Map<String, String> getRptNameMap() {
        return rptNameMap;
    }

    public void setRptNameMap(Map<String, String> rptNameMap) {
        this.rptNameMap = rptNameMap;
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

    public CustMngService getCustMngService() {
        return custMngService;
    }

    public void setCustMngService(CustMngService custMngService) {
        this.custMngService = custMngService;
    }

    public List<SvSaleDetail> getSalesVOList() {
        return salesVOList;
    }

    public void setSalesVOList(List<SvSaleDetail> salesVOList) {
        this.salesVOList = salesVOList;
    }

    public SvSaleDetail getSelectedSale() {
        return selectedSale;
    }

    public void setSelectedSale(SvSaleDetail selectedSale) {
        this.selectedSale = selectedSale;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getSubPrdName() {
        return subPrdName;
    }

    public void setSubPrdName(String subPrdName) {
        this.subPrdName = subPrdName;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public List<Ptenudetail> getDepositTypeList() {
        return depositTypeList;
    }

    public void setDepositTypeList(List<Ptenudetail> depositTypeList) {
        this.depositTypeList = depositTypeList;
    }
}

