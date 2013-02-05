package pams.batch.productsetcheck;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.batch.productsetcheck.product.BatchProductCheck;
import pams.batch.productsetcheck.product.SingleProductCheck;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-5-3
 * Time: ����2:17
 * To change this template use File | Settings | File Templates.
 */
public class CronMainHandler {
    private static final Logger logger = LoggerFactory.getLogger(CronMainHandler.class);

    @Resource
    private BatchProductCheck batchHandler;

    //�Ե��ʲ�Ʒ��¼��� �ݲ���
    @Resource
    private SingleProductCheck singleHandler;

    private String prdid;
    private boolean forceCheck;

    public void run() {
        process();
    }

    public void run(boolean forceCheck) {
        this.forceCheck = forceCheck;
        process();
    }

    public void run(String prdid) {
        this.prdid = prdid;
        process();
    }

    public void run(String prdid, boolean forceCheck) {
        this.prdid = prdid;
        this.forceCheck = forceCheck;
        process();
    }

    private void process() {
        long startTime = 0, endTime = 0;
        try {
            startTime = System.currentTimeMillis();
            if (StringUtils.isEmpty(this.prdid)) {
                batchHandler.startBatchCheck(this.forceCheck);
            } else {
                batchHandler.startBatchCheck(this.prdid, this.forceCheck);
            }
            endTime = System.currentTimeMillis();
        } catch (Exception e) {
            logger.error("���ݼ�˳��ִ���", e);
            throw new RuntimeException("���ݼ�˳��ִ���" + e.getMessage(), e);
        } finally {
            logger.info("���μ������ʱ�䣺" + (endTime - startTime) / 1000 / 60 + "��" + (endTime - startTime) / 1000 % 60 + "�롣");
        }

    }
}
