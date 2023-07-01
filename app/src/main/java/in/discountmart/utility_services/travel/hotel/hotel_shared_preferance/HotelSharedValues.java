package in.discountmart.utility_services.travel.hotel.hotel_shared_preferance;

import java.util.ArrayList;

import in.discountmart.utility_services.travel.hotel.hotel_model.HotelPassengerFooterModel;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelPassengerModel;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelRoomModel;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.BlockRoomDetail;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelAttraction;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelDetail;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelError;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelInfo;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelInfoResponse;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelRoomCombination;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelRoomResponse;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.RoomDetail;

public class HotelSharedValues {
    private static HotelSharedValues sharedValues = null;

    public int totMember=0;
    public int totChild=0;
    public int totAdult=0;
    public int totRoom=0;
    public int totAmount=0;
    public int totDiscount=0;
    public int totPaidAmount=0;
    public int Discount=0;
    public int margin=0;
    public int perRoomAmount=0;

    public String chkInDate="";
    public String chkOutDate="";
    public String city="";
    public String citycode="";
    public String countryCode="";
    public String hotelCode="";
    public String hotelRating="";
    public String hotelName="";
    public String traceID="";
    public String resultIndex="";
    public String roomIndex="";
    public String roomName="";
    public String hotelPromocode = "";
    public String hotelDiscount = "";
    public String hotelPromoAmount = "";
    public String hotelBookOtpID = "";
    public String hotelBookOtp = "";

    /*Save Hotel Room info in list*/
   public ArrayList<HotelRoomModel> hotelRoomInfoListShared=new ArrayList<HotelRoomModel>();

   public HotelInfoResponse hotelInfoResponse=new HotelInfoResponse();
   public HotelRoomResponse hotelRoomResponse=new HotelRoomResponse();
    public RoomDetail roomDetail=new RoomDetail();
   public HotelRoomCombination roomCombination=new HotelRoomCombination();
    public HotelInfo hotelInfo=new HotelInfo();
    public HotelError hotelError=new HotelError();
    public HotelDetail hotelDetail=new HotelDetail();
    public HotelAttraction hotelAttraction=new HotelAttraction();
   public ArrayList<HotelAttraction> hotelAttractionArrayList=new ArrayList<HotelAttraction>();
   public ArrayList<RoomDetail> roomDetailList=new ArrayList<RoomDetail>();
   public ArrayList<BlockRoomDetail> blockroomDetailList=new ArrayList<BlockRoomDetail>();
    public ArrayList<String> facilityList=new ArrayList<String>();
    public ArrayList<String> hotelImages=new ArrayList<>();
    public ArrayList<String> hotelSelectNameList=new ArrayList<>();
    public ArrayList<HotelPassengerModel> hotelPassengerModelArrayList=new ArrayList<HotelPassengerModel>();
    public ArrayList<HotelPassengerFooterModel> passengerFooterList=new ArrayList<HotelPassengerFooterModel>();




    public static HotelSharedValues getInstance() {
        if (sharedValues == null)
            sharedValues = new HotelSharedValues();
        return sharedValues;
    }
}
