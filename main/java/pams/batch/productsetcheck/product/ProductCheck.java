package pams.batch.productsetcheck.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import pams.batch.productsetcheck.checkpoint.CheckPoint;

import java.util.List;

/**
 * 产品检核.
 * User: zhanrui
 * Date: 11-4-28
 * Time: 上午11:21
 * To change this template use File | Settings | File Templates.
 */

@Component
public class ProductCheck extends AbstractProductCheck implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(ProductCheck.class);

    ApplicationContext applicationContext;

    private String prdid;
    private boolean forceCheck = false;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //=====================================================================

    @Override
    public void startBatchCheck(boolean forceCheck) {
        //List<String> products = super.selectNeedCheckProductList();
        super.initNeedCheckProductList();
        this.forceCheck = forceCheck;
        for (String prdid : super.needCheckProductIdList) {
            this.prdid = prdid;
            batchCheckOneProduct();
        }

        //
        psBatchCheckService.updateProductSetCheckFlagToSuccess();
    }

    @Override
    public void startBatchCheck(String prdid, boolean forceCheck) {
        this.prdid = prdid;
        this.forceCheck = forceCheck;
        batchCheckOneProduct();
        psBatchCheckService.updateProductSetCheckFlagToSuccess();
    }

    @Override
    public void startSingleCheck() {
        logger.info("单笔产品记录检核...");
    }


    //======================================================================

    /**
     * 根据产品ID 对全部待检核的该产品记录进行处理
     */
    private void batchCheckOneProduct() {
        try {
            processOneProductAllCheckPoints();
            psBatchCheckService.updateProductCheckFlagToSuccess(prdid);
        } catch (Exception e) {
            throw new RuntimeException("检核产品时出现错误。产品ID=" + prdid + "。 " + e.getMessage(), e);
        }
    }

    /**
     * 对一种产品的所有检核指标进行处理
     */
    private void processOneProductAllCheckPoints() {
        //检索某种产品的检核指标的检核程序名称（有效的）
        List<String> checkPointClassList = selectCheckPointClassName();

        for (String checkpointclass : checkPointClassList) {
            CheckPoint checkPoint = (CheckPoint) this.applicationContext.getBean(checkpointclass);
            checkPoint.startcheck(this.prdid, this.forceCheck);
        }
    }

    private List<String> selectCheckPointClassName() {
        return psBatchCheckService.selectCheckPointClassName(this.prdid);
    }
}
