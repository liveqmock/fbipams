package pams.repository.dao.saledata;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 对私业务营销数据检核点规则处理
 * User: zhanrui
 * Date: 2013-7-29
 * Time: 下午8:22
 */
@Component
public interface CheckpointMapper {

    @Select(value = "select distinct(prdid) from SV_SALE_CKPT_PRG t order by prdid")
    List<String> selectAllNeedCheckPrdid();

}
