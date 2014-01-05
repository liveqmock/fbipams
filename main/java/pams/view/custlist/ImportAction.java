package pams.view.custlist;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.utils.MessageUtil;
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
        options.put("1、临界值客户数据表(三个月度表+三个季度表)", "1");
        options.put("2、流失降级客户数据表", "2");
        options.put("3、持有理财卡但AUM不达标客户数据表", "3");
        options.put("4、优质重点产品客户数据表", "4");
        options.put("5、CTS客户数据表", "5");
        options.put("6、资产流失最大VIP客户数据表", "6");
        selectedOptions.add("1");
        selectedOptions.add("2");
        selectedOptions.add("3");
        selectedOptions.add("4");
        selectedOptions.add("5");
        selectedOptions.add("6");
    }
    @PostConstruct
    public void postConstruct() {
        DateTime dt = new DateTime();
        this.startdate = dt.minusMonths(1).dayOfMonth().withMaximumValue().toString("yyyyMMdd");

    }
    public String onETLRptData() {
        List<String> msgList = new ArrayList<>();
        try {
            if (selectedOptions.isEmpty()) {
                MessageUtil.addInfo("请选择导入的报表...");
                return null;
            }

            for (String option : selectedOptions) {
                switch (Integer.parseInt(option)) {
                    case 1:
                        String rptType = "AUM1_4_5";
                        String filename = "CUST_INFO_" + rptType + "_371_" + startdate + ".dat";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        rptType = "AUM1_15_20";
                        filename = "CUST_INFO_" + rptType + "_371_" + startdate + ".dat";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        rptType = "AUM1_40_50";
                        filename = "CUST_INFO_" + rptType + "_371_" + startdate + ".dat";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);

                        rptType = "AUM2_4_5";
                        filename = "CUST_INFO_" + rptType + "_371_" + startdate + ".dat";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        rptType = "AUM2_15_20";
                        filename = "CUST_INFO_" + rptType + "_371_" + startdate + ".dat";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        rptType = "AUM2_40_50";
                        filename = "CUST_INFO_" + rptType + "_371_" + startdate + ".dat";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);

                        rptType = "AUM5_4_5";
                        filename = "CUST_INFO_" + rptType + "_371_" + startdate + ".dat";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        rptType = "AUM5_15_20";
                        filename = "CUST_INFO_" + rptType + "_371_" + startdate + ".dat";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        rptType = "AUM5_40_50";
                        filename = "CUST_INFO_" + rptType + "_371_" + startdate + ".dat";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);

/*
                        String filename = "CUST_INFO_AUM1_4_5_371_" + startdate + ".dat";
                        String rptType = "0101";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        filename = "CUST_INFO_AUM1_15_20_371_" + startdate + ".dat";
                        rptType = "0102";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        filename = "CUST_INFO_AUM1_40_50_371_" + startdate + ".dat";
                        rptType = "0103";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);

                        filename = "CUST_INFO_AUM2_4_5_371_" + startdate + ".dat";
                        rptType = "0104";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        filename = "CUST_INFO_AUM2_15_20_371_" + startdate + ".dat";
                        rptType = "0105";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        filename = "CUST_INFO_AUM2_40_50_371_" + startdate + ".dat";
                        rptType = "0106";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);

*/
                        break;
                    case 2:
                        filename = "CUST_INFO_AUM3_371_" + startdate + ".dat";
                        rptType = "0201";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        filename = "CUST_INFO_AUM4_371_" + startdate + ".dat";
                        rptType = "0202";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        break;
                    case 3:
                        filename = "CUST_INFO_CARD_371_" + startdate + ".dat";
                        rptType = "0301";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        break;
                    case 4:
                        filename = "CUST_INFO_LOAN_371_" + startdate + ".dat";
                        rptType = "0401";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        filename = "CUST_INFO_CREDIT_CARD_371_" + startdate + ".dat";
                        rptType = "0402";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        break;
                    case 5:
                        filename = "CUST_INFO_CTS_371_" + startdate + ".dat";
                        rptType = "0501";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        break;
                    case 6:
                        filename = "CUST_INFO_VIP_371_" + startdate + ".dat";
                        rptType = "0601";
                        importService.importDataFromTxt(startdate, filename, rptType, msgList);
                        break;
                    default:
                }
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
