package pams.repository.dao.dataetl;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * User: zhanrui
 * Date: 12-12-19
 * Time: 下午8:22
 */
@Component
public interface DataETLMapper {

    //客户基本信息维护
    int mergeCustBaseRecords(@Param("rptDate") String rptDate);
    int updateCustBaseRecords4CertInfo();
    int updateNullCertTypeRecords(@Param("rptDate") String rptDate, @Param("certType") String certType);


    //大额资金流向
    int deleteLargeFlowData(String startDate);
    int importLargeFlowRecords(String startDate);

    //基金成交客户明细
    int deleteData_RptA07V1(String startDate);
    int importRecords_RptA07V1(String startDate);

    //渠道交易明细
    int deleteData_RptA08V1(String startDate);
    int importRecords_RptA08V1(String startDate);
}
