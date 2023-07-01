package in.discountmart.utility_services.travel.utility_cab.cab_model.cab_request_mdel;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class CabCityRequest extends DataRequest {
     String Name;//": "",
    String ID;//": 0

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
