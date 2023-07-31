package in.discountmart.business.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import in.base.network.NetworkClient;
import in.base.network.NetworkClient1;
import in.discountmart.R;
import in.discountmart.activity.LoginActivity;
import in.discountmart.business.adapter.PackageListAdapter;
import in.discountmart.business.adapter.SpinnerSingleItemAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.MyTeamService;
import in.discountmart.business.call_api.ProfileServices;
import in.discountmart.business.call_api.WalletServices;
import in.discountmart.business.model_business.SpinnerSingleItemModel;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.IDActivationBalance;
import in.discountmart.business.model_business.requestmodel.IDActivationRequest;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.model_business.responsemodel.GetMemberNameResponse;
import in.discountmart.business.model_business.responsemodel.GetWalleBalanceResponse;
import in.discountmart.business.model_business.responsemodel.IDActivePackageList;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.listener.AlertDialogButtonListener;
import in.discountmart.utility.AlertDialogUtils;
import in.discountmart.utility.ConnectivityUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IDActivation_Old_Activity extends AppCompatActivity {
    Context context;
    View view;
    TextView txtAvailBal;
    TextView txtErrorMsg;
    EditText edtxtMemId;
    TextView txtMemName;
    EditText edtxtAmount;
    EditText edtxtTPass;
    EditText edtxtEP;
    Spinner spnrPckg;
    Spinner spnrPayType;
    Button btnSubmit;
    Button btnCheck;
    LinearLayout layoutContent;
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
    boolean sponsorId;
    boolean walletBal;


    String strEP="";
    String strPckgId="";
    String strPckgName="";
    String strPckgAmount="";
    double amount=0;
    double epAmount=0;
    double epValue=0;
    ProgressDialog pDialog;
    String strPkgId="";
    String strPkgName="";
    String strApiKey="";
    ArrayList<IDActivePackageList.IDPackageList> pckArrayList;
    ArrayList<SpinnerSingleItemModel> singleItemList;
    SpinnerSingleItemAdapter spinnerAdapter;
    PackageListAdapter pkgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_activation);
        try {

            view=findViewById(android.R.id.content);

            content(view);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void content(View mainView){
        txtAvailBal=(TextView)mainView.findViewById(R.id.id_activation_act_txt_bal);
        edtxtAmount=(EditText)mainView.findViewById(R.id.id_activation_act_edTxt_amount);
        edtxtMemId=(EditText)mainView.findViewById(R.id.id_activation_act_edTxt_memId);
        edtxtTPass=(EditText)mainView.findViewById(R.id.id_activation_act_edTxt_trans_pass);
        txtMemName=(TextView) mainView.findViewById(R.id.id_activation_act_txt_membername);
        txtErrorMsg=(TextView) mainView.findViewById(R.id.id_activation_act_txt_error_msg);
        spnrPckg=(Spinner)mainView.findViewById(R.id.id_activation_act_spnr_pck);
        spnrPayType=(Spinner)mainView.findViewById(R.id.id_activation_act_spnr_paytype);
        btnSubmit=(Button)mainView.findViewById(R.id.id_activation_act_btn_submitt);
        btnCheck=(Button)mainView.findViewById(R.id.id_activation_act_btn_check);
        layoutContent=(LinearLayout) mainView.findViewById(R.id.id_activation_act_layout_content);

        /* Get api key value from Shared Preference */
        strApiKey=SharedPrefrence_Business.getApiKey(this);

        // Enabling Up / Back navigation
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ID Activation");

        /*Call From Wallet type*/
        if(!ConnectivityUtils.isNetworkEnabled(this)){
            Toast.makeText(IDActivation_Old_Activity.this,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
        }
        else {
            // getWalletList("From",strWalletType);
            getWalletBalance();
        }
        /*Spinner Package on item selected listener*/
        spnrPckg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerSingleItemModel packageList1 = (SpinnerSingleItemModel) adapterView.getItemAtPosition(i);
                    /*if(packageList1.getPkgid().equals("")){
                        Toast.makeText(getActivity(),"Please Id not Available",Toast.LENGTH_SHORT).show();
                    }*/

                strPckgId=packageList1.getId();
                strPckgName=packageList1.getName();
                strPckgAmount=packageList1.getAmount();
                edtxtAmount.setText(strPckgAmount);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //initPaymentType();
        /*Spinner Package on item selected listener*/
        /*spnrPayType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerSingleItemModel packageList1 = (SpinnerSingleItemModel) adapterView.getItemAtPosition(i);
                    *//*if(packageList1.getPkgid().equals("")){
                        Toast.makeText(getActivity(),"Please Id not Available",Toast.LENGTH_SHORT).show();
                    }*//*

                //strPckgId=packageList1.getId();
                //strPckgName=packageList1.getName();
                //strPckgAmount=packageList1.getAmount();
                //edtxtAmount.setText(strPckgAmount);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        /*Text sponsor name on click
         * check first epin is used or unused
         * than check sponsor id is correct or not */
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtxtMemId.getText().toString().equals("")){
                    Toast.makeText(IDActivation_Old_Activity.this,"Plz Enter Member Id", Toast.LENGTH_SHORT).show();
                    edtxtMemId.requestFocus();
                }
                else {
                    View view2 = IDActivation_Old_Activity.this.getCurrentFocus();
                    if (view2 != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view2.getWindowToken(),
                                0);
                    }
                    strMemId=edtxtMemId.getText().toString().toUpperCase();
                    String idno= SharedPrefrence_Business.getUserID(IDActivation_Old_Activity.this).toUpperCase();


                        //Call Epin ckeck api
                        if(!ConnectivityUtils.isNetworkEnabled(IDActivation_Old_Activity.this)){
                            Toast.makeText(IDActivation_Old_Activity.this,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            checkIdForActivation();
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
                if(edtxtMemId.getText().toString().equals("")){
                    //edtxtMemId.setError("Please enter Member Id");
                    Toast.makeText(IDActivation_Old_Activity.this,"Please enter Member Id",Toast.LENGTH_SHORT).show();
                    edtxtMemId.requestFocus();
                }
                else if(!sponsorId){
                    //edTextSponsorIdNo.setError("Enter Sponsor Id.No.");
                    //edTextSponsorIdNo.requestFocus();
                    Toast.makeText(IDActivation_Old_Activity.this,"click on check button for ID active or not",Toast.LENGTH_SHORT).show();
                }
                else if(strPckgId.equals("0")){
                    Toast.makeText(IDActivation_Old_Activity.this,"Please select proper package",Toast.LENGTH_SHORT).show();
                }
                else if(edtxtAmount.getText().toString().equals("")){
                    edtxtAmount.setError("Please select kit");
                    edtxtAmount.requestFocus();
                }
               /* else if(edTxtRemar.getText().toString().equals("")){
                    edTxtRemark.setError("Please write remark against transfer");
                    edTxtRemark.requestFocus();
                }*/
                else if(edtxtTPass.getText().toString().equals("")){
                    edtxtTPass.setError("Enter transaction password");
                    edtxtTPass.requestFocus();
                }
                else {
                    strMemId=edtxtMemId.getText().toString();
                    strAmount=edtxtAmount.getText().toString();
                    //strRemark=edTxtRemark.getText().toString();
                    strTPass=edtxtTPass.getText().toString();
                    View view1 = IDActivation_Old_Activity.this.getCurrentFocus();
                    if (view1 != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view1.getWindowToken(),
                                0);
                    }

                    if (sponsorId && walletBal) {
                            // call wallet transfer api
                        String msg= "Do you continue for Id Activation ?" ;
                            if (!ConnectivityUtils.isNetworkEnabled(IDActivation_Old_Activity.this)) {
                                Toast.makeText(IDActivation_Old_Activity.this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            } else {
                                in.discountmart.utility.AlertDialogUtils.showDialogWithOneButton(IDActivation_Old_Activity.this, new in.discountmart.listener.AlertDialogButtonListener() {
                                    @Override
                                    public void onButtontapped(String btnText) {
                                        if (btnText.equals("OK")) {
                                            getIDActivation();
                                        }
                                    }
                                },"Alert Dialog", msg,"OK");

                            }
                        } else if(!sponsorId && walletBal) {
                            String msg = "Please correct Id no.";
                            Toast.makeText(IDActivation_Old_Activity.this, msg, Toast.LENGTH_SHORT).show();

                        }
                        else if(sponsorId && !walletBal) {
                            String msg = "Check Wallet balance.";
                            Toast.makeText(IDActivation_Old_Activity.this, msg, Toast.LENGTH_SHORT).show();

                        }

                }




                //  }


            }
        });




    }

    public void initPaymentType(){

        ArrayList<SpinnerSingleItemModel> acTypeArryayList=new ArrayList<SpinnerSingleItemModel>();
        SpinnerSingleItemModel acTypeModel[]= new SpinnerSingleItemModel[1];
        acTypeModel[0]=new SpinnerSingleItemModel();
        acTypeModel[0].setName("Wallet");
        acTypeModel[0].setId("0");
        acTypeModel[0].setAmount("0");

        acTypeArryayList=new ArrayList<SpinnerSingleItemModel>(Arrays.asList(acTypeModel));

        spinnerAdapter = new SpinnerSingleItemAdapter(IDActivation_Old_Activity.this, acTypeArryayList);
        spnrPayType.setAdapter(spinnerAdapter);

    }

    /*Get Package List Api*/
    private void getPackageList(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        String stringRequestType="";
        stringRequestType= ApiConstants.REQUEST_CHECK_ID_KIT;
        baseRequest.setReqtype(stringRequestType);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(this));


        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Parameter=gson.toJson(baseRequest);
        Log.e("RequestPackageList:", Parameter);

        Call<IDActivePackageList> listResponseCall=
                NetworkClient1.getInstance(this).create(MyTeamService.class).fetchIDPackgeList(baseRequest,strApiKey);

        listResponseCall.enqueue(new Callback<IDActivePackageList>() {
            @Override
            public void onResponse(Call<IDActivePackageList> call, Response<IDActivePackageList> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();

                try {
                    IDActivePackageList listResponse=new IDActivePackageList();
                    listResponse=response.body();
                    if(listResponse!= null){
                        if (listResponse.getResponse().equals("OK")) {

                            if(listResponse.getGetkit() != null && listResponse.getGetkit().size() > 0) {
                                pckArrayList = new ArrayList<IDActivePackageList.IDPackageList>();
                                pckArrayList=listResponse.getGetkit();
                                singleItemList=new ArrayList<SpinnerSingleItemModel>();
                                //packageListArrayList = new ArrayList<PackageListResponse.PackageList>(Arrays.asList(packageList));
                                btnSubmit.setEnabled(true);
                                btnSubmit.setClickable(true);
                                for(int i=0; i < pckArrayList.size(); i++){
                                    SpinnerSingleItemModel singleItemModel=new SpinnerSingleItemModel();

                                    singleItemModel.setId(pckArrayList.get(i).getKitid());
                                    singleItemModel.setName(pckArrayList.get(i).getKitname());
                                    singleItemModel.setAmount(pckArrayList.get(i).getKitamount());
                                    singleItemList.add(singleItemModel);
                                }
                                if(singleItemList != null && singleItemList.size() > 0) {
                                    spinnerAdapter = new SpinnerSingleItemAdapter(IDActivation_Old_Activity.this, singleItemList);
                                    spnrPckg.setAdapter(spinnerAdapter);
                                }
                            }
                            else {
                                txtErrorMsg.setText("No kit found for activation ID");
                                btnSubmit.setEnabled(false);
                                btnSubmit.setClickable(false);
                                Toast.makeText(IDActivation_Old_Activity.this, "Package List Is Empty", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            Toast.makeText(IDActivation_Old_Activity.this, listResponse.getResponse(), Toast.LENGTH_SHORT).show();

                        }
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<IDActivePackageList> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
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

    /*Wallet Balance Api Request and response*/
    private void getWalletBalance(){

        pDialog=new ProgressDialog(IDActivation_Old_Activity.this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        IDActivationBalance baseRequest=new IDActivationBalance();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_ID_ACTIVE_BAL);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(IDActivation_Old_Activity.this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(IDActivation_Old_Activity.this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(IDActivation_Old_Activity.this));
        baseRequest.setWallettype("R");
        String request= new Gson().toJson(baseRequest);
        Log.e("Request",request);

        Call<GetWalleBalanceResponse> walleBalanceResponseCall=
                NetworkClient.getInstance(this).create(WalletServices.class).fetchIDActiveBalance(baseRequest,strApiKey);

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
                            layoutContent.setVisibility(View.GONE);
                            btnCheck.setClickable(false);
                            btnCheck.setEnabled(false);
                            walletBal=false;

                            txtErrorMsg.setText("Insufficient  Wallet Balance So You Can't Request.");
                        }
                        /*else if(availWalletBal < 249){
                            btnSubmit.setVisibility(View.GONE);
                            txtMsg.setText("Minimum 249/- Required to Selected Wallet, Plz Select Another");
                        }*/
                        else {
                            layoutContent.setVisibility(View.GONE);
                            btnCheck.setClickable(true);
                            btnCheck.setEnabled(true);
                            walletBal=true;
                            // kit api
                            getPackageList();
                        }
                    }
                    else if(Response.getResponse().contains("FAILED") && Response.getMsg().contains("Invalid Login")){
                        String toast= "Invalid login detail. Please login again";
                        Toast.makeText(IDActivation_Old_Activity.this,toast,Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(IDActivation_Old_Activity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else if(Response.getResponse().contains("FAILED")) {
                        Toast.makeText(IDActivation_Old_Activity.this, Response.getResponse(), Toast.LENGTH_SHORT).show();
                        layoutContent.setVisibility(View.GONE);
                        btnCheck.setClickable(false);
                        btnCheck.setEnabled(false);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetWalleBalanceResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(IDActivation_Old_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*Get Member Name Api request ane Response*/
    private void checkIdForActivation(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_CHECK_ID_FOR_ACTIVATION);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(this));
        baseRequest.setMemberid(edtxtMemId.getText().toString());
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(this));

        Call<BaseResponse> memberNameResponseCall=
                NetworkClient.getInstance(this).create(MyTeamService.class).fetchCheckIdForActivation(baseRequest,strApiKey);

        memberNameResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    BaseResponse Response =new BaseResponse();
                    Response=response.body();
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {
                            //getMemberName();
                            txtMemName.setText(Response.getMembername());
                            txtMemName.setTextColor(getResources().getColor(R.color.black));
                            sponsorId=true;
                            layoutContent.setVisibility(View.VISIBLE);

                        }
                        else if(Response.getResponse().contains("FAILED") && Response.getMsg().contains("Invalid Login")){
                            new BusinessDashboardActivity().blankValueFromSharedPreference(IDActivation_Old_Activity.this);
                        }
                        else if(Response.getResponse().contains("FAILED")){
                            layoutContent.setVisibility(View.GONE);
                            txtMemName.setText(Response.getMsg());
                            txtMemName.setTextColor(getResources().getColor(R.color.red));
                            sponsorId=false;
                            String toast= Response.getResponse()+ ":" + Response.getMsg();
                            Toast.makeText(IDActivation_Old_Activity.this, toast, Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        String msg="Something went wrong.";
                        Toast.makeText(IDActivation_Old_Activity.this,msg,Toast.LENGTH_SHORT).show();
                        new BusinessDashboardActivity().blankValueFromSharedPreference(IDActivation_Old_Activity.this);
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(IDActivation_Old_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
        baseRequest.setReqtype(ApiConstants.REQUEST_MEMBER_NAME);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        baseRequest.setMemberid(edtxtMemId.getText().toString());

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
                        txtMemName.setText(Response.getMemname());
                        txtMemName.setTextColor(getResources().getColor(R.color.black));
                        sponsorId=true;
                        layoutContent.setVisibility(View.VISIBLE);

                    }
                    else if(Response.getResponse().contains("FAILED") && Response.getMsg().contains("Invalid Login")){
                        new BusinessDashboardActivity().blankValueFromSharedPreference(IDActivation_Old_Activity.this);
                    }
                    else {
                        layoutContent.setVisibility(View.GONE);
                        txtMemName.setText(Response.getMsg());
                        txtMemName.setTextColor(getResources().getColor(R.color.red));
                        sponsorId=false;
                        String toast= Response.getResponse()+ ":" + Response.getMsg();
                        Toast.makeText(IDActivation_Old_Activity.this, toast, Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetMemberNameResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(IDActivation_Old_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    /* ID Activation Api Request and response*/
    private void getIDActivation(){

        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        IDActivationRequest baseRequest=new IDActivationRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_ID_ACTIVATION);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        baseRequest.setToidno(strMemId);
        baseRequest.setTranspassword(strTPass);
        baseRequest.setPackageid(strPckgId);

        String request= new Gson().toJson(baseRequest);
        Log.e("Request",request);

        Call<BaseResponse> walleBalanceResponseCall=
                NetworkClient.getInstance(this).create(MyTeamService.class).fetchIDActivate(baseRequest,strApiKey);

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

                            String msg= Response.getMsg() ;
                            if(SharedPrefrence_Business.getIsActive(IDActivation_Old_Activity.this).equals("N"))
                                SharedPrefrence_Business.setIsActive(IDActivation_Old_Activity.this,"Y");
                            else
                                SharedPrefrence_Business.setIsActive(IDActivation_Old_Activity.this,"Y");

                            AlertDialogUtils.showDialogWithOneButton(IDActivation_Old_Activity.this, new AlertDialogButtonListener() {
                                @Override
                                public void onButtontapped(String btnText) {
                                    if (btnText.equals("OK")) {
                                        Intent intent=new Intent(IDActivation_Old_Activity.this, BusinessDashboardActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            },"Alert Dialog", msg,"OK");
                        }
                        else if (Response.getResponse().equals("FAILED")){
                            String msg= Response.getResponse()+ " \n\n "+Response.getMsg();
                            AlertDialogUtils.showDialogWithOneButton(IDActivation_Old_Activity.this, new in.discountmart.listener.AlertDialogButtonListener() {
                                @Override
                                public void onButtontapped(String btnText) {

                                }
                            },"Alert Dialog", msg,"OK");
                            Toast.makeText(IDActivation_Old_Activity.this, Response.getResponse(), Toast.LENGTH_SHORT).show();

                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login")){
                            new BusinessDashboardActivity().blankValueFromSharedPreference(IDActivation_Old_Activity.this);
                            //finish();
                        }
                        else if(Response.getResponse().equals("Failed") && Response.getMsg().contains("Member already activated.")){
                            String msg= Response.getResponse()+ " \n\n "+Response.getMsg();
                            AlertDialogUtils.showDialogWithOneButton(IDActivation_Old_Activity.this, new in.discountmart.listener.AlertDialogButtonListener() {
                                @Override
                                public void onButtontapped(String btnText) {

                                }
                            },"Alert Dialog", msg,"OK");
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
                Toast.makeText(IDActivation_Old_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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