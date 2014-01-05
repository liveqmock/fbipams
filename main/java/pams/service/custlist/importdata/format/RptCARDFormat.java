package pams.service.custlist.importdata.format;

import org.springframework.stereotype.Component;
import pams.repository.model.ClsRptdata;
import pams.service.common.dataimport.DefaultClsRptDataFormat;

/**
 * User: zhanrui
 * Date: 20140105
 */
@Component
public class RptCARDFormat extends DefaultClsRptDataFormat {
    private String[] fieldNames = {"FincrdCardno", "FincrdBalMad", "FincrdBalTp", "FincrdTxnCntM6", "FincrdTxnAmtM6"};
    private String[] fieldTypes = {"String", "BigDecimal", "BigDecimal", "Integer", "BigDecimal"};

    @Override
    public ClsRptdata parse(String line) throws Exception {
        return parse(line, fieldNames, fieldTypes);
    }
}
