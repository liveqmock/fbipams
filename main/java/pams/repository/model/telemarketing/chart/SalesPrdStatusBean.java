package pams.repository.model.telemarketing.chart;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-4-19
 * Time: ÉÏÎç12:15
 * To change this template use File | Settings | File Templates.
 */
public class SalesPrdStatusBean {
    String deptid;
    String deptname;
    long totalnum;

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

    public long getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(long totalnum) {
        this.totalnum = totalnum;
    }
}
