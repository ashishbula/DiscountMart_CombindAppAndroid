package in.discountmart.utility_services.travel.bus.call_bus_api;

import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface BusServiceApi {

    /*City List*/
    @POST("/api/Bus/GetFromCity")
    Call<BaseResponse> fetchBusFromCityList(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /*City List*/
    @POST("/api/Bus/GetTOCity")
    Call<BaseResponse> fetchBusToCityList(@Body ApiRequest baseRequest, @Header("Token") String strToken);



    @POST("/api/Bus/SearchBus")
    Call<BaseResponse> fetchBusSearch(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    @POST("/api/Bus/SeatDetail")
    Call<BaseResponse> fetchBusSeats(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /*Selected Bus book Margin Api*/
    @POST("/api/Admin/GetBusDiscount")
    Call<BaseResponse> fetchBusMargin(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /*Selected Bus book  Api*/
    @POST("/api/Bus/BookSeat")
    Call<BaseResponse> fetchBusBook(@Body ApiRequest baseRequest, @Header("Token") String strToken);



}
