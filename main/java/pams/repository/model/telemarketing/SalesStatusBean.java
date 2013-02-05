package pams.repository.model.telemarketing;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-4-19
 * Time: ÉÏÎç12:15
 * To change this template use File | Settings | File Templates.
 */
public class SalesStatusBean {
    String prdid;
    String prdname;
    long salescount;

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

    public long getSalescount() {
        return salescount;
    }

    public void setSalescount(long salescount) {
        this.salescount = salescount;
    }
}
