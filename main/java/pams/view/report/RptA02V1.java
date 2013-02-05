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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基金业务委托情况统计表.
 * User: zhanrui
 * Date: 13-1-29
 * Time: 上午10:29
 */
@ManagedBean
@ViewScoped
public class RptA02V1 implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<SelectItem> branchList;

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
        this.startDate = new DateTime().dayOfMonth().withMinimumValue().toString("yyyyMMdd");
        this.endDate = new DateTime().toString("yyyyMMdd");
    }

    public void onQuery(){

        Map<String,Object> paramMap = new HashMap<>();

        String[] fundTypes = fundType.split(" ");
        paramMap.put("fundType", Arrays.asList(fundTypes));
        paramMap.put("branchId", this.branchId);
        paramMap.put("startDate", this.startDate);
        paramMap.put("endDate", this.endDate);
        detlList = ccbssJdbc.queryForList(assembleSql(), paramMap);

        Ptoplog oplog = new Ptoplog();
        oplog.setActionId("RptA02V1_onQuery");
        oplog.setActionName("综合报表:基金业务委托情况表");
        oplog.setOpDataBranchid(this.getBranchId());
        oplog.setOpDataStartdate(this.startDate);
        oplog.setOpDataEnddate(this.endDate);
        platformService.insertNewOperationLog(oplog);
    }


    private String assembleSql(){
        String sql = "SELECT T0.C0 机构代码," +
                "       T0.C1 机构名称," +
                "       T0.C12 证券代码," +
                "       (select sec_code_name from BM_SEC_CODE_DIM where sec_code = T0.C12) 证券名称 ," +
                "       T0.C2 认购金额," +
                "       T0.C3 认购数量," +
                "       T0.C4 申购金额," +
                "       T0.C5 申购数量," +
                "       T0.C6 赎回金额," +
                "       T0.C7 赎回数量," +
                "       T0.C8 转换金额," +
                "       T0.C9 转换数量," +
                "       T0.C10 定投金额," +
                "       T0.C11 定投数量," +
                "       SUM(T0.C2) OVER() 基金认购金额13," +
                "       SUM(T0.C3) OVER() 基金认购数量14," +
                "       SUM(T0.C4) OVER() 基金申购金额15," +
                "       SUM(T0.C5) OVER() 基金申购数量16," +
                "       SUM(T0.C6) OVER() 基金赎回份额17," +
                "       SUM(T0.C7) OVER() 基金赎回数量18," +
                "       SUM(T0.C8) OVER() 基金转换份额19," +
                "       SUM(T0.C9) OVER() 基金转换数量20," +
                "       SUM(T0.C10) OVER() 基金定投金额21," +
                "       SUM(T0.C11) OVER() 基金定投数量22" +
                "  FROM (SELECT BM_ST_TLR_ORG_DIM.ORG_CD C0," +
                "               BM_ST_TLR_ORG_DIM.INSTN_NAME C1," +
                "               BM_ST_TREQUEST_FT.SEC_CODE C12," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TREQUEST_FT.FUND_TYPE = '01' THEN" +
                "                      BM_ST_TREQUEST_FT.APP_SUM -" +
                "                      BM_ST_TREQUEST_FT.TA_FAIL_AMT" +
                "                   END) C2," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TREQUEST_FT.FUND_TYPE = '01' THEN" +
                "                      BM_ST_TREQUEST_FT.METRIC_COT -" +
                "                      BM_ST_TREQUEST_FT.TA_FAIL_CNT" +
                "                   END) C3," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TREQUEST_FT.FUND_TYPE = '02' THEN" +
                "                      BM_ST_TREQUEST_FT.APP_SUM -" +
                "                      BM_ST_TREQUEST_FT.TA_FAIL_AMT" +
                "                   END) C4," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TREQUEST_FT.FUND_TYPE = '02' THEN" +
                "                      BM_ST_TREQUEST_FT.METRIC_COT -" +
                "                      BM_ST_TREQUEST_FT.TA_FAIL_CNT" +
                "                   END) C5," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TREQUEST_FT.FUND_TYPE = '03' THEN" +
                "                      BM_ST_TREQUEST_FT.SHARES -" +
                "                      BM_ST_TREQUEST_FT.TA_FAIL_SHARES" +
                "                   END) C6," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TREQUEST_FT.FUND_TYPE = '03' THEN" +
                "                      BM_ST_TREQUEST_FT.METRIC_COT -" +
                "                      BM_ST_TREQUEST_FT.TA_FAIL_CNT" +
                "                   END) C7," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TREQUEST_FT.FUND_TYPE = '13' THEN" +
                "                      BM_ST_TREQUEST_FT.SHARES -" +
                "                      BM_ST_TREQUEST_FT.TA_FAIL_SHARES" +
                "                   END) C8," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TREQUEST_FT.FUND_TYPE = '13' THEN" +
                "                      BM_ST_TREQUEST_FT.METRIC_COT -" +
                "                      BM_ST_TREQUEST_FT.TA_FAIL_CNT" +
                "                   END) C9," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TREQUEST_FT.FUND_TYPE = '21' THEN" +
                "                      BM_ST_TREQUEST_FT.APP_SUM -" +
                "                      BM_ST_TREQUEST_FT.TA_FAIL_AMT" +
                "                   END) C10," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TREQUEST_FT.FUND_TYPE = '21' THEN" +
                "                      BM_ST_TREQUEST_FT.METRIC_COT -" +
                "                      BM_ST_TREQUEST_FT.TA_FAIL_CNT" +
                "                   END) C11" +
                "          FROM (SELECT * FROM BM_ST_TLR_ORG_DIM WHERE INSTN_LEVEL <> '5') BM_ST_TLR_ORG_DIM," +
                "               BM_ST_TREQUEST_FT BM_ST_TREQUEST_FT" +
                "         WHERE BM_ST_TREQUEST_FT.FUND_TYPE IN" +
                "               ('01', '02', '03', '13', '21')" +
                "           AND BM_ST_TLR_ORG_DIM.INSTN_LEVEL = '4'" +
                "           AND BM_ST_TLR_ORG_DIM.INST_CODE=:branchId" +

                "           AND BM_ST_TREQUEST_FT.SEC_CODE IN" +
                "               (select b.Sec_Code" +
                "                  from BF_PRD_ST_TFUND_CD_INFO a, BM_SEC_CODE_DIM b" +
                "                 where a.FUND_CD = b.ORI_SEC_CODE" +
                "                   and a.MK_CODE = b.MKT_ID" +
                "                   and a.FUND_CD in (:fundType))" +

                "           AND BM_ST_TREQUEST_FT.M_DAY_DATE >=:startDate" +
                "           AND BM_ST_TREQUEST_FT.M_DAY_DATE <=:endDate" +
                "           AND BM_ST_TLR_ORG_DIM.ORG_CD =" +
                "               BM_ST_TREQUEST_FT.INST_CODE" +
                "         GROUP BY BM_ST_TLR_ORG_DIM.ORG_CD," +
                "                  BM_ST_TLR_ORG_DIM.INSTN_NAME," +
                "                  BM_ST_TREQUEST_FT.SEC_CODE) T0" +
                " ORDER BY 机构代码 ASC NULLS LAST";

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
}
