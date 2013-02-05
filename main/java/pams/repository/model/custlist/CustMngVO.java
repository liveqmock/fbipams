package pams.repository.model.custlist;

import pams.repository.model.SvCmsCustbase;

import java.math.BigDecimal;

/**
 * 客户基本信息维护 客户经理分派等.
 * User: zhanrui
 * Date: 12-12-24
 * Time: 下午1:56
 */
public class CustMngVO extends SvCmsCustbase {
    private BigDecimal aumMonthCurr;
    private BigDecimal aumMonthLast;
    private BigDecimal aumTimepointDep;
    private BigDecimal loanBal;
    private BigDecimal ctsMarginBal;

    private String custmgrName;

    public BigDecimal getAumMonthCurr() {
        return aumMonthCurr;
    }

    public void setAumMonthCurr(BigDecimal aumMonthCurr) {
        this.aumMonthCurr = aumMonthCurr;
    }

    public BigDecimal getAumMonthLast() {
        return aumMonthLast;
    }

    public void setAumMonthLast(BigDecimal aumMonthLast) {
        this.aumMonthLast = aumMonthLast;
    }

    public BigDecimal getAumTimepointDep() {
        return aumTimepointDep;
    }

    public void setAumTimepointDep(BigDecimal aumTimepointDep) {
        this.aumTimepointDep = aumTimepointDep;
    }

    public BigDecimal getLoanBal() {
        return loanBal;
    }

    public void setLoanBal(BigDecimal loanBal) {
        this.loanBal = loanBal;
    }


    public BigDecimal getCtsMarginBal() {
        return ctsMarginBal;
    }

    public void setCtsMarginBal(BigDecimal ctsMarginBal) {
        this.ctsMarginBal = ctsMarginBal;
    }

    public String getCustmgrName() {
        return custmgrName;
    }

    public void setCustmgrName(String custmgrName) {
        this.custmgrName = custmgrName;
    }
}
