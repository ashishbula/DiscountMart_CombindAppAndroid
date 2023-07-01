package in.discountmart.utility_services.travel.utility_cab.cab_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
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
import in.discountmart.utility_services.travel.utility_cab.cab_model.cab_request_mdel.CabSearchRequest;
import in.discountmart.utility_services.travel.utility_cab.cab_model.cab_response_model.CabSearchResponse;
import in.discountmart.utility_services.travel.utility_cab.cab_sharedpreferance.CabSharedValues;
import in.discountmart.utility_services.travel.utility_cab.call_cab_api.CabServiceApi;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.DateUtilities;
import in.discountmart.utility_services.utilities.TokenBase64;
import in.discountmart.utility_services.utilities.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CabSearchActivity extends AppCompatActivity {

    View view;
    LinearLayout layout;
    LinearLayout layoutFrom;
    LinearLayout layoutTo;
    public static TextView txtFromCity;
    public static TextView txtToCity;
    public static EditText exTxtDate;
    public static EditText exTxtTime;
    TextView txtAc;
    TextView txtNonAc;
    Button btnSearch;
    ImageView imgRightArrow;
    ImageView imgRight_leftArrow;
    static String strDate="";
    static String strTime="";

    public static int yyFromDate ;
    public static int mmFromDate ;
    public static int ddFromDate ;
    ProgressDialog progressDialog;

    int JurneyType=0;
    String strAdult="0";
    String strChild="0";
    String strInfants="0";
    String strDepartDate="";
    String strDepartTime="";
    String strTodayDate="";
    String strTomorrowDate="";
    String strSelect="";
    String strFromCityCode="";
    String strToCityCode="";
    String strToCityName="";
    String strFromCityName="";
    String acType="";

    ArrayList<CabSearchResponse> cabSearchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_cab_search);
        try {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            view=findViewById(android.R.id.content);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Cab Search");

            // call method
            content();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void content(){
        layout=(LinearLayout)findViewById(R.id.cab_search_act_layout);
        layoutFrom=(LinearLayout)findViewById(R.id.cab_search_act_layout_from);
        layoutTo=(LinearLayout)findViewById(R.id.cab_search_act_layout_to);
        txtFromCity=(TextView)findViewById(R.id.cab_search_act_txt_from);
        txtToCity=(TextView)findViewById(R.id.cab_search_act_txt_tocity);
        txtAc=(TextView)findViewById(R.id.cab_search_act_txt_ac);
        txtNonAc=(TextView)findViewById(R.id.cab_search_act_txt_noac);
        exTxtDate=(EditText) findViewById(R.id.cab_search_act_edtxt_date);
        exTxtTime=(EditText) findViewById(R.id.cab_search_act_edtxt_time);
        btnSearch=(Button) findViewById(R.id.cab_search_act_btn_search);

        /*ImageView Reference*/
        imgRightArrow=(ImageView)findViewById(R.id.cab_img_oneway);
        imgRight_leftArrow=(ImageView)findViewById(R.id.cab_img_twoway);
        acType="true";
        /*TextView clicklistner*/
        txtAc.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    txtNonAc.setBackground(getResources().getDrawable(R.drawable.transparent_bg));
                    txtAc.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                    txtAc.setTextColor(getResources().getColor(R.color.white));
                    txtNonAc.setTextColor(getResources().getColor(R.color.gray));

                    acType="true";

                }
            }
        });


        /*TextView clicklistner*/
        txtNonAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    txtAc.setBackground(getResources().getDrawable(R.drawable.transparent_bg));
                    txtNonAc.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                    txtAc.setTextColor(getResources().getColor(R.color.gray));
                    txtNonAc.setTextColor(getResources().getColor(R.color.white));
                    acType="false";

                }
            }
        });

        /*Layout from city Click listener*/
        layoutFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CabSearchActivity.this, CabCityListActivity.class);
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
        layoutTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CabSearchActivity.this, CabCityListActivity.class);
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
        exTxtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDepartureDateFragment();
                newFragment.show(getSupportFragmentManager(), "DatePicker");

            }
        });

        /*Departure Date*/
        exTxtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectTimeFragment();
                newFragment.show(getSupportFragmentManager(), "TimePicker");

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtFromCity.getText().toString().equals("Select")){
                    Toast.makeText(CabSearchActivity.this,"Please Select From City",Toast.LENGTH_SHORT).show();
                }
                else if(txtToCity.getText().toString().equals("Select")){
                    Toast.makeText(CabSearchActivity.this,"Please Select To City",Toast.LENGTH_SHORT).show();
                }

                else if (exTxtDate.getText().toString().equals("")){
                    Toast.makeText(CabSearchActivity.this,"Please enter date",Toast.LENGTH_SHORT).show();
                }

                else if (exTxtTime.getText().toString().equals("")){
                    Toast.makeText(CabSearchActivity.this,"Please enter time",Toast.LENGTH_SHORT).show();
                }
                else {
                    strFromCityName=txtFromCity.getText().toString();
                    strToCityName=txtToCity.getText().toString();
                    strDepartDate=strDate;
                    strDepartTime=strTime;

                    /*Call api bus search api*/
                    if(!ConnectivityUtils.isNetworkEnabled(CabSearchActivity.this)){
                        Toast.makeText(CabSearchActivity.this,getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                    }
                    else {
                        getCabSearch();
                    }
                }
            }
        });
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
        exTxtDate.setText(day + "-" + Month + "-" + year);
        strDate = (day+ "-" + month + "-" + year );

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

    public static class SelectTimeFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar1 = Calendar.getInstance();
            int hr = calendar1.get(Calendar.HOUR);
            int min = calendar1.get(Calendar.MINUTE);

            TimePickerDialog dpd = new TimePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog_NoActionBar,this, hr, min,false);
            dpd.setTitle("Choose Time:");
            dpd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            //dpd.show();
            return dpd;
        }

        public void onTimeSet(TimePicker picker, int hr, int min) {
            String hour = DateUtilities.ConvertNumberIntoTwoDigit(Integer.toString(hr));
            String minute = DateUtilities.ConvertNumberIntoTwoDigit(Integer.toString(min + 1));
            String time= DateUtilities.timeconverter(hr+""+":"+min);
            exTxtTime.setText(time);
            strTime= time;


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

    /* Request and Response CAb Search*/
    public void getCabSearch(){
        try {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait for showing Cab result.");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String strApiRequest="";
            JSONObject object=null;
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(this).getLoggedinUser();
            String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);

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

                /* Flight Search Request Model*/

                CabSearchRequest busSearch=new CabSearchRequest();
                busSearch.setCabFrom(strDepartDate);
                busSearch.setCabGroupId(loginResponse.getCabGroupId());
                busSearch.setCabFrom(strFromCityName);
                busSearch.setCabTo(strToCityName);
                busSearch.setCabPickupDate(strDepartDate);
                busSearch.setCabPickupTime(strDepartTime);
                busSearch.setCabType(acType);

                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(busSearch);

                strApiRequest=new Gson().toJson(apiRequest);

                Log.e("Value",strApiRequest);
            }catch (Exception e){
                e.printStackTrace();
            }

            Call<BaseResponse >fetchBusSearch=
                    NetworkClient_Utility_1.getInstance(this).create(CabServiceApi.class).fetchCabSearch(apiRequest,strToken);


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

                                if(Response.getRESP_VALUE()==null || Response.getRESP_VALUE().isEmpty()
                                ||Response.getRESP_VALUE().equals("")){
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
                                        CabSearchResponse[]cabList= new Gson().fromJson(Response.getRESP_VALUE(),CabSearchResponse[].class);
                                        cabSearchList=new ArrayList<CabSearchResponse>(Arrays.asList(cabList));
                                        if(cabSearchList.size() > 0){

                                            CabSharedValues.getInstance().cabDepartureDate=strDepartDate;
                                            CabSharedValues.getInstance().cabDepartureTime=strDepartTime;
                                           // CabSharedValues.getInstance().cabArrivalDate=strFromCityCode;
                                           // CabSharedValues.getInstance().cabArrivalTime=strFromCityName;
                                            CabSharedValues.getInstance().cabFromCityName=strFromCityName;
                                            CabSharedValues.getInstance().cabToCityName=strToCityName;
                                           // CabSharedValues.getInstance().totKm=strToCityCode;
                                            //CabSharedValues.getInstance().perKmRate=strToCityCode;
                                            //CabSharedValues.getInstance().cabDepartureDate=strToCityName;

                                            Bundle bundle=new Bundle();
                                            bundle.putSerializable("CabList",cabList);
                                            bundle.putString("FromCity",strFromCityName);
                                            bundle.putString("ToCity",strToCityName);
                                            bundle.putString("DepartDate",exTxtDate.getText().toString());
                                            bundle.putString("DepartTime",exTxtTime.getText().toString());
                                            Intent intent=new Intent(CabSearchActivity.this, CabSearchListActivity.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                                        }

                                    }
                                    else {
                                        String toast= "Cab not found..for particular date";
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
                                else  {
                                    String toast= Response.getRESP_MSG();
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
                                Toast.makeText(CabSearchActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(CabSearchActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(CabSearchActivity.this, toast, Toast.LENGTH_SHORT).show();
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
