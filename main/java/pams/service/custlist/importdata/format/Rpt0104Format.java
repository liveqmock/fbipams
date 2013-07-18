package pams.service.custlist.importdata.format;

import org.springframework.stereotype.Component;
import pams.repository.model.SvClsCustinfo;
import pams.service.common.dataimport.DefaultFormat;

/**
 * 表一：临界值客户数据表 季度表。
 其中又下辖三个三级表格，分别为表1：4≤AUM＜5万客户数据表、表2：15≤AUM＜20客户数据表、表3：40万≤AUM＜50万客户数据表。
 * User: zhanrui
 * Date: 12-12-19
 * Time: 下午2:21
 * To change this template use File | Settings | File Templates.
 */
@Component
public class Rpt0104Format extends DefaultFormat {
    private String[] fieldNames = {"CustNo", "OpenactBranchId", "OpenactBranchName","BranchId", "BranchName", "CustName", "CustGender", "ContactInfo1", "ContactInfo2", "AumMonthCurr", "AumTimepointDep", "AumMonthLast"};
    private String[] fieldTypes = {"String", "String", "String", "String", "String", "String", "String", "String", "String", "BigDecimal", "BigDecimal", "BigDecimal"};

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
