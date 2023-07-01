package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model;

import java.io.Serializable;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class HotelSearchResponse extends BaseResponse implements Serializable {
    String ID;//": "0",
    String traceID;//": "00708086-87d2-4e29-8791-2720fe0a0a17",
    String hotelPicture;//": "https://api.tbotechnology.in/imageresource.aspx?img=FbrGPTrju5e5v0qrAGTD8pPBsj8/wYA5fyPRj8rAX+Te1BPSMNsGaqe9jJuBbmqOc2SOqUOLu5T+0ipvyIgv4XzPdTSh21CVg6ozGAjKovY=",
    String hotelName;//": "Lords Plaza Jaipur",
    String starRating;//": "3",
    String hotelCode;//": "1126127",
    String hotelAddress;//": "Sawai Ram Singh Road Opposite SMS Hospital , ",
    String price;//": "1622"
    String ResultIndex;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTraceID() {
        return traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public String getHotelPicture() {
        return hotelPicture;
    }

    public void setHotelPicture(String hotelPicture) {
        this.hotelPicture = hotelPicture;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getStarRating() {
        return starRating;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getResultIndex() {
        return ResultIndex;
    }

    public void setResultIndex(String resultIndex) {
        ResultIndex = resultIndex;
    }
}
