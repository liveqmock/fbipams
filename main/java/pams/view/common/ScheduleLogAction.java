package pams.view.common;

import pams.repository.dao.SvprdsalinfMapper;
import pams.repository.dao.SysSchedulerLogMapper;
import pams.repository.model.SysSchedulerLog;
import pams.repository.model.SysSchedulerLogExample;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ≤‚ ‘
 * User: zhanrui
 * Date: 11-3-18
 * Time: …œŒÁ11:20
 * To change this template use File | Settings | File Templates.
 */


@ManagedBean
@ViewScoped
public class ScheduleLogAction {
    private List<SysSchedulerLog> slogList;

    @ManagedProperty(value = "#{sysSchedulerLogMapper}")
    private SysSchedulerLogMapper sLogMapper;

    @ManagedProperty(value = "#{svprdsalinfMapper}")
    private SvprdsalinfMapper salesMapper;

    @ManagedProperty(value = "#{sysSchedulerLogExample}")
    private SysSchedulerLogExample sysSchedulerLogExample;

    public List<SysSchedulerLog> getSlogList() {
        return slogList;
    }

    public void setSlogList(List<SysSchedulerLog> slogList) {
        this.slogList = slogList;
    }

    public SysSchedulerLogMapper getsLogMapper() {
        return sLogMapper;
    }

    public void setsLogMapper(SysSchedulerLogMapper sLogMapper) {
        this.sLogMapper = sLogMapper;
    }

    public SysSchedulerLogExample getSysSchedulerLogExample() {
        return sysSchedulerLogExample;
    }

    public void setSysSchedulerLogExample(SysSchedulerLogExample sysSchedulerLogExample) {
        this.sysSchedulerLogExample = sysSchedulerLogExample;
    }

    public SvprdsalinfMapper getSalesMapper() {
        return salesMapper;
    }

    public void setSalesMapper(SvprdsalinfMapper salesMapper) {
        this.salesMapper = salesMapper;
    }

    //====================================================
    @PostConstruct
    public void init() {
        this.slogList = new ArrayList();
    }

    public String query() {
        sysSchedulerLogExample.createCriteria().andJobidIsNotNull();
        this.slogList = sLogMapper.selectByExample(sysSchedulerLogExample);
//        System.out.println(salesMapper.hashCode());
        return null;
    }

    public String query1() {
//        this.slogList = new ArrayList();
        String strTxnDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("txnDate", strTxnDate);
//        return "qryScheduleLog.xhtml";
//        return "salesInfoList.xhtml";
        return null;
    }

}

