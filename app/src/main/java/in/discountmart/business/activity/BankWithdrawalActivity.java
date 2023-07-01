package in.discountmart.business.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import in.base.network.NetworkClient;
import in.discountmart.R;
import in.discountmart.business.adapter.BankListAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.WalletServices;
import in.discountmart.business.fragment.SuccessMsgFragment;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.GetWalletBalanceRequest;
import in.discountmart.business.model_business.requestmodel.WithdrawalRequest;
import in.discountmart.business.model_business.responsemodel.BankListResponse;
import in.discountmart.business.model_business.responsemodel.BaseResponseEntity;
import in.discountmart.business.model_business.responsemodel.GetBankDetailResponse;
import in.discountmart.business.model_business.responsemodel.GetWalleBalanceResponse;
import in.discountmart.business.model_business.responsemodel.WithdrawlChargeResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.AlertDialogUtils;
import in.discountmart.business.utility.ConnectivityUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankWithdrawalActivity extends AppCompatActivity {

    Context context;
    TableLayout tableLayoutRecord;
    TextView textViewName;
    Spinner spinBankName;
    TextView textViewBranch;
    TextView textViewAcNo;
    TextView textViewIfsc;
    TextView textViewPan;
    TextView textViewFund;
    TextView txtTotAmount;
    TextView txtImps;
    TextView txtBtnSubmit;
    EditText edtxtTransPass;
    EditText edtxtAdminChrge;
    EditText edtxtTdsChrge;
    EditText edtxtWithdrwlAmount;
    TextView textViewNotice;
    EditText editTextReqFund;
    Button buttonSend;
    LinearLayout layoutNotice;
    LinearLayout layoutReqFund;
    LinearLayout layoutSend;
    LinearLayout layoutAdminChrge;
    LinearLayout layoutTdsChrge;
    LinearLayout layoutTPass;
    LinearLayout layoutWithdrawlAmount;

    double intWalletBal=0;
    double withDrawlAmount=0;
    String strReqAmount="0";
    String strImps="0";
    String strtotAmount="0";
    String strTransPass="0";
    String strApiKey="";


    ArrayList<BankListResponse.BankList> bankListArrayList;
    BankListResponse.BankList bankLists[];
    BankListAdapter bankAccountAdapter;
    ProgressDialog pDialog;
    //Empty Constructor
    public BankWithdrawalActivity(){
        //
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_withdrawal_fragment);
        context = BankWithdrawalActivity.this;
        try {

            textViewFund=(TextView)findViewById(R.id.bank_withdrawl_frag_txtView_avail_fund);
            textViewName=(TextView)findViewById(R.id.bank_withdrawl_frag_txtView_paye_nme);
            spinBankName=(Spinner) findViewById(R.id.bank_withdrawl_frag_spin_bank_name);
            textViewBranch=(TextView)findViewById(R.id.bank_withdrawl_frag_txtView_branch_name);
            textViewAcNo=(TextView)findViewById(R.id.bank_withdrawl_frag_txtView_acno);
            textViewIfsc=(TextView)findViewById(R.id.bank_withdrawl_frag_txtView_ifsc);
            textViewPan=(TextView)findViewById(R.id.bank_withdrawl_frag_txtView_pan);
            textViewNotice=(TextView)findViewById(R.id.bank_withdrawl_frag_Txt_notice);
            txtBtnSubmit=(TextView)findViewById(R.id.bank_withdrawl_frag_btn_check);

            edtxtTransPass=(EditText)findViewById(R.id.bank_withdrawl_frag_edTxt_pass);
            editTextReqFund=(EditText)findViewById(R.id.bank_withdrawl_frag_edTxt_req_fund);
            edtxtAdminChrge=(EditText)findViewById(R.id.bank_withdrawl_frag_edTxt_admin_chrge);
            edtxtTdsChrge=(EditText)findViewById(R.id.bank_withdrawl_frag_edTxt_tds_chrge);
            edtxtWithdrwlAmount=(EditText)findViewById(R.id.bank_withdrawl_frag_edTxt_withdrwl_amount);
            buttonSend=(Button)findViewById(R.id.bank_withdrawl_frag_btn_req_fund);
            layoutNotice=(LinearLayout)findViewById(R.id.bank_withdrawl_frag_layout_notice);
            layoutReqFund=(LinearLayout)findViewById(R.id.bank_withdrawl_frag_layout_fund_content);
            layoutSend=(LinearLayout)findViewById(R.id.bank_withdrawl_frag_layout_send);
            layoutTPass=(LinearLayout)findViewById(R.id.bank_withdrawl_frag_layout_pass);
            layoutAdminChrge=(LinearLayout)findViewById(R.id.bank_withdrawl_frag_layout_admin_chare);
            layoutTdsChrge=(LinearLayout)findViewById(R.id.bank_withdrawl_frag_layout_tds_chare);
            layoutWithdrawlAmount=(LinearLayout)findViewById(R.id.bank_withdrawl_frag_layout_withdrwl_amount);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(this);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Bank Withdrawal");

            //Call Bank List APi
            if(!ConnectivityUtils.isNetworkEnabled(context)){
                Toast.makeText(context,getString(R.string.network_error),Toast.LENGTH_SHORT).show();
            }
            else {
                //new getBankListDetails().execute();
                getBankList();
            }

            //spinner state selection

            spinBankName.setEnabled(false);
            spinBankName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent,
                                           View arg1, int position, long arg3) {
                    //String item = parent.getItemAtPosition(position).toString();

                    BankListResponse.BankList bankList=( BankListResponse.BankList)parent.getItemAtPosition(position);
                    //strBankId = bankList.getBankcode();
                    //strBankName=bankList.getBankname();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

            /*Button on click for send Request*/
            txtBtnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(editTextReqFund.getText().toString().equals("")){
                        Toast.makeText(context,"Plz Enter Request Amount",Toast.LENGTH_SHORT).show();
                        editTextReqFund.requestFocus();
                    }


                    else {
                        View view1 = BankWithdrawalActivity.this.getCurrentFocus();
                        if (view1 != null) {
                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(),
                                    0);
                        }
                        strReqAmount=editTextReqFund.getText().toString();
                        int amount= Integer.parseInt(strReqAmount);
                        withDrawlAmount= Double.parseDouble(strReqAmount);
                        if(amount >= 1000 ){

                            getWithdrawlCharge();
                        }
                        else {
                            Toast.makeText(context,"Request amount is less than from 1000/- rupees.",Toast.LENGTH_LONG).show();
                        }

                    }
                }
            });

            /*Button on click for send Request*/
            buttonSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(editTextReqFund.getText().toString().equals("")){
                        Toast.makeText(context,"Plz Enter Request Amount",Toast.LENGTH_SHORT).show();
                        editTextReqFund.requestFocus();
                    }
                    /*else if(txtImps.getText().toString().equals("")){
                        Toast.makeText(context,"Plz Enter Request Amount",Toast.LENGTH_SHORT).show();
                        //editTextReqFund.requestFocus();
                    }
                    else if(txtTotAmount.getText().toString().equals("")){
                        Toast.makeText(context,"Plz Enter Request Amount",Toast.LENGTH_SHORT).show();
                        //editTextReqFund.requestFocus();
                    }*/
                    else if(edtxtTransPass.getText().toString().equals("")){
                        Toast.makeText(context,"Plz Enter Transaction Password",Toast.LENGTH_SHORT).show();
                        //editTextReqFund.requestFocus();
                    }
                    else {
                        View view1 = BankWithdrawalActivity.this.getCurrentFocus();
                        if (view1 != null) {
                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(),
                                    0);
                        }
                        strReqAmount=editTextReqFund.getText().toString();
                        int amount= Integer.parseInt(strReqAmount);
                        if(amount >= 500 ){
                            //strImps=txtImps.getText().toString();
                            //strtotAmount=txtTotAmount.getText().toString();
                            strTransPass=edtxtTransPass.getText().toString();
                            //new getFundRequestDetails().execute();
                            getBankWithdrawlRequest();
                        }
                        else {
                            Toast.makeText(context,"Request amount is less than from 500/- rupees.",Toast.LENGTH_LONG).show();
                        }

                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /*Method of Spinner Set Index*/
    private int getIndexSpinBank(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){

            BankListResponse.BankList model = (BankListResponse.BankList)spinner.getItemAtPosition(i);

            if (model.getBankcode().equalsIgnoreCase(myString)){
                index=i;
            }
        }
        return index;
    }


    /*Bank list Api Request and Response*/
    private void getBankList(){

        pDialog=new ProgressDialog(BankWithdrawalActivity.this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest baseRequest= new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_BANKLIST);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        Call<BankListResponse> bankListCall=
                NetworkClient.getInstance(context).create(WalletServices.class).fetchBanklist(baseRequest,strApiKey);

        bankListCall.enqueue(new Callback<BankListResponse>() {
            @Override
            public void onResponse(Call<BankListResponse> call, Response<BankListResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();

                try {

                    BankListResponse Response = new BankListResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {

                        bankLists=Response.getBankers();
                        if(bankLists.length > 0){
                            bankListArrayList=new ArrayList<BankListResponse.BankList>(Arrays.asList(bankLists));
                            bankAccountAdapter=new BankListAdapter(context,bankListArrayList);
                            spinBankName.setAdapter(bankAccountAdapter);
                            //new getBankDetails().execute();
                            getBankDetail();
                        }
                        else {
                            String toast= Response.getResponse()+ "Record is Null";
                            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                        }


                    }
                    else {
                        String toast= Response.getResponse()+ ": Somthing Wrong" ;
                        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BankListResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(BankWithdrawalActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    /*Bank Detail Api Request and Response*/
    private void getBankDetail(){
        //showProgressDialog();
        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_BANK_DETAIL);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getUserID(context));

        Call<GetBankDetailResponse> bankDetailResponseCall=
                NetworkClient.getInstance(context).create(WalletServices.class).fetchBankDetail(baseRequest,strApiKey);

        bankDetailResponseCall.enqueue(new Callback<GetBankDetailResponse>() {
            @Override
            public void onResponse(Call<GetBankDetailResponse> call, Response<GetBankDetailResponse> response) {
                //hideProgressDialog();
                try {

                    GetBankDetailResponse Response =new GetBankDetailResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {

                        if(Response.getPayeename().equals(""))
                            textViewName.setText(" No Name");
                        else
                            textViewName.setText(Response.getPayeename());

                        spinBankName.setSelection(getIndexSpinBank(spinBankName,Response.getBankid()));
                        if(Response.getBranchname().equals(""))
                            textViewBranch.setText("No Branch Name");
                        else
                            textViewBranch.setText(Response.getBranchname());

                        if (Response.getAcno().equals(""))
                            textViewAcNo.setText("No Account Number");

                        else
                            textViewAcNo.setText(Response.getAcno());

                        if (Response.getIfsccode().equals(""))
                            textViewIfsc.setText("No IFSC Number");

                        else
                            textViewIfsc.setText(Response.getIfsccode());

                        if (Response.getPanno().equals(""))
                            textViewPan.setText("No Pan Number");

                        else
                            textViewPan.setText(Response.getPanno());



                        //new getWalletBalanceDetails().execute();
                       getWalletBalance();
                    }
                    else {
                        Toast.makeText(context, Response.getResponse(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetBankDetailResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(BankWithdrawalActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    /*Wallet Balance Api Request and response*/
    private void getWalletBalance(){

       // showProgressDialog();
        GetWalletBalanceRequest baseRequest=new GetWalletBalanceRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_WALLET_BALANCE);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        baseRequest.setActype("M");

        Call<GetWalleBalanceResponse> walleBalanceResponseCall=
                NetworkClient.getInstance(context).create(WalletServices.class).fetchWalletBalance(baseRequest,strApiKey);

        walleBalanceResponseCall.enqueue(new Callback<GetWalleBalanceResponse>() {
            @Override
            public void onResponse(Call<GetWalleBalanceResponse> call, Response<GetWalleBalanceResponse> response) {
               // hideProgressDialog();
                try {

                    GetWalleBalanceResponse Response =new GetWalleBalanceResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {

                        textViewFund.setText(Response.getBalance());
                        intWalletBal= Double.parseDouble(Response.getBalance());
                    if(intWalletBal < 1000 ){
                        layoutNotice.setVisibility(View.VISIBLE);
                        layoutReqFund.setVisibility(View.GONE);
                        layoutSend.setVisibility(View.GONE);
                        textViewNotice.setText("Wallet Amount Is Less Than 1000/- So You Can't Request");
                    }
                   else if(intWalletBal < 0){
                        layoutNotice.setVisibility(View.VISIBLE);
                        layoutReqFund.setVisibility(View.GONE);
                        layoutSend.setVisibility(View.GONE);
                        textViewNotice.setText("Insufficient Wallet Balance So You Can't Request");
                    }
                    else {
                        layoutNotice.setVisibility(View.GONE);
                        layoutReqFund.setVisibility(View.VISIBLE);
                        layoutSend.setVisibility(View.VISIBLE);
                        textViewNotice.setText("");
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
                Toast.makeText(BankWithdrawalActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    /* Withdrawal Charge  Api Request and response*/
    private void getWithdrawlCharge(){

        // showProgressDialog();
        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_WITHDRAWAL_CHARGE);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        //baseRequest.setActype("M");

        Call<WithdrawlChargeResponse> walleBalanceResponseCall=
                NetworkClient.getInstance(context).create(WalletServices.class).fetchWithdrawlCharge(baseRequest,strApiKey);

        walleBalanceResponseCall.enqueue(new Callback<WithdrawlChargeResponse>() {
            @Override
            public void onResponse(Call<WithdrawlChargeResponse> call, Response<WithdrawlChargeResponse> response) {
                // hideProgressDialog();
                try {

                    WithdrawlChargeResponse Response =new WithdrawlChargeResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {

                        String strAdiminChrge=Response.getAdmincharge();
                        String strTdsChrge=Response.getTdscharge();
                        double adminChrgePercentage= Double.parseDouble(strAdiminChrge);
                        double tdsChrgePercentage= Double.parseDouble(strTdsChrge);
                        double adminChargeAmount=withDrawlAmount*adminChrgePercentage/100;
                        double tdsChargeAmount=withDrawlAmount*tdsChrgePercentage/100;
                        double withdrwlamount=(withDrawlAmount-(adminChargeAmount+tdsChargeAmount));

                        edtxtAdminChrge.setText(String.valueOf(adminChargeAmount));
                        edtxtTdsChrge.setText(String.valueOf(tdsChargeAmount));
                        edtxtWithdrwlAmount.setText(String.valueOf(withdrwlamount));

                    }
                    else {
                        Toast.makeText(context, Response.getResponse(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<WithdrawlChargeResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(BankWithdrawalActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    /*Bank Withdrawl api Request and Response*/

    private void getBankWithdrawlRequest(){
        pDialog=new ProgressDialog(BankWithdrawalActivity.this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        final WithdrawalRequest Request = new WithdrawalRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_BANK_WITHDRAWAL);
        Request.setPasswd(SharedPrefrence_Business.getPassword(context));
        Request.setUserid(SharedPrefrence_Business.getUserID(context));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        Request.setReqamount(strReqAmount);
        Request.setTranspassword(strTransPass);

        Call<BaseResponseEntity> callbankWithdrawlRequest=
                NetworkClient.getInstance(context).create(WalletServices.class).fetchBankWithdrawlRequest(Request,strApiKey);

        callbankWithdrawlRequest.enqueue(new Callback<BaseResponseEntity>() {
            @Override
            public void onResponse(Call<BaseResponseEntity> call, Response<BaseResponseEntity> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    BaseResponseEntity Response = new BaseResponseEntity();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {
                        String msg=strReqAmount +"/- " + Response.getMsg() ;
                        SuccessMsgFragment fragment=new SuccessMsgFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Msg",msg);
                        fragment.setArguments(bundle);
                        ((BusinessDashboardActivity)context).replaceFragment_business(fragment,"SuccessMsgFragment",bundle);

                    }
                    else {
                        String toast= Response.getMsg() ;
                        AlertDialogUtils.showDialogWithNeutaral(context, "Notification!", toast);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponseEntity> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(BankWithdrawalActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
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
