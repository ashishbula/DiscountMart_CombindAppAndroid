package in.discountmart.utility_services.travel.hotel.hotel_activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
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

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.activity.VerifyOtpActivity;
import in.discountmart.utility_services.call_api.MainServices;
import in.discountmart.utility_services.constants.AppConstants;
import in.discountmart.utility_services.fund.activity.AddFundActivity;
import in.discountmart.utility_services.listener.AlertDialogButtonListener;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.request_model.MainBalanceRequest;
import in.discountmart.utility_services.model.request_model.SendOtpRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.model.response_model.MainBalanceResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.sharedpreferences.SharedPrefrence_Utility;
import in.discountmart.utility_services.travel.flight.call_flight_api.FlightApi;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.FlightBookRequest;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelPassengerFooterModel;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelPassengerModel;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelRoomModel;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model.HotelBookOnlineRequest;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model.HotelBookRequest;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model.OnlineBookRoomsDetail;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.BlockRoomDetail;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelSearchResponse;
import in.discountmart.utility_services.travel.hotel.hotel_shared_preferance.HotelSharedValues;
import in.discountmart.utility_services.travel.travel_sharedpreferance.TravelSharedPreferance;
import in.discountmart.utility_services.utilities.AlertDialogUtils;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpAndHotelPaymentActivity extends AppCompatActivity {

    RadioGroup rdgPaymentMode;
    RadioButton rdbMainWallet;
    RadioButton rdbE_Wallet;
    TextView txtMainBal;
    TextView txtBookAmount;
    TextView txtBalMsg;
    TextView txtDeductBal_other;
    TextView txtDeductBal_Main;
    TextView txtDeductBal_Promo;
    TextView txtClickHere;
    TextView txtPromo;
    AppCompatCheckBox chBoxMainWallet;
    AppCompatCheckBox chBoxE_Wallet;
    AppCompatCheckBox chBox_Promo;
    EditText edTxtTransactionPass;
    EditText edTxtAvailblefund;
    EditText edTxtRequestfund;
    EditText edTxtOtp;
    Spinner spinnerFundFrom;
    LinearLayout layoutClickHere;
    LinearLayout layChkBox_Mwallet;
    LinearLayout layChkBox_Ewallet;
    LinearLayout layChkBox_Promo;
    LinearLayout layoutPromoCode;

    Button btnOTP;
    Button btnPay_Book;
    Button btnCheckFund;
    Button btnRequestfund;
    LinearLayout layoutMainWallet;
    LinearLayout layoutE_Wallet;
    LinearLayout layoutMessage;
    LinearLayout layoutBtnotp;
    View view;

    ProgressDialog progressDialog;
    TextInputLayout inputLayoutOtp;
    double mainBal = 0;
    double totalPay = 0;
    double deductAmount = 0;
    String traceId = "";
    String resultIndex = "";
    String isLCC = "";
    static String otpId = "";
    static String otpnumber = "";
    String basePrice = "";
    String taxPrice = "";
    String totalPrice = "";
    String discount = "0";
    String promoCode = "";
    String promoDiscount = "";
    String promoIsApply = "";
    String sponsorFormNo = "";
    String formNo = "";
    String mobile = "";
    String email = "";
    String applyvoucher = "";
    String totAdult = "";
    String totChild = "";
    String totMem = "";
    String hotelName = "";
    String totRoom = "";
    String checkInDate = "";
    String checkOutDate = "";
    String city = "";

    ArrayList<HotelPassengerModel> pessengerInfoList;
    ArrayList<HotelPassengerModel> roompessengerInfoList;
    ArrayList<HotelPassengerModel> tempPassengerInfoList;
    ArrayList<HotelSearchResponse> selectHotelarrayList;
    HotelPassengerFooterModel passengerFooterInfo;
    ArrayList<HotelPassengerFooterModel> footerInfoArrayList;
    ArrayList<HotelBookRequest.HotelRoomPassenger> hotelPassengerArrayList;
    ArrayList<OnlineBookRoomsDetail.BookRoomPassenger> bookRoomPassengerArrayList;
    ArrayList<BlockRoomDetail> roomBlockArrayList;
    ArrayList<OnlineBookRoomsDetail> bookRoomsDetailArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_otp_and_hotel_payment);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view = findViewById(android.R.id.content);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Make Payment");


            rdgPaymentMode = (RadioGroup) findViewById(R.id.hotel_book_act_rdg_payment_method);
            rdbMainWallet = (RadioButton) findViewById(R.id.hotel_book_act_rdb_mainwallet);
            rdbE_Wallet = (RadioButton) findViewById(R.id.hotel_book_act_rdb_ewallet);
            txtMainBal = (TextView) findViewById(R.id.hotel_book_act_txt_main_bal);
            txtBalMsg = (TextView) findViewById(R.id.hotel_book_act_txt_bal_message);
            txtBookAmount = (TextView) findViewById(R.id.hotel_book_act_txt_tot_amount);
            txtDeductBal_Main = (TextView) findViewById(R.id.hotel_book_act_txt_main_bal_deduct);
            txtDeductBal_other = (TextView) findViewById(R.id.hotel_book_act_txt_bal_deduct_other);
            txtDeductBal_Promo = (TextView) findViewById(R.id.hotel_book_act_txt_bal_promo);
            txtClickHere = (TextView) findViewById(R.id.hotel_book_act_txt_clickhere);
            txtPromo = (TextView) findViewById(R.id.hotel_book_act_txt_promo);
            chBoxE_Wallet = (AppCompatCheckBox) findViewById(R.id.hotel_book_act_chbox_eWallet);
            chBoxMainWallet = (AppCompatCheckBox) findViewById(R.id.hotel_book_act_chbox_mainWallet);
            chBox_Promo = (AppCompatCheckBox) findViewById(R.id.hotel_book_act_chbox_promo);


            //layoutE_Wallet=(LinearLayout)findViewById(R.id.bus_book_act_layout_ewallet);
            layoutMainWallet = (LinearLayout) findViewById(R.id.hotel_book_act_layout_main_wallet);
            layoutMessage = (LinearLayout) findViewById(R.id.hotel_book_act_layout_message);
            layoutBtnotp = (LinearLayout) findViewById(R.id.hotel_book_act_layout_otp);
            layoutClickHere = (LinearLayout) findViewById(R.id.hotel_book_act_layout_clickhere);
            layChkBox_Ewallet = (LinearLayout) findViewById(R.id.hotel_book_act_layout_chbox_ewallt);
            layChkBox_Mwallet = (LinearLayout) findViewById(R.id.hotel_book_act_layout_chbox_main_wallet);
            layChkBox_Promo = (LinearLayout) findViewById(R.id.hotel_book_act_layout_chbox_promo);
            layoutPromoCode = (LinearLayout) findViewById(R.id.hotel_book_act_layout_promocode);

            btnOTP = (Button) findViewById(R.id.hotel_book_act_btn_otp);

            /* GEt value from bundle*/
            Bundle bundle=getIntent().getExtras();
           /* if(bundle != null){
                tempPassengerInfoList = new ArrayList<HotelPassengerModel>();
                tempPassengerInfoList= (ArrayList<HotelPassengerModel>) bundle.getSerializable("PassengerInfo");

            }*/
            /*Get Pessanger Detail Value form Shared Preference */
            tempPassengerInfoList = new ArrayList<HotelPassengerModel>();
            tempPassengerInfoList= HotelSharedValues.getInstance().hotelPassengerModelArrayList;
            //HotelSharedValues.getInstance().hotelPassengerModelArrayList.addAll(tempPassengerInfoList);

            //call api utility_main utility_wallet

            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            } else {
                getMainBalance();
            }



            //Passenger Detail get last index element from passenger info list and add in another list
            if (tempPassengerInfoList.size() > 0) {

                passengerFooterInfo = new HotelPassengerFooterModel();
                passengerFooterInfo.setMobile(tempPassengerInfoList.get(tempPassengerInfoList.size() - 1).getMobile());
                passengerFooterInfo.setGstno(tempPassengerInfoList.get(tempPassengerInfoList.size() - 1).getGstno());
                passengerFooterInfo.setGstemail(tempPassengerInfoList.get(tempPassengerInfoList.size() - 1).getGstemail());
                passengerFooterInfo.setCompanyName(tempPassengerInfoList.get(tempPassengerInfoList.size() - 1).getCompanyName());
                passengerFooterInfo.setCompanyAddress(tempPassengerInfoList.get(tempPassengerInfoList.size() - 1).getCompanyAddress());
                passengerFooterInfo.setContactno(tempPassengerInfoList.get(tempPassengerInfoList.size() - 1).getContactno());
                passengerFooterInfo.setEmail(tempPassengerInfoList.get(tempPassengerInfoList.size() - 1).getEmail());

                email = tempPassengerInfoList.get(tempPassengerInfoList.size() - 1).getEmail();
                mobile = tempPassengerInfoList.get(tempPassengerInfoList.size() - 1).getMobile();

                footerInfoArrayList = new ArrayList<HotelPassengerFooterModel>();
                footerInfoArrayList.addAll(Collections.singleton(passengerFooterInfo));


                /*remove last index element in passenger info list*/
                if (footerInfoArrayList != null && !footerInfoArrayList.isEmpty()) {
                    tempPassengerInfoList.remove(tempPassengerInfoList.size() - 1);
                    pessengerInfoList = new ArrayList<HotelPassengerModel>(tempPassengerInfoList);
                    roompessengerInfoList = new ArrayList<HotelPassengerModel>(tempPassengerInfoList);

                }

            }

            /*get total paid amount */
            if (HotelSharedValues.getInstance().totAmount > 0) {
                //totalPay= FlightSharedValues.getInstance().totPaidAmount;

                double totalFare = (int) HotelSharedValues.getInstance().totAmount;
                totalPay = (double) HotelSharedValues.getInstance().totAmount;
                totalPrice = String.valueOf(totalFare);
                txtBookAmount.setText(getResources().getString(R.string.rs_symbol) + " " + totalPrice);
            }
            /*get promo code and promo discount*/
            if (HotelSharedValues.getInstance().hotelPromocode.equals("") || HotelSharedValues.getInstance().hotelPromocode == null) {
                promoCode = "";
                //applyvoucher="false";
            } else {

                promoCode = HotelSharedValues.getInstance().hotelPromocode;
                //applyvoucher="true";
            }

            if (HotelSharedValues.getInstance().hotelPromoAmount.equals("") || HotelSharedValues.getInstance().hotelPromoAmount == null) {
                promoDiscount = "0";
                //applyvoucher="false";
            } else {
                //applyvoucher="true";
                promoDiscount = HotelSharedValues.getInstance().hotelPromoAmount;
            }

            discount= String.valueOf(HotelSharedValues.getInstance().totDiscount);

            totAdult = String.valueOf(HotelSharedValues.getInstance().totAdult);
            hotelName = HotelSharedValues.getInstance().hotelName;
            totChild = String.valueOf(HotelSharedValues.getInstance().totChild);
            totMem = String.valueOf(HotelSharedValues.getInstance().totMember);
            totRoom = String.valueOf(HotelSharedValues.getInstance().totRoom);
            checkInDate = HotelSharedValues.getInstance().chkInDate;
            checkOutDate = HotelSharedValues.getInstance().chkOutDate;
            city = HotelSharedValues.getInstance().city;

            /*Check Radion Button is check or not*/
            /* if(rdbE_Wallet.isChecked()) {

             *//*layoutE_Wallet.setVisibility(View.VISIBLE);
                layoutMainWallet.setVisibility(View.GONE);
                layoutBtnotp.setVisibility(View.GONE);*//*
            }
            else if(rdbMainWallet.isChecked()){
                //layoutE_Wallet.setVisibility(View.GONE);
                layoutMainWallet.setVisibility(View.VISIBLE);
                layoutBtnotp.setVisibility(View.VISIBLE);

            }*/

            /*Radio Button check change listener*/
            /*rdgPaymentMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    RadioButton rb = group.findViewById(checkedId);

                    String strRbtWallet=rb.getText().toString();
                    if(strRbtWallet.equals("Main Wallet")){

                        //layoutE_Wallet.setVisibility(View.GONE);
                        layoutMainWallet.setVisibility(View.VISIBLE);
                        layoutBtnotp.setVisibility(View.VISIBLE);

                    }
                    else if(strRbtWallet.equals("Pay Using E-Wallet")){
                        *//*layoutE_Wallet.setVisibility(View.VISIBLE);
                        layoutMainWallet.setVisibility(View.GONE);
                        layoutBtnotp.setVisibility(View.GONE);*//*


                    }
                }
            });*/

            /*Button Send Otp Click Listener*/
            btnOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    float discoutamt= Float.parseFloat(discount);
                    float promoDis = Float.parseFloat(promoDiscount);
                    double totCheckBal = mainBal + promoDis + discoutamt;
                    if (totCheckBal >= totalPay) {  // if(mainBal > totalPay){

                        layoutMessage.setVisibility(View.GONE);
                        txtBalMsg.setText("");

                        //Call otp api
                        /*Booking for otp process*/
                        //SendOtp();

                        /*Update code without otp*/
                        if(!ConnectivityUtils.isNetworkEnabled(OtpAndHotelPaymentActivity.this)){
                            Toast.makeText(OtpAndHotelPaymentActivity.this,getResources().getString(R.string.network_error),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {

                            showBookingDialog();
                        }
                    } else {
                        layoutMessage.setVisibility(View.VISIBLE);
                        txtBalMsg.setText("Wallet balance is low for proceed hotel book");
                    }


                }
            });

            /*Text Click here on click
             * go Add Fund Activity for
             * using e-utility_wallet, payment gateway*/
            txtClickHere.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OtpAndHotelPaymentActivity.this, AddFundActivity.class);
                    //String deductReqAmnt = String.valueOf(Math.round(deductAmount));
                    String deductReqAmnt = String.valueOf(deductAmount);
                    intent.putExtra("GW_Service", "Hotel");
                    intent.putExtra("Home", "false");
                    intent.putExtra("DeductAmount", deductReqAmnt);
                    intent.putExtra("ServiceType", "Hotel");
                    startActivityForResult(intent, AppConstants.Req_code_GW);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* Request and Response Get Main Balance*/
    public void getMainBalance() {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String strApiRequest = "";
            JSONObject object = null;
            LoginResponse loginResponse = new LoginResponse();
            loginResponse = new LoginPreferences_Utility(this).getLoggedinUser();
            String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
            String formno = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);
            String mobile = loginResponse.getMobileNo().substring(0, loginResponse.getMobileNo().length() - 2);
            String[] strResources = {"GDS", "FZ", "G8", "SG", "G9", "AK", "IX", "LB", "TR", "6E", "B3", "OP", "2T", "W5", "LV", "TZ", "ZO", "PY"};
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                String str = String.join(",", strResources);
                TravelSharedPreferance.setFlightSources(this, str);
            }

            JSONArray jsonArray = new JSONArray(Arrays.asList(strResources));
            String strToken = TokenBase64.getToken();

            /*Main Request Model*/
            ApiRequest apiRequest = new ApiRequest();

            try {
                /*Base Request Model*/
                BaseHeaderRequest headerRequest = new BaseHeaderRequest();
                headerRequest.setUserName(new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
                headerRequest.setPassword(new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
                headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/
                MainBalanceRequest request = new MainBalanceRequest();
                request.setFormNo(formno);

                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(request);

                strApiRequest = new Gson().toJson(apiRequest);

                Log.e("Value", strApiRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Call<BaseResponse> fetchFlightBook =
                    NetworkClient_Utility_1.getInstance(this).create(MainServices.class).fetchGetBalance(apiRequest, strToken);

            fetchFlightBook.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    try {

                        BaseResponse Response = new BaseResponse();
                        Response = response.body();

                        if(Response != null){
                            if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                if (!Response.getRESP_VALUE().equals("null") || !Response.getRESP_VALUE().equals("")) {

                                    MainBalanceResponse balanceResponse =
                                            new Gson().fromJson(Response.getRESP_VALUE(), MainBalanceResponse.class);

                                    //String toast= Response.getRESP_MSG();
                                    //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    if (balanceResponse != null) {
                                        // get Main Wallet balance
                                        new LoginPreferences_Utility(OtpAndHotelPaymentActivity.this).getLoggedinUser().setBalance(balanceResponse.getBalance());
                                        mainBal = Double.parseDouble(balanceResponse.getBalance());
                                        float discoutamt = Float.parseFloat(discount);
                                        float promo = Float.parseFloat(promoDiscount);
                                        double totCheckBal = mainBal + discoutamt + promo;
                                        txtMainBal.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(mainBal));

                                   /* if (totalPay <= totCheckBal) {
                                        layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                        layChkBox_Ewallet.setVisibility(View.GONE);
                                        deductAmount = mainBal - totalPay;
                                        txtMainBal.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(mainBal));
                                        txtDeductBal_Main.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(totalPay));

                                        chBoxMainWallet.setChecked(true);
                                        chBoxMainWallet.setEnabled(false);
                                        layoutClickHere.setVisibility(View.GONE);
                                        btnOTP.setVisibility(View.VISIBLE);

                                    }
                                    else if (totalPay > totCheckBal) {
                                        layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                        layChkBox_Ewallet.setVisibility(View.VISIBLE);
                                        deductAmount = totalPay - mainBal;
                                        txtMainBal.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(mainBal));
                                        txtDeductBal_Main.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(mainBal));
                                        txtDeductBal_other.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(deductAmount));
                                        chBoxMainWallet.setChecked(true);
                                        chBoxMainWallet.setEnabled(false);
                                        chBoxE_Wallet.setVisibility(View.VISIBLE);
                                        chBoxE_Wallet.setChecked(true);
                                        chBoxE_Wallet.setEnabled(false);
                                        //btnContinue.setVisibility(View.VISIBLE);
                                        layoutClickHere.setVisibility(View.VISIBLE);
                                        btnOTP.setVisibility(View.GONE);
                                    }*/

                                        //update
                                        if(promo > 0){
                                            layoutPromoCode.setVisibility(View.VISIBLE);
                                            double payAmount=totalPay-promo;
                                            txtPromo.setText(promoCode);
                                            if(payAmount <= mainBal){
                                                layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                                layChkBox_Promo.setVisibility(View.VISIBLE);
                                                layChkBox_Ewallet.setVisibility(View.GONE);
                                                deductAmount=payAmount;
                                                txtMainBal.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                                txtDeductBal_Main.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(payAmount));
                                                txtDeductBal_Promo.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(promo));

                                                chBoxMainWallet.setChecked(true);
                                                chBoxMainWallet.setEnabled(false);
                                                chBox_Promo.setEnabled(false);
                                                chBox_Promo.setChecked(true);

                                                layoutClickHere.setVisibility(View.GONE);
                                                btnOTP.setVisibility(View.VISIBLE);

                                            }
                                            else {
                                                layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                                layChkBox_Ewallet.setVisibility(View.VISIBLE);
                                                layChkBox_Promo.setVisibility(View.VISIBLE);
                                                deductAmount=payAmount-mainBal;
                                                txtMainBal.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                                txtDeductBal_Main.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                                txtDeductBal_other.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(deductAmount));
                                                txtDeductBal_Promo.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(promo));
                                                chBoxMainWallet.setChecked(true);
                                                chBoxMainWallet.setEnabled(false);
                                                chBoxE_Wallet.setVisibility(View.VISIBLE);
                                                chBoxE_Wallet.setChecked(true);
                                                chBoxE_Wallet.setEnabled(false);
                                                chBox_Promo.setEnabled(false);
                                                chBox_Promo.setChecked(true);
                                                //btnContinue.setVisibility(View.VISIBLE);
                                                layoutClickHere.setVisibility(View.VISIBLE);
                                                btnOTP.setVisibility(View.GONE);
                                            }
                                        }
                                        else {
                                            layoutPromoCode.setVisibility(View.GONE);
                                            layChkBox_Promo.setVisibility(View.GONE);
                                            txtPromo.setText("");
                                            txtDeductBal_Promo.setText("");
                                            if(totalPay <= mainBal){
                                                layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                                layChkBox_Ewallet.setVisibility(View.GONE);
                                                deductAmount=mainBal-totalPay;
                                                txtMainBal.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                                txtDeductBal_Main.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(totalPay));

                                                chBoxMainWallet.setChecked(true);
                                                chBoxMainWallet.setEnabled(false);
                                                layoutClickHere.setVisibility(View.GONE);
                                                btnOTP.setVisibility(View.VISIBLE);

                                            }
                                            else if(totalPay > mainBal){
                                                layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                                layChkBox_Ewallet.setVisibility(View.VISIBLE);
                                                deductAmount=totalPay-mainBal;
                                                txtMainBal.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                                txtDeductBal_Main.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                                txtDeductBal_other.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(deductAmount));
                                                chBoxMainWallet.setChecked(true);
                                                chBoxMainWallet.setEnabled(false);
                                                chBoxE_Wallet.setVisibility(View.VISIBLE);
                                                chBoxE_Wallet.setChecked(true);
                                                chBoxE_Wallet.setEnabled(false);
                                                //btnContinue.setVisibility(View.VISIBLE);
                                                layoutClickHere.setVisibility(View.VISIBLE);
                                                btnOTP.setVisibility(View.GONE);
                                            }
                                        }
                                    }

                                }
                                else if (Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")) {
                                    String toast = Response.getRESP_MSG();

                                    Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                            .show();

                                }

                            }
                            else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Try Again ";
                                //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();
                            }
                            else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                Toast.makeText(OtpAndHotelPaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OtpAndHotelPaymentActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(OtpAndHotelPaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Request and Response BillPay Send Otp*/
    public void SendOtp() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String strApiRequest = "";
        JSONObject object = null;
        LoginResponse loginResponse = new LoginResponse();
        loginResponse = new LoginPreferences_Utility(this).getLoggedinUser();

        String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
        String formno = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);
        String mobile = loginResponse.getMobileNo().substring(0, loginResponse.getMobileNo().length() - 2);

        String strToken = TokenBase64.getToken();

        /*Main Request Model*/
        ApiRequest apiRequest = new ApiRequest();

        try {

            /*Base Request Model*/
            BaseHeaderRequest headerRequest = new BaseHeaderRequest();
            headerRequest.setUserName(new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
            headerRequest.setPassword(new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
            headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/

            /* Send Otp Request Model*/

            SendOtpRequest sendOtpRequest = new SendOtpRequest();
            sendOtpRequest.setEmailID(new LoginPreferences_Utility(this).getLoggedinUser().getEmailId());
            sendOtpRequest.setFormNo(formno);
            sendOtpRequest.setMobileNo(mobile);
            sendOtpRequest.setSponsorFormNo(companyId);
            sendOtpRequest.setServiceName("H");
            sendOtpRequest.setUserName(new LoginPreferences_Utility(this).getLoggedinUser().getUserName());

            /*Set Value in Main Request Model*/
            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(sendOtpRequest);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<BaseResponse> fetchFlightOtp =
                NetworkClient_Utility_1.getInstance(this).create(FlightApi.class).fetchFlightBookOtp(apiRequest, strToken);
        fetchFlightOtp.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    BaseResponse Response = new BaseResponse();
                    Response = response.body();

                    if (Response != null) {
                        if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                            if (!Response.getRESP_VALUE().equals("null") || !Response.getRESP_VALUE().equals("")) {

                                // edTxtOtp.setEnabled(true);
                                String strResponse = Response.getRESP_VALUE();
                                otpId = strResponse;

                                HotelSharedValues.getInstance().hotelBookOtpID = otpId;

                                SharedPrefrence_Utility.setOtpCode(OtpAndHotelPaymentActivity.this, otpId);

                                //Intent intent=new Intent(OtpAndBusBookPaymentActivity.this, OtpVerify_BusBookActivity.class);
                                Intent intent = new Intent(OtpAndHotelPaymentActivity.this, VerifyOtpActivity.class);


                                intent.putExtra("OtpService", "Hotel");
                                startActivityForResult(intent, AppConstants.Req_code_otp);
                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);


                            } else if (Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")) {
                                String toast = Response.getRESP_MSG();
                                //edTxtOtp.setEnabled(false);
                                //Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();
                            }

                        }
                        else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {
                            //edTxtOtp.setEnabled(false);
                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            //Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        }
                        else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                            String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                            Toast.makeText(OtpAndHotelPaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(OtpAndHotelPaymentActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(OtpAndHotelPaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
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

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });


    }

    /* Request and Response Hotel Online Booking*/
    public void getHotelRoomBookOnline() {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait hotel booking is processing");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String strApiRequest = "";
            JSONObject object = null;
            LoginResponse loginResponse = new LoginResponse();
            loginResponse = new LoginPreferences_Utility(this).getLoggedinUser();
            String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
            String formno = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);
            String mobileno = loginResponse.getMobileNo().substring(0, loginResponse.getMobileNo().length() - 2);

            String strToken = TokenBase64.getToken();

            /*Main Request Model*/
            ApiRequest apiRequest = new ApiRequest();
            try {
                try {

                    /*Base Request Model*/
                    BaseHeaderRequest headerRequest = new BaseHeaderRequest();
                    headerRequest.setUserName(new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
                    headerRequest.setPassword(new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
                    headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/

                    /* Hotel Book Request Model*/

                    HotelBookOnlineRequest hotelBookRequest = new HotelBookOnlineRequest();
                    hotelBookRequest.setAdultCount(totAdult);
                    hotelBookRequest.setCheckInDate(checkInDate);
                    hotelBookRequest.setCheckOutDate(checkOutDate);
                    hotelBookRequest.setChildCount(totChild);
                    hotelBookRequest.setDiscount(discount);
                    if (!new LoginPreferences_Utility(this).getLoggedinUser().getEmailId().contentEquals(""))
                        hotelBookRequest.setEmailID(new LoginPreferences_Utility(this).getLoggedinUser().getEmailId());
                    else
                        hotelBookRequest.setEmailID(email);

                    hotelBookRequest.setFormNo(formno);
                    if (mobileno.contentEquals(""))
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

                    strApiRequest = new Gson().toJson(apiRequest);

                    Log.e("Value", strApiRequest);

                } catch (Exception e) {
                    e.printStackTrace();
                }

               /* Call<BaseResponse> fetchHotelBook =
                        NetworkClient_Utility_1.getInstance(this).create(HotelApi.class).fetchHotelRoomBookOnline(apiRequest, strToken);

                fetchHotelBook.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        try {

                            BaseResponse Response = new BaseResponse();
                            Response = response.body();

                            if (Response != null) {
                                if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                    if (Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")) {
                                        String toast = Response.getRESP_MSG();

                                        Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                                .setAction("CLOSE", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                    }
                                                })
                                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                                .show();

                                    } else if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {

                                        HotelOnlineBookResponse successResponse =
                                                new Gson().fromJson(Response.getRESP_VALUE(), HotelOnlineBookResponse.class);

                                        if (successResponse != null) {

                                            if (successResponse.getStatus().contentEquals("1") && successResponse.getErrorCode().contentEquals("0")) {
                                                Intent intent = new Intent(OtpAndHotelPaymentActivity.this, HotelBookSuccessMsgActivity.class);

                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("SuccessBook", successResponse);
                                                bundle.putString("Msg", Response.getRESP_MSG());
                                                bundle.putString("Book", "Online");
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            } else if (successResponse.getStatus().contentEquals("2") && successResponse.getErrorCode().contentEquals("2")) {

                                                String toast = successResponse.getErrorMessage();
                                                //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                                        .setAction("CLOSE", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {

                                                            }
                                                        })
                                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                                        .show();

                                            } else {

                                                String toast = successResponse.getErrorMessage();
                                                //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                                        .setAction("CLOSE", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {

                                                            }
                                                        })
                                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                                        .show();

                                            }

                                        }

                                    }

                                }

                                else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {
                                    String toast = Response.getRESPONSE() + "\n" + "Something wrong please try again ";
                                    //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                            .show();
                                }
                                else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("FAILED")) {
                                    String toast = Response.getRESPONSE() + "\n" + "Time Expire Please Try Again ";
                                    //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                            .show();
                                }
                                else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                    String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                    Toast.makeText(OtpAndHotelPaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(OtpAndHotelPaymentActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                }
                                else {

                                    String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                    Toast.makeText(OtpAndHotelPaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    }
                });*/
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*Get Pessagnger Json Array*/
    private ArrayList<HotelBookRequest.HotelRoomPassenger> passengersItem1() {
        JSONArray jSonArray = new JSONArray();
        JsonArray jsonArray = new JsonArray();
        // int listsize=pessengerInfoArrayList.size();

        hotelPassengerArrayList = new ArrayList<HotelBookRequest.HotelRoomPassenger>();
        ArrayList<HotelBookRequest.HotelRoomPassenger> passengerArrayList = new ArrayList<HotelBookRequest.HotelRoomPassenger>();
        Collection<FlightBookRequest.FlightPassenger> enums = null;

        if (pessengerInfoList != null && !pessengerInfoList.isEmpty()) {
            for (int i = 0; i < pessengerInfoList.size(); i++) {
                HotelBookRequest.HotelRoomPassenger hotelRoomPassenger = new HotelBookRequest.HotelRoomPassenger();

                if (pessengerInfoList.get(i).getPessengerType().equals("A")) {
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
                } else if (pessengerInfoList.get(i).getPessengerType().equals("C")) {
                    HotelBookRequest.HotelRoomPassenger hotelRoomPassenger2 = new HotelBookRequest.HotelRoomPassenger();

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


        passengerArrayList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<HotelBookRequest.HotelRoomPassenger>>() {
        }.getType());


        return passengerArrayList;
    }

    /*Get Hotel Room Json Array*/
    private ArrayList<OnlineBookRoomsDetail> hotelRoomDetail() {
        ArrayList<OnlineBookRoomsDetail> bookRoomsDetailList = new ArrayList<OnlineBookRoomsDetail>();
        try {
            JSONArray jSonArray = new JSONArray();
            JsonArray jsonArray = new JsonArray();

            /*Get Block Room list value form Hotel Shared and assign in list */
            roomBlockArrayList = new ArrayList<BlockRoomDetail>();
            roomBlockArrayList = HotelSharedValues.getInstance().blockroomDetailList;

            bookRoomsDetailArrayList = new ArrayList<OnlineBookRoomsDetail>();

            if (roomBlockArrayList != null && !roomBlockArrayList.isEmpty()) {
                for (int i = 0; i < roomBlockArrayList.size(); i++) {
                    String room = String.valueOf(i + 1);
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
            bookRoomsDetailList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<OnlineBookRoomsDetail>>() {
            }.getType());


        } catch (Exception e) {
        e.printStackTrace();
        }
        return bookRoomsDetailList;
    }

    /*Get Pessagnger Json Array*/
    private ArrayList<OnlineBookRoomsDetail.BookRoomPassenger> bookRoompassengers(String noofRoom) {

        JsonArray jsonArray = new JsonArray();

        ArrayList<HotelPassengerModel> pessInfoList = new ArrayList<HotelPassengerModel>();
        if (roompessengerInfoList.size() > 0 && roompessengerInfoList != null) {
            pessInfoList = roompessengerInfoList;
        }

        /*Get Hotel Room Model Value from Hotel Shared  and assign in Arraylist*/
        ArrayList<HotelRoomModel> hotelRoomArrayList = new ArrayList<HotelRoomModel>();
        hotelRoomArrayList = HotelSharedValues.getInstance().hotelRoomInfoListShared;

        bookRoomPassengerArrayList = new ArrayList<OnlineBookRoomsDetail.BookRoomPassenger>();
        ArrayList<OnlineBookRoomsDetail.BookRoomPassenger> passengerArrayList = new ArrayList<OnlineBookRoomsDetail.BookRoomPassenger>();

        if (pessInfoList != null && hotelRoomArrayList != null) {


            for (int h = 0; h < hotelRoomArrayList.size(); h++) {
                String roomNo = hotelRoomArrayList.get(h).getRoom();
                if (noofRoom.contentEquals(roomNo)) {
                    int totAdult = hotelRoomArrayList.get(h).getAdultcount();
                    int totChild = hotelRoomArrayList.get(h).getChildcount();
                    int tot = totAdult + totChild;
                    int adultIncrement = 0;
                    int childIncrement = 0;

                    if (tot > 0) {
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


                                        if(roompessengerInfoList != null && roompessengerInfoList.size() > 0){
                                            if(roompessengerInfoList != null && roompessengerInfoList.size()==1){
                                                final HotelPassengerModel remove = roompessengerInfoList.get(0);
                                                roompessengerInfoList.remove(remove);
                                                roompessengerInfoList.size();
                                            }
                                            else {
                                                final HotelPassengerModel remove = roompessengerInfoList.get(i);
                                                roompessengerInfoList.remove(remove);
                                                roompessengerInfoList.size();
                                            }

                                        }
                                        adultIncrement = j + 1;
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


                                        if(roompessengerInfoList != null && roompessengerInfoList.size() > 0){
                                            if(roompessengerInfoList != null && roompessengerInfoList.size()==1){
                                                final HotelPassengerModel remove = roompessengerInfoList.get(0);
                                                roompessengerInfoList.remove(remove);
                                                roompessengerInfoList.size();
                                            }
                                            else {
                                                final HotelPassengerModel remove = roompessengerInfoList.get(i);
                                                roompessengerInfoList.remove(remove);
                                                roompessengerInfoList.size();
                                            }

                                        }
                                        //roompessengerInfoList.remove(remove);
                                        childIncrement = c + 1;
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

        passengerArrayList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<OnlineBookRoomsDetail.BookRoomPassenger>>() {
        }.getType());

        return passengerArrayList;
    }

    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        return true;
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            onBackPressed();
            *//*if(progressDialog.isShowing())
                progressDialog.dismiss();
            fetchFlightSearch.cancel();*//*
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
       /* if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }*/
        super.onBackPressed();
        finish();
        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }

    /*Show alet dialog box
     * for asking before Hotel
     * room book.*/
    void showBookingDialog() {
        try {
            AlertDialogUtils.showDialogWithTwoButton(OtpAndHotelPaymentActivity.this, new AlertDialogButtonListener() {
                @Override
                public void onButtontapped(String btnText) {
                    if (btnText.equalsIgnoreCase("yes")) {
                        getHotelRoomBookOnline();
                    }
                }
            }, "Hotel Room Book", "Do you want to continue process for book hotel room?", "No", "Yes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == AppConstants.Req_code_otp) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    String msg = bundle.getString("Msg");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            assert msg != null;
                            if (msg.contentEquals("OK")) {
                                // call api Flight book

                                float discoutamt = Float.parseFloat(discount);
                                float promo = Float.parseFloat(promoDiscount);
                                double totCheckBal = mainBal + discoutamt + promo;
                                if (totCheckBal >= totalPay) {

                                    getHotelRoomBookOnline();
                                } else {

                                }
                            } else {
                            }

                        }
                    }, 500);

                }
            } else {


            }
        } else if (reqCode == AppConstants.Req_code_GW) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    String msg = bundle.getString("Msg");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            assert msg != null;
                            if (msg.contentEquals("OK")) {
                                getHotelRoomBookOnline();
                            } else {
                            }

                        }
                    }, 500);

                }
            } else {


            }
        }


    }
}
