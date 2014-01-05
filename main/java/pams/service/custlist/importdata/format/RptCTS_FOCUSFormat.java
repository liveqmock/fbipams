package pams.service.custlist.importdata.format;

import org.springframework.stereotype.Component;
import pams.repository.model.ClsRptdata;
import pams.service.common.dataimport.DefaultClsRptDataFormat;

/**
 * User: zhanrui
 * Date: 20140105
 */
@Component
public class RptCTS_FOCUSFormat extends DefaultClsRptDataFormat {
    private String[] fieldNames = {
            "CtsSignBranchId",
            "CtsSignActno",
            "CtsAmtB2sCm",
            "CtsAmtS2bCm",
            "CtsBalMargin",
            "CtsAmtBuyCm",
            "CtsAmtSellCm"
    };
    private String[] fieldTypes = {
            "String",
            "String",
            "BigDecimal",
            "BigDecimal",
            "BigDecimal",
            "BigDecimal",
            "BigDecimal"
    };

    @Override
    public ClsRptdata parse(String line) throws Exception {
        return parse(line, fieldNames, fieldTypes);
    }
}
