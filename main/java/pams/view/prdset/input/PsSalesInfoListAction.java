package pams.view.prdset.input;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.Svpsprdsetmain;
import pams.repository.model.Svpssaleinfo;
import pams.repository.model.prdset.PsPrdCkptInfoBean;
import pams.repository.model.prdset.PsSalesInfoBean;
import pams.repository.model.prdset.PsSalesPrdInfoBean;
import skyline.service.PlatformService;
import skyline.service.ToolsService;
import pams.service.prdset.PsSalesInfoService;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 产品套餐查询
 * User: zhanrui
 * Date: 11-4-11
 * Time: 下午12:15
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PsSalesInfoListAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //批量输入模式
    private String batchInputMode;
    //修改模式
    private String deleteMode;
    //提交模式
    private String commitMode;
    //归档(考核)模式
    private String archiveMode;

    private String operid;
    private String branchid;

    private PsSalesInfoBean vo;
    private PsSalesInfoBean selectedProductSetRecord;
    private PsSalesPrdInfoBean selectedProductRecord;
    //交易日期
    private String txnDate;
    private String startDate;
    private String endDate;
    private String archivestart;
    private String archiveend;
    private int totalcount = 0;
    private BigDecimal totalamt = new BigDecimal(0.00);

    private String panelTitle;

    private List<PsSalesInfoBean> salesList;
    private List<PsSalesPrdInfoBean> salesprdList;
    private List<PsPrdCkptInfoBean> salesprdckptList;

    private List<SelectItem> branchList;
    private List<SelectItem> prdsetList;
    private List<SelectItem> commitflagList;
    private List<SelectItem> checkflagList;
    private List<SelectItem> certTypeList;


    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{psSalesInfoService}")
    private PsSalesInfoService salesInfoService;


    public String getBatchInputMode() {
        return batchInputMode;
    }

    public void setBatchInputMode(String batchInputMode) {
        this.batchInputMode = batchInputMode;
    }

    public String getDeleteMode() {
        return deleteMode;
    }

    public void setDeleteMode(String deleteMode) {
        this.deleteMode = deleteMode;
    }

    public String getCommitMode() {
        return commitMode;
    }

    public void setCommitMode(String commitMode) {
        this.commitMode = commitMode;
    }

    public String getArchiveMode() {
        return archiveMode;
    }

    public void setArchiveMode(String archiveMode) {
        this.archiveMode = archiveMode;
    }

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }


    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(String panelTitle) {
        this.panelTitle = panelTitle;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }


    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    public void setSalesInfoService(PsSalesInfoService salesInfoService) {
        this.salesInfoService = salesInfoService;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }


    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public PsSalesInfoBean getSelectedProductSetRecord() {
        return selectedProductSetRecord;
    }

    public void setSelectedProductSetRecord(PsSalesInfoBean selectedProductSetRecord) {
        this.selectedProductSetRecord = selectedProductSetRecord;
    }

    public BigDecimal getTotalamt() {
        return totalamt;
    }

    public void setTotalamt(BigDecimal totalamt) {
        this.totalamt = totalamt;
    }

    public List<PsSalesInfoBean> getSalesList() {
        return salesList;
    }

    public void setSalesList(List<PsSalesInfoBean> salesList) {
        this.salesList = salesList;
    }

    public List<PsSalesPrdInfoBean> getSalesprdList() {
        return salesprdList;
    }

    public void setSalesprdList(List<PsSalesPrdInfoBean> salesprdList) {
        this.salesprdList = salesprdList;
    }

    public PsSalesInfoBean getVo() {
        return vo;
    }

    public void setVo(PsSalesInfoBean vo) {
        this.vo = vo;
    }

    public List<SelectItem> getPrdsetList() {
        return prdsetList;
    }

    public void setPrdsetList(List<SelectItem> prdsetList) {
        this.prdsetList = prdsetList;
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public List<SelectItem> getCommitflagList() {
        return commitflagList;
    }

    public void setCommitflagList(List<SelectItem> commitflagList) {
        this.commitflagList = commitflagList;
    }

    public List<SelectItem> getCheckflagList() {
        return checkflagList;
    }

    public void setCheckflagList(List<SelectItem> checkflagList) {
        this.checkflagList = checkflagList;
    }

    public List<SelectItem> getCertTypeList() {
        return certTypeList;
    }

    public void setCertTypeList(List<SelectItem> certTypeList) {
        this.certTypeList = certTypeList;
    }

    public List<PsPrdCkptInfoBean> getSalesprdckptList() {
        return salesprdckptList;
    }

    public void setSalesprdckptList(List<PsPrdCkptInfoBean> salesprdckptList) {
        this.salesprdckptList = salesprdckptList;
    }

    public PsSalesPrdInfoBean getSelectedProductRecord() {
        return selectedProductRecord;
    }

    public void setSelectedProductRecord(PsSalesPrdInfoBean selectedProductRecord) {
        this.selectedProductRecord = selectedProductRecord;
    }

    public String getArchivestart() {
        return archivestart;
    }

    public void setArchivestart(String archivestart) {
        this.archivestart = archivestart;
    }

    public String getArchiveend() {
        return archiveend;
    }

    public void setArchiveend(String archiveend) {
        this.archiveend = archiveend;
    }

    //============================================================
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        this.batchInputMode = request.getParameter("batch");
        this.deleteMode = request.getParameter("delete");
        this.archiveMode = request.getParameter("archive");
        this.commitMode = request.getParameter("commit");

        if (StringUtils.isEmpty(batchInputMode)) {
            this.batchInputMode = "0";
        }
        if (StringUtils.isEmpty(deleteMode)) {
            this.deleteMode = "0";
        }
        if (StringUtils.isEmpty(archiveMode)) {
            this.archiveMode = "0";
        }
        if (StringUtils.isEmpty(commitMode)) {
            this.commitMode = "0";
        }

        OperatorManager om = SystemService.getOperatorManager();
        this.operid = om.getOperatorId();
        this.branchid = om.getOperator().getDeptid();

        initBranchList();
        this.commitflagList = toolsService.getEnuSelectItemList("SVPSCOMMITFLAG", true, false);
        this.checkflagList = toolsService.getEnuSelectItemList("SVPSCHECKFLAG", true, false);
        this.certTypeList = toolsService.getEnuSelectItemList("CERTTYPE", true, true);
        this.prdsetList = initPrdsetSelectItemList();

        this.vo = new PsSalesInfoBean();
        this.vo.setTellerid(this.operid);
        this.vo.setBranchid(this.branchid);

        if ("1".equals(this.commitMode)) {
            this.vo.setCommitflag("0");
        }

        if ("1".equals(this.archiveMode)) {
            this.vo.setArchiveflag("1");
            this.vo.setCommitflag("1");
            this.vo.setCheckflag("1");
        } else if ("0".equals(this.archiveMode)) {
            this.vo.setArchiveflag("0");
        }


        this.startDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.endDate = this.startDate;

        DateTime dateTime = new DateTime();
        this.archivestart = dateTime.minusMonths(1).toString("yyyy-MM");
        this.archiveend = this.archivestart;
    }

    public String onQuery() {

        try {

            if ("1".equals(this.archiveMode)) {
                this.vo.setStartdate("");
                this.vo.setEnddate("");
                this.vo.setArchivestart(this.archivestart);
                this.vo.setArchiveend(this.archiveend);
            }else{
                this.vo.setStartdate(this.startDate);
                this.vo.setEnddate(this.endDate);
                this.vo.setArchivestart("");
                this.vo.setArchiveend("");
            }
            int qrymaxnum = Integer.parseInt(platformService.selectEnuExpandValue("SYSTEMPARAM", "MAXQRYNUM"));
            int count = salesInfoService.countPrdsetSalesinfo(this.vo);
            if (count > qrymaxnum) {
                MessageUtil.addError("查询记录数过大，已超过" + qrymaxnum + "条，请缩小查询范围。");
                return null;
            }

            this.salesList = salesInfoService.selectPrdsetSalesinfo(this.vo);

            if (this.salesList.size() == 0) {
                MessageUtil.addInfo("未查询到符合条件的记录。");
            }

            this.totalcount = this.salesList.size();

        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。");
        }
        return null;
    }

    /*打印数据*/
    public String onPrint() {
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            //检查查询参数
            if ("1".equals(this.archiveMode)) {
                this.vo.setStartdate("");
                this.vo.setEnddate("");
                this.vo.setArchivestart(this.archivestart);
                this.vo.setArchiveend(this.archiveend);
            }else{
                this.vo.setStartdate(this.startDate);
                this.vo.setEnddate(this.endDate);
                this.vo.setArchivestart("");
                this.vo.setArchiveend("");
            }
            int qrymaxnum = Integer.parseInt(platformService.selectEnuExpandValue("SYSTEMPARAM", "MAXQRYNUM"));
            int count = salesInfoService.countPrdsetSalesinfo(this.vo);
            if (count > qrymaxnum) {
                MessageUtil.addError("查询记录数过大，已超过" + qrymaxnum + "条，请缩小查询范围。");
                return null;
            } else if (count < 1) {
                MessageUtil.addInfo("无满足当前打印条件的记录。");
                return null;
            }
            List<PsSalesInfoBean> salesListPrt = salesInfoService.selectPrdsetSalesinfo(this.vo);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream os = response.getOutputStream();
            response.reset();
            String fileName = "salesinfoqry.xls";
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setContentType("application/msexcel");
            Map beans = new HashMap();
            //模板
            String modelPath = PropertyManager.getProperty("REPORT_ROOTPATH") + "psSalesInfoQrymodel.xls";
            beans.put("salesList",salesListPrt);
            XLSTransformer transformer = new XLSTransformer();
            if (this.batchInputMode.equals("1")) {
                if (this.archiveMode.equals("1")) {
                    transformer.setColumnsToHide(new short[]{(short)6});
                    beans.put("titlename","网点产品套餐考核后数据");
                } else {
                    transformer.setColumnsToHide(new short[]{(short)7});
                    beans.put("titlename","网点产品套餐考核前数据");
                }
            } else {
                transformer.setColumnsToHide(new short[]{(short)1,(short)2,(short)7});
                beans.put("titlename","个人产品套餐考核前数据");
            }
            InputStream is = new BufferedInputStream(new FileInputStream(modelPath));
            Workbook wb = transformer.transformXLS(is, beans);
            wb.write(os);
            is.close();
            os.flush();
            os.close();
        } catch (Exception e) {
            logger.error("打印数据时出现错误。", e);
            MessageUtil.addWarn("打印数据时出现错误。");
        }
        return null;
    }

    public String onDeleteRecord() {

        try {
            salesInfoService.deleteSalesInfoOneRecord((Svpssaleinfo) this.selectedProductSetRecord, this.operid);
            onQuery();
        } catch (Exception e) {
            logger.error("数据删除错误！", e);
            MessageUtil.addError("数据删除错误！");
        }
        return null;
    }

    public String onCommitOneRecord() {
        try {
            salesInfoService.commitSalesInfoOneRecord((Svpssaleinfo) this.selectedProductSetRecord, this.operid);
            onQuery();
        } catch (Exception e) {
            logger.error("数据提交错误！", e);
            MessageUtil.addError("数据提交错误！");
        }
        return null;
    }

    public String onCommitAllRecords() {
        try {
            if (this.salesList == null || this.salesList.size() == 0) {
                MessageUtil.addError("请先查询记录。");
                return null;
            }
            salesInfoService.commitSalesInfoAllRecords(this.salesList, this.operid);
            onQuery();
        } catch (Exception e) {
            logger.error("数据提交错误！", e);
            MessageUtil.addError("数据提交错误！");
        }
        return null;
    }

    public String onSelectPSRecord(SelectEvent event) {
        String prdsetguid = this.selectedProductSetRecord.getGuid();
        this.salesprdList = salesInfoService.selectPrdSalesInfo(prdsetguid);
        this.salesprdckptList = salesInfoService.selectPrdCkptInfo(prdsetguid);
        //logger.info("aaa"+this.selectedRecord.getGuid());
        //logger.info( ((PsSalesInfoBean)event.getObject()).getGuid());
        return null;
    }

    public String onSelectProductRecord(SelectEvent event){
         //this.salesprdckptList = salesInfoService.selectPrdCkptInfo(this.selectedProductRecord.getGuid());
         return null;
    }
    //=======================================

    /**
     * 获取产品套餐列表（期限？？）
     *
     * @return
     */
    private List<SelectItem> initPrdsetSelectItemList() {
        List<Svpsprdsetmain> records = salesInfoService.selectprdsetList();
        List<SelectItem> items = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem("", "全部");
        items.add(item);
        for (Svpsprdsetmain record : records) {
            //SelectItem item = new SelectItem(record.getPrdsetid(), record.getPrdsetid() + " " + record.getPrdsetname());
            item = new SelectItem(record.getPrdsetid(), record.getPrdsetname());
            items.add(item);
        }
        return items;
    }

    private void initBranchList() {
        List<String> records = platformService.selectBranchLevelString(this.branchid);
        this.branchList = new ArrayList<SelectItem>();
        for (String record : records) {
            String[] recordArray = record.split("\\|");
            SelectItem item = new SelectItem(recordArray[0], recordArray[1]);
            this.branchList.add(item);
        }
    }

    private void initCommitflagList() {
        List<String> records = platformService.selectBranchLevelString(this.branchid);
        this.branchList = new ArrayList<SelectItem>();
        for (String record : records) {
            String[] recordArray = record.split("\\|");
            SelectItem item = new SelectItem(recordArray[0], recordArray[1]);
            this.branchList.add(item);
        }
    }

}
