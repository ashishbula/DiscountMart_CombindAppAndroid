package in.discountmart.utility_services.recharge.recharge_model.recharge_request_model;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class CheckPostpaidIsbbpsRequest extends DataRequest {
       String AccountNo;//": "9251445903",
    String Amount;//": "100",
    String IpCode;//": "ATC"

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String accountNo) {
        AccountNo = accountNo;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getIpCode() {
        return IpCode;
    }

    public void setIpCode(String ipCode) {
        IpCode = ipCode;
    }
}
