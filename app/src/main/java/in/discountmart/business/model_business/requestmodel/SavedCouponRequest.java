package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 5/14/2018.
 */

public class SavedCouponRequest extends BaseRequest {
    //"savecouponreq" with "" +
    String email;//","
    String mobileno;//"

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }
}
