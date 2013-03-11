package pams.view.report;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
 * ��Ʋ�Ʒ�������ۿͻ���ϸ��.
 * User: zhanrui
 * Date: 13-1-29
 * Time: ����10:29
 */
@ManagedBean
@ViewScoped
public class RptA06V1 implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<SelectItem> branchList;
    private boolean isBizBranch; //�Ƿ�ҵ������

    private String branchId;
    private String operId;
    private String custName;
    private String startDate;
    private String endDate;
    private String fundType;
//    List<Map<String, Object>> detlList;
    List<RptA06V1ResultBean> detlList;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{odsbDataJdbc}")
    private NamedParameterJdbcTemplate odsbDataJdbc;

    @PostConstruct
    public void init() {
        OperatorManager om = SystemService.getOperatorManager();
        branchId = om.getOperator().getDeptid();
        operId = om.getOperatorId();

        this.branchList = toolsService.selectBranchList(branchId);
        if (branchList.size() == 1) {
            isBizBranch = true;
        }
        this.startDate = new DateTime().monthOfYear().withMinimumValue().dayOfMonth().withMinimumValue().toString("yyyy-MM-dd");
    }

    public void onQuery(){

        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("branchId", this.branchId);
        paramMap.put("startDate", this.startDate);
//        detlList = odsbDataJdbc.queryForList(assembleSql(), paramMap);
        detlList = odsbDataJdbc.query(assembleSql(), paramMap, new BeanPropertyRowMapper<RptA06V1ResultBean>(RptA06V1ResultBean.class));

        Ptoplog oplog = new Ptoplog();
        oplog.setActionId("RptA06V1_onQuery");
        oplog.setActionName("�ۺϱ���:��Ʋ�Ʒ�������ۿͻ���ϸ��");
        oplog.setOpDataBranchid(this.getBranchId());
        oplog.setOpDataStartdate(this.startDate);
        oplog.setOpDataEnddate(this.endDate);
        platformService.insertNewOperationLog(oplog);
    }


    private String assembleSql(){
        String sql = "SELECT A.TRAD_BRAN ���׻���," +
                "       (select CM_OPUN_FLNM_CHN" +
                "          from BF_PR_BR_INSTN q" +
                "         where q.opr_unit_cd = A.TRAD_BRAN) ��������," +
                "       A.CR_TX_DT ��������," +
                "       A.TRANS_ACCT_NO �����˺�," +
                "       C.CUST_NAME �ͻ�����," +
                "       B.LN_PROD_COD ��Ʒ���," +
                "       B.PRODUCT_NAME ��Ʒ����," +
                "       B.INVEST_START_DATE Ͷ����ʼ��," +
                "       B.INVEST_END_DATE Ͷ����ֹ��," +
                "       A.CFM_AMT_BKNT ȷ�Ͻ�," +
                "       CASE" +
                "         WHEN A.TRAD_CHANEL = '1000990010' THEN" +
                "          '��ͳ����'" +
                "         WHEN A.TRAD_CHANEL = '1000990030' THEN" +
                "          '�͹�ͻ�����'" +
                "         WHEN A.TRAD_CHANEL = '1000990031' THEN" +
                "          '�������'" +
                "         WHEN A.TRAD_CHANEL = '1000990032' THEN" +
                "          '�Ƹ�����'" +
                "         WHEN A.TRAD_CHANEL = '1000990041' THEN" +
                "          '��������'" +
                "         WHEN A.TRAD_CHANEL = '1000990042' THEN" +
                "          '��������'" +
                "         WHEN A.TRAD_CHANEL = '1000990050' THEN" +
                "          '��������'" +
                "         WHEN A.TRAD_CHANEL = '1000990060' THEN" +
                "          '������ҵ����'" +
                "         WHEN A.TRAD_CHANEL = '1000990070' THEN" +
                "          '��Ҫ�ͻ�����ϵͳ'" +
                "         WHEN A.TRAD_CHANEL = '1000990080' THEN" +
                "          '�ֻ�����'" +
                "         WHEN A.TRAD_CHANEL = '1000990090' THEN" +
                "          '�Ҿ�����'" +
                "         WHEN A.TRAD_CHANEL = '1000990100' THEN" +
                "          'POS'" +
                "         WHEN A.TRAD_CHANEL = '1000990110' THEN" +
                "          '�����ն�'" +
                "         WHEN A.TRAD_CHANEL = '1000990120' THEN" +
                "          '����'" +
                "         WHEN A.TRAD_CHANEL = '1000990130' THEN" +
                "          'ATMȡ���'" +
                "         WHEN A.TRAD_CHANEL = '1000990140' THEN" +
                "          '��ȡ��һ���'" +
                "         WHEN A.TRAD_CHANEL = '1000990150' THEN" +
                "          '���۲��ǻ�'" +
                "         WHEN A.TRAD_CHANEL = '1000990160' THEN" +
                "          'ҹ����'" +
                "         WHEN A.TRAD_CHANEL = '1000990170' THEN" +
                "          '��Ҷһ���'" +
                "         WHEN A.TRAD_CHANEL = '1000990200' THEN" +
                "          '��������'" +
                "         WHEN A.TRAD_CHANEL = '1000990210' THEN" +
                "          '��̨����'" +
                "         WHEN A.TRAD_CHANEL = '1000990999' THEN" +
                "          '����'" +
                "         ELSE" +
                "          '����'" +
                "       END ��������," +
                "       " +
                "       CASE" +
                "         WHEN A.PRODDEALTYPE = '1' THEN" +
                "          '�Ϲ�'" +
                "         WHEN A.PRODDEALTYPE = '2' THEN" +
                "          '�깺'" +
                "         WHEN A.PRODDEALTYPE = '3' THEN" +
                "          '���'" +
                "         WHEN A.PRODDEALTYPE = '4' THEN" +
                "          '��ֹ'" +
                "         WHEN A.PRODDEALTYPE = '5' THEN" +
                "          '���淢��'" +
                "         WHEN A.PRODDEALTYPE = '6' THEN" +
                "          '�ǽ��׹���-����'" +
                "         WHEN A.PRODDEALTYPE = '7' THEN" +
                "          'IPOתͶת��'" +
                "         WHEN A.PRODDEALTYPE = '8' THEN" +
                "          'IPO����'" +
                "         WHEN A.PRODDEALTYPE = '9' THEN" +
                "          '������Ͷ'" +
                "         WHEN A.PRODDEALTYPE = 'A' THEN" +
                "          'IPO�ۻ�'" +
                "         WHEN A.PRODDEALTYPE = 'B' THEN" +
                "          'IPOתͶת��'" +
                "         WHEN A.PRODDEALTYPE = 'F' THEN" +
                "          '�ǽ��׹���-����'" +
                "         WHEN A.PRODDEALTYPE = 'E' THEN" +
                "          '�������'" +
                "         WHEN A.PRODDEALTYPE = 'G' THEN" +
                "          '�Զ����'" +
                "         ELSE" +
                "          '����'" +
                "       END ��������," +
                "       C.CERT_NO ֤������" +
                "  FROM BF_EVT_NIN_FS_CONCLUDE_LIST A," +
                "       BF_PRD_NIN_FS_PROD_INFO     B," +
                "       BF_AGT_NIN_FS_TA_ACCOUNT    C" +
                " WHERE A.PRODUCT_ID = B.LN_PROD_COD" +
                "   AND A.CR_TX_DT>= :startDate" +
                "   AND A.TRAD_BRAN= :branchId" +
                "   AND A.CUST_TA_ID = C.CUST_TA_ID(+)  order by ��������" ;
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

/*
    public List<Map<String, Object>> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<Map<String, Object>> detlList) {
        this.detlList = detlList;
    }
*/

    public List<RptA06V1ResultBean> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<RptA06V1ResultBean> detlList) {
        this.detlList = detlList;
    }

    public NamedParameterJdbcTemplate getOdsbDataJdbc() {
        return odsbDataJdbc;
    }

    public void setOdsbDataJdbc(NamedParameterJdbcTemplate odsbDataJdbc) {
        this.odsbDataJdbc = odsbDataJdbc;
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

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public boolean isBizBranch() {
        return isBizBranch;
    }

    public void setBizBranch(boolean bizBranch) {
        isBizBranch = bizBranch;
    }
}
