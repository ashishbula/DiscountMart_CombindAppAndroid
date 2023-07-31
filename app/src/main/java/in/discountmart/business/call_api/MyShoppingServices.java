package in.discountmart.business.call_api;


import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.GetProductRequest;
import in.discountmart.business.model_business.responsemodel.MyPurchaseDetailResponse;

import in.discountmart.business.model_business.responsemodel.RepurchaseProductResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MyShoppingServices {
    //@POST("/Dtprocess.aspx")
    //Call<RepurchaseProductResponse> fetchRepurchaseProduct(@Body BaseRequest request);
    @POST("/ProcessAPIWithK")
    Call<RepurchaseProductResponse> fetchIDActivationProduct(@Body GetProductRequest request, @Header("apikey")String apikey);

    @POST("/ProcessAPIWithK")
    Call<MyPurchaseDetailResponse> fetchPurchaseDetail(@Body BaseRequest request);
}
