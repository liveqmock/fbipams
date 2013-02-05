package pams.view.test;

import pams.common.utils.MessageUtil;
import pams.repository.model.test.MyQueryModel;
import pams.service.test.TestService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-1-10
 * Time: ÏÂÎç3:52
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean (name = "TAction")
public class TestAction {
    private  String  title = "abc";

    private List<MyQueryModel> myQueryModels;

    @ManagedProperty(value = "#{testService}")
    private TestService testService;


    public void onQuery(){
        title = "123456";

        myQueryModels = testService.getRecords();

        //TestService ts = new TestService();
        MessageUtil.addError(testService.getMyString());
    }


    public String getTitle() {
        return title + "=====";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TestService getTestService() {
        return testService;
    }

    public void setTestService(TestService testService) {
        this.testService = testService;
    }

    public List<MyQueryModel> getMyQueryModels() {
        return myQueryModels;
    }

    public void setMyQueryModels(List<MyQueryModel> myQueryModels) {
        this.myQueryModels = myQueryModels;
    }
}
