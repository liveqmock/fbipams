package pams.service.effectcust;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pams.repository.dao.PtenudetailMapper;
import pams.repository.dao.SvEclCustinfoMapper;
import pams.repository.dao.effectcust.EffectCustlistMapper;
import pams.repository.model.Ptenudetail;
import pams.repository.model.PtenudetailExample;
import pams.repository.model.SvEclCustinfo;
import pams.repository.model.SvEclCustinfoExample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 有效客户拓展提升目标清单.
 * User: zhanrui
 * Date: 12-12-17
 * Time: 下午3:50
 */
@Service
public class EffectCustlistService<T> {
    private static final Logger logger = LoggerFactory.getLogger(EffectCustlistService.class);

    @Autowired
    private SvEclCustinfoMapper custinfoMapper;
    @Autowired
    private EffectCustlistMapper custlistMapper;
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

    public List<SvEclCustinfo> selectCustinfoRecords(String certType, String certNo, String startDate, String endDate){
        SvEclCustinfoExample example = new SvEclCustinfoExample();
        example.createCriteria().andRptDateBetween(startDate,endDate).andCertTypeEqualTo(certType).andCertNoEqualTo(certNo);
        return   custinfoMapper.selectByExample(example);
    }

    //=============

    public EffectCustlistMapper getCustlistMapper() {
        return custlistMapper;
    }

    public void setCustlistMapper(EffectCustlistMapper custlistMapper) {
        this.custlistMapper = custlistMapper;
    }

}
