package pams.view.userdefrpt;

import org.apache.commons.beanutils.PropertyUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.repository.dao.userdefrpt.UserDefRptMapper;
import pams.repository.model.ClsUdRptdata;
import pams.repository.model.userdefrpt.UserDefRptVO;

import java.util.List;
import java.util.Map;

/**
 * User: zhanrui
 * Date: 2013-01-24
 */
public class UserDefRptLazyDataModel extends LazyDataModel<ClsUdRptdata> {
    private static final Logger logger = LoggerFactory.getLogger(UserDefRptLazyDataModel.class);

    private UserDefRptVO paramBean;
    private UserDefRptMapper dbMapper;

    public UserDefRptLazyDataModel(UserDefRptMapper dbMapper, UserDefRptVO paramBean) {
        this.dbMapper = dbMapper;
        this.paramBean = paramBean;
    }

    @Override
    public List<ClsUdRptdata> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<ClsUdRptdata> dataList;
        try {
            UserDefRptVO vo = new UserDefRptVO();
            PropertyUtils.copyProperties(vo, paramBean);
            vo.setOffset(first);
            vo.setPagesize(first + pageSize);
            if (sortField != null) {
                vo.setSortField(changeBeanPropertyName2DBTableFieldName(sortField));
                if (sortOrder != null) {
                    if (sortOrder.compareTo(SortOrder.DESCENDING) == 0) {
                           vo.setSortOrder(" DESC ");
                    }
                }
            }else{ //默认排序字段
                vo.setSortField("BRANCHID");
//                vo.setSortField("1");
            }
            dataList = this.dbMapper.selectPagedRecords(vo);
        } catch (Exception e) {
            logger.error("查询数据出现错误.", e);
            throw new RuntimeException(e);
        }

        if (super.getRowCount() <= 0) {
            int total = dbMapper.countRecords(paramBean);
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
