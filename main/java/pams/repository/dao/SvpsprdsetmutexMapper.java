package pams.repository.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import pams.repository.model.Svpsprdsetmutex;
import pams.repository.model.SvpsprdsetmutexExample;

import java.util.List;
@Component

public interface SvpsprdsetmutexMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSPRDSETMUTEX
     *
     * @mbggenerated Thu Apr 21 10:11:26 CST 2011
     */
    int countByExample(SvpsprdsetmutexExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSPRDSETMUTEX
     *
     * @mbggenerated Thu Apr 21 10:11:26 CST 2011
     */
    int deleteByExample(SvpsprdsetmutexExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSPRDSETMUTEX
     *
     * @mbggenerated Thu Apr 21 10:11:26 CST 2011
     */
    int insert(Svpsprdsetmutex record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSPRDSETMUTEX
     *
     * @mbggenerated Thu Apr 21 10:11:26 CST 2011
     */
    int insertSelective(Svpsprdsetmutex record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSPRDSETMUTEX
     *
     * @mbggenerated Thu Apr 21 10:11:26 CST 2011
     */
    List<Svpsprdsetmutex> selectByExample(SvpsprdsetmutexExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSPRDSETMUTEX
     *
     * @mbggenerated Thu Apr 21 10:11:26 CST 2011
     */
    int updateByExampleSelective(@Param("record") Svpsprdsetmutex record, @Param("example") SvpsprdsetmutexExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSPRDSETMUTEX
     *
     * @mbggenerated Thu Apr 21 10:11:26 CST 2011
     */
    int updateByExample(@Param("record") Svpsprdsetmutex record, @Param("example") SvpsprdsetmutexExample example);
}