package pams.view.report;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-2-3
 * Time: ÏÂÎç2:22
 * To change this template use File | Settings | File Templates.
 */
public class RptA06V1ResultBean implements Serializable {
    private String pkid;
    private String branchId;
    private String branchName;
    private String txnDate;
    private String account;
    private String custName;
    private String pdtId;
    private String pdtName;
    private String invStartDate;
    private String invEndDate;
    private String cfmAmt;
    private String txnChannel;
    private String processType;
    private String certNo;
    private String contactInfo;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getPdtId() {
        return pdtId;
    }

    public void setPdtId(String pdtId) {
        this.pdtId = pdtId;
    }

    public String getPdtName() {
        return pdtName;
    }

    public void setPdtName(String pdtName) {
        this.pdtName = pdtName;
    }

    public String getInvStartDate() {
        return invStartDate;
    }

    public void setInvStartDate(String invStartDate) {
        this.invStartDate = invStartDate;
    }

    public String getInvEndDate() {
        return invEndDate;
    }

    public void setInvEndDate(String invEndDate) {
        this.invEndDate = invEndDate;
    }

    public String getCfmAmt() {
        return cfmAmt;
    }

    public void setCfmAmt(String cfmAmt) {
        this.cfmAmt = cfmAmt;
    }

    public String getTxnChannel() {
        return txnChannel;
    }

    public void setTxnChannel(String txnChannel) {
        this.txnChannel = txnChannel;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }


    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }
}
