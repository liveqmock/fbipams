package pams.view.telemarketing.summaryqry;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.Svmeetingevent;
import skyline.service.PlatformService;
import pams.service.telemarketing.MeetingInfoService;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 辖内机构夕会明细查询.
 * User: zhanrui
 * Date: 11-3-31
 * Time: 下午12:15
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class
        MeetingSummaryAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String operid;
    private String branchid;
    private String prdid;

    private Svmeetingevent selectedRecord;
    //交易日期
    private String txnDate;
    private Date startDate;
    private Date endDate;
    private int totalcount = 0;

    private String panelTitle;

    private List<Svmeetingevent> successEventsList;
    private List<Svmeetingevent> refuseEventsList1;
    private List<Svmeetingevent> refuseEventsList2;
    private List<Svmeetingevent> refuseEventsList3;

    private List<SelectItem> branchList;

    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{meetingInfoService}")
    private MeetingInfoService meetingInfoService;

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

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
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


    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public List<Svmeetingevent> getSuccessEventsList() {
        return successEventsList;
    }

    public void setSuccessEventsList(List<Svmeetingevent> successEventsList) {
        this.successEventsList = successEventsList;
    }

    public List<Svmeetingevent> getRefuseEventsList1() {
        return refuseEventsList1;
    }

    public void setRefuseEventsList1(List<Svmeetingevent> refuseEventsList1) {
        this.refuseEventsList1 = refuseEventsList1;
    }

    public List<Svmeetingevent> getRefuseEventsList2() {
        return refuseEventsList2;
    }

    public void setRefuseEventsList2(List<Svmeetingevent> refuseEventsList2) {
        this.refuseEventsList2 = refuseEventsList2;
    }

    public List<Svmeetingevent> getRefuseEventsList3() {
        return refuseEventsList3;
    }

    public void setRefuseEventsList3(List<Svmeetingevent> refuseEventsList3) {
        this.refuseEventsList3 = refuseEventsList3;
    }

    public MeetingInfoService getMeetingInfoService() {
        return meetingInfoService;
    }

    public void setMeetingInfoService(MeetingInfoService meetingInfoService) {
        this.meetingInfoService = meetingInfoService;
    }

    //============================================================
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        OperatorManager om = SystemService.getOperatorManager();
        this.operid = om.getOperatorId();
        this.branchid = om.getOperator().getDeptid();

        initBranchList();
        this.startDate = new Date();
        this.endDate = new Date();

        this.setPanelTitle("辖内机构夕会记录查询");

        max_query_count = Integer.parseInt(platformService.selectEnuExpandValue("SYSTEMPARAM", "MAXQRYNUM"));

    }

    public String onQuery() {

        try {
            //检查查询参数
            String start = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String end = new SimpleDateFormat("yyyy-MM-dd").format(endDate);


            int count = meetingInfoService.countSuccessEvents(this.branchid, start, end);
            if (count > max_query_count) {
                MessageUtil.addError("查询记录数过大，已超过" + max_query_count + "条，请缩小查询范围。");
                return null;
            }

            this.successEventsList = meetingInfoService.selectSuccessEvents(this.branchid, start, end);
            this.refuseEventsList1 = meetingInfoService.selectRefuseEvents1(this.branchid, start, end);
            this.refuseEventsList2 = meetingInfoService.selectRefuseEvents2(this.branchid, start, end);
            this.refuseEventsList3 = meetingInfoService.selectRefuseEvents3(this.branchid, start, end);

        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。");
        }
        return null;
    }

    public String onPrint() {
        try {
            //检查查询参数
            String start = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String end = new SimpleDateFormat("yyyy-MM-dd").format(endDate);

            int count = meetingInfoService.countSuccessEvents(this.branchid, start, end);
            if (count > max_query_count) {
                MessageUtil.addError("打印记录数过大，已超过" + max_query_count + "条，请缩小打印范围。");
                return null;
            } else if (count < 1) {
                MessageUtil.addInfo("无满足当前打印条件的记录。");
                return null;
            }

            List<Svmeetingevent> successList = meetingInfoService.selectSuccessEvents(this.branchid, start, end);
            List<Svmeetingevent> refuseList1 = meetingInfoService.selectRefuseEvents1(this.branchid, start, end);
            List<Svmeetingevent> refuseList2 = meetingInfoService.selectRefuseEvents2(this.branchid, start, end);
            List<Svmeetingevent> refuseList3 = meetingInfoService.selectRefuseEvents3(this.branchid, start, end);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream os = response.getOutputStream();
            response.reset();
            String fileName = "meetinginfo.xls";
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setContentType("application/msexcel");
            //模板
            String modelPath = PropertyManager.getProperty("REPORT_ROOTPATH") + "meetingtotalQrymodel.xls";
            Map beans = new HashMap();
            beans.put("successList",successList);
            beans.put("refuseList1",refuseList1);
            beans.put("refuseList2",refuseList2);
            beans.put("refuseList3",refuseList3);
            XLSTransformer transformer = new XLSTransformer();
            InputStream is = new BufferedInputStream(new FileInputStream(modelPath));
            Workbook wb = transformer.transformXLS(is, beans);
            wb.write(os);
            is.close();
            os.flush();
            os.close();

        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。");
        }
        return null;
    }

    //=======================================

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
