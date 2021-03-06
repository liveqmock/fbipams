package pams.repository.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import pams.repository.model.SvCmsCustbase;
import pams.repository.model.SvCmsCustbaseExample;

import java.util.List;

@Component
public interface SvCmsCustbaseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CMS_CUSTBASE
     *
     * @mbggenerated Sat Jan 26 23:08:57 CST 2013
     */
    int countByExample(SvCmsCustbaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CMS_CUSTBASE
     *
     * @mbggenerated Sat Jan 26 23:08:57 CST 2013
     */
    int deleteByExample(SvCmsCustbaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CMS_CUSTBASE
     *
     * @mbggenerated Sat Jan 26 23:08:57 CST 2013
     */
    int deleteByPrimaryKey(String guid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CMS_CUSTBASE
     *
     * @mbggenerated Sat Jan 26 23:08:57 CST 2013
     */
    int insert(SvCmsCustbase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CMS_CUSTBASE
     *
     * @mbggenerated Sat Jan 26 23:08:57 CST 2013
     */
    int insertSelective(SvCmsCustbase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CMS_CUSTBASE
     *
     * @mbggenerated Sat Jan 26 23:08:57 CST 2013
     */
    List<SvCmsCustbase> selectByExample(SvCmsCustbaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CMS_CUSTBASE
     *
     * @mbggenerated Sat Jan 26 23:08:57 CST 2013
     */
    SvCmsCustbase selectByPrimaryKey(String guid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CMS_CUSTBASE
     *
     * @mbggenerated Sat Jan 26 23:08:57 CST 2013
     */
    int updateByExampleSelective(@Param("record") SvCmsCustbase record, @Param("example") SvCmsCustbaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CMS_CUSTBASE
     *
     * @mbggenerated Sat Jan 26 23:08:57 CST 2013
     */
    int updateByExample(@Param("record") SvCmsCustbase record, @Param("example") SvCmsCustbaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CMS_CUSTBASE
     *
     * @mbggenerated Sat Jan 26 23:08:57 CST 2013
     */
    int updateByPrimaryKeySelective(SvCmsCustbase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_CMS_CUSTBASE
     *
     * @mbggenerated Sat Jan 26 23:08:57 CST 2013
     */
    int updateByPrimaryKey(SvCmsCustbase record);
}