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
 * ����ҵ��ɽ�ȷ�����ͳ�Ʊ�.
 * User: zhanrui
 * Date: 13-1-29
 * Time: ����10:29
 */
@ManagedBean
@ViewScoped
public class RptA01V1 implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<SelectItem> branchList;
    private boolean isBizBranch; //�Ƿ�ҵ������

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

        String[] fundTypes = fundType.split(" ");
        paramMap.put("fundType", Arrays.asList(fundTypes));
        paramMap.put("branchId", this.branchId);
        paramMap.put("startDate", this.startDate);
        paramMap.put("endDate", this.endDate);
        detlList = ccbssJdbc.queryForList(assembleSql(), paramMap);

        Ptoplog oplog = new Ptoplog();
        oplog.setActionId("RptA01V1_onQuery");
        oplog.setActionName("�ۺϱ���:����ҵ��ɽ�ȷ�������");
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
                "       T0.C2 �Ϲ�����," +
                "       T0.C3 �Ϲ����," +
                "       T0.C4 �깺����," +
                "       T0.C5 �깺���," +
                "       T0.C6 �������," +
                "       T0.C7 ��ؽ��," +
                "       T0.C8 ת������," +
                "       T0.C9 ת�����," +
                "       T0.C10 ��Ͷ����," +
                "       T0.C11 ��Ͷ���," +
                "       SUM(T0.C2) OVER() �����Ϲ����13," +
                "       SUM(T0.C3) OVER() �����Ϲ�����14," +
                "       SUM(T0.C4) OVER() �����깺���15," +
                "       SUM(T0.C5) OVER() �����깺����16," +
                "       SUM(T0.C6) OVER() �������ȷ�Ͻ��17," +
                "       SUM(T0.C7) OVER() �����������18," +
                "       SUM(T0.C8) OVER() ����ת���������19," +
                "       SUM(T0.C9) OVER() ����ת������20," +
                "       SUM(T0.C10) OVER() ����Ͷ���21," +
                "       SUM(T0.C11) OVER() ����Ͷ����22" +
                "  FROM (SELECT BM_SEC_PAR_INST_DIM4.INST_CODE C0," +
                "               BM_SEC_PAR_INST_DIM4.INST_NAME C1," +
                "               BM_ST_TT_RST_FT.SEC_CODE C12," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TT_RST_FT.TRAD_TYPE2 = '100' THEN" +
                "                      BM_ST_TT_RST_FT.SUM_RST" +
                "                   END) C2," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TT_RST_FT.TRAD_TYPE2 = '100' THEN" +
                "                      BM_ST_TT_RST_FT.AMT" +
                "                   END) C3," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TT_RST_FT.TRAD_TYPE2 = '101' THEN" +
                "                      BM_ST_TT_RST_FT.SUM_RST" +
                "                   END) C4," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TT_RST_FT.TRAD_TYPE2 = '101' THEN" +
                "                      BM_ST_TT_RST_FT.AMT" +
                "                   END) C5," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TT_RST_FT.TRAD_TYPE2 = '102' THEN" +
                "                      BM_ST_TT_RST_FT.SUM_RST" +
                "                   END) C6," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TT_RST_FT.TRAD_TYPE2 = '102' THEN" +
                "                      BM_ST_TT_RST_FT.AMT" +
                "                   END) C7," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TT_RST_FT.TRAD_TYPE2 = '103' THEN" +
                "                      BM_ST_TT_RST_FT.SUM_RST" +
                "                   END) C8," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TT_RST_FT.TRAD_TYPE2 = '103' THEN" +
                "                      BM_ST_TT_RST_FT.AMT" +
                "                   END) C9," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TT_RST_FT.TRAD_TYPE2 = '125' THEN" +
                "                      BM_ST_TT_RST_FT.SUM_RST" +
                "                   END) C10," +
                "               SUM(CASE" +
                "                     WHEN BM_ST_TT_RST_FT.TRAD_TYPE2 = '125' THEN" +
                "                      BM_ST_TT_RST_FT.AMT" +
                "                   END) C11" +
                "          FROM (SELECT A.ORG_CD AS INST_CODE," +
                "                       A.INSTN_NAME AS INST_NAME," +
                "                       A.INSTN_LEVEL AS INST_LEVEL," +
                "                       A.AG_SENIOR_NODE AS SEC_INST_NO," +
                "                       A.AG_SENIOR_NAME AS SEC_INST_NAME," +
                "                       (SELECT B.INSTN_LEVEL" +
                "                          FROM BM_ST_TLR_ORG_DIM B" +
                "                         WHERE A.AG_SENIOR_NODE = B.ORG_CD) AS SEC_INST_LEVEL" +
                "                  FROM BM_ST_TLR_ORG_DIM A" +
                "                 WHERE A.INSTN_LEVEL <> '5') BM_SEC_PAR_INST_DIM4," +
                "               BM_ST_TT_RST_FT BM_ST_TT_RST_FT" +
                "         WHERE BM_ST_TT_RST_FT.TRAD_TYPE2 IN" +
                "               ('100', '101', '102', '103', '125')" +
                "           AND BM_SEC_PAR_INST_DIM4.INST_LEVEL = '4'" +
                "           AND BM_SEC_PAR_INST_DIM4.INST_CODE = :branchId" +

                "           AND BM_ST_TT_RST_FT.SEC_CODE IN" +
                "               (select b.Sec_Code" +
                "                  from BF_PRD_ST_TFUND_CD_INFO a, BM_SEC_CODE_DIM b" +
                "                 where a.FUND_CD = b.ORI_SEC_CODE" +
                "                   and a.MK_CODE = b.MKT_ID" +
                "                   and a.FUND_CD in (:fundType))" +

                "           AND BM_ST_TT_RST_FT.M_DAY_DATE >=:startDate" +
                "           AND BM_ST_TT_RST_FT.M_DAY_DATE <=:endDate" +
                "           AND BM_SEC_PAR_INST_DIM4.INST_CODE =" +
                "               BM_ST_TT_RST_FT.INST_CODE" +
                "         GROUP BY BM_SEC_PAR_INST_DIM4.INST_CODE," +
                "                  BM_SEC_PAR_INST_DIM4.INST_NAME," +
                "                  BM_ST_TT_RST_FT.SEC_CODE ) T0" +
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

    public boolean isBizBranch() {
        return isBizBranch;
    }

    public void setBizBranch(boolean bizBranch) {
        isBizBranch = bizBranch;
    }
}
