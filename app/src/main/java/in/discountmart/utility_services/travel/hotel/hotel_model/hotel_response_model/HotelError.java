package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model;

public class HotelError {

    String ErrorCode;//": 0,
    String ErrorMessage;//": ""

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
