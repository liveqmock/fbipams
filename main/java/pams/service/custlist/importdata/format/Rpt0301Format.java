package pams.service.custlist.importdata.format;

import org.springframework.stereotype.Component;
import pams.repository.model.SvClsCustinfo;
import pams.service.common.dataimport.DefaultFormat;

/**
 * 表三：持有理财卡但AUM不达标客户数据表。
 * User: zhanrui
 * Date: 12-12-19
 * Time: 下午2:21
 * To change this template use File | Settings | File Templates.
 */
@Component
public class Rpt0301Format extends DefaultFormat {
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
