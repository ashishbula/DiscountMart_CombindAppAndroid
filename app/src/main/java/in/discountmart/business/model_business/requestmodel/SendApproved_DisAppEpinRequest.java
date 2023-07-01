package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 5/4/2018.
 */

public class SendApproved_DisAppEpinRequest extends BaseRequest {

    //String passwd;//":"sxdert12121","
    //String reqtype;//":"sendapprovedepin","
    //String userid;//":"DT009189","
    String reqno;//":"2438","
    String actype;//":"S","
    String kitid;//":107,}

    public String getReqno() {
        return reqno;
    }

    public void setReqno(String reqno) {
        this.reqno = reqno;
    }

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }

    public String getKitid() {
        return kitid;
    }

    public void setKitid(String kitid) {
        this.kitid = kitid;
    }
}
