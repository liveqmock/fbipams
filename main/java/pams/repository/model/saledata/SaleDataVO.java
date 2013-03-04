package pams.repository.model.saledata;

import pams.repository.model.SvSaleDetail;

import java.io.Serializable;

/**
 * User: zhanrui
 * Date: 13-2-8
 */
public class SaleDataVO extends SvSaleDetail implements Serializable {
    //≤ø√≈
    String deptid;
    String deptname;

    String tellername;
    String prdname;
    String subprdname;

    String custname;
    long rnum;

    public String getPrdname() {
        return prdname;
    }

    public void setPrdname(String prdname) {
        this.prdname = prdname;
    }

    public String getSubprdname() {
        return subprdname;
    }

    public void setSubprdname(String subprdname) {
        this.subprdname = subprdname;
    }

    public String getTellername() {
        return tellername;
    }

    public void setTellername(String tellername) {
        this.tellername = tellername;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public long getRnum() {
        return rnum;
    }

    public void setRnum(long rnum) {
        this.rnum = rnum;
    }
}
