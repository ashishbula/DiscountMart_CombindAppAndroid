package in.discountmart.business.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.business.adapter.LevelListAdapter;
import in.discountmart.business.adapter.MyDirectAdapter;
import in.discountmart.business.listener.PaginationScrollListener;
import in.discountmart.business.model_business.responsemodel.LevelListResponse;
import in.discountmart.business.model_business.responsemodel.LevelWiseReportResponse;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;
import java.util.Objects;

public class MyDirectDetailFragment extends Fragment {


    Context context;
    Spinner spinnerLevel;
    Spinner spinnerStatus;
    Spinner spinnerSearchBy;
    RecyclerView recyLevelReport;
    TextView textViewRecord;
    TextView txtTotDirectLeft;
    TextView txtTotDirectRight;
    TextView txtTotDirectTotal;
    TextView txtActDirectRight;
    TextView txtActDirectLeft;
    TextView txtActDirectTotal;
    TextView txtDirectBVLeft;
    TextView txtDirectBVRight;
    TextView txtDirectBVTotal;
    ProgressBar progressBar;
    LinearLayout layoutProgress;
    LinearLayout layoutNoRecord;
    LinearLayout layoutRecord;
    LinearLayout layoutTotal;
    View view1;

    ProgressDialog pDialog;

    String strLevelID="";
    String strLevelName="";
    String strStatus="";
    String strSearch="";

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
    //ArrayList<MyDirectResponse.MyDirect> directArrayList;

    /*Adapter*/
    LevelListAdapter levelListAdapter;
    MyDirectAdapter adapter;


    // Empty Constructor
    public MyDirectDetailFragment(){
        // Empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.business_level_report_detail_fragment, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        try {
            view1=getActivity().findViewById(android.R.id.content);
            context=getActivity();

            //spinnerLevel=(Spinner)rootView.findViewById(R.id.level_detail_frag_spinlevel);
            //spinnerStatus=(Spinner)rootView.findViewById(R.id.level_detail_frag_spiner_status);
            recyLevelReport=(RecyclerView)rootView.findViewById(R.id.level_detail_frag_recycler);
            textViewRecord=(TextView)rootView.findViewById(R.id.level_detail_frag_txtView_total_record);
            txtTotDirectLeft=(TextView)rootView.findViewById(R.id.level_detail_frag_txt_totdirect_left);
            txtTotDirectRight=(TextView)rootView.findViewById(R.id.level_detail_frag_txt_totdirect_right);
            txtTotDirectTotal=(TextView)rootView.findViewById(R.id.level_detail_frag_txt_totdirect_total);
            txtActDirectLeft=(TextView)rootView.findViewById(R.id.level_detail_frag_txt_actdirect_left);
            txtActDirectRight=(TextView)rootView.findViewById(R.id.level_detail_frag_txt_actdirect_right);
            txtActDirectTotal=(TextView)rootView.findViewById(R.id.level_detail_frag_txt_actdirect_total);
            txtDirectBVLeft=(TextView)rootView.findViewById(R.id.level_detail_frag_txt_directbv_left);
            txtDirectBVRight=(TextView)rootView.findViewById(R.id.level_detail_frag_txt_directbv_right);
            txtDirectBVTotal=(TextView)rootView.findViewById(R.id.level_detail_frag_txt_directbv_total);
            //progressBar=(ProgressBar)rootView.findViewById(R.id.level_detail_frag_progressBar1);
           // layoutProgress=(LinearLayout)rootView.findViewById(R.id.level_detail_frag_Layout_progressbar);
             layoutNoRecord=(LinearLayout)rootView.findViewById(R.id.level_detail_frag_layout_norecord);
            layoutRecord=(LinearLayout)rootView.findViewById(R.id.level_detail_frag_Layout_recycler);
            layoutTotal=(LinearLayout)rootView.findViewById(R.id.level_detail_frag_layout_total);

            Bundle bundle=getArguments();
            if(bundle != null){
                strLevelID=bundle.getString("Level");
                strSearch=bundle.getString("Search");
                strStatus=bundle.getString("Status");
            }

            from=1;
            to=10;
            if (!ConnectivityUtils.isNetworkEnabled(getActivity())) {
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            } else {
                //new getDownlineRightDetails().execute();
                ///getLevelWiseReportDetail();
            }

            /*Recycler View */
            adapter=new MyDirectAdapter(context);
            recyLevelReport.setHasFixedSize(false);
            LinearLayoutManager linearLayoutManager= new LinearLayoutManager(context);
            recyLevelReport.setLayoutManager(linearLayoutManager);
            recyLevelReport.setItemAnimator(new DefaultItemAnimator());
            recyLevelReport.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
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
                            //getLevelWiseReportDetail();
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

        return rootView;

    }


