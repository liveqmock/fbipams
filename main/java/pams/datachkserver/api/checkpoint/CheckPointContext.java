package pams.datachkserver.api.checkpoint;

import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-8-3
 * Time: ÉÏÎç11:20
 * To change this template use File | Settings | File Templates.
 */
public interface CheckPointContext {
    public  CheckPointContext getContext(String uripath);
    public String getContextPath();

    public CheckPoint getCheckPoint(String checkPointName) throws CheckPointException;

    public String getInitParameter(String name);
    public Enumeration<String> getInitParameterNames();
    public boolean setInitParameter(String name, String value);

    public Object getAttribute(String name);
    public Enumeration<String> getAttributeNames();
    public void setAttribute(String name, Object object);
    public void removeAttribute(String name);
}
