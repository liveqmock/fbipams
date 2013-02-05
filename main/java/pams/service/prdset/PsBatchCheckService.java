package pams.service.prdset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pams.repository.dao.*;
import pams.repository.dao.prdset.PsSalesInfoMapper;
import pams.repository.model.*;
import pams.repository.model.prdset.PsSalesPrdInfoBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 产品套餐相关处理.
 * User: zhanrui
 * Date: 11-4-15
 * Time: 下午7:16
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PsBatchCheckService {
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


    //==============数据检核==================

    public List<String>  selectAllProductFromCurrentPrdset(){
        return  this.psSalesInfoMapper.selectAllProductIdFromCurrentPrdset();
    }
    /**
     * 检索产品检核指标名称
     * @param prdid
     * @return
     */
    public List<String> selectCheckPointClassName(String prdid) {
        return this.psSalesInfoMapper.selectCheckPointClassName(prdid);
    }

    /**
     * 检索指定产品ID及某检核指标未通过的记录
     *
     * @param prdid
     * @return
     */
    public List<PsSalesPrdInfoBean> selectNeedCheckPrdinfoList(String prdid, String ckptid) {
        return this.psSalesInfoMapper.selectNeedCheckPrdinfoList(prdid, ckptid);

    }

    /**
     * 检索指定产品ID及某检核指标所有的记录（不包括未提交、已考核、已删除的）
     *
     * @param prdid
     * @return
     */
    public List<PsSalesPrdInfoBean> selectAllPrdinfoListForForceCheck(String prdid, String ckptid) {
        return this.psSalesInfoMapper.selectAllPrdinfoListForForceCheck(prdid, ckptid);

    }

    /**
     * 根据检核指标的程序名查找GUID
     *
     * @param ckptprog
     * @return
     */
    public String selectCheckPointRuleGuid(String ckptprog) {
        SvpsprdckptdefExample example = new SvpsprdckptdefExample();
        example.createCriteria().andCkptprogEqualTo(ckptprog);
        return svpsprdckptdefMapper.selectByExample(example).get(0).getGuid();
    }


    /**
     * 查看本检核指标的检查明细数据是否存在(未检核成功的)
     *
     * @param saleprdid
     * @param ckptid
     * @return
     */
    public Svpssaleprdckpt selectProductCheckpointFailDetail(String saleprdid, String ckptid) {
        SvpssaleprdckptExample example = new SvpssaleprdckptExample();
        example.createCriteria().andSaleprdidEqualTo(saleprdid).andCkptidEqualTo(ckptid);
        List<Svpssaleprdckpt> records = svpsSalePrdCkptMapper.selectByExample(example);
        if (records.size() == 0) {
            return null;
        } else {
            return records.get(0);
        }
    }

    public int insertNewProductCheckpointResult(Svpssaleprdckpt ckpt) {
        return svpsSalePrdCkptMapper.insert(ckpt);
    }

    public int updateProductCheckpointResult(Svpssaleprdckpt ckpt) {
        long times = ckpt.getChecktimes() + 1;
        ckpt.setChecktimes(times);
        return svpsSalePrdCkptMapper.updateByPrimaryKey(ckpt);
    }

    /**
     * 根据检核指标的检核结果更新产品表的检核状态->成功
     *
     * @param prdid
     * @return
     */
    public int updateProductCheckFlagToSuccess(String prdid) {
        SvpsprdckptdefExample example = new SvpsprdckptdefExample();
        example.createCriteria().andPrdidEqualTo(prdid).andValidflagEqualTo("1");
        int ckptcount = svpsprdckptdefMapper.countByExample(example);
        if (ckptcount == 0) {
            logger.info("待检核的产品不存在有效的检核指标。产品ID=" + prdid);
            return 0;
        }
        String checkdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String checktime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        return psSalesInfoMapper.updateProductCheckFlagToSuccess(prdid, ckptcount, checkdate, checktime);
    }

    /**
     * 更新产品套餐表的状态
     *
     * @return
     */
    public void updateProductSetCheckFlagToSuccess() {
        SvpsprdsetmainExample svpsprdsetmainExample = new SvpsprdsetmainExample();
        SvpsprdsetdetailExample svpsprdsetdetailExample = new SvpsprdsetdetailExample();
        //TODO 日期限制
        svpsprdsetmainExample.createCriteria();
        List<Svpsprdsetmain> svpsprdsetmainList = svpsprdsetmainMapper.selectByExample(svpsprdsetmainExample);

        svpsprdsetdetailExample.createCriteria();
        String checkdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String checktime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        for (Svpsprdsetmain svpsprdsetmain : svpsprdsetmainList) {
            svpsprdsetdetailExample.clear();
            svpsprdsetdetailExample.createCriteria().andPrdsetidEqualTo(svpsprdsetmain.getPrdsetid());
            int prdcount = svpsprdsetdetailMapper.countByExample(svpsprdsetdetailExample);
            psSalesInfoMapper.updateProductSetCheckFlagToSuccess(svpsprdsetmain.getPrdsetid(), prdcount, checkdate, checktime);
        }
    }

    /**
     * 强制重新检核：设置已成功(未考核的)的产品套餐为未通过
     */
    @Transactional
    public void updateAllProductSetCheckFlagToFail() {
        String currdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        psSalesInfoMapper.updateAllProductSetCheckFlagToFail("重新检核" + currdate);
        psSalesInfoMapper.updateAllProductCheckFlagToFail("重新检核" + currdate);
        psSalesInfoMapper.updateAllCheckPointCheckFlagToFail("重新检核" + currdate);
    }


}
