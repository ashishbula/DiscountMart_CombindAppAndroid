package in.discountmart.utility_services.recharge.recharge_model.recharge_response_model;

import in.discountmart.utility_services.model.response_model.BaseResponse;


public class LocationResponse extends BaseResponse {
    String Location;//":"Andhra Pradesh","
    String IsActive;//":true}


    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }
}
