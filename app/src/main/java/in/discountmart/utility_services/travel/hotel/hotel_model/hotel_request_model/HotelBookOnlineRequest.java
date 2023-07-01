package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model;

import java.util.ArrayList;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class HotelBookOnlineRequest extends DataRequest {

    String TotalAmount;//": "100",
    String FormNo;//": "1002",
    String PromoDiscount;//": "0",
    String Discount;//": "0",
    String EmailID;//":
    String PromoCode;//": "",
    String AdultCount;//": "1",
    String ChildCount;//": "1",
    String MobileNo;//": "8696499007",
    String Otp;//": "1341",
    String CheckOutDate;//": "2019-08-05",
    String CheckInDate;//": "2019-08-03"
    String City;

    String ResultIndex;//": "1",
    String HotelCode;//": "1366679",
    String HotelName;//": "Hotel Jinendra Inn",
    String GuestNationality;//": "IN",
    String NoOfRooms;//": "2",
    String ClientReferenceNo;//": "81537",
    String IsVoucherBooking;//": true,
    ArrayList<OnlineBookRoomsDetail>HotelRoomsDetails;
    String EndUserIp;//": "115.178.101.14",
    String TokenId;//": "96a2d371-c384-4cf4-9c2f-afb135d92921",
    String TraceId;//": "238fa391-2557-43c2-b161-eb027485f98e"

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getFormNo() {
        return FormNo;
    }

    public void setFormNo(String formNo) {
        FormNo = formNo;
    }

    public String getPromoDiscount() {
        return PromoDiscount;
    }

    public void setPromoDiscount(String promoDiscount) {
        PromoDiscount = promoDiscount;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getPromoCode() {
        return PromoCode;
    }

    public void setPromoCode(String promoCode) {
        PromoCode = promoCode;
    }

    public String getAdultCount() {
        return AdultCount;
    }

    public void setAdultCount(String adultCount) {
        AdultCount = adultCount;
    }

    public String getChildCount() {
        return ChildCount;
    }

    public void setChildCount(String childCount) {
        ChildCount = childCount;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getOtp() {
        return Otp;
    }

    public void setOtp(String otp) {
        Otp = otp;
    }

    public String getCheckOutDate() {
        return CheckOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        CheckOutDate = checkOutDate;
    }

    public String getCheckInDate() {
        return CheckInDate;
    }

    public void setCheckInDate(String checkInDate) {
        CheckInDate = checkInDate;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

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

    public ArrayList<OnlineBookRoomsDetail> getHotelRoomsDetails() {
        return HotelRoomsDetails;
    }

    public void setHotelRoomsDetails(ArrayList<OnlineBookRoomsDetail> hotelRoomsDetails) {
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
