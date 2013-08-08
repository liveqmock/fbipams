package pams.view.custlist;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.repository.dao.custlist.CustlistMapper;
import pams.repository.model.SvClsCustinfo;
import pams.repository.model.custlist.CustListParam;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 12-12-24
 * Time: 上午7:46
 */
public class LazyRptDataModel extends LazyDataModel<SvClsCustinfo> {
    private static final Logger logger = LoggerFactory.getLogger(LazyRptDataModel.class);

    private CustListParam paramBean;
    private CustlistMapper custlistMapper;

    public LazyRptDataModel(CustlistMapper custlistMapper, CustListParam paramBean) {
        this.custlistMapper = custlistMapper;
        this.paramBean = paramBean;
    }

    @Override
    public List<SvClsCustinfo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<SvClsCustinfo> dataList;
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
            }else{ //默认排序字段
                paramBean.setSortField("branch_id");
            }
            dataList = this.custlistMapper.selectCustlistRecordsByPageSize(paramBean);
        } catch (Exception e) {
            logger.error("查询数据出现错误.", e);
            throw new RuntimeException(e);
        }

        if (super.getRowCount() <= 0) {
            int total = custlistMapper.countCustlistRecords(paramBean);
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
