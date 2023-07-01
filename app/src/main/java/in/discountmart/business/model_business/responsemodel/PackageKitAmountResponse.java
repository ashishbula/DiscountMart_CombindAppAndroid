package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 4/13/2018.
 */

public class PackageKitAmountResponse extends BaseResponseEntity {
    //String response;//":"OK","
    String kitamount;//":"4500","
    String msg;//":"success"}
    String bv;

    public String getKitamount() {
        return kitamount;
    }

    public void setKitamount(String kitamount) {
        this.kitamount = kitamount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBv() {
        return bv;
    }

    public void setBv(String bv) {
        this.bv = bv;
    }
}
