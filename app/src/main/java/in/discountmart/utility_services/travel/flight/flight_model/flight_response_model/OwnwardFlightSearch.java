package in.discountmart.utility_services.travel.flight.flight_model.flight_response_model;

import java.io.Serializable;
import java.util.ArrayList;

public class OwnwardFlightSearch implements Serializable {

    String ID;//": "65",
    String traceID;//": "6bc93a3f-b833-4a1d-a258-58c258d91f10",
    String airlineName;//": "Indigo",
    String airlineCode;//": "6E",
    String airlineLogo;//": "https://utilitywebapi.bisplindia.in/AirlineLogo/6E.gif",
    String flightNumber;//": "244",
    String origin;//": "Ahmedabad",
    String destination;//": "Delhi",
    String arrivaltime;//": "16:30",
    String arrivalDatetime;//": "Saturday,Sep 07",
    String departureTime;//": "07:45",
    String departureDateTime;//": "Saturday,Sep 07",
    String stopage;//": "1 Stop",
    String fairType;//": "Refundable",
    String baseFare;//": "6583",
    String taxAmount;//": "1217",
    String grossAmount;//": "7800",
    String adultbaseFare;//": "6583",
    String childbaseFare;//: null,
    String infantbaseFare;//": null,
    String segmentID;//": null,
    String availableSeat;//": "9",
    String resultID;//": null,
    String differenceTime;//": "06h 10m",
    String resultIndex;//": "OB62",
    String isLCC;//": "true",
    String arriDate;//": "2019-09-07T16:30:00",
    String departDate;//": "2019-09-07T07:45:00"

