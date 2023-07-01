package in.discountmart.utility_services.billpayment.activity;

import static in.discountmart.utility_services.utilities.AlertDialogUtils.showDialogWithTwoButton;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import in.base.network.NetworkClient_Utility_1;
import in.base.ui.BaseActivity;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.activity.VerifyOtpActivity;
import in.discountmart.utility_services.billpayment.bill_pay_call_api.BillPayApi;
import in.discountmart.utility_services.billpayment.billpay_model.billpay_request_model.BillPayRequest;
import in.discountmart.utility_services.billpayment.billpay_model.billpay_response_model.BillPayResponse;
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
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillPayFinalDetailActivity extends BaseActivity {


    String strOtp="";
    ProgressDialog progressDialog;
    String applyvoucher="";
    String ServiceName="";
    String strBillAmount="";
    String strPromoAmount="";
    String strPromoCode="";
    String Servicetype="";
    String ServiceId="";
    String Service="";
    String refNo="";
    String otpId="";
    double mainBal=0;
    double totalPay=0;
    double deductAmount=0;
    int promoAmount=0;
    BillPayRequest billPayRequest;

    TextView txtServiceName;
    TextView txtServiceType;
    TextView txtBillAmount;
    TextView txtBillPayAmount;
    TextView txtRefNo;
    TextView txtBalMsg;
    TextView txtDeductBal_other;
    TextView txtDeductBal_Main;
    TextView txtDeductBal_Promo;
    TextView txtClickHere;
    TextView txtPromoCode;
    TextView txtMainBal;
    Button btnContinue;
    Button btnOTP;
    ImageView imgClose;
    LinearLayout layoutClickHere;
    LinearLayout layChkBox_Mwallet;
    LinearLayout layChkBox_Ewallet;
    LinearLayout layChkBox_Promo;;
    LinearLayout layoutMainWallet;
    LinearLayout layoutPayment;
    LinearLayout layoutMessage;
    LinearLayout layoutPromoCode;
    LinearLayout layoutBtnotp;
    AppCompatCheckBox chBoxMainWallet;
    AppCompatCheckBox chBoxE_Wallet;
    AppCompatCheckBox chBox_Promo;
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bill_pay_final_detail);
       /* if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }*/
        view=findViewById(android.R.id.content);

        try {
            txtBillAmount=(TextView)findViewById(R.id.billpay_final_detail_txt_billamunt);
            txtServiceName=(TextView)findViewById(R.id.billpay_final_detail_txt_service_name);
            txtServiceType=(TextView)findViewById(R.id.billpay_final_detail_txt_service_type);
            txtRefNo=(TextView)findViewById(R.id.billpay_final_detail_txt_refno);
            btnContinue=(Button)findViewById(R.id.billpay_final_detail_btn_continue);
            imgClose=(ImageView)findViewById(R.id.billpay_final_detail_img_close);

            btnOTP=(Button)findViewById(R.id.billpay_payment_btn_otp) ;
            chBoxE_Wallet=(AppCompatCheckBox)findViewById(R.id.billpay_payment_chbox_eWallet);
            chBoxMainWallet=(AppCompatCheckBox)findViewById(R.id.billpay_payment_chbox_mainWallet);
            chBox_Promo=(AppCompatCheckBox)findViewById(R.id.billpay_payment_chbox_promo);
            txtMainBal=(TextView) findViewById(R.id.billpay_payment_txt_main_bal);
            txtBalMsg=(TextView) findViewById(R.id.billpay_payment_txt_bal_message);
            txtBillPayAmount=(TextView) findViewById(R.id.billpay_payment_txt_totPay_amount);
            txtDeductBal_Main=(TextView) findViewById(R.id.billpay_payment_txt_main_bal_deduct);
            txtDeductBal_other=(TextView) findViewById(R.id.billpay_payment_txt_bal_deduct_other);
            txtDeductBal_Promo=(TextView) findViewById(R.id.billpay_payment_txt_promo_bal);
            txtPromoCode=(TextView) findViewById(R.id.billpay_payment_act_txt_promo);
            txtClickHere=(TextView)findViewById(R.id.billpay_payment_txt_clickhere);
            layoutMainWallet=(LinearLayout)findViewById(R.id.billpay_payment_layout_main_wallet);
            layoutMessage=(LinearLayout)findViewById(R.id.billpay_payment_layout_message);
            layoutBtnotp=(LinearLayout)findViewById(R.id.billpay_payment_layout_otp);
            layoutClickHere=(LinearLayout)findViewById(R.id.billpay_payment_layout_clickhere);
            layChkBox_Ewallet=(LinearLayout)findViewById(R.id.billpay_payment_layout_chbox_ewallt);
            layChkBox_Promo=(LinearLayout)findViewById(R.id.billpay_payment_layout_chbox_promo);
            layChkBox_Mwallet=(LinearLayout)findViewById(R.id.billpay_payment_layout_chbox_main_wallet);
            layoutPayment=(LinearLayout)findViewById(R.id.billpay_payment_layout);
            layoutPromoCode=(LinearLayout)findViewById(R.id.billpay_payment_act_layout_promocode);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Review & Bill Payment");

            Bundle bundle=getIntent().getExtras();
            if(bundle != null){

                billPayRequest=new BillPayRequest();
                billPayRequest= (BillPayRequest) bundle.getSerializable("BillPayRequest");
                //strOtp=bundle.getString("OTPId");
                ServiceName =bundle.getString("ServiceName");
                Servicetype =bundle.getString("ServiceType");
                strBillAmount =bundle.getString("BillAmount");
                strPromoAmount =bundle.getString("PromoAmount");
                strPromoCode =bundle.getString("PromoCode");
                ServiceId =bundle.getString("ServiceId");
                refNo =bundle.getString("Reference");
                Service =bundle.getString("Service");

                if(Service.equals("I")){
                    txtRefNo.setText("Policy Number :-  "+ refNo);
                }
                else if(Service.equals("E")){
                    txtRefNo.setText("K Number :-  "+ refNo);
                }
                else if(Service.equals("G")){
                    txtRefNo.setText("Group Number :-  "+ refNo);
                }
                else if(Service.equals("C")){
                    txtRefNo.setText("Credit Card Number :-  "+ refNo);
                }

                txtServiceName.setText("Service Provider :-  "+ServiceName);
                txtServiceType.setText("Service :-  "+Servicetype);
                txtBillAmount.setText("Bill Amount:-  "+getResources().getString(R.string.rs_symbol) +" "+strBillAmount);
                totalPay= Double.parseDouble(strBillAmount);
                //promoAmount= Integer.parseInt(strPromoAmount);

               /* *//*Button continue on click*//*
                btnContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        *//*Call api bill pay send otp*//*
                        if(!ConnectivityUtils.isNetworkEnabled(BillPayFinalDetailActivity.this)){
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
                            *//*Call api bill pay send otp*//*
                            billPaySendOtp();
                        }
                    }
                });*/
                /*Button continue on click*/
                btnContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*Call api bill pay send otp*/
                        if(!ConnectivityUtils.isNetworkEnabled(BillPayFinalDetailActivity.this)){
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
                            /*Call Main utility_wallet balance api*/
                            //billPaySendOtp();
                            getMainBalance();
                        }
                    }
                });

                /*Update*/
                /*Button Send Otp Click Listener*/
                btnOTP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         //float discoutamt= Float.parseFloat(discount);
                        float promoDis= Float.parseFloat(strPromoAmount);
                        double totCheckBal= mainBal+promoDis;

                        //update
                        if(promoDis > 0){
                            int proDisAmonut=0;
                            //proDisAmonut=promoAmount;
                            double payAmount=totalPay-promoDis;
                            double mainBalAmout=mainBal+promoDis;
                            if(totalPay <= mainBalAmout){
                                layoutMessage.setVisibility(View.GONE);
                                txtBalMsg.setText("");
                                //billPaySendOtp();
                                showDialog();
                            }
                            else {
                                layoutMessage.setVisibility(View.VISIBLE);
                                txtBalMsg.setText("Wallet Balance is Low for Proceed Bill Payment");
                            }
                        }
                        else {
                            if(mainBal >= totalPay){  // if(mainBal > totalPay){
                                layoutMessage.setVisibility(View.GONE);
                                txtBalMsg.setText("");

                                /* Bill Pay with Otp Service*/
                               // billPaySendOtp();

                                /*Without otp bill payment*/
                                /*Update code without otp*/
                            if(!ConnectivityUtils.isNetworkEnabled(BillPayFinalDetailActivity.this)){
                                Toast.makeText(BillPayFinalDetailActivity.this,getResources().getString(R.string.network_error),
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {

                                showDialog();
                            }
                            }
                            else{
                                layoutMessage.setVisibility(View.VISIBLE);
                                txtBalMsg.setText("Wallet Balance is Low for Proceed Bill Payment");
                            }
                        }


                    }
                });

                /*Text Click here on click
                 * go Add Fund Activity for
                 * using e-utility_wallet, payment gateway*/
                txtClickHere.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(BillPayFinalDetailActivity.this, AddFundActivity.class);
                        String deductReqAmnt= String.valueOf(deductAmount);
                        intent.putExtra("GW_Service",Service);
                        intent.putExtra("Home","false");
                        intent.putExtra("DeductAmount",deductReqAmnt);
                        intent.putExtra("ServiceType",Service);
                        startActivityForResult(intent, AppConstants.Req_code_GW);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    }
                });


              /*  imgClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });*/
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* Request and Response BillPay Send Otp*/
    public void billPaySendOtp(){

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
            sendOtpRequest.setServiceName(Servicetype);
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
                NetworkClient_Utility_1.getInstance(BillPayFinalDetailActivity.this).create(FlightApi.class).fetchFlightBookOtp(apiRequest,strToken);
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

                            /*FlightSharedValues.getInstance().flightBookOtpId=otpId;

                            Intent intent=new Intent(BillPayFinalDetailActivity.this, VerifyOtpActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("BillPayRequest",billPayRequest);
                            bundle.putString("OTPId",otpId);
                            bundle.putString("ServiceName",ServiceName);
                            bundle.putString("ServiceType",Servicetype);
                            bundle.putString("ServiceId",ServiceId);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            //finish();
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/

                                SharedPrefrence_Utility.setOtpCode(BillPayFinalDetailActivity.this,otpId);

                                //Intent intent=new Intent(OtpAndBusBookPaymentActivity.this, OtpVerify_BusBookActivity.class);
                                Intent intent=new Intent(BillPayFinalDetailActivity.this, VerifyOtpActivity.class);

                                intent.putExtra("OtpService",Service);
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
                            Toast.makeText(BillPayFinalDetailActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(BillPayFinalDetailActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                        else {
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
                    }
                    else {
                        String toast= Response.getRESPONSE()+ "Something wrong..";
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
                                        new LoginPreferences_Utility(BillPayFinalDetailActivity.this).getLoggedinUser().setBalance(balanceResponse.getBalance());
                                        double M_walletBal= Double.parseDouble(balanceResponse.getBalance());
                                        int proDisAmonut=0;

                                        double promoDis= Double.parseDouble(strPromoAmount);
                                        double totCheckBal= mainBal+promoDis;
                                        //mainBal=Math.round(M_walletBal);
                                        mainBal=M_walletBal;
                                        //DoubleRounder.round(PI, 3);
                                        //Precision.round(PI, 3);
                                        layoutPayment.setVisibility(View.VISIBLE);
                                        btnContinue.setEnabled(false);
                                        btnContinue.setVisibility(View.GONE);

                                        //update
                                        if(promoDis > 0){
                                            //proDisAmonut=promoAmount;
                                            double payAmount=totalPay-promoDis;

                                            double mainBalAmout=mainBal+promoDis;

                                            layoutPromoCode.setVisibility(View.VISIBLE);
                                            txtPromoCode.setText(strPromoCode);

                                            if(payAmount <= mainBal){
                                                layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                                layChkBox_Ewallet.setVisibility(View.GONE);
                                                layChkBox_Promo.setVisibility(View.VISIBLE);

                                               // deductAmount=mainBal-payAmount;
                                                double d_Amount=mainBal-payAmount;
                                                String strDouble = String.format("%.3f", d_Amount);
                                                deductAmount= Double.parseDouble(strDouble);
                                                txtBillPayAmount.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(totalPay));
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
                                                double ded_Amount=payAmount-mainBal;
                                                String strDouble = String.format("%.3f", ded_Amount);
                                                deductAmount= Double.parseDouble(strDouble);
                                                //deductAmount=payAmount-mainBal;
                                                //deductAmount=payAmount;
                                                txtMainBal.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                                txtDeductBal_Main.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                                txtDeductBal_other.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(deductAmount));
                                                txtBillPayAmount.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(totalPay));
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
                                            txtPromoCode.setText("");
                                            if(totalPay <= mainBal){
                                                layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                                layChkBox_Ewallet.setVisibility(View.GONE);
                                                layChkBox_Promo.setVisibility(View.GONE);
                                                //deductAmount=mainBal-totalPay;
                                                double d_Amount=mainBal-totalPay;
                                                String strDouble = String.format("%.3f", d_Amount);
                                                deductAmount= Double.parseDouble(strDouble);
                                                txtBillPayAmount.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(totalPay));
                                                txtMainBal.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                                txtDeductBal_Main.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(totalPay));
                                                txtDeductBal_Promo.setText("");

                                                chBoxMainWallet.setChecked(true);
                                                chBoxMainWallet.setEnabled(false);
                                                layoutClickHere.setVisibility(View.GONE);
                                                btnOTP.setVisibility(View.VISIBLE);

                                            }
                                            else if(totalPay > mainBal){
                                                layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                                layChkBox_Ewallet.setVisibility(View.VISIBLE);
                                                layChkBox_Promo.setVisibility(View.GONE);
                                                double d_Amount=totalPay-mainBal;
                                                String strDouble = String.format("%.3f", d_Amount);
                                                deductAmount= Double.parseDouble(strDouble);
                                                //deductAmount=Math.round(d_Amount);
                                                txtMainBal.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                                txtDeductBal_Main.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                                txtDeductBal_other.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(deductAmount));
                                                txtBillPayAmount.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(totalPay));
                                                txtDeductBal_Promo.setText("");
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
                                Toast.makeText(BillPayFinalDetailActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(BillPayFinalDetailActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                showSnackbar(toast);

                            }
                        }
                        else {
                            String toast= Response.getRESPONSE()+ "Something wrong..";
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



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* Request and Response Get Bill Payament sApi*/
    public void getBillPaymentDetail() {


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait for payment processing");
        progressDialog.setCancelable(true);
        progressDialog.show();

        String strApiRequest = "";
        JSONObject object = null;
        String strToken = TokenBase64.getToken();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse = new LoginPreferences_Utility(this).getLoggedinUser();
        String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
        String formno = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);

        String dob = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        ApiRequest apiRequest = new ApiRequest();
        try {

            /*Main Request Model*/

            BaseHeaderRequest headerRequest = new BaseHeaderRequest();
            headerRequest.setUserName(loginResponse.getUserName());
            headerRequest.setPassword(loginResponse.getPasswd());
            headerRequest.setSponsorFormNo(companyId);
//            if(loginResponse.getMemMode().equals("D"))
//                headerRequest.setSponsorFormNo(companyId);
//            else
//                headerRequest.setSponsorFormNo("");

            /*BillPay Request Model*/
            BillPayRequest providerRequest = new BillPayRequest();
            providerRequest.setServiceId(billPayRequest.getServiceId());
            providerRequest.setFormNo(billPayRequest.getFormNo());
            providerRequest.setAmount(billPayRequest.getAmount());
            providerRequest.setReferanceNumber(billPayRequest.getReferanceNumber());
            providerRequest.setBillerName(billPayRequest.getBillerName());
            providerRequest.setRegMobileNo(billPayRequest.getRegMobileNo());
            providerRequest.setPanNo(billPayRequest.getPanNo());
            providerRequest.setEmailId(billPayRequest.getEmailId());
            providerRequest.setCardType(billPayRequest.getCardType());
            providerRequest.setServiceTypeId(billPayRequest.getServiceTypeId());
            providerRequest.setLocationCode(billPayRequest.getLocationCode());
            providerRequest.setMeterNumber(billPayRequest.getMeterNumber());
            providerRequest.setBillUnit(billPayRequest.getBillUnit());
            providerRequest.setProcessCycle(billPayRequest.getProcessCycle());
            providerRequest.setCycleNumber(billPayRequest.getCycleNumber());
            providerRequest.setMeterReadingDate(billPayRequest.getMeterReadingDate());
            providerRequest.setGroupNo(billPayRequest.getGroupNo());
            providerRequest.setDistrict(billPayRequest.getDistrict());
            providerRequest.setConsumerType(billPayRequest.getConsumerType());
            providerRequest.setTypeOfPayment(billPayRequest.getTypeOfPayment());
            providerRequest.setERO(billPayRequest.getERO());
            providerRequest.setServicetype(billPayRequest.getServicetype());
            providerRequest.setDiviSionName(billPayRequest.getDiviSionName());
            providerRequest.setDob(billPayRequest.getDob());
            providerRequest.setBAnk(billPayRequest.getBAnk());
            providerRequest.setRRNo(billPayRequest.getRRNo());
            providerRequest.setMonthBill(billPayRequest.getMonthBill());
            providerRequest.setCreditCard(billPayRequest.getCreditCard());
            providerRequest.setSector(billPayRequest.getSector());
            providerRequest.setBlock(billPayRequest.getBlock());
            providerRequest.setFlatNo(billPayRequest.getFlatNo());
            providerRequest.setSubCode(billPayRequest.getSubCode());
            providerRequest.setSubdivisioncode(billPayRequest.getSubdivisioncode());
            providerRequest.setPromoCode(billPayRequest.getPromoCode());
            providerRequest.setDiscount(billPayRequest.getDiscount());
            providerRequest.setFileImg(billPayRequest.getFileImg());
            providerRequest.setFileImg1(billPayRequest.getFileImg1());
            providerRequest.setOtp(SharedPrefrence_Utility.getOtpNumber(this));
            providerRequest.setOtpId(SharedPrefrence_Utility.getOtpcode(this));

            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(providerRequest);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<BaseResponse> fetchBillPayCall =
                NetworkClient_Utility_1.getInstance(this).create(BillPayApi.class).fetchBillPayment(apiRequest, strToken);

        fetchBillPayCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    BaseResponse Response = new BaseResponse();
                    Response = response.body();

                    if (Response != null) {
                        if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                            if (Response.getRESP_VALUE().equals("") || Response.getRESP_VALUE().isEmpty()) {

                                String toast = Response.getRESP_MSG();
                                //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();
                            } else if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                //String serviceListResponse = Response.getRESPONSE();
                                BillPayResponse billPayResponse=new BillPayResponse();
                                billPayResponse=new Gson().fromJson(Response.getRESP_VALUE(),BillPayResponse.class);
                                if (billPayResponse != null) {

                                    Intent intent=new Intent(BillPayFinalDetailActivity.this, BillPaymentSuccessMsgActivity.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putString("Msg",Response.getRESP_MSG());
                                    bundle.putString("ServiceType",Servicetype);
                                    bundle.putString("Amount",billPayResponse.getAmount());
                                    bundle.putString("TransId",billPayResponse.getId());
                                    if(ServiceId.equals(billPayResponse.getServiceId()))
                                        bundle.putString("ServiceName",ServiceName);
                                    else
                                        bundle.putString("Service",billPayResponse.getServiceId());

                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);


                                } else {
                                    String toast = " City List empty";
                                    Toast.makeText(BillPayFinalDetailActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    //showSnackbar(toast);
                                }
                            }
                        }
                        else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("FAILED"))
                        {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(BillPayFinalDetailActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(BillPayFinalDetailActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(BillPayFinalDetailActivity.this, toast, Toast.LENGTH_SHORT).show();
                           // showSnackbar(toast);

                        }
                    }
                    else {
                        //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                        String toast = "something wrong..";
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
                // Toast.makeText(FlightCityListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

                                // call api Recharge
                                // if(mainBal >= totalPay){

                                /*Check network connection*/
                                if(!ConnectivityUtils.isNetworkEnabled(BillPayFinalDetailActivity.this)){
                                    Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(R.color.app_orange_light ))
                                            .show();
                                }
                                else {

                                    // call bill payment api
                                    getBillPaymentDetail();
                                    /*Show dialog for confirm bill pay*/
                                       /* if(Servicetype.equals("M") || Servicetype.equals("T")){
                                            showRechargeDialog();
                                        }
                                        else if(Servicetype.equals("D")){
                                            showDthRechargeDialog();
                                        }*/

                                }

                                //  }
                                /*else {
                                    //Toast.makeText(OtpVerify_FlightBookActivity.this,"Amount not sufficient",Toast.LENGTH_SHORT).show();
                                    Snackbar.make(view, "Amount not sufficient", Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(R.color.app_orange_light ))
                                            .show();
                                }*/
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
                                // call api bill payment api
                            getBillPaymentDetail();
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


    /*Show alet dialog box
     * for asking before Hotel
     * room book.*/
    void showDialog() {
        try {
            showDialogWithTwoButton(BillPayFinalDetailActivity.this, new AlertDialogButtonListener() {
                @Override
                public void onButtontapped(String btnText) {
                    if (btnText.equalsIgnoreCase("yes")) {
                        getBillPaymentDetail();
                    }
                }
            }, "Bill Payment", "Do you want to continue process for bill payment?", "No", "Yes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



/* if(promoAmount > 0){
                                        proDisAmonut=promoAmount;
                                        int payAmount=totalPay-promoAmount;
                                        int mainBalAmout=mainBal+proDisAmonut;

                                        layoutPromoCode.setVisibility(View.VISIBLE);
                                        txtPromoCode.setText(strPromoCode);
                                        if(totalPay <= mainBalAmout){
                                            layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                            layChkBox_Ewallet.setVisibility(View.GONE);
                                            //layChkBox_Promo.setVisibility(View.VISIBLE);
                                            //deductAmount=mainBal-totalPay;
                                            txtBillPayAmount.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(totalPay));
                                            txtMainBal.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                            txtDeductBal_Main.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(payAmount));
                                            //txtDeductBal_Promo.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(proDisAmonut));

                                            chBoxMainWallet.setChecked(true);
                                            chBox_Promo.setChecked(true);
                                            chBoxMainWallet.setEnabled(false);
                                            chBox_Promo.setEnabled(false);
                                            layoutClickHere.setVisibility(View.GONE);
                                            btnOTP.setVisibility(View.VISIBLE);

                                        }
                                        else if(totalPay > mainBalAmout){
                                            layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                            layChkBox_Ewallet.setVisibility(View.VISIBLE);
                                            layChkBox_Promo.setVisibility(View.VISIBLE);
                                            deductAmount=totalPay-mainBalAmout;

                                            txtMainBal.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                            txtBillPayAmount.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(totalPay));
                                            txtDeductBal_Main.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                            txtDeductBal_other.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(deductAmount));
                                            txtDeductBal_Promo.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(proDisAmonut));


                                            chBoxMainWallet.setChecked(true);
                                            chBoxMainWallet.setEnabled(false);
                                            chBoxE_Wallet.setVisibility(View.VISIBLE);
                                            chBoxE_Wallet.setChecked(true);
                                            chBoxE_Wallet.setEnabled(false);
                                            chBox_Promo.setChecked(true);
                                            chBox_Promo.setEnabled(false);
                                            //btnContinue.setVisibility(View.VISIBLE);
                                            layoutClickHere.setVisibility(View.VISIBLE);
                                            btnOTP.setVisibility(View.GONE);
                                        }

                                    }*/
