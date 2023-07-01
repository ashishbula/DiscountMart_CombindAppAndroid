package in.discountmart.utility_services.fund.fund_model.request;


import in.discountmart.utility_services.model.request_model.DataRequest;

public class CheckFundRequest extends DataRequest {
    String FormNo;//": "8476",
    String WalletType;//": "W",
    String Action;//": "C",
    String UserName;//": "932098",
    String Password;//": "408771",
    String Amount;//": "2"

    public String getFormNo() {
        return FormNo;
    }

    public void setFormNo(String formNo) {
        FormNo = formNo;
    }

    public String getWalletType() {
        return WalletType;
    }

    public void setWalletType(String walletType) {
        WalletType = walletType;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
