package pams.repository.dao.test;

import org.springframework.stereotype.Component;
import pams.repository.model.test.MyQueryModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-1-10
 * Time: обнГ4:16
 * To change this template use File | Settings | File Templates.
 */
@Component
public interface MytestMapper {
//    @Select (value = "select count(*) from ptoper")
    public int countCustlist();

    public List<MyQueryModel> selectRecords();
}
