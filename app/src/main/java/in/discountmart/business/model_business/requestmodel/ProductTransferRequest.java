package in.discountmart.business.model_business.requestmodel;

public class ProductTransferRequest extends BaseRequest {
    //String reqtype;//":"producttransfer","
    //String userid;//":"005152","
    //String passwd;//":"@navneet","
    String membername;//":"vision","
    String reqfor;//":"S","
    String prodid;//":"23,22","
    String qty;//":"1,2","
    String mobl;//":"8233057616"}

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getReqfor() {
        return reqfor;
    }

    public void setReqfor(String reqfor) {
        this.reqfor = reqfor;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getMobl() {
        return mobl;
    }

    public void setMobl(String mobl) {
        this.mobl = mobl;
    }
}
