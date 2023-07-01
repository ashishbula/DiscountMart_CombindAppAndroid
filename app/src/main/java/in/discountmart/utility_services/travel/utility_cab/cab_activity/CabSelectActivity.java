package in.discountmart.utility_services.travel.utility_cab.cab_activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import in.discountmart.utility_services.travel.utility_cab.cab_model.cab_request_mdel.CabDiscountRequest;
import in.discountmart.utility_services.travel.utility_cab.cab_model.cab_response_model.CabDiscountResponse;
import in.discountmart.utility_services.travel.utility_cab.cab_model.cab_response_model.CabSearchResponse;
import in.discountmart.utility_services.travel.utility_cab.cab_sharedpreferance.CabSharedValues;
import in.discountmart.utility_services.travel.utility_cab.call_cab_api.CabServiceApi;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.DateUtilities;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CabSelectActivity extends AppCompatActivity {

    Toolbar fToolbar;
    TextView txtFromCity;
    TextView txtToCity;
    TextView txtCabType;
    TextView txtCabName;
    TextView txtDistanceKM;
    TextView txtPerKmRate;
    TextView txtDepartDate;
    TextView txtDepartTime;
    TextView txtTotalFareAmout;
    TextView txtTotalAmount;

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
    ProgressDialog pDialog;

    public static LinearLayout layoutPromocode;
    public static LinearLayout layoutPromo;

    RecyclerView recyclerView;
    View view;

    Button btnContinue;

    ArrayList<CabSearchResponse> cabtSelectList;
    ArrayList<CabSearchResponse> cabSearchList;
    //FlightSelectAdapter adapter;

    String strFromCity = "";
    String strToCity = "";
    String strAdult = "";
    String strChild = "";
    String strInfant = "";
    String strDepartDate = "";
    String strDepartTime = "";
    String airlineCode = "";
    String strPromocode="";
    String strPromoAmount="";
    String strPromoStatus="";
    String strPromoDate="";
    String strDiscount="";
    String otpId="";
    int  totlePaidamount;


    float marginAmount = 0;
    double totFareamount = 0;
    double commisionAmnt = 0;
    double totAmount = 0;
    int discountAmnt = 0;
    int sendMarginAmnt = 0;
    int totDiscountAmnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_cab_select);

        try {

            fToolbar = (Toolbar) findViewById(R.id.cab_select_activity_toolbar);
            setSupportActionBar(fToolbar);
            view=findViewById(android.R.id.content);

            // toolbar fancy stuff
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            /*Blank Promo form shared*/
            SharedPrefrence_Utility.setPromocode(this,"");
            SharedPrefrence_Utility.setPromoDiscount(this,"");
            CabSharedValues.getInstance().cabPromoAmount="0";
            CabSharedValues.getInstance().cabPromocode="";


            txtFromCity = (TextView) findViewById(R.id.cab_select_activity_toolbar_txtfromcity);
            txtToCity = (TextView) findViewById(R.id.cab_select_activity_toolbar_txttocity);
            txtDepartDate = (TextView) findViewById(R.id.cab_select_activity_toolbar_txt_dDate);
            txtDepartTime = (TextView) findViewById(R.id.cab_select_activity_toolbar_txt_time);
            txtCabName = (TextView) findViewById(R.id.cab_select_act_txt_cabname);
            txtCabType = (TextView) findViewById(R.id.cab_select_act_txt_cabtype);
            txtPerKmRate = (TextView) findViewById(R.id.cab_select_act_txt_km_rate);
            txtDistanceKM = (TextView) findViewById(R.id.cab_select_act_txt_distance);
            txtTotalAmount = (TextView) findViewById(R.id.cab_select_act_txt_tot_amount);

            txtDiscount = (TextView) findViewById(R.id.cab_select_activity_txt_discount);
            btnContinue = (Button) findViewById(R.id.cab_select_activity_btn_continue);
            layoutFareAmount = (LinearLayout) findViewById(R.id.cab_select_activity_layout_fareamout);
            layoutEdit = (LinearLayout) findViewById(R.id.cab_select_activity_toolbar_layout_edit);
            layoutPromocode = (LinearLayout) findViewById(R.id.cab_select_activity_layout_promocode);
            layoutPromo = (LinearLayout) findViewById(R.id.cab_select_activity_layout_have_promo);
            txtTotalFareAmout = (TextView) findViewById(R.id.cab_select_activity_txt_total_amnt);
            txtDiscoutDetail = (TextView) findViewById(R.id.cab_select_activity_txt_dis_detail);
            txtPromocode = (TextView) findViewById(R.id.cab_select_activity_txt_promocode);
            txtPromocodeDetail = (TextView) findViewById(R.id.cab_select_activity_txt_cashback);
            txtCouponDate = (TextView) findViewById(R.id.cab_select_activity_txt_Date);
            imgRemovePromo = (ImageView) findViewById(R.id.cab_select_activity_img_remove_promo);


            /*Get Value in bundle from Search cab List Activity*/
            final Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                cabtSelectList = new ArrayList<CabSearchResponse>();

                CabSearchResponse cabSearch = (CabSearchResponse) bundle.getSerializable("CabSearch");
                cabtSelectList = new ArrayList<CabSearchResponse>(Arrays.asList(cabSearch));


                if (cabtSelectList.size() > 0) {
                    for (int i = 0; i < cabtSelectList.size(); i++) {
                        //airlineCode = cabtSelectList.get(i).getAirlineCode();
                        totAmount = Double.parseDouble(cabtSelectList.get(i).getTotal());
                        totFareamount = Double.parseDouble(cabtSelectList.get(i).getTotal());
                        txtCabName.setText(cabtSelectList.get(i).getCar());
                        txtCabType.setText(cabtSelectList.get(i).getType());
                        txtDistanceKM.setText(cabtSelectList.get(i).getDistance()+"(KM)");
                        txtPerKmRate.setText(getResources().getString(R.string.rs_symbol)+""+cabtSelectList.get(i).getRate()+"(Per KM)");
                        txtTotalAmount.setText(cabtSelectList.get(i).getTotal());
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


                strFromCity = bundle.getString("FromCity");
                strToCity = bundle.getString("ToCity");
                strDepartDate = bundle.getString("DepartDate");
                strDepartTime = bundle.getString("DepartTime");


                txtFromCity.setText(strFromCity);
                txtToCity.setText(strToCity);
                txtDepartDate.setText("Date : "+strDepartDate+" * ");
                txtDepartTime.setText("Time : "+strDepartTime);



            }
            double fareAmount=0;

            fareAmount = totAmount;

            txtTotalFareAmout.setText(getResources().getString(R.string.rs_symbol) + "" + fareAmount);

           CabSharedValues.getInstance().totPaidAmount = totAmount;
           totlePaidamount= (int) totAmount;


            //int totalFlight=flightSelectList.size();
            //txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));
            //recyclerView = (RecyclerView) findViewById(R.id.cab_select_activity_recycler);



            /*call get flight margin api*/
            if(!ConnectivityUtils.isNetworkEnabled(this)){
                Toast.makeText(CabSelectActivity.this,R.string.network_error,Toast.LENGTH_SHORT).show();
            }
            else {
                getCabMargin();
            }

            /*Tool bar top home icon on click go to HomeMainActivity_utility*/
           /* imgHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intenthome=new Intent(CabSelectActivity.this, MainActivity_utility.class);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intenthome);
                    finish();
                }
            });*/




            /*Layout havePromo click listener for promocode list*/
            layoutPromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String strCab="X";
                    Intent intent1 = new Intent(CabSelectActivity.this, PromocodeActivity.class);
                    intent1.putExtra("ServiceType",strCab);
                    intent1.putExtra("ServiceId","18");
                    startActivityForResult(intent1,18);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                }
            });

            /*Button continue click listner*/
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Intent intent = new Intent(CabSelectActivity.this, CabPassengerInfoActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("SelectCab",cabtSelectList);
                        bundle1.putString("FromCity", strFromCity);
                        bundle1.putString("ToCity", strToCity);
                        bundle1.putString("DepartDate", strDepartDate);
                        bundle1.putString("DepartTime", strDepartTime);

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
                    Intent intent1 = new Intent(CabSelectActivity.this, CabBookFaireDetailActivity.class);
                    startActivity(intent1);
                    //openActivityfromBottom();
                }
            });

            /*Image cross for promo on click*/
            imgRemovePromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Save Promo Code and amount in Shared Preferance*/
                    SharedPrefrence_Utility.setPromocode(CabSelectActivity.this,"");
                    SharedPrefrence_Utility.setPromoDiscount(CabSelectActivity.this,"");

                    CabSharedValues.getInstance().cabPromoAmount="0";
                    CabSharedValues.getInstance().cabPromocode="";

                    CabSelectActivity.txtPromocode.setText("");
                    CabSelectActivity.txtPromocodeDetail.setText("");
                    CabSelectActivity.txtCouponDate.setText("");

                    CabSelectActivity.layoutPromo.setVisibility(View.VISIBLE);
                    CabSelectActivity.layoutPromocode.setVisibility(View.GONE);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Request and Response api CAB Margin*/
    public void getCabMargin() {
        pDialog=new ProgressDialog(this);
        pDialog.setMessage( "Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
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

            /*Cab Margin Request Model*/
            CabDiscountRequest marginRequest = new CabDiscountRequest();
            marginRequest.setGroupId(new LoginPreferences_Utility(this).getLoggedinUser().getGroupId());
            //marginRequest.setAirlineCode(airlineCode);
            marginRequest.setSponsorFormNo(companyId);

            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(marginRequest);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        final Call<BaseResponse> fetchCabMargin =
                NetworkClient_Utility_1.getInstance(this).create(CabServiceApi.class).fetchCabMargin(apiRequest, strToken);

        fetchCabMargin.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
              if(pDialog.isShowing())
                  pDialog.dismiss();
                try {

                    BaseResponse Response = new BaseResponse();
                    Response = response.body();
                    if (Response != null) {
                        if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                            if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                String strResponse = Response.getRESP_VALUE();
                                CabDiscountResponse marginResponse = new Gson().fromJson(strResponse, CabDiscountResponse.class);
                                commisionAmnt = Double.parseDouble(marginResponse.getDiscount());
                                //marginAmount = Float.parseFloat(marginResponse.getFlightMarginInAmount());
                                int fareAmount=0;

                                if (commisionAmnt != 0) {
                                    double dis = getComission(commisionAmnt);
                                    //int disount =(int) Math.round(dis); // 3
                                    discountAmnt = (int) Math.round(dis);
                                    //sendMarginAmnt = (int) Math.round(marginAmount);


                                    totDiscountAmnt = discountAmnt + sendMarginAmnt;
                                    CabSharedValues.getInstance().totDiscount = totDiscountAmnt;
                                    CabSharedValues.getInstance().cabMargin=sendMarginAmnt;
                                    CabSharedValues.getInstance().cabCommsion=discountAmnt;
                                    //txtDiscount.setText(getResources().getString(R.string.rs_symbol)+ "" + totDiscountAmnt);
                                    //txtDiscoutDetail.setText(getResources().getString(R.string.str_have_discount_cong));
                                } else {
                                    double dis = getComission(commisionAmnt);
                                    //int disount =(int) Math.round(dis); // 3
                                    discountAmnt = (int) Math.round(dis);
                                    //sendMarginAmnt = (int) Math.round(marginAmount);

                                    totDiscountAmnt = discountAmnt + sendMarginAmnt;
                                    CabSharedValues.getInstance().totDiscount = totDiscountAmnt;
                                    CabSharedValues.getInstance().cabMargin=sendMarginAmnt;
                                    CabSharedValues.getInstance().cabCommsion=discountAmnt;
                                    //txtDiscount.setText(getResources().getString(R.string.rs_symbol)+ "" + totDiscountAmnt);

                                    // txtDiscoutDetail.setText("");
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
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
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
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        }
                        else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                            String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                            Toast.makeText(CabSelectActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(CabSelectActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(CabSelectActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                if(pDialog.isShowing())
                    pDialog.dismiss();
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
    }

    public double getComission ( double commision){

        double intcommision = 0;
        double comm;

        intcommision = (totFareamount * commision) / 100;

        comm = DecimalUtils.round(intcommision, 2);


        return comm;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        try {
            int compressionRatio=4;

            /*Set default promo value in shared*/
            //SharedPrefrence.setPromocode(this,"");
            //SharedPrefrence.setPromoDiscount(this,"");
            CabSharedValues.getInstance().cabPromocode = "";
            CabSharedValues.getInstance().cabPromoAmount = "0";


            if(requestCode == 18){
                if(resultCode == Activity.RESULT_OK){
                    strPromocode=data.getStringExtra("PromoCode");
                    strPromoAmount=data.getStringExtra("PromoAmount");
                    strPromoStatus=data.getStringExtra("Status");
                    strPromoDate=data.getStringExtra("Date");

                    if(strPromoStatus.equals("Unused")){

                        layoutPromo.setVisibility(View.GONE);
                        layoutPromocode.setVisibility(View.VISIBLE);
                        double promo= Double.parseDouble(strPromoAmount);
                        int promoAmnt=(int)Math.round(promo);
                        /*Save Promo Code and amount in Shared Preferance*/
                        if(promoAmnt < totlePaidamount){

                            /*Set  promo value in shared*/
                            CabSharedValues.getInstance().cabPromocode = strPromocode;
                            CabSharedValues.getInstance().cabPromoAmount = String.valueOf(promoAmnt);
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

                            CabSharedValues.getInstance().cabPromocode = strPromocode;
                            CabSharedValues.getInstance().cabPromoAmount = String.valueOf(totlePaidamount);


                            txtPromocode.setText(strPromocode);
                            txtPromocodeDetail.setText(getResources().getString(R.string.str_promo_cashbck_detail)+
                                    " "+getResources().getString(R.string.rs_symbol)+ "" +String.valueOf(totlePaidamount));
                            txtCouponDate.setText("Coupon will be expiry on date:- "+ DateUtilities.SpiltandConvertDate(strPromoDate));

                        }
                        else if(promoAmnt == totlePaidamount){

                            /*Set  promo value in shared*/
                            //SharedPrefrence.setPromocode(this,strPromocode);
                            //SharedPrefrence.setPromoDiscount(this,String.valueOf(promoAmnt));


                            CabSharedValues.getInstance().cabPromocode = strPromocode;
                            CabSharedValues.getInstance().cabPromoAmount = String.valueOf(promoAmnt);

                            txtPromocode.setText(strPromocode);
                            txtPromocodeDetail.setText(getResources().getString(R.string.str_promo_cashbck_detail)+
                                    " "+getResources().getString(R.string.rs_symbol)+ "" +String.valueOf(promoAmnt));
                            txtCouponDate.setText("Coupon will be expiry on date:- "+ DateUtilities.SpiltandConvertDate(strPromoDate));
                        }



                    }
                    else {
                        Toast.makeText(CabSelectActivity.this,"You can't use this because coupon is already used by use",
                                Toast.LENGTH_SHORT).show();
                        layoutPromo.setVisibility(View.VISIBLE);
                        layoutPromocode.setVisibility(View.GONE);
                        double promo= Double.parseDouble(strPromoAmount);
                        int promoAmnt=(int)Math.round(promo);
                        /*Save Promo Code and amount in Shared Preferance*/
                        /*Set default promo value in shared*/
                        CabSharedValues.getInstance().cabPromocode = "";
                        CabSharedValues.getInstance().cabPromoAmount = "0";

                        txtPromocode.setText("");
                        txtPromocodeDetail.setText("");
                        txtCouponDate.setText("");
                    }
                }
            }

        }catch (Exception e) {
            Toast.makeText(CabSelectActivity.this, e.toString(), Toast.LENGTH_LONG).show();
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