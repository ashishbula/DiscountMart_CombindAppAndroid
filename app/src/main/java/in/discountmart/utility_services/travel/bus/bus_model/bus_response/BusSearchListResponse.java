package in.discountmart.utility_services.travel.bus.bus_model.bus_response;

import java.io.Serializable;
import java.util.ArrayList;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class BusSearchListResponse extends BaseResponse implements Serializable {
     String Travels;//": "Gujarat travels",
    String BusType;//": null,
    String DepartureDateTime;//": "6:50 AM",
    String ArrivateDateTime;//": "6:50 AM",
    String AvailableSeats;//": "35",
    String Fare;//": 0,
    String FareText;//": null,
    String TripID;//": "1000282931220980464",
    String AvailableTrips_id;//": "0",
    String Sleeper;//": true,
    String Seater;//": true,
    String AC;//": true,
    String NonAc;//": false,
    ArrayList<CancellationCharge> CancelCharge;
    ArrayList<BoardingPoints>boardingPoint;
    ArrayList<DropingPoints>droppingPoint;

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

    public String getAvailableSeats() {
        return AvailableSeats;
    }

    public void setAvailableSeats(String availableSeats) {
        AvailableSeats = availableSeats;
    }

    public String getFare() {
        return Fare;
    }

    public void setFare(String fare) {
        Fare = fare;
    }

    public String getFareText() {
        return FareText;
    }

    public void setFareText(String fareText) {
        FareText = fareText;
    }

    public String getTripID() {
        return TripID;
    }

    public void setTripID(String tripID) {
        TripID = tripID;
    }

    public String getAvailableTrips_id() {
        return AvailableTrips_id;
    }

    public String getSleeper() {
        return Sleeper;
    }

    public void setSleeper(String sleeper) {
        Sleeper = sleeper;
    }

    public String getSeater() {
        return Seater;
    }

    public void setSeater(String seater) {
        Seater = seater;
    }

    public String getAC() {
        return AC;
    }

    public void setAC(String AC) {
        this.AC = AC;
    }

    public String getNonAc() {
        return NonAc;
    }

    public void setNonAc(String nonAc) {
        NonAc = nonAc;
    }

    public void setAvailableTrips_id(String availableTrips_id) {
        AvailableTrips_id = availableTrips_id;
    }

    public ArrayList<CancellationCharge> getCancelCharge() {
        return CancelCharge;
    }

    public void setCancelCharge(ArrayList<CancellationCharge> cancelCharge) {
        CancelCharge = cancelCharge;
    }

    public ArrayList<BoardingPoints> getBoardingPoint() {
        return boardingPoint;
    }

    public void setBoardingPoint(ArrayList<BoardingPoints> boardingPoint) {
        this.boardingPoint = boardingPoint;
    }

    public ArrayList<DropingPoints> getDroppingPoint() {
        return droppingPoint;
    }

    public void setDroppingPoint(ArrayList<DropingPoints> droppingPoint) {
        this.droppingPoint = droppingPoint;
    }

    public static class CancellationCharge implements Serializable{

        String CancelTime;//": "Between 12 hours and 0 hours before journey time",
        String CancelCharge;//": "85.00%"

        public String getCancelTime() {
            return CancelTime;
        }

        public void setCancelTime(String cancelTime) {
            CancelTime = cancelTime;
        }

        public String getCancelCharge() {
            return CancelCharge;
        }

        public void setCancelCharge(String cancelCharge) {
            CancelCharge = cancelCharge;
        }
    }

    public static class BoardingPoints implements Serializable{
        String Addresss;//": "Gujarat Travels,Shop No.2, Opp. MetroPiller No.176, Polovictory,Station Road, Jaipur 0141-2201104-2201106",
        String BpID;//": "787007",
        String BpName;//": "Gujarat travels,shop no.2, opp. Metropiller no.176, polovictory,station road (pickup by van) ",
        String ContactNumber;//": "9214122200,9214133300,0141-2201104,2201106",
        String LandMark;//": "Station road",
        String Location;//": null,
        String Prime;//": false,
        String Time;//": null,
        String BoardingTime;//": "3:30 PM"

        public String getAddresss() {
            return Addresss;
        }

        public void setAddresss(String addresss) {
            Addresss = addresss;
        }

        public String getBpID() {
            return BpID;
        }

        public void setBpID(String bpID) {
            BpID = bpID;
        }

        public String getBpName() {
            return BpName;
        }

        public void setBpName(String bpName) {
            BpName = bpName;
        }

        public String getContactNumber() {
            return ContactNumber;
        }

        public void setContactNumber(String contactNumber) {
            ContactNumber = contactNumber;
        }

        public String getLandMark() {
            return LandMark;
        }

        public void setLandMark(String landMark) {
            LandMark = landMark;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String location) {
            Location = location;
        }

        public String getPrime() {
            return Prime;
        }

        public void setPrime(String prime) {
            Prime = prime;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time = time;
        }

        public String getBoardingTime() {
            return BoardingTime;
        }

        public void setBoardingTime(String boardingTime) {
            BoardingTime = boardingTime;
        }
    }
    public static class DropingPoints implements Serializable{

        String Addresss;//": "Gujarat travels 7 mv house hajipur garden shahibuagh ",
        String BpID;//": "18841712",
        String BpName;//": "Others",
        String ContactNumber;//": "",
        String LandMark;//": "Gujarat travels 7 mv house hajipur garden shahibuagh ",
        String Location;//": null,
        String Prime;//": false,
        String  Time;//": null,
        String DroppingTime;//": "6:5 AM"

        public String getAddresss() {
            return Addresss;
        }

        public void setAddresss(String addresss) {
            Addresss = addresss;
        }

        public String getBpID() {
            return BpID;
        }

        public void setBpID(String bpID) {
            BpID = bpID;
        }

        public String getBpName() {
            return BpName;
        }

        public void setBpName(String bpName) {
            BpName = bpName;
        }

        public String getContactNumber() {
            return ContactNumber;
        }

        public void setContactNumber(String contactNumber) {
            ContactNumber = contactNumber;
        }

        public String getLandMark() {
            return LandMark;
        }

        public void setLandMark(String landMark) {
            LandMark = landMark;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String location) {
            Location = location;
        }

        public String getPrime() {
            return Prime;
        }

        public void setPrime(String prime) {
            Prime = prime;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time = time;
        }

        public String getDroppingTime() {
            return DroppingTime;
        }

        public void setDroppingTime(String droppingTime) {
            DroppingTime = droppingTime;
        }
    }
}
