package skyline.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pams.repository.dao.*;
import pams.repository.model.*;
import pub.platform.form.config.SystemAttributeNames;
import pub.platform.security.OperatorManager;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台表处理
 * User: zhanrui
 * Date: 11-4-5
 * Time: 下午7:42
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PlatformService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PtoperMapper ptoperMapper;

    @Autowired

    private PtoplogMapper ptoplogMapper;

    @Autowired
    private PtdeptMapper ptdeptMapper;

    @Autowired
    private PtenudetailMapper enudetailMapper;

    @Autowired
    private PtmenuMapper menuMapper;

    @Autowired
    private SysSchedulerLogMapper sysSchedulerLogMapper;
    /**
     * 查找与指定柜员同网点的柜员列表
     * @param tellerid  指定柜员ID
     * @return   list （not null）
     */
    public List<Ptoper> selectBranchTellers(String tellerid) {
            PtoperExample example = new PtoperExample();
            example.createCriteria().andOperidEqualTo(tellerid);
            List<Ptoper> records = ptoperMapper.selectByExample(example);
            if (records.size() == 0) {
                return records;
            } else {
                String deptid = records.get(0).getDeptid();
                example.clear();
                example.createCriteria().andDeptidEqualTo(deptid);
                return ptoperMapper.selectByExample(example);
            }
    }

    /**
     * 查找指定枚举清单
     * @param enuid
     * @return
     */
    public List<Ptenudetail> selectEnuDetail(String enuid) {
            PtenudetailExample example = new PtenudetailExample();
            example.createCriteria().andEnutypeEqualTo(enuid);
            example.setOrderByClause(" dispno ");
            return enudetailMapper.selectByExample(example);
    }

    //返回 机构号|机构名称
    public List<String> selectBranchLevelString(String branchid){
          return ptdeptMapper.selectBranchLevelString(branchid);
    }

    //返回 机构号
    public  List<String> selectBranchLevelList(String branchid){
        return ptdeptMapper.selectBranchLevelList(branchid);
    }
    /**
     * 获取枚举表中某一项的 扩展定义值
     * @param enuType
     * @param enuItemValue
     * @return
     */
    public String  selectEnuExpandValue(String enuType, String enuItemValue){
        PtenudetailExample example = new PtenudetailExample();
        example.createCriteria().andEnutypeEqualTo(enuType).andEnuitemvalueEqualTo(enuItemValue);
        //TODO 错误判断？
        return enudetailMapper.selectByExample(example).get(0).getEnuitemexpand();
    }

    /**
     * 检索 枚举值和扩展值的对应关系 MAP
     * @param enuType
     * @return
     */
    public Map<String,String> selectEnuItemValueToExpandValueMap(String enuType){
        PtenudetailExample example = new PtenudetailExample();
        example.createCriteria().andEnutypeEqualTo(enuType);
        List <Ptenudetail> records = enudetailMapper.selectByExample(example);
        Map<String,String> enuMap = new HashMap<String, String>();
        for (Ptenudetail record : records) {
            enuMap.put(record.getEnuitemvalue(), record.getEnuitemexpand());
        }
        return enuMap;
    }

    //******************************
    /**
     * 查询某一年的调度日志
     * @param curryear
     * @return
     * @throws ParseException
     */
    public List<SysSchedulerLog> selectSchedulerLogByYear(String curryear) throws ParseException {
        SysSchedulerLogExample example = new SysSchedulerLogExample();
        String firstDayThisYear = new SimpleDateFormat("yyyy-01-01").format(new Date());
        //Date date = new SimpleDateFormat("yyyy-MM-dd").parse(firstDayThisYear);
        //example.createCriteria().andTimeGreaterThanOrEqualTo(date);
        example.createCriteria();
        List<SysSchedulerLog> sysSchedulerLogs = sysSchedulerLogMapper.selectByExample(example);
        return sysSchedulerLogs;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertNewOperationLog(Ptoplog ptoplog){
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) extContext.getSession(true);
        OperatorManager om = (OperatorManager) session.getAttribute(SystemAttributeNames.USER_INFO_NAME);
        if (om == null) {
            throw new RuntimeException("用户未登录！");
        }

        ptoplog.setOperId(om.getOperatorId());
        ptoplog.setOperName(om.getOperatorName());
        ptoplog.setOperBranchid(om.getOperator().getDeptid());
        ptoplog.setHostIp(om.getRemoteAddr());
        ptoplog.setOpDate(new Date());

        ptoplogMapper.insert(ptoplog);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertNewOperationLog(String actionId, String actionName, String opLog){
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) extContext.getSession(true);
        OperatorManager om = (OperatorManager) session.getAttribute(SystemAttributeNames.USER_INFO_NAME);
        if (om == null) {
            throw new RuntimeException("用户未登录！");
        }

        Ptoplog ptoplog = new Ptoplog();
        ptoplog.setOperId(om.getOperatorId());
        ptoplog.setOperName(om.getOperatorName());
        ptoplog.setOperBranchid(om.getOperator().getDeptid());
        ptoplog.setHostIp(om.getRemoteAddr());
        ptoplog.setOpDate(new Date());

        ptoplog.setActionId(actionId);
        ptoplog.setActionName(actionName);
        ptoplog.setOpLog(opLog);

        ptoplogMapper.insert(ptoplog);
    }
}
