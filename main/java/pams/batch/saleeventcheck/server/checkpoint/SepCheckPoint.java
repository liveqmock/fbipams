package pams.batch.saleeventcheck.server.checkpoint;

import pams.checkpoint.CheckPointException;
import pams.checkpoint.GenericCheckPoint;

import java.io.IOException;

/**
 * ��˽Ӫ�����˻�����.
 * User: zhanrui
 * Date: 13-8-3
 * Time: ����1:11
 */
public abstract class SepCheckPoint extends GenericCheckPoint {
    public SepCheckPoint(){
        //
    }
    protected void service(SepCheckPointRequest req, SepCheckPointResponse resp)
            throws CheckPointException, IOException {
        //������
    }
}
