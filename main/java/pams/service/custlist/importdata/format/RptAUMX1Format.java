package pams.service.custlist.importdata.format;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;
import pams.repository.model.ClsRptdata;
import pams.service.common.dataimport.DefaultFormat;

/**
 * User: zhanrui
 * Date: 20140126
 */
@Component
public class RptAUMX1Format extends DefaultFormat {
    private String[] fieldNames = {};
    private String[] fieldTypes = {};

    private String[] commonFieldNames = {"BaseBranchId", "BaseCustnoEcif", "BaseCustname", "AssetAumTp", "SignNetBank", "SignMobiBank"};
    private String[] commonFieldTypes = {"String", "String", "String", "BigDecimal", "String", "String"};

    @Override
    public ClsRptdata parse(String line) throws Exception {
        return parse(line, fieldNames, fieldTypes);
    }

    private ClsRptdata parse(String line, String[] fieldNames, String[] fieldTypes) throws Exception {
        String[] fields = line.split("\\|");
        ClsRptdata bean = new ClsRptdata();
        if (fields != null) {
            if (fieldNames.length > 0) {
                assembleBean(bean, fields, (String[]) ArrayUtils.addAll(commonFieldNames, fieldNames), (String[]) ArrayUtils.addAll(commonFieldTypes, fieldTypes));
            } else {
                assembleBean(bean, fields, commonFieldNames, commonFieldTypes);
            }
        }
        return bean;
    }

}
