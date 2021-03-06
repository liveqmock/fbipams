package pams.repository.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import pams.repository.model.Svprdsalinf;
import pams.repository.model.SvprdsalinfChd;
import pams.repository.model.SvprdsalinfExample;

import java.util.List;

@Component
public interface SvprdsalinfMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALINF
     *
     * @mbggenerated Fri Apr 29 14:06:38 CST 2011
     */
    int countByExample(SvprdsalinfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALINF
     *
     * @mbggenerated Fri Apr 29 14:06:38 CST 2011
     */
    int deleteByExample(SvprdsalinfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALINF
     *
     * @mbggenerated Fri Apr 29 14:06:38 CST 2011
     */
    int deleteByPrimaryKey(String guid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALINF
     *
     * @mbggenerated Fri Apr 29 14:06:38 CST 2011
     */
    int insert(Svprdsalinf record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALINF
     *
     * @mbggenerated Fri Apr 29 14:06:38 CST 2011
     */
    int insertSelective(Svprdsalinf record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALINF
     *
     * @mbggenerated Fri Apr 29 14:06:38 CST 2011
     */
    List<Svprdsalinf> selectByExample(SvprdsalinfExample example);

    /**
     * createBy: haiyu
     * remark: 获取相同网点的各产品种类所有数据*/
    List<SvprdsalinfChd> selectForPrdid(@Param("prdid") String prdid,@Param("operid") String operid
            ,@Param("enutype") String enutype,@Param("txndate") String txndate);

     /**
     * createBy: haiyu
     * remark: 获取相同网点的各产品种类所有数据*/
    List<SvprdsalinfChd> selectForPrdid2(@Param("prdid") String prdid,@Param("operid") String operid
            ,@Param("txndate") String txndate);

    /**
     * createBy: haiyu
     * remark: 获取相同网点的各产品种类所有数据*/
    List<SvprdsalinfChd> selectForOperid(@Param("prdid") String prdid,@Param("operid") String operid
            ,@Param("enutype") String enutype,@Param("txndate") String txndate);

     /**
     * createBy: haiyu
     * remark: 获取相同网点的各产品种类所有数据*/
    List<SvprdsalinfChd> selectForOperid2(@Param("prdid") String prdid,@Param("operid") String operid
            ,@Param("txndate") String txndate);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALINF
     *
     * @mbggenerated Fri Apr 29 14:06:38 CST 2011
     */
    Svprdsalinf selectByPrimaryKey(String guid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALINF
     *
     * @mbggenerated Fri Apr 29 14:06:38 CST 2011
     */
    int updateByExampleSelective(@Param("record") Svprdsalinf record, @Param("example") SvprdsalinfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALINF
     *
     * @mbggenerated Fri Apr 29 14:06:38 CST 2011
     */
    int updateByExample(@Param("record") Svprdsalinf record, @Param("example") SvprdsalinfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALINF
     *
     * @mbggenerated Fri Apr 29 14:06:38 CST 2011
     */
    int updateByPrimaryKeySelective(Svprdsalinf record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPRDSALINF
     *
     * @mbggenerated Fri Apr 29 14:06:38 CST 2011
     */
    int updateByPrimaryKey(Svprdsalinf record);
}