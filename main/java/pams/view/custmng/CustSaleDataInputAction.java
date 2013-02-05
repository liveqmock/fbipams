package pams.view.custmng;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.*;
import pams.repository.model.telemarketing.SalesInfoBean;
import pams.service.telemarketing.TmSalesInfoService;
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
    private String operid;
    private SalesInfoBean vo;
    //��������
    private String txnDate;
    //�������������־
    private String batchInputMode;
    //�������·��
    //perf������ҵ����
    //plan������ҵ���ƻ���
    //interview�������̸��
    private String routerPath;

    private String panelTitle;

    private boolean isSubprdidShow = true;

    private List<Svprdsalinf> salesList;

    private List<Ptoper> ptoperList;
    private List<Ptenudetail> prdTypeList;
    private List<Ptenudetail> insureTypeList;
    private List<Ptenudetail> fpTypeList;
    private List<Ptenudetail> foundTypeList;
    private List<Ptenudetail> creditcardTypeList;
    private List<Ptenudetail> ebankTypeList;
    private List<Ptenudetail> goldTypeList;

    private List<SelectItem> certTypeList;
    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;

    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{tmSalesInfoService}")
    private TmSalesInfoService salesInfoService;

    public CustSaleDataInputAction() {
    }

    @PostConstruct
    public void init() {
        this.vo = new SalesInfoBean();
        this.vo.setSalesamt1(new BigDecimal(0));
        this.vo.setSalesnum1((long) 0);

        this.salesList = new ArrayList();

/*
        FacesContext context = FacesContext.getCurrentInstance();
        Map params = context.getExternalContext().getSessionMap();

        String paramTxndate = (String) params.get("txnDate");
        params.remove("txnDate");
        if (StringUtils.isEmpty(paramTxndate)) {
            logger.error("���ڲ�������");
            MessageUtil.addError("���ڲ�������");
            return;
        }
                this.txnDate = paramTxndate;

*/
        this.txnDate = new DateTime().toString("yyyy-MM-dd");
        this.vo.setTxndate(this.txnDate);

        this.batchInputMode = "perf";
        this.panelTitle = "����ҵ������¼��";

        OperatorManager om = SystemService.getOperatorManager();
        this.operid = om.getOperatorId();
        this.vo.setTellerid(this.operid);
        this.vo.setTellername(om.getOperatorName());

        this.ptoperList = platformService.selectBranchTellers(this.operid);
        this.prdTypeList = platformService.selectEnuDetail("SVTPRDTYPE");
        this.foundTypeList = platformService.selectEnuDetail("SVTFUNDTYPE");
        this.insureTypeList = platformService.selectEnuDetail("SVTINSURETYPE");
        this.fpTypeList = platformService.selectEnuDetail("SVTFPTYPE");
        this.creditcardTypeList = platformService.selectEnuDetail("SVTCREDITCARDTYPE");
        this.ebankTypeList = platformService.selectEnuDetail("SVTEBANKTYPE");
        this.goldTypeList = platformService.selectEnuDetail("SVTGOLDTYPE");

        this.certTypeList = toolsService.getEnuSelectItemList("CERTTYPE", false, true);

    }

    /**
     * ������¼
     *
     * @return
     */
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
            this.vo.setSubprdname("");
        }

        //�ͻ����Ʊ���(��ȥ 05 06 08)
        /*
        if (prdid.equals("01") || prdid.equals("02") || prdid.equals("03")
                ||prdid.equals("04") || prdid.equals("07") || prdid.equals("09")
                ||prdid.equals("10") || prdid.equals("11"))
        {
            if (StringUtils.isEmpty(this.vo.getCustomername())) {
                MessageUtil.addError("�ͻ������������");
                return null;
            }
        }else{
            this.vo.setCustomername("");
        }
        */

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
        this.vo.setOperid(this.operid);
        this.vo.setOperdate(date);
        this.vo.setRecversion((long) 0);

        Svprdsalinf svprdsalinf;
        Svprdsalplan svprdsalplan;
        Svintvinf svintvinf;
        try {
            svprdsalinf = new Svprdsalinf();
            PropertyUtils.copyProperties(svprdsalinf, this.vo);
            salesInfoService.insertSalesInfo(svprdsalinf);
        } catch (Exception e) {
            MessageUtil.addError("����ת������");
            logger.error("����ת������", e);
            return null;
        }

        MessageUtil.addInfo("�����ѳɹ�����...");

        //����VO
        this.vo.setSubprdid("");
        this.vo.setSubprdname("");
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
        this.vo.setPrdname(name);
    }

    /**
     * ��ԱID������� ��ʾ��Ա����
     */
    public void onTelleridListener() {
        String name = searchTellerName();
        if (name == null) {
            name = "�˹�Ա�����ڡ�";
        }
        this.vo.setTellername(name);
    }

    public void onSubprdidListener() {
        if (StringUtils.isEmpty(this.vo.getSubprdid())) {
            return;
        }
        String name = searchSubprdName();
        if (name == null) {
            name = "δ�ҵ���Ӧ�Ĳ�Ʒ�������ơ�";
        }
        this.vo.setSubprdname(name);
    }

    /**
     * For UI remotecommand  ����Աid����ֵ
     *
     * @param actionEvent
     */
    public void onChecktellerid(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (searchTellerName() == null) {
            requestContext.addCallbackParam("isValid", false);
        } else
            requestContext.addCallbackParam("isValid", true);
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

    //============================================

    /**
     * ���ҹ�Ա����
     *
     * @return
     */
    public String searchTellerName() {
        for (Ptoper ptoper : this.ptoperList) {
            if (this.vo.getTellerid().equals(ptoper.getOperid())) {
                return (ptoper.getOpername());
            }
        }
        return null;
    }

    /**
     * ���Ҳ�Ʒ����ID��Ӧ������
     *
     * @return
     */
    public String searchPrdName() {
        for (Ptenudetail ptenudetail : this.prdTypeList) {
            if (this.vo.getPrdid().equals(ptenudetail.getEnuitemvalue())) {
                return (ptenudetail.getEnuitemlabel());
            }
        }
        return null;
    }


    /**
     * �����Ӳ�ƷID��Ӧ������
     *
     * @return
     */
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

    //====================================================

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public SalesInfoBean getVo() {
        return vo;
    }

    public void setVo(SalesInfoBean vo) {
        this.vo = vo;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getBatchInputMode() {
        return batchInputMode;
    }

    public void setBatchInputMode(String batchInputMode) {
        this.batchInputMode = batchInputMode;
    }

    public String getRouterPath() {
        return routerPath;
    }

    public void setRouterPath(String routerPath) {
        this.routerPath = routerPath;
    }

    public String getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(String panelTitle) {
        this.panelTitle = panelTitle;
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

    public TmSalesInfoService getSalesInfoService() {
        return salesInfoService;
    }

    public void setSalesInfoService(TmSalesInfoService salesInfoService) {
        this.salesInfoService = salesInfoService;
    }
}

