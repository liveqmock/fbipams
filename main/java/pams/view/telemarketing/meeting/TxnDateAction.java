package pams.view.telemarketing.meeting;

import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.dao.SvmeetinginfMapper;
import pams.repository.model.Svmeetinginf;
import pams.repository.model.SvmeetinginfExample;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-3-18
 * Time: 上午11:20
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@ViewScoped
public class TxnDateAction implements Serializable {
    private String sysDate;
    private String sysTime;
    private Date txnDate;
    private String inputMode;
    @ManagedProperty(value = "#{svmeetinginfMapper}")
    private SvmeetinginfMapper meetingMapper;


    public Date getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    public String getSysDate() {
        return sysDate;
    }

    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    public String getSysTime() {
        return sysTime;
    }

    public void setSysTime(String sysTime) {
        this.sysTime = sysTime;
    }

    public String getInputMode() {
        return inputMode;
    }

    public void setInputMode(String inputMode) {
        this.inputMode = inputMode;
    }

    public SvmeetinginfMapper getMeetingMapper() {
        return meetingMapper;
    }

    public void setMeetingMapper(SvmeetinginfMapper meetingMapper) {
        this.meetingMapper = meetingMapper;
    }

    //====================================================
    @PostConstruct
    public void init() {
        this.sysDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.sysTime = new SimpleDateFormat("HH:mm:ss").format(new Date());

        this.txnDate = new Date();

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        this.inputMode = request.getParameter("type");
        if (inputMode == null) {
            MessageUtil.addError("模式参数错误");
        }
    }

    public String onNextButton() {
        String strTxnDate = new SimpleDateFormat("yyyy-MM-dd").format(this.txnDate);

        if ("update".equals(this.inputMode)) {
            OperatorManager om = SystemService.getOperatorManager();
            String branchid = om.getOperator().getDeptid();

            //根据输入的日期和网点号 查找此日会议记录 若找到 则为更新模式
            SvmeetinginfExample meetingExample = new SvmeetinginfExample();
            meetingExample.createCriteria().andBranchidEqualTo(branchid).andTxndateEqualTo(strTxnDate);

            List<Svmeetinginf> meetingList = meetingMapper.selectByExample(meetingExample);
            if (meetingList.size() >= 1) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("txnDate", strTxnDate);
                return "meetingInput.xhtml";
            } else {
                MessageUtil.addError("此日期本网点无夕会记录。");
                return null;
            }
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("txnDate", strTxnDate);
        return "meetingInput.xhtml";
    }

}

