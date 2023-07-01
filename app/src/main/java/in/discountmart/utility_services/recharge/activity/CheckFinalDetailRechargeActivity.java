package in.discountmart.utility_services.recharge.activity;

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
import in.discountmart.utility_services.recharge.call_recharge_api.RechargeApi;
import in.discountmart.utility_services.recharge.recharge_model.recharge_request_model.RechargeRequest;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.RechargeResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.sharedpreferences.SharedPrefrence_Utility;
import in.discountmart.utility_services.travel.flight.call_flight_api.FlightApi;
import in.discountmart.utility_services.utilities.AlertDialogUtils;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CheckFinalDetailRechargeActivity extends BaseActivity {

    String strOtp="";
    ProgressDialog progressDialog;
    String applyvoucher="";
    String operator="";
    String strAmount="";
    String Servicetype="";
    String ServiceId="";
    String Service="";
    String MobNo="";
    String otpId="";
    double mainBal=0;
    double totalPay=0;
    double deductAmount=0;
    String discount="";
    String promoCode="";
    String promoDiscount="";
    RechargeRequest rechargeRequest;

    TextView txtOperatorName;
    TextView txtServiceType;
    TextView txtAmount;
    TextView txtMobile;
    TextView txtRechargeAmount;
    TextView txtBalMsg;
    TextView txtDeductBal_other;
    TextView txtDeductBal_Main;
    TextView txtClickHere;
    TextView txtMainBal;
    Button btnContinue;
    Button btnOTP;
    Button btnRecharge;
    ImageView imgClose;
    LinearLayout layoutClickHere;
    LinearLayout layChkBox_Mwallet;
    LinearLayout layChkBox_Ewallet;
    LinearLayout layoutMainWallet;
    LinearLayout layoutPayment;
    LinearLayout layoutMessage;
    LinearLayout layoutBtnotp;
    AppCompatCheckBox chBoxMainWallet;
    AppCompatCheckBox chBoxE_Wallet;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_check_final_detail_recharge);
       /* if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }*/
        view=findViewById(android.R.id.content);
        try {
            txtAmount=(TextView)findViewById(R.id.recharge_final_detail_txt_billamunt);
            txtOperatorName=(TextView)findViewById(R.id.recharge_final_detail_txt_service_name);
            txtServiceType=(TextView)findViewById(R.id.recharge_final_detail_txt_service_type);
            txtMobile=(TextView)findViewById(R.id.recharge_final_detail_txt_mobno);
            btnContinue=(Button)findViewById(R.id.recharge_final_detail_btn_continue);
            //imgClose=(ImageView)findViewById(R.id.recharge_final_detail_img_close);
            chBoxE_Wallet=(AppCompatCheckBox)findViewById(R.id.recharge_payment_chbox_eWallet);
            chBoxMainWallet=(AppCompatCheckBox)findViewById(R.id.recharge_payment_chbox_mainWallet);

            btnOTP=(Button) findViewById(R.id.recharge_payment_btn_otp);
            btnRecharge=(Button) findViewById(R.id.recharge_payment_btn_recharge);
            //btnCheckFund=(Button) findViewById(R.id.flight_book_act_btn_validate);
            // btnRequestfund=(Button) findViewById(R.id.flight_book_act_btn_fund_request);
            txtMainBal=(TextView) findViewById(R.id.recharge_payment_txt_main_bal);
            txtBalMsg=(TextView) findViewById(R.id.recharge_payment_txt_bal_message);
            txtRechargeAmount=(TextView) findViewById(R.id.recharge_payment_txt_totPay_amount);
            txtDeductBal_Main=(TextView) findViewById(R.id.recharge_payment_txt_main_bal_deduct);
            txtDeductBal_other=(TextView) findViewById(R.id.recharge_payment_txt_bal_deduct_other);
            txtClickHere=(TextView)findViewById(R.id.recharge_payment_txt_clickhere);
            layoutMainWallet=(LinearLayout)findViewById(R.id.recharge_payment_layout_main_wallet);
            layoutMessage=(LinearLayout)findViewById(R.id.recharge_payment_layout_message);
            layoutBtnotp=(LinearLayout)findViewById(R.id.recharge_payment_layout_otp);
            layoutClickHere=(LinearLayout)findViewById(R.id.recharge_payment_layout_clickhere);
            layChkBox_Ewallet=(LinearLayout)findViewById(R.id.recharge_payment_layout_chbox_ewallt);
            layChkBox_Mwallet=(LinearLayout)findViewById(R.id.recharge_payment_layout_chbox_main_wallet);
            layoutPayment=(LinearLayout)findViewById(R.id.recharge_payment_layout);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Review & Recharge Payment");

            Bundle bundle=getIntent().getExtras();
            if(bundle != null){

                rechargeRequest=new RechargeRequest();
                rechargeRequest= (RechargeRequest) bundle.getSerializable("RechargeRequest");
                //strOtp=bundle.getString("OTPId");
                operator =bundle.getString("Operator");
                Servicetype =bundle.getString("ServiceType");
                strAmount =bundle.getString("Amount");
                ServiceId =bundle.getString("ServiceId");
                MobNo =bundle.getString("MobileNo");
                Service =bundle.getString("Service");

                if(Servicetype.equals("M")){
                    txtServiceType.setText("Prepaid Mobile");
                    txtMobile.setText("Mobile Number :-  "+MobNo);
                }
                else if(Servicetype.equals("T")){
                    txtServiceType.setText("Postpaid Mobile");
                    txtMobile.setText("Mobile Number :-  "+MobNo);
                }
                else if(Servicetype.equals("D")){
                    txtServiceType.setText("DTH");
                    txtMobile.setText("DTH Number :-  "+MobNo);
                }

                txtOperatorName.setText("Operator :-  "+operator);
                txtAmount.setText("Amount :-  "+getResources().getString(R.string.rs_symbol) +" "+strAmount);
                totalPay= Double.parseDouble(strAmount);


                /*Button continue on click*/
                btnContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*Call api bill pay send otp*/
                        if(!ConnectivityUtils.isNetworkEnabled(CheckFinalDetailRechargeActivity.this)){
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

                /*Button Send Otp Click Listener*/
                btnOTP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       // float discoutamt= Float.parseFloat(discount);
                        //float promoDis= Float.parseFloat(promoDiscount);
                        //float totCheckBal= mainBal+discoutamt+promoDis;

                        if(mainBal >= totalPay){  // if(mainBal > totalPay){

                            layoutMessage.setVisibility(View.GONE);
                            txtBalMsg.setText("");

                            /*Show dialog for confirm bill pay*/
                                       /* if(Servicetype.equals("M") || Servicetype.equals("T")){
                                            showRechargeDialog();
                                        }
                                        else if(Servicetype.equals("D")){
                                            showDthRechargeDialog();
                                        }*/


                            /*With Recharge with otp service*/
                            billPaySendOtp();

                            /*Without otp Recharge */
                            /*Update code without otp*/
                            /*if(!ConnectivityUtils.isNetworkEnabled(CheckFinalDetailRechargeActivity.this)){
                                Toast.makeText(CheckFinalDetailRechargeActivity.this,getResources().getString(R.string.network_error),
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {

                                showDialog();
                            }*/
                        }

                        else{
                            layoutMessage.setVisibility(View.VISIBLE);
                            txtBalMsg.setText("Wallet Balance is Low for Proceed Recharge");
                        }


                    }
                });


                /*Button continue for Recharge Click Listener*/
                btnRecharge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // float discoutamt= Float.parseFloat(discount);
                        //float promoDis= Float.parseFloat(promoDiscount);
                        //float totCheckBal= mainBal+discoutamt+promoDis;

                        if(mainBal >= totalPay){  // if(mainBal > totalPay){

                            layoutMessage.setVisibility(View.GONE);
                            txtBalMsg.setText("");

                            //Show dialog for confirm bill pay
                                        if(Servicetype.equals("M") || Servicetype.equals("T")){
                                            showRechargeDialog();
                                        }
                                        else if(Servicetype.equals("D")){
                                            showDthRechargeDialog();
                                        }


                            /*With Recharge with otp service*/
                           //billPaySendOtp();

                            /*Without otp Recharge */
                            /*Update code without otp*/
                            /*if(!ConnectivityUtils.isNetworkEnabled(CheckFinalDetailRechargeActivity.this)){
                                Toast.makeText(CheckFinalDetailRechargeActivity.this,getResources().getString(R.string.network_error),
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {

                                showDialog();
                            }*/
                        }

                        else{
                            layoutMessage.setVisibility(View.VISIBLE);
                            txtBalMsg.setText("Wallet Balance is Low for Proceed Recharge");
                        }


                    }
                });

                /*Text Click here on click
                 * go Add Fund Activity for
                 * using e-utility_wallet, payment gateway*/
                txtClickHere.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(CheckFinalDetailRechargeActivity.this, AddFundActivity.class);
                        String deductReqAmnt= String.valueOf(deductAmount);
                        intent.putExtra("GW_Service",Servicetype);
                        intent.putExtra("Home","false");
                        intent.putExtra("DeductAmount",deductReqAmnt);
                        intent.putExtra("ServiceType",Servicetype);
                        startActivityForResult(intent, AppConstants.Req_code_GW);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    }
                });
            }
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
                                        new LoginPreferences_Utility(CheckFinalDetailRechargeActivity.this).getLoggedinUser().setBalance(balanceResponse.getBalance());
                                        double M_walletBal= Double.parseDouble(balanceResponse.getBalance());
                                       // mainBal=Math.round(M_walletBal);
                                        mainBal=M_walletBal;
                                        layoutPayment.setVisibility(View.VISIBLE);
                                        btnContinue.setEnabled(false);
                                        btnContinue.setVisibility(View.GONE);

                                        if(totalPay <= mainBal){
                                            layChkBox_Mwallet.setVisibility(View.VISIBLE);
                                            layChkBox_Ewallet.setVisibility(View.GONE);
                                            //deductAmount=mainBal-totalPay;
                                            txtRechargeAmount.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(totalPay));
                                            txtMainBal.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(mainBal));
                                            txtDeductBal_Main.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(totalPay));

                                            chBoxMainWallet.setChecked(true);
                                            chBoxMainWallet.setEnabled(false);
                                            layoutClickHere.setVisibility(View.GONE);
                                            btnOTP.setVisibility(View.GONE);
                                            btnRecharge.setVisibility(View.VISIBLE);

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
                                            txtRechargeAmount.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(totalPay));
                                            chBoxMainWallet.setChecked(true);
                                            chBoxMainWallet.setEnabled(false);
                                            chBoxE_Wallet.setVisibility(View.VISIBLE);
                                            chBoxE_Wallet.setChecked(true);
                                            chBoxE_Wallet.setEnabled(false);
                                            //btnContinue.setVisibility(View.VISIBLE);
                                            layoutClickHere.setVisibility(View.VISIBLE);
                                            btnOTP.setVisibility(View.GONE);
                                            btnRecharge.setVisibility(View.GONE);
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
                                Toast.makeText(CheckFinalDetailRechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(CheckFinalDetailRechargeActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(CheckFinalDetailRechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                NetworkClient_Utility_1.getInstance(CheckFinalDetailRechargeActivity.this).create(FlightApi.class).fetchFlightBookOtp(apiRequest,strToken);
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

                                // FlightSharedValues.getInstance().flightBookOtpId=otpId;

                            /*Intent intent=new Intent(CheckFinalDetailRechargeActivity.this, VerifyOtpActivity.class);
                            //Intent intent=new Intent(CheckFinalDetailRechargeActivity.this, OtpActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("RechargeRequest",rechargeRequest);
                            bundle.putString("OTPId",otpId);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            //finish();
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/

                                SharedPrefrence_Utility.setOtpCode(CheckFinalDetailRechargeActivity.this,otpId);

                                //Intent intent=new Intent(OtpAndBusBookPaymentActivity.this, OtpVerify_BusBookActivity.class);
                                Intent intent=new Intent(CheckFinalDetailRechargeActivity.this, VerifyOtpActivity.class);


                                intent.putExtra("OtpService",Servicetype);
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
                            Toast.makeText(CheckFinalDetailRechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(CheckFinalDetailRechargeActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(CheckFinalDetailRechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
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


    }

    /* Request and Response Recharge Api*/
    public void getRecharge(){
        try {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait for processing recharge");
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
                RechargeRequest request=new RechargeRequest();
                request.setAccountNo(rechargeRequest.getAccountNo());
                request.setAction(rechargeRequest.getAction());
                request.setReferenceId(rechargeRequest.getReferenceId());

                request.setAmount(rechargeRequest.getAmount());
                request.setFormno(formno);
                request.setIpCode(rechargeRequest.getIpCode());
                request.setIsBBPS(rechargeRequest.getIsBBPS());
                request.setOperatorName(rechargeRequest.getOperatorName());
                request.setOpratorCode(rechargeRequest.getOpratorCode());
                request.setServiceType(rechargeRequest.getServiceType());
                request.setLocation(rechargeRequest.getLocation());

                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(request);

                strApiRequest=new Gson().toJson(apiRequest);

                Log.e("Value",strApiRequest);
            }catch (Exception e){
                e.printStackTrace();
            }


            if(Servicetype.equals("M") ||Servicetype.equals("D") ){
                Call<BaseResponse> fetchRecharge= NetworkClient_Utility_1.getInstance(this).create(RechargeApi.class).fetchRechargeRequest(apiRequest,strToken);

                fetchRecharge.enqueue(new Callback<BaseResponse>() {
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

                                        //Call utility_main balance api
                                        getMainBalance();

                                        RechargeResponse rechargeResponse=
                                                new Gson().fromJson(Response.getRESP_VALUE(),RechargeResponse.class);

                                        if(rechargeResponse != null ){
                                            if(rechargeResponse.getStatus().equals("SUCCESS")){

                                                Intent intent=new Intent(CheckFinalDetailRechargeActivity.this,RechargeSucccessMsgActivity.class);
                                                Bundle bundle=new Bundle();
                                                bundle.putString("Mobile",rechargeResponse.getAccountNo());
                                                bundle.putString("Servicetype",Servicetype);
                                                bundle.putString("Amount",rechargeResponse.getAmount());
                                                bundle.putString("Status",rechargeResponse.getStatus());
                                                bundle.putString("OpId",rechargeResponse.getOperatorID());
                                                bundle.putString("TId",rechargeResponse.getTID());
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                finish();
                                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                                            }
                                            else {
                                                Intent intent=new Intent(CheckFinalDetailRechargeActivity.this,RechargeSucccessMsgActivity.class);
                                                Bundle bundle=new Bundle();
                                                bundle.putString("Mobile",rechargeResponse.getAccountNo());
                                                bundle.putString("Servicetype",Servicetype);
                                                bundle.putString("Amount",rechargeResponse.getAmount());
                                                bundle.putString("Status",rechargeResponse.getStatus());
                                                bundle.putString("OpId",rechargeResponse.getOperatorID());
                                                bundle.putString("TId",rechargeResponse.getTID());
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                finish();
                                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                                            String toast=rechargeResponse.getStatus()+ " "+ rechargeResponse.getRESP_MSG();
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
                                    Toast.makeText(CheckFinalDetailRechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(CheckFinalDetailRechargeActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                    startActivity(intent);
                                    finish();

                                }
                                else {

                                    String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                    Toast.makeText(CheckFinalDetailRechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    // showSnackbar(toast);

                                }
                            }
                            else {
                                Snackbar.make(view, "Somtehing wrong..", Snackbar.LENGTH_LONG)
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

            else if(Servicetype.equals("T")) {
                Call<BaseResponse> fetchRecharge = NetworkClient_Utility_1.getInstance(this).create(RechargeApi.class).fetchRechargeRequest_postpaid(apiRequest, strToken);

                fetchRecharge.enqueue(new Callback<BaseResponse>() {
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

                                        //Call utility_main balance api
                                        getMainBalance();

                                        RechargeResponse rechargeResponse =
                                                new Gson().fromJson(Response.getRESP_VALUE(), RechargeResponse.class);

                                        if (rechargeResponse != null) {
                                            if (rechargeResponse.getStatus().equals("SUCCESS")) {

                                                Intent intent = new Intent(CheckFinalDetailRechargeActivity.this, RechargeSucccessMsgActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString("Mobile", rechargeResponse.getAccountNo());
                                                bundle.putString("Servicetype", Servicetype);
                                                bundle.putString("Amount", rechargeResponse.getAmount());
                                                bundle.putString("Status", rechargeResponse.getStatus());
                                                bundle.putString("OpId", rechargeResponse.getOperatorID());
                                                bundle.putString("TId", rechargeResponse.getTID());
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                finish();
                                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                                            } else {
                                                Intent intent = new Intent(CheckFinalDetailRechargeActivity.this, RechargeSucccessMsgActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString("Mobile", rechargeResponse.getAccountNo());
                                                bundle.putString("Servicetype", Servicetype);
                                                bundle.putString("Amount", rechargeResponse.getAmount());
                                                bundle.putString("Status", rechargeResponse.getStatus());
                                                bundle.putString("OpId", rechargeResponse.getOperatorID());
                                                bundle.putString("TId", rechargeResponse.getTID());
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                finish();
                                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                                            String toast=rechargeResponse.getStatus()+ " "+ rechargeResponse.getRESP_MSG();
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

                                    } else if (Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")) {
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

                                } else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {
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
                                } else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                    String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                    Toast.makeText(CheckFinalDetailRechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CheckFinalDetailRechargeActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                    startActivity(intent);
                                    finish();

                                } else {

                                    String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                                    Toast.makeText(CheckFinalDetailRechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    // showSnackbar(toast);

                                }
                            } else {
                                Snackbar.make(view, "Somtehing wrong..", Snackbar.LENGTH_LONG)
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




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Show Mobile Recharge dialog*/
    void showRechargeDialog(){
        try{
           /* StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("Amount of :- " + strAmount);
            sb.append("\n");
            sb.append("Mobile no:- " + strAccountNo);
            sb.append("\n");
            sb.append("Operator of:- " + strOpratorName);*/
            String alert1 = "Amount of : " + rechargeRequest.getAmount();
            String alert2 = "Mobile no: " + rechargeRequest.getAccountNo();
            String alert3 = "Operator of: " + rechargeRequest.getOperatorName();

            String messageText = "Do you want to continue to recharge."+
                    "\n\n"+alert1+
                    "\n"+alert2+
                    "\n"+alert3;

            AlertDialogUtils.showAlertdialogContext(CheckFinalDetailRechargeActivity.this, new AlertDialogButtonListener() {
                @Override
                public void onButtontapped(String btnText) {
                    if(btnText.equalsIgnoreCase("YES")){

                        /*Call recharge api*/
                        getRecharge();

                    }
                }
            },"Recharge",messageText);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Show DTH Recharge dialog*/
    void showDthRechargeDialog(){
        try{
           /* StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("Amount of :- " + strAmount);
            sb.append("\n");
            sb.append("Mobile no:- " + strAccountNo);
            sb.append("\n");
            sb.append("Operator of:- " + strOpratorName);*/
            String alert1 = "Amount of : " + rechargeRequest.getAmount();
            String alert2 = "Mobile no / Reg.Id : " + rechargeRequest.getAccountNo();
            String alert3 = "Operator of: " + rechargeRequest.getOperatorName();

            String messageText = "Do you want to continue to recharge."+
                    "\n\n"+alert1+
                    "\n"+alert2+
                    "\n"+alert3;

            AlertDialogUtils.showAlertdialogContext(CheckFinalDetailRechargeActivity.this, new AlertDialogButtonListener() {
                @Override
                public void onButtontapped(String btnText) {
                    if(btnText.equalsIgnoreCase("YES")){

                        /*Call recharge api*/
                        getRecharge();

                    }
                }
            },"Recharge",messageText);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Show alet dialog box
     * for asking before Hotel
     * room book.*/
    void showDialog() {
        try {
            AlertDialogUtils.showDialogWithTwoButton(CheckFinalDetailRechargeActivity.this, new AlertDialogButtonListener() {
                @Override
                public void onButtontapped(String btnText) {
                    if (btnText.equalsIgnoreCase("yes")) {
                        getRecharge();
                    }
                }
            }, "Recharge", "Do you want to continue process for recharge?", "No", "Yes");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                                    if(!ConnectivityUtils.isNetworkEnabled(CheckFinalDetailRechargeActivity.this)){
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

                                        // call recharge api
                                        getRecharge();
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
                              getRecharge();
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
