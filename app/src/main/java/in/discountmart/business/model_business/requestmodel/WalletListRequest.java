package in.discountmart.business.model_business.requestmodel;

public class WalletListRequest extends BaseRequest {
   // {"reqtype":"FWalletList","userid":"WI223344","passwd":"wi@1234","" +
    String actype;//":"","
    String walletname;//":""}

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }

    public String getWalletname() {
        return walletname;
    }

    public void setWalletname(String walletname) {
        this.walletname = walletname;
    }
}
