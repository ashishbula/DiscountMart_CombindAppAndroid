package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 3/22/2018.
 */

public class LoginResponseEntity  {

    String response;//":"OK","
    String mname;//":"Vision Root","
    String isactive;//"/:"Y","
    String isfranchise;//":"","
    String profilepic;//
    String mobileno;//
    String emailid;
    String msg;
    String address;
    String apikey;
    String couponreq;
    String doj;
    String packagename;



    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public String getIsfranchise() {
        return isfranchise;
    }

    public void setIsfranchise(String isfranchise) {
        this.isfranchise = isfranchise;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getCouponreq() {
        return couponreq;
    }

    public void setCouponreq(String couponreq) {
        this.couponreq = couponreq;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }
}
