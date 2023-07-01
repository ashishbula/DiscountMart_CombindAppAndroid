package in.discountmart.utility_services.travel.flight.flight_activity;

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

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import in.base.network.NetworkClient_Utility_1;
import in.base.ui.BaseActivity;
import in.base.util.DecimalUtils;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.activity.MainActivity_utility;
import in.discountmart.utility_services.activity.PromocodeActivity;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.sharedpreferences.SharedPrefrence_Utility;
import in.discountmart.utility_services.travel.flight.adapter.FlightSelectAdapter;
import in.discountmart.utility_services.travel.flight.call_flight_api.FlightApi;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.FlightMarginRequest;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightMarginResponse;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightSearchResponse;
import in.discountmart.utility_services.travel.flight.flight_sharedpreference.FlightSharedValues;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.DividerItemDecoration;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectFlightActivity extends BaseActivity {

    Toolbar fToolbar;
    TextView txtFromCity;
    TextView txtToCity;
    TextView txtDepartDate;
    TextView txtAdult;
    TextView txtChild;
    TextView txtInfants;
    TextView txtTotalFlight;
    TextView txtTotalPassenger;
    TextView txtTotalFareAmout;
    TextView txtDiscount;
    TextView txtDiscoutDetail;
    public static TextView txtPromocode;
    public static TextView txtPromocodeDetail;
    public static TextView txtCouponDate;
    ImageView imgRemovePromo;
    ImageView imgHome;
    ImageView imgEdit;
    LinearLayout layoutFareAmount;
    LinearLayout layoutEdit;

    public static LinearLayout layoutPromocode;
    public static LinearLayout layoutPromo;

    RecyclerView recyclerView;
    View view;

    Button btnContinue;

    ArrayList<FlightSearchResponse> flightSelectList;
    ArrayList<FlightSearchResponse.FlightSearchSegment> flightSearchSegmentsList;
    FlightSelectAdapter adapter;

    String strFromCity = "";
    String strToCity = "";
    String strAdult = "";
    String strChild = "";
    String strInfant = "";
    String strDepartDate = "";
    String airlineCode = "";

    int totAdult = 0;
    int totChild = 0;
    int totInfants = 0;
    int totPessenger = 0;
    int totFareamount = 0;

    float marginAmount = 0;
    double commisionAmnt = 0;
    int discountAmnt = 0;
    int sendMarginAmnt = 0;
    int totDiscountAmnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_select_flight);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {

            fToolbar = (Toolbar) findViewById(R.id.flight_select_activity_toolbar);
            setSupportActionBar(fToolbar);
            view=findViewById(android.R.id.content);

            // toolbar fancy stuff
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            /*Blank Promo form shared*/
            SharedPrefrence_Utility.setPromocode(this,"");
            SharedPrefrence_Utility.setPromoDiscount(this,"");

            txtFromCity = (TextView) findViewById(R.id.flight_select_activity_toolbar_txtfromcity);
            txtToCity = (TextView) findViewById(R.id.flight_select_activity_toolbar_txttocity);
            txtDepartDate = (TextView) findViewById(R.id.flight_select_activity_toolbar_txt_dDate);
            txtAdult = (TextView) findViewById(R.id.flight_select_activity_toolbar_txt_adult);
            txtChild = (TextView) findViewById(R.id.flight_select_activity_toolbar_txt_child);
            txtInfants = (TextView) findViewById(R.id.flight_select_activity_toolbar_txt_infants);
            txtDiscount = (TextView) findViewById(R.id.flight_select_activity_txt_discount);
            btnContinue = (Button) findViewById(R.id.flight_select_activity_btn_continue);
            layoutFareAmount = (LinearLayout) findViewById(R.id.flight_select_activity_layout_fareamout);
            layoutEdit = (LinearLayout) findViewById(R.id.flight_select_activity_toolbar_layout_edit);
            layoutPromocode = (LinearLayout) findViewById(R.id.flight_select_activity_layout_promocode);
            layoutPromo = (LinearLayout) findViewById(R.id.flight_select_activity_layout_have_promo);
            txtTotalFareAmout = (TextView) findViewById(R.id.flight_select_activity_txt_total_amnt);
            txtTotalPassenger = (TextView) findViewById(R.id.flight_select_activity_txt_total_travelor);
            txtDiscoutDetail = (TextView) findViewById(R.id.flight_select_activity_txt_dis_detail);
            txtPromocode = (TextView) findViewById(R.id.flight_select_activity_txt_promocode);
            txtPromocodeDetail = (TextView) findViewById(R.id.flight_select_activity_txt_cashback);
            txtCouponDate = (TextView) findViewById(R.id.flight_select_activity_txt_Date);
            imgRemovePromo = (ImageView) findViewById(R.id.flight_select_activity_img_remove_promo);
            imgHome = (ImageView) findViewById(R.id.flight_select_activity_toolbar_img_home);
            imgEdit = (ImageView) findViewById(R.id.flight_select_activity_toolbar_img_edit);
            //txtTotalFlight=(TextView)findViewById(R.id.flight_select_activity_txt_totalflight);

            /*Get Value in bundle from SearchFlightList Activity*/
            final Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                flightSelectList = new ArrayList<FlightSearchResponse>();
                flightSearchSegmentsList = new ArrayList<FlightSearchResponse.FlightSearchSegment>();
                FlightSearchResponse flightSearchResponse = (FlightSearchResponse) bundle.getSerializable("FlightList");
                flightSelectList = new ArrayList<FlightSearchResponse>(Arrays.asList(flightSearchResponse));
                flightSearchSegmentsList = new ArrayList<FlightSearchResponse.FlightSearchSegment>(flightSearchResponse.getSegment());

                if (flightSelectList.size() > 0) {
                    for (int i = 0; i < flightSelectList.size(); i++) {
                        airlineCode = flightSelectList.get(i).getAirlineCode();
                        totFareamount = Integer.parseInt(flightSearchResponse.getGrossAmount());
                    }
                }

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


                strFromCity     = bundle.getString("FromCity");
                strToCity       = bundle.getString("ToCity");
                strAdult        = bundle.getString("Adult");
                strChild        = bundle.getString("Child");
                strInfant       = bundle.getString("Infants");
                strDepartDate   = bundle.getString("DepartDate");

                if(strChild.contentEquals("0")){
                    txtChild.setText("");
                }
                else {
                    txtChild.setText("Child "+strChild);
                }
                if(strAdult.contentEquals("0")){
                    txtAdult.setText("");
                }
                else {
                    txtAdult.setText("Adult "+strAdult+" - ");
                }
                if(strInfant.contentEquals("0")){
                    txtInfants.setText("");
                }
                else {
                    txtInfants.setText(" - "+"Inf "+strInfant);
                }
                txtFromCity.setText(strFromCity);
                txtToCity.setText(strToCity);
                txtDepartDate.setText(strDepartDate+" * ");

                totAdult = Integer.parseInt(strAdult);
                totChild = Integer.parseInt(strChild);
                totInfants = Integer.parseInt(strInfant);
                totPessenger = totAdult + totChild + totInfants;

            }
            int fareAmount=0;

            fareAmount = totFareamount;

            txtTotalFareAmout.setText(getResources().getString(R.string.rs_symbol) + "" + fareAmount);
            txtTotalPassenger.setText("Base Fare for " + String.valueOf(totPessenger) + " Travelers");
            FlightSharedValues.getInstance().totPaidAmount = fareAmount;


            //int totalFlight=flightSelectList.size();
                //txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));
                recyclerView = (RecyclerView) findViewById(R.id.flight_select_activity_recycler);

                //flightSearchList = new ArrayList<>();
                adapter = new FlightSelectAdapter(SelectFlightActivity.this, flightSelectList, flightSearchSegmentsList);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(SelectFlightActivity.this, DividerItemDecoration.VERTICAL_LIST, 0));
                recyclerView.setAdapter(adapter);

                /*call get flight margin api*/
                if(!ConnectivityUtils.isNetworkEnabled(this)){
                    Toast.makeText(SelectFlightActivity.this,R.string.network_error,Toast.LENGTH_SHORT).show();
                }
                else {
                    getFlightMargin();
                }

                /*Tool bar top home icon on click go to HomeMainActivity_utility*/
            imgHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intenthome=new Intent(SelectFlightActivity.this, MainActivity_utility.class);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intenthome);
                    finish();
                }
            });

            /*Tool bar top edit icon on click go to FlightSearchActivity*/
            layoutEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intenthome=new Intent(SelectFlightActivity.this, FlightSearchActivity.class);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intenthome.putExtra("Edit","true");
                    startActivity(intenthome);
                    finish();
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
                }
            });

                /*Layout havePromo click listener for promocode list*/
                layoutPromo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strFlight="R";
                        Intent intent1 = new Intent(SelectFlightActivity.this, PromocodeActivity.class);
                        intent1.putExtra("ServiceType",strFlight);
                        intent1.putExtra("ServiceId","6");
                        startActivity(intent1);
                        openActivityfromBottom();

                    }
                });

                /*Button continue click listner*/
                btnContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            Intent intent = new Intent(SelectFlightActivity.this, FlightPassengerInfoActivity.class);
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("FromCity", strFromCity);
                            bundle1.putString("ToCity", strToCity);
                            bundle1.putString("Adult", strAdult);
                            bundle1.putString("Child", strChild);
                            bundle1.putString("Infants", strInfant);
                            bundle1.putString("DepartDate", strDepartDate);

                            intent.putExtras(bundle1);
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                });

                /*Layout for Base Fare Amount*/
                layoutFareAmount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(SelectFlightActivity.this, FlightBookFareDetailActivity.class);
                        intent1.putExtra("FlightType","Ownward");
                        startActivity(intent1);
                        openActivityfromBottom();
                    }
                });

                /*Image cross for promo on click*/
            imgRemovePromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Save Promo Code and amount in Shared Preferance*/
                    SharedPrefrence_Utility.setPromocode(SelectFlightActivity.this,"");
                    SharedPrefrence_Utility.setPromoDiscount(SelectFlightActivity.this,"");

                    SelectFlightActivity.txtPromocode.setText("");
                    SelectFlightActivity.txtPromocodeDetail.setText("");
                    SelectFlightActivity.txtCouponDate.setText("");

                    SelectFlightActivity.layoutPromo.setVisibility(View.VISIBLE);
                    SelectFlightActivity.layoutPromocode.setVisibility(View.GONE);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Request and Response api Flight Margin*/
    public void getFlightMargin() {
        showProgressDialog();
        String strApiRequest = "";
        JSONObject object = null;
        String strToken = TokenBase64.getToken();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse = new LoginPreferences_Utility(this).getLoggedinUser();
        String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);

        ApiRequest apiRequest = new ApiRequest();
        try {

            /*Main Request Model*/
            BaseHeaderRequest headerRequest = new BaseHeaderRequest();
            headerRequest.setUserName(loginResponse.getUserName());
            headerRequest.setPassword(loginResponse.getPasswd());
            headerRequest.setSponsorFormNo(companyId);
//            if(loginResponse.getMemMode().equals("D"))
//                headerRequest.setSponsorFormNo(companyId);
//            else
//                headerRequest.setSponsorFormNo("");

            /*Flight Margin Request Model*/
            FlightMarginRequest marginRequest = new FlightMarginRequest();
            marginRequest.setGroupID(new LoginPreferences_Utility(this).getLoggedinUser().getGroupId());
            marginRequest.setAirlineCode(airlineCode);
            marginRequest.setSponsorFormNo(companyId);

            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(marginRequest);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        final Call<BaseResponse> fetchFlightMargin =
                NetworkClient_Utility_1.getInstance(this).create(FlightApi.class).fetchFlightMargin(apiRequest, strToken);

        fetchFlightMargin.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                hideProgressDialog();
                try {

                    BaseResponse Response = new BaseResponse();
                    Response = response.body();
                    if (Response != null) {
                        if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                            if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                String strResponse = Response.getRESP_VALUE();
                                FlightMarginResponse marginResponse = new Gson().fromJson(strResponse, FlightMarginResponse.class);
                                commisionAmnt = Float.parseFloat(marginResponse.getCommission());
                                marginAmount = Float.parseFloat(marginResponse.getFlightMarginInAmount());
                                int fareAmount=0;

                                if (commisionAmnt != 0 || marginAmount != 0) {
                                    double dis = getComission(commisionAmnt);
                                    //int disount =(int) Math.round(dis); // 3
                                    discountAmnt = (int) Math.round(dis);
                                    sendMarginAmnt = (int) Math.round(marginAmount);


                                    totDiscountAmnt = discountAmnt + sendMarginAmnt;
                                    FlightSharedValues.getInstance().totDiscount = totDiscountAmnt;
                                    FlightSharedValues.getInstance().flightMargin=sendMarginAmnt;
                                    FlightSharedValues.getInstance().flightCommsion=discountAmnt;
                                    //txtDiscount.setText(getResources().getString(R.string.rs_symbol)+ "" + totDiscountAmnt);
                                    //txtDiscoutDetail.setText(getResources().getString(R.string.str_have_discount_cong));
                                } else {
                                    double dis = getComission(commisionAmnt);
                                    //int disount =(int) Math.round(dis); // 3
                                    discountAmnt = (int) Math.round(dis);
                                    sendMarginAmnt = (int) Math.round(marginAmount);

                                    totDiscountAmnt = discountAmnt + sendMarginAmnt;
                                    FlightSharedValues.getInstance().flightMargin=sendMarginAmnt;
                                    FlightSharedValues.getInstance().flightCommsion=discountAmnt;
                                    FlightSharedValues.getInstance().totDiscount = totDiscountAmnt;
                                    //txtDiscount.setText(getResources().getString(R.string.rs_symbol)+ "" + totDiscountAmnt);

                                   // txtDiscoutDetail.setText("");
                                }


                            } else if (Response.getRESP_VALUE().isEmpty()|| Response.getRESP_VALUE().equals("")) {

                                String toast = Response.getRESP_MSG();
                                //Toast.makeText(SelectFlightActivity.this, toast, Toast.LENGTH_SHORT).show();
                                showSnackbar(toast);
                            }
                        }
                        else  if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            //Toast.makeText(SelectFlightActivity.this, toast, Toast.LENGTH_SHORT).show();
                            showSnackbar(toast);

                        }
                        else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                            String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                            Toast.makeText(SelectFlightActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SelectFlightActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(SelectFlightActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public double getComission ( double commision){

            double intcommision = 0;
            double comm;

            intcommision = (totFareamount * commision) / 100;

            comm = DecimalUtils.round(intcommision, 2);


            return comm;
        }

    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        //openActivityFromTop();
        return true;
    }


}

