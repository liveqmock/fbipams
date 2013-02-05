package pams.repository.model;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-3-29
 * Time: 20:44:08
 * To change this template use File | Settings | File Templates.
 */
public class SvprdsalinfChd extends Svprdsalinf {
    private String opername;
    private String enuitemlabel;

    public String getOpername() {
        return opername;
    }

    public void setOpername(String opername) {
        this.opername = opername;
    }

    public String getEnuitemlabel() {
        return enuitemlabel;
    }

    public void setEnuitemlabel(String enuitemlabel) {
        this.enuitemlabel = enuitemlabel;
    }
}
