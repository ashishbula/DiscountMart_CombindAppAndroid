package in.discountmart.business.model_business.requestmodel;

/**
 * Created by Dell on 10-01-2018.
 */

public class LevelListRequest {
    String userid;//"
    String reqtype;//
    String passwd;//

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getReqtype() {
        return reqtype;
    }

    public void setReqtype(String reqtype) {
        this.reqtype = reqtype;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
