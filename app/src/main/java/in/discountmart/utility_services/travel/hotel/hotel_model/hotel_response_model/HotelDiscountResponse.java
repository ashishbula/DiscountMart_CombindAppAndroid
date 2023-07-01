package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class HotelDiscountResponse extends BaseResponse {

    String GroupId;//":null,
    String Discount;//":2.00}
    String HotelMarginInAmount;
    String MaxCommission;

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

    public String getHotelMarginInAmount() {
        return HotelMarginInAmount;
    }

    public void setHotelMarginInAmount(String hotelMarginInAmount) {
        HotelMarginInAmount = hotelMarginInAmount;
    }

    public String getMaxCommission() {
        return MaxCommission;
    }

    public void setMaxCommission(String maxCommission) {
        MaxCommission = maxCommission;
    }
}
