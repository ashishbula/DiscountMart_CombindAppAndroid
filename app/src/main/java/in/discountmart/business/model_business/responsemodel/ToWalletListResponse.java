package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class ToWalletListResponse extends BaseResponse{
    ArrayList<ToWalletList>towallet;

    public ArrayList<ToWalletList> getTowallet() {
        return towallet;
    }

    public void setTowallet(ArrayList<ToWalletList> towallet) {
        this.towallet = towallet;
    }

    public static class ToWalletList{
        String wallettype;//": "A",
        String walletname;//": "---Select Wallet---"

        public String getWallettype() {
            return wallettype;
        }

        public void setWallettype(String wallettype) {
            this.wallettype = wallettype;
        }

        public String getWalletname() {
            return walletname;
        }

        public void setWalletname(String walletname) {
            this.walletname = walletname;
        }
    }
}
