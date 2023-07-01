package in.discountmart.business.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.base.network.NetworkClient;
import in.discountmart.R;
import in.discountmart.business.adapter.MemberRelationAdapter;
import in.discountmart.business.adapter.StateListAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.ProfileServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.SetProfileRequest;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.model_business.responsemodel.GetProfileResponse;
import in.discountmart.business.model_business.responsemodel.MemberRelationListResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;
import in.discountmart.business.utility.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    Context context;
    ProgressDialog pDialog;
    EditText txtViewSponserID;
    EditText txtViewPlacementID;
    EditText txtViewPosition;

    EditText edTxtName;
    EditText edTxtFName;
    public static EditText edTxtDOB;
    EditText edTxtMobile;
    EditText edTxtPhone;
    EditText edTxtEmail;
    EditText edTxtNominee;
    EditText edTxtNomRelation;
    EditText edTxtDateOfJoin;
    EditText edTxtDateOfActive;

    Spinner spinMemRelation;

    Button btnSubmitt;

    String strSponserId = "";
    String strPlaementId = "";
    String strPosition = "";
    String strName = "";
    String strFname = "";
    String strDOB = "";
    String strMemRelation = "";
    String strMobile = "";
    String strPhone = "";
    String strEmail = "";
    String strNominee = "";
    String strNomRelation = "";
    String strApiKey = "";

    ArrayList<MemberRelationListResponse.MemberRelation> memberRelationArrayList;
    MemberRelationListResponse.MemberRelation memberRelation[];
    StateListAdapter stateListAdapter;
    MemberRelationAdapter relationAdapter;

    public static String stringDOB = "";
    public static String strMarrigeDate = "";


    public static int yyFromDate;
    public static int mmFromDate;
    public static int ddFromDate;

    ProgressDialog progressDialog;
    View view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_activity_edit_profile);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Profile");

            view1 = findViewById(android.R.id.content);

            txtViewSponserID = (EditText) findViewById(R.id.edit_profile_act_edtxt_sponserId);
            // txtViewPlacementID = (TextView) rootView.findViewById(R.id.edit_profile_fragment_TxtView_placementId);
            txtViewPosition = (EditText) findViewById(R.id.edit_profile_act_edtxt_position);
            edTxtName = (EditText) findViewById(R.id.edit_profile_act_edTxt_name);
            edTxtFName = (EditText) findViewById(R.id.edit_profile_act_edTxt_fath_husband_name);
            edTxtDOB = (EditText) findViewById(R.id.edit_profile_act_edTxt_dob);
            edTxtMobile = (EditText) findViewById(R.id.edit_profile_act_edTxt_mobile);
            edTxtPhone = (EditText) findViewById(R.id.edit_profile_act_edTxt_phone);
            edTxtEmail = (EditText) findViewById(R.id.edit_profile_act_edTxt_email);
            edTxtNominee = (EditText) findViewById(R.id.edit_profile_act_edTxt_nomineeName);
            edTxtDateOfActive = (EditText) findViewById(R.id.edit_profile_act_edTxt_join_date);
            edTxtDateOfJoin = (EditText) findViewById(R.id.edit_profile_act_edTxt_active_date);
            edTxtNomRelation = (EditText) findViewById(R.id.edit_profile_act_edTxt_nomineerelation);
            spinMemRelation = (Spinner) findViewById(R.id.edit_profile_act_spiner_prefix);
            btnSubmitt = (Button) findViewById(R.id.edit_profile_act_button_submit);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(this);

            //Call MemberRelation List Api
            if (!ConnectivityUtils.isNetworkEnabled(EditProfileActivity.this)) {
                Toast.makeText(EditProfileActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            } else {
                getMemberRelationList();
            }

            /*Spinner Relation Item Selected Listner*/
            spinMemRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    MemberRelationListResponse.MemberRelation memberRelation1 =
                            (MemberRelationListResponse.MemberRelation) parent.getItemAtPosition(position);
                    strMemRelation = memberRelation1.getMemrel();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /*Edit text DOB on click*/
            edTxtDOB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectDOBDateFragment();
                    newFragment.show(getSupportFragmentManager(), "DatePicker");
                }
            });

            /*Button click lisetner*/
            btnSubmitt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /// strPlaementId = txtViewPlacementID.getText().toString();
                    strSponserId = txtViewSponserID.getText().toString();
                    strPosition = txtViewPosition.getText().toString();

                    strName = edTxtName.getText().toString();
                    strFname = edTxtFName.getText().toString();
                    strDOB = edTxtDOB.getText().toString();
                    strMobile = edTxtMobile.getText().toString();
                    strPhone = edTxtPhone.getText().toString();
                    strEmail = edTxtEmail.getText().toString();
                    strNominee = edTxtNominee.getText().toString();
                    strNomRelation = edTxtNomRelation.getText().toString();

                    //Call EditProfile Api
                    if (!ConnectivityUtils.isNetworkEnabled(EditProfileActivity.this)) {
                        Toast.makeText(EditProfileActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getEditProfileDetail();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*Method of Spinner Set Index*/
    private int getIndexSpinMemRelation(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {

            MemberRelationListResponse.MemberRelation model = (MemberRelationListResponse.MemberRelation) spinner.getItemAtPosition(i);

            if (model.getMemrel().equalsIgnoreCase(myString)) {
                index = i;
            }
        }
        return index;
    }

    /*MemberRelationList Api Request and Response*/
    private void getMemberRelationList() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setReqtype(ApiConstants.REQUEST_MEMBER_RELATION);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(EditProfileActivity.this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(EditProfileActivity.this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(EditProfileActivity.this));

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(baseRequest);
        Log.e("ReqMemberRelation:", Get_Paramenter);

        Call<MemberRelationListResponse> relationListResponseCall =
                NetworkClient.getInstance(EditProfileActivity.this).create(ProfileServices.class).fetchMemberRelationList(baseRequest,strApiKey);

        relationListResponseCall.enqueue(new Callback<MemberRelationListResponse>() {
            @Override
            public void onResponse(Call<MemberRelationListResponse> call, Response<MemberRelationListResponse> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    MemberRelationListResponse Response = new MemberRelationListResponse();
                    Response = response.body();
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {
                            memberRelation = Response.getMemrels();

                            if (memberRelation.length > 0) {
                                memberRelationArrayList = new ArrayList<MemberRelationListResponse.MemberRelation>(Arrays.asList(memberRelation));
                                relationAdapter = new MemberRelationAdapter(EditProfileActivity.this, memberRelationArrayList);
                                spinMemRelation.setAdapter(relationAdapter);
                                //new getProfileDetails().execute();
                                getProfileDetail();


                            } else {
                                String toast = Response.getResponse() + "Record is Null";
                                Toast.makeText(EditProfileActivity.this, toast, Toast.LENGTH_SHORT).show();
                            }

                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            new BusinessDashboardActivity().blankValueFromSharePreference(EditProfileActivity.this,Response.getMsg());
                        }
                        else if(Response.getResponse().equals("FAILED")) {
                            String msg=Response.getResponse()+ " "+"Something went wrong..";
                            Snackbar.make(view1, msg, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();

                        }
                        else {
                            String toast = Response.getResponse() + ":" + Response.getMsg();
                            Toast.makeText(EditProfileActivity.this, toast, Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        String msg="Something went wrong..";
                        Snackbar.make(view1, msg, Snackbar.LENGTH_LONG)
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
            public void onFailure(Call<MemberRelationListResponse> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Snackbar.make(view1, t.getMessage(), Snackbar.LENGTH_LONG)
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

    /*GetProfile Api request and Response*/
    private void getProfileDetail() {
        //showProgressDialog();
        BaseRequest baseRequest = new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_GET_PROFILE);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(EditProfileActivity.this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(EditProfileActivity.this));
        baseRequest.setMemberid(SharedPrefrence_Business.getUserID(EditProfileActivity.this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(EditProfileActivity.this));

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(baseRequest);
        Log.e("ReqGetProfile:", Get_Paramenter);

        Call<GetProfileResponse> getProfileResponseCall =
                NetworkClient.getInstance(EditProfileActivity.this).create(ProfileServices.class).fetchGetProfileDetail(baseRequest,strApiKey);

        getProfileResponseCall.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                //hideProgressDialog();
                try {

                    GetProfileResponse Response = new GetProfileResponse();
                    Response = response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {
                            //txtViewSponserID.setText(Response.getIdno());
                            //txtViewPlacementID.setText(Response.getUplinerid());
                            //txtViewPosition.setText(Response.getPosition());

                            if (!Response.getIdno().toString().equals("")) {
                                txtViewSponserID.setText(Response.getIdno());
                                txtViewSponserID.setEnabled(false);
                                txtViewSponserID.setFocusable(false);
                            } else {
                                //txtViewSponserID.setText("Enter Sponsor id");
                                txtViewSponserID.setEnabled(false);
                                txtViewSponserID.setFocusable(false);
                            }

                        /*if (!Response.getIdno().toString().equals("")) {
                            //txtViewPosition.setText(Response.getPosition());
                            txtViewPosition.setEnabled(false);
                            txtViewPosition.setFocusable(false);
                        } else {
                            txtViewPosition.setText("Enter Side");
                            txtViewPosition.setEnabled(false);
                            txtViewPosition.setFocusable(false);
                        }*/
                            if (!Response.getName().toString().equals("")) {
                                edTxtName.setText(Response.getName());
                                edTxtName.setEnabled(false);
                                edTxtName.setFocusable(false);
                            } else {
                                //edTxtName.setText("");
                                edTxtName.setEnabled(true);
                                edTxtName.setFocusable(true);
                            }


                        /*if (!Response.getDateofjoining().equals("")) {
                            edTxtDateOfJoin.setText(Response.getDateofjoining());
                            edTxtDateOfJoin.setEnabled(false);
                            edTxtDateOfJoin.setFocusable(false);
                        } else {
                            edTxtDateOfJoin.setText(Response.getDateofjoining());
                            edTxtDateOfJoin.setEnabled(false);
                            edTxtDateOfJoin.setFocusable(false);
                        }*/

                       /* if (!Response.getDateofactivation().equals("")) {
                            edTxtDateOfActive.setText(Response.getDateofactivation());
                            edTxtDateOfActive.setEnabled(false);
                            edTxtDateOfActive.setFocusable(false);
                        }*/
                       /* else {
                            edTxtDateOfActive.setText(Response.getDateofactivation());
                            edTxtDateOfActive.setEnabled(false);
                            edTxtDateOfActive.setFocusable(false);
                        }*/



                            if(!Response.getDob().toString().equals(""))
                            {
                                edTxtDOB.setText(Response.getDob());
                                edTxtDOB.setEnabled(false);
                                edTxtDOB.setFocusable(false);
                            }
                            else {
                                edTxtDOB.setText("");
                                //edTxtDOB.setHint("Please Select Date");
                                edTxtDOB.setEnabled(true);
                                edTxtDOB.setFocusable(true);
                            }

                            if (!Response.getMobile().toString().equals("")) {
                                edTxtMobile.setText(Response.getMobile());
                                edTxtMobile.setEnabled(false);
                                edTxtMobile.setFocusable(false);
                            } else {
                                edTxtMobile.setText("");
                                //edTxtMobile.setHint("Enter Mobile No.");
                                edTxtMobile.setEnabled(true);
                                edTxtMobile.setFocusable(true);
                            }


                            if (!Response.getFname().equals("")) {
                                edTxtFName.setText(Response.getFname());
                                edTxtFName.setEnabled(false);
                                edTxtFName.setFocusable(false);
                            } else {
                                //edTxtFName.setHint("Enter Father name");
                                edTxtFName.setEnabled(true);
                                edTxtFName.setFocusable(true);
                            }
                            if (!Response.getEmail().equals("")) {
                                edTxtEmail.setText(Response.getEmail());
                                edTxtEmail.setEnabled(false);
                                edTxtEmail.setFocusable(false);
                            } else {
                                //edTxtFName.setHint("Enter Father name");
                                edTxtEmail.setEnabled(true);
                                edTxtEmail.setFocusable(true);
                            }

                        /*if (!Response.getNominee().equals("")) {
                            edTxtNominee.setText(Response.getNominee());
                            edTxtNominee.setEnabled(false);
                            edTxtNominee.setFocusable(false);
                        } else {
                            edTxtNominee.setHint("Enter Nominee name");
                            edTxtNominee.setEnabled(true);
                            edTxtNominee.setFocusable(true);
                        }*/

                       /* if (!Response.getNomineerelation().equals("")) {
                            edTxtNomRelation.setText(Response.getRelation());
                            edTxtNomRelation.setEnabled(false);
                            edTxtNomRelation.setFocusable(false);
                        } else {
                            edTxtNomRelation.setHint("Enter Relation");
                            edTxtNomRelation.setEnabled(true);
                            edTxtNomRelation.setFocusable(true);
                        }*/


                            edTxtEmail.setText(Response.getEmail());
                            edTxtPhone.setText(Response.getPhoneno());
                            edTxtNominee.setText(Response.getNominee());
                            edTxtNomRelation.setText(Response.getNomineerelation());
                            spinMemRelation.setSelection(getIndexSpinMemRelation(spinMemRelation, Response.getRelation()));

                        }

                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            new BusinessDashboardActivity().blankValueFromSharePreference(EditProfileActivity.this,Response.getMsg());
                        }
                        else if(Response.getResponse().equals("FAILED")) {
                            String msg=Response.getResponse()+ " "+"Something went wrong..";
                            Snackbar.make(view1, msg, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();

                        }
                        else {
                            String toast = Response.getResponse();
                            Snackbar.make(view1, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();

                        }
                    }
                    else {
                        String toast = "Something went wrong..";
                        Snackbar.make(view1, toast, Snackbar.LENGTH_LONG)
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
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Snackbar.make(view1, t.getMessage(), Snackbar.LENGTH_LONG)
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

    /*GetEdit Profile Api Request and Response*/
    private void getEditProfileDetail() {
        progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        SetProfileRequest Request = new SetProfileRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_SET_PROFILE);
        Request.setPasswd(SharedPrefrence_Business.getPassword(EditProfileActivity.this));
        Request.setUserid(SharedPrefrence_Business.getUserID(EditProfileActivity.this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(EditProfileActivity.this));
        Request.setDob(strDOB);
        Request.setEmail(strEmail);
        Request.setFname(strFname);
        Request.setMobile(strMobile);
        Request.setName(strName);
        Request.setNominee(strNominee);
        Request.setNomineerelation(strNomRelation);
        Request.setPhno(strPhone);
        Request.setRelation(strMemRelation);
        Request.setMemberid(SharedPrefrence_Business.getUserID(EditProfileActivity.this));

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(Request);
        Log.e("ReqSetProfile:", Get_Paramenter);

        Call<BaseResponse> callEditProfile =
                NetworkClient.getInstance(EditProfileActivity.this).create(ProfileServices.class).fetchEditProfileDetail(Request,strApiKey);

        callEditProfile.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    BaseResponse Response = new BaseResponse();
                    Response = response.body();

                    if (Response.getResponse().equals("OK")) {
                        String toast = Response.getResponse() + ":- Profile Update Sucessfully";
                        Toast.makeText(EditProfileActivity.this, toast, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditProfileActivity.this, BusinessDashboardActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        String toast = Response.getResponse() + " :- Profile not update Somthing Wrong";
                        Toast.makeText(EditProfileActivity.this, toast, Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Snackbar.make(view1, t.getMessage(), Snackbar.LENGTH_LONG)
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

    // date picker open for CHECKIN-Date and selected date set to textview
    public static class SelectDOBDateFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar1 = Calendar.getInstance();
            int yy = calendar1.get(Calendar.YEAR);
            int mm = calendar1.get(Calendar.MONTH);
            int dd = calendar1.get(Calendar.DAY_OF_MONTH);

            //Disable date before today date
            DatePickerDialog dpd = new DatePickerDialog(getContext(), this, yy, mm, dd);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                int thisMonth = mm + 1;
                String stringTodayDate = dd + "/" + thisMonth + "/" + yy;
                Date todayDate = sdf.parse(stringTodayDate);
                dpd.getDatePicker().setMaxDate(todayDate.getTime());

            } catch (ParseException e) {
                //handle exception
            }
            return dpd;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            SetDOBDate(yy, mm + 1, dd);
        }
    }

    public static void SetDOBDate(int year, int month, int day) {
        String Month = Utilities.convertMonth(month);
        edTxtDOB.setText(day + "-" + Month + "-" + year);
        stringDOB = (Utilities.convertdayNumber(day) + "-" + Utilities.convertMonthNumber(month) + "-" + year).toString();

        yyFromDate = year;
        mmFromDate = month;
        ddFromDate = day;

        Calendar mCalendarTo = Calendar.getInstance();
        mCalendarTo.set(yyFromDate, mmFromDate, ddFromDate);
        mCalendarTo.add(Calendar.DATE, 1);
        int yy = mCalendarTo.get(Calendar.YEAR);
        int mm = mCalendarTo.get(Calendar.MONTH);
        int dd = mCalendarTo.get(Calendar.DAY_OF_MONTH);
        String mon = Utilities.convertMonthtoText(mm);

        //set Next Date as Check out date
        //textViewToDate.setText(dd  + "-" + mon + "-" + yy);
        //stringToDate = (yy + "-" + Utilities.convertMonthNumber(mm) + "-" + Utilities.convertdayNumber(dd)).toString();
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
