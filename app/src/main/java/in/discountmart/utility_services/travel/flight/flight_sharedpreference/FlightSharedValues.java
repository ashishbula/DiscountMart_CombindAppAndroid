package in.discountmart.utility_services.travel.flight.flight_sharedpreference;

import java.util.ArrayList;

import in.discountmart.utility_services.model.response_model.SendOtpResponse;
import in.discountmart.utility_services.model.response_model.VerifyOtpResponse;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PessengerInfo;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightSearchResponse;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.OwnwardFlightSearch;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.ReturnFlightSearch;

public class FlightSharedValues {
    private static FlightSharedValues sharedValues = null;

    /*** ***********Flight**************/
    public String flightDepatureTime = ""; //Departure Or Arrival
    public String flightRetDepatureTime = ""; //Departure Or Arrival
    public String flightArrivalTime = ""; //Departure Or Arrival
    public String flightRetArrivalTime = ""; //Departure Or Arrival
    public String flightOriginCityID = "";
    public String flightRetOriginCityID = "";
    public String flightDesinationCityID = "";
    public String flightRetDesinationCityID = "";
    public String flightOriginCityName = "";
    public String flightRetOriginCityName = "";
    public String flightDesinationCityName = "";
    public String flightRetDesinationCityName = "";
    public String flightBookOtp = "";
    public String flightBookOtpId = "";
    public int totAdults=0;
    public int totChilds=0;
    public int totInfants=0;
    public int totPessanger=0;
    public int totPaidAmount=0;
    public int totBaseFare=0;
    public int totTax=0;
    public int totDiscount=0;
    public int totOwnFlightDiscount=0;
    public int totRetFlightDiscount=0;
    public int flightCommsion=0;
    public int ownflightCommsion=0;
    public int retflightCommsion=0;
    public int flightMargin=0;
    public int ownflightMargin=0;
    public int retflightMargin=0;
    public int ownflightPrice=0;
    public int returnflightPrice=0;
    public int totflightPrice=0;
    public int jurneyType;

    public PessengerInfo pessengerInfo_shared=new PessengerInfo();

    /* Save Passenger Detail */
    public ArrayList<PessengerInfo> pessengerInfoArrayList=new ArrayList<PessengerInfo>();
    public ArrayList<PessengerInfo> pessengerFooterArrayList=new ArrayList<PessengerInfo>();

    /*Select Flight List*/
    public ArrayList<FlightSearchResponse> flightSelectArrayList=new ArrayList<FlightSearchResponse>();
     public FlightSearchResponse flightSearchResponse= new FlightSearchResponse();

     public OwnwardFlightSearch selectedOwnwardFlight=new OwnwardFlightSearch();
     public ReturnFlightSearch selectedReturnFlight=new ReturnFlightSearch();
    public ArrayList<OwnwardFlightSearch> selectedOwnFlightList=new ArrayList<OwnwardFlightSearch>();
    public ArrayList<ReturnFlightSearch> selectedReturnFlightList=new ArrayList<ReturnFlightSearch>();


     public VerifyOtpResponse flightOtp=new VerifyOtpResponse();
     public SendOtpResponse sendOtpResponse=new SendOtpResponse();

    public String Flight_Way = "";



    /*** ***********end**************/

    public static FlightSharedValues getInstance() {
        if (sharedValues == null)
            sharedValues = new FlightSharedValues();
        return sharedValues;
    }
}


