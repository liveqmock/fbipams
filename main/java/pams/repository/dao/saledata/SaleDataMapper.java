package pams.repository.dao.saledata;

import org.springframework.stereotype.Component;
import pams.repository.model.saledata.SaleDataQryParamBean;
import pams.repository.model.saledata.SaleDataVO;

import java.util.List;

/**
 * User: zhanrui
 * Date: 13-3-3
 * Time: обнГ8:22
 */
@Component
public interface SaleDataMapper {

    int countSaleDataRecords(SaleDataQryParamBean paramBean);
    List<SaleDataVO> selectSaleDataRecords(SaleDataQryParamBean paramBean);
}
