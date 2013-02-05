package pams.repository.model;

import java.math.BigDecimal;
import java.util.Date;

public class Svprdsalplan {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.GUID
     *
     * @mbggenerated Fri Apr 29 10:31:01 CST 2011
     */
    private String guid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.PRDID
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private String prdid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.TELLERID
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private String tellerid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.SUBPRDID
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private String subprdid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.SALESNUM1
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private Long salesnum1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.SALESAMT1
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private BigDecimal salesamt1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.SALESNUM2
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private Long salesnum2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.SALESAMT2
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private BigDecimal salesamt2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.SALESNUM3
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private Long salesnum3;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.SALESAMT3
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private BigDecimal salesamt3;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.CUSTOMERNAME
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private String customername;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.TXNDATE
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private String txndate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.TXNTIME
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private String txntime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.OPERID
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private String operid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.OPERDATE
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private Date operdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.RECVERSION
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private Long recversion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.REMARK
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.CERTTYPE
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private String certtype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVPRDSALPLAN.CERTNO
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    private String certno;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.GUID
     *
     * @return the value of SVPRDSALPLAN.GUID
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public String getGuid() {
        return guid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.GUID
     *
     * @param guid the value for SVPRDSALPLAN.GUID
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setGuid(String guid) {
        this.guid = guid == null ? null : guid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.PRDID
     *
     * @return the value of SVPRDSALPLAN.PRDID
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public String getPrdid() {
        return prdid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.PRDID
     *
     * @param prdid the value for SVPRDSALPLAN.PRDID
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setPrdid(String prdid) {
        this.prdid = prdid == null ? null : prdid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.TELLERID
     *
     * @return the value of SVPRDSALPLAN.TELLERID
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public String getTellerid() {
        return tellerid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.TELLERID
     *
     * @param tellerid the value for SVPRDSALPLAN.TELLERID
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setTellerid(String tellerid) {
        this.tellerid = tellerid == null ? null : tellerid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.SUBPRDID
     *
     * @return the value of SVPRDSALPLAN.SUBPRDID
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public String getSubprdid() {
        return subprdid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.SUBPRDID
     *
     * @param subprdid the value for SVPRDSALPLAN.SUBPRDID
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setSubprdid(String subprdid) {
        this.subprdid = subprdid == null ? null : subprdid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.SALESNUM1
     *
     * @return the value of SVPRDSALPLAN.SALESNUM1
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public Long getSalesnum1() {
        return salesnum1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.SALESNUM1
     *
     * @param salesnum1 the value for SVPRDSALPLAN.SALESNUM1
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setSalesnum1(Long salesnum1) {
        this.salesnum1 = salesnum1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.SALESAMT1
     *
     * @return the value of SVPRDSALPLAN.SALESAMT1
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public BigDecimal getSalesamt1() {
        return salesamt1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.SALESAMT1
     *
     * @param salesamt1 the value for SVPRDSALPLAN.SALESAMT1
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setSalesamt1(BigDecimal salesamt1) {
        this.salesamt1 = salesamt1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.SALESNUM2
     *
     * @return the value of SVPRDSALPLAN.SALESNUM2
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public Long getSalesnum2() {
        return salesnum2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.SALESNUM2
     *
     * @param salesnum2 the value for SVPRDSALPLAN.SALESNUM2
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setSalesnum2(Long salesnum2) {
        this.salesnum2 = salesnum2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.SALESAMT2
     *
     * @return the value of SVPRDSALPLAN.SALESAMT2
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public BigDecimal getSalesamt2() {
        return salesamt2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.SALESAMT2
     *
     * @param salesamt2 the value for SVPRDSALPLAN.SALESAMT2
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setSalesamt2(BigDecimal salesamt2) {
        this.salesamt2 = salesamt2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.SALESNUM3
     *
     * @return the value of SVPRDSALPLAN.SALESNUM3
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public Long getSalesnum3() {
        return salesnum3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.SALESNUM3
     *
     * @param salesnum3 the value for SVPRDSALPLAN.SALESNUM3
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setSalesnum3(Long salesnum3) {
        this.salesnum3 = salesnum3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.SALESAMT3
     *
     * @return the value of SVPRDSALPLAN.SALESAMT3
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public BigDecimal getSalesamt3() {
        return salesamt3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.SALESAMT3
     *
     * @param salesamt3 the value for SVPRDSALPLAN.SALESAMT3
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setSalesamt3(BigDecimal salesamt3) {
        this.salesamt3 = salesamt3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.CUSTOMERNAME
     *
     * @return the value of SVPRDSALPLAN.CUSTOMERNAME
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public String getCustomername() {
        return customername;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.CUSTOMERNAME
     *
     * @param customername the value for SVPRDSALPLAN.CUSTOMERNAME
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.TXNDATE
     *
     * @return the value of SVPRDSALPLAN.TXNDATE
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public String getTxndate() {
        return txndate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.TXNDATE
     *
     * @param txndate the value for SVPRDSALPLAN.TXNDATE
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setTxndate(String txndate) {
        this.txndate = txndate == null ? null : txndate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.TXNTIME
     *
     * @return the value of SVPRDSALPLAN.TXNTIME
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public String getTxntime() {
        return txntime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.TXNTIME
     *
     * @param txntime the value for SVPRDSALPLAN.TXNTIME
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setTxntime(String txntime) {
        this.txntime = txntime == null ? null : txntime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.OPERID
     *
     * @return the value of SVPRDSALPLAN.OPERID
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public String getOperid() {
        return operid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.OPERID
     *
     * @param operid the value for SVPRDSALPLAN.OPERID
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setOperid(String operid) {
        this.operid = operid == null ? null : operid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.OPERDATE
     *
     * @return the value of SVPRDSALPLAN.OPERDATE
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public Date getOperdate() {
        return operdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.OPERDATE
     *
     * @param operdate the value for SVPRDSALPLAN.OPERDATE
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setOperdate(Date operdate) {
        this.operdate = operdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.RECVERSION
     *
     * @return the value of SVPRDSALPLAN.RECVERSION
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public Long getRecversion() {
        return recversion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.RECVERSION
     *
     * @param recversion the value for SVPRDSALPLAN.RECVERSION
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setRecversion(Long recversion) {
        this.recversion = recversion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.REMARK
     *
     * @return the value of SVPRDSALPLAN.REMARK
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.REMARK
     *
     * @param remark the value for SVPRDSALPLAN.REMARK
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.CERTTYPE
     *
     * @return the value of SVPRDSALPLAN.CERTTYPE
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public String getCerttype() {
        return certtype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.CERTTYPE
     *
     * @param certtype the value for SVPRDSALPLAN.CERTTYPE
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setCerttype(String certtype) {
        this.certtype = certtype == null ? null : certtype.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVPRDSALPLAN.CERTNO
     *
     * @return the value of SVPRDSALPLAN.CERTNO
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public String getCertno() {
        return certno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVPRDSALPLAN.CERTNO
     *
     * @param certno the value for SVPRDSALPLAN.CERTNO
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    public void setCertno(String certno) {
        this.certno = certno == null ? null : certno.trim();
    }
}