package in.discountmart.utility_services.travel.bus.bus_activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.travel.bus.bus_model.bus_request.BusSearchRequest;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusSearchListResponse;
import in.discountmart.utility_services.travel.bus.bus_sharedprference.BusSharedValues;
import in.discountmart.utility_services.travel.bus.call_bus_api.BusServiceApi;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.TokenBase64;
import in.discountmart.utility_services.utilities.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusSearchActivity extends AppCompatActivity {

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
    TextView txtToday;
    TextView txtTomorrow;
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
    String strTodayDate="";
    String strTomorrowDate="";
    String strSelect="";
    String strFromCityCode="";
    String strToCityCode="";
    String strToCityName="";
    String strFromCityName="";
    String edit="";
    public static String departureDate="";
    public static String returnDate="";
    int value;



    public static int yyFromDate ;
    public static int mmFromDate ;
    public static int ddFromDate ;
    ProgressDialog progressDialog;

    Call<BaseResponse> fetchBusSearch;

    //ArrayList<BusSearchRequest> flightSegmentArrayList;

    ArrayList<BusSearchListResponse> busSearchList;
    //ArrayList<FlightSearchRequest.FlightSearch.FlightSources> flightSourcesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bus_search);

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
            getSupportActionBar().setTitle("Bus Search");

            /* text view reference*/
            txtFromCity=(TextView)findViewById(R.id.bus_search_act_txt_fromcity);
            txtFromCitycode=(TextView)findViewById(R.id.bus_search_act_txt_fromcitycode);
            txtToCity=(TextView)findViewById(R.id.bus_search_act_txt_tocity);
            txtToCitycode=(TextView)findViewById(R.id.bus_search_act_txt_tocitycode);
            txtToday=(TextView)findViewById(R.id.bus_search_act_txt_todaydate);
            txtTomorrow=(TextView)findViewById(R.id.bus_search_act_txt_tomorowdate);

            /*EditText Reference*/
            edTxtDepartDate=(EditText)findViewById(R.id.bus_search_act_edtxt_departuredate);

            /*Layout reference*/
            layoutFromCity=(LinearLayout)findViewById(R.id.bus_search_act_layout_from);
            layoutToCity=(LinearLayout)findViewById(R.id.bus_search_act_layout_to);
            /*ImageView Reference*/
            imgRightArrow=(ImageView)findViewById(R.id.bus_img_oneway);
            imgRight_leftArrow=(ImageView)findViewById(R.id.bus_img_twoway);

            btnSearch=(Button)findViewById(R.id.bus_search_act_btn_search);

            imgRight_leftArrow.setVisibility(View.GONE);

            /*Get Intent value and set*/
            Intent intent=getIntent();
            if(intent != null){
                edit=intent.getStringExtra("Edit");

                if(edit.contentEquals("true")){
                    edTxtDepartDate.setText(BusSharedValues.getInstance().busDepDate);
                    txtFromCity.setText(BusSharedValues.getInstance().busFromCityName);
                    txtToCity.setText(BusSharedValues.getInstance().busToCityName);
                    strFromCityCode=BusSharedValues.getInstance().busFromCityID;
                    strFromCityName=BusSharedValues.getInstance().busFromCityName;
                    strToCityCode=BusSharedValues.getInstance().busToCityID;
                    strToCityName=BusSharedValues.getInstance().busToCityName;
                    departureDate=BusSharedValues.getInstance().busDepatureTime;

                }
                else {
                    /*For get Today date and set on edtxtDepartDate*/
                    String dob = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
                    String strDepDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    departureDate=strDepDate;
                    edTxtDepartDate.setText(dob);

                    /*Check condition */
                    if(!edTxtDepartDate.getText().toString().equals("") &&
                            edTxtDepartDate.getText().toString().equals(dob)){

                        txtToday.setTextColor(getResources().getColor(R.color.colorPrimary));
                        txtToday.setEnabled(false);
                    }
                }
            }


           /* else if(!edTxtDepartDate.getText().toString().equals("") &&
                    !edTxtDepartDate.getText().toString().equals(strDepartDate)){

            }*/

            /*Text Tomorrow on click get tomorrow date*/
            txtTomorrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    Date tomorrow = calendar.getTime();
                    String strTomorrowDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(tomorrow);
                    String tomorrowDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(tomorrow);
                    departureDate=strTomorrowDate;
                    edTxtDepartDate.setText(tomorrowDate);

                    txtToday.setEnabled(true);
                    txtToday.setTextColor(getResources().getColor(R.color.gray));
                    txtTomorrow.setTextColor(getResources().getColor(R.color.colorPrimary));
                    txtTomorrow.setEnabled(false);
                }
            });

            /*Text Today on click get today date*/
            txtToday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    Date today = calendar.getTime();
                    String stringTodayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(today);
                    String todayDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(today);
                    departureDate=stringTodayDate;
                    edTxtDepartDate.setText(todayDate);

                    txtToday.setEnabled(false);
                    txtToday.setTextColor(getResources().getColor(R.color.colorPrimary));
                    txtTomorrow.setTextColor(getResources().getColor(R.color.gray));
                    txtTomorrow.setEnabled(true);
                }
            });

            /*Layout from city Click listener*/
            layoutFromCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(BusSearchActivity.this, BusCityListActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("Select","From");
                    // bundle.putString("Hint","From City");
                    bundle.putInt("Value",1);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,1);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });
            /*Layout from city Click listener*/
            layoutToCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(BusSearchActivity.this, BusCityListActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("Select","To");
                    bundle.putString("CityId",strFromCityCode);
                    bundle.putInt("Value",2);
                    // bundle.putString("Hint","To City");
                    intent.putExtras(bundle);
                    startActivityForResult(intent,2);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });

            /*Departure Date*/
            edTxtDepartDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectDepartureDateFragment();
                    newFragment.show(getSupportFragmentManager(), "DatePicker");

                    txtToday.setEnabled(true);
                    txtToday.setTextColor(getResources().getColor(R.color.gray));
                    txtTomorrow.setTextColor(getResources().getColor(R.color.gray));
                    txtTomorrow.setEnabled(true);

                }
            });


            /*Button Flight Search Click Listener*/
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(txtFromCity.getText().toString().equals("Select")){
                            Toast.makeText(context,"Please Select From City",Toast.LENGTH_SHORT).show();
                    }
                    else if(txtToCity.getText().toString().equals("Select")){
                            Toast.makeText(context,"Please Select To City",Toast.LENGTH_SHORT).show();
                    }

                    else if (edTxtDepartDate.getText().toString().equals("")){

                        edTxtDepartDate.setError("Select Date");
                        edTxtDepartDate.requestFocus();
                    }

                    else {

                        strFromCityName=txtFromCity.getText().toString();
                        strToCityName=txtToCity.getText().toString();
                        strDepartDate=departureDate;

                        /*Call api bus search api*/
                        if(!ConnectivityUtils.isNetworkEnabled(context)){
                                Toast.makeText(context,getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                        }
                        else {
                            getBusSearch();
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
        departureDate = (year + "-" + month + "-" + day );

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
        returnDate = (day + "-" + Month + "-" + year );

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


    /* Request and Response Bus Search*/
    public void getBusSearch(){
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

                BusSearchRequest busSearch=new BusSearchRequest();
                busSearch.setDOJ(strDepartDate);
                busSearch.setFromCityID(strFromCityCode);
                busSearch.setToCityID(strToCityCode);
                busSearch.setBusGroupId(loginResponse.getBusGroupId());


                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(busSearch);

                strApiRequest=new Gson().toJson(apiRequest);

                Log.e("Value",strApiRequest);
            }catch (Exception e){
                e.printStackTrace();
            }

            fetchBusSearch=
                    NetworkClient_Utility_1.getInstance(context).create(BusServiceApi.class).fetchBusSearch(apiRequest,strToken);


            fetchBusSearch.enqueue(new Callback<BaseResponse>() {
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
                                    String[] busListResponse=Response.getRESPONSE().split("");
                                    if(busListResponse.length > 0){
                                        BusSearchListResponse[]busList= new Gson().fromJson(Response.getRESP_VALUE(),BusSearchListResponse[].class);
                                        busSearchList=new ArrayList<BusSearchListResponse>(Arrays.asList(busList));
                                        if(busSearchList.size() > 0){

                                            BusSharedValues.getInstance().busDepatureTime=strDepartDate;
                                            BusSharedValues.getInstance().busDepDate=edTxtDepartDate.getText().toString();
                                            BusSharedValues.getInstance().busFromCityID=strFromCityCode;
                                            BusSharedValues.getInstance().busFromCityName=strFromCityName;
                                            BusSharedValues.getInstance().busToCityID=strToCityCode;
                                            BusSharedValues.getInstance().busToCityName=strToCityName;

                                            Bundle bundle=new Bundle();
                                            bundle.putSerializable("BusList",busList);
                                            bundle.putString("FromCity",strFromCityName);
                                            bundle.putString("ToCity",strToCityName);
                                            bundle.putString("DepartDate",edTxtDepartDate.getText().toString());
                                            Intent intent=new Intent(context, BusSearchListActivity.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                                        }

                                    }
                                    else {
                                        String toast= "bus not found..for particular date";
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
                                Toast.makeText(BusSearchActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(BusSearchActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(BusSearchActivity.this, toast, Toast.LENGTH_SHORT).show();
                                // showSnackbar(toast);

                            }
                        }
                        else {
                            //Toast.makeText(context,"something error may be server / other",Toast.LENGTH_SHORT).show();

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
        if ((keyCode == KeyEvent.KEYCODE_BACK ))
        {
            if(progressDialog.isShowing())
                progressDialog.dismiss();

            if (fetchBusSearch != null || fetchBusSearch == null)
                fetchBusSearch.cancel();
            //onBackPressed();

        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        super.onBackPressed();
       /* if (fetchBusSearch != null){
            fetchBusSearch.cancel();
            finish();
        }*/
        finish();
        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if(reqCode==1){
            if(resultCode == Activity.RESULT_OK){
                Bundle bundle=data.getExtras();
                if(bundle != null){
                    strFromCityCode=bundle.getString("CityId");
                    strFromCityName=bundle.getString("City");
                    txtFromCity.setText(strFromCityName);

                }

            }
        }
        else if(reqCode == 2){
            if(resultCode == Activity.RESULT_OK){
                Bundle bundle=data.getExtras();
                if(bundle != null){
                    strToCityCode=bundle.getString("CityId");
                    strToCityName=bundle.getString("City");
                    txtToCity.setText(strToCityName);

                }

            }
        }


    }
}
