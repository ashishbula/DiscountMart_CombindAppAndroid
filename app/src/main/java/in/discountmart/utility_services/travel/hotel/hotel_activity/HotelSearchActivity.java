package in.discountmart.utility_services.travel.hotel.hotel_activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.travel.hotel.call_hotel_api.HotelApi;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelRoomModel;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model.HotelSearchRequest;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelSearchResponse;
import in.discountmart.utility_services.travel.hotel.hotel_shared_preferance.HotelSharedValues;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.TokenBase64;
import in.discountmart.utility_services.utilities.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelSearchActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout layoutCity;

    public static TextView txtCity;
    public static TextView txtCityId;
    public static EditText edTxtCheckInDate;
    public static EditText edTxtCheckOutDate;
    public static EditText edTxtGuest;
    public static EditText edTxtRoom;
    Button btnSearch;
    View view;

    public static String strCheckInDate="";
    public static String strCheckOutDate="";
    public static String strGuest="";
    public static String strRoom="";
    public static String strCity="";
    public static String strCityCode="";
    public String strDays="";
    public String totRoom="";
    public String totAdult="";
    public String totChild="";
    public String totMember="";

    int adult;
    int child;
    int totMem;
    int rooms;

    public static int yyFromDate ;
    public static int mmFromDate ;
    public static int ddFromDate ;
    String strFlightType="D";
    boolean edit;
    ProgressDialog progressDialog;

    Call<BaseResponse> fetchHotelSearch;
    ArrayList<HotelRoomModel> hotelRoomList;

    /*ArrarList of Object*/
    ArrayList<HotelSearchRequest.RoomSegment> roomSegmentArrayList;
    ArrayList<HotelSearchResponse> hotelSearchArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_hotel_search);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            view=findViewById(android.R.id.content);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Hotel");

            layoutCity=(LinearLayout)findViewById(R.id.hotel_search_act_layout_city);
            txtCity=(TextView)findViewById(R.id.hotel_search_act_txt_city);
            txtCityId=(TextView)findViewById(R.id.flight_search_act_txt_cityid);
            edTxtCheckInDate=(EditText)findViewById(R.id.hotel_search_act_edtxt_checkin_date);
            edTxtCheckOutDate=(EditText)findViewById(R.id.hotel_search_act_edtxt_checkout_date);
            edTxtGuest=(EditText)findViewById(R.id.hotel_search_act_edtxt_guest);
            edTxtRoom=(EditText)findViewById(R.id.hotel_search_act_edtxt_rooms);
            btnSearch=(Button)findViewById(R.id.hotel_search_act_btn_search);

            edit=false;
            hotelRoomList=new ArrayList<HotelRoomModel>();

    /*Update start*/
            Bundle bundle= new Bundle();
            bundle.clear();
            bundle=getIntent().getExtras();
            if(bundle != null){
                hotelRoomList=new ArrayList<HotelRoomModel>();
                hotelRoomList= (ArrayList<HotelRoomModel>) bundle.getSerializable("EditRoomInfo");
                //HotelSharedValues.getInstance().hotelRoomInfoListShared=hotelRoomList;

                String city=bundle.getString("City");
                String chkInDate=bundle.getString("ChkInDate");
                String chkOutDate=bundle.getString("ChkOutDate");
                 adult=bundle.getInt("Adult");
                 child=bundle.getInt("Child");
                 rooms=bundle.getInt("TotRoom");
                 edit=bundle.getBoolean("Edit");

                txtCity.setText(city);
                txtCityId.setText(HotelSharedValues.getInstance().citycode);
                edTxtCheckInDate.setText(chkInDate);
                edTxtCheckOutDate.setText(chkOutDate);
                strRoom= String.valueOf(rooms);

                 totMem=adult+child;
                if(child > 0){
                    totMember= String.valueOf(adult) +" Adult " +"," +String.valueOf(child )+" Child";
                }
                else {
                    totMember= String.valueOf(adult) +" Adult " ;
                }

                edTxtRoom.setText(String.valueOf(rooms)+ " Room");
                edTxtGuest.setText(String.valueOf(totMember));
            }
            else {
                hotelRoomList=new ArrayList<HotelRoomModel>();
            }
