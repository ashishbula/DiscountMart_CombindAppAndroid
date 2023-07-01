package in.discountmart.utility_services.travel.flight.flight_model.flight_request_model;


import java.util.ArrayList;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class FlightBookRequest extends DataRequest {
    String ResultIndex;//": "OB1",
    String EndUserIp;//": "115.178.101.14",
    String TokenId;//": "f5e955da-bc2e-49f3-b9b5-5d1b2636f2a6",
    String IsLCC;//": "False",
    String TraceId;//": "7e37b1cb-35c0-4f38-a770-1f173c41317f",
    String Discount;
    String FormNo;
    String SponsorFormNo;
    String Otp;
    String IsApplyVoucher;
    String PromoDiscount;
    String PromoCode;
    String MobileNo;
    String EmailID;
    String TotalAmount;
    String BasePrice;
    String TaxAmount;


    ArrayList<FlightPassenger> Passengers;

    public String getResultIndex() {
        return ResultIndex;
    }

    public void setResultIndex(String resultIndex) {
        ResultIndex = resultIndex;
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

    public String getIsLCC() {
        return IsLCC;
    }

    public void setIsLCC(String isLCC) {
        IsLCC = isLCC;
    }

    public String getTraceId() {
        return TraceId;
    }

    public void setTraceId(String traceId) {
        TraceId = traceId;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getFormNo() {
        return FormNo;
    }

    public void setFormNo(String formNo) {
        FormNo = formNo;
    }

    public String getSponsorFormNo() {
        return SponsorFormNo;
    }

    public void setSponsorFormNo(String sponsorFormNo) {
        SponsorFormNo = sponsorFormNo;
    }

    public String getOtp() {
        return Otp;
    }

    public void setOtp(String otp) {
        Otp = otp;
    }

    public String getIsApplyVoucher() {
        return IsApplyVoucher;
    }

    public void setIsApplyVoucher(String isApplyVoucher) {
        IsApplyVoucher = isApplyVoucher;
    }

    public String getPromoDiscount() {
        return PromoDiscount;
    }

    public void setPromoDiscount(String promoDiscount) {
        PromoDiscount = promoDiscount;
    }

    public String getPromoCode() {
        return PromoCode;
    }

    public void setPromoCode(String promoCode) {
        PromoCode = promoCode;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getBasePrice() {
        return BasePrice;
    }

    public void setBasePrice(String basePrice) {
        BasePrice = basePrice;
    }

    public String getTaxAmount() {
        return TaxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        TaxAmount = taxAmount;
    }

    public ArrayList<FlightPassenger> getPassengers() {
        return Passengers;
    }

    public void setPassengers(ArrayList<FlightPassenger> passengers) {
        Passengers = passengers;
    }

    public static class FlightPassenger{

        String Title;//": "Mr",
        String FirstName;//": "Amit",
        String LastName;//": "Saini",
        String PaxType;//"//: 1,
        String DateOfBirth;//": "1987-02-28T00:00:00.0000000+05:30",
        String Gender;//": 1,
        String PassportNo;//": "KJHHJKHKJH",
        String PassportExpiry;//": "2020-12-06T00:00:00",
        String AddressLine1;//": "123, Test",
        String AddressLine2;//": "",
        String Fare;//": null,
        String City;//": "Bhilwara",
        String CountryCode;//": "IN",
        String CountryName;//": "India",
        String Nationality;//": "IN",
        String ContactNo;//": "8890885477",
        String Email;//": "aks.bispl@gmail.com",
        String IsLeadPax;//": true,
        String FFAirlineCode;//": null,
        String FFNumber;//": "",
        String GSTCompanyAddress;//": "",
        String GSTCompanyContactNumber;//": "",
        String GSTCompanyName;//": "",
        String GSTNumber;//": "",
        String GSTCompanyEmail;//": ""

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

        public String getDateOfBirth() {
            return DateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            DateOfBirth = dateOfBirth;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public String getPassportNo() {
            return PassportNo;
        }

        public void setPassportNo(String passportNo) {
            PassportNo = passportNo;
        }

        public String getPassportExpiry() {
            return PassportExpiry;
        }

        public void setPassportExpiry(String passportExpiry) {
            PassportExpiry = passportExpiry;
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

        public String getFare() {
            return Fare;
        }

        public void setFare(String fare) {
            Fare = fare;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String city) {
            City = city;
        }

        public String getCountryCode() {
            return CountryCode;
        }

        public void setCountryCode(String countryCode) {
            CountryCode = countryCode;
        }

        public String getCountryName() {
            return CountryName;
        }

        public void setCountryName(String countryName) {
            CountryName = countryName;
        }

        public String getNationality() {
            return Nationality;
        }

        public void setNationality(String nationality) {
            Nationality = nationality;
        }

        public String getContactNo() {
            return ContactNo;
        }

        public void setContactNo(String contactNo) {
            ContactNo = contactNo;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getIsLeadPax() {
            return IsLeadPax;
        }

        public void setIsLeadPax(String isLeadPax) {
            IsLeadPax = isLeadPax;
        }

        public String getFFAirlineCode() {
            return FFAirlineCode;
        }

        public void setFFAirlineCode(String FFAirlineCode) {
            this.FFAirlineCode = FFAirlineCode;
        }

        public String getFFNumber() {
            return FFNumber;
        }

        public void setFFNumber(String FFNumber) {
            this.FFNumber = FFNumber;
        }

        public String getGSTCompanyAddress() {
            return GSTCompanyAddress;
        }

        public void setGSTCompanyAddress(String GSTCompanyAddress) {
            this.GSTCompanyAddress = GSTCompanyAddress;
        }

        public String getGSTCompanyContactNumber() {
            return GSTCompanyContactNumber;
        }

        public void setGSTCompanyContactNumber(String GSTCompanyContactNumber) {
            this.GSTCompanyContactNumber = GSTCompanyContactNumber;
        }

        public String getGSTCompanyName() {
            return GSTCompanyName;
        }

        public void setGSTCompanyName(String GSTCompanyName) {
            this.GSTCompanyName = GSTCompanyName;
        }

        public String getGSTNumber() {
            return GSTNumber;
        }

        public void setGSTNumber(String GSTNumber) {
            this.GSTNumber = GSTNumber;
        }

        public String getGSTCompanyEmail() {
            return GSTCompanyEmail;
        }

        public void setGSTCompanyEmail(String GSTCompanyEmail) {
            this.GSTCompanyEmail = GSTCompanyEmail;
        }
    }
}
