package pams.view.effectcust;

import org.apache.commons.lang.StringUtils;
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
        options.put("7���ʲ���ʧ���VIP�ͻ����ݱ�", "7");
        options.put("8���ʲ���ʧ���VIP�ͻ����ݱ�", "8");
        options.put("9���ʲ���ʧ���VIP�ͻ����ݱ�", "9");
        selectedOptions.add("1");
        selectedOptions.add("2");
        selectedOptions.add("3");
        selectedOptions.add("4");
        selectedOptions.add("5");
        selectedOptions.add("6");
        selectedOptions.add("7");
        selectedOptions.add("8");
        selectedOptions.add("9");
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
                MessageUtil.addInfo("��ѡ����ı���...");
                return null;
            }

            for (String option : selectedOptions) {
                String fileSn = StringUtils.leftPad("" + (Integer.parseInt(option) - 1), 2, '0');
                String rptSn = StringUtils.leftPad(option, 2, '0');
                String filename = "LIST_" + fileSn + "_371000000.dat";
                String rptType = "10" + rptSn;
                eclImportService.importDataFromTxt(startdate, filename, rptType, msgList);
            }

            MessageUtil.addInfo("���ݴ��������£�");
            for (String s : msgList) {
                MessageUtil.addInfo(s);
            }
        } catch (Exception ex) {
            logger.error("���ݴ������", ex);
            for (String s : msgList) {
                MessageUtil.addError(s);
            }
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

    public EclImportService getEclImportService() {
        return eclImportService;
    }

    public void setEclImportService(EclImportService eclImportService) {
        this.eclImportService = eclImportService;
    }
}
