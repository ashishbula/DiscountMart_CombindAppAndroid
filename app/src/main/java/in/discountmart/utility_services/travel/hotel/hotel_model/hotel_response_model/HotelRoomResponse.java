package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model;

import java.util.ArrayList;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class HotelRoomResponse extends BaseResponse {
    String TraceId;
    String IsUnderCancellationAllowed;
    String IsPolicyPerStay;

    ArrayList<RoomDetail> HotelRoomsDetails;
    HotelRoomCombination RoomCombinations;

    public String getTraceId() {
        return TraceId;
    }

    public void setTraceId(String traceId) {
        TraceId = traceId;
    }

    public String getIsUnderCancellationAllowed() {
        return IsUnderCancellationAllowed;
    }

    public void setIsUnderCancellationAllowed(String isUnderCancellationAllowed) {
        IsUnderCancellationAllowed = isUnderCancellationAllowed;
    }

    public String getIsPolicyPerStay() {
        return IsPolicyPerStay;
    }

    public void setIsPolicyPerStay(String isPolicyPerStay) {
        IsPolicyPerStay = isPolicyPerStay;
    }

    public ArrayList<RoomDetail> getHotelRoomsDetails() {
        return HotelRoomsDetails;
    }

    public void setHotelRoomsDetails(ArrayList<RoomDetail> hotelRoomsDetails) {
        HotelRoomsDetails = hotelRoomsDetails;
    }

    public HotelRoomCombination getRoomCombinations() {
        return RoomCombinations;
    }

    public void setRoomCombinations(HotelRoomCombination roomCombinations) {
        RoomCombinations = roomCombinations;
    }
}
