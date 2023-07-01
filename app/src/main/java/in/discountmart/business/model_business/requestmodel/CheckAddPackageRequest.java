package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 5/2/2018.
 */

public class CheckAddPackageRequest extends BaseRequest {

    //String passwd;//":"sxdert12121","
    //String reqtype;//":"addpkg","
    //String userid;//":"DT009189","
    String pkgid;//":"130","
    String qty;//":"1"}

    public String getPkgid() {
        return pkgid;
    }

    public void setPkgid(String pkgid) {
        this.pkgid = pkgid;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
