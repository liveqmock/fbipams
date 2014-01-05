package pams.service.custlist.importdata.format;

import org.springframework.stereotype.Component;
import pams.repository.model.ClsRptdata;
import pams.service.common.dataimport.DefaultClsRptDataFormat;

/**
 * User: zhanrui
 * Date: 20140105
 */
@Component
public class RptCARD3Format extends DefaultClsRptDataFormat {
    private String[] fieldNames = {"FincrdCardno", "FincrdBalMad", "FincrdBalTp", "FincrdTxnCntM6", "FincrdTxnAmtM6", "BaseOrigBranchName", "BaseOrigBranchId",};
    private String[] fieldTypes = {"String", "BigDecimal", "BigDecimal", "Integer", "BigDecimal", "String", "String"};

    @Override
    public ClsRptdata parse(String line) throws Exception {
        return parse(line, fieldNames, fieldTypes);
    }
}
