package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class HotelInfoRequest extends DataRequest {
     String EndUserIp;//": "115.178.101.14",
    String TokenId;//": "97b99d42-a0d1-474e-a2ff-5da4af3ef32f",
    String TraceId;//": "c3f9c644-0ca8-4f39-8b51-553324c07c66",
    String ResultIndex;//": "1",
    String HotelCode;//": "1357791"
    String HotelGroupId;//": true

    public String getEndUserIp() {
        return EndUserIp;
    }

    public void setEndUserIp(String endUserIp) {
        EndUserIp = endUserIp;
    }

    public String getTokenId() {
        return TokenId;
    }

    public void setTokenId(String tokenId) {
        TokenId = tokenId;
    }

    public String getTraceId() {
        return TraceId;
    }

    public void setTraceId(String traceId) {
        TraceId = traceId;
    }

    public String getResultIndex() {
        return ResultIndex;
    }

    public void setResultIndex(String resultIndex) {
        ResultIndex = resultIndex;
    }

    public String getHotelCode() {
        return HotelCode;
    }

    public void setHotelCode(String hotelCode) {
        HotelCode = hotelCode;
    }

    public String getHotelGroupId() {
        return HotelGroupId;
    }

    public void setHotelGroupId(String hotelGroupId) {
        HotelGroupId = hotelGroupId;
    }
}
