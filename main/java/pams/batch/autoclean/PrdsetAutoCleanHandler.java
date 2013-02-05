package pams.batch.autoclean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-5-1
 * Time: обнГ9:55
 * To change this template use File | Settings | File Templates.
 */
public class PrdsetAutoCleanHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="prdsetAutoCleaner")
    private AutoCleaner cleaner;

    public void run(){
       cleaner.deleteOutDateRecord();
    }
}
