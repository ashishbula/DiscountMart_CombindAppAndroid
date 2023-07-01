package in.discountmart.utility_services.travel.flight.flight_model.flight_request_model;


import in.discountmart.utility_services.model.request_model.DataRequest;

public class GetCityList extends DataRequest {

    String CityName;//":"jai","
    String Type;//":"D"

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
