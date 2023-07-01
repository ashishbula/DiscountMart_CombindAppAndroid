package in.discountmart.utility_services.travel.hotel.hotel_model;

import java.io.Serializable;
import java.util.ArrayList;

public class HotelRoomModel implements Serializable {
    String room;
    String adult;
    String child;
    int adultcount;
    int childcount;
    boolean age;
    int flag;
    int childage1;
    int childage2;
    boolean addLayout;


    ArrayList<Child_AgeModel> childAgeModels=new ArrayList<Child_AgeModel>();

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public int getAdultcount() {
        return adultcount;
    }

    public void setAdultcount(int adultcount) {
        this.adultcount = adultcount;
    }

    public int getChildcount() {
        return childcount;
    }

    public void setChildcount(int childcount) {
        this.childcount = childcount;
    }

    public ArrayList<Child_AgeModel> getChildAgeModels() {
        return childAgeModels;
    }

    public void setChildAgeModels(ArrayList<Child_AgeModel> childAgeModels) {
        this.childAgeModels = childAgeModels;
    }

    public boolean isAge() {
        return age;
    }

    public void setAge(boolean age) {
        this.age = age;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getChildage1() {
        return childage1;
    }

    public void setChildage1(int childage1) {
        this.childage1 = childage1;
    }

    public int getChildage2() {
        return childage2;
    }

    public void setChildage2(int childage2) {
        this.childage2 = childage2;
    }

    public boolean isAddLayout() {
        return addLayout;
    }

    public void setAddLayout(boolean addLayout) {
        this.addLayout = addLayout;
    }


}
