package pams.service.dataetl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pams.repository.dao.SvCmsCustbaseMapper;
import pams.repository.dao.dataetl.DataETLMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 客户基本信息维护，客户分派.
 * User: zhanrui
 * Date: 12-12-17
 * Time: 下午3:50
 */
@Service
public class DataETLService {
    private static final Logger logger = LoggerFactory.getLogger(DataETLService.class);

    @Autowired
    private SvCmsCustbaseMapper cmsCustbaseMapper;
    @Autowired
    private DataETLMapper dataETLMapper;

    public int mergeCustBaseRecords(String rptDate) {
        //检查参数
        try {
            new SimpleDateFormat("yyyyMMdd").parse(rptDate);
        } catch (ParseException e) {
            throw new RuntimeException("日期格式错误!", e);
        }

        //merge前对证件号为空的进行处理
        updateNullCertTypeRecords(rptDate, "居民身份证");
        int cnt =  dataETLMapper.mergeCustBaseRecords(rptDate);
        dataETLMapper.updateCustBaseRecords4CertInfo();
        return cnt;
    }

    private int updateNullCertTypeRecords(String rptDate, String certType){
         return dataETLMapper.updateNullCertTypeRecords(rptDate,certType);
    }



    public void importLargeFlowRecords(String startDate){
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        } catch (ParseException e) {
            throw new RuntimeException("日期格式错误!", e);
        }
        dataETLMapper.deleteLargeFlowData(startDate);
        dataETLMapper.importLargeFlowRecords(startDate);
    }

    public void importData_RptA07V1(String startDate){
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        } catch (ParseException e) {
            throw new RuntimeException("日期格式错误!", e);
        }
        dataETLMapper.deleteData_RptA07V1(startDate);
        dataETLMapper.importRecords_RptA07V1(startDate);
    }
    public void importData_RptA08V1(String startDate){
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        } catch (ParseException e) {
            throw new RuntimeException("日期格式错误!", e);
        }
        dataETLMapper.deleteData_RptA08V1(startDate);
        dataETLMapper.importRecords_RptA08V1(startDate);
    }
    public void importData_RptA11V1(String startDate, String endDate){
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
        } catch (ParseException e) {
            throw new RuntimeException("日期格式错误!", e);
        }
        dataETLMapper.deleteData_RptA11V1(startDate, endDate);
        dataETLMapper.importRecords_RptA11V1(startDate, endDate);
    }
    public void deleteData_RptA11V1(String startDate, String endDate){
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
        } catch (ParseException e) {
            throw new RuntimeException("日期格式错误!", e);
        }
        dataETLMapper.deleteData_RptA11V1(startDate, endDate);
    }
    //=====================================================================
}
