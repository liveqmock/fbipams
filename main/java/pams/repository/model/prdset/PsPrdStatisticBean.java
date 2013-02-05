package pams.repository.model.prdset;

/**
 * 数据统计.
 * User: zhanrui
 * Date: 11-5-26
 * Time: 下午5:26
 * To change this template use File | Settings | File Templates.
 */
public class PsPrdStatisticBean {
     String seqno;
     String prdid;
     String prdname;
     String commitflag;
     String checkflag;
     String archiveflag;
     int totalnum;

    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno;
    }

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

    public String getCommitflag() {
        return commitflag;
    }

    public void setCommitflag(String commitflag) {
        this.commitflag = commitflag;
    }

    public String getCheckflag() {
        return checkflag;
    }

    public void setCheckflag(String checkflag) {
        this.checkflag = checkflag;
    }

    public String getArchiveflag() {
        return archiveflag;
    }

    public void setArchiveflag(String archiveflag) {
        this.archiveflag = archiveflag;
    }

    public int getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(int totalnum) {
        this.totalnum = totalnum;
    }
}
