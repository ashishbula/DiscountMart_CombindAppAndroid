package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class HotelInfoResponse extends BaseResponse {

    HotelInfo HotelInfoResult;

    public HotelInfo getHotelInfoResult() {
        return HotelInfoResult;
    }

    public void setHotelInfoResult(HotelInfo hotelInfoResult) {
        HotelInfoResult = hotelInfoResult;
    }
}
