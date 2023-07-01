package in.discountmart.business.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;

import in.base.network.NetworkClient;
import in.discountmart.R;
import in.discountmart.activity.LoginActivity;
import in.discountmart.business.adapter.WalletListAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.ProfileServices;
import in.discountmart.business.call_api.WalletServices;
import in.discountmart.business.listener.AlertDialogButtonListener;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.GetWalletBalanceRequest;
import in.discountmart.business.model_business.requestmodel.WalletTransferRequest;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.model_business.responsemodel.GetMemberNameResponse;
import in.discountmart.business.model_business.responsemodel.GetWalleBalanceResponse;
import in.discountmart.business.model_business.responsemodel.WalletListResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.AlertDialogUtils;
import in.discountmart.business.utility.ConnectivityUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletTransferActivity extends AppCompatActivity {

    Context context;
    View view;
    Spinner spnrFromWallet;
    Spinner spnrToWallet;
    TextView txtAvailBal;
    TextView txtSponserName;
    TextView txtMsg;
    TextView txtAvailWalletBal;
    EditText edTxtAmount;
    EditText edTxtMemId;
    EditText edTxtMemName;
    EditText edTxtTransPass;
    EditText edTxtRemark;
    Button btnSubmit;
    ProgressDialog pDialog;

    double availWalletBal=0;
    String strWalletName="";
    String strWalletType="";
    String strFromWalletType="";
    String strFromWalletName="";
    String strToWalletType="";
    String strToWalletName="";
    String strMemId="";
    String strMemName="";
    String strAmount="";
    String strRemark="";
    String strTPass="";
    String strApiKey="";
    boolean sponsorId;

    /* Model class Object ArrayList*/
    ArrayList<WalletListResponse.WalletList> fromWalletList;
    ArrayList<WalletListResponse.WalletList> toWalletList;

    /* Adapter */
    WalletListAdapter walletAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_transfer);
        try {

            view=findViewById(android.R.id.content);
            txtAvailBal=(TextView)findViewById(R.id.wallet_transfer_act_txt_avail_bal);
            txtMsg=(TextView)findViewById(R.id.wallet_transfer_act_txt_msg);
            //txtAvailWalletBal=(TextView)findViewById(R.id.wallet_transfer_frag_txt_avail_wallet_amount);
            edTxtAmount=(EditText)findViewById(R.id.wallet_transfer_act_edtxt_amount);
            edTxtMemId=(EditText)findViewById(R.id.wallet_transfer_act_edtxt_id);
            txtSponserName=(TextView)findViewById(R.id.wallet_transfer_act_edtxt_name);
            edTxtRemark=(EditText)findViewById(R.id.wallet_transfer_act_edtxt_remark);
            edTxtTransPass=(EditText)findViewById(R.id.wallet_transfer_act_edtxt_tpass);
            //spnrFromWallet=(Spinner)findViewById(R.id.wallet_transfer_frag_spnr_from_wallet);
            //spnrToWallet=(Spinner)findViewById(R.id.wallet_transfer_frag_spnr_to_wallet);
            btnSubmit=(Button) findViewById(R.id.wallet_transfer_act_btn_submit);
            //edTxtAmount.addTextChangedListener(new MyTextWatcher(edTxtAmount));


            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(this);


            // Enabling Up / Back navigation
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Wallet Transfer");

            /*Call From Wallet type*/
            if(!ConnectivityUtils.isNetworkEnabled(this)){
                Toast.makeText(WalletTransferActivity.this,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
            }
            else {
                // getWalletList("From",strWalletType);
                getWalletBalance();
            }
            /*Spinner From Wallet*/
           /* spnrFromWallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    WalletListResponse.WalletList walletList= (WalletListResponse.WalletList)adapterView.getItemAtPosition(i);
                    strFromWalletType=walletList.getActype();
                    strFromWalletName=walletList.getActype();
                    //Call To Wallet Api

                    getWalletList("To",strFromWalletType);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });*/

            /*Spinner From Wallet*/
           /* spnrToWallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    WalletListResponse.WalletList walletList= (WalletListResponse.WalletList)adapterView.getItemAtPosition(i);
                    strToWalletType=walletList.getActype();
                    strToWalletName=walletList.getWalletname();
                    //Call To Wallet Api

                    getWalletBalance(strFromWalletType);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });*/

            /*Text sponsor name on click
             * check first epin is used or unused
             * than check sponsor id is correct or not */
            txtSponserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(edTxtMemId.getText().toString().equals("")){
                        Toast.makeText(WalletTransferActivity.this,"Plz Member Id", Toast.LENGTH_SHORT).show();
                        edTxtMemId.requestFocus();
                    }
                    else {

                        strMemId=edTxtMemId.getText().toString().toUpperCase();
                        String idno=SharedPrefrence_Business.getUserID(WalletTransferActivity.this).toUpperCase();
                        if(strMemId.equals(idno))
                        {
                            String msg="Sorry !...Same Id no. to not wallet transfer";
                            sponsorId=false;
                            AlertDialogUtils.showDialogWithOneButton(WalletTransferActivity.this, new AlertDialogButtonListener() {
                                @Override
                                public void onButtontapped(String btnText) {
                                    if (btnText.equals("OK")) {

                                    }
                                }
                            },"Alert Dialog", msg,"OK");
                        }
                        else {
                            //Call Epin ckeck api
                            if(!ConnectivityUtils.isNetworkEnabled(WalletTransferActivity.this)){
                                Toast.makeText(WalletTransferActivity.this,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {

                                getMemberName();
                            }
                        }

                    }
                }
            });

            /*Button Submit on click */
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   /* if(strFromWalletType.equals("")){
                        Toast.makeText(context,"Please select from wallet",Toast.LENGTH_SHORT).show();
                    }
                    else if(strToWalletType.equals("")){
                        Toast.makeText(context,"Please select to wallet",Toast.LENGTH_SHORT).show();
                    }*/
                    if(edTxtMemId.getText().toString().equals("")){
                        edTxtMemId.setError("Please enter Member Id");
                        edTxtMemId.requestFocus();
                    }
                    else if(txtSponserName.getText().toString().equals("Click here to get name")){
                        //edTextSponsorIdNo.setError("Enter Sponsor Id.No.");
                        //edTextSponsorIdNo.requestFocus();
                        Toast.makeText(WalletTransferActivity.this,"click on click here text",Toast.LENGTH_SHORT).show();
                    }
                    else if(edTxtAmount.getText().toString().equals("")){
                        edTxtAmount.setError("Please enter amount");
                        edTxtAmount.requestFocus();
                    }
                    else if(edTxtRemark.getText().toString().equals("")){
                        edTxtRemark.setError("Please write remark against transfer");
                        edTxtRemark.requestFocus();
                    }
                    else if(edTxtTransPass.getText().toString().equals("")){
                        edTxtTransPass.setError("Enter transaction password");
                        edTxtTransPass.requestFocus();
                    }
                    else {
                        strMemId=edTxtMemId.getText().toString();
                        strAmount=edTxtAmount.getText().toString();
                        strRemark=edTxtRemark.getText().toString();
                        strTPass=edTxtTransPass.getText().toString();

                        double amount=Double.parseDouble(strAmount);

                        if(amount <= 0){
                            txtMsg.setText("Enter correct amount");
                        }
                        else if(amount > availWalletBal){
                            txtMsg.setText("Enter amount should not greater than from wallet amount");
                        }

                        else {
                            if (sponsorId) {
                                // call wallet transfer api
                                if (!ConnectivityUtils.isNetworkEnabled(WalletTransferActivity.this)) {
                                    Toast.makeText(WalletTransferActivity.this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                } else {
                                    getWalletTransfer();
                                }
                            } else {
                                String msg = "Please correct Id no.";
                                Toast.makeText(WalletTransferActivity.this, msg, Toast.LENGTH_SHORT).show();

                            }
                        }
                    }




                        //  }


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


    /*Wallet Balance Api Request and response*/
    private void getWalletBalance(){

        pDialog=new ProgressDialog(WalletTransferActivity.this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        GetWalletBalanceRequest baseRequest=new GetWalletBalanceRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_WALLET_BAL);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(WalletTransferActivity.this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(WalletTransferActivity.this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(WalletTransferActivity.this));
        baseRequest.setActype("M");
        String request= new Gson().toJson(baseRequest);
        Log.e("Request",request);

        Call<GetWalleBalanceResponse> walleBalanceResponseCall=
                NetworkClient.getInstance(this).create(WalletServices.class).fetchWalletBalance(baseRequest,strApiKey);

        walleBalanceResponseCall.enqueue(new Callback<GetWalleBalanceResponse>() {
            @Override
            public void onResponse(Call<GetWalleBalanceResponse> call, Response<GetWalleBalanceResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    GetWalleBalanceResponse Response =new GetWalleBalanceResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {

                        txtAvailBal.setText(Response.getBalance());
                        //txtAvailWalletBal.setText(Response.getBalance());
                        availWalletBal= Double.parseDouble(Response.getBalance());

                        if(availWalletBal <= 0){
                            btnSubmit.setVisibility(View.GONE);

                            txtMsg.setText("Insufficient Selected Wallet Balance So You Can't Request, Plz Select Another");
                        }
                        /*else if(availWalletBal < 249){
                            btnSubmit.setVisibility(View.GONE);
                            txtMsg.setText("Minimum 249/- Required to Selected Wallet, Plz Select Another");
                        }*/
                        else {
                            btnSubmit.setVisibility(View.VISIBLE);
                            //edTxtAmount.setText("249");
                            //edTxtAmount.setEnabled(false);
                            txtMsg.setText("");
                        }
                    }
                    else if(Response.getResponse().contains("FAILED") && Response.getMsg().contains("Invalid Login")){
                        String toast= "Invalid login detail. Please login again";
                        Toast.makeText(WalletTransferActivity.this,toast,Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(WalletTransferActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else if(Response.getResponse().contains("FAILED")) {
                        Toast.makeText(WalletTransferActivity.this, Response.getResponse(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetWalleBalanceResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(WalletTransferActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Wallet Transfer Api Request and response*/
    private void getWalletTransfer(){

        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        WalletTransferRequest baseRequest=new WalletTransferRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_WALLET_TRANSFER_NEW);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        baseRequest.setToidno(strMemId);
        baseRequest.setReqamount(strAmount);
        baseRequest.setRemarks(strRemark);
        baseRequest.setTranspassword(strTPass);
        String request= new Gson().toJson(baseRequest);
        Log.e("Request",request);

        Call<BaseResponse> walleBalanceResponseCall=
                NetworkClient.getInstance(this).create(WalletServices.class).fetchWalletTransferNew(baseRequest,strApiKey);

        walleBalanceResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    BaseResponse Response =new BaseResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {

                            String msg=strAmount +"/- " + Response.getMsg() ;

                            AlertDialogUtils.showDialogWithOneButton(WalletTransferActivity.this, new AlertDialogButtonListener() {
                                @Override
                                public void onButtontapped(String btnText) {
                                    if (btnText.equals("OK")) {
                                        Intent intent=new Intent(WalletTransferActivity.this, BusinessDashboardActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            },"Alert Dialog", msg,"OK");
                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login")){
                            new BusinessDashboardActivity().blankValueFromSharedPreference(WalletTransferActivity.this);
                            //finish();
                        }
                        else if (Response.getResponse().equals("FAILED")){
                            Toast.makeText(WalletTransferActivity.this, Response.getResponse(), Toast.LENGTH_SHORT).show();

                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(WalletTransferActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Get Member Name Api request ane Response*/
    private void getMemberName(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_GET_MEMBER_NAME);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        baseRequest.setMemberid(edTxtMemId.getText().toString());

        Call<GetMemberNameResponse> memberNameResponseCall=
                NetworkClient.getInstance(this).create(ProfileServices.class).fetchMemberName(baseRequest,strApiKey);

        memberNameResponseCall.enqueue(new Callback<GetMemberNameResponse>() {
            @Override
            public void onResponse(Call<GetMemberNameResponse> call, Response<GetMemberNameResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    GetMemberNameResponse Response =new GetMemberNameResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {
                        txtSponserName.setText(Response.getMemname());
                        sponsorId=true;

                    }
                    else if(Response.getResponse().contains("FAILED") && Response.getMsg().contains("Invalid Login")){
                        new BusinessDashboardActivity().blankValueFromSharedPreference(WalletTransferActivity.this);
                    }
                    else {
                        txtSponserName.setText(Response.getMsg());
                        sponsorId=false;
                        String toast= Response.getResponse()+ ":" + Response.getMsg();
                        Toast.makeText(WalletTransferActivity.this, toast, Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetMemberNameResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(WalletTransferActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }
}