package in.discountmart.utility_services.travel.hotel.call_hotel_api;



import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface HotelApi {

    /*HotelCityListActivity*/
    /*City List*/
    @POST("/api/City/GetHotelCityList")
    Call<BaseResponse> fetchHoteltCityList(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    @POST("/api/Hotel/SearchHotel")
    Call<BaseResponse> fetchHotelSearch(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    @POST("/api/Hotel/HotelInfo")
    Call<BaseResponse> fetchHotelInfo(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    @POST("/api/Hotel/HotelRoom")
    Call<BaseResponse> fetchHotelRoom(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /*Selected Hotel Room  book Margin Api*/
    @POST("/api/Admin/GetHotelMargin")
    Call<BaseResponse> fetchHotelRoomMargin(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /*Hotel Room Book Offline*/
    @POST("/api/Hotel/OfflineBookRoom")
    Call<BaseResponse> fetchHotelRoomBook(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    @POST("/api/Hotel/BlockHotel")
    Call<BaseResponse> fetchHotelRoomBlock(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /*Hotel Room Book Online*/
    @POST("/api/Hotel/BookRoom")
    Call<BaseResponse> fetchHotelRoomBookOnline(@Body ApiRequest baseRequest, @Header("Token") String strToken);

}
