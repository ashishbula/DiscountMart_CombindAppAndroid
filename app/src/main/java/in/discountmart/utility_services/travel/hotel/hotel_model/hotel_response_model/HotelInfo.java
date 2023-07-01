package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model;

public class HotelInfo {

    String ResponseStatus;
    String TraceId;
    HotelError Error;
    HotelDetail HotelDetails;

    public String getResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        ResponseStatus = responseStatus;
    }

    public String getTraceId() {
        return TraceId;
    }

    public void setTraceId(String traceId) {
        TraceId = traceId;
    }

    public HotelError getError() {
        return Error;
    }

    public void setError(HotelError error) {
        Error = error;
    }

    public HotelDetail getHotelDetails() {
        return HotelDetails;
    }

    public void setHotelDetails(HotelDetail hotelDetails) {
        HotelDetails = hotelDetails;
    }
}
