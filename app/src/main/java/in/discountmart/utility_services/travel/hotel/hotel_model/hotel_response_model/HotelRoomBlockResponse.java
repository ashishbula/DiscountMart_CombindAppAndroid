package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model;

import java.util.ArrayList;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class HotelRoomBlockResponse extends BaseResponse {

    HotelRoomBlockResult BlockRoomResult;

    public HotelRoomBlockResult getBlockRoomResult() {
        return BlockRoomResult;
    }

    public void setBlockRoomResult(HotelRoomBlockResult blockRoomResult) {
        BlockRoomResult = blockRoomResult;
    }

    public static class HotelRoomBlockResult{
        String AvailabilityType;//": "Confirm",
        String IsPackageDetailsMandatory;//": false,
        String TraceId;//": "47648a9b-c715-4a52-a0ba-db17cb4bed76",
        String ResponseStatus;//": 1,
        String GSTAllowed;//": false,
        String IsPriceChanged;//": false,
        String IsPackageFare;//": false,
        String IsCancellationPolicyChanged;//": false,
        String IsHotelPolicyChanged;//": true,
        String HotelNorms;//":
        String HotelName;//":
        String AddressLine1;//":
        String AddressLine2;//":
        String StarRating;//":
        String HotelPolicyDetail;//":
        String Latitude;//":
        String Longitude;//":
        String BookingAllowedForRoamer;//":
        RoomError Error;

        /*class*/
        ArrayList<BlockRoomDetail>HotelRoomsDetails;

        public String getAvailabilityType() {
            return AvailabilityType;
        }

        public void setAvailabilityType(String availabilityType) {
            AvailabilityType = availabilityType;
        }

        public String getIsPackageDetailsMandatory() {
            return IsPackageDetailsMandatory;
        }

        public void setIsPackageDetailsMandatory(String isPackageDetailsMandatory) {
            IsPackageDetailsMandatory = isPackageDetailsMandatory;
        }

        public String getTraceId() {
            return TraceId;
        }

        public void setTraceId(String traceId) {
            TraceId = traceId;
        }

        public String getResponseStatus() {
            return ResponseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            ResponseStatus = responseStatus;
        }

        public String getGSTAllowed() {
            return GSTAllowed;
        }

        public void setGSTAllowed(String GSTAllowed) {
            this.GSTAllowed = GSTAllowed;
        }

        public String getIsPriceChanged() {
            return IsPriceChanged;
        }

        public void setIsPriceChanged(String isPriceChanged) {
            IsPriceChanged = isPriceChanged;
        }

        public String getIsPackageFare() {
            return IsPackageFare;
        }

        public void setIsPackageFare(String isPackageFare) {
            IsPackageFare = isPackageFare;
        }

        public String getIsCancellationPolicyChanged() {
            return IsCancellationPolicyChanged;
        }

        public void setIsCancellationPolicyChanged(String isCancellationPolicyChanged) {
            IsCancellationPolicyChanged = isCancellationPolicyChanged;
        }

        public String getIsHotelPolicyChanged() {
            return IsHotelPolicyChanged;
        }

        public void setIsHotelPolicyChanged(String isHotelPolicyChanged) {
            IsHotelPolicyChanged = isHotelPolicyChanged;
        }

        public String getHotelNorms() {
            return HotelNorms;
        }

        public void setHotelNorms(String hotelNorms) {
            HotelNorms = hotelNorms;
        }

        public String getHotelName() {
            return HotelName;
        }

        public void setHotelName(String hotelName) {
            HotelName = hotelName;
        }

        public String getAddressLine1() {
            return AddressLine1;
        }

        public void setAddressLine1(String addressLine1) {
            AddressLine1 = addressLine1;
        }

        public String getAddressLine2() {
            return AddressLine2;
        }

        public void setAddressLine2(String addressLine2) {
            AddressLine2 = addressLine2;
        }

        public String getStarRating() {
            return StarRating;
        }

        public void setStarRating(String starRating) {
            StarRating = starRating;
        }

        public String getHotelPolicyDetail() {
            return HotelPolicyDetail;
        }

        public void setHotelPolicyDetail(String hotelPolicyDetail) {
            HotelPolicyDetail = hotelPolicyDetail;
        }

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String latitude) {
            Latitude = latitude;
        }

        public String getLongitude() {
            return Longitude;
        }

        public void setLongitude(String longitude) {
            Longitude = longitude;
        }

        public String getBookingAllowedForRoamer() {
            return BookingAllowedForRoamer;
        }

        public void setBookingAllowedForRoamer(String bookingAllowedForRoamer) {
            BookingAllowedForRoamer = bookingAllowedForRoamer;
        }

        public RoomError getError() {
            return Error;
        }

        public void setError(RoomError error) {
            Error = error;
        }

        public ArrayList<BlockRoomDetail> getHotelRoomsDetails() {
            return HotelRoomsDetails;
        }

        public void setHotelRoomsDetails(ArrayList<BlockRoomDetail> hotelRoomsDetails) {
            HotelRoomsDetails = hotelRoomsDetails;
        }

        public static class RoomError{
            String ErrorCode;//
            String ErrorMessage;//

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
    }

}
