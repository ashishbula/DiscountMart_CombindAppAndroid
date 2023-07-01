package in.discountmart.utility_services.fund.fund_model.request;

public class GatewayOrderIdRequest {
    String amount;//": 50000,
    String currency;//": "INR",
    String receipt;//": "receipt#1"

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }
}
