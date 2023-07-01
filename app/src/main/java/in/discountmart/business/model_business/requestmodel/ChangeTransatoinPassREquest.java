package in.discountmart.business.model_business.requestmodel;

public class ChangeTransatoinPassREquest extends BaseRequest {
    //String reqtype;
    //String userid;
    //String passwd;
    String tpasswd;
    String ntpasswd;

    public String getTpasswd() {
        return tpasswd;
    }

    public void setTpasswd(String tpasswd) {
        this.tpasswd = tpasswd;
    }

    public String getNtpasswd() {
        return ntpasswd;
    }

    public void setNtpasswd(String ntpasswd) {
        this.ntpasswd = ntpasswd;
    }
}
