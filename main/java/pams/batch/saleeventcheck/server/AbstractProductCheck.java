package pams.batch.saleeventcheck.server;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import pams.batch.saleeventcheck.server.checkpoint.SepCheckPointRequest;
import pams.batch.saleeventcheck.server.checkpoint.SepCheckPointResponse;
import pams.repository.model.Svpssaleprdckpt;
import pams.repository.model.prdset.PsSalesPrdInfoBean;
import pams.service.prdset.PsBatchCheckService;
import skyline.service.PlatformService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: zhanrui
 * Date: 20130728
 */
@Deprecated
public abstract class AbstractProductCheck{
    private static final Logger logger = LoggerFactory.getLogger(AbstractProductCheck.class);

    private static final String SQL_CHECK_ODSBSTATUS = "select TO_CHAR(biz_date, 'yyyymmdd') as biz_date,jobflow_status from odssys.f_jci_jobflowinstance where job_flow_id ='990063719900001'";

    @Resource
    protected SimpleJdbcTemplate simpleJdbcTemplate;
    @Resource
    protected PlatformService platformService;

    //是否强制重新检核
    private boolean forceCheck;
    //证件类型
    protected String certtype;
    //证件号码
    protected String certno;



    @Resource
    protected PsBatchCheckService psBatchCheckService;



    @Resource
    protected CheckRulesService checkRulesService;

    abstract public void startBatchCheck(boolean forceCheck);
    abstract public void startBatchCheck(String prdid, boolean forceCheck);

    abstract public void startSingleCheck();

    protected List<String> needCheckPrdidList = new ArrayList<String>();

    public AbstractProductCheck(){
    }
    protected void initNeedCheckProductList(){
/*
        List<String> products = new ArrayList<String>();
        products.add("14");
        products.add("15");
        products.add("17");
        products.add("06");
        products.add("07");
        return products;
*/
        this.needCheckPrdidList = checkRulesService.selectAllNeedCheckPrdid();
    }

    public final void startcheck(String prdid, boolean forceCheck) {
        String odsbstatus = null;
        try {
            odsbstatus = getOdsbSysStatus();
        } catch (Exception e) {
            //TODO
            throw new RuntimeException("ODSB系统链接错误。", e);
        }
        logger.info("ODSB STATUS=" + odsbstatus);

        String odsbstatusConfig = null;

        odsbstatusConfig = getSystemParamForOdsbCheckConfig();

        if (!odsbstatusConfig.equals(odsbstatus)) {
            logger.info("ODSB系统未就绪。");
            throw new RuntimeException("ODSB系统未就绪。");
        }

        logger.info("指标检核开始...产品ID = " + prdid);

        this.forceCheck = forceCheck;

        beforeProcess();
        //process(prdid);
        postProcess();
        logger.info("指标检核完成。");
    }

    abstract protected void process(SepCheckPointRequest request, SepCheckPointResponse response);

    protected void beforeProcess() {
    }

    protected void postProcess() {
    }

    protected List<PsSalesPrdInfoBean> selectNeedCheckPrdinfoList(String prdid, String ckptprogguid) {
        //TODO 已检核通过的根据参数强制重新检核
        //TODO  加上时间（年限）限制 防止记录数过多
        if (forceCheck) {
            return psBatchCheckService.selectAllPrdinfoListForForceCheck(prdid, ckptprogguid);
        } else {
            return psBatchCheckService.selectNeedCheckPrdinfoList(prdid, ckptprogguid);
        }
    }

    /**
     * 公用方法：根据证件信息查询ODSB客户ID
     * 此方法自ECIF系统的个人客户基本信息表（BF_PR_IND_INFO）中获取数据
     *
     * @return
     */
    protected String selectCustnoFromODSB() {
        try {
            Map map = simpleJdbcTemplate.queryForMap(
                    "select p_cust_no from bf_pr_ind_info where cert_type = ? and cert_no = ? "
                    , certtype, certno);
            return (String) map.get("p_cust_no");
        } catch (DataAccessException e) {
            return null;
        }
    }

    protected String selectCheckPointRuleGuid(String ckptprog) {
        return psBatchCheckService.selectCheckPointRuleGuid(ckptprog);
    }


    /**
     * 处理检核指标的结果记录
     *
     * @param prdguid      产品ID
     * @param ckptprogguid 检核程序名称
     * @param checkflag    检核结果 “0”：失败 “1”：成功
     * @param checklog     检核结果日志
     */
    protected void addOrUpdateCheckpointDetailRecord(String prdguid, String ckptprogguid, String checkflag, String checklog) {
        //查看本检核指标的检查明细数据是否存在，不存在则新增一条，已存在则更新相关信息
        Svpssaleprdckpt prdckpt = psBatchCheckService.selectProductCheckpointFailDetail(prdguid, ckptprogguid);
        Date date = new Date();
        String chkdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String chktime = new SimpleDateFormat("HH:mm:ss").format(date);
        if (prdckpt == null) {
            //TODO 批量事务？
            prdckpt = new Svpssaleprdckpt();
            prdckpt.setCkptid(ckptprogguid);
            prdckpt.setSaleprdid(prdguid);
            prdckpt.setCheckflag(checkflag);
            prdckpt.setCheckdate(chkdate);
            prdckpt.setChecktime(chktime);
            prdckpt.setChecktimes(1L);
            prdckpt.setChecklog(checklog);
            prdckpt.setChpttype("1");  //开关型
            prdckpt.setChptswitch(checkflag); //开关结果
            psBatchCheckService.insertNewProductCheckpointResult(prdckpt);
        } else {
            prdckpt.setCheckflag(checkflag);
            prdckpt.setCheckdate(chkdate);
            prdckpt.setChecktime(chktime);
            prdckpt.setChecklog(checklog);
            prdckpt.setChptswitch(checkflag); //开关结果
            psBatchCheckService.updateProductCheckpointResult(prdckpt);
        }

    }

    //===========================================================================

    /**
     * 检查ODSB系统是否可用
     *
     * @return
     * @throws org.springframework.dao.DataAccessException
     *
     */
    private String getOdsbSysStatus() {
        try {
            Map map = simpleJdbcTemplate.queryForMap(SQL_CHECK_ODSBSTATUS);
            return (String) map.get("jobflow_status");
        } catch (DataAccessException e) {
            //TODO :在数据库中记录检核状态   （以及最后一次检核的业务系统日期）
            logger.error("ODSB系统连接错误!");
            throw e;
        }
    }

    private String getSystemParamForOdsbCheckConfig() {
        String config = null;
        try {
            config = platformService.selectEnuExpandValue("SYSTEMPARAM", "ODSBSTATUS");
        } catch (Exception e) {
            logger.error("ODSB检核参数未定义！");
            config = "3";
        }
        if (StringUtils.isEmpty(config)) {
            config = "3";
        }
        return config;
    }

}
