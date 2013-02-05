package pams.repository.model.prdset;

import pams.repository.model.Svpssaleinfo;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-4-20
 * Time: 下午10:20
 * To change this template use File | Settings | File Templates.
 */
public class PsSalesInfoBean extends Svpssaleinfo {
    String branchname;   //机构名称
    String tellername;   //柜员姓名
    String certtypename;  //证件类型名称
    String prdsetname;   //产品套餐名称

    String commitflagname;
    String checkflagname;
    String archiveflagname;
    String startdate;
    String enddate;

    //考核年月 yyyy-MM
    String archivestart;
    String archiveend;

    public String getCommitflagname() {
        return commitflagname;
    }

    public void setCommitflagname(String commitflagname) {
        this.commitflagname = commitflagname;
    }

    public String getCheckflagname() {
        return checkflagname;
    }

    public void setCheckflagname(String checkflagname) {
        this.checkflagname = checkflagname;
    }

    public String getArchiveflagname() {
        return archiveflagname;
    }

    public void setArchiveflagname(String archiveflagname) {
        this.archiveflagname = archiveflagname;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getCerttypename() {
        return certtypename;
    }

    public void setCerttypename(String certtypename) {
        this.certtypename = certtypename;
    }

    public String getPrdsetname() {
        return prdsetname;
    }

    public void setPrdsetname(String prdsetname) {
        this.prdsetname = prdsetname;
    }

    public String getTellername() {
        return tellername;
    }

    public void setTellername(String tellername) {
        this.tellername = tellername;
    }

    public String getArchivestart() {
        return archivestart;
    }

    public void setArchivestart(String archivestart) {
        this.archivestart = archivestart;
    }

    public String getArchiveend() {
        return archiveend;
    }

    public void setArchiveend(String archiveend) {
        this.archiveend = archiveend;
    }
}
