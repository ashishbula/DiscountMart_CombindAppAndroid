package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 4/20/2018.
 */

public class GetProductRequest extends BaseRequest {

    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
