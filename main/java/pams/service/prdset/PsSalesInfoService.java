package pams.service.prdset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pams.repository.dao.*;
import pams.repository.dao.prdset.PsSalesInfoMapper;
import pams.repository.model.*;
import pams.repository.model.prdset.PsPrdCkptInfoBean;
import pams.repository.model.prdset.PsSalesInfoBean;
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
public class PsSalesInfoService {
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
    private SvpsarchivectlMapper svpsarchivectlMapper;

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

    /**
     * 产品套餐主要信息清单
     *
     * @return
     */
    public List<Svpsprdsetmain> selectprdsetList(Date date) {
        SvpsprdsetmainExample example = new SvpsprdsetmainExample();
        String strdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        example.createCriteria().andEnddateGreaterThanOrEqualTo(strdate).andStartdateLessThanOrEqualTo(strdate);
        return this.svpsprdsetmainMapper.selectByExample(example);
    }

    public List<Svpsprdsetmain> selectprdsetList() {
        SvpsprdsetmainExample example = new SvpsprdsetmainExample();
        example.createCriteria();
        return this.svpsprdsetmainMapper.selectByExample(example);
    }

    /**
     * 查找同一柜员同一产品套餐的情况
     *
     * @param certtype
     * @param certno
     * @param prdsetid
     * @return
     */
    public List<Svpssaleinfo> searchDuplicateRecord(String certtype, String certno, String prdsetid) {
        SvpssaleinfoExample example = new SvpssaleinfoExample();
        example.createCriteria().andCerttypeEqualTo(certtype).andCertnoEqualTo(certno).
                andPrdsetidEqualTo(prdsetid).andRecstsEqualTo("N");
        return this.svpsSaleInfoMapper.selectByExample(example);
    }

    public List<Svpssaleinfo> searchDuplicateRecord(String certtype, String certno) {
        SvpssaleinfoExample example = new SvpssaleinfoExample();
        example.createCriteria().andCerttypeEqualTo(certtype).andCertnoEqualTo(certno).andRecstsEqualTo("N");
        return this.svpsSaleInfoMapper.selectByExample(example);
    }

    public Svpsprdsetmain selectPrdsetMainInfo(String id) {
        SvpsprdsetmainExample example = new SvpsprdsetmainExample();
        example.createCriteria().andPrdsetidEqualTo(id);
        return this.svpsprdsetmainMapper.selectByExample(example).get(0);
    }


    @Transactional
    public int insertPsSalesInfo(Svpssaleinfo record) {
        //生成GUID 插入套餐签约信息表
        String saleinfoGuid = this.svpsSaleInfoMapper.selectGuid();
        record.setGuid(saleinfoGuid);
        int count = this.svpsSaleInfoMapper.insertByGuid(record);

        //生成相应的产品信息
        SvpsprdsetdetailExample psdExample = new SvpsprdsetdetailExample();
        psdExample.createCriteria().andPrdsetidEqualTo(record.getPrdsetid());
        List<Svpsprdsetdetail> psDetails = svpsprdsetdetailMapper.selectByExample(psdExample);
        for (Svpsprdsetdetail psDetail : psDetails) {
            Svpssaleprdinfo saleprdinfo = new Svpssaleprdinfo();
            saleprdinfo.setPrdid(psDetail.getPrdid());
            saleprdinfo.setSaleinfokey(saleinfoGuid);
            saleprdinfo.setCheckflag("0");
            saleprdinfo.setChecktimes((long) 0);
            svpsSalePrdInfoMapper.insert(saleprdinfo);
        }
        return count;
    }

    /**
     * 统计查询时的记录数
     *
     * @param branchid
     * @param startdate
     * @param enddate
     * @param prdid
     * @return
     */
    public int countSalesinfoBySearchCondition(String branchid, String startdate, String enddate, String prdid) {
//        return psSalesInfoMapper.countSalesinfoBySearchCondition(branchid, startdate, enddate, prdid);
        return 0;
    }

    /**
     * 查询产品套餐明细数据
     *
     * @return
     */
    public int countPrdsetSalesinfo(PsSalesInfoBean bean) {
        return psSalesInfoMapper.countPrdsetSalesinfo(bean);
    }

    /**
     * 根据查询参数查找 产品套餐 明细
     *
     * @param bean
     * @return
     */
    public List<PsSalesInfoBean> selectPrdsetSalesinfo(PsSalesInfoBean bean) {
        return psSalesInfoMapper.selectPrdsetSalesinfo(bean);
    }

    /**
     * 删除 产品套餐签约记录 （逻辑删除）
     *
     * @param record
     * @return
     */
    @Transactional
    public int deleteSalesInfoOneRecord(Svpssaleinfo record, String operid) {
        String guid = record.getGuid();
        long recversion = record.getRecversion();
        Svpssaleinfo oldrecord = svpsSaleInfoMapper.selectByPrimaryKey(guid);
        if (oldrecord.getRecversion() != recversion) {
            throw new RuntimeException("该条数据库记录已被修改。");
        }
        oldrecord.setRecversion(++recversion);
        oldrecord.setRecsts("D");
        oldrecord.setDeloperid(operid);
        oldrecord.setDeloperdate(new Date());
        return svpsSaleInfoMapper.updateByPrimaryKey(oldrecord);
    }


