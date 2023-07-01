package in.discountmart.business.model_business.requestmodel;

public class ChangePasswordRequest extends BaseRequest {
    String npasswd;//

    public String getNpasswd() {
        return npasswd;
    }

    public void setNpasswd(String npasswd) {
        this.npasswd = npasswd;
    }
}
