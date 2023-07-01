package in.discountmart.business.call_api;


import in.discountmart.business.model_business.requestmodel.BaseFromToRequest;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.GetWalletBalanceRequest;
import in.discountmart.business.model_business.requestmodel.IDActivationBalance;
import in.discountmart.business.model_business.requestmodel.MainToShopWalletTransferRequest;
import in.discountmart.business.model_business.requestmodel.MainWalletReportRequest;
import in.discountmart.business.model_business.requestmodel.WalletListRequest;
import in.discountmart.business.model_business.requestmodel.WalletReportRequest;
import in.discountmart.business.model_business.requestmodel.WalletReqRequest;
import in.discountmart.business.model_business.requestmodel.WalletRequestBankListRequest;
import in.discountmart.business.model_business.requestmodel.WalletTransferRequest;
import in.discountmart.business.model_business.requestmodel.WithdrawalRequest;
import in.discountmart.business.model_business.responsemodel.BankListResponse;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.model_business.responsemodel.BaseResponseEntity;
import in.discountmart.business.model_business.responsemodel.Dy_WalletReportReponse;
import in.discountmart.business.model_business.responsemodel.Dy_WalletRequesDetailResponse;
import in.discountmart.business.model_business.responsemodel.Dy_WithdrawalDetailResponse;
import in.discountmart.business.model_business.responsemodel.FundReqDetailResponse;
import in.discountmart.business.model_business.responsemodel.GetBankDetailResponse;
import in.discountmart.business.model_business.responsemodel.GetWalleBalanceResponse;
import in.discountmart.business.model_business.responsemodel.MainWalletReportResponse;
import in.discountmart.business.model_business.responsemodel.PayModeListResponse;
import in.discountmart.business.model_business.responsemodel.ToWalletListResponse;
import in.discountmart.business.model_business.responsemodel.WalletListResponse;
import in.discountmart.business.model_business.responsemodel.WalletReportResponse;
import in.discountmart.business.model_business.responsemodel.WalletReqDetailResponse;
import in.discountmart.business.model_business.responsemodel.WalletRequestDetail;
import in.discountmart.business.model_business.responsemodel.WithdrawalDetailResponse;
import in.discountmart.business.model_business.responsemodel.WithdrawlChargeResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface WalletServices {

    //for  Main Wallet Transaction Detail Api  (MainWalletReportFragment)
    @POST("/ProcessAPIWithK")
    Call<MainWalletReportResponse> fetchMainWalletReport(@Body MainWalletReportRequest request, @Header("apikey")String apikey);

    @POST("/ProcessAPIWithK")
    Call<WalletReportResponse> fetchWalletReport(@Body WalletReportRequest request, @Header("apikey")String apikey);

    @POST("/ProcessAPIWithK")
    Call<GetBankDetailResponse> fetchBankDetail(@Body BaseRequest request, @Header("apikey")String apikey);

    //for  BankList Api  (BankWithdrawal Fragment)
    @POST("/ProcessAPIWithK")
    Call<BankListResponse> fetchBanklist(@Body BaseRequest request, @Header("apikey")String apikey);

    //for  BankWithdrawl Api  (BankWithdrawal Fragment)
    @POST("/ProcessAPIWithK")
    Call<BaseResponseEntity> fetchBankWithdrawlRequest(@Body WithdrawalRequest request, @Header("apikey")String apikey);

    //for  Wallet Api  (BankWithdrawal Fragment)
    @POST("/ProcessAPIWithK")
    Call<GetWalleBalanceResponse> fetchWalletBalance(@Body GetWalletBalanceRequest request, @Header("apikey")String apikey);


    //for  ID Activation get Wallet balance Api  (For ID Activation)
    @POST("/ProcessAPIWithK")
    Call<GetWalleBalanceResponse> fetchIDActiveBalance(@Body IDActivationBalance request, @Header("apikey")String apikey);

    //for  BankWithdrawlReport Api  (BankWithdrawalReport Fragment)
    @POST("/ProcessAPIWithK")
    Call<FundReqDetailResponse> fetchFundRequestDetail(@Body BaseRequest request, @Header("apikey")String apikey);

    //for  BankWithdrawlRecharge Api  (BankWithdrawalReport Fragment/Activity)
    @POST("/ProcessAPIWithK")
    Call<WithdrawlChargeResponse> fetchWithdrawlCharge(@Body BaseRequest request, @Header("apikey")String apikey);


    //for  WalletRequestDetail Api  (WalletRequestDetailFragment)
    @POST("/ProcessAPIWithK")
    Call<WalletRequestDetail> fetchWalletRequestDetail(@Body BaseRequest request, @Header("apikey")String apikey);

    @POST("/ProcessAPIWithK")
    Call<WalletReqDetailResponse> fetchWalletReqDetail(@Body BaseFromToRequest request, @Header("apikey")String apikey);

    @POST("/ProcessAPIWithK")
    Call<BankListResponse> fetchWalletRequestBankList(@Body WalletRequestBankListRequest requrest, @Header("apikey")String apikey);

    //for  WalletRequest Api  (WalletRequestFragment)
    @POST("/ProcessAPIWithK")
    Call<BaseResponseEntity> fetchWalletRequest(@Body WalletReqRequest request, @Header("apikey")String apikey);

    @POST("/ProcessAPIWithK")
    Call<PayModeListResponse> fetchPaymodeList(@Body BaseRequest requrest, @Header("apikey")String apikey);

    @POST("/ProcessAPIWithK")
    Call<ToWalletListResponse> fetchTo_WalletList(@Body BaseRequest requrest, @Header("apikey")String apikey);


    @Multipart
    @POST("/ProcessAPIWithK")
    Call<BaseResponseEntity> fetchWalletRequestWithImage(@Part MultipartBody.Part body1, @Part("myjson") RequestBody body, @Header("apikey")String apikey);

    @POST("/ProcessAPIWithK")
    Call<WalletListResponse> fetchWalletList(@Body WalletListRequest requrest, @Header("apikey")String apikey);

    /* Main To Shopping Wallet Transfer  Api service*/
    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fetchWalletTransfer(@Body MainToShopWalletTransferRequest requrest, @Header("apikey")String apikey);

    /*Wallet Transfer  Api service*/
    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fetchWalletTransferNew(@Body WalletTransferRequest requrest, @Header("apikey")String apikey);

    //for  BankWithdrawlReport Api  (BankWithdrawalReport Fragment)
    @POST("/ProcessAPIWithK")
    Call<WithdrawalDetailResponse> fetchWithdrawalDetail(@Body BaseFromToRequest request, @Header("apikey")String apikey);

    @POST("/ProcessAPIWithK")
    Call<WalletListResponse> fetchWalletList2(@Body BaseRequest requrest,@Header("apikey") String apikey);
    @POST("/ProcessAPIWithK")
    Call<Dy_WalletReportReponse> fetchDynamicWalletReport(@Body WalletReportRequest request, @Header("apikey")String apikey);

    @POST("/ProcessAPIWithK")
    Call<Dy_WalletRequesDetailResponse> fetchDynamicWalletRequestDetail(@Body BaseFromToRequest request, @Header("apikey")String apikey);

    @POST("/ProcessAPIWithK")
    Call<Dy_WithdrawalDetailResponse> fetchDynamicWithdrawalDetail(@Body BaseFromToRequest request, @Header("apikey")String apikey);


}
