package pams.datachkserver.api.checkpoint.sep;

import pams.datachkserver.api.checkpoint.CheckPointException;
import pams.datachkserver.api.checkpoint.CheckPointRequest;
import pams.datachkserver.api.checkpoint.CheckPointResponse;
import pams.datachkserver.api.checkpoint.GenericCheckPoint;

import java.io.IOException;

/**
 * 对私营销活动检核基础类.
 * User: zhanrui
 * Date: 13-8-3
 * Time: 下午1:11
 */
public abstract class SepCheckPoint extends GenericCheckPoint {
    public SepCheckPoint(){
        //
    }

    protected void doCheck(SepCheckPointRequest req, SepCheckPointResponse resp)
            throws CheckPointException, IOException {
        System.out.println("111");
    }

    protected void service(SepCheckPointRequest req, SepCheckPointResponse resp) throws CheckPointException, IOException {
        doCheck(req, resp);
    }

    @Override
    public void service(CheckPointRequest req, CheckPointResponse resp) throws CheckPointException, IOException {
        SepCheckPointRequest request;
        SepCheckPointResponse response;
        try {
            request = (SepCheckPointRequest) req;
            response = (SepCheckPointResponse) resp;
        } catch (ClassCastException e) {
            throw new CheckPointException("non-SEP request or response");
        }
        service(request, response);
    }
}
