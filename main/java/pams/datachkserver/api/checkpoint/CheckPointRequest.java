package pams.datachkserver.api.checkpoint;

import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-8-3
 * Time: ÉÏÎç11:40
 * To change this template use File | Settings | File Templates.
 */
public interface CheckPointRequest {
    public String getParameter(String name);
    public Enumeration<String> getParameterNames();
    public String[] getParameterValues(String name);
    public Map<String, String[]> getParameterMap();

    public Object getAttribute(String name);
    public Enumeration<String> getAttributeNames();
    public void setAttribute(String name, Object o);
    public void removeAttribute(String name);

    public ServletContext getServletContext();
}
