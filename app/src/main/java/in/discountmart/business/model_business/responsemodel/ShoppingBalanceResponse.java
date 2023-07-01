package in.discountmart.business.model_business.responsemodel;

public class ShoppingBalanceResponse extends BaseResponseEntity {
    String credit;//":"16086.00","
    String debit;//":"1001.00","
    String balance;//":"15085.00","
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
