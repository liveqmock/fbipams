package pams.view.report;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pams.common.SystemService;
import pams.repository.model.Ptoplog;
import skyline.service.PlatformService;
import skyline.service.ToolsService;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账户金情况统计表.
 * User: zhanrui
 * Date: 13-1-29
 * Time: 上午10:29
 */
@ManagedBean
@ViewScoped
public class RptA04V1 implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<SelectItem> branchList;
    private boolean isBizBranch; //是否业务网点

    private String branchId;
    private String operId;
    private String startDate;
    private String endDate;
    private String fundType;
    List<Map<String, Object>> detlList;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{ccbssJdbc}")
    private NamedParameterJdbcTemplate ccbssJdbc;

    @PostConstruct
    public void init() {
        OperatorManager om = SystemService.getOperatorManager();
        branchId = om.getOperator().getDeptid();
        operId = om.getOperatorId();

        this.branchList = toolsService.selectBranchList(branchId);
        if (branchList.size() == 1) {
            isBizBranch = true;
        }
        this.startDate = new DateTime().dayOfMonth().withMinimumValue().toString("yyyyMMdd");
        this.endDate = new DateTime().toString("yyyyMMdd");
    }

    public void onQuery(){

        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("branchId", this.branchId);
        paramMap.put("startDate", this.startDate);
        paramMap.put("endDate", this.endDate);
        detlList = ccbssJdbc.queryForList(assembleSql(), paramMap);

        Ptoplog oplog = new Ptoplog();
        oplog.setActionId("RptA04V1_onQuery");
        oplog.setActionName("综合报表:账户金成交统计表");
        oplog.setOpDataBranchid(this.getBranchId());
        oplog.setOpDataStartdate(this.startDate);
        oplog.setOpDataEnddate(this.endDate);
        platformService.insertNewOperationLog(oplog);

    }


    private String assembleSql(){
        String sql = "select case BM_ST_TLR_ORG_DIM.INSTN_LEVEL" +
                "         when '4' then" +
                "          BM_ST_TLR_ORG_DIM.AG_SENIOR_NAME" +
                "         else" +
                "          BM_ST_TLR_ORG_DIM.INSTN_NAME" +
                "       end levelkey," +
                "       BM_ST_TLR_ORG_DIM.INSTN_LEVEL 机构级别," +
                "       BM_ST_TLR_ORG_DIM.ORG_CD 机构代码," +
                "       BM_ST_TLR_ORG_DIM.INSTN_NAME 机构名称key," +
                "       BM_SEC_CODE_DIM.SEC_CODE_NAME 证券名称," +
                "       BM_SEC_TRAD_TYPE_DIM.TRAD_TYPE2 交易类型代码key," +
                "       BM_SEC_TRAD_TYPE_DIM.TRAD_TYPE_NAME 交易类型," +
                "       sum(BM_ST_TT_RST_FT.AMT) 成交数量," +
                "       sum(BM_ST_TT_RST_FT.SUM_RST) 成交金额" +
                "  from BM_ST_TLR_ORG_DIM    BM_ST_TLR_ORG_DIM," +
                "       BM_SEC_CODE_DIM      BM_SEC_CODE_DIM," +
                "       BM_SEC_TRAD_TYPE_DIM BM_SEC_TRAD_TYPE_DIM," +
                "       BM_ST_TT_RST_FT      BM_ST_TT_RST_FT" +
                " where BM_ST_TT_RST_FT.M_DAY_DATE >= :startDate" +
                "   and BM_ST_TT_RST_FT.M_DAY_DATE <= :endDate" +
                "   and BM_ST_TLR_ORG_DIM.ORG_CD = :branchId" +
                "   and BM_SEC_TRAD_TYPE_DIM.TRAD_TYPE2 in ('1061', '1062')" +
                "   and BM_ST_TT_RST_FT.CHAN_TYPE in" +
                "       ('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')" +
                "   and BM_ST_TLR_ORG_DIM.INSTN_LEVEL = 4" +
                "   and BM_ST_TT_RST_FT.SEC_TYPE = '31'" +
                "   and BM_SEC_CODE_DIM.SEC_CODE in" +
                "       ('201019995', '201019999', '201020001', '201030001')" +
                "   and BM_ST_TLR_ORG_DIM.ORG_CD = BM_ST_TT_RST_FT.INST_CODE" +
                "   and BM_SEC_CODE_DIM.SEC_CODE = BM_ST_TT_RST_FT.SEC_CODE" +
                "   and BM_SEC_TRAD_TYPE_DIM.TRAD_TYPE2 = BM_ST_TT_RST_FT.TRAD_TYPE2" +
                " group by case BM_ST_TLR_ORG_DIM.INSTN_LEVEL" +
                "            when '4' then" +
                "             BM_ST_TLR_ORG_DIM.AG_SENIOR_NAME" +
                "            else" +
                "             BM_ST_TLR_ORG_DIM.INSTN_NAME" +
                "          end," +
                "          BM_ST_TLR_ORG_DIM.INSTN_LEVEL," +
                "          BM_ST_TLR_ORG_DIM.ORG_CD," +
                "          BM_ST_TLR_ORG_DIM.INSTN_NAME," +
                "          BM_SEC_CODE_DIM.SEC_CODE_NAME," +
                "          BM_SEC_TRAD_TYPE_DIM.TRAD_TYPE2," +
                "          BM_SEC_TRAD_TYPE_DIM.TRAD_TYPE_NAME" +
                " order by 机构级别        desc nulls last," +
                "          机构代码       asc nulls last," +
                "          证券名称        asc nulls last," +
                "          交易类型代码key asc nulls last";

        return sql;
    }
    //=============================================
    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
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

    public List<Map<String, Object>> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<Map<String, Object>> detlList) {
        this.detlList = detlList;
    }

    public NamedParameterJdbcTemplate getCcbssJdbc() {
        return ccbssJdbc;
    }

    public void setCcbssJdbc(NamedParameterJdbcTemplate ccbssJdbc) {
        this.ccbssJdbc = ccbssJdbc;
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

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public boolean isBizBranch() {
        return isBizBranch;
    }

    public void setBizBranch(boolean bizBranch) {
        isBizBranch = bizBranch;
    }
}
