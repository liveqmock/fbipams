package pams.service.custlist.importdata.format;

import org.springframework.stereotype.Component;
import pams.repository.model.SvClsCustinfo;
import pams.service.common.dataimport.DefaultFormat;

/**
 * 表四：优质重点产品客户数据表。
  其中又下辖二个三级表格，分别为表1：个贷余额大于50万元客户数据表、表2：信用卡额度大于10000元客户数据表。
 * User: zhanrui
 * Date: 12-12-19
 * Time: 下午2:21
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
