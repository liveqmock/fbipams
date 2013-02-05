package pams.repository.dao.telemarketing;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import pams.repository.model.telemarketing.SalesInfoBean;
import pams.repository.model.telemarketing.SalesStatusBean;
import pams.repository.model.telemarketing.chart.SalesPrdStatusBean;

import java.util.List;

@Component
public interface TmSalesInfoMapper {

    /**
     * ���ݻ���id ���� ʵ��ҵ�� ��¼����
     * @param branchid
     * @return
     */
    int countSalesinfoBySearchCondition(@Param("branchid") String branchid, @Param("startdate") String startdate,
                                        @Param("enddate") String enddate, @Param("prdid") String prdid);
    /**
     * ���ݻ���id ����ʵ��ҵ�� ��¼
     * @param branchid
     * @return
     */
    List<SalesInfoBean> selectSalesinfoBySearchCondition(@Param("branchid") String branchid, @Param("startdate") String startdate,
                                                         @Param("enddate") String enddate, @Param("prdid") String prdid);


    //=================================================================

    /**
     * ���ݻ���id ���� ʵ��ҵ�� ��¼����
     * @param branchid
     * @return
     */
    int countSalesplanBySearchCondition(@Param("branchid") String branchid, @Param("startdate") String startdate,
                                        @Param("enddate") String enddate, @Param("prdid") String prdid);
    /**
     * ���ݻ���id ����ʵ��ҵ�� ��¼
     * @param branchid
     * @return
     */
    List<SalesInfoBean> selectSalesplanBySearchCondition(@Param("branchid") String branchid, @Param("startdate") String startdate,
                                                         @Param("enddate") String enddate, @Param("prdid") String prdid);


    //=================================================================

    /**
     * ���ݻ���id ���� ʵ��ҵ�� ��¼����
     * @param branchid
     * @return
     */
    int countInterviewBySearchCondition(@Param("branchid") String branchid, @Param("startdate") String startdate,
                                        @Param("enddate") String enddate, @Param("prdid") String prdid);
    /**
     * ���ݻ���id ����ʵ��ҵ�� ��¼
     * @param branchid
     * @return
     */
    List<SalesInfoBean> selectInterviewBySearchCondition(@Param("branchid") String branchid, @Param("startdate") String startdate,
                                                         @Param("enddate") String enddate, @Param("prdid") String prdid);

    /**
     * ͳ��ҵ�����а���Ʒ�����������������
     * @return
     */
    List<SalesStatusBean> analysisSalesDataForPieChart();
    List<SalesPrdStatusBean> analysisSalesDataForNumberType(@Param("prdid") String prdid);
    List<SalesPrdStatusBean> analysisSalesDataForAmtType(@Param("prdid") String prdid);
}