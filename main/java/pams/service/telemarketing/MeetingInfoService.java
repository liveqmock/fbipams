package pams.service.telemarketing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pams.repository.dao.SvmeetingeventMapper;
import pams.repository.dao.SvmeetinginfMapper;
import pams.repository.model.Svmeetingevent;
import pams.repository.model.SvmeetingeventExample;
import pams.repository.model.Svmeetinginf;
import pams.repository.model.SvmeetinginfExample;

import java.util.List;

/**
 * 夕会表处理.
 * User: zhanrui
 * Date: 11-4-5
 * Time: 下午4:31
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MeetingInfoService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SvmeetinginfMapper meetingMapper;
    @Autowired
    private SvmeetingeventMapper eventMapper;


    public SvmeetingeventMapper getEventMapper() {
        return eventMapper;
    }

    public void setEventMapper(SvmeetingeventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    public SvmeetinginfMapper getMeetingMapper() {
        return meetingMapper;
    }

    public void setMeetingMapper(SvmeetinginfMapper meetingMapper) {
        this.meetingMapper = meetingMapper;
    }

    //=======================================================

    /**
     * 根据机构号和交易日期查找唯一的夕会记录
     * @param branchid
     * @param txndate
     * @return
     */
    public List<Svmeetinginf> selectMeetingInfoByBranchIdAndDate(String branchid, String txndate){
        SvmeetinginfExample meetingExample = new SvmeetinginfExample();
        meetingExample.createCriteria().andBranchidEqualTo(branchid).andTxndateEqualTo(txndate);

        return  meetingMapper.selectByExample(meetingExample);
    }

    public List<Svmeetingevent> selectEventInfo(String meetingid){
        SvmeetingeventExample eventExample = new SvmeetingeventExample();
        eventExample.createCriteria().andMeetingidEqualTo(meetingid);
        return eventMapper.selectByExample(eventExample);
    }

    /**
     * 更新夕会主表及相应事件从表
     * @param meetinginf
     * @param successEvent
     * @param refuseEvent1
     * @param refuseEvent2
     * @param refuseEvent3
     */
    @Transactional
    public void updateMainAndDetailRecord(Svmeetinginf meetinginf,
                                          Svmeetingevent successEvent,
                                          Svmeetingevent refuseEvent1,
                                          Svmeetingevent refuseEvent2,
                                          Svmeetingevent refuseEvent3) {

        long meetinfVersion = meetinginf.getRecversion();

        SvmeetinginfExample meetingExample = new SvmeetinginfExample();
        meetingExample.createCriteria().andGuidEqualTo(meetinginf.getGuid()).andRecversionEqualTo(meetinfVersion);
        meetinfVersion++;
        meetinginf.setRecversion(meetinfVersion);
        int rtn = meetingMapper.updateByExample(meetinginf, meetingExample);

        if (rtn == 0) {
            throw new RuntimeException("更新夕会主表失败。");
        }

        eventMapper.updateByPrimaryKey(successEvent);
        eventMapper.updateByPrimaryKey(refuseEvent1);
        eventMapper.updateByPrimaryKey(refuseEvent2);
        eventMapper.updateByPrimaryKey(refuseEvent3);
    }

    /**
     * 新增主表从表记录
     * @param meetinginf
     * @param successEvent
     * @param refuseEvent1
     * @param refuseEvent2
     * @param refuseEvent3
     */

    @Transactional
    public void insertMainAndDetailRecord(Svmeetinginf meetinginf,
                                          Svmeetingevent successEvent,
                                          Svmeetingevent refuseEvent1,
                                          Svmeetingevent refuseEvent2,
                                          Svmeetingevent refuseEvent3) {

        String meetinginfKey = meetingMapper.selectKey();
        meetinginf.setGuid(meetinginfKey);

        meetingMapper.insertByCustomKey(meetinginf);

        //事件表
        successEvent.setMeetingid(meetinginfKey);
        refuseEvent1.setMeetingid(meetinginfKey);
        refuseEvent2.setMeetingid(meetinginfKey);
        refuseEvent3.setMeetingid(meetinginfKey);
        eventMapper.insert(successEvent);
        eventMapper.insert(refuseEvent1);
        eventMapper.insert(refuseEvent2);
        eventMapper.insert(refuseEvent3);
    }

    /**
     * 检索成功案例 注意 机构名放入备注字段 remark
     * @param branchid
     * @param startdate
     * @param enddate
     * @return
     */
    public List<Svmeetingevent> selectSuccessEvents(String branchid, String startdate, String enddate){
        return eventMapper.selectSuccessEvents(branchid, startdate, enddate);
    }
    public List<Svmeetingevent> selectRefuseEvents1(String branchid, String startdate, String enddate){
        return eventMapper.selectRefuseEvents1(branchid, startdate, enddate);
    }
    public List<Svmeetingevent> selectRefuseEvents2(String branchid, String startdate, String enddate){
        return eventMapper.selectRefuseEvents2(branchid, startdate, enddate);
    }
    public List<Svmeetingevent> selectRefuseEvents3(String branchid, String startdate, String enddate){
        return eventMapper.selectRefuseEvents3(branchid, startdate, enddate);
    }

    /**
     * 查询前的统计
     * @param branchid
     * @param startdate
     * @param enddate
     * @return
     */
    public int countSuccessEvents(String branchid, String startdate, String enddate){
        return eventMapper.countSuccessEvents(branchid, startdate, enddate);
    }

}
