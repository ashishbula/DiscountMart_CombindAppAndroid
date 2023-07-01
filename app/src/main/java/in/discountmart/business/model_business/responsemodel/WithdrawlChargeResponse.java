package in.discountmart.business.model_business.responsemodel;

public class WithdrawlChargeResponse extends BaseResponse{
    String admincharge;//":"5.00","
    String tdscharge;//":"5.00","response":"OK","msg":"Success"}

    public String getAdmincharge() {
        return admincharge;
    }

    public void setAdmincharge(String admincharge) {
        this.admincharge = admincharge;
    }

    public String getTdscharge() {
        return tdscharge;
    }

    public void setTdscharge(String tdscharge) {
        this.tdscharge = tdscharge;
    }
}
