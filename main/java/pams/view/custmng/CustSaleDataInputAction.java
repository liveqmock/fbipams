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
 * Time: ����11:20
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
            MessageUtil.addError("�˹���ֻ�Ի������㿪��.");
            this.detlCustList = new ArrayList<>();
        }else{
            detlCustList = custMngService.selectCustBaseByCustMgr(branchId, operId);

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("CustSaleDataInput_initList");
            oplog.setActionName("�ͻ�Ӫ����¼����:��ʼ���ͻ��嵥");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            platformService.insertNewOperationLog(oplog);
        }
    }

    public String onQuery() {
        try {
            detlCustList = custMngService.selectCustBaseByCustMgr(branchId, operId, paramBean.getCustName(),paramBean.getCustNo());

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("CustSaleDataInput_onQuery");
            oplog.setActionName("�ͻ�Ӫ����¼����:�ͻ���Ϣ��ѯ");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            platformService.insertNewOperationLog(oplog);
        } catch (Exception e) {
            logger.error("��ѯ����ʱ���ִ���", e);
            MessageUtil.addWarn("��ѯ����ʱ���ִ���" + e.getMessage());
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
            logger.error("��ѯ����ʱ���ִ���", e);
            MessageUtil.addWarn("��ѯ����ʱ���ִ���" + e.getMessage());
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
            oplog.setActionName("�ͻ�Ӫ����Ϣ����:�޸Ŀͻ�������Ϣ");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            platformService.insertNewOperationLog(oplog);

        }
    }


    //====Ӫ����Ϣ¼��====================================================
    public String onSaveBtnClick() {
        //�������ݼ��
        if (searchTellerName() == null) {
            MessageUtil.addError("��Ա�������");
            return null;
        }
        if (searchPrdName() == null) {
            MessageUtil.addError("��Ʒ�����������");
            return null;
        }

        String prdid = this.vo.getPrdid();
        String subprdid = this.vo.getSubprdid();
        //��Ʒ�������
        if (prdid.equals("01")
                || prdid.equals("02")
                || prdid.equals("03")
                || prdid.equals("04")
                || prdid.equals("06")
                || prdid.equals("08")
                || prdid.equals("09")
                ) {
            if (searchSubprdName() == null) {
                MessageUtil.addError("��Ʒ�����������");
                return null;
            }
        } else {
            this.vo.setSubprdid("");
            this.subPrdName = "";
        }

        //�ɽ�������������(��ȥ 05 06 08 09 10)
        if (prdid.equals("01") || prdid.equals("02") || prdid.equals("03")
                || prdid.equals("04") || prdid.equals("07")
                || prdid.equals("11")) {
            if (this.vo.getSalesamt1().doubleValue() <= 0) {
                MessageUtil.addError("�ɽ�����������");
                return null;
            }
            this.vo.setSalesnum1((long) 0);
        } else {
            if (this.vo.getSalesnum1() <= 0) {
                MessageUtil.addError("�ɽ������������");
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
            MessageUtil.addError("����ת������");
            logger.error("����ת������", e);
            return null;
        }

        MessageUtil.addInfo("�����ѳɹ�����...");

        //����VO
        this.vo.setSubprdid("");
        this.subPrdName = "";
        this.vo.setSalesamt1(new BigDecimal(0));
        this.vo.setSalesnum1((long) 0);
        return null;
    }


    /**
     * ��ƷID������� ��ʾ��Ʒ����
     */
    public void onPrdidListener() {
        String name = searchPrdName();
        if (name == null) {
            name = "δ�ҵ���Ӧ�Ĳ�Ʒ�������ơ�";
        }
        this.subPrdName = name;
    }

    public void onSubprdidListener() {
        if (StringUtils.isEmpty(this.vo.getSubprdid())) {
            return;
        }
        String name = searchSubprdName();
        if (name == null) {
            name = "δ�ҵ���Ӧ�Ĳ�Ʒ�������ơ�";
        }
        this.subPrdName = name;
    }

    /**
     * For UI remotecommand  ����Ʒ��������ֵ
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
     * For UI remotecommand  ����Ʒ��������ֵ
     *
     * @param actionEvent
     */
    public void onChecksubprdid(ActionEvent actionEvent) {
        if (StringUtils.isEmpty(this.vo.getSubprdid())) {
            MessageUtil.addError("��Ʒ���಻��Ϊ�գ�");
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

    //===Ӫ����ʷ=================================================
    public String onQuerySales() {
        try {
            //����ѯ����
            //String start = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            //String end = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
//                selectRecords4Svprdsalinf(start, end);

        } catch (Exception e) {
            logger.error("��ѯ����ʱ���ִ���", e);
            MessageUtil.addWarn("��ѯ����ʱ���ִ���");
        }
        return null;
    }

    public String onDeleteRecord() {

        try {
            custMngService.deleteSaleDetailInfo(this.selectedSale);
            salesVOList = custMngService.selectsaleDetails(selectedCust.getGuid());
        } catch (Exception e) {
            logger.error("����ɾ������", e);
            MessageUtil.addError("����ɾ������");
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

