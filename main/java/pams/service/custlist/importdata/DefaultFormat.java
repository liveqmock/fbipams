package pams.service.custlist.importdata;

import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 12-12-19
 * Time: 下午3:31
 * To change this template use File | Settings | File Templates.
 */
public abstract class DefaultFormat implements Format {

    protected void assembleBean(Object object ,String[] fields, String[] fieldNames, String[] fieldTypes) throws Exception {
        Class clazz = object.getClass();
        for (int i = 0; i < fieldNames.length; i++) {
            String methodName = "set" + fieldNames[i];
            Method method = null;
            switch (fieldTypes[i]) {
                case "None":
                    continue; //此字段不处理
                case "String":
                    method = clazz.getMethod(methodName, String.class);
                    method.invoke(object, fields[i]);
                    break;
                case "BigDecimal":
                    method = clazz.getMethod(methodName, BigDecimal.class);
                    method.invoke(object, new BigDecimal(fields[i]));
                    break;
                default:
                    throw new RuntimeException("错误的数据类型.");
            }
        }
    }
}
