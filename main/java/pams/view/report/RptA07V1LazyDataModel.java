package pams.view.report;

import org.apache.commons.beanutils.PropertyUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.repository.dao.report.OdsbRptMapper;
import pams.repository.model.OdsbStTconfirmTrad;
import pams.repository.model.report.BasePagedQryParamBean;

import java.util.List;
import java.util.Map;

/**
 * User: zhanrui
 * Date: 2013-01-24
 */
public class RptA07V1LazyDataModel extends LazyDataModel<OdsbStTconfirmTrad> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BasePagedQryParamBean paramBean;
    private OdsbRptMapper odsbRptMapper;

    public RptA07V1LazyDataModel(OdsbRptMapper odsbRptMapper, BasePagedQryParamBean paramBean) {
        this.odsbRptMapper = odsbRptMapper;
        this.paramBean = paramBean;
    }

    @Override
    public List<OdsbStTconfirmTrad> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<OdsbStTconfirmTrad> dataList;
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
                param.setSortField("CUST_OPEN_NODE");
            }
            dataList = this.odsbRptMapper.selectRptA07V1Records(param);
        } catch (Exception e) {
            logger.error("��ѯ���ݳ��ִ���.", e);
            throw new RuntimeException(e);
        }

        if (super.getRowCount() <= 0) {
            int total = odsbRptMapper.countRptA07V1Records(paramBean);
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
