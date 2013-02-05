package pams.repository.model.prdset;

import pams.repository.model.Svpsprdsetdetail;
import pams.repository.model.Svpsprdsetmain;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-4-21
 * Time: 下午10:58
 * To change this template use File | Settings | File Templates.
 */
public class PrdsetInfoBean extends Svpsprdsetmain{

    List<Svpsprdsetdetail> products;     //此套餐的产品清单
    List<Svpsprdsetmain>  mutexPrdsets;  //互斥产品套餐清单

    BigDecimal price ; //当前价格
}
