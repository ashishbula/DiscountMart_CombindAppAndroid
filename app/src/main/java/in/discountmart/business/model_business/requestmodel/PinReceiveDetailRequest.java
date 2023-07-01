package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 4/10/2018.
 */

public class PinReceiveDetailRequest extends BaseRequest {
    //String userid;//":"DT2262252","
    //String reqtype;//":"receivedetail","
    //String passwd;//":"P5EC4","
    String pkgid;//":"0","
    String from;//":"1","
    String to;//":"10"}

    public String getPkgid() {
        return pkgid;
    }

    public void setPkgid(String pkgid) {
        this.pkgid = pkgid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
