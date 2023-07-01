package in.discountmart.utility_services.model.response_model;

public class MainBalanceResponse extends BaseResponse {
    String Balance;//":46748.0,"
    String ttlBalance;//":0.0

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getTtlBalance() {
        return ttlBalance;
    }

    public void setTtlBalance(String ttlBalance) {
        this.ttlBalance = ttlBalance;
    }
}
