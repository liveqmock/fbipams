package pams.repository.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import pams.repository.model.Ptdept;
import pams.repository.model.PtdeptExample;

import java.util.List;

@Component

public interface PtdeptMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTDEPT
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int countByExample(PtdeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTDEPT
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int deleteByExample(PtdeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTDEPT
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int deleteByPrimaryKey(String deptid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTDEPT
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int insert(Ptdept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTDEPT
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int insertSelective(Ptdept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTDEPT
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    List<Ptdept> selectByExample(PtdeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTDEPT
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    Ptdept selectByPrimaryKey(String deptid);

    Ptdept selectByOperid(@Param("operid") String operid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTDEPT
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int updateByExampleSelective(@Param("record") Ptdept record, @Param("example") PtdeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTDEPT
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int updateByExample(@Param("record") Ptdept record, @Param("example") PtdeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTDEPT
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int updateByPrimaryKeySelective(Ptdept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTDEPT
     *
     * @mbggenerated Sat Mar 19 22:33:44 CST 2011
     */
    int updateByPrimaryKey(Ptdept record);


    /**
     * 获取机构层次列表
     * @param branchid
     * @return
     */
    @Select("select deptid || '|' || LPad('　', (level - 1) * 2, '　') || deptname" +
            "  from ptdept" +
            " start with deptid = #{branchid}" +
            "connect by prior deptid = parentdeptid")
    List<String> selectBranchLevelString(@Param("branchid") String branchid);

    @Select("select deptid " +
            "  from ptdept" +
            " start with deptid = #{branchid}" +
            "connect by prior deptid = parentdeptid")
    List<String> selectBranchLevelList(@Param("branchid") String branchid);
}