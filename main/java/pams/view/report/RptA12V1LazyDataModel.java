package pams.view.report;

import org.apache.commons.beanutils.PropertyUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.repository.dao.report.OdsbRptMapper;
import pams.repository.model.OdsbCtsBal;
import pams.repository.model.report.BasePagedQryParamBean;

import java.util.List;
import java.util.Map;

/**
 * User: zhanrui
 * Date: 2013-03-28
 */
public class RptA12V1LazyDataModel extends LazyDataModel<OdsbCtsBal> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BasePagedQryParamBean paramBean;
    private OdsbRptMapper odsbRptMapper;

    public RptA12V1LazyDataModel(OdsbRptMapper odsbRptMapper, BasePagedQryParamBean paramBean) {
        this.odsbRptMapper = odsbRptMapper;
        this.paramBean = paramBean;
    }

    @Override
    public List<OdsbCtsBal> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<OdsbCtsBal> dataList;
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
            }else{ //Ĭ�������ֶ�
                param.setSortField("INST_NO");
            }
            dataList = this.odsbRptMapper.selectRptA12V1Records(param);
        } catch (Exception e) {
            logger.error("��ѯ���ݳ��ִ���.", e);
            throw new RuntimeException(e);
        }

        if (super.getRowCount() <= 0) {
            int total = odsbRptMapper.countRptA12V1Records(paramBean);
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
