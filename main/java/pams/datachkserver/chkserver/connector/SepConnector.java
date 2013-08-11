package pams.datachkserver.chkserver.connector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pams.datachkserver.api.checkpoint.sep.SepCheckPointRequest;
import pams.datachkserver.api.checkpoint.sep.SepCheckPointResponse;
import pams.datachkserver.chkserver.container.core.SepCheckEngine;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 外部接口适配器: 营销活动检核协议.
 * User: zhanrui
 * Date: 13-8-6
 * Time: 下午2:17
 */
@Service
public class SepConnector {

    @Autowired
    private SepCheckEngine engine;

    public void process(Map<String, Object> request, Map<String, Object> response) {
        SepCheckPointRequest req = getRequest(request);
        SepCheckPointResponse resp = new SepCheckPointResponse();

        try {
            engine.process(req, resp);
        } catch (Exception e) {
            response.put("rtncode", "1111");
            String msg = e.getMessage();
            if (msg.getBytes().length > 30) {
                msg = msg.substring(0, 30);
            }
            response.put("rtnmsg", msg);
            return;
        }

        response.put("rtncode", resp.getRtnCode());
        response.put("rtnmsg", resp.getRtnMsg());
    }

    private SepCheckPointRequest getRequest(Map<String, Object> request) {
        SepCheckPointRequest sepRequest = new SepCheckPointRequest();

        sepRequest.setPrdid((String) request.get("prdid"));
        sepRequest.setSubPrdid((String) request.get("subprdid"));
        sepRequest.setTellerid((String) request.get("tellerid"));
        sepRequest.setBankid((String) request.get("bankid"));

        sepRequest.setTxnDate((String) request.get("txndate"));
        sepRequest.setTxnTime((String) request.get("txntime"));

        sepRequest.setSalesNum1((Long) request.get("salesnum1"));
        sepRequest.setSalesNum2((Long) request.get("salesnum2"));
        sepRequest.setSalesNum3((Long) request.get("salesnum3"));
        sepRequest.setSalesAmt1((BigDecimal) request.get("salesamt1"));
        sepRequest.setSalesAmt2((BigDecimal) request.get("salesamt2"));
        sepRequest.setSalesAmt3((BigDecimal) request.get("salesamt3"));

        sepRequest.setCustNo((String) request.get("custno"));
        sepRequest.setCertType((String) request.get("certtype"));
        sepRequest.setCertNo((String) request.get("certno"));

        return sepRequest;
    }
}
