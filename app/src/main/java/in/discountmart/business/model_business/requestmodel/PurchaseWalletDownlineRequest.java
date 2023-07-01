package in.discountmart.business.model_business.requestmodel;

public class PurchaseWalletDownlineRequest extends BaseRequest {
    //String reqtype;//":"purchasewalletdownline","
    //String userid;//":"dt007925","
   // String passwd;//":"123456","
    //String touserid;//":"DT006091","
    String transpasswd;//":"R*A55","
    String amount;//":"10","
    String remark;//":"test by bispl"}

    public String getTranspasswd() {
        return transpasswd;
    }

    public void setTranspasswd(String transpasswd) {
        this.transpasswd = transpasswd;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
