package pams.view.custlist;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.utils.MessageUtil;
import pams.repository.model.Ptenudetail;
import pams.service.custlist.importdata.ClsDataImportService;
import skyline.service.PlatformService;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 12-12-17
 * Time: 下午3:40
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ImportAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ImportAction.class);
    private static final long serialVersionUID = 1366227629931959859L;

    private String startdate;

    private List<String> selectedOptions = new ArrayList<>();
    private Map<String, String> options;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{clsDataImportService}")
    private ClsDataImportService importService;

    public  ImportAction(){
        options = new LinkedHashMap<String, String>();
    }
    @PostConstruct
    public void postConstruct() {
        DateTime dt = new DateTime();
        this.startdate = dt.minusMonths(1).dayOfMonth().withMaximumValue().toString("yyyyMMdd");

        List<Ptenudetail> ptenudetails =  platformService.selectEnuDetail("CUST_LIST_RPT_TYPE");
        int count = 0;
        for (Ptenudetail ptenudetail : ptenudetails) {
            count++;
            options.put("" + count + ptenudetail.getEnuitemlabel(), ptenudetail.getEnuitemvalue());
            selectedOptions.add(ptenudetail.getEnuitemvalue());
        }
    }

    public String disSelectAllOption(){
        selectedOptions = new ArrayList<>();
        return null;
    }
    public String selectAllOption(){
        for (Map.Entry<String, String> entry : options.entrySet()) {
               String option = (String)entry.getValue();
            selectedOptions.add(option);
        }
        return null;
    }
    public String onETLRptData() {
        if (selectedOptions.isEmpty()) {
            MessageUtil.addInfo("请选择导入的报表...");
            return null;
        }
        List<String> msgList = new ArrayList<>();

        try {
            for (String rptType : selectedOptions) {
                String filename = "CUST_INFO_" + rptType + "_371_" + startdate + ".dat";
                importService.importDataFromTxt(startdate, filename, rptType, msgList);
            }
            MessageUtil.addInfo("数据处理结果如下：");
            for (String s : msgList) {
                MessageUtil.addInfo(s);
            }
        } catch (Exception ex) {
            logger.error("数据处理错误。", ex);
            for (String s : msgList) {
                MessageUtil.addError(s);
            }
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

    public ClsDataImportService getImportService() {
        return importService;
    }

    public void setImportService(ClsDataImportService importService) {
        this.importService = importService;
    }
}
