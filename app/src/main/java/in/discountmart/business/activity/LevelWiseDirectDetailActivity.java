package in.discountmart.business.activity;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.base.network.NetworkClient1;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import in.discountmart.R;
import in.discountmart.business.adapter.LevelListAdapter;
import in.discountmart.business.adapter.LevelWiseDirectAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.MyTeamService;
import in.discountmart.business.listener.PaginationScrollListener;
import in.discountmart.business.model_business.requestmodel.LevelWiseReportRequest;
import in.discountmart.business.model_business.responsemodel.LevelListResponse;
import in.discountmart.business.model_business.responsemodel.LevelWiseReportResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LevelWiseDirectDetailActivity extends AppCompatActivity {

    RecyclerView recyLevelReport;
    LinearLayout layoutNoRecord;
    LinearLayout layoutRecord;
    LinearLayout layoutTotal;
    TextView textViewRecord;
    TextView txtTitle;
    View view1;

    LevelWiseDirectAdapter groupAdapter;
    ProgressDialog pDialog;

    String strLevelID="";
    String strLevelName="";
    String strStatus="";
    String strSearch="";
    String strFlag="";
    String strApiKey="";

    boolean msgShown;

    int from=0;
    int to=0;
    int totalRecord;
    private static final int PAGE_START = 1;
    private static final int FROM_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_COUNT = 0;
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;

    /*Entity Class*/
    LevelListResponse.LevelList levelList[];
    LevelWiseReportResponse.LevelWiseReport levelWiseReport[];

    /*Array List*/
    ArrayList<LevelListResponse.LevelList> levelListArrayList;
    ArrayList<LevelWiseReportResponse.LevelWiseReport> levelWiseReportArrayList;

    /*Adapter*/
    LevelListAdapter levelListAdapter;
    LevelWiseDirectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_activity_level_wise_direct_detail);
        try {
            view1=findViewById(android.R.id.content);

            //spinnerLevel=(Spinner)rootView.findViewById(R.id.level_detail_frag_spinlevel);
            //spinnerStatus=(Spinner)rootView.findViewById(R.id.level_detail_frag_spiner_status);
            recyLevelReport=(RecyclerView)findViewById(R.id.level_detail_act_recycler);
            textViewRecord=(TextView)findViewById(R.id.level_detail_act_txtView_total_record);
            txtTitle=(TextView)findViewById(R.id.level_detail_act_txt_title);

            //progressBar=(ProgressBar)rootView.findViewById(R.id.level_detail_frag_progressBar1);
            // layoutProgress=(LinearLayout)rootView.findViewById(R.id.level_detail_frag_Layout_progressbar);
            layoutNoRecord=(LinearLayout)findViewById(R.id.level_detail_act_layout_norecord);
            layoutRecord=(LinearLayout)findViewById(R.id.level_detail_act_Layout_recycler);
            layoutTotal=(LinearLayout)findViewById(R.id.level_detail_act_layout_total);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(this);

            /*Toolbar back arrow and title enable */
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Level Wise Direct Detail");

            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                strLevelID=bundle.getString("Level");
                strFlag=bundle.getString("Flag");
                //trStatus=bundle.getString("Status");
            }
            if(strFlag.equals("act"))
                txtTitle.setText("Active Member Detail");
            else
                txtTitle.setText("De-Active Member Detail");

            from=1;
            to=10;
            if (!ConnectivityUtils.isNetworkEnabled(LevelWiseDirectDetailActivity.this)) {
                Toast.makeText(LevelWiseDirectDetailActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            } else {
                //new getDownlineRightDetails().execute();
                getLevelWiseReportDetail();
            }

            /*Recycler View */
            adapter=new LevelWiseDirectAdapter(this,strFlag);
            recyLevelReport.setHasFixedSize(false);
            LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
            recyLevelReport.setLayoutManager(linearLayoutManager);
            recyLevelReport.setItemAnimator(new DefaultItemAnimator());
            recyLevelReport.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
            recyLevelReport.setAdapter(adapter);

            recyLevelReport.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
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
                            getLevelWiseReportDetail();
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

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Get Level Wise Report Response Api*/
    private void getLevelWiseReportDetail(){
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

        LevelWiseReportRequest Request=new LevelWiseReportRequest();
        /*Set value in Entity class*/
        if(strFlag.equals("act")){
            Request.setReqtype(ApiConstants.REQUEST_LEVEL_DETAIL_ACTIVE);
            Request.setFlag("act");
        }

        else{
            Request.setReqtype(ApiConstants.REQUEST_LEVEL_DETAIL_DEACTIVE);
            Request.setFlag("deact");
        }


        Request.setPasswd(SharedPrefrence_Business.getPassword(this));
        Request.setUserid(SharedPrefrence_Business.getUserID(this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        Request.setLevel(strLevelID);
        //Request.setStatus(strStatus);
        //Request.setLegno(strSearch);
        Request.setFrom(String.valueOf(from));
        Request.setTo(String.valueOf(to));

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(Request);
        Log.e("ReqLevelWiseReport:", Get_Paramenter);

        Call<LevelWiseReportResponse> reportResponseCall
                = NetworkClient1.getInstance(this).create(MyTeamService.class).fetchLevelWiseReport(Request,strApiKey);

        reportResponseCall.enqueue(new Callback<LevelWiseReportResponse>() {
            @Override
            public void onResponse(Call<LevelWiseReportResponse> call, Response<LevelWiseReportResponse> response) {
                //hideProgressDialog();
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    LevelWiseReportResponse Response=new LevelWiseReportResponse();
                    Response=response.body();
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {

                            if(Response.getLevelReportCountDetail()!= null && Response.getLevelReportCountDetail().size() > 0){

                                layoutNoRecord.setVisibility(View.GONE);
                                layoutRecord.setVisibility(View.VISIBLE);
                                layoutTotal.setVisibility(View.VISIBLE);

                                levelWiseReportArrayList=new ArrayList<LevelWiseReportResponse.LevelWiseReport>();
                                levelWiseReportArrayList=Response.getLevelReportCountDetail();
                                totalRecord = Integer.parseInt(Response.getRecordcount());
                                textViewRecord.setText("Record"+"\n"+Response.getRecordcount());
                                textViewRecord.setTextSize(12);

                                int totcount= Integer.parseInt(Response.getRecordcount());
                                if(totcount <= 10){
                                    TOTAL_PAGES= 1;
                                    adapter.addAll(levelWiseReportArrayList);
                                    //adapter.removeLoadingFooter();
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
                                        adapter.addAll(levelWiseReportArrayList);

                                        if (currentPage <= TOTAL_PAGES)
                                            adapter.addLoadingFooter();
                                        else
                                            isLastPage = true;
                                    }
                                    else
                                        loadNextPage(levelWiseReportArrayList);
                                }
                            }
                            else {
                                isLastPage = true;
                                isLoading = false;
                                // Toast.makeText(context,"Record is empty"  +downlineDetailReeponse.getRecordcount(), Toast.LENGTH_SHORT).show();
                                //downlineDetailsArrayList = new ArrayList<DownlineDetailResponse.DownlineDetails>(Arrays.asList(downlineDetails));
                                //downlineDetailsArrayList.clear();
                                //downlineDetailAdapter.setData( downlineDetailsArrayList, recyViewDownlineLeft);
                                layoutTotal.setVisibility(View.GONE);
                                // textViewTotal.setText("Record \n"+downlineDetailReeponse.getRecordcount());
                                //ftbTotal.setImageBitmap(textAsBitmap("Record \n"+downlineDetailReeponse.getRecordcount(),12, Color.WHITE));
                                layoutNoRecord.setVisibility(View.VISIBLE);
                                layoutRecord.setVisibility(View.GONE);
                                //layoutProgress.setVisibility(View.GONE);
                            }

                        }
                        else {
                            String msg="Something went wrong.." +" "+Response.getMsg();
                            Snackbar.make(view1, msg, Snackbar.LENGTH_LONG)
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
                        String msg="Something wrong..may be server error";
                        Snackbar.make(view1, msg, Snackbar.LENGTH_LONG)
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
            public void onFailure(Call<LevelWiseReportResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                Snackbar.make(view1, t.getMessage(), Snackbar.LENGTH_LONG)
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

    private void loadNextPage(ArrayList<LevelWiseReportResponse.LevelWiseReport> list) {
        Log.d(TAG, "loadNextPage: " + currentPage);
        ArrayList<LevelWiseReportResponse.LevelWiseReport> rechargeReports=new ArrayList<LevelWiseReportResponse.LevelWiseReport>();
        rechargeReports=list;
        adapter.removeLoadingFooter();
        isLoading = false;

        adapter.addAll(rechargeReports);

        if (currentPage != TOTAL_PAGES)
            adapter.addLoadingFooter();
        else{
            isLastPage = true;
            Toast.makeText(this,"No more record available",Toast.LENGTH_SHORT).show();
        }


    }


    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

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



    }
}