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

    //�Ƿ�ǿ�����¼��
    private boolean forceCheck;
    //֤������
    protected String certtype;
    //֤������
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
            throw new RuntimeException("ODSBϵͳ���Ӵ���", e);
        }
        logger.info("ODSB STATUS=" + odsbstatus);

        String odsbstatusConfig = null;

        odsbstatusConfig = getSystemParamForOdsbCheckConfig();

        if (!odsbstatusConfig.equals(odsbstatus)) {
            logger.info("ODSBϵͳδ������");
            throw new RuntimeException("ODSBϵͳδ������");
        }

        logger.info("ָ���˿�ʼ...��ƷID = " + prdid);

        this.forceCheck = forceCheck;

        beforeProcess();
        //process(prdid);
        postProcess();
        logger.info("ָ������ɡ�");
    }

    abstract protected void process(SepCheckPointRequest request, SepCheckPointResponse response);

    protected void beforeProcess() {
    }

    protected void postProcess() {
    }

    protected List<PsSalesPrdInfoBean> selectNeedCheckPrdinfoList(String prdid, String ckptprogguid) {
        //TODO �Ѽ��ͨ���ĸ��ݲ���ǿ�����¼��
        //TODO  ����ʱ�䣨���ޣ����� ��ֹ��¼������
        if (forceCheck) {
            return psBatchCheckService.selectAllPrdinfoListForForceCheck(prdid, ckptprogguid);
        } else {
            return psBatchCheckService.selectNeedCheckPrdinfoList(prdid, ckptprogguid);
        }
    }

    /**
     * ���÷���������֤����Ϣ��ѯODSB�ͻ�ID
     * �˷�����ECIFϵͳ�ĸ��˿ͻ�������Ϣ��BF_PR_IND_INFO���л�ȡ����
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
     * ������ָ��Ľ����¼
     *
     * @param prdguid      ��ƷID
     * @param ckptprogguid ��˳�������
     * @param checkflag    ��˽�� ��0����ʧ�� ��1�����ɹ�
     * @param checklog     ��˽����־
     */
    protected void addOrUpdateCheckpointDetailRecord(String prdguid, String ckptprogguid, String checkflag, String checklog) {
        //�鿴�����ָ��ļ����ϸ�����Ƿ���ڣ�������������һ�����Ѵ�������������Ϣ
        Svpssaleprdckpt prdckpt = psBatchCheckService.selectProductCheckpointFailDetail(prdguid, ckptprogguid);
        Date date = new Date();
        String chkdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String chktime = new SimpleDateFormat("HH:mm:ss").format(date);
        if (prdckpt == null) {
            //TODO ��������
            prdckpt = new Svpssaleprdckpt();
            prdckpt.setCkptid(ckptprogguid);
            prdckpt.setSaleprdid(prdguid);
            prdckpt.setCheckflag(checkflag);
            prdckpt.setCheckdate(chkdate);
            prdckpt.setChecktime(chktime);
            prdckpt.setChecktimes(1L);
            prdckpt.setChecklog(checklog);
            prdckpt.setChpttype("1");  //������
            prdckpt.setChptswitch(checkflag); //���ؽ��
            psBatchCheckService.insertNewProductCheckpointResult(prdckpt);
        } else {
            prdckpt.setCheckflag(checkflag);
            prdckpt.setCheckdate(chkdate);
            prdckpt.setChecktime(chktime);
            prdckpt.setChecklog(checklog);
            prdckpt.setChptswitch(checkflag); //���ؽ��
            psBatchCheckService.updateProductCheckpointResult(prdckpt);
        }

    }

    //===========================================================================

    /**
     * ���ODSBϵͳ�Ƿ����
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
            //TODO :�����ݿ��м�¼���״̬   ���Լ����һ�μ�˵�ҵ��ϵͳ���ڣ�
            logger.error("ODSBϵͳ���Ӵ���!");
            throw e;
        }
    }

    private String getSystemParamForOdsbCheckConfig() {
        String config = null;
        try {
            config = platformService.selectEnuExpandValue("SYSTEMPARAM", "ODSBSTATUS");
        } catch (Exception e) {
            logger.error("ODSB��˲���δ���壡");
            config = "3";
        }
        if (StringUtils.isEmpty(config)) {
            config = "3";
        }
        return config;
    }

}
