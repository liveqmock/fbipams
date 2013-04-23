package pams.repository.model.effectcust;

import pams.repository.model.SvEclCustinfo;

/**
 * User: zhanrui
 * Date: 13-04-24
 * Time: ÏÂÎç1:56
 */
public class EffectCustinfoVO extends SvEclCustinfo {
    private int offset;
    private int pagesize;
    private String  sortField;
    private String  sortOrder;

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
