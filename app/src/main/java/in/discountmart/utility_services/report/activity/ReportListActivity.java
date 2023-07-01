package in.discountmart.utility_services.report.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.activity.PromocodeListActivity;
import in.discountmart.utility_services.constants.AppConstants;
import in.discountmart.utility_services.fund.activity.AddFundActivity;
import in.discountmart.utility_services.fund.activity.FundRequestActivity;
import in.discountmart.utility_services.fund.activity.FundRequestStatusActivity;
import in.discountmart.utility_services.report.report_adapter.NameListAdapter;
import in.discountmart.utility_services.utilities.DividerItemDecoration;

public class ReportListActivity extends AppCompatActivity implements NameListAdapter.ListAdapterListener {

    RecyclerView recyclerView;
    LinearLayout layoutMain;
    View view;
    //ImageView imgFund;
   // ImageView imgAccount;
    ImageView imgReport;
    ImageView imgShare;
    NameListAdapter adapter;
    ArrayList<String> reportList;
    String strReport="";
    ProgressDialog pDialog;
    //DashboardResponse Response;
    String strLinkLeft = "";
    String strLinkRight = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_report_list);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        view=findViewById(android.R.id.content);
        try {
            recyclerView=(RecyclerView)findViewById(R.id.reportlist_activity_recycler);
            //imgAccount=(ImageView)findViewById(R.id.reportlist_activity_img_account);
           // imgFund=(ImageView)findViewById(R.id.reportlist_activity_img_fund);
            imgReport=(ImageView)findViewById(R.id.reportlist_activity_img_report);
            imgShare=(ImageView)findViewById(R.id.reportlist_activity_img_shareapp);
            layoutMain=(LinearLayout)findViewById(R.id.reportlist_activity_layout_main);

            Intent bundle=getIntent();

            if(bundle != null){
                strReport=bundle.getStringExtra("Report");
            }


            //REcycler View set divider item line
          LinearLayoutManager mLayoutManager = new LinearLayoutManager(ReportListActivity.this);
           mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

          recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,0));


            reportList=new ArrayList<>();
            reportList=new ArrayList<>();
            if(strReport.contentEquals(AppConstants.TAG_FUND)){
                reportList=getFundReportList();
                //imgFund.setVisibility(View.VISIBLE);
                imgReport.setVisibility(View.GONE);
                //imgAccount.setVisibility(View.GONE);
                imgShare.setVisibility(View.GONE);
            }
            else if(strReport.contentEquals(AppConstants.TAG_REPORT)){
                reportList=getReportList();
                //imgFund.setVisibility(View.GONE);
                imgReport.setVisibility(View.VISIBLE);
                //imgAccount.setVisibility(View.GONE);
                imgShare.setVisibility(View.GONE);
            }
            else if(strReport.contentEquals(AppConstants.TAG_ACCOUNT)){
                reportList=getAccountReportList();
               // imgFund.setVisibility(View.GONE);
                imgReport.setVisibility(View.GONE);
               // imgAccount.setVisibility(View.VISIBLE);
                imgShare.setVisibility(View.GONE);
            }
            else if(strReport.contentEquals(AppConstants.TAG_SHARE)){
                reportList=getShareList();
                //imgFund.setVisibility(View.GONE);
                imgReport.setVisibility(View.GONE);
                //imgAccount.setVisibility(View.GONE);
                imgShare.setVisibility(View.VISIBLE);
            }


            if(reportList.size() > 0){
                //flightSearchList = new ArrayList<>();
                adapter = new NameListAdapter(this, reportList, ReportListActivity.this,strReport);
            }
            recyclerView.setAdapter(adapter);

            /*on click blank space exit acivity*/
            layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }
        //getDashboardDetail();

    }

    /*Initiat Fund Type list*/
    public ArrayList<String> getFundReportList(){

        ArrayList<String> list=new ArrayList<>();
        list.add("Add Fund");
        list.add("Fund Request");
        list.add("Fund Request Status");
        list.add("Get Promocode");


        return list;
    }

    /*Initiat report Type list*/
    public ArrayList<String> getReportList(){

        ArrayList<String> list=new ArrayList<>();
        list.add("Recharge Report");
        list.add("Bill Payment Report");
        list.add("Ledger Report");
        list.add("Bus Book Detail");
        //list.add("Hotel Book Detail");
        list.add("Flight Book Detail");
        return list;
    }

    /*Initiat Account Type list*/
    public ArrayList<String> getAccountReportList(){

        ArrayList<String> list=new ArrayList<>();
        list.add("Ledger Report");
        list.add("Review Detail");
        list.add("Recharge Detail");

        return list;
    }

    /*Initiat Share Type list*/
    public ArrayList<String> getShareList(){

        ArrayList<String> list=new ArrayList<>();
        list.add("Left Referral Link");
        list.add("Right Referral Link");
        list.add("Share App");
        list.add("Registration");

        return list;
    }
    @Override
    public void onSelected(String item) {//Get Promocode
        try {
            if(!item.contentEquals("")){
                if(item.contentEquals("Add Fund")){
                    Intent intent=new Intent(ReportListActivity.this, AddFundActivity.class);
                    intent.putExtra("GW_Bus","");
                    intent.putExtra("Home","true");
                    intent.putExtra("DeductAmount","");
                    intent.putExtra("ServiceType","");
                    startActivity(intent);
                    finish();
                }
                else if(item.contentEquals("Fund Request")){

                    Intent fundIntent=new Intent(ReportListActivity.this, FundRequestActivity.class);
                    startActivity(fundIntent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                    finish();

                }
                else if(item.contentEquals("Fund Request Status")){
                    Intent fundIntent=new Intent(ReportListActivity.this, FundRequestStatusActivity.class);
                    startActivity(fundIntent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                    finish();
                }

                else if(item.contentEquals("Get Promocode")){
                    Intent fundIntent=new Intent(ReportListActivity.this, PromocodeListActivity.class);
                    startActivity(fundIntent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                    finish();
                }
                else if(item.contentEquals("Recharge Report")){
                    Intent rechargeIntent=new Intent(ReportListActivity.this, RechargeReportActivity.class);
                    startActivity(rechargeIntent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                    finish();
                }
                else if(item.contentEquals("Bill Payment Report")){
                    Intent billIntent=new Intent(ReportListActivity.this, BillPamentReportActivity.class);
                    startActivity(billIntent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                    finish();
                }
                else if(item.contentEquals("Ledger Report")){
                    Intent ledgerIntent=new Intent(ReportListActivity.this, LedgerReportActivity.class);
                    startActivity(ledgerIntent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                    finish();
                }
                else if(item.contentEquals("Bus Book Detail")){
                    Intent ledgerIntent=new Intent(ReportListActivity.this, BusBookReportActivity.class);
                    startActivity(ledgerIntent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                    finish();
                }
                else if(item.contentEquals("Flight Book Detail")){
                    Intent ledgerIntent=new Intent(ReportListActivity.this, FlightBookReportActivity.class);
                    startActivity(ledgerIntent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                    finish();
                }
                else if(item.contentEquals("Left Referral Link")){
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String string = strLinkLeft;

                    shareIntent.putExtra(Intent.EXTRA_TEXT, string);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Discount Mania");
                    startActivity(Intent.createChooser(shareIntent, "SHARE_APP"));
                }
                else if(item.contentEquals("Right Referral Link")){
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String string = strLinkRight;

                    shareIntent.putExtra(Intent.EXTRA_TEXT, string);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Discount Mania");
                    startActivity(Intent.createChooser(shareIntent, "SHARE_APP"));
                }
               /* else if(item.contentEquals("Share App")){
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String string = "\n Get the best deals on travel services and online Recharges and Shopping. All at one place by simply downloading Goldwings App. \n" +
                            "Visit us at\n"+"Referral ID : " + new LoginPreferences_brand(ReportListActivity.this).getLoginDetail_Brand().getUser_id()+"\n\n";
                    string = string + "https://play.google.com/store/apps/details?id=" + ReportListActivity.this.getPackageName() + "\n\n";
                    //   + "Sponser Code : " + userInfo_Shoppe.getPhone();

                    shareIntent.putExtra(Intent.EXTRA_TEXT, string);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Goldwings");
                    startActivity(Intent.createChooser(shareIntent, "SHARE_APP"));

                    finish();
                }*/
                /*else if(item.contentEquals("Registration")){
                    Intent i = new Intent(ReportListActivity.this, RegistrationActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    finish();

                }*/

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    /*private void getDashboardDetail(){

        pDialog=new ProgressDialog(ReportListActivity.this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest request=new BaseRequest();
        *//*Set value in Entity class*//*
        request.setReqtype(ApiConstants.REQUEST_DASHBOARD);
        request.setPasswd(SharedPrefrence_Business.getPassword(ReportListActivity.this));
        request.setUserid(SharedPrefrence_Business.getUserID(ReportListActivity.this));

        Call<DashboardResponse> callAddressDetail=
                NetworkClient1.getInstance(ReportListActivity.this).create(ProfileServices.class).fetchDashboardDetail(request);

        callAddressDetail.enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, retrofit2.Response<DashboardResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    Response =new DashboardResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {

                            *//*if(Response.getReferalurlleft().equals("") && Response.getReferalurlleft() != null
                                    && Response.getReferalurlright().equals("") && Response.getReferalurlright() != null){
                            }
                            else {
                                strLinkLeft=Response.getReferalurlleft();
                                strLinkRight=Response.getReferalurlright();
                            }*//*

                        }
                        else {
                            String toast= Response.getResponse()+ ":" + Response.getMsg();
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
                    else {
                        String toast="Something wrong..server error";
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
            public void onFailure(Call<DashboardResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
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


    }*/

}