    /*Get Level Wise Report Response Api*/
    /*private void getLevelWiseReportDetail(){
        //showProgressDialog();
        if(currentPage == 1)
        {
            pDialog=new ProgressDialog(context);
            pDialog.setMessage("Please wait");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        else {
            //if(pDialog.isShowing())
            pDialog.dismiss();
        }

        MyDirectRequest Request=new MyDirectRequest();
        *//*Set value in Entity class*//*
        Request.setReqtype(ApiConstants.REQUEST_Leve_WISE_REPORT);
        Request.setPasswd(SharedPrefrence_Business.getPassword(context));
        Request.setUserid(SharedPrefrence_Business.getUserID(context));
        Request.setLevelid(strLevelID);
        Request.setStatus(strStatus);
        Request.setLegno(strSearch);
        Request.setFrom(String.valueOf(from));
        Request.setTo(String.valueOf(to));

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(Request);
        Log.e("ReqLevelWiseReport:", Get_Paramenter);

        Call<MyDirectResponse> reportResponseCall
                = NetworkClient1.getInstance(context).create(MyTeamService.class).fetchMyDirectDetail(Request);

        reportResponseCall.enqueue(new Callback<MyDirectResponse>() {
            @Override
            public void onResponse(Call<MyDirectResponse> call, Response<MyDirectResponse> response) {
                //hideProgressDialog();
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    MyDirectResponse Response=new MyDirectResponse();
                    Response=response.body();
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {

                            *//*Set Value in Total Direct Value*//*
                            txtTotDirectLeft.setText(Response.getTotaldirectleft());
                            txtTotDirectRight.setText(Response.getTotaldirectright());
                            int totDLeft= Integer.parseInt(Response.getTotaldirectleft());
                            int totDRight= Integer.parseInt(Response.getTotaldirectright());
                            int totDTotal=totDLeft+totDRight;

                            txtTotDirectTotal.setText(String.valueOf(totDTotal));

                            *//*Set Value in Active Direct Value*//*
                            txtActDirectLeft.setText(Response.getActivedirectleft());
                            txtActDirectRight.setText(Response.getActivedirectright());

                            int totActLeft= Integer.parseInt(Response.getActivedirectleft());
                            int totActRight= Integer.parseInt(Response.getActivedirectright());
                            int totActTotal=totActLeft+totActRight;

                            txtActDirectTotal.setText(String.valueOf(totActTotal));

                            *//*Set Value in Direct BV Value*//*
                            txtDirectBVLeft.setText(Response.getDirectbvleft());
                            txtDirectBVRight.setText(Response.getDirectbvright());

                            float dirBVLeft= Float.parseFloat(Response.getDirectbvleft());
                            float dirBVRight= Float.parseFloat(Response.getDirectbvright());
                            float dirBVTotal=dirBVLeft+dirBVRight;

                            txtDirectBVTotal.setText(String.valueOf(dirBVTotal));

                            // levelWiseReport = Response.getDirects();

                            if(Response.getDirects()!= null && Response.getDirects().size() > 0){

                                layoutNoRecord.setVisibility(View.GONE);
                                layoutRecord.setVisibility(View.VISIBLE);
                                layoutTotal.setVisibility(View.VISIBLE);

                                directArrayList=new ArrayList<MyDirectResponse.MyDirect>();
                                directArrayList=Response.getDirects();
                                totalRecord = Integer.parseInt(Response.getRecordcount());
                                textViewRecord.setText("Record"+"\n"+Response.getRecordcount());
                                textViewRecord.setTextSize(12);

                                int totcount= Integer.parseInt(Response.getRecordcount());
                                if(totcount <= 10){
                                    TOTAL_PAGES= 1;
                                    adapter.addAll(directArrayList);
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
                                        adapter.addAll(directArrayList);

                                        if (currentPage <= TOTAL_PAGES)
                                            adapter.addLoadingFooter();
                                        else
                                            isLastPage = true;
                                    }
                                    else
                                        loadNextPage(directArrayList);
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
                            String msg=Response.getMsg()+ " "+"Something went wrong..";
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
                        String msg="Something went wrong.. may be server error";
                        Snackbar.make(view1,msg, Snackbar.LENGTH_LONG)
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
            public void onFailure(Call<MyDirectResponse> call, Throwable t) {

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
    }*/

