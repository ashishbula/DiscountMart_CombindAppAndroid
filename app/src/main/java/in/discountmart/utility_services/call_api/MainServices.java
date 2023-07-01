package in.discountmart.utility_services.call_api;



import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.LoginRequest;
import in.discountmart.utility_services.model.request_model.TokenRequest;
import in.discountmart.utility_services.model.request_model.VersionRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.PromoVerifyResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MainServices {
    @POST("/api/Admin/GetVersionNo")
    Call<BaseResponse> fetchVersion(@Body VersionRequest baseRequest, @Header("Token") String strToken);


    @POST("/api/Login/UserLogin")
    Call<BaseResponse> fetchLogin(@Body LoginRequest baseRequest, @Header("Token") String strToken);

    @POST("/api/Login/GetToken")
    Call<BaseResponse> fetchToken(@Body TokenRequest baseRequest, @Header("Token") String strToken);//GetToken

    /*Get Main Wallet Balance Api*/
    @POST("/api/Admin/GetUserBalance")
    Call<BaseResponse> fetchGetBalance(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /*Get Main Wallet Balance Api*/
    @POST("/api/PromoCode/GetPromoCode")
    Call<BaseResponse> fetchPromocode(@Body ApiRequest baseRequest, @Header("Token") String strToken);


    @GET("/handler/City.ashx")
    Call<PromoVerifyResponse> fetchPromoVerify(@Query("formNo") String fromno, @Query("service") String serviceId, @Query("action") String action);

    //for  Wallet Api  (BankWithdrawal Fragment)
//    @POST("/processapi.aspx")
//    Call<GetWalleBalanceResponse> fetchMWalletBusinessBalance(@Body GetWalletBalanceRequest request);
//    //for  Wallet Api  (BankWithdrawal Fragment)
//    @POST("/processapi.aspx")
//    Call<GetWalleBalanceResponse> fetchSWalletBusinessBalance(@Body GetWalletBalanceRequest request);

}
