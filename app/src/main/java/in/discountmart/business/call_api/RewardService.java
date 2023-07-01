package in.discountmart.business.call_api;



import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.responsemodel.RewardResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RewardService {

    //for Reward List Api  (Reward fragment)
    @POST("/ProcessAPIWithK")
    Call<RewardResponse> fetchReward(@Body BaseRequest baseRequest, @Header("apikey") String apikey);
}
