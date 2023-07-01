package in.discountmart.utility_services.travel.hotel.hotel_model;

import java.io.Serializable;

public class Child_AgeModel implements Serializable {

    String child;
    String age;
    String room;

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
