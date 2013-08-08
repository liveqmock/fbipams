package pams.repository.model.custlist;

import pams.repository.model.SvClsCustinfo;

import java.math.BigDecimal;

/**
 * 客户基本信息维护 客户经理分派等.
 * User: zhanrui
 * Date: 13-8-8
 * Time: 下午1:56
 */
public class CustListParam extends SvClsCustinfo {

    private BigDecimal startAum;
    private BigDecimal endAum;

    private int offset;
    private int pagesize;
    private String  sortField;
    private String  sortOrder;

    public BigDecimal getStartAum() {
        return startAum;
    }

    public void setStartAum(BigDecimal startAum) {
        this.startAum = startAum;
    }

    public BigDecimal getEndAum() {
        return endAum;
    }

    public void setEndAum(BigDecimal endAum) {
        this.endAum = endAum;
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
}
