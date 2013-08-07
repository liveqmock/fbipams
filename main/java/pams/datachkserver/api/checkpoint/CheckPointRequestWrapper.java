package pams.datachkserver.api.checkpoint;

import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Map;

/**
 * 包装类.
 * User: zhanrui
 * Date: 13-8-6
 * Time: 上午11:13
 */
public class CheckPointRequestWrapper implements CheckPointRequest {
    private  CheckPointRequest request;

    public CheckPointRequestWrapper(CheckPointRequest request){
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        this.request = request;
    }

    public CheckPointRequest getRequest(){
        return this.request;
    }
    public void setRequest(CheckPointRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        this.request = request;
    }


    @Override
    public String getParameter(String name) {
        return this.request.getParameter(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return this.request.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.request.getParameterValues(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.request.getParameterMap();
    }



    @Override
    public Object getAttribute(String name) {
        return this.request.getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return this.request.getAttributeNames();
    }

    @Override
    public void setAttribute(String name, Object o) {
        this.request.setAttribute(name, o);
    }

    @Override
    public void removeAttribute(String name) {
        this.request.removeAttribute(name);
    }

    @Override
    public ServletContext getServletContext() {
        return request.getServletContext();
    }
}
