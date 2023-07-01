package in.discountmart.activity;


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
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import in.base.network.NetworkClient;
import in.base.ui.BaseActivity;
import in.discountmart.R;
import in.discountmart.call_api.ProfileServices;
import in.discountmart.constants.ApiConstants;
import in.discountmart.listener.AlertDialogButtonListener;
import in.discountmart.model_business.requestmodel.BaseRequest;
import in.discountmart.model_business.responsemodel.ForgotPasswordResponse;
import in.discountmart.shared_pref_business.SharedPrefrence;
import in.discountmart.utility.AlertDialogUtils;
import in.discountmart.utility.ConnectivityUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotpassActivity extends BaseActivity {

    ImageView imghome;
    EditText edtxtemailid;
    TextView mTxtvActionBarTitle;
    String sys,ismailsent,issmssent,isuser;
    Button submit;
    String strApiKey="";
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgotpassword);
        try {

            ForgotpassActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            view = findViewById(android.R.id.content);
            edtxtemailid=(EditText)findViewById(R.id.edtxtemailforgot);
            submit=(Button)findViewById(R.id.btnsubmitforgetpass);

            /* Get api key value from Shared Preference */
            strApiKey= SharedPrefrence.getApiKey(this);

            // getname=getActivity().getIntent().getBundleExtra("bundle").getString(AppConstants.INTENT_NAME);
            submit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    if (edtxtemailid.getText().toString().equals("")) {

                        Toast.makeText(ForgotpassActivity.this, "Please Enter userid ", Toast.LENGTH_LONG).show();
                        return;

                    }
                    else {

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
        BaseRequest baseRequest=new BaseRequest();
        String parameter="";
        baseRequest.setUserid(edtxtemailid.getText().toString());
        baseRequest.setReqtype(ApiConstants.REQUEST_FORGOT_PASSWORD);

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
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {
                            if (Response.getIsmailsent().equals("Y") && Response.getIssmssent().equals("Y")) {

                                AlertDialogUtils.showDialogWithOneButton(ForgotpassActivity.this, new AlertDialogButtonListener() {
                                    @Override
                                    public void onButtontapped(String btnText) {
                                        if (btnText.equals("OK")) {
                                            Intent intent=new Intent(ForgotpassActivity.this,LoginActivity.class);
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
                            } else if (Response.getIsmailsent().equals("N") && Response.getIssmssent().equals("Y")) {
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
                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(ForgotpassActivity.this,Response.getMsg());

                        }
                        else if(Response.getResponse().equals("FAILED")) {
                            String msg=Response.getResponse()+ " "+"Something went wrong..";
                            Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
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
                        String msg="Something went wrong. Please try again";
                        blankValueFromSharePreference(ForgotpassActivity.this,msg);
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
