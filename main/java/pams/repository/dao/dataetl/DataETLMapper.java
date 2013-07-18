package pams.repository.dao.dataetl;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * User: zhanrui
 * Date: 12-12-19
 * Time: ����8:22
 */
@Component
public interface DataETLMapper {

    //�ͻ�������Ϣά��
    int mergeCustBaseRecords(@Param("rptDate") String rptDate);
    int updateCustBaseRecords4CertInfo();
    int updateNullCertTypeRecords(@Param("rptDate") String rptDate, @Param("certType") String certType);


    //����ʽ�����
    int deleteLargeFlowData(String startDate);
    int importLargeFlowRecords(String startDate);

    //����ɽ��ͻ���ϸ
    int deleteData_RptA07V1(String startDate);
    int importRecords_RptA07V1(String startDate);

    //����������ϸ
    int deleteData_RptA08V1(String startDate);
    int importRecords_RptA08V1(String startDate);
}
