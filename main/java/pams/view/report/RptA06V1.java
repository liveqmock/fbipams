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
 * 理财产品渠道销售客户明细表.
 * User: zhanrui
 * Date: 13-1-29
 * Time: 上午10:29
 */
@ManagedBean
@ViewScoped
public class RptA06V1 implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<SelectItem> branchList;
    private boolean isBizBranch; //是否业务网点

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
        oplog.setActionName("综合报表:理财产品渠道销售客户明细表");
        oplog.setOpDataBranchid(this.getBranchId());
        oplog.setOpDataStartdate(this.startDate);
        oplog.setOpDataEnddate(this.endDate);
        platformService.insertNewOperationLog(oplog);
    }


    private String assembleSql(){
        String sql = "SELECT A.TRAD_BRAN 交易机构," +
                "       (select CM_OPUN_FLNM_CHN" +
                "          from BF_PR_BR_INSTN q" +
                "         where q.opr_unit_cd = A.TRAD_BRAN) 机构名称," +
                "       A.CR_TX_DT 交易日期," +
                "       A.TRANS_ACCT_NO 交易账号," +
                "       C.CUST_NAME 客户名称," +
                "       B.LN_PROD_COD 产品编号," +
                "       B.PRODUCT_NAME 产品名称," +
                "       B.INVEST_START_DATE 投资起始日," +
                "       B.INVEST_END_DATE 投资终止日," +
                "       A.CFM_AMT_BKNT 确认金额钞," +
                "       CASE" +
                "         WHEN A.TRAD_CHANEL = '1000990010' THEN" +
                "          '传统柜面'" +
                "         WHEN A.TRAD_CHANEL = '1000990030' THEN" +
                "          '低柜客户经理'" +
                "         WHEN A.TRAD_CHANEL = '1000990031' THEN" +
                "          '理财中心'" +
                "         WHEN A.TRAD_CHANEL = '1000990032' THEN" +
                "          '财富中心'" +
                "         WHEN A.TRAD_CHANEL = '1000990041' THEN" +
                "          '呼叫中心'" +
                "         WHEN A.TRAD_CHANEL = '1000990042' THEN" +
                "          '呼叫中心'" +
                "         WHEN A.TRAD_CHANEL = '1000990050' THEN" +
                "          '网上银行'" +
                "         WHEN A.TRAD_CHANEL = '1000990060' THEN" +
                "          '分行企业银行'" +
                "         WHEN A.TRAD_CHANEL = '1000990070' THEN" +
                "          '重要客户服务系统'" +
                "         WHEN A.TRAD_CHANEL = '1000990080' THEN" +
                "          '手机银行'" +
                "         WHEN A.TRAD_CHANEL = '1000990090' THEN" +
                "          '家居银行'" +
                "         WHEN A.TRAD_CHANEL = '1000990100' THEN" +
                "          'POS'" +
                "         WHEN A.TRAD_CHANEL = '1000990110' THEN" +
                "          '自助终端'" +
                "         WHEN A.TRAD_CHANEL = '1000990120' THEN" +
                "          '存款机'" +
                "         WHEN A.TRAD_CHANEL = '1000990130' THEN" +
                "          'ATM取款机'" +
                "         WHEN A.TRAD_CHANEL = '1000990140' THEN" +
                "          '存取款一体机'" +
                "         WHEN A.TRAD_CHANEL = '1000990150' THEN" +
                "          '存折补登机'" +
                "         WHEN A.TRAD_CHANEL = '1000990160' THEN" +
                "          '夜间金库'" +
                "         WHEN A.TRAD_CHANEL = '1000990170' THEN" +
                "          '外币兑换机'" +
                "         WHEN A.TRAD_CHANEL = '1000990200' THEN" +
                "          '电视渠道'" +
                "         WHEN A.TRAD_CHANEL = '1000990210' THEN" +
                "          '电台渠道'" +
                "         WHEN A.TRAD_CHANEL = '1000990999' THEN" +
                "          '其它'" +
                "         ELSE" +
                "          '其他'" +
                "       END 交易渠道," +
                "       " +
                "       CASE" +
                "         WHEN A.PRODDEALTYPE = '1' THEN" +
                "          '认购'" +
                "         WHEN A.PRODDEALTYPE = '2' THEN" +
                "          '申购'" +
                "         WHEN A.PRODDEALTYPE = '3' THEN" +
                "          '赎回'" +
                "         WHEN A.PRODDEALTYPE = '4' THEN" +
                "          '终止'" +
                "         WHEN A.PRODDEALTYPE = '5' THEN" +
                "          '收益发放'" +
                "         WHEN A.PRODDEALTYPE = '6' THEN" +
                "          '非交易过户-过出'" +
                "         WHEN A.PRODDEALTYPE = '7' THEN" +
                "          'IPO转投转出'" +
                "         WHEN A.PRODDEALTYPE = '8' THEN" +
                "          'IPO返款'" +
                "         WHEN A.PRODDEALTYPE = '9' THEN" +
                "          '红利再投'" +
                "         WHEN A.PRODDEALTYPE = 'A' THEN" +
                "          'IPO扣划'" +
                "         WHEN A.PRODDEALTYPE = 'B' THEN" +
                "          'IPO转投转入'" +
                "         WHEN A.PRODDEALTYPE = 'F' THEN" +
                "          '非交易过户-过入'" +
                "         WHEN A.PRODDEALTYPE = 'E' THEN" +
                "          '赎回收益'" +
                "         WHEN A.PRODDEALTYPE = 'G' THEN" +
                "          '自动理财'" +
                "         ELSE" +
                "          '其他'" +
                "       END 处理类型," +
                "       C.CERT_NO 证件号码" +
                "  FROM BF_EVT_NIN_FS_CONCLUDE_LIST A," +
                "       BF_PRD_NIN_FS_PROD_INFO     B," +
                "       BF_AGT_NIN_FS_TA_ACCOUNT    C" +
                " WHERE A.PRODUCT_ID = B.LN_PROD_COD" +
                "   AND A.CR_TX_DT>= :startDate" +
                "   AND A.TRAD_BRAN= :branchId" +
                "   AND A.CUST_TA_ID = C.CUST_TA_ID(+)  order by 交易日期" ;
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
