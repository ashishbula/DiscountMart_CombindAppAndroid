package in.discountmart.business.call_api;


import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.PincodeDetailRequest;
import in.discountmart.business.model_business.requestmodel.SaveKvcRequest;
import in.discountmart.business.model_business.requestmodel.UpdateAddressProofRequest;
import in.discountmart.business.model_business.requestmodel.UpdateBankProofRequest;
import in.discountmart.business.model_business.requestmodel.UploadPanCardRequest;
import in.discountmart.business.model_business.responsemodel.AccountTypeListResponse;
import in.discountmart.business.model_business.responsemodel.BankListResponse;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.model_business.responsemodel.GetAddressProofResponse;
import in.discountmart.business.model_business.responsemodel.GetBankProofResponse;
import in.discountmart.business.model_business.responsemodel.GetPanCardDetailResponse;
import in.discountmart.business.model_business.responsemodel.IdProofListResponse;
import in.discountmart.business.model_business.responsemodel.KycDetailResponse;
import in.discountmart.business.model_business.responsemodel.KycImageResponse;
import in.discountmart.business.model_business.responsemodel.PincodeDetailRespose;
import in.discountmart.business.model_business.responsemodel.UploadImageResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadKYCServices {
/*Address Proof Fragment*/
    //for IDProof List Api  (AddressProof Fragment)
    @POST("/ProcessAPIWithK")
    Call<IdProofListResponse> fetchIdProofList(@Body BaseRequest baseRequest, @Header("apikey")String apikey);


    //Pincode Area Detail API
    @POST("/ProcessAPIWithK")
    Call<PincodeDetailRespose> fetchPincodeAreaDetail(@Body PincodeDetailRequest pincodeDetailRequest, @Header("apikey")String apikey);

    //for GetAddressDetail  Api  (AddressProof Fragment)
    @POST("/ProcessAPIWithK")
    Call<GetAddressProofResponse> fetchAddressDetail(@Body BaseRequest baseRequest, @Header("apikey")String apikey);

    //for Update Address Proof Detail Api (Without Image)(AddressProof Fragment)
    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fetchUpdateAddress(@Body UpdateAddressProofRequest request, @Header("apikey")String apikey);

    //For Update Address Proof Detail api (With Image)
    //@Multipart
    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fetchUpdateAddressWithImage(@Body RequestBody request, @Header("apikey")String apikey);

/*Bank Proof Fragment*/

    //for Account Type List Api  (BankProog Fragment)
    @POST("/ProcessAPIWithK")
    Call<AccountTypeListResponse> fetchAccountTypeList(@Body BaseRequest baseRequest, @Header("apikey")String apikey);

    //for  BankList Api
    @POST("/ProcessAPIWithK")
    Call<BankListResponse> fetchBanklist(@Body BaseRequest request, @Header("apikey")String apikey);
    //for GetBank Detail Api
    @POST("/ProcessAPIWithK")
    Call<GetBankProofResponse> fetchBankProofDetail(@Body BaseRequest baseRequest, @Header("apikey")String apikey);

    //for Update Bank Proof Detail api (without image)
    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fetchUpdateBankProofDetail(@Body UpdateBankProofRequest baseRequest, @Header("apikey")String apikey);

    //Update Bank Proof Detail Api  (With Image)
    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fetchUpdateBankProofWithImage(@Body RequestBody request, @Header("apikey")String apikey);

    //Update Bank Proof Detail api (Using Mulitpart With Image)
    @Multipart
    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fetchUpdateBankWithImageMultipart(@Part MultipartBody.Part body1, @Part("myjson") RequestBody body, @Header("apikey")String apikey);

/*Pan Card Fragment*/
    // for get Pan card Detail Api request and response
    @POST("/ProcessAPIWithK")
    Call<GetPanCardDetailResponse> fetchPanCardDetail(@Body BaseRequest request, @Header("apikey")String apikey);

    //Update Pan Card Detail api (Using Mulitpart With Image)
    @Multipart
    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fetchUpdatePanDetailWithImageMultipart(@Part MultipartBody.Part body1, @Part("myjson") RequestBody body, @Header("apikey")String apikey);

    //for Pan CardDetail api (without image)
    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fetchUpdatePanCardDetail(@Body UploadPanCardRequest baseRequest, @Header("apikey")String apikey);


    //Upload kyc image api
    @Multipart
    @POST("/ProcessAPIWithK")
    Call<KycImageResponse> fetchKycImage(@Part MultipartBody.Part body1, @Part("myjson") RequestBody body, @Header("apikey")String apikey);

    //for Get Kyc Detail Api
    @POST("/ProcessAPIWithK")
    Call<KycDetailResponse> fetchKycDetail(@Body BaseRequest baseRequest, @Header("apikey")String apikey);

    //for Save Kyc Detail Api
    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fetchSaveKyc(@Body SaveKvcRequest baseRequest, @Header("apikey")String apikey);

    //Upload Upload image api
    @Multipart
    @POST("/ProcessAPIWithK")
    Call<UploadImageResponse> fetchUploadImage(@Part MultipartBody.Part body1, @Part("myjson") RequestBody body, @Header("apikey") String apikey);




}
