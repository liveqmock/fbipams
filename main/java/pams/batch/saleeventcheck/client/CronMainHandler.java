package pams.batch.saleeventcheck.client;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.batch.saleeventcheck.server.SepServer;

import javax.annotation.Resource;

/**
 * User: zhanrui
 * Date: 2013-8-3
 * Time: ����2:17
 */
public class CronMainHandler {
    private static final Logger logger = LoggerFactory.getLogger(CronMainHandler.class);

    @Resource
    private SepServer batchHandler;

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
