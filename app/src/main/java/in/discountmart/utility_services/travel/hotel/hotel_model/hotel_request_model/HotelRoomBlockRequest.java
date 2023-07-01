package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model;

import java.util.ArrayList;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class HotelRoomBlockRequest extends DataRequest {

  String ResultIndex;//": "1",
    String HotelCode;//": "1126127",
    String HotelName;//": "Lords Plaza Jaipur",
    String GuestNationality;//": "IN",
    String NoOfRooms;//": "2",
    String ClientReferenceNo;//": "0",
    String IsVoucherBooking;//": true,
    /*Class*/
    ArrayList<HotelRoomsDetail> HotelRoomsDetails;
    String EndUserIp;//": "115.178.101.14",
    String TokenId;//": "cbe37533-f74e-4a66-bec6-c354251e7dd4",
    String TraceId;//": "105c8c11-4b18-48ec-8914-c3faf83b6584"

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

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }

    public String getGuestNationality() {
        return GuestNationality;
    }

    public void setGuestNationality(String guestNationality) {
        GuestNationality = guestNationality;
    }

    public String getNoOfRooms() {
        return NoOfRooms;
    }

    public void setNoOfRooms(String noOfRooms) {
        NoOfRooms = noOfRooms;
    }

    public String getClientReferenceNo() {
        return ClientReferenceNo;
    }

    public void setClientReferenceNo(String clientReferenceNo) {
        ClientReferenceNo = clientReferenceNo;
    }

    public String getIsVoucherBooking() {
        return IsVoucherBooking;
    }

    public void setIsVoucherBooking(String isVoucherBooking) {
        IsVoucherBooking = isVoucherBooking;
    }

    public ArrayList<HotelRoomsDetail> getHotelRoomsDetails() {
        return HotelRoomsDetails;
    }

    public void setHotelRoomsDetails(ArrayList<HotelRoomsDetail> hotelRoomsDetails) {
        HotelRoomsDetails = hotelRoomsDetails;
    }

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
}
