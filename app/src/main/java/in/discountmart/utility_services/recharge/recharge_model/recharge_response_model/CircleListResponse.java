package in.discountmart.utility_services.recharge.recharge_model.recharge_response_model;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class CircleListResponse extends BaseResponse {

    String ID;//": "1",
    String CircleName;//": "Andhra Pradesh & Telangana",
    String Code;//": "AP",
    String GpArea;//": "State of Andhra Pradesh, State of Telangana and Yanam district",
    String IsActive;//": true,
    String Category;//": "A"

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCircleName() {
        return CircleName;
    }

    public void setCircleName(String circleName) {
        CircleName = circleName;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getGpArea() {
        return GpArea;
    }

    public void setGpArea(String gpArea) {
        GpArea = gpArea;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
