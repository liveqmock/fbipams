package pams.view.telemarketing;

import net.sf.jxls.report.ReportManager;
import net.sf.jxls.report.ReportManagerImpl;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;
import pams.common.SystemService;
import pams.repository.dao.PtdeptMapper;
import pams.repository.dao.SvprdsalinfMapper;
import pams.repository.model.Ptdept;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.db.ConnectionManager;
import pub.platform.db.DatabaseConnection;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-3-31
 * Time: 21:52:06
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "SalesInfoCellectExport")
@RequestScoped
public class SalesInfoCollectExport {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private DecimalFormat df0 = new DecimalFormat("###,##0.00");
    @ManagedProperty(value = "#{svprdsalinfMapper}")
    private SvprdsalinfMapper salesMapper;
    @ManagedProperty(value = "#{ptdeptMapper}")
    private PtdeptMapper ptdeptmapper;
    @Min(message = "网点编号必须是数字", value = 0)
    private String wddeptid;
    private Date begindate;
    private Date enddate;

    private String sysDate;
    private String sysTime;

    @PostConstruct
    public void init() {
        begindate = new Date();
        enddate = new Date();
        this.sysDate = sdf.format(new Date());
        this.sysTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public void ExcelExport() {
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream os = response.getOutputStream();
            response.reset();
            String fileName = "salesinfoTotal1.xls";
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setContentType("application/msexcel");
            //模板
            String modelPath = PropertyManager.getProperty("REPORT_ROOTPATH") + "salescellectmodel.xls";
            Map beans = new HashMap();
            String operid = SystemService.getOperatorManager().getOperatorId();
            Ptdept dept = ptdeptmapper.selectByOperid(operid);
            this.wddeptid = dept.getDeptid();
            String deptname = dept.getDeptname();
            beans.put("deptname", deptname);
            beans.put("wddeptid", this.wddeptid);
            beans.put("begindate", sdf.format(this.begindate));
            beans.put("enddate", sdf.format(this.enddate));
            DatabaseConnection conn = ConnectionManager.getInstance().get();

            ReportManager reportManager = new ReportManagerImpl(conn.getConnection(), beans);
            beans.put("rm", reportManager);
            XLSTransformer transformer = new XLSTransformer();
            InputStream is = new BufferedInputStream(new FileInputStream(modelPath));
            Workbook wb = transformer.transformXLS(is, beans);
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

    public SvprdsalinfMapper getSalesMapper() {
        return salesMapper;
    }

    public void setSalesMapper(SvprdsalinfMapper salesMapper) {
        this.salesMapper = salesMapper;
    }

    public PtdeptMapper getPtdeptmapper() {
        return ptdeptmapper;
    }

    public void setPtdeptmapper(PtdeptMapper ptdeptmapper) {
        this.ptdeptmapper = ptdeptmapper;
    }

    public Date getBegindate() {
        return begindate;
    }

    public void setBegindate(Date begindate) {
        this.begindate = begindate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getWddeptid() {
        return wddeptid;
    }

    public void setWddeptid(String wddeptid) {
        this.wddeptid = wddeptid;
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
