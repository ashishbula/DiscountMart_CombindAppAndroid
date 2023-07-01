package in.discountmart.utility_services.travel.utility_cab.cab_model.cab_response_model;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class CabDiscountResponse extends BaseResponse {
    String GroupId;//":1,"
    String Discount;//":10.00}

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
