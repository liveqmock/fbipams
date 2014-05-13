package pams.view.userdefrpt;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.ClsUdTblinfo;
import pams.repository.model.Ptoplog;
import pams.repository.model.userdefrpt.UserDefRptVO;
import pams.service.userdefrpt.UserDefRptService;
import pub.platform.security.OperatorManager;
import skyline.service.PlatformService;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 自定义报表 管理功能.
 * User: zhanrui
 * Date: 14-4-24
 * Time: 上午11:00
 */
@ManagedBean
@ViewScoped
public class UserDefRptMngAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserDefRptVO paramBean;
    private ClsUdTblinfo clsUdTblinfo = new ClsUdTblinfo();
    private List<ClsUdTblinfo> detlRecords;
    private ClsUdTblinfo selectedRecord;

    private boolean isBizBranch; //是否业务网点
    private String title = "...";
    private String rptno;
    private UploadedFile uploadedFile;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{userDefRptService}")
    private UserDefRptService userDefRptService;

    @PostConstruct
    public void init() {
        OperatorManager om = SystemService.getOperatorManager();
        String operid = om.getOperatorId();
        String branchid = om.getOperator().getDeptid();

        Map<String, String> paramsMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        rptno = StringUtils.isEmpty(paramsMap.get("rptno")) ? "" : paramsMap.get("rptno");

        //HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        detlRecords = userDefRptService.selectTblInfos();

        clsUdTblinfo.setRptno("11");
    }

    public void onAddRpt() {
        try {
            //检查KEY重复
            ClsUdTblinfo  tbl_db = userDefRptService.selectTblInfo(clsUdTblinfo.getRptno());
            if (clsUdTblinfo.getRptno().equals(tbl_db.getRptno())) {
                MessageUtil.addError("报表编号重复");
                return;
            }

            userDefRptService.insertTblInfo(clsUdTblinfo);
            detlRecords = userDefRptService.selectTblInfos();


            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("UserDefRptMng_onAddRpt");
            oplog.setActionName("阶段性攻坚报表:新增报表");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            platformService.insertNewOperationLog(oplog);
        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。" + e.getMessage());
        }
    }

    public void onModifyRpt() {
        try {
            ClsUdTblinfo  tbl_db = userDefRptService.selectTblInfo(clsUdTblinfo.getRptno());
            if (tbl_db == null) {
                MessageUtil.addError("报表不存在。");
                return;
            }

            userDefRptService.modifyTblInfo(clsUdTblinfo);
            detlRecords = userDefRptService.selectTblInfos();

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("UserDefRptMng_onModifyRpt");
            oplog.setActionName("阶段性攻坚报表:报表修改");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            platformService.insertNewOperationLog(oplog);
        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。" + e.getMessage());
        }
    }
    public void onDeleteRpt() {
        try {
            ClsUdTblinfo  tbl_db = userDefRptService.selectTblInfo(clsUdTblinfo.getRptno());
            if (tbl_db == null) {
                MessageUtil.addError("报表不存在。");
                return;
            }
            userDefRptService.clearAllRptInfo(clsUdTblinfo.getRptno());
            detlRecords = userDefRptService.selectTblInfos();

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("UserDefRptMng_onDeleteRpt");
            oplog.setActionName("阶段性攻坚报表:报表清除");
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            platformService.insertNewOperationLog(oplog);

            userDefRptService.insertTblInfo(clsUdTblinfo);
        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。" + e.getMessage());
        }
    }

    public String startShowRpt(){
        return "userDefRptShow";
    }

    public String startImport() {
        return "rptDataImp";
    }

    public void startModiRpt(){
//        Map<String, String> paramsMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        String  rptno = StringUtils.isEmpty(paramsMap.get("rptno")) ? "" : paramsMap.get("rptno");
/*
        this.clsUdTblinfo = userDefRptService.selectTblInfo(this.selectedRecord.getRptno());
        if (clsUdTblinfo == null) {
            MessageUtil.addError("报表不存在。");
            return;
        }
*/
        this.clsUdTblinfo = selectedRecord;
        clsUdTblinfo.setRptno("22");

    }

    //===================================================================
    public UserDefRptVO getParamBean() {
        return paramBean;
    }

    public void setParamBean(UserDefRptVO paramBean) {
        this.paramBean = paramBean;
    }

    public List<ClsUdTblinfo> getDetlRecords() {
        return detlRecords;
    }

    public void setDetlRecords(List<ClsUdTblinfo> detlRecords) {
        this.detlRecords = detlRecords;
    }

    public ClsUdTblinfo getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(ClsUdTblinfo selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public boolean isBizBranch() {
        return isBizBranch;
    }

    public void setBizBranch(boolean isBizBranch) {
        this.isBizBranch = isBizBranch;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public ClsUdTblinfo getClsUdTblinfo() {
        return clsUdTblinfo;
    }

    public void setClsUdTblinfo(ClsUdTblinfo clsUdTblinfo) {
        this.clsUdTblinfo = clsUdTblinfo;
    }
}
