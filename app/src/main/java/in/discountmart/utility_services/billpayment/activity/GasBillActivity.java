package in.discountmart.utility_services.billpayment.activity;

import static in.discountmart.utility_services.utilities.Utilities.isValidEmail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.activity.PromocodeActivity;
import in.discountmart.utility_services.billpayment.bill_pay_call_api.BillPayApi;
import in.discountmart.utility_services.billpayment.billpay_model.billpay_request_model.BillPayRequest;
import in.discountmart.utility_services.billpayment.billpay_model.billpay_request_model.GetParameterRequest;
import in.discountmart.utility_services.billpayment.billpay_model.billpay_response_model.BillPayResponse;
import in.discountmart.utility_services.billpayment.billpay_model.billpay_response_model.BillPayServiceProviderListResonse;
import in.discountmart.utility_services.billpayment.billpay_model.billpay_response_model.GetParameterResponse;
import in.discountmart.utility_services.billpayment.billpay_shared_preferance.BillPaySharedPreferance;
import in.discountmart.utility_services.call_api.MainServices;
import in.discountmart.utility_services.listener.AlertDialogButtonListener;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.request_model.MainBalanceRequest;
import in.discountmart.utility_services.model.request_model.SendOtpRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.model.response_model.MainBalanceResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.travel.flight.call_flight_api.FlightApi;
import in.discountmart.utility_services.travel.flight.flight_sharedpreference.FlightSharedValues;
import in.discountmart.utility_services.travel.travel_sharedpreferance.TravelSharedPreferance;
import in.discountmart.utility_services.utilities.AlertDialogUtils;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GasBillActivity extends AppCompatActivity {
    EditText edtxtReferenceNo;
    EditText edtxtBillAmount;
    EditText edtxtCustomerName;
    EditText edtxtMobile;
    EditText edtxtEmailId;
    EditText edtxtCardNo1;
    EditText edtxtCardNo2;
    EditText edtxtCardNo3;
    EditText edtxtCardNo4;
    EditText edtxtConfCardNo1;
    EditText edtxtConfCardNo2;
    EditText edtxtConfCardNo3;
    EditText edtxtConfCardNo4;
    EditText edtxtPromocode;
    EditText edtxtDiscount;
    EditText edtxtGroupNo;
    TextView txtPromocode;
    TextView txtWalletAmount;
    TextView txtHavePromocode;
    TextView txtPromoDiscount;
    TextView txtPromoUsed;
    public static TextView txtServiceGas;
    TextView btnCalPromodis;
    Button btnBillPay;
    RadioGroup rdgCardType;
    RadioButton rdbVisa;
    RadioButton rdbMaster;
    RadioButton rdbAS;
    RadioButton rdbCash;
    Toolbar toolbar;
    LinearLayout layoutOperator;
    LinearLayout layoutCardNo;
    LinearLayout layoutConfCardNo;
    LinearLayout layoutCardType;
    LinearLayout layoutPromo;
    LinearLayout layoutDiscount;
    LinearLayout layoutRef;
    LinearLayout layoutGroupNo;
    LinearLayout layoutEmail;
    LinearLayout layoutMobile;
    LinearLayout layoutCustName;
    LinearLayout layoutBillamount;
    LinearLayout layoutPromocode;
    LinearLayout layoutRemovePromo;

    CollapsingToolbarLayout collapsingToolbarLayout;
    View view;

    String serviceType="";
    String serviceTypeId="";
    String strBillAmount="";
    String strRefNo="";
    String strGroupNo="";
    String strBillerName="";
    String strRegMobile="";
    String strPanNo="";
    String strEmailId="";
    String strCardType="";
    String strCardNo1="";
    String strCardNo2="";
    String strCardNo3="";
    String strCardNo4="";
    String strConfCardNo1="";
    String strConfCardNo2="";
    String strConfCardNo3="";
    String strConfCardNo4="";
    String strCardNum="";
    String strConfCardNum="";
    String strDob="";
    String strPromocode="";
    String strPromoAmount="";
    String strPromoStatus="";
    String strDiscount="";
    String strFileImg="";
    public static String serviceID="";
    String home="";
    boolean promoApply;



    String walletAmount="";
    float mainBal=0;
    float totalPay=0;
    public static String serviceId="";
    String otpId="";
    String service="";
    ProgressDialog progressDialog;
    /*Array List of Object*/
    ArrayList<BillPayServiceProviderListResonse> serviceProviderArrayList;
    ArrayList<GetParameterResponse> parameterResponseArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_gas_bill);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view=findViewById(android.R.id.content);
        try {
            toolbar=(Toolbar)findViewById(R.id.gas_billpay_act_toolbar);
            //collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.gas_billpay_act_collasp_layout);

           setSupportActionBar(toolbar);

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           //getSupportActionBar().setTitle("Gas Bill Pay");


            //collapsingToolbarLayout.setTitle("Gas Bill Pay");

            edtxtReferenceNo=(EditText)findViewById(R.id.gas_billpay_act_edtxt_ref_no);
            edtxtBillAmount=(EditText)findViewById(R.id.gas_billpay_act_edtxt_bill_amnt);
            edtxtCustomerName=(EditText)findViewById(R.id.gas_billpay_act_edtxt_cust_name);
            edtxtGroupNo=(EditText)findViewById(R.id.gas_billpay_act_edtxt_gasno);
            edtxtMobile=(EditText)findViewById(R.id.gas_billpay_act_edtxt_mobileno);
            edtxtEmailId=(EditText)findViewById(R.id.gas_billpay_act_edtxt_email);
            edtxtCardNo1=(EditText)findViewById(R.id.gas_billpay_act_edtxt_cardno1);
            edtxtCardNo2=(EditText)findViewById(R.id.gas_billpay_act_edtxt_cardno2);
            edtxtCardNo3=(EditText)findViewById(R.id.gas_billpay_act_edtxt_cardno3);
            edtxtCardNo4=(EditText)findViewById(R.id.gas_billpay_act_edtxt_cardno4);
            edtxtConfCardNo1=(EditText)findViewById(R.id.gas_billpay_act_edtxt_conf_cardno1);
            edtxtConfCardNo2=(EditText)findViewById(R.id.gas_billpay_act_edtxt_conf_cardno2);
            edtxtConfCardNo3=(EditText)findViewById(R.id.gas_billpay_act_edtxt_conf_cardno3);
            edtxtConfCardNo4=(EditText)findViewById(R.id.gas_billpay_act_edtxt_conf_cardno4);
            edtxtPromocode=(EditText)findViewById(R.id.gas_billpay_act_edtxt_promocode);
            edtxtDiscount=(EditText)findViewById(R.id.gas_billpay_act_edtxt_promo_dis);
            txtServiceGas=(TextView)findViewById(R.id.gas_billpay_act_txt_oprator);
            txtWalletAmount=(TextView)findViewById(R.id.gas_billpay_act_txt_walletamount);
            layoutOperator=(LinearLayout)findViewById(R.id.gas_billpay_act_layout_oprator);
            layoutCardNo=(LinearLayout)findViewById(R.id.gas_billpay_act_layout_card_no);
            layoutConfCardNo=(LinearLayout)findViewById(R.id.gas_billpay_act_layout_confcardno);
            layoutPromo=(LinearLayout)findViewById(R.id.gas_billpay_act_layout_promocode);
            layoutDiscount=(LinearLayout)findViewById(R.id.gas_billpay_act_layout_discount);
            layoutRef=(LinearLayout)findViewById(R.id.gas_billpay_act_layout_ref);
            layoutBillamount=(LinearLayout)findViewById(R.id.gas_billpay_act_layout_billamount);
            layoutCustName=(LinearLayout)findViewById(R.id.gas_billpay_act_layout_billername);
            layoutMobile=(LinearLayout)findViewById(R.id.gas_billpay_act_layout_mobile);
            layoutEmail=(LinearLayout)findViewById(R.id.gas_billpay_act_layout_email);
            layoutGroupNo=(LinearLayout)findViewById(R.id.gas_billpay_act_layout_groupno);
            layoutRemovePromo=(LinearLayout)findViewById(R.id.gas_billpay_act_layout_remove_promo);

            btnBillPay=(Button)findViewById(R.id.gas_billpay_act_btn_billpay);
            btnCalPromodis=(TextView)findViewById(R.id.gas_billpay_act_txt_cal_dis);
            txtHavePromocode=(TextView)findViewById(R.id.gas_billpay_act_txt_getpromo);
            //txtPromoDiscount=(TextView)findViewById(R.id.gas_billpay_act_txt_cal_dis);
            txtPromoUsed=(TextView)findViewById(R.id.gas_billpay_act_txt_used_promo);

            serviceType= BillPaySharedPreferance.getServiceType(this);
            serviceTypeId= BillPaySharedPreferance.getServiceTypeID(this);

            InputFilter filter = new InputFilter() {
                public CharSequence filter(CharSequence source, int start, int end,
                                           Spanned dest, int dstart, int dend) {
                    for (int i = start; i < end; i++) {
                        if (!Character.isLetterOrDigit(source.charAt(i))) {
                            return "";
                        }
                    }
                    return null;
                }
            };

            edtxtReferenceNo.setFilters(new InputFilter[]{filter});
            edtxtGroupNo.setFilters(new InputFilter[]{filter});
            edtxtCustomerName.addTextChangedListener(new MyTextWatcher(edtxtCustomerName));
            edtxtEmailId.addTextChangedListener(new MyTextWatcher(edtxtEmailId));

            /*Value get form Bill Service list activity When we click from home dashboard */
            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                serviceId=bundle.getString("ServiceId");
                home=bundle.getString("Home");
                serviceProviderArrayList=new ArrayList<BillPayServiceProviderListResonse>();
                serviceProviderArrayList= (ArrayList<BillPayServiceProviderListResonse>) bundle.getSerializable("ServiceProviderList");

            }


            if(serviceProviderArrayList.size() > 0){
                for (int i=0;i < serviceProviderArrayList.size();i++){
                    txtServiceGas.setText(serviceProviderArrayList.get(i).getService());
                    service=serviceProviderArrayList.get(i).getService();

                }
            }

            /*Show Promo or not*/
            LoginResponse loginPreferences=new LoginResponse();
            loginPreferences = new LoginPreferences_Utility(this).getLoggedinUser();
            if(loginPreferences != null){
                if(loginPreferences.getMemMode().equals("D")){
                    layoutPromo.setVisibility(View.VISIBLE);
                    layoutDiscount.setVisibility(View.VISIBLE);

                }
                else {
                    layoutPromo.setVisibility(View.GONE);
                    layoutDiscount.setVisibility(View.GONE);
                }
            }

            if(home.equals("true")){

                /*Call Api Get Parameter*/
                if(!ConnectivityUtils.isNetworkEnabled(GasBillActivity.this)){
                    Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }

                else {
                    getParameter();
                }
            }

            /*text choose operator o click get service list*/
            layoutOperator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(GasBillActivity.this,BillPayServiceListActivity.class);
                    intent.putExtra("ServiceType",serviceType);
                    intent.putExtra("ServiceTypeId",serviceTypeId);
                    intent.putExtra("Home","false");
                    startActivityForResult(intent, 102);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });

            txtServiceGas.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(!s.toString().equals(getResources().getString(R.string.str_choose_oprator))){
                        /*Call Api Get Parameter*/
                        if(!ConnectivityUtils.isNetworkEnabled(GasBillActivity.this)){
                            Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        }

                        else {
                            getParameter();
                        }
                    }


                }
            });

            /*Button Get Promo code on have promo button click*/
            txtHavePromocode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String strCredit="G";
                    Intent intent1 = new Intent(GasBillActivity.this, PromocodeActivity.class);
                    intent1.putExtra("ServiceType",strCredit);
                    intent1.putExtra("ServiceId","19");

                    startActivityForResult(intent1,19);
                    // startActivity(intent1);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });

            //update
            /*Image close on click
             * remove get promo code*/
            layoutRemovePromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edtxtPromocode.setText("");
                    strPromoAmount="0";
                    strPromocode="";
                    strPromoStatus="";
                    edtxtBillAmount.setEnabled(true);

                    layoutRemovePromo.setVisibility(View.GONE);
                    //edtxtPromocode.setVisibility(View.GONE);
                    edtxtDiscount.setText("");
                    strDiscount="0";
                    strFileImg="";
                    promoApply=false;


                }
            });

            //update
            /*Calculate promo discount on calculate discount btn on click */
            btnCalPromodis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int bilamount=0;
                    int promodis=0;
                    if(edtxtBillAmount.getText().toString().equals("")){
                        Toast.makeText(GasBillActivity.this,"Please enter bill amount",
                                Toast.LENGTH_SHORT).show();

                    }
                    else {
                        if(edtxtPromocode.getText().toString().equals("")){
                            Toast.makeText(GasBillActivity.this,"Please enter promocode first",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {

                            strPromocode=edtxtPromocode.getText().toString();
                            if(strPromoStatus.equals("Used")){
                                edtxtDiscount.setText("0");
                            }
                            else {

                                //update
                                strBillAmount=edtxtBillAmount.getText().toString();

                                /*For check if */
                                if(isInteger(edtxtBillAmount.getText().toString())){

                                    int billAmount= Integer.parseInt(edtxtBillAmount.getText().toString());
                                    strBillAmount=String.valueOf(billAmount);
                                }
                                else if(isDouble(edtxtBillAmount.getText().toString())){

                                    int promo;
                                    double billAmount= Double.parseDouble(edtxtBillAmount.getText().toString());

                                    promo= (int) Math.round(billAmount);

                                    strBillAmount=String.valueOf(promo);
                                }

                                promodis=Integer.parseInt(strPromoAmount);
                                bilamount=Integer.parseInt(strBillAmount);

                                if(promodis > bilamount){

                                    edtxtDiscount.setText(String.valueOf(bilamount));
                                }
                                else if(promodis < bilamount){
                                    edtxtDiscount.setText(String.valueOf(promodis));
                                }
                                else if(promodis == bilamount){
                                    edtxtDiscount.setText(String.valueOf(bilamount));
                                }
                            }
                        }

                    }
                }
            });

            /*Button Bill on click listener*/
            btnBillPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    strRefNo = edtxtReferenceNo.getText().toString();
                    strBillAmount = edtxtBillAmount.getText().toString();
                    strBillerName = edtxtCustomerName.getText().toString();
                    strRegMobile = edtxtMobile.getText().toString();
                    strEmailId = edtxtEmailId.getText().toString();

                    strPromocode = edtxtPromocode.getText().toString();
                    strDiscount = edtxtDiscount.getText().toString();

                    if (strRefNo.equals("") && layoutRef.getVisibility() == View.VISIBLE) {
                           /* edtxtReferenceNo.setError("Please enter reference number");
                            edtxtReferenceNo.requestFocus();*/
                        Toast.makeText(GasBillActivity.this,
                                "Please Enter Reference Number", Toast.LENGTH_SHORT).show();

                    } else if (strBillAmount.equals("") && layoutBillamount.getVisibility() == View.VISIBLE) {

                        edtxtBillAmount.setError("Please enter bill amount");
                        edtxtBillAmount.requestFocus();
                    } else if (strBillerName.equals("") && layoutCustName.getVisibility() == View.VISIBLE) {
                        edtxtCustomerName.setError("Please customer name");
                        edtxtCustomerName.requestFocus();
                    } else if (strRegMobile.equals("") && layoutMobile.getVisibility() == View.VISIBLE) {
                        edtxtMobile.setError("Please enter regiser mobile no.");
                        edtxtMobile.requestFocus();
                    } else if (strRegMobile.length() > 0 && strRegMobile.length() < 10
                            && layoutMobile.getVisibility() == View.VISIBLE) {
                        edtxtMobile.setError("enter complete mobile no.");
                        edtxtMobile.requestFocus();
                    }
                    else if (strEmailId.equals("") && layoutEmail.getVisibility() == View.VISIBLE) {

                        edtxtEmailId.setError("Please enter email id.");
                        edtxtEmailId.requestFocus();
                    }
                    else  if(!isValidEmail(strEmailId) && strEmailId.length() > 0
                            && layoutEmail.getVisibility() == View.VISIBLE){
                        edtxtEmailId.setError("enter valid email id.");
                        edtxtEmailId.requestFocus();
                    }

                    /*else if (strPromocode.equals("") && layoutPromo.getVisibility() == View.VISIBLE) {
                     *//*edtxtPromocode.setError("Enter promocode.");
                            edtxtPromocode.requestFocus();*//*
                        Toast.makeText(GasBillActivity.this,
                                "Please Enter promocode", Toast.LENGTH_SHORT).show();

                    } else if (strDiscount.equals("") && layoutDiscount.getVisibility() == View.VISIBLE) {
                            *//*edtxtDiscount.setError("Click on calculate discount");
                            edtxtDiscount.requestFocus();*//*
                        Toast.makeText(GasBillActivity.this,
                                "Click on calculate discount", Toast.LENGTH_SHORT).show();

                    }*/ else {

                        totalPay= Float.parseFloat(edtxtBillAmount.getText().toString());
                        float totCheckBal= mainBal;
                      //  if(totCheckBal >= totalPay){
                            /*Call api save bill pay api*/
                            if (!ConnectivityUtils.isNetworkEnabled(GasBillActivity.this)) {
                                Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();
                            } else {

                                /*update*/
                                if(strDiscount.equals(""))
                                    strDiscount="0";
                                /*Call api bill pay send otp */

                                //show confirmation dialog
                                // showBillPayDialog();

                                /*sending data at BillPay final detail show activity*/
                                LoginResponse loginResponse = new LoginResponse();
                                loginResponse = new LoginPreferences_Utility(GasBillActivity.this).getLoggedinUser();
                                String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
                                String formno = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);

                                String dob = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                                BillPayRequest providerRequest = new BillPayRequest();
                                providerRequest.setServiceId(serviceId);
                                providerRequest.setFormNo(formno);
                                providerRequest.setAmount(strBillAmount);
                                providerRequest.setReferanceNumber(strRefNo);
                                providerRequest.setBillerName(strBillerName);
                                providerRequest.setRegMobileNo(strRegMobile);
                                providerRequest.setPanNo(strPanNo);
                                providerRequest.setEmailId(strEmailId);
                                providerRequest.setCardType(strCardType);
                                providerRequest.setServiceTypeId(serviceTypeId);
                                providerRequest.setLocationCode("");
                                providerRequest.setMeterNumber("");
                                providerRequest.setBillUnit("");
                                providerRequest.setProcessCycle("");
                                providerRequest.setCycleNumber("");
                                providerRequest.setMeterReadingDate("");
                                providerRequest.setGroupNo("");
                                providerRequest.setDistrict("0");
                                providerRequest.setConsumerType("");
                                providerRequest.setTypeOfPayment("");
                                providerRequest.setERO("");
                                providerRequest.setServicetype("");
                                providerRequest.setDiviSionName("");
                                providerRequest.setDob(dob);
                                providerRequest.setBAnk("");
                                providerRequest.setRRNo("");
                                providerRequest.setMonthBill("");
                                providerRequest.setCreditCard("");
                                providerRequest.setSector("");
                                providerRequest.setBlock("");
                                providerRequest.setFlatNo("");
                                providerRequest.setSubCode("");
                                providerRequest.setSubdivisioncode("");
                                if(strPromoStatus.equals("Used")){
                                    providerRequest.setPromoCode("");
                                }
                                else if(strPromoStatus.equals("Unused")){
                                    providerRequest.setPromoCode(strPromocode);
                                }
                                else {
                                    providerRequest.setPromoCode("");
                                }

                                providerRequest.setDiscount(strDiscount);
                                providerRequest.setFileImg(strFileImg);
                                FlightSharedValues.getInstance().flightBookOtpId=otpId;

                                Intent intent=new Intent(GasBillActivity.this, BillPayFinalDetailActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("BillPayRequest",providerRequest);
                                bundle.putString("OTPId",otpId);
                                bundle.putString("ServiceName",service);
                                bundle.putString("BillAmount",strBillAmount);
                                bundle.putString("Reference",strRefNo);
                                bundle.putString("PromoAmount",strDiscount);//update
                                bundle.putString("PromoCode",strPromocode);//update
                                bundle.putString("Service","G");
                                bundle.putString("ServiceType","Gas Bill Pay");
                                bundle.putString("ServiceId",serviceId);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                //finish();
                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                            }
                       /* }
                        else {
                            AlertDialogUtils_shop.showDialog(GasBillActivity.this,
                                    "Alert Dialog","Wallet amount not sufficient for bill pay");
                        }*/


                    }

                }

            });



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public class MyTextWatcher implements TextWatcher {
        private EditText editText;

        public MyTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text = editText.getText().toString();
            if (text.startsWith(" ")) {
                editText.setText(text.trim());

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

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

        if(reqCode==102){
            if(resultCode == Activity.RESULT_OK){
                Bundle bundle=data.getExtras();
                serviceProviderArrayList=new ArrayList<BillPayServiceProviderListResonse>();
                serviceProviderArrayList= (ArrayList<BillPayServiceProviderListResonse>) bundle.getSerializable("ServiceProviderList");
                serviceId=bundle.getString("ServiceId");
                if(serviceProviderArrayList.size() > 0){
                    for (int i=0;i < serviceProviderArrayList.size();i++){
                        txtServiceGas.setText(serviceProviderArrayList.get(i).getService());
                        //serviceId=serviceProviderArrayList.get(i).getServiceId();

                    }
                }

            }
        }

        // update
        else if(reqCode == 19){
            if(resultCode == Activity.RESULT_OK){

                strPromocode=data.getStringExtra("PromoCode");
                strPromocode=data.getStringExtra("PromoCode");
                strPromoStatus=data.getStringExtra("Status");



                if(isInteger(data.getStringExtra("PromoAmount"))){

                    int promoAmount= Integer.parseInt(data.getStringExtra("PromoAmount"));
                    strPromoAmount=String.valueOf(promoAmount);
                }
                else if(isDouble(data.getStringExtra("PromoAmount"))){

                    int promo;
                    double promoAmount= Double.parseDouble(data.getStringExtra("PromoAmount"));
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        promo= Math.toIntExact(Math.round(promoAmount));
                    }
                    else {
                        promo= (int) Math.round(promoAmount);
                    }
                    strPromoAmount=String.valueOf(promo);
                }

                /*if(strPromoStatus.equals("Unused")){

                    edtxtPromocode.setText(strPromocode);
                    edtxtPromocode.setEnabled(false);
                    edtxtPromocode.setFocusable(false);
                    txtHavePromocode.setVisibility(View.VISIBLE);
                    txtPromoUsed.setVisibility(View.GONE);
                    //txtPromoDiscount.setClickable(true);
                    //txtPromoDiscount.setEnabled(true);
                }
                else {
                    edtxtPromocode.setText(strPromocode);
                    edtxtPromocode.setEnabled(false);
                    edtxtPromocode.setFocusable(false);
                    txtHavePromocode.setVisibility(View.GONE);
                    txtPromoUsed.setVisibility(View.VISIBLE);
                    txtPromoUsed.setText("You have been already used this promo");
                    edtxtDiscount.setText("0");
                    //txtPromoDiscount.setClickable(false);
                    //txtPromoDiscount.setEnabled(false);
                }*/


                /* Update promo amount calculation*/
                if(strPromoStatus.equals("Unused")){
                    int bilamount=0;
                    int promodis=0;
                    layoutRemovePromo.setVisibility(View.VISIBLE);
                    promoApply=true;
                    edtxtPromocode.setText(strPromocode);
                    edtxtPromocode.setEnabled(false);
                    edtxtPromocode.setFocusable(false);
                    edtxtBillAmount.setEnabled(false);
                    txtHavePromocode.setVisibility(View.VISIBLE);
                    txtPromoUsed.setVisibility(View.GONE);

                    strBillAmount=edtxtBillAmount.getText().toString();

                    /*For check if */
                    if(isInteger(edtxtBillAmount.getText().toString())){

                        int billAmount= Integer.parseInt(edtxtBillAmount.getText().toString());
                        strBillAmount=String.valueOf(billAmount);
                    }
                    else if(isDouble(edtxtBillAmount.getText().toString())){

                        int promo;
                        double billAmount= Double.parseDouble(edtxtBillAmount.getText().toString());

                        promo= (int) Math.round(billAmount);

                        strBillAmount=String.valueOf(promo);
                    }

                    promodis=Integer.parseInt(strPromoAmount);
                    bilamount=Integer.parseInt(strBillAmount);

                    if(promodis > bilamount){

                        edtxtDiscount.setText(String.valueOf(bilamount));
                    }
                    else if(promodis < bilamount){
                        edtxtDiscount.setText(String.valueOf(promodis));
                    }
                    else if(promodis == bilamount){
                        edtxtDiscount.setText(String.valueOf(bilamount));
                    }


                    //txtPromoDiscount.setClickable(true);
                    //txtPromoDiscount.setEnabled(true);
                }
                else {
                    //layoutRemovePromo.setVisibility(View.VISIBLE);
                    //edtxtPromocode.setText("");
                    edtxtPromocode.setEnabled(false);
                    edtxtPromocode.setFocusable(false);
                    edtxtBillAmount.setEnabled(true);

                    //txtHavePromocode.setVisibility(View.VISIBLE);
                    //txtPromoUsed.setVisibility(View.VISIBLE);
                    //txtPromoUsed.setText("You have been already used this promo");
                    edtxtDiscount.setText("0");
                    promoApply=false;
                    // layoutFileUpload.setVisibility(View.GONE);
                    //txtPromoDiscount.setClickable(false);
                    //txtPromoDiscount.setEnabled(false);
                }
            }
        }


    }

    //update
    /*Method for check is value is integer or not*/
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /*Method for check is value is double or not*/
    public static boolean isDouble(String str) {
        try {
            double v = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException nfe) {
        }
        return false;
    }

    /* Request and Response Get Parameeter Api*/
    public void getParameter() {


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String strApiRequest = "";
        JSONObject object = null;
        String strToken = TokenBase64.getToken();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse = new LoginPreferences_Utility(this).getLoggedinUser();
        String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);

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

            /*Getcity List Request Model*/
            GetParameterRequest providerRequest = new GetParameterRequest();
            providerRequest.setServiceId(serviceId);


            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(providerRequest);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<BaseResponse> fetchServiceProviderListCall =
                NetworkClient_Utility_1.getInstance(this).create(BillPayApi.class).fetchBillPayParameter(apiRequest, strToken);

        fetchServiceProviderListCall.enqueue(new Callback<BaseResponse>() {
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
                            }
                            else if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {

                                //cal get Wallet Bal api
                                 getMainBalance();

                                String[] serviceListResponse = Response.getRESPONSE().split("");
                                if (serviceListResponse.length > 0) {
                                    GetParameterResponse[] parameterList = new Gson().fromJson(Response.getRESP_VALUE(), GetParameterResponse[].class);
                                    List<GetParameterResponse> serviceArrayList = new ArrayList<GetParameterResponse>(Arrays.asList(parameterList));

                                    // adding contacts to contacts list
                                    parameterResponseArrayList=new ArrayList<GetParameterResponse>();
                                    parameterResponseArrayList.clear();
                                    parameterResponseArrayList.addAll(serviceArrayList);

                                   if(parameterResponseArrayList.size() > 0 ){
                                       for (int i=0; i < parameterResponseArrayList.size(); i++){
                                           if(parameterResponseArrayList.get(i).getReferanceNumber().equals("Y")){
                                               layoutRef.setVisibility(View.VISIBLE);
                                               strRefNo="";
                                           }
                                           else {
                                               layoutRef.setVisibility(View.GONE);
                                               strRefNo="gone";
                                           }
                                           if(parameterResponseArrayList.get(i).getBillAmount().equals("Y")){
                                               layoutBillamount.setVisibility(View.VISIBLE);
                                               strBillAmount="";
                                           }
                                           else {
                                               layoutBillamount.setVisibility(View.GONE);
                                               strBillAmount="gone";
                                           }
                                           if(parameterResponseArrayList.get(i).getGroupNo().equals("Y")){
                                               layoutGroupNo.setVisibility(View.VISIBLE);
                                               strGroupNo="";
                                           }
                                           else {
                                               layoutGroupNo.setVisibility(View.GONE);
                                               strGroupNo="gone";
                                           }
                                            if(parameterResponseArrayList.get(i).getBillerName().equals("Y")){
                                               layoutCustName.setVisibility(View.VISIBLE);
                                               strBillerName="";
                                           }
                                           else {
                                                layoutCustName.setVisibility(View.GONE);
                                                strBillerName="gone";
                                            }
                                            if(parameterResponseArrayList.get(i).getRegMobileNo().equals("Y")){
                                               layoutMobile.setVisibility(View.VISIBLE);
                                               strRegMobile="";

                                           }
                                           else {
                                                layoutMobile.setVisibility(View.GONE);
                                                strRegMobile="gone";
                                            }
                                            if(parameterResponseArrayList.get(i).getEmailId().equals("Y")){
                                               layoutEmail.setVisibility(View.VISIBLE);
                                               strEmailId="";
                                           }
                                           else {
                                                layoutEmail.setVisibility(View.GONE);
                                                strEmailId="gone";
                                            }

                                            if(parameterResponseArrayList.get(i).getCardType().equals("Y")){
                                               layoutCardNo.setVisibility(View.VISIBLE);
                                               layoutConfCardNo.setVisibility(View.VISIBLE);
                                               strCardNum="";
                                               strConfCardNum="";
                                           }
                                           else {
                                                layoutCardNo.setVisibility(View.GONE);
                                                layoutConfCardNo.setVisibility(View.GONE);
                                                strCardNum="gone";
                                                strConfCardNum="gone";
                                            }
                                          /* else if(parameterResponseArrayList.get(i).getCreditCard().equals("Y")){
                                               edtxtEmailId.setVisibility(View.VISIBLE);
                                           }*/


                                           layoutPromo.setVisibility(View.VISIBLE);
                                           layoutDiscount.setVisibility(View.VISIBLE);
                                       }
                                   }

                                } else {
                                    String toast = " City List empty";
                                    Toast.makeText(GasBillActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    //showSnackbar(toast);
                                }
                            }
                        }
                        else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {

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
                            Toast.makeText(GasBillActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(GasBillActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(GasBillActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                                        new LoginPreferences_Utility(GasBillActivity.this).getLoggedinUser().setBalance(balanceResponse.getBalance());
                                        mainBal= Float.parseFloat(balanceResponse.getBalance());
                                        txtWalletAmount.setText(getResources().getString(R.string.rs_symbol)+ " "+String.valueOf(mainBal));
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
                                Toast.makeText(GasBillActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(GasBillActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

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

    /* Request and Response Get Bill Payament sApi*/
    public void getBillPaymentDetail() {


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
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
            providerRequest.setServiceId(serviceId);
            providerRequest.setFormNo(formno);
            providerRequest.setAmount(strBillAmount);
            providerRequest.setReferanceNumber(strRefNo);
            providerRequest.setBillerName(strBillerName);
            providerRequest.setRegMobileNo(strRegMobile);
            providerRequest.setPanNo(strPanNo);
            providerRequest.setEmailId(strEmailId);
            providerRequest.setCardType(strCardType);
            providerRequest.setServiceTypeId(serviceTypeId);
            providerRequest.setLocationCode("");
            providerRequest.setMeterNumber("");
            providerRequest.setBillUnit("");
            providerRequest.setProcessCycle("");
            providerRequest.setCycleNumber("");
            providerRequest.setMeterReadingDate("");
            providerRequest.setGroupNo("");
            providerRequest.setDistrict("");
            providerRequest.setConsumerType("");
            providerRequest.setTypeOfPayment("");
            providerRequest.setERO("");
            providerRequest.setServicetype("");
            providerRequest.setDiviSionName("");
            providerRequest.setDob(dob);
            providerRequest.setBAnk("");
            providerRequest.setRRNo("");
            providerRequest.setMonthBill("");
            providerRequest.setCreditCard("");
            providerRequest.setSector("");
            providerRequest.setBlock("");
            providerRequest.setFlatNo("");
            providerRequest.setSubCode("");
            providerRequest.setSubdivisioncode("");
            if(strPromoStatus.equals("Used")){
                providerRequest.setPromoCode("");
            }
            else if(strPromoStatus.equals("Unused")){
                providerRequest.setPromoCode(strPromocode);
            }
            else {
                providerRequest.setPromoCode("");
            }

            providerRequest.setDiscount(strDiscount);
            providerRequest.setFileImg(strFileImg);

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
                                String serviceListResponse = Response.getRESPONSE();
                                BillPayResponse billPayResponse=new BillPayResponse();
                                billPayResponse=new Gson().fromJson(Response.getRESP_VALUE(),BillPayResponse.class);
                                if (billPayResponse != null) {

                                    Intent intent=new Intent(GasBillActivity.this, BillPaymentSuccessMsgActivity.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putString("Msg",Response.getRESP_MSG());
                                    bundle.putString("ServiceType","Gas Bill Pay");
                                    bundle.putString("Amount",billPayResponse.getAmount());
                                    bundle.putString("TransId",billPayResponse.getId());
                                    if(serviceId.equals(billPayResponse.getServiceId()))
                                        bundle.putString("Service",txtServiceGas.getText().toString());
                                    else
                                        bundle.putString("Service",billPayResponse.getServiceId());

                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);


                                } else {
                                    String toast = " City List empty";
                                    Toast.makeText(GasBillActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    //showSnackbar(toast);
                                }
                            }
                        } else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("FAILED")) {

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
                    } else {
                        //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                        String toast = "something error may be server / other";
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

            /* Send Otp Request Model*/

            SendOtpRequest sendOtpRequest=new SendOtpRequest();
            sendOtpRequest.setEmailID(new LoginPreferences_Utility(this).getLoggedinUser().getEmailId());
            sendOtpRequest.setFormNo(formno);
            sendOtpRequest.setMobileNo(mobile);
            sendOtpRequest.setSponsorFormNo(companyId);
            sendOtpRequest.setServiceName("Gas");
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
                NetworkClient_Utility_1.getInstance(GasBillActivity.this).create(FlightApi.class).fetchFlightBookOtp(apiRequest,strToken);
        fetchFlightOtp.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
               if(progressDialog.isShowing())
                   progressDialog.dismiss();
                try {

                    BaseResponse Response =new BaseResponse();
                    Response=response.body();

                    if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                        if(!Response.getRESP_VALUE().equals("null") || ! Response.getRESP_VALUE().equals("")){

                            // edTxtOtp.setEnabled(true);
                            String strResponse=Response.getRESP_VALUE();
                            otpId=strResponse;
                            // SendOtpResponse otpResponse= new Gson().fromJson(strResponse,SendOtpResponse.class);
                            //otpId=otpResponse.getId();

                            /*BillPay Request Model*/

                            LoginResponse loginResponse = new LoginResponse();
                            loginResponse = new LoginPreferences_Utility(GasBillActivity.this).getLoggedinUser();
                            String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
                            String formno = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);

                            String dob = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                            BillPayRequest providerRequest = new BillPayRequest();
                            providerRequest.setServiceId(serviceId);
                            providerRequest.setFormNo(formno);
                            providerRequest.setAmount(strBillAmount);
                            providerRequest.setReferanceNumber(strRefNo);
                            providerRequest.setBillerName(strBillerName);
                            providerRequest.setRegMobileNo(strRegMobile);
                            providerRequest.setPanNo(strPanNo);
                            providerRequest.setEmailId(strEmailId);
                            providerRequest.setCardType(strCardType);
                            providerRequest.setServiceTypeId(serviceTypeId);
                            providerRequest.setLocationCode("");
                            providerRequest.setMeterNumber("");
                            providerRequest.setBillUnit("");
                            providerRequest.setProcessCycle("");
                            providerRequest.setCycleNumber("");
                            providerRequest.setMeterReadingDate("");
                            providerRequest.setGroupNo("");
                            providerRequest.setDistrict("");
                            providerRequest.setConsumerType("");
                            providerRequest.setTypeOfPayment("");
                            providerRequest.setERO("");
                            providerRequest.setServicetype("");
                            providerRequest.setDiviSionName("");
                            providerRequest.setDob(dob);
                            providerRequest.setBAnk("");
                            providerRequest.setRRNo("");
                            providerRequest.setMonthBill("");
                            providerRequest.setCreditCard("");
                            providerRequest.setSector("");
                            providerRequest.setBlock("");
                            providerRequest.setFlatNo("");
                            providerRequest.setSubCode("");
                            providerRequest.setSubdivisioncode("");
                            if(strPromoStatus.equals("Used")){
                                providerRequest.setPromoCode("");
                            }
                            else if(strPromoStatus.equals("Unused")){
                                providerRequest.setPromoCode(strPromocode);
                            }
                            else {
                                providerRequest.setPromoCode("");
                            }

                            providerRequest.setDiscount(strDiscount);
                            providerRequest.setFileImg(strFileImg);
                            FlightSharedValues.getInstance().flightBookOtpId=otpId;

                            Intent intent=new Intent(GasBillActivity.this, OtpAndVerify_BillPayActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("BillPayRequest",providerRequest);
                            bundle.putString("OTPId",otpId);
                            bundle.putString("ServiceName",service);
                            bundle.putString("ServiceType","Gas Bill Pay");
                            bundle.putString("ServiceId",serviceId);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            //finish();
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

    /*Dialog box show*/
    void showBillPayDialog(){
        try{
           /* StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("Amount of :- " + strAmount);
            sb.append("\n");
            sb.append("Mobile no:- " + strAccountNo);
            sb.append("\n");
            sb.append("Operator of:- " + strOpratorName);*/
            String alert1 = "Bill Amount of :   " + strBillAmount;
            String alert2 = "Service For :   " + "Gas Bill Pay";
            String alert3 = "Service Provider :   " + service;

            String messageText = "Do you want to continue to bill pay."+
                    "\n\n"+alert1+
                    "\n"+alert2+
                    "\n"+alert3;

            AlertDialogUtils.showAlertdialogContext(GasBillActivity.this, new AlertDialogButtonListener() {
                @Override
                public void onButtontapped(String btnText) {
                    if(btnText.equalsIgnoreCase("YES")){

                        /*Call bill pay send otp api*/
                       // billPaySendOtp();
                    }
                }
            },"Bill Payment",messageText);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
