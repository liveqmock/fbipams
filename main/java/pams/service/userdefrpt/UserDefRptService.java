package pams.service.userdefrpt;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pams.repository.dao.ClsUdFldinfoMapper;
import pams.repository.dao.ClsUdRptdataMapper;
import pams.repository.dao.ClsUdTblinfoMapper;
import pams.repository.dao.userdefrpt.UserDefRptMapper;
import pams.repository.model.*;
import pams.repository.model.userdefrpt.UserDefRptVO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 14-4-24
 * Time: 下午2:29
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserDefRptService {
    @Autowired
    private UserDefRptMapper userDefRptMapper;
    @Autowired
    private ClsUdRptdataMapper rptdataMapper;
    @Autowired
    private ClsUdFldinfoMapper fldinfoMapper;
    @Autowired
    private ClsUdTblinfoMapper tblinfoMapper;

    public List<ClsUdRptdata> selectPagedLargeDepFlowRecords(UserDefRptVO paramBean) {
        return userDefRptMapper.selectPagedRecords(paramBean);
    }

    public UserDefRptMapper getUserDefRptMapper() {
        return userDefRptMapper;
    }

    public void setUserDefRptMapper(UserDefRptMapper userDefRptMapper) {
        this.userDefRptMapper = userDefRptMapper;
    }

    public ClsUdRptdataMapper getRptdataMapper() {
        return rptdataMapper;
    }

    public void setRptdataMapper(ClsUdRptdataMapper rptdataMapper) {
        this.rptdataMapper = rptdataMapper;
    }


    //rptdata
    public void clearRptData(String rptno){
        ClsUdRptdataExample example = new ClsUdRptdataExample();
        example.createCriteria().andRptnoEqualTo(rptno);
        rptdataMapper.deleteByExample(example);
    }
    public void insertRptData(String rptno, String[] fields) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ClsUdRptdata record = new ClsUdRptdata();
        Class clazz = record.getClass();

        record.setRptno(rptno);
        record.setBranchid(fields[0]);

        for (int i = 1; i< fields.length; i++){
                String seq = StringUtils.leftPad("" + i, 2, "0");
                Method m = clazz.getDeclaredMethod("setFld" + seq, String.class);
                m.invoke(record, fields[i]);
        }
        rptdataMapper.insert(record);
    }
/*
    public void insertRptData(String rptno, List<String> fields) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ClsUdRptdata record = new ClsUdRptdata();
        Class clazz = record.getClass();

        record.setRptno(rptno);
        record.setBranchid(fields.get(0));

        for (int i = 1; i< fields.size(); i++){
                String seq = StringUtils.leftPad("" + i, 2, "0");
                Method m = clazz.getDeclaredMethod("setFld" + seq, String.class);
                m.invoke(record, fields.get(i));
        }
        rptdataMapper.insert(record);
    }
*/


    //columns   info
    public List<ClsUdFldinfo> selectColumnsDefine(String rptno) {
        ClsUdFldinfoExample example = new ClsUdFldinfoExample();
        example.createCriteria().andRptnoEqualTo(rptno);
        example.setOrderByClause(" fldsn ");
        return fldinfoMapper.selectByExample(example);
    }

    public void clearColumnNames(String rptno) {
        ClsUdFldinfoExample example = new ClsUdFldinfoExample();
        example.createCriteria().andRptnoEqualTo(rptno);
        fldinfoMapper.deleteByExample(example);
    }

    public void insertColumnDefInfo(String rptno, String[] fields) {
        for (int i = 1; i< fields.length; i++){
            ClsUdFldinfo clsUdFldinfo = new ClsUdFldinfo();
            clsUdFldinfo.setRptno(rptno);
            clsUdFldinfo.setFldname(fields[i]);
            clsUdFldinfo.setFldsn(StringUtils.leftPad("" + i, 2, "0"));
            clsUdFldinfo.setFldtype("0"); //暂时统一为字符类型
            clsUdFldinfo.setRecver(1);
            fldinfoMapper.insert(clsUdFldinfo);
        }
    }
/*
    public void insertColumnDefInfo(String rptno, List<String> fields) {
        for (int i = 1; i< fields.size(); i++){
            ClsUdFldinfo clsUdFldinfo = new ClsUdFldinfo();
            clsUdFldinfo.setRptno(rptno);
            clsUdFldinfo.setFldname(fields.get(i));
            clsUdFldinfo.setFldsn(StringUtils.leftPad("" + i, 2, "0"));
            clsUdFldinfo.setFldtype("0"); //暂时统一为字符类型
            clsUdFldinfo.setRecver(1);
            fldinfoMapper.insert(clsUdFldinfo);
        }
    }
*/


    //Table def info
    public List<ClsUdTblinfo> selectTblInfos() {
        ClsUdTblinfoExample example = new ClsUdTblinfoExample();
        //example.createCriteria().andStatusEqualTo("0");
        example.createCriteria();
        example.setOrderByClause(" rptno desc");

        return tblinfoMapper.selectByExample(example);
    }
    public ClsUdTblinfo selectTblInfo(String rptno) {
        return tblinfoMapper.selectByPrimaryKey(rptno);
    }

    //新增报表
    public void insertTblInfo(ClsUdTblinfo record){
        tblinfoMapper.insert(record);
    }
    //修改报表
    public void modifyTblInfo(ClsUdTblinfo clsUdTblinfo){
        ClsUdTblinfo record = tblinfoMapper.selectByPrimaryKey(clsUdTblinfo.getRptno());
        clsUdTblinfo.setRecver(record.getRecver() + 1);
        tblinfoMapper.updateByPrimaryKeySelective(clsUdTblinfo);
    }

    //更新数据导入时间
    public void updateImportDataDate(String rptno) {
        ClsUdTblinfo record =  tblinfoMapper.selectByPrimaryKey(rptno);
        record.setImpdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        tblinfoMapper.updateByPrimaryKey(record);
    }

    //关闭报表  清空所有相关数据
    public void clearAllRptInfo(String rptno) {
        tblinfoMapper.deleteByPrimaryKey(rptno);
        clearRptData(rptno);
        clearColumnNames(rptno);
    }
}
