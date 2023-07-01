package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model;

public class HotelRoomsDetail {

    String ID;//": null,
    String RoomIndex;//": "1",
    String RoomTypeCode;//": "3497390|eab66b9a-ef65-6890-5278-2973afec8823|1^^1^^632566||eab66b9a-ef65-6890-5278-2973afec8823~!:~1~!:~1",
    String RoomTypeName;//": "Deluxe Room",
    String RatePlanCode;//": "632566||eab66b9a-ef65-6890-5278-2973afec8823",
    String BedTypeCode;//": null,
    String SmokingPreference;//": "0",
    String Supplements;//": null,
    RoomPrice Price;
    String HotelPassenger;//": null,
    String Amenity;//": null,
    String BedTypes;//": null,
    String CancellationPolicies;//": null,
    String CancellationPolicy;//": null,
    String Inclusion;//": null,
    String LastCancelDate;//": null

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getBedTypeCode() {
        return BedTypeCode;
    }

    public void setBedTypeCode(String bedTypeCode) {
        BedTypeCode = bedTypeCode;
    }

    public String getSmokingPreference() {
        return SmokingPreference;
    }

    public void setSmokingPreference(String smokingPreference) {
        SmokingPreference = smokingPreference;
    }

    public String getSupplements() {
        return Supplements;
    }

    public void setSupplements(String supplements) {
        Supplements = supplements;
    }

    public RoomPrice getPrice() {
        return Price;
    }

    public void setPrice(RoomPrice price) {
        Price = price;
    }

    public String getHotelPassenger() {
        return HotelPassenger;
    }

    public void setHotelPassenger(String hotelPassenger) {
        HotelPassenger = hotelPassenger;
    }

    public String getAmenity() {
        return Amenity;
    }

    public void setAmenity(String amenity) {
        Amenity = amenity;
    }

    public String getBedTypes() {
        return BedTypes;
    }

    public void setBedTypes(String bedTypes) {
        BedTypes = bedTypes;
    }

    public String getCancellationPolicies() {
        return CancellationPolicies;
    }

    public void setCancellationPolicies(String cancellationPolicies) {
        CancellationPolicies = cancellationPolicies;
    }

    public String getCancellationPolicy() {
        return CancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        CancellationPolicy = cancellationPolicy;
    }

    public String getInclusion() {
        return Inclusion;
    }

    public void setInclusion(String inclusion) {
        Inclusion = inclusion;
    }

    public String getLastCancelDate() {
        return LastCancelDate;
    }

    public void setLastCancelDate(String lastCancelDate) {
        LastCancelDate = lastCancelDate;
    }

    public static class RoomPrice{
        String CurrencyCode;//": "INR",
        double RoomPrice;//": 1499.09,
        double Tax;//": 270.9,
        double ExtraGuestCharge;//": 0,
        double ChildCharge;//": 0,
        double OtherCharges;//": 4.9,
        double Discount;//": 0,
        double PublishedPrice;//": 1784.69,
        double PublishedPriceRoundedOff;//": 1785,
        double OfferedPrice;//": 1774.89,
        double OfferedPriceRoundedOff;//": 1775,
        double AgentCommission;//": 9.8,
        double AgentMarkUp;//": 0,
        double ServiceTax;//": 0.7,
        double TDS;//": 4.2

        public String getCurrencyCode() {
            return CurrencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            CurrencyCode = currencyCode;
        }

        public double getRoomPrice() {
            return RoomPrice;
        }

        public void setRoomPrice(double roomPrice) {
            RoomPrice = roomPrice;
        }

        public double getTax() {
            return Tax;
        }

        public void setTax(double tax) {
            Tax = tax;
        }

        public double getExtraGuestCharge() {
            return ExtraGuestCharge;
        }

        public void setExtraGuestCharge(double extraGuestCharge) {
            ExtraGuestCharge = extraGuestCharge;
        }

        public double getChildCharge() {
            return ChildCharge;
        }

        public void setChildCharge(double childCharge) {
            ChildCharge = childCharge;
        }

        public double getOtherCharges() {
            return OtherCharges;
        }

        public void setOtherCharges(double otherCharges) {
            OtherCharges = otherCharges;
        }

        public double getDiscount() {
            return Discount;
        }

        public void setDiscount(double discount) {
            Discount = discount;
        }

        public double getPublishedPrice() {
            return PublishedPrice;
        }

        public void setPublishedPrice(double publishedPrice) {
            PublishedPrice = publishedPrice;
        }

        public double getPublishedPriceRoundedOff() {
            return PublishedPriceRoundedOff;
        }

        public void setPublishedPriceRoundedOff(double publishedPriceRoundedOff) {
            PublishedPriceRoundedOff = publishedPriceRoundedOff;
        }

        public double getOfferedPrice() {
            return OfferedPrice;
        }

        public void setOfferedPrice(double offeredPrice) {
            OfferedPrice = offeredPrice;
        }

        public double getOfferedPriceRoundedOff() {
            return OfferedPriceRoundedOff;
        }

        public void setOfferedPriceRoundedOff(double offeredPriceRoundedOff) {
            OfferedPriceRoundedOff = offeredPriceRoundedOff;
        }

        public double getAgentCommission() {
            return AgentCommission;
        }

        public void setAgentCommission(double agentCommission) {
            AgentCommission = agentCommission;
        }

        public double getAgentMarkUp() {
            return AgentMarkUp;
        }

        public void setAgentMarkUp(double agentMarkUp) {
            AgentMarkUp = agentMarkUp;
        }

        public double getServiceTax() {
            return ServiceTax;
        }

        public void setServiceTax(double serviceTax) {
            ServiceTax = serviceTax;
        }

        public double getTDS() {
            return TDS;
        }

        public void setTDS(double TDS) {
            this.TDS = TDS;
        }
    }
}
