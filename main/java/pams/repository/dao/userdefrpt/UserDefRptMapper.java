package pams.repository.dao.userdefrpt;

import org.springframework.stereotype.Component;
import pams.repository.model.ClsUdRptdata;
import pams.repository.model.userdefrpt.UserDefRptVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-1-24
 * Time: обнГ1:40
 * To change this template use File | Settings | File Templates.
 */
@Component
public interface UserDefRptMapper {
    int countRecords(UserDefRptVO paramBean);
    List<ClsUdRptdata> selectRecords(UserDefRptVO paramBean);
    List<ClsUdRptdata> selectPagedRecords(UserDefRptVO paramBean);
}
