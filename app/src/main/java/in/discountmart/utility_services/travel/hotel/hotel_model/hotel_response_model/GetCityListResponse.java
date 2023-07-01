package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model;

public class GetCityListResponse {

     String id;//": 0,
    String CityCode;//": "AGR",
    String CityName;//": "AGR (Agra)",
    String CountryCode;//": null,
    String AirportDesc;//": null,
    String Type;//": null,
    String Activestatus;//": null

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getAirportDesc() {
        return AirportDesc;
    }

    public void setAirportDesc(String airportDesc) {
        AirportDesc = airportDesc;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getActivestatus() {
        return Activestatus;
    }

    public void setActivestatus(String activestatus) {
        Activestatus = activestatus;
    }
}
