package in.discountmart.utility_services.travel.bus.bus_sharedprference;

import java.util.ArrayList;

import in.discountmart.utility_services.travel.bus.bus_model.Boarding_DropModel;
import in.discountmart.utility_services.travel.bus.bus_model.BusPassengerModel;
import in.discountmart.utility_services.travel.bus.bus_model.BusSeatModel;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusSearchListResponse;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusSeatsResponse;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PessengerInfo;

public class BusSharedValues {

    private static BusSharedValues sharedValues = null;

    /*** ***********Bus**************/
    public String busDepatureTime = ""; //Departure Or Arrival
    public String busDepatureDate = ""; //Departure Or Arrival
    public String busDepDate = ""; //Departure Or Arrival
    public String busArrivalTime = ""; //Departure Or Arrival
    public String busFromCityID = "";
    public String busFromCityName = "";
    public String busToCityID = "";
    public String busToCityName = "";
    public String busTripId = "";
    public String busDropPoint = "";
    public String busBoardPoint = "";
    public String busTravelName = "";
    public String busType = "";
    public String busPromocode = "";
    public String busPromoAmount = "";
    public String busLadiesSeat = "";

    public String busBookOtp = "";
    public String busBookOtpId = "";
    public int totPaidAmount=0;
    public int totDiscount=0;
    public int busCommsion=0;
    public int busMargin=0;
    public double TotalFare = 0;

    public PessengerInfo pessengerInfo_shared=new PessengerInfo();

    /* Save Passenger Detail */
    public ArrayList<BusPassengerModel> buspessengerInfoList=new ArrayList<BusPassengerModel>();
    public ArrayList<BusPassengerModel> pessengerFooterArrayList=new ArrayList<BusPassengerModel>();

    /*Select Flight List*/
    public ArrayList<BusSearchListResponse> busSelectArrayList=new ArrayList<BusSearchListResponse>();
    public ArrayList<BusSearchListResponse.BoardingPoints> boardPointArrayList=new ArrayList<BusSearchListResponse.BoardingPoints>();
    public ArrayList<BusSearchListResponse.DropingPoints> dropPointArrayList=new ArrayList<BusSearchListResponse.DropingPoints>();
    public ArrayList<BusSeatsResponse.BusSeats> busSeatsArrayList=new ArrayList<BusSeatsResponse.BusSeats>();
    public ArrayList<BusSeatsResponse.BusSeats> busSeatsLowerList=new ArrayList<BusSeatsResponse.BusSeats>();
    public ArrayList<BusSeatsResponse.BusSeats> busSeatsUpperList=new ArrayList<BusSeatsResponse.BusSeats>();

    public ArrayList<BusSeatModel> busSeatModelArrayList=new ArrayList<BusSeatModel>();
    public ArrayList<Boarding_DropModel> dropModelArrayList=new ArrayList<Boarding_DropModel>();
    public BusSearchListResponse busSearchResponse= new BusSearchListResponse();

    public ArrayList<String> SelectedSeatList = new ArrayList<>(); // selected seat detail
    public ArrayList<String> SelectedSeatFare = new ArrayList<>(); // selected seat detail
    public ArrayList<String> SelectedSeatType = new ArrayList<>(); // selected seat detail
    public ArrayList<String> selectTravelsList = new ArrayList<>(); // selected Travel detail
    public ArrayList<String> selectBordPointList = new ArrayList<>(); // selected Boarding Point detail
    public ArrayList<String> selectDropPointList = new ArrayList<>(); // selected Drop Point detail




    /*** ***********end**************/

    public static BusSharedValues getInstance() {
        if (sharedValues == null)
            sharedValues = new BusSharedValues();
        return sharedValues;
    }
}
