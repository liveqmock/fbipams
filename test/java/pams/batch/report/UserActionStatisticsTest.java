package pams.batch.report;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pams.repository.model.SysSchedulerLog;
import skyline.service.PlatformService;
import pams.service.prdset.PsSalesInfoService;
import pams.service.prdset.PsStatisticService;

import java.text.ParseException;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-5-30
 * Time: 上午10:59
 * To change this template use File | Settings | File Templates.
 */

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserActionStatisticsTest {

    @Autowired
    private PsSalesInfoService psSalesInfoService;
    @Autowired
    private PlatformService platformService;
    @Autowired
    private PsStatisticService psStatisticService;




    @Test
    public void testPlatform() {
//        String strDays = platformService.selectEnuExpandValue("SYSTEMPARAM", "OUTDATEDAYS");
//        assertEquals("未提交过期天数", "7", strDays);

        try {
            List<SysSchedulerLog> sysSchedulerLogs = platformService.selectSchedulerLogByYear("2011");
            int count = sysSchedulerLogs.size();
            assertEquals(18, count);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void testSalesInfo() {
        int count = psSalesInfoService.deleteOutDateRecordByDate("2011-01-01", "AUTO");

    }


    @Test
    public void testStatisticsService() {
        psStatisticService.selectPrdsetStatisticData("14");
    }

    @Test
    public void testOutput() {
        UserActionStatistics statis = new UserActionStatistics();
        assertEquals(10, statis.countUserAccessTimes());
    }


}
