package in.discountmart.utility_services.travel.flight.flight_model.flight_request_model;


import in.discountmart.utility_services.model.request_model.DataRequest;

public class FlightMarginRequest extends DataRequest {
    String GroupID;//": "0",
    String AirlineCode;//": "SG",
    String SponsorFormNo;//": "1001"

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String groupID) {
        GroupID = groupID;
    }

    public String getAirlineCode() {
        return AirlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        AirlineCode = airlineCode;
    }

    public String getSponsorFormNo() {
        return SponsorFormNo;
    }

    public void setSponsorFormNo(String sponsorFormNo) {
        SponsorFormNo = sponsorFormNo;
    }
}
