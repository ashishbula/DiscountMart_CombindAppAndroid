package in.discountmart.business.call_api;

import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.responsemodel.WelcomeLetterResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface DocumentService {

    /*WelcomeLetter Api*/
    @POST("/ProcessAPIWithK")
    Call<WelcomeLetterResponse> fetchWelcomeLetter(@Body BaseRequest baseRequest, @Header("apikey")String apikey);

    //@Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}
