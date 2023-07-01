package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model;

import java.io.Serializable;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class HotelOnlineBookResponse extends BaseResponse implements Serializable {
    String HotelName;//": "Jaipur Heritage",
    String RoomType;//": "Deluxe Room",
    String Status;//": null,
    String HotelBookingStatus;//": "Confirmed",
    String ConfirmationNo;//": "NDLLS3(2258845)",
    String BookingRefNo;//": "8259098",
    String BookingID;//": "1499369",
    String Price;//": "1478.00",
    String NoOfRooms;//": "2",
    String CheckInDate;//": "28-08-2019 00:00:00",
    String CheckOutDate;//": "29-08-2019 00:00:00",
    String BookingDate;//": "26-08-2019 08:24:49",
    String City;//": "Jaipur"
    String ErrorCode;//": "Jaipur"
    String ErrorMessage;//": "Jaipur"

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }

    public String getRoomType() {
        return RoomType;
    }

    public void setRoomType(String roomType) {
        RoomType = roomType;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getHotelBookingStatus() {
        return HotelBookingStatus;
    }

    public void setHotelBookingStatus(String hotelBookingStatus) {
        HotelBookingStatus = hotelBookingStatus;
    }

    public String getConfirmationNo() {
        return ConfirmationNo;
    }

    public void setConfirmationNo(String confirmationNo) {
        ConfirmationNo = confirmationNo;
    }

    public String getBookingRefNo() {
        return BookingRefNo;
    }

    public void setBookingRefNo(String bookingRefNo) {
        BookingRefNo = bookingRefNo;
    }

    public String getBookingID() {
        return BookingID;
    }

    public void setBookingID(String bookingID) {
        BookingID = bookingID;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getNoOfRooms() {
        return NoOfRooms;
    }

    public void setNoOfRooms(String noOfRooms) {
        NoOfRooms = noOfRooms;
    }

    public String getCheckInDate() {
        return CheckInDate;
    }

    public void setCheckInDate(String checkInDate) {
        CheckInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return CheckOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        CheckOutDate = checkOutDate;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

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
