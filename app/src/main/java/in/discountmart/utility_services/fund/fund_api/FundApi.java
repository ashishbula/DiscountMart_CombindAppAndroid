package in.discountmart.utility_services.fund.fund_api;


import in.discountmart.utility_services.fund.fund_model.request.GatewayOrderIdRequest;
import in.discountmart.utility_services.fund.fund_model.request.TokenKeyRequest;
import in.discountmart.utility_services.fund.fund_model.response.GatwayOrderIdResponse;
import in.discountmart.utility_services.fund.fund_model.response.TokenKeyResponse;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FundApi {

    /* fund request*/
    @POST("/api/Admin/FundRequest")
    Call<BaseResponse> fundRequest(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /* Add fund */
    @POST("/api/Admin/FundRequest")
    Call<BaseResponse> fetchAddfund(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /*Get Wallet Type */
    @POST("/api/Wallet/GetWalletType")
    Call<BaseResponse> fetchWalletType(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /* Check fund available */
    @POST("/api/Wallet/CheckWallet")
    Call<BaseResponse> fetchCheckFund(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /*Get Debit/Deduct Amount Api*/
    @POST("/api/PaymentGetway/DeductAmount")
    Call<BaseResponse> fetchDebitAmount(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /*Get Credit Amount Api*/
    @POST("/api/PaymentGetway/CreditAmount")
    Call<BaseResponse> fetchCreditAmount(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /* Deduct Wallet fund */
    @POST("/api/Wallet/DeductWallet")
    Call<BaseResponse> fetchDeductFund(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /* Fund REquest Status api  */
    @POST("/api/Admin/FundRequestStatus")
    Call<BaseResponse> fetchFundReqStatus(@Body ApiRequest baseRequest, @Header("Token") String strToken);
    //Home/GeneratePaymentToken

    /* Get token key api  */
    @POST("/api/Home/GeneratePaymentToken")
    Call<TokenKeyResponse> fetchTokenKey(@Body TokenKeyRequest request);


    @POST("/v1/orders")
    Call<GatwayOrderIdResponse> fetchOrder(@Body GatewayOrderIdRequest request , @Header("Authorization") String str);

}
