package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 3/31/2018.
 */

public class RepurchaseWalletReportResponse extends BaseResponseEntity {

    String recordcount;//": "10",
    String credit;//": "3425.00",
    String debit;//": "10.00",
    String balance;//": "3415.00",
    RepurchaseWallet achistory[];//": [

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public RepurchaseWallet[] getAchistory() {
        return achistory;
    }

    public void setAchistory(RepurchaseWallet[] achistory) {
        this.achistory = achistory;
    }

    public class RepurchaseWallet{
         String sno;//": "1",
        String date;//": "24-03-2018 12:14AM",
        String deposit;//": "9189.00",
        String used;//": "0.00",
        String balance;//": "3415.00",
        String remarks;//": "Repurchase Wallet Amount Credited on 23-Mar-2018"

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDeposit() {
            return deposit;
        }

        public void setDeposit(String deposit) {
            this.deposit = deposit;
        }

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }


}
