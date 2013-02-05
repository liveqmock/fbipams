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
 * ��Ʒ�ײ���ش���.
 * User: zhanrui
 * Date: 11-4-15
 * Time: ����7:16
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


    //==============���ݼ��==================

    public List<String>  selectAllProductFromCurrentPrdset(){
        return  this.psSalesInfoMapper.selectAllProductIdFromCurrentPrdset();
    }
    /**
     * ������Ʒ���ָ������
     * @param prdid
     * @return
     */
    public List<String> selectCheckPointClassName(String prdid) {
        return this.psSalesInfoMapper.selectCheckPointClassName(prdid);
    }

    /**
     * ����ָ����ƷID��ĳ���ָ��δͨ���ļ�¼
     *
     * @param prdid
     * @return
     */
    public List<PsSalesPrdInfoBean> selectNeedCheckPrdinfoList(String prdid, String ckptid) {
        return this.psSalesInfoMapper.selectNeedCheckPrdinfoList(prdid, ckptid);

    }

    /**
     * ����ָ����ƷID��ĳ���ָ�����еļ�¼��������δ�ύ���ѿ��ˡ���ɾ���ģ�
     *
     * @param prdid
     * @return
     */
    public List<PsSalesPrdInfoBean> selectAllPrdinfoListForForceCheck(String prdid, String ckptid) {
        return this.psSalesInfoMapper.selectAllPrdinfoListForForceCheck(prdid, ckptid);

    }

    /**
     * ���ݼ��ָ��ĳ���������GUID
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
     * �鿴�����ָ��ļ����ϸ�����Ƿ����(δ��˳ɹ���)
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
     * ���ݼ��ָ��ļ�˽�����²�Ʒ��ļ��״̬->�ɹ�
     *
     * @param prdid
     * @return
     */
    public int updateProductCheckFlagToSuccess(String prdid) {
        SvpsprdckptdefExample example = new SvpsprdckptdefExample();
        example.createCriteria().andPrdidEqualTo(prdid).andValidflagEqualTo("1");
        int ckptcount = svpsprdckptdefMapper.countByExample(example);
        if (ckptcount == 0) {
            logger.info("����˵Ĳ�Ʒ��������Ч�ļ��ָ�ꡣ��ƷID=" + prdid);
            return 0;
        }
        String checkdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String checktime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        return psSalesInfoMapper.updateProductCheckFlagToSuccess(prdid, ckptcount, checkdate, checktime);
    }

    /**
     * ���²�Ʒ�ײͱ��״̬
     *
     * @return
     */
    public void updateProductSetCheckFlagToSuccess() {
        SvpsprdsetmainExample svpsprdsetmainExample = new SvpsprdsetmainExample();
        SvpsprdsetdetailExample svpsprdsetdetailExample = new SvpsprdsetdetailExample();
        //TODO ��������
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
     * ǿ�����¼�ˣ������ѳɹ�(δ���˵�)�Ĳ�Ʒ�ײ�Ϊδͨ��
     */
    @Transactional
    public void updateAllProductSetCheckFlagToFail() {
        String currdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        psSalesInfoMapper.updateAllProductSetCheckFlagToFail("���¼��" + currdate);
        psSalesInfoMapper.updateAllProductCheckFlagToFail("���¼��" + currdate);
        psSalesInfoMapper.updateAllCheckPointCheckFlagToFail("���¼��" + currdate);
    }


}
