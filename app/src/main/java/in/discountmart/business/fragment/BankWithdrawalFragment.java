package in.discountmart.business.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import in.base.network.NetworkClient;
import in.base.ui.BaseFragment;
import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.adapter.BankListAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.WalletServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.GetWalletBalanceRequest;
import in.discountmart.business.model_business.requestmodel.WithdrawalRequest;
import in.discountmart.business.model_business.responsemodel.BankListResponse;
import in.discountmart.business.model_business.responsemodel.BaseResponseEntity;
import in.discountmart.business.model_business.responsemodel.GetBankDetailResponse;
import in.discountmart.business.model_business.responsemodel.GetWalleBalanceResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.AlertDialogUtils;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankWithdrawalFragment extends BaseFragment {

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
    EditText edtxtTransPass;
    TextView textViewNotice;
    EditText editTextReqFund;
    Button buttonSend;
    LinearLayout layoutNotice;
    LinearLayout layoutReqFund;
    LinearLayout layoutSend;

    double intWalletBal=0;
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
    public BankWithdrawalFragment(){
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.bank_withdrawal_fragment, container, false);
        context = getActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        try {

            textViewFund=(TextView)rootView.findViewById(R.id.bank_withdrawl_frag_txtView_avail_fund);
            textViewName=(TextView)rootView.findViewById(R.id.bank_withdrawl_frag_txtView_paye_nme);
            spinBankName=(Spinner) rootView.findViewById(R.id.bank_withdrawl_frag_spin_bank_name);
            textViewBranch=(TextView)rootView.findViewById(R.id.bank_withdrawl_frag_txtView_branch_name);
            textViewAcNo=(TextView)rootView.findViewById(R.id.bank_withdrawl_frag_txtView_acno);
            textViewIfsc=(TextView)rootView.findViewById(R.id.bank_withdrawl_frag_txtView_ifsc);
            textViewPan=(TextView)rootView.findViewById(R.id.bank_withdrawl_frag_txtView_pan);
            textViewNotice=(TextView)rootView.findViewById(R.id.bank_withdrawl_frag_Txt_notice);

            edtxtTransPass=(EditText)rootView.findViewById(R.id.bank_withdrawl_frag_edTxt_pass);
            editTextReqFund=(EditText)rootView.findViewById(R.id.bank_withdrawl_frag_edTxt_req_fund);
            buttonSend=(Button)rootView.findViewById(R.id.bank_withdrawl_frag_btn_req_fund);
            layoutNotice=(LinearLayout)rootView.findViewById(R.id.bank_withdrawl_frag_layout_notice);
            layoutReqFund=(LinearLayout)rootView.findViewById(R.id.bank_withdrawl_frag_layout_fund_content);
            layoutSend=(LinearLayout)rootView.findViewById(R.id.bank_withdrawl_frag_layout_send);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);
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
                        View view1 = getActivity().getCurrentFocus();
                        if (view1 != null) {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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


        return rootView;
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

        showProgressDialog();
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
               hideProgressDialog();
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

                hideProgressDialog();
                showToast(t.getMessage());
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
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));

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

                hideProgressDialog();
                showToast(t.getMessage());
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
                    if(intWalletBal < 500 ){
                        layoutNotice.setVisibility(View.VISIBLE);
                        layoutReqFund.setVisibility(View.GONE);
                        layoutSend.setVisibility(View.GONE);
                        textViewNotice.setText("Wallet Amount Is Less Than 500/- So You Can't Request");
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
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    /*Bank Withdrawl api Request and Response*/

    private void getBankWithdrawlRequest(){
       showProgressDialog();
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
                hideProgressDialog();
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

                hideProgressDialog();
                showToast(t.getMessage());
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




}
