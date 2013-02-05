package pams.service.telemarketing;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pams.repository.dao.*;
import pams.repository.dao.telemarketing.TmSalesInfoMapper;
import pams.repository.model.*;
import pams.repository.model.telemarketing.SalesInfoBean;
import pams.repository.model.telemarketing.SalesStatusBean;
import pams.repository.model.telemarketing.chart.SalesPrdStatusBean;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * �绰Ӫ����ʵ��ҵ����ҵ���ƻ���̸����̸����.
 * User: zhanrui
 * Date: 11-4-5
 * Time: ����7:16
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TmSalesInfoService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TmSalesInfoMapper salesinfoMapper;

    @Autowired
    private SvprdsalinfMapper salesperfMapper;
    @Autowired
    private SvprdsalinfDelMapper salesperfDelMapper;
    @Autowired
    private SvprdsalplanMapper salesplanMapper;
    @Autowired
    private SvintvinfMapper interviewMapper;
    @Autowired
    private SvintvinfDelMapper interviewDelMapper;

    /**
     * ����ָ�������� �ض���Ա��ҵ����¼
     * @param operid
     * @param start
     * @param end
     * @return
     */
    public List<Svprdsalinf> selectSalesInfoRecords(String operid, String start, String end) {
        SvprdsalinfExample example = new SvprdsalinfExample();
        example.createCriteria().andTelleridEqualTo(operid).andTxndateBetween(start, end);
        example.setOrderByClause("txndate,txntime");
        return this.salesperfMapper.selectByExample(example);
    }

    /**
     * ����ָ�������� �ض���Ա�嵥��ҵ����¼
     * @param ptoperIdList
     * @param start
     * @param end
     * @return
     */
    public List<Svprdsalinf> selectSalesInfoRecords(List<String> ptoperIdList, String start, String end) {
        SvprdsalinfExample example = new SvprdsalinfExample();
        example.createCriteria().andTelleridIn(ptoperIdList).andTxndateBetween(start, end);
        return this.salesperfMapper.selectByExample(example);
    }
    /**
     * ����ָ�������� �ض���Ա��ҵ���ƻ���¼
     * @param operid
     * @param start
     * @param end
     * @return
     */
    public List<Svprdsalplan> selectSalesPlanRecords(String operid, String start, String end) {
        SvprdsalplanExample example = new SvprdsalplanExample();
        example.createCriteria().andTelleridEqualTo(operid).andTxndateBetween(start, end);
        example.setOrderByClause("txndate,txntime");
        return this.salesplanMapper.selectByExample(example);
    }

    /**
     * ����ָ�������� �ض���Ա�嵥��ҵ���ƻ���¼
     * @param ptoperIdList
     * @param start
     * @param end
     * @return
     */
    public List<Svprdsalplan> selectSalesPlanRecords(List<String> ptoperIdList, String start, String end) {
        SvprdsalplanExample example = new SvprdsalplanExample();
        example.createCriteria().andTelleridIn(ptoperIdList).andTxndateBetween(start, end);
        return this.salesplanMapper.selectByExample(example);
    }

    /**
     * ����ָ�������� �ض���Ա�ķ�̸��¼
     * @param operid
     * @param start
     * @param end
     * @return
     */
    public List<Svintvinf> selectInterviewRecords(String operid, String start, String end) {
        SvintvinfExample example = new SvintvinfExample();
        example.createCriteria().andTelleridEqualTo(operid).andTxndateBetween(start, end);
        return this.interviewMapper.selectByExample(example);
    }

    /**
     * ����ָ�������� �ض���Ա�嵥�ķ�̸��¼
     * @param ptoperIdList
     * @param start
     * @param end
     * @return
     */
    public List<Svintvinf> selectInterviewRecords(List<String> ptoperIdList, String start, String end) {
        SvintvinfExample example = new SvintvinfExample();
        example.createCriteria().andTelleridIn(ptoperIdList).andTxndateBetween(start, end);
        return this.interviewMapper.selectByExample(example);
    }

    /**
     * ��������ɾ��ҵ����¼
     * @return
     */
    @Transactional
    public int deleteSalesInfoOneRecord(SalesInfoBean record, String operid) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        SvprdsalinfDel delRecord = new SvprdsalinfDel();
        PropertyUtils.copyProperties(delRecord, record);
        delRecord.setDeloperid(operid);
        delRecord.setDeloperdate(new Date());
        salesperfDelMapper.insert(delRecord);
        return salesperfMapper.deleteByPrimaryKey(record.getGuid());
    }
    public int deleteSalesPlanOneRecord(String guid){
        return salesplanMapper.deleteByPrimaryKey(guid);
    }

    @Transactional
    public int deleteInterviewOneRecord(SalesInfoBean record, String operid) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        SvintvinfDel delRecord = new SvintvinfDel();
        PropertyUtils.copyProperties(delRecord, record);
        delRecord.setDeloperid(operid);
        delRecord.setDeloperdate(new Date());
        interviewDelMapper.insert(delRecord);
        return interviewMapper.deleteByPrimaryKey(record.getGuid());
    }

    public int insertSalesInfo(Svprdsalinf record){
        return salesperfMapper.insert(record);
    }
    public int insertSalesPlan(Svprdsalplan record){
        return salesplanMapper.insert(record);
    }
    public int insertInterview(Svintvinf record){
        return interviewMapper.insert(record);
    }

    /**
     * ���ݲ�ѯ����ͳ�� ʵ��ҵ�� ���� ����
     * @param branchid
     * @param startdate
     * @param enddate
     * @return
     */
    public int countSalesinfoBySearchCondition(String branchid,String startdate,String enddate,String prdid){
        return salesinfoMapper.countSalesinfoBySearchCondition(branchid, startdate, enddate, prdid);
    }
    /**
     * ���ݲ�ѯ�������� ʵ��ҵ�� ����
     * @param branchid
     * @param startdate
     * @param enddate
     * @return
     */
    public List<SalesInfoBean> selectSalesinfoBySearchCondition(String branchid,String startdate,String enddate,String prdid){
        return salesinfoMapper.selectSalesinfoBySearchCondition(branchid, startdate, enddate, prdid);
    }

    /**
     * ���ݲ�ѯ����ͳ�� �ƻ�ҵ�� ��������
     * @param branchid
     * @param startdate
     * @param enddate
     * @return
     */
    public int countSalesplanBySearchCondition(String branchid,String startdate,String enddate,String prdid){
        return salesinfoMapper.countSalesplanBySearchCondition(branchid, startdate, enddate, prdid);
    }
    /**
     * ���ݲ�ѯ�������� �ƻ�ҵ�� ����
     * @param branchid
     * @param startdate
     * @param enddate
     * @return
     */
    public List<SalesInfoBean> selectSalesplanBySearchCondition(String branchid,String startdate,String enddate,String prdid){
        return salesinfoMapper.selectSalesplanBySearchCondition(branchid, startdate, enddate, prdid);
    }

    /**
     * ���ݲ�ѯ����ͳ�� ��̸ ����  ����
     * @param branchid
     * @param startdate
     * @param enddate
     * @return
     */
    public int countInterviewBySearchCondition(String branchid,String startdate,String enddate,String prdid){
        return salesinfoMapper.countInterviewBySearchCondition(branchid, startdate, enddate, prdid);
    }
    /**
     * ���ݲ�ѯ�������� ��̸ ����
     * @param branchid
     * @param startdate
     * @param enddate
     * @return
     */
    public List<SalesInfoBean> selectInterviewBySearchCondition(String branchid,String startdate,String enddate,String prdid){
        return salesinfoMapper.selectInterviewBySearchCondition(branchid, startdate, enddate, prdid);
    }

    /**
     * ͳ��ҵ�����а���Ʒ�����������������
     * @return
     */
    public List<SalesStatusBean> analysisSalesDataForPieChart(){
         return salesinfoMapper.analysisSalesDataForPieChart();
    }
    public List<SalesPrdStatusBean> analysisSalesDataForNumberTypeCategoryChart(String prdid){
         return salesinfoMapper.analysisSalesDataForNumberType(prdid);
    }
    public List<SalesPrdStatusBean> analysisSalesDataForAmtTypeCategoryChart(String prdid){
         return salesinfoMapper.analysisSalesDataForAmtType(prdid);
    }

}
