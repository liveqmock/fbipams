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
 * 产品检核主入口.
 * User: zhanrui
 * Date: 2013-7-28
 * Time: 上午11:21
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

    //ODSB连接不上
    private static String RTN_CODE_ODSB_CONNECT_ERR = "1001";
    //ODSB不可用
    private static String RTN_CODE_ODSB_NOTAVAILABLE = "1002";
    //检核过程出现异常
    private static String RTN_CODE_CHECK_PROC_ERR = "1003";
    //检核时出现系统IO错误
    private static String RTN_CODE_CHECK_IO_ERR = "1004";

    //检核程序未定义
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
            String rtnMsg = "系统错误：分行数据仓库系统（ODSB）不可用。";
            response.setRtnCode(RTN_CODE_ODSB_CONNECT_ERR);
            response.setRtnMsg(rtnMsg);
            return;
        }

        String odsbstatusConfig = "";
        odsbstatusConfig = getSystemParamForOdsbCheckConfig();
        if (!odsbstatusConfig.equals(odsbstatus)) {
            String rtnMsg = "分行数据仓库系统（ODSB）系统未就绪, 状态码:" + odsbstatus;
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

        //获取检核程序配置
        List<SvSaleCkptPrg> svSaleCkptPrgList;
        if (subprdid == null) {
            svSaleCkptPrgList = checkRulesService.selectCheckpointHandler(prdid);
        } else {
            svSaleCkptPrgList = checkRulesService.selectCheckpointHandler(prdid, subprdid);
        }

        //检核处理
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
                    //出现错误后，不再继续检核。
                    break;
                }
            }
        } catch (CheckPointException e) {
            response.setRtnCode(RTN_CODE_CHECK_PROC_ERR);
            response.setRtnMsg(e.getMessage());
            logger.error("检核数据时出现错误：", e);
            //出现系统错误，不再继续下一个检核程序
            return;
        } catch (IOException e) {
            response.setRtnCode(RTN_CODE_CHECK_IO_ERR);
            response.setRtnMsg("检核时出现系统错误。");
            logger.error("检核时出现系统错误：", e);
            //出现系统错误，不再继续下一个检核程序
            return;
        }

        //返回值处理
        if (isPass) {
            response.setRtnCode(RTN_CODE_OK);
            response.setRtnMsg("检核通过");
        }else {
            if (respTmp.getRtnCode() == null) {
                response.setRtnCode(RTN_CODE_CHECK_PROG_NOT_DEFINE);
                response.setRtnMsg("检核未通过，此产品暂不支持检核处理。");
            }else{
                response.setRtnCode(respTmp.getRtnCode());
                response.setRtnMsg(respTmp.getRtnMsg());
            }
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
        String SQL_CHECK_ODSBSTATUS = "select TO_CHAR(biz_date, 'yyyymmdd') as biz_date,jobflow_status from odssys.f_jci_jobflowinstance where job_flow_id ='990063719900001'";
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
