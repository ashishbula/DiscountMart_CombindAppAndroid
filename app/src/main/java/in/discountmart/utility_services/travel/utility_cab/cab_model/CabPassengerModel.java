package in.discountmart.utility_services.travel.utility_cab.cab_model;

import java.io.Serializable;

public class CabPassengerModel implements Serializable {
    String Name;
    String gender;
    String email;
    String mobile;
    String age;
   String seat;
   String seatType;
   int type;
   String errorMsg;
   int totalSeat;

    public static final int HEADER_TYPE=0;
    public static final int ITEM_TYPE=1;
    public static final int FOOTER_TYPE=2;
    private boolean isGenderChecked;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isGenderChecked() {
        return isGenderChecked;
    }

    public void setGenderChecked(boolean genderChecked) {
        isGenderChecked = genderChecked;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getTotalSeat() {
        return totalSeat;
    }

    public void setTotalSeat(int totalSeat) {
        this.totalSeat = totalSeat;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }
}
