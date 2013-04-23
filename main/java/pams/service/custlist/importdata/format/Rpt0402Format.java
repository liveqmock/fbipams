package pams.service.custlist.importdata.format;

import org.springframework.stereotype.Component;
import pams.repository.model.SvClsCustinfo;
import pams.service.common.dataimport.DefaultFormat;

/**
 * ���ģ������ص��Ʒ�ͻ����ݱ�
  ��������Ͻ����������񣬷ֱ�Ϊ��1������������50��Ԫ�ͻ����ݱ���2�����ÿ���ȴ���10000Ԫ�ͻ����ݱ�
 * User: zhanrui
 * Date: 12-12-19
 * Time: ����2:21
 * To change this template use File | Settings | File Templates.
 */
@Component
public class Rpt0402Format extends DefaultFormat {
    private String[] fieldNames = {"BranchId", "BranchName", "CustName", "CertType", "CertNo", "ContactInfo1", "ContactInfo2", "AumMonthCurr", "AumTimepointDep", "LoanBal"};
    private String[] fieldTypes = {"String", "String", "String", "String", "String", "String", "String", "BigDecimal", "BigDecimal", "BigDecimal"};

    @Override
    public SvClsCustinfo parse(String line) throws Exception {
        String[] fields = line.split("\\|");
        SvClsCustinfo bean = new SvClsCustinfo();
        if (fields != null) {
            assembleBean(bean, fields, fieldNames, fieldTypes);
        }
        return bean;
    }
}
