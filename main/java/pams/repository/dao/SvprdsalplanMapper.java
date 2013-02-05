package pams.repository.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import pams.repository.model.SvprdsalinfChd;
import pams.repository.model.Svprdsalplan;
import pams.repository.model.SvprdsalplanExample;

import java.util.List;

@Component
public interface SvprdsalplanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALPLAN
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    int countByExample(SvprdsalplanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALPLAN
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    int deleteByExample(SvprdsalplanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALPLAN
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    int deleteByPrimaryKey(String guid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALPLAN
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    int insert(Svprdsalplan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALPLAN
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    int insertSelective(Svprdsalplan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALPLAN
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    List<Svprdsalplan> selectByExample(SvprdsalplanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALPLAN
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    Svprdsalplan selectByPrimaryKey(String guid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALPLAN
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    int updateByExampleSelective(@Param("record") Svprdsalplan record, @Param("example") SvprdsalplanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALPLAN
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    int updateByExample(@Param("record") Svprdsalplan record, @Param("example") SvprdsalplanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALPLAN
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    int updateByPrimaryKeySelective(Svprdsalplan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALPLAN
     *
     * @mbggenerated Fri Apr 29 10:31:02 CST 2011
     */
    int updateByPrimaryKey(Svprdsalplan record);

    /**
     * createBy: haiyu
     * remark: 获取相同网点的各产品种类所有数据
     */
    List<SvprdsalinfChd> selectForBranch1(@Param("prdid") String prdid, @Param("operid") String operid
            , @Param("enutype") String enutype, @Param("begindate") String begindate, @Param("enddate") String enddate);

    /**
     * createBy: haiyu
     * remark: 获取相同网点的各产品种类所有数据
     */
    List<SvprdsalinfChd> selectForBranch2(@Param("prdid") String prdid, @Param("operid") String operid
            , @Param("begindate") String begindate, @Param("enddate") String enddate);

    /**
     * createBy: haiyu
     * remark: 获取相同网点的各产品种类所有数据
     */
    List<SvprdsalinfChd> selectForTeller1(@Param("prdid") String prdid, @Param("operid") String operid
            , @Param("enutype") String enutype, @Param("begindate") String begindate, @Param("enddate") String enddate);

    /**
     * createBy: haiyu
     * remark: 获取相同网点的各产品种类所有数据
     */
    List<SvprdsalinfChd> selectForTeller2(@Param("prdid") String prdid, @Param("operid") String operid
            , @Param("begindate") String begindate, @Param("enddate") String enddate);


}