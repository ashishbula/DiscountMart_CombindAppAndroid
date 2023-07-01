package in.discountmart.utility_services.travel.flight.flight_activity;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import in.discountmart.utility_services.sharedpreferences.SharedPrefrence_Utility;
import in.discountmart.utility_services.travel.flight.call_flight_api.FlightApi;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.FlightBookRequest;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PassengerFooterInfo;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PessengerInfo;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightSearchResponse;
import in.discountmart.utility_services.travel.flight.flight_sharedpreference.FlightSharedValues;
import in.discountmart.utility_services.travel.travel_sharedpreferance.TravelSharedPreferance;
import in.discountmart.utility_services.utilities.DateUtilities;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class OtpVerify_FlightBookActivity extends AppCompatActivity {

    TextView txtMessage;
    TextView txtVerifyMsg;
    TextView txtMobilno;

    EditText edTxtOtp;
    LinearLayout layoutBook;

    Button btnOTPVerify;
    Button btnPay_Book;

    LinearLayout layoutOtpMsgVerify;
    LinearLayout layoutOtpBtnVerify;

    float mainBal=0;
    float totalPay=0;
    String traceId="";
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

    ArrayList<PessengerInfo> pessengerInfoList;
    ArrayList<PessengerInfo> tempPassengerInfoList;
    ArrayList<FlightSearchResponse> selectFlightarrayList;
    PassengerFooterInfo passengerFooterInfo;
    ArrayList<PassengerFooterInfo>footerInfoArrayList;
    ArrayList<FlightBookRequest.FlightPassenger> flightPassengerArrayList;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_otp_verify__flight_book);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            txtMessage=(TextView)findViewById(R.id.otp_veriry_flight_book_act_txt_message);
            txtVerifyMsg=(TextView)findViewById(R.id.otp_veriry_flight_book_act_txt_otpverify);
            edTxtOtp=(EditText)findViewById(R.id.otp_veriry_flight_book_act_edtxt_otp);
            layoutBook=(LinearLayout) findViewById(R.id.otp_verify_flight_book_act_layout_book);
            layoutOtpMsgVerify=(LinearLayout) findViewById(R.id.otp_verify_flight_book_act_layout_otpverify);
            layoutOtpBtnVerify=(LinearLayout) findViewById(R.id.otp_verify_flight_book_act_layout_btn_verify);
            btnPay_Book=(Button) findViewById(R.id.otp_verify_flight_book_act_btn_flight_book);
            btnOTPVerify=(Button) findViewById(R.id.otp_verify_flight_book_act_btn_otpverify);
            txtMobilno=(TextView)findViewById(R.id.otp_veriry_flight_book_act_txt_num);

            /*for toolbar button and title*/
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.str_flight_book_toolbar_title));

            view=findViewById(android.R.id.content);

            if (checkAndRequestPermissions()) {
                // carry on the normal flow, as the case of  permissions  granted.
            }
            LocalBroadcastManager.getInstance(OtpVerify_FlightBookActivity.this).registerReceiver(receiver, new IntentFilter("otp"));

            /*Get Pessanger Detail Value form Shared Preference */

            tempPassengerInfoList=new ArrayList<PessengerInfo>();
            tempPassengerInfoList= FlightSharedValues.getInstance().pessengerInfoArrayList;

            //Passenger Detail get last index element from passenger info list and add in another list
            if(tempPassengerInfoList.size() > 0){

                passengerFooterInfo=new PassengerFooterInfo();

                passengerFooterInfo.setMobile(tempPassengerInfoList.get(tempPassengerInfoList.size()-1).getMobile());
                passengerFooterInfo.setGstno(tempPassengerInfoList.get(tempPassengerInfoList.size()-1).getGstno());
                passengerFooterInfo.setGstemail(tempPassengerInfoList.get(tempPassengerInfoList.size()-1).getGstemail());
                passengerFooterInfo.setCompanyName(tempPassengerInfoList.get(tempPassengerInfoList.size()-1).getCompanyName());
                passengerFooterInfo.setCompanyAddress(tempPassengerInfoList.get(tempPassengerInfoList.size()-1).getCompanyAddress());
                passengerFooterInfo.setContactno(tempPassengerInfoList.get(tempPassengerInfoList.size()-1).getContactno());
                passengerFooterInfo.setEmail(tempPassengerInfoList.get(tempPassengerInfoList.size()-1).getEmail());

                footerInfoArrayList=new ArrayList<PassengerFooterInfo>();
                footerInfoArrayList.addAll(Collections.singleton(passengerFooterInfo));


                /*remove last index element in passenger info list*/
                if(footerInfoArrayList != null && !footerInfoArrayList.isEmpty()){
                    tempPassengerInfoList.remove(tempPassengerInfoList.size()-1);
                    pessengerInfoList=new ArrayList<PessengerInfo>(tempPassengerInfoList);

                }

            }
            /*Get Mobule no. Value from Login Shared Preference for showing in text*/
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(OtpVerify_FlightBookActivity.this).getLoggedinUser();
            String mobile= loginResponse.getMobileNo().substring(0,loginResponse.getMobileNo().length()-2);
            String mobNumber=mobile.substring(7,10);
            txtMobilno.setText(getResources().getString(R.string.str_otp_send_to)+"*******"+mobNumber);

            // get value from Select Flight Detail list
            selectFlightarrayList= FlightSharedValues.getInstance().flightSelectArrayList;
            if(selectFlightarrayList.size() > 0){

                for (int j=0;j < selectFlightarrayList.size(); j++){
                    traceId=selectFlightarrayList.get(j).getTraceID();
                    resultIndex=selectFlightarrayList.get(j).getResultIndex();
                    isLCC=selectFlightarrayList.get(j).getIsLCC();
                    basePrice=selectFlightarrayList.get(j).getBaseFare();
                    taxPrice=selectFlightarrayList.get(j).getTaxAmount();

                }
            }

            /*get total paid amount */
            if(FlightSharedValues.getInstance().totPaidAmount > 0){
                totalPay= FlightSharedValues.getInstance().totPaidAmount;
            }
            /*get promo code and promo discount*/
            if(SharedPrefrence_Utility.getPromocode(this).equals("") || SharedPrefrence_Utility.getPromocode(this)==null){
                promoCode= "";
                applyvoucher="false";
            }
            else {

                promoCode= SharedPrefrence_Utility.getPromocode(this);
                applyvoucher="true";
            }

            if(SharedPrefrence_Utility.getPromoDiscount(this).equals("") || SharedPrefrence_Utility.getPromoDiscount(this)==null){
                promoDiscount="0";
                applyvoucher="false";
            }
            else {
                applyvoucher="true";
                promoDiscount= SharedPrefrence_Utility.getPromoDiscount(this);
            }

            // get discount value from shared preference
            discount= String.valueOf(FlightSharedValues.getInstance().totDiscount);

           /* edTxtOtp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    try {


                        if (s.toString().equals("")) {
                            Toast.makeText(OtpVerify_FlightBookActivity.this, "Please Enter Otp Number", Toast.LENGTH_SHORT).show();
                        } else if (s.toString().length() == 5) {
                            //call verify otp api
                            strOtp = s.toString();
                            flightBookOtpVerify();

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });*/

           /*Button Otp Veryfi*/
            btnOTPVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(edTxtOtp.getText().toString().equals("")){
                            Toast.makeText(OtpVerify_FlightBookActivity.this,"Please Enter Otp",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            strOtp=edTxtOtp.getText().toString();
                            //flightBookOtpVerify();
                              // Hide soft input key board
                        View view = OtpVerify_FlightBookActivity.this.getCurrentFocus();
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

            btnPay_Book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Total utility_main utility_wallet and flight discount amount will be add than compare*/

                    float discoutamt= Float.parseFloat(discount);
                    float promo= Float.parseFloat(promoDiscount);
                    float totCheckBal= mainBal+discoutamt+promo;
                    if(totCheckBal >= totalPay){

                        getFlightBook();
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

    public void verifyOtp(){
        try {

            progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("Please wait");
                progressDialog.setCancelable(false);
                progressDialog.show();
                String strApiRequest="";
                JSONObject object=null;
                LoginResponse loginResponse=new LoginResponse();
                loginResponse=new LoginPreferences_Utility(OtpVerify_FlightBookActivity.this).getLoggedinUser();
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
                verifyOtpRequest.setOTPID(FlightSharedValues.getInstance().flightBookOtpId);

              ApiRequest mainRequest= new ApiRequest();
                /*Set Value in Main Request Model*/
            mainRequest.setDATA(verifyOtpRequest);
            mainRequest.setHEADER(headerRequest);

                strApiRequest=new Gson().toJson(mainRequest);

            Timber.e(strApiRequest);

            Call<BaseResponse> fetchVerifyOtp=
                    NetworkClient_Utility_1.getInstance(OtpVerify_FlightBookActivity.this).create(FlightApi.class).fetchVerifyOtp(strToken,mainRequest);
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
                                    FlightSharedValues.getInstance().flightBookOtp=otpnumber;

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
                                    Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OtpVerify_FlightBookActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                // showSnackbar(toast);

                            }
                        }
                        else {
                            String toast = "something wrong..";
                            //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
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


    /* Request and Response Flight Booking*/
    public void getFlightBook(){
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

                    FlightBookRequest flightBookRequest=new FlightBookRequest();
                    flightBookRequest.setEndUserIp("");
                    flightBookRequest.setIsLCC(isLCC);
                    flightBookRequest.setPassengers(passengersItem1());
                    flightBookRequest.setResultIndex(resultIndex);
                    flightBookRequest.setTokenId("");
                    flightBookRequest.setTraceId(traceId);
                    flightBookRequest.setBasePrice(basePrice);
                    flightBookRequest.setDiscount(discount);
                    flightBookRequest.setEmailID(new LoginPreferences_Utility(this).getLoggedinUser().getEmailId());
                    flightBookRequest.setFormNo(formno);
                    flightBookRequest.setIsApplyVoucher(applyvoucher);
                    flightBookRequest.setMobileNo(mobile);
                    flightBookRequest.setOtp(FlightSharedValues.getInstance().flightBookOtpId);
                    flightBookRequest.setPromoCode(promoCode);
                    flightBookRequest.setPromoDiscount(promoDiscount);
                    flightBookRequest.setSponsorFormNo(companyId);
                    flightBookRequest.setTaxAmount(taxPrice);
                    flightBookRequest.setTotalAmount(String.valueOf(totalPay));

                    /*Set Value in Main Request Model*/
                    apiRequest.setHEADER(headerRequest);
                    apiRequest.setDATA(flightBookRequest);

                    strApiRequest=new Gson().toJson(apiRequest);

                    Log.e("Value",strApiRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }

                /*Call<BaseResponse> fetchFlightBook=
                        NetworkClient_Utility_1.getInstance(this).create(FlightApi.class).fetchFlightBook(apiRequest,strToken);

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

                                    if(Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")){
                                        String toast= Response.getRESP_MSG();
                                        //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                   *//* Intent intent=new Intent(OtpVerify_FlightBookActivity.this,FlightBookSuccessMsgActivity.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putString("Msg",Response.getRESP_MSG());
                                    intent.putExtras(bundle);
                                    startActivity(intent);*//*
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

                                        FlightBookSuccessResponse successResponse=
                                                new Gson().fromJson(Response.getRESP_VALUE(),FlightBookSuccessResponse.class);
                                        ArrayList<FlightBookSuccessResponse.PassengerDetail> passengerDetailArrayList=
                                                new ArrayList<FlightBookSuccessResponse.PassengerDetail>(successResponse.getPassanger());


                                        //String toast= Response.getRESP_MSG();
                                        //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                        if(successResponse != null && passengerDetailArrayList.size() > 0){
                                            Intent intent=new Intent(OtpVerify_FlightBookActivity.this,FlightBookSuccessMsgActivity.class);

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
                                    Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(OtpVerify_FlightBookActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                }
                                else {

                                    String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                    Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    // showSnackbar(toast);

                                }
                            }
                            else {
                                String toast = "something wrong..";
                                //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();
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
                });*/
            }catch (Exception e){
                e.printStackTrace();
            }


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
                                            new LoginPreferences_Utility(OtpVerify_FlightBookActivity.this).getLoggedinUser().setBalance(balanceResponse.getBalance());
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
                                    Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(OtpVerify_FlightBookActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                }
                                else {

                                    String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                    Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    // showSnackbar(toast);

                                }
                            }
                            else {
                                String toast = "something wrong..";
                                //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
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


    /*Get Pessagnger Json Array*/
    private ArrayList<FlightBookRequest.FlightPassenger> passengersItem1() {
        JSONArray jSonArray = new JSONArray();
        JsonArray jsonArray=new JsonArray();
        // int listsize=pessengerInfoArrayList.size();

        flightPassengerArrayList=new ArrayList<FlightBookRequest.FlightPassenger>();
        ArrayList<FlightBookRequest.FlightPassenger> passengerArrayList = new ArrayList<FlightBookRequest.FlightPassenger>();
        Collection<FlightBookRequest.FlightPassenger> enums = null;

        if(pessengerInfoList != null && ! pessengerInfoList.isEmpty()){
            for(int i=0;i < pessengerInfoList.size();i++){
                FlightBookRequest.FlightPassenger flightPassenger = new FlightBookRequest.FlightPassenger();

                if(pessengerInfoList.get(i).getPessengerType().equals("A")){

                    int age= Integer.parseInt(pessengerInfoList.get(i).getDob());
                    String strDepDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                    String[] items1 = strDepDate.split("-");
                    String yy=items1[0];
                    String mm=items1[1];
                    String dd=items1[2];
                    int d = Integer.parseInt(dd);
                    int m = Integer.parseInt(mm);
                    int y = Integer.parseInt(yy);

                    int year=y-age;
                    String month= DateUtilities.convertMonthtoText(m);
                    String day=DateUtilities.ConvertNumberIntoTwoDigit(dd);
                    String dob=day+"-"+month+"-"+year;

                    flightPassenger.setTitle(pessengerInfoList.get(i).getNametitle());

                    flightPassenger.setFirstName(pessengerInfoList.get(i).getName());
                    flightPassenger.setLastName(pessengerInfoList.get(i).getSurName());
                    flightPassenger.setPaxType("1");
                    flightPassenger.setDateOfBirth(dob);
                    flightPassenger.setGender(pessengerInfoList.get(i).getGender());
                    flightPassenger.setPassportNo("");
                    flightPassenger.setPassportExpiry("");
                    flightPassenger.setAddressLine1(new LoginPreferences_Utility(this).getLoggedinUser().getAddress());
                    flightPassenger.setAddressLine2("");
                    flightPassenger.setFare("");
                    flightPassenger.setCity("Bhilwara");
                    flightPassenger.setCountryCode("IN");
                    flightPassenger.setCountryName("India");
                    flightPassenger.setNationality("IN");
                    flightPassenger.setContactNo(footerInfoArrayList.get(0).getMobile());
                    flightPassenger.setEmail(footerInfoArrayList.get(0).getEmail());
                    flightPassenger.setIsLeadPax("true");
                    flightPassenger.setFFAirlineCode("");
                    flightPassenger.setFFNumber("");
                    flightPassenger.setGSTCompanyAddress(footerInfoArrayList.get(0).getCompanyAddress());
                    flightPassenger.setGSTCompanyContactNumber(footerInfoArrayList.get(0).getContactno());
                    flightPassenger.setGSTCompanyName(footerInfoArrayList.get(0).getCompanyName());
                    flightPassenger.setGSTNumber(footerInfoArrayList.get(0).getGstno());
                    flightPassenger.setGSTCompanyEmail(footerInfoArrayList.get(0).getGstemail());
                    // flightPassengerArrayList.add(pessengerInfoArrayList.get(i).checkedId);

                    flightPassengerArrayList.add(flightPassenger);
                }

                else if(pessengerInfoList.get(i).getPessengerType().equals("C")){

                    int age= Integer.parseInt(pessengerInfoList.get(i).getDob());
                    String strDepDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                    String[] items1 = strDepDate.split("-");
                    String yy=items1[0];
                    String mm=items1[1];
                    String dd=items1[2];
                    int d = Integer.parseInt(dd);
                    int m = Integer.parseInt(mm);
                    int y = Integer.parseInt(yy);

                    int year=y-age;
                    String month= DateUtilities.convertMonthtoText(m);
                    String day=DateUtilities.ConvertNumberIntoTwoDigit(dd);
                    String dob=day+"-"+month+"-"+year;
                    FlightBookRequest.FlightPassenger flightPassenger2 = new FlightBookRequest.FlightPassenger();

                    flightPassenger2.setTitle(pessengerInfoList.get(i).getNametitle());
                    flightPassenger2.setFirstName(pessengerInfoList.get(i).getName());
                    flightPassenger2.setLastName(pessengerInfoList.get(i).getSurName());
                    flightPassenger2.setPaxType("2");
                    flightPassenger2.setDateOfBirth(dob);
                    flightPassenger2.setGender(pessengerInfoList.get(i).getGender());
                    flightPassenger2.setPassportNo("");
                    flightPassenger2.setPassportExpiry("");
                    flightPassenger2.setAddressLine1("");
                    flightPassenger2.setAddressLine2("");
                    flightPassenger2.setFare("");
                    flightPassenger2.setCity("Bhilwara");
                    flightPassenger2.setCountryCode("IN");
                    flightPassenger2.setCountryName("India");
                    flightPassenger2.setNationality("IN");
                    flightPassenger2.setContactNo(footerInfoArrayList.get(0).getMobile());
                    flightPassenger2.setEmail(footerInfoArrayList.get(0).getEmail());
                    flightPassenger2.setIsLeadPax("true");
                    flightPassenger2.setFFAirlineCode("");
                    flightPassenger2.setFFNumber("");
                    flightPassenger2.setGSTCompanyAddress(footerInfoArrayList.get(0).getCompanyAddress());
                    flightPassenger2.setGSTCompanyContactNumber(footerInfoArrayList.get(0).getContactno());
                    flightPassenger2.setGSTCompanyName(footerInfoArrayList.get(0).getCompanyName());
                    flightPassenger2.setGSTNumber(footerInfoArrayList.get(0).getGstno());
                    flightPassenger2.setGSTCompanyEmail(footerInfoArrayList.get(0).getGstemail());


                    flightPassengerArrayList.add(flightPassenger2);
                }

                else if(pessengerInfoList.get(i).getPessengerType().equals("I")){
                    int age= Integer.parseInt(pessengerInfoList.get(i).getDob());
                    String strDepDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                    String[] items1 = strDepDate.split("-");
                    String yy=items1[0];
                    String mm=items1[1];
                    String dd=items1[2];
                    int d = Integer.parseInt(dd);
                    int m = Integer.parseInt(mm);
                    int y = Integer.parseInt(yy);


                    int year=y-age;
                    String month= DateUtilities.convertMonthtoText(m);
                    String day=DateUtilities.ConvertNumberIntoTwoDigit(dd);
                    String dob=day+"-"+month+"-"+year;

                    FlightBookRequest.FlightPassenger flightPassenger3 = new FlightBookRequest.FlightPassenger();

                    flightPassenger3.setTitle(pessengerInfoList.get(i).getNametitle());

                    flightPassenger3.setFirstName(pessengerInfoList.get(i).getName());
                    flightPassenger3.setLastName(pessengerInfoList.get(i).getSurName());
                    flightPassenger3.setPaxType("3");
                    flightPassenger3.setDateOfBirth(dob);
                    flightPassenger3.setGender(pessengerInfoList.get(i).getGender());
                    flightPassenger3.setPassportNo("");
                    flightPassenger3.setPassportExpiry("");
                    flightPassenger3.setAddressLine1("");
                    flightPassenger3.setAddressLine2("");
                    flightPassenger3.setFare("");
                    flightPassenger3.setCity("Bhilwara");
                    flightPassenger3.setCountryCode("IN");
                    flightPassenger3.setCountryName("India");
                    flightPassenger3.setNationality("IN");
                    flightPassenger3.setContactNo(footerInfoArrayList.get(0).getMobile());
                    flightPassenger3.setEmail(footerInfoArrayList.get(0).getEmail());
                    flightPassenger3.setIsLeadPax("true");
                    flightPassenger3.setFFAirlineCode("");
                    flightPassenger3.setFFNumber("");
                    flightPassenger3.setGSTCompanyAddress(footerInfoArrayList.get(0).getCompanyAddress());
                    flightPassenger3.setGSTCompanyContactNumber(footerInfoArrayList.get(0).getContactno());
                    flightPassenger3.setGSTCompanyName(footerInfoArrayList.get(0).getCompanyName());
                    flightPassenger3.setGSTNumber(footerInfoArrayList.get(0).getGstno());
                    flightPassenger3.setGSTCompanyEmail(footerInfoArrayList.get(0).getGstemail());


                    flightPassengerArrayList.add(flightPassenger3);
                }

            }



            //jSonArray=new JSONArray(flightSegmentArrayList);
               /* String jsonInString = new Gson().toJson(flightSegment);
                JSONObject mJSONObject = null;
                try {
                    mJSONObject = new JSONObject(jsonInString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jSonArray.put(mJSONObject);*/

        }
        //flightPassengerArrayList=new ArrayList<FlightBookRequest.FlightPassenger>(Arrays.asList(flightPassenger));
        jsonArray = new Gson().toJsonTree(flightPassengerArrayList).getAsJsonArray();


        passengerArrayList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<FlightBookRequest.FlightPassenger>>(){}.getType());



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
