package pams.batch.autoclean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import skyline.service.PlatformService;

import javax.sql.DataSource;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-5-30
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class PrdsetAutoCleanerTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private PlatformService platformService;

    @Autowired
    private AutoCleaner autoCleaner;

    @Autowired
    //注入特定数据源
    public void setDataSource(@Qualifier("pamsDataSource") DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Test
    public void testPlatformEnuConfig() {
        String strDays = platformService.selectEnuExpandValue("SYSTEMPARAM", "OUTDATEDAYS");
        assertEquals("未提交过期天数", "7", strDays);
    }

    @Test
    public void testDeleteOutDateRecord() throws Exception {
        autoCleaner.deleteOutDateRecord();
    }
}
