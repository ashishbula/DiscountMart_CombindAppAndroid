package in.discountmart.business.activity;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import in.base.network.NetworkClient;
import in.base.ui.BaseActivity;
import in.discountmart.R;
import in.discountmart.activity.LoginActivity;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.ProfileServices;
import in.discountmart.business.listener.AlertDialogButtonListener;
import in.discountmart.business.model_business.requestmodel.ForgotPassRequest;
import in.discountmart.business.model_business.responsemodel.ForgotPasswordResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.AlertDialogUtils;
import in.discountmart.business.utility.ConnectivityUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotpassActivity extends BaseActivity {

    ImageView imghome;
    EditText edtxtId;
    EditText edtxtMobile;
    TextView mTxtvActionBarTitle;
    String sys,ismailsent,issmssent,isuser;
    Button submit;
    String strMobile="";
    String strId="";
    String strApiKey="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.business_activity_forgotpassword);
        try {

            ForgotpassActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            edtxtMobile=(EditText)findViewById(R.id.forgotpass_act_edtxt_mobile);
            edtxtId=(EditText)findViewById(R.id.forgotpass_act_edtxt_id);
            submit=(Button)findViewById(R.id.forgotpass_act_btn_submit);

            /* Get api key value from Shared Preference */
            strApiKey= SharedPrefrence_Business.getApiKey(this);

            // getname=getActivity().getIntent().getBundleExtra("bundle").getString(AppConstants.INTENT_NAME);
            submit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    if (edtxtId.getText().toString().equals("")) {

                        Toast.makeText(ForgotpassActivity.this, "Please Enter userid ", Toast.LENGTH_LONG).show();
                        return;

                    }
                   /* else if (edtxtMobile.getText().toString().equals("")) {

                        Toast.makeText(ForgotpassActivity.this, "Please Enter mobile no ", Toast.LENGTH_LONG).show();
                        return;

                    }
                    else if(edtxtMobile.getText().toString().length() < 10 &&
                            edtxtMobile.getText().toString().length() > 10){
                        Toast.makeText(ForgotpassActivity.this, "Enter correct mobile no ", Toast.LENGTH_LONG).show();
                        return;
                    }*/
                    else {
                        strMobile=edtxtMobile.getText().toString();
                        strId=edtxtId.getText().toString();

                        if(!ConnectivityUtils.isNetworkEnabled(ForgotpassActivity.this)){
                            Toast.makeText(ForgotpassActivity.this,getResources().getString(R.string.network_error)
                                    , Toast.LENGTH_SHORT).show();
                        }
                        else {

                            //Call api
                            forgotPasswordDetail();
                        }

                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /*Forgot password api*/

    public void forgotPasswordDetail(){
        showProgressDialog();
        ForgotPassRequest baseRequest=new ForgotPassRequest();
        String parameter="";
        baseRequest.setUserid(strId);
        baseRequest.setMobileno("");
        baseRequest.setReqtype(ApiConstants.REQUEST_FORGOT_PASSWORD);
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(this));

        Gson gson=new Gson();
        parameter=gson.toJson(baseRequest);
        Log.e("ForgotPassReq->",parameter);

        Call<ForgotPasswordResponse> passwordResponseCall=
                NetworkClient.getInstance(ForgotpassActivity.this).create(ProfileServices.class).fetchForgotPassword(baseRequest,strApiKey);

        passwordResponseCall.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                hideProgressDialog();
                try {
                    ForgotPasswordResponse Response = new ForgotPasswordResponse();
                    Response = response.body();

                    if (Response.getResponse().equals("OK")) {
                        if (Response.getIsmailsent().equals("Y") && Response.getIssmssent().equals("Y")) {

                            AlertDialogUtils.showDialogWithOneButton(ForgotpassActivity.this, new AlertDialogButtonListener() {
                                @Override
                                public void onButtontapped(String btnText) {
                                    if (btnText.equals("OK")) {
                                        Intent intent=new Intent(ForgotpassActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            },"Alert Dialog", "SMS & Mail Have Been Send Plz Check Your Phone and Mail Id","OK");

                        } else if (Response.getIsmailsent().equals("Y") && Response.getIssmssent().equals("N")) {
                            AlertDialogUtils.showDialogWithOneButton(ForgotpassActivity.this, new AlertDialogButtonListener() {
                                @Override
                                public void onButtontapped(String btnText) {
                                    if (btnText.equals("OK")) {
                                        Intent intent=new Intent(ForgotpassActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            },"Alert Dialog", "Mail Have Been Send In Your Mail Id","OK");
                        }
                        else if (Response.getIsmailsent().equals("N") && Response.getIssmssent().equals("Y")) {
                            AlertDialogUtils.showDialogWithOneButton(ForgotpassActivity.this, new AlertDialogButtonListener() {
                                @Override
                                public void onButtontapped(String btnText) {
                                    if (btnText.equals("OK")) {

                                        Intent intent=new Intent(ForgotpassActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            },"Alert Dialog", "Message Have Been Send In Your Phone No.","OK");
                        }
                        else if (Response.getIsuser().equals("Y") && Response.getIsmailsent().equals("N")) {
                            AlertDialogUtils.showDialogWithOneButton(ForgotpassActivity.this, new AlertDialogButtonListener() {
                                @Override
                                public void onButtontapped(String btnText) {
                                    if (btnText.equals("OK")) {

                                        Intent intent=new Intent(ForgotpassActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            },"Alert Dialog", "Message Have Been Send In Your Phone No.","OK");
                        }
                        else if (Response.getIsuser().equals("Y") && Response.getIsmailsent().equals("Y")) {
                            AlertDialogUtils.showDialogWithOneButton(ForgotpassActivity.this, new AlertDialogButtonListener() {
                                @Override
                                public void onButtontapped(String btnText) {
                                    if (btnText.equals("OK")) {

                                        Intent intent=new Intent(ForgotpassActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            },"Alert Dialog", "Message Have Been Send In Your Phone No. and email id","OK");
                        }
                    }
                    else {
                        AlertDialogUtils.showDialogWithOneButton(ForgotpassActivity.this, new AlertDialogButtonListener() {
                            @Override
                            public void onButtontapped(String btnText) {
                                if(btnText.equals("OK")){

                                }
                            }
                        },"Alert Dialog","Plz fill correct id","OK");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {

                hideProgressDialog();
                showToast(t.getMessage());
            }
        });


    }

}
