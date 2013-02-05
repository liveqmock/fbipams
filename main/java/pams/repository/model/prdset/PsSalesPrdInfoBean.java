package pams.repository.model.prdset;

import pams.repository.model.Svpssaleprdinfo;

/**
 * 已签约的产品信息详细记录.
 * User: zhanrui
 * Date: 11-4-25
 * Time: 下午12:04
 * To change this template use File | Settings | File Templates.
 */
public class PsSalesPrdInfoBean extends Svpssaleprdinfo{
    private String prdname;
    //检核结果
    private String checkflagname;

    //对应产品套餐表中的信息
    private String certtype;
    private String certno;
    private String txndate;

    //对应产品检核明细表中的信息
    private String ckptguid;
    private String chptcheckflag;

    public String getPrdname() {
        return prdname;
    }

    public void setPrdname(String prdname) {
        this.prdname = prdname;
    }

    public String getCheckflagname() {
        return checkflagname;
    }

    public void setCheckflagname(String checkflagname) {
        this.checkflagname = checkflagname;
    }

    public String getCerttype() {
        return certtype;
    }

    public void setCerttype(String certtype) {
        this.certtype = certtype;
    }

    public String getCertno() {
        return certno;
    }

    public void setCertno(String certno) {
        this.certno = certno;
    }

    public String getCkptguid() {
        return ckptguid;
    }

    public void setCkptguid(String ckptguid) {
        this.ckptguid = ckptguid;
    }

    public String getChptcheckflag() {
        return chptcheckflag;
    }

    public void setChptcheckflag(String chptcheckflag) {
        this.chptcheckflag = chptcheckflag;
    }

    public String getTxndate() {
        return txndate;
    }

    public void setTxndate(String txndate) {
        this.txndate = txndate;
    }
}
