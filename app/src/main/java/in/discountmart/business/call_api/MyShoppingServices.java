package in.discountmart.business.call_api;


import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.responsemodel.MyPurchaseDetailResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MyShoppingServices {
    //@POST("/Dtprocess.aspx")
    //Call<RepurchaseProductResponse> fetchRepurchaseProduct(@Body BaseRequest request);

    @POST("/ProcessAPIWithK")
    Call<MyPurchaseDetailResponse> fetchPurchaseDetail(@Body BaseRequest request);
}
