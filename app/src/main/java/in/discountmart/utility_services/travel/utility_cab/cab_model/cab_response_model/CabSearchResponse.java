package in.discountmart.utility_services.travel.utility_cab.cab_model.cab_response_model;

import java.io.Serializable;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class CabSearchResponse extends BaseResponse implements Serializable {
     String CabID;//": 225,
    String Source;//": "Jaipur",
    String Dest;//": "Ajmer",
    String Distance;//": "130",
    String Rate;//": 12,
    String Car;//": "Tata Indica",
    String Total;//": 3120,
    String Type;//": "NON AC"

    public String getCabID() {
        return CabID;
    }

    public void setCabID(String cabID) {
        CabID = cabID;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getDest() {
        return Dest;
    }

    public void setDest(String dest) {
        Dest = dest;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getCar() {
        return Car;
    }

    public void setCar(String car) {
        Car = car;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
