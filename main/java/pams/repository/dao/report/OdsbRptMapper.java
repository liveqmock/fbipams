package pams.repository.dao.report;

import org.springframework.stereotype.Component;
import pams.repository.model.*;
import pams.repository.model.report.BasePagedQryParamBean;

import java.util.List;

/**
 * User: zhanrui
 * Date: 12-12-19
 * Time: ����8:22
 */
@Component
public interface OdsbRptMapper {
    //�ۺϱ�������ɽ��ͻ���ϸ RPTA06V1
    int countRptA07V1Records(BasePagedQryParamBean paramBean);
    List<OdsbStTconfirmTrad> selectRptA07V1Records(BasePagedQryParamBean paramBean);

    //�ۺϱ������������������ RPTA08V1
    int countRptA08V1Records(BasePagedQryParamBean paramBean);
    List<OdsbChnTrad> selectRptA08V1Records(BasePagedQryParamBean paramBean);

    //�ۺϱ��������������� RPTA09V1
    int countRptA09V1Records(BasePagedQryParamBean paramBean);
    List<OdsbPayrollTxn> selectRptA09V1Records(BasePagedQryParamBean paramBean);
    //�ۺϱ���ס������������ RPTA10V1
    int countRptA10V1Records(BasePagedQryParamBean paramBean);
    List<OdsbHousingfundTxn> selectRptA10V1Records(BasePagedQryParamBean paramBean);
    //�ۺϱ�������ҵ������ RPTA11V1
    int countRptA11V1Records(BasePagedQryParamBean paramBean);
    List<OdsbPayagentTxn> selectRptA11V1Records(BasePagedQryParamBean paramBean);
}
