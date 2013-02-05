package pams.view.telemarketing;

import net.sf.jxls.report.ReportManager;
import net.sf.jxls.report.ReportManagerImpl;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;
import pams.common.SystemService;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.db.ConnectionManager;
import pub.platform.db.DatabaseConnection;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-4-1
 * Time: 20:53:57
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "InterviewInfoBranchExport")
@RequestScoped
public class InterviewInfoBranchExport {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Date txnDate;

    private String sysDate;
    private String sysTime;

    @PostConstruct
    public void init() {
        this.txnDate = new Date();
        this.sysDate = sdf.format(new Date());
        this.sysTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public void ExcelReport() {
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();
            String fileName = "interviewbranchinfo.xls";
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setContentType("application/msexcel");
            //Ä£°å
            String modelPath = PropertyManager.getProperty("REPORT_ROOTPATH") + "interviewBranchModel.xls";
            String operid = SystemService.getOperatorManager().getOperatorId();
            Map beans = new HashMap();
            beans.put("operid",operid);
            beans.put("txndate", sdf.format(this.txnDate));
            beans.put("printdate", this.sysDate);
            DatabaseConnection conn = ConnectionManager.getInstance().get();

            ReportManager reportManager = new ReportManagerImpl(conn.getConnection(), beans);
            beans.put("rm", reportManager);
            XLSTransformer transformer = new XLSTransformer();
            InputStream is = new BufferedInputStream(new FileInputStream(modelPath));
            Workbook wb = transformer.transformXLS(is, beans);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionManager.getInstance().release();
        }
    }

    public void SingleExcelReport() {
        DatabaseConnection conn=null;
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();
            String fileName = "interviewsingleinfo.xls";
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setContentType("application/msexcel");
            //Ä£°å
            String modelPath = PropertyManager.getProperty("REPORT_ROOTPATH") + "interviewSingleModel.xls";
            String operid = SystemService.getOperatorManager().getOperatorId();
            Map beans = new HashMap();
            beans.put("operid",operid);
            beans.put("txndate", sdf.format(this.txnDate));
            beans.put("printdate", this.sysDate);
            conn = ConnectionManager.getInstance().get();

            ReportManager reportManager = new ReportManagerImpl(conn.getConnection(), beans);
            beans.put("rm", reportManager);
            XLSTransformer transformer = new XLSTransformer();
            InputStream is = new BufferedInputStream(new FileInputStream(modelPath));
            Workbook wb = transformer.transformXLS(is, beans);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            conn.close();
        }
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
}
