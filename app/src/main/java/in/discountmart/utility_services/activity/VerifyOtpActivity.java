package in.discountmart.utility_services.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.billpayment.activity.BillPayFinalDetailActivity;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.request_model.SendOtpRequest;
import in.discountmart.utility_services.model.request_model.VerifyOtpRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.recharge.activity.CheckFinalDetailRechargeActivity;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.sharedpreferences.SharedPrefrence_Utility;
import in.discountmart.utility_services.travel.bus.bus_activity.OtpAndBusBookPaymentActivity;
import in.discountmart.utility_services.travel.flight.call_flight_api.FlightApi;
import in.discountmart.utility_services.travel.flight.flight_activity.FlightBookOtp_PaymentActivity;
import in.discountmart.utility_services.travel.hotel.hotel_activity.OtpAndHotelPaymentActivity;
import in.discountmart.utility_services.travel.utility_cab.cab_activity.Otp_CabBookPaymentActivity;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class VerifyOtpActivity extends AppCompatActivity {
    TextView txtMessage;
    TextView txtMobileNo;
    TextView txtVerifyMsg;

    EditText edTxtOtp;
    LinearLayout layoutBook;

    Button btnOTPVerify;
    TextView btnResend;

    LinearLayout layoutOtpMsgVerify;
    LinearLayout layoutOtpBtnVerify;
    LinearLayout layoutOtpBtnResend;
    String strOtp="";
    String otpId="";
    String otpnumber="";
    String OtpSerice="";
    String ServiceName="";
    View view;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_verify_otp);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view=findViewById(android.R.id.content);
        try {
            txtMessage=(TextView)findViewById(R.id.otp_verify_act_txt_message);
            txtMobileNo=(TextView)findViewById(R.id.otp_verify_act_act_txt_num);
            txtVerifyMsg=(TextView)findViewById(R.id.otp_verify_act_txt_otpverify);
            edTxtOtp=(EditText)findViewById(R.id.otp_verify_act_edtxt_otp);
            layoutOtpBtnResend=(LinearLayout) findViewById(R.id.otp_verify_act_layout_resend);
            layoutOtpMsgVerify=(LinearLayout) findViewById(R.id.otp_verify_act_layout_msg_otpverify);
            layoutOtpBtnVerify=(LinearLayout) findViewById(R.id.otp_verify_act_layout_btn_verify);
            btnResend=(TextView) findViewById(R.id.otp_verify_act_btn_resend);
            btnOTPVerify=(Button) findViewById(R.id.otp_verify_act_btn_otpverify);

            /*for toolbar button and title*/
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.otp_verify));


            Intent intent=getIntent();
            if(intent != null){
                OtpSerice=intent.getStringExtra("OtpService");
                if(OtpSerice.contentEquals("Bus"))
                    ServiceName="B";
                else if(OtpSerice.contentEquals("Flight"))
                    ServiceName="F";
                else if(OtpSerice.contentEquals("Hotel"))
                    ServiceName="H";
                else if(OtpSerice.contentEquals("M"))
                    ServiceName="M";
                else if(OtpSerice.contentEquals("T"))
                    ServiceName="T";
                else if(OtpSerice.contentEquals("D"))
                    ServiceName="D";
                else if(OtpSerice.contentEquals("I"))
                    ServiceName="I";
                else if(OtpSerice.contentEquals("C"))
                    ServiceName="C";
                else if(OtpSerice.contentEquals("G"))
                    ServiceName="G";
                else if(OtpSerice.contentEquals("E"))
                    ServiceName="E";
                else if(OtpSerice.contentEquals("Cab"))
                    ServiceName="X";
            }

            /*Get Mobule no. Value from Login Shared Preference for showing in text*/
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(VerifyOtpActivity.this).getLoggedinUser();
            if(! loginResponse.getMobileNo().contentEquals("") && loginResponse.getMobileNo().length() > 10){
                String mobile= loginResponse.getMobileNo().substring(0,loginResponse.getMobileNo().length()-2);
                String mobNumber=mobile.substring(7,10);
                txtMobileNo.setText(getResources().getString(R.string.str_otp_send_to)+"*******"+mobNumber);
                layoutOtpBtnResend.setVisibility(View.GONE);
                layoutOtpBtnVerify.setVisibility(View.VISIBLE);
            }
            else if(! loginResponse.getMobileNo().contentEquals("") && loginResponse.getMobileNo().length() ==10){
                String mobile= loginResponse.getMobileNo();
                String mobNumber=mobile.substring(7,10);
                txtMobileNo.setText(getResources().getString(R.string.str_otp_send_to)+"*******"+mobNumber);
                layoutOtpBtnResend.setVisibility(View.GONE);
                layoutOtpBtnVerify.setVisibility(View.VISIBLE);
            }
            else {
                String mobile= "0000000000";
                String mobNumber=mobile.substring(7,10);
                txtMobileNo.setText(getResources().getString(R.string.str_otp_send_to)+"*******"+mobNumber);

                txtVerifyMsg.setText("Can't process Your mobile no. is not available,");
                layoutOtpBtnResend.setVisibility(View.GONE);
                layoutOtpBtnVerify.setVisibility(View.GONE);
            }

            /*Button Otp Veryfi*/
            btnOTPVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(edTxtOtp.getText().toString().equals("")){
                            Toast.makeText(VerifyOtpActivity.this,"Please Enter Otp",Toast.LENGTH_SHORT).show();
                        }
                        else {

                            strOtp=edTxtOtp.getText().toString();
                            View view = VerifyOtpActivity.this.getCurrentFocus();
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

            /*Text Resend otp on click listener*/
            btnResend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //call resend otp api
                    ResendSendOtp();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Request and response api  for  otp verify*/
    public void verifyOtp(){
        try {

            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait get otp verify");
            progressDialog.setCancelable(false);
            progressDialog.show();
            String strApiRequest="";
            JSONObject object=null;
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(VerifyOtpActivity.this).getLoggedinUser();
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
            verifyOtpRequest.setOTPID(SharedPrefrence_Utility.getOtpcode(this));

            ApiRequest mainRequest= new ApiRequest();
            /*Set Value in Main Request Model*/
            mainRequest.setDATA(verifyOtpRequest);
            mainRequest.setHEADER(headerRequest);

            strApiRequest=new Gson().toJson(mainRequest);

            Timber.e(strApiRequest);

            Call<BaseResponse> fetchVerifyOtp=
                    NetworkClient_Utility_1.getInstance(VerifyOtpActivity.this).create(FlightApi.class).fetchVerifyOtp(strToken,mainRequest);
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
                                    //BusSharedValues.getInstance().busBookOtp=otpnumber;
                                    SharedPrefrence_Utility.setOtpNumber(VerifyOtpActivity.this,otpnumber);

                                    layoutOtpBtnResend.setVisibility(View.GONE);
                                    if(OtpSerice.contentEquals("Bus")){
                                        Intent intentBus=new Intent(VerifyOtpActivity.this, OtpAndBusBookPaymentActivity.class);
                                        intentBus.putExtra("Msg","OK");
                                        setResult(RESULT_OK,intentBus);
                                        finish();
                                    }
                                    else if(OtpSerice.contentEquals("Flight")){
                                        Intent intentFlight=new Intent(VerifyOtpActivity.this, FlightBookOtp_PaymentActivity.class);
                                        intentFlight.putExtra("Msg","OK");
                                        setResult(RESULT_OK,intentFlight);
                                        finish();
                                    }

                                    else if(OtpSerice.contentEquals("Hotel")){
                                        Intent intentHotel=new Intent(VerifyOtpActivity.this, OtpAndHotelPaymentActivity.class);
                                        intentHotel.putExtra("Msg","OK");
                                        setResult(RESULT_OK,intentHotel);
                                        finish();
                                    }
                                    else if(OtpSerice.contentEquals("Cab")){
                                        Intent intentHotel=new Intent(VerifyOtpActivity.this, Otp_CabBookPaymentActivity.class);
                                        intentHotel.putExtra("Msg","OK");
                                        setResult(RESULT_OK,intentHotel);
                                        finish();
                                    }
                                    else if(OtpSerice.contentEquals("M")|| OtpSerice.contentEquals("T")
                                            ||OtpSerice.contentEquals("D")){
                                        Intent intentRecharge=new Intent(VerifyOtpActivity.this, CheckFinalDetailRechargeActivity.class);
                                        intentRecharge.putExtra("Msg","OK");
                                        setResult(RESULT_OK,intentRecharge);
                                        finish();
                                    }
                                    else if(OtpSerice.contentEquals("I")|| OtpSerice.contentEquals("G")
                                            ||OtpSerice.contentEquals("C") || OtpSerice.contentEquals("E")){
                                        Intent intentBillPay=new Intent(VerifyOtpActivity.this, BillPayFinalDetailActivity.class);
                                        intentBillPay.putExtra("Msg","OK");
                                        setResult(RESULT_OK,intentBillPay);
                                        finish();
                                    }
                                    // call api utility_main utility_wallet
                                    //getMainBalance();

                                }
                                else if(Response.getRESP_VALUE().equals("")){
                                    String toast= Response.getRESP_MSG();
                                    layoutOtpBtnResend.setVisibility(View.VISIBLE);
                                    layoutOtpBtnVerify.setVisibility(View.VISIBLE);

                                    txtVerifyMsg.setText(Response.getRESP_MSG());
                                    Toast.makeText(VerifyOtpActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    if(OtpSerice.contentEquals("I")|| OtpSerice.contentEquals("G")
                                            ||OtpSerice.contentEquals("C") || OtpSerice.contentEquals("E")){
                                        Intent intent1=new Intent(VerifyOtpActivity.this, BillPayFinalDetailActivity.class);
                                        setResult(RESULT_OK,intent1);
                                        finish();
                                    }
                                }

                            }
                            else if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")){

                                String toast= Response.getRESPONSE()+ ": " +Response.getRESP_MSG();
                                //layoutBook.setVisibility(View.GONE);
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
                                Toast.makeText(VerifyOtpActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(VerifyOtpActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                //layoutBook.setVisibility(View.GONE);
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

    /* Request and Response ReSend Otp*/
    public void ResendSendOtp(){

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
            sendOtpRequest.setServiceName(ServiceName);
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
                NetworkClient_Utility_1.getInstance(VerifyOtpActivity.this).create(FlightApi.class).fetchFlightBookOtp(apiRequest,strToken);
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
                                SharedPrefrence_Utility.setOtpCode(VerifyOtpActivity.this,otpId);

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
                            Toast.makeText(VerifyOtpActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(VerifyOtpActivity.this, LoginActivity.class);
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
