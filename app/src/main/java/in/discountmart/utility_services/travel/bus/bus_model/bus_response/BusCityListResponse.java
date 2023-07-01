package in.discountmart.utility_services.travel.bus.bus_model.bus_response;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class BusCityListResponse extends BaseResponse {

    String Name;//":"Elurupadu","
    String ID;//":190

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
