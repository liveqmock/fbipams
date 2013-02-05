package pams.view.telemarketing.meeting;

import pams.repository.model.Svmeetingevent;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-4-1
 * Time: ÏÂÎç4:57
 * To change this template use File | Settings | File Templates.
 */
public class MeetingInfoVO {
    private Svmeetingevent successEvent1;
    private Svmeetingevent refuseEvent1;
    private Svmeetingevent refuseEvent2;
    private Svmeetingevent refuseEvent3;

    public MeetingInfoVO(String operid){
        successEvent1 = new Svmeetingevent();
        successEvent1.setOperid(operid);
        refuseEvent1 = new Svmeetingevent();
        refuseEvent1.setOperid(operid);
        refuseEvent2 = new Svmeetingevent();
        refuseEvent2.setOperid(operid);
        refuseEvent3 = new Svmeetingevent();
        refuseEvent3.setOperid(operid);
    }
    public Svmeetingevent getSuccessEvent1() {
        return successEvent1;
    }

    public void setSuccessEvent1(Svmeetingevent successEvent1) {
        this.successEvent1 = successEvent1;
    }

    public Svmeetingevent getRefuseEvent1() {
        return refuseEvent1;
    }

    public void setRefuseEvent1(Svmeetingevent refuseEvent1) {
        this.refuseEvent1 = refuseEvent1;
    }

    public Svmeetingevent getRefuseEvent2() {
        return refuseEvent2;
    }

    public void setRefuseEvent2(Svmeetingevent refuseEvent2) {
        this.refuseEvent2 = refuseEvent2;
    }

    public Svmeetingevent getRefuseEvent3() {
        return refuseEvent3;
    }

    public void setRefuseEvent3(Svmeetingevent refuseEvent3) {
        this.refuseEvent3 = refuseEvent3;
    }
}
