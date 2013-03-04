package pams.view.saledata;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.repository.dao.saledata.SaleDataMapper;
import pams.repository.model.saledata.SaleDataQryParamBean;
import pams.repository.model.saledata.SaleDataVO;

import java.util.List;
import java.util.Map;

/**
 * User: zhanrui
 * Date: 2013-03-03
 */
public class SaleDataQryModel extends LazyDataModel<SaleDataVO> {
    private static final Logger logger = LoggerFactory.getLogger(SaleDataQryModel.class);

    private SaleDataQryParamBean paramBean;
    private SaleDataMapper saleDataMapper;
    //private String tellerId;

    public SaleDataQryModel(SaleDataMapper mapper, SaleDataQryParamBean paramBean) {
        this.saleDataMapper = mapper;
        this.paramBean = paramBean;
        //this.tellerId = tellerId;
    }

    @Override
    public List<SaleDataVO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<SaleDataVO> dataList;
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
            dataList = this.saleDataMapper.selectSaleDataRecords(paramBean);
        } catch (Exception e) {
            logger.error("查询数据出现错误.", e);
            throw new RuntimeException(e);
        }

        if (super.getRowCount() <= 0) {
            int total = saleDataMapper.countSaleDataRecords(paramBean);
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
