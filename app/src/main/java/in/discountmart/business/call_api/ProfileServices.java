package in.discountmart.business.call_api;

import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.ChangePasswordRequest;
import in.discountmart.business.model_business.requestmodel.ChangeTransatoinPassREquest;
import in.discountmart.business.model_business.requestmodel.CheckUserNameRequest;
import in.discountmart.business.model_business.requestmodel.CheckValideSideRequest;
import in.discountmart.business.model_business.requestmodel.ForgotPassRequest;
import in.discountmart.business.model_business.requestmodel.NewJoinOtpRequest;
import in.discountmart.business.model_business.requestmodel.NewRegistrationRequest;
import in.discountmart.business.model_business.requestmodel.SetProfileRequest;
import in.discountmart.business.model_business.requestmodel.TestimonialRequest;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.model_business.responsemodel.DashboardResponse;
import in.discountmart.business.model_business.responsemodel.ForgotPasswordResponse;
import in.discountmart.business.model_business.responsemodel.GetMemberNameResponse;
import in.discountmart.business.model_business.responsemodel.GetProfileResponse;
import in.discountmart.business.model_business.responsemodel.LoginResponseEntity;
import in.discountmart.business.model_business.responsemodel.MemberRelationListResponse;
import in.discountmart.business.model_business.responsemodel.NewRegisterResponse;
import in.discountmart.business.model_business.responsemodel.StateListResponse;
import in.discountmart.business.model_business.responsemodel.UploadImageResponse;
import in.discountmart.business.model_business.responsemodel.WelcomeBannerResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ProfileServices {

    @POST("/ProcessAPIWithK")
    Call<WelcomeBannerResponse> fetchWelcomeBanner(@Body BaseRequest baseRequest);

    /*Login Api*/
    @POST("/ProcessAPIWithK")
    Call<LoginResponseEntity> fetchLogin(@Body BaseRequest baseRequest);

    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fetchOtp(@Body NewJoinOtpRequest baseRequest, @Header("apikey")String apikey);//

    /*For New Registration Api*/
    //for State List Api  (New Registration Activity)
    @POST("/ProcessAPIWithK")
    Call<StateListResponse> fetchStateList(@Body BaseRequest baseRequest, @Header("apikey")String apikey);

    //for Member Relation Api  (New Registration Activity)
    @POST("/ProcessAPIWithK")
    Call<MemberRelationListResponse> fetchMemberRelationList(@Body BaseRequest baseRequest, @Header("apikey")String apikey);

    //for MemberName Api  (New Registration Activity)
    @POST("/ProcessAPIWithK")
    Call<GetMemberNameResponse> fetchMemberName(@Body BaseRequest baseRequest, @Header("apikey")String apikey);

    //for check user name Api  (New Registration Activity)
    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fetchChecjUserName(@Body CheckUserNameRequest baseRequest, @Header("apikey")String apikey);

    //for NewRegistration Api  (New Registration Activity)
    @POST("/ProcessAPIWithK")
    Call<NewRegisterResponse> fetchNewRegistration(@Body NewRegistrationRequest registrationRequest, @Header("apikey")String apikey);

    /*Edit Profile*/
    //for GetProfile Detail Api  (Edit Profile Fragment)
    @POST("/ProcessAPIWithK")
    Call<GetProfileResponse> fetchGetProfileDetail(@Body BaseRequest baseRequest, @Header("apikey")String apikey);

    //for EditProfile  Api  (Edit Profile Fragment)
    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fetchEditProfileDetail(@Body SetProfileRequest baseRequest, @Header("apikey")String apikey);

    //ChangePassword Api   (ChangePassword Fragment)
    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fatchChangePassword(@Body ChangePasswordRequest changePasswordRequest, @Header("apikey")String apikey);

    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fatchCheckSide(@Body CheckValideSideRequest request, @Header("apikey")String apikey);

    //ChangeTransactionPassword Api   (ChangeTransactionPassword Fragment)
    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fatchChangeTransactionPassword(@Body ChangeTransatoinPassREquest changePasswordRequest, @Header("apikey")String apikey);

    //Forgot Password api
    @POST("/ProcessAPIWithK")
    Call<ForgotPasswordResponse> fetchForgotPassword(@Body ForgotPassRequest request, @Header("apikey")String apikey);

    //Testimonial  api
    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fetchTestimonial(@Body TestimonialRequest request);
    @POST("/ProcessAPIWithK")
    Call<DashboardResponse> fetchDashboardDetail(@Body BaseRequest request, @Header("apikey")String apikey);


    @Multipart
    @POST("/ProcessAPIWithK")
    Call<UploadImageResponse> fetchProfileImageMultipart(@Part MultipartBody.Part body1, @Part("myjson") RequestBody body, @Header("apikey")String apikey);
}
