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
 * 产品套餐输入.
 * User: zhanrui
 * Date: 11-3-18
 * Time: 上午11:20
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@ViewScoped
public class PsSalesInfoInputAction implements Serializable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String operid;
    private String branchid;
    private PsSalesInfoBean vo;
    //网点批量输入标志
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
            this.panelTitle = "个人产品套餐数据录入";
        } else {
            this.panelTitle = "网点产品套餐批量录入";
        }

        OperatorManager om = SystemService.getOperatorManager();
        this.operid = om.getOperatorId();
        this.branchid = om.getOperator().getDeptid();

        //VO INIT
        this.vo = new PsSalesInfoBean();
        this.vo.setTellerid(this.operid);
        this.vo.setTellername(om.getOperatorName());
        this.vo.setTxndate(new SimpleDateFormat("yyyyMMdd").format(new Date()));

        //本机构柜员列表
        this.ptoperList = platformService.selectBranchTellers(this.operid);

        this.prdsetList = initPrdsetSelectItemList(new Date());
        this.certTypeList = toolsService.getEnuSelectItemList("CERTTYPE",false,true);
    }

    /**
     * 新增记录
     *
     * @return
     */
    public String onSaveBtnClick() {
        //输入内容检查
        if (searchTellerName() == null) {
            MessageUtil.addError("柜员输入错误");
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
        //日期格式
        try {
            this.vo.setTxndate(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(txndate)));
        } catch (ParseException e) {
            MessageUtil.addError("日期格式转换错误。");
            return null;
        }

        try {
            psSalesInfoService.insertPsSalesInfo(this.vo);
        } catch (Exception e) {
            MessageUtil.addError("记录保存错误！");
            logger.error("数据转换错误！", e);
            return null;
        }

        MessageUtil.addInfo("数据已成功保存...");

        //重置VO
        this.vo.setTxndate(txndate);
        return null;
    }


    public void onTxndateListener() {
        if (StringUtils.isEmpty(this.vo.getTxndate())) {
            //MessageUtil.addError("日期不能为空。");
            return;
        }
        if (this.vo.getTxndate().length() != 8) {
            //MessageUtil.addError("请输入8位日期。");
            return;
        }
        try {
            Date date = new SimpleDateFormat("yyyyMMdd").parse(this.vo.getTxndate());
            this.prdsetList = initPrdsetSelectItemList(date);
            if (this.prdsetList.size() == 0) {
                this.txndatemsg = "此日期内无可用的产品套餐。";
            }
        } catch (ParseException e) {
            MessageUtil.addError("日期格式错误。");
        }
    }


    /**
     * 柜员ID输入完后 提示柜员名称
     */
    public void onTelleridListener() {
        String name = searchTellerName();
        if (name == null) {
            name = "此柜员不存在。";
        }
        this.vo.setTellername(name);
    }

    /**
     * For UI remotecommand  检查柜员id输入值
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
                this.txndatemsg = "此日期内无可用的产品套餐。";
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
     * 查找柜员名称
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
     * 查找同一柜员同一产品套餐的情况
     *
     * @return
     */
    private boolean checkDuplicateRecord() {
        List<Svpssaleinfo> records = psSalesInfoService.searchDuplicateRecord(this.vo.getCerttype(), this.vo.getCertno(), this.vo.getPrdsetid());
        if (records.size() > 0) {
            MessageUtil.addError("已存在此客户" + this.vo.getPrdsetid() + "产品套餐的记录。");
            MessageUtil.addError("办理机构：" + records.get(0).getBranchid());
            MessageUtil.addError("办理柜员：" + records.get(0).getTellerid());
            return false;
        }
        return true;
    }

    private boolean checkPrdsetMutex() {
        Svpsprdsetmain prdsetMain = psSalesInfoService.selectPrdsetMainInfo(this.vo.getPrdsetid());
        if (prdsetMain.getMutexflag().equals("1")) {//全部相容
            return true;
        } else if (prdsetMain.getMutexflag().equals("0")) {//全部互斥
            List<Svpssaleinfo> records = psSalesInfoService.searchDuplicateRecord(this.vo.getCerttype(), this.vo.getCertno());
            if (records.size() > 0) {
                MessageUtil.addError("已存在与此产品套餐不相容的签约记录。");
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
     * 根据日期获取可用的套餐清单
     * 初始时可根据当前系统日期获取清单
     * 当用户输入了签约日期时，根据签约日期获取清单
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

