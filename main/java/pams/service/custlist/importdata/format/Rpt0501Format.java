package pams.service.custlist.importdata.format;

import org.springframework.stereotype.Component;
import pams.repository.model.SvClsCustinfo;
import pams.service.common.dataimport.DefaultFormat;

/**
 * ���壺CTS�ͻ����ݱ�
 * User: zhanrui
 * Date: 12-12-19
 * Time: ����2:21
 * To change this template use File | Settings | File Templates.
 */
@Component
public class Rpt0501Format extends DefaultFormat {
    private String[] fieldNames = {"CustNo", "BranchId", "BranchName", "CustName", "CustGender", "ContactInfo1", "ContactInfo2", "AumMonthCurr", "AumTimepointDep", "OpenactBranchId", "OpenactBranchName", "CtsMarginBal"};
    private String[] fieldTypes = {"String", "String", "String", "String", "String", "String", "String", "BigDecimal", "BigDecimal", "String", "String", "BigDecimal"};

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
