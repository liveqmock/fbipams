package pams.service.common.dataimport;

import org.apache.commons.lang.StringUtils;

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
            String field = fields[i];
            switch (fieldTypes[i]) {
                case "None":
                    continue; //此字段不处理
                case "String":
                    method = clazz.getMethod(methodName, String.class);
                    method.invoke(object, field);
                    break;
                case "Short":
                    method = clazz.getMethod(methodName, Short.class);
                    if (StringUtils.isEmpty(field) || "无".equals(field)) {
                        field = "0";
                    }
                    method.invoke(object, Short.parseShort(field));
                    break;
                case "Integer":
                    method = clazz.getMethod(methodName, Integer.class);
                    if (StringUtils.isEmpty(field) || "无".equals(field)) {
                        field = "0";
                    }
                    method.invoke(object, Integer.parseInt(field));
                    break;
                case "BigDecimal":
                    method = clazz.getMethod(methodName, BigDecimal.class);
                    if (StringUtils.isEmpty(field) || "无".equals(field)) {
                        field = "0";
                    }
                    method.invoke(object, new BigDecimal(field));
                    break;
                default:
                    throw new RuntimeException("错误的数据类型.");
            }
        }
    }
}
