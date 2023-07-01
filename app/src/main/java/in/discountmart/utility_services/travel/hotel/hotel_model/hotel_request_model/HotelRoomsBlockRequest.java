package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class HotelRoomsBlockRequest extends DataRequest {
    String ResultIndex;//": "1",
    String HotelCode;//": "1126127",
    String HotelName;//": "Lords Plaza Jaipur",
    String NoOfRooms;//": "2",
    String TraceId;//": "105c8c11-4b18-48ec-8914-c3faf83b6584"
    String RoomIndex;//": "1",
    String EndUserIp;//": "115.178.101.14",
    String TokenId;//": "97b99d42-a0d1-474e-a2ff-5da4af3ef32f",

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

    public String getNoOfRooms() {
        return NoOfRooms;
    }

    public void setNoOfRooms(String noOfRooms) {
        NoOfRooms = noOfRooms;
    }

    public String getTraceId() {
        return TraceId;
    }

    public void setTraceId(String traceId) {
        TraceId = traceId;
    }

    public String getRoomIndex() {
        return RoomIndex;
    }

    public void setRoomIndex(String roomIndex) {
        RoomIndex = roomIndex;
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
}
