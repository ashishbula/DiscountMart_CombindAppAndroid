package in.discountmart.utility_services.recharge.activity;

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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.call_api.MainServices;
import in.discountmart.utility_services.listener.AlertDialogButtonListener;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.request_model.MainBalanceRequest;
import in.discountmart.utility_services.model.request_model.VerifyOtpRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.model.response_model.MainBalanceResponse;
import in.discountmart.utility_services.recharge.call_recharge_api.RechargeApi;
import in.discountmart.utility_services.recharge.recharge_model.recharge_request_model.RechargeRequest;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.RechargeResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.travel.flight.call_flight_api.FlightApi;
import in.discountmart.utility_services.travel.flight.flight_sharedpreference.FlightSharedValues;
import in.discountmart.utility_services.utilities.AlertDialogUtils;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class OtpAndVerify_RechargeActivity extends AppCompatActivity {

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
    String traceId="";
    String resultIndex="";
    String isLCC="";
    static String otpId="";
    static String otpnumber="";

    String serviceId="";
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
    String ServiceName="";
    String Servicetype="";
    String ServiceId="";
    String billAmount="";

    RechargeRequest rechargeRequest;

    View view;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_otp_and_verify__recharge);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view=findViewById(android.R.id.content);
        try {
            txtMessage=(TextView)findViewById(R.id.otp_veriry_recharge_act_txt_message);
            txtMobileNo=(TextView)findViewById(R.id.otp_veriry_recharge_act_txt_num);
            txtVerifyMsg=(TextView)findViewById(R.id.otp_veriry_recharge_act_txt_otpverify);
            edTxtOtp=(EditText)findViewById(R.id.otp_veriry_recharge_act_edtxt_otp);
            layoutBook=(LinearLayout) findViewById(R.id.otp_verify_recharge_act_layout_book);
            layoutOtpMsgVerify=(LinearLayout) findViewById(R.id.otp_verify_recharge_act_layout_otpverify);
            layoutOtpBtnVerify=(LinearLayout) findViewById(R.id.otp_verify_recharge_act_layout_btn_verify);
            btnPay_Book=(Button) findViewById(R.id.otp_verify_recharge_act_btn_flight_book);
            btnOTPVerify=(Button) findViewById(R.id.otp_verify_recharge_act_btn_otpverify);


            /*for toolbar button and title*/
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.str_recharge_verify_otp_title));

            Bundle bundle=getIntent().getExtras();
            if(bundle != null){

                rechargeRequest=new RechargeRequest();
                rechargeRequest= (RechargeRequest) bundle.getSerializable("RechargeRequest");

                assert rechargeRequest != null;
                billAmount=rechargeRequest.getAmount();
                strOtp=bundle.getString("OTPId");
                //ServiceName =bundle.getString("ServiceName");
                Servicetype =rechargeRequest.getServiceType();
                //ServiceId =bundle.getString("ServiceId");
            }


            /*Get Mobule no. Value from Login Shared Preference for showing in text*/
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(OtpAndVerify_RechargeActivity.this).getLoggedinUser();
            String mobile= loginResponse.getMobileNo().substring(0,loginResponse.getMobileNo().length()-2);
            String mobNumber=mobile.substring(7,10);
            txtMobileNo.setText(getResources().getString(R.string.str_otp_send_to)+"*******"+mobNumber);

            if (checkAndRequestPermissions()) {
                // carry on the normal flow, as the case of  permissions  granted.
            }
            LocalBroadcastManager.getInstance(OtpAndVerify_RechargeActivity.this).registerReceiver(receiver, new IntentFilter("otp"));

            /*Button Otp Veryfi*/
            btnOTPVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(edTxtOtp.getText().toString().equals("")){
                            Toast.makeText(OtpAndVerify_RechargeActivity.this,"Please Enter Otp",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            strOtp=edTxtOtp.getText().toString();
                            /*Hide soft input keyboard*/
                            View view = OtpAndVerify_RechargeActivity.this.getCurrentFocus();
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

                    totalPay= Float.parseFloat(rechargeRequest.getAmount());
                    float totCheckBal= mainBal;
                    if(totCheckBal >= totalPay){

                        /*Check network connection*/
                        if(!ConnectivityUtils.isNetworkEnabled(OtpAndVerify_RechargeActivity.this)){
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
                            /*Show dialog for confirm bill pay*/
                            if(Servicetype.equals("M") || Servicetype.equals("T")){
                                showRechargeDialog();
                            }
                            else if(Servicetype.equals("D")){
                                showDthRechargeDialog();
                            }

                        }

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

    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int receiveSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
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

        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        super.onBackPressed();


    }

    public void verifyOtp(){
        try {

            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait for otp verify..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            String strApiRequest="";
            JSONObject object=null;
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(OtpAndVerify_RechargeActivity.this).getLoggedinUser();
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
                    NetworkClient_Utility_1.getInstance(OtpAndVerify_RechargeActivity.this).create(FlightApi.class).fetchVerifyOtp(strToken,mainRequest);
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
                                    Toast.makeText(OtpAndVerify_RechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(OtpAndVerify_RechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OtpAndVerify_RechargeActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(OtpAndVerify_RechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                                        new LoginPreferences_Utility(OtpAndVerify_RechargeActivity.this).getLoggedinUser().setBalance(balanceResponse.getBalance());
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
                                Toast.makeText(OtpAndVerify_RechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OtpAndVerify_RechargeActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(OtpAndVerify_RechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
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

    /* Request and Response Recharge Api*/
    public void getRecharge(){
        try {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait..");
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

                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(request);

                strApiRequest=new Gson().toJson(apiRequest);

                Log.e("Value",strApiRequest);
            }catch (Exception e){
                e.printStackTrace();
            }

            Call<BaseResponse> fetchRecharge=
                    NetworkClient_Utility_1.getInstance(this).create(RechargeApi.class).fetchRechargeRequest(apiRequest,strToken);

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

                                    /*Call utility_main balance api*/
                                    getMainBalance();

                                    RechargeResponse rechargeResponse=
                                            new Gson().fromJson(Response.getRESP_VALUE(),RechargeResponse.class);

                                    if(rechargeResponse != null ){
                                        if(rechargeResponse.getStatus().equals("SUCCESS")){

                                            Intent intent=new Intent(OtpAndVerify_RechargeActivity.this,RechargeSucccessMsgActivity.class);
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
                                Toast.makeText(OtpAndVerify_RechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OtpAndVerify_RechargeActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(OtpAndVerify_RechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
                                // showSnackbar(toast);

                            }
                        }
                        else {
                            Snackbar.make(view, "Something wrong..", Snackbar.LENGTH_LONG)
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

            AlertDialogUtils.showAlertdialogContext(OtpAndVerify_RechargeActivity.this, new AlertDialogButtonListener() {
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

            AlertDialogUtils.showAlertdialogContext(OtpAndVerify_RechargeActivity.this, new AlertDialogButtonListener() {
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
}
