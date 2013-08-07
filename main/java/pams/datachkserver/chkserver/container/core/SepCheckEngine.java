package pams.datachkserver.chkserver.container.core;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import pams.datachkserver.api.checkpoint.CheckPoint;
import pams.datachkserver.api.checkpoint.CheckPointException;
import pams.datachkserver.api.checkpoint.sepcheckpoint.SepCheckPointRequest;
import pams.datachkserver.api.checkpoint.sepcheckpoint.SepCheckPointResponse;
import pams.datachkserver.chkserver.container.util.CheckRule;
import pams.repository.model.SvSaleCkptPrg;
import skyline.service.PlatformService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ��Ʒ��������.
 * User: zhanrui
 * Date: 2013-7-28
 * Time: ����11:21
 */

@Service
public class SepCheckEngine implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(SepCheckEngine.class);
    ApplicationContext applicationContext;

    @Resource
    protected SimpleJdbcTemplate simpleJdbcTemplate;
    @Resource
    protected PlatformService platformService;
    @Resource
    protected CheckRule checkRulesService;

    private static String RTN_CODE_OK = "0000";

    //ODSB���Ӳ���
    private static String RTN_CODE_ODSB_CONNECT_ERR = "1001";
    //ODSB������
    private static String RTN_CODE_ODSB_NOTAVAILABLE = "1002";
    //��˹��̳����쳣
    private static String RTN_CODE_CHECK_PROC_ERR = "1003";
    //���ʱ����ϵͳIO����
    private static String RTN_CODE_CHECK_IO_ERR = "1004";

    //��˳���δ����
    private static String RTN_CODE_CHECK_PROG_NOT_DEFINE = "2000";

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void process(SepCheckPointRequest request, SepCheckPointResponse response) {
/*
        String odsbstatus = null;
        try {
            odsbstatus = getOdsbSysStatus();
        } catch (Exception e) {
            String rtnMsg = "ϵͳ���󣺷������ݲֿ�ϵͳ��ODSB�������á�";
            response.setRtnCode(RTN_CODE_ODSB_CONNECT_ERR);
            response.setRtnMsg(rtnMsg);
            return;
        }

        String odsbstatusConfig = "";
        odsbstatusConfig = getSystemParamForOdsbCheckConfig();
        if (!odsbstatusConfig.equals(odsbstatus)) {
            String rtnMsg = "�������ݲֿ�ϵͳ��ODSB��ϵͳδ����, ״̬��:" + odsbstatus;
            response.setRtnCode(RTN_CODE_ODSB_NOTAVAILABLE);
            response.setRtnMsg(rtnMsg);
            return;
        }

*/

        String prdid = request.getPrdid();
        if (prdid == null) {
            throw new IllegalArgumentException("Prdid cannot be null");
        }
        String subprdid = request.getSubPrdid();

        //��ȡ��˳�������
        List<SvSaleCkptPrg> svSaleCkptPrgList;
        if (subprdid == null) {
            svSaleCkptPrgList = checkRulesService.selectCheckpointHandler(prdid);
        } else {
            svSaleCkptPrgList = checkRulesService.selectCheckpointHandler(prdid, subprdid);
        }

        //��˴���
        boolean isPass = false;
        SepCheckPointResponse respTmp = new SepCheckPointResponse();
        try {
            for (SvSaleCkptPrg svSaleCkptPrg : svSaleCkptPrgList) {
                CheckPoint checkPoint = (CheckPoint) this.applicationContext.getBean(svSaleCkptPrg.getCkptprog());
                respTmp = new SepCheckPointResponse();
                checkPoint.service(request, respTmp);
                if (RTN_CODE_OK.equals(respTmp.getRtnCode())) {
                    isPass = true;
                } else {
                    isPass = false;
                    //���ִ���󣬲��ټ�����ˡ�
                    break;
                }
            }
        } catch (CheckPointException e) {
            response.setRtnCode(RTN_CODE_CHECK_PROC_ERR);
            response.setRtnMsg(e.getMessage());
            logger.error("�������ʱ���ִ���", e);
            //����ϵͳ���󣬲��ټ�����һ����˳���
            return;
        } catch (IOException e) {
            response.setRtnCode(RTN_CODE_CHECK_IO_ERR);
            response.setRtnMsg("���ʱ����ϵͳ����");
            logger.error("���ʱ����ϵͳ����", e);
            //����ϵͳ���󣬲��ټ�����һ����˳���
            return;
        }

        //����ֵ����
        if (isPass) {
            response.setRtnCode(RTN_CODE_OK);
            response.setRtnMsg("���ͨ��");
        }else {
            if (respTmp.getRtnCode() == null) {
                response.setRtnCode(RTN_CODE_CHECK_PROG_NOT_DEFINE);
                response.setRtnMsg("���δͨ�����˲�Ʒ�ݲ�֧�ּ�˴���");
            }else{
                response.setRtnCode(respTmp.getRtnCode());
                response.setRtnMsg(respTmp.getRtnMsg());
            }
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
        String SQL_CHECK_ODSBSTATUS = "select TO_CHAR(biz_date, 'yyyymmdd') as biz_date,jobflow_status from odssys.f_jci_jobflowinstance where job_flow_id ='990063719900001'";
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
