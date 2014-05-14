package pams.view.userdefrpt;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.utils.MessageUtil;
import pams.repository.model.Ptoplog;
import pams.service.userdefrpt.UserDefRptService;
import skyline.service.PlatformService;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * �Զ��屨�� ���ݵ���.
 * User: zhanrui
 * Date: 14-4-24
 * Time: ����11:00
 */
@ManagedBean
@ViewScoped
public class DataImportAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String rptno;
    private UploadedFile file;


    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{userDefRptService}")
    private UserDefRptService userDefRptService;

    @PostConstruct
    public void init() {
        Map<String, String> paramsMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        rptno = StringUtils.isEmpty(paramsMap.get("rptno")) ? "" : paramsMap.get("rptno");

        if (StringUtils.isEmpty(rptno)) {
            throw new RuntimeException("��ָ��������.");
        }
    }

    public void onUpload(FileUploadEvent event) {
        InputStream is = null;
        try {
             is = event.getFile().getInputstream();
//            InputStream is = file.getInputstream();

            XSSFWorkbook wb = new XSSFWorkbook(is);
            XSSFSheet sheet = wb.getSheetAt(0);
            int rowCnt = sheet.getLastRowNum();
            XSSFCell cell;

            //����ԭ�� �ֶζ���
            userDefRptService.clearColumnNames(rptno);
            //����ԭ�б�������
            userDefRptService.clearRptData(rptno);

            for (int i = 0; i < rowCnt; i++) {
                int cellCnt = sheet.getRow(i).getLastCellNum();
                List<String> fields = new ArrayList<>();
                for (int j = 0; j < cellCnt; j++) {
                    cell = sheet.getRow(i).getCell(j);
                    String cellValue = "��ֵ";
                    if (cell != null) {
                        if (cell.getCellType() == 1) {
                            cellValue = cell.getStringCellValue();
                        } else if (cell.getCellType() == 0) {
                            cellValue = NumberFormat.getNumberInstance().format(cell.getNumericCellValue()).replaceAll(",", "");
                        } else {
                            cellValue = "��ʽ����ȷ,��������";
                        }
                    }
                    fields.add(cellValue);
                }

                if (i == 0) {  //��һ�� ����
                    userDefRptService.insertColumnDefInfo(rptno, fields);
                } else {
                    userDefRptService.insertRptData(rptno, fields);
                }
            }

            //���µ���ʱ��
            userDefRptService.updateImportDataDate(rptno);

            MessageUtil.addInfo(" �ѳɹ����롣");

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("UserDefRptImp_onUpload");
            oplog.setActionName("�׶��Թ��ᱨ��:�������ݵ��� " + rptno);
            platformService.insertNewOperationLog(oplog);
        } catch (Exception ex) {
            logger.error(event.getFile().getFileName() + " ����ʧ�ܡ�", ex);
            MessageUtil.addError("����ʧ��." + ex.getMessage());
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String onBack() {
        return "userDefRptMng";
//        return "userDefRptMng" + "?faces-redirect=true";
    }
    //===================================================================


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

    public UserDefRptService getUserDefRptService() {
        return userDefRptService;
    }

    public void setUserDefRptService(UserDefRptService userDefRptService) {
        this.userDefRptService = userDefRptService;
    }

    public String getRptno() {
        return rptno;
    }

    public void setRptno(String rptno) {
        this.rptno = rptno;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
}