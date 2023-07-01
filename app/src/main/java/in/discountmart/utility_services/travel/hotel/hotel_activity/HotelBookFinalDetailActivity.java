package in.discountmart.utility_services.travel.hotel.hotel_activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import in.base.network.NetworkClient_Utility_1;
import in.base.util.DecimalUtils;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.activity.PromocodeActivity;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.travel.hotel.call_hotel_api.HotelApi;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelPassengerModel;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model.HotelDiscountRequest;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelDiscountResponse;
import in.discountmart.utility_services.travel.hotel.hotel_shared_preferance.HotelSharedValues;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.DateUtilities;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelBookFinalDetailActivity extends AppCompatActivity {

    TextView txtHotelName;
    TextView txtHotelCity;
    TextView txtCheckInDate;
    TextView txtCheckOutDate;
    TextView txtTotalRoom;
    TextView txtTotalGuest;
    TextView txtRoomTypeName;
    AppCompatRatingBar ratingBar;

    ImageView imageViewClose;
    TextView txtFromCity;
    TextView txtToCity;
    TextView txtTotFare;
    TextView txtTotAmount;
    TextView txtTotRoom;
    public static TextView txtPromocode;
    public static TextView txtPromocodeDetail;
    public static TextView txtCouponDate;
    TextView txtDiscount;
    TextView txtDiscoutDetail;
    ImageView imgRemovePromo;
    LinearLayout layoutFareAmount;
    public static LinearLayout layoutHavePromo;

    public static LinearLayout layoutPromocode;
    LinearLayout layoutPromo;

    LinearLayout layoutBusDetail;
    LinearLayout layoutPassengerDetail;
    Button btnContinue;
    ProgressDialog progressDialog;
    View view;

    String strPromocode="";
    String strPromoAmount="";
    String strPromoStatus="";
    String strPromoDate="";
    String strDiscount="";
    String otpId="";
    int  totlePaidamount;

    float marginAmount = 0;
    double commisionAmnt = 0;
    double maxCommission = 0;
    int discountAmnt = 0;
    int sendMarginAmnt = 0;
    int totDiscountAmnt = 0;

    String strCity="";
    String strToCity="";
    String strAdult="";
    String strChild="";
    String strInfant="";
    String strCheckInDate="";
    String strCheckOurDate="";
    String strHotelCode="";
    String strTraceId="";
    String strResultIndex="";
    String strHotelName="";

    String strHotelCity="";
    String strRoom="";
    String strRoomName="";
    String strGuest="";
    String strCheckOutDate="";
    String strHotelRating="";

    int totHeader=1;
    int totAdult=0;
    int totChild=0;
    int totRooms=0;
    int totPessenger=0;
    int totOther=1;

    ArrayList<HotelPassengerModel> hotelPassengerList;


    TextView txtTotalFareAmout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_hotel_book_final_detail);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view=findViewById(android.R.id.content);
        try {
            //imageViewClose=(ImageView)findViewById(R.id.room_book_final_detail_act_imag_close);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.str_hotel_book_review));

            layoutPassengerDetail=(LinearLayout)findViewById(R.id.room_book_final_detail_act_layout_passenger_detail);

            txtHotelName=(TextView)findViewById(R.id.room_book_final_detail_act_txt_hotelname);
            txtHotelCity=(TextView)findViewById(R.id.room_book_final_detail_act_txt_hotel_city);
            txtRoomTypeName=(TextView)findViewById(R.id.room_book_final_detail_act_txt_roomName);
            txtCheckInDate=(TextView)findViewById(R.id.room_book_final_detail_act_txt_checkIn_date);
            txtCheckOutDate=(TextView)findViewById(R.id.room_book_final_detail_act_txt_checkOut_date);
            txtTotalRoom=(TextView)findViewById(R.id.room_book_final_detail_act_txt_hotel_room);
            txtTotalGuest=(TextView)findViewById(R.id.room_book_final_detail_act_txt_hotel_guest);
            ratingBar=(AppCompatRatingBar)findViewById(R.id.room_book_final_detail_act_rating_bar);
            txtTotFare=(TextView)findViewById(R.id.room_book_final_detail_act_txt_total_amount);
            txtDiscount=(TextView)findViewById(R.id.room_book_final_detail_act_txt_discount);
            txtDiscoutDetail=(TextView)findViewById(R.id.room_book_final_detail_act_txt_dis_detail);
            txtTotalFareAmout=(TextView)findViewById(R.id.room_book_final_detail_act_txt_total_amnt);
            txtTotRoom=(TextView)findViewById(R.id.room_book_final_detail_act_txt_total_room);
            btnContinue=(Button)findViewById(R.id.room_book_final_detail_act_btn_continue);
            layoutPromo=(LinearLayout)findViewById(R.id.room_book_final_detail_act_layout_promo);
            layoutFareAmount=(LinearLayout)findViewById(R.id.room_book_final_detail_act_layout_fareamout);
            layoutPromocode=(LinearLayout)findViewById(R.id.room_book_final_detail_act_layout_promocode);
            layoutHavePromo=(LinearLayout)findViewById(R.id.room_book_final_detail_act_layout_have_promo);
            txtPromocode=(TextView)findViewById(R.id.room_book_final_detail_act_txt_promocode);
            txtPromocodeDetail=(TextView)findViewById(R.id.room_book_final_detail_act_txt_cashback_detail);
            txtCouponDate=(TextView)findViewById(R.id.room_book_final_detail_act_txt_promo_expire_Date);
            imgRemovePromo=(ImageView)findViewById(R.id.room_book_final_detail_act_img_remove_promo);

            /*Gt value from Hotel Shared Preference and set on text*/
            strCheckInDate= HotelSharedValues.getInstance().chkInDate;
            strCheckOutDate= HotelSharedValues.getInstance().chkOutDate;
            strHotelName= HotelSharedValues.getInstance().hotelName;
            strRoomName= HotelSharedValues.getInstance().roomName;
            strHotelCity= HotelSharedValues.getInstance().city;
            strHotelRating= HotelSharedValues.getInstance().hotelRating;
            strRoom= String.valueOf(HotelSharedValues.getInstance().totRoom);
            strGuest= String.valueOf(HotelSharedValues.getInstance().totMember);

            strHotelCode=HotelSharedValues.getInstance().hotelCode;
            strResultIndex=HotelSharedValues.getInstance().resultIndex;
            strTraceId=HotelSharedValues.getInstance().traceID;
            totlePaidamount=HotelSharedValues.getInstance().totAmount;


            /*Set default blank value to promo*/
            HotelSharedValues.getInstance().hotelPromocode="";
            HotelSharedValues.getInstance().hotelPromoAmount="";
            //SharedPrefrence.setPromoDiscount(this,"");

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
            txtRoomTypeName.setText(strRoomName);
            txtTotFare.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(totlePaidamount));

            btnContinue.setText("Proceed to pay " );
            //btnContinue.setText("Proceed to pay  " + getResources().getString(R.string.rs_symbol)+"" + String.valueOf(totlePaidamount));
            /*Set value for bottom base fare*/
            txtTotRoom.setText("Base fare for "+ strRoom+ " room");
            txtTotalFareAmout.setText(getResources().getString(R.string.rs_symbol)+" "+totlePaidamount);

            layoutHavePromo.setVisibility(View.VISIBLE);
            layoutPromocode.setVisibility(View.GONE);

            /*Call Hotel Book Margin */
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
                getHotelRoomDiscount();
            }
            /* GEt value from bundle*/
            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                hotelPassengerList = new ArrayList<HotelPassengerModel>();
                hotelPassengerList= (ArrayList<HotelPassengerModel>) bundle.getSerializable("PassengerInfo");

            }

            //hotelPassengerList=HotelSharedValues.getInstance().hotelPassengerModelArrayList;


            if(hotelPassengerList.size() > 0){
               // HotelSharedValues.getInstance().hotelPassengerModelArrayList=hotelPassengerList;
                showPassengerDetail(hotelPassengerList);
            }

            /*Layout havePromo click listener for promocode list*/
            layoutPromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String strHotel="H";
                    Intent intent1 = new Intent(HotelBookFinalDetailActivity.this, PromocodeActivity.class);
                    intent1.putExtra("ServiceType",strHotel);
                    intent1.putExtra("ServiceId","8");
                    startActivityForResult(intent1,8);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                }
            });

            /*Image cross for promo on click*/
            imgRemovePromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Save Promo Code and amount in Shared Preferance*/
                   // SharedPrefrence.setPromocode(HotelBookFinalDetailActivity.this,"");
                   // SharedPrefrence.setPromoDiscount(HotelBookFinalDetailActivity.this,"");

                    HotelSharedValues.getInstance().hotelPromocode="";
                    HotelSharedValues.getInstance().hotelPromoAmount="";

                    txtPromocode.setText("");
                    txtPromocodeDetail.setText("");
                    txtCouponDate.setText("");

                    layoutHavePromo.setVisibility(View.VISIBLE);
                    layoutPromocode.setVisibility(View.GONE);
                }
            });

            /*Button Continue click listener*/
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(HotelBookFinalDetailActivity.this, OtpAndHotelPaymentActivity.class);

                    Bundle bundle1=new Bundle();
                    bundle1.putSerializable("PassengerInfo",hotelPassengerList);
                    HotelSharedValues.getInstance().hotelPassengerModelArrayList.clear();
                    HotelSharedValues.getInstance().hotelPassengerModelArrayList.addAll(hotelPassengerList);
                    intent.putExtras(bundle1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                }
            });

            /*Layour base fare on click go in base fare activity*/
            layoutFareAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(HotelBookFinalDetailActivity.this,HotelBookBaseFareDetailActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Method for passenger detail*/
    public void showPassengerDetail(ArrayList<HotelPassengerModel> infoList){

        if(layoutPassengerDetail != null)
            layoutPassengerDetail.removeAllViews();
        for (int i = 0; i < infoList.size(); i++) {

            LayoutInflater mInflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mInflator.inflate(R.layout.utility_room_book_final_detail_passenger_item, null);

            TextView txtTitle;
            TextView txtSeatCount;
            TextView txtPassengerName;
            TextView txtPassengerAge;
            TextView txtPassengerGender;



            txtSeatCount=(TextView)view.findViewById(R.id.room_book_detail_act_txt_count);
            txtTitle=(TextView)view.findViewById(R.id.room_book_detail_act_txt_pass_type);
            txtPassengerName=(TextView)view.findViewById(R.id.room_book_detail_act_txt_passenger_name);
            txtPassengerAge=(TextView)view.findViewById(R.id.room_book_detail_act_txt_passenger_age);
            txtPassengerGender=(TextView)view.findViewById(R.id.room_book_detail_act_txt_gender);



            if(infoList.get(i).getType()==1){
                /*if(infoList.get(i).getSeatType().equals("1")){
                    txtSeatType.setText("Seat");
                }
                else {
                    txtSeatType.setText("Sleeper");
                }*/

                int adultcount=i;
                txtSeatCount.setText(String.valueOf(adultcount+1));
                txtPassengerName.setText(infoList.get(i).getNametitle()+" "+infoList.get(i).getName());
                if(infoList.get(i).getPessengerType().equals("A"))
                    txtTitle.setText("Adult");
                else
                    txtTitle.setText("Child");
                txtPassengerAge.setText(infoList.get(i).getDob());
                txtPassengerGender.setText(infoList.get(i).getGender());
            }
            else if(infoList.get(i).getType()==2){
                break;
                //return;

            }

            layoutPassengerDetail.addView(view);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        try {
            int compressionRatio=4;
            HotelSharedValues.getInstance().hotelPromocode="";
            HotelSharedValues.getInstance().hotelPromoAmount="";

            //SharedPrefrence.setPromocode(this,"");
            //SharedPrefrence.setPromoDiscount(this,"");

            if(requestCode == 8){
                if(resultCode == Activity.RESULT_OK){
                    strPromocode=data.getStringExtra("PromoCode");
                    strPromoAmount=data.getStringExtra("PromoAmount");
                    strPromoStatus=data.getStringExtra("Status");
                    strPromoDate=data.getStringExtra("Date");

                    if(strPromoStatus.equals("Unused")){

                        layoutHavePromo.setVisibility(View.GONE);
                        layoutPromocode.setVisibility(View.VISIBLE);
                        double promo= Double.parseDouble(strPromoAmount);
                        int promoAmnt=(int)Math.round(promo);
                        /*Save Promo Code and amount in Shared Preferance*/
                        if(promoAmnt < totlePaidamount){
                            HotelSharedValues.getInstance().hotelPromocode=strPromocode;
                            HotelSharedValues.getInstance().hotelPromoAmount=String.valueOf(promoAmnt);

                            //SharedPrefrence.setPromocode(this,strPromocode);
                            //SharedPrefrence.setPromoDiscount(this,String.valueOf(promoAmnt));

                            txtPromocode.setText(strPromocode);
                            txtPromocodeDetail.setText(getResources().getString(R.string.str_promo_cashbck_detail)+
                                    " "+getResources().getString(R.string.rs_symbol)+ "" +String.valueOf(promoAmnt));
                            txtCouponDate.setText("Coupon will be expiry on date:- "+ DateUtilities.SpiltandConvertDate(strPromoDate));

                        }
                        else if(promoAmnt > totlePaidamount){
                             HotelSharedValues.getInstance().hotelPromocode=strPromocode;
                            HotelSharedValues.getInstance().hotelPromoAmount=String.valueOf(totlePaidamount);
                            //SharedPrefrence.setPromocode(this,strPromocode);
                            //SharedPrefrence.setPromoDiscount(this,String.valueOf(totlePaidamount));
                            txtPromocode.setText(strPromocode);
                            txtPromocodeDetail.setText(getResources().getString(R.string.str_promo_cashbck_detail)+
                                    " "+getResources().getString(R.string.rs_symbol)+ "" +String.valueOf(totlePaidamount));
                            txtCouponDate.setText("Coupon will be expiry on date:- "+ DateUtilities.SpiltandConvertDate(strPromoDate));

                        }
                        else if(promoAmnt == totlePaidamount){
                            HotelSharedValues.getInstance().hotelPromocode=strPromocode;
                            HotelSharedValues.getInstance().hotelPromoAmount=String.valueOf(promoAmnt);

                            //SharedPrefrence.setPromocode(this,strPromocode);
                            //SharedPrefrence.setPromoDiscount(this,String.valueOf(promoAmnt));
                            txtPromocode.setText(strPromocode);
                            txtPromocodeDetail.setText(getResources().getString(R.string.str_promo_cashbck_detail)+
                                    " "+getResources().getString(R.string.rs_symbol)+ "" +String.valueOf(promoAmnt));
                            txtCouponDate.setText("Coupon will be expiry on date:- "+ DateUtilities.SpiltandConvertDate(strPromoDate));
                        }



                    }
                    else {
                        Toast.makeText(HotelBookFinalDetailActivity.this,"You cann't use this because coupon is already used by use",
                                Toast.LENGTH_SHORT).show();
                        layoutHavePromo.setVisibility(View.VISIBLE);
                        layoutPromocode.setVisibility(View.GONE);
                        double promo= Double.parseDouble(strPromoAmount);
                        int promoAmnt=(int)Math.round(promo);
                        /*Save Promo Code and amount in Shared Preferance*/
                        HotelSharedValues.getInstance().hotelPromoAmount="";
                        HotelSharedValues.getInstance().hotelPromocode="";

                        txtPromocode.setText("");
                        txtPromocodeDetail.setText("");
                        txtCouponDate.setText("");
                    }
                }
            }

        }catch (Exception e) {
            Toast.makeText(HotelBookFinalDetailActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    /* Request and Response api Hotel Room Discount*/
    public void getHotelRoomDiscount() {
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

            //Main Request Model
            BaseHeaderRequest headerRequest = new BaseHeaderRequest();
            headerRequest.setUserName(loginResponse.getUserName());
            headerRequest.setPassword(loginResponse.getPasswd());
            headerRequest.setSponsorFormNo(companyId);
//            if(loginResponse.getMemMode().equals("D"))
//                headerRequest.setSponsorFormNo(companyId);
//            else
//                headerRequest.setSponsorFormNo("");

            // Bus Margin Request Model
            HotelDiscountRequest marginRequest = new HotelDiscountRequest();
            marginRequest.setGroupID(new LoginPreferences_Utility(this).getLoggedinUser().getHotelGroupId());
            marginRequest.setSponsorFormNo(companyId);

            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(marginRequest);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Call<BaseResponse> fetchBusMargin =
                NetworkClient_Utility_1.getInstance(this).create(HotelApi.class).fetchHotelRoomMargin(apiRequest, strToken);

        fetchBusMargin.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    BaseResponse Response = new BaseResponse();
                    Response = response.body();
                    if (Response != null) {
                        if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                            if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                String strResponse = Response.getRESP_VALUE();
                                HotelDiscountResponse marginResponse = new Gson().fromJson(strResponse, HotelDiscountResponse.class);
                                commisionAmnt = Float.parseFloat(marginResponse.getDiscount());
                                marginAmount=Float.parseFloat(marginResponse.getHotelMarginInAmount());
                                maxCommission=Double.parseDouble(marginResponse.getMaxCommission());
                                // btnContinue.setVisibility(View.VISIBLE);
                                int fareAmount=0;

                                if (commisionAmnt != 0 || marginAmount !=0 ) {
                                    double dis = getComission(commisionAmnt);
                                    //int disount =(int) Math.round(dis); // 3
                                    discountAmnt = (int) Math.round(dis);
                                    sendMarginAmnt = (int) Math.round(marginAmount);

                                    totDiscountAmnt = discountAmnt +sendMarginAmnt;
                                    if(totDiscountAmnt > maxCommission){
                                        int maxDist=(int)Math.round(maxCommission);
                                        HotelSharedValues.getInstance().totDiscount = maxDist;
                                    }
                                    else {
                                        HotelSharedValues.getInstance().totDiscount = totDiscountAmnt;
                                    }

                                    HotelSharedValues.getInstance().Discount = discountAmnt;
                                    HotelSharedValues.getInstance().margin = sendMarginAmnt;
                                    fareAmount = totlePaidamount ;

                                } else {
                                    double dis = getComission(commisionAmnt);
                                    //int disount =(int) Math.round(dis); // 3
                                    discountAmnt = (int) Math.round(dis);
                                    sendMarginAmnt = (int) Math.round(marginAmount);

                                    totDiscountAmnt = discountAmnt +sendMarginAmnt;
                                    if(totDiscountAmnt > maxCommission){
                                        int maxDist=(int)Math.round(maxCommission);
                                        HotelSharedValues.getInstance().totDiscount = maxDist;
                                    }
                                    else {
                                        HotelSharedValues.getInstance().totDiscount = totDiscountAmnt;
                                    }
                                    HotelSharedValues.getInstance().Discount = discountAmnt;
                                    HotelSharedValues.getInstance().margin = sendMarginAmnt;
                                }


                            } else if (Response.getRESP_VALUE().isEmpty()|| Response.getRESP_VALUE().equals("")) {

                                btnContinue.setVisibility(View.GONE);
                                String toast = Response.getRESP_MSG();
                                //Toast.makeText(SelectFlightActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                        else  if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            //Toast.makeText(SelectFlightActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(HotelBookFinalDetailActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(HotelBookFinalDetailActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(HotelBookFinalDetailActivity.this, toast, Toast.LENGTH_SHORT).show();
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
    }

    public double getComission ( double commision){

        double intcommision = 0;
        double comm;

        intcommision = (totlePaidamount * commision) / 100;

        comm = DecimalUtils.round(intcommision, 2);


        return comm;
    }
}
