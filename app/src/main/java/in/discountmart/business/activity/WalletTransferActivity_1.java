package in.discountmart.business.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.ArrayList;

import in.base.network.NetworkClient;
import in.base.network.NetworkClient1;
import in.discountmart.R;
import in.discountmart.business.adapter.WalletListAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.WalletServices;
import in.discountmart.business.listener.AlertDialogButtonListener;
import in.discountmart.business.model_business.requestmodel.GetWalletBalanceRequest;
import in.discountmart.business.model_business.requestmodel.MainToShopWalletTransferRequest;
import in.discountmart.business.model_business.requestmodel.WalletListRequest;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.model_business.responsemodel.GetWalleBalanceResponse;
import in.discountmart.business.model_business.responsemodel.WalletListResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.AlertDialogUtils;
import in.discountmart.business.utility.ConnectivityUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletTransferActivity_1 extends AppCompatActivity {

    Context context;
    View view;
    Spinner spnrFromWallet;
    Spinner spnrToWallet;
    TextView txtAvailBal;
    TextView txtMsg;
    TextView txtAvailWalletBal;
    EditText edTxtAmount;
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
    String strAmount="";
    String strRemark="";
    String strTPass="";
    String strApiKey="";

    /* Model class Object ArrayList*/
    ArrayList<WalletListResponse.WalletList> fromWalletList;
    ArrayList<WalletListResponse.WalletList> toWalletList;

    /* Adapter */
    WalletListAdapter walletAdapter;


    public WalletTransferActivity_1() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_fragment_wallet_transfer);
        try {
            context=WalletTransferActivity_1.this;
            view=WalletTransferActivity_1.this.findViewById(android.R.id.content);
            txtAvailBal=(TextView)findViewById(R.id.wallet_transfer_frag_txt_avail_bal);
            txtMsg=(TextView)findViewById(R.id.wallet_transfer_frag_txt_msg);
            txtAvailWalletBal=(TextView)findViewById(R.id.wallet_transfer_frag_txt_avail_wallet_amount);
            edTxtAmount=(EditText)findViewById(R.id.wallet_transfer_frag_edtxt_amount);
            edTxtRemark=(EditText)findViewById(R.id.wallet_transfer_frag_edtxt_remark);
            edTxtTransPass=(EditText)findViewById(R.id.wallet_transfer_frag_edtxt_tpass);
            spnrFromWallet=(Spinner)findViewById(R.id.wallet_transfer_frag_spnr_from_wallet);
            spnrToWallet=(Spinner)findViewById(R.id.wallet_transfer_frag_spnr_to_wallet);
            btnSubmit=(Button) findViewById(R.id.wallet_transfer_frag_btn_submit);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Wallet Tranfer");

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(this);


            /*Call From Wallet type*/
            if(!ConnectivityUtils.isNetworkEnabled(context)){
                Toast.makeText(context,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
            }
            else {
                getWalletList("From",strWalletType);
            }
            /*Spinner From Wallet*/
            spnrFromWallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            });

            /*Spinner From Wallet*/
            spnrToWallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            });

            /*Button Submit on click */
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(strFromWalletType.equals("")){
                        Toast.makeText(context,"Please select from wallet",Toast.LENGTH_SHORT).show();
                    }
                    else if(strToWalletType.equals("")){
                        Toast.makeText(context,"Please select to wallet",Toast.LENGTH_SHORT).show();
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
                        strAmount=edTxtAmount.getText().toString();
                        strRemark=edTxtRemark.getText().toString();
                        strTPass=edTxtTransPass.getText().toString();

                        double amount=Double.parseDouble(strAmount);

                        if(amount < 10 ){
                            txtMsg.setText("Invalid Request Amount!! Required minimum 10 /-");
                        }
                        else {

                            // call wallet transfer api
                            if(!ConnectivityUtils.isNetworkEnabled(context)){
                                Toast.makeText(context,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getWalletTransfer();
                            }
                        }

                    }
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Get Paymode List Api*/
    public void getWalletList(final String type, String walletType){

        pDialog=new ProgressDialog(context);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        WalletListRequest Request = new WalletListRequest();

        /*Pos Method*/
        String Get_Paramenter = "";
        try {

            /*Set value in Entity class*/
            if(type.equals("From"))
                Request.setReqtype(ApiConstants.REQUEST_FROM_WALLET);
            else
                Request.setReqtype(ApiConstants.REQUEST_TO_WALLET);
            Request.setPasswd(SharedPrefrence_Business.getPassword(context));
            Request.setUserid(SharedPrefrence_Business.getUserID(context));
            Request.setIslogin(SharedPrefrence_Business.getIsLogin(context));
            //Request.setActype(walletType);
            //Request.setWalletname(strWalletName);

            // Request.setCountrycode("1");

            //1. Convert object to JSON string
            Gson gson = new Gson();
            Get_Paramenter = gson.toJson(Request);
            Log.e("ReqWalletList:", Get_Paramenter);

        } catch (Exception e) {
        }

        Call<WalletListResponse> payModeListResponseCall=
                NetworkClient1.getInstance(context).create(WalletServices.class).fetchWalletList(Request,strApiKey);

        payModeListResponseCall.enqueue(new Callback<WalletListResponse>() {
            @Override
            public void onResponse(Call<WalletListResponse> call, Response<WalletListResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try{
                    WalletListResponse listResponse=response.body();
                    if(listResponse.getResponse().equals("OK")){

                        if(listResponse.getWallet() != null && listResponse.getWallet().size()> 0){

                            if(type.equals("From")){

                                fromWalletList=new ArrayList<WalletListResponse.WalletList>();
                                fromWalletList=listResponse.getWallet();
                                walletAdapter=new WalletListAdapter(context,fromWalletList);
                                spnrFromWallet.setAdapter(walletAdapter);
                            }
                            else{
                                toWalletList=new ArrayList<WalletListResponse.WalletList>();
                                toWalletList=listResponse.getWallet();
                                walletAdapter=new WalletListAdapter(context,toWalletList);
                                spnrToWallet.setAdapter(walletAdapter);
                            }



                        }
                        else {
                            String toast= listResponse.getResponse()+ "Record is Empty";
                            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<WalletListResponse> call, Throwable t) {

                try{
                    if(pDialog.isShowing())
                        pDialog.dismiss();
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


    }


    /*Wallet Balance Api Request and response*/
    private void getWalletBalance(String walletType){

        pDialog=new ProgressDialog(context);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        GetWalletBalanceRequest baseRequest=new GetWalletBalanceRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_WALLET_BAL);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        baseRequest.setActype(walletType);
        String request= new Gson().toJson(baseRequest);
        Log.e("Request",request);

        Call<GetWalleBalanceResponse> walleBalanceResponseCall=
                NetworkClient.getInstance(context).create(WalletServices.class).fetchWalletBalance(baseRequest,strApiKey);

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
                        txtAvailWalletBal.setText(Response.getBalance());
                        availWalletBal= Double.parseDouble(Response.getBalance());

                        if(availWalletBal <= 0){
                            btnSubmit.setVisibility(View.GONE);

                            txtMsg.setText("Insufficient Selected Wallet Balance So You Can't Request, Plz Select Another");
                        }
                        else if(availWalletBal < 10){
                            btnSubmit.setVisibility(View.GONE);
                            txtMsg.setText("Minimum 10/- Required in Selected Wallet, Plz Select Another");
                        }
                        else {
                            btnSubmit.setVisibility(View.VISIBLE);

                            txtMsg.setText("");
                        }
                    }
                    else {
                        Toast.makeText(context, Response.getResponse(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetWalleBalanceResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Wallet Transfer Api Request and response*/
    private void getWalletTransfer(){

        pDialog=new ProgressDialog(context);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        MainToShopWalletTransferRequest baseRequest=new MainToShopWalletTransferRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_WALLET_TRANSFER);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        baseRequest.setFromactype(strFromWalletType);
        baseRequest.setFromwallet(strFromWalletName);
        baseRequest.setTowallet(strToWalletName);
        baseRequest.setToactype(strToWalletType);
        baseRequest.setAmount(strAmount);
        baseRequest.setRemarks(strRemark);
        baseRequest.setTranspasswd(strTPass);

        Call<BaseResponse> walleBalanceResponseCall=
                NetworkClient.getInstance(context).create(WalletServices.class).fetchWalletTransfer(baseRequest,strApiKey);

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

                            AlertDialogUtils.showDialogWithOneButton(WalletTransferActivity_1.this, new AlertDialogButtonListener() {
                                @Override
                                public void onButtontapped(String btnText) {
                                    if (btnText.equals("OK")) {
                                        Intent intent=new Intent(WalletTransferActivity_1.this, BusinessDashboardActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            },"Alert Dialog", msg,"OK");
                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login")){
                            new BusinessDashboardActivity().blankValueFromSharedPreference(WalletTransferActivity_1.this);
                            //finish();
                        }
                        else {
                            Toast.makeText(WalletTransferActivity_1.this, Response.getResponse(), Toast.LENGTH_SHORT).show();

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
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
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

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK ))
        {
            if(progressDialog.isShowing())
                progressDialog.dismiss();

            if (fetchBusSearch != null || fetchBusSearch == null)
                fetchBusSearch.cancel();
            //onBackPressed();

        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        super.onBackPressed();
       /* if (fetchBusSearch != null){
            fetchBusSearch.cancel();
            finish();
        }*/
        finish();
        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }

}
