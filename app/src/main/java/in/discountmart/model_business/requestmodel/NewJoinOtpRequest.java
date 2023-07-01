package in.discountmart.model_business.requestmodel;

public class NewJoinOtpRequest extends BaseRequest {
    //String reqtype;//":"reqjoiningotp","
    //String  userid;//":"Pc11000001","
   // String passwd;//":"123456","
    String mobileno;//":"8233832624","
    String membername;//":"test"}
    String emailid;//":"kirtibhadada.bispl@gmail.com",
    String referralid;//": "22334455","
    String side;//": "1","
    String uplinerid;//": "24927191"}

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getReferralid() {
        return referralid;
    }

    public void setReferralid(String referralid) {
        this.referralid = referralid;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getUplinerid() {
        return uplinerid;
    }

    public void setUplinerid(String uplinerid) {
        this.uplinerid = uplinerid;
    }
}
