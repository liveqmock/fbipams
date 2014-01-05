package pams.repository.dao.custlist;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import pams.repository.model.Ptoper;
import pams.repository.model.ClsRptdata;
import pams.repository.model.custlist.CustListParam;
import pams.repository.model.custlist.CustMngParam;
import pams.repository.model.custlist.CustMngVO;

import java.util.List;

/**
 * User: zhanrui
 * Date: 12-12-19
 * Time: ����8:22
 */
@Component
public interface CustlistMapper {
    int insertBatch(List<ClsRptdata> beans);
    int deleteRecords(@Param("rptdate") String rptdate, @Param("rpttype") String rpttype);

    List<ClsRptdata> selectCustlistRecordsByCustno(@Param("custNo") String custNo);
    List<ClsRptdata> selectCustlistRecordsByCertInfo(@Param("certType") String certType, @Param("certNo") String certNo);
    List<ClsRptdata> selectCustlistRecordsByPageSize(CustListParam bean);
    int countCustlistRecords(CustListParam bean);

    //�ͻ�������Ϣά��
    int countCustMngRecords(CustMngParam bean);
    List<CustMngVO> selectCustMngRecordsByPageSize(CustMngParam bean);

    //�ͻ�������Ϣ��ѯ���ɲ��²����㣩
    int countCustBaseRecords(CustMngParam bean);
    List<CustMngVO> selectCustBaseRecordsByPageSize(CustMngParam bean);


    //��ȡĳ������˿ͻ������λ��Ա�嵥
    List<Ptoper> selectPtopersForRole_CustMgr(String branchId);

    @Select("select max(rpt_date) from sv_cls_custinfo")
    String selectCustListMaxRptDate();
}
