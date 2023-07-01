package in.discountmart.business.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.base.network.NetworkClient1;
import in.base.ui.BaseActivity;
import in.discountmart.R;
import in.discountmart.business.adapter.DownlinePurchaseAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.MyTeamService;
import in.discountmart.business.listener.PaginationScrollListener;
import in.discountmart.business.model_business.requestmodel.DownlinePurchaseRequest;
import in.discountmart.business.model_business.responsemodel.DownlinePurchaseResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownlinePurchaseActivity extends BaseActivity {

    RecyclerView recyViewRepurchaseDownline;
    Context context;

    TextView textViewTotalBV;
    TextView textViewHeaderName;
    TextView textViewTotalRecord;

    LinearLayout linearLayoutNext;
    LinearLayout layoutNoRecord;
    LinearLayout layoutRecord;

    DownlinePurchaseAdapter downlineAdapter;
    ArrayList<DownlinePurchaseResponse.DownlinePurchaseItem> downlineRepurchaseArrayList;
    DownlinePurchaseResponse.DownlinePurchaseItem downlineRepurchase[];

    ProgressDialog pDialog;

    int from;
    int to;
    int totalRecord;

    boolean msgShown;

    String stringFromDate="";
    String stringToDate="";
    String stringSide="";
    String stringFro;
    String strLevel="";
    String strtype="";
    String strApiKey="";


    String stringType;
    String stringsendFromDate;
    String stringsendToDate;

    Date formDate;
    Date toDate;
    SimpleDateFormat sendFormet;
    SimpleDateFormat getFormet;
    private static final int PAGE_START = 0;
    private boolean isLoading=false ;
    private boolean isLastPage=false;
    private int TOTAL_PAGES=1;
    private int currentPage=TOTAL_PAGES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_downline);

        context=this;
        recyViewRepurchaseDownline=(RecyclerView)findViewById(R.id.purchase_downline_activity_recycler);

        textViewTotalBV=(TextView)findViewById(R.id.purchase_downline_activity_txtView_totle_bv);
        textViewTotalRecord=(TextView)findViewById(R.id.purchase_downline_activity_txtView_totle_record);
        textViewHeaderName=(TextView)findViewById(R.id.purchase_downline_activity_txtView_name);
        layoutNoRecord=(LinearLayout)findViewById(R.id.purchase_downline_activity_layout_noRecord);
        layoutRecord=(LinearLayout)findViewById(R.id.purchase_downline_activity_layout_Record);

        /* Get api key value from Shared Preference */
        strApiKey=SharedPrefrence_Business.getApiKey(this);


        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Downline Purchase");
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }



        Bundle getBundle=getIntent().getExtras();
        if(getBundle!= null){
            stringFromDate=getBundle.getString("FromDate");
            stringToDate=getBundle.getString("ToDate");
            stringSide=getBundle.getString("Group");
            stringType=getBundle.getString("Type");
            strLevel=getBundle.getString("levelId");

           /* assert stringType != null;
            if(stringType.equals("T")){
                textViewHeaderName.setText("Dwonline Purchase");

            }
            else if(stringType.equals("R")){
                textViewHeaderName.setText("Downline Repurchase");
            }*/
        }
        else {
            Toast.makeText(this,"Empty Bundle", Toast.LENGTH_SHORT).show();;
        }

        sendFormet=new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        getFormet=new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        //Date formDate=new Date();
        //Date toDate=new Date();
        /*try{
            formDate=getFormet.parse(stringFromDate);
            toDate=getFormet.parse(stringToDate);

            stringsendFromDate=sendFormet.format(formDate);
            stringsendToDate=sendFormet.format(toDate);

        }catch (Exception e){
            e.printStackTrace();
        }*/

        downlineAdapter = new DownlinePurchaseAdapter(this);
        //recyViewDownlineRight.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyViewRepurchaseDownline.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyViewRepurchaseDownline.setLayoutManager(llm);

        recyViewRepurchaseDownline.setAdapter(downlineAdapter);

        from=1;
        to=10;
        //new getDownlineRepurchaseDetails().execute();
        if(!ConnectivityUtils.isNetworkEnabled(this)){
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
        else {
            getDownlinePurchaseDetail();
        }

        recyViewRepurchaseDownline.addOnScrollListener(new PaginationScrollListener(llm) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        from = from + 10;
                        to = to + 10;
                        getDownlinePurchaseDetail();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }


    /*Back Press Arrow o ToolBar*/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    // Method for fetching data and get Details
    private void getDownlinePurchaseDetail(){
        //showProgressDialog();
        if(currentPage == 1)
        {
            pDialog=new ProgressDialog(this);
            pDialog.setMessage("Please wait");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        else {
            //if(pDialog.isShowing())
            pDialog.dismiss();
        }

        DownlinePurchaseRequest downlinePurchaseRequest = new DownlinePurchaseRequest();

        /*Set value in Entity class*/
        downlinePurchaseRequest.setReqtype(ApiConstants.REQUEST_DOWNLINE_PURCHASE);
        downlinePurchaseRequest.setPasswd(SharedPrefrence_Business.getPassword(this));
        downlinePurchaseRequest.setUserid(SharedPrefrence_Business.getUserID(this));
        downlinePurchaseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(this));

        downlinePurchaseRequest.setFrom(String.valueOf(from));
        downlinePurchaseRequest.setTo(String.valueOf(to));
        downlinePurchaseRequest.setLegno(stringSide);
        downlinePurchaseRequest.setLevel(strLevel);
        downlinePurchaseRequest.setType(stringType);
        downlinePurchaseRequest.setFromdate(String.valueOf(stringFromDate));
        downlinePurchaseRequest.setTodate(String.valueOf(stringToDate));
        //downlinePurchaseRequest.setType(stringType);

        Call<DownlinePurchaseResponse> downlinePurchaseResponseCall=
                NetworkClient1.getInstance(this).create(MyTeamService.class).fetchDownlinePurchaseDetails(downlinePurchaseRequest,strApiKey);

        downlinePurchaseResponseCall.enqueue(new Callback<DownlinePurchaseResponse>() {
            @Override
            public void onResponse(Call<DownlinePurchaseResponse> call, Response<DownlinePurchaseResponse> response) {

               if(pDialog.isShowing())
                   pDialog.dismiss();
                try {
                    DownlinePurchaseResponse Response=new DownlinePurchaseResponse();
                    Response=response.body();

                    assert Response != null;
                    if (Response.getResponse().equals("OK")) {
                        if(Response.getDownlinepurchase()!= null && Response.getDownlinepurchase().size() > 0){

                            layoutNoRecord.setVisibility(View.GONE);
                            layoutRecord.setVisibility(View.VISIBLE);


                            downlineRepurchaseArrayList=new ArrayList<DownlinePurchaseResponse.DownlinePurchaseItem>();
                            downlineRepurchaseArrayList=Response.getDownlinepurchase();
                            totalRecord = Integer.parseInt(Response.getRecordcount());
                            textViewTotalRecord.setText(Response.getRecordcount());
                            //Toast.makeText(context,"Record:" +downlineDetailReeponse.getRecordcount(),Toast.LENGTH_SHORT).show();
                            // fillData(downlineDetails);

                            int totcount= Integer.parseInt(Response.getRecordcount());
                            if(totcount <= 10){
                                TOTAL_PAGES= 1;
                                downlineAdapter.addAll(downlineRepurchaseArrayList);
                                //LevelWiseDirectAdapter.removeLoadingFooter();
                                isLastPage = true;
                                isLoading=false;

                            }
                            else {

                                int quotient = totcount/10;
                                int remainder = totcount%10;
                                if(remainder > 0){
                                    TOTAL_PAGES= quotient+1;
                                }
                                else {
                                    TOTAL_PAGES= quotient;
                                }

                                if(currentPage==1){
                                    downlineAdapter.addAll(downlineRepurchaseArrayList);

                                    if (currentPage <= TOTAL_PAGES){
                                        downlineAdapter.addLoadingFooter();

                                    }

                                    else
                                        isLastPage = true;
                                }
                                else
                                    loadNextPage(downlineRepurchaseArrayList);
                            }
                        }
                        else {
                            isLastPage = true;
                            isLoading = false;
                            // Toast.makeText(context,"Record is empty"  +downlineDetailReeponse.getRecordcount(), Toast.LENGTH_SHORT).show();
                            //downlineDetailsArrayList = new ArrayList<DownlineDetailResponse.DownlineDetails>(Arrays.asList(downlineDetails));
                            //downlineDetailsArrayList.clear();
                            //downlineDetailAdapter.setData( downlineDetailsArrayList, recyViewDownlineLeft);
                            //layoutTotal.setVisibility(View.GONE);
                            // textViewTotal.setText("Record \n"+downlineDetailReeponse.getRecordcount());
                            //ftbTotal.setImageBitmap(textAsBitmap("Record \n"+downlineDetailReeponse.getRecordcount(),12, Color.WHITE));
                            layoutNoRecord.setVisibility(View.VISIBLE);
                            layoutRecord.setVisibility(View.GONE);
                            //layoutProgress.setVisibility(View.GONE);
                        }

                    }
                    else {
                        Toast.makeText(DownlinePurchaseActivity.this, Response.getResponse(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DownlinePurchaseResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(DownlinePurchaseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadNextPage(ArrayList<DownlinePurchaseResponse.DownlinePurchaseItem> list) {
        Log.d(TAG, "loadNextPage: " + currentPage);
        ArrayList<DownlinePurchaseResponse.DownlinePurchaseItem> rechargeReports=new ArrayList<DownlinePurchaseResponse.DownlinePurchaseItem>();
        rechargeReports=list;
        downlineAdapter.removeLoadingFooter();
        isLoading = false;

        downlineAdapter.addAll(rechargeReports);

        if (currentPage != TOTAL_PAGES)
            downlineAdapter.addLoadingFooter();
        else{
            isLastPage = true;
            Toast.makeText(DownlinePurchaseActivity.this,"No more record available",Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.business_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent intent=new Intent(DownlinePurchaseActivity.this, BusinessDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



}
