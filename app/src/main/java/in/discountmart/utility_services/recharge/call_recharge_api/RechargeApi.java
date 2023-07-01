package in.discountmart.utility_services.recharge.call_recharge_api;

import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.recharge.recharge_model.recharge_request_model.CircleRequest;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.GetOperatorResponse;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface RechargeApi {

    @POST("/api/Recharge/GetService")
    Call<BaseResponse> fetchServiceList(@Body ApiRequest baseRequest, @Header("Token") String strToken);

   // @POST("/api/Recharge/GenerateRequest") // prepaid
    @POST("/api/Recharge/Recharge") // prepaid
    Call<BaseResponse> fetchRechargeRequest(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    @POST("/api/Recharge/Recharge") // postpaid
    Call<BaseResponse> fetchRechargeRequest_postpaid(@Body ApiRequest baseRequest, @Header("Token") String strToken);


    @POST("/api/Recharge//GetPOSTPAIDFETCHBILL")
    Call<BaseResponse> fetchCheckPostpaidIsbbpsRequest(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    @GET
    Call<GetOperatorResponse> fetchOperator(@Url String url);

    @POST("/api/Recharge/GetLocation")
    Call<BaseResponse> fetchCircleList(@Body CircleRequest request);
//

    @POST("/api/Recharge/GetCircle")
    Call<BaseResponse> fetchCircleListForPrepaid(@Body CircleRequest request);

    @POST("/api/Recharge/GetRechargePlan")
    Call<BaseResponse> fetchRechargePlan(@Body ApiRequest request, @Header("Token") String strToken);

 @POST("/api/Recharge/GetRechargePlanDetail")
 Call<BaseResponse> fetchRechargePlanDetail(@Body ApiRequest request, @Header("Token") String strToken);
 @POST("/api/Recharge/GetRechargePlanDetail")
 Single<BaseResponse> fetchRechargePlanData(@Body ApiRequest request, @Header("Token") String strToken);

 @POST("/api/Recharge/GetRechargePlanDetail")
 Observable<BaseResponse> getNewsData();
}
