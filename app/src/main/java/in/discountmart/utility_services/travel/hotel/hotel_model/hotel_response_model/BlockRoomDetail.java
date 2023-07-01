package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model;

import java.util.ArrayList;

public class BlockRoomDetail {
    String ChildCount;//": 0,
    String RequireAllPaxDetails;//": false,
    String RoomId;//": 0,
    String RoomStatus;//": 0,
    String RoomIndex;//": 1,
    String RoomTypeCode;//": "3496918|4382baac-b3b0-81dc-9ee5-110785604590|1^^1^^495002|165007344|4382baac-b3b0-81dc-9ee5-110785604590~!:~1~!:~1",
    String RoomDescription;//": "",
    String RoomTypeName;//": "Deluxe Room",
    String RatePlanCode;//": "495002|165007344|4382baac-b3b0-81dc-9ee5-110785604590",
    String RatePlan;//": 13,
    String InfoSource;//": "FixedCombination",
    String SequenceNo;//": "TH~~1356513~1",
    ArrayList<RoomDayRate>DayRates;
    String SupplierPrice;
    BlockRoomPrice Price;
    String RoomPromotion;//
    ArrayList<String> Amenities;
    ArrayList<String> Amenity;
    ArrayList<String> BedTypes;
    //ArrayList<String> HotelSupplements;
    ArrayList<BlockRoomCancelPolicy> CancellationPolicies;
    String SmokingPreference;//
    String LastCancellationDate;//
    String SupplierSpecificData;//
    String CancellationPolicy;//

    public String getChildCount() {
        return ChildCount;
    }

    public void setChildCount(String childCount) {
        ChildCount = childCount;
    }

    public String getRequireAllPaxDetails() {
        return RequireAllPaxDetails;
    }

    public void setRequireAllPaxDetails(String requireAllPaxDetails) {
        RequireAllPaxDetails = requireAllPaxDetails;
    }

    public String getRoomId() {
        return RoomId;
    }

    public void setRoomId(String roomId) {
        RoomId = roomId;
    }

    public String getRoomStatus() {
        return RoomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        RoomStatus = roomStatus;
    }

    public String getRoomIndex() {
        return RoomIndex;
    }

    public void setRoomIndex(String roomIndex) {
        RoomIndex = roomIndex;
    }

    public String getRoomTypeCode() {
        return RoomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        RoomTypeCode = roomTypeCode;
    }

    public String getRoomDescription() {
        return RoomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        RoomDescription = roomDescription;
    }

    public String getRoomTypeName() {
        return RoomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        RoomTypeName = roomTypeName;
    }

    public String getRatePlanCode() {
        return RatePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        RatePlanCode = ratePlanCode;
    }

    public String getRatePlan() {
        return RatePlan;
    }

    public void setRatePlan(String ratePlan) {
        RatePlan = ratePlan;
    }

    public String getInfoSource() {
        return InfoSource;
    }

    public void setInfoSource(String infoSource) {
        InfoSource = infoSource;
    }

    public String getSequenceNo() {
        return SequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        SequenceNo = sequenceNo;
    }

    public ArrayList<RoomDayRate> getDayRates() {
        return DayRates;
    }

    public void setDayRates(ArrayList<RoomDayRate> dayRates) {
        DayRates = dayRates;
    }

    public String getSupplierPrice() {
        return SupplierPrice;
    }

    public void setSupplierPrice(String supplierPrice) {
        SupplierPrice = supplierPrice;
    }

    public BlockRoomPrice getPrice() {
        return Price;
    }

    public void setPrice(BlockRoomPrice price) {
        Price = price;
    }

    public String getRoomPromotion() {
        return RoomPromotion;
    }

    public void setRoomPromotion(String roomPromotion) {
        RoomPromotion = roomPromotion;
    }

    public ArrayList<String> getAmenities() {
        return Amenities;
    }

    public void setAmenities(ArrayList<String> amenities) {
        Amenities = amenities;
    }

    public ArrayList<String> getAmenity() {
        return Amenity;
    }

    public void setAmenity(ArrayList<String> amenity) {
        Amenity = amenity;
    }

    public ArrayList<String> getBedTypes() {
        return BedTypes;
    }

    public void setBedTypes(ArrayList<String> bedTypes) {
        BedTypes = bedTypes;
    }

   /* public ArrayList<String> getHotelSupplements() {
        return HotelSupplements;
    }

    public void setHotelSupplements(ArrayList<String> hotelSupplements) {
        HotelSupplements = hotelSupplements;
    }*/

    public ArrayList<BlockRoomCancelPolicy> getCancellationPolicies() {
        return CancellationPolicies;
    }

    public void setCancellationPolicies(ArrayList<BlockRoomCancelPolicy> cancellationPolicies) {
        CancellationPolicies = cancellationPolicies;
    }

    public String getSmokingPreference() {
        return SmokingPreference;
    }

    public void setSmokingPreference(String smokingPreference) {
        SmokingPreference = smokingPreference;
    }

    public String getLastCancellationDate() {
        return LastCancellationDate;
    }

    public void setLastCancellationDate(String lastCancellationDate) {
        LastCancellationDate = lastCancellationDate;
    }

    public String getSupplierSpecificData() {
        return SupplierSpecificData;
    }

    public void setSupplierSpecificData(String supplierSpecificData) {
        SupplierSpecificData = supplierSpecificData;
    }

    public String getCancellationPolicy() {
        return CancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        CancellationPolicy = cancellationPolicy;
    }

    public static class RoomDayRate{
        double Amount;//": 656.01,
        String Date;//": "2019-08-24T00:00:00"
    }

    public static class BlockRoomCancelPolicy{
        int Charge;//": 100,
        int ChargeType;//": 2,
        String Currency;//": "INR",
        String FromDate;//": "2019-08-22T00:00:00",
        String ToDate;//": "2019-08-25T23:59:59"

        public int getCharge() {
            return Charge;
        }

        public void setCharge(int charge) {
            Charge = charge;
        }

        public int getChargeType() {
            return ChargeType;
        }

        public void setChargeType(int chargeType) {
            ChargeType = chargeType;
        }

        public String getCurrency() {
            return Currency;
        }

        public void setCurrency(String currency) {
            Currency = currency;
        }

        public String getFromDate() {
            return FromDate;
        }

        public void setFromDate(String fromDate) {
            FromDate = fromDate;
        }

        public String getToDate() {
            return ToDate;
        }

        public void setToDate(String toDate) {
            ToDate = toDate;
        }
    }


}
