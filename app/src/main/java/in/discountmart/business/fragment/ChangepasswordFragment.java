package in.discountmart.business.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import in.base.network.NetworkClient;
import in.discountmart.R;
import in.discountmart.activity.LoginActivity;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.ProfileServices;
import in.discountmart.business.model_business.requestmodel.ChangePasswordRequest;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangepasswordFragment extends Fragment {

    public ChangepasswordFragment(){ }

    EditText edtxtoldpass;
    EditText edtxtnewpass;
    EditText edtxtConfpass;

    Button submit;
    Context context;
    String strOldPass="";
    String strNewPass="";
    String strApiKey="";
    ProgressDialog pDialog;
    View view1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.business_change_pass_fragment, container, false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            context = getActivity();
            view1 = getActivity().findViewById(android.R.id.content);


            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);

            edtxtnewpass = (EditText) rootView.findViewById(R.id.change_frag_edtxt_newpass);
            edtxtoldpass = (EditText) rootView.findViewById(R.id.change_frag_edtxt_oldpass);
            edtxtConfpass = (EditText) rootView.findViewById(R.id.change_frag_edtxt_confpass);
            submit = (Button) rootView.findViewById(R.id.change_frag_edtxt_btn_submit);
            // getname=getActivity().getIntent().getBundleExtra("bundle").getString(AppConstants.INTENT_NAME);

            submit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    if (edtxtnewpass.getText().toString().equals("")) {

                        Toast.makeText(getActivity(), "Please Enter new password", Toast.LENGTH_LONG).show();
                        return;

                    }
                    if (edtxtoldpass.getText().toString().equals("")) {

                        Toast.makeText(getActivity(), "Please Enter old password", Toast.LENGTH_LONG).show();
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
                        //new getChangePassDetails().execute();

                        getChangePasswordDetails();


                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rootView;

    }

    /*Change Password Detail Request and Response Api Method*/
    private void getChangePasswordDetails() {
        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        /*Set value in Entity class*/
        changePasswordRequest.setReqtype(ApiConstants.REQUEST_CHANGE_PASSWORD);
        changePasswordRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        changePasswordRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        changePasswordRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        changePasswordRequest.setNpasswd(strNewPass);
        Call<BaseResponse> responseCall = NetworkClient.getInstance(getActivity()).create(ProfileServices.class).fatchChangePassword(changePasswordRequest,strApiKey);

        responseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
               if(pDialog.isShowing())
                       pDialog.dismiss();
                BaseResponse baseResponse = response.body();
                if (baseResponse.getResponse().equalsIgnoreCase("OK")) {
                    String toast= baseResponse.getResponse() +":- Change Password Sucessfully";
                    Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                    Intent i =new Intent(getActivity(), LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getActivity().startActivity(i);
                } else {
                    //showToast(baseResponse.getResponse());
                    String toast= baseResponse.getResponse() +" :- No Change Password Somthing Wrong";
                    Snackbar.make(view1, toast, Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable throwable) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Snackbar.make(view1, throwable.getMessage(), Snackbar.LENGTH_LONG)
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
