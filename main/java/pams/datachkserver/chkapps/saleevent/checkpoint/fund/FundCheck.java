package pams.datachkserver.chkapps.saleevent.checkpoint.fund;

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
 * �����Ʒ���
 * User: zhanrui
 * Date: 13-8-3
 * Time: ����7:47
 */
@Component("fund.FundCheck")
public class FundCheck extends SepCheckPoint {
    private static final Logger logger = LoggerFactory.getLogger(FundCheck.class);

    @Override
    public void doCheck(SepCheckPointRequest req, SepCheckPointResponse resp) throws CheckPointException, IOException {

        //��ȡ֤ȯ����
        String sql = "select secur_cord from BF_AGT_NIN_ST_TA_SEC_CARD where ECIF_CUSTNO=?";
        List<Map<String,Object>> secur_cord_list = jdbcTemplate.queryForList(sql, req.getCustNo());

        int count = 0;
        for (Map<String, Object> secur_cord_record : secur_cord_list) {
            sql = "select fund_acct_no from BF_AGT_NIN_ST_TACCOINFO where SECUR_CORD=? and D_OPENDATE=?";
            String secur_cord = (String) secur_cord_record.get("secur_cord");
            List<Map<String,Object>> fund_acct_no_list = jdbcTemplate.queryForList(sql, secur_cord, req.getTxnDate());
            for (Map<String, Object> fund_acct_no_record : fund_acct_no_list) {
                String fund_acct_no = (String) fund_acct_no_record.get("fund_acct_no");
                sql = "select count(*) from bf_evt_nin_st_tconfirm_trad where fund_acct_no=? and d_cdate=? and f_confirmbalance=?";
                count += jdbcTemplate.queryForInt(sql, fund_acct_no, req.getTxnDate(), req.getSalesAmt1());
            }
        }

        if (count == 0) {
            resp.setRtnCode("2001");
            resp.setRtnMsg("δ�ҵ���Ӧ�Ĵ���¼.");
        }else{
            resp.setRtnCode("0000");
            resp.setRtnMsg("���ҵ���Ӧ�Ĵ���¼.");
        }
    }
}
