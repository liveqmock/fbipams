package pams.service.effectcust;

import org.springframework.stereotype.Component;
import pams.repository.model.SvEclCustinfo;
import pams.service.common.dataimport.DefaultFormat;

/**
 * 有效客户拓展提升目标清单
 * 表1-9：格式统一
 * User: zhanrui
 * Date: 13-04-23
 * Time: 下午2:21
 * To change this template use File | Settings | File Templates.
 */
@Component
public class Rpt1001Format extends DefaultFormat {
    private String[] fieldNames = {
            "BankId",
            "BranchId",
            "CustId",
            "CustName",
            "CustAge",
            "ContactInfo1",
            "ContactInfo2",
            "ContactInfo3",
            "Occupation",
            "Jobtitle",
            "StsRiskRating",
            "StsOcrmAttention",
            "StsNetbankSign",
            "StsMtbankSign",
            "StsTelbankSign",
            "Aum",
            "BalDep",
            "BalFin",
            "BalFund",
            "BalInsure",
            "BalCts",
            "BalMetal"};
    private String[] fieldTypes = {
            "String",
            "String",
            "String",
            "String",
            "Short",
            "String",
            "String",
            "String",
            "String",
            "String",
            "String",
            "String",
            "String",
            "String",
            "String",
            "BigDecimal",
            "BigDecimal",
            "BigDecimal",
            "BigDecimal",
            "BigDecimal",
            "BigDecimal",
            "BigDecimal"};

    @Override
    public SvEclCustinfo parse(String line) throws Exception {
        String[] fields = line.split("\\|");
        SvEclCustinfo bean = new SvEclCustinfo();
        if (fields.length != fieldNames.length) {
            return null;
        }
        if (fields != null) {
            assembleBean(bean, fields, fieldNames, fieldTypes);
        }
        return bean;
    }
}
