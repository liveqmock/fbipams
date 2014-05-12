package pams.view.userdefrpt;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
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
import java.io.InputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义报表 数据导入.
 * User: zhanrui
 * Date: 14-4-24
 * Time: 上午11:00
 */
@ManagedBean
@ViewScoped
public class DataImportAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String rptno;

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
    }

    public void onUpload(FileUploadEvent event) {
        try {
            InputStream is = event.getFile().getInputstream();
            XSSFWorkbook wb = new XSSFWorkbook(is);
            XSSFSheet sheet = wb.getSheetAt(0);
            int rowCnt = sheet.getLastRowNum();
            XSSFCell cell;

            //清理原有 字段定义
            userDefRptService.clearColumnNames(rptno);
            //清理原有报表数据
            userDefRptService.clearRptData(rptno);

            for (int i = 0; i < rowCnt; i++) {
                int cellCnt = sheet.getRow(i).getLastCellNum();
                List<String> fields = new ArrayList<>();
                for (int j = 0; j < cellCnt; j++) {
                    cell = sheet.getRow(i).getCell(j);
                    String cellValue = "空值";
                    if (cell != null) {
                        if (cell.getCellType() == 1) {
                            cellValue = cell.getStringCellValue();
                        } else if (cell.getCellType() == 0) {
                            cellValue = NumberFormat.getNumberInstance().format(cell.getNumericCellValue()).replaceAll(",", "");
                        } else {
                            cellValue = "格式不正确,导入有误";
                        }
                    }
                    fields.add(cellValue);
                }

                if (i == 0) {  //第一行 标题
                    userDefRptService.insertColumnDefInfo(rptno, fields);
                } else {
                    userDefRptService.insertRptData(rptno, fields);
                }
            }

            //更新导入时间
            userDefRptService.updateImportDataDate(rptno);

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("UserDefRptImp_onUpload");
            oplog.setActionName("阶段性攻坚报表:报表数据导入 " + rptno);
            platformService.insertNewOperationLog(oplog);

            MessageUtil.addInfo(event.getFile().getFileName() + " 已成功导入。");
        } catch (Exception ex) {
            logger.error(event.getFile().getFileName() + " 导入失败。", ex);
            MessageUtil.addError("导入失败." + ex.getMessage());
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

}