package pams.repository.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import pams.repository.model.SvSaleCkptPrg;
import pams.repository.model.SvSaleCkptPrgExample;

import java.util.List;

@Component
public interface SvSaleCkptPrgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_SALE_CKPT_PRG
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    int countByExample(SvSaleCkptPrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_SALE_CKPT_PRG
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    int deleteByExample(SvSaleCkptPrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_SALE_CKPT_PRG
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    int deleteByPrimaryKey(String guid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_SALE_CKPT_PRG
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    int insert(SvSaleCkptPrg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_SALE_CKPT_PRG
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    int insertSelective(SvSaleCkptPrg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_SALE_CKPT_PRG
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    List<SvSaleCkptPrg> selectByExample(SvSaleCkptPrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_SALE_CKPT_PRG
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    SvSaleCkptPrg selectByPrimaryKey(String guid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_SALE_CKPT_PRG
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    int updateByExampleSelective(@Param("record") SvSaleCkptPrg record, @Param("example") SvSaleCkptPrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_SALE_CKPT_PRG
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    int updateByExample(@Param("record") SvSaleCkptPrg record, @Param("example") SvSaleCkptPrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_SALE_CKPT_PRG
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    int updateByPrimaryKeySelective(SvSaleCkptPrg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SV_SALE_CKPT_PRG
     *
     * @mbggenerated Mon Jul 29 11:41:31 CST 2013
     */
    int updateByPrimaryKey(SvSaleCkptPrg record);
}