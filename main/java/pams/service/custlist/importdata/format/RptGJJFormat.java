package pams.service.custlist.importdata.format;

import org.springframework.stereotype.Component;
import pams.repository.model.ClsRptdata;
import pams.service.common.dataimport.DefaultClsRptDataFormat;

/**
 * User: zhanrui
 * Date: 20140105
 */
@Component
public class RptGJJFormat extends DefaultClsRptDataFormat {
    private String[] fieldNames = {"GjjAumMad","GjjAumTp","GjjAmtAddCm"};
    private String[] fieldTypes = {"BigDecimal", "BigDecimal", "BigDecimal"};

    @Override
    public ClsRptdata parse(String line) throws Exception {
        return parse(line, fieldNames, fieldTypes);
    }
}
