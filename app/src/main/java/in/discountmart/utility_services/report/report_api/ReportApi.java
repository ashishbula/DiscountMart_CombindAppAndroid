package in.discountmart.utility_services.report.report_api;

import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ReportApi {

    /*Recharge report detail List api*/
    @POST("/api/Recharge/GetRechargeReport")
    Call<BaseResponse> fetchRechargeReportList(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /*BillPayment report detail List api*/
    @POST("/api/Bill/GetBillReport")
    Call<BaseResponse> fetchBillPaymentReport(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /*Ledger report detail List api*/
    @POST("/api/Admin/LedgerReport")
    Call<BaseResponse> fetchLedgerReport(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /*bus book report detail List api*/
    @POST("/api/Bus/GetBusReport")
    Call<BaseResponse> fetchBusReport(@Body ApiRequest baseRequest, @Header("Token") String strToken);

    /* Flight report api  */
    @POST("/api/Flight/GetFlightReport")
    Call<BaseResponse> fetchFlightReport(@Body ApiRequest baseRequest, @Header("Token") String strToken);


    /* CAB Book Report api  */
    @POST("/api/Cab/GetCabBookingReport")
    Call<BaseResponse> fetchCabBookReport(@Body ApiRequest baseRequest, @Header("Token") String strToken);



}

