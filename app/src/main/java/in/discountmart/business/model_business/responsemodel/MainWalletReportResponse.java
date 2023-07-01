package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 3/31/2018.
 */

public class MainWalletReportResponse extends BaseResponseEntity {

        String credit;//": "-808823.00",
    String debit;//": "284886.00",
    String balance;//": "-1093709.00",
    String recordcount;//": "125",
    MainWallet achistory[];//": [

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

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public MainWallet[] getAchistory() {
        return achistory;
    }

    public void setAchistory(MainWallet[] achistory) {
        this.achistory = achistory;
    }

    public static class MainWallet{
        String sno;//": "1",
        String date;//": "24-03-2018 12:14AM",
        String deposit;//": "9189.00",
        String used;//": "0.00",
        String balance;
        String remarks;//": "Daily Closing Incentive Credited on 23-Mar-2018"

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
