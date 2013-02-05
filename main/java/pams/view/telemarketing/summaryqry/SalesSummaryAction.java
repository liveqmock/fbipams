package pams.view.telemarketing.summaryqry;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.Ptenudetail;
import pams.repository.model.telemarketing.SalesInfoBean;
import skyline.service.PlatformService;
import pams.service.telemarketing.TmSalesInfoService;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 辖内机构汇总及明细查询. 包括实际业绩、计划业绩、访谈记录
 * User: zhanrui
 * Date: 11-3-31
 * Time: 下午12:15
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class SalesSummaryAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String operid;
    private String branchid;
    private String prdid;

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

    private List<SalesInfoBean> salesVOList;
    private List<SelectItem> prdTypeList;
    private List<SelectItem> branchList;

    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{tmSalesInfoService}")
    private TmSalesInfoService salesInfoService;

    private  int max_query_count;

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
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

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    public void setSalesInfoService(TmSalesInfoService salesInfoService) {
        this.salesInfoService = salesInfoService;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public String getPrdid() {
        return prdid;
    }

    public void setPrdid(String prdid) {
        this.prdid = prdid;
    }

    public List<SelectItem> getPrdTypeList() {
        return prdTypeList;
    }

    public void setPrdTypeList(List<SelectItem> prdTypeList) {
        this.prdTypeList = prdTypeList;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public SalesInfoBean getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(SalesInfoBean selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    //============================================================
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        this.routerPath = request.getParameter("path");
        if (StringUtils.isEmpty(this.routerPath)) {
            logger.error("参数错误！");
            MessageUtil.addError("参数错误！");
            return;
        }

        if ("perf".equals(this.routerPath)) {
            this.panelTitle = "辖内机构业绩记录查询";
        } else if ("plan".equals(this.routerPath)) {
            this.panelTitle = "辖内机构业绩计划记录查询";
        } else if ("interview".equals(this.routerPath)) {
            this.panelTitle = "辖内网点访谈（电话）记录查询";
        }

        OperatorManager om = SystemService.getOperatorManager();
        this.operid = om.getOperatorId();
        this.branchid = om.getOperator().getDeptid();

        initPrdTypeList();
        initBranchList();
        this.startDate = new Date();
        this.endDate = new Date();
        max_query_count = Integer.parseInt(platformService.selectEnuExpandValue("SYSTEMPARAM", "MAXQRYNUM"));

    }

    public String onQuery() {

        try {
            //检查查询参数
            String start = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String end = new SimpleDateFormat("yyyy-MM-dd").format(endDate);

            if ("perf".equals(this.routerPath)) {
                int count = salesInfoService.countSalesinfoBySearchCondition(this.branchid, start, end, this.prdid);
                if (count > max_query_count) {
                    MessageUtil.addError("查询记录数过大，已超过" + max_query_count + "条，请缩小查询范围。");
                    return null;
                }
                this.salesVOList = salesInfoService.selectSalesinfoBySearchCondition(this.branchid, start, end, this.prdid);
            } else if ("plan".equals(this.routerPath)) {
                int count = salesInfoService.countSalesplanBySearchCondition(this.branchid, start, end, this.prdid);
                if (count > max_query_count) {
                    MessageUtil.addError("查询记录数过大，已超过" + max_query_count + "条，请缩小查询范围。");
                    return null;
                }
                this.salesVOList = salesInfoService.selectSalesplanBySearchCondition(this.branchid, start, end, this.prdid);
            } else if ("interview".equals(this.routerPath)) {
                int count = salesInfoService.countInterviewBySearchCondition(this.branchid, start, end, this.prdid);
                if (count > max_query_count) {
                    MessageUtil.addError("查询记录数过大，已超过" + max_query_count + "条，请缩小查询范围。");
                    return null;
                }
                this.salesVOList = salesInfoService.selectInterviewBySearchCondition(this.branchid, start, end, this.prdid);
            }

            this.totalcount = this.salesVOList.size();
            this.totalSalesamt1 = new BigDecimal(0.00);
            this.totalSalesnum1 = 0;
            for (SalesInfoBean vo : this.salesVOList) {
                this.totalSalesamt1 = this.totalSalesamt1.add(vo.getSalesamt1());
                this.totalSalesnum1 += vo.getSalesnum1();
            }
            this.totalSalesamt1 = this.totalSalesamt1.divide(new BigDecimal(10000)).setScale(0, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。");
        }
        return null;
    }

    /*打印数据*/
    public String onPrint() {
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            //检查查询参数
            String start = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String end = new SimpleDateFormat("yyyy-MM-dd").format(endDate);

            if ("perf".equals(this.routerPath)) {
                int count = salesInfoService.countSalesinfoBySearchCondition(this.branchid, start, end, this.prdid);
                if (count > max_query_count) {
                    MessageUtil.addError("打印记录数过大，已超过" + max_query_count + "条，请缩小查询范围。");
                    return null;
                } else if (count < 1) {
                    MessageUtil.addInfo("无满足当前打印条件的记录。");
                    return null;
                }
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                ServletOutputStream os = response.getOutputStream();
                response.reset();
                String fileName = "salesinfoqry.xls";
                response.setHeader("Content-disposition", "attachment; filename=" + fileName);
                response.setContentType("application/msexcel");
                //模板
                String modelPath = PropertyManager.getProperty("REPORT_ROOTPATH") + "salestotalQrymodel.xls";
                Map beans = new HashMap();
                List<SalesInfoBean> salesVOListPrt = salesInfoService.selectSalesinfoBySearchCondition(this.branchid, start, end, this.prdid);
                beans.put("titlename","辖内机构业绩记录查询");
                beans.put("salesList",salesVOListPrt);
                XLSTransformer transformer = new XLSTransformer();
                InputStream is = new BufferedInputStream(new FileInputStream(modelPath));
                Workbook wb = transformer.transformXLS(is, beans);
                wb.write(os);
                is.close();
                os.flush();
                os.close();
            } else if ("plan".equals(this.routerPath)) {
                int count = salesInfoService.countSalesplanBySearchCondition(this.branchid, start, end, this.prdid);
                if (count > max_query_count) {
                    MessageUtil.addError("打印记录数过大，已超过" + max_query_count + "条，请缩小查询范围。");
                    return null;
                } else if (count < 1) {
                    MessageUtil.addInfo("无满足当前打印条件的记录。");
                    return null;
                }
                List<SalesInfoBean> salesPlanVOListPrt = salesInfoService.selectSalesplanBySearchCondition(this.branchid, start, end, this.prdid);
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                ServletOutputStream os = response.getOutputStream();
                response.reset();
                String fileName = "salesplaninfoqry.xls";
                response.setHeader("Content-disposition", "attachment; filename=" + fileName);
                response.setContentType("application/msexcel");
                //模板
                String modelPath = PropertyManager.getProperty("REPORT_ROOTPATH") + "salestotalQrymodel.xls";
                Map beans = new HashMap();
                beans.put("titlename","辖内机构业绩计划记录查询");
                beans.put("salesList",salesPlanVOListPrt);
                XLSTransformer transformer = new XLSTransformer();
                InputStream is = new BufferedInputStream(new FileInputStream(modelPath));
                Workbook wb = transformer.transformXLS(is, beans);
                wb.write(os);
                is.close();
                os.flush();
                os.close();
            } else if ("interview".equals(this.routerPath)) {
                int count = salesInfoService.countInterviewBySearchCondition(this.branchid, start, end, this.prdid);
                if (count > max_query_count) {
                    MessageUtil.addError("打印记录数过大，已超过" + max_query_count + "条，请缩小查询范围。");
                    return null;
                } else if (count < 1) {
                    MessageUtil.addInfo("无满足当前打印条件的记录。");
                    return null;
                }
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                ServletOutputStream os = response.getOutputStream();
                response.reset();
                String fileName = "interviewinfoqry.xls";
                response.setHeader("Content-disposition", "attachment; filename=" + fileName);
                response.setContentType("application/msexcel");
                //模板
                String modelPath = PropertyManager.getProperty("REPORT_ROOTPATH") + "interviewtotalQrymodel.xls";
                Map beans = new HashMap();
                List<SalesInfoBean> interviewVOListPrt = salesInfoService.selectInterviewBySearchCondition(this.branchid, start, end, this.prdid);
                beans.put("interviewList",interviewVOListPrt);
                XLSTransformer transformer = new XLSTransformer();
                InputStream is = new BufferedInputStream(new FileInputStream(modelPath));
                Workbook wb = transformer.transformXLS(is, beans);
                wb.write(os);
                is.close();
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            logger.error("打印数据时出现错误。", e);
            MessageUtil.addWarn("打印数据时出现错误。");
        }
        return null;
    }

    //=======================================

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

    private void initBranchList() {
        List<String> records = platformService.selectBranchLevelString(this.branchid);
        this.branchList = new ArrayList<SelectItem>();
        for (String record : records) {
            String[] recordArray = record.split("\\|");
            SelectItem item = new SelectItem(recordArray[0], recordArray[1]);
            this.branchList.add(item);
        }
    }

}
