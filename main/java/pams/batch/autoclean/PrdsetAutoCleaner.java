package pams.batch.autoclean;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import skyline.service.PlatformService;
import pams.service.prdset.PsSalesInfoService;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-5-1
 * Time: 下午9:06
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PrdsetAutoCleaner implements AutoCleaner {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PsSalesInfoService psSalesInfoService;
    @Resource
    private PlatformService platformService;

    public void deleteOutDateRecord() {

        DateTime datetime = new DateTime();

        //过期天数默认7天
        int outdateDays = 7;

        String strDays = platformService.selectEnuExpandValue("SYSTEMPARAM", "OUTDATEDAYS");
        outdateDays = Integer.parseInt(strDays);

        String strdate = datetime.minusDays(outdateDays).toString("yyyy-MM-dd");
        int count = psSalesInfoService.deleteOutDateRecordByDate(strdate, "AUTO");
        logger.info("删除" + strdate + "（含）之前的产品套餐签约记录数为 " + count + " 条。");
    }
}
