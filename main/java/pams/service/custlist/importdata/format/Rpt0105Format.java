package pams.service.custlist.importdata.format;

import org.springframework.stereotype.Component;
import pams.repository.model.SvClsCustinfo;
import pams.service.custlist.importdata.DefaultFormat;

/**
 * ��һ���ٽ�ֵ�ͻ����ݱ� ���ȱ�
 ��������Ͻ����������񣬷ֱ�Ϊ��1��4��AUM��5��ͻ����ݱ���2��15��AUM��20�ͻ����ݱ���3��40���AUM��50��ͻ����ݱ�
 * User: zhanrui
 * Date: 12-12-19
 * Time: ����2:21
 * To change this template use File | Settings | File Templates.
 */
@Component
public class Rpt0105Format extends DefaultFormat {
    private String[] fieldNames = {"BranchId", "BranchName", "CustName", "CertType", "CertNo", "ContactInfo1", "ContactInfo2", "AumMonthCurr", "AumTimepointDep"};
    private String[] fieldTypes = {"String", "String", "String", "String", "String", "String", "String", "BigDecimal", "BigDecimal"};

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
