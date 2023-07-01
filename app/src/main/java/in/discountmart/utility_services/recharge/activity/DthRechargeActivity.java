package in.discountmart.utility_services.recharge.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.call_api.MainServices;
import in.discountmart.utility_services.listener.AlertDialogButtonListener;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.request_model.MainBalanceRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.model.response_model.MainBalanceResponse;
import in.discountmart.utility_services.recharge.call_recharge_api.RechargeApi;
import in.discountmart.utility_services.recharge.recharge_model.recharge_request_model.RechargeRequest;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.GetServiceProviderResponse;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.RechargeResponse;
import in.discountmart.utility_services.recharge.recharge_shared_preferance.RechargeSharedPreferance;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.utilities.AlertDialogUtils;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DthRechargeActivity extends AppCompatActivity {

    public static TextView txtService;
    public TextView txtWalletBal;
    EditText edTxtCardNo;
    EditText edTxtAmount;
    Button btnRecharge;
    LinearLayout layoutOperator;
    TextInputLayout txtInputCardNo;

    View view;

     String strService="";
     String strType="";
     String strCardNo="";
     String strAmount="";
    String home="";
     ArrayList<GetServiceProviderResponse> serviceProviderArrayList;

     ProgressDialog progressDialog;
     float mainBal=0;

    float totalPay=0;
    String strServiceType="";
    String strServiceTypeId="";
    String strAccountNo="";

    String strOpratorName="";
    String strOpratorCode="";
    String strIsbbps="";
    String strAction="";
    String strIpcode="";
    String strReferenceId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_dth_recharge);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            view=findViewById(android.R.id.content);

            txtService=(TextView)findViewById(R.id.dth_recharge_act_txt_oprator);
            txtWalletBal=(TextView)findViewById(R.id.dth_recharge_act_txt_balance);
            edTxtCardNo=(EditText)findViewById(R.id.dth_recharge_act_edtxt_card_num);
            edTxtAmount=(EditText)findViewById(R.id.dth_recharge_act_edtxt_amount);
            btnRecharge=(Button)findViewById(R.id.dth_recharge_act_btn_recharge);
            layoutOperator=(LinearLayout)findViewById(R.id.dth_recharge_act_layout_oprator);
            txtInputCardNo=(TextInputLayout) findViewById(R.id.dth_recharge_act_txtinput_card_num);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("DTH Recharge");
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

            /*Get Value form shared prefrence*/
            strType= RechargeSharedPreferance.getServiceType(this);
            strService=RechargeSharedPreferance.getDthServiceProvider(this);
            strServiceType=RechargeSharedPreferance.getServiceType(this);
            strServiceTypeId=RechargeSharedPreferance.getServiceTypeId(this);

            /*Choose operator on click get list service provider*/
            layoutOperator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(DthRechargeActivity.this, ServiceProviderActivity.class);
                    intent.putExtra("ServiceType",strServiceType);
                    intent.putExtra("ServiceTypeId",strServiceTypeId);
                    intent.putExtra("Home","false");
                    startActivityForResult(intent, 2);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });

            /*Call utility_main utility_wallet api*/
            if(!ConnectivityUtils.isNetworkEnabled(this)){
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
                getMainBalance();
            }

            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                serviceProviderArrayList=new ArrayList<GetServiceProviderResponse>();
                serviceProviderArrayList= (ArrayList<GetServiceProviderResponse>) bundle.getSerializable("ServiceDTHProviderList");

            }

            if(serviceProviderArrayList.size() > 0){
                for (int i=0;i < serviceProviderArrayList.size();i++){
                    txtService.setText(serviceProviderArrayList.get(i).getCompanyName());
                    strIpcode=serviceProviderArrayList.get(i).getIPcode();
                    strOpratorCode=serviceProviderArrayList.get(i).getCode();
                    strOpratorName=serviceProviderArrayList.get(i).getCompanyName();
                    strIsbbps=serviceProviderArrayList.get(i).getIsBBPS();

                }
            }
            if(!strService.equals("")){
                if(strService.contains("VIDEOCON D2h")){

                    //edTxtCardNo.setHint(getResources().getString(R.string.str_d2h_id_enter_regis_mob));
                    txtInputCardNo.setHint(getResources().getString(R.string.str_d2h_id_enter_regis_mob));
                }
                else if(strService.contains("Airtel")){
                    //edTxtCardNo.setHint(getResources().getString(R.string.str_airtel_customor_id));
                    txtInputCardNo.setHint(getResources().getString(R.string.str_airtel_customor_id));
                }

                else if(strService.contains("Reliance")){
                   // edTxtCardNo.setHint(getResources().getString(R.string.str_sun_or_relince_smart_card_no));
                    txtInputCardNo.setHint(getResources().getString(R.string.str_sun_or_relince_smart_card_no));
                }
                else if(strService.contains("DISH")){
                    //edTxtCardNo.setHint(getResources().getString(R.string.str_dish_regis_mob_card_no));
                    txtInputCardNo.setHint(getResources().getString(R.string.str_dish_regis_mob_card_no));
                }
                else if(strService.contains("SUN")){
                    //edTxtCardNo.setHint(getResources().getString(R.string.str_sun_or_relince_smart_card_no));
                    txtInputCardNo.setHint(getResources().getString(R.string.str_sun_or_relince_smart_card_no));
                }
                /*else if(strService.equals("Tata Sky Topup")){
                    edTxtCardNo.setHint(getResources().getString(R.string.str_tata_regis_mob_subscrib_no));
                }*/
                else if(strService.contains("TATA")){
                    edTxtCardNo.setHint(getResources().getString(R.string.str_tata_regis_mob_subscrib_no));
                }
            }

            /*Button Recharge on click*/
            btnRecharge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        if(txtService.getText().toString().equals("")){
                            Toast.makeText(DthRechargeActivity.this,"Please Enter Service Provider",Toast.LENGTH_SHORT).show();
                        }
                        else if(edTxtCardNo.getText().toString().equals("")){
                            edTxtCardNo.setError("Enter Card / register mobile number");
                            edTxtCardNo.requestFocus();
                        }

                        else if(edTxtAmount.getText().toString().equals("")){
                            edTxtAmount.setError("Enter Amount of Recharge");
                            edTxtAmount.requestFocus();
                        }
                        else {

                            strCardNo=edTxtCardNo.getText().toString();
                            strAccountNo=edTxtCardNo.getText().toString();
                            strAmount=edTxtAmount.getText().toString();

                            totalPay= Float.parseFloat(edTxtAmount.getText().toString());
                            float totCheckBal= mainBal;
                            //if(totCheckBal >= totalPay){

                                if(!ConnectivityUtils.isNetworkEnabled(DthRechargeActivity.this)){
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

                                    if(!strIsbbps.equals("")){
                                        if(strType.equals("D") && strIsbbps.equals("false")){

                                            /*Call api*/
                                            //getRecharge();
                                            //showRechargeDialog();
                                            //Call Request method and send request data next check final detail recharge activity
                                            RechargeRequest();
                                        }


                                    }

                                }
                            //}
                           /* else {
                                Snackbar.make(view, "Wallet amount not sufficient", Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(R.color.app_orange_light ))
                                        .show();
                            }*/

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
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
                                        new LoginPreferences_Utility(DthRechargeActivity.this).getLoggedinUser().setBalance(balanceResponse.getBalance());
                                        mainBal= Float.parseFloat(balanceResponse.getBalance());
                                        txtWalletBal.setText(getResources().getString(R.string.rs_symbol)+ " "+String.valueOf(mainBal));
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
                                Toast.makeText(DthRechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(DthRechargeActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(DthRechargeActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                RechargeRequest request=new RechargeRequest();
                request.setAccountNo(strAccountNo);
                request.setAction("DTH");
                request.setReferenceId(strReferenceId);

                /*if(strType.equals("M")){
                    request.setAction("PREPAID");
                    request.setReferenceId(strReferenceId);
                }

                else if(strType.equals("T")){
                    request.setAction("POSTPAID");
                    request.setReferenceId(strReferenceId);
                }*/


                request.setAmount(strAmount);
                request.setFormno(formno);
                request.setIpCode(strIpcode);
                request.setIsBBPS(strIsbbps);
                request.setOperatorName(strOpratorName);
                request.setOpratorCode(strOpratorCode);
                request.setServiceType(strType);

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

                                            Intent intent=new Intent(DthRechargeActivity.this,RechargeSucccessMsgActivity.class);
                                            Bundle bundle=new Bundle();
                                            bundle.putString("Mobile",rechargeResponse.getAccountNo());
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
                            Snackbar.make(view, "Somthing Error from server / other ", Snackbar.LENGTH_LONG)
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

    void showRechargeDialog(){
        try{
           /* StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("Amount of :- " + strAmount);
            sb.append("\n");
            sb.append("Mobile no:- " + strAccountNo);
            sb.append("\n");
            sb.append("Operator of:- " + strOpratorName);*/
            String alert1 = "Amount of : " + strAmount;
            String alert2 = "Mobile no: " + strAccountNo;
            String alert3 = "Operator of: " + strOpratorName;

            String messageText = "Do you want to continue to recharge."+
                    "\n\n"+alert1+
                    "\n"+alert2+
                    "\n"+alert3;

            AlertDialogUtils.showAlertdialogContext(DthRechargeActivity.this, new AlertDialogButtonListener() {
                @Override
                public void onButtontapped(String btnText) {
                    if(btnText.equalsIgnoreCase("YES")){

                        /*Call recharge api*/
                        //getRecharge();
                    }
                }
            },"Recharge",messageText);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*SEnd Request in next Activity*/
    public void RechargeRequest(){
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
            request.setAccountNo(strAccountNo);
            request.setAction("DTH");
            request.setReferenceId(strReferenceId);

                /*if(strType.equals("M")){
                    request.setAction("PREPAID");
                    request.setReferenceId(strReferenceId);
                }

                else if(strType.equals("T")){
                    request.setAction("POSTPAID");
                    request.setReferenceId(strReferenceId);
                }*/


            request.setAmount(strAmount);
            request.setFormno(formno);
            request.setIpCode(strIpcode);
            request.setIsBBPS(strIsbbps);
            request.setOperatorName(strOpratorName);
            request.setOpratorCode(strOpratorCode);
            request.setServiceType(strType);



        Intent intent=new Intent(DthRechargeActivity.this, CheckFinalDetailRechargeActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("RechargeRequest",request);

        bundle.putString("ServiceType",strType);
        bundle.putString("Operator",strOpratorName);
        bundle.putString("Amount",strAmount);
        bundle.putString("MobileNo",strAccountNo);
        bundle.putString("ServiceId",strServiceType);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        try {
            int compressionRatio=4;

            if(requestCode==2){
                if(resultCode == Activity.RESULT_OK){
                    Bundle bundle1=data.getExtras();
                    strType= RechargeSharedPreferance.getServiceType(this);
                    strService=RechargeSharedPreferance.getDthServiceProvider(this);
                    strServiceType=RechargeSharedPreferance.getServiceType(this);
                    strServiceTypeId=RechargeSharedPreferance.getServiceTypeId(this);
                    if(!strService.equals("")){
                        if(strService.contains("VIDEOCON D2h")){

                           // edTxtCardNo.setHint(getResources().getString(R.string.str_d2h_id_enter_regis_mob));
                            txtInputCardNo.setHint(getResources().getString(R.string.str_d2h_id_enter_regis_mob));
                            //edTxtAmount.setText("");
                            edTxtCardNo.setText("");
                        }
                        else if(strService.contains("Airtel")){
                            //edTxtCardNo.setHint(getResources().getString(R.string.str_airtel_customor_id));
                            txtInputCardNo.setHint(getResources().getString(R.string.str_airtel_customor_id));
                            //edTxtAmount.setText("");
                            edTxtCardNo.setText("");
                        }

                        else if(strService.contains("Reliance")){
                            //edTxtCardNo.setHint(getResources().getString(R.string.str_sun_or_relince_smart_card_no));
                            txtInputCardNo.setHint(getResources().getString(R.string.str_sun_or_relince_smart_card_no));
                           // edTxtAmount.setText("");
                            edTxtCardNo.setText("");
                        }
                        else if(strService.contains("DISH")){
                            //edTxtCardNo.setHint(getResources().getString(R.string.str_dish_regis_mob_card_no));
                            txtInputCardNo.setHint(getResources().getString(R.string.str_dish_regis_mob_card_no));
                            //edTxtAmount.setText("");
                            edTxtCardNo.setText("");
                        }
                        else if(strService.contains("SUN")){
                           // edTxtCardNo.setHint(getResources().getString(R.string.str_sun_or_relince_smart_card_no));
                            txtInputCardNo.setHint(getResources().getString(R.string.str_sun_or_relince_smart_card_no));
                            //edTxtAmount.setText("");
                            edTxtCardNo.setText("");
                        }
                /*else if(strService.equals("Tata Sky Topup")){
                    edTxtCardNo.setHint(getResources().getString(R.string.str_tata_regis_mob_subscrib_no));
                }*/
                        else if(strService.contains("TATA")){
                            //edTxtCardNo.setHint(getResources().getString(R.string.str_tata_regis_mob_subscrib_no));
                            txtInputCardNo.setHint(getResources().getString(R.string.str_tata_regis_mob_subscrib_no));
                            //edTxtAmount.setText("");
                            edTxtCardNo.setText("");
                        }
                    }
                    if(bundle1 != null){
                        serviceProviderArrayList=new ArrayList<GetServiceProviderResponse>();
                        serviceProviderArrayList= (ArrayList<GetServiceProviderResponse>) bundle1.getSerializable("ServiceDTHProviderList");
                        strService=RechargeSharedPreferance.getDthServiceProvider(this);
                        if(serviceProviderArrayList.size() > 0){
                            for (int i=0;i < serviceProviderArrayList.size();i++){
                                txtService.setText(serviceProviderArrayList.get(i).getCompanyName());
                                strIpcode=serviceProviderArrayList.get(i).getIPcode();
                                strOpratorCode=serviceProviderArrayList.get(i).getCode();
                                strOpratorName=serviceProviderArrayList.get(i).getCompanyName();
                                strIsbbps=serviceProviderArrayList.get(i).getIsBBPS();

                            }
                        }
                    }


                }
            }


        }catch (Exception e) {
            Toast.makeText(DthRechargeActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}
