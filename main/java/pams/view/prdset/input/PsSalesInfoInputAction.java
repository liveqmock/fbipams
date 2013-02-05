package pams.view.prdset.input;

import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.Ptoper;
import pams.repository.model.Svpsprdsetmain;
import pams.repository.model.Svpssaleinfo;
import pams.repository.model.prdset.PsSalesInfoBean;
import skyline.service.PlatformService;
import skyline.service.ToolsService;
import pams.service.prdset.PsSalesInfoService;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ��Ʒ�ײ�����.
 * User: zhanrui
 * Date: 11-3-18
 * Time: ����11:20
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@ViewScoped
public class PsSalesInfoInputAction implements Serializable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String operid;
    private String branchid;
    private PsSalesInfoBean vo;
    //�������������־
    private String batchInputMode;
    private String tellerIDFiledReadonly;

    private String panelTitle;
    private String txndatemsg;

    private List<Ptoper> ptoperList;
    private List<SelectItem> prdsetList;
    private List<SelectItem> certTypeList;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{psSalesInfoService}")
    private PsSalesInfoService psSalesInfoService;


    public String getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(String panelTitle) {
        this.panelTitle = panelTitle;
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    public void setPsSalesInfoService(PsSalesInfoService psSalesInfoService) {
        this.psSalesInfoService = psSalesInfoService;
    }

    public PsSalesInfoBean getVo() {
        return vo;
    }

    public void setVo(PsSalesInfoBean vo) {
        this.vo = vo;
    }

    public String getTellerIDFiledReadonly() {
        return tellerIDFiledReadonly;
    }

    public void setTellerIDFiledReadonly(String tellerIDFiledReadonly) {
        this.tellerIDFiledReadonly = tellerIDFiledReadonly;
    }

    public List<SelectItem> getCertTypeList() {
        return certTypeList;
    }

    public void setCertTypeList(List<SelectItem> certTypeList) {
        this.certTypeList = certTypeList;
    }

    public List<SelectItem> getPrdsetList() {
        return prdsetList;
    }

    public void setPrdsetList(List<SelectItem> prdsetList) {
        this.prdsetList = prdsetList;
    }

    public String getTxndatemsg() {
        return txndatemsg;
    }

    public void setTxndatemsg(String txndatemsg) {
        this.txndatemsg = txndatemsg;
    }


    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public String getBatchInputMode() {
        return batchInputMode;
    }

    public void setBatchInputMode(String batchInputMode) {
        this.batchInputMode = batchInputMode;
    }
//====================================================

    public PsSalesInfoInputAction() {
    }

    @PostConstruct
    public void init() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        this.batchInputMode = request.getParameter("batch");
        if (batchInputMode == null) {
            this.batchInputMode = "0";
        }
        if ("0".equals(this.batchInputMode)) {
            this.panelTitle = "���˲�Ʒ�ײ�����¼��";
        } else {
            this.panelTitle = "�����Ʒ�ײ�����¼��";
        }

        OperatorManager om = SystemService.getOperatorManager();
        this.operid = om.getOperatorId();
        this.branchid = om.getOperator().getDeptid();

        //VO INIT
        this.vo = new PsSalesInfoBean();
        this.vo.setTellerid(this.operid);
        this.vo.setTellername(om.getOperatorName());
        this.vo.setTxndate(new SimpleDateFormat("yyyyMMdd").format(new Date()));

        //��������Ա�б�
        this.ptoperList = platformService.selectBranchTellers(this.operid);

        this.prdsetList = initPrdsetSelectItemList(new Date());
        this.certTypeList = toolsService.getEnuSelectItemList("CERTTYPE",false,true);
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

        if (!checkDuplicateRecord()) {
            return null;
        }
        if (!checkPrdsetMutex()) {
            return null;
        }

        String prdsetid = this.vo.getPrdsetid();

        Date date = new Date();
        this.vo.setTxntime(new SimpleDateFormat("HH:mm:ss").format(date));
        this.vo.setOperid(this.operid);
        this.vo.setOperdate(date);
        this.vo.setRecversion((long) 0);
        this.vo.setBranchid(this.branchid);
        this.vo.setCommitflag("0");
        this.vo.setChecktimes((long) 0);
        this.vo.setCheckflag("0");
        this.vo.setArchiveflag("0");
        this.vo.setRecsts("N");

        String txndate = this.vo.getTxndate();
        //���ڸ�ʽ
        try {
            this.vo.setTxndate(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(txndate)));
        } catch (ParseException e) {
            MessageUtil.addError("���ڸ�ʽת������");
            return null;
        }

        try {
            psSalesInfoService.insertPsSalesInfo(this.vo);
        } catch (Exception e) {
            MessageUtil.addError("��¼�������");
            logger.error("����ת������", e);
            return null;
        }

        MessageUtil.addInfo("�����ѳɹ�����...");

        //����VO
        this.vo.setTxndate(txndate);
        return null;
    }


    public void onTxndateListener() {
        if (StringUtils.isEmpty(this.vo.getTxndate())) {
            //MessageUtil.addError("���ڲ���Ϊ�ա�");
            return;
        }
        if (this.vo.getTxndate().length() != 8) {
            //MessageUtil.addError("������8λ���ڡ�");
            return;
        }
        try {
            Date date = new SimpleDateFormat("yyyyMMdd").parse(this.vo.getTxndate());
            this.prdsetList = initPrdsetSelectItemList(date);
            if (this.prdsetList.size() == 0) {
                this.txndatemsg = "���������޿��õĲ�Ʒ�ײ͡�";
            }
        } catch (ParseException e) {
            MessageUtil.addError("���ڸ�ʽ����");
        }
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

    /**
     * For UI remotecommand  ����Աid����ֵ
     *
     * @param actionEvent
     */
    public void onCheckTellerID(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (searchTellerName() == null) {
            requestContext.addCallbackParam("isValid", false);
        } else
            requestContext.addCallbackParam("isValid", true);
    }

/*
    public void onCheckTxndate(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        try {
            Date date = new SimpleDateFormat("yyyyMMdd").parse(this.vo.getTxndate());
            this.prdsetList = initPrdsetSelectItemList(date);
            if (this.prdsetList.size()==0) {
                this.txndatemsg = "���������޿��õĲ�Ʒ�ײ͡�";
                MessageUtil.addError("aaa");
                requestContext.addCallbackParam("isValid", false);
            }
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
            requestContext.addCallbackParam("isValid", true);
    }
*/

    //============================================

    /**
     * ���ҹ�Ա����
     *
     * @return
     */
    private String searchTellerName() {
        for (Ptoper ptoper : this.ptoperList) {
            if (this.vo.getTellerid().equals(ptoper.getOperid())) {
                return (ptoper.getOpername());
            }
        }
        return null;
    }


    /**
     * ����ͬһ��Աͬһ��Ʒ�ײ͵����
     *
     * @return
     */
    private boolean checkDuplicateRecord() {
        List<Svpssaleinfo> records = psSalesInfoService.searchDuplicateRecord(this.vo.getCerttype(), this.vo.getCertno(), this.vo.getPrdsetid());
        if (records.size() > 0) {
            MessageUtil.addError("�Ѵ��ڴ˿ͻ�" + this.vo.getPrdsetid() + "��Ʒ�ײ͵ļ�¼��");
            MessageUtil.addError("���������" + records.get(0).getBranchid());
            MessageUtil.addError("�����Ա��" + records.get(0).getTellerid());
            return false;
        }
        return true;
    }

    private boolean checkPrdsetMutex() {
        Svpsprdsetmain prdsetMain = psSalesInfoService.selectPrdsetMainInfo(this.vo.getPrdsetid());
        if (prdsetMain.getMutexflag().equals("1")) {//ȫ������
            return true;
        } else if (prdsetMain.getMutexflag().equals("0")) {//ȫ������
            List<Svpssaleinfo> records = psSalesInfoService.searchDuplicateRecord(this.vo.getCerttype(), this.vo.getCertno());
            if (records.size() > 0) {
                MessageUtil.addError("�Ѵ�����˲�Ʒ�ײͲ����ݵ�ǩԼ��¼��");
                return false;
            } else {
                return true;
            }
        } else {
            //TODO
            return true;
        }
    }


    /**
     * �������ڻ�ȡ���õ��ײ��嵥
     * ��ʼʱ�ɸ��ݵ�ǰϵͳ���ڻ�ȡ�嵥
     * ���û�������ǩԼ����ʱ������ǩԼ���ڻ�ȡ�嵥
     *
     * @param date
     * @return
     */
    private List<SelectItem> initPrdsetSelectItemList(Date date) {
        List<Svpsprdsetmain> records = psSalesInfoService.selectprdsetList(date);
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (Svpsprdsetmain record : records) {
            SelectItem item = new SelectItem(record.getPrdsetid(), record.getPrdsetid() + " " + record.getPrdsetname());
            items.add(item);
        }
        return items;
    }

}

