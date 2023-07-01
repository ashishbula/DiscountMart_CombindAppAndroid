package in.discountmart.utility_services.billpayment.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.Collections;
import java.util.List;

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.billpayment.adapter.BillPayServiceProviderAdaper;
import in.discountmart.utility_services.billpayment.bill_pay_call_api.BillPayApi;
import in.discountmart.utility_services.billpayment.billpay_model.billpay_request_model.BillPayServiceProviderListRequest;
import in.discountmart.utility_services.billpayment.billpay_model.billpay_response_model.BillPayServiceProviderListResonse;
import in.discountmart.utility_services.billpayment.billpay_shared_preferance.BillPaySharedPreferance;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.DividerItemDecoration;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillPayServiceListActivity extends AppCompatActivity implements BillPayServiceProviderAdaper.ServiceProviderAdapterListener {
    LinearLayout layoutHeader;
    RecyclerView recyclerViewList;
    BillPayServiceProviderAdaper adapter;
    ArrayList<BillPayServiceProviderListResonse> shortList;
    public static TextView txtAge1;
    public static TextView txtAge2;

    ProgressDialog progressDialog;
    String type;
    public static int age;

    String serviceType="";
    String serviceTypeId="";
    View view;
    String home="";

    /*Array List of Object*/
    ArrayList<BillPayServiceProviderListResonse> serviceProviderArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bill_pay_service_list);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        //view = findViewById(R.id.content);
        try {
            view=findViewById(android.R.id.content);
            layoutHeader = (LinearLayout) findViewById(R.id.billpay_service_list_act_layout_header);
            recyclerViewList = (RecyclerView) findViewById(R.id.billpay_service_list_act_recycler_list);

            Intent intent = getIntent();
            home=intent.getStringExtra("Home");

            if(home.equals("true")){
                serviceType= BillPaySharedPreferance.getServiceType(this);
                serviceTypeId= BillPaySharedPreferance.getServiceTypeID(this);
            }
            else {
                serviceType = intent.getStringExtra("ServiceType");
                serviceTypeId = intent.getStringExtra("ServiceTypeId");
            }



            serviceProviderArrayList = new ArrayList<BillPayServiceProviderListResonse>();

            //if (serviceProviderArrayList.size() > 0) {
            //flightSearchList = new ArrayList<>();
            adapter = new BillPayServiceProviderAdaper(BillPayServiceListActivity.this, serviceProviderArrayList, BillPayServiceListActivity.this);
            // }

            /*REcycler View set divider item line*/
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerViewList.setLayoutManager(mLayoutManager);
            recyclerViewList.setItemAnimator(new DefaultItemAnimator());
            recyclerViewList.addItemDecoration(new DividerItemDecoration(BillPayServiceListActivity.this, DividerItemDecoration.VERTICAL_LIST, 0));
            recyclerViewList.setAdapter(adapter);
            /*Call api */

            if(!ConnectivityUtils.isNetworkEnabled(BillPayServiceListActivity.this)){
                Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();
            }
            else
            {
                getServiceProviderList();
            }



            layoutHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSelected(BillPayServiceProviderListResonse item) {

        try {

            if(item != null){

                if(home.equals("true")){
                    if(serviceType.equals("G") ) {

                        ArrayList<BillPayServiceProviderListResonse> mobproviderArrayList = new ArrayList<BillPayServiceProviderListResonse>(Collections.singleton(item));
                        if (mobproviderArrayList.size() > 0) {

                            Intent intent = new Intent(BillPayServiceListActivity.this,GasBillActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("ServiceId",item.getServiceId());
                            bundle.putString("Home","true");
                            bundle.putSerializable("ServiceProviderList", mobproviderArrayList);

                            intent.putExtras(bundle);

                            //DthRechargeActivity.txtService.setText(item.getCompanyName());

                           // RechargeSharedPreferance.setDthServiceProvider(this, item.getCompanyName());
                            //RechargeSharedPreferance.setServiceType(this, "D");
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                        }
                    }
                    else if (serviceType.equals("C")) {

                        ArrayList<BillPayServiceProviderListResonse> dthproviderArrayList = new ArrayList<BillPayServiceProviderListResonse>(Collections.singleton(item));

                        if (dthproviderArrayList.size() > 0) {
                            Intent intent = new Intent(BillPayServiceListActivity.this,CreditCardBillPayActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("ServiceId",item.getServiceId());
                            bundle.putString("Home","true");
                            bundle.putSerializable("ServiceProviderList", dthproviderArrayList);

                            intent.putExtras(bundle);

                            //DthRechargeActivity.txtService.setText(item.getCompanyName());

                            // RechargeSharedPreferance.setDthServiceProvider(this, item.getCompanyName());
                            //RechargeSharedPreferance.setServiceType(this, "D");
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);


                           // startActivity(intent);
                            //finish();
                            //overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        }


                    }

                    else if (serviceType.equals("I")) {

                        ArrayList<BillPayServiceProviderListResonse> dthproviderArrayList = new ArrayList<BillPayServiceProviderListResonse>(Collections.singleton(item));

                        if (dthproviderArrayList.size() > 0) {
                            Intent intent = new Intent(BillPayServiceListActivity.this,InsuranceBillPayActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("ServiceId",item.getServiceId());
                            bundle.putString("Home","true");
                            bundle.putSerializable("ServiceProviderList", dthproviderArrayList);

                            intent.putExtras(bundle);

                            //DthRechargeActivity.txtService.setText(item.getCompanyName());

                            // RechargeSharedPreferance.setDthServiceProvider(this, item.getCompanyName());
                            //RechargeSharedPreferance.setServiceType(this, "D");
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);


                            // startActivity(intent);
                            //finish();
                            //overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        }


                    }
                    else if (serviceType.equals("E")) {

                        ArrayList<BillPayServiceProviderListResonse> dthproviderArrayList = new ArrayList<BillPayServiceProviderListResonse>(Collections.singleton(item));

                        if (dthproviderArrayList.size() > 0) {
                            Intent intent = new Intent(BillPayServiceListActivity.this,ElectricityBillPayActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("ServiceId",item.getServiceId());
                            bundle.putString("Home","true");
                            bundle.putSerializable("ServiceProviderList", dthproviderArrayList);

                            intent.putExtras(bundle);

                            //DthRechargeActivity.txtService.setText(item.getCompanyName());

                            // RechargeSharedPreferance.setDthServiceProvider(this, item.getCompanyName());
                            //RechargeSharedPreferance.setServiceType(this, "D");
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);


                            // startActivity(intent);
                            //finish();
                            //overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        }


                    }

                }
                else if(home.equals("false")){

                    if(serviceType.equals("G") ) {

                        ArrayList<BillPayServiceProviderListResonse> mproviderArrayList = new ArrayList<BillPayServiceProviderListResonse>(Collections.singleton(item));
                        if (mproviderArrayList.size() > 0) {
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("ServiceProviderList", mproviderArrayList);
                            bundle.putString("ServiceId", item.getServiceId());
                            intent.putExtras(bundle);

                            //MobileRechargeActivity.txtOperator.setText(item.getCompanyName());
                            setResult(RESULT_OK, intent);
                            finish();

                        }
                    }
                    else if (serviceType.equals("C")) {

                        ArrayList<BillPayServiceProviderListResonse> dproviderArrayList = new ArrayList<BillPayServiceProviderListResonse>(Collections.singleton(item));

                        if (dproviderArrayList.size() > 0) {
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("ServiceProviderList", dproviderArrayList);
                            bundle.putString("ServiceId", item.getServiceId());
                            intent.putExtras(bundle);

                            //MobileRechargeActivity.txtOperator.setText(item.getCompanyName());
                            setResult(RESULT_OK, intent);
                            finish();
                        }


                    }

                    else if (serviceType.equals("I")) {

                        ArrayList<BillPayServiceProviderListResonse> dproviderArrayList = new ArrayList<BillPayServiceProviderListResonse>(Collections.singleton(item));

                        if (dproviderArrayList.size() > 0) {
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("ServiceProviderList", dproviderArrayList);
                            bundle.putString("ServiceId", item.getServiceId());
                            intent.putExtras(bundle);

                            //MobileRechargeActivity.txtOperator.setText(item.getCompanyName());
                            setResult(RESULT_OK, intent);
                            finish();
                        }


                    }
                    else if (serviceType.equals("E")) {

                        ArrayList<BillPayServiceProviderListResonse> dproviderArrayList = new ArrayList<BillPayServiceProviderListResonse>(Collections.singleton(item));

                        if (dproviderArrayList.size() > 0) {
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("ServiceProviderList", dproviderArrayList);
                            bundle.putString("ServiceId", item.getServiceId());
                            intent.putExtras(bundle);

                            //MobileRechargeActivity.txtOperator.setText(item.getCompanyName());
                            setResult(RESULT_OK, intent);
                            finish();
                        }


                    }

                }


            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* Request and Response Service Provider List*/
    public void getServiceProviderList() {

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
            BillPayServiceProviderListRequest providerRequest = new BillPayServiceProviderListRequest();
            providerRequest.setServiceTypeId(serviceTypeId);


            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(providerRequest);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<BaseResponse> fetchServiceProviderListCall =
                NetworkClient_Utility_1.getInstance(this).create(BillPayApi.class).fetchBillPayServiceList(apiRequest, strToken);

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
                            }
                            else if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                String[] serviceListResponse = Response.getRESPONSE().split("");
                                if (serviceListResponse.length > 0) {
                                    BillPayServiceProviderListResonse[] serviceList = new Gson().fromJson(Response.getRESP_VALUE(), BillPayServiceProviderListResonse[].class);
                                    List<BillPayServiceProviderListResonse> serviceArrayList = new ArrayList<BillPayServiceProviderListResonse>(Arrays.asList(serviceList));

                                    // adding contacts to contacts list
                                    serviceProviderArrayList.clear();
                                    serviceProviderArrayList.addAll(serviceArrayList);

                                    // refreshing recycler view
                                    adapter.notifyDataSetChanged();
                                } else {
                                    String toast = " City List empty";
                                    Toast.makeText(BillPayServiceListActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(BillPayServiceListActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(BillPayServiceListActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(BillPayServiceListActivity.this, toast, Toast.LENGTH_SHORT).show();
                            // showSnackbar(toast);

                        }
                    }
                    else {
                        //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                        String toast = "something wrong..";
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
}
