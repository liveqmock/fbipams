package pams.view.dataetl;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.utils.MessageUtil;
import skyline.service.PlatformService;
import skyline.service.ToolsService;
import pams.service.dataetl.DataETLService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: zhanrui
 * Date: 12-12-17
 * Time: 下午3:40
 */
@ManagedBean
@ViewScoped
public class DataETLAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(DataETLAction.class);
    private static final long serialVersionUID = 1366227629931959859L;

    private String startdate;
    private String startdateRpt11;
    private String enddate;
    private String enddateRpt11;
    private String largeStartdate;
    private String currYearStartdate;

    private List<String> selectedOptions = new ArrayList<>();
    private Map<String, String> options;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{dataETLService}")
    private DataETLService dataETLService;

    @PostConstruct
    public void postConstruct() {
        DateTime dt = new DateTime();
        this.startdate = dt.minusMonths(1).dayOfMonth().withMaximumValue().toString("yyyyMMdd");
        this.startdateRpt11 = dt.minusMonths(1).dayOfMonth().withMaximumValue().toString("yyyy-MM-dd");
        this.enddate = dt.minusDays(1).toString("yyyyMMdd");
        this.enddateRpt11 = dt.minusDays(1).toString("yyyy-MM-dd");
        this.largeStartdate = dt.minusDays(7).toString("yyyy-MM-dd");
        this.currYearStartdate = dt.monthOfYear().withMinimumValue().dayOfMonth().withMinimumValue().toString("yyyy-MM-dd");
    }

    public String onProcessCustBase() {
        try {
            dataETLService.mergeCustBaseRecords(startdate);
            MessageUtil.addInfo("客户基本信息处理完成...");
        } catch (Exception ex) {
            logger.error("数据处理错误。", ex);
            MessageUtil.addError("数据处理错误。" + ex.getMessage());
        }
        return null;
    }

    public String onProcessLargeFlowData() {
        try {
            dataETLService.importLargeFlowRecords(largeStartdate);
            MessageUtil.addInfo("数据处理完成...");
        } catch (Exception ex) {
            logger.error("数据处理错误。", ex);
            MessageUtil.addError("数据处理错误。" + ex.getMessage());
        }
        return null;
    }

    public String onProcessRptA07V1Data() {
        try {
            dataETLService.importData_RptA07V1(largeStartdate);
            MessageUtil.addInfo("数据处理完成...");
        } catch (Exception ex) {
            logger.error("数据处理错误。", ex);
            MessageUtil.addError("数据处理错误。" + ex.getMessage());
        }
        return null;
    }
    public String onProcessRptA08V1Data() {
        try {
            dataETLService.importData_RptA08V1(currYearStartdate);
            MessageUtil.addInfo("数据处理完成...");
        } catch (Exception ex) {
            logger.error("数据处理错误。", ex);
            MessageUtil.addError("数据处理错误。" + ex.getMessage());
        }
        return null;
    }
    public String onProcessRptA11V1Data() {
        try {
            dataETLService.importData_RptA11V1(startdateRpt11, enddateRpt11);
            MessageUtil.addInfo("数据处理完成...");
        } catch (Exception ex) {
            logger.error("数据处理错误。", ex);
            MessageUtil.addError("数据处理错误。" + ex.getMessage());
        }
        return null;
    }
    public String onDeleteRptA11V1Data() {
        try {
            dataETLService.deleteData_RptA11V1(startdateRpt11, enddateRpt11);
            MessageUtil.addInfo("数据处理完成...");
        } catch (Exception ex) {
            logger.error("数据处理错误。", ex);
            MessageUtil.addError("数据处理错误。" + ex.getMessage());
        }
        return null;
    }

    private String checkAndTransInputDate() {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(startdate);
        } catch (ParseException e) {
            MessageUtil.addError("日期输入错误。");
            return null;
        }
        return (new SimpleDateFormat("yyyyMMdd").format(date));
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public List<String> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
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

    public DataETLService getDataETLService() {
        return dataETLService;
    }

    public void setDataETLService(DataETLService dataETLService) {
        this.dataETLService = dataETLService;
    }

    public String getLargeStartdate() {
        return largeStartdate;
    }

    public void setLargeStartdate(String largeStartdate) {
        this.largeStartdate = largeStartdate;
    }

    public String getCurrYearStartdate() {
        return currYearStartdate;
    }

    public void setCurrYearStartdate(String currYearStartdate) {
        this.currYearStartdate = currYearStartdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getStartdateRpt11() {
        return startdateRpt11;
    }

    public void setStartdateRpt11(String startdateRpt11) {
        this.startdateRpt11 = startdateRpt11;
    }

    public String getEnddateRpt11() {
        return enddateRpt11;
    }

    public void setEnddateRpt11(String enddateRpt11) {
        this.enddateRpt11 = enddateRpt11;
    }
}
