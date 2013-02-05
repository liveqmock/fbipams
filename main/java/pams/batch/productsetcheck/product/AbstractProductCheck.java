package pams.batch.productsetcheck.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.service.prdset.PsBatchCheckService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-5-5
 * Time: ÉÏÎç10:23
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractProductCheck implements  BatchProductCheck, SingleProductCheck{
    private static final Logger logger = LoggerFactory.getLogger(AbstractProductCheck.class);

    @Resource
    protected PsBatchCheckService psBatchCheckService;

    abstract public void startBatchCheck(boolean forceCheck);
    abstract public void startBatchCheck(String prdid, boolean forceCheck);

    abstract public void startSingleCheck();

    protected List<String> needCheckProductIdList = new ArrayList<String>();

    public AbstractProductCheck(){
    }
    protected void initNeedCheckProductList(){
/*
        List<String> products = new ArrayList<String>();
        products.add("14");
        products.add("15");
        products.add("17");
        products.add("06");
        products.add("07");
        return products;
*/
        this.needCheckProductIdList = psBatchCheckService.selectAllProductFromCurrentPrdset();
    }

}
