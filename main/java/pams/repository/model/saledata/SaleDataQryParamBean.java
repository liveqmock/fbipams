package pams.repository.model.saledata;

import java.math.BigDecimal;

/**
 * User: zhanrui
 * Date: 13-1-24
 * Time: ÏÂÎç1:41
 */
public class SaleDataQryParamBean {
    private String branchId;
    private String custName;
    private String certNo;
    private String startDate;
    private String endDate;
    private BigDecimal startAmt;
    private BigDecimal endAmt;
    private String prdId;
    private String tellerId;

    private String checkFlag;
    private String archiveFlag;
    private String reviewFlag;
    private String archiveStartDate;
    private String archiveEndDate;
    private String checkStartDate;
    private String checkEndDate;
    private String reviewStartDate;
    private String reviewEndDate;
    private String reviewReplyStartDate;
    private String reviewReplyEndDate;

    private int offset;
    private int pagesize;
    private String  sortField;
    private String  sortOrder;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getStartAmt() {
        return startAmt;
    }

    public void setStartAmt(BigDecimal startAmt) {
        this.startAmt = startAmt;
    }

    public BigDecimal getEndAmt() {
        return endAmt;
    }

    public void setEndAmt(BigDecimal endAmt) {
        this.endAmt = endAmt;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getPrdId() {
        return prdId;
    }

    public void setPrdId(String prdId) {
        this.prdId = prdId;
    }

    public String getTellerId() {
        return tellerId;
    }

    public void setTellerId(String tellerId) {
        this.tellerId = tellerId;
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    public String getArchiveFlag() {
        return archiveFlag;
    }

    public void setArchiveFlag(String archiveFlag) {
        this.archiveFlag = archiveFlag;
    }

    public String getReviewFlag() {
        return reviewFlag;
    }

    public void setReviewFlag(String reviewFlag) {
        this.reviewFlag = reviewFlag;
    }

    public String getArchiveStartDate() {
        return archiveStartDate;
    }

    public void setArchiveStartDate(String archiveStartDate) {
        this.archiveStartDate = archiveStartDate;
    }

    public String getArchiveEndDate() {
        return archiveEndDate;
    }

    public void setArchiveEndDate(String archiveEndDate) {
        this.archiveEndDate = archiveEndDate;
    }

    public String getCheckStartDate() {
        return checkStartDate;
    }

    public void setCheckStartDate(String checkStartDate) {
        this.checkStartDate = checkStartDate;
    }

    public String getCheckEndDate() {
        return checkEndDate;
    }

    public void setCheckEndDate(String checkEndDate) {
        this.checkEndDate = checkEndDate;
    }

    public String getReviewStartDate() {
        return reviewStartDate;
    }

    public void setReviewStartDate(String reviewStartDate) {
        this.reviewStartDate = reviewStartDate;
    }

    public String getReviewEndDate() {
        return reviewEndDate;
    }

    public void setReviewEndDate(String reviewEndDate) {
        this.reviewEndDate = reviewEndDate;
    }

    public String getReviewReplyStartDate() {
        return reviewReplyStartDate;
    }

    public void setReviewReplyStartDate(String reviewReplyStartDate) {
        this.reviewReplyStartDate = reviewReplyStartDate;
    }

    public String getReviewReplyEndDate() {
        return reviewReplyEndDate;
    }

    public void setReviewReplyEndDate(String reviewReplyEndDate) {
        this.reviewReplyEndDate = reviewReplyEndDate;
    }
}
