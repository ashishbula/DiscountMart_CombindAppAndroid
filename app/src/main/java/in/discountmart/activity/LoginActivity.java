package in.discountmart.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import in.base.network.NetworkClient;
import in.base.network.NetworkClient_Utility;
import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.call_api.ProfileServices;
import in.discountmart.constants.ApiConstants;
import in.discountmart.model_business.requestmodel.BaseRequest;
import in.discountmart.model_business.responsemodel.LoginResponseEntity;
import in.discountmart.shared_pref_business.SharedPrefrence;
import in.discountmart.utility.ConnectivityUtils;
import in.discountmart.utility_services.call_api.MainServices;
import in.discountmart.utility_services.constants.AppConstants;
import in.discountmart.utility_services.model.request_model.LoginRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtxtUserId;
    EditText edtxtUserPass;
    Button btnSecure_Login;
    boolean flagValidation;
    String stringUserid = "";
    String stringPassword = "";
    String strErrorMessage = "";
    String stringReqType;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    TextView txtforgetpass;
    TextView txtNewReg;

    private CheckBox rememberCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    Context context;
    ProgressDialog pDialog;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        view=findViewById(android.R.id.content);

        try{

            LoginActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            edtxtUserId = (EditText) findViewById(R.id.login_edtxt_userid);
            edtxtUserPass = (EditText) findViewById(R.id.login_edtxt_pass);
            txtforgetpass = (TextView) findViewById(R.id.login_txt_forgot_pass);
            txtNewReg = (TextView) findViewById(R.id.login_txt_registration);
            rememberCheckBox=(CheckBox)findViewById(R.id.login_chkbox_remember);
            btnSecure_Login = (Button) findViewById(R.id.login_btn_login);

            loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            loginPrefsEditor = loginPreferences.edit();

            saveLogin = loginPreferences.getBoolean("saveLogin",false);

            if (saveLogin == true) {
                edtxtUserId.setText(loginPreferences.getString("userid", ""));
                edtxtUserPass.setText(loginPreferences.getString("password", ""));
                rememberCheckBox.setChecked(true);
            }
           /* txtforgetpass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), ForgotpassActivity.class);
                    startActivity(i);
                }
            });*/

            /*txtNewReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url="https://cpanel.ucmventures.com/NewJoiningfreeuc";
                    Intent i = new Intent(LoginActivity.this, WebViewActivity.class);
                    //i.putExtra("Home","true");
                    i.putExtra("Type","Registration");
                    i.putExtra("URL",url);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });*/

            if(rememberCheckBox.isChecked()){
                stringUserid = edtxtUserId.getText().toString().trim();
                stringPassword = edtxtUserPass.getText().toString().trim();

                loginPrefsEditor.putBoolean("saveLogin", true);
                loginPrefsEditor.putString("userid", stringUserid);
                loginPrefsEditor.putString("password", stringPassword);
                loginPrefsEditor.commit();

            }
            else {
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
            }

            rememberCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (rememberCheckBox.isChecked()) {

                        // null value validation
                        if (edtxtUserId.getText().toString().trim().equals("")) {
                            Toast.makeText(LoginActivity.this,
                                    "Please Enter Userid.", Toast.LENGTH_LONG)
                                    .show();
                            edtxtUserId.requestFocus();
                            return;
                        } else if (edtxtUserPass.getText().toString().trim().equals("")) {
                            Toast.makeText(getApplicationContext(),
                                    "Please Enter Password.", Toast.LENGTH_LONG)
                                    .show();
                            edtxtUserPass.requestFocus();
                            return;

                        } else {
                            stringUserid = edtxtUserId.getText().toString().trim();
                            stringPassword = edtxtUserPass.getText().toString().trim();

                            loginPrefsEditor.putBoolean("saveLogin", true);
                            loginPrefsEditor.putString("userid", stringUserid);
                            loginPrefsEditor.putString("password", stringPassword);
                            loginPrefsEditor.commit();

                        }

                    }
                    else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }
                }
            });

            btnSecure_Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // null value validation
                    if (edtxtUserId.getText().toString().trim().equals("")) {
                        edtxtUserId.setError("Please enter user id");
                        edtxtUserId.requestFocus();
                       // return;
                    } else if (edtxtUserPass.getText().toString().trim().equals("")) {
                        edtxtUserPass.setError("Please enter user password");
                        edtxtUserPass.requestFocus();
                        //return;

                    } else {
                        View view1 = LoginActivity.this.getCurrentFocus();
                        if (view1 != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(),
                                    0);
                        }
                        stringUserid = edtxtUserId.getText().toString().trim();
                        stringPassword = edtxtUserPass.getText().toString().trim();

                        if (rememberCheckBox.isChecked()) {
                            loginPrefsEditor.putBoolean("saveLogin", true);
                            loginPrefsEditor.putString("userid", stringUserid);
                            loginPrefsEditor.putString("password", stringPassword);
                            loginPrefsEditor.commit();
                        } else {
                            loginPrefsEditor.clear();
                            loginPrefsEditor.commit();
                        }
                       /* Intent i = new Intent(LoginActivity.this, DashboardActivity.class);

                        startActivity(i);
                       // finish();
                        finish();overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/
                        //Call Login Api
                        if(!ConnectivityUtils.isNetworkEnabled(LoginActivity.this)){
                            Toast.makeText(LoginActivity.this,getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                        }
                        else {
                            getLoginDetail();


                        }

                    }


                }

            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getLoginDetail(){
        pDialog=new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest Request = new BaseRequest();
       // Set value com Entity class
        Request.setReqtype(ApiConstants.REQUEST_LOGIN);
        Request.setPasswd(stringPassword);
        Request.setUserid(stringUserid);
        Request.setIslogin("N");

        Call<LoginResponseEntity> loginResponseEntityCall=
                NetworkClient.getInstance(LoginActivity.this).create(ProfileServices.class).fetchLogin(Request);

        loginResponseEntityCall.enqueue(new Callback<LoginResponseEntity>() {
            @Override
            public void onResponse(Call<LoginResponseEntity> call, Response<LoginResponseEntity> response) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    LoginResponseEntity Response =new LoginResponseEntity();
                    Response=response.body();
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {
                            SharedPrefrence.setUserID(LoginActivity.this,edtxtUserId.getText().toString());
                            SharedPrefrence.setPassword(LoginActivity.this,edtxtUserPass.getText().toString());
                            SharedPrefrence.setUsername(LoginActivity.this,Response.getMname());
                            SharedPrefrence.setUserMobileNumber(LoginActivity.this,Response.getMobileno());
                            SharedPrefrence.setProfileIamge(LoginActivity.this,Response.getProfilepic());
                            SharedPrefrence.setEmailId(LoginActivity.this,Response.getEmail());
                            SharedPrefrence.setIsActive(LoginActivity.this,Response.getIsactive());
                            SharedPrefrence.setAddress(LoginActivity.this,Response.getAddress());
                            //SharedPrefrence.setDOJ(LoginActivity.this,Response.get());
                            SharedPrefrence.setApiKey(LoginActivity.this,Response.getApikey());
                            SharedPrefrence.setIsLogin(LoginActivity.this,"Y");

                            SharedPrefrence_Business.setUserID(LoginActivity.this,edtxtUserId.getText().toString());
                            SharedPrefrence_Business.setPassword(LoginActivity.this,edtxtUserPass.getText().toString());
                            SharedPrefrence_Business.setUsername(LoginActivity.this,Response.getMname());
                            SharedPrefrence_Business.setUserMobileNumber(LoginActivity.this,Response.getMobileno());
                            SharedPrefrence_Business.setProfileIamge(LoginActivity.this,Response.getProfilepic());
                            SharedPrefrence_Business.setEmailId(LoginActivity.this,Response.getEmail());
                            SharedPrefrence_Business.setIsActive(LoginActivity.this,Response.getIsactive());
                            SharedPrefrence_Business.setAddress(LoginActivity.this,Response.getAddress());
                            //SharedPrefrence.setDOJ(LoginActivity.this,Response.get());
                            SharedPrefrence_Business.setApiKey(LoginActivity.this,Response.getApikey());
                            SharedPrefrence_Business.setIsLogin(LoginActivity.this,"Y");

                            // cll utility login api
                            getLoginDetail_utility();

                            //Intent i=new Intent(LoginActivity.this, DashboardActivity.class);
                           // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //startActivity(i);
                            //finish();
                            //overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        }
                        else {

                            //getLoginDetail_utility();
                            String toast= Response.getResponse()+ ": "+Response.getMsg();
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
                        String toast= Response.getResponse()+ ": "+Response.getMsg();
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
            public void onFailure(Call<LoginResponseEntity> call, Throwable t) {
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

    //UTILITY SERVICES LOGIN
    public void getLoginDetail_utility(){
        pDialog=new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();
        String strToken= TokenBase64.getToken();
        LoginRequest Request = new LoginRequest();
        Request.setUserName(stringUserid);
        Request.setPasswd(stringPassword);
        Request.setSponsorFormNo(AppConstants.companyId);

        Call<BaseResponse> loginResponseEntityCall= NetworkClient_Utility.getInstance(LoginActivity.this).create(MainServices.class).fetchLogin(Request,strToken);

        loginResponseEntityCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {
                    BaseResponse Response = new BaseResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                            if(!Response.getRESP_VALUE().isEmpty() || !Response.getRESP_VALUE().equals("")){
                                String  detailEntity= Response.getRESP_VALUE();
                                LoginResponse loginDetailResponse = new Gson().fromJson(detailEntity, LoginResponse.class);
                                new LoginPreferences_Utility(LoginActivity.this).setLoggedinUser(loginDetailResponse);

                                Intent i=new Intent(LoginActivity.this, DashboardActivity.class);
                                // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();
                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                            }
                            else {
                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(LoginActivity.this, toast, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {
                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(LoginActivity.this, toast, Toast.LENGTH_SHORT).show();
                            //getBusinessLoginDetail();
                        }
                        else {
                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(LoginActivity.this, toast, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        String toast= "Something wrong.. ";
                        Toast.makeText(LoginActivity.this, toast, Toast.LENGTH_SHORT).show();
                        //loginApiCall_Cashback();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getBusinessLoginDetail(){
        pDialog=new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest Request = new BaseRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_LOGIN);
        Request.setPasswd(stringPassword);
        Request.setUserid(stringUserid);
        Request.setIslogin("N");

        Call<LoginResponseEntity> loginResponseEntityCall=
                NetworkClient.getInstance(LoginActivity.this).create(ProfileServices.class).fetchLogin(Request);


        loginResponseEntityCall.enqueue(new Callback<LoginResponseEntity>() {
            @Override
            public void onResponse(Call<LoginResponseEntity> call, Response<LoginResponseEntity> response) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    LoginResponseEntity Response =new LoginResponseEntity();
                    Response=response.body();
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {
                            SharedPrefrence_Business.setUserID(LoginActivity.this,edtxtUserId.getText().toString());
                            SharedPrefrence_Business.setPassword(LoginActivity.this,edtxtUserPass.getText().toString());
                            SharedPrefrence_Business.setUsername(LoginActivity.this,Response.getMname());
                            SharedPrefrence_Business.setUserMobileNumber(LoginActivity.this,Response.getMobileno());
                            SharedPrefrence_Business.setProfileIamge(LoginActivity.this,Response.getProfilepic());
                            SharedPrefrence_Business.setEmailId(LoginActivity.this,Response.getEmailid());
                            SharedPrefrence_Business.setIsActive(LoginActivity.this,Response.getIsactive());
                            SharedPrefrence_Business.setAddress(LoginActivity.this,Response.getAddress());
                            SharedPrefrence_Business.setIsLogin(LoginActivity.this,"Y");
                            SharedPrefrence_Business.setApiKey(LoginActivity.this,Response.getApikey());
                            SharedPrefrence_Business.setPackage(LoginActivity.this,Response.getPackagename());

                            //loginAPICall_shop();

                            Intent i=new Intent(LoginActivity.this, BusinessDashboardActivity.class);
                            startActivity(i);
                            finish();
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        }
                        else {

                            String toast= Response.getResponse()+ ": "+Response.getMsg();
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

                        String toast= Response.getResponse()+ ": "+Response.getMsg();
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
            public void onFailure(Call<LoginResponseEntity> call, Throwable t) {
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



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
