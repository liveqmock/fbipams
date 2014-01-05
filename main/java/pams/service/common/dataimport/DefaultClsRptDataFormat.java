package pams.service.common.dataimport;

import org.apache.commons.lang.ArrayUtils;
import pams.repository.model.ClsRptdata;

/**
 * 新客户名单制报表
 * User: zhanrui
 * Date: 20140105
 */
public abstract class DefaultClsRptDataFormat extends DefaultFormat {
    protected String[] commonFieldNames = {"BaseBranchName",	"BaseBranchId",	"BaseLevelSys",	"BaseLevelManual",	"BaseCustnoEcif",	"BaseCustname",	"BasePhone1",	"BasePhone2",	"AssetAumMad",	"AssetAumTp",	"AssetBalDepSa",	"AssetBalDepTd",	"AssetBalFund",	"AssetBalActMetal",	"AssetBalRealMetal",	"AssetBalBnd",	"AssetBalFin",	"AssetBalCts",	"AssetBalInsure",	"AssetBalInsureBuy",	"AssetBalFinOth",	"LoanBalTp",	"TxnSaveAmt",	"TxnSaveTimes",	"TxnDrawAmt",	"TxnDrawTimes",	"TxnBankcardTimes",	"TxnBankcardAmt",	"TxnCrcardTimes",	"TxnCrcardAmt",	"SignNetBank",	"SignMobiBank",	"SignTelBank"};
    protected String[] commonFieldTypes = {"String",	"String",	"String",	"String",	"String",	"String",	"String",	"String",	"BigDecimal",	"BigDecimal",	"BigDecimal",	"BigDecimal",	"BigDecimal",	"BigDecimal",	"BigDecimal",	"BigDecimal",	"BigDecimal",	"BigDecimal",	"BigDecimal",	"BigDecimal",	"BigDecimal",	"BigDecimal",	"BigDecimal",	"Integer",	"BigDecimal",	"Integer",	"Integer",	"BigDecimal",	"Integer",	"BigDecimal",	"String",	"String",	"String"};


    protected ClsRptdata parse(String line, String[] fieldNames, String[] fieldTypes) throws Exception {
        String[] fields = line.split("\\|");
        ClsRptdata bean = new ClsRptdata();
        if (fields != null) {
            if (fieldNames.length > 0) {
                assembleBean(bean, fields, (String[])ArrayUtils.addAll(commonFieldNames, fieldNames), (String[])ArrayUtils.addAll(commonFieldTypes, fieldTypes));
            } else {
                assembleBean(bean, fields, commonFieldNames, commonFieldTypes);
            }
        }
        return bean;
    }

}
