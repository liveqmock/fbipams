package pams.datachkserver.api.checkpoint.sepcheckpoint;

import pams.datachkserver.api.checkpoint.CheckPointException;
import pams.datachkserver.api.checkpoint.GenericCheckPoint;

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
