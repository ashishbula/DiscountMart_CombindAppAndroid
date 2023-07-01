package in.discountmart.utility_services.travel.flight.flight_model.flight_response_model;


import java.io.Serializable;
import java.util.ArrayList;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class FlightBookSuccessResponse extends BaseResponse implements Serializable {

     String formNo;//": "1002",
    String bookingID;//": "1458758",
    String pnrNo;//": "HIBHGN",
    String origin;//": "JAI",
    String destination;//": "IXC",
    String bookingDate;//": "4/19/2019 11:56:03 AM",
    String departureDate;//": "5/11/2019 10:40:00 AM",
    String arrivalDate;//": "5/11/2019 11:50:00 AM",
    String flightNumber;//": null,
    String airlineCode;//": null,
    String totalAmount;//": "5183.00",
    ArrayList<PassengerDetail> passanger;

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getPnrNo() {
        return pnrNo;
    }

    public void setPnrNo(String pnrNo) {
        this.pnrNo = pnrNo;
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

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
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

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ArrayList<PassengerDetail> getPassanger() {
        return passanger;
    }

    public void setPassanger(ArrayList<PassengerDetail> passanger) {
        this.passanger = passanger;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public static class PassengerDetail implements Serializable {

        String Title;//": "Mr",
        String FirstName;//": "amit",
        String LastName;//": "Mr",
        String PaxType;//": 0,
        String DateOfBirth;//": null,
        String Gender;//": 0,
        String Age;//": 0,
        String PassportNo;//": null,
        String PassportExpiry;//": null,
        String AddressLine1;//": null,
        String AddressLine2;//": null,
        String Fare;//": null,
        String City;//": null,
        String CountryCode;//": null,
        String CountryName;//": null,
        String Nationality;//": null,
        String ContactNo;//": null,
        String Email;//": null,
        String IsLeadPax;//": false,
        String FFAirlineCode;//": null,
        String FFNumber;//": null,
        String GSTCompanyAddress;//": null,
        String GSTCompanyContactNumber;//": null,
        String GSTCompanyName;//": null,
        String GSTNumber;//": null,
        String GSTCompanyEmail;//": null

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String firstName) {
            FirstName = firstName;
        }

        public String getLastName() {
            return LastName;
        }

        public void setLastName(String lastName) {
            LastName = lastName;
        }

        public String getPaxType() {
            return PaxType;
        }

        public void setPaxType(String paxType) {
            PaxType = paxType;
        }

        public String getDateOfBirth() {
            return DateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            DateOfBirth = dateOfBirth;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }
    }

}
