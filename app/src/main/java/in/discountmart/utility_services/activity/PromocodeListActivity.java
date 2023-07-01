package in.discountmart.utility_services.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
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

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.adapter.PromocodeListAdapter;
import in.discountmart.utility_services.call_api.MainServices;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.request_model.PromocodeRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.model.response_model.PromocodeRespose;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.DividerItemDecoration;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PromocodeListActivity extends AppCompatActivity  {
    RecyclerView recyViewPromo;
    ImageView imgClose;
    PromocodeListAdapter promocodeAdapter;
    LinearLayout layoutRecord;
    LinearLayout layoutNoRecord;
    ProgressDialog progressDialog;
    LinearLayout layoutPromoDate;
    TextView txtPromoDate;
    TextView txtPromoMsg;
    ArrayList<PromocodeRespose> promocodeList;
    Context context;
    String strServiceType="";
    String strServiceId="0";
    boolean promoUse=false;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_promocode_list);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        view=findViewById(android.R.id.content);
        try {

            /*for toolbar button and title*/
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Utility Promo Code");

            recyViewPromo=(RecyclerView)findViewById(R.id.promolist_activity_recycler);
            layoutRecord=(LinearLayout)findViewById(R.id.promolist_activity_layout_promo);
            layoutNoRecord=(LinearLayout)findViewById(R.id.promolist_activity_layout_norecord);


            promocodeList = new ArrayList<PromocodeRespose>();
            promocodeAdapter = new PromocodeListAdapter(PromocodeListActivity.this, promocodeList);

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

                getPromocodeList();
            }


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
        loginResponse=new LoginPreferences_Utility(PromocodeListActivity.this).getLoggedinUser();
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
                                Toast.makeText(PromocodeListActivity.this, toast, Toast.LENGTH_SHORT).show();

                            }

                            else if(Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")){
                                String[] promoList=Response.getRESP_VALUE().split("");
                                if( promoList != null && promoList.length > 0){

                                    PromocodeRespose[]promoResList= new Gson().fromJson(Response.getRESP_VALUE(),PromocodeRespose[].class);

                                    if( promoResList != null && promoResList .length > 0){

                                        layoutRecord.setVisibility(View.VISIBLE);
                                        layoutNoRecord.setVisibility(View.GONE);
                                        List<PromocodeRespose> tempList=new ArrayList<PromocodeRespose>(Arrays.asList(promoResList));

                                        // adding contacts to contacts list
                                        promocodeList.clear();
                                        promocodeList.addAll(tempList);

                                        // refreshing recycler view

                                        promocodeAdapter.setPromoVerify(promoUse, PromocodeListActivity.this);

                                        promocodeAdapter.notifyDataSetChanged();
                                    }
                                    else {
                                        layoutRecord.setVisibility(View.GONE);
                                        layoutNoRecord.setVisibility(View.VISIBLE);

                                    }


                                }

                                else {
                                    layoutRecord.setVisibility(View.GONE);
                                    layoutNoRecord.setVisibility(View.VISIBLE);

                                }
                            }
                        }
                        else  if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")){

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(PromocodeListActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(PromocodeListActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(PromocodeListActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(PromocodeListActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(PromocodeListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

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
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

    }


}
