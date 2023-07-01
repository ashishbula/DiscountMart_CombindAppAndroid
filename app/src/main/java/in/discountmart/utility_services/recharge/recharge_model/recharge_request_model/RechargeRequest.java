package in.discountmart.utility_services.recharge.recharge_model.recharge_request_model;


import java.io.Serializable;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class RechargeRequest extends DataRequest implements Serializable {
    String AccountNo;//": "8890885477", //mobile no
    String Amount;//": "10",
    String ServiceType;//": "M",
    String Formno;//": "1002",
    String OperatorName;//": "Airtel",
    String OpratorCode;//": "1",
    String IsBBPS;//": "False",
    String Action;//": "PREPAID",
    String IpCode;//": "ATP"
    String ReferenceId;
String Location;

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

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

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getFormno() {
        return Formno;
    }

    public void setFormno(String formno) {
        Formno = formno;
    }

    public String getOperatorName() {
        return OperatorName;
    }

    public void setOperatorName(String operatorName) {
        OperatorName = operatorName;
    }

    public String getOpratorCode() {
        return OpratorCode;
    }

    public void setOpratorCode(String opratorCode) {
        OpratorCode = opratorCode;
    }

    public String getIsBBPS() {
        return IsBBPS;
    }

    public void setIsBBPS(String isBBPS) {
        IsBBPS = isBBPS;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getIpCode() {
        return IpCode;
    }

    public void setIpCode(String ipCode) {
        IpCode = ipCode;
    }

    public String getReferenceId() {
        return ReferenceId;
    }

    public void setReferenceId(String referenceId) {
        ReferenceId = referenceId;
    }
}
