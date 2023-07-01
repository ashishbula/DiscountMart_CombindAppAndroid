package in.discountmart.business.model_business.requestmodel;

public class WalletReportRequest extends BaseRequest  {

    //String passwd;//": "DM@1234",
    //String reqtype;//": "allwalletlist",  "
    //String userid;//": "DM123456",  "
    String wallettype;//":"S",  "
    String fromno;//":"1",  "
    String tono;//":"10"}

    public String getWallettype() {
        return wallettype;
    }

    public void setWallettype(String wallettype) {
        this.wallettype = wallettype;
    }

    public String getFromno() {
        return fromno;
    }

    public void setFromno(String fromno) {
        this.fromno = fromno;
    }

    public String getTono() {
        return tono;
    }

    public void setTono(String tono) {
        this.tono = tono;
    }
}
