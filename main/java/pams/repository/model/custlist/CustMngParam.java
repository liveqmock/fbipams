package pams.repository.model.custlist;

import pams.repository.model.SvCmsCustbase;

/**
 * �ͻ�������Ϣά�� �ͻ�������ɵ�.
 * User: zhanrui
 * Date: 12-12-24
 * Time: ����1:56
 */
public class CustMngParam extends SvCmsCustbase {

    //�Ƿ���ʾ�ѷ��ɿͻ�����ļ�¼ false-����ʾ  true-��ʾ
    private boolean hideDispatchedFlag;
    private int offset;
    private int pagesize;
    private String  sortField;
    private String  sortOrder;

    String[] custNameList;

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


    public boolean isHideDispatchedFlag() {
        return hideDispatchedFlag;
    }

    public void setHideDispatchedFlag(boolean hideDispatchedFlag) {
        this.hideDispatchedFlag = hideDispatchedFlag;
    }

    public String[] getCustNameList() {
        return custNameList;
    }

    public void setCustNameList(String[] custNameList) {
        this.custNameList = custNameList;
    }
}
