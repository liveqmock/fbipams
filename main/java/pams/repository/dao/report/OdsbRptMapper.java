package pams.repository.dao.report;

import org.springframework.stereotype.Component;
import pams.repository.model.*;
import pams.repository.model.report.BasePagedQryParamBean;

import java.util.List;

/**
 * User: zhanrui
 * Date: 12-12-19
 * Time: 下午8:22
 */
@Component
public interface OdsbRptMapper {
    //综合报表：基金成交客户明细 RPTA06V1
    int countRptA07V1Records(BasePagedQryParamBean paramBean);
    List<OdsbStTconfirmTrad> selectRptA07V1Records(BasePagedQryParamBean paramBean);

    //综合报表：渠道交易情况数据 RPTA08V1
    int countRptA08V1Records(BasePagedQryParamBean paramBean);
    List<OdsbChnTrad> selectRptA08V1Records(BasePagedQryParamBean paramBean);

    //综合报表：代发工资数据 RPTA09V1
    int countRptA09V1Records(BasePagedQryParamBean paramBean);
    List<OdsbPayrollTxn> selectRptA09V1Records(BasePagedQryParamBean paramBean);
    //综合报表：住房公积金数据 RPTA10V1
    int countRptA10V1Records(BasePagedQryParamBean paramBean);
    List<OdsbHousingfundTxn> selectRptA10V1Records(BasePagedQryParamBean paramBean);
    //综合报表：代发业务数据 RPTA11V1
    int countRptA11V1Records(BasePagedQryParamBean paramBean);
    List<OdsbPayagentTxn> selectRptA11V1Records(BasePagedQryParamBean paramBean);
}
