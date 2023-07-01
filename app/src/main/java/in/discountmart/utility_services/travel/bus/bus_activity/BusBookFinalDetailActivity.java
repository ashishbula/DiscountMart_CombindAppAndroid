package in.discountmart.utility_services.travel.bus.bus_activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
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

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import in.base.network.NetworkClient_Utility_1;
import in.base.util.DecimalUtils;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.activity.PromocodeActivity;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.request_model.SendOtpRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.sharedpreferences.SharedPrefrence_Utility;
import in.discountmart.utility_services.travel.bus.bus_model.BusPassengerModel;
import in.discountmart.utility_services.travel.bus.bus_model.bus_request.BusBookDiscount;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusDiscountResponse;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusSearchListResponse;
import in.discountmart.utility_services.travel.bus.bus_sharedprference.BusSharedValues;
import in.discountmart.utility_services.travel.bus.call_bus_api.BusServiceApi;
import in.discountmart.utility_services.travel.flight.call_flight_api.FlightApi;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.DateUtilities;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusBookFinalDetailActivity extends AppCompatActivity {


    ImageView imageViewClose;
    TextView txtFromCity;
    TextView txtToCity;
    TextView txtTotFare;
    public static TextView txtPromocode;
    public static TextView txtPromocodeDetail;
    public static TextView txtCouponDate;
    TextView txtDiscount;
    TextView txtDiscoutDetail;
    TextView txtTotalAmount;
    TextView txtTotalPassenger;
    ImageView imgRemovePromo;
    LinearLayout layoutFareAmount;;
    public static LinearLayout layoutHavePromo;

    public static LinearLayout layoutPromocode;
    LinearLayout layoutPromo;

    LinearLayout layoutBusDetail;
    LinearLayout layoutPassengerDetail;
    Button btnContinue;
    ProgressDialog progressDialog;
    View view;

    ArrayList<BusPassengerModel> pessengerInfoArrayList;
    ArrayList<BusSearchListResponse> selectBusarrayList;
    ArrayList<BusPassengerModel> pessengerFooterArrayList;

    String strPromocode="";
    String strPromoAmount="";
    String strPromoStatus="";
    String strPromoDate="";
    String strDiscount="";
    String otpId="";
    int  totlePaidamount;

    float marginAmount = 0;
    double commisionAmnt = 0;
    int discountAmnt = 0;
    int sendMarginAmnt = 0;
    int totDiscountAmnt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bus_book_final_detail);
        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        view=findViewById(android.R.id.content);
        try {

            imageViewClose=(ImageView)findViewById(R.id.bus_book_detail_act_imag_close);

            layoutPassengerDetail=(LinearLayout)findViewById(R.id.bus_book_detail_act_layout_passenger_detail);
            layoutBusDetail=(LinearLayout)findViewById(R.id.bus_book_detail_act_layout_bus_detail);
            txtFromCity=(TextView)findViewById(R.id.bus_book_detail_act_txt_fromcity);
            txtToCity=(TextView)findViewById(R.id.bus_book_detail_act_txt_tocity);
            txtTotFare=(TextView)findViewById(R.id.bus_book_detail_act_txt_total_amount);
            txtDiscount=(TextView)findViewById(R.id.bus_book_detail_act_txt_discount);
            txtDiscoutDetail=(TextView)findViewById(R.id.bus_book_detail_act_txt_dis_detail);
            txtTotalAmount=(TextView)findViewById(R.id.bus_book_detail_act_txt_total_amnt);
            txtTotalPassenger=(TextView)findViewById(R.id.bus_book_detail_act_txt_total_travelor);
            btnContinue=(Button)findViewById(R.id.bus_book_detail_act_btn_continue);
            layoutPromo=(LinearLayout)findViewById(R.id.bus_book_detail_act_layout_promo);
            layoutPromocode=(LinearLayout)findViewById(R.id.bus_book_detail_act_layout_promocode);
            layoutHavePromo=(LinearLayout)findViewById(R.id.bus_book_detail_act_layout_have_promo);
            layoutFareAmount=(LinearLayout)findViewById(R.id.bus_book_detail_act_layout_fareamout);
            txtPromocode=(TextView)findViewById(R.id.bus_book_detail_act_txt_promocode);
            txtPromocodeDetail=(TextView)findViewById(R.id.bus_book_detail_act_txt_cashback_detail);
            txtCouponDate=(TextView)findViewById(R.id.bus_book_detail_act_txt_promo_expire_Date);
            imgRemovePromo=(ImageView)findViewById(R.id.bus_book_detail_act_img_remove_promo);


            /*Get Intent Value from previous activity*/
            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                pessengerInfoArrayList=new ArrayList<BusPassengerModel>();
                pessengerInfoArrayList= (ArrayList<BusPassengerModel>) bundle.getSerializable("PassengerInfo");

            }

            /*Set default promo value in shared*/
            SharedPrefrence_Utility.setPromocode(this,"");
            SharedPrefrence_Utility.setPromoDiscount(this,"");
            BusSharedValues.getInstance().busPromocode = "";
            BusSharedValues.getInstance().busPromoAmount = "";


            assert pessengerInfoArrayList != null;
            if(pessengerInfoArrayList.size() > 0){
                showPassengerDetail(pessengerInfoArrayList);
            }

            selectBusarrayList= BusSharedValues.getInstance().busSelectArrayList;
            if(selectBusarrayList.size() > 0){

                showSelectFlightDetail(selectBusarrayList);
            }
            totlePaidamount= (int) BusSharedValues.getInstance().TotalFare;

            /*Get Value FORM Bus Shared Preference*/
            txtToCity.setText(BusSharedValues.getInstance().busToCityName);
            txtFromCity.setText(BusSharedValues.getInstance().busFromCityName);

            int fareAmount=0;
            fareAmount = totlePaidamount ;

            txtTotalAmount.setText(getResources().getString(R.string.rs_symbol) + "" + fareAmount);
            String totalSeat= String.valueOf(BusSharedValues.getInstance().busSeatModelArrayList.size());
            txtTotalPassenger.setText("Base fare for" + totalSeat+ " Seats" );
            txtTotFare.setText(getResources().getString(R.string.rs_symbol)+"" +String.valueOf(totlePaidamount));
            btnContinue.setText("Proceed to pay  " + getResources().getString(R.string.rs_symbol)+"" + String.valueOf(totlePaidamount));
            BusSharedValues.getInstance().totPaidAmount = fareAmount;

            /*Show Promo or not*/
           /* LoginResponse loginPreferences=new LoginResponse();
            loginPreferences = new LoginPreferences_brand(this).getLoggedinUser();
            if(loginPreferences != null){
                if(loginPreferences.getMemMode().equals("D")){
                    layoutPromo.setVisibility(View.VISIBLE);
                }
                else {
                    layoutPromo.setVisibility(View.GONE);
                }
            }*/

            /*Call Bus discount api*/
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
                getBusDiscount();
            }
            /*Remove Promo coupon on promo close image click*/

            imgRemovePromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    BusSharedValues.getInstance().busPromocode = "";
                    BusSharedValues.getInstance().busPromoAmount = "";

                    //SharedPrefrence.setPromocode(BusBookFinalDetailActivity.this,"");
                   // SharedPrefrence.setPromoDiscount(BusBookFinalDetailActivity.this,"");

                    txtPromocode.setText("");
                    txtPromocodeDetail.setText("");
                    txtCouponDate.setText("");

                    layoutHavePromo.setVisibility(View.VISIBLE);
                    layoutPromocode.setVisibility(View.GONE);

                }
            });
            /*image close click listener*/
            imageViewClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });


            /*Layout havePromo click listener for promocode list*/
            layoutPromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String strBus="B";
                    Intent intent1 = new Intent(BusBookFinalDetailActivity.this, PromocodeActivity.class);
                    intent1.putExtra("ServiceType",strBus);
                    intent1.putExtra("ServiceId","7");
                    startActivityForResult(intent1,7);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                }
            });

            /*Button Continue click listener*/
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(BusBookFinalDetailActivity.this, OtpAndBusBookPaymentActivity.class);

                    Bundle bundle1=new Bundle();
                    bundle1.putSerializable("PassengerInfo",pessengerInfoArrayList);
                    BusSharedValues.getInstance().buspessengerInfoList=pessengerInfoArrayList;
                    intent.putExtras(bundle1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                }
            });

            /*Layout base detail on click*/
            layoutFareAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(BusBookFinalDetailActivity.this,BusBookFareDetailActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /*Method for passenger detail*/
    public void showPassengerDetail(ArrayList<BusPassengerModel> infoList){

        if(layoutPassengerDetail != null)
            layoutPassengerDetail.removeAllViews();
        for (int i = 0; i < infoList.size(); i++) {

            LayoutInflater mInflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mInflator.inflate(R.layout.utility_bus_book_final_detail_passenger_item, null);

            TextView txtAge;
            TextView txtSeatCount;
            TextView txtPassengerName;
            TextView txtSeatName;

            txtSeatCount=(TextView)view.findViewById(R.id.bus_book_detail_act_txt_seat_count);
            txtSeatName=(TextView)view.findViewById(R.id.bus_book_detail_act_txt_seat_name);
            txtPassengerName=(TextView)view.findViewById(R.id.bus_book_detail_act_txt_passenger_name);
            txtAge=(TextView)view.findViewById(R.id.bus_book_detail_act_txt_passenger_age);



            if(infoList.get(i).getType()==1){
                /*if(infoList.get(i).getSeatType().equals("1")){
                    txtSeatType.setText("Seat");
                }
                else {
                    txtSeatType.setText("Sleeper");
                }*/

                int adultcount=infoList.get(i).getTotalSeat();
                txtSeatCount.setText(String.valueOf(adultcount));
                txtPassengerName.setText(infoList.get(i).getName());
                txtSeatName.setText(infoList.get(i).getSeat());
                txtAge.setText(infoList.get(i).getAge());
            }
            else if(infoList.get(i).getType()==2){
                break;
                //return;

            }

            layoutPassengerDetail.addView(view);
        }
    }

    /*Method for passenger detail*/
    public void showSelectFlightDetail(ArrayList<BusSearchListResponse> selectBusarrayList){
        if(layoutBusDetail != null)
            layoutBusDetail.removeAllViews();

        for (int j=0; j < selectBusarrayList.size(); j++){
            LayoutInflater mInflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mInflator.inflate(R.layout.utility_bus_book_final_detail_select_bus_item, null);

             TextView txtTravelName;
             TextView txtBusType;

             TextView txtDTime;
             TextView txtDDay;
             TextView txtDdate;
             TextView txtATime;
             TextView txtAday;
             TextView txtAdate;
             TextView txtTotHour;

             txtTravelName=(TextView)view.findViewById(R.id.bus_book_detail_item_txt_travels_name);
            txtBusType=(TextView)view.findViewById(R.id.bus_book_detail_item_txt_bus_type);
            txtDTime=(TextView)view.findViewById(R.id.bus_book_detail_item_txt_departure_time);
            txtDdate=(TextView)view.findViewById(R.id.bus_book_detail_item_txt_departure_date);
            txtDDay=(TextView)view.findViewById(R.id.bus_book_detail_item_txt_departure_day);
            //txtPrice=(TextView)view.findViewById(R.id.bus_book_detail_item_txt_cancel_policy);
            txtATime=(TextView)view.findViewById(R.id.bus_book_detail_item_txt_arrival_time);
            txtAdate=(TextView)view.findViewById(R.id.bus_book_detail_item_txt_arrival_date);
            txtAday=(TextView)view.findViewById(R.id.bus_book_detail_item_txt_arrival_day);
            txtTotHour=(TextView)view.findViewById(R.id.bus_book_detail_item_txt_total_hour);

            txtTravelName.setText(selectBusarrayList.get(j).getTravels());
            txtBusType.setText(selectBusarrayList.get(j).getBusType());
            String[] strDepartDate=selectBusarrayList.get(j).getDepartureDateTime().split(",");

            txtDdate.setText(strDepartDate[1]);
            txtDTime.setText(strDepartDate[2]);
            txtDDay.setText(strDepartDate[0]);

            String[] strArrivalDate=selectBusarrayList.get(j).getArrivateDateTime().split(",");
            txtATime.setText(strArrivalDate[2]);
            txtAdate.setText(strArrivalDate[1]);
            txtAday.setText(strArrivalDate[0]);

            String depDate_Time="";
            String arriveDate_Time="";
            String depTime=strDepartDate[2];
            String depDate=strDepartDate[1];
            String aDate=strArrivalDate[1];
            String atime=strArrivalDate[2];

            depDate_Time=depDate+""+depTime;
            arriveDate_Time=aDate+""+atime;

            DateFormat recformet=new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.US);
            DateFormat convertFormet=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss",Locale.US);
            Date date1=null;
            Date date2=null;
            String convertDepDate="";
            String convertArriveDate="";
            try {
                date1=recformet.parse(depDate_Time);
                date2=recformet.parse(arriveDate_Time);
                convertDepDate=convertFormet.format(date1);
                convertArriveDate=convertFormet.format(date2);

            }catch (Exception e){
                e.printStackTrace();
            }
            txtTotHour.setText(DateUtilities.getDifferenceTime(convertDepDate,convertArriveDate));

            layoutBusDetail.addView(view);
        }
    }

    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
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
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        try {
            int compressionRatio=4;

            /*Set default promo value in shared*/
            //SharedPrefrence.setPromocode(this,"");
            //SharedPrefrence.setPromoDiscount(this,"");
            BusSharedValues.getInstance().busPromocode = "";
            BusSharedValues.getInstance().busPromoAmount = "0";

            if(requestCode == 7){
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

                            /*Set  promo value in shared*/
                            BusSharedValues.getInstance().busPromocode = strPromocode;
                            BusSharedValues.getInstance().busPromoAmount = String.valueOf(promoAmnt);
                            //SharedPrefrence.setPromocode(this,strPromocode);
                            //SharedPrefrence.setPromoDiscount(this,String.valueOf(promoAmnt));


                            txtPromocode.setText(strPromocode);
                            txtPromocodeDetail.setText(getResources().getString(R.string.str_promo_cashbck_detail)+
                                    " "+getResources().getString(R.string.rs_symbol)+ "" +String.valueOf(promoAmnt));
                            txtCouponDate.setText("Coupon will be expiry on date:- "+ DateUtilities.SpiltandConvertDate(strPromoDate));

                        }
                        else if(promoAmnt > totlePaidamount){

                            /*Set  promo value in shared*/
                            //SharedPrefrence.setPromocode(this,strPromocode);
                            // SharedPrefrence.setPromoDiscount(this,String.valueOf(totlePaidamount));

                            BusSharedValues.getInstance().busPromocode = strPromocode;
                            BusSharedValues.getInstance().busPromoAmount = String.valueOf(totlePaidamount);
                            txtPromocode.setText(strPromocode);
                            txtPromocodeDetail.setText(getResources().getString(R.string.str_promo_cashbck_detail)+
                                    " "+getResources().getString(R.string.rs_symbol)+ "" +String.valueOf(totlePaidamount));
                            txtCouponDate.setText("Coupon will be expiry on date:- "+ DateUtilities.SpiltandConvertDate(strPromoDate));

                        }
                        else if(promoAmnt == totlePaidamount){

                            /*Set  promo value in shared*/
                            //SharedPrefrence.setPromocode(this,strPromocode);
                            //SharedPrefrence.setPromoDiscount(this,String.valueOf(promoAmnt));

                            BusSharedValues.getInstance().busPromocode = strPromocode;
                            BusSharedValues.getInstance().busPromoAmount = String.valueOf(promoAmnt);

                            txtPromocode.setText(strPromocode);
                            txtPromocodeDetail.setText(getResources().getString(R.string.str_promo_cashbck_detail)+
                                    " "+getResources().getString(R.string.rs_symbol)+ "" +String.valueOf(promoAmnt));
                            txtCouponDate.setText("Coupon will be expiry on date:- "+ DateUtilities.SpiltandConvertDate(strPromoDate));
                        }



                    }
                    else {
                        Toast.makeText(BusBookFinalDetailActivity.this,"You can't use this because coupon is already used by use",
                                Toast.LENGTH_SHORT).show();
                        layoutHavePromo.setVisibility(View.VISIBLE);
                        layoutPromocode.setVisibility(View.GONE);
                        double promo= Double.parseDouble(strPromoAmount);
                        int promoAmnt=(int)Math.round(promo);
                        /*Save Promo Code and amount in Shared Preferance*/
                        /*Set default promo value in shared*/
                        BusSharedValues.getInstance().busPromocode = "";
                        BusSharedValues.getInstance().busPromoAmount = "0";

                        txtPromocode.setText("");
                        txtPromocodeDetail.setText("");
                        txtCouponDate.setText("");
                    }
                }
            }

        }catch (Exception e) {
            Toast.makeText(BusBookFinalDetailActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    /* Request and Response BillPay Send Otp*/
    public void SendOtp(){

        progressDialog = new ProgressDialog(this);
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

            /* Send Otp Request Model*/

            SendOtpRequest sendOtpRequest=new SendOtpRequest();
            sendOtpRequest.setEmailID(new LoginPreferences_Utility(this).getLoggedinUser().getEmailId());
            sendOtpRequest.setFormNo(formno);
            sendOtpRequest.setMobileNo(mobile);
            sendOtpRequest.setSponsorFormNo(companyId);
            sendOtpRequest.setServiceName("B");
            sendOtpRequest.setUserName(new LoginPreferences_Utility(this).getLoggedinUser().getUserName());

            /*Set Value in Main Request Model*/
            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(sendOtpRequest);

            strApiRequest=new Gson().toJson(apiRequest);

            Log.e("Value",strApiRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
        Call<BaseResponse> fetchFlightOtp=
                NetworkClient_Utility_1.getInstance(BusBookFinalDetailActivity.this).create(FlightApi.class).fetchFlightBookOtp(apiRequest,strToken);
        fetchFlightOtp.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    BaseResponse Response =new BaseResponse();
                    Response=response.body();

                    if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                        if(!Response.getRESP_VALUE().equals("null") || ! Response.getRESP_VALUE().equals("")){

                            // edTxtOtp.setEnabled(true);
                            String strResponse=Response.getRESP_VALUE();
                            otpId=strResponse;

                            BusSharedValues.getInstance().busBookOtpId=otpId;

                            Intent intent=new Intent(BusBookFinalDetailActivity.this, OtpVerify_BusBookActivity.class);

                            Bundle bundle1=new Bundle();
                            bundle1.putSerializable("PassengerInfo",pessengerInfoArrayList);
                            intent.putExtras(bundle1);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);


                        }
                        else if(Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")){
                            String toast= Response.getRESP_MSG();
                            //edTxtOtp.setEnabled(false);
                            //Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                        //edTxtOtp.setEnabled(false);
                        String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                        //Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                        //edTxtOtp.setEnabled(false);
                        String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                        //Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
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


    }

    /* Request and Response api Flight Margin*/
    public void getBusDiscount() {
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
            BusBookDiscount marginRequest = new BusBookDiscount();
            marginRequest.setGroupID(new LoginPreferences_Utility(this).getLoggedinUser().getBusGroupId());
            marginRequest.setSponsorFormNo(companyId);

            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(marginRequest);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        final Call<BaseResponse> fetchBusMargin =
                NetworkClient_Utility_1.getInstance(this).create(BusServiceApi.class).fetchBusMargin(apiRequest, strToken);

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
                                BusDiscountResponse marginResponse = new Gson().fromJson(strResponse, BusDiscountResponse.class);
                                commisionAmnt = Float.parseFloat(marginResponse.getDiscount());

                               // btnContinue.setVisibility(View.VISIBLE);
                                int fareAmount=0;

                                if (commisionAmnt != 0 ) {
                                    double dis = getComission(commisionAmnt);
                                    //int disount =(int) Math.round(dis); // 3
                                    discountAmnt = (int) Math.round(dis);
                                   // sendMarginAmnt = (int) Math.round(marginAmount);


                                    totDiscountAmnt = discountAmnt ;
                                    BusSharedValues.getInstance().totDiscount = totDiscountAmnt;
                                    //BusSharedValues.getInstance().flightMargin=sendMarginAmnt;
                                    //BusSharedValues.getInstance().flightCommsion=discountAmnt;
                                    //fareAmount = totFareamount - totDiscountAmnt;


                                   // txtDiscount.setText(getResources().getString(R.string.rs_symbol)+ "" + totDiscountAmnt);

                                  //  txtDiscoutDetail.setText(getResources().getString(R.string.str_have_discount_cong_bus));
                                } else {
                                    double dis = getComission(commisionAmnt);
                                    //int disount =(int) Math.round(dis); // 3
                                    discountAmnt = (int) Math.round(dis);
                                    totDiscountAmnt = discountAmnt ;
                                    BusSharedValues.getInstance().totDiscount = totDiscountAmnt;

                                   // txtDiscount.setText(getResources().getString(R.string.rs_symbol)+ "" + totDiscountAmnt);
                                   // btnContinue.setText("Proceed to pay  " + getResources().getString(R.string.rs_symbol)+"" + String.valueOf(fareAmount));

                                    //txtDiscoutDetail.setText("");
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
                            Toast.makeText(BusBookFinalDetailActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(BusBookFinalDetailActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(BusBookFinalDetailActivity.this, toast, Toast.LENGTH_SHORT).show();
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
