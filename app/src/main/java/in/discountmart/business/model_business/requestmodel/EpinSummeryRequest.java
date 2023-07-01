package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 3/22/2018.
 */

public class EpinSummeryRequest extends BaseRequest {

    //String userid;//":"dt009189","
    //String reqtype;//":"epinsumm","
    //String passwd;//":"zcsx19","
    String pkgid;//":"0","
    String ptype;//":"B","
    String from;//":"1","
    String to;//":"10"}

    public String getPkgid() {
        return pkgid;
    }

    public void setPkgid(String pkgid) {
        this.pkgid = pkgid;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
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
