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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �ͻ�֤ȯ�ֲּ�Ӯ�������ϸ��.
 * User: zhanrui
 * Date: 13-1-29
 * Time: ����10:29
 */
@ManagedBean
@ViewScoped
public class RptA03V1 implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<SelectItem> branchList;

    private String branchId;
    private String operId;
    private String startDate;
    private String endDate;
    private String fundType;
    private BigDecimal startAmt = new BigDecimal("0.00");
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

        paramMap.put("branchId", this.branchId);
        paramMap.put("startAmt", this.startAmt);
        //paramMap.put("startDate", "20130101");    //ODSB����20130101֮ǰ������
        paramMap.put("endDate", this.endDate);
        detlList = ccbssJdbc.queryForList(assembleSql(), paramMap);

        Ptoplog oplog = new Ptoplog();
        oplog.setActionId("RptA03V1_onQuery");
        oplog.setActionName("�ۺϱ���:�ͻ�֤ȯ�ֲּ�Ӯ�������ϸ��");
        oplog.setOpDataBranchid(this.getBranchId());
        oplog.setOpDataStartdate(this.startDate);
        oplog.setOpDataEnddate(this.endDate);
        platformService.insertNewOperationLog(oplog);
    }


    private String assembleSql(){
        String sql = "SELECT BM_ST_CUST_PC_SECU_FT.M_CHILDNETNO    ������," +
                "       BM_ST_CUST_PC_SECU_FT.M_CUST_NAME     custName," +
                "       BM_ST_CUST_PC_SECU_FT.M_CERTI_CODE    ֤������," +
                "       BM_SEC_TYPE_DIM.SEC_TYPE_NAME         ��������," +
                "       BM_ST_CUST_PC_SECU_FT.M_TACODE        �г�����," +
                "       BM_ST_CUST_PC_SECU_FT.M_SECUR_CODE    ֤ȯ����," +
                "       BM_SEC_CODE_DIM.SEC_CODE_NAME         ֤ȯ����," +
                "       BM_ST_CUST_PC_SECU_FT.M_SECUR_SUM     ֤ȯ���," +
                "       BM_ST_CUST_PC_SECU_FT.F_LAST_NETVALUE �ο��۸�," +
                "       BM_ST_CUST_PC_SECU_FT.M_CURR_SEC_BVL  ��ֵ," +
                "       BM_ST_CUST_PC_SECU_FT.PROFIT_AMT      ӯ��" +
                "  FROM BM_ST_CUST_PC_SECU_FT BM_ST_CUST_PC_SECU_FT," +
                "       BM_SEC_TYPE_DIM       BM_SEC_TYPE_DIM," +
                "       BM_SEC_CODE_DIM       BM_SEC_CODE_DIM" +
                " WHERE BM_ST_CUST_PC_SECU_FT.M_BOUNDTYPE in ('31','21','41','22','11')" +
                "   AND  BM_ST_CUST_PC_SECU_FT.M_CHILDNETNO =:branchId" +
                "   AND  BM_ST_CUST_PC_SECU_FT.M_PAPERDATE =:endDate" +
                "   AND BM_ST_CUST_PC_SECU_FT.M_SECUR_SUM >=:startAmt " +
                "   AND BM_ST_CUST_PC_SECU_FT.M_BOUNDTYPE = BM_SEC_TYPE_DIM.SEC_TYPE" +
                "   AND BM_ST_CUST_PC_SECU_FT.M_SECUR_CODE =" +
                "       BM_SEC_CODE_DIM.ORI_SEC_CODE" +
                "   AND BM_ST_CUST_PC_SECU_FT.M_TACODE = BM_SEC_CODE_DIM.MKT_ID" +
                " ORDER BY ֤ȯ���  NULLS LAST";

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

    public BigDecimal getStartAmt() {
        return startAmt;
    }

    public void setStartAmt(BigDecimal startAmt) {
        this.startAmt = startAmt;
    }
}
