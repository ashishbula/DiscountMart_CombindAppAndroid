package in.discountmart.utility_services.fund.fund_model.response;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class CheckFundResponse extends BaseResponse {
    String walletBalance;//":"50387.00","
    String deductamount;//":null,"
    String voucherNo;//":null

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getDeductamount() {
        return deductamount;
    }

    public void setDeductamount(String deductamount) {
        this.deductamount = deductamount;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }
}
