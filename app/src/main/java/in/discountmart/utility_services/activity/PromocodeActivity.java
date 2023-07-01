package in.discountmart.utility_services.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.base.network.NetworkClient_Utility;
import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.adapter.PromocodeAdapter;
import in.discountmart.utility_services.call_api.MainServices;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.request_model.PromoVerifyRequest;
import in.discountmart.utility_services.model.request_model.PromocodeRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.model.response_model.PromoVerifyResponse;
import in.discountmart.utility_services.model.response_model.PromocodeRespose;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.sharedpreferences.SharedPrefrence_Utility;
import in.discountmart.utility_services.travel.flight.flight_activity.SelectFlightActivity;
import in.discountmart.utility_services.travel.flight.flight_sharedpreference.FlightSharedValues;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.DateUtilities;
import in.discountmart.utility_services.utilities.DividerItemDecoration;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PromocodeActivity extends AppCompatActivity implements PromocodeAdapter.PromoCodeAdapterListener {
    RecyclerView recyViewPromo;
    ImageView imgClose;
    PromocodeAdapter promocodeAdapter;
    ProgressDialog progressDialog;
    LinearLayout layoutPromoDate;
    TextView txtPromoDate;
    TextView txtPromoMsg;
    ArrayList<PromocodeRespose> promocodeList;
    Context context;
    String strServiceType="";
    String strServiceId="";
    boolean promoUse=false;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_promocode);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        view=findViewById(android.R.id.content);
        try {

            SharedPrefrence_Utility.setPromocode(this,"");
            SharedPrefrence_Utility.setPromoDiscount(this,"");

            Intent intent=getIntent();
            strServiceType=intent.getStringExtra("ServiceType");
            strServiceId=intent.getStringExtra("ServiceId");

            recyViewPromo=(RecyclerView)findViewById(R.id.promocode_activity_recycler);
            imgClose=(ImageView)findViewById(R.id.promocode_activity_imag_close);
            layoutPromoDate=(LinearLayout)findViewById(R.id.promocode_activity_layout_promodate);
            txtPromoDate=(TextView)findViewById(R.id.promocode_activity_txt_promodate);
            txtPromoMsg=(TextView)findViewById(R.id.promocode_activity_txt_promomsg);

            promocodeList = new ArrayList<PromocodeRespose>();
            promocodeAdapter = new PromocodeAdapter(PromocodeActivity.this, promocodeList, this);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyViewPromo.setLayoutManager(mLayoutManager);
            recyViewPromo.setItemAnimator(new DefaultItemAnimator());
            recyViewPromo.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,10));
            recyViewPromo.setAdapter(promocodeAdapter);

            /*Call Api Promocode List*/

            if(!ConnectivityUtils.isNetworkEnabled(this)){
                Toast.makeText(this,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
            }
            else {
                //getPromocodeList();
                getVerifyPromocode();
            }

            /*Image close button click to close activity*/
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* Request and Response Promocode List*/
    public void getPromocodeList(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String strApiRequest="";
        JSONObject object=null;
        String strToken= TokenBase64.getToken();
        LoginResponse loginResponse=new LoginResponse();
        loginResponse=new LoginPreferences_Utility(PromocodeActivity.this).getLoggedinUser();
        String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);
        String formNo=loginResponse.getFormNo().substring(0,loginResponse.getFormNo().length()-2);

        ApiRequest apiRequest = new ApiRequest();
        try {

            /*Main Request Model*/

            BaseHeaderRequest headerRequest =new BaseHeaderRequest();
            headerRequest.setUserName(loginResponse.getUserName());
            headerRequest.setPassword(loginResponse.getPasswd());
            headerRequest.setSponsorFormNo(companyId);
//            if(loginResponse.getMemMode().equals("D"))
//                headerRequest.setSponsorFormNo(companyId);
//            else
//                headerRequest.setSponsorFormNo("");

            /*Promocode Request Model*/
            PromocodeRequest promocodeRequest=new PromocodeRequest();
            promocodeRequest.setFormNo(formNo);
            promocodeRequest.setServiceId(strServiceId);

            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(promocodeRequest);

            strApiRequest=new Gson().toJson(apiRequest);

            Log.e("Value",strApiRequest);
        }catch (Exception e){
            e.printStackTrace();
        }


        Call<BaseResponse> fetchPromocodeListCall=
                NetworkClient_Utility_1.getInstance(this).create(MainServices.class).fetchPromocode(apiRequest,strToken);

        fetchPromocodeListCall.enqueue(new Callback<BaseResponse>() {
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
                                Toast.makeText(PromocodeActivity.this, toast, Toast.LENGTH_SHORT).show();

                            }

                            else if(Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")){
                                String[] promoList=Response.getRESP_VALUE().split("");
                                if(promoList != null && promoList.length > 0){
                                    PromocodeRespose[]promoResList= new Gson().fromJson(Response.getRESP_VALUE(),PromocodeRespose[].class);
                                    List<PromocodeRespose> tempList=new ArrayList<PromocodeRespose>(Arrays.asList(promoResList));

                                    if(tempList!= null && tempList.size() > 0){
                                        // adding contacts to contacts list
                                        promocodeList.clear();
                                        promocodeList.addAll(tempList);

                                        // refreshing recycler view

                                        promocodeAdapter.setPromoVerify(promoUse,PromocodeActivity.this);

                                        promocodeAdapter.notifyDataSetChanged();
                                    }
                                    else {
                                        String toast= " Sorry no promocode available for this service.";
                                        Toast.makeText(PromocodeActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    }

                                }
                                else {
                                    String toast= " Sorry no promocode available for this service.";
                                    Toast.makeText(PromocodeActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    //showSnackbar(toast);
                                }
                            }
                        }
                        else  if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")){

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(PromocodeActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(PromocodeActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(PromocodeActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(PromocodeActivity.this, toast, Toast.LENGTH_SHORT).show();
                            // showSnackbar(toast);

                        }
                    }
                    else {
                        String toast= "Something wrong..";
                        //Toast.makeText(PromocodeActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(PromocodeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    /* Request and Response Get Main Balance*/
    public void getVerifyPromocode() {
        try {

            progressDialog = new ProgressDialog(PromocodeActivity.this);
            progressDialog.setMessage("please wait...");
            progressDialog.setCancelable(true);
            progressDialog.show();

            String strApiRequest = "";
            JSONObject object = null;
            LoginResponse loginResponse = new LoginResponse();
            loginResponse = new LoginPreferences_Utility(this).getLoggedinUser();
            String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
            String formno = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);
            String mobile = loginResponse.getMobileNo().substring(0, loginResponse.getMobileNo().length() - 2);

            String strToken = TokenBase64.getToken();
            String promoMonth="ValidPromoMonth";


            try {
                /*Base Request Model*/
                PromoVerifyRequest request = new PromoVerifyRequest();
                request.setFormNo(formno);
                request.setServiceId(strServiceId);
                request.setValidPromoMonth("ValidPromoMonth");
               // String promoMonth="ValidPromoMonth";

                strApiRequest = new Gson().toJson(request);

                Log.e("PromoVerifyRequest", strApiRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Call<PromoVerifyResponse> fetchCall =
                    NetworkClient_Utility.getInstance(this).createCatalogue(MainServices.class).fetchPromoVerify(formno,strServiceId,promoMonth);

            fetchCall.enqueue(new Callback<PromoVerifyResponse>() {
                @Override
                public void onResponse(Call<PromoVerifyResponse> call, Response<PromoVerifyResponse> response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    try {

                        PromoVerifyResponse Response = new PromoVerifyResponse();
                        Response = response.body();

                        if(Response != null){

                            if(Response.isVoucher()){

                                promoUse=false;

                                layoutPromoDate.setVisibility(View.VISIBLE);
                                String datePromo= DateUtilities.jsonToDateString(Response.getFromDate());
                                txtPromoDate.setText("Sorry! You can use below" +" PROMO CODE "+" after this "+"\n"+ Html.fromHtml("<b>"+datePromo+"</b>")+ " Date");
                                //txtPromoMsg.setText("Sorry! You can use this code after.");
                                // call promo list api
                               // getPromocodeList();
                            }
                            else {
                                promoUse=true;
                                layoutPromoDate.setVisibility(View.GONE);
                                // call promo list api
                                getPromocodeList();

                            }
                        }
                        else {
                            String toast= "Something wrong..";
                            //Toast.makeText(PromocodeActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<PromoVerifyResponse> call, Throwable t) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(PromocodeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*Interface cll from promocodeAdapter class and select item form list and set on SelectFlight Activity */
    @Override
    public void onPromoSelected(PromocodeRespose promocode) {

        SharedPrefrence_Utility.setPromocode(this,"");
        SharedPrefrence_Utility.setPromoDiscount(this,"");
        if(promocode != null){

            if(strServiceType.equals("R")){
                if(promocode.getStatus().equals("Unused")){

                    SelectFlightActivity.layoutPromo.setVisibility(View.GONE);
                    SelectFlightActivity.layoutPromocode.setVisibility(View.VISIBLE);
                    double promo= Double.parseDouble(promocode.getAmount());
                    int promoAmnt=(int)Math.round(promo);

                    /*Save Promo Code and amount in Shared Preferance*/
                    int totPaidAmount= FlightSharedValues.getInstance().totPaidAmount;
                    if(totPaidAmount > promoAmnt){
                        SharedPrefrence_Utility.setPromocode(this,promocode.getVoucherCode());
                        SharedPrefrence_Utility.setPromoDiscount(this,String.valueOf(promoAmnt));
                        SelectFlightActivity.txtPromocodeDetail.setText(getResources().getString(R.string.str_promo_cashbck_detail)+
                                " "+getResources().getString(R.string.rs_symbol)+ "" +String.valueOf(promoAmnt));
                    }
                    else if(totPaidAmount < promoAmnt){
                        SharedPrefrence_Utility.setPromocode(this,promocode.getVoucherCode());
                        SharedPrefrence_Utility.setPromoDiscount(this,String.valueOf(totPaidAmount));

                        SelectFlightActivity.txtPromocodeDetail.setText(getResources().getString(R.string.str_promo_cashbck_detail)+
                                " "+getResources().getString(R.string.rs_symbol)+ "" +String.valueOf(totPaidAmount));
                    }

                    else {
                        SharedPrefrence_Utility.setPromocode(this,promocode.getVoucherCode());
                        SharedPrefrence_Utility.setPromoDiscount(this,String.valueOf(promoAmnt));
                        SelectFlightActivity.txtPromocodeDetail.setText(getResources().getString(R.string.str_promo_cashbck_detail)+
                                " "+getResources().getString(R.string.rs_symbol)+ "" +String.valueOf(promoAmnt));

                    }

                    SelectFlightActivity.txtPromocode.setText(promocode.getVoucherCode());
                    SelectFlightActivity.txtCouponDate.setText("Coupon will be expiry on date:- "+DateUtilities.SpiltandConvertDate(promocode.getToDate()));
                    finish();
                }
                else  if(promocode.getStatus().equals("Used")){
                    Toast.makeText(PromocodeActivity.this,"You cann't use this because coupon is already used by use ",Toast.LENGTH_SHORT).show();
                    double promo= Double.parseDouble(promocode.getAmount());
                    int promoAmnt=(int)Math.round(promo);
                    /*Save Promo Code and amount in Shared Preferance*/
                    SharedPrefrence_Utility.setPromocode(this,"");
                    SharedPrefrence_Utility.setPromoDiscount(this,"");

                    SelectFlightActivity.txtPromocode.setText("");
                    SelectFlightActivity.txtPromocodeDetail.setText("");
                    SelectFlightActivity.txtCouponDate.setText("");

                    SelectFlightActivity.layoutPromo.setVisibility(View.VISIBLE);
                    SelectFlightActivity.layoutPromocode.setVisibility(View.GONE);
                }
            }
            else if(strServiceType.equals("C")){

                if(promocode.getStatus().equals("Unused")){
                    Intent intent=new Intent();
                    intent.putExtra("PromoCode",promocode.getVoucherCode());
                    intent.putExtra("PromoAmount",promocode.getAmount());
                    intent.putExtra("Status",promocode.getStatus());

                    setResult(RESULT_OK, intent);
                    finish();
                }
                else if(promocode.getStatus().equals("Used")){
                    Intent intent=new Intent();
                    intent.putExtra("PromoCode",promocode.getVoucherCode());
                    intent.putExtra("PromoAmount",promocode.getAmount());
                    intent.putExtra("Status",promocode.getStatus());

                    setResult(RESULT_OK, intent);
                    finish();
                }


            }
            else if(strServiceType.equals("B")){

                if(promocode.getStatus().equals("Unused")){
                    Intent intent=new Intent();
                    intent.putExtra("PromoCode",promocode.getVoucherCode());
                    intent.putExtra("PromoAmount",promocode.getAmount());
                    intent.putExtra("Status",promocode.getStatus());
                    intent.putExtra("Date",promocode.getToDate());

                    setResult(RESULT_OK, intent);
                    finish();
                }
                else if(promocode.getStatus().equals("Used")){
                    Intent intent=new Intent();
                    intent.putExtra("PromoCode",promocode.getVoucherCode());
                    intent.putExtra("PromoAmount",promocode.getAmount());
                    intent.putExtra("Status",promocode.getStatus());
                    intent.putExtra("Date",promocode.getToDate());

                    setResult(RESULT_OK, intent);
                    finish();
                }


            }
            else if(strServiceType.equals("H")){

                if(promocode.getStatus().equals("Unused")){
                    Intent intent=new Intent();
                    intent.putExtra("PromoCode",promocode.getVoucherCode());
                    intent.putExtra("PromoAmount",promocode.getAmount());
                    intent.putExtra("Status",promocode.getStatus());
                    intent.putExtra("Date",promocode.getToDate());

                    setResult(RESULT_OK, intent);
                    finish();
                }
                else if(promocode.getStatus().equals("Used")){
                    Intent intent=new Intent();
                    intent.putExtra("PromoCode",promocode.getVoucherCode());
                    intent.putExtra("PromoAmount",promocode.getAmount());
                    intent.putExtra("Status",promocode.getStatus());
                    intent.putExtra("Date",promocode.getToDate());

                    setResult(RESULT_OK, intent);
                    finish();
                }


            }
            else if(strServiceType.equals("I")){

                if(promocode.getStatus().equals("Unused")){
                    Intent intent=new Intent();
                    intent.putExtra("PromoCode",promocode.getVoucherCode());
                    intent.putExtra("PromoAmount",promocode.getAmount());
                    intent.putExtra("Status",promocode.getStatus());
                    intent.putExtra("Date",promocode.getToDate());

                    setResult(RESULT_OK, intent);
                    finish();
                }
                else if(promocode.getStatus().equals("Used")){
                    Intent intent=new Intent();
                    intent.putExtra("PromoCode",promocode.getVoucherCode());
                    intent.putExtra("PromoAmount",promocode.getAmount());
                    intent.putExtra("Status",promocode.getStatus());
                    intent.putExtra("Date",promocode.getToDate());

                    setResult(RESULT_OK, intent);
                    finish();
                }


            }
            else if(strServiceType.equals("G")){

                if(promocode.getStatus().equals("Unused")){
                    Intent intent=new Intent();
                    intent.putExtra("PromoCode",promocode.getVoucherCode());
                    intent.putExtra("PromoAmount",promocode.getAmount());
                    intent.putExtra("Status",promocode.getStatus());
                    intent.putExtra("Date",promocode.getToDate());

                    setResult(RESULT_OK, intent);
                    finish();
                }
                else if(promocode.getStatus().equals("Used")){
                    Intent intent=new Intent();
                    intent.putExtra("PromoCode",promocode.getVoucherCode());
                    intent.putExtra("PromoAmount",promocode.getAmount());
                    intent.putExtra("Status",promocode.getStatus());
                    intent.putExtra("Date",promocode.getToDate());

                    setResult(RESULT_OK, intent);
                    finish();
                }


            }
            else if(strServiceType.equals("E")){

                if(promocode.getStatus().equals("Unused")){
                    Intent intent=new Intent();
                    intent.putExtra("PromoCode",promocode.getVoucherCode());
                    intent.putExtra("PromoAmount",promocode.getAmount());
                    intent.putExtra("Status",promocode.getStatus());
                    intent.putExtra("Date",promocode.getToDate());

                    setResult(RESULT_OK, intent);
                    finish();
                }
                else if(promocode.getStatus().equals("Used")){
                    /*Intent intent=new Intent();
                    intent.putExtra("PromoCode",promocode.getVoucherCode());
                    intent.putExtra("PromoAmount",promocode.getAmount());
                    intent.putExtra("Status",promocode.getStatus());
                    intent.putExtra("Date",promocode.getToDate());

                    setResult(RESULT_OK, intent);*/
                    Toast.makeText(PromocodeActivity.this,"Promocode already used",Toast.LENGTH_SHORT).show();
                    finish();
                }


            }

            /* For Cab Promocode*/
            else if(strServiceType.equals("X")){

                if(promocode.getStatus().equals("Unused")){
                    Intent intent=new Intent();
                    intent.putExtra("PromoCode",promocode.getVoucherCode());
                    intent.putExtra("PromoAmount",promocode.getAmount());
                    intent.putExtra("Status",promocode.getStatus());
                    intent.putExtra("Date",promocode.getToDate());

                    setResult(RESULT_OK, intent);
                    finish();
                }
                else if(promocode.getStatus().equals("Used")){
                    /*Intent intent=new Intent();
                    intent.putExtra("PromoCode",promocode.getVoucherCode());
                    intent.putExtra("PromoAmount",promocode.getAmount());
                    intent.putExtra("Status",promocode.getStatus());
                    intent.putExtra("Date",promocode.getToDate());

                    setResult(RESULT_OK, intent);*/
                    Toast.makeText(PromocodeActivity.this,"Promocode already used",Toast.LENGTH_SHORT).show();
                    finish();
                }


            }

        }

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


}
