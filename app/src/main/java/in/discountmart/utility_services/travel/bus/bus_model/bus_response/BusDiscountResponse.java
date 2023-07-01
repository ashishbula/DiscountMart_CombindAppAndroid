package in.discountmart.utility_services.travel.bus.bus_model.bus_response;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class BusDiscountResponse extends BaseResponse {
    String GroupId;//":1,"
    String Discount;//":5.00

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
