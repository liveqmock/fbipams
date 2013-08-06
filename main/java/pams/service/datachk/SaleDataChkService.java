package pams.service.datachk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pams.batch.saleeventcheck.client.CronMainHandler;
import pams.repository.dao.saledata.SaleDataChkMapper;
import pams.repository.model.saledata.SaleDataChkVO;
import pams.repository.model.saledata.SaleDataQryParamBean;

import javax.annotation.Resource;
import java.util.List;

/**
 * User: zhanrui
 * Date: 13-3-4
 * Time: ÏÂÎç2:29
 */
@Service
public class SaleDataChkService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "saleeventCheck")
    private  CronMainHandler cronMainHandler;
    @Autowired
    private SaleDataChkMapper saleDataChkMapper;

    public List<SaleDataChkVO> selectPagedRecords_SaleData(SaleDataQryParamBean paramBean){
        return saleDataChkMapper.selectSaleDataRecords(paramBean);
    }

    public void checkSaleData(){
         cronMainHandler.run();
    }

    //===========================================

    public SaleDataChkMapper getSaleDataChkMapper() {
        return saleDataChkMapper;
    }

    public void setSaleDataChkMapper(SaleDataChkMapper saleDataChkMapper) {
        this.saleDataChkMapper = saleDataChkMapper;
    }

    public CronMainHandler getCronMainHandler() {
        return cronMainHandler;
    }

    public void setCronMainHandler(CronMainHandler cronMainHandler) {
        this.cronMainHandler = cronMainHandler;
    }
}
