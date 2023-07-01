package in.discountmart.utility_services.fund.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility.ConnectivityUtils;
import in.discountmart.utility_services.activity.MainActivity_utility;
import in.discountmart.utility_services.billpayment.activity.BillPayFinalDetailActivity;
import in.discountmart.utility_services.call_api.MainServices;
import in.discountmart.utility_services.fund.adapter.WalletTypeAdapter;
import in.discountmart.utility_services.fund.fund_api.FundApi;
import in.discountmart.utility_services.fund.fund_model.request.CheckFundRequest;
import in.discountmart.utility_services.fund.fund_model.request.DeductFundRequest;
import in.discountmart.utility_services.fund.fund_model.request.GatewayOrderIdRequest;
import in.discountmart.utility_services.fund.fund_model.request.WalletTypeRequest;
import in.discountmart.utility_services.fund.fund_model.response.CheckFundResponse;
import in.discountmart.utility_services.fund.fund_model.response.GatwayOrderIdResponse;
import in.discountmart.utility_services.fund.fund_model.response.WalletTypeResponse;
import in.discountmart.utility_services.listener.AlertDialogButtonListener;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.request_model.DebitAmountRequest;
import in.discountmart.utility_services.model.request_model.MainBalanceRequest;
import in.discountmart.utility_services.model.request_model.SendOtpRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.model.response_model.MainBalanceResponse;
import in.discountmart.utility_services.recharge.activity.CheckFinalDetailRechargeActivity;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.travel.bus.bus_activity.OtpAndBusBookPaymentActivity;
import in.discountmart.utility_services.travel.flight.call_flight_api.FlightApi;
import in.discountmart.utility_services.travel.flight.flight_activity.FlightBookOtp_PaymentActivity;
import in.discountmart.utility_services.travel.hotel.hotel_activity.OtpAndHotelPaymentActivity;
import in.discountmart.utility_services.utilities.AlertDialogUtils;
import in.discountmart.utility_services.utilities.TokenBase64;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFundActivity extends AppCompatActivity  {
    TextView txtMemberId;
    TextView txtFundMsg;
    TextView txtAvailFund;
    TextView txtMainBal;
    TextView txtDeductMsg;
    EditText edtxtTransPass;
    EditText edtxtFundReq;
    EditText edtxtEmail;
    EditText edtxtMobile;
    EditText edtxtReqGatwayAmount;
    RadioGroup rdgOption;
    RadioButton rdbEWallet;
    RadioButton rdbOnline;
    AppCompatSpinner spinnerWalletType;
    Button btnCheck;
    Button btnRequest;
    Button btnReqGatway;
    TextInputLayout inputLayAmntReq;
    LinearLayout layoutFundReq;
    LinearLayout layoutGatway;
    LinearLayout layoutEwallet;
    View view;
    ProgressDialog progressDialog;

    WalletTypeAdapter walletAdapter;
    ArrayList<WalletTypeResponse> walletTypeList;
    LoginResponse loginResponse;

    String walletType = "";
    String serviceType = "";
    String walletVal = "";
    double e_walletFund = 0;
    double reqFund = 0;
    double deciReqFund = 0;
    String reqAmount = "";
    String transPass = "";
    String otpId = "";
    String mobileNo = "";
    String memberId = "";
    String emailID = "";
    String name = "";
    String address = "";
    String selectOption = "";
    String orderDate="";
    String orderId = "";
    String service_GW = "";
    String home = "";

    double mainBal = 0;
    int totReqAmount = 0;
    double order_amoumt=0;
    double req_amoumt=0;

    boolean otpService;
    boolean gatewayfromHome=false;
    boolean addFundfromHome=false;

    private Timer timer = new Timer();
    private final long DELAY = 600; // in ms

    // For Payment Gateway Paytm

    private String mPaymentToken = "";
    private String mAccessKey = "9ef75b80-c283-11ea-9f9d-e51962785b5c";// live

    private String merchentId_Test="VlJmGV42276932466884";
    private String merchentKey_Test="2i_CTPdGL8jP1U88";

    private String merchentKey_Live="dnVcloWr42pcrNbV";
    private String merchentId_Live="zZHYpy94148089052325";
    private String strSignature="";
    private String strToken="";
    Integer ActivityRequestCode = 2;

    // for payment gateway Razor pay
    // String orderId="";
    String keyID_test="rzp_test_0nE87tlcn32uGD";
    String secret_test="lELoCDjczrkXt2CvzLFC7G35";
    //String keyID_live="rzp_live_PpLj9eSWqvra8K";
    String keyID_live="rzp_live_cBJCpYzLP3ZDN7";
    //String secret_live="0ydzLHgOfGadTxntZftaZba1";
    String secret_live="ECqKTa5O0QLW1nW3tCIxC4SN";
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    //df496de0-ab1f-11ea-9247-efc71315c08c test key
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_add_fund);
        try {
            view = findViewById(android.R.id.content);

            /*Component View Reference*/
            txtMemberId = (TextView) findViewById(R.id.add_fund_act_txt_memberid);
            txtAvailFund = (TextView) findViewById(R.id.add_fund_act_txt_available_fund);
            txtMainBal = (TextView) findViewById(R.id.add_fund_act_txt_balance);
            txtFundMsg = (TextView) findViewById(R.id.add_fund_act_txt_message);
            txtDeductMsg = (TextView) findViewById(R.id.add_fund_act_txt_deduct_msg);
            inputLayAmntReq = (TextInputLayout) findViewById(R.id.add_fund_act_txtInputLay_amntreq);
            edtxtFundReq = (EditText) findViewById(R.id.add_fund_act_edtxt_request_amount);
            edtxtEmail = (EditText) findViewById(R.id.add_fund_act_edtxt_email);
            edtxtMobile = (EditText) findViewById(R.id.add_fund_act_edtxt_mobile);
            edtxtReqGatwayAmount = (EditText) findViewById(R.id.add_fund_act_edtxt_gatewayreq_amount);
            edtxtTransPass = (EditText) findViewById(R.id.add_fund_act_edtxt_trans_pass);
            spinnerWalletType = (AppCompatSpinner) findViewById(R.id.add_fund_act_spinner_fund_from);
            btnCheck = (Button) findViewById(R.id.add_fund_act_btn_checkfund);
            btnRequest = (Button) findViewById(R.id.add_fund_act_btn_fund_request);
            btnReqGatway = (Button) findViewById(R.id.add_fund_act_btn_fund_gatewayreq);
            layoutFundReq = (LinearLayout) findViewById(R.id.add_fund_act_layout_fund_request);
            layoutEwallet = (LinearLayout) findViewById(R.id.add_fund_act_layout_ewallet);
            layoutGatway = (LinearLayout) findViewById(R.id.add_fund_act_layout_gateway);
            rdgOption = (RadioGroup) findViewById(R.id.add_fund_act_rdg_option);
            rdbEWallet = (RadioButton) findViewById(R.id.add_fund_act_rdb_wallet);
            rdbOnline = (RadioButton) findViewById(R.id.add_fund_act_rdb_gateway);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add Fund");

            // Call Utility Wallet Balance api
            if(!ConnectivityUtils.isNetworkEnabled(AddFundActivity.this)){
                Toast.makeText(AddFundActivity.this,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
            }
            else {
                getMainUtilityWalletBalance();
            }


            Intent intent = getIntent();
            if (intent != null) {
                home = intent.getStringExtra("Home");

                if (home.contentEquals("true")) {
                    service_GW = "";
                    reqAmount = "";
                    serviceType = "";
                    reqFund = 0;
                    gatewayfromHome=true;
                    addFundfromHome=true;
                } else {
                    service_GW = intent.getStringExtra("GW_Service");
                    reqAmount = intent.getStringExtra("DeductAmount");
                    Log.e("ReqAmount",reqAmount);
                    serviceType = intent.getStringExtra("ServiceType");
                    reqFund = Double.parseDouble(reqAmount);
                    Log.e("ReqAmount", String.valueOf(reqFund));
                    gatewayfromHome=false;
                    addFundfromHome=false;
                }

            }
            if (home.contentEquals("false")) {
                txtDeductMsg.setText("Remaining amount deduct from");
            } else {
                txtDeductMsg.setText("Add amount in your utility wallet then");
            }

            walletTypeList = new ArrayList<WalletTypeResponse>();
            walletAdapter = new WalletTypeAdapter(AddFundActivity.this, walletTypeList);
            spinnerWalletType.setAdapter(walletAdapter);

            /*Get Value from login shared preference */
            loginResponse = new LoginResponse();
            loginResponse = new LoginPreferences_Utility(AddFundActivity.this).getLoggedinUser();
            txtMemberId.setText(loginResponse.getUserName());
            memberId=loginResponse.getUserName();
            mobileNo = loginResponse.getMobileNo().substring(0, loginResponse.getMobileNo().length() - 2);
            emailID = loginResponse.getEmailId();
            address = loginResponse.getAddress();
            Log.e("Mobile No :- ",mobileNo);

            /* Check Default radio button
            * for select option that add fund from
            * E- wallet or Online payment
            * is check or not*/
            /*if(rdbEWallet.isChecked()){
                layoutGatway.setVisibility(View.GONE);
                layoutEwallet.setVisibility(View.VISIBLE);
                *//*Call api Wallet type*//*
                getWalletList();
            }
            else if (rdbOnline.isChecked()) {
                layoutGatway.setVisibility(View.VISIBLE);
                        layoutEwallet.setVisibility(View.GONE);
                        if (home.contentEquals("false")) {
                            edtxtReqGatwayAmount.setText(reqAmount);
                            edtxtReqGatwayAmount.setEnabled(false);
                        } else {
                            edtxtReqGatwayAmount.setText("");
                            edtxtReqGatwayAmount.setEnabled(true);
                        }

                        if (mobileNo.equals("")) {

                            edtxtMobile.setEnabled(true);
                        } else {

                            edtxtMobile.setText(mobileNo);
                            edtxtMobile.setEnabled(false);
                        }
                        if (emailID.equals("")) {

                            edtxtEmail.setEnabled(true);
                        } else {
                            edtxtEmail.setEnabled(false);
                            edtxtEmail.setText(emailID);
                        }
            }*/


            /*Radion Group choose option on check change listener*/
            rdgOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = (RadioButton) group.findViewById(checkedId);
                    selectOption = rb.getText().toString();

                    if (selectOption.contentEquals("Wallet")) {//EP Wallet
                        layoutGatway.setVisibility(View.GONE);
                        layoutEwallet.setVisibility(View.VISIBLE);
                        /*Call api Wallet type*/
                        getWalletList();
                    } else {
                        layoutGatway.setVisibility(View.VISIBLE);
                        layoutEwallet.setVisibility(View.GONE);
                        if (home.contentEquals("false")) {
                            edtxtReqGatwayAmount.setText(reqAmount);
                            edtxtReqGatwayAmount.setEnabled(false);
                        } else {
                            edtxtReqGatwayAmount.setText("");
                            edtxtReqGatwayAmount.setEnabled(true);
                        }

                        if (mobileNo.equals("")) {

                            edtxtMobile.setEnabled(true);
                        } else {

                            edtxtMobile.setText(mobileNo);
                            edtxtMobile.setEnabled(false);
                        }
                        if (emailID.equals("")) {

                            edtxtEmail.setEnabled(true);
                        } else {
                            edtxtEmail.setEnabled(false);
                            edtxtEmail.setText(emailID);
                        }

                    }
                }
            });

            /*Button  payment gateway request on click*/
            btnReqGatway.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtxtReqGatwayAmount.getText().toString().contentEquals("")) {
                        edtxtReqGatwayAmount.setError("Enter Amount");
                        edtxtReqGatwayAmount.requestFocus();
                    }
                    else if (edtxtMobile.getText().toString().equals("")) {
                        edtxtMobile.setError("Enter mobile no.");
                        edtxtMobile.requestFocus();
                    }
                    else if (edtxtEmail.getText().toString().equals("")) {
                        edtxtEmail.setError("Enter email id.");
                        edtxtEmail.requestFocus();
                    }
                    else {

                        reqFund = Double.parseDouble(edtxtReqGatwayAmount.getText().toString());
                        reqAmount = edtxtReqGatwayAmount.getText().toString();
                        mobileNo = edtxtMobile.getText().toString();
                        emailID = edtxtEmail.getText().toString();
                        // call get company balance api
                        // call Payment token key api
                        //getPaymentTokenKey(reqAmount,mobileNo,emailID);


                        if(!ConnectivityUtils.isNetworkEnabled(AddFundActivity.this)){
                            Toast.makeText(AddFundActivity.this,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //getMainCompBalance();
                            showDialogContinueAddFund_FromGateway();
                        }

                    }
                }
            });

            /*Spinner Package List */
            spinnerWalletType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // check = check + 1;
                    //if (check > 1)
                    // Get selected operator entity
                    WalletTypeResponse walletList = (WalletTypeResponse) parent.getItemAtPosition(position);
                    /*if(packageList1.getPkgid().equals("")){
                        Toast.makeText(getActivity(),"Please Id not Available",Toast.LENGTH_SHORT).show();
                    }*/

                    walletType = walletList.getWalletType();
                    walletVal = walletList.getWalletTypeVal();

                    inputLayAmntReq.setVisibility(View.GONE);
                    layoutFundReq.setVisibility(View.GONE);
                    txtAvailFund.setText("");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /*Button Check fund*/
            btnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Call Check fund Api*/
                    if (edtxtTransPass.getText().toString().contentEquals("")) {
                        edtxtTransPass.setError("Enter transaction password");
                        edtxtTransPass.requestFocus();
                    }
                    else {
                        transPass = edtxtTransPass.getText().toString();
                        LoginResponse loginResponse = new LoginPreferences_Utility(AddFundActivity.this).getLoggedinUser();

                        View view = AddFundActivity.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(),
                                    0);
                        }
                        getcheckFund();
                        /*if (transPass.contentEquals(loginResponse.getPasswd())) {
                            //Call check fund request
                            getcheckFund();
                        } else {
                            Toast.makeText(AddFundActivity.this, "Please enter correct password.", Toast.LENGTH_SHORT).show();
                        }*/

                    }

                }
            });

            /*Button Fund request on click*/
            btnRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (edtxtTransPass.getText().toString().contentEquals("")) {
                            edtxtTransPass.setError("Enter transaction password");
                            edtxtTransPass.requestFocus();
                        } else if (edtxtFundReq.getText().toString().contentEquals("") || edtxtFundReq.getText().toString().contentEquals("0")) {
                            edtxtFundReq.setError("Enter request amount");
                            edtxtFundReq.requestFocus();
                        } else {
                            // LoginResponse loginResponse=new LoginPreferences_brand(AddFundActivity.this).getLoggedinUser();
                            reqFund = Double.parseDouble(edtxtFundReq.getText().toString());
                            reqAmount = edtxtFundReq.getText().toString();
                            transPass = edtxtTransPass.getText().toString();
                            View view = AddFundActivity.this.getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(),
                                        0);
                            }

                            if (reqFund <= e_walletFund) {

                                /*Call otp api*/
                                if (mobileNo.contentEquals("") || mobileNo.length() < 10) {
                                    showMsgDialog1();
                                    //getMainCompBalance();

                                }
                                else {

                                    if(!ConnectivityUtils.isNetworkEnabled(AddFundActivity.this)){
                                        Toast.makeText(AddFundActivity.this,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        //getMainCompBalance();
                                        showDialogContinueAddFund_FromWallet();
                                    }
                                }
                            } else {
                                Toast.makeText(AddFundActivity.this, "Request amount should not greater from available fund.", Toast.LENGTH_SHORT).show();

                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* Request and Response Get Main Balance*/
    public void getMainUtilityWalletBalance() {
        try {

            progressDialog = new ProgressDialog(AddFundActivity.this);
            progressDialog.setMessage("please wait...");
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

                        if (Response != null) {
                            if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                if (!Response.getRESP_VALUE().equals("null") || !Response.getRESP_VALUE().equals("")) {

                                    MainBalanceResponse balanceResponse =
                                            new Gson().fromJson(Response.getRESP_VALUE(), MainBalanceResponse.class);

                                    //String toast= Response.getRESP_MSG();
                                    //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    if (balanceResponse != null) {
                                        // get Main Wallet balance
                                        new LoginPreferences_Utility(AddFundActivity.this).getLoggedinUser().setBalance(balanceResponse.getBalance());
                                        double bal = Float.parseFloat(balanceResponse.getBalance());
                                        mainBal = Math.round(bal);
                                        txtMainBal.setText(getResources().getString(R.string.rs_symbol) +""+balanceResponse.getBalance());

                                    }

                                }
                                else if (Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")) {
                                    String toast = Response.getRESP_MSG();
                                    Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    //showSnackbar(toast);

                                }

                            }
                            else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Try Again ";
                                Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                                //showSnackbar(toast);
                            }
                            else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddFundActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                            else {

                                String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                                Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                                // showSnackbar(toast);

                            }
                        } else {
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
                    Toast.makeText(AddFundActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Request and Response Promocode List*/
    public void getWalletList() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String strApiRequest = "";
        JSONObject object = null;
        String strToken = TokenBase64.getToken();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse = new LoginPreferences_Utility(AddFundActivity.this).getLoggedinUser();
        String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
        String formNo = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);

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

            /*Promocode Request Model*/
            WalletTypeRequest request = new WalletTypeRequest();
            request.setSponsorFormNo(companyId);

            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(request);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<BaseResponse> fetchWalletTypeCall =
                NetworkClient_Utility_1.getInstance(this).create(FundApi.class).fetchWalletType(apiRequest, strToken);

        fetchWalletTypeCall.enqueue(new Callback<BaseResponse>() {
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
                                Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();

                            } else if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                String[] walletList = Response.getRESPONSE().split("");
                                if (walletList.length > 0) {
                                    WalletTypeResponse[] walletResList = new Gson().fromJson(Response.getRESP_VALUE(), WalletTypeResponse[].class);
                                    List<WalletTypeResponse> tempList = new ArrayList<WalletTypeResponse>(Arrays.asList(walletResList));

                                    // adding contacts to contacts list
                                    walletTypeList.clear();
                                    walletTypeList.addAll(tempList);
                                    // refreshing recycler view
                                    walletAdapter.notifyDataSetChanged();
                                } else {
                                    String toast = " Wallet List empty";
                                    Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    //showSnackbar(toast);
                                }
                            }
                        } else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddFundActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            // showSnackbar(toast);

                        }
                    } else {
                        String toast = "Something wrong..";
                        //Toast.makeText(PromocodeActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddFundActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    /* Request and Response Check Fund*/
    public void getcheckFund() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String strApiRequest = "";
        JSONObject object = null;
        String strToken = TokenBase64.getToken();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse = new LoginPreferences_Utility(AddFundActivity.this).getLoggedinUser();
        String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
        String formNo = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);

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

            /*Promocode Request Model*/
            CheckFundRequest request = new CheckFundRequest();
            request.setAction("C");
            request.setAmount("");
            request.setFormNo(formNo);
            request.setPassword(transPass);
            request.setUserName(loginResponse.getUserName());
            request.setWalletType(walletVal);

            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(request);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<BaseResponse> fetchWalletTypeCall =
                NetworkClient_Utility_1.getInstance(this).create(FundApi.class).fetchCheckFund(apiRequest, strToken);

        fetchWalletTypeCall.enqueue(new Callback<BaseResponse>() {
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
                                Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            }

                            else if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {

                                CheckFundResponse fundResponse = new Gson().fromJson(Response.getRESP_VALUE(), CheckFundResponse.class);
                                if (fundResponse.getWalletBalance() == null) {
                                    txtAvailFund.setText("sorry... amount is not available in this wallet");

                                } else {
                                    //txtAvailFund.setText("sorry... amount is not available in this wallet");

                                    double fund = Double.parseDouble(fundResponse.getWalletBalance());
                                    //e_walletFund = (int) Math.round(fund);
                                    e_walletFund =  Math.round(fund);
                                    if (e_walletFund > 0) {
                                        inputLayAmntReq.setVisibility(View.VISIBLE);
                                        layoutFundReq.setVisibility(View.VISIBLE);
                                        if (home.contentEquals("false")) {
                                            edtxtFundReq.setText(String.valueOf(reqFund));
                                            edtxtFundReq.setEnabled(false);
                                        } else {
                                            edtxtFundReq.setText("");
                                            edtxtFundReq.setEnabled(true);
                                        }
                                        txtAvailFund.setText("Available Fund :" + getResources().getString(R.string.rs_symbol) + " " + e_walletFund);


                                    } else {
                                        inputLayAmntReq.setVisibility(View.GONE);
                                        layoutFundReq.setVisibility(View.GONE);
                                        txtAvailFund.setText("Available Fund :" + getResources().getString(R.string.rs_symbol) + " " + e_walletFund + "\n"
                                                + " sorry... amount is  not sufficient.");
                                    }
                                }


                            }
                        }
                        else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            txtAvailFund.setText(toast);
                            //Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();

                        }
                        else if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("FAILED")) {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            txtAvailFund.setText(toast);
                            //Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddFundActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            // showSnackbar(toast);

                        }
                    }
                    else {
                        String toast = "something wrong..";
                        //Toast.makeText(PromocodeActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddFundActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    /* Request and Response Deduct Fund*/
    public void getDeductFund() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String strApiRequest = "";
        JSONObject object = null;
        String strToken = TokenBase64.getToken();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse = new LoginPreferences_Utility(AddFundActivity.this).getLoggedinUser();
        String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
        String formNo = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);

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

            /*Promocode Request Model*/
            DeductFundRequest request = new DeductFundRequest();
            request.setAction("D");
            request.setAmount(reqAmount);
            request.setFormNo(formNo);
            request.setPassword(transPass);
            request.setUserName(loginResponse.getUserName());
            request.setWalletType(walletVal);

            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(request);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<BaseResponse> fetchWalletTypeCall =
                NetworkClient_Utility_1.getInstance(this).create(FundApi.class).fetchDeductFund(apiRequest, strToken);

        fetchWalletTypeCall.enqueue(new Callback<BaseResponse>() {
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
                                Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            }
                            else if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {

                                CheckFundResponse fundResponse = new Gson().fromJson(Response.getRESP_VALUE(), CheckFundResponse.class);
                                double deduct = Double.parseDouble(fundResponse.getDeductamount());
                                //int deductBal = (int) Math.round(deduct);
                                double deductBal =  Math.round(deduct);


                               // Check from which activity come and again to previous
                                if (deductBal == reqFund) {
                                    if (home.contentEquals("true")) {

                                        Toast.makeText(AddFundActivity.this, "Add Fund Successfull", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AddFundActivity.this, MainActivity_utility.class);
                                        startActivity(intent);
                                        finish();
                                        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

                                        // call Check Utility Wallet api
                                        //getMainUtilityWalletBalance();


                                    } else {
                                        Bundle bundle = new Bundle();
                                        if (serviceType.contentEquals("Bus")) {
                                            Intent intent = new Intent(AddFundActivity.this, OtpAndBusBookPaymentActivity.class);
                                            bundle.putString("Msg", "OK");
                                            intent.putExtras(bundle);
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        } else if (serviceType.contentEquals("Flight")) {
                                            Intent intent = new Intent(AddFundActivity.this, FlightBookOtp_PaymentActivity.class);
                                            bundle.putString("Msg", "OK");
                                            intent.putExtras(bundle);
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        } else if (serviceType.contentEquals("Hotel")) {
                                            Intent intent = new Intent(AddFundActivity.this, OtpAndHotelPaymentActivity.class);
                                            bundle.putString("Msg", "OK");
                                            intent.putExtras(bundle);
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        } else if (serviceType.contentEquals("M") || serviceType.contentEquals("T")
                                                || serviceType.contentEquals("D")) {
                                            Intent intent1 = new Intent(AddFundActivity.this, CheckFinalDetailRechargeActivity.class);
                                            bundle.putString("Msg", "OK");
                                            intent1.putExtras(bundle);
                                            setResult(RESULT_OK, intent1);
                                            finish();
                                        } else if (serviceType.contentEquals("I") || serviceType.contentEquals("G")
                                                || serviceType.contentEquals("C") || serviceType.contentEquals("E")) {
                                            Intent intent1 = new Intent(AddFundActivity.this, BillPayFinalDetailActivity.class);
                                            bundle.putString("Msg", "OK");
                                            intent1.putExtras(bundle);
                                            setResult(RESULT_OK, intent1);
                                            finish();
                                        }

                                    }
                                } else {
                                    Toast.makeText(AddFundActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();

                                }


                            }
                        }
                        else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            //Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddFundActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            // showSnackbar(toast);

                        }
                    } else {
                        String toast = "Something  wrong..";
                        //Toast.makeText(PromocodeActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddFundActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    /* Request and Response Get company Balance*/
    public void getMainCompBalance() {
        try {

            progressDialog = new ProgressDialog(AddFundActivity.this);
            progressDialog.setMessage("please wait...");
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
                MainBalanceRequest request = new MainBalanceRequest();
                request.setFormNo(companyId);

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

                        if (Response != null) {
                            if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                if (!Response.getRESP_VALUE().equals("null") || !Response.getRESP_VALUE().equals("")) {

                                    MainBalanceResponse balanceResponse =
                                            new Gson().fromJson(Response.getRESP_VALUE(), MainBalanceResponse.class);

                                    if (balanceResponse != null) {

                                        float bal = Float.parseFloat(balanceResponse.getBalance());
                                        //int compBal = Math.round(bal);
                                        double compBal = Math.round(bal);
                                        Log.e("CompanyBal :-",String.valueOf(compBal));
                                        //int reqBal= Integer.parseInt(edtxtFundReq.getText().toString());

                                        /* When Select E-Wallet option
                                        * than using wallet fund will be add
                                        * */

                                        if (rdbEWallet.isChecked()) {//
                                            if(addFundfromHome){
                                                if (reqFund < compBal) {
                                                    if(Integer.parseInt(reqAmount) >= 10){
                                                        //Call Debit amoutnapi
                                                        getDeductFund();
                                                    }
                                                    else {
                                                        String msg="Amount should be more than or equal 10/-";

                                                        AlertDialogUtils.showDialog(AddFundActivity.this,
                                                                "Alert Dialog", msg);
                                                    }

                                                } else {
                                                    AlertDialogUtils.showDialog(AddFundActivity.this,
                                                            "Alert Dialog", "Contact to administrator");
//Toast.makeText(AddFundActivity.this, "Contact to administrator", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            else {
                                                if (reqFund < compBal) {
                                                    if(Integer.parseInt(reqAmount) > 0){
                                                        //Call Debit amoutnapi
                                                        getDeductFund();
                                                    }
                                                    else {
                                                        String msg="Amount should be more than 0/-";

                                                        AlertDialogUtils.showDialog(AddFundActivity.this,
                                                                "Alert Dialog", msg);
                                                    }

                                                } else {
                                                    AlertDialogUtils.showDialog(AddFundActivity.this,
                                                            "Alert Dialog", "Contact to administrator");
//Toast.makeText(AddFundActivity.this, "Contact to administrator", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                        }
                                        /* When select another option online payment
                                        * then using payment gateway deduct payment from
                                        * user account and add his wallet
                                        * than next process will be
                                        * execute */

                                        else {
                                            if(gatewayfromHome){
                                                if (reqFund < compBal) {
                                                    // call Payment token key api

                                                    if(reqFund >= 1){
                                                        //Call Debit amoutnapi
                                                        if(!ConnectivityUtils.isNetworkEnabled(AddFundActivity.this)){
                                                            Toast.makeText(AddFundActivity.this,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                                                        }
                                                        else {
                                                            //DebitAmountApi();

                                                            //getOrderID_PaymentGateway(keyID_live,secret_live,reqAmount);
                                                        }

                                                    }
                                                    else {
                                                        String msg="Amount should be more than and equal 10/-";
                                                        AlertDialogUtils.showDialog(AddFundActivity.this,
                                                                "Alert Dialog", msg);
                                                    }

                                                }
                                                else {
                                                    AlertDialogUtils.showDialog(AddFundActivity.this,
                                                            "Alert Dialog", "Contact to administrator");


                                                    //Toast.makeText(AddFundActivity.this, "Contact to administrator", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                            else {
                                                if (reqFund < compBal) {
                                                    // call Payment token key api

                                                    if(reqFund > 0){
                                                        //Call Debit amoutnapi
                                                        if(!ConnectivityUtils.isNetworkEnabled(AddFundActivity.this)){
                                                            Toast.makeText(AddFundActivity.this,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                                                        }
                                                        else {
                                                            //DebitAmountApi();

                                                            //getOrderID_PaymentGateway(keyID_live,secret_live,reqAmount);
                                                        }

                                                    }
                                                    else {
                                                        String msg="Please check amount !";
                                                        AlertDialogUtils.showDialog(AddFundActivity.this,
                                                                "Alert Dialog", msg);
                                                    }

                                                }
                                                else {
                                                    AlertDialogUtils.showDialog(AddFundActivity.this,
                                                            "Alert Dialog", "Contact to administrator");


                                                    //Toast.makeText(AddFundActivity.this, "Contact to administrator", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        }

                                    }

                                }
                                else if (Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")) {
                                    String toast = Response.getRESP_MSG();
                                    Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    //showSnackbar(toast);

                                }

                            } else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Try Again ";
                                Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                                //showSnackbar(toast);
                            }
                            else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddFundActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                            else {

                                String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                                Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AddFundActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Request and Response Add fund Send Otp*/
    public void addFundSentOtp() {

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
            sendOtpRequest.setServiceName("U");
            sendOtpRequest.setUserName(new LoginPreferences_Utility(this).getLoggedinUser().getUserName());

            /*Set Value in Main Request Model*/
            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(sendOtpRequest);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<BaseResponse> fetchOtp =
                NetworkClient_Utility_1.getInstance(AddFundActivity.this).create(FlightApi.class).fetchFlightBookOtp(apiRequest, strToken);
        fetchOtp.enqueue(new Callback<BaseResponse>() {
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


                                Intent intent = new Intent(AddFundActivity.this, AddfundOtp_VerifyActivity.class);
                                //Intent intent=new Intent(CheckFinalDetailRechargeActivity.this, OtpActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("OTPId", otpId);
                                intent.putExtras(bundle);
                                startActivityForResult(intent, 1);
                                //finish();
                                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

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

                        } else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {
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
                        } else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                            String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                            Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddFundActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            // showSnackbar(toast);

                        }
                    } else {
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

    /*For Payment Gateway Api's*/
    /* Debit amount api for first
       deduct amount from wallet
    Request and Response Deduct Fund*/
    public void DebitAmountApi(String OrderAmount,String emailid, String mobile,String orderID) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String strApiRequest = "";
        JSONObject object = null;
        String strToken = TokenBase64.getToken();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse = new LoginPreferences_Utility(AddFundActivity.this).getLoggedinUser();
        String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
        String formNo = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);

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

            /*Promocode Request Model*/
            DebitAmountRequest request = new DebitAmountRequest();
            //orderId = System.currentTimeMillis() + "";

            request.setAmount(reqAmount);
            // request.setAmount(String.valueOf(reqFund));
            request.setFormNo(formNo);
            request.setOrderNo(orderID);

            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(request);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<BaseResponse> fetchDebitAmountCall =
                NetworkClient_Utility_1.getInstance(this).create(FundApi.class).fetchDebitAmount(apiRequest, strToken);

        fetchDebitAmountCall.enqueue(new Callback<BaseResponse>() {
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
                                Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            }
                            else if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                // call gateway api
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        // setPaymentView(mPaymentToken,mAccessKey);
                                        double amount= Double.parseDouble(reqAmount);
                                        //initPayment_Token(orderId,merchentId_Live,merchentKey_Live,String.valueOf(amount));

                                        //getOrderID_PaymentGateway(keyID_live,secret_live,reqAmount);
                                        //startPayment(OrderAmount,emailid,mobile,orderId);
                                    }
                                }, 300);


                            }
                            else {
                                Toast.makeText(AddFundActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }

                        }
                        else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddFundActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }
                        else {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            // showSnackbar(toast);

                        }
                    }
                    else {
                        String toast = "Something  wrong";
                        //Toast.makeText(PromocodeActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddFundActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

  /* ************* First we call this api
  1 ) getOrderID_PaymentGateway Api for Get OrderID get from Razor api
  2) Then We call Debit Amount Api for debit amount from user a/c
  3) Then we startPayment api for start gateway page
  4) Then last call credit amount api to credit amount in user a/c.
      after gateway response success or not
  ********************************************/






    private String hmacSha(String KEY, String VALUE, String SHA_TYPE) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(KEY.getBytes("UTF-8"), SHA_TYPE);
            Mac mac = Mac.getInstance(SHA_TYPE);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(VALUE.getBytes("UTF-8"));
            byte[] hexArray = {(byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f'};
            byte[] hexChars = new byte[rawHmac.length * 2];
            for (int j = 0; j < rawHmac.length; j++) {
                int v = rawHmac[j] & 0xFF;
                hexChars[j * 2] = hexArray[v >>> 4];
                hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            }
            return new String(hexChars);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /* Credit api for
    Request and Response Credit Fund in user wallet
    after gateway response then this api will be call
    if Gateway response success then credit amount in user wallet
    if Gateway response failed then not credit in wallet, wallet amount as it is as before debit  */
    public void CreditAmountApi(String amount,String orderId,String tid, String status,String transDate, String response) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String strApiRequest = "";
        JSONObject object = null;
        String strToken = TokenBase64.getToken();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse = new LoginPreferences_Utility(AddFundActivity.this).getLoggedinUser();
        String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
        String formNo = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);

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

            /*Promocode Request Model*/
            DebitAmountRequest request = new DebitAmountRequest();

            request.setAmount(amount);
            request.setFormNo(formNo);
            request.setOrderNo(orderId);
            request.setRRNNo("");
            request.setStatus(status);
            request.setTID(tid);
            request.setGatewayResponse(response);
            request.setTransactionDate(transDate);

            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(request);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<BaseResponse> fetchDebitAmountCall =
                NetworkClient_Utility_1.getInstance(this).create(FundApi.class).fetchCreditAmount(apiRequest, strToken);

        fetchDebitAmountCall.enqueue(new Callback<BaseResponse>() {
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
                                Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            } else if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                Bundle bundle = new Bundle();

                                if (serviceType.contentEquals("Bus")) {
                                    Intent intent = new Intent(AddFundActivity.this, OtpAndBusBookPaymentActivity.class);
                                    bundle.putString("Msg", "OK");
                                    intent.putExtras(bundle);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else if (serviceType.contentEquals("Flight")) {
                                    Intent intent = new Intent(AddFundActivity.this, FlightBookOtp_PaymentActivity.class);
                                    bundle.putString("Msg", "OK");
                                    intent.putExtras(bundle);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else if (serviceType.contentEquals("Hotel")) {
                                    Intent intent = new Intent(AddFundActivity.this, OtpAndHotelPaymentActivity.class);
                                    bundle.putString("Msg", "OK");
                                    intent.putExtras(bundle);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else if (serviceType.contentEquals("M") || serviceType.contentEquals("T")
                                        || serviceType.contentEquals("D")) {
                                    Intent intent1 = new Intent(AddFundActivity.this, CheckFinalDetailRechargeActivity.class);
                                    bundle.putString("Msg", "OK");
                                    intent1.putExtras(bundle);
                                    setResult(RESULT_OK, intent1);
                                    finish();
                                } else if (serviceType.contentEquals("I") || serviceType.contentEquals("G")
                                        || serviceType.contentEquals("C") || serviceType.contentEquals("E")) {
                                    Intent intent1 = new Intent(AddFundActivity.this, BillPayFinalDetailActivity.class);
                                    bundle.putString("Msg", "OK");
                                    intent1.putExtras(bundle);
                                    setResult(RESULT_OK, intent1);
                                    finish();
                                } else {
                                    String toast = Response.getRESPONSE() + " : " + Response.getRESP_MSG();
                                    AlertDialogUtils.showDialogWithOneButton(AddFundActivity.this, "Alert Message !", toast, new AlertDialogButtonListener() {
                                                @Override
                                                public void onButtontapped(String btnText) {
                                                    if (btnText.contains("Ok")) {
                                                        finish();
                                                    }
                                                }
                                            }
                                    );
                                }

                            } else {
                                //Toast.makeText(AddFundActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                String toast = Response.getRESPONSE() + " Something went wrong";
                                AlertDialogUtils.showDialogWithOneButton(AddFundActivity.this, "Alert Message !", toast, new AlertDialogButtonListener() {
                                            @Override
                                            public void onButtontapped(String btnText) {
                                                if (btnText.contains("Ok")) {
                                                    finish();
                                                }
                                            }
                                        }
                                );

                            }

                        }
                        else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {

                            String toast = Response.getRESPONSE() + " : " + Response.getRESP_MSG();
                            Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            String msg= "Transaction Cancel";

                            AlertDialogUtils.showDialogWithOneButton(AddFundActivity.this, "Alert Message !", toast, new AlertDialogButtonListener() {
                                        @Override
                                        public void onButtontapped(String btnText) {
                                            if (btnText.contains("Ok")) {
                                                finish();
                                            }
                                        }
                                    }
                            );

                        }
                        /*else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                            String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                            Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddFundActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } */
                        else {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            Toast.makeText(AddFundActivity.this, toast, Toast.LENGTH_SHORT).show();
                            // showSnackbar(toast);

                        }
                    } else {
                        String toast = "Something wrong";
                        //Toast.makeText(PromocodeActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddFundActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    /*Show Mobile Recharge dialog*/
    void showDialog() {
        try {
           /* StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("Amount of :- " + strAmount);
            sb.append("\n");
            sb.append("Mobile no:- " + strAccountNo);
            sb.append("\n");
            sb.append("Operator of:- " + strOpratorName);*/
            String alert1 = "Amount of : " + edtxtFundReq.getText().toString();
            String messageText = "Do you want to continue to add fund request." +
                    "\n\n" + alert1;
            //"\n"+alert2+
            // "\n"+alert3;

            AlertDialogUtils.showAlertdialogContext(AddFundActivity.this, new AlertDialogButtonListener() {
                @Override
                public void onButtontapped(String btnText) {
                    if (btnText.equalsIgnoreCase("YES")) {


                        /*Call recharge api
                         * with otp service*/

                        addFundSentOtp();
                       /* if(otpService){
                            addFundSentOtp();
                        }
                        else {
                           *//* Recharge without
                         * service*//*
                            getMainCompBalance();
                        }*/

                        /*Recharge without
                         * service*/
                        // getMainCompBalance();


                    }
                }
            }, "Add Fund", messageText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Show dialog for Continue add fund request
    * from wallet*/
    void showDialogContinueAddFund_FromWallet() {
        try {
           /* StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("Amount of :- " + strAmount);
            sb.append("\n");
            sb.append("Mobile no:- " + strAccountNo);
            sb.append("\n");
            sb.append("Operator of:- " + strOpratorName);*/
            String alert1 = "Amount of : " + edtxtFundReq.getText().toString();
            String messageText = "Do you want to continue to add fund request." +
                    "\n\n" + alert1;
            //"\n"+alert2+
            // "\n"+alert3;

            AlertDialogUtils.showAlertdialogContext(AddFundActivity.this, new AlertDialogButtonListener() {
                @Override
                public void onButtontapped(String btnText) {
                    if (btnText.equalsIgnoreCase("YES")) {


                        /*Call recharge api
                         * with otp service*/

                       getMainCompBalance();
                       /* if(otpService){
                            addFundSentOtp();
                        }
                        else {
                           *//* Recharge without
                         * service*//*
                            getMainCompBalance();
                        }*/

                        /*Recharge without
                         * service*/
                        // getMainCompBalance();


                    }
                }
            }, "Add Fund", messageText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Show dialog for Continue add fund request
     * from wallet*/
    void showDialogContinueAddFund_FromGateway() {
        try {
           /* StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("Amount of :- " + strAmount);
            sb.append("\n");
            sb.append("Mobile no:- " + strAccountNo);
            sb.append("\n");
            sb.append("Operator of:- " + strOpratorName);*/
            String alert1 = "Amount of : " + edtxtReqGatwayAmount.getText().toString();
            String messageText = "Do you want to continue to add fund request." +
                    "\n\n" + alert1;
            //"\n"+alert2+
            // "\n"+alert3;

            AlertDialogUtils.showAlertdialogContext(AddFundActivity.this, new AlertDialogButtonListener() {
                @Override
                public void onButtontapped(String btnText) {
                    if (btnText.equalsIgnoreCase("YES")) {


                        /*Call recharge api
                         * with otp service*/

                        getMainCompBalance();
                       /* if(otpService){
                            addFundSentOtp();
                        }
                        else {
                           *//* Recharge without
                         * service*//*
                            getMainCompBalance();
                        }*/

                        /*Recharge without
                         * service*/
                        // getMainCompBalance();


                    }
                }
            }, "Add Fund", messageText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*show message dialog*/
    void showMsgDialog1() {
        try {
            AlertDialogUtils.showDialog(AddFundActivity.this,
                    "Alert Dialog", "mobile number not proper, so you can't process for add fund request ");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            if(progressDialog.isShowing())
                progressDialog.dismiss();

            onBackPressed();
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

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        // check if the request code is same as what is passed here it is 1
        System.out.println("RESULTCODE--->" + resultCode);
        System.out.println("REQUESTCODE--->" + reqCode);
        System.out.println("RESULT_OK--->" + RESULT_OK);

        /*For E -utility_wallet*/
        if (reqCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    String number = bundle.getString("number");
                    String msg = bundle.getString("Msg");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            assert msg != null;
                            if (msg.contentEquals("OK")) {
                                //Call fund deduct api
                                if(!ConnectivityUtils.isNetworkEnabled(AddFundActivity.this)){
                                    Toast.makeText(AddFundActivity.this,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    getMainCompBalance();
                                }

                            }
                        }
                    }, 500);

                }

            } else {


            }
        }
        /*For Payment gateway result */
        else if (reqCode == ActivityRequestCode ) {
            if(progressDialog.isShowing())
                progressDialog.dismiss();
           if(data != null){
               try {
                   String response=data.getStringExtra("response");
                  // Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage") + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
                   System.out.println("TransResponse: " + response);
                   JSONObject jsonObject = new JSONObject(response );
                   String status=jsonObject.getString("STATUS");
                   String transID=jsonObject.getString("TXNID");
                   String transDate=jsonObject.getString("TXNDATE");
                   if(status.equals("TXN_SUCCESS")){
                       String strStatus="SUCCESS";
                       if(!ConnectivityUtils.isNetworkEnabled(AddFundActivity.this)){
                           Toast.makeText(AddFundActivity.this,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                       }
                       else {
                           CreditAmountApi(reqAmount,orderId,transID,strStatus,orderDate,response);
                       }

                   }
                   else {
                       String strStatus="CANCEL";
                       if(!ConnectivityUtils.isNetworkEnabled(AddFundActivity.this)){
                           Toast.makeText(AddFundActivity.this,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                       }
                       else {
                           CreditAmountApi(reqAmount,orderId,transID,strStatus,orderDate,response);
                       }
                   }
               }
               catch (Exception e){
                   e.printStackTrace();
               }



           }
           else {
               String strAlertMsg="Something went wrong.";
               AlertDialogUtils.showDialogWithOneButton(AddFundActivity.this, "Alert Message !", strAlertMsg, new AlertDialogButtonListener() {
                           @Override
                           public void onButtontapped(String btnText) {
                               if (btnText.contains("Ok")) {
                                   finish();
                               }
                           }
                       }
               );
           }


        }


    }

    /*Method for encode base64 to string*/
    /*public String encodeBase64(String encode) {
        String decode = null;

        try {


            decode = Base64.encode(encode.getBytes());
        } catch (Exception e) {
            System.out.println("Unable to decode : " + e);
        }
        return decode;
    }*/

    private String getDate(){
        DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
        String date=dfDate.format(Calendar.getInstance().getTime());
        DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");
        String time = dfTime.format(Calendar.getInstance().getTime());
        return date + " " + time;
    }

    /*For payment Gateway*/
   /* private void initPayment(String amount,String transId){
        String strMerchantID="Mt012";
        String strLoginID="110862";
        String strPass="a5cf5c9e";
        String strCustAcNo="00000034503902289";
        String strAmount=amount+".00";
       // mlogin-110862
        //pwd-a5cf5c9e
        //productid-UTILITY
        //clientcode-Mt012
        //customer acno-00000034503902289

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        //String datetime = dateformat.format(c.getTime());
         orderDate = getDate();
        System.out.println(orderDate);


        String uniqueID = UUID.randomUUID().toString();
        Intent newPayIntent=new Intent(this, PayActivity.class);
        newPayIntent.putExtra("merchantId", strMerchantID);
//txnscamt Fixed. Must be 0
        newPayIntent.putExtra("txnscamt", "0");
        newPayIntent.putExtra("loginid",strLoginID );
        newPayIntent.putExtra("password", strPass);
        newPayIntent.putExtra("prodid", "UTILITY");
//txncurr Fixed. Must be �INR�
        newPayIntent.putExtra("txncurr", "INR");
        newPayIntent.putExtra("clientcode",encodeBase64 ("Mt012"));
        newPayIntent.putExtra("custacc",strCustAcNo );
        newPayIntent.putExtra("channelid", "INT");
//amt  Should be 2 decimal number i.e 1.00

        newPayIntent.putExtra("amt",strAmount );
        newPayIntent.putExtra("txnid", transId);

//Date Should be in same format
        //newPayIntent.putExtra("date", "01/10/2019 18:31:00");
        newPayIntent.putExtra("date", orderDate);
        newPayIntent.putExtra("signature_request","6c5670550f86f48960" );
       // newPayIntent.putExtra("signature_request","KEY123657234" );
        newPayIntent.putExtra("signature_response","5b83ae4ce33667b39e" );
        newPayIntent.putExtra("discriminator","All");
        newPayIntent.putExtra("isLive",true);
//Optinal Parameters
//Only for Name
        newPayIntent.putExtra("customerName", name);
//Only for Email ID
        newPayIntent.putExtra("customerEmailID", emailID);
//Only for Mobile Number
        newPayIntent.putExtra("customerMobileNo",mobileNo );
//Only for Address
        newPayIntent.putExtra("billingAddress", address);
// Can pass any data
        newPayIntent.putExtra("optionalUdf9", "");
// Pass data in XML format, only for Multi product
        newPayIntent.putExtra("mprod", "");

        Log.e("GatewayRequest:-  " , intentToString(newPayIntent));

        startActivityForResult(newPayIntent,10);

    }*/

    public static String intentToString(Intent intent) {
        if (intent == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder()
                .append(intent.getAction())
                .append(" data: ")
                .append(intent.getDataString())
                .append(" extras: ")
                ;
               for (String key : intent.getExtras().keySet())
            stringBuilder.append(key).append("=").append(intent.getExtras().get(key)).append(" ");

        return stringBuilder.toString();

    }


}
