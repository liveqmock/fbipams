package pams.view.telemarketing;

import pams.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-3-18
 * Time: 上午11:20
 * To change this template use File | Settings | File Templates.
 */


@ManagedBean
@ViewScoped
public class SalesDateAction implements Serializable {

    private String sysDate;
    private String sysTime;
    private String txnDate;

    //0：单人输入模式   1：网点批量输入模式
    private String batchInputMode;

    //程序调用路径
    //perf：处理业绩表
    //plan：处理业绩计划表
    //interview：处理访谈表
    private String routerPath;

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
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


    //====================================================
    @PostConstruct
    public void init() {
        this.sysDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.txnDate = this.sysDate;

        this.sysTime = new SimpleDateFormat("HH:mm:ss").format(new Date());

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        this.batchInputMode = request.getParameter("batch");
        if (batchInputMode == null) {
            MessageUtil.addError("模式参数错误");
        }
        this.routerPath = request.getParameter("path");
        if (routerPath == null) {
            MessageUtil.addError("路径参数错误");
        }
        if ("plan".equals(this.routerPath)) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            cal.set(Calendar.DAY_OF_WEEK, 2);
            this.txnDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        }

    }

    public String onNextButton() {
        //String strTxnDate = new SimpleDateFormat("yyyy-MM-dd").format(this.txnDate);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("txnDate", this.txnDate);
        //单笔或批量输入模式
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("batch", this.batchInputMode);
        //出路数据表选择
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("path", this.routerPath);

        if (("perf".equals(this.routerPath)) ||
                ("plan".equals(this.routerPath))) {
            if (this.batchInputMode.equals("0")) {
                return "salesInfoSingleInput.xhtml";
            } else {
                return "salesInfoBatchInput.xhtml";
            }
        } else if ("interview".equals(this.routerPath)) {
            if (this.batchInputMode.equals("0")) {
                return "interviewInfoSingleInput.xhtml";
            } else {
                return "interviewInfoBatchInput.xhtml";
            }
        } else {
            return null;
        }
    }

}

