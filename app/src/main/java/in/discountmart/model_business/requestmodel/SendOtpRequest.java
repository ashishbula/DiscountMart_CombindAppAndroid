package in.discountmart.model_business.requestmodel;

public class SendOtpRequest  {
    String reqtype;//": "SendOtp",
    String userid;//": "Rakesh",
    //String MobileNO;//": "9672773138",
    //String EmailID;//": "bispl.rakeshsoni@gmail.com",
    String IsMobile;//": "Y",
    String IsEmail;//": "Y",
    String OtpMobile;//": "111111",
    String OtpEmail;//": "222222"
    String islogin;

    public String getIslogin() {
        return islogin;
    }

    public void setIslogin(String islogin) {
        this.islogin = islogin;
    }

    public String getReqtype() {
        return reqtype;
    }

    public void setReqtype(String reqtype) {
        this.reqtype = reqtype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getIsMobile() {
        return IsMobile;
    }

    public void setIsMobile(String isMobile) {
        IsMobile = isMobile;
    }

    public String getIsEmail() {
        return IsEmail;
    }

    public void setIsEmail(String isEmail) {
        IsEmail = isEmail;
    }

    public String getOtpMobile() {
        return OtpMobile;
    }

    public void setOtpMobile(String otpMobile) {
        OtpMobile = otpMobile;
    }

    public String getOtpEmail() {
        return OtpEmail;
    }

    public void setOtpEmail(String otpEmail) {
        OtpEmail = otpEmail;
    }
}
