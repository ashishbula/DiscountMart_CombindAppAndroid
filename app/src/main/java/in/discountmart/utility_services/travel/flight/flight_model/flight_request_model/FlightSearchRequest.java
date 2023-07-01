package in.discountmart.utility_services.travel.flight.flight_model.flight_request_model;


import java.util.ArrayList;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class FlightSearchRequest extends DataRequest {

    String EndUserIp;//": "115.178.101.14",
    String TokenId;//": "f5e955da-bc2e-49f3-b9b5-5d1b2636f2a6",
    String AdultCount;//": "1",
    String ChildCount;//": "0",
    String InfantCount;//": "0",
    String DirectFlight;//": "false",
    String OneStopFlight;//": "false",
    String JourneyType;//": "1",
    String PreferredAirlines;//": null,
    String GroupID;
    ArrayList<FlightSegment> Segments;
    String Sources[];

    public String getEndUserIp() {
        return EndUserIp;
    }

    public void setEndUserIp(String endUserIp) {
        EndUserIp = endUserIp;
    }

    public String getTokenId() {
        return TokenId;
    }

    public void setTokenId(String tokenId) {
        TokenId = tokenId;
    }

    public String getAdultCount() {
        return AdultCount;
    }

    public void setAdultCount(String adultCount) {
        AdultCount = adultCount;
    }

    public String getChildCount() {
        return ChildCount;
    }

    public void setChildCount(String childCount) {
        ChildCount = childCount;
    }

    public String getInfantCount() {
        return InfantCount;
    }

    public void setInfantCount(String infantCount) {
        InfantCount = infantCount;
    }

    public String getDirectFlight() {
        return DirectFlight;
    }

    public void setDirectFlight(String directFlight) {
        DirectFlight = directFlight;
    }

    public String getOneStopFlight() {
        return OneStopFlight;
    }

    public void setOneStopFlight(String oneStopFlight) {
        OneStopFlight = oneStopFlight;
    }

    public String getJourneyType() {
        return JourneyType;
    }

    public void setJourneyType(String journeyType) {
        JourneyType = journeyType;
    }

    public String getPreferredAirlines() {
        return PreferredAirlines;
    }

    public void setPreferredAirlines(String preferredAirlines) {
        PreferredAirlines = preferredAirlines;
    }

    public ArrayList<FlightSegment> getSegments() {
        return Segments;
    }

    public void setSegments(ArrayList<FlightSegment> segments) {
        Segments = segments;
    }

    public String[] getSources() {
        return Sources;
    }

    public void setSources(String[] sources) {
        Sources = sources;
    }

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String groupID) {
        GroupID = groupID;
    }

    public static class FlightSegment{

        String Origin;//": "JAI",
        String Destination;//": "IXC",
        String OriginFull;//": "JAI (Jaipur)",
        String DestinationFull;//": "IXC (Chandigarh)",
        String FlightCabinClass;//": "1",
        String PreferredDepartureTime;//": "2019-03-05",
        String PreferredArrivalTime;//": "2019-03-05"

        public String getOrigin() {
            return Origin;
        }

        public void setOrigin(String origin) {
            Origin = origin;
        }

        public String getDestination() {
            return Destination;
        }

        public void setDestination(String destination) {
            Destination = destination;
        }

        public String getOriginFull() {
            return OriginFull;
        }

        public void setOriginFull(String originFull) {
            OriginFull = originFull;
        }

        public String getDestinationFull() {
            return DestinationFull;
        }

        public void setDestinationFull(String destinationFull) {
            DestinationFull = destinationFull;
        }

        public String getFlightCabinClass() {
            return FlightCabinClass;
        }

        public void setFlightCabinClass(String flightCabinClass) {
            FlightCabinClass = flightCabinClass;
        }

        public String getPreferredDepartureTime() {
            return PreferredDepartureTime;
        }

        public void setPreferredDepartureTime(String preferredDepartureTime) {
            PreferredDepartureTime = preferredDepartureTime;
        }

        public String getPreferredArrivalTime() {
            return PreferredArrivalTime;
        }

        public void setPreferredArrivalTime(String preferredArrivalTime) {
            PreferredArrivalTime = preferredArrivalTime;
        }
    }





}
