package pams.repository.model.saledata;

import java.io.Serializable;

/**
 * User: zhanrui
 * Date: 13-2-8
 */
public class SaleDataChkVO extends SaleDataVO implements Serializable {
    private String checkflag;
    private String checkflagDesc;
    private String checkdate;
    private String checktime;
    private String checkoperid;
    private String checkopername;
    private String checklog;

    private String reviewflag;
    private String reviewflagDesc;
    private String reviewdesc;
    private String reviewresult;
    private String reviewreplydesc;

    private String archiveflag;
    private String archiveflagDesc;
    private String archivedate;
    public String getCheckflag() {
        return checkflag;
    }

    public void setCheckflag(String checkflag) {
        this.checkflag = checkflag;
    }

    public String getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(String checkdate) {
        this.checkdate = checkdate;
    }

    public String getCheckoperid() {
        return checkoperid;
    }

    public void setCheckoperid(String checkoperid) {
        this.checkoperid = checkoperid;
    }

    public String getChecklog() {
        return checklog;
    }

    public void setChecklog(String checklog) {
        this.checklog = checklog;
    }

    public String getReviewflag() {
        return reviewflag;
    }

    public void setReviewflag(String reviewflag) {
        this.reviewflag = reviewflag;
    }

    public String getReviewdesc() {
        return reviewdesc;
    }

    public void setReviewdesc(String reviewdesc) {
        this.reviewdesc = reviewdesc;
    }

    public String getReviewresult() {
        return reviewresult;
    }

    public void setReviewresult(String reviewresult) {
        this.reviewresult = reviewresult;
    }

    public String getReviewreplydesc() {
        return reviewreplydesc;
    }

    public void setReviewreplydesc(String reviewreplydesc) {
        this.reviewreplydesc = reviewreplydesc;
    }

    public String getCheckopername() {
        return checkopername;
    }

    public void setCheckopername(String checkopername) {
        this.checkopername = checkopername;
    }

    public String getChecktime() {
        return checktime;
    }

    public void setChecktime(String checktime) {
        this.checktime = checktime;
    }

    public String getArchiveflag() {
        return archiveflag;
    }

    public void setArchiveflag(String archiveflag) {
        this.archiveflag = archiveflag;
    }

    public String getArchivedate() {
        return archivedate;
    }

    public void setArchivedate(String archivedate) {
        this.archivedate = archivedate;
    }

    public String getCheckflagDesc() {
        return checkflagDesc;
    }

    public void setCheckflagDesc(String checkflagDesc) {
        this.checkflagDesc = checkflagDesc;
    }

    public String getReviewflagDesc() {
        return reviewflagDesc;
    }

    public void setReviewflagDesc(String reviewflagDesc) {
        this.reviewflagDesc = reviewflagDesc;
    }

    public String getArchiveflagDesc() {
        return archiveflagDesc;
    }

    public void setArchiveflagDesc(String archiveflagDesc) {
        this.archiveflagDesc = archiveflagDesc;
    }
}
