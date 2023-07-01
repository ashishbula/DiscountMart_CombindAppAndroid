package in.discountmart.utility_services.travel.hotel.hotel_activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import in.discountmart.utility_services.travel.hotel.call_hotel_api.HotelApi;
import in.discountmart.utility_services.travel.hotel.hotel_adapter.HotelRoomAdapter;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model.HotelRoomRequest;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model.HotelRoomsBlockRequest;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model.HotelRoomsDetail;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.BlockRoomDetail;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelRoomBlockResponse;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelRoomCombination;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelRoomResponse;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.RoomDetail;
import in.discountmart.utility_services.travel.hotel.hotel_shared_preferance.HotelSharedValues;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.DividerItemDecoration;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelRoomActivity extends AppCompatActivity implements HotelRoomAdapter.RoomListAdapterListener{
    TextView txtHotelName;
    TextView txtHotelCity;
    TextView txtCheckInDate;
    TextView txtCheckOutDate;
    TextView txtTotalRoom;
    TextView txtTotalGuest;
    AppCompatRatingBar ratingBar;
    RecyclerView recyclerView;
    View view;

    String strHotelCode="";
    String strTraceId="";
    String strResultIndex="";
    String strHotelName="";
    String roomIndex= "";

    String strHotelCity="";
    String strRoom="";
    String strGuest="";
    String strCheckInDate="";
    String strCheckOutDate="";
    String strHotelRating="";
    ProgressDialog progressDialog;

    /*Model class object */
    HotelRoomResponse hotelRoomResponse;
    HotelRoomCombination roomCombination;
    ArrayList<HotelRoomCombination.Combination> combinationList;

    /*Adapter*/
    HotelRoomAdapter hotelRoomAdapter;
    /*Model class array list*/
    ArrayList<RoomDetail> roomDetailArrayList;
    ArrayList<RoomDetail> roomCombArrayList;
    ArrayList<HotelRoomsDetail> roomBlockArrayList;
    ArrayList<Integer> roomIndexList;
    HotelRoomBlockResponse.HotelRoomBlockResult hotelRoomBlockResult;

    ArrayList<BlockRoomDetail> blockRoomDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_hotel_room);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            view=findViewById(android.R.id.content);
            txtHotelName=(TextView)findViewById(R.id.hotel_room_actvity_txt_hotelname);
            txtHotelCity=(TextView)findViewById(R.id.hotel_room_actvity_txt_hotel_city);
            txtCheckInDate=(TextView)findViewById(R.id.hotel_room_actvity_txt_checkIn_date);
            txtCheckOutDate=(TextView)findViewById(R.id.hotel_room_actvity_txt_checkOut_date);
            txtTotalRoom=(TextView)findViewById(R.id.hotel_room_actvity_txt_hotel_room);
            txtTotalGuest=(TextView)findViewById(R.id.hotel_room_actvity_txt_hotel_guest);
            ratingBar=(AppCompatRatingBar)findViewById(R.id.hotel_room_actvity_rating_bar);
            recyclerView=(RecyclerView)findViewById(R.id.hotel_room_actvity_recycler);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Select Room");

            /*Gt value from Hotel Shared Preference and set on text*/
            strCheckInDate= HotelSharedValues.getInstance().chkInDate;
            strCheckOutDate= HotelSharedValues.getInstance().chkOutDate;
            strHotelName= HotelSharedValues.getInstance().hotelName;
            strHotelCity= HotelSharedValues.getInstance().city;
            strHotelRating= HotelSharedValues.getInstance().hotelRating;
            strRoom= String.valueOf(HotelSharedValues.getInstance().totRoom);
            strGuest= String.valueOf(HotelSharedValues.getInstance().totMember);

            strHotelCode=HotelSharedValues.getInstance().hotelCode;
            strResultIndex=HotelSharedValues.getInstance().resultIndex;
            strTraceId=HotelSharedValues.getInstance().traceID;

            /*Set Vlaue on text*/
            txtHotelCity.setText(strHotelCity);
            txtTotalGuest.setText("Guest "+strGuest);
            txtTotalRoom.setText("Room "+ strRoom);
            txtCheckInDate.setText(strCheckInDate);
            txtCheckOutDate.setText(strCheckOutDate);
            if(strHotelRating.contentEquals("0") || strHotelRating.contentEquals(""))
                ratingBar.setRating(0);
            else
                ratingBar.setRating(Float.parseFloat(strHotelRating));

            txtHotelName.setText(strHotelName);

           // roomDetailArrayList=new ArrayList<>();

            //flightSearchList = new ArrayList<>();
           // hotelRoomAdapter = new HotelRoomAdapter(HotelRoomActivity.this, roomDetailArrayList, HotelRoomActivity.this);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(HotelRoomActivity.this,DividerItemDecoration.VERTICAL_LIST,0));
            //recyclerView.setAdapter(hotelRoomAdapter);

            /*Call hotel room api*/
            if(!ConnectivityUtils.isNetworkEnabled(this)){
                Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();
            }
            else {
                getHotelRoom();
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /* Request and Response Hotel Room*/
    public void getHotelRoom(){
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

                HotelRoomRequest hotelInfoRequest=new HotelRoomRequest();
                hotelInfoRequest.setEndUserIp("");
                hotelInfoRequest.setHotelCode(strHotelCode);
                hotelInfoRequest.setResultIndex(strResultIndex);
                hotelInfoRequest.setTokenId("");
                hotelInfoRequest.setTraceId(strTraceId);
                hotelInfoRequest.setHotelGroupId(hotelGroupId);
                hotelInfoRequest.setNoOfRoom(String.valueOf(HotelSharedValues.getInstance().totRoom));



                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(hotelInfoRequest);

                strApiRequest=new Gson().toJson(apiRequest);

                Log.e("Value",strApiRequest);
            }catch (Exception e){
                e.printStackTrace();
            }


            Call<BaseResponse> fetchHotelInfo=
                    NetworkClient_Utility_1.getInstance(this).create(HotelApi.class).fetchHotelRoom(apiRequest,strToken);

            fetchHotelInfo.enqueue(new Callback<BaseResponse>() {
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
                                    hotelRoomResponse =new HotelRoomResponse();
                                    hotelRoomResponse =new Gson().fromJson(Response.getRESP_VALUE(), HotelRoomResponse.class);

                                    /*Check Hotel room response value is null or not*/
                                    if(hotelRoomResponse != null){
                                        HotelSharedValues.getInstance().hotelRoomResponse= hotelRoomResponse;
                                        roomDetailArrayList=new ArrayList<RoomDetail>();
                                        if(hotelRoomResponse.getHotelRoomsDetails() != null && hotelRoomResponse.getRoomCombinations() != null ){

                                            /*Assign room detail value in room array list*/
                                            roomDetailArrayList=hotelRoomResponse.getHotelRoomsDetails();

                                            /*Assign room combination value in combination object and combination list*/
                                            roomCombination=new HotelRoomCombination();
                                            roomCombination=hotelRoomResponse.getRoomCombinations();
                                            combinationList=roomCombination.getRoomCombination();
                                            if(roomDetailArrayList.size() > 0){
                                                //HotelSharedValues.getInstance().roomDetailList=roomDetailArrayList;
                                                ArrayList<RoomDetail> tempRoomList=new ArrayList<RoomDetail>();
                                                tempRoomList.clear();
                                                tempRoomList.addAll(roomDetailArrayList);

                                                hotelRoomAdapter=new HotelRoomAdapter(HotelRoomActivity.this,tempRoomList,HotelRoomActivity.this);
                                                recyclerView.setAdapter(hotelRoomAdapter);
                                            }

                                        }
                                        else {
                                            String toast= "no room find please try other ";
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
                                        String toast= "Something error.. no room find please try other ";
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
                                Toast.makeText(HotelRoomActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(HotelRoomActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(HotelRoomActivity.this, toast, Toast.LENGTH_SHORT).show();
                                // showSnackbar(toast);

                            }
                        }
                        else {
                            //Toast.makeText(HotelRoomActivity.this,"something error may be server / other",Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.business_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home_menu_home) {
            Intent intent=new Intent(HotelRoomActivity.this, MainActivity_utility.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRoomSelected(RoomDetail roomDetail,int pos) {
        try {
            if(roomDetail != null){

                String strInfoSource="";
                ArrayList<RoomDetail> tempRoomList=new ArrayList<RoomDetail>(Arrays.asList(roomDetail));
                HotelSharedValues.getInstance().roomDetail=roomDetail;
                HotelSharedValues.getInstance().roomDetailList=tempRoomList;
                HotelSharedValues.getInstance().roomIndex=roomDetail.getRoomIndex();
                HotelSharedValues.getInstance().perRoomAmount=roomDetail.getPrice().getPublishedPrice();
                HotelSharedValues.getInstance().roomName=roomDetail.getRoomTypeName();
                roomIndex= roomDetail.getRoomIndex();

                /*Call api Hotel Room Block for book room */
                if(!ConnectivityUtils.isNetworkEnabled(HotelRoomActivity.this)){
                    Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                }
                else {
                    getHotelRoomBlock();
                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void getHotelRoomBlock(){
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
                try {

                    /*Base Request Model*/
                    BaseHeaderRequest headerRequest =new BaseHeaderRequest();
                    headerRequest.setUserName(  new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
                    headerRequest.setPassword( new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
                    headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                    headerRequest.setSponsorFormNo(companyId);
            else
                    headerRequest.setSponsorFormNo("");*//*

                    *//* Flight Search Request Model*/

                    HotelRoomsBlockRequest roomBlockRequest=new HotelRoomsBlockRequest();

                    roomBlockRequest.setResultIndex(HotelSharedValues.getInstance().resultIndex);
                    roomBlockRequest.setHotelCode(strHotelCode);
                    roomBlockRequest.setHotelName(strHotelName);
                    roomBlockRequest.setNoOfRooms(strRoom);
                    roomBlockRequest.setRoomIndex(roomIndex);
                    roomBlockRequest.setEndUserIp("");
                    roomBlockRequest.setTokenId("");
                    roomBlockRequest.setTraceId(HotelSharedValues.getInstance().traceID);

                    /*Set Value in Main Request Model*/
                    apiRequest.setHEADER(headerRequest);
                    apiRequest.setDATA(roomBlockRequest);

                    strApiRequest=new Gson().toJson(apiRequest);

                    Log.e("Value",strApiRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }

                Call<BaseResponse> fetchHotelRoomBlockk=
                        NetworkClient_Utility_1.getInstance(this).create(HotelApi.class).fetchHotelRoomBlock(apiRequest,strToken);

                fetchHotelRoomBlockk.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                        try {

                            BaseResponse Response =new BaseResponse();
                            Response=response.body();

                            if(Response != null){
                                if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                    if(Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")){
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
                                    else if(Response.getRESP_VALUE()!= null || ! Response.getRESP_VALUE().equals("")){

                                        HotelRoomBlockResponse roomBlockResponse=
                                                new Gson().fromJson(Response.getRESP_VALUE(), HotelRoomBlockResponse.class);

                                        if(roomBlockResponse != null){
                                            hotelRoomBlockResult=new HotelRoomBlockResponse.HotelRoomBlockResult();
                                            hotelRoomBlockResult=roomBlockResponse.getBlockRoomResult();
                                            if(hotelRoomBlockResult != null){
                                                if(hotelRoomBlockResult.getAvailabilityType().contentEquals("Confirm")
                                                && hotelRoomBlockResult.getResponseStatus().contentEquals("1")){

                                                    blockRoomDetail=new ArrayList<BlockRoomDetail>();
                                                    blockRoomDetail=hotelRoomBlockResult.getHotelRoomsDetails();
                                                    if(blockRoomDetail.size() > 0){
                                                        HotelSharedValues.getInstance().blockroomDetailList=blockRoomDetail;

                                                        Intent intent =new Intent(HotelRoomActivity.this,HotelPassengerInfoActivity.class);
                                                        startActivity(intent);
                                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                                                    }

                                                }
                                                else if(hotelRoomBlockResult.getResponseStatus().equals("2") &&
                                                        hotelRoomBlockResult.getError().getErrorCode().equals("2")){
                                                    String toast=   hotelRoomBlockResult.getError().getErrorMessage();
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
                                                    String toast=  "Select room not available please select other ";
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
                                                String toast=  "This Room not available Please try other ";
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
                                            String toast=  "This Room not available Please try other ";
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

                                }
                                else if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")){
                                    String toast= Response.getRESPONSE()+ "\n" + "Time Expire Please Try Again ";
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
                                    Toast.makeText(HotelRoomActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(HotelRoomActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                }
                                else {

                                    String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                    Toast.makeText(HotelRoomActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    // showSnackbar(toast);

                                }
                            }
                            else {
                                //Toast.makeText(HotelRoomActivity.this,"something error may be server / other",Toast.LENGTH_SHORT).show();

                                String toast=  "Something wrong.. ";
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



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();                        Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
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


        }catch (Exception e){
            e.printStackTrace();
        }
    }



   /* public void onRoomSelected(RoomDetail roomDetail,int pos) {
        try {
            if(roomDetail != null){
                int roomIndex;
                String strInfoSource="";
                ArrayList<RoomDetail> tempRoomList=new ArrayList<RoomDetail>(Arrays.asList(roomDetail));

                //HotelSharedValues.getInstance().roomDetailList=tempRoomList;
                HotelSharedValues.getInstance().roomName=roomDetail.getRoomTypeName();
                roomIndex= Integer.parseInt(roomDetail.getRoomIndex());
                strInfoSource=roomDetail.getInfoSource();

                if(roomDetailArrayList.size() > 0 && roomCombination != null){
                    if(strInfoSource.equals("FixedCombination") && roomCombination.getInfoSource().equals("FixedCombination")){
                        ArrayList<HotelRoomCombination.Combination> tempCombList=new ArrayList<HotelRoomCombination.Combination>();
                        tempCombList=roomCombination.getRoomCombination();
                        if(tempCombList.size() > 0 && tempCombList.size() == 1){
                            ArrayList<Integer> roomIndexList=new ArrayList<Integer>();
                            roomIndexList=tempCombList.get(0).getRoomIndex();
                            if (roomIndexList.size() > 0){
                                for (int i=0; i < roomIndexList.size(); i++){
                                    if(roomIndex==roomIndexList.get(i)){
                                        HotelSharedValues.getInstance().roomDetail=roomDetail;
                                        HotelSharedValues.getInstance().roomDetailList=tempRoomList;
                                        roomCombArrayList.addAll(tempRoomList);
                                    }
                                }
                            }
                        }
                        else if(tempCombList.size() > 1){
                            for (int i=0; i < tempCombList.size(); i++){
                                ArrayList<Integer> roomTempIndexList=new ArrayList<Integer>();
                                roomTempIndexList=tempCombList.get(i).getRoomIndex();

                                for(int j=0; j < roomTempIndexList.size(); j++){
                                    if(roomIndex==roomTempIndexList.get(j)){
                                        roomIndexList=new ArrayList<Integer>();
                                        roomIndexList.addAll(tempCombList.get(i).getRoomIndex());

                                        if(roomIndexList.size() > 0){
                                            roomCombArrayList=new ArrayList<RoomDetail>();
                                            for(int k=0; k < roomIndexList.size(); k++){
                                                for(int l=0; l <roomDetailArrayList.size(); l++){
                                                    int listRoomIndex= Integer.parseInt(roomDetailArrayList.get(l).getRoomIndex());
                                                    if(roomIndexList.get(k).equals(listRoomIndex)){
                                                        roomCombArrayList.add(roomDetailArrayList.get(l));

                                                    }
                                                }
                                            }
                                            //HotelSharedValues.getInstance().roomDetail=roomDetail;
                                            HotelSharedValues.getInstance().roomDetailList=roomCombArrayList;
                                        }

                                    }
                                }
                            }
                        }

                        if(roomCombArrayList.size() > 0){
                            *//*Call room block api *//*
                            getHotelRoomBlock();
                        }

                    }


                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }*/

    /* Request and Response Hotel Room  Block*/
    /*public void getHotelRoomBlock(){
        try {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String strApiRequest="";
            JSONObject object=null;
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_brand(this).getLoggedinUser();
            String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);
            String formno=loginResponse.getFormNo().substring(0,loginResponse.getFormNo().length()-2);
            String mobile=loginResponse.getMobileNo().substring(0,loginResponse.getMobileNo().length()-2);

            String strToken= TokenBase64.getToken();

            *//*Main Request Model*//*
            ApiRequest apiRequest = new ApiRequest();
            try {
                try {

                    *//*Base Request Model*//*
                    BaseHeaderRequest headerRequest =new BaseHeaderRequest();
                    headerRequest.setUserName(  new LoginPreferences_brand(this).getLoggedinUser().getUserName());
                    headerRequest.setPassword( new LoginPreferences_brand(this).getLoggedinUser().getPasswd());
                    headerRequest.setSponsorFormNo(companyId);
           *//* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*//*

                    *//* Flight Search Request Model*//*

                    HotelRoomBlockRequest roomBlockRequest=new HotelRoomBlockRequest();

                    roomBlockRequest.setResultIndex(HotelSharedValues.getInstance().resultIndex);
                    roomBlockRequest.setHotelCode(strHotelCode);
                    roomBlockRequest.setHotelName(strHotelName);
                    roomBlockRequest.setGuestNationality("IN");
                    roomBlockRequest.setNoOfRooms(strRoom);
                    roomBlockRequest.setClientReferenceNo("0");
                    roomBlockRequest.setIsVoucherBooking("true");
                    roomBlockRequest.setHotelRoomsDetails(hotelRoomDetail());
                    roomBlockRequest.setEndUserIp("");
                    roomBlockRequest.setTokenId("");
                    roomBlockRequest.setTraceId(HotelSharedValues.getInstance().traceID);

                    *//*Set Value in Main Request Model*//*
                    apiRequest.setHEADER(headerRequest);
                    apiRequest.setDATA(roomBlockRequest);

                    strApiRequest=new Gson().toJson(apiRequest);

                    Log.e("Value",strApiRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }

                Call<BaseResponse> fetchHotelRoomBlockk=
                        NetworkClient_Utility_1.getInstance(this).create(HotelApi.class).fetchHotelRoomBlock(apiRequest,strToken);

                fetchHotelRoomBlockk.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                        try {

                            BaseResponse Response =new BaseResponse();
                            Response=response.body();

                            if(Response != null){
                                if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                    if(Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")){
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
                                    else if(Response.getRESP_VALUE()!= null || ! Response.getRESP_VALUE().equals("")){

                                        HotelRoomBlockResponse roomBlockResponse=
                                                new Gson().fromJson(Response.getRESP_VALUE(), HotelRoomBlockResponse.class);

                                        Intent intent =new Intent(HotelRoomActivity.this,HotelPassengerInfoActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);


                                    }

                                }
                                else if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")){
                                    String toast= Response.getRESPONSE()+ "\n" + "Time Expire Please Try Again ";
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
                                Toast.makeText(HotelRoomActivity.this,"something error may be server / other",Toast.LENGTH_SHORT).show();
                            }



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();                        Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
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


        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    /*Get Pessagnger Json Array*/
   /* private ArrayList<HotelRoomsDetail> hotelRoomDetail() {
        ArrayList<HotelRoomsDetail> blockArrayList = new ArrayList<HotelRoomsDetail>();
        try {
            JSONArray jSonArray = new JSONArray();
            JsonArray jsonArray=new JsonArray();

            roomBlockArrayList=new ArrayList<HotelRoomsDetail>();

            Collection<FlightBookRequest.FlightPassenger> enums = null;

            if(roomCombArrayList != null && ! roomCombArrayList.isEmpty()){
                for(int i=0;i < roomCombArrayList.size();i++){
                    HotelRoomsDetail hotelRoom = new HotelRoomsDetail();
                    HotelRoomsDetail.RoomPrice roomPrice = new HotelRoomsDetail.RoomPrice();

                    roomPrice.setCurrencyCode(roomCombArrayList.get(i).getPrice().getCurrencyCode());
                    roomPrice.setRoomPrice(roomCombArrayList.get(i).getPrice().getRoomPrice());
                    roomPrice.setTax(roomCombArrayList.get(i).getPrice().getTax());
                    roomPrice.setExtraGuestCharge(roomCombArrayList.get(i).getPrice().getExtraGuestCharge());
                    roomPrice.setChildCharge(roomCombArrayList.get(i).getPrice().getChildCharge());
                    roomPrice.setOtherCharges(roomCombArrayList.get(i).getPrice().getOtherCharges());
                    roomPrice.setDiscount(roomCombArrayList.get(i).getPrice().getDiscount());
                    roomPrice.setPublishedPrice(roomCombArrayList.get(i).getPrice().getPublishedPrice());
                    roomPrice.setPublishedPriceRoundedOff(roomCombArrayList.get(i).getPrice().getPublishedPriceRoundedOff());
                    roomPrice.setOfferedPrice(roomCombArrayList.get(i).getPrice().getOfferedPrice());
                    roomPrice.setOfferedPriceRoundedOff(roomCombArrayList.get(i).getPrice().getOfferedPriceRoundedOff());
                    roomPrice.setAgentCommission(roomCombArrayList.get(i).getPrice().getAgentCommission());
                    roomPrice.setAgentMarkUp(roomCombArrayList.get(i).getPrice().getAgentMarkUp());
                    roomPrice.setServiceTax(roomCombArrayList.get(i).getPrice().getServiceTax());
                    roomPrice.setTDS(roomCombArrayList.get(i).getPrice().getTDS());

                    //flightPassenger.setGender(pessengerInfoList.get(i).getGender());
                    hotelRoom.setID(null);
                    hotelRoom.setRoomIndex(roomCombArrayList.get(i).getRoomIndex());
                    hotelRoom.setRoomTypeCode(roomCombArrayList.get(i).getRoomTypeCode());
                    hotelRoom.setRoomTypeName(roomCombArrayList.get(i).getRoomTypeName());
                    hotelRoom.setRatePlanCode(roomCombArrayList.get(i).getRatePlanCode());
                    hotelRoom.setBedTypeCode(null);
                    hotelRoom.setSmokingPreference("0");
                    hotelRoom.setSupplements(null);
                    hotelRoom.setPrice(roomPrice);
                    hotelRoom.setHotelPassenger(null);
                    hotelRoom.setAmenity(null);
                    hotelRoom.setBedTypes(null);
                    hotelRoom.setCancellationPolicies(null);
                    hotelRoom.setCancellationPolicy(null);
                    hotelRoom.setInclusion(null);
                    hotelRoom.setLastCancelDate(null);

                    roomBlockArrayList.add(hotelRoom);

                }
            }
            //flightPassengerArrayList=new ArrayList<FlightBookRequest.FlightPassenger>(Arrays.asList(flightPassenger));
            jsonArray = new Gson().toJsonTree(roomBlockArrayList).getAsJsonArray();
            blockArrayList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<HotelRoomsDetail>>(){}.getType());


        }catch (Exception e){
            e.printStackTrace();
        }
        return blockArrayList;
    }*/


}
