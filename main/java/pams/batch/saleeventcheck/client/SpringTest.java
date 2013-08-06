package pams.batch.saleeventcheck.client;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static java.lang.System.exit;

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
        System.out.println("========= game over =============");
        exit(0);
    }

    private void springstart() {
//        ApplicationContext ctx = new FileSystemXmlApplicationContext("file:e:\\temp\\applicationContext.xml");
//        ApplicationContext ctx = new FileSystemXmlApplicationContext("file:**out\\artifacts\\ccbpams\\WEB-INF\\applicationContext.xml");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        CronMainHandler bean = (CronMainHandler)ctx.getBean("saleeventCheck");
        bean.run();
    }
}
