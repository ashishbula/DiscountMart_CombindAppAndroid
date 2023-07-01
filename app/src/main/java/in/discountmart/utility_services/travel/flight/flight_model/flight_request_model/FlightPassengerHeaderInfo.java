package in.discountmart.utility_services.travel.flight.flight_model.flight_request_model;


import in.discountmart.utility_services.model.request_model.DataRequest;

public class FlightPassengerHeaderInfo extends DataRequest {

    String name;
    String surname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
