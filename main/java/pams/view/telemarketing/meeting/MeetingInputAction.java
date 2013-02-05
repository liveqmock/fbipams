package pams.view.telemarketing.meeting;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pams.common.SystemService;
import pams.common.utils.MessageUtil;
import pams.repository.model.Svmeetingevent;
import pams.repository.model.Svmeetinginf;
import pams.service.telemarketing.MeetingInfoService;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-4-1
 * Time: 下午4:50
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@ViewScoped
public class MeetingInputAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private int INPUTMODE_INSERT = 1;
    private int INPUTMODE_UPDATE = 2;

    private String operid;
    private String branchid;
    private String txndate;
    private MeetingInfoVO vo;

    private Svmeetinginf meetinginf = null;
    private int inputMode = INPUTMODE_INSERT;

    @ManagedProperty(name = "meetingInfoService", value = "#{meetingInfoService}")
    private MeetingInfoService meetingInfoService;

    //========================================
    public MeetingInfoVO getVo() {
        return vo;
    }

    public void setVo(MeetingInfoVO vo) {
        this.vo = vo;
    }

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public void setMeetingInfoService(MeetingInfoService meetingInfoService) {
        this.meetingInfoService = meetingInfoService;
    }

    //========================================

    @PostConstruct
    public void init() {
        OperatorManager om = SystemService.getOperatorManager();
        this.operid = om.getOperatorId();
        this.branchid = om.getOperator().getDeptid();

        FacesContext context = FacesContext.getCurrentInstance();
        Map params = context.getExternalContext().getSessionMap();

        this.txndate = (String) params.get("txnDate");
        params.remove("txnDate");
        if (StringUtils.isEmpty(this.txndate)) {
            logger.error("日期参数错误！");
            MessageUtil.addError("日期参数错误！");
            return;
        }
        initUIData();
    }

    /**
     * 初始化页面bean
     */
    private void initUIData() {
        //根据输入的日期和网点号 查找此日会议记录 若找到 则为更新模式
        List<Svmeetinginf> meetingList = meetingInfoService.selectMeetingInfoByBranchIdAndDate(this.branchid, this.txndate);
        this.vo = new MeetingInfoVO(this.operid);
        if (meetingList.size() >= 1) {
            this.meetinginf = meetingList.get(0);
            String meetingid = meetingList.get(0).getGuid();
            List<Svmeetingevent> eventList = meetingInfoService.selectEventInfo(meetingid);
            for (Svmeetingevent event : eventList) {
                int seqno = event.getSeqno();
                if ("1".equals(event.getEventtype())) {
                    this.vo.setSuccessEvent1(event);
                } else {
                    switch (seqno) {
                        case 2:
                            this.vo.setRefuseEvent1(event);
                            break;
                        case 3:
                            this.vo.setRefuseEvent2(event);
                            break;
                        case 4:
                            this.vo.setRefuseEvent3(event);
                            break;
                        default:
                    }
                }
            }
        }


    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String onSaveBtnClick() {

        if (this.meetinginf == null) {
            insertRecord();
            MessageUtil.addInfo("新的会议记录已保存完成。");
        } else {
            updateRecord();
            initUIData();
            MessageUtil.addInfo("会议记录已更新完成。");
        }
        return null;
    }

    //====================
    private void updateRecord() {
        Date date = new Date();
        String txntime = new SimpleDateFormat("HH:mm:ss").format(date);

        //修改主表的操作时间和交易时间
        meetinginf.setOperdate(date);
        meetinginf.setTxntime(txntime);

        //更新时间表 暂未对recversion进行处理
        this.vo.getSuccessEvent1().setOperdate(date);
        this.vo.getSuccessEvent1().setTxntime(txntime);

        this.vo.getRefuseEvent1().setOperdate(date);
        this.vo.getRefuseEvent1().setTxntime(txntime);

        this.vo.getRefuseEvent2().setOperdate(date);
        this.vo.getRefuseEvent2().setTxntime(txntime);

        this.vo.getRefuseEvent3().setOperdate(date);
        this.vo.getRefuseEvent3().setTxntime(txntime);

        meetingInfoService.updateMainAndDetailRecord(
                this.meetinginf,
                this.vo.getSuccessEvent1(),
                this.vo.getRefuseEvent1(),
                this.vo.getRefuseEvent2(),
                this.vo.getRefuseEvent3()
        );

    }

    /**
     * 新增记录
     */

    private void insertRecord() {
        this.meetinginf = new Svmeetinginf();
        meetinginf.setBranchid(this.branchid);
        meetinginf.setOperid(this.operid);

        Date date = new Date();
        String txntime = new SimpleDateFormat("HH:mm:ss").format(date);

        meetinginf.setOperdate(date);
        meetinginf.setRecversion((long) 0);
        meetinginf.setTxndate(txndate);
        meetinginf.setTxntime(txntime);


        short seqno = 1;
        Svmeetingevent event = this.vo.getSuccessEvent1();
        event.setEventtype("1");
        event.setSeqno(seqno);
        event.setOperid(this.operid);
        event.setOperdate(date);
        event.setTxndate(this.txndate);
        event.setTxntime(txntime);
        event.setRecversion((long) 0);
        seqno++;

        event = this.vo.getRefuseEvent1();
        event.setEventtype("2");
        event.setSeqno(seqno);
        event.setOperid(this.operid);
        event.setOperdate(date);
        event.setTxndate(this.txndate);
        event.setTxntime(txntime);
        event.setRecversion((long) 0);
        seqno++;

        event = this.vo.getRefuseEvent2();
        event.setEventtype("2");
        event.setSeqno(seqno);
        event.setOperid(this.operid);
        event.setOperdate(date);
        event.setTxndate(this.txndate);
        event.setTxntime(txntime);
        event.setRecversion((long) 0);
        seqno++;

        event = this.vo.getRefuseEvent3();
        event.setEventtype("2");
        event.setSeqno(seqno);
        event.setOperid(this.operid);
        event.setOperdate(date);
        event.setTxndate(this.txndate);
        event.setTxntime(txntime);
        event.setRecversion((long) 0);

        meetingInfoService.insertMainAndDetailRecord(
                this.meetinginf,
                this.vo.getSuccessEvent1(),
                this.vo.getRefuseEvent1(),
                this.vo.getRefuseEvent2(),
                this.vo.getRefuseEvent3()
        );

    }
}
