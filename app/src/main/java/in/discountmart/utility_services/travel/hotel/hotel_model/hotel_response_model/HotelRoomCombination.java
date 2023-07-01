package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model;

import java.util.ArrayList;

public class HotelRoomCombination {

    String InfoSource;//": "FixedCombination",
    String IsPolicyPerStay;//": false,

    ArrayList<Combination> RoomCombination;

    public String getInfoSource() {
        return InfoSource;
    }

    public void setInfoSource(String infoSource) {
        InfoSource = infoSource;
    }

    public String getIsPolicyPerStay() {
        return IsPolicyPerStay;
    }

    public void setIsPolicyPerStay(String isPolicyPerStay) {
        IsPolicyPerStay = isPolicyPerStay;
    }

    public ArrayList<Combination> getRoomCombination() {
        return RoomCombination;
    }

    public void setRoomCombination(ArrayList<Combination> roomCombination) {
        RoomCombination = roomCombination;
    }

    public static class Combination{

        ArrayList<Integer> RoomIndex;

        public ArrayList<Integer> getRoomIndex() {
            return RoomIndex;
        }

        public void setRoomIndex(ArrayList<Integer> roomIndex) {
            RoomIndex = roomIndex;
        }
    }
}
