package pams.datachkserver.api.checkpoint;

import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-8-3
 * Time: ÉÏÎç11:45
 */
public abstract class GenericCheckPoint implements CheckPoint, CheckPointConfig, Serializable {
    private static final long serialVersionUID = 1L;

    private transient CheckPointConfig config;

    public GenericCheckPoint() {
        // NOOP
    }

    @Override
    public void init(CheckPointConfig config) throws CheckPointException {
        this.config = config;
        this.init();
    }
    public void init() throws CheckPointException {
        // NOOP by default
    }

    @Override
    public CheckPointConfig getCheckPointConfig() {
        return config;
    }

    @Override
    public String getCheckPointInfo() {
        return "";
    }

    @Override
    public abstract void service(CheckPointRequest req, CheckPointResponse res) throws CheckPointException, IOException;

    @Override
    public void destroy() {
        //
    }

    @Override
    public String getCheckPointName() {
        return config.getCheckPointName();
    }

    @Override
    public CheckPointContext getCheckPointContext() {
        return getCheckPointConfig().getCheckPointContext();
    }

    @Override
    public String getInitParameter(String name) {
        return getCheckPointConfig().getInitParameter(name);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return getCheckPointConfig().getInitParameterNames();
    }
}
