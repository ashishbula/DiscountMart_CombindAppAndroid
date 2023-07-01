package in.discountmart.utility_services.recharge.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.base.network.NetworkClient_Utility_1;
import in.base.util.PermissionUtil;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.call_api.MainServices;
import in.discountmart.utility_services.listener.AlertDialogButtonListener;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.request_model.MainBalanceRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.model.response_model.MainBalanceResponse;
import in.discountmart.utility_services.recharge.call_recharge_api.RechargeApi;
import in.discountmart.utility_services.recharge.fragment.LocationListFragment;
import in.discountmart.utility_services.recharge.recharge_model.recharge_request_model.CheckPostpaidIsbbpsRequest;
import in.discountmart.utility_services.recharge.recharge_model.recharge_request_model.CircleRequest;
import in.discountmart.utility_services.recharge.recharge_model.recharge_request_model.GetServiceProviderRequest;
import in.discountmart.utility_services.recharge.recharge_model.recharge_request_model.RechargeRequest;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.CheckPostpaidIsbbpsResponse;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.GetServiceProviderResponse;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.LocationResponse;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.RechargeResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.utilities.AlertDialogUtils;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class DatacardActivity extends AppCompatActivity implements LocationListFragment.Listener {
    public static final String TAG = "MainActivity";
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    public TextView txtOperator;
    public TextView txtCircle;
    public TextView txtWalletBal;
    public TextView txtSelectCircle;
    public TextView txtSelectOperator;
    EditText edTxtMobile;
    EditText edTxtAmount;
    LinearLayout layoutContact;
    LinearLayout layoutOperator;
    LinearLayout layoutCircle;
    LinearLayout layoutRecharge;

    Button btnRecharge;
    View view;
    ProgressDialog progressDialog;

    String strType="";
    String home="";

    /*Array List of Object*/
    ArrayList<GetServiceProviderResponse> serviceProviderArrayList;
    ArrayList<LocationResponse> circleList;

    /*For Get Contact from List*/
    ListView listView ;
    ArrayList<String> StoreContacts ;
    ArrayAdapter<String> arrayAdapter ;
    Cursor cursor ;

    public  static final int RequestPermissionCode  = 1 ;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private final int REQUEST_CODE=99;
    private static final int REQUEST_CONTACTS = 1;

    float mainBal=0;
    float totalPay=0;
    String strServiceType="";
    String strAccountNo="";
    String strAmount="";
    String strOpratorName="";
    String opratorName="";
    String opratorCircle="";
    String strOpratorCode="";
    String strIsbbps_Pre="";
    String strIsbbps_Post="";
    String strAction="";
    String strIpcode="";
    String strReferenceId="";
    String strCircle="";
    boolean operator;
    boolean circle;
    private static String[] PERMISSIONS_CONTACT = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_mobile_recharge);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            view=findViewById(android.R.id.content);
            collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.mobile_recharge_act_collasp_layout);
            toolbar=(Toolbar)findViewById(R.id.mobile_recharge_act_toolbar);
            txtOperator=(TextView)findViewById(R.id.mobile_recharge_act_txt_oprator);
            txtCircle=(TextView)findViewById(R.id.mobile_recharge_act_txt_circle);
            txtWalletBal=(TextView)findViewById(R.id.mobile_recharge_act_txt_balance);
            txtSelectCircle=(TextView)findViewById(R.id.mobile_recharge_act_txt_select_circle);
            txtSelectOperator=(TextView)findViewById(R.id.mobile_recharge_act_txt_select_oprator);
            edTxtAmount=(EditText)findViewById(R.id.mobile_recharge_act_edtxt_amount);
            edTxtMobile=(EditText)findViewById(R.id.mobile_recharge_act_edtxt_mobile);
            layoutContact=(LinearLayout)findViewById(R.id.mobile_recharge_act_layout_contact);
            layoutOperator=(LinearLayout)findViewById(R.id.mobile_recharge_act_layout_oprator);
            layoutCircle=(LinearLayout)findViewById(R.id.mobile_recharge_act_layout_circle);
            layoutRecharge=(LinearLayout)findViewById(R.id.mobile_recharge_act_layout_recharge);
            btnRecharge=(Button)findViewById(R.id.mobile_recharge_act_btn_recharge);

            setSupportActionBar(toolbar);

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Data Card");
            //txtSelectOperator.setText(getResources().getString(R.string.str_choose_oprator));
            //txtSelectCircle.setText(getResources().getString(R.string.str_choose_circle));

            /*Check permission request*/

            requestContactsPermissions();
            /*Get Intent*/
            Intent intent=getIntent();
            strType= intent.getStringExtra("Type");
            home= intent.getStringExtra("Home");

                strType="M";
                txtSelectOperator.setText(getResources().getString(R.string.str_choose_oprator));
                txtSelectCircle.setText(getResources().getString(R.string.str_choose_circle));

            /*Edit text mobile on text change listener*/
          /*  edTxtMobile.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                        if(!s.toString().contentEquals("") && s.toString().length() == 10){
                            strAccountNo=edTxtMobile.getText().toString();
                            View view = DatacardActivity.this.getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                            // mocking network delay for API call
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    getOperator_Circle();

                                }
                            }, 500);
                        }



                }
            });*/


            /*Call main wallet api*/
            if(!ConnectivityUtils.isNetworkEnabled(this)){
                Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.app_orange_light ))
                        .show();
            }
            else {
                getMainBalance();
            }

            /*Text Choose operator on click listener get list*/
            layoutOperator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!strType.equals("")){
                        if(!edTxtMobile.getText().toString().contentEquals("") && edTxtMobile.getText().toString().length() == 10){
                            Intent intent1=new Intent(DatacardActivity.this,ServiceProviderActivity.class);
                            intent1.putExtra("ServiceType",strType);
                            //intent1.putExtra("ServiceTypeId",strType);
                            intent1.putExtra("Home","false");
                            startActivityForResult(intent1, 1);
                            overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                        }
                        else {
                            Toast.makeText(DatacardActivity.this,"Please enter complete mobile no.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                }
            });
            final BottomSheetDialogFragment myBottomSheet = LocationListFragment.newInstance("Circle List");

            /*Text Choose Cicle on click listener get list*/
            layoutCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!strType.equals("")){
                        if(edTxtMobile.getText().toString().contentEquals("") && edTxtMobile.getText().toString().length() != 10){
                            Toast.makeText(DatacardActivity.this,"Please enter complete mobile no.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if(opratorName.equals("")){
                            Toast.makeText(DatacardActivity.this,"Please select operator",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {

                            myBottomSheet.show(getSupportFragmentManager(), myBottomSheet.getTag());
                            Intent intent1=new Intent(DatacardActivity.this, LocationListFragment.class);

                            /*Bundle bundle =new Bundle();
                            intent1.putExtra("ServiceType",strType);
                            //intent1.putExtra("ServiceTypeId",strType);
                            intent1.putExtra("Home","false");
                            startActivityForResult(intent1, 2);
                            overridePendingTransition(R.anim.slide_up, R.anim.slide_down);*/
                        }


                    }
                }
            });


            /*Image Contact on click get contact list*/
            layoutContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showContacts(v);

                }
            });

            /*Button Recharge on click*/
            btnRecharge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Radio Prepaid is checked*/
                    if(strType.contentEquals("M")){
                       /* if(txtOperator.getText().toString().equals(getResources().getString(R.string.str_choose_oprator))){
                            Toast.makeText(MobileRechargeActivity.this,"Please choose service operator",Toast.LENGTH_SHORT).show();
                        }*/
                         if(edTxtMobile.getText().toString().equals("")){
                            edTxtMobile.setError("Enter Mobile Number");
                            edTxtMobile.requestFocus();
                        }
                        else if(edTxtAmount.getText().toString().equals("")){
                            edTxtAmount.setError("Enter Amount of Recharge");
                            edTxtAmount.requestFocus();
                        }
                        else if(txtOperator.getText().equals("")){
                             Toast.makeText(DatacardActivity.this,"Please choose service operator",Toast.LENGTH_SHORT).show();
                         }
                         else if(txtCircle.getText().equals("")){
                             Toast.makeText(DatacardActivity.this,"Please choose circle",Toast.LENGTH_SHORT).show();
                         }
                        else {

                            if(edTxtMobile.getText().toString().length() < 10 || edTxtMobile.getText().toString().length() > 10){
                                Toast.makeText(DatacardActivity.this,"Please check the number ",Toast.LENGTH_SHORT).show();

                            }

                            else {

                                //float discoutamt= Float.parseFloat(discount);
                                //strOpratorName=txtOperator.getText().toString();
                                strAmount=edTxtAmount.getText().toString();
                                strAccountNo=edTxtMobile.getText().toString();
                                strCircle=txtCircle.getText().toString();
                                strOpratorName=txtOperator.getText().toString();

                                totalPay= Float.parseFloat(edTxtAmount.getText().toString());
                                float totCheckBal= mainBal;
                                //if(totCheckBal >= totalPay){

                                if(!ConnectivityUtils.isNetworkEnabled(DatacardActivity.this)){
                                    Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(R.color.app_orange_light ))
                                            .show();
                                }
                                else {
                                    if(!strIsbbps_Pre.equals("")){
                                        if(strType.equals("M") && strIsbbps_Pre.equals("false")){

                                            /*Call api*/
                                            /*Send Data in next check final detail activity*/
                                            RechargeRequest();
                                        }
                                        else {
                                            strIsbbps_Pre="false";
                                            RechargeRequest();
                                        }
                                    }
                                    else {
                                        strIsbbps_Pre="false";
                                        RechargeRequest();
                                    }

                                }


                            }


                        }
                    }
                    /*Radio Postpaid is checked*/
                    else {

                        if(edTxtMobile.getText().toString().equals("")){
                            edTxtMobile.setError("Enter Mobile Number");
                            edTxtMobile.requestFocus();
                        }
                       else if(txtSelectOperator.getText().toString().equals(getResources().getString(R.string.str_choose_oprator))){
                            Toast.makeText(DatacardActivity.this,"Please choose service operator",Toast.LENGTH_SHORT).show();
                        }
                        else if(txtSelectCircle.getText().toString().equals(getResources().getString(R.string.str_choose_circle))){
                            Toast.makeText(DatacardActivity.this,"Please choose circle",Toast.LENGTH_SHORT).show();
                        }

                        else if(edTxtAmount.getText().toString().equals("")){
                            edTxtAmount.setError("Enter Amount of Recharge");
                            edTxtAmount.requestFocus();
                        }
                        else {

                            if(edTxtMobile.getText().toString().length() < 10 || edTxtMobile.getText().toString().length() > 10){
                                Toast.makeText(DatacardActivity.this,"Please check the number ",Toast.LENGTH_SHORT).show();

                            }

                            else {

                                //float discoutamt= Float.parseFloat(discount);
                                strOpratorName=txtSelectOperator.getText().toString();
                                strAmount=edTxtAmount.getText().toString();
                                strAccountNo=edTxtMobile.getText().toString();
                                strCircle=txtSelectCircle.getText().toString();
                                totalPay= Float.parseFloat(edTxtAmount.getText().toString());
                                float totCheckBal= mainBal;
                                //if(totCheckBal >= totalPay){

                                if(!ConnectivityUtils.isNetworkEnabled(DatacardActivity.this)){
                                    Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(R.color.app_orange_light ))
                                            .show();
                                }
                                else {
                                    if(!strIsbbps_Post.equals("")){

                                        if(strType.equals("T") && strIsbbps_Post.equals("false")){

                                            int postAmount= Integer.parseInt(strAmount);
                                            if(postAmount < 50 ){
                                                AlertDialogUtils.showDialog(DatacardActivity.this,
                                                        "Alert Dialog","Minimum amount should be Rs.50");
                                            }
                                            else {
                                                /*Call api*/
                                                RechargeRequest();
                                            }
                                        }
                                        else if(strType.equals("T") && strIsbbps_Post.equals("true")){

                                            int postAmount= Integer.parseInt(strAmount);
                                            if(postAmount < 50 ){
                                                AlertDialogUtils.showDialog(DatacardActivity.this,
                                                        "Alert Dialog","Minimum amount should be Rs.50");
                                            }
                                            else {
                                                /*Call api*/
                                                /*First check isbbps is true for postpaid*/
                                                //getCheckPostpaidIsbbps(strAccountNo);
                                                RechargeRequest();

                                            }

                                        }

                                    }

                                }


                            }

                        }
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


        collapsingToolbarLayout.setTitle("Datacard Recharge");
        //collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.coll_toolbar_title);
        //collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.exp_toolbar_title);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

       /* if (requestCode == REQUEST_CAMERA) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result for camera permission.
            Log.i(TAG, "Received response for Camera permission request.");

            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
                Log.i(TAG, "CAMERA permission has now been granted. Showing preview.");
                Snackbar.make(mLayout, R.string.permision_available_camera,
                        Snackbar.LENGTH_SHORT).show();
            } else {
                Log.i(TAG, "CAMERA permission was NOT granted.");
                Snackbar.make(mLayout, R.string.permissions_not_granted,
                        Snackbar.LENGTH_SHORT).show();

            }
            // END_INCLUDE(permission_result)

        }*/  if (requestCode == REQUEST_CONTACTS) {
            Log.i(TAG, "Received response for contact permissions request.");

            // We have requested multiple permissions for contacts, so all of them need to be
            // checked.
            if (PermissionUtil.verifyPermissions(grantResults)) {
                // All required permissions have been granted, display contacts fragment.
                /*Snackbar.make(view, R.string.permision_available_contacts,
                        Snackbar.LENGTH_SHORT)
                        .show();*/
            } else {
                Log.i(TAG, "Contacts permissions were NOT granted.");
                Snackbar.make(view, R.string.permissions_not_granted,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

            if(reqCode == REQUEST_CODE){
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String num = "";
                            if (Integer.valueOf(hasNumber) == 1) {
                            Cursor numbers = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                            while (numbers.moveToNext()) {
                            num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            //Toast.makeText(MobileRechargeActivity.this, "Number="+num, Toast.LENGTH_LONG).show();
                                }
                                if(num.startsWith("+"))
                                {
                                    if(num.length()==13)
                                    {
                                        String str_getMOBILE=num.substring(3);
                                        edTxtMobile.setText(str_getMOBILE);
                                    }
                                    else if(num.length()==14)
                                    {
                                        String str_getMOBILE=num.substring(4);
                                        edTxtMobile.setText(str_getMOBILE);
                                    }


                                }
                                else if(num.length() == 11)
                                {
                                    String str_getMOBILE=num.substring(1);
                                    edTxtMobile.setText(str_getMOBILE);
                                }
                                else {
                                    edTxtMobile.setText(num);
                                }
                        //edTxtMobile.setText(get_Mo);
                            }
                            else {

                            }
                    }
                }
                else if(resultCode== RESULT_CANCELED) {

                }
            }
            else if(reqCode==1){
                if(resultCode == Activity.RESULT_OK){
                    Bundle bundle=data.getExtras();
                    serviceProviderArrayList=new ArrayList<GetServiceProviderResponse>();
                    serviceProviderArrayList= (ArrayList<GetServiceProviderResponse>) bundle.getSerializable("ServiceMobProviderList");

                    if(serviceProviderArrayList != null && serviceProviderArrayList.size() > 0){
                        for (int i=0;i < serviceProviderArrayList.size();i++){
                            //txtOperator.setText(serviceProviderArrayList.get(i).getCompanyName());

                            strIpcode=serviceProviderArrayList.get(i).getIPcode();
                            strOpratorCode=serviceProviderArrayList.get(i).getCode();
                            strOpratorName=serviceProviderArrayList.get(i).getCompanyName();
                            strIsbbps_Pre=serviceProviderArrayList.get(i).getIsBBPS();
                            strIsbbps_Post=serviceProviderArrayList.get(i).getIsBBPS();

                                txtOperator.setText("");
                                opratorName=serviceProviderArrayList.get(i).getCompanyName();
                                txtOperator.setText(serviceProviderArrayList.get(i).getCompanyName());
                                txtSelectOperator.setText(getResources().getString(R.string.str_change_operator));


                        }
                    }
                }
                else if(resultCode== RESULT_CANCELED) {

                }
            }
            else if(reqCode==2){
                if(resultCode == Activity.RESULT_OK){
                    Bundle bundle=data.getExtras();
                    circleList=new ArrayList<LocationResponse>();
                    circleList= (ArrayList<LocationResponse>) bundle.getSerializable("CircleList");

                    if(circleList != null && circleList.size() > 0){
                        for (int i=0;i < circleList.size();i++){
                            //txtOperator.setText(serviceProviderArrayList.get(i).getCompanyName());

                            opratorCircle= circleList.get(i).getLocation();

                             //txtOperator.setText("");
                                txtCircle.setText(opratorCircle);
                                txtSelectCircle.setText(getResources().getString(R.string.str_change_circle));
                        }
                    }
                }
                else if(resultCode== RESULT_CANCELED) {

                }
            }


    }

    /* Request and Response Get Main Balance*/
    public void getMainBalance(){
        try {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String strApiRequest="";
            JSONObject object=null;
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(this).getLoggedinUser();
            String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);
            String formno=loginResponse.getFormNo().substring(0,loginResponse.getFormNo().length()-2);
            String mobile=loginResponse.getMobileNo().substring(0,loginResponse.getMobileNo().length()-2);

            String strToken= TokenBase64.getToken();

            /*Main Request Model*/
            ApiRequest apiRequest = new ApiRequest();

            try {
                /*Base Request Model*/
                BaseHeaderRequest headerRequest =new BaseHeaderRequest();
                headerRequest.setUserName(  new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
                headerRequest.setPassword( new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
                headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/
                MainBalanceRequest request=new MainBalanceRequest();
                request.setFormNo(formno);

                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(request);

                strApiRequest=new Gson().toJson(apiRequest);

                Log.e("Value",strApiRequest);
            }catch (Exception e){
                e.printStackTrace();
            }

            Call<BaseResponse> fetchFlightBook=
                    NetworkClient_Utility_1.getInstance(this).create(MainServices.class).fetchGetBalance(apiRequest,strToken);

            fetchFlightBook.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    try {

                        BaseResponse Response =new BaseResponse();
                        Response=response.body();

                        if(Response != null){
                            if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                if(!Response.getRESP_VALUE().equals("null") || ! Response.getRESP_VALUE().equals("")){

                                    MainBalanceResponse balanceResponse=
                                            new Gson().fromJson(Response.getRESP_VALUE(),MainBalanceResponse.class);

                                    //String toast= Response.getRESP_MSG();
                                    //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    if(balanceResponse != null){
                                        // get Main Wallet balance
                                        new LoginPreferences_Utility(DatacardActivity.this).getLoggedinUser().setBalance(balanceResponse.getBalance());
                                        mainBal= Float.parseFloat(balanceResponse.getBalance());
                                        txtWalletBal.setText(getResources().getString(R.string.rs_symbol)+ " "+String.valueOf(mainBal));
                                    }

                                }
                                else if(Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")){
                                    String toast= Response.getRESP_MSG();

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

                            else if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")){
                                String toast= Response.getRESPONSE()+ "\n" + "Please Try Again ";
                                //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                        .show();
                            }

                            else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                Toast.makeText(DatacardActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(DatacardActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                            else {
                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(DatacardActivity.this, toast, Toast.LENGTH_SHORT).show();
                                // showSnackbar(toast);

                            }

                        }

                        else {
                            String toast = "something wrong..";
                            //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
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




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* Request and Response Get Operator and Circle*/
   /* public void getOperator_Circle(){
        try {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();


            Call<GetOperatorResponse> fetchFlightBook=
                    NetworkClient_Utility_1.getInstance(this).createCatalogue(RechargeApi.class).fetchOperator("lookup/"+edTxtMobile.getText().toString());

            fetchFlightBook.enqueue(new Callback<GetOperatorResponse>() {
                @Override
                public void onResponse(Call<GetOperatorResponse> call, Response<GetOperatorResponse> response) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    try {

                        GetOperatorResponse Response =new GetOperatorResponse();
                        Response=response.body();


                        if(Response != null){

                            if(Response.getCircle() != null && Response.getOperator() != null) {
                                if(!Response.getCircle().contentEquals("") && !Response.getOperator().contentEquals("")){
                                    //txtOperator_circle.setText(Response.getOperator() + "," +Response.getCircle());

                                    opratorName=Response.getOperator();
                                    opratorCircle=Response.getCircle();
                                    // call service operator list api
                                    getServiceProviderList();
                                }
                                else {
                                    Toast.makeText(DatacardActivity.this,"Data not found",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                if(Response.getStatus() != null && Response.getMsg() != null) {
                                    if(Response.getStatus().contentEquals("error") && !Response.getMsg().contentEquals("")){
                                        txtOperator.setText(Response.getStatus() + "," +Response.getMsg());
                                    }
                                    else {

                                        Toast.makeText(DatacardActivity.this,"Something error",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                        }
                        else {
                            Snackbar.make(view, "Something error", Snackbar.LENGTH_LONG)
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
                public void onFailure(Call<GetOperatorResponse> call, Throwable t) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
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




        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    /* Request and Response Recharge Api*/
    public void getRecharge(){
        try {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String strApiRequest="";
            JSONObject object=null;
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(this).getLoggedinUser();
            String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);
            String formno=loginResponse.getFormNo().substring(0,loginResponse.getFormNo().length()-2);
            String mobile=loginResponse.getMobileNo().substring(0,loginResponse.getMobileNo().length()-2);

            String strToken= TokenBase64.getToken();

            /*Main Request Model*/
            ApiRequest apiRequest = new ApiRequest();

            try {
                /*Base Request Model*/
                BaseHeaderRequest headerRequest =new BaseHeaderRequest();
                headerRequest.setUserName(  new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
                headerRequest.setPassword( new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
                headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/
                RechargeRequest request=new RechargeRequest();
                request.setAccountNo(strAccountNo);

                if(strType.equals("M")){
                    request.setAction("PREPAID");
                    request.setReferenceId(strReferenceId);
                    request.setIsBBPS(strIsbbps_Pre);
                }

                else if(strType.equals("T")){
                    request.setAction("POSTPAID");
                    request.setReferenceId(strReferenceId);
                    request.setIsBBPS(strIsbbps_Post);
                }


                request.setAmount(strAmount);
                request.setFormno(formno);
                request.setIpCode(strIpcode);

                request.setOperatorName(strOpratorName);
                request.setOpratorCode(strOpratorCode);
                request.setServiceType(strType);

                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(request);

                strApiRequest=new Gson().toJson(apiRequest);

                Log.e("Value",strApiRequest);
            }catch (Exception e){
                e.printStackTrace();
            }

            Call<BaseResponse> fetchRecharge=
                    NetworkClient_Utility_1.getInstance(this).create(RechargeApi.class).fetchRechargeRequest(apiRequest,strToken);

            fetchRecharge.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    try {

                        BaseResponse Response =new BaseResponse();
                        Response=response.body();

                        if(Response != null){
                            if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                if(!Response.getRESP_VALUE().equals("null") || ! Response.getRESP_VALUE().equals("")){

                                    /*Call main balance api*/
                                    getMainBalance();

                                    RechargeResponse rechargeResponse=
                                            new Gson().fromJson(Response.getRESP_VALUE(),RechargeResponse.class);

                                    if(rechargeResponse != null ){
                                        if(rechargeResponse.getStatus().equals("SUCCESS")){

                                            Intent intent=new Intent(DatacardActivity.this,RechargeSucccessMsgActivity.class);
                                            Bundle bundle=new Bundle();
                                            bundle.putString("Mobile",rechargeResponse.getAccountNo());
                                            bundle.putString("Amount",rechargeResponse.getAmount());
                                            bundle.putString("Status",rechargeResponse.getStatus());
                                            bundle.putString("OpId",rechargeResponse.getOperatorID());
                                            bundle.putString("TId",rechargeResponse.getTID());
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);


                                        }
                                        else {

                                            String toast=rechargeResponse.getStatus()+ " "+ rechargeResponse.getRESP_MSG();
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

                                }
                                else if(Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")){
                                    String toast= Response.getRESP_MSG();

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
                            else if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")){
                                String toast= Response.getRESPONSE()+ "\n" + "Please Try Again ";
                                //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                        .show();
                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            Snackbar.make(view, "Somthing Error from server / other ", Snackbar.LENGTH_LONG)
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
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
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




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* Request and Response Service Provider List*/
    public void getServiceProviderList() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String strApiRequest = "";
        JSONObject object = null;
        String strToken = TokenBase64.getToken();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse = new LoginPreferences_Utility(this).getLoggedinUser();
        String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);

        ApiRequest apiRequest = new ApiRequest();
        try {

            /*Main Request Model*/

            BaseHeaderRequest headerRequest = new BaseHeaderRequest();
            headerRequest.setUserName(loginResponse.getUserName());
            headerRequest.setPassword(loginResponse.getPasswd());
            headerRequest.setSponsorFormNo(companyId);
//            if(loginResponse.getMemMode().equals("D"))
//                headerRequest.setSponsorFormNo(companyId);
//            else
//                headerRequest.setSponsorFormNo("");

            /*Getcity List Request Model*/
            GetServiceProviderRequest providerRequest = new GetServiceProviderRequest();
            providerRequest.setService(strType);


            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(providerRequest);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<BaseResponse> fetchServiceProviderListCall =
                NetworkClient_Utility_1.getInstance(this).create(RechargeApi.class).fetchServiceList(apiRequest, strToken);

        fetchServiceProviderListCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    BaseResponse Response = new BaseResponse();
                    Response = response.body();

                    if (Response != null) {
                        if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                            if (Response.getRESP_VALUE().equals("") || Response.getRESP_VALUE().isEmpty()) {

                                String toast = Response.getRESP_MSG();
                                //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();
                            }
                            else if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                String[] serviceListResponse = Response.getRESPONSE().split("");
                                if (serviceListResponse.length > 0) {

                                    GetServiceProviderResponse[] serviceList = new Gson().fromJson(Response.getRESP_VALUE(), GetServiceProviderResponse[].class);
                                    List<GetServiceProviderResponse> serviceArrayList = new ArrayList<GetServiceProviderResponse>(Arrays.asList(serviceList));
                                    serviceProviderArrayList=new ArrayList<GetServiceProviderResponse>();
                                    serviceProviderArrayList.clear();
                                    serviceProviderArrayList.addAll(serviceArrayList);
                                    // call Circle List
                                    getCircleList();


                                    /*if(serviceArrayList.size() > 0) {
                                        for (int i = 0; i < serviceArrayList.size(); i++) {

                                            if(opratorName.equals(serviceArrayList.get(i).getCompanyName())){
                                                //txtOperator.setText(serviceArrayList.get(i).getCompanyName());
                                                txtOperator_circle.setText(opratorName + "," +opratorCircle);
                                                strIpcode = serviceArrayList.get(i).getIPcode();
                                                strOpratorCode = serviceArrayList.get(i).getCode();
                                                strOpratorName = serviceArrayList.get(i).getCompanyName();
                                                strIsbbps_Pre = serviceArrayList.get(i).getIsBBPS();
                                                strIsbbps_Post = serviceArrayList.get(i).getIsBBPS();
                                            }


                                        }

                                    }*/

                                } else {
                                    String toast = "Operator list empty";
                                    Toast.makeText(DatacardActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    //showSnackbar(toast);
                                }
                            }
                        }
                        else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();

                        }

                        else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                            String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                            Toast.makeText(DatacardActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(DatacardActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                        else {
                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(DatacardActivity.this, toast, Toast.LENGTH_SHORT).show();
                            // showSnackbar(toast);

                        }
                    }
                    else {
                        //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                        String toast = "something wrong";
                        Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                // Toast.makeText(FlightCityListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });
    }

    /* Request and Response Service Provider List*/
    public void getCircleList() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        CircleRequest request = new CircleRequest();
        try {
            String authkey="";
            String clientID="";
            String serviceTpe="";
            request.setAuthKey(authkey);
            request.setClientID(clientID);
            request.setServiceType(serviceTpe);

            String strApiRequest = new Gson().toJson(request);

            Timber.e(strApiRequest,"CircleListRequest");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<BaseResponse> fetchServiceProviderListCall =
                NetworkClient_Utility_1.getInstance(this).create(RechargeApi.class).fetchCircleList(request);

        fetchServiceProviderListCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    BaseResponse Response = new BaseResponse();
                    Response = response.body();

                    if (Response != null) {
                        if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                            if (Response.getRESP_VALUE().equals("") || Response.getRESP_VALUE().isEmpty()) {

                                String toast = Response.getRESP_MSG();
                                //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();
                            }
                            else if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                String[] serviceListResponse = Response.getRESPONSE().split("");
                                if (serviceListResponse.length > 0) {
                                    LocationResponse[] serviceList = new Gson().fromJson(Response.getRESP_VALUE(), LocationResponse[].class);
                                    List<LocationResponse> serviceArrayList = new ArrayList<LocationResponse>(Arrays.asList(serviceList));
                                    circleList=new ArrayList<LocationResponse>();
                                    // adding contacts to contacts list
                                    circleList.clear();
                                    circleList.addAll(serviceArrayList);

                                     if(serviceProviderArrayList.size() > 0 && circleList.size() > 0) {
                                        for (int i = 0; i < serviceProviderArrayList.size(); i++) {

                                            if(opratorName.equals(serviceProviderArrayList.get(i).getCompanyName())){
                                                //txtOperator.setText(serviceArrayList.get(i).getCompanyName());
                                                //txtOperator_circle.setText(opratorName + "," +opratorCircle);
                                                strIpcode = serviceProviderArrayList.get(i).getIPcode();
                                                strOpratorCode = serviceProviderArrayList.get(i).getCode();
                                                strOpratorName = serviceProviderArrayList.get(i).getCompanyName();
                                                strIsbbps_Pre = serviceProviderArrayList.get(i).getIsBBPS();
                                                strIsbbps_Post = serviceProviderArrayList.get(i).getIsBBPS();
                                                operator=true;
                                            }


                                        }
                                        for (int j=0; j < circleList.size(); j++){
                                            if(opratorCircle.equals(circleList.get(j).getLocation())){
                                                strCircle=circleList.get(j).getLocation();
                                                circle=true;

                                            }
                                        }

                                        if(operator && circle){
                                            txtOperator.setText(opratorName);
                                            txtCircle.setText(opratorCircle);
                                            txtSelectOperator.setText(getResources().getString(R.string.str_change_operator));
                                            txtSelectCircle.setText(getResources().getString(R.string.str_change_circle));
                                        }

                                    }

                                    // refreshing recycler view
                                    //adapter.notifyDataSetChanged();
                                } else {
                                    String toast = " City List empty";
                                    Toast.makeText(DatacardActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    //showSnackbar(toast);
                                }
                            }
                        }
                        else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();

                        }
                        else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                            String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                            Toast.makeText(DatacardActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(DatacardActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(DatacardActivity.this, toast, Toast.LENGTH_SHORT).show();
                            // showSnackbar(toast);

                        }
                    }
                    else {
                        //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                        String toast = "something wrong";
                        Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                // Toast.makeText(FlightCityListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });
    }

    /* Request and Response Check Postpaid isbbps*/
    public void getCheckPostpaidIsbbps(String mob){
        try {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String strApiRequest="";
            JSONObject object=null;
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(this).getLoggedinUser();
            String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);
            String formno=loginResponse.getFormNo().substring(0,loginResponse.getFormNo().length()-2);
            String mobile=loginResponse.getMobileNo().substring(0,loginResponse.getMobileNo().length()-2);

            String strToken= TokenBase64.getToken();

            /*Main Request Model*/
            ApiRequest apiRequest = new ApiRequest();

            try {
                /*Base Request Model*/
                BaseHeaderRequest headerRequest =new BaseHeaderRequest();
                headerRequest.setUserName(  new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
                headerRequest.setPassword( new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
                headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/
                CheckPostpaidIsbbpsRequest request=new CheckPostpaidIsbbpsRequest();
                request.setAccountNo(mob);

                request.setAmount(strAmount);
                request.setIpCode(strIpcode);


                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(request);

                strApiRequest=new Gson().toJson(apiRequest);

                Log.e("Value",strApiRequest);
            }catch (Exception e){
                e.printStackTrace();
            }

            Call<BaseResponse> fetchCheckPostpaidIsbbpsCall=
                    NetworkClient_Utility_1.getInstance(this).create(RechargeApi.class).fetchCheckPostpaidIsbbpsRequest(apiRequest,strToken);

            fetchCheckPostpaidIsbbpsCall.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    try {

                        BaseResponse Response =new BaseResponse();
                        Response=response.body();

                        if(Response != null){
                            if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                if(!Response.getRESP_VALUE().equals("null") || ! Response.getRESP_VALUE().equals("")){

                                    CheckPostpaidIsbbpsResponse checkResponse=
                                            new Gson().fromJson(Response.getRESP_VALUE(),CheckPostpaidIsbbpsResponse.class);
                                    CheckPostpaidIsbbpsResponse.CheckData checkData=new CheckPostpaidIsbbpsResponse.CheckData();

                                    if( checkResponse != null){

                                        checkData= checkResponse.getData();

                                        if(checkData.getDueamount()!=null && checkData.getDuedate() != null){
                                            if(!checkData.getDueamount().equals("") && checkData.getDueamount().equals(strAmount)){
                                                edTxtAmount.setEnabled(false);
                                                strReferenceId=checkData.getReference_id();

                                                    /*Call Api*/
                                                    // getRecharge();
                                                    //showRechargeDialog();
                                                RechargeRequest();


                                            }
                                            else {
                                                edTxtAmount.setEnabled(true);
                                                Toast.makeText(DatacardActivity.this,"Get Dues is blank",Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        else {
                                            String toast=checkResponse.getStatus()+ "\n Please check mobile no. or operator";
                                           Toast.makeText(DatacardActivity.this,
                                                   toast,Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                    else {
                                        Toast.makeText(DatacardActivity.this,
                                                "Something went wrong",Toast.LENGTH_SHORT).show();
                                    }

                                }
                                else if(Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("") ||Response.getRESP_VALUE() ==null){
                                    String toast= Response.getRESP_MSG();

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
                            else if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")){
                                String toast= Response.getRESPONSE()+ "\n" + "Please Try Again ";
                                //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                        .show();
                            }

                            else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                Toast.makeText(DatacardActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(DatacardActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }

                            else {
                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(DatacardActivity.this, toast, Toast.LENGTH_SHORT).show();
                                // showSnackbar(toast);

                            }
                        }
                        else {
                            Snackbar.make(view, "Something wrong..", Snackbar.LENGTH_LONG)
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
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
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




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void showRechargeDialog(){
        try{
           /* StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("Amount of :- " + strAmount);
            sb.append("\n");
            sb.append("Mobile no:- " + strAccountNo);
            sb.append("\n");
            sb.append("Operator of:- " + strOpratorName);*/
            String alert1 = "Amount of : " + strAmount;
            String alert2 = "Mobile no: " + strAccountNo;
            String alert3 = "Operator of: " + strOpratorName;

            String messageText = "Do you want to continue to recharge."+
                    "\n\n"+alert1+
                    "\n"+alert2+
                    "\n"+alert3;

            AlertDialogUtils.showAlertdialogContext(DatacardActivity.this, new AlertDialogButtonListener() {
                @Override
                public void onButtontapped(String btnText) {
                    if(btnText.equalsIgnoreCase("YES")){

                        /*Call recharge api*/
                        //getRecharge();
                        //RechargeRequest();
                    }
                }
            },"Recharge",messageText);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void showCheckIsbbsTrueDialog(){
        try{
           /* StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("Amount of :- " + strAmount);
            sb.append("\n");
            sb.append("Mobile no:- " + strAccountNo);
            sb.append("\n");
            sb.append("Operator of:- " + strOpratorName);*/
            String alert1 = "Amount of : " + strAmount;
            String alert2 = "Mobile no: " + strAccountNo;
            String alert3 = "Operator of: " + strOpratorName;

            String messageText = "Do you want to continue to recharge."+
                    "\n\n"+alert1+
                    "\n"+alert2+
                    "\n"+alert3;

            AlertDialogUtils.showAlertdialogContext(DatacardActivity.this, new AlertDialogButtonListener() {
                @Override
                public void onButtontapped(String btnText) {
                    if(btnText.equalsIgnoreCase("YES")){

                        /*Call recharge api*/
                        //getCheckPostpaidIsbbps();
                    }
                }
            },"Recharge",messageText);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*SEnd Request in next Activity*/
    public void RechargeRequest(){
        LoginResponse loginResponse=new LoginResponse();
        loginResponse=new LoginPreferences_Utility(this).getLoggedinUser();
        String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);
        String formno=loginResponse.getFormNo().substring(0,loginResponse.getFormNo().length()-2);
        String mobile=loginResponse.getMobileNo().substring(0,loginResponse.getMobileNo().length()-2);

        String strToken= TokenBase64.getToken();

        /*Main Request Model*/
        ApiRequest apiRequest = new ApiRequest();


            /*Base Request Model*/
            BaseHeaderRequest headerRequest =new BaseHeaderRequest();
            headerRequest.setUserName(  new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
            headerRequest.setPassword( new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
            headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/
            RechargeRequest request=new RechargeRequest();
            request.setAccountNo(strAccountNo);

            if(strType.equals("M")){
                request.setAction("PREPAID");
                request.setReferenceId(strReferenceId);
                request.setIsBBPS(strIsbbps_Pre);
            }

            else if(strType.equals("T")){
                request.setAction("POSTPAID");
                request.setReferenceId(strReferenceId);
                request.setIsBBPS(strIsbbps_Post);
            }


            request.setAmount(strAmount);
            request.setFormno(formno);
            request.setIpCode(strIpcode);

            request.setOperatorName(strOpratorName);
            request.setOpratorCode(strOpratorCode);
            request.setServiceType(strType);
            request.setLocation(strCircle);

            String strRequest =new Gson().toJson(request);
            Log.d("Request :-", strRequest);



        Intent intent=new Intent(DatacardActivity.this, CheckFinalDetailRechargeActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("RechargeRequest",request);

        bundle.putString("ServiceType",strType);
        bundle.putString("Operator",strOpratorName);
        bundle.putString("Amount",strAmount);
        bundle.putString("MobileNo",strAccountNo);
        bundle.putString("ServiceId",strServiceType);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
    }

    public void showContacts(View v) {
        Log.i(TAG, "Show contacts button pressed. Checking permissions.");

        // Verify that all required contact permissions have been granted.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Contacts permissions have not been granted.
            Log.i(TAG, "Contact permissions has NOT been granted. Requesting permissions.");
            requestContactsPermissions();

        } else {

            // Contact permissions have been granted. Show the contacts fragment.
            Log.i(TAG,
                    "Contact permissions have already been granted. Displaying contact details.");
            showContactList();
        }
    }

    private void requestContactsPermissions() {
        // BEGIN_INCLUDE(contacts_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_CONTACTS)) {

            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.
            Log.i(TAG, "Displaying contacts permission rationale to provide additional context.");

            // Display a SnackBar with an explanation and a button to trigger the request.
            Snackbar.make(view, R.string.permission_contacts_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat
                                    .requestPermissions(DatacardActivity.this, PERMISSIONS_CONTACT,
                                            REQUEST_CONTACTS);
                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary ))
                    .show();
        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
        }
        // END_INCLUDE(contacts_permission_request)
    }

    private void showContactList() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onItemSelect(LocationResponse item) {
        try {
            if(item != null){

                    txtCircle.setText(item.getLocation());
                    strCircle=item.getLocation();
                    txtSelectCircle.setText(getResources().getString(R.string.str_change_circle));



            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
