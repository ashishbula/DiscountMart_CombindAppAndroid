package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 3/22/2018.
 */

public class LoginResponse_Business {

    String response;//":"OK","
    String mname;//":"Vision Root","
    String isactive;//"/:"Y","
    String isfranchise;//":"","
    String profilepic;//
    String mobileno;//
    String email;
    String msg;
    String address;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
