package pams.repository.dao.saledata;

import org.springframework.stereotype.Component;
import pams.repository.model.saledata.SaleDataChkVO;
import pams.repository.model.saledata.SaleDataQryParamBean;

import java.util.List;

/**
 * User: zhanrui
 * Date: 13-3-3
 * Time: обнГ8:22
 */
@Component
public interface SaleDataChkMapper {

    int countSaleDataRecords(SaleDataQryParamBean paramBean);
    List<SaleDataChkVO> selectSaleDataRecords(SaleDataQryParamBean paramBean);
}
