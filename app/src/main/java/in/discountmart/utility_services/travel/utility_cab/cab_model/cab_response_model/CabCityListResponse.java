package in.discountmart.utility_services.travel.utility_cab.cab_model.cab_response_model;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class CabCityListResponse extends BaseResponse {
    String Name;//":"Agra ","
    String ID;//":1

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
