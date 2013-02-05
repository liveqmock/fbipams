package pams.repository.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import pams.repository.model.Ptenudetail;
import pams.repository.model.PtenudetailExample;
import pams.repository.model.PtenudetailKey;

import java.util.List;

@Component

public interface PtenudetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTENUDETAIL
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int countByExample(PtenudetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTENUDETAIL
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int deleteByExample(PtenudetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTENUDETAIL
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int deleteByPrimaryKey(PtenudetailKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTENUDETAIL
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int insert(Ptenudetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTENUDETAIL
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int insertSelective(Ptenudetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTENUDETAIL
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    List<Ptenudetail> selectByExample(PtenudetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTENUDETAIL
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    Ptenudetail selectByPrimaryKey(PtenudetailKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTENUDETAIL
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int updateByExampleSelective(@Param("record") Ptenudetail record, @Param("example") PtenudetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTENUDETAIL
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int updateByExample(@Param("record") Ptenudetail record, @Param("example") PtenudetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTENUDETAIL
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int updateByPrimaryKeySelective(Ptenudetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTENUDETAIL
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int updateByPrimaryKey(Ptenudetail record);
}