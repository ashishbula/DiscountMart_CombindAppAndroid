package in.discountmart.business.model_business.requestmodel;

public class ProductListRequest extends BaseRequest {

        String ordertype;

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }
}
