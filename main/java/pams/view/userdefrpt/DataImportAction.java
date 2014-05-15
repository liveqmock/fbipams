package pams.view.userdefrpt;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 自定义报表 数据导入.
 * User: zhanrui
 * Date: 14-4-24
 * Time: 上午11:00
 */
@ManagedBean
@ViewScoped
//@RequestScoped
public class DataImportAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String rptno;
    private String rptname;
    private int impcount;
    private int rowcount;
    private int cellcount;

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
        rptname = StringUtils.isEmpty(paramsMap.get("rptname")) ? "" : paramsMap.get("rptname");
        if (StringUtils.isEmpty(rptno)) {
            throw new RuntimeException("请指定报表编号.");
        }
    }

    public void onUpload() {
        long start = System.currentTimeMillis();
        impcount = 0;
        rowcount = 0;
        cellcount = 0;
        InputStream is = null;
        try {
            is = file.getInputstream();

            XSSFWorkbook wb = new XSSFWorkbook(is);
            XSSFSheet sheet = wb.getSheetAt(0);

            rowcount = sheet.getLastRowNum();

            //清理原有 字段定义
            userDefRptService.clearColumnNames(rptno);
            //清理原有报表数据
            userDefRptService.clearRptData(rptno);

            //根据第一列的字段数 设置列数
            cellcount = sheet.getRow(0).getLastCellNum();

            String[] fields = new String[cellcount];

            //设置数据库表字段信息
            getOneRow(sheet, 0, cellcount, fields);
            userDefRptService.insertColumnDefInfo(rptno, fields);
            rowcount--;

            //数据导入
            for (int i = 1; i <= rowcount; i++) {
                getOneRow(sheet, i, cellcount, fields);
                if (StringUtils.isEmpty(fields[0])) { //第一列为空
                    break;
                } else {
                    userDefRptService.insertRptData(rptno, fields);
                    impcount++;
                }
            }

            //更新导入时间
            userDefRptService.updateImportDataDate(rptno);

            MessageUtil.addInfo(" 已成功导入。");

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("UserDefRptImp_onUpload");
            oplog.setActionName("阶段性攻坚报表:报表数据导入 " + rptno);
            platformService.insertNewOperationLog(oplog);
        } catch (Exception ex) {
            logger.error(" 导入失败。", ex);
            MessageUtil.addError("导入失败." + ex.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            long end = System.currentTimeMillis();
            MessageUtil.addInfo("共耗时:" + (end - start) / 1000 + "秒...");
            logger.info("导入记录数:" + impcount);
        }
    }

    private void getOneRow(XSSFSheet sheet, int row, int cellCnt, String[] fields) {
        for (int j = 0; j < cellCnt; j++) {
            XSSFCell cell = sheet.getRow(row).getCell(j);
            String cellValue = "";
            if (cell != null) {
                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                    cellValue = cell.getStringCellValue();
                } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                        cellValue = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                    } else {
                        cellValue = NumberFormat.getNumberInstance().format(cell.getNumericCellValue()).replaceAll(",", "");
                    }
                } else if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                    cellValue = "";
                } else {
                    cellValue = "格式有误";
                }
                fields[j] = cellValue;
            } else {
                fields[j] = "";
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

    public String getRptname() {
        return rptname;
    }

    public void setRptname(String rptname) {
        this.rptname = rptname;
    }

    public int getImpcount() {
        return impcount;
    }

    public void setImpcount(int impcount) {
        this.impcount = impcount;
    }

    public int getRowcount() {
        return rowcount;
    }

    public void setRowcount(int rowcount) {
        this.rowcount = rowcount;
    }

    public int getCellcount() {
        return cellcount;
    }

    public void setCellcount(int cellcount) {
        this.cellcount = cellcount;
    }
}