    public int commitSalesInfoOneRecord(Svpssaleinfo record, String operid) {
        String guid = record.getGuid();
        long recversion = record.getRecversion();
        Svpssaleinfo oldrecord = svpsSaleInfoMapper.selectByPrimaryKey(guid);
        if (oldrecord.getRecversion() != recversion) {
            throw new RuntimeException("该条数据库记录已被修改。");
        }
        oldrecord.setRecversion(++recversion);
        oldrecord.setCommitflag("1");
        oldrecord.setCommitdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        oldrecord.setCommittime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        oldrecord.setCommiter(operid);
        return svpsSaleInfoMapper.updateByPrimaryKey(oldrecord);
    }

    /**
     * 批量提交记录
     *
     * @param records
     * @param operid
     * @return
     */
    @Transactional
    public int commitSalesInfoAllRecords(List<PsSalesInfoBean> records, String operid) {
        int count = 0;
        for (PsSalesInfoBean record : records) {
            String guid = record.getGuid();
            long recversion = record.getRecversion();
            Svpssaleinfo oldrecord = svpsSaleInfoMapper.selectByPrimaryKey(guid);
            if (oldrecord.getRecversion() != recversion) {
                throw new RuntimeException("该条数据库记录已被修改。");
            }
            oldrecord.setRecversion(++recversion);
            oldrecord.setCommitflag("1");
            oldrecord.setCommitdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            oldrecord.setCommittime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
            oldrecord.setCommiter(operid);
            count += svpsSaleInfoMapper.updateByPrimaryKey(oldrecord);
        }
        return count;
    }

    /**
     * 根据产品套餐ID检索已签约产品明细
     *
     * @param guid
     * @return
     */
    public List<PsSalesPrdInfoBean> selectPrdSalesInfo(String guid) {
        return psSalesInfoMapper.selectPrdSalesInfo(guid);
    }

    /**
     * 根据产品ID查找本产品所有检核指标的明细记录
     *
     * @param prdsetguid
     * @return
     */
    public List<PsPrdCkptInfoBean> selectPrdCkptInfo(String prdsetguid) {
        //SvpssaleprdckptExample example = new SvpssaleprdckptExample();
        //example.createCriteria().andSaleprdidEqualTo(guid);
        //return svpsSalePrdCkptMapper.selectByExample(example);
        return psSalesInfoMapper.selectPrdCkptInfo(prdsetguid);
    }

    /**
     * 逻辑删除传入参数日期之前的未提交的正常的记录
     *
     * @param strdate
     * @return
     */
    @Transactional
    public int deleteOutDateRecordByDate(String strdate, String operid) {
        SvpssaleinfoExample example = new SvpssaleinfoExample();
        example.createCriteria()
                .andTxndateLessThanOrEqualTo(strdate)
                .andRecstsEqualTo("N")
                .andCommitflagEqualTo("0");
        Svpssaleinfo record = new Svpssaleinfo();
        record.setRecsts("D");
        record.setDeloperid(operid);
        record.setDeloperdate(new Date());
        return svpsSaleInfoMapper.updateByExampleSelective(record, example);
    }

    //===================考核处理===================================

    /**
     * 查看某月份是否已经考核过
     * @param year
     * @param month
     * @return
     */
    public boolean  isPrdsetArchived(String year, String month){
        SvpsarchivectlKey key = new SvpsarchivectlKey();
        key.setArchiveyear(year);
        key.setArchivemonth(month);
        Svpsarchivectl ctl = svpsarchivectlMapper.selectByPrimaryKey(key);
        if (ctl == null) {
            return false;
        }else{
            return true;
        }
    }


    /**
     * 考核逻辑处理
     *
     */
    @Transactional
    public int updatePrdsetArchiveFlagAndControlInfo(String year, String month, String operid) {
        if (Integer.parseInt(year) < 2000 || Integer.parseInt(year) > 2100
                || month.length() != 2
                || Integer.parseInt(month) < 1
                || Integer.parseInt(month) > 12
                ) {
            throw  new IllegalArgumentException("年份或月份错误。");
        }
        int count = psSalesInfoMapper.updateAllArchiveFlag(year + "-" + month, operid);
        insertOrUpdatePrdsetArchiveCtlInfo(year, month, operid);
        return count;
    }

    /**
     * 更新或追加考核主控信息
     * @param year
     * @param month
     * @param operid
     */
    private void insertOrUpdatePrdsetArchiveCtlInfo(String year, String month, String operid) {
        SvpsarchivectlKey key = new SvpsarchivectlKey();
        key.setArchiveyear(year);
        key.setArchivemonth(month);
        Svpsarchivectl ctl = svpsarchivectlMapper.selectByPrimaryKey(key);
        if (ctl == null) {
            ctl = new Svpsarchivectl();
            ctl.setArchiveyear(year);
            ctl.setArchivemonth(month);
            ctl.setOperid(operid);
            ctl.setOperdate(new Date());
            ctl.setRecversion(0L);
            ctl.setRemark("初始考核");
            svpsarchivectlMapper.insert(ctl);
        }else{
            ctl.setOperid(operid);
            ctl.setOperdate(new Date());
            ctl.setRecversion(ctl.getRecversion() + 1);
            ctl.setRemark("重新考核");
            svpsarchivectlMapper.updateByPrimaryKeySelective(ctl);
        }
    }
}
