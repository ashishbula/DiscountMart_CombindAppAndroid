package in.discountmart.business.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

import in.base.network.NetworkClient;
import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.adapter.PackageListAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.EpinServices;
import in.discountmart.business.call_api.ProfileServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.model_business.responsemodel.GetMemberNameResponse;
import in.discountmart.business.model_business.responsemodel.PackageListResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinTransferFragment extends Fragment {

    Context context;
    EditText editTextIdNo;
    EditText editTextQuantity;
    EditText editTextRemark;
    TextView txtMemberName;
    TextView txtKitQty;
    Spinner spinnerKit;
    Button btnSubmitt;
    ProgressDialog pDialog;
    View view;

    String strKitId="";
    String strKitName="";
    String strKitQty="";
    String strIdNo="";
    String strMemberName="";
    String strQuantity="";
    String strRemark="";
    String strApiKey="";

    PackageListResponse.PackageList packageList[];
    ArrayList<PackageListResponse.PackageList> packageListArrayList;
    PackageListAdapter packageListAdapter;
    //Empty constructor
    public  PinTransferFragment(){
        //empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.business_pintransfer, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            context = getActivity();
            view=getActivity().findViewById(android.R.id.content);
            txtMemberName=(TextView) rootView.findViewById(R.id.pin_transfer_fragment_txt_distName);

            spinnerKit=(Spinner)rootView.findViewById(R.id.pin_transfer_fragment_spinner_kit);
            txtKitQty=(TextView) rootView.findViewById(R.id.pin_transfer_fragment_txt_qty);
            editTextIdNo=(EditText)rootView.findViewById(R.id.pin_transfer_fragment_edtxt_idno);
            editTextQuantity=(EditText)rootView.findViewById(R.id.pin_transfer_fragment_edtxt_qty);
            editTextRemark=(EditText)rootView.findViewById(R.id.pin_transfer_fragment_edtxt_remark);

            btnSubmitt=(Button)rootView.findViewById(R.id.pin_transfer_fragment_btn_submitt);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);


            /*Call Package List APi*/
            if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
            else {
                getPackageList();
                // new getPackageListDetails().execute();
            }

            /*text view mamber name click listner*/
            txtMemberName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(editTextIdNo.getText().equals("")){
                        editTextIdNo.setError("Plz Enter ID Number");
                    }
                    else {
                        strIdNo=editTextIdNo.getText().toString();
                        getMemberName();
                    }

                }
            });

            /*Spinner kit item selected listener*/
            spinnerKit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    PackageListResponse.PackageList packageList=(PackageListResponse.PackageList)parent.getItemAtPosition(position);
                    if(packageList != null){
                        strKitId=packageList.getPkgid();
                        strKitName=packageList.getPkgname();
                        strKitQty=packageList.getQty();
                        txtKitQty.setText("Total Qty:-"+strKitQty);
                    }
                    else {
                        Toast.makeText(context,"No Kit Available", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /*Edit text quantity text change listner*/
            editTextQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
                @Override
                public void afterTextChanged(Editable s) {
                    try{
                        strQuantity=editTextQuantity.getText().toString();
                        int qty=0;
                        int kitQty=0;
                        kitQty= Integer.parseInt(strKitQty);
                        qty= Integer.parseInt(strQuantity);

                        if(strQuantity.matches("") || strQuantity.matches("0")){
                            editTextQuantity.setError(" Plz enter quantity");
                            editTextQuantity.requestFocus();
                        }
                        if(qty == 0){
                            editTextQuantity.setError(" Plz enter proper quantity");
                            editTextQuantity.requestFocus();
                        }

                        else if(qty > kitQty){
                            editTextQuantity.setError("Plz enter lower quantity from total kit quantity");
                            editTextQuantity.requestFocus();
                        }
                        else if(qty > 0 && qty < kitQty){
                            strQuantity=editTextQuantity.getText().toString();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });

            /*Button submitt click listner*/
            btnSubmitt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(editTextIdNo.getText().equals("")){
                        editTextIdNo.setError("Plz Enter ID Number");
                        editTextIdNo.requestFocus();
                    }
                    else if(txtMemberName.getText().toString().equals("")){
                        txtMemberName.setError("First Member Name");
                        txtMemberName.requestFocus();
                    }
                    else if(strKitId.equals("0")){
                        Toast.makeText(context,"Plz Select Proper Kit", Toast.LENGTH_SHORT).show();
                    }
                    else if(editTextQuantity.getText().toString().equals("")){
                        editTextQuantity.setError("Enter Quantity");
                        editTextQuantity.requestFocus();
                    }

                    else if(editTextRemark.getText().toString().equals("")){
                        editTextRemark.setError("Enter Remark");
                        editTextRemark.requestFocus();
                    }
                    else {

                        strQuantity=editTextQuantity.getText().toString();
                        strRemark=editTextRemark.getText().toString();
                        strMemberName=txtMemberName.getText().toString();
                        strIdNo=editTextIdNo.getText().toString();

                        /*Call api*/
                        if(!ConnectivityUtils.isNetworkEnabled(context)){
                            Toast.makeText(context,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            getPinTransferforOTP();
                        }
                    }

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return rootView;
    }


    /*Package List Api request and response*/
    private void getPackageList(){
        pDialog=new ProgressDialog(context);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        String stringRequestType="";
        stringRequestType= ApiConstants.PIN_TRANSFER_PACKAGE;
        baseRequest.setReqtype(stringRequestType);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        Call<PackageListResponse> listResponseCall=
                NetworkClient.getInstance(context).create(EpinServices.class).fetchPackageList(baseRequest,strApiKey);

        listResponseCall.enqueue(new Callback<PackageListResponse>() {
            @Override
            public void onResponse(Call<PackageListResponse> call, Response<PackageListResponse> response) {
               if(pDialog.isShowing())
                   pDialog.dismiss();
                PackageListResponse listResponse=new PackageListResponse();
                listResponse=response.body();
                try {

                    if (listResponse.getResponse().equals("OK")) {
                        packageList = listResponse.getPackages();
                        if(packageList != null && packageList.length > 0) {
                            packageListArrayList = new ArrayList<PackageListResponse.PackageList>(Arrays.asList(packageList));
                            packageListAdapter = new PackageListAdapter(getActivity(), packageListArrayList);
                            spinnerKit.setAdapter(packageListAdapter);
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
            public void onFailure(Call<PackageListResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Get Member Name Api request ane Response*/
    private void getMemberName(){
        pDialog=new ProgressDialog(context);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_GET_MEMBER_NAME);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        baseRequest.setMemberid(editTextIdNo.getText().toString());

        Call<GetMemberNameResponse> memberNameResponseCall=
                NetworkClient.getInstance(context).create(ProfileServices.class).fetchMemberName(baseRequest,strApiKey);

        memberNameResponseCall.enqueue(new Callback<GetMemberNameResponse>() {
            @Override
            public void onResponse(Call<GetMemberNameResponse> call, Response<GetMemberNameResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    GetMemberNameResponse Response =new GetMemberNameResponse();
                    Response=response.body();
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {

                            txtMemberName.setText(Response.getMemname());
                        }
                        else {
                            String toast= Response.getResponse()+ ":" + Response.getMsg();

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

                        String toast= "Something went wrong..";
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
            public void onFailure(Call<GetMemberNameResponse> call, Throwable t) {
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

    /*Pin Transfer api request and response*/
    private void getPinTransferforOTP(){
        pDialog=new ProgressDialog(context);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        String stringRequestType="";
        stringRequestType= ApiConstants.PIN_TRANSFER_OTP;
        baseRequest.setReqtype(stringRequestType);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        Call<BaseResponse> callPinTransferOTP=
                NetworkClient.getInstance(context).create(EpinServices.class).fetchPinTransOTP(baseRequest,strApiKey);

        callPinTransferOTP.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                if(pDialog.isShowing())
                   pDialog.dismiss();

                try{
                    BaseResponse Response=new BaseResponse();
                    Response=response.body();
                    if(Response != null){
                        if(Response.getResponse().equals("OK")){

                            PinTransferOTPFragment fragment=new PinTransferOTPFragment();
                            //OtpFragment fragment=new OtpFragment();
                            Bundle bundle=new Bundle();

                            bundle.putString("toid",strIdNo);
                            bundle.putString("pckid",strKitId);
                            bundle.putString("qty",strQuantity);
                            bundle.putString("remark",strRemark);
                            fragment.setArguments(bundle);
                            ((BusinessDashboardActivity)context).replaceFragment_business(fragment,"PinTransferOTPFragment",bundle);

                        }
                        else {
                            String toast= Response.getResponse()+ ":" + Response.getMsg();

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
                        String toast=  "Something went wrong.." ;

                        Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
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
}
