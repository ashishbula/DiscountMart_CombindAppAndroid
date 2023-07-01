package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 3/30/2018.
 */

public class GetWalleBalanceResponse extends BaseResponseEntity {
    String credit;//":"-808823.00","
    String debit;//":"284886.00","
    String balance;//":"-1093709.00","
    //String response;//":"OK"}


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
}
