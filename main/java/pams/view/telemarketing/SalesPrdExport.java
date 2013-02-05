package pams.view.telemarketing;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import pams.common.SystemService;
import pams.repository.dao.PtdeptMapper;
import pams.repository.dao.SvprdsalinfMapper;
import pams.repository.model.Ptdept;
import pams.repository.model.SvprdsalinfChd;
import pub.platform.advance.utils.PropertyManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-3-29
 * Time: 15:40:46
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean (name="SalesPrdExport")
@RequestScoped
public class SalesPrdExport {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private DecimalFormat df0 = new DecimalFormat("###,##0.00");
    @ManagedProperty(value = "#{svprdsalinfMapper}")
    private SvprdsalinfMapper salesMapper;
    @ManagedProperty(value = "#{ptdeptMapper}")
    private PtdeptMapper ptdeptmapper;

    private String sysDate;
    private String sysTime;
    private Date txnDate;

    private List<SvprdsalinfChd> svplinfListJJ ;
    private List<SvprdsalinfChd> svplinfListBX ;
    private List<SvprdsalinfChd> svplinfListLC ;
    private List<SvprdsalinfChd> svplinfListCK ;
    private List<SvprdsalinfChd> svplinfListJJK;
    private List<SvprdsalinfChd> svplinfListXYK;
    private List<SvprdsalinfChd> svplinfListDK ;
    private List<SvprdsalinfChd> svplinfListDZ ;
    private List<SvprdsalinfChd> svplinfListHJ ;
    private List<SvprdsalinfChd> svplinfListQT ;

    private double totalAmtJJ;
    private double totalAmtBX;
    private double totalAmtLC;
    private double totalAmtCK;
    private double totalAmtJJK;
    private double totalAmtXYK;
    private double totalAmtDK;
    private double totalAmtDZ;
    private double totalAmtHJ;
    private double totalAmtQT;

