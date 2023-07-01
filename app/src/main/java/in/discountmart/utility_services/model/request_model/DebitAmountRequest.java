package in.discountmart.utility_services.model.request_model;

public class DebitAmountRequest extends DataRequest{
    String FormNo;//": "1002",
    String OrderNo;//": "12355555",
    String Amount;//": "1",
    String TransactionDate;//": "",
    String TID;//": "",
    String RRNNo;//": "",
    String Status;//":
    String GatewayResponse;//":

    public String getFormNo() {
        return FormNo;
    }

    public void setFormNo(String formNo) {
        FormNo = formNo;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        TransactionDate = transactionDate;
    }

    public String getTID() {
        return TID;
    }

    public void setTID(String TID) {
        this.TID = TID;
    }

    public String getRRNNo() {
        return RRNNo;
    }

    public void setRRNNo(String RRNNo) {
        this.RRNNo = RRNNo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getGatewayResponse() {
        return GatewayResponse;
    }

    public void setGatewayResponse(String gatewayResponse) {
        GatewayResponse = gatewayResponse;
    }
}
