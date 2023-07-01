package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 3/30/2018.
 */

public class GetWalletBalanceRequest extends BaseRequest {

    String actype;//":"D"}
    String wallettype;

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }

    public String getWallettype() {
        return wallettype;
    }

    public void setWallettype(String wallettype) {
        this.wallettype = wallettype;
    }
}
