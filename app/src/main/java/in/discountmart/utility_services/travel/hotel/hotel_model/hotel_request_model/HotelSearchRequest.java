package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model;

import java.util.ArrayList;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class HotelSearchRequest extends DataRequest {

    ArrayList<RoomSegment> RoomGuests=new ArrayList<RoomSegment>();

     String EndUserIp;//": "115.178.101.14",
    String TokenId;//": "bbf3a40b-e5ca-498d-9533-f06408c3caef",
    String CheckInDate;//": "12/06/2019",
    String NoOfNights;//": 1,
    String CountryCode;//": "IN",
    String CityId;//": 140522,
    String ResultCount;//": null,
    String PreferredCurrency;//": "INR",
    String GuestNationality;//": "IN",
    String NoOfRooms;//": 2,
    String PreferredHotel;//": "Udaipur",
    String MaxRating;//": 5,
    String MinRating;//": 0,
    String ReviewScore;//": 0,
    String IsNearBySearchAllowed;//": true
    String HotelGroupId;//": true

    public ArrayList<RoomSegment> getRoomGuests() {
        return RoomGuests;
    }

    public void setRoomGuests(ArrayList<RoomSegment> roomGuests) {
        RoomGuests = roomGuests;
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

    public String getCheckInDate() {
        return CheckInDate;
    }

    public void setCheckInDate(String checkInDate) {
        CheckInDate = checkInDate;
    }

    public String getNoOfNights() {
        return NoOfNights;
    }

    public void setNoOfNights(String noOfNights) {
        NoOfNights = noOfNights;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getResultCount() {
        return ResultCount;
    }

    public void setResultCount(String resultCount) {
        ResultCount = resultCount;
    }

    public String getPreferredCurrency() {
        return PreferredCurrency;
    }

    public void setPreferredCurrency(String preferredCurrency) {
        PreferredCurrency = preferredCurrency;
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

    public String getPreferredHotel() {
        return PreferredHotel;
    }

    public void setPreferredHotel(String preferredHotel) {
        PreferredHotel = preferredHotel;
    }

    public String getMaxRating() {
        return MaxRating;
    }

    public void setMaxRating(String maxRating) {
        MaxRating = maxRating;
    }

    public String getMinRating() {
        return MinRating;
    }

    public void setMinRating(String minRating) {
        MinRating = minRating;
    }

    public String getReviewScore() {
        return ReviewScore;
    }

    public void setReviewScore(String reviewScore) {
        ReviewScore = reviewScore;
    }

    public String getIsNearBySearchAllowed() {
        return IsNearBySearchAllowed;
    }

    public void setIsNearBySearchAllowed(String isNearBySearchAllowed) {
        IsNearBySearchAllowed = isNearBySearchAllowed;
    }

    public String getHotelGroupId() {
        return HotelGroupId;
    }

    public void setHotelGroupId(String hotelGroupId) {
        HotelGroupId = hotelGroupId;
    }

    public static class RoomSegment{
        String NoOfAdults;//": 2,
        String NoOfChild;//": 2,
        String ChildAge[];

        public String getNoOfAdults() {
            return NoOfAdults;
        }

        public void setNoOfAdults(String noOfAdults) {
            NoOfAdults = noOfAdults;
        }

        public String getNoOfChild() {
            return NoOfChild;
        }

        public void setNoOfChild(String noOfChild) {
            NoOfChild = noOfChild;
        }

        public String[] getChildAge() {
            return ChildAge;
        }

        public void setChildAge(String[] childAge) {
            ChildAge = childAge;
        }
    }
}
