package pams.service.saledata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pams.repository.dao.saledata.SaleDataMapper;
import pams.repository.model.saledata.SaleDataQryParamBean;
import pams.repository.model.saledata.SaleDataVO;

import java.util.List;

/**
 * User: zhanrui
 * Date: 13-3-4
 * Time: ÏÂÎç2:29
 */
@Service
public class SaleDataService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SaleDataMapper saleDataMapper;

    public List<SaleDataVO> selectPagedRecords_SaleData(SaleDataQryParamBean paramBean){
        return saleDataMapper.selectSaleDataRecords(paramBean);
    }

    //===========================================
    public SaleDataMapper getSaleDataMapper() {
        return saleDataMapper;
    }

    public void setSaleDataMapper(SaleDataMapper saleDataMapper) {
        this.saleDataMapper = saleDataMapper;
    }
}
