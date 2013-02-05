package pams.view.telemarketing;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.*;
import pams.repository.model.telemarketing.SalesInfoBean;
import skyline.service.PlatformService;
import pams.service.telemarketing.TmSalesInfoService;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-3-31
 * Time: 下午12:15
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class SalesQueryAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String operid;
    private SalesInfoBean vo;
    private SalesInfoBean selectedRecord;
    //交易日期
    private String txnDate;
    private Date startDate;
    private Date endDate;
    private int totalcount = 0;
    private BigDecimal totalSalesamt1 = new BigDecimal(0.00);
    private long totalSalesnum1 = 0;

    //网点批量输入标志
    private String batchInputMode;
    //程序调用路径
    //perf：处理业绩表
    //plan：处理业绩计划表
    //interview：处理访谈表
    private String routerPath;

    private String panelTitle;

    private List<Svprdsalinf> salesList;
    private List<Svprdsalplan> salesplanList;
    private List<Svintvinf> interviewList;

    private List<SalesInfoBean> salesVOList;

    private List<Ptoper> ptoperList;
    private List<String> ptoperIdList;
    private List<Ptenudetail> prdTypeList;
    private List<Ptenudetail> insureTypeList;
    private List<Ptenudetail> fpTypeList;
    private List<Ptenudetail> foundTypeList;
    private List<Ptenudetail> creditcardTypeList;
    private List<Ptenudetail> ebankTypeList;
    private List<Ptenudetail> goldTypeList;


    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{tmSalesInfoService}")
    private TmSalesInfoService salesInfoService;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Svprdsalinf> getSalesList() {
        return salesList;
    }

    public void setSalesList(List<Svprdsalinf> salesList) {
        this.salesList = salesList;
    }

    public List<SalesInfoBean> getSalesVOList() {
        return salesVOList;
    }

    public void setSalesVOList(List<SalesInfoBean> salesVOList) {
        this.salesVOList = salesVOList;
    }


    public String getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(String panelTitle) {
        this.panelTitle = panelTitle;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public BigDecimal getTotalSalesamt1() {
        return totalSalesamt1;
    }

    public void setTotalSalesamt1(BigDecimal totalSalesamt1) {
        this.totalSalesamt1 = totalSalesamt1;
    }

    public long getTotalSalesnum1() {
        return totalSalesnum1;
    }

    public void setTotalSalesnum1(long totalSalesnum1) {
        this.totalSalesnum1 = totalSalesnum1;
    }

    public SalesInfoBean getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(SalesInfoBean selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    public void setSalesInfoService(TmSalesInfoService salesInfoService) {
        this.salesInfoService = salesInfoService;
    }

    //============================================================
    @PostConstruct
    public void init() {
        this.vo = new SalesInfoBean();
        this.vo.setSalesamt1(new BigDecimal(0));
        this.vo.setSalesnum1((long) 0);

        this.salesList = new ArrayList();

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        this.batchInputMode = request.getParameter("batch");
        if (StringUtils.isEmpty(this.batchInputMode)) {
            logger.error("输入模式参数错误！");
            MessageUtil.addError("输入模式参数错误！");
            return;

        }
        if ("0".equals(this.batchInputMode)) {
            this.panelTitle = "个人业绩数据查询";
        } else {
            this.panelTitle = "网点业绩数据查询";
        }

        this.routerPath = request.getParameter("path");
        if (StringUtils.isEmpty(this.routerPath)) {
            logger.error("参数错误！");
            MessageUtil.addError("参数错误！");
            return;
        }

        if ("perf".equals(this.routerPath)) {
            if ("0".equals(this.batchInputMode)) {
                this.panelTitle = "个人业绩数据查询";
            } else {
                this.panelTitle = "网点业绩数据查询";
            }
        } else if ("plan".equals(this.routerPath)) {
            if ("0".equals(this.batchInputMode)) {
                this.panelTitle = "个人业绩计划数据查询";
            } else {
                this.panelTitle = "网点业绩计划数据查询";
            }
        } else if ("interview".equals(this.routerPath)) {
            if ("0".equals(this.batchInputMode)) {
                this.panelTitle = "个人访谈（电话）数据查询";
            } else {
                this.panelTitle = "网点访谈（电话）数据查询";
            }
        }


        OperatorManager om = SystemService.getOperatorManager();
        this.operid = om.getOperatorId();
        this.vo.setTellerid(this.operid);
        this.vo.setTellername(om.getOperatorName());

        this.ptoperList = platformService.selectBranchTellers(this.operid);
        this.ptoperIdList = getOperidList();

        this.prdTypeList = platformService.selectEnuDetail("SVTPRDTYPE");
        this.foundTypeList = platformService.selectEnuDetail("SVTFUNDTYPE");
        this.insureTypeList = platformService.selectEnuDetail("SVTINSURETYPE");
        this.fpTypeList = platformService.selectEnuDetail("SVTFPTYPE");
        this.creditcardTypeList = platformService.selectEnuDetail("SVTCREDITCARDTYPE");
        this.ebankTypeList = platformService.selectEnuDetail("SVTEBANKTYPE");
        this.goldTypeList = platformService.selectEnuDetail("SVTGOLDTYPE");


        this.startDate = new Date();
        this.endDate = new Date();
        if ("plan".equals(this.routerPath)) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            cal.set(Calendar.DAY_OF_WEEK, 2);
            this.startDate = cal.getTime();
            cal.set(Calendar.DAY_OF_WEEK, 6);
            this.endDate = cal.getTime();
        }
    }

    public String onQuery() {

        try {
            //检查查询参数
            String start = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String end = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
            if ("perf".equals(this.routerPath)) {
                selectRecords4Svprdsalinf(start, end);
            } else if ("plan".equals(this.routerPath)) {
                selectRecords4Svprdsalplan(start, end);
            } else if ("interview".equals(this.routerPath)) {
                selectRecords4Svintvinf(start, end);
            }
            this.totalcount = this.salesVOList.size();
            this.totalSalesamt1 = new BigDecimal(0.00);
            this.totalSalesnum1 = 0;
            for (SalesInfoBean vo : this.salesVOList) {
                this.totalSalesamt1 = this.totalSalesamt1.add(vo.getSalesamt1());
                this.totalSalesnum1 += vo.getSalesnum1();
            }

        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。");
        }
        return null;
    }

    public String onDeleteRecord() {

        try {
            if ("perf".equals(this.routerPath)) {
                salesInfoService.deleteSalesInfoOneRecord(this.selectedRecord, this.operid);
            } else if ("plan".equals(this.routerPath)) {
                salesInfoService.deleteSalesPlanOneRecord(this.selectedRecord.getGuid());
            } else if ("interview".equals(this.routerPath)) {
                salesInfoService.deleteInterviewOneRecord(this.selectedRecord, this.operid);
            }
            onQuery();
        } catch (Exception e) {
            logger.error("数据删除错误！", e);
            MessageUtil.addError("数据删除错误！");
        }
        return null;
    }
    //=======================================

    private void selectRecords4Svprdsalinf(String start, String end) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        if (this.batchInputMode.equals("0")) {
            this.salesList = salesInfoService.selectSalesInfoRecords(this.operid, start, end);
        } else {
            this.salesList = salesInfoService.selectSalesInfoRecords(this.ptoperIdList, start, end);
        }
        assembleVOList4Svprdsalinf();
    }

    private void selectRecords4Svprdsalplan(String start, String end) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        if (this.batchInputMode.equals("0")) {
            this.salesplanList = salesInfoService.selectSalesPlanRecords(this.operid, start, end);
        } else {
            this.salesplanList = salesInfoService.selectSalesPlanRecords(this.ptoperIdList, start, end);
        }
        assembleVOList4Svprdsalplan();
    }

    private void selectRecords4Svintvinf(String start, String end) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        if (this.batchInputMode.equals("0")) {
            this.interviewList = salesInfoService.selectInterviewRecords(this.operid, start, end);
        } else {
            this.interviewList = salesInfoService.selectInterviewRecords(this.ptoperIdList, start, end);
        }
        assembleVOList4Svintvinf();
    }


    private List<String> getOperidList() {
        List<String> idList = new ArrayList<String>();
        for (Ptoper ptoper : this.ptoperList) {
            idList.add(ptoper.getOperid());
        }
        return idList;
    }

    //============================================

    /**
     * 组 实际业绩表数据
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    private void assembleVOList4Svprdsalinf() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        this.salesVOList = new ArrayList<SalesInfoBean>();
        for (Svprdsalinf record : this.salesList) {
            SalesInfoBean vo = new SalesInfoBean();
            PropertyUtils.copyProperties(vo, record);
            String prdid = vo.getPrdid();
            vo.setPrdname(searchPrdName(prdid));
            if ("01".equals(prdid)
                    || "02".equals(prdid)
                    || "03".equals(prdid)
                    || "06".equals(prdid)
                    || "08".equals(prdid)
                    || "09".equals(prdid)) {
                vo.setSubprdname(searchSubprdName(prdid, vo.getSubprdid()));
            }
            vo.setTellername(this.vo.getTellername());
            this.salesVOList.add(vo);
        }
    }

    /**
     * 组 业绩计划表数据
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    private void assembleVOList4Svprdsalplan() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        this.salesVOList = new ArrayList<SalesInfoBean>();
        for (Svprdsalplan record : this.salesplanList) {
            SalesInfoBean vo = new SalesInfoBean();
            PropertyUtils.copyProperties(vo, record);
            String prdid = vo.getPrdid();
            vo.setPrdname(searchPrdName(prdid));
            if ("01".equals(prdid)
                    || "02".equals(prdid)
                    || "03".equals(prdid)
                    || "06".equals(prdid)
                    || "08".equals(prdid)
                    || "09".equals(prdid)) {
                vo.setSubprdname(searchSubprdName(prdid, vo.getSubprdid()));
            }
            vo.setTellername(this.vo.getTellername());
            this.salesVOList.add(vo);
        }
    }

    /**
     * 组 访谈表数据
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    private void assembleVOList4Svintvinf() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        this.salesVOList = new ArrayList<SalesInfoBean>();
        for (Svintvinf record : this.interviewList) {
            SalesInfoBean vo = new SalesInfoBean();
            PropertyUtils.copyProperties(vo, record);
            String prdid = vo.getPrdid();
            vo.setPrdname(searchPrdName(prdid));
            if ("01".equals(prdid)
                    || "02".equals(prdid)
                    || "03".equals(prdid)
                    || "06".equals(prdid)
                    || "08".equals(prdid)
                    || "09".equals(prdid)) {
                vo.setSubprdname(searchSubprdName(prdid, vo.getSubprdid()));
            }
            vo.setTellername(this.vo.getTellername());
            this.salesVOList.add(vo);
        }
    }

    /**
     * 查找柜员名称
     *
     * @return
     */
    public String searchTellerName(String id) {
        for (Ptoper ptoper : this.ptoperList) {
            if (id.equals(ptoper.getOperid())) {
                return (ptoper.getOpername());
            }
        }
        return null;
    }

    /**
     * 查找产品种类ID对应的名称
     *
     * @return
     */
    public String searchPrdName(String prdid) {
        for (Ptenudetail ptenudetail : this.prdTypeList) {
            if (prdid.equals(ptenudetail.getEnuitemvalue())) {
                return (ptenudetail.getEnuitemlabel());
            }
        }
        return null;
    }


    /**
     * 查找子产品ID对应的名称
     *
     * @return
     */
    private String searchSubprdName(String prdid, String subprdid) {
        if (prdid.equals("01")) {
            for (Ptenudetail ptenudetail : this.foundTypeList) {
                if (subprdid.equals(ptenudetail.getEnuitemvalue())) {
                    return (ptenudetail.getEnuitemlabel());
                }
            }
        } else if (prdid.equals("02")) {
            for (Ptenudetail ptenudetail : this.insureTypeList) {
                if (subprdid.equals(ptenudetail.getEnuitemvalue())) {
                    return (ptenudetail.getEnuitemlabel());
                }
            }
        } else if (prdid.equals("03")) {
            for (Ptenudetail ptenudetail : this.fpTypeList) {
                if (subprdid.equals(ptenudetail.getEnuitemvalue())) {
                    return (ptenudetail.getEnuitemlabel());
                }
            }
        } else if (prdid.equals("06")) {
            for (Ptenudetail ptenudetail : this.creditcardTypeList) {
                if (subprdid.equals(ptenudetail.getEnuitemvalue())) {
                    return (ptenudetail.getEnuitemlabel());
                }
            }
        } else if (prdid.equals("08")) {
            for (Ptenudetail ptenudetail : this.ebankTypeList) {
                if (subprdid.equals(ptenudetail.getEnuitemvalue())) {
                    return (ptenudetail.getEnuitemlabel());
                }
            }
        } else if (prdid.equals("09")) {
            for (Ptenudetail ptenudetail : this.goldTypeList) {
                if (subprdid.equals(ptenudetail.getEnuitemvalue())) {
                    return (ptenudetail.getEnuitemlabel());
                }
            }
        }
        return null;
    }

}
