package pams.repository.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import pams.repository.model.SvClsCustinfo;
import pams.repository.model.SvClsCustinfoExample;

import java.util.List;

@Component
public interface SvClsCustinfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CLS_CUSTINFO
     *
     * @mbggenerated Tue Dec 18 13:28:46 CST 2012
     */
    int countByExample(SvClsCustinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CLS_CUSTINFO
     *
     * @mbggenerated Tue Dec 18 13:28:46 CST 2012
     */
    int deleteByExample(SvClsCustinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CLS_CUSTINFO
     *
     * @mbggenerated Tue Dec 18 13:28:46 CST 2012
     */
    int deleteByPrimaryKey(String guid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CLS_CUSTINFO
     *
     * @mbggenerated Tue Dec 18 13:28:46 CST 2012
     */
    int insert(SvClsCustinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CLS_CUSTINFO
     *
     * @mbggenerated Tue Dec 18 13:28:46 CST 2012
     */
    int insertSelective(SvClsCustinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CLS_CUSTINFO
     *
     * @mbggenerated Tue Dec 18 13:28:46 CST 2012
     */
    List<SvClsCustinfo> selectByExample(SvClsCustinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CLS_CUSTINFO
     *
     * @mbggenerated Tue Dec 18 13:28:46 CST 2012
     */
    SvClsCustinfo selectByPrimaryKey(String guid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CLS_CUSTINFO
     *
     * @mbggenerated Tue Dec 18 13:28:46 CST 2012
     */
    int updateByExampleSelective(@Param("record") SvClsCustinfo record, @Param("example") SvClsCustinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CLS_CUSTINFO
     *
     * @mbggenerated Tue Dec 18 13:28:46 CST 2012
     */
    int updateByExample(@Param("record") SvClsCustinfo record, @Param("example") SvClsCustinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CLS_CUSTINFO
     *
     * @mbggenerated Tue Dec 18 13:28:46 CST 2012
     */
    int updateByPrimaryKeySelective(SvClsCustinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CLS_CUSTINFO
     *
     * @mbggenerated Tue Dec 18 13:28:46 CST 2012
     */
    int updateByPrimaryKey(SvClsCustinfo record);

    //=======
//    List<SvClsCustinfo> selectTest();
    int selectTest();
}