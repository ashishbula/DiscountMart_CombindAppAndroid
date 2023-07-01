package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class WalletListResponse extends BaseResponse {
    ArrayList<WalletList> wallet;

    public ArrayList<WalletList> getWallet() {
        return wallet;
    }

    public void setWallet(ArrayList<WalletList> wallet) {
        this.wallet = wallet;
    }

    public static class WalletList{
        String actype;//":"G","
        String walletname;//":"Gift Cashback"

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
}
