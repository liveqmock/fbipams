package pams.repository.model.prdset;

import pams.repository.model.Svpssaleprdckpt;

/**
 * ��ǩԼ�Ĳ�Ʒ�����Ϣ��ϸ��¼.
 * User: zhanrui
 * Date: 11-4-25
 * Time: ����12:04
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
