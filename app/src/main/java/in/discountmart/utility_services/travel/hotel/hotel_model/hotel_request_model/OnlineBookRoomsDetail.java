package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model;

import java.util.ArrayList;

public class OnlineBookRoomsDetail {

    String ID;//": null,
    String RoomIndex;//": "1",
    String RoomTypeCode;//": "15406020|fdb7009e-e499-7260-ca11-a513b65e7867|1^^1^^1480634||fdb7009e-e499-7260-ca11-a513b65e7867~!:~1~!:~1",
    String RoomTypeName;//": "Deluxe Room",
    String RatePlanCode;//": "1480634||fdb7009e-e499-7260-ca11-a513b65e7867",
    String BedTypeCode;//": null,
    String SmokingPreference;//": "0",
    String Supplements;//": null,
    BookRoomPrice Price;
    ArrayList<BookRoomPassenger>HotelPassenger;
     String Amenity;//": null,
    String BedTypes;//": null,
    String CancellationPolicies;//": null,
    String CancellationPolicy;//": null,
    String Inclusion;//": null,
    String LastCancelDate;//": null,
    String InfoSource;//": null

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

    public BookRoomPrice getPrice() {
        return Price;
    }

    public void setPrice(BookRoomPrice price) {
        Price = price;
    }

    public ArrayList<BookRoomPassenger> getHotelPassenger() {
        return HotelPassenger;
    }

    public void setHotelPassenger(ArrayList<BookRoomPassenger> hotelPassenger) {
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

    public String getInfoSource() {
        return InfoSource;
    }

    public void setInfoSource(String infoSource) {
        InfoSource = infoSource;
    }

    public static class BookRoomPrice{
        String CurrencyCode;//": "INR",
        double RoomPrice;//": 590.63,
        double Tax;//": 177.1,
        double ExtraGuestCharge;//": 0,
        double ChildCharge;//": 0,
        double OtherCharges;//": 4.9,
        double Discount;//": 0,
        double PublishedPrice;//": 782.43,
        double PublishedPriceRoundedOff;//": 782,
        double OfferedPrice;//": 772.63,
        int OfferedPriceRoundedOff;//": 773,
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

        public int getOfferedPriceRoundedOff() {
            return OfferedPriceRoundedOff;
        }

        public void setOfferedPriceRoundedOff(int offeredPriceRoundedOff) {
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

    public static class BookRoomPassenger{
        String Title;//": "Mr",
        String FirstName;//": "Amit",
        String MiddleName;//": null,
        String LastName;//": "saini",
        String PaxType;//": 1,
        String Age;//": 25,
        String PassportNo;//": null,
        String PassportIssueDate;//": "0001-01-01T00:00:00",
        String PassportExpDate;//": "0001-01-01T00:00:00",
        String Phoneno;//": "",
        String Email;//": "",
        String LeadPassenger;//": true

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String firstName) {
            FirstName = firstName;
        }

        public String getMiddleName() {
            return MiddleName;
        }

        public void setMiddleName(String middleName) {
            MiddleName = middleName;
        }

        public String getLastName() {
            return LastName;
        }

        public void setLastName(String lastName) {
            LastName = lastName;
        }

        public String getPaxType() {
            return PaxType;
        }

        public void setPaxType(String paxType) {
            PaxType = paxType;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String age) {
            Age = age;
        }

        public String getPassportNo() {
            return PassportNo;
        }

        public void setPassportNo(String passportNo) {
            PassportNo = passportNo;
        }

        public String getPassportIssueDate() {
            return PassportIssueDate;
        }

        public void setPassportIssueDate(String passportIssueDate) {
            PassportIssueDate = passportIssueDate;
        }

        public String getPassportExpDate() {
            return PassportExpDate;
        }

        public void setPassportExpDate(String passportExpDate) {
            PassportExpDate = passportExpDate;
        }

        public String getPhoneno() {
            return Phoneno;
        }

        public void setPhoneno(String phoneno) {
            Phoneno = phoneno;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getLeadPassenger() {
            return LeadPassenger;
        }

        public void setLeadPassenger(String leadPassenger) {
            LeadPassenger = leadPassenger;
        }
    }
}
