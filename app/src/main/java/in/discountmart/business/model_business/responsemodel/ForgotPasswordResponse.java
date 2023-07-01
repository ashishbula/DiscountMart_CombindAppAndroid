package in.discountmart.business.model_business.responsemodel;

public class ForgotPasswordResponse extends BaseResponseEntity {
    String ismailsent;
    String issmssent;
    String isuser;

    public String getIsmailsent() {
        return ismailsent;
    }

    public void setIsmailsent(String ismailsent) {
        this.ismailsent = ismailsent;
    }

    public String getIssmssent() {
        return issmssent;
    }

    public void setIssmssent(String issmssent) {
        this.issmssent = issmssent;
    }

    public String getIsuser() {
        return isuser;
    }

    public void setIsuser(String isuser) {
        this.isuser = isuser;
    }
}
