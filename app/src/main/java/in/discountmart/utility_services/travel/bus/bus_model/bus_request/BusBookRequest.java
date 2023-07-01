package in.discountmart.utility_services.travel.bus.bus_model.bus_request;

import java.util.ArrayList;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class BusBookRequest extends DataRequest {
    String AvailableTripId;//": "1422",
    String BoardingPointId;//": "1406",
    String DOJ;//": "2019-07-26",
    String City;//": "2019-07-26",
    String Destination;//": "2019-07-26",
    String TotalAmount;//": "2019-07-26",
    String SeatPrice;//": "2019-07-26",
    String PromoDiscount;//": "2019-07-26",
    String Discount;//": "2019-07-26",
    String PromoCode;//": "2019-07-26",
    String FormNo;//": "2019-07-26",
    String MobileNo;//": "2019-07-26",
    String EmailID;//": "2019-07-26",
    String Otp;//": "2019-07-26",
    String Source;
    ArrayList<InventoryItemsList> InventoryItems;

    public String getAvailableTripId() {
        return AvailableTripId;
    }

    public void setAvailableTripId(String availableTripId) {
        AvailableTripId = availableTripId;
    }

    public String getBoardingPointId() {
        return BoardingPointId;
    }

    public void setBoardingPointId(String boardingPointId) {
        BoardingPointId = boardingPointId;
    }

    public String getDOJ() {
        return DOJ;
    }

    public void setDOJ(String DOJ) {
        this.DOJ = DOJ;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getSeatPrice() {
        return SeatPrice;
    }

    public void setSeatPrice(String seatPrice) {
        SeatPrice = seatPrice;
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

    public String getPromoCode() {
        return PromoCode;
    }

    public void setPromoCode(String promoCode) {
        PromoCode = promoCode;
    }

    public String getFormNo() {
        return FormNo;
    }

    public void setFormNo(String formNo) {
        FormNo = formNo;
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

    public String getOtp() {
        return Otp;
    }

    public void setOtp(String otp) {
        Otp = otp;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public ArrayList<InventoryItemsList> getInventoryItems() {
        return InventoryItems;
    }

    public void setInventoryItems(ArrayList<InventoryItemsList> inventoryItems) {
        InventoryItems = inventoryItems;
    }

    public static class InventoryItemsList{
        String Fare;
        String LadiesSeat;
        String SeatName;

        Passengers Passenger;

        public String getFare() {
            return Fare;
        }

        public void setFare(String fare) {
            Fare = fare;
        }

        public String getLadiesSeat() {
            return LadiesSeat;
        }

        public void setLadiesSeat(String ladiesSeat) {
            LadiesSeat = ladiesSeat;
        }

        public String getSeatName() {
            return SeatName;
        }

        public void setSeatName(String seatName) {
            SeatName = seatName;
        }

        public Passengers getPassenger() {
            return Passenger;
        }

        public void setPassenger(Passengers passenger) {
            Passenger = passenger;
        }

        public static class Passengers{
            String Age;
            String Gender;
            String IdNumber;
            String IdType;
            String Mobile;
            String Name;
            String Primary;
            String Title;
            String Email;

            public String getAge() {
                return Age;
            }

            public void setAge(String age) {
                Age = age;
            }

            public String getGender() {
                return Gender;
            }

            public void setGender(String gender) {
                Gender = gender;
            }

            public String getIdNumber() {
                return IdNumber;
            }

            public void setIdNumber(String idNumber) {
                IdNumber = idNumber;
            }

            public String getIdType() {
                return IdType;
            }

            public void setIdType(String idType) {
                IdType = idType;
            }

            public String getMobile() {
                return Mobile;
            }

            public void setMobile(String mobile) {
                Mobile = mobile;
            }

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public String getPrimary() {
                return Primary;
            }

            public void setPrimary(String primary) {
                Primary = primary;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String title) {
                Title = title;
            }

            public String getEmail() {
                return Email;
            }

            public void setEmail(String email) {
                Email = email;
            }
        }
    }


}
