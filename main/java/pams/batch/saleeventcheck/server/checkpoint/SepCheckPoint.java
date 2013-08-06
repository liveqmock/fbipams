package pams.batch.saleeventcheck.server.checkpoint;

import pams.checkpoint.CheckPointException;
import pams.checkpoint.GenericCheckPoint;

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
    protected void service(SepCheckPointRequest req, SepCheckPointResponse resp)
            throws CheckPointException, IOException {
        //待覆盖
    }
}
