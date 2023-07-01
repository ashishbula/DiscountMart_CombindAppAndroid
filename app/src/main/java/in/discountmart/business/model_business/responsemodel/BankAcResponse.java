package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 5/11/2018.
 */

public class BankAcResponse extends BaseResponseEntity {

    BankAcccount bankers[];

    public BankAcccount[] getBankers() {
        return bankers;
    }

    public void setBankers(BankAcccount[] bankers) {
        this.bankers = bankers;
    }

    public class BankAcccount{
        String bankcode;//": "1",
        String bankname;//": "PUNJAB NATIONAL BANK",
        String acno;//": "0"

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

        public String getAcno() {
            return acno;
        }

        public void setAcno(String acno) {
            this.acno = acno;
        }
    }

}
