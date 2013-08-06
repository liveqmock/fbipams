package pams.batch.saleeventcheck.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pams.repository.dao.SvSaleCkptPrgMapper;
import pams.repository.dao.saledata.CheckpointMapper;
import pams.repository.model.SvSaleCkptPrg;
import pams.repository.model.SvSaleCkptPrgExample;

import java.util.List;

/**
 * 检核规则获取处理
 * User: zhanrui
 * Date: 2013-7-28
 * Time: 下午10:54
 */
@Service
public class CheckRulesService {
    @Autowired
    private CheckpointMapper checkpointMapper;
    @Autowired
    private SvSaleCkptPrgMapper svSaleCkptPrgMapper;

    List<String> selectAllNeedCheckPrdid() {
        return checkpointMapper.selectAllNeedCheckPrdid();
    }

    List<SvSaleCkptPrg> selectCheckpointHandler(String prdid) {
        if (prdid == null) {
            throw new IllegalArgumentException("Prdid cannot be null!");
        }
        SvSaleCkptPrgExample example = new SvSaleCkptPrgExample();
        example.createCriteria().andPrdidEqualTo(prdid)
                .andValidflagEqualTo("1");
        example.setOrderByClause(" seqno ");
        return svSaleCkptPrgMapper.selectByExample(example);
    }

    List<SvSaleCkptPrg> selectCheckpointHandler(String prdid, String subprdid) {
        if (prdid == null || subprdid == null) {
            throw new IllegalArgumentException("Prdid and subprdid cannot be null!");
        }
        SvSaleCkptPrgExample example = new SvSaleCkptPrgExample();
        example.createCriteria().andPrdidEqualTo(prdid).andSubprdidEqualTo(subprdid)
                .andValidflagEqualTo("1");
        example.setOrderByClause(" seqno ");
        return svSaleCkptPrgMapper.selectByExample(example);
    }
}
