package pams.batch.productsetcheck.product;

/**
 * ��Ʒ���
 * User: zhanrui
 * Date: 11-4-28
 * Time: ����10:59
 * To change this template use File | Settings | File Templates.
 */
public interface BatchProductCheck {

    //���ڴ����Ʒ���е�ĳ���Ʒ����ͳһ���
    public void startBatchCheck(String prdid, boolean forceCheck);
    public void startBatchCheck(boolean forceCheck);

}
