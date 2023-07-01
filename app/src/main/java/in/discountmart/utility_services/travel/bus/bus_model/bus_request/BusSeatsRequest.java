package in.discountmart.utility_services.travel.bus.bus_model.bus_request;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class BusSeatsRequest extends DataRequest {
       String TripID;//": "1000282931243018188"

    public String getTripID() {
        return TripID;
    }

    public void setTripID(String tripID) {
        TripID = tripID;
    }
}
