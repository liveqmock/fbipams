package report

import javax.faces.bean.ManagedBean
import javax.faces.bean.RequestScoped

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-1-29
 * Time: обнГ1:49
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@RequestScoped
class GroovyBean implements Serializable{
    String message = "жпнд"
    def sayHello(){
        return "/UI/pams/custmng/custBaseAdd.xhtml";
    }
}
