package in.discountmart.utility_services.model.request_model;

public class SendOtpRequest extends DataRequest {
    String FormNo;//":"1002","
    String MobileNo;//":"8890885477","
    String UserName;//":"demo",
    String SponsorFormNo;//":"1003","
    String EmailID;//":"aks.bispl@gmail.com
    String ServiceName;//":"aks.bispl@gmail.com

    public String getFormNo() {
        return FormNo;
    }

    public void setFormNo(String formNo) {
        FormNo = formNo;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getSponsorFormNo() {
        return SponsorFormNo;
    }

    public void setSponsorFormNo(String sponsorFormNo) {
        SponsorFormNo = sponsorFormNo;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }
}
