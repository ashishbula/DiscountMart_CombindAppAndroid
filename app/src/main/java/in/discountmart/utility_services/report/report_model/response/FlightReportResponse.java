package in.discountmart.utility_services.report.report_model.response;

import java.io.Serializable;
import java.util.ArrayList;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class FlightReportResponse extends BaseResponse implements Serializable{

    String TotalCount;
    ArrayList<FlightReport>flightReport;

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }

    public ArrayList<FlightReport> getFlightReport() {
        return flightReport;
    }

    public void setFlightReport(ArrayList<FlightReport> flightReport) {
        this.flightReport = flightReport;
    }

    public static class FlightReport implements Serializable {
         String userName;//": "Admin0021",
        String distributor;//": "goldwing",
        String mobileNo;//": "9900530700",
        String origin;//": "JAI",
        String destination;//": "BLR",
        String passengerName;//": " Pukh Raj",
        String flightAmount;//": 3494,
        String publishedFare;//": 3494,
        String departureDate;//": "28-Apr-2021",
        String arrivalDate;//": "29-Apr-2021",
        String trnsactionDate;//": "08-Mar-2021",
        String status;//": "Booked",
        String transId;//": "ZHDIPI",
        String bookingId;//": null,
        String traceId;//": "8a80e0be-afc9-4ecf-b0ea-4e3650f31a9a",
        String IsLCC;//": null,
        String ID;//": 3689,
        String discount;//": 105
        String FlightCode;//": "6E",
        String FlightName;//": "Indigo",
        String NoOFPassenger;//": 1
        String DepartureTime;//": "19:30:00",
        String ArrivalTime;//": "21:15:00"
        String OriginCity;//":"Udaipur","
        String DestinationCity;//":"Mumbai"

        public String getFlightCode() {
            return FlightCode;
        }

        public void setFlightCode(String flightCode) {
            FlightCode = flightCode;
        }

        public String getFlightName() {
            return FlightName;
        }

        public void setFlightName(String flightName) {
            FlightName = flightName;
        }

        public String getNoOFPassenger() {
            return NoOFPassenger;
        }

        public void setNoOFPassenger(String noOFPassenger) {
            NoOFPassenger = noOFPassenger;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDistributor() {
            return distributor;
        }

        public void setDistributor(String distributor) {
            this.distributor = distributor;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
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

        public String getPassengerName() {
            return passengerName;
        }

        public void setPassengerName(String passengerName) {
            this.passengerName = passengerName;
        }

        public String getFlightAmount() {
            return flightAmount;
        }

        public void setFlightAmount(String flightAmount) {
            this.flightAmount = flightAmount;
        }

        public String getPublishedFare() {
            return publishedFare;
        }

        public void setPublishedFare(String publishedFare) {
            this.publishedFare = publishedFare;
        }

        public String getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(String departureDate) {
            this.departureDate = departureDate;
        }

        public String getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(String arrivalDate) {
            this.arrivalDate = arrivalDate;
        }

        public String getTrnsactionDate() {
            return trnsactionDate;
        }

        public void setTrnsactionDate(String trnsactionDate) {
            this.trnsactionDate = trnsactionDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTransId() {
            return transId;
        }

        public void setTransId(String transId) {
            this.transId = transId;
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public String getTraceId() {
            return traceId;
        }

        public void setTraceId(String traceId) {
            this.traceId = traceId;
        }

        public String getIsLCC() {
            return IsLCC;
        }

        public void setIsLCC(String isLCC) {
            IsLCC = isLCC;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDepartureTime() {
            return DepartureTime;
        }

        public void setDepartureTime(String departureTime) {
            DepartureTime = departureTime;
        }

        public String getArrivalTime() {
            return ArrivalTime;
        }

        public void setArrivalTime(String arrivalTime) {
            ArrivalTime = arrivalTime;
        }

        public String getOriginCity() {
            return OriginCity;
        }

        public void setOriginCity(String originCity) {
            OriginCity = originCity;
        }

        public String getDestinationCity() {
            return DestinationCity;
        }

        public void setDestinationCity(String destinationCity) {
            DestinationCity = destinationCity;
        }
    }
}
