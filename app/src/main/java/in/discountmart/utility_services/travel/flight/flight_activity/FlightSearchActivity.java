package in.discountmart.utility_services.travel.flight.flight_activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.base.network.NetworkClient_Utility_1;

import in.discountmart.R;
import in.discountmart.activity.LoginActivity;
import in.discountmart.utility.ConnectivityUtils;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.travel.flight.call_flight_api.FlightApi;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.FlightSearchRequest;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightSearchResponse;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightSearchReturnResponse;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.OwnwardFlightSearch;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.ReturnFlightSearch;
import in.discountmart.utility_services.travel.flight.flight_sharedpreference.FlightSharedValues;
import in.discountmart.utility_services.travel.travel_sharedpreferance.TravelSharedPreferance;
import in.discountmart.utility_services.utilities.TokenBase64;
import in.discountmart.utility_services.utilities.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightSearchActivity extends AppCompatActivity {

    Context context;
    RadioGroup rdgType;
    RadioButton rdbDemostic;
    RadioButton rdbInternationl;

    LinearLayout layoutFromCity;
    LinearLayout layoutToCity;

    TextView txtOneWay;
    TextView txtRoundTrip;
    TextView txtAdultNum;
    TextView txtChildNum;
    TextView txtInfantsNum;
    public static TextView txtFromCity;
    public static TextView txtFromCitycode;
    public static TextView txtToCity;
    public static TextView txtToCitycode;
    public static EditText edTxtDepartDate;
    public static EditText edTxtReturnDate;
    TextView txtAdultIncrement;
    TextView txtChildIncrement;
    TextView txtInfantsIncrement;
    TextView txtInfantsDecrement;
    TextView txtChildDecrement;
    TextView txtAdultDecrement;
    TextView txtLink;
    Button btnSearch;
    ImageView imgRightArrow;
    ImageView imgRight_leftArrow;
    View view;

    int JurneyType=0;
    String strAdult="0";
    String strChild="0";
    String strInfants="0";
    String strDepartDate="";
    String strReturnDate="";
    String strFlightType="";
    String strFromCityCode="";
    String strToCityCode="";
    String strToCityName="";
    String strFromCityName="";
    String edit="";
    public static String departureDate="";
    public static String returnDate="";
    int value;
    static int adultdinteger = 0;
    static int childinteger = 0;
    static int infantinteger = 0;


    public static int yyFromDate ;
    public static int mmFromDate ;
    public static int ddFromDate ;
    ProgressDialog progressDialog;

    Call<BaseResponse> fetchFlightSearch;
    Call<BaseResponse> fetchFlightSearchReturn;

    ArrayList<FlightSearchRequest.FlightSegment> flightSegmentArrayList;

    ArrayList<FlightSearchResponse> flightSearchList;
    //ArrayList<FlightSearchRequest.FlightSearch.FlightSources> flightSourcesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_flight_search);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context =this;
        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/
        view=findViewById(android.R.id.content);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Flight");

            /*Radio Group and Radion Button */
            rdgType=(RadioGroup)findViewById(R.id.filght_serach_act_rdg_type);
            rdbDemostic=(RadioButton)findViewById(R.id.flight_search_act_rdb_demostic);
            rdbInternationl=(RadioButton)findViewById(R.id.flight_search_act_rdb_internation);

            /*Text View Reference*/
            txtOneWay=(TextView)findViewById(R.id.flight_search_act_txt_oneway);
            txtRoundTrip=(TextView)findViewById(R.id.flight_search_act_txt_rondtrip);
            txtAdultNum=(TextView)findViewById(R.id.flight_search_act_txt_number_adult);
            txtChildNum=(TextView)findViewById(R.id.flight_search_act_txt_number_child);
            txtInfantsNum=(TextView)findViewById(R.id.flight_search_act_txt_number_infants);
            txtFromCity=(TextView)findViewById(R.id.flight_search_act_txt_fromcity);
            txtFromCitycode=(TextView)findViewById(R.id.flight_search_act_txt_fromcitycode);
            txtToCity=(TextView)findViewById(R.id.flight_search_act_txt_tocity);
            txtToCitycode=(TextView)findViewById(R.id.flight_search_act_txt_tocitycode);
            txtLink=(TextView)findViewById(R.id.flight_search_act_txt_link);

            /*EditText Reference*/
            edTxtDepartDate=(EditText)findViewById(R.id.flight_search_act_edtxt_departuredate);
            edTxtReturnDate=(EditText)findViewById(R.id.flight_search_act_edtxt_returndate);

            /*Layout reference*/
            layoutFromCity=(LinearLayout)findViewById(R.id.flight_search_act_layout_from);
            layoutToCity=(LinearLayout)findViewById(R.id.flight_search_act_layout_to);
            /*ImageView Reference*/
            imgRightArrow=(ImageView)findViewById(R.id.img_oneway);
            imgRight_leftArrow=(ImageView)findViewById(R.id.img_twoway);

            /* Button Reference*/
            txtAdultDecrement=(TextView)findViewById(R.id.flight_search_act_txt_adult_decrese);
            txtChildDecrement=(TextView)findViewById(R.id.flight_search_act_txt_child_decrese);
            txtInfantsDecrement=(TextView)findViewById(R.id.flight_search_act_txt_infants_decrese);
            txtAdultIncrement=(TextView)findViewById(R.id.flight_search_act_txt_adult_increse);
            txtChildIncrement=(TextView)findViewById(R.id.flight_search_act_txt_child_increse);
            txtInfantsIncrement=(TextView)findViewById(R.id.flight_search_act_txt_infants_increse);
            btnSearch=(Button)findViewById(R.id.flight_search_act_btn_search);

        /*Update*/

            /*Get Edit Bundle */
            Intent intent=getIntent();
            if(intent != null){
                edit=intent.getStringExtra("Edit");
                if(edit.contentEquals("true")){
                    JurneyType= FlightSharedValues.getInstance().jurneyType;
                    if(JurneyType == 1){
                        edTxtDepartDate.setText(FlightSharedValues.getInstance().flightDepatureTime);
                        txtFromCitycode.setText(FlightSharedValues.getInstance().flightOriginCityID);
                        txtFromCity.setText(FlightSharedValues.getInstance().flightOriginCityName);
                        txtToCitycode.setText(FlightSharedValues.getInstance().flightDesinationCityID);
                        txtToCity.setText(FlightSharedValues.getInstance().flightDesinationCityName);

                        adultdinteger=FlightSharedValues.getInstance().totAdults;
                        childinteger=FlightSharedValues.getInstance().totChilds;
                        infantinteger=FlightSharedValues.getInstance().totInfants;
                        //FlightSharedValues.getInstance().totPessanger=totPessanger;

                        edTxtReturnDate.setEnabled(false);
                        imgRight_leftArrow.setVisibility(View.GONE);
                        txtAdultNum.setText(String.valueOf(adultdinteger));
                        txtChildNum.setText(String.valueOf(childinteger));
                        txtInfantsNum.setText(String.valueOf(infantinteger));

                        /*Call method for set previous select value in adut, chilc, infants*/
                        setAdultValue(adultdinteger);
                        setChildValue(childinteger);
                        setInfantsValue(infantinteger);

                    }
                }
                else {
                    edTxtReturnDate.setEnabled(false);
                    imgRight_leftArrow.setVisibility(View.GONE);
                    JurneyType=1;
                    adultdinteger=1;
                    childinteger = 0;
                    infantinteger = 0;
                    txtAdultNum.setText(String.valueOf(adultdinteger));
                    txtChildNum.setText(String.valueOf(childinteger));
                    txtInfantsNum.setText(String.valueOf(infantinteger));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        txtAdultDecrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));
                        txtChildDecrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));
                        txtInfantsDecrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));
                    }
                }

            }
            else {
                edTxtReturnDate.setEnabled(false);
                imgRight_leftArrow.setVisibility(View.GONE);
                JurneyType=1;
                adultdinteger=1;
                childinteger = 0;
                infantinteger = 0;
                txtAdultNum.setText(String.valueOf(adultdinteger));
                txtChildNum.setText(String.valueOf(childinteger));
                txtInfantsNum.setText(String.valueOf(infantinteger));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    txtAdultDecrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));
                    txtChildDecrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));
                    txtInfantsDecrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));
                }
            }





            //  }

            txtLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String urlCentre = "http://distributor.bisplindia.in/Files/Testpage.pdf";
                    Intent i2 = new Intent(Intent.ACTION_VIEW);
                    i2.setData(Uri.parse(urlCentre));
                    startActivity(i2);
                }
            });

            /*TextView clicklistner*/
            txtOneWay.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        txtRoundTrip.setBackground(getResources().getDrawable(R.drawable.transparent_bg));
                        txtOneWay.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                        txtOneWay.setTextColor(getResources().getColor(R.color.white));
                        txtRoundTrip.setTextColor(getResources().getColor(R.color.gray));
                        imgRight_leftArrow.setVisibility(View.GONE);
                        imgRightArrow.setVisibility(View.VISIBLE);
                        //txtOneWay.setTextColor(getResources().getColor(R.color.white));
                        //txtRoundTrip.setTextColor(getResources().getColor(R.color.black));
                        JurneyType=1;
                        //edTxtReturnDate.setFocusable(false);
                        edTxtReturnDate.setEnabled(false);
                        edTxtDepartDate.setEnabled(true);
                    }
                }
            });


            /*TextView clicklistner*/
            txtRoundTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        txtOneWay.setBackground(getResources().getDrawable(R.drawable.transparent_bg));
                        txtRoundTrip.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                        txtOneWay.setTextColor(getResources().getColor(R.color.gray));
                        txtRoundTrip.setTextColor(getResources().getColor(R.color.white));
                        imgRight_leftArrow.setVisibility(View.VISIBLE);
                        imgRightArrow.setVisibility(View.GONE);
                        JurneyType=2;
                        edTxtReturnDate.setEnabled(true);
                        edTxtDepartDate.setEnabled(true);
                    }
                }
            });

            /*Radio Button Check is cheked or not*/
            if(rdbDemostic.isChecked()){

                strFlightType="D";

            }
            else if(rdbInternationl.isChecked()){
                strFlightType="I";
            }
            /*Radio Button Check listener*/
            rdgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int selectId=rdgType.getCheckedRadioButtonId();
                    RadioButton rb=(RadioButton)group.findViewById(checkedId);
                    String strRbtnVlaue=rb.getText().toString();
                    if(strRbtnVlaue.equals("Domestic")){
                        strFlightType="D";

                    }
                    else if(strRbtnVlaue.equals("International")){
                        strFlightType="I";

                    }
                }
            });

            /*Layout from city Click listener*/
            layoutFromCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(FlightSearchActivity.this, FlightCityListActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("Type",strFlightType);
                   // bundle.putString("Hint","From City");
                    bundle.putInt("Value",1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });
            /*Layout from city Click listener*/
            layoutToCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(FlightSearchActivity.this, FlightCityListActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("Type",strFlightType);
                    bundle.putInt("Value",2);
                   // bundle.putString("Hint","To City");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });

            /*Departure Date*/
            edTxtDepartDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectDepartureDateFragment();
                    newFragment.show(getSupportFragmentManager(), "DatePicker");
                }
            });

            /*Return Date*/
            edTxtReturnDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectReturnDateFragment();
                    newFragment.show(getSupportFragmentManager(), "DatePicker");
                }
            });

            /*Button Adult Increment Click Listener*/
            txtAdultIncrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(adultdinteger == 1  || adultdinteger < 9){
                        adultdinteger = adultdinteger + 1;

                        txtAdultNum.setText(String.valueOf(adultdinteger));

                        txtAdultIncrement.setFocusable(true);
                        txtAdultIncrement.setClickable(true);
                        txtAdultDecrement.setFocusable(true);
                        txtAdultDecrement.setClickable(true);


                    }
                    else if(adultdinteger ==9) {
                        adultdinteger = 9;
                        txtAdultNum.setText(String.valueOf(adultdinteger));

                        txtAdultIncrement.setFocusable(false);
                        txtAdultIncrement.setClickable(false);
                        txtAdultDecrement.setFocusable(true);
                        txtAdultDecrement.setClickable(true);

                    }
                    if(adultdinteger == 1){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtAdultIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                            txtAdultDecrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));

                        }
                    }
                    else if(adultdinteger > 1 && adultdinteger < 9){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtAdultIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                            txtAdultDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));

                        }
                    }
                    else if(adultdinteger == 9){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtAdultIncrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));
                            txtAdultDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));

                        }
                    }




                }
            });

            /*Button Adult Decrement Click Listener*/
            txtAdultDecrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //increaseInteger(v);
                    //auldinteger=0;

                    if(adultdinteger == 1 ){
                        adultdinteger = 1;
                        txtAdultNum.setText(String.valueOf(adultdinteger));

                        txtAdultDecrement.setFocusable(false);
                        txtAdultDecrement.setClickable(false);
                        txtAdultIncrement.setFocusable(true);
                        txtAdultIncrement.setClickable(true);
                    }
                    else if(adultdinteger > 1){
                        adultdinteger = adultdinteger-1;
                        txtAdultNum.setText(String.valueOf(adultdinteger));

                        txtAdultDecrement.setFocusable(true);
                        txtAdultDecrement.setClickable(true);
                        txtAdultIncrement.setFocusable(true);
                        txtAdultIncrement.setClickable(true);
                    }

                    if(adultdinteger == 1){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtAdultIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                            txtAdultDecrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));

                        }
                    }
                    else if(adultdinteger > 1 && adultdinteger < 9){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtAdultIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                            txtAdultDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));

                        }
                    }
                    else if(adultdinteger == 9){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtAdultIncrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));
                            txtAdultDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));

                        }
                    }

                }
            });

            /*Button Child Increment Click Listener*/
            txtChildIncrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //increaseInteger(v);
                    //minteger=0;

                    if(childinteger == 1  || childinteger < 5){

                        childinteger = childinteger + 1;
                        txtChildNum.setText(String.valueOf(childinteger));
                        txtChildIncrement.setFocusable(true);
                        txtChildIncrement.setClickable(true);
                        txtChildDecrement.setFocusable(true);
                        txtChildDecrement.setClickable(true);
                    }
                    else if(childinteger ==5) {

                        childinteger = 5;
                        txtChildNum.setText(String.valueOf(childinteger));
                        txtChildIncrement.setFocusable(false);
                        txtChildIncrement.setClickable(false);
                        txtChildDecrement.setFocusable(true);
                        txtChildDecrement.setClickable(true);

                    }

                    if(childinteger == 0){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtChildIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                            txtChildDecrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));

                        }
                    }
                    else if(childinteger > 0 && childinteger < 5){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtChildIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                            txtChildDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));

                        }
                    }
                    else if(childinteger == 5){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtChildIncrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));
                            txtChildDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));

                        }
                    }

                }
            });

            /*Button Child Decrement Click Listener*/
            txtChildDecrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //increaseInteger(v);
                    // minteger=0;


                    if(childinteger == 0 ){

                        childinteger=0;
                        txtChildNum.setText(String.valueOf(childinteger));
                        txtChildDecrement.setFocusable(false);
                        txtChildDecrement.setClickable(false);
                        txtChildIncrement.setFocusable(true);
                        txtChildIncrement.setClickable(true);

                    }
                    else if(childinteger > 0){

                        childinteger = childinteger - 1;
                        txtChildNum.setText(String.valueOf(childinteger));
                        txtChildDecrement.setFocusable(true);
                        txtChildDecrement.setClickable(true);
                        txtChildIncrement.setFocusable(true);
                        txtChildIncrement.setClickable(true);
                    }
                    if(childinteger == 0){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtChildIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                            txtChildDecrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));

                        }
                    }
                    else if(childinteger > 0 && childinteger < 5){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtChildIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                            txtChildDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));

                        }
                    }
                    else if(childinteger == 5){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtChildIncrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));
                            txtChildDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));

                        }
                    }

                }
            });

            /*Button Infants Increment Click Listener*/
            txtInfantsIncrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //increaseInteger(v);
                    //infantinteger=0;


                    if(infantinteger == 1  || infantinteger < 5){

                        infantinteger = infantinteger + 1;
                        txtInfantsNum.setText(String.valueOf(infantinteger));
                        txtInfantsIncrement.setFocusable(true);
                        txtInfantsIncrement.setClickable(true);
                        txtInfantsDecrement.setFocusable(true);
                        txtInfantsDecrement.setClickable(true);
                    }
                    else if(infantinteger ==5) {

                        infantinteger = 5;
                        txtInfantsNum.setText(String.valueOf(infantinteger));
                        txtInfantsIncrement.setFocusable(false);
                        txtInfantsIncrement.setClickable(false);
                        txtInfantsDecrement.setFocusable(true);
                        txtInfantsDecrement.setClickable(true);

                    }
                    if(infantinteger == 0){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtInfantsIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                            txtInfantsDecrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));

                        }
                    }
                    else if(infantinteger > 0 && infantinteger < 5){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtInfantsIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                            txtInfantsDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));

                        }
                    }
                    else if(infantinteger == 5){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtInfantsIncrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));
                            txtInfantsDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));

                        }
                    }

                }
            });

            /*Button Infants Decrement Click Listener*/
            txtInfantsDecrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //increaseInteger(v);
                    // minteger=0;


                    if(infantinteger == 0 ){
                        infantinteger = 0;
                        txtInfantsNum.setText(String.valueOf(infantinteger));

                        txtInfantsDecrement.setFocusable(false);
                        txtInfantsDecrement.setClickable(false);
                        txtInfantsIncrement.setFocusable(true);
                        txtInfantsIncrement.setClickable(true);

                    }
                    else if(infantinteger > 0){
                        infantinteger = infantinteger - 1;
                        txtInfantsNum.setText(String.valueOf(infantinteger));

                        txtInfantsDecrement.setFocusable(true);
                        txtInfantsDecrement.setClickable(true);
                        txtInfantsIncrement.setFocusable(true);
                        txtInfantsIncrement.setClickable(true);
                    }

                    if(infantinteger == 0){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtInfantsIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                            txtInfantsDecrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));

                        }
                    }
                    else if(infantinteger > 0 && infantinteger < 5){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtInfantsIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                            txtInfantsDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));

                        }
                    }
                    else if(infantinteger == 5){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            txtInfantsIncrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));
                            txtInfantsDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));

                        }
                    }

                }
            });

            /*Button Flight Search Click Listener*/
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(JurneyType == 1){
                        if(txtFromCity.getText().toString().equals("City")){
                            Toast.makeText(context,"Please Select From City",Toast.LENGTH_SHORT).show();
                        }
                        else if(txtToCity.getText().toString().equals("City")){
                            Toast.makeText(context,"Please Select To City",Toast.LENGTH_SHORT).show();
                        }
                        else if(txtFromCity.getText().toString().equals("From")){
                            Toast.makeText(context,"Please Select From City",Toast.LENGTH_SHORT).show();
                        }
                        else if (edTxtDepartDate.getText().toString().equals("")){

                            edTxtDepartDate.setError("Select Date");
                            edTxtDepartDate.requestFocus();
                        }
                        else if(txtAdultNum.getText().toString().equals("0")){
                            Toast.makeText(context,"Please Select Adults",Toast.LENGTH_SHORT).show();
                        }
                        else {

                            strFromCityName=txtFromCity.getText().toString();
                            strFromCityCode=txtFromCitycode.getText().toString();
                            strToCityName=txtToCity.getText().toString();
                            strToCityCode=txtToCitycode.getText().toString();
                            strDepartDate=edTxtDepartDate.getText().toString();
                            strAdult=txtAdultNum.getText().toString();
                            strChild=txtChildNum.getText().toString();
                            strInfants=txtInfantsNum.getText().toString();
                            /*update in 18/09/2019*/
                            String departDate=edTxtDepartDate.getText().toString();

                            String []date1=departDate.split("-");
                            String day=date1[0];
                            String month= Utilities.convertTextToNumMonth(date1[1]);
                            String year=date1[2];
                            departureDate=year+"-"+month+"-"+day;

                            View view = FlightSearchActivity.this.getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(),
                                        0);
                            }
                            if(!ConnectivityUtils.isNetworkEnabled(context)){
                                Toast.makeText(context,getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getFlightSearch();
                            }
                        }

                    }
                    else {
                        if(txtFromCity.getText().toString().equals("City")){
                            Toast.makeText(context,"Please Select From City",Toast.LENGTH_SHORT).show();
                        }
                        else if(txtToCity.getText().toString().equals("City")){
                            Toast.makeText(context,"Please Select To City",Toast.LENGTH_SHORT).show();
                        }
                        else if(txtFromCity.getText().toString().equals("From")){
                            Toast.makeText(context,"Please Select From City",Toast.LENGTH_SHORT).show();
                        }
                        else if (edTxtDepartDate.getText().toString().equals("")){

                            edTxtDepartDate.setError("Select Date");
                            edTxtDepartDate.requestFocus();
                        }
                        else if (edTxtReturnDate.getText().toString().equals("")){

                            edTxtReturnDate.setError("Select Date");
                            edTxtReturnDate.requestFocus();
                        }
                        else if(txtAdultNum.getText().toString().equals("0")){
                            Toast.makeText(context,"Please Select Adults",Toast.LENGTH_SHORT).show();
                        }
                        else {

                            strFromCityName=txtFromCity.getText().toString();
                            strFromCityCode=txtFromCitycode.getText().toString();
                            strToCityName=txtToCity.getText().toString();
                            strToCityCode=txtToCitycode.getText().toString();
                            strDepartDate=edTxtDepartDate.getText().toString();
                            strReturnDate=edTxtReturnDate.getText().toString();
                            strAdult=txtAdultNum.getText().toString();
                            strChild=txtChildNum.getText().toString();
                            strInfants=txtInfantsNum.getText().toString();

                            /*update in 18/09/2019*/
                            String departDate=edTxtDepartDate.getText().toString();

                            String []date1=departDate.split("-");
                            String day=date1[0];
                            String month=Utilities.convertTextToNumMonth(date1[1]);
                            String year=date1[2];
                            departureDate=year+"-"+month+"-"+day;

                            /*update in 18/09/2019*/
                            String retDate=edTxtReturnDate.getText().toString();

                            String []date2=retDate.split("-");
                            String retDay=date2[0];
                            String retMonth=Utilities.convertTextToNumMonth(date2[1]);
                            String retYear=date2[2];
                            returnDate=retYear+"-"+retMonth+"-"+retDay;

                            if(!ConnectivityUtils.isNetworkEnabled(context)){
                                Toast.makeText(context,getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getFlightSearchReturn();
                            }
                        }
                    }

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // date picker open for CHECKIN-Date and selected date set to textview
    public static class SelectDepartureDateFragment extends DialogFragment implements
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
                dpd.getDatePicker().setMinDate(todayDate.getTime());

            } catch (ParseException e) {
                //handle exception
            }
            return dpd;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            SetDepartureDate(yy, mm +1, dd);
        }
    }

    public static void SetDepartureDate(int year, int month, int day) {
        String Month = Utilities.convertMonth(month);
        edTxtDepartDate.setText(day + "-" + Month + "-" + year);
        departureDate = (year + "-" + Utilities.convertMonthNumber(month) + "-" + Utilities.convertdayNumber(day) );

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

    // date picker open for CHECKIN-Date and selected date set to textview
    public static class SelectReturnDateFragment extends DialogFragment implements
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
                dpd.getDatePicker().setMinDate(todayDate.getTime());

            } catch (ParseException e) {
                //handle exception
            }
            return dpd;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            SetReturnDate(yy, mm +1, dd);
        }
    }

    public static void SetReturnDate(int year, int month, int day) {
        String Month = Utilities.convertMonth(month);
        edTxtReturnDate.setText(day + "-" + Month + "-" + year);
        //returnDate = (day + "-" + Month + "-" + year );
        returnDate = (year + "-" + Utilities.convertMonthNumber(month) + "-" + Utilities.convertdayNumber(day) );

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


    /* Request and Response Flight Search*/
    public void getFlightSearch(){
        try {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String strApiRequest="";
            JSONObject object=null;
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(context).getLoggedinUser();
            String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);

            String[] strResources={"GDS","FZ","G8","SG","G9","AK","IX","LB","TR","6E","B3","OP","2T","W5","LV","TZ","ZO","PY"};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String str = String.join(",", strResources);
                TravelSharedPreferance.setFlightSources(context,str);
            }

            JSONArray jsonArray = new JSONArray(Arrays.asList(strResources));
            String strToken= TokenBase64.getToken();

            /*Main Request Model*/
            ApiRequest apiRequest = new ApiRequest();

            try {

                /*Base Request Model*/
                BaseHeaderRequest headerRequest =new BaseHeaderRequest();
                headerRequest.setUserName(  new LoginPreferences_Utility(context).getLoggedinUser().getUserName());
                headerRequest.setPassword( new LoginPreferences_Utility(context).getLoggedinUser().getPasswd());
                headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/

                /* Flight Search Request Model*/

                FlightSearchRequest flightSearch=new FlightSearchRequest();
                flightSearch.setAdultCount(txtAdultNum.getText().toString());
                flightSearch.setChildCount(txtChildNum.getText().toString());
                flightSearch.setInfantCount(txtInfantsNum.getText().toString());
                flightSearch.setDirectFlight("false");
                flightSearch.setEndUserIp("");
                flightSearch.setJourneyType(String.valueOf(JurneyType));
                flightSearch.setOneStopFlight("false");
                flightSearch.setPreferredAirlines("");
                flightSearch.setSegments(segmentItem());
                flightSearch.setSources(strResources);
                flightSearch.setTokenId("");
                flightSearch.setGroupID(loginResponse.getGroupId());


                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(flightSearch);

                strApiRequest=new Gson().toJson(apiRequest);

                Log.e("Value",strApiRequest);
            }catch (Exception e){
                e.printStackTrace();
            }


             fetchFlightSearch=
                    NetworkClient_Utility_1.getInstance(context).create(FlightApi.class).fetchFlightSearch(apiRequest,strToken);

            fetchFlightSearch.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                   if(progressDialog.isShowing())
                       progressDialog.dismiss();
                    try {

                        BaseResponse Response =new BaseResponse();
                        Response=response.body();

                        if(Response != null){
                            if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                if(Response.getRESP_VALUE().equals("") || Response.getRESP_VALUE().isEmpty()){
                                    String toast= Response.getRESP_MSG();
                                    //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                                    Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                            .show();
                                }
                                else if(!(Response.getRESP_VALUE()==null) ||  !(Response.getRESP_VALUE().equals(""))){
                                    String[] flightListResponse=Response.getRESPONSE().split("");
                                    if(flightListResponse.length > 0){
                                        FlightSearchResponse[]flightList= new Gson().fromJson(Response.getRESP_VALUE(),FlightSearchResponse[].class);
                                        flightSearchList=new ArrayList<FlightSearchResponse>(Arrays.asList(flightList));
                                        if(flightSearchList.size() > 0){

                                            int totAdult=Integer.parseInt(strAdult);
                                            int totChild=Integer.parseInt(strChild);
                                            int totInfant=Integer.parseInt(strInfants);
                                            int totPessanger=totAdult+totChild+totInfant;

                                            FlightSharedValues.getInstance().flightDepatureTime=strDepartDate;
                                            FlightSharedValues.getInstance().flightOriginCityID=strFromCityCode;
                                            FlightSharedValues.getInstance().flightOriginCityName=strFromCityName;
                                            FlightSharedValues.getInstance().flightDesinationCityID=strToCityCode;
                                            FlightSharedValues.getInstance().flightDesinationCityName=strToCityName;
                                            FlightSharedValues.getInstance().totAdults=totAdult;
                                            FlightSharedValues.getInstance().totChilds=totChild;
                                            FlightSharedValues.getInstance().totInfants=totInfant;
                                            FlightSharedValues.getInstance().totPessanger=totPessanger;
                                            FlightSharedValues.getInstance().jurneyType=JurneyType;

                                            Bundle bundle=new Bundle();
                                            bundle.putSerializable("FlightList",flightList);
                                            bundle.putString("FromCity",strFromCityName);
                                            bundle.putString("ToCity",strToCityName);
                                            bundle.putString("Adult",strAdult);
                                            bundle.putString("Child",strChild);
                                            bundle.putString("Infants",strInfants);
                                            bundle.putString("DepartDate",strDepartDate);
                                            Intent intent=new Intent(FlightSearchActivity.this, FlightSearchListActivity.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                                        }


                                    }
                                    else {
                                        String toast= "flight not found..for particular date";
                                        // Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
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
                            else if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR"))  {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(FlightSearchActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(FlightSearchActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(FlightSearchActivity.this, toast, Toast.LENGTH_SHORT).show();
                                // showSnackbar(toast);

                            }
                        }
                        else {
                            //Toast.makeText(context,"flight not available / please try later or other",Toast.LENGTH_SHORT).show();
                            String toast= "Something wrong..";
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

    /* Request and Response Flight Search return*/
    public void getFlightSearchReturn(){
        try {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String strApiRequest="";
            JSONObject object=null;
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(context).getLoggedinUser();
            String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);

            String[] strResources={"GDS","FZ","G8","SG","G9","AK","IX","LB","TR","6E","B3","OP","2T","W5","LV","TZ","ZO","PY"};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String str = String.join(",", strResources);
                TravelSharedPreferance.setFlightSources(context,str);
            }


            JSONArray jsonArray = new JSONArray(Arrays.asList(strResources));
            String strToken= TokenBase64.getToken();

            /*Main Request Model*/
            ApiRequest apiRequest = new ApiRequest();

            try {

                /*Base Request Model*/
                BaseHeaderRequest headerRequest =new BaseHeaderRequest();
                headerRequest.setUserName(  new LoginPreferences_Utility(context).getLoggedinUser().getUserName());
                headerRequest.setPassword( new LoginPreferences_Utility(context).getLoggedinUser().getPasswd());
                headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/

                /* Flight Search Request Model*/

                FlightSearchRequest flightSearch=new FlightSearchRequest();
                flightSearch.setAdultCount(txtAdultNum.getText().toString());
                flightSearch.setChildCount(txtChildNum.getText().toString());
                flightSearch.setInfantCount(txtInfantsNum.getText().toString());
                flightSearch.setDirectFlight("false");
                flightSearch.setEndUserIp("");
                flightSearch.setJourneyType(String.valueOf(JurneyType));
                flightSearch.setOneStopFlight("false");
                flightSearch.setPreferredAirlines("");
                flightSearch.setSegments(segmentItem());
                flightSearch.setSources(strResources);
                flightSearch.setTokenId("");
                flightSearch.setGroupID(loginResponse.getGroupId());


                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(flightSearch);

                strApiRequest=new Gson().toJson(apiRequest);

                Log.e("Value",strApiRequest);
            }catch (Exception e){
                e.printStackTrace();
            }


            fetchFlightSearchReturn=
                    NetworkClient_Utility_1.getInstance(context).create(FlightApi.class).fetchFlightSearchReturn(apiRequest,strToken);

            fetchFlightSearchReturn.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    try {

                        BaseResponse Response =new BaseResponse();
                        Response=response.body();

                        if(Response != null){
                            if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                if(Response.getRESP_VALUE().equals("") || Response.getRESP_VALUE().isEmpty()){
                                    String toast= Response.getRESP_MSG();
                                    //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                                    Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                            .show();
                                }
                                else if(!(Response.getRESP_VALUE()==null) ||  !(Response.getRESP_VALUE().equals(""))){
                                    FlightSearchReturnResponse flightReturnResponse= new Gson().fromJson(Response.getRESP_VALUE(),FlightSearchReturnResponse.class);

                                    if(flightReturnResponse!= null){


                                        ArrayList<OwnwardFlightSearch> ownwardFlightList=new ArrayList<OwnwardFlightSearch>();
                                        ArrayList<ReturnFlightSearch> returnFlightList=new ArrayList<ReturnFlightSearch>();
                                        ownwardFlightList=flightReturnResponse.getLstOneFlightResponse();
                                        returnFlightList=flightReturnResponse.getLstReturnFlightResponse();

                                        if(ownwardFlightList != null && returnFlightList != null){
                                            try {
                                                int totAdult=Integer.parseInt(strAdult);
                                                int totChild=Integer.parseInt(strChild);
                                                int totInfant=Integer.parseInt(strInfants);
                                                int totPessanger=totAdult+totChild+totInfant;

                                                FlightSharedValues.getInstance().flightDepatureTime=strDepartDate;
                                                FlightSharedValues.getInstance().flightRetDepatureTime=strReturnDate;
                                                FlightSharedValues.getInstance().flightOriginCityID=strFromCityCode;
                                                FlightSharedValues.getInstance().flightRetOriginCityID=strToCityCode;
                                                FlightSharedValues.getInstance().flightOriginCityName=strFromCityName;
                                                FlightSharedValues.getInstance().flightRetOriginCityName=strToCityName;
                                                FlightSharedValues.getInstance().flightDesinationCityID=strToCityCode;
                                                FlightSharedValues.getInstance().flightRetDesinationCityID=strFromCityCode;
                                                FlightSharedValues.getInstance().flightDesinationCityName=strToCityName;
                                                FlightSharedValues.getInstance().flightRetDesinationCityName=strFromCityName;
                                                FlightSharedValues.getInstance().totAdults=totAdult;
                                                FlightSharedValues.getInstance().totChilds=totChild;
                                                FlightSharedValues.getInstance().totInfants=totInfant;
                                                FlightSharedValues.getInstance().totPessanger=totPessanger;


                                                Intent intent1=new Intent(FlightSearchActivity.this, FlightSearchRoundTripListActivity.class);
                                                Bundle bundle1=new Bundle();
                                               // bundle1.putSerializable("OwnwardFlightList",ownwardFlightList);
                                                //bundle1.putSerializable("ReturnFlightList",returnFlightList);
                                                bundle1.putSerializable("FlightReturnList",flightReturnResponse);
                                                bundle1.putString("FromCity",strFromCityName);
                                                bundle1.putString("FromCityCode",strFromCityCode);
                                                bundle1.putString("RetFromCityCode",strToCityCode);
                                                bundle1.putString("ToCity",strToCityName);
                                                bundle1.putString("ToCityCode",strToCityCode);
                                                bundle1.putString("RetToCityCode",strFromCityCode);
                                                bundle1.putString("Adult",strAdult);
                                                bundle1.putString("Child",strChild);
                                                bundle1.putString("Infants",strInfants);
                                                bundle1.putString("DepartDate",strDepartDate);
                                                bundle1.putString("RetDepartDate",strReturnDate);
                                                intent1.putExtras(bundle1);
                                                startActivity(intent1);
                                                //overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                                            }catch (Exception e){
                                                e.printStackTrace();
                                                Log.e("REturnFilghtRes_Error", e.getMessage());
                                                Toast.makeText(FlightSearchActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                        else {
                                            String toast= "flight not found..for particular date";
                                            // Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
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
                                        String toast= "flight not found..for particular date";
                                        // Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
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
                            else if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR"))  {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(FlightSearchActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(FlightSearchActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(FlightSearchActivity.this, toast, Toast.LENGTH_SHORT).show();
                                // showSnackbar(toast);

                            }
                        }
                        else {
                            //Toast.makeText(context,"flight not available / please try later or other",Toast.LENGTH_SHORT).show();
                            String toast= "Something wrong..";
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

    private ArrayList<FlightSearchRequest.FlightSegment> segmentItem() {
        JSONArray jSonArray = new JSONArray();
        JsonArray jsonArray=new JsonArray();
        FlightSearchRequest.FlightSegment flightSegment = new FlightSearchRequest.FlightSegment();

        ArrayList<FlightSearchRequest.FlightSegment> segmentsArrayList = new ArrayList<FlightSearchRequest.FlightSegment>();
        Collection<FlightSearchRequest.FlightSegment> enums = null;

        if(JurneyType ==1 ){
            for(int i=0;i < 1;i++){
                flightSegment.setOrigin(strFromCityCode);
                flightSegment.setOriginFull(strFromCityName);
                flightSegment.setDestination(strToCityCode);
                flightSegment.setDestinationFull(strToCityName);
                flightSegment.setFlightCabinClass("1");
                flightSegment.setPreferredDepartureTime(departureDate);
                flightSegment.setPreferredArrivalTime(departureDate);
                flightSegmentArrayList=new ArrayList<FlightSearchRequest.FlightSegment>();
                flightSegmentArrayList.add( flightSegment);
                jsonArray = new Gson().toJsonTree(flightSegmentArrayList).getAsJsonArray();
                //jSonArray=new JSONArray(flightSegmentArrayList);
               /* String jsonInString = new Gson().toJson(flightSegment);
                JSONObject mJSONObject = null;
                try {
                    mJSONObject = new JSONObject(jsonInString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jSonArray.put(mJSONObject);*/

            }
        }
        else {
            for(int i=0;i < 2;i++){
                if(i==0){
                    FlightSearchRequest.FlightSegment flightSegment1=new FlightSearchRequest.FlightSegment();
                    flightSegment1.setOrigin(strFromCityCode);
                    flightSegment1.setOriginFull(strFromCityName);
                    flightSegment1.setDestination(strToCityCode);
                    flightSegment1.setDestinationFull(strToCityName);
                    flightSegment1.setFlightCabinClass("1");
                    flightSegment1.setPreferredDepartureTime(departureDate);
                    flightSegment1.setPreferredArrivalTime(departureDate);

                    flightSegmentArrayList=new ArrayList<FlightSearchRequest.FlightSegment>();
                    flightSegmentArrayList.add( flightSegment1);
                    //jsonArray = new Gson().toJsonTree(flightSegmentArrayList).getAsJsonArray();
                }
                else if(i ==1){
                    FlightSearchRequest.FlightSegment flightSegment2=new FlightSearchRequest.FlightSegment();
                    flightSegment2.setOrigin(strToCityCode);
                    flightSegment2.setOriginFull(strToCityName);
                    flightSegment2.setDestination(strFromCityCode);
                    flightSegment2.setDestinationFull(strFromCityName);
                    flightSegment2.setFlightCabinClass("1");
                    flightSegment2.setPreferredDepartureTime(returnDate);
                    flightSegment2.setPreferredArrivalTime(returnDate);

                    //flightSegmentArrayList=new ArrayList<FlightSearchRequest.FlightSegment>();
                    flightSegmentArrayList.add( flightSegment2);
                    jsonArray = new Gson().toJsonTree(flightSegmentArrayList).getAsJsonArray();
                }

                //jSonArray=new JSONArray(flightSegmentArrayList);
                /*String jsonInString = new Gson().toJson(flightSegment);
                JSONObject mJSONObject = null;
                try {
                    mJSONObject = new JSONObject(jsonInString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jSonArray.put(mJSONObject);*/

            }
        }

        segmentsArrayList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<FlightSearchRequest.FlightSegment>>(){}.getType());

        return segmentsArrayList;
    }

    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        return true;
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {

            if(progressDialog.isShowing())
                progressDialog.dismiss();

            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }*/

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
    public void onResume(){
        super.onResume();

    }

    /*Method for setAdult value*/
    public void setAdultValue(int val){
        if(val == 1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                txtAdultIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                txtAdultDecrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));

            }

            txtAdultIncrement.setFocusable(true);
            txtAdultIncrement.setClickable(true);
            txtAdultDecrement.setFocusable(false);
            txtAdultDecrement.setClickable(false);
        }
        else if(val > 1 && val < 9){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                txtAdultIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                txtAdultDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));

            }
            txtAdultIncrement.setFocusable(true);
            txtAdultIncrement.setClickable(true);
            txtAdultDecrement.setFocusable(true);
            txtAdultDecrement.setClickable(true);
        }
        else if(val == 9){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                txtAdultIncrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));
                txtAdultDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));

            }
            txtAdultIncrement.setFocusable(false);
            txtAdultIncrement.setClickable(false);
            txtAdultDecrement.setFocusable(true);
            txtAdultDecrement.setClickable(true);
        }
    }

    /*Method for set Child value*/
    public void setChildValue(int val){

        if(val == 0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                txtChildIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                txtChildDecrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));

            }
            txtChildIncrement.setFocusable(true);
            txtChildIncrement.setClickable(true);
            txtChildDecrement.setFocusable(false);
            txtChildDecrement.setClickable(false);
        }
        else if(val > 0 && val < 5){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                txtChildIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                txtChildDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
            }
            txtChildIncrement.setFocusable(true);
            txtChildIncrement.setClickable(true);
            txtChildDecrement.setFocusable(true);
            txtChildDecrement.setClickable(true);
        }
        else if(val == 5){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                txtChildIncrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));
                txtChildDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
            }
            txtChildIncrement.setFocusable(false);
            txtChildIncrement.setClickable(false);
            txtChildDecrement.setFocusable(true);
            txtChildDecrement.setClickable(true);
        }
    }

    /*Method for set Infants value*/
    public void setInfantsValue(int val){

        if(val == 0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                txtInfantsIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                txtInfantsDecrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));

            }

            txtInfantsIncrement.setFocusable(true);
            txtInfantsIncrement.setClickable(true);
            txtInfantsDecrement.setFocusable(false);
            txtInfantsDecrement.setClickable(false);
        }
        else if(val > 0 && val < 5){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                txtInfantsIncrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                txtInfantsDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
            }
            txtInfantsIncrement.setFocusable(true);
            txtInfantsIncrement.setClickable(true);
            txtInfantsDecrement.setFocusable(true);
            txtInfantsDecrement.setClickable(true);
        }
        else if(val == 5){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                txtInfantsIncrement.setBackground(getResources().getDrawable(R.drawable.light_gray_bg));
                txtInfantsDecrement.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));

            }
            txtInfantsIncrement.setFocusable(false);
            txtInfantsIncrement.setClickable(false);
            txtInfantsDecrement.setFocusable(true);
            txtInfantsDecrement.setClickable(true);
        }
    }
}
