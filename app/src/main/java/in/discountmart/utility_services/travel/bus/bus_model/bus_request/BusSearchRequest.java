package in.discountmart.utility_services.travel.bus.bus_model.bus_request;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class BusSearchRequest extends DataRequest {

     String FromCityID;//": "1422",
    String ToCityID;//": "1406",
    String DOJ;//": "2019-07-20"
    String BusGroupId;

    public String getFromCityID() {
        return FromCityID;
    }

    public void setFromCityID(String fromCityID) {
        FromCityID = fromCityID;
    }

    public String getToCityID() {
        return ToCityID;
    }

    public void setToCityID(String toCityID) {
        ToCityID = toCityID;
    }

    public String getDOJ() {
        return DOJ;
    }

    public void setDOJ(String DOJ) {
        this.DOJ = DOJ;
    }

    public String getBusGroupId() {
        return BusGroupId;
    }

    public void setBusGroupId(String busGroupId) {
        BusGroupId = busGroupId;
    }
}
