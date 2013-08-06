package pams.repository.model.saledata;

import java.io.Serializable;

/**
 * User: zhanrui
 * Date: 13-2-8
 */
public class SaleDataChkVO extends SaleDataVO implements Serializable {
    private String checkflag;
    private String checkdate;
    private String checkoperid;
    private String checklog;

    private String reviewflag;
    private String reviewdesc;
    private String reviewresult;
    private String reviewreplydesc;

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
}
