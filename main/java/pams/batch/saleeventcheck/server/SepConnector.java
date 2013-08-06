package pams.batch.saleeventcheck.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pams.batch.saleeventcheck.server.checkpoint.SepCheckPointRequest;
import pams.batch.saleeventcheck.server.checkpoint.SepCheckPointResponse;

import java.util.Map;

/**
 * 外部接口适配器.
 * User: zhanrui
 * Date: 13-8-6
 * Time: 下午2:17
 */
@Service
public class SepConnector {

    @Autowired
    private SepServer server;

    public void process(Map<String, Object> request, Map<String, Object> response) {

        SepCheckPointRequest req = getRequest(request);
        SepCheckPointResponse resp = new SepCheckPointResponse();
        server.process(req, resp);

        response.put("rtncode", "0000");
    }

    private SepCheckPointRequest getRequest(Map<String, Object> request){
        SepCheckPointRequest sepRequest = new SepCheckPointRequest();
        sepRequest.setPrdid((String)request.get("prdid"));

        return sepRequest;
    }
}
