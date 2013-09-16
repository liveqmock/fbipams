package pams.repository.dao.custlist;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import pams.repository.model.Ptoper;
import pams.repository.model.SvClsCustinfo;
import pams.repository.model.custlist.CustListParam;
import pams.repository.model.custlist.CustMngParam;
import pams.repository.model.custlist.CustMngVO;

import java.util.List;

/**
 * User: zhanrui
 * Date: 12-12-19
 * Time: 下午8:22
 */
@Component
public interface CustlistMapper {
    int insertBatch(List<SvClsCustinfo> beans);
    int deleteRecords(@Param("rptdate") String rptdate, @Param("rpttype") String rpttype);

    List<SvClsCustinfo> selectCustlistRecordsByCustno(@Param("custNo") String custNo);
    List<SvClsCustinfo> selectCustlistRecordsByCertInfo(@Param("certType") String certType, @Param("certNo") String certNo);
    List<SvClsCustinfo> selectCustlistRecordsByPageSize(CustListParam bean);
    int countCustlistRecords(SvClsCustinfo bean);

    //客户基本信息维护
    int countCustMngRecords(CustMngParam bean);
    List<CustMngVO> selectCustMngRecordsByPageSize(CustMngParam bean);

    //客户基本信息查询（可查下层网点）
    int countCustBaseRecords(CustMngParam bean);
    List<CustMngVO> selectCustBaseRecordsByPageSize(CustMngParam bean);


    //获取某网点个人客户经理岗位人员清单
    List<Ptoper> selectPtopersForRole_CustMgr(String branchId);

    @Select("select max(rpt_date) from sv_cls_custinfo")
    String selectCustListMaxRptDate();
}
