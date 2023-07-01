package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 3/31/2018.
 */

public class WalletResponse extends BaseResponseEntity {

    String recordcount;//": "42",
    String credit;//": "64838.00",
    String debit;//": "59988.00",
    String balance;//": "4850.00",
    ServiceWallet achistory[];//": [

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

    public ServiceWallet[] getAchistory() {
        return achistory;
    }

    public void setAchistory(ServiceWallet[] achistory) {
        this.achistory = achistory;
    }

    public class ServiceWallet{
        String sno;//": "1",
        String date;//": "09-02-2018 11:28AM",
        String deposit;//": "9189.00",
        String used;//": "150.00",
        String balance;//": "4850.00",
        String remarks;//": "Product purchased Against DT/2017-18/WR/2470."

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
