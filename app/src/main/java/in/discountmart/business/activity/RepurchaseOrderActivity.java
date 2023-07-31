package in.discountmart.business.activity;

import static in.discountmart.business.business_constants.AppConstants.CURRENT_TAG;
import static in.discountmart.business.business_constants.AppConstants.REPURCHASE_PRODUCT;

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
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;

import in.base.network.NetworkClient;
import in.discountmart.R;
import in.discountmart.activity.LoginActivity;
import in.discountmart.business.adapter.PackageListAdapter;
import in.discountmart.business.adapter.RepurchaseProductAdapter;
import in.discountmart.business.adapter.SpinnerSingleItemAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.MyTeamService;
import in.discountmart.business.call_api.WalletServices;
import in.discountmart.business.model_business.AddProductModel;
import in.discountmart.business.model_business.SpinnerSingleItemModel;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.IDActivationBalance;
import in.discountmart.business.model_business.responsemodel.CheckValidIDResponse;
import in.discountmart.business.model_business.responsemodel.GetWalleBalanceResponse;
import in.discountmart.business.model_business.responsemodel.IDActivePackageList;
import in.discountmart.business.model_business.responsemodel.RepurchaseProductResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.listener.AlertDialogButtonListener;
import in.discountmart.utility.AlertDialogUtils;
import in.discountmart.utility.ConnectivityUtils;

import java.util.ArrayList;
import java.util.Arrays;

