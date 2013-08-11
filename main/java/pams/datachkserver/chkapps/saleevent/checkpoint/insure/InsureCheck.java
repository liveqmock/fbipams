package pams.datachkserver.chkapps.saleevent.checkpoint.insure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pams.datachkserver.api.checkpoint.CheckPointException;
import pams.datachkserver.api.checkpoint.sep.SepCheckPoint;
import pams.datachkserver.api.checkpoint.sep.SepCheckPointRequest;
import pams.datachkserver.api.checkpoint.sep.SepCheckPointResponse;

import java.io.IOException;

/**
 * ���գ�����ͨ����Ʒ���
 * User: zhanrui
 * Date: 13-8-3
 * Time: ����7:47
 */
@Component("insure.InsureCheck")
public class InsureCheck extends SepCheckPoint {
    private static final Logger logger = LoggerFactory.getLogger(InsureCheck.class);

    @Override
    public void doCheck(SepCheckPointRequest req, SepCheckPointResponse resp) throws CheckPointException, IOException {
        String dcc_custno = generateDccCustnoByCertInfo(req.getCertType(), req.getCertNo());
        if (dcc_custno == null) {
            resp.setRtnCode("2000");
            resp.setRtnMsg("δ�ҵ���Ӧ��DCC�ͻ���,���ܽ��м�˴���.");
            return;
        }

        String sql = "select count(*) from bf_evt_nin_iias_jrnl where cust_no=? and cr_tx_dt=? and cr_tx_amt=? and org_cd=?";
        int count = jdbcTemplate.queryForInt(sql, dcc_custno, req.getTxnDate(), req.getSalesAmt1(), req.getBankid());
        if (count == 0) {
            resp.setRtnCode("2001");
            resp.setRtnMsg("δ�ҵ���Ӧ�Ĵ���¼.");
        }else{
            resp.setRtnCode("0000");
            resp.setRtnMsg("���ҵ���Ӧ�Ĵ���¼.");
        }
    }
}
