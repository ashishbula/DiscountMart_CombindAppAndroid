package in.discountmart.utility_services.travel.flight.flight_model.flight_response_model;


import java.io.Serializable;
import java.util.ArrayList;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class FlightSearchResponse extends BaseResponse implements Serializable {
    String ID;//": "1",
    String traceID;//": "2b872b74-e6e8-42f6-b4c2-d284d32fcfd2",
    String airlineName;//": "Air India",
    String airlineCode;//": "AI",
    String airlineLogo;//": "AirlineLogo/AI.gif",
    String flightNumber;//": "492",
    String origin;//": "Jaipur",
    String destination;//": "Delhi",
    String arrivaltime;//": "14:30",
    String arrivalDatetime;//": "Sunday,Mar 24",
    String departureTime;//": "13:30",
    String departureDateTime;//": "Sunday,Mar 24",
    String stopage;//": "Non Stop",
    String fairType;//": "Refundable",
    String baseFare;//": "7800",
    String taxAmount;//": "3096",
    String grossAmount;//": "10896",
    String adultbaseFare;//": "3400",
    String childbaseFare;//": "3400",
    String infantbaseFare;//": "1000",
    String segmentID;//: null,
    String availableSeat;//": "9",
    String resultID;//": null,
    String differenceTime;//": "01h 00m",
    String resultIndex;//": "OB1",
    String isLCC;//true
    String arriDate;//": "2019-06-12T07:00:00",
    String departDate;//": "2019-06-12T05:10:00"

    ArrayList<FlightSearchSegment> Segment;

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

    public ArrayList<FlightSearchSegment> getSegment() {
        return Segment;
    }

    public void setSegment(ArrayList<FlightSearchSegment> segment) {
        Segment = segment;
    }



    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public static class FlightSearchSegment implements Serializable{
        String ID;//": null,
        String traceID;//": null,
        String airlineName;//": "Air India",
        String airlineCode;//": "AI",
        String airlineLogo;//": null,
        String flightNumber;//": null,
        String origin;//": "Agra",
        String destination;//": "Jaipur",
        String arrivaltime;//": "13:25",
        String arrivalDatetime;//": "Thursday,Mar 14",
        String departureTime;//": "11:30",
        String departureDateTime;//": "Thursday,Mar 14",
        String stopage;//": null,
        String fairType;//": null,
        String baseFare;//": null,
        String taxAmount;//" : null,
        String grossAmount;//": null,
        String segmentID;//": null,
        String availableSeat;//": "6",
        String resultID;//": "1",
        String differenceTime;//": "01h 55m"
        String arriDate;//": "2019-06-12T14:40:00",
        String departDate;//": "2019-06-12T13:40:00"

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
