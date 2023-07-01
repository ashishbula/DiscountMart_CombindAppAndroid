package in.discountmart.utility_services.recharge.recharge_model.recharge_response_model;


import in.discountmart.utility_services.model.response_model.BaseResponse;

public class RechargeResponse extends BaseResponse {
    String OperatorID;//":"1316565959",
    String Amount;//":"10.00",
    String TID;//":"1190614124859NSXLM",
    String OrderID;//":null,
    String AccountNo;//":"8890885477",
    String ServiceType;//":"M",
    String Status;//":"SUCCESS"}

    public String getOperatorID() {
        return OperatorID;
    }

    public void setOperatorID(String operatorID) {
        OperatorID = operatorID;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTID() {
        return TID;
    }

    public void setTID(String TID) {
        this.TID = TID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String accountNo) {
        AccountNo = accountNo;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
