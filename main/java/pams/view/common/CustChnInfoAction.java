package pams.view.common;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pub.platform.security.OperatorManager;
import skyline.service.PlatformService;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人客户渠道相关信息.
 * User: zhanrui
 * Date: 13-1-29
 * Time: 上午10:29
 */
@ManagedBean
@ViewScoped
public class CustChnInfoAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<SelectItem> branchList;

    private String branchId;
    private String operId;
    private String startDate;
    private String endDate;
    List<Map<String, Object>> detlList;

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

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        String certNo = request.getParameter("certNo");
        if (StringUtils.isEmpty(certNo)) {
            MessageUtil.addError("输入参数错误！");
            return;
        }

/*

        this.branchList = toolsService.selectBranchList(branchId);
        this.startDate = new DateTime().dayOfMonth().withMinimumValue().toString("yyyyMMdd");
        this.endDate = new DateTime().toString("yyyyMMdd");

*/
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("certNo", certNo);
        List<String> ectip_cust_no_list = odsbDataJdbc.queryForList("select ectip_cust_no from bf_pr_ind_info_ectip where cert_type = 'A' and cert_no = :certNo",
                paramMap, String.class);
        if (ectip_cust_no_list.isEmpty()) {
            detlList = new ArrayList<>();
        } else {
            String ectip_cust_no = ectip_cust_no_list.get(0);
            paramMap = new HashMap<>();
            paramMap.put("ectip_cust_no", ectip_cust_no);
            detlList = odsbDataJdbc.queryForList(assembleBaseInfoSql(), paramMap);
        }
    }


    private String assembleBaseInfoSql() {
        String sql = "select" +
                " CASE CHN_NO" +
                "   WHEN '01' THEN" +
                "    '网上银行'" +
                "   WHEN '02' THEN" +
                "    '手机银行'" +
                "   WHEN '03' THEN" +
                "    '电话银行'" +
                "   WHEN '05' THEN" +
                "    '短信'" +
                "   WHEN '07' THEN" +
                "    'CCC'" +
                "   WHEN '09' THEN" +
                "    '家居银行'" +
                "   WHEN '10' THEN" +
                "    '家加E'" +
                "   WHEN '11' THEN" +
                "    '网站'" +
                "   WHEN '21' THEN" +
                "    'E动终端'" +
                "   WHEN '89' THEN" +
                "    'ECTIP后台'" +
                "   WHEN '93' THEN" +
                "    'EAIB'" +
                "   WHEN '95' THEN" +
                "    'ECIF'" +
                "   WHEN '99' THEN" +
                "    'EAIH'" +
                "   ELSE" +
                "    '其他'" +
                " END 渠道," +
                " CASE FLT_CUST_GRD" +
                "   WHEN '1' then" +
                "    '普通'" +
                "   WHEN '2' THEN" +
                "    '高级'" +
                "   WHEN '3' THEN" +
                "    'VIP'" +
                "   ELSE" +
                "    '其它'" +
                " END 客户级别," +
                " SIGN_DT 开通签约日期," +
                " END_SIGN_DT 取消签约日期" +
                "  from bf_agt_chn_chnp_cust a" +
                " where ectip_cust_no = :ectip_cust_no" +
                " order by  CHN_NO";
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

}