/*update end*/

            /*Layout city on click get city name*/
            layoutCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(HotelSearchActivity.this, HotelCityListActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("Type",strFlightType);
                    // bundle.putString("Hint","From City");
                    bundle.putInt("Value",1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });

            /*check-in Date*/
            edTxtCheckInDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectCheckInDateFragment();
                    newFragment.show(getSupportFragmentManager(), "DatePicker");
                }
            });

            /*check-out Date*/
            edTxtCheckOutDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectCheckOutDateFragment();
                    newFragment.show(getSupportFragmentManager(), "DatePicker");
                }
            });

            /*update*/
            /*EDit text Gust on click open HotelSelectRoomActivity and get room and adilt and child */
            edTxtGuest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(HotelSharedValues.getInstance().hotelRoomInfoListShared.size() > 0 && !edTxtGuest.getText().toString().contentEquals("") &&
                            !edTxtRoom.getText().toString().contentEquals("")){
                        //HotelSharedValues.getInstance().hotelRoomInfoListShared=hotelRoomList;
                        edit=true;
                        Intent intent=new Intent(HotelSearchActivity.this, HotelSelectRoomActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("Type",strFlightType);
                        bundle.putSerializable("RoomGuestList",HotelSharedValues.getInstance().hotelRoomInfoListShared);
                        bundle.putBoolean("Edit",edit);
                        bundle.putInt("Value",1);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                    }
                   /* else if(HotelSharedValues.getInstance().hotelRoomInfoListShared.size() == 0 && !edTxtGuest.getText().toString().contentEquals("") &&
                            !edTxtRoom.getText().toString().contentEquals("")) {
                        edit=true;
                        Intent intent=new Intent(HotelSearchActivity.this, HotelSelectRoomActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("Type",strFlightType);
                        bundle.putSerializable("RoomGuestList",HotelSharedValues.getInstance().hotelRoomInfoListShared);
                        bundle.putBoolean("Edit",edit);
                        bundle.putInt("Value",1);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                    }*/
                    else  {
                        edit=false;
                        Intent intent=new Intent(HotelSearchActivity.this, HotelSelectRoomActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("Type",strFlightType);
                        bundle.putSerializable("RoomGuestList",HotelSharedValues.getInstance().hotelRoomInfoListShared);
                        bundle.putBoolean("Edit",edit);
                        bundle.putInt("Value",1);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                    }


                }
            });

            /*update*/
            /*EDit text Room on click open HotelSelectRoomActivity and get room and adilt and child */
            edTxtRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(HotelSharedValues.getInstance().hotelRoomInfoListShared.size() > 0 && !edTxtGuest.getText().toString().contentEquals("") &&
                            !edTxtRoom.getText().toString().contentEquals("")){
                        //HotelSharedValues.getInstance().hotelRoomInfoListShared=hotelRoomList;
                        edit=true;
                        Intent intent=new Intent(HotelSearchActivity.this, HotelSelectRoomActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("Type",strFlightType);
                        bundle.putSerializable("RoomGuestList",HotelSharedValues.getInstance().hotelRoomInfoListShared);
                        bundle.putBoolean("Edit",edit);
                        bundle.putInt("Value",1);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                    }
                   /* else if(HotelSharedValues.getInstance().hotelRoomInfoListShared.size() == 0 && !edTxtGuest.getText().toString().contentEquals("") &&
                            !edTxtRoom.getText().toString().contentEquals("")) {
                        edit=true;
                        Intent intent=new Intent(HotelSearchActivity.this, HotelSelectRoomActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("Type",strFlightType);
                        bundle.putSerializable("RoomGuestList",HotelSharedValues.getInstance().hotelRoomInfoListShared);
                        bundle.putBoolean("Edit",edit);
                        bundle.putInt("Value",1);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                    }*/
                    else  {
                        edit=false;
                        Intent intent=new Intent(HotelSearchActivity.this, HotelSelectRoomActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("Type",strFlightType);
                        bundle.putSerializable("RoomGuestList",HotelSharedValues.getInstance().hotelRoomInfoListShared);
                        bundle.putBoolean("Edit",edit);
                        bundle.putInt("Value",1);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                    }

                }
            });


            /*update*/
            /*Button Search on click get hotel list*/
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(txtCity.getText().toString().equals("")){
                        Toast.makeText(HotelSearchActivity.this,"Please Enter City",Toast.LENGTH_SHORT).show();
                    }
                    else if(edTxtCheckInDate.getText().toString().equals("")){
                        edTxtCheckInDate.setError("Please Check-in date");
                        edTxtCheckInDate.requestFocus();
                    }
                    else if(edTxtCheckOutDate.getText().toString().equals("")){
                        edTxtCheckOutDate.setError("Please Check-out date");
                        edTxtCheckOutDate.requestFocus();
                    }
                    else if(edTxtGuest.getText().toString().equals("")){
                        edTxtGuest.setError("Enter number of guest ");
                        edTxtGuest.requestFocus();
                    }
                    else if(edTxtRoom.getText().toString().equals("")){
                        edTxtRoom.setError("Enter number of room ");
                        edTxtRoom.requestFocus();
                    }
                    else {

                        /*update in 18/09/2019*/
                        String chkInDate=edTxtCheckInDate.getText().toString();
                        String chkOutDate=edTxtCheckOutDate.getText().toString();
                        String []date1=chkInDate.split("-");
                        String chkday=date1[0];
                        String chkMonth= Utilities.convertTextToNumMonth(date1[1]);
                        String chkYear=date1[2];
                        strCheckInDate=chkday+"/"+chkMonth+"/"+chkYear;

                        String []date2=chkOutDate.split("-");
                        String chkOutday=date2[0];
                        String chkOutMonth=Utilities.convertTextToNumMonth(date2[1]);
                        String chkOutYear=date2[2];
                        strCheckOutDate=chkOutday+"/"+chkOutMonth+"/"+chkOutYear;

                        float days=getNoOfDaysBetweenTwoDate(strCheckInDate,strCheckOutDate);
                        int day=Math.round(days);
                         strDays= String.valueOf(day);

                         /*Call Flight api*/
                        if(!ConnectivityUtils.isNetworkEnabled(HotelSearchActivity.this)){
                            Toast.makeText(HotelSearchActivity.this,getResources().getString(R.string.network_error)
                            ,Toast.LENGTH_SHORT).show();
                        }
                        else {
                            getHotelSearch();
                        }
                    }


                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // date picker open for CheckInDate-Date and selected date set to edittext
    public static class SelectCheckInDateFragment extends DialogFragment implements
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
            SetCheckInDate (yy, mm +1, dd);
        }
    }

    public static void SetCheckInDate(int year, int month, int day) {
        String Month = Utilities.convertMonth(month);
        String Day=Utilities.ConvertNumberIntoTwoDigit(String.valueOf(day));
        edTxtCheckInDate.setText(Day + "-" + Month + "-" + year);
        strCheckInDate = (Day + "/" + Utilities.convertMonthNumber(month) + "/" + year );

        yyFromDate = year;
        mmFromDate = month;
        ddFromDate = day;
        String sourceDate1 = day+"/"+Month+"/"+year;
        String sourceDate2 = day+"-"+Month+"-"+year;
        Date nextDate1 = null;
        Date nextDate2 = null;

        SimpleDateFormat format1 = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        try{
            nextDate1  = format1.parse(sourceDate1);
            nextDate2  = format2.parse(sourceDate2);

        }catch (Exception e){
            e.printStackTrace();
        }

        nextDate1= Utilities.addDays(nextDate1, 1);
        nextDate2= Utilities.addDays(nextDate2, 1);
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MMM/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
        String strDate1=formatter1.format(nextDate1);
        String strDate2=formatter2.format(nextDate2);

        //set Next Date as Check out date
        //edTxtCheckOutDate.setText(dd  + "-" + mon + "-" + yy);
        edTxtCheckOutDate.setText(strDate2);
        //strCheckOutDate=(dd + "/" +strMonth+ "/" + yy );
        strCheckOutDate=(strDate1);

        //stringToDate = (yy + "-" + Utilities.convertMonthNumber(mm) + "-" + Utilities.convertdayNumber(dd)).toString();
    }

    // date picker open for CheckOutDate and selected date set to edittext
    public static class SelectCheckOutDateFragment extends DialogFragment implements
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

                if(edTxtCheckOutDate.getText().toString().equals("")){
                    Date todayDate1 = sdf.parse(stringTodayDate);
                    dpd.getDatePicker().setMinDate(todayDate1.getTime());
                }
                else {
                    String []date2=edTxtCheckOutDate.getText().toString().split("-");
                    String chkOutday=date2[0];
                    String chkOutMonth=Utilities.convertTextToNumMonth(date2[1]);
                    String chkOutYear=date2[2];
                    String chkOutDate=chkOutday+"/"+chkOutMonth+"/"+chkOutYear;
                    Date fromDate = sdf.parse(chkOutDate);
                    dpd.getDatePicker().setMinDate(fromDate.getTime());
                }

            } catch (ParseException e) {
                System.out.println(e.toString());            }
            return dpd;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            SetCheckOutDate(yy, mm +1, dd);
        }
    }

    public static void SetCheckOutDate(int year, int month, int day) {
        String Month = Utilities.convertMonth(month);
        edTxtCheckOutDate.setText(day + "-" + Month + "-" + year);
        strCheckOutDate = (day + "/" + Utilities.convertMonthNumber(month) + "/" + year );

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

    public float getNoOfDaysBetweenTwoDate(String date1,String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        float daysBetween = 0;
        try {
            Date chkInDate = sdf.parse(date1);
            Date chkOutDate = sdf.parse(date2);

            long difference = chkOutDate.getTime() - chkInDate.getTime();
            daysBetween = (difference / (1000 * 60 * 60 * 24));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return daysBetween;
    }

    /* Request and Response Hotel Search*/
    public void getHotelSearch(){
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
            String hotelGroupId=loginResponse.getHotelGroupId();


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

                HotelSearchRequest hotelSearchRequest=new HotelSearchRequest();
                hotelSearchRequest.setCheckInDate(strCheckInDate);
                hotelSearchRequest.setCityId(strCityCode);
                hotelSearchRequest.setCountryCode("IN");
                hotelSearchRequest.setEndUserIp("");
                hotelSearchRequest.setGuestNationality("IN");
                hotelSearchRequest.setIsNearBySearchAllowed("true");
                hotelSearchRequest.setMaxRating("5");
                hotelSearchRequest.setMinRating("0");
                hotelSearchRequest.setNoOfNights(strDays);
                hotelSearchRequest.setNoOfRooms(strRoom);
                hotelSearchRequest.setPreferredCurrency("INR");
                hotelSearchRequest.setPreferredHotel("");
                hotelSearchRequest.setResultCount("0");
                hotelSearchRequest.setReviewScore("0");
                hotelSearchRequest.setRoomGuests(getRoomSegment());
                hotelSearchRequest.setTokenId(strToken);
                hotelSearchRequest.setHotelGroupId(hotelGroupId);


                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(hotelSearchRequest);

                strApiRequest=new Gson().toJson(apiRequest);

                Log.e("Value",strApiRequest);
            }catch (Exception e){
                e.printStackTrace();
            }


            fetchHotelSearch=
                    NetworkClient_Utility_1.getInstance(this).create(HotelApi.class).fetchHotelSearch(apiRequest,strToken);

            fetchHotelSearch.enqueue(new Callback<BaseResponse>() {
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
                                    String[] hotelListResponse=Response.getRESPONSE().split("");
                                    if(hotelListResponse.length > 0){
                                        HotelSearchResponse[]hotelList= new Gson().fromJson(Response.getRESP_VALUE(),HotelSearchResponse[].class);
                                        hotelSearchArrayList=new ArrayList<HotelSearchResponse>(Arrays.asList(hotelList));
                                        if(hotelSearchArrayList.size() > 0){


                                            HotelSharedValues.getInstance().chkInDate=edTxtCheckInDate.getText().toString();
                                            HotelSharedValues.getInstance().chkOutDate=edTxtCheckOutDate.getText().toString();
                                            HotelSharedValues.getInstance().city=txtCity.getText().toString();

                                            Bundle bundle=new Bundle();
                                            bundle.putSerializable("Hotel",hotelList);
                                            bundle.putString("City",txtCity.getText().toString());
                                            bundle.putString("ChkInDate",edTxtCheckInDate.getText().toString());
                                            bundle.putString("ChkOutDate",edTxtCheckOutDate.getText().toString());
                                            Intent intent=new Intent(HotelSearchActivity.this, HotelSearchListActivity.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                                        }


                                    }
                                    else {
                                        String toast= "hotel not found..for particular date";
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
                                Toast.makeText(HotelSearchActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(HotelSearchActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(HotelSearchActivity.this, toast, Toast.LENGTH_SHORT).show();
                                // showSnackbar(toast);

                            }
                        }
                        else {
                            //Toast.makeText(HotelSearchActivity.this,"something error may be server / other",Toast.LENGTH_SHORT).show();

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

    public ArrayList<HotelSearchRequest.RoomSegment> getRoomSegment(){

        String age="";
        JsonArray jsonArrayRoom=new JsonArray();
        //String ageArray [] = new String[0];
        ArrayList<HotelRoomModel> tempList= HotelSharedValues.getInstance().hotelRoomInfoListShared;
        roomSegmentArrayList=new ArrayList<HotelSearchRequest.RoomSegment>();


        ArrayList<HotelSearchRequest.RoomSegment> segmentArrayList=new ArrayList<HotelSearchRequest.RoomSegment>();

        if(tempList.size() > 0){
            for(int i=0; i < tempList.size(); i++){
                HotelSearchRequest.RoomSegment roomSegment=new HotelSearchRequest.RoomSegment();
                roomSegment.setNoOfAdults(tempList.get(i).getAdult());
                roomSegment.setNoOfChild(tempList.get(i).getChild());


                if(tempList.get(i).getChild().equals("0")){
                    String[] arrayAge = age.split(",");
                    roomSegment.setChildAge(arrayAge);
                }
                else {
                    if(tempList.get(i).getChildAgeModels().size() > 0){

                        for(int j=0; j < tempList.get(i).getChildAgeModels().size(); j++){
                            if(age.equals("")){

                                age= tempList.get(i).getChildAgeModels().get(j).getAge();
                            }
                            else {

                                age=age + ","+ tempList.get(i).getChildAgeModels().get(j).getAge();
                            }


                        }
                        //String []ageArray = new String[0];
                        //ageArray[j]=age;
                        String[] arrayAge = age.split(",");
                        roomSegment.setChildAge(arrayAge);

                    }
                }


                if(roomSegmentArrayList.size()==0){

                    roomSegmentArrayList=new ArrayList<HotelSearchRequest.RoomSegment>(Collections.singleton(roomSegment));
                    age="";
                    /*if(ageArray.length > 0)
                        ageArray= new String[0];*/

                }
                else {
                    roomSegmentArrayList.add(roomSegment);

                    age="";
                    /*if(ageArray.length > 0)
                        ageArray= new String[0];*/
                }




            }
        }
        jsonArrayRoom = new Gson().toJsonTree(roomSegmentArrayList).getAsJsonArray();
        segmentArrayList = new Gson().fromJson(jsonArrayRoom.toString(), new TypeToken<List<HotelSearchRequest.RoomSegment>>(){}.getType());


        return segmentArrayList;
    }

    @Override
    public void onResume(){
        super.onResume();
        hotelRoomList=new ArrayList<HotelRoomModel>();
    }


}
