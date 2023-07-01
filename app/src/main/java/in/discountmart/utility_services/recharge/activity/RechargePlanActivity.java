package in.discountmart.utility_services.recharge.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.recharge.adapter.RechargePlanDetailAdapter;
import in.discountmart.utility_services.recharge.adapter.RechargePlanDynamicAdapter;
import in.discountmart.utility_services.recharge.call_recharge_api.RechargeApi;
import in.discountmart.utility_services.recharge.fragment.FragmentDynamicTabs;
import in.discountmart.utility_services.recharge.recharge_model.RechargePlanDetails;
import in.discountmart.utility_services.recharge.recharge_model.recharge_request_model.RechargePlanDetailRequest;
import in.discountmart.utility_services.recharge.recharge_model.recharge_request_model.RechargePlanRequest;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.RechargePlanDetail;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.RechargePlanResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.utilities.TokenBase64;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RechargePlanActivity extends AppCompatActivity implements FragmentDynamicTabs.PlanDetailListener {
    private ViewPager viewPager;
    AppBarLayout appBarLayout;
    private TabLayout mTabLayout;
    ProgressDialog progressDialog;
    View view;
    PlanDetailListener listener=null;
    String strMobileNo="";
    String strCircle="";
    String strIpcode="";
    int planListSize=0;
    int planPosition=0;

    Handler handler;
    Handler handler2;
    private CompositeDisposable disposable = new CompositeDisposable();
    ArrayList<RechargePlanResponse.RechargePlansUnique> plansArrayList;
    HashMap<String, ArrayList<RechargePlanDetails.PlanDetail>> mapPlanDetailList;
    ArrayList<RechargePlanDetails> planDetailsList;
    //ArrayList<RechargePlanDetails.PlanDetail> planDetailsList;

    ArrayList<RechargePlanDetail> planDetailArrayList;
    ArrayList<RechargePlanResponse.RechargePlansUnique> planArrayList;
    RechargePlanDetailAdapter planDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_plan);
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Recharge Plan");
            view=findViewById(android.R.id.content);

            /* Get Bundle / Intent value*/
            Intent intent=getIntent();
            if(intent != null){
                strMobileNo=intent.getStringExtra("Mobile");
                strIpcode=intent.getStringExtra("IPCode");
                strCircle=intent.getStringExtra("Circle");
            }

            // Call method
            initViews();



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* Public method of content view
    * and set their reference*/
    public void initViews(){
        viewPager=findViewById(R.id.recharge_plan_act_viewpager);
        mTabLayout=findViewById(R.id.recharge_plan_act_tabs);

        // setOffscreenPageLimit means number
        // of tabs to be shown in one page
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // setCurrentItem as the tab position
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
               // viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
        //setDynamicFragmentToTabLayout();
        planDetailsList=new ArrayList<RechargePlanDetails>();
        getRechargePlan();




    }

    /* Request and Response Service Provider List*/
    public void getRechargePlan() {

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

            /*Main Request Model*/

            BaseHeaderRequest headerRequest = new BaseHeaderRequest();
            headerRequest.setUserName(loginResponse.getUserName());
            headerRequest.setPassword(loginResponse.getPasswd());
            headerRequest.setSponsorFormNo(companyId);
//            if(loginResponse.getMemMode().equals("D"))
//                headerRequest.setSponsorFormNo(companyId);
//            else
//                headerRequest.setSponsorFormNo("");

            /*Getcity List Request Model*/
            RechargePlanRequest request = new RechargePlanRequest();
            request.setCircle(strCircle);
            request.setMobileno(strMobileNo);
            request.setIpCode(strIpcode);


            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(request);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<BaseResponse> fetchServiceProviderListCall =
                NetworkClient_Utility_1.getInstance(this).create(RechargeApi.class).fetchRechargePlan(apiRequest, strToken);

        fetchServiceProviderListCall.enqueue(new Callback<BaseResponse>() {
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
                                //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();
                            } else if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                //String[] serviceListResponse = Response.getRESPONSE().split("");
                                RechargePlanResponse serviceList = new Gson().fromJson(Response.getRESP_VALUE(), RechargePlanResponse.class);
                                if (serviceList != null) {
                                    //RechargePlanResponse serviceList = new Gson().fromJson(Response.getRESP_VALUE(), RechargePlanResponse.class);
                                    plansArrayList=new ArrayList<RechargePlanResponse.RechargePlansUnique>();
                                    plansArrayList= serviceList.getLstUniquePlan();
                                    //List<GetServiceProviderResponse> serviceArrayList = new ArrayList<GetServiceProviderResponse>(Arrays.asList(serviceList));
                                    //planListSize=
                                    if(plansArrayList != null && plansArrayList.size() > 0){
                                       // planListSize=plansArrayList.size();
                                        setDynamicFragmentToTabLayout(plansArrayList);


                                    }

                                } else {
                                    String toast = " City List empty";
                                    Toast.makeText(RechargePlanActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    //showSnackbar(toast);
                                }
                            }
                        }
                        else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(RechargePlanActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RechargePlanActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(RechargePlanActivity.this, toast, Toast.LENGTH_SHORT).show();
                            // showSnackbar(toast);

                        }
                    }
                    else {
                        //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                        String toast = "something wrong";
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
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                // Toast.makeText(FlightCityListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void createHandler(String plan, String ipcode, String circle) {
        Thread thread = new Thread() {
            public void run() {
                Looper.prepare();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //if(planListSize > 0){
                            //getRechargePlanDetail(plan,ipcode,circle);
                        //}
                        // Do Work
                        handler.removeCallbacks(this);
                        Looper.myLooper().quit();
                    }
                }, 6000);

                Looper.loop();
            }
        };
        thread.start();
    }

    /* Request and Response Service Provider List*/
    public void getRechargePlanDetail(String plan,String ipcode,String circle) {

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

            /*Main Request Model*/

            BaseHeaderRequest headerRequest = new BaseHeaderRequest();
            headerRequest.setUserName(loginResponse.getUserName());
            headerRequest.setPassword(loginResponse.getPasswd());
            headerRequest.setSponsorFormNo(companyId);
//            if(loginResponse.getMemMode().equals("D"))
//                headerRequest.setSponsorFormNo(companyId);
//            else
//                headerRequest.setSponsorFormNo("");

            /*Getcity List Request Model*/
            RechargePlanDetailRequest request = new RechargePlanDetailRequest();
            request.setCircle(circle);
            request.setPlan(plan);
            request.setIpCode(ipcode);


            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(request);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<BaseResponse> fetchServiceProviderListCall =
                NetworkClient_Utility_1.getInstance(this).create(RechargeApi.class).fetchRechargePlanDetail(apiRequest, strToken);

        fetchServiceProviderListCall.enqueue(new Callback<BaseResponse>() {
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
                                //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();
                            } else if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                String[] planDetails = Response.getRESPONSE().split("");
                                //RechargePlanDetail serviceList = new Gson().fromJson(Response.getRESP_VALUE(), RechargePlanDetail.class);
                                //planListSize--;

                                planDetailArrayList=new ArrayList<RechargePlanDetail>();
                                if (planDetails != null && planDetails.length > 0) {
                                    //planListSize--;

                                    //layoutNoResult.setVisibility(View.GONE);
                                    //layoutResult.setVisibility(View.VISIBLE);
                                    RechargePlanDetail[] serviceList = new Gson().fromJson(Response.getRESP_VALUE(), RechargePlanDetail[].class);
                                    ArrayList<RechargePlanDetail> planDetailList = new ArrayList<RechargePlanDetail>(Arrays.asList(serviceList));
                                    //planDetailArrayList = new ArrayList<RechargePlanDetail>();
                                  //  planDetailsList=new ArrayList<RechargePlanDetails>();
                                    mapPlanDetailList=new HashMap<String,ArrayList<RechargePlanDetails.PlanDetail>>();
                                    //ArrayList<RechargePlanDetails> list=new ArrayList<>();

                                    setPlanDetailList(planDetailList);

                                }
                                else {
                                    RechargePlanDetail[] serviceList = new Gson().fromJson(Response.getRESP_VALUE(), RechargePlanDetail[].class);
                                    List<RechargePlanDetail> planDetailList = new ArrayList<RechargePlanDetail>(Arrays.asList(serviceList));
                                    //planDetailArrayList = new ArrayList<RechargePlanDetail>();
                                    //planDetailArrayList.clear();
                                   // planDetailsList.clear();

                                }

                            }
                        }
                        else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(RechargePlanActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RechargePlanActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(RechargePlanActivity.this, toast, Toast.LENGTH_SHORT).show();
                            // showSnackbar(toast);

                        }
                    }
                    else {
                        //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                        String toast = "something wrong";
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
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                // Toast.makeText(FlightCityListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    @SuppressLint("CheckResult")

    private void callRechargePlanDataApi(String plan,String ipcode,String circle) {

        if(planListSize > 0){

            Timer timer=  new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    //getRechargePlanDetail(plan,ipcode,circle);




                }
            },0,6000);

        }
        else {

        }

    }

    public interface PlanDetailListener {
        void onItemSelect(RechargePlanDetail item);
    }
    private  void setPlanDetailList(ArrayList<RechargePlanDetail> planDetailList){
        try {
            String planName="";
            if(planDetailList!=null && planDetailList.size()>0){



                for(int j=planPosition; j<plansArrayList.size(); j++){
                    ArrayList<RechargePlanDetails.PlanDetail> objectList = new ArrayList<>();
                     planName=plansArrayList.get(j).getRecharge_short_description();
                    RechargePlanDetails planDetails1=new RechargePlanDetails();
                    planDetails1.setPlanName(plansArrayList.get(j).getRecharge_short_description());

                    for(int i=0; i < planDetailList.size(); i++){
                        int index=planDetailList.size();
                        //if(index > 0)
                        RechargePlanDetails.PlanDetail detail=new RechargePlanDetails.PlanDetail();
                        detail.setPlanName(planName);
                        detail.setId(planDetailList.get(i).getId());
                        detail.setSp_key(planDetailList.get(i).getSp_key());
                        detail.setSp_circle(planDetailList.get(i).getSp_circle());
                        detail.setRecharge_value(planDetailList.get(i).getRecharge_value());
                        detail.setRecharge_talktime(planDetailList.get(i).getRecharge_talktime());
                        detail.setDetails(planDetailList.get(i).getDetails());
                        detail.setRecharge_validity(planDetailList.get(i).getRecharge_validity());
                        detail.setRecharge_short_description(planDetailList.get(i).getRecharge_short_description());
                        detail.setRecharge_description(planDetailList.get(i).getRecharge_description());
                        detail.setLast_updated_dt(planDetailList.get(i).getLast_updated_dt());
                        objectList.add(detail);


                        //mapPlanDetailList.put(sortID,objectList);

                    }
                    planDetails1.setPlandetails(objectList);
                    if(planDetailsList == null)
                        planDetailsList=new ArrayList<>();

                    planDetailsList.add(j,planDetails1);
                    planListSize--;
                    planPosition++;
                    break;

                }

                if(planListSize > 0){

                    String strPlan=plansArrayList.get(plansArrayList.size()-planListSize).getRecharge_short_description();
                    getRechargePlanDetail(strPlan,strIpcode,strCircle);

                }else {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }

            }

            if(plansArrayList.size()== planDetailsList.size()){

                RechargePlanDynamicAdapter mDynamicFragmentAdapter = new RechargePlanDynamicAdapter(planName,getSupportFragmentManager(), mTabLayout.getTabCount(),planDetailsList,strCircle,strIpcode);

                // set the adapter
                viewPager.setAdapter(mDynamicFragmentAdapter);

                // set the current item as 0 (when app opens for first time)
                viewPager.setCurrentItem(0);


            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // show all the tab using DynamicFragmnetAdapter
    private void setDynamicFragmentToTabLayout(ArrayList<RechargePlanResponse.RechargePlansUnique> plansArrayList) {
        // here we have given 10 as the tab number
        // you can give any number here
        try {
            if(plansArrayList != null && plansArrayList.size()> 0){

                planListSize=plansArrayList.size();
                String planName="";
                for (int i = 0; i < plansArrayList.size(); i++) {
                    // set the tab name as "Page: " + i
                    mTabLayout.addTab(mTabLayout.newTab().setText(plansArrayList.get(i).getRecharge_short_description()));
                    //mTabLayout.addTab(mTabLayout.newTab().sett(plansArrayList.get(i).getRecharge_short_description()));
                    planName=plansArrayList.get(i).getRecharge_short_description();
                    String finalPlanName = planName;

                    //createHandler(finalPlanName,strIpcode,strCircle);
                    if(i==0){
                        String strPlan=plansArrayList.get(i).getRecharge_short_description();
                        getRechargePlanDetail(strPlan,strIpcode,strCircle);
                    }

                    /*if(planListSize > 0){
                               getRechargePlanDetail(finalPlanName,strIpcode,strCircle);

                           }*/

                   /* runOnUiThread(new Runnable() {
                        public void run() {
                            //textView.setText(String.valueOf("Plan :  " + planArrayList.get(position).getRecharge_short_description()));
                            //String rechargeplan=planArrayList.get(position).getRecharge_short_description();
                            // getRechargePlanDetail(rechargeplan,strIpcode,strCircle);

                            if(planListSize > 0){
                                getRechargePlanDetail(finalPlanName,strIpcode,strCircle);

                            }

                        }
                    });*/
                   // handler = new Handler(Looper.getMainLooper());


                    /*handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(planListSize > 0){
                                progressDialog = new ProgressDialog(RechargePlanActivity.this);
                                progressDialog.setMessage("Please wait");
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                callRechargePlanDataApi(finalPlanName,strIpcode,strCircle);
                                if(progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }
                            else {
                                if(progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }
                        }
                    }, 4000 );*/


                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onPlanSelect(RechargePlanDetail item) {
        try {

            if(item != null){
                Intent intent=new Intent();
                intent.putExtra("PlanDetail",item.getRecharge_description());
                intent.putExtra("Value",item.getRecharge_value());
                setResult(RESULT_OK, intent);
                finish();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /* Request and Response Service Provider List*/
   
}