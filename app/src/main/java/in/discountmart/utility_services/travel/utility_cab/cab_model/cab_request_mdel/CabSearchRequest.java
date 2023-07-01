package in.discountmart.utility_services.travel.utility_cab.cab_model.cab_request_mdel;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class CabSearchRequest extends DataRequest {
     String CabFrom;//": "jaipur",
    String CabTo;//": "Ajmer",
    String CabGroupId;//": "1",
    String CabPickupDate;//": "07-02-2021",
    String CabPickupTime;//": "04:50 pm",
    String CabType;//": false

    public String getCabFrom() {
        return CabFrom;
    }

    public void setCabFrom(String cabFrom) {
        CabFrom = cabFrom;
    }

    public String getCabTo() {
        return CabTo;
    }

    public void setCabTo(String cabTo) {
        CabTo = cabTo;
    }

    public String getCabGroupId() {
        return CabGroupId;
    }

    public void setCabGroupId(String cabGroupId) {
        CabGroupId = cabGroupId;
    }

    public String getCabPickupDate() {
        return CabPickupDate;
    }

    public void setCabPickupDate(String cabPickupDate) {
        CabPickupDate = cabPickupDate;
    }

    public String getCabPickupTime() {
        return CabPickupTime;
    }

    public void setCabPickupTime(String cabPickupTime) {
        CabPickupTime = cabPickupTime;
    }

    public String getCabType() {
        return CabType;
    }

    public void setCabType(String cabType) {
        CabType = cabType;
    }
}
