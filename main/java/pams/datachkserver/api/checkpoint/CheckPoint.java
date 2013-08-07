package pams.datachkserver.api.checkpoint;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2013-7-28
 * Time: обнГ2:47
 */
public interface CheckPoint {
    public void init(CheckPointConfig config) throws CheckPointException;
    public CheckPointConfig getCheckPointConfig();
    public String getCheckPointInfo();
    public void service(CheckPointRequest req, CheckPointResponse res)
            throws CheckPointException, IOException;
    public void destroy();
}
