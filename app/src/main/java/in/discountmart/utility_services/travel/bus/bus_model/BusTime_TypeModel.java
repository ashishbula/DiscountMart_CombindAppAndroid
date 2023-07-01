package in.discountmart.utility_services.travel.bus.bus_model;

import java.io.Serializable;

public class BusTime_TypeModel implements Serializable {
    String type;
    String time;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
