package pams.datachkserver.api.checkpoint.sepcheckpoint;

import pams.datachkserver.api.checkpoint.CheckPointRequest;

import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-8-3
 * Time: ÉÏÎç10:30
 * To change this template use File | Settings | File Templates.
 */
public class SepCheckPointRequest implements CheckPointRequest {
    private String prdid;
    private String subPrdid;
    private String operid;
    private String chkPeriod;
    private String ckptParam;

    private String custNo;
    private String certType;
    private String certNo;

    private String txnDate;
    private String txnTime;

    private Long salesNum1;
    private BigDecimal salesAmt1;
    private Long salesNum2;
    private BigDecimal salesAmt2;
    private Long salesNum3;
    private BigDecimal salesAmt3;

    private String remark;



    public String getPrdid() {
        return prdid;
    }

    public void setPrdid(String prdid) {
        this.prdid = prdid;
    }

    public String getSubPrdid() {
        return subPrdid;
    }

    public void setSubPrdid(String subPrdid) {
        this.subPrdid = subPrdid;
    }

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public String getChkPeriod() {
        return chkPeriod;
    }

    public void setChkPeriod(String chkPeriod) {
        this.chkPeriod = chkPeriod;
    }

    public String getCkptParam() {
        return ckptParam;
    }

    public void setCkptParam(String ckptParam) {
        this.ckptParam = ckptParam;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }

    public Long getSalesNum1() {
        return salesNum1;
    }

    public void setSalesNum1(Long salesNum1) {
        this.salesNum1 = salesNum1;
    }

    public BigDecimal getSalesAmt1() {
        return salesAmt1;
    }

    public void setSalesAmt1(BigDecimal salesAmt1) {
        this.salesAmt1 = salesAmt1;
    }

    public Long getSalesNum2() {
        return salesNum2;
    }

    public void setSalesNum2(Long salesNum2) {
        this.salesNum2 = salesNum2;
    }

    public BigDecimal getSalesAmt2() {
        return salesAmt2;
    }

    public void setSalesAmt2(BigDecimal salesAmt2) {
        this.salesAmt2 = salesAmt2;
    }

    public Long getSalesNum3() {
        return salesNum3;
    }

    public void setSalesNum3(Long salesNum3) {
        this.salesNum3 = salesNum3;
    }

    public BigDecimal getSalesAmt3() {
        return salesAmt3;
    }

    public void setSalesAmt3(BigDecimal salesAmt3) {
        this.salesAmt3 = salesAmt3;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    //==========================
    @Override
    public Object getAttribute(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setAttribute(String name, Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeAttribute(String name) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ServletContext getServletContext() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getParameter(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String[] getParameterValues(String name) {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
