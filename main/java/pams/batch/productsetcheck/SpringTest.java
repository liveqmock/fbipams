package pams.batch.productsetcheck;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-5-13
 * Time: ÏÂÎç2:15
 * To change this template use File | Settings | File Templates.
 */
public class SpringTest {
    public static void main(String[] argv) {
        SpringTest main = new SpringTest();
        main.springstart();
    }

    private void springstart() {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("file:**out\\artifacts\\ccbpams\\WEB-INF\\applicationContext.xml");
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        CronMainHandler bean = (CronMainHandler)ctx.getBean("prdsetCheck");
        bean.run();
    }
}
