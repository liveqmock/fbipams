package pams.repository.model.telemarketing;

import pams.repository.model.Svintvinf;

import java.io.Serializable;

/**
 * ҵ�����۵Ļ������̳��� svintvinf ���绰��̸�� ԭ�򣺴˱���ֶ�ȫ������ҵ��ʵ�ʱ�ͼƻ���
 * User: zhanrui
 * Date: 11-3-20
 * Time: ����11:46
 * To change this template use File | Settings | File Templates.
 */
public class SalesInfoBean extends Svintvinf implements Serializable {
    //����
    String deptid;
    String deptname;

    String tellername;
    String prdname;
    String subprdname;

    public String getPrdname() {
        return prdname;
    }

    public void setPrdname(String prdname) {
        this.prdname = prdname;
    }

    public String getSubprdname() {
        return subprdname;
    }

    public void setSubprdname(String subprdname) {
        this.subprdname = subprdname;
    }

    public String getTellername() {
        return tellername;
    }

    public void setTellername(String tellername) {
        this.tellername = tellername;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
}
