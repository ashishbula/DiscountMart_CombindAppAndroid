package in.discountmart.utility_services.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONObject;

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.activity.LoginActivity;
import in.discountmart.utility.ConnectivityUtils;
import in.discountmart.utility_services.call_api.MainServices;
import in.discountmart.utility_services.fund.activity.AddFundActivity;
import in.discountmart.utility_services.fund.activity.FundRequestActivity;
import in.discountmart.utility_services.fund.activity.FundRequestStatusActivity;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.request_model.MainBalanceRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.model.response_model.MainBalanceResponse;
import in.discountmart.utility_services.report.activity.LedgerReportActivity;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWalletActivity extends AppCompatActivity {

    Context context;
    View view;
    TextView textViewTotalBalance;
    TextView txtAvailBal_Utility;
    TextView txtAvailBal_Shopping;
    TextView txtAvailBal_Business;

    Button button_viewdetail_Utility;
    Button button_viewdetail_Shopping;
    Button button_viewdetail_Business;

    Double availWalletBal_utility = 0.0;
    Double availWalletBal_shopping= 0.0;
    Double availWalletBal_business= 0.0;

    LinearLayout layout_AddFund;
    LinearLayout layout_AddFund_content;

    TextView txtAdd_Fund;
    TextView txtFund_Request;
    TextView txtFund_Request_Status;
    TextView txtGet_Promocode;
    TextView txt_LedgerReport;

    LinearLayout layout_FundTranfer;
    LinearLayout layout_FundTranfer_Content;

    TextView txtBusinees_to_shopping;
    TextView txtbusiness_to_other;
    TextView txtBankwithdrawal;

    boolean flagAddFund = false;
    boolean flagFundTransfer = false;

    ProgressDialog pDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_activity_mywallet);
        try {

            view = findViewById(android.R.id.content);
            textViewTotalBalance = (TextView) findViewById(R.id.txt_name_total_balance);
            txtAvailBal_Utility = (TextView) findViewById(R.id.txt_name_available_balance_utility);
            txtAvailBal_Shopping = (TextView) findViewById(R.id.txt_name_available_balance_shopping);
            txtAvailBal_Business = (TextView) findViewById(R.id.txt_name_available_balance_business);

             button_viewdetail_Utility = (Button) findViewById(R.id.button_viewdetail_utility);
             button_viewdetail_Shopping = (Button) findViewById(R.id.button_viewdetail_shopping);
             button_viewdetail_Business = (Button) findViewById(R.id.button_viewdetail_business);

             layout_AddFund = (LinearLayout) findViewById(R.id.layout_add_fund);
             layout_AddFund_content= (LinearLayout) findViewById(R.id.layout_add_fund_content);

            txtAdd_Fund = (TextView) findViewById(R.id.txt_name_add_fund);
            txtFund_Request = (TextView) findViewById(R.id.txt_name_fund_request);
            txtFund_Request_Status = (TextView) findViewById(R.id.txt_name_fund_request_status);
            txtGet_Promocode = (TextView) findViewById(R.id.txt_name_promocode);
            txt_LedgerReport= (TextView) findViewById(R.id.txt_name_ledgerreport);

            layout_FundTranfer = (LinearLayout) findViewById(R.id.layout_fund_tranfer);
            layout_FundTranfer_Content= (LinearLayout) findViewById(R.id.layout_fund_tranfer_content);

             txtBusinees_to_shopping= (TextView) findViewById(R.id.txt_business_to_shopping);
             txtbusiness_to_other= (TextView) findViewById(R.id.txt_business_to_other);
             txtBankwithdrawal= (TextView) findViewById(R.id.txt_bank_withdrawal);

            context = MyWalletActivity.this;
            // Enabling Up / Back navigation
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Wallet");

            /*Call From Wallet type*/
            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(MyWalletActivity.this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            } else {
                // getWalletList("From",strWalletType);
                getMainBalance_utility();
            }

            /* Button View bottom Business on click*/
           /* button_viewdetail_Business.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent mainIntent = new Intent(MyWalletActivity.this, CommonReportActivity.class);
                    mainIntent.putExtra("Title", "Income Report");
                    mainIntent.putExtra("Type","Wallet_M");
                    startActivity(mainIntent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });*/

            /* Button View bottom Shopping on click*/
           /* button_viewdetail_Shopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainIntent = new Intent(MyWalletActivity.this, CommonReportActivity.class);
                    mainIntent.putExtra("Title", "Main Wallet Report");
                    mainIntent.putExtra("Type","Wallet_R");
                    startActivity(mainIntent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });*/

            /* Button View bottom Utility on click*/
            button_viewdetail_Utility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent fundIntent=new Intent(MyWalletActivity.this, LedgerReportActivity.class);
                    startActivity(fundIntent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                }
            });

            layout_AddFund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(flagAddFund) {
                        layout_AddFund_content.setVisibility(View.GONE);
                        flagAddFund=false;
                        if(flagFundTransfer) {
                            layout_FundTranfer_Content.setVisibility(View.GONE);
                            flagFundTransfer=false;
                        }

                    }

                    else {
                        layout_AddFund_content.setVisibility(View.VISIBLE);
                        flagAddFund=true;
                        if(flagFundTransfer) {
                            layout_FundTranfer_Content.setVisibility(View.GONE);
                            flagFundTransfer=false;
                        }
                    }

                }
            });

            layout_FundTranfer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(flagFundTransfer) {
                        layout_FundTranfer_Content.setVisibility(View.GONE);
                        flagFundTransfer=false;
                        if(flagAddFund) {
                            layout_AddFund_content.setVisibility(View.GONE);
                            flagAddFund=false;
                        }
                    }
                    else {
                        layout_FundTranfer_Content.setVisibility(View.VISIBLE);
                        flagFundTransfer=true;
                        if(flagAddFund) {
                            layout_AddFund_content.setVisibility(View.GONE);
                            flagAddFund=false;
                        }
                    }

                }
            });

            txtAdd_Fund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MyWalletActivity.this, AddFundActivity.class);
                    intent.putExtra("GW_Bus","");
                    intent.putExtra("Home","true");
                    intent.putExtra("DeductAmount","");
                    intent.putExtra("ServiceType","");
                    startActivity(intent);
                }
            });

            txtFund_Request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent fundIntent=new Intent(MyWalletActivity.this, FundRequestActivity.class);
                    startActivity(fundIntent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                }
            });
            txtFund_Request_Status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent fundIntent=new Intent(MyWalletActivity.this, FundRequestStatusActivity.class);
                    startActivity(fundIntent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                }
            });
            txtGet_Promocode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent fundIntent=new Intent(MyWalletActivity.this, PromocodeListActivity.class);
                    startActivity(fundIntent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                }
            });

           /* txtBankwithdrawal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent fundIntent=new Intent(MyWalletActivity.this, BankWithdrawalActivity.class);
                    startActivity(fundIntent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                }
            });*/

            /*txtBusinees_to_shopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MyWalletActivity.this, WalletTransferActivity_1.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });*/

            /*txtbusiness_to_other.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MyWalletActivity.this, WalletTransferActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });*/
           /* txt_LedgerReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent fundIntent=new Intent(MyWalletActivity.this, LedgerReportActivity.class);
                    startActivity(fundIntent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                }
            });*/


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private void getM_WalletBalance_Business(String wallet) {

        pDialog = new ProgressDialog(MyWalletActivity.this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        GetWalletBalanceRequest baseRequest = new GetWalletBalanceRequest();
        *//*Set value in Entity class*//*
        baseRequest.setReqtype(ApiConstants.REQUEST_WALLET_BAL);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(MyWalletActivity.this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(MyWalletActivity.this));
        baseRequest.setWallettype(wallet);
        String request = new Gson().toJson(baseRequest);
        Log.e("Request", request);

        Call<GetWalleBalanceResponse> walleBalanceResponseCall =
                NetworkClient.getInstance(this).create(WalletServices.class).fetchWalletBalance(baseRequest);

        walleBalanceResponseCall.enqueue(new Callback<GetWalleBalanceResponse>() {
            @Override
            public void onResponse(Call<GetWalleBalanceResponse> call, Response<GetWalleBalanceResponse> response) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    GetWalleBalanceResponse Response = new GetWalleBalanceResponse();
                    Response = response.body();

                    if (Response.getResponse().equals("OK")) {

                        txtAvailBal_Business.setText(Response.getBalance());
                        availWalletBal_business = Double.parseDouble(Response.getBalance());
                        Double totalBal =  availWalletBal_utility + availWalletBal_shopping + availWalletBal_business;
                        textViewTotalBalance.setText(String.valueOf(totalBal));

                        getR_WalletBalance_Business("R");



                    } else if (Response.getResponse().contains("FAILED") && Response.getMsg().contains("Invalid Login")) {
                        String toast = "Invalid login detail. Please login again";
                        Toast.makeText(MyWalletActivity.this, toast, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MyWalletActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else if (Response.getResponse().contains("FAILED")) {
                        Toast.makeText(MyWalletActivity.this, Response.getResponse(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetWalleBalanceResponse> call, Throwable t) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(MyWalletActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    /*private void getR_WalletBalance_Business(String wallet) {

        pDialog = new ProgressDialog(MyWalletActivity.this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        GetWalletBalanceRequest baseRequest = new GetWalletBalanceRequest();
        *//*Set value in Entity class*//*
        baseRequest.setReqtype(ApiConstants.REQUEST_WALLET_BAL);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(MyWalletActivity.this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(MyWalletActivity.this));
        baseRequest.setWallettype(wallet);
        String request = new Gson().toJson(baseRequest);
        Log.e("Request", request);

        Call<GetWalleBalanceResponse> walleBalanceResponseCall =
                NetworkClient.getInstance(this).create(WalletServices.class).fetchWalletBalance(baseRequest);

        walleBalanceResponseCall.enqueue(new Callback<GetWalleBalanceResponse>() {
            @Override
            public void onResponse(Call<GetWalleBalanceResponse> call, Response<GetWalleBalanceResponse> response) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    GetWalleBalanceResponse Response = new GetWalleBalanceResponse();
                    Response = response.body();

                    if (Response.getResponse().equals("OK")) {

                        //Total Balance
                        txtAvailBal_Shopping.setText(Response.getBalance());
                        availWalletBal_shopping = Double.parseDouble(Response.getBalance());
                        Double totalBal =  availWalletBal_utility + availWalletBal_shopping + availWalletBal_business;
                        textViewTotalBalance.setText(String.valueOf(totalBal));



                    } else if (Response.getResponse().contains("FAILED") && Response.getMsg().contains("Invalid Login")) {
                        String toast = "Invalid login detail. Please login again";
                        Toast.makeText(MyWalletActivity.this, toast, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MyWalletActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else if (Response.getResponse().contains("FAILED")) {
                        Toast.makeText(MyWalletActivity.this, Response.getResponse(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetWalleBalanceResponse> call, Throwable t) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(MyWalletActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/


    /*public void getMainWalletReport() {
        pDialog = new ProgressDialog(MyWalletActivity.this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        MainWalletReportRequest mainWalletReportRequest = new MainWalletReportRequest();

        *//*Pos Method*//*
        String Get_Paramenter = "";
        try {

            *//*Set value in Entity class*//*
            mainWalletReportRequest.setReqtype(ApiConstants.REQUEST_SHOPPING_WALLET_REPORT);
            mainWalletReportRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
            mainWalletReportRequest.setUserid(SharedPrefrence_Business.getUserID(context));
            mainWalletReportRequest.setFrom(String.valueOf(1));
            mainWalletReportRequest.setTo(String.valueOf(10));

            //1. Convert object to JSON string
            Gson gson = new Gson();
            Get_Paramenter = gson.toJson(mainWalletReportRequest);
            Log.e("ReqShopWalletReport:", Get_Paramenter);

        } catch (Exception e) {
        }

        Call<MainWalletReportResponse> walletReportResponseCall =
                NetworkClient1.getInstance(context).create(WalletServices.class).fetchMainWalletReport(mainWalletReportRequest);

        walletReportResponseCall.enqueue(new Callback<MainWalletReportResponse>() {
            @Override
            public void onResponse(Call<MainWalletReportResponse> call, Response<MainWalletReportResponse> response) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    //string = jsonObject.toString();
                    MainWalletReportResponse mainWalletReportResponse = new MainWalletReportResponse();
                    mainWalletReportResponse = response.body();

                    if (mainWalletReportResponse.getResponse().equals("OK")) {

                        availWalletBal_shopping = Double.parseDouble(mainWalletReportResponse.getBalance());
                        txtAvailBal_Shopping.setText(availWalletBal_shopping.toString());
                        getMainBalance_utility();
                    } else {
                        Toast.makeText(context, mainWalletReportResponse.getResponse(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MainWalletReportResponse> call, Throwable t) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(MyWalletActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }*/

    public void getMainBalance_utility() {
        try {

            pDialog = new ProgressDialog(MyWalletActivity.this);
            pDialog.setMessage("please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

            String strApiRequest = "";
            JSONObject object = null;
            LoginResponse loginResponse = new LoginResponse();
            loginResponse = new LoginPreferences_Utility(this).getLoggedinUser();
            String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
            String formno = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);
            String mobile = loginResponse.getMobileNo().substring(0, loginResponse.getMobileNo().length() - 2);

            String strToken = TokenBase64.getToken();

            /*Main Request Model*/
            ApiRequest apiRequest = new ApiRequest();

            try {
                /*Base Request Model*/
                BaseHeaderRequest headerRequest = new BaseHeaderRequest();
                headerRequest.setUserName(new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
                headerRequest.setPassword(new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
                headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/
                MainBalanceRequest request = new MainBalanceRequest();
                request.setFormNo(formno);

                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(request);

                strApiRequest = new Gson().toJson(apiRequest);

                Log.e("Value", strApiRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Call<BaseResponse> fetchFlightBook =
                    NetworkClient_Utility_1.getInstance(this).create(MainServices.class).fetchGetBalance(apiRequest, strToken);

            fetchFlightBook.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    
                    try {

                        BaseResponse Response = new BaseResponse();
                        Response = response.body();

                        if(Response != null){
                            if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                if (!Response.getRESP_VALUE().equals("null") || !Response.getRESP_VALUE().equals("")) {

                                    MainBalanceResponse balanceResponse =
                                            new Gson().fromJson(Response.getRESP_VALUE(), MainBalanceResponse.class);

                                    if (balanceResponse != null) {
                                        // get Main Wallet balance
                                        new LoginPreferences_Utility(MyWalletActivity.this).getLoggedinUser().setBalance(balanceResponse.getBalance());
                                        availWalletBal_utility = Double.parseDouble(balanceResponse.getBalance());
                                        new LoginPreferences_Utility(MyWalletActivity.this).getLoggedinUser().setBalance(String.valueOf(availWalletBal_utility));
                                        txtAvailBal_Utility.setText(String.valueOf(availWalletBal_utility));

                                        Double totalBal =  availWalletBal_utility ;
                                        textViewTotalBalance.setText(String.valueOf(totalBal));
                                        //getM_WalletBalance_Business("M");

                                    }

                                } else if (Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")) {
                                    String toast = Response.getRESP_MSG();
                                    Toast.makeText(MyWalletActivity.this, toast, Toast.LENGTH_SHORT).show();
                                }

                            }
                            else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Try Again ";
                                Toast.makeText(MyWalletActivity.this, toast, Toast.LENGTH_SHORT).show();
                                //showSnackbar(toast);
                            }

                            else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                Toast.makeText(MyWalletActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MyWalletActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                finish();
                                startActivity(intent);

                            }
                            else {

                                String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                                Toast.makeText(MyWalletActivity.this, toast, Toast.LENGTH_SHORT).show();
                                //showSnackbar(toast);

                            }
                        }
                        else {
                            String toast = "Something went wrong..";
                            Toast.makeText(MyWalletActivity.this, toast, Toast.LENGTH_SHORT).show();
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                   
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    Toast.makeText(MyWalletActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
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

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK ))
        {
            if(progressDialog.isShowing())
                progressDialog.dismiss();

            if (fetchBusSearch != null || fetchBusSearch == null)
                fetchBusSearch.cancel();
            //onBackPressed();

        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        super.onBackPressed();
       /* if (fetchBusSearch != null){
            fetchBusSearch.cancel();
            finish();
        }*/
        finish();
        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }

}
