package pams.repository.model;

import java.util.Date;

public class SvSaleCkptJrnl {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SV_SALE_CKPT_JRNL.GUID
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    private String guid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SV_SALE_CKPT_JRNL.PROCESSID
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    private String processid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SV_SALE_CKPT_JRNL.PRDID
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    private String prdid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SV_SALE_CKPT_JRNL.CHKTOTAL
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    private Integer chktotal;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SV_SALE_CKPT_JRNL.CHKSUCC
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    private Integer chksucc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SV_SALE_CKPT_JRNL.CHKFAIL
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    private Integer chkfail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SV_SALE_CKPT_JRNL.OPERID
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    private String operid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SV_SALE_CKPT_JRNL.STARTDATE
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    private Date startdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SV_SALE_CKPT_JRNL.ENDDATE
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    private Date enddate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SV_SALE_CKPT_JRNL.ELAPSEDTIME
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    private Short elapsedtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SV_SALE_CKPT_JRNL.RECVERSION
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    private Long recversion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SV_SALE_CKPT_JRNL.REMARK
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SV_SALE_CKPT_JRNL.GUID
     *
     * @return the value of SV_SALE_CKPT_JRNL.GUID
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public String getGuid() {
        return guid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SV_SALE_CKPT_JRNL.GUID
     *
     * @param guid the value for SV_SALE_CKPT_JRNL.GUID
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public void setGuid(String guid) {
        this.guid = guid == null ? null : guid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SV_SALE_CKPT_JRNL.PROCESSID
     *
     * @return the value of SV_SALE_CKPT_JRNL.PROCESSID
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public String getProcessid() {
        return processid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SV_SALE_CKPT_JRNL.PROCESSID
     *
     * @param processid the value for SV_SALE_CKPT_JRNL.PROCESSID
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public void setProcessid(String processid) {
        this.processid = processid == null ? null : processid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SV_SALE_CKPT_JRNL.PRDID
     *
     * @return the value of SV_SALE_CKPT_JRNL.PRDID
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public String getPrdid() {
        return prdid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SV_SALE_CKPT_JRNL.PRDID
     *
     * @param prdid the value for SV_SALE_CKPT_JRNL.PRDID
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public void setPrdid(String prdid) {
        this.prdid = prdid == null ? null : prdid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SV_SALE_CKPT_JRNL.CHKTOTAL
     *
     * @return the value of SV_SALE_CKPT_JRNL.CHKTOTAL
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public Integer getChktotal() {
        return chktotal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SV_SALE_CKPT_JRNL.CHKTOTAL
     *
     * @param chktotal the value for SV_SALE_CKPT_JRNL.CHKTOTAL
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public void setChktotal(Integer chktotal) {
        this.chktotal = chktotal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SV_SALE_CKPT_JRNL.CHKSUCC
     *
     * @return the value of SV_SALE_CKPT_JRNL.CHKSUCC
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public Integer getChksucc() {
        return chksucc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SV_SALE_CKPT_JRNL.CHKSUCC
     *
     * @param chksucc the value for SV_SALE_CKPT_JRNL.CHKSUCC
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public void setChksucc(Integer chksucc) {
        this.chksucc = chksucc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SV_SALE_CKPT_JRNL.CHKFAIL
     *
     * @return the value of SV_SALE_CKPT_JRNL.CHKFAIL
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public Integer getChkfail() {
        return chkfail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SV_SALE_CKPT_JRNL.CHKFAIL
     *
     * @param chkfail the value for SV_SALE_CKPT_JRNL.CHKFAIL
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public void setChkfail(Integer chkfail) {
        this.chkfail = chkfail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SV_SALE_CKPT_JRNL.OPERID
     *
     * @return the value of SV_SALE_CKPT_JRNL.OPERID
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public String getOperid() {
        return operid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SV_SALE_CKPT_JRNL.OPERID
     *
     * @param operid the value for SV_SALE_CKPT_JRNL.OPERID
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public void setOperid(String operid) {
        this.operid = operid == null ? null : operid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SV_SALE_CKPT_JRNL.STARTDATE
     *
     * @return the value of SV_SALE_CKPT_JRNL.STARTDATE
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public Date getStartdate() {
        return startdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SV_SALE_CKPT_JRNL.STARTDATE
     *
     * @param startdate the value for SV_SALE_CKPT_JRNL.STARTDATE
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SV_SALE_CKPT_JRNL.ENDDATE
     *
     * @return the value of SV_SALE_CKPT_JRNL.ENDDATE
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public Date getEnddate() {
        return enddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SV_SALE_CKPT_JRNL.ENDDATE
     *
     * @param enddate the value for SV_SALE_CKPT_JRNL.ENDDATE
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SV_SALE_CKPT_JRNL.ELAPSEDTIME
     *
     * @return the value of SV_SALE_CKPT_JRNL.ELAPSEDTIME
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public Short getElapsedtime() {
        return elapsedtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SV_SALE_CKPT_JRNL.ELAPSEDTIME
     *
     * @param elapsedtime the value for SV_SALE_CKPT_JRNL.ELAPSEDTIME
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public void setElapsedtime(Short elapsedtime) {
        this.elapsedtime = elapsedtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SV_SALE_CKPT_JRNL.RECVERSION
     *
     * @return the value of SV_SALE_CKPT_JRNL.RECVERSION
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public Long getRecversion() {
        return recversion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SV_SALE_CKPT_JRNL.RECVERSION
     *
     * @param recversion the value for SV_SALE_CKPT_JRNL.RECVERSION
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public void setRecversion(Long recversion) {
        this.recversion = recversion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SV_SALE_CKPT_JRNL.REMARK
     *
     * @return the value of SV_SALE_CKPT_JRNL.REMARK
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SV_SALE_CKPT_JRNL.REMARK
     *
     * @param remark the value for SV_SALE_CKPT_JRNL.REMARK
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}