package in.discountmart.utility_services.travel.bus.bus_activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.activity.MainActivity_utility;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.travel.bus.bus_fragment.BusSeatsFragment;
import in.discountmart.utility_services.travel.bus.bus_model.BusSeatModel;
import in.discountmart.utility_services.travel.bus.bus_model.bus_request.BusSeatsRequest;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusSearchListResponse;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusSeatsResponse;
import in.discountmart.utility_services.travel.bus.bus_sharedprference.BusSharedValues;
import in.discountmart.utility_services.travel.bus.call_bus_api.BusServiceApi;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusSeatsActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    TextView txtTravelName;
    TextView txtToCity;
    TextView txtFromCity;
    TextView txtDepartureDate;
    TextView txtBusType;
    TextView txtDepartDate;
    ImageView imgHome;
    ImageView imgEdit;
    LinearLayout layoutEdit;
    public static TextView txtFareAmount;
    public static TextView txtTotalSeat;
    public static Button btnNext;
    public static LinearLayout layoutNext;
    View view;
    ProgressDialog progressDialog;

    public static ArrayList<String> arrayListSeatName;
    public static ArrayList<String> arrayListSeatFare;
    public static ArrayList<String> arrayListSeatType;
    public static ArrayList<BusSeatModel> busSeatArrayList;
    public static String stringSeatName;
    //public static String stringSeatFare;
    public static double doubleFare;

    String strTripId = "";
    String strDepartDate = "";
    String strTravelName = "";
    String strBusType = "";
    String strSelectionType = "";
    String strFromCity="";
    String strToCity="";
    String strDepartureDate="";

    BusSearchListResponse busSearchListResponse;

    BusSeatsResponse.BusSeats busSeats[];
    ArrayList<BusSeatsResponse.BusSeats> busSeatsArrayList;
    ArrayList<BusSeatsResponse.BusSeats> busSeatUpperArrayList;
    ArrayList<BusSeatsResponse.BusSeats> busSeatLowerArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bus_seats);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        view = findViewById(android.R.id.content);
        try {
            toolbar = findViewById(R.id.bus_seat_act_toolbar);
            txtToCity= findViewById(R.id.bus_seat_act_toolbar_txt_tocity);
            txtFromCity= findViewById(R.id.bus_seat_act_toolbar_txt_fromcity);
            txtDepartureDate= findViewById(R.id.bus_seat_act_toolbar_txt_depdate);
            txtBusType = findViewById(R.id.bus_seat_act_txt_bus_type);
            txtTravelName = findViewById(R.id.bus_seat_act_txt_travel_name);
            txtDepartDate = findViewById(R.id.bus_seat_act_txt_dep_Date);
            txtFareAmount = findViewById(R.id.bus_seat_act_txt_total_amnt);
            txtTotalSeat = findViewById(R.id.bus_seat_act_txt_total_seats);
            btnNext = (Button) findViewById(R.id.bus_seat_act_btn_continue);
            layoutNext = (LinearLayout) findViewById(R.id.bus_seat_act_layout_nextbtn);
            layoutEdit = (LinearLayout) findViewById(R.id.bus_seat_act_toolbar_layout_edit);
            imgHome=(ImageView)findViewById(R.id.bus_seat_act_toolbar_img_home);
            imgEdit=(ImageView)findViewById(R.id.bus_seat_act_toolbar_img_edit);

            setSupportActionBar(toolbar);

            // toolbar fancy stuff
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            arrayListSeatName = new ArrayList<>();
            arrayListSeatFare = new ArrayList<>();
            arrayListSeatType = new ArrayList<>();
            busSeatArrayList=new ArrayList<BusSeatModel>();
            stringSeatName = "";
            //stringSeatFare="";
            doubleFare = 0.0;

            /*Get Value from bundle*/
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                strTripId = bundle.getString("TripId");
                strTravelName = bundle.getString("Travel");
                strDepartDate = bundle.getString("DepartTime");
                strFromCity = bundle.getString("FromCity");
                strToCity = bundle.getString("ToCity");
                strDepartureDate = bundle.getString("DepartDate");
                strBusType = bundle.getString("BusType");
                strSelectionType = bundle.getString("SelectionType");
                BusSearchListResponse busSearch = (BusSearchListResponse) bundle.getSerializable("BusSearch");

                if (busSearch != null) {
                    busSearchListResponse = busSearch;
                }

                //String departDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(strDepartDate);

                /*Set value in Text Travel name */
                String travelName = "";
                if (strTravelName.length() >= 25) {
                    travelName = strTravelName.substring(0, 25) + "...";
                    txtTravelName.setText(travelName);

                } else {
                    travelName = strTravelName;
                    txtTravelName.setText(travelName);

                }
                /*Set value in Text Bus type */
                String busType = "";
                if (strBusType.length() >= 35) {
                    busType = strBusType.substring(0, 35) + "...";
                    txtBusType.setText(busType);

                } else {
                    busType = strBusType;
                    txtBusType.setText(busType);

                }
                /*Set value in Text Departure date */
                txtDepartDate.setText(strDepartDate);
                /*Set value in Text from,to, city, and date */
                txtFromCity.setText(strFromCity);
                txtToCity.setText(strToCity);
                txtDepartureDate.setText(strDepartureDate);


                /*Get bus seat by call api */
                if (!ConnectivityUtils.isNetworkEnabled(this)) {
                        Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    } else {
                        getBusSeats();
                    }

            }

            /*Chekc if seat is select or not*/
          /*  if(arrayListSeatFare.size() > 0 && arrayListSeatName.size() > 0){

                btnNext.setEnabled(true);

            }
            else {
                btnNext.setEnabled(false);
            }*/

            /*Button next on click*/
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        if (busSeatArrayList.size() > 0 ) {
                            Intent intent=new Intent(BusSeatsActivity.this,BusBoarding_DropPointActivity.class);

                            Bundle dropBundle = new Bundle();
                            BusSharedValues.getInstance().busTravelName=strTravelName;
                            BusSharedValues.getInstance().busType=strBusType;
                            BusSharedValues.getInstance().busDepatureDate=strDepartDate;
                            dropBundle.clear();
                            dropBundle.putSerializable("BusSearch", busSearchListResponse);
                            dropBundle.putString("DepartTime", strDepartDate);
                            dropBundle.putString("Travel", strTravelName);
                            dropBundle.putString("BusType", strBusType);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                            //fragment.setArguments(dropBundle);
                            //replaceFragment1(fragment, "Boarding", dropBundle);

                        } else {
                            Toast.makeText(BusSeatsActivity.this, "Please Select Seat", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            /*Tool bar top home icon on click go to HomeMainActivity_utility*/
            imgHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intenthome=new Intent(BusSeatsActivity.this, MainActivity_utility.class);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intenthome);
                    finish();
                }
            });

            /*Image edit icon on click go BusSearchActivity for edit*/
            layoutEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intenthome=new Intent(BusSeatsActivity.this, BusSearchActivity.class);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intenthome.putExtra("Edit","true");
                    startActivity(intenthome);
                    finish();
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /* Request and Response Bus Search*/
    public void getBusSeats() {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String strApiRequest = "";
            JSONObject object = null;
            LoginResponse loginResponse = new LoginResponse();
            loginResponse = new LoginPreferences_Utility(this).getLoggedinUser();
            String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);

            String strToken = TokenBase64.getToken();

            /*Main Request Model*/
            ApiRequest apiRequest = new ApiRequest();

            try {

                /*Base Request Model*/
                BaseHeaderRequest headerRequest = new BaseHeaderRequest();
                headerRequest.setUserName(new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
                headerRequest.setPassword(new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
                headerRequest.setSponsorFormNo(companyId);
            /*if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/

                /* Bus Seats Request Model*/

                BusSeatsRequest busSeatsRequest = new BusSeatsRequest();
                busSeatsRequest.setTripID(strTripId);


                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(busSeatsRequest);

                strApiRequest = new Gson().toJson(apiRequest);

                Log.e("Value", strApiRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }


            Call<BaseResponse> callFetchBusSeat =
                    NetworkClient_Utility_1.getInstance(this).create(BusServiceApi.class).fetchBusSeats(apiRequest, strToken);

            callFetchBusSeat.enqueue(new Callback<BaseResponse>() {
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
                                else if(!(Response.getRESP_VALUE() == null)  && Response.getRESP_VALUE().contentEquals("Trip is not available")){
                                    Toast.makeText(BusSeatsActivity.this,Response.getRESP_VALUE(),Toast.LENGTH_SHORT).show();
                                }
                                else if (!(Response.getRESP_VALUE() == null) || !(Response.getRESP_VALUE().equals(""))) {
                                    //String[] busSeatsResponse=Response.getRESPONSE().split("");
                                    String maxSeat = "0";

                                    String strResponse = new Gson().toJson(Response.getRESP_VALUE());
                                    JSONObject containerObject = new JSONObject(Response.getRESP_VALUE());

                                    //has method
                                    if (containerObject.has("maxSeatsPerTicket")) {
                                        //get Value of video
                                        maxSeat = containerObject.getString("maxSeatsPerTicket");
                                    } else {
                                        maxSeat = "0";
                                    }

                                    BusSeatsResponse busSeat = new Gson().fromJson(Response.getRESP_VALUE(), BusSeatsResponse.class);


                                    if (busSeat != null) {

                                        busSeats = busSeat.getSeats();
                                        if (busSeats.length > 0) {
                                            busSeatsArrayList = new ArrayList<BusSeatsResponse.BusSeats>(Arrays.asList(busSeats));
                                            if (busSeatsArrayList.size() > 0) {

                                                BusSharedValues.getInstance().busSeatsArrayList = busSeatsArrayList;
                                                busSeatLowerArrayList=new ArrayList<BusSeatsResponse.BusSeats>();
                                                 busSeatUpperArrayList=new ArrayList<BusSeatsResponse.BusSeats>();
                                                if(busSeatsArrayList.size() > 0){
                                                    for (int i=0; i < busSeatsArrayList.size(); i++){
                                                        if (busSeatsArrayList.get(i).getzIndex().equals("0")){

                                                            // busSeatsResponse1.setAvailable(busSeatsArrayList.get(i).getAvailable());
                                                            busSeatLowerArrayList.add(busSeatsArrayList.get(i));
                                                        }

                                                        else{
                                                            busSeatUpperArrayList.add(busSeatsArrayList.get(i));
                                                        }

                                                    }
                                                }



                                                if(busSeatUpperArrayList.size() > 0){
                                                    BusSharedValues.getInstance().busSeatsUpperList=busSeatUpperArrayList;

                                                }
                                                else {
                                                    BusSharedValues.getInstance().busSeatsUpperList=busSeatUpperArrayList;

                                                }
                                                if(busSeatLowerArrayList.size() > 0 ){
                                                    BusSharedValues.getInstance().busSeatsLowerList=busSeatLowerArrayList;
                                                }
                                                else {
                                                    BusSharedValues.getInstance().busSeatsLowerList=busSeatLowerArrayList;
                                                }

                                                BusSeatsFragment fragment = new BusSeatsFragment();
                                                Bundle bundle = new Bundle();
                                                bundle.clear();
                                                bundle.putSerializable("BusSeat", busSeat);
                                                bundle.putString("MaxSeat", maxSeat);

                                               /* fragment.setArguments(bundle);
                                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                                ft.replace(R.id.bus_seat_act_frame, fragment, AppConstants.TAG_HOME);
                                                ft.commit();*/

                                                replaceFragment1(fragment,"BusSeatFragment",bundle);
                                            } else {

                                            }
                                        } else {
                                            String toast = busSeat.getRESP_MSG() + "No Seats ";
                                            // Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                                    .setAction("CLOSE", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                        }
                                                    })
                                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                                    .show();
                                        }


                                    } else {
                                        String toast = busSeat.getRESP_MSG() + busSeat.getRESP_VALUE();
                                        // Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                                        Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                                .setAction("CLOSE", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                    }
                                                })
                                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                                .show();
                                    }
                                }

                            }
                            else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {

                                String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
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
                            else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                Toast.makeText(BusSeatsActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(BusSeatsActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(BusSeatsActivity.this, toast, Toast.LENGTH_SHORT).show();
                                // showSnackbar(toast);

                            }
                        }
                        else {
                            Toast.makeText(BusSeatsActivity.this, "something wrong..", Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void replaceFragment1(Fragment fragment, String tag, Bundle bundle) {

        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.bus_seat_act_frame, fragment, tag);
        fragment.setRetainInstance(true);
        //to add fragment to stack
        ft.addToBackStack(tag);
        try {
            ft.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            ft.commitAllowingStateLoss();
        }
    }

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
            onBackPressed();
            *//*if(progressDialog.isShowing())
                progressDialog.dismiss();
            fetchFlightSearch.cancel();*//*
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        /*if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }*/
        super.onBackPressed();
         finish();
        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }

}
