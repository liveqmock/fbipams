package pams.repository.dao.report;

import org.springframework.stereotype.Component;
import pams.repository.model.OdsbChnTrad;
import pams.repository.model.OdsbStTconfirmTrad;
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
}
