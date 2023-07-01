package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model;

import java.util.ArrayList;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class HotelBookRequest extends DataRequest {

     String HotelName;//": "Abcd",
    String TotalAmount;//": "100",
    String  NoOfRooms;//": "2",
    String FormNo;//": "1002",
    String PromoDiscount;//": "0",
    String Discount;//": "0",
    String EmailID;//":
    String PromoCode;//": "",
    String AdultCount;//": "1",
    String ChildCount;//": "1",
    ArrayList<HotelRoomPassenger> HotelPassenger;
     String MobileNo;//": "8696499007",
    String Otp;//": "1341",
    String CheckOutDate;//": "2019-08-05",
    String CheckInDate;//": "2019-08-03"
    String City;

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getNoOfRooms() {
        return NoOfRooms;
    }

    public void setNoOfRooms(String noOfRooms) {
        NoOfRooms = noOfRooms;
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

    public ArrayList<HotelRoomPassenger> getHotelPassenger() {
        return HotelPassenger;
    }

    public void setHotelPassenger(ArrayList<HotelRoomPassenger> hotelPassenger) {
        HotelPassenger = hotelPassenger;
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

    public static class HotelRoomPassenger{
        String Title;//": "Mr",
        String FirstName;//": "Amit",
        String MiddleName;//": "",
        String LastName;//": "Saini",
        String PaxType;//": 1,
        String Age;//": 32,
        String PassportNo;//": "",
        String PassportIssueDate;//": null,
        String PassportExpDate;//": null,
        String Phoneno;//": "",
        String Email;//": "aks.bispl@gmail.com"

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
    }
}
