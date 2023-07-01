package in.discountmart.utility_services.travel.hotel.hotel_activity;

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
import java.util.Collections;
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
import in.discountmart.utility_services.travel.flight.call_flight_api.FlightApi;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.FlightBookRequest;
import in.discountmart.utility_services.travel.hotel.call_hotel_api.HotelApi;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelPassengerFooterModel;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelPassengerModel;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelRoomModel;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model.HotelBookOnlineRequest;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model.HotelBookRequest;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model.OnlineBookRoomsDetail;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.BlockRoomDetail;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelBookResponse;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelOnlineBookResponse;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelSearchResponse;
import in.discountmart.utility_services.travel.hotel.hotel_shared_preferance.HotelSharedValues;
import in.discountmart.utility_services.travel.travel_sharedpreferance.TravelSharedPreferance;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class OtpVerify_HotelBookActivity extends AppCompatActivity {


    TextView txtMessage;
    TextView txtVerifyMsg;
    TextView txtMobilno;


    EditText edTxtOtp;
    LinearLayout layoutBook;

    Button btnOTPVerify;
    Button btnPay_Book;

    LinearLayout layoutOtpMsgVerify;
    LinearLayout layoutOtpBtnVerify;


    double mainBal=0;
    double totalPay=0;
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
    String totAdult="";
    String totChild="";
    String totMem="";
    String hotelName="";
    String totRoom="";
    String checkInDate="";
    String checkOutDate="";
    String city="";

    ArrayList<HotelPassengerModel> pessengerInfoList;
    ArrayList<HotelPassengerModel> roompessengerInfoList;
    ArrayList<HotelPassengerModel> tempPassengerInfoList;
    ArrayList<HotelSearchResponse> selectHotelarrayList;
    HotelPassengerFooterModel passengerFooterInfo;
    ArrayList<HotelPassengerFooterModel>footerInfoArrayList;
    ArrayList<HotelBookRequest.HotelRoomPassenger> hotelPassengerArrayList;
    ArrayList< OnlineBookRoomsDetail.BookRoomPassenger> bookRoomPassengerArrayList;
    ArrayList<BlockRoomDetail> roomBlockArrayList;
    ArrayList<OnlineBookRoomsDetail> bookRoomsDetailArrayList;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_otp_verify__hotel_book);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            txtMessage=(TextView)findViewById(R.id.otp_verify_hotel_book_act_txt_message);
            txtVerifyMsg=(TextView)findViewById(R.id.otp_verify_hotel_book_act_txt_otpverify);
            txtMobilno=(TextView)findViewById(R.id.otp_veriry_hotel_book_act_txt_num);
            edTxtOtp=(EditText)findViewById(R.id.otp_verify_hotel_book_act_edtxt_otp);
            layoutBook=(LinearLayout) findViewById(R.id.otp_verify_hotel_book_act_layout_book);
            layoutOtpMsgVerify=(LinearLayout) findViewById(R.id.otp_verify_hotel_book_act_layout_otpverify);
            layoutOtpBtnVerify=(LinearLayout) findViewById(R.id.otp_verify_hotel_book_act_layout_btn_verify);
            btnPay_Book=(Button) findViewById(R.id.otp_verify_hotel_book_act_btn_flight_book);
            btnOTPVerify=(Button) findViewById(R.id.otp_verify_hotel_book_act_btn_otpverify);

            /*for toolbar button and title*/
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.str_hotel_book_toolbar_title));

            view=findViewById(android.R.id.content);

            if (checkAndRequestPermissions()) {
                // carry on the normal flow, as the case of  permissions  granted.
            }
            LocalBroadcastManager.getInstance(OtpVerify_HotelBookActivity.this).registerReceiver(receiver, new IntentFilter("otp"));

            /*Get Pessanger Detail Value form Shared Preference */

            tempPassengerInfoList=new ArrayList<HotelPassengerModel>();
            tempPassengerInfoList= HotelSharedValues.getInstance().hotelPassengerModelArrayList;

            //Passenger Detail get last index element from passenger info list and add in another list
            if(tempPassengerInfoList.size() > 0){

                passengerFooterInfo=new HotelPassengerFooterModel();

                passengerFooterInfo.setMobile(tempPassengerInfoList.get(tempPassengerInfoList.size()-1).getMobile());
                passengerFooterInfo.setGstno(tempPassengerInfoList.get(tempPassengerInfoList.size()-1).getGstno());
                passengerFooterInfo.setGstemail(tempPassengerInfoList.get(tempPassengerInfoList.size()-1).getGstemail());
                passengerFooterInfo.setCompanyName(tempPassengerInfoList.get(tempPassengerInfoList.size()-1).getCompanyName());
                passengerFooterInfo.setCompanyAddress(tempPassengerInfoList.get(tempPassengerInfoList.size()-1).getCompanyAddress());
                passengerFooterInfo.setContactno(tempPassengerInfoList.get(tempPassengerInfoList.size()-1).getContactno());
                passengerFooterInfo.setEmail(tempPassengerInfoList.get(tempPassengerInfoList.size()-1).getEmail());

                email=tempPassengerInfoList.get(tempPassengerInfoList.size()-1).getEmail();
                mobile=tempPassengerInfoList.get(tempPassengerInfoList.size()-1).getMobile();

                footerInfoArrayList=new ArrayList<HotelPassengerFooterModel>();
                footerInfoArrayList.addAll(Collections.singleton(passengerFooterInfo));


                /*remove last index element in passenger info list*/
                if(footerInfoArrayList != null && !footerInfoArrayList.isEmpty()){
                    tempPassengerInfoList.remove(tempPassengerInfoList.size()-1);
                    pessengerInfoList=new ArrayList<HotelPassengerModel>(tempPassengerInfoList);
                    roompessengerInfoList=new ArrayList<HotelPassengerModel>(tempPassengerInfoList);

                }

            }
            /*Get Mobule no. Value from Login Shared Preference for showing in text*/
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(OtpVerify_HotelBookActivity.this).getLoggedinUser();
            String mobile= loginResponse.getMobileNo().substring(0,loginResponse.getMobileNo().length()-2);
            String mobNumber=mobile.substring(7,10);
            txtMobilno.setText(getResources().getString(R.string.str_otp_send_to)+"*******"+mobNumber);

            /*get total paid amount */
            if(HotelSharedValues.getInstance().totAmount > 0){
                //totalPay= FlightSharedValues.getInstance().totPaidAmount;

                double totalFare= (int) HotelSharedValues.getInstance().totAmount;
                totalPay= (float) HotelSharedValues.getInstance().totAmount;
                totalPrice=String.valueOf(totalFare);
            }
            /*get promo code and promo discount*/
            if(HotelSharedValues.getInstance().hotelPromocode.equals("") || HotelSharedValues.getInstance().hotelPromocode == null){
                promoCode= "";
                //applyvoucher="false";
            }
            else {

                promoCode=HotelSharedValues.getInstance().hotelPromocode;
                //applyvoucher="true";
            }

            if(HotelSharedValues.getInstance().hotelPromoAmount.equals("") ||HotelSharedValues.getInstance().hotelPromoAmount==null){
                promoDiscount="0";
                //applyvoucher="false";
            }
            else {
                //applyvoucher="true";
                promoDiscount=HotelSharedValues.getInstance().hotelPromoAmount;
            }

            /*get hotel room discount*/
            if(HotelSharedValues.getInstance().totDiscount == 0 ){
                discount= "";
                //applyvoucher="false";
            }
            else {

                discount= String.valueOf(HotelSharedValues.getInstance().totDiscount);
                //applyvoucher="true";
            }


            totAdult= String.valueOf(HotelSharedValues.getInstance().totAdult);
            hotelName= HotelSharedValues.getInstance().hotelName;
            totChild= String.valueOf(HotelSharedValues.getInstance().totChild);
            totMem= String.valueOf(HotelSharedValues.getInstance().totMember);
            totRoom= String.valueOf(HotelSharedValues.getInstance().totRoom);
            checkInDate=HotelSharedValues.getInstance().chkInDate;
            checkOutDate=HotelSharedValues.getInstance().chkOutDate;
            city=HotelSharedValues.getInstance().city;


            /*Button Otp Veryfi*/
            btnOTPVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(edTxtOtp.getText().toString().equals("")){
                            Toast.makeText(OtpVerify_HotelBookActivity.this,"Please Enter Otp",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            strOtp=edTxtOtp.getText().toString();

                            /*Hide soft input keyboard*/
                            View view = OtpVerify_HotelBookActivity.this.getCurrentFocus();
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

                    //double discoutamt= Float.parseFloat(discount);
                    float promo= Float.parseFloat(promoDiscount);
                    double totCheckBal= mainBal+promo;
                    if(totCheckBal >= totalPay){

                        getHotelRoomBookOnline();
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
            loginResponse=new LoginPreferences_Utility(OtpVerify_HotelBookActivity.this).getLoggedinUser();
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
            verifyOtpRequest.setOTPID(HotelSharedValues.getInstance().hotelBookOtpID);

            ApiRequest mainRequest= new ApiRequest();
            /*Set Value in Main Request Model*/
            mainRequest.setDATA(verifyOtpRequest);
            mainRequest.setHEADER(headerRequest);

            strApiRequest=new Gson().toJson(mainRequest);

            Timber.e(strApiRequest);

            Call<BaseResponse> fetchVerifyOtp=
                    NetworkClient_Utility_1.getInstance(OtpVerify_HotelBookActivity.this).create(FlightApi.class).fetchVerifyOtp(strToken,mainRequest);
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
                                    HotelSharedValues.getInstance().hotelBookOtp=otpnumber;

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
                                    Toast.makeText(OtpVerify_HotelBookActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(OtpVerify_HotelBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OtpVerify_HotelBookActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(OtpVerify_HotelBookActivity.this, toast, Toast.LENGTH_SHORT).show();
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


    /* Request and Response Hotel offline Booking*/
    public void getHotelRoomBookOffline(){
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

                    HotelBookRequest hotelBookRequest=new HotelBookRequest();
                    hotelBookRequest.setAdultCount(totAdult);
                    hotelBookRequest.setCheckInDate(checkInDate);
                    hotelBookRequest.setCheckOutDate(checkOutDate);
                    hotelBookRequest.setChildCount(totChild);
                    hotelBookRequest.setDiscount(discount);
                    hotelBookRequest.setEmailID(new LoginPreferences_Utility(this).getLoggedinUser().getEmailId());
                    hotelBookRequest.setFormNo(formno);
                    hotelBookRequest.setHotelName(hotelName);
                    hotelBookRequest.setMobileNo(mobile);
                    hotelBookRequest.setNoOfRooms(totRoom);
                    hotelBookRequest.setOtp(HotelSharedValues.getInstance().hotelBookOtpID);
                    hotelBookRequest.setPromoCode(promoCode);
                    hotelBookRequest.setPromoDiscount(promoDiscount);
                    hotelBookRequest.setTotalAmount(totalPrice);
                    hotelBookRequest.setCity(city);
                    hotelBookRequest.setHotelPassenger(passengersItem1());



                    /*Set Value in Main Request Model*/
                    apiRequest.setHEADER(headerRequest);
                    apiRequest.setDATA(hotelBookRequest);

                    strApiRequest=new Gson().toJson(apiRequest);

                    Log.e("Value",strApiRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }

                Call<BaseResponse> fetchHotelBook=
                        NetworkClient_Utility_1.getInstance(this).create(HotelApi.class).fetchHotelRoomBook(apiRequest,strToken);

                fetchHotelBook.enqueue(new Callback<BaseResponse>() {
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

                                        HotelBookResponse successResponse=
                                                new Gson().fromJson(Response.getRESP_VALUE(),HotelBookResponse.class);



                                        //String toast= Response.getRESP_MSG();
                                        //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                        if(successResponse != null ){
                                            Intent intent=new Intent(OtpVerify_HotelBookActivity.this, HotelBookSuccessMsgActivity.class);

                                            Bundle bundle=new Bundle();
                                            bundle.putSerializable("SuccessBook",successResponse);
                                            bundle.putString("Msg",Response.getRESP_MSG());
                                            bundle.putString("Book","Offline");
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
                                else {

                                    String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
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
                            }
                            else {
                                Toast.makeText(OtpVerify_HotelBookActivity.this,"something error may be server / other",Toast.LENGTH_SHORT).show();
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
                                        new LoginPreferences_Utility(OtpVerify_HotelBookActivity.this).getLoggedinUser().setBalance(balanceResponse.getBalance());
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
                                Toast.makeText(OtpVerify_HotelBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OtpVerify_HotelBookActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(OtpVerify_HotelBookActivity.this, toast, Toast.LENGTH_SHORT).show();
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

    /* Request and Response Hotel Online Booking*/
    public void getHotelRoomBookOnline(){
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
            String mobileno=loginResponse.getMobileNo().substring(0,loginResponse.getMobileNo().length()-2);

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

                    /* Hotel Book Request Model*/

                    HotelBookOnlineRequest hotelBookRequest=new HotelBookOnlineRequest();
                    hotelBookRequest.setAdultCount(totAdult);
                    hotelBookRequest.setCheckInDate(checkInDate);
                    hotelBookRequest.setCheckOutDate(checkOutDate);
                    hotelBookRequest.setChildCount(totChild);
                    hotelBookRequest.setDiscount(discount);
                    if(! new LoginPreferences_Utility(this).getLoggedinUser().getEmailId().contentEquals(""))
                        hotelBookRequest.setEmailID(new LoginPreferences_Utility(this).getLoggedinUser().getEmailId());
                    else
                        hotelBookRequest.setEmailID(email);

                    hotelBookRequest.setFormNo(formno);
                    if(mobileno.contentEquals(""))
                        hotelBookRequest.setMobileNo(mobile);
                    else
                        hotelBookRequest.setMobileNo(mobileno);
                    hotelBookRequest.setOtp(HotelSharedValues.getInstance().hotelBookOtpID);
                    hotelBookRequest.setPromoCode(promoCode);
                    hotelBookRequest.setPromoDiscount(promoDiscount);
                    hotelBookRequest.setTotalAmount(totalPrice);
                    hotelBookRequest.setCity(city);

                    hotelBookRequest.setResultIndex(HotelSharedValues.getInstance().resultIndex);
                    hotelBookRequest.setHotelCode(HotelSharedValues.getInstance().hotelCode);
                    hotelBookRequest.setHotelName(hotelName);
                    hotelBookRequest.setGuestNationality("IN");
                    hotelBookRequest.setNoOfRooms(totRoom);
                    hotelBookRequest.setClientReferenceNo("");
                    hotelBookRequest.setIsVoucherBooking("true");
                    hotelBookRequest.setHotelRoomsDetails(hotelRoomDetail());
                    hotelBookRequest.setEndUserIp("");
                    hotelBookRequest.setTokenId("");
                    hotelBookRequest.setTraceId(HotelSharedValues.getInstance().traceID);

                    /*Set Value in Main Request Model*/
                    apiRequest.setHEADER(headerRequest);
                    apiRequest.setDATA(hotelBookRequest);

                    strApiRequest=new Gson().toJson(apiRequest);

                    Log.e("Value",strApiRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }

                Call<BaseResponse> fetchHotelBook=
                        NetworkClient_Utility_1.getInstance(this).create(HotelApi.class).fetchHotelRoomBookOnline(apiRequest,strToken);

                fetchHotelBook.enqueue(new Callback<BaseResponse>() {
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

                                        HotelOnlineBookResponse successResponse=
                                                new Gson().fromJson(Response.getRESP_VALUE(),HotelOnlineBookResponse.class);

                                        if(successResponse != null ){

                                            if(successResponse.getStatus().contentEquals("1") && successResponse.getErrorCode().contentEquals("0")){
                                                Intent intent=new Intent(OtpVerify_HotelBookActivity.this, HotelBookSuccessMsgActivity.class);

                                                Bundle bundle=new Bundle();
                                                bundle.putSerializable("SuccessBook",successResponse);
                                                bundle.putString("Msg",Response.getRESP_MSG());
                                                bundle.putString("Book","Online");
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            }
                                            else  if(successResponse.getStatus().contentEquals("2")&& successResponse.getErrorCode().contentEquals("2")){

                                                String toast= successResponse.getErrorMessage();
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

                                            else  {

                                                String toast= successResponse.getErrorMessage();
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
                                    Toast.makeText(OtpVerify_HotelBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(OtpVerify_HotelBookActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                }
                                else {

                                    String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                    Toast.makeText(OtpVerify_HotelBookActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                });
            }catch (Exception e){
                e.printStackTrace();
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /*Get Pessagnger Json Array*/
    private ArrayList<HotelBookRequest.HotelRoomPassenger> passengersItem1() {
        JSONArray jSonArray = new JSONArray();
        JsonArray jsonArray=new JsonArray();
        // int listsize=pessengerInfoArrayList.size();

        hotelPassengerArrayList=new ArrayList<HotelBookRequest.HotelRoomPassenger>();
        ArrayList<HotelBookRequest.HotelRoomPassenger> passengerArrayList = new ArrayList<HotelBookRequest.HotelRoomPassenger>();
        Collection<FlightBookRequest.FlightPassenger> enums = null;

        if(pessengerInfoList != null && ! pessengerInfoList.isEmpty()){
            for(int i=0;i < pessengerInfoList.size();i++){
                HotelBookRequest.HotelRoomPassenger hotelRoomPassenger = new HotelBookRequest.HotelRoomPassenger();

                if(pessengerInfoList.get(i).getPessengerType().equals("A")){
                    hotelRoomPassenger.setTitle(pessengerInfoList.get(i).getNametitle());

                    hotelRoomPassenger.setFirstName(pessengerInfoList.get(i).getName());
                    hotelRoomPassenger.setLastName(pessengerInfoList.get(i).getSurName());
                    hotelRoomPassenger.setMiddleName("");
                    hotelRoomPassenger.setPaxType("1");
                    hotelRoomPassenger.setAge(pessengerInfoList.get(i).getDob());
                    //flightPassenger.setGender(pessengerInfoList.get(i).getGender());
                    hotelRoomPassenger.setPassportNo("");
                    hotelRoomPassenger.setPassportExpDate("");
                    hotelRoomPassenger.setPassportIssueDate("");
                    hotelRoomPassenger.setPhoneno("");

                    hotelRoomPassenger.setEmail(footerInfoArrayList.get(0).getEmail());

                    // flightPassengerArrayList.add(pessengerInfoArrayList.get(i).checkedId);

                    hotelPassengerArrayList.add(hotelRoomPassenger);
                }

                else if(pessengerInfoList.get(i).getPessengerType().equals("C")){
                    HotelBookRequest.HotelRoomPassenger hotelRoomPassenger2 = new  HotelBookRequest.HotelRoomPassenger();

                    hotelRoomPassenger2.setTitle(pessengerInfoList.get(i).getNametitle());

                    hotelRoomPassenger2.setFirstName(pessengerInfoList.get(i).getName());
                    hotelRoomPassenger2.setLastName(pessengerInfoList.get(i).getSurName());
                    hotelRoomPassenger2.setMiddleName("");
                    hotelRoomPassenger2.setPaxType("2");
                    hotelRoomPassenger2.setAge(pessengerInfoList.get(i).getDob());
                    //flightPassenger.setGender(pessengerInfoList.get(i).getGender());
                    hotelRoomPassenger2.setPassportNo("");
                    hotelRoomPassenger2.setPassportExpDate("");
                    hotelRoomPassenger2.setPassportIssueDate("");
                    hotelRoomPassenger2.setPhoneno("");

                    hotelRoomPassenger2.setEmail(footerInfoArrayList.get(0).getEmail());



                    hotelPassengerArrayList.add(hotelRoomPassenger2);
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
        jsonArray = new Gson().toJsonTree(hotelPassengerArrayList).getAsJsonArray();


        passengerArrayList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<HotelBookRequest.HotelRoomPassenger>>(){}.getType());



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

    /*Get Hotel Room Json Array*/
    private ArrayList<OnlineBookRoomsDetail> hotelRoomDetail() {
        ArrayList<OnlineBookRoomsDetail> bookRoomsDetailList = new ArrayList<OnlineBookRoomsDetail>();
        try {
            JSONArray jSonArray = new JSONArray();
            JsonArray jsonArray=new JsonArray();

            /*Get Block Room list value form Hotel Shared and assign in list */
            roomBlockArrayList=new ArrayList<BlockRoomDetail>();
            roomBlockArrayList= HotelSharedValues.getInstance().blockroomDetailList;

            bookRoomsDetailArrayList=new ArrayList<OnlineBookRoomsDetail>();

            if(roomBlockArrayList != null && ! roomBlockArrayList.isEmpty()){
                for(int i=0;i < roomBlockArrayList.size();i++){
                    String room= String.valueOf(i+1);
                    OnlineBookRoomsDetail hotelRoom = new OnlineBookRoomsDetail();

                    OnlineBookRoomsDetail.BookRoomPassenger roomPassenger = new OnlineBookRoomsDetail.BookRoomPassenger();

                    /*Get room Price from block room list and set BookRoomPrice class*/
                    OnlineBookRoomsDetail.BookRoomPrice roomPrice = new OnlineBookRoomsDetail.BookRoomPrice();
                    roomPrice.setCurrencyCode(roomBlockArrayList.get(i).getPrice().getCurrencyCode());
                    roomPrice.setRoomPrice(Double.parseDouble(roomBlockArrayList.get(i).getPrice().getRoomPrice()));
                    roomPrice.setTax(roomBlockArrayList.get(i).getPrice().getTax());
                    roomPrice.setExtraGuestCharge(roomBlockArrayList.get(i).getPrice().getExtraGuestCharge());
                    roomPrice.setChildCharge(roomBlockArrayList.get(i).getPrice().getChildCharge());
                    roomPrice.setOtherCharges(roomBlockArrayList.get(i).getPrice().getOtherCharges());
                    roomPrice.setDiscount(roomBlockArrayList.get(i).getPrice().getDiscount());
                    roomPrice.setPublishedPrice(roomBlockArrayList.get(i).getPrice().getPublishedPrice());
                    roomPrice.setPublishedPriceRoundedOff(roomBlockArrayList.get(i).getPrice().getPublishedPriceRoundedOff());
                    roomPrice.setOfferedPrice(roomBlockArrayList.get(i).getPrice().getOfferedPrice());
                    roomPrice.setOfferedPriceRoundedOff(roomBlockArrayList.get(i).getPrice().getOfferedPriceRoundedOff());
                    roomPrice.setAgentCommission(roomBlockArrayList.get(i).getPrice().getAgentCommission());
                    roomPrice.setAgentMarkUp(roomBlockArrayList.get(i).getPrice().getAgentMarkUp());
                    roomPrice.setServiceTax(roomBlockArrayList.get(i).getPrice().getServiceTax());
                    roomPrice.setTDS(roomBlockArrayList.get(i).getPrice().getTDS());

                    /*Set Book Room Value*/
                    hotelRoom.setID(null);
                    hotelRoom.setRoomIndex(roomBlockArrayList.get(i).getRoomIndex());
                    hotelRoom.setRoomTypeCode(roomBlockArrayList.get(i).getRoomTypeCode());
                    hotelRoom.setRoomTypeName(roomBlockArrayList.get(i).getRoomTypeName());
                    hotelRoom.setRatePlanCode(roomBlockArrayList.get(i).getRatePlanCode());
                    hotelRoom.setBedTypeCode(null);
                    hotelRoom.setSmokingPreference("0");
                    hotelRoom.setSupplements(null);
                    hotelRoom.setPrice(roomPrice);
                    hotelRoom.setHotelPassenger(bookRoompassengers(room));
                    hotelRoom.setAmenity(null);
                    hotelRoom.setBedTypes(null);
                    hotelRoom.setCancellationPolicies(null);
                    hotelRoom.setCancellationPolicy(null);
                    hotelRoom.setInclusion(null);
                    hotelRoom.setLastCancelDate(null);

                    bookRoomsDetailArrayList.add(hotelRoom);

                }
            }
            //flightPassengerArrayList=new ArrayList<FlightBookRequest.FlightPassenger>(Arrays.asList(flightPassenger));
            jsonArray = new Gson().toJsonTree(bookRoomsDetailArrayList).getAsJsonArray();
            bookRoomsDetailList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<OnlineBookRoomsDetail>>(){}.getType());


        }catch (Exception e){
                e.printStackTrace();
        }
        return bookRoomsDetailList;
    }

    /*Get Pessagnger Json Array*/
    private ArrayList<OnlineBookRoomsDetail.BookRoomPassenger> bookRoompassengers( String noofRoom) {

        JsonArray jsonArray=new JsonArray();

        ArrayList<HotelPassengerModel>pessInfoList=new ArrayList<HotelPassengerModel>();
        if(roompessengerInfoList.size() > 0 && roompessengerInfoList != null){
            pessInfoList=roompessengerInfoList;
        }

        /*Get Hotel Room Model Value from Hotel Shared  and assign in Arraylist*/
        ArrayList<HotelRoomModel> hotelRoomArrayList=new ArrayList<HotelRoomModel>();
        hotelRoomArrayList= HotelSharedValues.getInstance().hotelRoomInfoListShared;

        bookRoomPassengerArrayList=new ArrayList< OnlineBookRoomsDetail.BookRoomPassenger>();
        ArrayList<OnlineBookRoomsDetail.BookRoomPassenger> passengerArrayList = new ArrayList <OnlineBookRoomsDetail.BookRoomPassenger>();

        if(pessInfoList != null && hotelRoomArrayList!= null){


            for(int h=0; h < hotelRoomArrayList.size(); h++){
                String roomNo=hotelRoomArrayList.get(h).getRoom();
                if(noofRoom.contentEquals(roomNo)){
                    int totAdult=hotelRoomArrayList.get(h).getAdultcount();
                    int totChild=hotelRoomArrayList.get(h).getChildcount();
                    int tot=totAdult+totChild;
                    int adultIncrement=0;
                    int childIncrement=0;

                    if(tot > 0) {
                        for (int i = 0; i < pessInfoList.size(); i++) {

                            if (totAdult > adultIncrement) {

                                for (int j = 0; j < totAdult; j++) {
                                    OnlineBookRoomsDetail.BookRoomPassenger hotelRoomPassenger = new OnlineBookRoomsDetail.BookRoomPassenger();

                                    if (pessInfoList.get(i).getPessengerType().equals("A")) {
                                        hotelRoomPassenger.setTitle(pessInfoList.get(i).getNametitle());
                                        hotelRoomPassenger.setFirstName(pessInfoList.get(i).getName());
                                        hotelRoomPassenger.setLastName(pessInfoList.get(i).getSurName());
                                        hotelRoomPassenger.setMiddleName("");
                                        hotelRoomPassenger.setPaxType("1");
                                        hotelRoomPassenger.setAge(pessInfoList.get(i).getDob());
                                        //flightPassenger.setGender(pessengerInfoList.get(i).getGender());
                                        hotelRoomPassenger.setPassportNo("");
                                        hotelRoomPassenger.setPassportExpDate("");
                                        hotelRoomPassenger.setPassportIssueDate("");
                                        hotelRoomPassenger.setPhoneno("");
                                        hotelRoomPassenger.setEmail(footerInfoArrayList.get(0).getEmail());
                                        hotelRoomPassenger.setLeadPassenger("true");

                                        bookRoomPassengerArrayList.add(hotelRoomPassenger);

                                        final HotelPassengerModel remove = roompessengerInfoList.get(j);
                                        roompessengerInfoList.remove(remove);
                                        adultIncrement=j+1;
                                    }
                                }


                            }
                            if (totChild > childIncrement) {
                                for (int c = 0; c < totChild; c++) {

                                    if (pessInfoList.get(i).getPessengerType().equals("C")) {
                                    OnlineBookRoomsDetail.BookRoomPassenger hotelRoomPassenger2 = new OnlineBookRoomsDetail.BookRoomPassenger();

                                    hotelRoomPassenger2.setTitle(pessInfoList.get(i).getNametitle());
                                    hotelRoomPassenger2.setFirstName(pessInfoList.get(i).getName());
                                    hotelRoomPassenger2.setLastName(pessInfoList.get(i).getSurName());
                                    hotelRoomPassenger2.setMiddleName("");
                                    hotelRoomPassenger2.setPaxType("2");
                                    hotelRoomPassenger2.setAge(pessInfoList.get(i).getDob());
                                    //flightPassenger.setGender(pessengerInfoList.get(i).getGender());
                                    hotelRoomPassenger2.setPassportNo("");
                                    hotelRoomPassenger2.setPassportExpDate("");
                                    hotelRoomPassenger2.setPassportIssueDate("");
                                    hotelRoomPassenger2.setPhoneno("");
                                    hotelRoomPassenger2.setEmail(pessInfoList.get(0).getEmail());
                                    hotelRoomPassenger2.setLeadPassenger("true");
                                    bookRoomPassengerArrayList.add(hotelRoomPassenger2);

                                    final HotelPassengerModel remove = roompessengerInfoList.get(i);
                                    roompessengerInfoList.remove(remove);
                                        childIncrement=c+1;
                                    }
                                }

                            }
                        }
                    }

                }
            }

        }
        //flightPassengerArrayList=new ArrayList<FlightBookRequest.FlightPassenger>(Arrays.asList(flightPassenger));
        jsonArray = new Gson().toJsonTree(bookRoomPassengerArrayList).getAsJsonArray();

        passengerArrayList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<OnlineBookRoomsDetail.BookRoomPassenger>>(){}.getType());

        return passengerArrayList;
    }
}
