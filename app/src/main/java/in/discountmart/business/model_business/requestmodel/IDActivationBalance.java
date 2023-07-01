package in.discountmart.business.model_business.requestmodel;

public class IDActivationBalance extends BaseRequest {

    //String passwd;//": "DM@1234",  "reqtype": "getbalance",  "userid": "DM123456","
    String wallettype;//'//'":"S

    public String getWallettype() {
        return wallettype;
    }

    public void setWallettype(String wallettype) {
        this.wallettype = wallettype;
    }
}
