package in.discountmart.utility_services.travel.bus.bus_activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.call_api.MainServices;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.request_model.MainBalanceRequest;
import in.discountmart.utility_services.model.request_model.VerifyOtpRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.model.response_model.MainBalanceResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.travel.bus.bus_model.BusPassengerModel;
import in.discountmart.utility_services.travel.bus.bus_model.BusSeatModel;
import in.discountmart.utility_services.travel.bus.bus_model.bus_request.BusBookRequest;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusBookSuccessResponse;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusSearchListResponse;
import in.discountmart.utility_services.travel.bus.bus_sharedprference.BusSharedValues;
import in.discountmart.utility_services.travel.bus.call_bus_api.BusServiceApi;
import in.discountmart.utility_services.travel.flight.call_flight_api.FlightApi;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PassengerFooterInfo;
import in.discountmart.utility_services.travel.travel_sharedpreferance.TravelSharedPreferance;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class OtpVerify_BusBookActivity extends AppCompatActivity {


    TextView txtMessage;
    TextView txtMobileNo;
    TextView txtVerifyMsg;

    EditText edTxtOtp;
    LinearLayout layoutBook;

    Button btnOTPVerify;
    Button btnPay_Book;

    LinearLayout layoutOtpMsgVerify;
    LinearLayout layoutOtpBtnVerify;


    float mainBal=0;
    float totalPay=0;
    String availableTripId="";
    String boardPointId="";
    String datOfJourney="";
    String fromCityId="";
    String destinationId="";
    String resultIndex="";
    String isLCC="";
    static String otpId="";
    static String otpnumber="";
    String basePrice="";
    String taxPrice="";
    String totalPrice="";
    String discount="";
    String promoCode="";
    String promoDiscount="";
    String promoIsApply="";
    String sponsorFormNo="";
    String formNo="";
    String mobile="";
    String email="";
    String strOtp="";
    ProgressDialog progressDialog;
    String applyvoucher="";

    ArrayList<BusPassengerModel> pessengerInfoList;
    ArrayList<BusPassengerModel> tempPassengerInfoList;
    ArrayList<BusSearchListResponse> selectBusArrayList;
    ArrayList<BusSeatModel> busSeatModelArrayList;
    PassengerFooterInfo passengerFooterInfo;
    ArrayList<PassengerFooterInfo>footerInfoArrayList;
    ArrayList<BusBookRequest.InventoryItemsList> busInventoryArrayList;


    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_otp_and_verify_bus_book_payment);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view=findViewById(android.R.id.content);
        try {
            txtMessage=(TextView)findViewById(R.id.otp_verify_bus_bookpay_act_txt_message);
            txtMobileNo=(TextView)findViewById(R.id.otp_veriry_bus_book_act_txt_num);
            txtVerifyMsg=(TextView)findViewById(R.id.otp_verify_bus_bookpayy_act_txt_otpverify);
            edTxtOtp=(EditText)findViewById(R.id.otp_verify_bus_bookpay_act_edtxt_otp);
            layoutBook=(LinearLayout) findViewById(R.id.otp_verify_bus_bookpay_act_layout_book);
            layoutOtpMsgVerify=(LinearLayout) findViewById(R.id.otp_verify_bus_bookpay_act_layout_msg_otpverify);
            layoutOtpBtnVerify=(LinearLayout) findViewById(R.id.otp_verify_bus_bookpay_act_layout_btn_verify);
            btnPay_Book=(Button) findViewById(R.id.otp_verify_bus_bookpay_act_btn_bus_book);
            btnOTPVerify=(Button) findViewById(R.id.otp_verify_bus_bookpay_act_btn_otpverify);

            /*for toolbar button and title*/
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.str_bus_book_toolbar_title));

            if (checkAndRequestPermissions()) {
                // carry on the normal flow, as the case of  permissions  granted.
            }
            LocalBroadcastManager.getInstance(OtpVerify_BusBookActivity.this).registerReceiver(receiver, new IntentFilter("otp"));


            /*Get Pessanger Detail Value form Shared Preference */

            tempPassengerInfoList=new ArrayList<BusPassengerModel>();
            tempPassengerInfoList= BusSharedValues.getInstance().buspessengerInfoList;

            //Passenger Detail get last index element from passenger info list and add in another list
            if(tempPassengerInfoList.size() > 0){

                /*remove last index element in passenger info list*/

                tempPassengerInfoList.remove(tempPassengerInfoList.size()-1);
                pessengerInfoList=new ArrayList<BusPassengerModel>(tempPassengerInfoList);

            }

            // get value from Bus Shared Preference
            selectBusArrayList= BusSharedValues.getInstance().busSelectArrayList;
            if(BusSharedValues.getInstance().busSeatModelArrayList.size() > 0){
                busSeatModelArrayList=BusSharedValues.getInstance().busSeatModelArrayList;
            }
            busSeatModelArrayList=BusSharedValues.getInstance().busSeatModelArrayList;
            availableTripId=BusSharedValues.getInstance().busTripId;
            boardPointId=BusSharedValues.getInstance().busBoardPoint;
            datOfJourney=BusSharedValues.getInstance().busDepatureDate;
            fromCityId=BusSharedValues.getInstance().busFromCityID;
            destinationId=BusSharedValues.getInstance().busToCityID;
            datOfJourney=BusSharedValues.getInstance().busDepatureTime;


            /*Get Mobule no. Value from Login Shared Preference for showing in text*/
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(OtpVerify_BusBookActivity.this).getLoggedinUser();
            String mobile= loginResponse.getMobileNo().substring(0,loginResponse.getMobileNo().length()-2);
            String mobNumber=mobile.substring(7,10);
            txtMobileNo.setText(getResources().getString(R.string.str_otp_send_to)+"*******"+mobNumber);

           /* if(selectBusArrayList.size() > 0){

                for (int j=0;j < selectBusArrayList.size(); j++){
                    availableTripId=selectBusArrayList.get(j).getTripID();


                }
            }*/

            /*get total paid amount */
            if(BusSharedValues.getInstance().TotalFare > 0){
                //totalPay= FlightSharedValues.getInstance().totPaidAmount;

                int totalFare= (int) BusSharedValues.getInstance().TotalFare;
                totalPay= (float) BusSharedValues.getInstance().TotalFare;
                totalPrice=String.valueOf(totalFare);
            }
            /*get promo code and promo discount*/
            if(BusSharedValues.getInstance().busPromocode.equals("") || BusSharedValues.getInstance().busPromocode == null){
                promoCode= "";
                applyvoucher="false";
            }
            else {

                promoCode=BusSharedValues.getInstance().busPromocode;
                applyvoucher="true";
            }

            if(BusSharedValues.getInstance().busPromoAmount.equals("") || BusSharedValues.getInstance().busPromoAmount==null){
                promoDiscount="0";
                applyvoucher="false";
            }
            else {
                applyvoucher="true";
                promoDiscount=BusSharedValues.getInstance().busPromoAmount;
            }
            discount= String.valueOf(BusSharedValues.getInstance().totDiscount);


            /*Button Otp Veryfi*/
            btnOTPVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(edTxtOtp.getText().toString().equals("")){
                            Toast.makeText(OtpVerify_BusBookActivity.this,"Please Enter Otp",Toast.LENGTH_SHORT).show();
                        }
                        else {

                            strOtp=edTxtOtp.getText().toString();
                            View view = OtpVerify_BusBookActivity.this.getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(),
                                        0);
                            }

                            verifyOtp();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            /*Button */
            btnPay_Book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Total utility_main utility_wallet and flight discount amount will be add than compare*/

                    float discoutamt= Float.parseFloat(discount);
                    float promo= Float.parseFloat(promoDiscount);
                    float totCheckBal= mainBal+discoutamt+promo;
                    if(totCheckBal >= totalPay){

                        getBusBook();
                    }
                    else {
                        //Toast.makeText(OtpVerify_FlightBookActivity.this,"Amount not sufficient",Toast.LENGTH_SHORT).show();
                        Snackbar.make(view, "Amount not sufficient", Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.app_orange_light ))
                                .show();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (Objects.requireNonNull(intent.getAction()).equalsIgnoreCase("otp")) {
                        final String message = intent.getStringExtra("message");
                        edTxtOtp.setText(message);
                        //Do whatever you want with the code here
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    /*Request and response api  for  otp verify*/
    public void verifyOtp(){
        try {

            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
            String strApiRequest="";
            JSONObject object=null;
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(OtpVerify_BusBookActivity.this).getLoggedinUser();
            String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);
            String strToken= TokenBase64.getToken();


            /*Base Request Model*/
            BaseHeaderRequest headerRequest =new BaseHeaderRequest();
            headerRequest.setUserName(  new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
            headerRequest.setPassword( new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
            headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/

            /* Send Otp Request Model*/

            VerifyOtpRequest verifyOtpRequest=new VerifyOtpRequest();
            verifyOtpRequest.setOTP(strOtp);
            verifyOtpRequest.setOTPID(BusSharedValues.getInstance().busBookOtpId);

            ApiRequest mainRequest= new ApiRequest();
            /*Set Value in Main Request Model*/
            mainRequest.setDATA(verifyOtpRequest);
            mainRequest.setHEADER(headerRequest);

            strApiRequest=new Gson().toJson(mainRequest);

            Timber.e(strApiRequest);

            Call<BaseResponse> fetchVerifyOtp=
                    NetworkClient_Utility_1.getInstance(OtpVerify_BusBookActivity.this).create(FlightApi.class).fetchVerifyOtp(strToken,mainRequest);
            fetchVerifyOtp.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    try {

                        BaseResponse Response =new BaseResponse();
                        Response=response.body();

                        if(Response != null){
                            if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                if(!Response.getRESP_VALUE().equals("null") || ! Response.getRESP_VALUE().equals("")){

                                    String strResponse=Response.getRESP_VALUE();
                                    otpnumber= strResponse;

                                    txtVerifyMsg.setText(Response.getRESP_MSG());
                                    BusSharedValues.getInstance().busBookOtp=otpnumber;

                                    layoutOtpBtnVerify.setVisibility(View.GONE);
                                    layoutBook.setVisibility(View.VISIBLE);
                                    // call api utility_main utility_wallet
                                    getMainBalance();

                                }
                                else if(Response.getRESP_VALUE().equals("")){
                                    String toast= Response.getRESP_MSG();
                                    layoutBook.setVisibility(View.GONE);
                                    layoutOtpBtnVerify.setVisibility(View.VISIBLE);

                                    txtVerifyMsg.setText(Response.getRESP_MSG());
                                    Toast.makeText(OtpVerify_BusBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                }

                            }
                            else if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")){

                                String toast= Response.getRESPONSE()+ ": " +Response.getRESP_MSG();
                                layoutBook.setVisibility(View.GONE);
                                layoutOtpBtnVerify.setVisibility(View.VISIBLE);
                                txtVerifyMsg.setText(Response.getRESP_MSG());
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                        .show();

                            }
                            else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                Toast.makeText(OtpVerify_BusBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OtpVerify_BusBookActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(OtpVerify_BusBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                // showSnackbar(toast);

                            }
                        }
                        else {
                            //Toast.makeText(context,"something error may be server / other",Toast.LENGTH_SHORT).show();

                            String toast= "Something wrong..";
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* Request and Response Get Main Balance*/
    public void getMainBalance(){
        try {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String strApiRequest="";
            JSONObject object=null;
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(this).getLoggedinUser();
            String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);
            String formno=loginResponse.getFormNo().substring(0,loginResponse.getFormNo().length()-2);
            String mobile=loginResponse.getMobileNo().substring(0,loginResponse.getMobileNo().length()-2);
            String[] strResources={"GDS","FZ","G8","SG","G9","AK","IX","LB","TR","6E","B3","OP","2T","W5","LV","TZ","ZO","PY"};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String str = String.join(",", strResources);
                TravelSharedPreferance.setFlightSources(this,str);
            }

            JSONArray jsonArray = new JSONArray(Arrays.asList(strResources));
            String strToken= TokenBase64.getToken();

            /*Main Request Model*/
            ApiRequest apiRequest = new ApiRequest();

            try {
                /*Base Request Model*/
                BaseHeaderRequest headerRequest =new BaseHeaderRequest();
                headerRequest.setUserName(  new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
                headerRequest.setPassword( new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
                headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/
                MainBalanceRequest request=new MainBalanceRequest();
                request.setFormNo(formno);

                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(request);

                strApiRequest=new Gson().toJson(apiRequest);

                Log.e("Value",strApiRequest);
            }catch (Exception e){
                e.printStackTrace();
            }

            Call<BaseResponse> fetchFlightBook=
                    NetworkClient_Utility_1.getInstance(this).create(MainServices.class).fetchGetBalance(apiRequest,strToken);

            fetchFlightBook.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    try {

                        BaseResponse Response =new BaseResponse();
                        Response=response.body();

                        if(Response != null){
                            if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                if(!Response.getRESP_VALUE().equals("null") || ! Response.getRESP_VALUE().equals("")){

                                    MainBalanceResponse balanceResponse=
                                            new Gson().fromJson(Response.getRESP_VALUE(),MainBalanceResponse.class);

                                    //String toast= Response.getRESP_MSG();
                                    //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    if(balanceResponse != null){
                                        // get Main Wallet balance
                                        new LoginPreferences_Utility(OtpVerify_BusBookActivity.this).getLoggedinUser().setBalance(balanceResponse.getBalance());
                                        mainBal= Float.parseFloat(balanceResponse.getBalance());
                                    }

                                }
                                else if(Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")){
                                    String toast= Response.getRESP_MSG();

                                    Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                            .show();

                                }

                            }
                            else if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")){
                                String toast= Response.getRESPONSE()+ "\n" + "Please Try Again ";
                                //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                        .show();
                            }
                            else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                Toast.makeText(OtpVerify_BusBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OtpVerify_BusBookActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(OtpVerify_BusBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                // showSnackbar(toast);

                            }
                        }
                        else {
                            //Toast.makeText(context,"something error may be server / other",Toast.LENGTH_SHORT).show();

                            String toast= "Something wrong..";
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                }
            });




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* Request and Response Bus Booking*/
    public void getBusBook(){
        try {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String strApiRequest="";
            JSONObject object=null;
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(this).getLoggedinUser();
            String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);
            String formno=loginResponse.getFormNo().substring(0,loginResponse.getFormNo().length()-2);
            String mobile=loginResponse.getMobileNo().substring(0,loginResponse.getMobileNo().length()-2);

            String strToken= TokenBase64.getToken();

            /*Main Request Model*/
            ApiRequest apiRequest = new ApiRequest();

                try {

                    /*Base Request Model*/
                    BaseHeaderRequest headerRequest =new BaseHeaderRequest();
                    headerRequest.setUserName(  new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
                    headerRequest.setPassword( new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
                    headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/

                    /* Flight Search Request Model*/

                    BusBookRequest busBookRequest=new BusBookRequest();
                    busBookRequest.setAvailableTripId(availableTripId);
                    busBookRequest.setBoardingPointId(boardPointId);
                    busBookRequest.setCity(fromCityId);
                    busBookRequest.setDestination(destinationId);
                    busBookRequest.setDiscount(discount);
                    busBookRequest.setSeatPrice(totalPrice);
                    busBookRequest.setDOJ(datOfJourney);
                    busBookRequest.setEmailID(new LoginPreferences_Utility(this).getLoggedinUser().getEmailId());
                    busBookRequest.setFormNo(formno);
                    busBookRequest.setBoardingPointId(boardPointId);
                    busBookRequest.setMobileNo(mobile);
                    busBookRequest.setOtp(BusSharedValues.getInstance().busBookOtpId);
                    busBookRequest.setPromoCode(promoCode);
                    busBookRequest.setPromoDiscount(promoDiscount);
                    busBookRequest.setSource(fromCityId);
                    busBookRequest.setTotalAmount(totalPrice);
                    busBookRequest.setInventoryItems(passengersItem1());


                    /*Set Value in Main Request Model*/
                    apiRequest.setHEADER(headerRequest);
                    apiRequest.setDATA(busBookRequest);

                    strApiRequest=new Gson().toJson(apiRequest);

                    Log.e("Value",strApiRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }

                Call<BaseResponse> fetchBusBook=
                        NetworkClient_Utility_1.getInstance(this).create(BusServiceApi.class).fetchBusBook(apiRequest,strToken);

                fetchBusBook.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                        try {

                            BaseResponse Response =new BaseResponse();
                            Response=response.body();

                            if(Response != null){
                                if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                    if(Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")){
                                        String toast= Response.getRESP_MSG();
                                        //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                   /* Intent intent=new Intent(OtpVerify_FlightBookActivity.this,FlightBookSuccessMsgActivity.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putString("Msg",Response.getRESP_MSG());
                                    intent.putExtras(bundle);
                                    startActivity(intent);*/
                                        //showSnackbar(toast);
                                        Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                                .setAction("CLOSE", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                    }
                                                })
                                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                                .show();

                                    }
                                    else if(Response.getRESP_VALUE()!= null || ! Response.getRESP_VALUE().equals("")){

                                        BusBookSuccessResponse successResponse=
                                                new Gson().fromJson(Response.getRESP_VALUE(),BusBookSuccessResponse.class);
                                        /*ArrayList<FlightBookSuccessResponse.PassengerDetail> passengerDetailArrayList=
                                                new ArrayList<FlightBookSuccessResponse.PassengerDetail>(successResponse.getPassanger());*/


                                        //String toast= Response.getRESP_MSG();
                                        //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                        if(successResponse != null ){
                                            Intent intent=new Intent(OtpVerify_BusBookActivity.this, BusBookSuccessMsgActivity.class);

                                            Bundle bundle=new Bundle();
                                            bundle.putSerializable("SuccessBook",successResponse);
                                            bundle.putString("Msg",Response.getRESP_MSG());
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }

                                    }

                                }
                                else if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")){
                                    String toast= Response.getRESPONSE()+ "\n" + "Time Expire Please Try Again ";
                                    //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                            .show();
                                }
                                else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                    String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                    Toast.makeText(OtpVerify_BusBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(OtpVerify_BusBookActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                }
                                else {

                                    String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                    Toast.makeText(OtpVerify_BusBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    // showSnackbar(toast);

                                }
                            }
                            else {
                                Toast.makeText(OtpVerify_BusBookActivity.this,"something wrong..",Toast.LENGTH_SHORT).show();
                            }



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();                        Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();
                    }
                });



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Get Pessagnger Json Array*/
    private ArrayList<BusBookRequest.InventoryItemsList> passengersItem1() {
        JSONArray jSonArray = new JSONArray();
        JsonArray jsonArray=new JsonArray();
        // int listsize=pessengerInfoArrayList.size();

        busInventoryArrayList=new ArrayList<BusBookRequest.InventoryItemsList>();
        ArrayList<BusBookRequest.InventoryItemsList> passengerArrayList = new ArrayList<BusBookRequest.InventoryItemsList>();
        Collection<BusBookRequest.InventoryItemsList> enums = null;

        if(pessengerInfoList != null && ! pessengerInfoList.isEmpty()){
            for(int i=0;i < pessengerInfoList.size();i++){
                BusBookRequest.InventoryItemsList busPassenger = new BusBookRequest.InventoryItemsList();

                BusBookRequest.InventoryItemsList.Passengers passengers=new BusBookRequest.InventoryItemsList.Passengers();

                passengers.setAge(pessengerInfoList.get(i).getAge());
                passengers.setGender(pessengerInfoList.get(i).getGender());
                passengers.setIdNumber("");
                passengers.setIdType("");
                passengers.setMobile(pessengerInfoList.get(i).getMobile());
                passengers.setName(pessengerInfoList.get(i).getName());
                passengers.setPrimary("");
                passengers.setTitle(pessengerInfoList.get(i).getGender());


                busPassenger.setFare(busSeatModelArrayList.get(i).getAmount());
                busPassenger.setLadiesSeat(busSeatModelArrayList.get(i).getLadiesSeat());
                busPassenger.setSeatName(busSeatModelArrayList.get(i).getSeat());
                busPassenger.setPassenger(passengers);

                busInventoryArrayList.add(busPassenger);

            }




        }
        //flightPassengerArrayList=new ArrayList<FlightBookRequest.FlightPassenger>(Arrays.asList(flightPassenger));
        jsonArray = new Gson().toJsonTree(busInventoryArrayList).getAsJsonArray();


        passengerArrayList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<BusBookRequest.InventoryItemsList>>(){}.getType());



        return passengerArrayList;
    }

    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int receiveSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        //LocalBroadcastManager.getInstance(OtpVerify_FlightBookActivity.this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // LocalBroadcastManager.getInstance(OtpVerify_FlightBookActivity.this).unregisterReceiver(receiver);
    }

    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        return true;
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
       /* if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }*/
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }
}
