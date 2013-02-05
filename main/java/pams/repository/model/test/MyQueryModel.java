package pams.repository.model.test;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-1-10
 * Time: обнГ4:30
 * To change this template use File | Settings | File Templates.
 */
public class MyQueryModel {
    private String menuid;
    private String menuname;

    //-------
    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getMenuid() {
        return menuid;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }
}
