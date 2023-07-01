package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 5/15/2018.
 */

public class CouponInfoResponse extends BaseResponseEntity {

    //String response;//":"OK","
    String email;//":"test.Bispl@Gmail.com","
    String pkgid;//":"153","
    String upgradedate;//":"20-Jan-2018","
    String msg;//":"success"}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPkgid() {
        return pkgid;
    }

    public void setPkgid(String pkgid) {
        this.pkgid = pkgid;
    }

    public String getUpgradedate() {
        return upgradedate;
    }

    public void setUpgradedate(String upgradedate) {
        this.upgradedate = upgradedate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
