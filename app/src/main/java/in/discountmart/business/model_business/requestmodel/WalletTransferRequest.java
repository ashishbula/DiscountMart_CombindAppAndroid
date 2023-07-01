package in.discountmart.business.model_business.requestmodel;

public class WalletTransferRequest extends BaseRequest{
   // String userid;//":"GW223344","
    //String passwd;//":"123456","
    //String reqtype;//":"maintoshoppewallettransfer","
    String transpassword;//":"123@123",

    String toidno;//":"testuser","
    String remarks;//":"test by bispl"}
    String reqamount;//":"10","

    public String getTranspassword() {
        return transpassword;
    }

    public void setTranspassword(String transpassword) {
        this.transpassword = transpassword;
    }

    public String getToidno() {
        return toidno;
    }

    public void setToidno(String toidno) {
        this.toidno = toidno;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReqamount() {
        return reqamount;
    }

    public void setReqamount(String reqamount) {
        this.reqamount = reqamount;
    }
}
