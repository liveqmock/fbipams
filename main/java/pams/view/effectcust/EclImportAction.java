package pams.view.effectcust;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.utils.MessageUtil;
import pams.service.effectcust.EclImportService;
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
 * ��Ч�ͻ���չ����Ŀ���嵥
 * User: zhanrui
 * Date: 13-4-17
 * Time: ����3:40
 */
@ManagedBean
@ViewScoped
public class EclImportAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(EclImportAction.class);
    private static final long serialVersionUID = 1366227629931959859L;

    private String startdate;

    private List<String> selectedOptions = new ArrayList<>();
    private Map<String, String> options;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{eclImportService}")
    private EclImportService eclImportService;

    public EclImportAction(){
        options = new LinkedHashMap<String, String>();
        options.put("1���ٽ�ֵ�ͻ����ݱ�(�����¶ȱ�+�������ȱ�)", "1");
        options.put("2����ʧ�����ͻ����ݱ�", "2");
        options.put("3��������ƿ���AUM�����ͻ����ݱ�", "3");
        options.put("4�������ص��Ʒ�ͻ����ݱ�", "4");
        options.put("5��CTS�ͻ����ݱ�", "5");
        options.put("6���ʲ���ʧ���VIP�ͻ����ݱ�", "6");
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
        try {
            if (selectedOptions.isEmpty()) {
                MessageUtil.addInfo("��ѡ����ı���...");
                return null;
            }

            List<String> msgList = new ArrayList<>();
            for (String option : selectedOptions) {
                switch (Integer.parseInt(option)) {
                    case 1:
                        String filename = "CUST_INFO_AUM1_4_5_371_" + startdate + ".dat";
                        String rptType = "0101";
                        eclImportService.importDataFromTxt(startdate, filename, rptType, msgList);
                        filename = "CUST_INFO_AUM1_15_20_371_" + startdate + ".dat";
                        rptType = "0102";
                        eclImportService.importDataFromTxt(startdate, filename, rptType, msgList);
                        filename = "CUST_INFO_AUM1_40_50_371_" + startdate + ".dat";
                        rptType = "0103";
                        eclImportService.importDataFromTxt(startdate, filename, rptType, msgList);

                        filename = "CUST_INFO_AUM2_4_5_371_" + startdate + ".dat";
                        rptType = "0104";
                        eclImportService.importDataFromTxt(startdate, filename, rptType, msgList);
                        filename = "CUST_INFO_AUM2_15_20_371_" + startdate + ".dat";
                        rptType = "0105";
                        eclImportService.importDataFromTxt(startdate, filename, rptType, msgList);
                        filename = "CUST_INFO_AUM2_40_50_371_" + startdate + ".dat";
                        rptType = "0106";
                        eclImportService.importDataFromTxt(startdate, filename, rptType, msgList);

                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    default:
                }
            }
            MessageUtil.addInfo("���ݴ��������£�");
            for (String s : msgList) {
                MessageUtil.addInfo(s);
            }
        } catch (Exception ex) {
            logger.error("���ݴ������", ex);
            MessageUtil.addError("���ݴ������" + ex.getMessage());
        }
        return null;
    }
    private String checkAndTransInputDate() {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(startdate);
        } catch (ParseException e) {
            MessageUtil.addError("�����������");
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

    public EclImportService getImportService() {
        return eclImportService;
    }

    public void setImportService(EclImportService importService) {
        this.eclImportService = importService;
    }
}
