package in.discountmart.utility_services.travel.flight.flight_model.flight_response_model;


import java.io.Serializable;
import java.util.ArrayList;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class FlightSearchReturnResponse extends BaseResponse implements Serializable {

    ArrayList<OwnwardFlightSearch> lstOneFlightResponse;
    ArrayList<ReturnFlightSearch>lstReturnFlightResponse;

    public ArrayList<OwnwardFlightSearch> getLstOneFlightResponse() {
        return lstOneFlightResponse;
    }

    public void setLstOneFlightResponse(ArrayList<OwnwardFlightSearch> lstOneFlightResponse) {
        this.lstOneFlightResponse = lstOneFlightResponse;
    }

    public ArrayList<ReturnFlightSearch> getLstReturnFlightResponse() {
        return lstReturnFlightResponse;
    }

    public void setLstReturnFlightResponse(ArrayList<ReturnFlightSearch> lstReturnFlightResponse) {
        this.lstReturnFlightResponse = lstReturnFlightResponse;
    }
}
