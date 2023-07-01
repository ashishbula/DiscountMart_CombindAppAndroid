package in.discountmart.business.model_business.requestmodel;

public class PinGenerateRequest extends BaseRequest {
    //String reqtype;//": "pingenerate",
    //String userid;//": "005152",
    //String passwd;//": "@navneet",
    String kitid;//": "32",
    String qty;//": "1",
    String actype;//": "1",
    String transpassw;//": "@navneet"

    public String getKitid() {
        return kitid;
    }

    public void setKitid(String kitid) {
        this.kitid = kitid;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTranspassw() {
        return transpassw;
    }

    public void setTranspassw(String transpassw) {
        this.transpassw = transpassw;
    }

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }
}
