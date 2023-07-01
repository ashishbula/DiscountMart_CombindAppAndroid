package in.discountmart.utility_services.fund.fund_model.request;

public class TokenKeyRequest {
    String amount;//":"","
    String contact_number;//":"8696499007","
    String email_id;//":"aashish.dtil@gmail.com","
    String currency;//":"INR"}

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
