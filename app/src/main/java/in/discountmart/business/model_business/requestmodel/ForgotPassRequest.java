package in.discountmart.business.model_business.requestmodel;

public class ForgotPassRequest {
    String mobileno;//": "8233057616",  "
    String reqtype;//": "forgotpassword",  "
    String userid;//": "DM123456"}
    String islogin;

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
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

    public String getIslogin() {
        return islogin;
    }

    public void setIslogin(String islogin) {
        this.islogin = islogin;
    }
}
