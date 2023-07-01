package in.discountmart.utility_services.travel.flight.flight_activity;

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
import in.base.util.DecimalUtils;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.activity.PromocodeActivity;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.sharedpreferences.SharedPrefrence_Utility;
import in.discountmart.utility_services.travel.flight.adapter.OwnwardFlightSelectAdapter;
import in.discountmart.utility_services.travel.flight.adapter.ReturnFlightSelectAdapter;
import in.discountmart.utility_services.travel.flight.call_flight_api.FlightApi;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.FlightMarginRequest;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightMarginResponse;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.OwnwardFlightSearch;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.ReturnFlightSearch;
import in.discountmart.utility_services.travel.flight.flight_sharedpreference.FlightSharedValues;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.DividerItemDecoration;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectReturnFlightActivity extends AppCompatActivity {

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
    LinearLayout layoutFareAmount;
    View view;

    public static LinearLayout layoutPromocode;
    public static LinearLayout layoutPromo;

    RecyclerView recyOwnward;
    RecyclerView recyReturn;

    Button btnContinue;
    ProgressDialog progressDialog;

    /*Object of arraylist*/
    ArrayList<OwnwardFlightSearch> ownflightSelectList;
    ArrayList<ReturnFlightSearch> retflightSelectList;
    ArrayList<OwnwardFlightSearch.OwnFlightSegment> ownflightSegmentsList;
    ArrayList<ReturnFlightSearch.ReturnFlightSegment> returnflightSegmentsList;

    OwnwardFlightSelectAdapter ownwardFlightAdapter;
    ReturnFlightSelectAdapter returnFlightAdapter;

    OwnwardFlightSearch ownwardFlightSearch;
    ReturnFlightSearch returnFlightSearch;

    String strFromCity = "";
    String strToCity = "";
    String strAdult = "";
    String strChild = "";
    String strInfant = "";
    String strDepartDate = "";
    String strRetDate = "";
    String ownAirlineCode = "";
    String retAirlineCode = "";

    int totAdult = 0;
    int totChild = 0;
    int totInfants = 0;
    int totPessenger = 0;
    int totFareamount = 0;

    float ownmarginAmount = 0;
    float retmarginAmount = 0;
    double retcommisionAmnt = 0;
    double owncommisionAmnt = 0;
    int owndiscountAmnt = 0;
    int retdiscountAmnt = 0;
    int sendMarginAmnt1 = 0;
    int sendMarginAmnt2 = 0;
    int totDiscountAmnt = 0;
    int ownTotDiscount = 0;
    int retTotDiscount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_select_return_flight);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view=findViewById(android.R.id.content);
        try {

            fToolbar = (Toolbar) findViewById(R.id.return_flight_select_act_toolbar);
            setSupportActionBar(fToolbar);

            // toolbar fancy stuff
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            /*Blank Promo form shared*/
            SharedPrefrence_Utility.setPromocode(this,"");
            SharedPrefrence_Utility.setPromoDiscount(this,"");

            txtFromCity = (TextView) findViewById(R.id.return_flight_select_act_toolbar_txtfromcity);
            txtToCity = (TextView) findViewById(R.id.return_flight_select_act_toolbar_txttocity);
            txtDepartDate = (TextView) findViewById(R.id.return_flight_select_act_toolbar_txt_dDate);
            txtAdult = (TextView) findViewById(R.id.return_flight_select_act_toolbar_txt_adult);
            txtChild = (TextView) findViewById(R.id.return_flight_select_act_toolbar_txt_child);
            txtInfants = (TextView) findViewById(R.id.return_flight_select_act_toolbar_txt_infants);
            txtDiscount = (TextView) findViewById(R.id.return_flight_select_act_txt_discount);
            btnContinue = (Button) findViewById(R.id.return_flight_select_act_btn_continue);
            layoutFareAmount = (LinearLayout) findViewById(R.id.return_flight_select_act_layout_fareamout);
            layoutPromocode = (LinearLayout) findViewById(R.id.return_flight_select_act_layout_promocode);
            layoutPromo = (LinearLayout) findViewById(R.id.return_flight_select_act_layout_have_promo);
            txtTotalFareAmout = (TextView) findViewById(R.id.return_flight_select_act_txt_total_amnt);
            txtTotalPassenger = (TextView) findViewById(R.id.return_flight_select_act_txt_total_travelor);
            txtDiscoutDetail = (TextView) findViewById(R.id.return_flight_select_act_txt_dis_detail);
            txtPromocode = (TextView) findViewById(R.id.return_flight_select_act_txt_promocode);
            txtPromocodeDetail = (TextView) findViewById(R.id.return_flight_select_act_txt_cashback);
            txtCouponDate = (TextView) findViewById(R.id.return_flight_select_act_txt_Date);
            imgRemovePromo = (ImageView) findViewById(R.id.return_flight_select_act_img_remove_promo);
            //txtTotalFlight=(TextView)findViewById(R.id.flight_select_activity_txt_totalflight);

            /*Get Value in bundle from FlightSearchRoundTrip Activity*/
            final Bundle bundle = getIntent().getExtras();
            if (bundle != null) {

                /*Ownward flight */
                ownflightSelectList = new ArrayList<OwnwardFlightSearch>();
                ownflightSegmentsList = new ArrayList<OwnwardFlightSearch.OwnFlightSegment>();
                ownwardFlightSearch = (OwnwardFlightSearch) bundle.getSerializable("OwnFlightList");

                ownflightSelectList = new ArrayList<OwnwardFlightSearch>(Arrays.asList(ownwardFlightSearch));
                ownflightSegmentsList = new ArrayList<OwnwardFlightSearch.OwnFlightSegment>(ownwardFlightSearch.getSegment());

                /*Return flight */
                retflightSelectList = new ArrayList<ReturnFlightSearch>();
                returnflightSegmentsList = new ArrayList<ReturnFlightSearch.ReturnFlightSegment>();
                returnFlightSearch = (ReturnFlightSearch) bundle.getSerializable("RetFlightList");

                retflightSelectList = new ArrayList<ReturnFlightSearch>(Arrays.asList(returnFlightSearch));
                returnflightSegmentsList = new ArrayList<ReturnFlightSearch.ReturnFlightSegment>(returnFlightSearch.getSegment());

                if (ownwardFlightSearch !=null) {
                    ownAirlineCode = ownwardFlightSearch.getAirlineCode();
                }
                if(returnFlightSearch != null){
                    retAirlineCode=returnFlightSearch.getAirlineCode();
                }

                totFareamount = FlightSharedValues.getInstance().totflightPrice;
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

               strFromCity = bundle.getString("FromCity");
                strToCity = bundle.getString("ToCity");
                strAdult = bundle.getString("Adult");
                strChild = bundle.getString("Child");
                strInfant = bundle.getString("Infants");
                strDepartDate = bundle.getString("DepartDate");
                strRetDate = bundle.getString("RetDate");
                txtFromCity.setText(strFromCity);
                txtToCity.setText(strToCity);
                txtAdult.setText("Adult " + strAdult);
                txtChild.setText("Child " + strChild);
                txtInfants.setText("Inf " + strInfant);
                txtDepartDate.setText(strDepartDate+ " - "+ strRetDate);

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


           /*Recycler View select Ownward flight */
            recyOwnward = (RecyclerView) findViewById(R.id.return_flight_select_act_recyOwnFlight);

            //flightSearchList = new ArrayList<>();
            ownwardFlightAdapter = new OwnwardFlightSelectAdapter(SelectReturnFlightActivity.this, ownflightSelectList, ownflightSegmentsList);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyOwnward.setLayoutManager(mLayoutManager);
            recyOwnward.setItemAnimator(new DefaultItemAnimator());
            recyOwnward.addItemDecoration(new DividerItemDecoration(SelectReturnFlightActivity.this, DividerItemDecoration.VERTICAL_LIST, 0));
            recyOwnward.setAdapter(ownwardFlightAdapter);

            /*Recycler View select Return flight */
            recyReturn = (RecyclerView) findViewById(R.id.return_flight_select_act_recyRetFlight);

            //flightSearchList = new ArrayList<>();
            returnFlightAdapter = new ReturnFlightSelectAdapter(SelectReturnFlightActivity.this, retflightSelectList, returnflightSegmentsList);

            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
            recyReturn.setLayoutManager(mLayoutManager1);
            recyReturn.setItemAnimator(new DefaultItemAnimator());
            recyReturn.addItemDecoration(new DividerItemDecoration(SelectReturnFlightActivity.this, DividerItemDecoration.VERTICAL_LIST, 0));
            recyReturn.setAdapter(returnFlightAdapter);

            /*call get flight margin api*/
            if(!ConnectivityUtils.isNetworkEnabled(this)){
                Toast.makeText(SelectReturnFlightActivity.this,R.string.network_error,Toast.LENGTH_SHORT).show();
            }
            else {

                getOwnwardFlightMargin();
            }

            /*Layout havePromo click listener for promocode list*/
            layoutPromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String strFlight="R";
                    Intent intent1 = new Intent(SelectReturnFlightActivity.this, PromocodeActivity.class);
                    intent1.putExtra("ServiceType",strFlight);
                    intent1.putExtra("ServiceId","6");
                    startActivity(intent1);
                    //openActivityfromBottom();
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

                }
            });

            /*Button continue click listner*/
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Intent intent = new Intent(SelectReturnFlightActivity.this, FlightPassengerInfoActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("FromCity", strFromCity);
                        bundle1.putString("ToCity", strToCity);
                        bundle1.putString("Adult", strAdult);
                        bundle1.putString("Child", strChild);
                        bundle1.putString("Infants", strInfant);
                        bundle1.putString("DepartDate", strDepartDate);
                        bundle1.putString("RetDate", strRetDate);

                        intent.putExtras(bundle1);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });

            /*Layout for Base Fare Amount*/
            layoutFareAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(SelectReturnFlightActivity.this, FlightBookFareDetailActivity.class);
                    intent1.putExtra("FlightType","Return");
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });

            /*Image cross for promo on click*/
            imgRemovePromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Save Promo Code and amount in Shared Preferance*/
                    SharedPrefrence_Utility.setPromocode(SelectReturnFlightActivity.this,"");
                    SharedPrefrence_Utility.setPromoDiscount(SelectReturnFlightActivity.this,"");

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

    /* Request and Response api Ownward Flight Margin*/
    public void getOwnwardFlightMargin() {
       // showProgressDialog();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
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
            marginRequest.setAirlineCode(ownAirlineCode);
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
                //hideProgressDialog();
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    BaseResponse Response = new BaseResponse();
                    Response = response.body();
                    if (Response != null) {
                        if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                            if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                String strResponse = Response.getRESP_VALUE();
                                FlightMarginResponse marginResponse = new Gson().fromJson(strResponse, FlightMarginResponse.class);
                                owncommisionAmnt = Float.parseFloat(marginResponse.getCommission());
                                ownmarginAmount = Float.parseFloat(marginResponse.getFlightMarginInAmount());
                                int fareAmount=0;

                                if (owncommisionAmnt != 0 || ownmarginAmount != 0) {
                                    double dis = getComission(owncommisionAmnt);
                                    //int disount =(int) Math.round(dis); // 3
                                    owndiscountAmnt = (int) Math.round(dis);
                                    sendMarginAmnt1 = (int) Math.round(ownmarginAmount);


                                   // totDiscountAmnt = owndiscountAmnt + sendMarginAmnt1;
                                    ownTotDiscount = owndiscountAmnt + sendMarginAmnt1;
                                    totDiscountAmnt=ownTotDiscount+retTotDiscount;
                                    FlightSharedValues.getInstance().totOwnFlightDiscount = ownTotDiscount;
                                    FlightSharedValues.getInstance().ownflightMargin=sendMarginAmnt1;
                                    FlightSharedValues.getInstance().ownflightCommsion=owndiscountAmnt;
                                    FlightSharedValues.getInstance().totDiscount=totDiscountAmnt;

                                } else {
                                    double dis = getComission(owncommisionAmnt);
                                    //int disount =(int) Math.round(dis); // 3
                                    owndiscountAmnt = (int) Math.round(dis);
                                    sendMarginAmnt1 = (int) Math.round(ownmarginAmount);


                                    // totDiscountAmnt = owndiscountAmnt + sendMarginAmnt1;
                                    ownTotDiscount = owndiscountAmnt + sendMarginAmnt1;
                                    totDiscountAmnt=ownTotDiscount+retTotDiscount;
                                    FlightSharedValues.getInstance().totOwnFlightDiscount = ownTotDiscount;
                                    FlightSharedValues.getInstance().ownflightMargin=sendMarginAmnt1;
                                    FlightSharedValues.getInstance().ownflightCommsion=owndiscountAmnt;
                                    FlightSharedValues.getInstance().totDiscount=totDiscountAmnt;
                                }

                                /*Call Return flight margin api*/
                                getReturnFlightMargin();


                            } else if (Response.getRESP_VALUE().isEmpty()|| Response.getRESP_VALUE().equals("")) {

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
                            Toast.makeText(SelectReturnFlightActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SelectReturnFlightActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(SelectReturnFlightActivity.this, toast, Toast.LENGTH_SHORT).show();
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
               // hideProgressDialog();
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

    /* Request and Response api Return Flight Margin*/
    public void getReturnFlightMargin() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
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
            marginRequest.setAirlineCode(retAirlineCode);
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
              if(progressDialog.isShowing())
                  progressDialog.dismiss();
                try {

                    BaseResponse Response = new BaseResponse();
                    Response = response.body();
                    if (Response != null) {
                        if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                            if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                String strResponse = Response.getRESP_VALUE();
                                FlightMarginResponse marginResponse = new Gson().fromJson(strResponse, FlightMarginResponse.class);
                                retcommisionAmnt = Float.parseFloat(marginResponse.getCommission());
                                retmarginAmount = Float.parseFloat(marginResponse.getFlightMarginInAmount());
                                int fareAmount=0;

                                if (retcommisionAmnt != 0 || retmarginAmount != 0) {
                                    double dis = getComission(retcommisionAmnt);
                                    //int disount =(int) Math.round(dis); // 3
                                    retdiscountAmnt = (int) Math.round(dis);
                                    sendMarginAmnt2 = (int) Math.round(retmarginAmount);


                                    // totDiscountAmnt = owndiscountAmnt + sendMarginAmnt1;
                                    retTotDiscount = retdiscountAmnt + sendMarginAmnt2;
                                    totDiscountAmnt=ownTotDiscount+retTotDiscount;
                                    FlightSharedValues.getInstance().totRetFlightDiscount = retTotDiscount;
                                    FlightSharedValues.getInstance().retflightMargin=sendMarginAmnt2;
                                    FlightSharedValues.getInstance().retflightCommsion=retdiscountAmnt;
                                    FlightSharedValues.getInstance().totDiscount=totDiscountAmnt;

                                } else {
                                    double dis = getComission(retcommisionAmnt);
                                    //int disount =(int) Math.round(dis); // 3
                                    retdiscountAmnt = (int) Math.round(dis);
                                    sendMarginAmnt2 = (int) Math.round(retmarginAmount);


                                    // totDiscountAmnt = owndiscountAmnt + sendMarginAmnt1;
                                    retTotDiscount = retdiscountAmnt + sendMarginAmnt2;
                                    totDiscountAmnt=ownTotDiscount+retTotDiscount;
                                    FlightSharedValues.getInstance().totRetFlightDiscount = retTotDiscount;
                                    FlightSharedValues.getInstance().retflightMargin=sendMarginAmnt2;
                                    FlightSharedValues.getInstance().retflightCommsion=retdiscountAmnt;
                                    FlightSharedValues.getInstance().totDiscount=totDiscountAmnt;
                                }


                            } else if (Response.getRESP_VALUE().isEmpty()|| Response.getRESP_VALUE().equals("")) {

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
                            Toast.makeText(SelectReturnFlightActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SelectReturnFlightActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(SelectReturnFlightActivity.this, toast, Toast.LENGTH_SHORT).show();
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

        intcommision = (totFareamount * commision) / 100;

        comm = DecimalUtils.round(intcommision, 2);


        return comm;
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
