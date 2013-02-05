package pams.repository.dao.prdset;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import pams.repository.model.prdset.*;

import java.util.List;

@Component
public interface PsSalesInfoMapper {

    /**
     * ���ݲ�ѯ�������� ��Ʒ�ײ� ��ϸ
     */
    int countPrdsetSalesinfo(PsSalesInfoBean bean);


    /**
     * ���ݲ�ѯ�������� ��Ʒ�ײ� ��ϸ
     */
    List<PsSalesInfoBean> selectPrdsetSalesinfo(PsSalesInfoBean bean);

    /**
     *  ���ݲ�Ʒ�ײ�ID������ǩԼ��Ʒ��ϸ
     * @param guid
     * @return
     */
    List<PsSalesPrdInfoBean> selectPrdSalesInfo(String guid);


    /**
     * ��ˣ������������˵Ĳ�ƷID�嵥
     * @return
     */
    List<String> selectAllProductIdFromCurrentPrdset();


    /**
     * �������ָ��Ĵ��������
     * @param prdid
     * @return
     */
    List<String> selectCheckPointClassName(String prdid);

    /**
     * ����ָ����ƷID�м��δͨ���ļ�¼
     * @param prdid
     * @param ckptid
     * @return
     */
    List<PsSalesPrdInfoBean> selectNeedCheckPrdinfoList(@Param("prdid") String prdid, @Param("ckptid")String ckptid);

    /**
     * ����ָ����ƷID��ĳ���ָ�����еļ�¼��������δ�ύ���ѿ��ˡ���ɾ���ģ�
     * @param prdid
     * @param ckptid
     * @return
     */
    List<PsSalesPrdInfoBean> selectAllPrdinfoListForForceCheck(@Param("prdid") String prdid, @Param("ckptid")String ckptid);

    /**
     * ���ݲ�Ʒ�ײ�ID������ؼ�˽��
     * @param prdsetguid
     * @return
     */
    List<PsPrdCkptInfoBean> selectPrdCkptInfo(@Param("prdsetguid")String prdsetguid);

    /**
     * ���ݼ��ָ��ļ�˽�����²�Ʒ��ļ��״̬->�ɹ�
     * @param prdid
     * @param ckptcount  ����Ʒ���ͨ������Ҫ�ļ��ָ����
     * @return
     */
    int updateProductCheckFlagToSuccess(@Param("prdid") String prdid,
                                        @Param("ckptcount")int ckptcount,
                                        @Param("checkdate")String checkdate,
                                        @Param("checktime")String checktime);

    /**
     * ���·���������Ʒ�ײͱ�ļ��״̬Ϊͨ��->�ɹ�
     * @param prdsetid
     * @param prdcount  ����Ʒ���ͨ������Ҫ�ļ��ָ����
     * @return
     */
    int updateProductSetCheckFlagToSuccess(@Param("prdsetid") String prdsetid,
                                        @Param("prdcount")int prdcount,
                                        @Param("checkdate")String checkdate,
                                        @Param("checktime")String checktime);

    /**
     *    ǿ�����¼�ˣ������ѳɹ�(δ���˵�)�Ĳ�Ʒ �ײ� Ϊδͨ��
     * @param checklog
     * @return
     */
    int updateAllProductSetCheckFlagToFail(@Param("checklog")String checklog);

    /**
     *  ǿ�����¼�ˣ������ѳɹ�(δ���˵�)�Ĳ�ƷΪδͨ��
     * @param checklog
     * @return
     */
    int updateAllProductCheckFlagToFail(@Param("checklog")String checklog);

    /**
     *  ǿ�����¼�ˣ������ѳɹ�(δ���˵�)�ļ��ָ��Ϊδͨ��
     * @param checklog
     * @return
     */
    int updateAllCheckPointCheckFlagToFail(@Param("checklog")String checklog);

    /**
     * ��������ͳ��
     * @param deptid
     * @return
     */
    List<PsPrdsetStatisticBean> selectPrdsetStatisticData(@Param("deptid")String deptid);
    List<PsPrdStatisticBean> selectPrdStatisticData(@Param("deptid")String deptid);

    /**
     * ���˴����ÿ���״̬���������ڣ�yyyy-MM��
     * @param archivedate
     * @param archiveoperid
     * @return
     */
    int updateAllArchiveFlag(@Param("archivedate")String archivedate,@Param("archiveoperid")String archiveoperid);

}