package in.discountmart.business.model_business.responsemodel;

public class ProductReqBalResponse extends BaseResponseEntity {
    String mbalance;//":"605.00",
    String rbalance;//0.00","
    //String response":"OK"}


    public String getMbalance() {
        return mbalance;
    }

    public void setMbalance(String mbalance) {
        this.mbalance = mbalance;
    }

    public String getRbalance() {
        return rbalance;
    }

    public void setRbalance(String rbalance) {
        this.rbalance = rbalance;
    }
}
