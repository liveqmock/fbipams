package pams.view.userdefrpt;

import org.apache.commons.lang.StringUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.ClsUdFldinfo;
import pams.repository.model.ClsUdRptdata;
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
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单个自定义报表 展示.
 * User: zhanrui
 * Date: 14-4-24
 * Time: 上午11:00
 */
@ManagedBean
@ViewScoped
public class UserDefRptShowAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserDefRptVO paramBean;
    private ClsUdRptdata selectedRecord;

    private boolean isBizBranch; //是否业务网点
    private String title = "...";
    private String tableWidth = "width:100%";
    private String rptno;
    Map<String,String> banksMap = new HashMap<>();

    private LazyDataModel<ClsUdRptdata> lazyDataModel;
    private List<SelectItem> branchList;
    private List<ColumnModel> columns = new ArrayList<ColumnModel>();

    private DataTable dataTable;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{userDefRptService}")
    private UserDefRptService userDefRptService;

    static public class ColumnModel implements Serializable {

        private String header;
        private String property;

        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }

        public String getHeader() {
            return header;
        }

        public String getProperty() {
            return property;
        }
    }

    @PostConstruct
    public void init() {
        Map<String, String> paramsMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        rptno = StringUtils.isEmpty(paramsMap.get("rptno")) ? "" : paramsMap.get("rptno");

        if (StringUtils.isEmpty(rptno)) {
            throw new RuntimeException("请指定报表编号.");
        }

        OperatorManager om = SystemService.getOperatorManager();
        String operid = om.getOperatorId();
        String branchid = om.getOperator().getDeptid();


        //HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        this.branchList = toolsService.selectBranchList(branchid);
        if (branchList.size() == 1) {
            isBizBranch = true;
        }

        this.paramBean = new UserDefRptVO();

        //初始化机构map
        List<String> bankIdAndNames = platformService.selectBranchIdAndName(branchid);
        for (String bankIdAndName : bankIdAndNames) {
            String[] sa = bankIdAndName.split("\\|");
            banksMap.put(sa[0].trim(),sa[1].trim());
        }

        //动态生成dtatable column
        columns.add(new ColumnModel("机构号", "branchid"));
        List<ClsUdFldinfo> fldinfos = userDefRptService.selectColumnsDefine(rptno);
        for (ClsUdFldinfo fldinfo : fldinfos) {
            columns.add(new ColumnModel(fldinfo.getFldname(), "fld"+fldinfo.getFldsn()));
        }

        //设置UI表格宽度
        tableWidth = "width:" + 100 * fldinfos.size() + "px;";

        //表信息
        ClsUdTblinfo clsUdTblinfo = userDefRptService.selectTblInfo(rptno);
        title = clsUdTblinfo.getRptname();

        this.paramBean.setBranchId(branchid);
        this.paramBean.setRptno(rptno);
        this.paramBean.setPagesize(0);
        this.paramBean.setOffset(0);
        this.lazyDataModel = new pams.view.userdefrpt.UserDefRptLazyDataModel(userDefRptService.getUserDefRptMapper(), this.paramBean);

    }

    public String onQuery() {
        try {
            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("UserDefRptShow_onQuery");
            oplog.setActionName("阶段性攻坚报表:查询 " + title);
            oplog.setOpDataBranchid(this.paramBean.getBranchId());
            platformService.insertNewOperationLog(oplog);

            this.paramBean.setRptno(rptno);

            this.lazyDataModel = new pams.view.userdefrpt.UserDefRptLazyDataModel(userDefRptService.getUserDefRptMapper(), this.paramBean);
        } catch (Exception e) {
            logger.error("查询数据时出现错误。", e);
            MessageUtil.addWarn("查询数据时出现错误。" + e.getMessage());
        }
        return null;
    }

    //===================================================================

    public UserDefRptVO getParamBean() {
        return paramBean;
    }

    public void setParamBean(UserDefRptVO paramBean) {
        this.paramBean = paramBean;
    }

    public ClsUdRptdata getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(ClsUdRptdata selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public LazyDataModel<ClsUdRptdata> getLazyDataModel() {
        return lazyDataModel;
    }

    public void setLazyDataModel(LazyDataModel<ClsUdRptdata> lazyDataModel) {
        this.lazyDataModel = lazyDataModel;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
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

    public boolean isBizBranch() {
        return isBizBranch;
    }

    public void setBizBranch(boolean bizBranch) {
        isBizBranch = bizBranch;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public Map<String, String> getBanksMap() {
        return banksMap;
    }

    public void setBanksMap(Map<String, String> banksMap) {
        this.banksMap = banksMap;
    }

    public String getTableWidth() {
        return tableWidth;
    }

    public void setTableWidth(String tableWidth) {
        this.tableWidth = tableWidth;
    }
}
