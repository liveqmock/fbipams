package pub.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-11-1
 * Time: 14:47:13
 * To change this template use File | Settings | File Templates.
 */
public class Ibator {
    public static void main(String[] argv) {
        Ibator ibator = new Ibator();

        ibator.run("resources/generatorConfig.xml");
    }

    public void run(String configfile) {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;

        String path = this.getClass().getResource("").getPath();
        File configFile = new File(path + configfile);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;

        try {
            config = cp.parseConfiguration(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = null;
        try {
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
