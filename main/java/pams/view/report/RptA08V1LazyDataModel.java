package pams.view.report;

import org.apache.commons.beanutils.PropertyUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.repository.dao.report.OdsbRptMapper;
import pams.repository.model.OdsbChnTrad;
import pams.repository.model.report.BasePagedQryParamBean;

import java.util.List;
import java.util.Map;

/**
 * User: zhanrui
 * Date: 2013-02-22
 */
public class RptA08V1LazyDataModel extends LazyDataModel<OdsbChnTrad> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BasePagedQryParamBean paramBean;
    private OdsbRptMapper odsbRptMapper;

    public RptA08V1LazyDataModel(OdsbRptMapper odsbRptMapper, BasePagedQryParamBean paramBean) {
        this.odsbRptMapper = odsbRptMapper;
        this.paramBean = paramBean;
    }

    @Override
    public List<OdsbChnTrad> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<OdsbChnTrad> dataList;
        try {
            BasePagedQryParamBean param = new BasePagedQryParamBean();
            PropertyUtils.copyProperties(param, paramBean);
            param.setOffset(first);
            param.setPagesize(first + pageSize);
            if (sortField != null) {
                param.setSortField(changeBeanPropertyName2DBTableFieldName(sortField));
                if (sortOrder != null) {
                    if (sortOrder.compareTo(SortOrder.DESCENDING) == 0) {
                           param.setSortOrder(" DESC ");
                    }
                }
            }else{ //默认排序字段
                param.setSortField("CHN_NO");
            }
            dataList = this.odsbRptMapper.selectRptA08V1Records(param);
        } catch (Exception e) {
            logger.error("查询数据出现错误.", e);
            throw new RuntimeException(e);
        }

        if (super.getRowCount() <= 0) {
            int total = odsbRptMapper.countRptA08V1Records(paramBean);
            this.setRowCount(total);
        }
        this.setPageSize(pageSize);
        return dataList;
    }

    private String changeBeanPropertyName2DBTableFieldName(String propertyName) {
        char[] ch = propertyName.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < propertyName.length(); i++) {
            if ('A' <= ch[i] && ch[i] <= 'Z') {
                sb.append("_");
                sb.append(String.valueOf(ch[i]).toLowerCase());
            }else{
                sb.append(String.valueOf(ch[i]).toLowerCase());
            }
        }
        return sb.toString();
    }
}
