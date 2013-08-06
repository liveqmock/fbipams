package pams.checkpoint;

import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-8-3
 * Time: ионГ11:16
 * To change this template use File | Settings | File Templates.
 */
public interface CheckPointConfig {
    public String getCheckPointName();
    public CheckPointContext getCheckPointContext();
    public String getInitParameter(String name);
    public Enumeration<String> getInitParameterNames();
}
