package in.discountmart.utility_services.model.request_model;

public class BaseHeaderRequest {

    String UserName;//":"DT123","
    String Password;//":"123"
    String SponsorFormNo;
    String FormNo;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSponsorFormNo() {
        return SponsorFormNo;
    }

    public void setSponsorFormNo(String sponsorFormNo) {
        SponsorFormNo = sponsorFormNo;
    }

    public String getFormNo() {
        return FormNo;
    }

    public void setFormNo(String formNo) {
        FormNo = formNo;
    }
}
