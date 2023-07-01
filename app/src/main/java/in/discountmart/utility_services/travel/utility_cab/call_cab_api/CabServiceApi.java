package in.discountmart.utility_services.travel.utility_cab.call_cab_api;

import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CabServiceApi {


    /*City List*/
    @POST("/api/Cab/GetCabCity")
    Call<BaseResponse> fetchCabCityList(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /*City List*/
    @POST("/api/Bus/GetTOCity")
    Call<BaseResponse> fetchBusToCityList(@Body ApiRequest baseRequest, @Header("Token") String strToken);



    @POST("/api/Cab/GetSearchCab")
    Call<BaseResponse> fetchCabSearch(@Body ApiRequest baseRequest, @Header("Token") String strToken);


    /*Selected Cab Book Discount Api*/
    @POST("/api/Cab/getCabDisCount")
    Call<BaseResponse> fetchCabMargin(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /*Selected Cab book  Api*/
    @POST("/api/Cab/BookingCabDetail")
    Call<BaseResponse> fetchCabBook(@Body ApiRequest baseRequest, @Header("Token") String strToken);
}


