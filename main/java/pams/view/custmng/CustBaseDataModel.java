package pams.view.custmng;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.repository.dao.custlist.CustlistMapper;
import pams.repository.model.custlist.CustMngParam;
import pams.repository.model.custlist.CustMngVO;

import java.util.List;
import java.util.Map;

/**
 * User: zhanrui
 * Date: 2013-01-24
 */
public class CustBaseDataModel extends LazyDataModel<CustMngVO> {
    private static final Logger logger = LoggerFactory.getLogger(CustBaseDataModel.class);

    private CustMngParam paramBean;
    private CustlistMapper custlistMapper;

    public CustBaseDataModel(CustlistMapper custlistMapper, CustMngParam paramBean) {
        this.custlistMapper = custlistMapper;
        this.paramBean = paramBean;
    }

    @Override
    public List<CustMngVO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<CustMngVO> dataList;
        try {
            paramBean.setOffset(first);
            paramBean.setPagesize(first + pageSize);
            if (sortField != null) {
                paramBean.setSortField(changeBeanPropertyName2DBTableFieldName(sortField));
                if (sortOrder != null) {
                    if (sortOrder.compareTo(SortOrder.DESCENDING) == 0) {
                        paramBean.setSortOrder(" DESC ");
                    }
                }
            } else { //默认排序字段
                paramBean.setSortField("branch_id");
            }
            dataList = this.custlistMapper.selectCustBaseRecordsByPageSize(paramBean);
        } catch (Exception e) {
            logger.error("查询数据出现错误.", e);
            throw new RuntimeException(e);
        }

        if (super.getRowCount() <= 0) {
            int total = custlistMapper.countCustBaseRecords(paramBean);
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
