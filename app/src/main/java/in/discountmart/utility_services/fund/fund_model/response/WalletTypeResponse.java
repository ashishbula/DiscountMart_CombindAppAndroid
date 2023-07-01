package in.discountmart.utility_services.fund.fund_model.response;


import in.discountmart.utility_services.model.response_model.BaseResponse;

public class WalletTypeResponse extends BaseResponse {
    String WalletType;
    String WalletTypeVal;

    public String getWalletType() {
        return WalletType;
    }

    public void setWalletType(String walletType) {
        WalletType = walletType;
    }

    public String getWalletTypeVal() {
        return WalletTypeVal;
    }

    public void setWalletTypeVal(String walletTypeVal) {
        WalletTypeVal = walletTypeVal;
    }
}
