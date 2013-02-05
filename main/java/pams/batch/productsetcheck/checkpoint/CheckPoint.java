package pams.batch.productsetcheck.checkpoint;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-4-28
 * Time: обнГ2:47
 * To change this template use File | Settings | File Templates.
 */
public interface CheckPoint {
   void startcheck(String prdid, boolean forceCheck);
}
