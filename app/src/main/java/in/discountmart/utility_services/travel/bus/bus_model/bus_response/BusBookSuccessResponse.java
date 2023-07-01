package in.discountmart.utility_services.travel.bus.bus_model.bus_response;

import java.io.Serializable;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class BusBookSuccessResponse extends BaseResponse implements Serializable {

     String Travels;//": "TESTING ACCOUNT",
    String BusType;//": "Espano Divo A/C Seater Air Suspension (1+1)",
    String DepartureDateTime;//": "9:0 PM",
    String ArrivateDateTime;//": "11:0 PM",
    String Source;//": "Mysore",
    String Destination;//": "Bangalore",
    String PNR;//": "94XJEMZ8",
    String BookingID;//": "94XJEMZ8",
    String TotalAmount;//": null,
    String BookedDate;//": null,
    String Status;//": "BOOKED"

    public String getTravels() {
        return Travels;
    }

    public void setTravels(String travels) {
        Travels = travels;
    }

    public String getBusType() {
        return BusType;
    }

    public void setBusType(String busType) {
        BusType = busType;
    }

    public String getDepartureDateTime() {
        return DepartureDateTime;
    }

    public void setDepartureDateTime(String departureDateTime) {
        DepartureDateTime = departureDateTime;
    }

    public String getArrivateDateTime() {
        return ArrivateDateTime;
    }

    public void setArrivateDateTime(String arrivateDateTime) {
        ArrivateDateTime = arrivateDateTime;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getPNR() {
        return PNR;
    }

    public void setPNR(String PNR) {
        this.PNR = PNR;
    }

    public String getBookingID() {
        return BookingID;
    }

    public void setBookingID(String bookingID) {
        BookingID = bookingID;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getBookedDate() {
        return BookedDate;
    }

    public void setBookedDate(String bookedDate) {
        BookedDate = bookedDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
