package in.discountmart.business.model_business.requestmodel;

public class PinTransferDetailRequest extends BaseRequest {

    //String userid;//": "005152",
    //String reqtype;//": "transferdetail",
    //String passwd;//": "@navneet",
    String pkgid;//": "0",
    String from;//": "1",
    String to;//": "10"

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
