package in.discountmart.business.model_business.requestmodel;

public class DeliveryCenterListRequest extends BaseRequest{
    String statecode;

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }
}