    ArrayList<OwnFlightSegment> Segment;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTraceID() {
        return traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getAirlineLogo() {
        return airlineLogo;
    }

    public void setAirlineLogo(String airlineLogo) {
        this.airlineLogo = airlineLogo;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getArrivaltime() {
        return arrivaltime;
    }

    public void setArrivaltime(String arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public String getArrivalDatetime() {
        return arrivalDatetime;
    }

    public void setArrivalDatetime(String arrivalDatetime) {
        this.arrivalDatetime = arrivalDatetime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(String departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getStopage() {
        return stopage;
    }

    public void setStopage(String stopage) {
        this.stopage = stopage;
    }

    public String getFairType() {
        return fairType;
    }

    public void setFairType(String fairType) {
        this.fairType = fairType;
    }

    public String getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(String baseFare) {
        this.baseFare = baseFare;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(String grossAmount) {
        this.grossAmount = grossAmount;
    }

    public String getAdultbaseFare() {
        return adultbaseFare;
    }

    public void setAdultbaseFare(String adultbaseFare) {
        this.adultbaseFare = adultbaseFare;
    }

    public String getChildbaseFare() {
        return childbaseFare;
    }

    public void setChildbaseFare(String childbaseFare) {
        this.childbaseFare = childbaseFare;
    }

    public String getInfantbaseFare() {
        return infantbaseFare;
    }

    public void setInfantbaseFare(String infantbaseFare) {
        this.infantbaseFare = infantbaseFare;
    }

    public String getSegmentID() {
        return segmentID;
    }

    public void setSegmentID(String segmentID) {
        this.segmentID = segmentID;
    }

    public String getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(String availableSeat) {
        this.availableSeat = availableSeat;
    }

    public String getResultID() {
        return resultID;
    }

    public void setResultID(String resultID) {
        this.resultID = resultID;
    }

    public String getDifferenceTime() {
        return differenceTime;
    }

    public void setDifferenceTime(String differenceTime) {
        this.differenceTime = differenceTime;
    }

    public String getResultIndex() {
        return resultIndex;
    }

    public void setResultIndex(String resultIndex) {
        this.resultIndex = resultIndex;
    }

    public String getIsLCC() {
        return isLCC;
    }

    public void setIsLCC(String isLCC) {
        this.isLCC = isLCC;
    }

    public String getArriDate() {
        return arriDate;
    }

    public void setArriDate(String arriDate) {
        this.arriDate = arriDate;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public ArrayList<OwnFlightSegment> getSegment() {
        return Segment;
    }

    public void setSegment(ArrayList<OwnFlightSegment> segment) {
        Segment = segment;
    }

    public static class OwnFlightSegment implements Serializable{
         String ID;//": null,
        String traceID;//": null,
        String airlineName;//": "Indigo",
        String airlineCode;//": "6E",
        String airlineLogo;//": null,
        String flightNumber;//": "244",
        String origin;//": "Ahmedabad",
        String destination;//": "Lucknow",
        String arrivaltime;//": "10:20",
        String arrivalDatetime;//": "Saturday,Sep 07",
        String departureTime;//": "07:45",
        String departureDateTime;//": "Saturday,Sep 07",
        String stopage;//": null,
        String fairType;//": null,
        String baseFare;//": null,
        String taxAmount;//": null,
        String grossAmount;//": null,
        String segmentID;//": null,
        String availableSeat;//": "9",
        String resultID;//": "65",
        String differenceTime;//": "02h 35m",
        String arriDate;//": "2019-09-07T10:20:00",
        String departDate;//": "2019-09-07T07:45:00"

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTraceID() {
            return traceID;
        }

        public void setTraceID(String traceID) {
            this.traceID = traceID;
        }

        public String getAirlineName() {
            return airlineName;
        }

        public void setAirlineName(String airlineName) {
            this.airlineName = airlineName;
        }

        public String getAirlineCode() {
            return airlineCode;
        }

        public void setAirlineCode(String airlineCode) {
            this.airlineCode = airlineCode;
        }

        public String getAirlineLogo() {
            return airlineLogo;
        }

        public void setAirlineLogo(String airlineLogo) {
            this.airlineLogo = airlineLogo;
        }

        public String getFlightNumber() {
            return flightNumber;
        }

        public void setFlightNumber(String flightNumber) {
            this.flightNumber = flightNumber;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getArrivaltime() {
            return arrivaltime;
        }

        public void setArrivaltime(String arrivaltime) {
            this.arrivaltime = arrivaltime;
        }

        public String getArrivalDatetime() {
            return arrivalDatetime;
        }

        public void setArrivalDatetime(String arrivalDatetime) {
            this.arrivalDatetime = arrivalDatetime;
        }

        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        public String getDepartureDateTime() {
            return departureDateTime;
        }

        public void setDepartureDateTime(String departureDateTime) {
            this.departureDateTime = departureDateTime;
        }

        public String getStopage() {
            return stopage;
        }

        public void setStopage(String stopage) {
            this.stopage = stopage;
        }

        public String getFairType() {
            return fairType;
        }

        public void setFairType(String fairType) {
            this.fairType = fairType;
        }

        public String getBaseFare() {
            return baseFare;
        }

        public void setBaseFare(String baseFare) {
            this.baseFare = baseFare;
        }

        public String getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(String taxAmount) {
            this.taxAmount = taxAmount;
        }

        public String getGrossAmount() {
            return grossAmount;
        }

        public void setGrossAmount(String grossAmount) {
            this.grossAmount = grossAmount;
        }

        public String getSegmentID() {
            return segmentID;
        }

        public void setSegmentID(String segmentID) {
            this.segmentID = segmentID;
        }

        public String getAvailableSeat() {
            return availableSeat;
        }

        public void setAvailableSeat(String availableSeat) {
            this.availableSeat = availableSeat;
        }

        public String getResultID() {
            return resultID;
        }

        public void setResultID(String resultID) {
            this.resultID = resultID;
        }

        public String getDifferenceTime() {
            return differenceTime;
        }

        public void setDifferenceTime(String differenceTime) {
            this.differenceTime = differenceTime;
        }

        public String getArriDate() {
            return arriDate;
        }

        public void setArriDate(String arriDate) {
            this.arriDate = arriDate;
        }

        public String getDepartDate() {
            return departDate;
        }

        public void setDepartDate(String departDate) {
            this.departDate = departDate;
        }
    }

}
