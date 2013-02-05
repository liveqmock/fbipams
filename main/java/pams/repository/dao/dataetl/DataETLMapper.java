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
    int updateNullCertTypeRecords(@Param("rptDate") String rptDate, @Param("certType") String certType);


    //����ʽ�����
    int deleteLargeFlowData(String startDate);
    int importLargeFlowRecords(String startDate);
}
