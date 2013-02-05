package pams.service.prdset;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-5-25
 * Time: ÉÏÎç10:23
 * To change this template use File | Settings | File Templates.
 */
@Service
public class OdsbQryService {

    @Resource
    protected SimpleJdbcTemplate simpleJdbcTemplate;

    public String getOdsbSysStatus() {
        Map map = simpleJdbcTemplate.queryForMap("select TO_CHAR(biz_date, 'yyyymmdd') as biz_date,jobflow_status from odssys.f_jci_jobflowinstance where job_flow_id ='990063719900001'");
        return (String) map.get("jobflow_status");
    }

}
