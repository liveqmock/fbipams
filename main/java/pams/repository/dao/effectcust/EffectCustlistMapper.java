package pams.repository.dao.effectcust;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import pams.repository.model.SvEclCustinfo;
import pams.repository.model.effectcust.EffectCustinfoVO;

import java.util.List;

/**
 * User: zhanrui
 * Date: 13-4-23
 * Time: обнГ8:22
 */
@Component
public interface EffectCustlistMapper {
    int insertBatch(List<SvEclCustinfo> beans);
    int deleteRecords(@Param("rpttype") String rpttype);

    List<SvEclCustinfo> selectCustlistRecordsByPageSize(EffectCustinfoVO bean);
    int countCustlistRecords(SvEclCustinfo bean);
}
