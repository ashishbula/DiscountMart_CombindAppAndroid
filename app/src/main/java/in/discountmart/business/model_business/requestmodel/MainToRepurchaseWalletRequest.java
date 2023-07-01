package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 3/30/2018.
 */

public class MainToRepurchaseWalletRequest extends BaseRequest {
    //String userid;//":"dt009189","
    //String reqtype;//":"mtoswallet","
    //String passwd;//":"zcsx19","
    String tpasswd;//":"tyurew1234","
    String amount;//":"250","
    String remark;//":""}

    public String getTpasswd() {
        return tpasswd;
    }

    public void setTpasswd(String tpasswd) {
        this.tpasswd = tpasswd;
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