import in.discountmart.business.adapter.PackageListAdapter;
import in.discountmart.business.adapter.RepurchaseProductAdapter;
import in.discountmart.business.adapter.SpinnerSingleItemAdapter;
import in.discountmart.business.model_business.AddProductModel;
import in.discountmart.business.model_business.SpinnerSingleItemModel;
import in.discountmart.business.model_business.responsemodel.IDActivePackageList;
import in.discountmart.business.model_business.responsemodel.RepurchaseProductResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepurchaseOrderActivity extends AppCompatActivity {
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

    //*************
    TextView txtHeader;
    RecyclerView recyclerView;
    RepurchaseProductAdapter adapter;
    LinearLayout layoutRecord;
    public  LinearLayout layoutFooter;
    LinearLayout layoutNoRecord;

    public  TextView txtQuantity;
    public  TextView txtPrice;
    public  TextView txtOrderNow;
    public  TextView txtTotProduct;

    String token="";
    ProgressDialog progressDialog;
    ArrayList<RepurchaseProductResponse.ProductList> productArrayList;
    // ArrayList<AddCartRequest.Product> addproductArrayList;
    ArrayList<AddProductModel> tempSelectProductList;
    // ArrayList<CartListResponse.CartProduct> cartProductArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_activation_new);
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
        getSupportActionBar().setTitle("Product Request");

        /*Call From Wallet typ
        e*/
        if(!ConnectivityUtils.isNetworkEnabled(this)){
            Toast.makeText(RepurchaseOrderActivity.this,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(RepurchaseOrderActivity.this,"Plz Enter Member Id", Toast.LENGTH_SHORT).show();
                    edtxtMemId.requestFocus();
                }
                else {
                    View view2 = RepurchaseOrderActivity.this.getCurrentFocus();
                    if (view2 != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view2.getWindowToken(),
                                0);
                    }
                    strMemId=edtxtMemId.getText().toString().toUpperCase();
                    String idno= SharedPrefrence_Business.getUserID(RepurchaseOrderActivity.this).toUpperCase();

                        //Call Epin ckeck api
                        if(!ConnectivityUtils.isNetworkEnabled(RepurchaseOrderActivity.this)){
                            Toast.makeText(RepurchaseOrderActivity.this,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(RepurchaseOrderActivity.this,"Please enter Member Id",Toast.LENGTH_SHORT).show();
                    edtxtMemId.requestFocus();
                }
                else if(!sponsorId){
                    //edTextSponsorIdNo.setError("Enter Sponsor Id.No.");
                    //edTextSponsorIdNo.requestFocus();
                    Toast.makeText(RepurchaseOrderActivity.this,"click on check button for ID is active or not",Toast.LENGTH_SHORT).show();
                }
                else if(strPckgId.equals("0")){
                    Toast.makeText(RepurchaseOrderActivity.this,"Please select proper package",Toast.LENGTH_SHORT).show();
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
                    View view1 = RepurchaseOrderActivity.this.getCurrentFocus();
                    if (view1 != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view1.getWindowToken(),
                                0);
                    }

                    if (sponsorId && walletBal) {
                            // call wallet transfer api
                        String msg= "Do you continue for Id Activation ?" ;
                            if (!ConnectivityUtils.isNetworkEnabled(RepurchaseOrderActivity.this)) {
                                Toast.makeText(RepurchaseOrderActivity.this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            } else {
                                AlertDialogUtils.showMaterialDialogWithOneButton_2(RepurchaseOrderActivity.this, new AlertDialogButtonListener() {
                                    @Override
                                    public void onButtontapped(String btnText) {
                                        if (btnText.equals("OK")) {
                                           // getIDActivation();
                                        }
                                    }
                                },"Alert Dialog", msg,"OK");

                            }
                        } else if(!sponsorId && walletBal) {
                            String msg = "Please correct Id no.";
                            Toast.makeText(RepurchaseOrderActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                        else if(sponsorId && !walletBal) {
                            String msg = "Check Wallet balance.";
                            Toast.makeText(RepurchaseOrderActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }
                }
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

        spinnerAdapter = new SpinnerSingleItemAdapter(RepurchaseOrderActivity.this, acTypeArryayList);
        spnrPayType.setAdapter(spinnerAdapter);

    }

    /*Wallet Balance Api Request and response*/
    private void getWalletBalance(){

        pDialog=new ProgressDialog(RepurchaseOrderActivity.this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        IDActivationBalance baseRequest=new IDActivationBalance();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_ID_ACTIVE_BAL);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(RepurchaseOrderActivity.this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(RepurchaseOrderActivity.this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(RepurchaseOrderActivity.this));
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
                        }
                    }
                    else if(Response.getResponse().contains("FAILED") && Response.getMsg().contains("Invalid Login")){
                        String toast= "Invalid login detail. Please login again";
                        Toast.makeText(RepurchaseOrderActivity.this,toast,Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RepurchaseOrderActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else if(Response.getResponse().contains("FAILED")) {
                        Toast.makeText(RepurchaseOrderActivity.this, Response.getResponse(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(RepurchaseOrderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

        Call<CheckValidIDResponse> memberNameResponseCall=
                NetworkClient.getInstance(this).create(MyTeamService.class).fetchCheckIdForActivation_new(baseRequest,strApiKey);

        memberNameResponseCall.enqueue(new Callback<CheckValidIDResponse>() {
            @Override
            public void onResponse(Call<CheckValidIDResponse> call, Response<CheckValidIDResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    CheckValidIDResponse Response =new CheckValidIDResponse();
                    Response=response.body();
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {
                            //getMemberName();
                            txtMemName.setText(Response.getMembername());
                            txtMemName.setTextColor(getResources().getColor(R.color.black));
                            sponsorId=true;
                            String mobNo = Response.getMobileno();
                            CURRENT_TAG=REPURCHASE_PRODUCT;
                           // if (sponsorId && walletBal && (Response.getActivestatus().equals("Y"))) {
                            if (sponsorId && walletBal) {
                                // call wallet transfer api
                                String msg= "Do you continue for Id Activation ?" ;
                                if (!ConnectivityUtils.isNetworkEnabled(RepurchaseOrderActivity.this)) {
                                    Toast.makeText(RepurchaseOrderActivity.this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                } else {
                                    AlertDialogUtils.showMaterialDialogWithOneButton_2(RepurchaseOrderActivity.this, new AlertDialogButtonListener() {
                                        @Override
                                        public void onButtontapped(String btnText) {
                                            if (btnText.equals("OK")) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("idno", strMemId);
                                                bundle.putString("shoppingwallet", String.valueOf(availWalletBal));
                                                bundle.putString("mobileno", mobNo);
                                                Intent activationIntent = new Intent(RepurchaseOrderActivity.this, RepurchaseOrderProduct.class);
                                                activationIntent.putExtras(bundle);
                                                startActivity(activationIntent);
                                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                                            }
                                        }
                                    }, "Alert Dialog", msg, "OK");
                                }
                            }
                            else if(sponsorId && !walletBal) {
                                String msg = "Check Wallet balance.";
                                Toast.makeText(RepurchaseOrderActivity.this, msg, Toast.LENGTH_SHORT).show();

                            }
                            else if (Response.getActivestatus().equals("N"))
                            {
                                layoutContent.setVisibility(View.GONE);
                                txtMemName.setText("ID not Active. Please Check..!");
                                txtMemName.setTextColor(getResources().getColor(R.color.red));                            }
                            //layoutContent.setVisibility(View.VISIBLE);
                        }
                        else if(Response.getResponse().contains("FAILED") && Response.getMsg().contains("Invalid Login")){
                            new BusinessDashboardActivity().blankValueFromSharedPreference(RepurchaseOrderActivity.this);
                        }
                        else if(Response.getResponse().contains("FAILED")){
                            layoutContent.setVisibility(View.GONE);
                            txtMemName.setText(Response.getMsg());
                            txtMemName.setTextColor(getResources().getColor(R.color.red));
                            sponsorId=false;
                            String toast= Response.getResponse()+ " : " + Response.getMsg();
                            Toast.makeText(RepurchaseOrderActivity.this, toast, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        String msg="Something went wrong.";
                        Toast.makeText(RepurchaseOrderActivity.this,msg,Toast.LENGTH_SHORT).show();
                        new BusinessDashboardActivity().blankValueFromSharedPreference(RepurchaseOrderActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CheckValidIDResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(RepurchaseOrderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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