package in.discountmart.utility_services.travel.bus.bus_activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

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
import in.discountmart.utility_services.utilities.AlertDialogUtils;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpAndBusBookPaymentActivity extends AppCompatActivity {

    RadioGroup rdgPaymentMode;
    RadioButton rdbMainWallet;
    AppCompatCheckBox chBoxMainWallet;
    AppCompatCheckBox chBoxE_Wallet;
    AppCompatCheckBox chBox_Promo;
    RadioButton rdbE_Wallet;
    TextView txtMainBal;
    TextView txtDeductBal_other;
    TextView txtDeductBal_Main;
    TextView txtDeductBal_Promo;
    TextView txtClickHere;
    TextView txtBookAmount;
    TextView txtPromoCode;
    TextView txtBalMsg;
    Button btnOTP;
    Button btnContinue;
    LinearLayout layoutMainWallet;
    LinearLayout layoutMessage;
    LinearLayout layoutBtnotp;
    LinearLayout layoutClickHere;
    LinearLayout layChkBox_Mwallet;
    LinearLayout layChkBox_Ewallet;
    LinearLayout layChkBox_Promo;
    LinearLayout layoutPromoCode;
    View view;

    ProgressDialog progressDialog;

    double mainBal=0;
    double deductAmount=0;
    double totalPay=0;
    static String otpId="";
    String totalPrice="";
    String discount="";
    String promoCode="";
    String promoDiscount="";


    String availableTripId="";
    String boardPointId="";
    String datOfJourney="";
    String fromCityId="";
    String destinationId="";
    String resultIndex="";
    String isLCC="";

    static String otpnumber="";
    String basePrice="";
    String taxPrice="";
    String promoIsApply="";
    String sponsorFormNo="";
    String formNo="";
    String mobile="";
    String email="";
    String strOtp="";
    String applyvoucher="";

    ArrayList<BusPassengerModel> pessengerInfoList;
    ArrayList<BusPassengerModel> tempPassengerInfoList;
    ArrayList<BusSearchListResponse> selectBusArrayList;
    ArrayList<BusSeatModel> busSeatModelArrayList;
    PassengerFooterInfo passengerFooterInfo;
    ArrayList<PassengerFooterInfo>footerInfoArrayList;
    ArrayList<BusBookRequest.InventoryItemsList> busInventoryArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_otp_and_busbook_payment);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view=findViewById(android.R.id.content);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Make Payment");


           // rdgPaymentMode=(RadioGroup)findViewById(R.id.bus_book_act_rdg_payment_method);
           // rdbMainWallet=(RadioButton) findViewById(R.id.bus_book_act_rdb_mainwallet);
            //rdbE_Wallet=(RadioButton) findViewById(R.id.bus_book_act_rdb_ewallet);
            txtMainBal=(TextView) findViewById(R.id.bus_book_act_txt_main_bal);
            txtDeductBal_Main=(TextView) findViewById(R.id.bus_book_act_txt_main_bal_deduct);
            txtDeductBal_other=(TextView) findViewById(R.id.bus_book_act_txt_bal_deduct_other);
            txtDeductBal_Promo=(TextView) findViewById(R.id.bus_book_act_txt_promo_bal);
            txtBalMsg=(TextView)findViewById(R.id.bus_book_act_txt_bal_message);
            txtBookAmount=(TextView)findViewById(R.id.bus_book_act_txt_tot_amount);
            txtClickHere=(TextView)findViewById(R.id.bus_book_act_txt_clickhere);
            txtPromoCode=(TextView)findViewById(R.id.bus_book_act_txt_promo);
            chBoxE_Wallet=(AppCompatCheckBox)findViewById(R.id.bus_book_act_chbox_eWallet);
            chBoxMainWallet=(AppCompatCheckBox)findViewById(R.id.bus_book_act_chbox_mainWallet);
            chBox_Promo=(AppCompatCheckBox)findViewById(R.id.bus_book_act_chbox_promo);


            //layoutE_Wallet=(LinearLayout)findViewById(R.id.bus_book_act_layout_ewallet);
            layoutMainWallet=(LinearLayout)findViewById(R.id.bus_book_act_layout_main_wallet);
            layoutMessage=(LinearLayout)findViewById(R.id.bus_book_act_layout_message);
            layoutBtnotp=(LinearLayout)findViewById(R.id.bus_book_act_layout_otp);
            layoutClickHere=(LinearLayout)findViewById(R.id.bus_book_act_layout_clickhere);
            layChkBox_Ewallet=(LinearLayout)findViewById(R.id.bus_book_act_layout_chbox_ewallt);
            layChkBox_Mwallet=(LinearLayout)findViewById(R.id.bus_book_act_layout_chbox_main_wallet);
            layChkBox_Promo=(LinearLayout)findViewById(R.id.bus_book_act_layout_chbox_promo);
            layoutPromoCode=(LinearLayout)findViewById(R.id.bus_book_act_layout_promocode);
            btnOTP=(Button)findViewById(R.id.bus_book_act_btn_otp);
            //btnContinue=(Button)findViewById(R.id.bus_book_act_btn_continue);

            /*Get Intent Value from previous activity*/
            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                ArrayList<BusPassengerModel> tempInfoList=new ArrayList<BusPassengerModel>();
                tempInfoList=(ArrayList<BusPassengerModel>) bundle.getSerializable("PassengerInfo");
                tempPassengerInfoList=new ArrayList<BusPassengerModel>();
                tempPassengerInfoList= tempInfoList;
                BusSharedValues.getInstance().buspessengerInfoList=tempPassengerInfoList;

            }

            /*Get Pessanger Detail Value form Shared Preference */
           // tempPassengerInfoList=new ArrayList<BusPassengerModel>();
            //ArrayList<BusPassengerModel> tempInfoList=new ArrayList<BusPassengerModel>();
            //tempInfoList=BusSharedValues.getInstance().buspessengerInfoList;
            //tempPassengerInfoList= tempInfoList;

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


            /*get total paid amount */
            if(BusSharedValues.getInstance().TotalFare > 0){
                //totalPay= FlightSharedValues.getInstance().totPaidAmount;

                int totalFare= (int) BusSharedValues.getInstance().TotalFare;
                totalPay= (double) BusSharedValues.getInstance().TotalFare;
                totalPrice=String.valueOf(totalFare);
                txtBookAmount.setText(getResources().getString(R.string.rs_symbol)+" "+totalPrice);
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

            if(BusSharedValues.getInstance().busPromoAmount.equals("") || BusSharedValues.getInstance().busPromoAmount == null){
                promoDiscount="0";
                //applyvoucher="false";
            }
            else {
                //applyvoucher="true";
                promoDiscount=BusSharedValues.getInstance().busPromoAmount;
            }

            discount= String.valueOf(BusSharedValues.getInstance().totDiscount);

            //call api utility_main utility_wallet

            if(!ConnectivityUtils.isNetworkEnabled(this)){
                Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();
            }
            else {
                getMainBalance();
            }

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

           /* *//*Radio Button check change listener*//*
            rdgPaymentMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
                    float promoDis= Float.parseFloat(promoDiscount);
                    double totCheckBal= mainBal+discoutamt+promoDis;
                    if(totCheckBal >= totalPay){  // if(mainBal > totalPay){

                        layoutMessage.setVisibility(View.GONE);
                        txtBalMsg.setText("");

                        //Call api
                       //SendOtp();

                        /*Update code without otp*/
                        if(!ConnectivityUtils.isNetworkEnabled(OtpAndBusBookPaymentActivity.this)){
                            Toast.makeText(OtpAndBusBookPaymentActivity.this,getResources().getString(R.string.network_error),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {

                           showBookingDialog();
                        }

                    }

                    else{

                        //Call api
                        //SendOtp();
                        layoutMessage.setVisibility(View.VISIBLE);
                       txtBalMsg.setText("Wallet Balance is Low for Proceed Bus book");
                    }


                }
            });

            /*Text Click here on click
             * go Add Fund Activity for
             * using e-utility_wallet, payment gateway*/
            txtClickHere.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(OtpAndBusBookPaymentActivity.this, AddFundActivity.class);
                    String deductReqAmnt= String.valueOf(deductAmount);
                    intent.putExtra("GW_Service","Bus");
                    intent.putExtra("Home","false");
                    intent.putExtra("DeductAmount",deductReqAmnt);
                    intent.putExtra("ServiceType","Bus");
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
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
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
                                        new LoginPreferences_Utility(OtpAndBusBookPaymentActivity.this).getLoggedinUser().setBalance(balanceResponse.getBalance());
                                        mainBal= Double.parseDouble(balanceResponse.getBalance());

                                        //update
                                        float discoutamt= Float.parseFloat(discount);
                                        float promoDis= Float.parseFloat(promoDiscount);
                                        double totCheckBal= mainBal+discoutamt+promoDis;

                                   /* if(totalPay <= totCheckBal){
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
                                    }*/

                                        //update
                                        if(promoDis > 0){
                                            layoutPromoCode.setVisibility(View.VISIBLE);
                                            txtPromoCode.setText(promoCode);
                                            double payAmount=totalPay-promoDis;
                                            //double d_Amount=mainBal-payAmount;

                                            if(payAmount <= mainBal){
                                                layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                                layChkBox_Promo.setVisibility(View.VISIBLE);
                                                layChkBox_Ewallet.setVisibility(View.GONE);
                                                //deductAmount=payAmount;
                                                String strDouble = String.format("%.3f", payAmount);
                                                deductAmount= Double.parseDouble(strDouble);
                                                txtMainBal.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                                txtDeductBal_Main.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(payAmount));
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
                                                layChkBox_Ewallet.setVisibility(View.VISIBLE);
                                                layChkBox_Promo.setVisibility(View.VISIBLE);
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
                                            txtPromoCode.setText(promoCode);
                                            txtDeductBal_Promo.setText("");
                                            if(totalPay <= mainBal){
                                                layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                                layChkBox_Ewallet.setVisibility(View.GONE);
                                                double d_Amount=mainBal-totalPay;
                                                //deductAmount=mainBal-totalPay;
                                                String strDouble = String.format("%.3f", d_Amount);
                                                deductAmount= Double.parseDouble(strDouble);
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
                                                String strDouble = String.format("%.3f", d_Amount);
                                                deductAmount= Double.parseDouble(strDouble);
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
                                Toast.makeText(OtpAndBusBookPaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OtpAndBusBookPaymentActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(OtpAndBusBookPaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
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

    /* Request and Response BillPay Send Otp*/
    public void SendOtp(){

        progressDialog = new ProgressDialog(this);
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

            /* Send Otp Request Model*/

            SendOtpRequest sendOtpRequest=new SendOtpRequest();
            sendOtpRequest.setEmailID(new LoginPreferences_Utility(this).getLoggedinUser().getEmailId());
            sendOtpRequest.setFormNo(formno);
            sendOtpRequest.setMobileNo(mobile);
            sendOtpRequest.setSponsorFormNo(companyId);
            sendOtpRequest.setServiceName("B");
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
                NetworkClient_Utility_1.getInstance(OtpAndBusBookPaymentActivity.this).create(FlightApi.class).fetchFlightBookOtp(apiRequest,strToken);
        fetchFlightOtp.enqueue(new Callback<BaseResponse>() {
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

                                // edTxtOtp.setEnabled(true);
                                String strResponse=Response.getRESP_VALUE();
                                otpId=strResponse;

                                BusSharedValues.getInstance().busBookOtpId=otpId;
                                SharedPrefrence_Utility.setOtpCode(OtpAndBusBookPaymentActivity.this,otpId);

                                //Intent intent=new Intent(OtpAndBusBookPaymentActivity.this, OtpVerify_BusBookActivity.class);
                                Intent intent=new Intent(OtpAndBusBookPaymentActivity.this, VerifyOtpActivity.class);

                           /* Bundle bundle1=new Bundle();
                            bundle1.putSerializable("PassengerInfo",pessengerInfoArrayList);
                            intent.putExtras(bundle1);*/
                                intent.putExtra("OtpService","Bus");
                                startActivityForResult(intent, AppConstants.Req_code_otp);
                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);


                            }
                            else if(Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")){
                                String toast= Response.getRESP_MSG();
                                //edTxtOtp.setEnabled(false);
                                //Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            //edTxtOtp.setEnabled(false);
                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            //Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(OtpAndBusBookPaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(OtpAndBusBookPaymentActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(OtpAndBusBookPaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                                            Intent intent=new Intent(OtpAndBusBookPaymentActivity.this, BusBookSuccessMsgActivity.class);

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
                                    Toast.makeText(OtpAndBusBookPaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(OtpAndBusBookPaymentActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                }
                                else {

                                    String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                    Toast.makeText(OtpAndBusBookPaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    // showSnackbar(toast);

                                }
                            }
                            else {
                                Toast.makeText(OtpAndBusBookPaymentActivity.this,"something wrong..",Toast.LENGTH_SHORT).show();
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
                passengers.setEmail(pessengerInfoList.get(i).getEmail());

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

        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        //Intent intent=new Intent(this,BusPassengerInfoActivity.class);
        //startActivity(intent);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        finish();
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
                                // call api bus book

                                float discoutamt= Float.parseFloat(discount);
                                float promo= Float.parseFloat(promoDiscount);
                                double totCheckBal= mainBal+discoutamt+promo;
                                if(totCheckBal >= totalPay){

                                    getBusBook();
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
                                // call api bus book
                                getBusBook();
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

    void showBookingDialog() {
        try {
            AlertDialogUtils.showDialogWithTwoButton(OtpAndBusBookPaymentActivity.this, new AlertDialogButtonListener() {
                @Override
                public void onButtontapped(String btnText) {
                    if (btnText.equalsIgnoreCase("yes")) {
                        getBusBook();
                    }
                }
            }, "Booking Seat", "Do you want to continue for book seat process?", "No", "Yes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
