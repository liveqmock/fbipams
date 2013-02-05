package pams.repository.dao.prdset;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import pams.repository.model.prdset.*;

import java.util.List;

@Component
public interface PsSalesInfoMapper {

    /**
     * 根据查询参数查找 产品套餐 明细
     */
    int countPrdsetSalesinfo(PsSalesInfoBean bean);


    /**
     * 根据查询参数查找 产品套餐 明细
     */
    List<PsSalesInfoBean> selectPrdsetSalesinfo(PsSalesInfoBean bean);

    /**
     *  根据产品套餐ID检索已签约产品明细
     * @param guid
     * @return
     */
    List<PsSalesPrdInfoBean> selectPrdSalesInfo(String guid);


    /**
     * 检核：检索所有需检核的产品ID清单
     * @return
     */
    List<String> selectAllProductIdFromCurrentPrdset();


    /**
     * 检索检核指标的处理程序定义
     * @param prdid
     * @return
     */
    List<String> selectCheckPointClassName(String prdid);

    /**
     * 检索指定产品ID中检核未通过的记录
     * @param prdid
     * @param ckptid
     * @return
     */
    List<PsSalesPrdInfoBean> selectNeedCheckPrdinfoList(@Param("prdid") String prdid, @Param("ckptid")String ckptid);

    /**
     * 检索指定产品ID及某检核指标所有的记录（不包括未提交、已考核、已删除的）
     * @param prdid
     * @param ckptid
     * @return
     */
    List<PsSalesPrdInfoBean> selectAllPrdinfoListForForceCheck(@Param("prdid") String prdid, @Param("ckptid")String ckptid);

    /**
     * 根据产品套餐ID查找相关检核结果
     * @param prdsetguid
     * @return
     */
    List<PsPrdCkptInfoBean> selectPrdCkptInfo(@Param("prdsetguid")String prdsetguid);

    /**
     * 根据检核指标的检核结果更新产品表的检核状态->成功
     * @param prdid
     * @param ckptcount  本产品检核通过所需要的检核指标数
     * @return
     */
    int updateProductCheckFlagToSuccess(@Param("prdid") String prdid,
                                        @Param("ckptcount")int ckptcount,
                                        @Param("checkdate")String checkdate,
                                        @Param("checktime")String checktime);

    /**
     * 更新符合条件产品套餐表的检核状态为通过->成功
     * @param prdsetid
     * @param prdcount  本产品检核通过所需要的检核指标数
     * @return
     */
    int updateProductSetCheckFlagToSuccess(@Param("prdsetid") String prdsetid,
                                        @Param("prdcount")int prdcount,
                                        @Param("checkdate")String checkdate,
                                        @Param("checktime")String checktime);

    /**
     *    强制重新检核：设置已成功(未考核的)的产品 套餐 为未通过
     * @param checklog
     * @return
     */
    int updateAllProductSetCheckFlagToFail(@Param("checklog")String checklog);

    /**
     *  强制重新检核：设置已成功(未考核的)的产品为未通过
     * @param checklog
     * @return
     */
    int updateAllProductCheckFlagToFail(@Param("checklog")String checklog);

    /**
     *  强制重新检核：设置已成功(未考核的)的检核指标为未通过
     * @param checklog
     * @return
     */
    int updateAllCheckPointCheckFlagToFail(@Param("checklog")String checklog);

    /**
     * 总量数据统计
     * @param deptid
     * @return
     */
    List<PsPrdsetStatisticBean> selectPrdsetStatisticData(@Param("deptid")String deptid);
    List<PsPrdStatisticBean> selectPrdStatisticData(@Param("deptid")String deptid);

    /**
     * 考核处理：置考核状态及考核日期（yyyy-MM）
     * @param archivedate
     * @param archiveoperid
     * @return
     */
    int updateAllArchiveFlag(@Param("archivedate")String archivedate,@Param("archiveoperid")String archiveoperid);

}