    @PostConstruct
    public void init() {
        this.sysDate = sdf.format(new Date());
        this.txnDate = new Date();
        this.sysTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    /*
    * ��������*/
    public void ExcelReport() {
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String strTxnDate = sdf.format(this.txnDate);
            String operid = SystemService.getOperatorManager().getOperatorId();
            ServletOutputStream os = response.getOutputStream(); //��������
            response.reset();
            String fileName = "salesinfo.xls";
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
//            response.setContentType("application/x-download");
            response.setContentType("application/msexcel");
            Ptdept dept = ptdeptmapper.selectByOperid(operid);
            svplinfListJJ = salesMapper.selectForPrdid("01",operid,"SVTFUNDTYPE",strTxnDate);   //����
            svplinfListBX = salesMapper.selectForPrdid("02",operid,"SVTINSURETYPE",strTxnDate);   //����
            svplinfListLC = salesMapper.selectForPrdid("03",operid,"SVTFPTYPE",strTxnDate);   //���
            svplinfListCK = salesMapper.selectForPrdid2("04",operid,strTxnDate);   //-���
            svplinfListJJK = salesMapper.selectForPrdid2("05",operid,strTxnDate);   //��ǿ�
            svplinfListXYK = salesMapper.selectForPrdid2("06",operid,strTxnDate);   //���ÿ�
            svplinfListDK = salesMapper.selectForPrdid2("07",operid,strTxnDate);   //����
            svplinfListDZ = salesMapper.selectForPrdid2("08",operid,strTxnDate);   //��������
            svplinfListHJ = salesMapper.selectForPrdid2("09",operid,strTxnDate);   //�ƽ�
            svplinfListQT = salesMapper.selectForPrdid2("10",operid,strTxnDate);   //����
            int[] arry = {svplinfListJJ.size(),svplinfListBX.size(),svplinfListLC.size(),svplinfListCK.size(),
            svplinfListJJK.size(),svplinfListXYK.size(),svplinfListDK.size(),svplinfListDZ.size(),svplinfListHJ.size(),
            svplinfListQT.size()};
            int maxLen = this.getMax(arry);
            String modelPath = PropertyManager.getProperty("REPORT_ROOTPATH") + "salesinfoModel.xls";
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(modelPath));
            HSSFSheet sheet = workbook.getSheetAt(0);   //ȡ�õ�һ��sheet
            HSSFCellStyle cellstyle = getStyle(workbook,1);
            HSSFCellStyle cellstyleCenter = getStyle(workbook,2);
            HSSFCellStyle cellstyleRight = getStyle(workbook,3);
            //���ñ�ͷ����������ǰʱ��
            HSSFRow rowHead0 = sheet.getRow(0);
            HSSFRow rowHead = sheet.getRow(1);
            HSSFCell cellHead = rowHead0.getCell(0);
            cellHead.setCellValue(new HSSFRichTextString("����ҵ����¼��"));
            cellHead = rowHead.getCell(1);
            cellHead.setCellValue(new HSSFRichTextString(dept.getDeptname()));
            cellHead = rowHead.getCell(9);
            cellHead.setCellValue(new HSSFRichTextString(this.sysDate));
            //�������
            setContent2(sheet,cellstyle,cellstyleRight,cellstyleCenter,maxLen);
            //����ܶ�
            setTotalAmt(sheet);
            //�ϲ���
            setMerged(workbook,sheet,maxLen);
            
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*���˱����*/
    public void SingleExcelReport() {
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String strTxnDate = sdf.format(this.txnDate);
            String operid = SystemService.getOperatorManager().getOperatorId();
            ServletOutputStream os = response.getOutputStream(); //��������
            response.reset();
            String fileName = "salesinfoSingle.xls";
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
//            response.setContentType("application/x-download");
            response.setContentType("application/msexcel");
            Ptdept dept = ptdeptmapper.selectByOperid(operid);
            svplinfListJJ = salesMapper.selectForOperid("01",operid,"SVTFUNDTYPE",strTxnDate);   //����
            svplinfListBX = salesMapper.selectForOperid("02",operid,"SVTINSURETYPE",strTxnDate);   //����
            svplinfListLC = salesMapper.selectForOperid("03",operid,"SVTFPTYPE",strTxnDate);   //���
            svplinfListCK = salesMapper.selectForOperid2("04",operid,strTxnDate);   //-���
            svplinfListJJK = salesMapper.selectForOperid2("05",operid,strTxnDate);   //��ǿ�
            svplinfListXYK = salesMapper.selectForOperid2("06",operid,strTxnDate);   //���ÿ�
            svplinfListDK = salesMapper.selectForOperid2("07",operid,strTxnDate);   //����
            svplinfListDZ = salesMapper.selectForOperid2("08",operid,strTxnDate);   //��������
            svplinfListHJ = salesMapper.selectForOperid2("09",operid,strTxnDate);   //�ƽ�
            svplinfListQT = salesMapper.selectForOperid2("10",operid,strTxnDate);   //����
            int[] arry = {svplinfListJJ.size(),svplinfListBX.size(),svplinfListLC.size(),svplinfListCK.size(),
            svplinfListJJK.size(),svplinfListXYK.size(),svplinfListDK.size(),svplinfListDZ.size(),svplinfListHJ.size(),
            svplinfListQT.size()};
            int maxLen = this.getMax(arry);
            String modelPath = PropertyManager.getProperty("REPORT_ROOTPATH") + "salesinfoModel.xls";
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(modelPath));
            HSSFSheet sheet = workbook.getSheetAt(0);   //ȡ�õ�һ��sheet
            HSSFCellStyle cellstyle = getStyle(workbook,1);
            HSSFCellStyle cellstyleCenter = getStyle(workbook,2);
            HSSFCellStyle cellstyleRight = getStyle(workbook,3);
            //���ñ�ͷ����������ǰʱ��
            HSSFRow rowHead0 = sheet.getRow(0);
            HSSFRow rowHead = sheet.getRow(1);
            HSSFCell cellHead = rowHead0.getCell(0);
            cellHead.setCellValue(new HSSFRichTextString("����ҵ����¼��"));
            cellHead = rowHead.getCell(1);
            cellHead.setCellValue(new HSSFRichTextString(dept.getDeptname()));
            cellHead = rowHead.getCell(9);
            cellHead.setCellValue(new HSSFRichTextString(this.sysDate));
            //�������
            setContent2(sheet,cellstyle,cellstyleRight,cellstyleCenter,maxLen);
            //����ܶ�
            setTotalAmt(sheet);
            //�ϲ���
            setMerged(workbook,sheet,maxLen);

            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setTotalAmt(HSSFSheet sheet) {
        HSSFRow row = sheet.getRow(3);
        row.getCell(1).setCellValue(new HSSFRichTextString(df0.format(this.totalAmtJJ)));
        row.getCell(6).setCellValue(new HSSFRichTextString(df0.format(this.totalAmtBX)));
        row.getCell(11).setCellValue(new HSSFRichTextString(df0.format(this.totalAmtLC)));
        row.getCell(16).setCellValue(new HSSFRichTextString(df0.format(this.totalAmtCK)));
        row.getCell(20).setCellValue(new HSSFRichTextString(df0.format(this.totalAmtJJK)));
        row.getCell(23).setCellValue(new HSSFRichTextString(df0.format(this.totalAmtXYK)));
        row.getCell(26).setCellValue(new HSSFRichTextString(df0.format(this.totalAmtDK)));
        row.getCell(30).setCellValue(new HSSFRichTextString(df0.format(this.totalAmtDZ)));
        row.getCell(33).setCellValue(new HSSFRichTextString(df0.format(this.totalAmtHJ)));
        row.getCell(37).setCellValue(new HSSFRichTextString(df0.format(this.totalAmtQT)));
    }

    private void setMerged(HSSFWorkbook workbook,HSSFSheet sheet,int maxLen) {
        sheet.addMergedRegion(new Region(4,(short)0,(4+maxLen),(short)0));
        HSSFCellStyle headLeftStyle = workbook.createCellStyle();
        //���õױ߿�
        headLeftStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
       //���õױ߿���ɫ;
       headLeftStyle.setBottomBorderColor(HSSFColor.BLACK.index);
       sheet.getRow(4+maxLen).createCell(0).setCellStyle(headLeftStyle);
    }

    private void setContent2(HSSFSheet sheet,HSSFCellStyle cellstyle,HSSFCellStyle cellstyleRight,HSSFCellStyle cellstyleCenter
            ,int maxLength) {
        HSSFRow row;
        int rowIndexStart = 5;
        SvprdsalinfChd sales;
        for (int i = 0;i < maxLength;i++) {
            row = sheet.createRow(rowIndexStart);
            row.setHeight((short)500);
            HSSFCell cell = null;
            //jj
            int cellIndexStartJJ = 2;
            if (i+1 <= svplinfListJJ.size()) {
                sales = svplinfListJJ.get(i);
                myCreateCell(cellIndexStartJJ - 1,String.valueOf(i+1), row, cell,cellstyleCenter); //���
                myCreateCell(cellIndexStartJJ,sales.getOpername(), row, cell,cellstyle); //��Ա
                myCreateCell(cellIndexStartJJ + 1,sales.getCustomername(),row,cell,cellstyle); //�ͻ�
                myCreateCell(cellIndexStartJJ + 2,sales.getEnuitemlabel(),row,cell,cellstyle); //����
                totalAmtJJ += sales.getSalesamt1().doubleValue();
                myCreateCell(cellIndexStartJJ + 3,df0.format(sales.getSalesamt1()),row,cell,cellstyleRight); //���
            } else {
                myCreateCell(cellIndexStartJJ - 1,String.valueOf(i+1), row, cell,cellstyleCenter); //���
                myCreateCell(cellIndexStartJJ," ", row, cell,cellstyle); //��Ա
                myCreateCell(cellIndexStartJJ + 1," ",row,cell,cellstyle); //�ͻ�
                myCreateCell(cellIndexStartJJ + 2," ",row,cell,cellstyle); //����
                myCreateCell(cellIndexStartJJ + 3," ",row,cell,cellstyleRight); //���
            }

            //����
            int cellIndexStartBX = 7;
            if (i+1 <= svplinfListBX.size()) {
                sales = svplinfListBX.get(i);
                myCreateCell(cellIndexStartBX - 1,String.valueOf(i+1), row, cell,cellstyleCenter); //���
                myCreateCell(cellIndexStartBX,sales.getOpername(), row, cell,cellstyle); //��Ա
                myCreateCell(cellIndexStartBX + 1,sales.getCustomername(),row,cell,cellstyle); //�ͻ�
                myCreateCell(cellIndexStartBX + 2,sales.getEnuitemlabel(),row,cell,cellstyle); //����
                this.totalAmtBX += sales.getSalesamt1().doubleValue();
                myCreateCell(cellIndexStartBX + 3,df0.format(sales.getSalesamt1()),row,cell,cellstyleRight); //���
            } else {
                myCreateCell(cellIndexStartBX - 1,String.valueOf(i+1), row, cell,cellstyleCenter); //���
                myCreateCell(cellIndexStartBX," ", row, cell,cellstyle); //��Ա
                myCreateCell(cellIndexStartBX + 1," ",row,cell,cellstyle); //�ͻ�
                myCreateCell(cellIndexStartBX + 2," ",row,cell,cellstyle); //����
                myCreateCell(cellIndexStartBX + 3," ",row,cell,cellstyleRight); //���
            }

            //���
            int cellIndexStartLC = 12;
            if (i+1 <= svplinfListLC.size()) {
                sales = svplinfListLC.get(i);
                myCreateCell(cellIndexStartLC - 1,String.valueOf(i+1), row, cell,cellstyleCenter); //���
                myCreateCell(cellIndexStartLC, sales.getOpername(), row,cell,cellstyle); //��Ա
                myCreateCell(cellIndexStartLC + 1,sales.getCustomername(),row,cell,cellstyle); //�ͻ�
                myCreateCell(cellIndexStartLC + 2,sales.getEnuitemlabel(),row,cell,cellstyle); //����
                this.totalAmtLC += sales.getSalesamt1().doubleValue();
                myCreateCell(cellIndexStartLC + 3,df0.format(sales.getSalesamt1()),row,cell,cellstyleRight); //���
            } else {
                myCreateCell(cellIndexStartLC - 1,String.valueOf(i+1), row, cell,cellstyleCenter); //���
                myCreateCell(cellIndexStartLC," ", row, cell,cellstyle); //��Ա
                myCreateCell(cellIndexStartLC + 1," ",row,cell,cellstyle); //�ͻ�
                myCreateCell(cellIndexStartLC + 2," ",row,cell,cellstyle); //����
                myCreateCell(cellIndexStartLC + 3," ",row,cell,cellstyleRight); //���
            }

            //���
            int cellIndexStartCK = 17;
            if (i+1 <= svplinfListCK.size()){
                sales = svplinfListCK.get(i);
                myCreateCell(cellIndexStartCK - 1,String.valueOf(i+1), row, cell,cellstyleCenter); //���
                myCreateCell(cellIndexStartCK, sales.getOpername(), row,cell,cellstyle); //��Ա
                myCreateCell(cellIndexStartCK + 1,sales.getCustomername(),row,cell,cellstyle); //�ͻ�
                this.totalAmtCK += sales.getSalesamt1().doubleValue();
                myCreateCell(cellIndexStartCK + 2,df0.format(sales.getSalesamt1()),row,cell,cellstyleRight); //���
            } else {
                myCreateCell(cellIndexStartCK - 1,String.valueOf(i+1), row, cell,cellstyleCenter); //���
                myCreateCell(cellIndexStartCK," ", row, cell,cellstyle); //��Ա
                myCreateCell(cellIndexStartCK + 1," ",row,cell,cellstyle); //�ͻ�
                myCreateCell(cellIndexStartCK + 2," ",row,cell,cellstyleRight);
            }

            //��ǿ�
            int cellIndexStartJJK = 21;
            if (i+1 <= svplinfListJJK.size()){
                sales = svplinfListJJK.get(i);
                myCreateCell(cellIndexStartJJK - 1,String.valueOf(i+1), row, cell,cellstyleCenter); //���
                myCreateCell(cellIndexStartJJK, sales.getOpername(), row,cell,cellstyle); //��Ա
                this.totalAmtJJK += sales.getSalesnum1().doubleValue();
                myCreateCell(cellIndexStartJJK + 1,String.valueOf(sales.getSalesnum1()),row,cell,cellstyleRight); //Ӫ������
            } else {
                myCreateCell(cellIndexStartJJK - 1,String.valueOf(i+1), row, cell,cellstyleCenter);
                myCreateCell(cellIndexStartJJK," ", row, cell,cellstyle);
                myCreateCell(cellIndexStartJJK + 1," ",row,cell,cellstyleRight);
            }

            //svplinfListXYK ���ÿ�
            int cellIndexStartXYK = 24;
            if (i+1 <= svplinfListXYK.size()){
                sales = svplinfListXYK.get(i);
                myCreateCell(cellIndexStartXYK - 1,String.valueOf(i+1), row, cell,cellstyleCenter); //���
                myCreateCell(cellIndexStartXYK, sales.getOpername(), row,cell,cellstyle); //��Ա
                this.totalAmtXYK += sales.getSalesnum1().doubleValue();
                myCreateCell(cellIndexStartXYK + 1,String.valueOf(sales.getSalesnum1()),row,cell,cellstyleRight); //Ӫ������
            } else {
                myCreateCell(cellIndexStartXYK - 1,String.valueOf(i+1), row, cell,cellstyleCenter);
                myCreateCell(cellIndexStartXYK," ", row, cell,cellstyle);
                myCreateCell(cellIndexStartXYK + 1," ",row,cell,cellstyleRight);
            }

            //����
            int cellIndexStartDK = 27;
            if (i+1 <= svplinfListDK.size()){
                sales = svplinfListDK.get(i);
                myCreateCell(cellIndexStartDK - 1,String.valueOf(i+1), row, cell,cellstyleCenter); //���
                myCreateCell(cellIndexStartDK, sales.getOpername(), row,cell,cellstyle); //��Ա
                myCreateCell(cellIndexStartDK + 1,sales.getCustomername(),row,cell,cellstyle); //�ͻ�
                this.totalAmtDK += sales.getSalesamt1().doubleValue();
                myCreateCell(cellIndexStartDK + 2,df0.format(sales.getSalesamt1()),row,cell,cellstyleRight); //���
            } else {
                myCreateCell(cellIndexStartDK - 1,String.valueOf(i+1), row, cell,cellstyleCenter);
                myCreateCell(cellIndexStartDK," ", row, cell,cellstyle);
                myCreateCell(cellIndexStartDK + 1," ",row,cell,cellstyle);
                myCreateCell(cellIndexStartDK + 2," ",row,cell,cellstyleRight);
            }

            //��������
            int cellIndexStartDZ = 31;
            if (i+1 <= svplinfListDZ.size()){
                sales = svplinfListDZ.get(i);
                myCreateCell(cellIndexStartDZ - 1,String.valueOf(i+1), row, cell,cellstyleCenter); //���
                myCreateCell(cellIndexStartDZ, sales.getOpername(), row,cell,cellstyle); //��Ա
                this.totalAmtDZ += sales.getSalesnum1().doubleValue();
                myCreateCell(cellIndexStartDZ + 1,String.valueOf(sales.getSalesnum1()),row,cell,cellstyleRight); //Ӫ������
            } else {
                myCreateCell(cellIndexStartDZ - 1,String.valueOf(i+1), row, cell,cellstyleCenter);
                myCreateCell(cellIndexStartDZ," ", row, cell,cellstyle);
                myCreateCell(cellIndexStartDZ + 1," ",row,cell,cellstyleRight);
            }

            //�ƽ�
            int cellIndexStartHJ = 34;
            if (i+1 <= svplinfListHJ.size()){
                sales = svplinfListHJ.get(i);
                myCreateCell(cellIndexStartHJ - 1,String.valueOf(i+1), row, cell,cellstyleCenter); //���
                myCreateCell(cellIndexStartHJ, sales.getOpername(), row,cell,cellstyle); //��Ա
                myCreateCell(cellIndexStartHJ + 1,sales.getCustomername(),row,cell,cellstyle); //�ͻ�
                this.totalAmtHJ += sales.getSalesnum1().doubleValue();
                myCreateCell(cellIndexStartHJ + 2,df0.format(sales.getSalesnum1()),row,cell,cellstyleRight); //�ɽ�����
            } else {
                myCreateCell(cellIndexStartHJ - 1,String.valueOf(i+1), row, cell,cellstyleCenter);
                myCreateCell(cellIndexStartHJ," ", row, cell,cellstyle);
                myCreateCell(cellIndexStartHJ + 1," ",row,cell,cellstyle);
                myCreateCell(cellIndexStartHJ + 2," ",row,cell,cellstyleRight);
            }

            //����
            int cellIndexStartQT = 38;
            if (i+1 <= svplinfListQT.size()){
                sales = svplinfListQT.get(i);
                myCreateCell(cellIndexStartQT - 1,String.valueOf(i+1), row, cell,cellstyleCenter); //���
                myCreateCell(cellIndexStartQT, sales.getOpername(), row,cell,cellstyle); //��Ա
                myCreateCell(cellIndexStartQT + 1,sales.getCustomername(),row,cell,cellstyle); //�ͻ�
                this.totalAmtQT += sales.getSalesamt1().doubleValue();
                myCreateCell(cellIndexStartQT + 2,df0.format(sales.getSalesamt1()),row,cell,cellstyleRight); //���
            } else {
                myCreateCell(cellIndexStartQT - 1,String.valueOf(i+1), row, cell,cellstyleCenter);
                myCreateCell(cellIndexStartQT," ", row, cell,cellstyle);
                myCreateCell(cellIndexStartQT + 1," ",row,cell,cellstyle);
                myCreateCell(cellIndexStartQT + 2," ",row,cell,cellstyleRight);
            }

            rowIndexStart++;
        }
    }

    private static void myCreateCell(int cellIndex, String value, HSSFRow row, HSSFCell cell,HSSFCellStyle cellstyle) {
        cell = row.createCell(cellIndex);
        cell.setCellValue(new HSSFRichTextString(value));
        cell.setCellStyle(cellstyle);
    }

    public HSSFCellStyle getStyle(HSSFWorkbook workbook,int flag) {
       //��������;
       HSSFFont font = workbook.createFont();
       //���������С;
       font.setFontHeightInPoints((short) 12);
       //������������;
       font.setFontName("����");
       //font.setItalic(true);
       //font.setStrikeout(true);
       //������ʽ;
       HSSFCellStyle style = workbook.createCellStyle();
       //���õױ߿�;
       style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
       //���õױ߿���ɫ;
       style.setBottomBorderColor(HSSFColor.BLACK.index);
       //������߿�;
       style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
       //������߿���ɫ;
       style.setLeftBorderColor(HSSFColor.BLACK.index);
       //�����ұ߿�;
       style.setBorderRight(HSSFCellStyle.BORDER_THIN);
       //�����ұ߿���ɫ;
       style.setRightBorderColor(HSSFColor.BLACK.index);
       //���ö��߿�;
       style.setBorderTop(HSSFCellStyle.BORDER_THIN);
       //���ö��߿���ɫ;
       style.setTopBorderColor(HSSFColor.BLACK.index);
       //����ʽ��Ӧ�����õ�����;
        style.setFont(font);
        //�����Զ�����;
        style.setWrapText(true);
        //����ˮƽ���뷽ʽ
        if (flag == 1) {
            style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        } else if (flag == 2) {
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        } else if (flag == 3) {
            style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        } else {
            style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        }
        //���ô�ֱ�������ʽΪ���ж���;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }

    private int getMax(int[] intArray) {
        int temp = 0;
        for(int i = 0;i < intArray.length;i++) {
            if (intArray[i] > temp) {
                temp = intArray[i];
            }
        }
        return temp;
    }

    public SvprdsalinfMapper getSalesMapper() {
        return salesMapper;
    }

    public void setSalesMapper(SvprdsalinfMapper salesMapper) {
        this.salesMapper = salesMapper;
    }

    public Date getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    public String getSysDate() {
        return sysDate;
    }

    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    public String getSysTime() {
        return sysTime;
    }

    public void setSysTime(String sysTime) {
        this.sysTime = sysTime;
    }

    public PtdeptMapper getPtdeptmapper() {
        return ptdeptmapper;
    }

    public void setPtdeptmapper(PtdeptMapper ptdeptmapper) {
        this.ptdeptmapper = ptdeptmapper;
    }
}