   /* private void loadNextPage(ArrayList<MyDirectResponse.MyDirect> list) {
        Log.d(TAG, "loadNextPage: " + currentPage);
        ArrayList<MyDirectResponse.MyDirect> rechargeReports=new ArrayList<MyDirectResponse.MyDirect>();
        rechargeReports=list;
        adapter.removeLoadingFooter();
        isLoading = false;

        adapter.addAll(rechargeReports);

        if (currentPage != TOTAL_PAGES)
            adapter.addLoadingFooter();
        else{
            isLastPage = true;
            Toast.makeText(context,"No more record available",Toast.LENGTH_SHORT).show();
        }


    }*/
   /* public void fillData( LevelWiseReportResponse.LevelWiseReport arrList[]) {
        //recyViewDesigLead.setVisibility(View.VISIBLE);

        if (from == 1  ) {

            levelWiseReportArrayList = new ArrayList<LevelWiseReportResponse.LevelWiseReport>(Arrays.asList(arrList));
            levelWiseDirectAdapter.setData(levelWiseReportArrayList,recyLevelReport);

        }
        else  {

            ArrayList<LevelWiseReportResponse.LevelWiseReport> temList;
            temList= new ArrayList<LevelWiseReportResponse.LevelWiseReport>(Arrays.asList(arrList));
            //final int positionStart = levelWiseReportArrayList.size() +1;
            levelWiseReportArrayList.addAll(temList);
            levelWiseDirectAdapter.setData( levelWiseReportArrayList, recyLevelReport);
            //levelWiseDirectAdapter. notifyItemRangeRemoved(positionStart, levelWiseReportArrayList.size());

        }
        levelWiseDirectAdapter.notifyDataSetChanged();
        levelWiseDirectAdapter.setLoaded();



        levelWiseDirectAdapter.setOnLoadMoreListener(new LevelWiseDirectAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the LevelWiseDirectAdapter will check view_type and show progress bar at bottom
                from = from + 10;
                to = to + 10;
                //layoutProgress.setVisibility(View.VISIBLE);
                // levelWiseReportArrayList.add(null);
                //leaderListAdapter.notifyDataSetChanged();//(desigLeadArrayList.size() );
                //levelWiseDirectAdapter.notifyItemInserted(levelWiseReportArrayList.size()-1);

                recyLevelReport.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        //   remove progress item
                        //levelWiseReportArrayList.remove(levelWiseReportArrayList.size()-1);

                        //levelWiseDirectAdapter.notifyItemRemoved(levelWiseReportArrayList.size());
                        //levelWiseDirectAdapter.notifyItemInserted(levelWiseReportArrayList.size());

                        int size=0;
                        size=size+levelWiseReportArrayList.size();
                        if (size < totalRecord) {
                            //get more data

                            getLevelWiseReportDetail();
                        }

                        else  {
                            //show msg no more data available
                            Toast.makeText(context, "No More Record Available", Toast.LENGTH_SHORT).show();
                            //msgShown = true;
                            layoutProgress.setVisibility(View.GONE);
                        }

                    }
                },1000);
            }
        });

    }*/
    @Override
    public void onResume(){
        super.onResume();

    }


}
