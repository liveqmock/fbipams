package pams.service.prdset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pams.repository.dao.*;
import pams.repository.dao.prdset.PsSalesInfoMapper;
import pams.repository.model.prdset.PsPrdStatisticBean;
import pams.repository.model.prdset.PsPrdsetStatisticBean;

import java.util.List;

/**
 * 产品套餐相关处理.
 * User: zhanrui
 * Date: 11-4-15
 * Time: 下午7:16
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PsStatisticService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SvpsprdsetmainMapper svpsprdsetmainMapper;
    @Autowired
    private SvpsprdsetdetailMapper svpsprdsetdetailMapper;
    @Autowired
    private SvpsprdsetpriceMapper svpsprdsetpriceMapper;
    @Autowired
    private SvpsprdsetmutexMapper svpsprdsetmutexMapper;

    @Autowired
    private SvpsprdinfoMapper svpsprdinfoMapper;


    @Autowired
    private SvpssaleinfoMapper svpsSaleInfoMapper;
    @Autowired
    private SvpssaleprdinfoMapper svpsSalePrdInfoMapper;
    @Autowired
    private SvpsprdckptdefMapper svpsprdckptdefMapper;
    @Autowired
    private SvpssaleprdckptMapper svpsSalePrdCkptMapper;

    @Autowired
    private PsSalesInfoMapper psSalesInfoMapper;

    public void setSvpsprdsetmainMapper(SvpsprdsetmainMapper svpsprdsetmainMapper) {
        this.svpsprdsetmainMapper = svpsprdsetmainMapper;
    }

    public void setSvpsprdsetdetailMapper(SvpsprdsetdetailMapper svpsprdsetdetailMapper) {
        this.svpsprdsetdetailMapper = svpsprdsetdetailMapper;
    }

    public void setSvpsprdsetpriceMapper(SvpsprdsetpriceMapper svpsprdsetpriceMapper) {
        this.svpsprdsetpriceMapper = svpsprdsetpriceMapper;
    }

    public void setSvpsprdsetmutexMapper(SvpsprdsetmutexMapper svpsprdsetmutexMapper) {
        this.svpsprdsetmutexMapper = svpsprdsetmutexMapper;
    }

    public void setSvpsprdinfoMapper(SvpsprdinfoMapper svpsprdinfoMapper) {
        this.svpsprdinfoMapper = svpsprdinfoMapper;
    }


    public void setsvpsSalesInfoMapper(SvpssaleinfoMapper svpsSalesInfoMapper) {
        this.svpsSaleInfoMapper = svpsSalesInfoMapper;
    }

    //===================================================================

    /**
     * 总量数据统计
     * @param deptid
     * @return
     */
    public  List<PsPrdsetStatisticBean> selectPrdsetStatisticData(String deptid){
        return this.psSalesInfoMapper.selectPrdsetStatisticData(deptid);
    }
    public  List<PsPrdStatisticBean> selectPrdStatisticData(String deptid){
        return this.psSalesInfoMapper.selectPrdStatisticData(deptid);
    }
}
