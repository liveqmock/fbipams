package pams.repository.model.prdset;

import pams.repository.model.Svpsprdsetdetail;
import pams.repository.model.Svpsprdsetmain;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-4-21
 * Time: ����10:58
 * To change this template use File | Settings | File Templates.
 */
public class PrdsetInfoBean extends Svpsprdsetmain{

    List<Svpsprdsetdetail> products;     //���ײ͵Ĳ�Ʒ�嵥
    List<Svpsprdsetmain>  mutexPrdsets;  //�����Ʒ�ײ��嵥

    BigDecimal price ; //��ǰ�۸�
}
