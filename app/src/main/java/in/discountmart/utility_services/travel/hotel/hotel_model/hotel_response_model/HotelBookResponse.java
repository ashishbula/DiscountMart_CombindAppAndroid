package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model;

import java.io.Serializable;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class HotelBookResponse extends BaseResponse implements Serializable {
    String hotelName;//":"Abcd","
    String TransactionID;//":"4","
    String Status;//":"BOOKED","
    String City;//":""}",

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
