package pams.datachkserver.chkapps.saleevent.checkpoint.noblemetal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pams.datachkserver.api.checkpoint.CheckPointException;
import pams.datachkserver.api.checkpoint.sep.SepCheckPoint;
import pams.datachkserver.api.checkpoint.sep.SepCheckPointRequest;
import pams.datachkserver.api.checkpoint.sep.SepCheckPointResponse;

import java.io.IOException;

/**
 * 黄金等贵金属产品检核
 * User: zhanrui
 * Date: 13-8-3
 * Time: 上午7:47
 */
@Component("noblemetal.NobleMetalCheck")
public class NobleMetalCheck extends SepCheckPoint {
    private static final Logger logger = LoggerFactory.getLogger(NobleMetalCheck.class);

    @Override
    public void doCheck(SepCheckPointRequest req, SepCheckPointResponse resp) throws CheckPointException, IOException {
        String dcc_custno = generateDccCustnoByCertInfo(req.getCertType(), req.getCertNo());
        if (dcc_custno == null) {
            resp.setRtnCode("2000");
            resp.setRtnMsg("未找到对应的DCC客户号,不能进行检核处理.");
            return;
        }

        String sql = "select count(*) from bf_evt_nin_xtgtxgtx where cert_type=? and cert_no=? and cr_tx_dt=? and weight=?";
        int count = jdbcTemplate.queryForInt(sql, req.getCertType(), req.getCertNo(), req.getTxnDate(), req.getSalesNum1());
        if (count == 0) {
            resp.setRtnCode("2001");
            resp.setRtnMsg("未找到对应的存款记录.");
        }else{
            resp.setRtnCode("0000");
            resp.setRtnMsg("已找到对应的存款记录.");
        }
    }
}
