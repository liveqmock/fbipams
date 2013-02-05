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
 * ��Ʒ���.
 * User: zhanrui
 * Date: 11-4-28
 * Time: ����11:21
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
        logger.info("���ʲ�Ʒ��¼���...");
    }


    //======================================================================

    /**
     * ���ݲ�ƷID ��ȫ������˵ĸò�Ʒ��¼���д���
     */
    private void batchCheckOneProduct() {
        try {
            processOneProductAllCheckPoints();
            psBatchCheckService.updateProductCheckFlagToSuccess(prdid);
        } catch (Exception e) {
            throw new RuntimeException("��˲�Ʒʱ���ִ��󡣲�ƷID=" + prdid + "�� " + e.getMessage(), e);
        }
    }

    /**
     * ��һ�ֲ�Ʒ�����м��ָ����д���
     */
    private void processOneProductAllCheckPoints() {
        //����ĳ�ֲ�Ʒ�ļ��ָ��ļ�˳������ƣ���Ч�ģ�
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
