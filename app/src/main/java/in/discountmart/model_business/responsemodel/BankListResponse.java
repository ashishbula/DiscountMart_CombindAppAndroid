package in.discountmart.model_business.responsemodel;

import java.io.Serializable;

/**
 * Created by Dell on 19-01-2018.
 */

public class BankListResponse extends BaseResponse implements Serializable {
    //String response;
    BankList bankers[];



    public BankList[] getBankers() {
        return bankers;
    }

    public void setBankers(BankList[] bankers) {
        this.bankers = bankers;
    }

    public static class BankList implements Serializable {

        String bankcode;//": "0",
        String bankname;//": "-- No Bank Found --"

        public String getBankcode() {
            return bankcode;
        }

        public void setBankcode(String bankcode) {
            this.bankcode = bankcode;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }
    }
}
