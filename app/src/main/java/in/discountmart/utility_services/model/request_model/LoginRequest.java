package in.discountmart.utility_services.model.request_model;

public class LoginRequest {

        String UserName;//": "Demo",
    String Passwd;//": "123"
    String SponsorFormNo;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPasswd() {
        return Passwd;
    }

    public void setPasswd(String passwd) {
        Passwd = passwd;
    }

    public String getSponsorFormNo() {
        return SponsorFormNo;
    }

    public void setSponsorFormNo(String sponsorFormNo) {
        SponsorFormNo = sponsorFormNo;
    }
}
