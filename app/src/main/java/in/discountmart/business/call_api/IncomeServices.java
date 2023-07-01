package in.discountmart.business.call_api;



import in.discountmart.business.model_business.requestmodel.BaseFromToRequest;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.BinaryIncomeRequest;
import in.discountmart.business.model_business.requestmodel.DailyIncentiveRequest;
import in.discountmart.business.model_business.requestmodel.DirectIncomeRequest;
import in.discountmart.business.model_business.requestmodel.DownBusinessRequest;
import in.discountmart.business.model_business.requestmodel.LeadershipBonusRequest;
import in.discountmart.business.model_business.requestmodel.MonthlyIncentiveRequest;
import in.discountmart.business.model_business.requestmodel.MyBusinessRequest;
import in.discountmart.business.model_business.requestmodel.RetailIncentiveRequest;
import in.discountmart.business.model_business.requestmodel.WeeklyIncentiveRequest;
import in.discountmart.business.model_business.requestmodel.YearlyReardRequest;
import in.discountmart.business.model_business.responsemodel.BinaryIncomeResponse;
import in.discountmart.business.model_business.responsemodel.DailyIncentiveResponse;
import in.discountmart.business.model_business.responsemodel.DirectIncomeReponse;
import in.discountmart.business.model_business.responsemodel.DownBusinessResponse;
import in.discountmart.business.model_business.responsemodel.Dy_DailyIncentiveResponse;
import in.discountmart.business.model_business.responsemodel.Dy_MFADetailResponse;
import in.discountmart.business.model_business.responsemodel.Dy_MonthlyRewardPoints;
import in.discountmart.business.model_business.responsemodel.Dy_WeeklyIncentiveResponse;
import in.discountmart.business.model_business.responsemodel.FPVReportResponse;
import in.discountmart.business.model_business.responsemodel.LeadershipBonusResponse;
import in.discountmart.business.model_business.responsemodel.MonthSessionResponse;
import in.discountmart.business.model_business.responsemodel.MonthlyIncentiveResponse;
import in.discountmart.business.model_business.responsemodel.MyBusinessResponse;
import in.discountmart.business.model_business.responsemodel.RetailIncentiveResponse;
import in.discountmart.business.model_business.responsemodel.WeeklyIncentiveResponse;
import in.discountmart.business.model_business.responsemodel.YearlyRewardResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IncomeServices {
    //for Direct Income Api Detail (Direct Income Fragment)
    @POST("/ProcessAPIWithK")
    Call<DirectIncomeReponse> fetchDirectIncomeDetail(@Body DirectIncomeRequest directIncomeRequest);

    //for Weekly Income Api Detail (Weekly Incentive Fragment)
    @POST("/ProcessAPIWithK")
    Call<WeeklyIncentiveResponse> fetchWeeklyIncomeDetail(@Body WeeklyIncentiveRequest weeklyIncentiveRequest);

    //for BinaryIncome Api Detail (Binary Income Fragment)
    @POST("/ProcessAPIWithK")
    Call<BinaryIncomeResponse> fetchBinaryIncomeDetail(@Body BinaryIncomeRequest binaryIncomeRequest);

    //for LeaderShip Bonus Income Api Detail (LeaderShip Bonus Income Fragment)
    @POST("/ProcessAPIWithK")
    Call<LeadershipBonusResponse> fetchLeadershipBonusDetail(@Body LeadershipBonusRequest request);

    //for YearlyReward  Api Detail (YearlyReward  Fragment)
    @POST("/ProcessAPIWithK")
    Call<YearlyRewardResponse> fetchYearlyRewardDetail(@Body YearlyReardRequest request);

    //for RetailIncentive  Api Detail (RetailIncentive Fragment)
    @POST("/ProcessAPIWithK")
    Call<RetailIncentiveResponse> fetchRetailIncentiveDetail(@Body RetailIncentiveRequest request);

    //for Session Api Detail (MyBusiness Fragment)
    @POST("/ProcessAPIWithK")
    Call<MonthSessionResponse> fetchSessionList(@Body BaseRequest request, @Header("apikey") String apikey);

    //for Self Business  Api  (MyBusiness Fragment)
    @POST("/ProcessAPIWithK")
    Call<MyBusinessResponse> fetchMyBusinessDetail(@Body MyBusinessRequest request, @Header("apikey") String apikey);

    //for MyBusinessDown  Api  (MyBusinessDown Fragment)
    @POST("/ProcessAPIWithK")
    Call<DownBusinessResponse> fetchDownBusinessDetail(@Body DownBusinessRequest request, @Header("apikey") String apikey);

    @POST("/ProcessAPIWithK")
    Call<MonthlyIncentiveResponse> fetchMonthlyIncomeDetail(@Body MonthlyIncentiveRequest weeklyIncentiveRequest);

    @POST("/ProcessAPIWithK")
    Call<FPVReportResponse> fetchFPVReportDetail(@Body BaseFromToRequest request);

    @POST("/ProcessAPIWithK")
    Call<DailyIncentiveResponse> fetchDailyIncentiveDetail(@Body DailyIncentiveRequest request);


    @POST("/ProcessAPIWithK")
    Call<Dy_DailyIncentiveResponse> fetchDailyIncentive(@Body DailyIncentiveRequest request);


    @POST("/ProcessAPIWithK")
    Call<Dy_DailyIncentiveResponse> fetchDynamicDailyIncentive(@Body DailyIncentiveRequest request, @Header("apikey") String apikey);


    @POST("/ProcessAPIWithK")
    Call<Dy_WeeklyIncentiveResponse> fetchDynamicWeeklyIncentive(@Body WeeklyIncentiveRequest request, @Header("apikey") String apikey);


    @POST("/ProcessAPIWithK")
    Call<Dy_MFADetailResponse> fetchDynamicMFAReport(@Body BaseRequest request, @Header("apikey") String apikey);


    @POST("/ProcessAPIWithK")
    Call<Dy_MonthlyRewardPoints> fetchDynamicMonthlyRewardPoint(@Body BaseFromToRequest request, @Header("apikey") String apikey);





}
