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
 * ����ҵ��ί�����ͳ�Ʊ�.
 * User: zhanrui
 * Date: 13-1-29
 * Time: ����10:29
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
        oplog.setActionName("�ۺϱ���:����ҵ��ί�������");
        oplog.setOpDataBranchid(this.getBranchId());
        oplog.setOpDataStartdate(this.startDate);
        oplog.setOpDataEnddate(this.endDate);
        platformService.insertNewOperationLog(oplog);
    }


    private String assembleSql(){
        String sql = "SELECT T0.C0 ��������," +
                "       T0.C1 ��������," +
                "       T0.C12 ֤ȯ����," +
                "       (select sec_code_name from BM_SEC_CODE_DIM where sec_code = T0.C12) ֤ȯ���� ," +
                "       T0.C2 �Ϲ����," +
                "       T0.C3 �Ϲ�����," +
                "       T0.C4 �깺���," +
                "       T0.C5 �깺����," +
                "       T0.C6 ��ؽ��," +
                "       T0.C7 �������," +
                "       T0.C8 ת�����," +
                "       T0.C9 ת������," +
                "       T0.C10 ��Ͷ���," +
                "       T0.C11 ��Ͷ����," +
                "       SUM(T0.C2) OVER() �����Ϲ����13," +
                "       SUM(T0.C3) OVER() �����Ϲ�����14," +
                "       SUM(T0.C4) OVER() �����깺���15," +
                "       SUM(T0.C5) OVER() �����깺����16," +
                "       SUM(T0.C6) OVER() ������طݶ�17," +
                "       SUM(T0.C7) OVER() �����������18," +
                "       SUM(T0.C8) OVER() ����ת���ݶ�19," +
                "       SUM(T0.C9) OVER() ����ת������20," +
                "       SUM(T0.C10) OVER() ����Ͷ���21," +
                "       SUM(T0.C11) OVER() ����Ͷ����22" +
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
                " ORDER BY �������� ASC NULLS LAST";

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
