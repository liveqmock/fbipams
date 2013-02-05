package pams.service.custmng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pams.repository.dao.PtoperMapper;
import pams.repository.dao.SvCmsCustbaseMapper;
import pams.repository.dao.custlist.CustlistMapper;
import pams.repository.model.Ptoper;
import pams.repository.model.PtoperExample;
import pams.repository.model.SvCmsCustbase;
import pams.repository.model.SvCmsCustbaseExample;

import java.util.List;

/**
 * �ͻ�������Ϣά�����ͻ�����.
 * User: zhanrui
 * Date: 12-12-17
 * Time: ����3:50
 */
@Service
public class CustMngService {
    private static final Logger logger = LoggerFactory.getLogger(CustMngService.class);

    @Autowired
    private SvCmsCustbaseMapper cmsCustbaseMapper;
    @Autowired
    private CustlistMapper custlistMapper;
    @Autowired
    private PtoperMapper ptoperMapper;

    public String selectMaxCurrMaxRptdate() {
        return custlistMapper.selectCustListMaxRptDate();
    }

    public void dispatchCustMgr(String guid, String custmgrId) {
        SvCmsCustbaseExample example = new SvCmsCustbaseExample();
        example.createCriteria().andGuidEqualTo(guid);
        SvCmsCustbase cust = cmsCustbaseMapper.selectByPrimaryKey(guid);
        cust.setCustmgrId(custmgrId);
        cmsCustbaseMapper.updateByPrimaryKey(cust);
    }

    public List<Ptoper> selectCustMgr(String branchId) {
        //return custlistMapper.selectPtopersForRole_CustMgr(branchId);
        PtoperExample example = new PtoperExample();
        example.createCriteria().andDeptidEqualTo(branchId).andOperenabledEqualTo("1");
        return ptoperMapper.selectByExample(example);
    }


    public List<SvCmsCustbase> selectCustBaseByCustMgr(String branchId, String custmgrId) {
        SvCmsCustbaseExample example = new SvCmsCustbaseExample();
        example.createCriteria().andBranchIdEqualTo(branchId).andCustmgrIdEqualTo(custmgrId);
        return cmsCustbaseMapper.selectByExample(example);
    }

    public List<SvCmsCustbase> selectCustBaseByCustMgr(String branchId, String custmgrId, String custName, String certNo) {
        SvCmsCustbaseExample example = new SvCmsCustbaseExample();
        example.createCriteria().andBranchIdEqualTo(branchId).andCustmgrIdEqualTo(custmgrId)
                .andCustNameLike("%" + custName + "%")
                .andCertNoLike("%" + certNo + "%");
        return cmsCustbaseMapper.selectByExample(example);
    }

    public void updateOneCustBaseRecord(SvCmsCustbase custbase){
    }

    public void updateCustBaseContractExtInfo(String guid, String info){
        SvCmsCustbase base = cmsCustbaseMapper.selectByPrimaryKey(guid);
        base.setContactInfoExt(info);
        cmsCustbaseMapper.updateByPrimaryKey(base);
    }
    public void updateCustBaseRemark(String guid, String info){
        SvCmsCustbase base = cmsCustbaseMapper.selectByPrimaryKey(guid);
        base.setRemark(info);
        cmsCustbaseMapper.updateByPrimaryKey(base);
    }

    //=====================================================================
    public CustlistMapper getCustlistMapper() {
        return custlistMapper;
    }

    public void setCustlistMapper(CustlistMapper custlistMapper) {
        this.custlistMapper = custlistMapper;
    }
}
