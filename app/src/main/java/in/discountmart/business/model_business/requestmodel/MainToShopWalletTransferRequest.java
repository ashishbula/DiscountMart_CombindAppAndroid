package in.discountmart.business.model_business.requestmodel;

public class MainToShopWalletTransferRequest extends BaseRequest {
    //String reqtype;//": "wallettransfer",
    //String userid;//": "WI223344",
   // String passwd;//": "wayfast@12",
    String amount;//": "1",
    String fromwallet;//": "Direct Income",
    String towallet;//": "Main Wallet",
    String fromactype;//": "D",
    String toactype;//": "M",
    String remarks;//": "test By Bispl",
    String transpasswd;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFromwallet() {
        return fromwallet;
    }

    public void setFromwallet(String fromwallet) {
        this.fromwallet = fromwallet;
    }

    public String getTowallet() {
        return towallet;
    }

    public void setTowallet(String towallet) {
        this.towallet = towallet;
    }

    public String getFromactype() {
        return fromactype;
    }

    public void setFromactype(String fromactype) {
        this.fromactype = fromactype;
    }

    public String getToactype() {
        return toactype;
    }

    public void setToactype(String toactype) {
        this.toactype = toactype;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTranspasswd() {
        return transpasswd;
    }

    public void setTranspasswd(String transpasswd) {
        this.transpasswd = transpasswd;
    }
}
