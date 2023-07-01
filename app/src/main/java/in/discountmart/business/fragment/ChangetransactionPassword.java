package in.discountmart.business.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import in.base.network.NetworkClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.ProfileServices;
import in.discountmart.business.model_business.requestmodel.ChangeTransatoinPassREquest;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangetransactionPassword extends Fragment {

    public ChangetransactionPassword(){}

    EditText edtxtoldpass;
    EditText edtxtnewpass;
    EditText edtxtConfpass;
    Button submit;

    String strOldPass="";
    String strNewPass="";
    String strApiKey="";

    Context context;
    ProgressDialog pDialog;
    View view1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            rootView = inflater.inflate(R.layout.business_change_transaction_pass_fragment, container, false);
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            context = getActivity();
            view1 = getActivity().findViewById(android.R.id.content);


            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);


            edtxtoldpass = (EditText) rootView.findViewById(R.id.change_transPass_frag_edttxt_oldtpass);
            edtxtnewpass = (EditText) rootView.findViewById(R.id.change_transPass_frag_edttxt_newpass);
            edtxtConfpass = (EditText) rootView.findViewById(R.id.change_transPass_frag_edTxt_conf_password);
            submit = (Button) rootView.findViewById(R.id.change_transPass_frag_btn_submit);
            // getname=getActivity().getIntent().getBundleExtra("bundle").getString(AppConstants.INTENT_NAME);

            submit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub

                    if (edtxtoldpass.getText().toString().equals("")) {

                        Toast.makeText(getActivity(), "Please Enter old password", Toast.LENGTH_LONG).show();
                        return;

                    }
                    if (edtxtnewpass.getText().toString().equals("")) {

                        Toast.makeText(getActivity(), "Please Enter new password", Toast.LENGTH_LONG).show();
                        return;

                    }
                    if (edtxtConfpass.getText().toString().equals("")) {

                        Toast.makeText(getActivity(), "Please Enter Confirm password", Toast.LENGTH_LONG).show();
                        return;

                    }
                    if (!edtxtnewpass.getText().toString().equals(edtxtConfpass.getText().toString())) {

                        Toast.makeText(getActivity(), "Password Not Match", Toast.LENGTH_LONG).show();
                        return;

                    } else {
                        strOldPass = edtxtoldpass.getText().toString();
                        strNewPass = edtxtConfpass.getText().toString();
                        //call api
                        if (!ConnectivityUtils.isNetworkEnabled(context)) {
                            Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        } else {
                            getChangeTransPassDetail();
                        }


                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rootView;

    }


    /*ChangeTransactionPassword Api */
    private void getChangeTransPassDetail(){
        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        ChangeTransatoinPassREquest Request = new ChangeTransatoinPassREquest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_CHANGE_TRANS_PASS);
        Request.setPasswd(SharedPrefrence_Business.getPassword(context));
        Request.setUserid(SharedPrefrence_Business.getUserID(context));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        Request.setNtpasswd(strNewPass);
        Request.setTpasswd(strOldPass);
        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(Request);
        Log.e("ReqChangeTPass:-", Get_Paramenter);

        Call<BaseResponse> callChangeTransPass=
                NetworkClient.getInstance(context).create(ProfileServices.class).fatchChangeTransactionPassword(Request,strApiKey);

        callChangeTransPass.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
               if(pDialog.isShowing())
                   pDialog.dismiss();
                try {

                    BaseResponse Response = new BaseResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {
                        String toast= Response.getResponse() +":- Change Transaction Password Sucessfully";
                        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                        ((BusinessDashboardActivity)context).loadHomeFragment_Business();

                    }
                    else {
                        String toast= Response.getResponse() +" :- No Change Transaction Password Somthing Wrong";
                        Snackbar.make(view1, toast, Snackbar.LENGTH_LONG)
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
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Snackbar.make(view1, t.getMessage(), Snackbar.LENGTH_LONG)
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
