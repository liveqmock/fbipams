package pams.view.effectcust;

import org.apache.commons.beanutils.PropertyUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.repository.dao.effectcust.EffectCustlistMapper;
import pams.repository.model.SvEclCustinfo;
import pams.repository.model.effectcust.EffectCustinfoVO;

import java.util.List;
import java.util.Map;

/**
 * User: zhanrui
 * Date: 13-4-23
 * Time: 上午7:46
 */
public class LazyRptDataModel extends LazyDataModel<SvEclCustinfo> {
    private static final Logger logger = LoggerFactory.getLogger(LazyRptDataModel.class);

    private SvEclCustinfo paramBean;
    private EffectCustlistMapper custlistMapper;

    public LazyRptDataModel(EffectCustlistMapper custlistMapper, SvEclCustinfo paramBean) {
        this.custlistMapper = custlistMapper;
        this.paramBean = paramBean;
    }

    @Override
    public List<SvEclCustinfo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<SvEclCustinfo> dataList;
        try {
            EffectCustinfoVO vo = new EffectCustinfoVO();
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
                vo.setSortField("branch_id");
            }
            dataList = this.custlistMapper.selectCustlistRecordsByPageSize(vo);
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
