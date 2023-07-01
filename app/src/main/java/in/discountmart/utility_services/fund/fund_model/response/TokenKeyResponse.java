package in.discountmart.utility_services.fund.fund_model.response;

public class TokenKeyResponse  {

    String amount;//": "1.00",
    String currency;//": "INR",
    String mtx;//": "27e1e090-b0d",
    String attempts;//": 0,
    String sub_accounts_id;//": null,
    String id;//": "sb_pt_BRrz8CnM0xEvjhC",
    String entity;//": "payment_token",
    String status;//": "created",
    String error;//": null
    String error_code;//": null
    String responceCode;//": null
    //"request_id": null
         /*   "customer": {
        "contact_number": "8696499007",
                "email_id": "aashish.dtil@gmail.com",
                "id": "sb_cs_H9LNopZfZxwFDB",
                "entity": "customer"
    },*/


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

    public String getMtx() {
        return mtx;
    }

    public void setMtx(String mtx) {
        this.mtx = mtx;
    }

    public String getAttempts() {
        return attempts;
    }

    public void setAttempts(String attempts) {
        this.attempts = attempts;
    }

    public String getSub_accounts_id() {
        return sub_accounts_id;
    }

    public void setSub_accounts_id(String sub_accounts_id) {
        this.sub_accounts_id = sub_accounts_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getResponceCode() {
        return responceCode;
    }

    public void setResponceCode(String responceCode) {
        this.responceCode = responceCode;
    }
}
