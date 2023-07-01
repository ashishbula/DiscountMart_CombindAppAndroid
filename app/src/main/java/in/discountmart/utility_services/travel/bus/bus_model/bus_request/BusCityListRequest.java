package in.discountmart.utility_services.travel.bus.bus_model.bus_request;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class BusCityListRequest extends DataRequest {

    String Name;//": "",
    String ID;//": 1422

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
