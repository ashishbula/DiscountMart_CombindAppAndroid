package in.discountmart.business.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

import in.base.network.NetworkClient;
import in.base.network.NetworkClient1;
import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.adapter.PinGeneratePackageAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.EpinServices;
import in.discountmart.business.call_api.WalletServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.GetWalletBalanceRequest;
import in.discountmart.business.model_business.requestmodel.PinGeneratePackage;
import in.discountmart.business.model_business.requestmodel.PinGenerateRequest;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.model_business.responsemodel.GetWalleBalanceResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinGenerateFRagment extends Fragment {

    Context context;
    TextView txtAvalBal;
    TextView txtShopBal;
    Spinner spinnerPackage;
    EditText editTextQty;
    EditText editTextTPass;
    Button btnSubmitt;

    ProgressDialog pDialog;
    PinGeneratePackage.PackageList packageList[];
    ArrayList<PinGeneratePackage.PackageList> packageListArrayList;
    PinGeneratePackageAdapter packageListAdapter;

    String strPackageId="";
    String strPackage="";
    String strQty="";
    String strTPass="";
    String strApiKey="";
    String strPkgAmount="";
    static float pkgAmount=0;
    static float totalAmount=0;
    static float walletBal=0;
    //Empty Constructor
    public PinGenerateFRagment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.pin_generate_fragment, container, false);
            context = getActivity();

            txtAvalBal = (TextView) rootView.findViewById(R.id.pin_generate_fragt_avl_bal);
            txtShopBal = (TextView) rootView.findViewById(R.id.pin_generate_fragt_txt_shop_bal);
            spinnerPackage = (Spinner) rootView.findViewById(R.id.pin_generate_fragt_spinner_kit);
            editTextQty = (EditText) rootView.findViewById(R.id.pin_generate_fragt_edtxt_qty);
            editTextTPass = (EditText) rootView.findViewById(R.id.pin_generate_fragt_edtxt_tPass);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);

            /*Call Api Shopping Bal*/
            if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
            else {
                getWalletBalance();
                // new getPackageListDetails().execute();
            }

            /*Spinner Package List */
            spinnerPackage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // check = check + 1;
                    //if (check > 1)
                    // Get selected operator entity
                    PinGeneratePackage.PackageList packageList1 = (PinGeneratePackage.PackageList) parent.getItemAtPosition(position);
                    /*if(packageList1.getPkgid().equals("")){
                        Toast.makeText(getActivity(),"Please Id not Available",Toast.LENGTH_SHORT).show();
                    }*/

                    strPackageId=packageList1.getKitid();
                    strPackage=packageList1.getKitname();
                    pkgAmount=Float.parseFloat(packageList1.getKitamount());

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            btnSubmitt = (Button) rootView.findViewById(R.id.pin_generate_fragt_btn_submitt);

            /*Button submitt click listener*/
            btnSubmitt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(strPackageId.equals("")){
                        Toast.makeText(context,"Plz Select Package",Toast.LENGTH_SHORT).show();
                    }
                    else if (editTextQty.getText().toString().equals("")){
                        editTextQty.setError("Plz Enter Quantity");
                        editTextQty.requestFocus();
                    }
                    else if (editTextTPass.getText().toString().equals("")){
                        editTextTPass.setError("Plz Enter Transaction Password");
                        editTextTPass.requestFocus();
                    }
                    else {
                        strQty=editTextQty.getText().toString();
                        strTPass=editTextTPass.getText().toString();

                        float qty=0;
                        qty=Float.parseFloat(strQty);
                        totalAmount=pkgAmount*qty;
                        if(totalAmount <= walletBal){
                            /*Call Pin Generate api*/
                            if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getPinGenerateDetail();
                                // new getPackageListDetails().execute();
                            }
                        }
                        else {
                            Toast.makeText(getActivity(), "Your Selected Kit Amount is More Than From Wallet Balance ", Toast.LENGTH_SHORT).show();
                        }


                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Pin Generate :" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    /*Wallet Balance Api Request and response*/
    private void getWalletBalance(){

        pDialog=new ProgressDialog(context);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();

        GetWalletBalanceRequest baseRequest=new GetWalletBalanceRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_WALLET_BALANCE);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        baseRequest.setActype("R");

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

                        float bal=0;
                        bal= Float.parseFloat(Response.getBalance());
                        walletBal=Float.parseFloat(Response.getBalance());
                        if(bal > 0){

                            txtAvalBal.setText("");
                            txtShopBal.setText("Shopping Balance:- " +Response.getBalance());
                            btnSubmitt.setVisibility(View.VISIBLE);

                            /*Call packae List api*/
                            getPackageList();
                        }
                        else {
                            txtAvalBal.setText("Sorry Your Balance Is Not Enough So Can't Generate Pin ");
                            txtShopBal.setText("Shopping Balance:- " +Response.getBalance());
                            btnSubmitt.setVisibility(View.GONE);
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

    /*Pin Generate Package List Api Request and REsponse*/
    private void getPackageList(){
        pDialog=new ProgressDialog(context);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        String stringRequestType="";
        stringRequestType= ApiConstants.REQUEST_PIN_GENERATE_PACKAE_LIST;
        baseRequest.setReqtype(stringRequestType);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        Call<PinGeneratePackage> listResponseCall=
                NetworkClient1.getInstance(context).create(EpinServices.class).fetchPinGeneratePackageList(baseRequest,strApiKey);

        listResponseCall.enqueue(new Callback<PinGeneratePackage>() {
            @Override
            public void onResponse(Call<PinGeneratePackage> call, Response<PinGeneratePackage> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();

                PinGeneratePackage listResponse=new PinGeneratePackage();
                listResponse=response.body();
                try {

                    if (listResponse.getResponse().equals("OK")) {
                        packageList = listResponse.getKit();
                        if(packageList != null && packageList.length > 0) {
                            packageListArrayList = new ArrayList<PinGeneratePackage.PackageList>(Arrays.asList(packageList));
                            packageListAdapter = new PinGeneratePackageAdapter(getActivity(), packageListArrayList);
                            spinnerPackage.setAdapter(packageListAdapter);
                        }
                        else {
                            Toast.makeText(context, "Package List Is Empty", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(context, listResponse.getResponse(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<PinGeneratePackage> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*Shopping Balance Api*/
    private void getPinGenerateDetail(){
        pDialog=new ProgressDialog(context);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();

        PinGenerateRequest baseRequest=new PinGenerateRequest();
        /*Set value in Entity class*/
        String stringRequestType="";
        stringRequestType= ApiConstants.REQUEST_PIN_GENERATE;
        baseRequest.setReqtype(stringRequestType);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        baseRequest.setKitid(strPackageId);
        baseRequest.setQty(strQty);
        baseRequest.setActype("R");
        baseRequest.setTranspassw(strTPass);

        Call<BaseResponse> listResponseCall=
                NetworkClient.getInstance(context).create(EpinServices.class).fetchPinGenerate(baseRequest,strApiKey);

        listResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();

                BaseResponse Response=new BaseResponse();
                Response=response.body();
                try {

                    if (Response.getResponse().equals("OK")) {

                        SuccessMsgFragment fragment=new SuccessMsgFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Msg",Response.getMsg());
                        fragment.setArguments(bundle);
                        ((BusinessDashboardActivity)context).replaceFragment_business(fragment,"SuccessMsgFragment",bundle);

                    }
                    else {
                        String toast= Response.getResponse()+ ":" + Response.getMsg();
                        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();

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
}
