package pams.datachkserver.chkapps.saleevent.checkpoint.finplan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pams.datachkserver.api.checkpoint.CheckPointException;
import pams.datachkserver.api.checkpoint.sep.SepCheckPoint;
import pams.datachkserver.api.checkpoint.sep.SepCheckPointRequest;
import pams.datachkserver.api.checkpoint.sep.SepCheckPointResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 理财产品检核
 * User: zhanrui
 * Date: 13-8-3
 * Time: 上午7:47
 */
@Component("finplan.FinPlanCheck")
public class FinPlanCheck extends SepCheckPoint {
    private static final Logger logger = LoggerFactory.getLogger(FinPlanCheck.class);

    @Override
    public void doCheck(SepCheckPointRequest req, SepCheckPointResponse resp) throws CheckPointException, IOException {
        String dcc_custno = generateDccCustnoByCertInfo(req.getCertType(), req.getCertNo());
        if (dcc_custno == null) {
            resp.setRtnCode("2000");
            resp.setRtnMsg("未找到对应的DCC客户号,不能进行检核处理.");
            return;
        }

        String sql = "select cust_ta_id from bf_agt_nin_fs_ta_account where cert_type=? and cert_no=?";
        List<Map<String,Object>> cust_ta_id_list = jdbcTemplate.queryForList(sql, req.getCertType(), req.getCertNo());

        int count = 0;
        for (Map<String, Object> record : cust_ta_id_list) {
            String  cust_ta_id = (String)record.get("cust_ta_id");
            sql = "select count(*) from bf_agt_nin_fs_cust_prod_list where cust_ta_id=? and effective_date=? and start_amt=?";
            count += jdbcTemplate.queryForInt(sql, cust_ta_id, req.getTxnDate(), req.getSalesAmt1());
        }

        if (count == 0) {
            resp.setRtnCode("2001");
            resp.setRtnMsg("未找到对应的存款记录.");
        }else{
            resp.setRtnCode("0000");
            resp.setRtnMsg("已找到对应的存款记录.");
        }
    }
}
