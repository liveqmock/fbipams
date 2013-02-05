package pams.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pams.repository.dao.test.MytestMapper;
import pams.repository.model.test.MyQueryModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-1-10
 * Time: ÏÂÎç4:06
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TestService {

    @Autowired
    private  MytestMapper mytestMapper;


    public String getMyString(){
        return new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date()) + "  " +getCount();
    }

    public int getCount(){
         return mytestMapper.countCustlist();
    }

    public List<MyQueryModel> getRecords(){
        return mytestMapper.selectRecords();
    }
}
