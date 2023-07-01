package in.discountmart.business.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import in.base.network.NetworkClient;
import in.base.network.NetworkClient1;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import in.discountmart.R;
import in.discountmart.business.adapter.ComplaintListAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.ComplaintServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.ComplaintRequest;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.model_business.responsemodel.ComplaintTypeListResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintActivity extends AppCompatActivity {

    EditText edtxtSellerId;
    EditText edtxtName;
    EditText edtxtMail;
    EditText edtxtMobile;
    Spinner spinComptype;
    EditText edTxtSubject;
    EditText edTxtDescription;
    Button btnSubmit;
    Button btnHome;
    TextView txtMsg;
    LinearLayout layoutMsg;
    LinearLayout layoutItm;
    LinearLayout layoutMain;
    TextInputLayout txtInputLayoutId;
    TextInputLayout txtInputLayoutName;
    TextInputLayout txtInputLayoutMail;
    TextInputLayout txtInputLayoutMobile;

    Context context;

    String strComplaint="";
    String strComplaintId="";
    String strSellerId="";
    String strName="";
    String strMobile="";
    String strEmail="";
    String strSubject="";
    String strDescription="";

    ArrayList<ComplaintTypeListResponse.ComplaintList> complaintLists;
    ComplaintTypeListResponse.ComplaintList complaintList[];
    Call<BaseResponse> callSaveComplaint;

    ComplaintListAdapter adapter;
    ProgressDialog progressDialog;
    View view;
    String strApiKey="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_activity_complaint);

        view=findViewById(android.R.id.content);
        try{

            context=ComplaintActivity.this;


            //for Action Bar
            // Enabling Up / Back navigation
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Complaint");

            edtxtSellerId=(EditText) findViewById(R.id.complaint_activity_edTxt_sellerId);
            edtxtName=(EditText) findViewById(R.id.complaint_activity_edTxt_membername);
            edtxtMobile=(EditText) findViewById(R.id.complaint_activity_edTxt_mobile);
            edtxtMail=(EditText) findViewById(R.id.complaint_activity_edTxt_email);
            spinComptype=(Spinner)findViewById(R.id.complaint_activity_spinner_compaint);
            edTxtSubject=(EditText)findViewById(R.id.complaint_activity_edtxt_subject);
            edTxtDescription=(EditText)findViewById(R.id.complaint_activity_edtxt_description);
            btnSubmit=(Button)findViewById(R.id.complaint_activity_btn_submit);
            btnHome=(Button)findViewById(R.id.complaint_activity_btn_home);
            txtMsg=(TextView) findViewById(R.id.complaint_activity_txtview_msg);
            layoutMsg=(LinearLayout) findViewById(R.id.complaint_activity_layout_msg);
            layoutItm=(LinearLayout) findViewById(R.id.complaint_activity_layout_item);
            layoutMain=(LinearLayout) findViewById(R.id.complaint_activity_layout_main);
            txtInputLayoutId=(TextInputLayout) findViewById(R.id.complaint_activity_TxtInput_sellerId);
            txtInputLayoutMail=(TextInputLayout) findViewById(R.id.complaint_activity_TxtInput_email);
            txtInputLayoutName=(TextInputLayout) findViewById(R.id.complaint_activity_TxtInput_name);
            txtInputLayoutMobile=(TextInputLayout) findViewById(R.id.complaint_activity_TxtInput_mobile);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(this);

            if(SharedPrefrence_Business.getUserID(context).equals("")){
                edtxtSellerId.setEnabled(true);
               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    txtInputLayoutId.setBackground(getResources().getDrawable(R.drawable.white_bg_box_round_corner));
                }*/
            }
            else {
                edtxtSellerId.setEnabled(false);
                edtxtSellerId.setText(SharedPrefrence_Business.getUserID(context));
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    txtInputLayoutId.setBackground(getResources().getDrawable(R.drawable.gray_bg_box));
                }*/
            }
            if(SharedPrefrence_Business.getUsername(context).equals("")){
                edtxtName.setEnabled(true);
               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    txtInputLayoutName.setBackground(getResources().getDrawable(R.drawable.white_bg_box_round_corner));
                }*/
            }
            else {
                edtxtName.setEnabled(false);
                edtxtName.setText(SharedPrefrence_Business.getUsername(context));
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    txtInputLayoutName.setBackground(getResources().getDrawable(R.drawable.gray_bg_box));
                }*/
            }

            if(SharedPrefrence_Business.getUserMobileNumber(context).equals("")){
                edtxtMobile.setEnabled(true);
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    txtInputLayoutMobile.setBackground(getResources().getDrawable(R.drawable.white_bg_box_round_corner));
                }*/
            }
            else {
                edtxtMobile.setEnabled(false);
                edtxtMobile.setText(SharedPrefrence_Business.getUserMobileNumber(context));
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    txtInputLayoutMobile.setBackground(getResources().getDrawable(R.drawable.gray_bg_box));
                }*/
            }
            if(SharedPrefrence_Business.getEmailId(context).equals("")){
                edtxtMail.setEnabled(true);
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    txtInputLayoutMail.setBackground(getResources().getDrawable(R.drawable.white_bg_box_round_corner));
                }*/
            }
            else {
                edtxtMail.setEnabled(false);
                edtxtMail.setText(SharedPrefrence_Business.getEmailId(context));
               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    txtInputLayoutMail.setBackground(getResources().getDrawable(R.drawable.gray_bg_box));
                }*/
            }

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(edtxtSellerId.getText().toString().equals("")){
                        edtxtSellerId.setError("Plz Enter ID Number");
                        edtxtSellerId.requestFocus();
                    }
                    else if(edtxtName.getText().toString().equals("")){
                        edtxtName.setError("Plz Enter Name");
                        edtxtName.requestFocus();
                    }

                    else if(edtxtMail.getText().toString().equals("")){
                        edtxtMail.setError("Plz Enter Email Id");
                        edtxtMail.requestFocus();
                    }
                    else if(edtxtMobile.getText().toString().equals("")){
                        edtxtMobile.setError("Plz Enter Mobile Number");
                        edtxtMobile.requestFocus();
                    }

                    else if(edTxtDescription.getText().toString().equals("")){
                        edTxtDescription.setError("Plz Enter Description");
                        edTxtDescription.requestFocus();
                    }
                    else if(edTxtSubject.getText().toString().equals("")){
                        edTxtSubject.setError("Plz Enter Subject");
                        edTxtSubject.requestFocus();
                    }
                    else {
                        //Call Complaint Type  List APi
                        if(!ConnectivityUtils.isNetworkEnabled(context)){
                            Toast.makeText(context,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            getSaveComplaint();
                        }
                    }


                }
            });

            /*Button Home click listner*/
            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(ComplaintActivity.this, BusinessDashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });



            //Call Complaint Type  List APi
            if(!ConnectivityUtils.isNetworkEnabled(context)){
                Toast.makeText(context,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
            else {
                getComplaintTypeList();
            }
            spinComptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    ComplaintTypeListResponse.ComplaintList complaintList1=(ComplaintTypeListResponse.ComplaintList)parent.getItemAtPosition(position);

                    if(complaintList1 != null){
                        strComplaint=complaintList1.getComplaintname();
                        strComplaintId=complaintList1.getComplaintid();
                    }
                    else {

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        catch (Exception e){
            Log.e("ExceptionComplaint:",e.getMessage());
        }
    }


    /*Complaint list Api Request and Response*/
    private void getComplaintTypeList(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        BaseRequest baseRequest= new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_COMPLAINT_TYPE_LIST);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));


        Call<ComplaintTypeListResponse> bankListCall=
                NetworkClient1.getInstance(context).create(ComplaintServices.class).fetchComplaintList(baseRequest,strApiKey);

        bankListCall.enqueue(new Callback<ComplaintTypeListResponse>() {
            @Override
            public void onResponse(Call<ComplaintTypeListResponse> call, Response<ComplaintTypeListResponse> response) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    ComplaintTypeListResponse Response = new ComplaintTypeListResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {

                            complaintList=Response.getComplainttype();
                            if(complaintList.length > 0){
                                complaintLists=new ArrayList<ComplaintTypeListResponse.ComplaintList>(Arrays.asList(complaintList));

                                adapter=new ComplaintListAdapter(context,complaintLists);
                                spinComptype.setAdapter(adapter);

                            }
                            else {
                                String toast= Response.getResponse()+ "Record is Null";
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
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            new BusinessDashboardActivity().blankValueFromSharePreference(ComplaintActivity.this,Response.getMsg());
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
                        else {
                            String toast= Response.getResponse()+ ": Somthing Wrong" ;
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
                        String toast= "Something went wrong.." ;
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
            public void onFailure(Call<ComplaintTypeListResponse> call, Throwable t) {

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

    }

    /* SAve Complaint  Api Request and Response*/
    private void getSaveComplaint(){

       progressDialog=new ProgressDialog(this);
       progressDialog.setMessage("please wait..");
       progressDialog.setCancelable(false);
       progressDialog.show();

       ComplaintRequest baseRequest= new ComplaintRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_COMPLAINT);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        baseRequest.setComplaintid(strComplaintId);
        baseRequest.setDescription(edTxtDescription.getText().toString());
        baseRequest.setEmail(edtxtMail.getText().toString());
        baseRequest.setIdno(SharedPrefrence_Business.getUserID(context));
        baseRequest.setMobileno(edtxtMobile.getText().toString());
        baseRequest.setName(edtxtName.getText().toString());
        baseRequest.setSubject(edTxtSubject.getText().toString());
        String strRequest=new Gson().toJson(baseRequest);
        Log.e("SaveComplaintReq:",strRequest);

        callSaveComplaint= NetworkClient.getInstance(context).create(ComplaintServices.class).fetchComplaint(baseRequest,strApiKey);

        callSaveComplaint.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
              if(progressDialog.isShowing())
                  progressDialog.dismiss();
                try {

                    BaseResponse Response = new BaseResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {

                        layoutItm.setVisibility(View.GONE);
                        layoutMsg.setVisibility(View.VISIBLE);
                        txtMsg.setText(Response.getMsg());

                        /*SuccessMsgFragment fragment=new SuccessMsgFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Msg",Response.getMsg());
                        fragment.setArguments(bundle);*/


                        //((BusinessDashboardActivity)context).replaceFragment(new SuccessMsgFragment(),"SuccessMsgFragment",bundle);
                        //finish();
                    }
                    else {
                        String toast= Response.getResponse()+ ": Somthing Wrong" ;
                        Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();
                        layoutItm.setVisibility(View.VISIBLE);
                        layoutMsg.setVisibility(View.GONE);

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
        //callSaveComplaint.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.business_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent intent=new Intent(ComplaintActivity.this,BusinessDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
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
}
