package pams.service.common.dataimport

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 12-12-19
 * Time: обнГ2:16
 * To change this template use File | Settings | File Templates.
 */
public interface Format<T> {
    T parse(String line) throws Exception;
}