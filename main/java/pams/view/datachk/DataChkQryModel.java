package pams.view.datachk;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.repository.dao.saledata.SaleDataChkMapper;
import pams.repository.model.saledata.SaleDataChkVO;
import pams.repository.model.saledata.SaleDataQryParamBean;

import java.util.List;
import java.util.Map;

/**
 * 数据检核
 * User: zhanrui
 * Date: 2013-08-03
 */
public class DataChkQryModel extends LazyDataModel<SaleDataChkVO> {
    private static final Logger logger = LoggerFactory.getLogger(DataChkQryModel.class);

    private SaleDataQryParamBean paramBean;
    private SaleDataChkMapper saleDataChkMapper;
    //private String tellerId;

    public DataChkQryModel(SaleDataChkMapper mapper, SaleDataQryParamBean paramBean) {
        this.saleDataChkMapper = mapper;
        this.paramBean = paramBean;
        //this.tellerId = tellerId;
    }

    @Override
    public List<SaleDataChkVO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<SaleDataChkVO> dataList;
        try {
            paramBean.setOffset(first);
            paramBean.setPagesize(first + pageSize);
            //paramBean.setTellerId(tellerId);
            if (sortField != null) {
                paramBean.setSortField(changeBeanPropertyName2DBTableFieldName(sortField));
                if (sortOrder != null) {
                    if (sortOrder.compareTo(SortOrder.DESCENDING) == 0) {
                        paramBean.setSortOrder(" DESC ");
                    }
                }
            } else { //默认排序字段
                //paramBean.setSortField("branch_id");
            }
            dataList = this.saleDataChkMapper.selectSaleDataRecords(paramBean);
        } catch (Exception e) {
            logger.error("查询数据出现错误.", e);
            throw new RuntimeException(e);
        }

        if (super.getRowCount() <= 0) {
            int total = saleDataChkMapper.countSaleDataRecords(paramBean);
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
            } else {
                sb.append(String.valueOf(ch[i]).toLowerCase());
            }
        }
        return sb.toString();
    }
}
