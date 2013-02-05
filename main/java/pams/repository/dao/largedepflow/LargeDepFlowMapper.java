package pams.repository.dao.largedepflow;

import org.springframework.stereotype.Component;
import pams.repository.model.OdsbLargedepFlow;
import pams.repository.model.largedepflow.LargeDepFlowVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-1-24
 * Time: обнГ1:40
 * To change this template use File | Settings | File Templates.
 */
@Component
public interface LargeDepFlowMapper {
    int countLargeDepFlowRecords(LargeDepFlowVO paramBean);
    List<OdsbLargedepFlow> selectLargeDepFlowRecords(LargeDepFlowVO paramBean);
    List<OdsbLargedepFlow> selectPagedLargeDepFlowRecords(LargeDepFlowVO paramBean);
}
