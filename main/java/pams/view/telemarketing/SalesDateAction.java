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
 * Time: ����11:20
 * To change this template use File | Settings | File Templates.
 */


@ManagedBean
@ViewScoped
public class SalesDateAction implements Serializable {

    private String sysDate;
    private String sysTime;
    private String txnDate;

    //0����������ģʽ   1��������������ģʽ
    private String batchInputMode;

    //�������·��
    //perf������ҵ����
    //plan������ҵ���ƻ���
    //interview�������̸��
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
            MessageUtil.addError("ģʽ��������");
        }
        this.routerPath = request.getParameter("path");
        if (routerPath == null) {
            MessageUtil.addError("·����������");
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
        //���ʻ���������ģʽ
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("batch", this.batchInputMode);
        //��·���ݱ�ѡ��
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

