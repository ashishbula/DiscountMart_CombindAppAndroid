package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 3/22/2018.
 */

public class EPinDetailReqEntity extends BaseRequest{

    //String userid;//":"dt009189","
    //String reqtype;//":"business_epindetail","
    //String passwd;//":"zcsx19","
    String pkgid;//":"123","
    String ptype;//":"B","
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

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }
}
