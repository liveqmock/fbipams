package pams.service.custlist.importdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pams.repository.dao.ClsRptdataMapper;
import pams.repository.dao.PtenudetailMapper;
import pams.repository.dao.custlist.CustlistMapper;
import pams.repository.model.ClsRptdata;
import pams.repository.model.Ptenudetail;
import pams.repository.model.PtenudetailExample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户名单制.
 * User: zhanrui
 * Date: 12-12-17
 * Time: 下午3:50
 */
@Service
public class CustlistService<T> {
    private static final Logger logger = LoggerFactory.getLogger(CustlistService.class);

    @Autowired
    private ClsRptdataMapper custinfoMapper;
    @Autowired
    private CustlistMapper custlistMapper;
    @Autowired
    private PtenudetailMapper ptenudetailMapper;

    //枚举map ：枚举值->枚举label
    public Map<String,String> selectPtEnuDeatilItemsLabelMap(String enuType){
        PtenudetailExample example = new PtenudetailExample();
        example.createCriteria().andEnutypeEqualTo(enuType);
        List<Ptenudetail>  records = ptenudetailMapper.selectByExample(example);

        Map<String,String> enuMaps = new HashMap<>();
        for (Ptenudetail record : records) {
               enuMaps.put(record.getEnuitemvalue(), record.getEnuitemlabel());
        }
        return enuMaps;
    }
    //枚举map ：枚举值->枚举label
    public Map<String,String> selectPtEnuDeatilItemsExpandMap(String enuType){
        PtenudetailExample example = new PtenudetailExample();
        example.createCriteria().andEnutypeEqualTo(enuType);
        List<Ptenudetail>  records = ptenudetailMapper.selectByExample(example);

        Map<String,String> enuMaps = new HashMap<>();
        for (Ptenudetail record : records) {
               enuMaps.put(record.getEnuitemvalue(), record.getEnuitemexpand());
        }
        return enuMaps;
    }

/*
    public List<ClsRptdata> selectCustinfoRecords(String certType, String certNo, String startDate, String endDate){
        ClsRptdataExample example = new ClsRptdataExample();
        example.createCriteria().andRptDateBetween(startDate,endDate).andCertTypeEqualTo(certType).andCertNoEqualTo(certNo);
        return   custinfoMapper.selectByExample(example);
    }
*/

    public List<ClsRptdata> selectCustlistRecordsByCertInfo(String certType, String certNo)  {
        return custlistMapper.selectCustlistRecordsByCertInfo(certType, certNo);
    }
    public List<ClsRptdata> selectCustlistRecordsByCustno(String custNo)  {
        return custlistMapper.selectCustlistRecordsByCustno(custNo);
    }
    //=============

    public CustlistMapper getCustlistMapper() {
        return custlistMapper;
    }

    public void setCustlistMapper(CustlistMapper custlistMapper) {
        this.custlistMapper = custlistMapper;
    }

}
