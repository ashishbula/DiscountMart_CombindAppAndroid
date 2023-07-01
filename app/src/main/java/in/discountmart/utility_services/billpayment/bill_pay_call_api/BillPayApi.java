package in.discountmart.utility_services.billpayment.bill_pay_call_api;


import in.discountmart.utility_services.billpayment.billpay_model.billpay_response_model.UploadDocumentResponse;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface BillPayApi {

    @POST("/api/Bill/GetBillService")
    Call<BaseResponse> fetchBillPayServiceList(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    @POST("/api/Bill/GetBillServiceParamemter")
    Call<BaseResponse> fetchBillPayParameter(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    @POST("/api/Bill/SaveBillPayment")
    Call<BaseResponse> fetchBillPayment(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    @Multipart
    @POST("/handler/fileupload.ashx")
    Call<UploadDocumentResponse> fetchUploadImageMultipart(@Part MultipartBody.Part body1, @Part("name") RequestBody body);



}
