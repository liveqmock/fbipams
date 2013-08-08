package pams.datachkserver.api.checkpoint.sep;

import pams.datachkserver.api.checkpoint.CheckPointResponse;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-8-3
 * Time: ÉÏÎç10:32
 * To change this template use File | Settings | File Templates.
 */
public class SepCheckPointResponse implements CheckPointResponse{
    private String prdid;
    private String subPrdid;
    private String rtnCode;
    private String rtnMsg;

    public String getPrdid() {
        return prdid;
    }

    public void setPrdid(String prdid) {
        this.prdid = prdid;
    }

    public String getSubPrdid() {
        return subPrdid;
    }

    public void setSubPrdid(String subPrdid) {
        this.subPrdid = subPrdid;
    }

    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }
}
