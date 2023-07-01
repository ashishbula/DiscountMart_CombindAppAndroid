package in.discountmart.business.call_api;


import in.discountmart.business.model_business.requestmodel.BaseFromToRequest;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.DownlineDetailRequest;
import in.discountmart.business.model_business.requestmodel.DownlinePurchaseRequest;
import in.discountmart.business.model_business.requestmodel.DownlineRankRequest;
import in.discountmart.business.model_business.requestmodel.DownlineStatusRequest;
import in.discountmart.business.model_business.requestmodel.IDActivationRequest;
import in.discountmart.business.model_business.requestmodel.LevelWiseCountRequest;
import in.discountmart.business.model_business.requestmodel.LevelWiseReportRequest;
import in.discountmart.business.model_business.requestmodel.MyDirectRequest;
import in.discountmart.business.model_business.requestmodel.MyPurchaseRequest;
import in.discountmart.business.model_business.requestmodel.OuterTeamReportRequest;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.model_business.responsemodel.DownlineDetailResponse;
import in.discountmart.business.model_business.responsemodel.DownlinePurchaseResponse;
import in.discountmart.business.model_business.responsemodel.DownlineStatusResponse;
import in.discountmart.business.model_business.responsemodel.Dy_CommunityDetailResponse;
import in.discountmart.business.model_business.responsemodel.Dy_DownlineDetailResponse;
import in.discountmart.business.model_business.responsemodel.Dy_DownlinePurchaseResponse;
import in.discountmart.business.model_business.responsemodel.Dy_MyDirectResponse;
import in.discountmart.business.model_business.responsemodel.Dy_MyRewardResponse;
import in.discountmart.business.model_business.responsemodel.Dy_UpgradeReportResponse;
import in.discountmart.business.model_business.responsemodel.GeneologyResponse;
import in.discountmart.business.model_business.responsemodel.IDActivePackageList;
import in.discountmart.business.model_business.responsemodel.LevelListResponse;
import in.discountmart.business.model_business.responsemodel.LevelWiseCountReport;
import in.discountmart.business.model_business.responsemodel.LevelWiseCountResponse;
import in.discountmart.business.model_business.responsemodel.LevelWiseReportResponse;
import in.discountmart.business.model_business.responsemodel.MyPurchaseResponse;
import in.discountmart.business.model_business.responsemodel.NewsResponse;
import in.discountmart.business.model_business.responsemodel.OuterTeamReportResponse;
import in.discountmart.business.model_business.responsemodel.RankListResponse;
import in.discountmart.business.model_business.responsemodel.RankReportResponse;
import in.discountmart.business.model_business.responsemodel.SponsorGeneologyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MyTeamService {
    /*WebviewFragment Api*/
    String postUrl= "/ProcessAPIWithK";
    @POST(postUrl)
    Call<GeneologyResponse> fetchGeneology(@Body BaseRequest baseRequest, @Header("apikey")String apikey);

    //for My Direct Fragment
    @POST(postUrl)
    Call<SponsorGeneologyResponse> fetchSponserGeneology(@Body BaseRequest baseRequest, @Header("apikey")String apikey);

    /*Downline Fraggment*/
    /*Downline status*/
    @POST(postUrl)
    Call<DownlineStatusResponse> fetchDownlineStatus(@Body DownlineStatusRequest downlineStatusRequest, @Header("apikey")String apikey);

    /*Downline Detail*/
    @POST(postUrl)
    Call<DownlineDetailResponse> fetchDownlineDetails(@Body DownlineDetailRequest downlineDetailRequest, @Header("apikey")String apikey);

    // For Level Wise Direct Report Fragment
    @POST(postUrl)
    Call<LevelListResponse> fetchLevelList(@Body BaseRequest baseRequest, @Header("apikey")String apikey);

    @POST(postUrl)
    Call<LevelWiseReportResponse> fetchLevelWiseReport(@Body LevelWiseReportRequest levelWiseReportRequest, @Header("apikey")String apikey);
    @POST(postUrl)
    Call<DownlinePurchaseResponse> fetchDownlinePurchaseDetails(@Body DownlinePurchaseRequest downlinePurchaseRequest, @Header("apikey")String apikey);

    //for  MyPurchase Api  (MyPurchase Fragment)
    @POST(postUrl)
    Call<MyPurchaseResponse> fetchMyPurchaseDetail(@Body MyPurchaseRequest request, @Header("apikey")String apikey);

    //for  MyPurchase Api  (MyPurchase Fragment)
    @POST(postUrl)
    Call<OuterTeamReportResponse> fetchOuterTeamReport(@Body OuterTeamReportRequest request, @Header("apikey")String apikey);
    //for  My Direct Api  ( My Direct Fragment)
    @POST(postUrl)
    Call<Dy_MyDirectResponse> fetchMyDirectDetail(@Body MyDirectRequest request, @Header("apikey")String apikey);


    //for Level Wise Count api  ( Level Wise Fragment)
    @POST(postUrl)
    Call<LevelWiseCountResponse> fetchLevelWiseCount(@Body BaseRequest request, @Header("apikey")String apikey);

    //for Rank List api
    @POST(postUrl)
    Call<RankListResponse> fetchRankList(@Body BaseRequest request, @Header("apikey")String apikey);

    //for Rank List api
    @POST(postUrl)
    Call<RankReportResponse> fetchDownlineRankReport(@Body DownlineRankRequest request, @Header("apikey")String apikey);


    //for  News Api  ( NewsEvent Fragment)
    @POST(postUrl)
    Call<NewsResponse> fetchNews(@Body BaseRequest request, @Header("apikey")String apikey);

    //for  ID Activation Package List api
    @POST(postUrl)
    Call<IDActivePackageList> fetchIDPackgeList(@Body BaseRequest request, @Header("apikey")String apikey);

    //for Package List Detail
    @POST(postUrl)
    Call<BaseResponse> fetchIDActivate(@Body IDActivationRequest baseRequest, @Header("apikey")String apikey);

    //for  ID Activation Package List api
    @POST(postUrl)
    Call<LevelWiseCountReport> fetchLevelWiseCountReport(@Body LevelWiseCountRequest request, @Header("apikey")String apikey);

   /* //for  Upliner Tree Api  (Upliner Tree Fragment)
    @POST("/Dtprocess.aspx")
    Call<UplinerTreeResponse> fetchUplinerTree(@Body UplinerTreeRequest request);



    //for  FV Report Api  (FV Report Fragment)
    @POST("/Dtprocess.aspx")
    Call<FVReportResponse> fetchFvReport(@Body FvReportRequest request);*/

    /*Downline Detail*/
    @POST(postUrl)
    Call<Dy_DownlineDetailResponse> fetchDy_DownlineDetails(@Body DownlineDetailRequest downlineDetailRequest , @Header("apikey")String apikey);

    //for  My Direct Api  ( My Direct Fragment)
    @POST(postUrl)
    Call<Dy_MyDirectResponse> fetchDynamicMyDirectDetail(@Body MyDirectRequest request,@Header("apikey")String apikey);

    //for  My Direct Api  ( My Direct Fragment)
    @POST(postUrl)
    Call<Dy_DownlinePurchaseResponse> fetchDynamicDownlinePurchaseDetail(@Body DownlinePurchaseRequest request, @Header("apikey")String apikey);

    //for  CHECK ID ACTIVATION
    @POST(postUrl)
    Call<BaseResponse> fetchCheckIdForActivation(@Body BaseRequest request,@Header("apikey") String apikey);
    //for Reward List Api  (Reward fragment)
    @POST(postUrl)
    Call<Dy_MyRewardResponse> fetchReward(@Body BaseRequest baseRequest, @Header("apikey")String apikey);

    //for  My Direct Api  ( My Direct Fragment)
    @POST(postUrl)
    Call<Dy_UpgradeReportResponse> fetchDynamicUpgradeReport(@Body BaseRequest request, @Header("apikey")String apikey);
    //for  My Direct Api  ( My Direct Fragment)
    @POST(postUrl)
    Call<Dy_CommunityDetailResponse> fetchDynamicCommunityDetail(@Body BaseFromToRequest request, @Header("apikey")String apikey);


}
