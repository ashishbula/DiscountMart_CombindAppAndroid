package in.discountmart.utility_services.travel.utility_cab.cab_sharedpreferance;

import java.util.ArrayList;

import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PessengerInfo;
import in.discountmart.utility_services.travel.utility_cab.cab_model.CabPassengerModel;
import in.discountmart.utility_services.travel.utility_cab.cab_model.cab_response_model.CabSearchResponse;

public class CabSharedValues {

    private static CabSharedValues sharedValues = null;

    /*** ***********Cab**************/
    public String cabDepartureDate = ""; //Departure Or Arrival
    public String cabDepartureTime = ""; //Departure Or Arrival
    public String cabArrivalDate = ""; //Departure Or Arrival
    public String cabArrivalTime = ""; //Departure Or Arrival
    public String cabFromCityID = "";
    public String cabFromCityName = "";
    public String cabToCityID = "";
    public String cabToCityName = "";

    public String cabName = "";
    public String cabType = "";
    public String cabPromocode = "";
    public String cabPromoAmount = "";
    public String busLadiesSeat = "";

    public String cabBookOtp = "";
    public String cabBookOtpId = "";
    public double totPaidAmount=0;
    public int totKm=0;
    public int perKmRate=0;
    public int totDiscount=0;
    public int cabCommsion=0;
    public int cabMargin=0;
    public double TotalFare = 0;

    public PessengerInfo pessengerInfo_shared=new PessengerInfo();

    /* Save Passenger Detail */
    public ArrayList<CabPassengerModel> cabPessengerInfoList=new ArrayList<CabPassengerModel>();
    public ArrayList<CabPassengerModel> pessengerFooterArrayList=new ArrayList<CabPassengerModel>();

    /*Select Flight List*/
    public ArrayList<CabSearchResponse> cabSelectArrayList=new ArrayList<CabSearchResponse>();

    /*** ***********end**************/

    public static CabSharedValues getInstance() {
        if (sharedValues == null)
            sharedValues = new CabSharedValues();
        return sharedValues;
    }
}
