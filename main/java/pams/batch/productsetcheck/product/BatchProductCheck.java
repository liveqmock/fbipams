package pams.batch.productsetcheck.product;

/**
 * 产品检核
 * User: zhanrui
 * Date: 11-4-28
 * Time: 上午10:59
 * To change this template use File | Settings | File Templates.
 */
public interface BatchProductCheck {

    //对于待检产品表中的某类产品进行统一检核
    public void startBatchCheck(String prdid, boolean forceCheck);
    public void startBatchCheck(boolean forceCheck);

}
