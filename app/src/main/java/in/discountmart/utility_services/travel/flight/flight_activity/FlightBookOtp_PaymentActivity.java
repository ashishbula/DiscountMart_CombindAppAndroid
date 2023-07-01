package in.discountmart.utility_services.travel.flight.flight_activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
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

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import in.base.network.NetworkClient_Utility_1;
import in.base.ui.BaseActivity;
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
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PassengerFooterInfo;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PessengerInfo;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightBookSuccessResponse;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightSearchResponse;
import in.discountmart.utility_services.travel.flight.flight_sharedpreference.FlightSharedValues;
import in.discountmart.utility_services.travel.travel_sharedpreferance.TravelSharedPreferance;
import in.discountmart.utility_services.utilities.AlertDialogUtils;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.DateUtilities;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightBookOtp_PaymentActivity extends BaseActivity {

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
    TextView txtPromoCode;
    EditText edTxtTransactionPass;
    EditText edTxtAvailblefund;
    EditText edTxtRequestfund;
    Spinner spinnerFundFrom;
    Button btnOTP;
    Button btnCheckFund;
    Button btnRequestfund;
    AppCompatCheckBox chBoxMainWallet;
    AppCompatCheckBox chBoxE_Wallet;
    AppCompatCheckBox chBox_Promo;

    LinearLayout layoutMainWallet;
    LinearLayout layoutE_Wallet;
    LinearLayout layoutMessage;
    LinearLayout layoutBtnotp;
    SwipeRefreshLayout refreshLayout;
    LinearLayout layoutClickHere;
    LinearLayout layChkBox_Mwallet;
    LinearLayout layChkBox_Ewallet;
    LinearLayout layChkBox_Promo;
    LinearLayout layoutPromoCode;
    ProgressDialog progressDialog;
    View view;

    double mainBal = 0;
    double totalPay = 0;
    double deductAmount = 0;
    static String otpId="";
    String discount="";
    String promoCode="";
    String promoDiscount="";
    String traceId="";
    String resultIndex="";
    String isLCC="";
    String basePrice="";
    String taxPrice="";
    String applyvoucher="";


    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    BroadcastReceiver receiver;

    ArrayList<PessengerInfo> pessengerInfoList;
    ArrayList<PessengerInfo> tempPassengerInfoList;
    ArrayList<FlightSearchResponse> selectFlightarrayList;
    PassengerFooterInfo passengerFooterInfo;
    ArrayList<PassengerFooterInfo>footerInfoArrayList;
    ArrayList<FlightBookRequest.FlightPassenger> flightPassengerArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_flight_book_payment);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view=findViewById(android.R.id.content);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Make Payment");

            rdgPaymentMode=(RadioGroup)findViewById(R.id.flight_book_act_rdg_payment_method);
            rdbMainWallet=(RadioButton) findViewById(R.id.flight_book_act_rdb_mainwallet);
            rdbE_Wallet=(RadioButton) findViewById(R.id.flight_book_act_rdb_ewallet);
            txtMainBal=(TextView) findViewById(R.id.flight_book_act_txt_main_bal);
            txtBookAmount=(TextView) findViewById(R.id.flight_book_act_txt_tot_amount);
            txtDeductBal_Main=(TextView) findViewById(R.id.flight_book_act_txt_main_bal_deduct);
            txtDeductBal_other=(TextView) findViewById(R.id.flight_book_act_txt_bal_deduct_other);
            txtDeductBal_Promo=(TextView) findViewById(R.id.flight_book_act_txt_bal_promo);
            txtPromoCode=(TextView) findViewById(R.id.flight_book_act_txt_promo);
            txtClickHere=(TextView)findViewById(R.id.flight_book_act_txt_clickhere);
            //edTxtTransactionPass=(EditText) findViewById(R.id.flight_book_act_edtxt_trans_pass);
            //edTxtAvailblefund=(EditText) findViewById(R.id.flight_book_act_edtxt_avail_amount);
            //edTxtRequestfund=(EditText) findViewById(R.id.flight_book_act_edtxt_request_amount);
            //spinnerFundFrom=(Spinner) findViewById(R.id.flight_book_act_spinner_fund_from);
            txtBalMsg=(TextView)findViewById(R.id.flight_book_act_txt_bal_message);
            refreshLayout=(SwipeRefreshLayout)findViewById(R.id.flight_book_act_layout_refresh);
            chBoxE_Wallet=(AppCompatCheckBox)findViewById(R.id.flight_book_act_chbox_eWallet);
            chBoxMainWallet=(AppCompatCheckBox)findViewById(R.id.flight_book_act_chbox_mainWallet);
            chBox_Promo=(AppCompatCheckBox)findViewById(R.id.flight_book_act_chbox_promo);

            btnOTP=(Button) findViewById(R.id.flight_book_act_btn_otp);
            //btnCheckFund=(Button) findViewById(R.id.flight_book_act_btn_validate);
           // btnRequestfund=(Button) findViewById(R.id.flight_book_act_btn_fund_request);

            //layoutE_Wallet=(LinearLayout)findViewById(R.id.flight_book_act_layout_ewallet);
            layoutMainWallet=(LinearLayout)findViewById(R.id.flight_book_act_layout_main_wallet);
            layoutMessage=(LinearLayout)findViewById(R.id.flight_book_act_layout_message);
            layoutBtnotp=(LinearLayout)findViewById(R.id.flight_book_act_layout_otp);
            layoutClickHere=(LinearLayout)findViewById(R.id.flight_book_act_layout_clickhere);
            layChkBox_Ewallet=(LinearLayout)findViewById(R.id.flight_book_act_layout_chbox_ewallt);
            layChkBox_Mwallet=(LinearLayout)findViewById(R.id.flight_book_act_layout_chbox_main_wallet);
            layChkBox_Promo=(LinearLayout)findViewById(R.id.flight_book_act_layout_chbox_promo);
            layoutPromoCode=(LinearLayout)findViewById(R.id.flight_book_act_layout_promocode);

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

            //call api utility_main utility_wallet
            if(!ConnectivityUtils.isNetworkEnabled(this)){
                showSnackbar(getResources().getString(R.string.network_error));
            }
            else {
                getMainBalance();
            }

            /*
             * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
             * performs a swipe-to-refresh gesture call Main Balance api.
             */
            refreshLayout.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {

                            // This method performs the actual data-refresh operation.
                            // The method calls setRefreshing(false) when it's finished.
                            getMainBalance();
                        }
                    }
            );


            /*get total paid amount */
            if(FlightSharedValues.getInstance().totPaidAmount > 0){
                totalPay= FlightSharedValues.getInstance().totPaidAmount;
                txtBookAmount.setText(getResources().getString(R.string.rs_symbol)+""+ String.valueOf(FlightSharedValues.getInstance().totPaidAmount));
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

            /*Check Radion Button is check or not*//*
            if(rdbE_Wallet.isChecked()) {

                layoutE_Wallet.setVisibility(View.VISIBLE);
                layoutMainWallet.setVisibility(View.GONE);
                layoutBtnotp.setVisibility(View.GONE);
            }
            else if(rdbMainWallet.isChecked()){
                layoutE_Wallet.setVisibility(View.GONE);
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

                        layoutE_Wallet.setVisibility(View.GONE);
                        layoutMainWallet.setVisibility(View.VISIBLE);
                        layoutBtnotp.setVisibility(View.VISIBLE);

                    }
                    else if(strRbtWallet.equals("Pay Using E-Wallet")){
                        layoutE_Wallet.setVisibility(View.VISIBLE);
                        layoutMainWallet.setVisibility(View.GONE);
                        layoutBtnotp.setVisibility(View.GONE);


                    }
                }
            });*/

            /*Button Send Otp Click Listener*/
            btnOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    double discoutamt= Double.parseDouble(discount);
                    double promoDis= Double.parseDouble(promoDiscount);
                    //float totCheckBal= mainBal+discoutamt+promoDis;
                    double totCheckBal = mainBal + promoDis + discoutamt;
                    if(totCheckBal >= totalPay){  // if(mainBal > totalPay){

                        layoutMessage.setVisibility(View.GONE);
                        txtBalMsg.setText("");
                       /*For otp process*/
                       // flightBookSendOtp();

                        /*Update code without otp*/
                        if(!ConnectivityUtils.isNetworkEnabled(FlightBookOtp_PaymentActivity.this)){
                            Toast.makeText(FlightBookOtp_PaymentActivity.this,getResources().getString(R.string.network_error),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {

                            showBookingDialog();
                        }

                    }

                    else{
                        layoutMessage.setVisibility(View.VISIBLE);
                        txtBalMsg.setText("Wallet Balance is Low for Proceed Flight Book");
                    }


                }
            });

            /*Text Click here on click
             * go Add Fund Activity for
             * using e-utility_wallet, payment gateway*/
            txtClickHere.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(FlightBookOtp_PaymentActivity.this, AddFundActivity.class);
                    String deductReqAmnt= String.valueOf(deductAmount);
                    intent.putExtra("GW_Service","Flight");
                    intent.putExtra("Home","false");
                    intent.putExtra("DeductAmount",deductReqAmnt);
                    intent.putExtra("ServiceType","Flight");
                    startActivityForResult(intent, AppConstants.Req_code_GW);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* Request and Response Get Main Balance*/
    public void getMainBalance(){
        try {
            showProgressDialog();
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
                    hideProgressDialog();
                    if(refreshLayout.isRefreshing())
                        refreshLayout.setRefreshing(false);
                    try {

                        BaseResponse Response =new BaseResponse();
                        Response=response.body();

                        if(Response != null){
                            if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                if(!Response.getRESP_VALUE().equals("null") || ! Response.getRESP_VALUE().equals("")){

                                    MainBalanceResponse balanceResponse=
                                            new Gson().fromJson(Response.getRESP_VALUE(),MainBalanceResponse.class);

                                    if(balanceResponse != null){
                                        // get Main Wallet balance
                                        new LoginPreferences_Utility(FlightBookOtp_PaymentActivity.this).getLoggedinUser().setBalance(balanceResponse.getBalance());
                                        mainBal= Double.parseDouble(balanceResponse.getBalance());

                                        double discoutamt= Double.parseDouble(discount);
                                        double promoDis= Double.parseDouble(promoDiscount);
                                        double totCheckBal= mainBal+discoutamt+promoDis;

                                    /*if(totalPay <= totCheckBal){
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
                                    else if(totalPay > totCheckBal){
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
                                    } */

                                        //update
                                        if(promoDis > 0){
                                            double payAmount=totalPay-promoDis;
                                            //double d_Amount=mainBal-payAmount;
                                            String strDouble = String.format("%.3f", payAmount);

                                            layoutPromoCode.setVisibility(View.VISIBLE);

                                            txtPromoCode.setText(promoCode);
                                            if(payAmount <= mainBal){
                                                layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                                layChkBox_Promo.setVisibility(View.VISIBLE);
                                                layChkBox_Ewallet.setVisibility(View.GONE);
                                                //deductAmount=payAmount;
                                                deductAmount= Double.parseDouble(strDouble);
                                                txtMainBal.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                                txtDeductBal_Main.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(deductAmount));
                                                txtDeductBal_Promo.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(promoDis));

                                                chBoxMainWallet.setChecked(true);
                                                chBoxMainWallet.setEnabled(false);
                                                chBox_Promo.setEnabled(false);
                                                chBox_Promo.setChecked(true);
                                                layoutClickHere.setVisibility(View.GONE);
                                                btnOTP.setVisibility(View.VISIBLE);

                                            }
                                            else {
                                                layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                                layChkBox_Promo.setVisibility(View.VISIBLE);
                                                layChkBox_Ewallet.setVisibility(View.VISIBLE);
                                                //deductAmount=payAmount-mainBal;
                                                double d_Amount=payAmount-mainBal;
                                                String str_Double = String.format("%.3f", d_Amount);
                                                deductAmount= Double.parseDouble(str_Double);
                                                txtMainBal.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                                txtDeductBal_Main.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                                txtDeductBal_other.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(deductAmount));
                                                txtDeductBal_Promo.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(promoDis));

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
                                            txtDeductBal_Promo.setText("");
                                            txtPromoCode.setText("");
                                            if(totalPay <= mainBal){
                                                layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                                layChkBox_Ewallet.setVisibility(View.GONE);
                                                //deductAmount=mainBal-totalPay;
                                                double d_Amount=mainBal-totalPay;
                                                String str_Double = String.format("%.3f", d_Amount);
                                                deductAmount= Double.parseDouble(str_Double);

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
                                                //deductAmount=totalPay-mainBal;
                                                double d_Amount=totalPay-mainBal;
                                                String str_Double = String.format("%.3f", d_Amount);
                                                deductAmount= Double.parseDouble(str_Double);

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
                                else if(Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")){
                                    String toast= Response.getRESP_MSG();

                                    showSnackbar(toast);

                                }

                            }
                            else if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")){
                                String toast= Response.getRESPONSE()+ "\n" + "Please Try Again ";
                                //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                showSnackbar(toast);
                            }
                            else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(FlightBookOtp_PaymentActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                    hideProgressDialog();
                    if(refreshLayout.isRefreshing())
                        refreshLayout.setRefreshing(false);
                    showToast(t.getMessage());
                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* Request and Response Flight Book Send Otp*/
    public void flightBookSendOtp(){

            showProgressDialog();
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

                    /* Send Otp Request Model*/

                    SendOtpRequest sendOtpRequest=new SendOtpRequest();
                    sendOtpRequest.setEmailID(new LoginPreferences_Utility(this).getLoggedinUser().getEmailId());
                    sendOtpRequest.setFormNo(formno);
                    sendOtpRequest.setMobileNo(mobile);
                    sendOtpRequest.setServiceName("F");
                    sendOtpRequest.setSponsorFormNo(companyId);
                    sendOtpRequest.setUserName(new LoginPreferences_Utility(this).getLoggedinUser().getUserName());

                    /*Set Value in Main Request Model*/
                    apiRequest.setHEADER(headerRequest);
                    apiRequest.setDATA(sendOtpRequest);

                    strApiRequest=new Gson().toJson(apiRequest);

                    Log.e("Value",strApiRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Call<BaseResponse> fetchFlightOtp=
                        NetworkClient_Utility_1.getInstance(FlightBookOtp_PaymentActivity.this).create(FlightApi.class).fetchFlightBookOtp(apiRequest,strToken);
                fetchFlightOtp.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        hideProgressDialog();
                        try {

                            BaseResponse Response =new BaseResponse();
                            Response=response.body();

                            if(Response != null){
                                if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                    if(!Response.getRESP_VALUE().equals("null") || ! Response.getRESP_VALUE().equals("")){

                                        // edTxtOtp.setEnabled(true);
                                        String strResponse=Response.getRESP_VALUE();
                                        otpId=strResponse;

                                        FlightSharedValues.getInstance().flightBookOtpId=otpId;

                                  /*  Intent intent=new Intent(FlightBookOtp_PaymentActivity.this,OtpVerify_FlightBookActivity.class);
                                    intent.putExtra("OTPId",otpId);
                                    startActivity(intent);*/


                                        SharedPrefrence_Utility.setOtpCode(FlightBookOtp_PaymentActivity.this,otpId);

                                        //Intent intent=new Intent(OtpAndBusBookPaymentActivity.this, OtpVerify_BusBookActivity.class);
                                        Intent intent=new Intent(FlightBookOtp_PaymentActivity.this, VerifyOtpActivity.class);


                                        intent.putExtra("OtpService","Flight");
                                        startActivityForResult(intent, AppConstants.Req_code_otp);
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                                    }
                                    else if(Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")){
                                        String toast= Response.getRESP_MSG();
                                        //edTxtOtp.setEnabled(false);
                                        //Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                                        showSnackbar(toast);
                                    }

                                }
                                else if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")){
                                    //edTxtOtp.setEnabled(false);
                                    String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                    //Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    showSnackbar(toast);
                                }
                                else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                    String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                    Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(FlightBookOtp_PaymentActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                }
                                else {

                                    String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                    Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
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

                        hideProgressDialog();
                        showToast(t.getMessage());
                    }
                });


    }

    /* Request and Response Flight Booking*/
    public void getFlightBook(){
        try {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait for flight book");
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
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
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

                Call<BaseResponse> fetchFlightBook=
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
                                    /*Intent intent=new Intent(OtpVerify_FlightBookActivity.this,FlightBookSuccessMsgActivity.class);
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

                                        FlightBookSuccessResponse successResponse=
                                                new Gson().fromJson(Response.getRESP_VALUE(),FlightBookSuccessResponse.class);
                                        ArrayList<FlightBookSuccessResponse.PassengerDetail> passengerDetailArrayList=
                                                new ArrayList<FlightBookSuccessResponse.PassengerDetail>(successResponse.getPassanger());


                                        //String toast= Response.getRESP_MSG();
                                        //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                        if(successResponse != null && passengerDetailArrayList.size() > 0){
                                            Intent intent=new Intent(FlightBookOtp_PaymentActivity.this,FlightBookSuccessMsgActivity.class);

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
                                    Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(FlightBookOtp_PaymentActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                }
                                else {

                                    String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                    Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    // showSnackbar(toast);

                                }
                            }
                            else {
                                Toast.makeText(FlightBookOtp_PaymentActivity.this,"something wrong",Toast.LENGTH_SHORT).show();
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

                    /*Convert age to DOB*/
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

    /*Show alet dialog box
    * for asking before flight
    * seat book.*/
    void showBookingDialog() {
        try {
            AlertDialogUtils.showDialogWithTwoButton(FlightBookOtp_PaymentActivity.this, new AlertDialogButtonListener() {
                @Override
                public void onButtontapped(String btnText) {
                    if (btnText.equalsIgnoreCase("yes")) {
                        getFlightBook();
                    }
                }
            }, "Flight Seat Book", "Do you want to continue process for book flight seat?", "No", "Yes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        //openActivityFromTop();
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
        //overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if(reqCode==AppConstants.Req_code_otp){
            if(resultCode == Activity.RESULT_OK){
                Bundle bundle=data.getExtras();
                if(bundle != null){
                    String msg=bundle.getString("Msg");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            assert msg != null;
                            if(msg.contentEquals("OK")){
                                // call api Flight book

                                float discoutamt= Float.parseFloat(discount);
                                float promo= Float.parseFloat(promoDiscount);
                                double totCheckBal= mainBal+discoutamt+promo;
                                if(totCheckBal >= totalPay){

                                    getFlightBook();
                                }
                                else {

                                }
                            }
                            else {
                            }

                        }
                    }, 500);

                }
            }
            else {


            }
        }
        else if(reqCode==AppConstants.Req_code_GW){
            if(resultCode == Activity.RESULT_OK){
                Bundle bundle=data.getExtras();
                if(bundle != null){
                    String msg=bundle.getString("Msg");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            assert msg != null;
                            if(msg.contentEquals("OK")){
                                // call api Flight book
                                getFlightBook();
                            }
                            else {
                            }

                        }
                    }, 500);

                }
            }
            else {


            }
        }



    }



}
