package pams.repository.model.prdset;

import pams.repository.model.Svpssaleprdckpt;

/**
 * 已签约的产品检核信息详细记录.
 * User: zhanrui
 * Date: 11-4-25
 * Time: 下午12:04
 * To change this template use File | Settings | File Templates.
 */
public class PsPrdCkptInfoBean extends Svpssaleprdckpt{
    private String prdid;
    private String prdname;
    private String checkflagname;
    private String ckptprdname;

    public String getPrdid() {
        return prdid;
    }

    public void setPrdid(String prdid) {
        this.prdid = prdid;
    }

    public String getPrdname() {
        return prdname;
    }

    public void setPrdname(String prdname) {
        this.prdname = prdname;
    }

    public String getCkptprdname() {
        return ckptprdname;
    }

    public void setCkptprdname(String ckptprdname) {
        this.ckptprdname = ckptprdname;
    }

    public String getCheckflagname() {
        return checkflagname;
    }

    public void setCheckflagname(String checkflagname) {
        this.checkflagname = checkflagname;
    }
}
