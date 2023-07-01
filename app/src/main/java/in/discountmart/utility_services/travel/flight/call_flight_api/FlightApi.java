package in.discountmart.utility_services.travel.flight.call_flight_api;


import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FlightApi {

    /*City List*/
    @POST("/api/City/GetCityList")
    Call<BaseResponse> fetchFlightCityList(@Body ApiRequest baseRequest, @Header("Token") String strToken);//Flight/SearchFlight


    @POST("/api/Flight/SearchFlight")
    Call<BaseResponse> fetchFlightSearch(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    @POST("/api/Flight/SearchReturnFlight")
    Call<BaseResponse> fetchFlightSearchReturn(@Body ApiRequest baseRequest, @Header("Token") String strToken);


    /*Selected Flight Margin Api*/
    @POST("/api/Admin/GetFlightMargin")
    Call<BaseResponse> fetchFlightMargin(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /*Selected Flight Book Send Otp*/
   @POST("/api/Admin/OTP")//http://utilitywebapi.bisplindia.in/api/Admin/OTP
   Call<BaseResponse> fetchFlightBookOtp(@Body ApiRequest baseRequest, @Header("Token") String strToekn);

    /*Selected Flight Book Verify Otp*/
    @POST("/api/Admin/VerifyOTP")
    Call<BaseResponse> fetchVerifyOtp(@Header("Token") String strToken, @Body ApiRequest baseRequest);


    /*Flight Book Api*/
    @POST("/api/Flight/BookFlight")
    Call<BaseResponse> fetchFlightBook(@Body ApiRequest baseRequest, @Header("Token") String strToken);
}
