package pams.repository.model.custlist;

import pams.repository.model.SvClsCustinfo;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 12-12-24
 * Time: ÏÂÎç1:56
 * To change this template use File | Settings | File Templates.
 */
public class CustinfoVO extends SvClsCustinfo {
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
