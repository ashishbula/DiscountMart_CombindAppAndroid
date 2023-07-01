package in.discountmart.business.call_api;

import in.discountmart.business.model_business.requestmodel.BaseFromToRequest;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.ComplaintReplyRequest;
import in.discountmart.business.model_business.requestmodel.ComplaintRequest;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.model_business.responsemodel.ComplaintDetailItemViewResponse;
import in.discountmart.business.model_business.responsemodel.ComplaintDetailResponse;
import in.discountmart.business.model_business.responsemodel.ComplaintTypeListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ComplaintServices {

    //for Complaint List Detail Api
    @POST("/ProcessAPIWithK")
    Call<ComplaintTypeListResponse> fetchComplaintList(@Body BaseRequest baseRequest, @Header("apikey") String apikey);

    //for Complaint  Detail Api
    @POST("/ProcessAPIWithK")
    Call<BaseResponse> fetchComplaint(@Body ComplaintRequest complaintRequest, @Header("apikey") String apikey);

    //for Complaint  Detail Api
    @POST("/ProcessAPIWithK")
    Call<ComplaintDetailResponse> fetchComplaintDetail(@Body BaseFromToRequest complaintRequest, @Header("apikey") String apikey);

    //for Complaint  Reply Api
    @POST("/ProcessAPIWithK")
    Call<ComplaintDetailItemViewResponse> fetchComplaintDetailView(@Body ComplaintReplyRequest complaintRequest, @Header("apikey") String apikey);
}
