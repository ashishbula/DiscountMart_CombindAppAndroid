package in.discountmart.business.fragment;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.base.network.NetworkClient1;
import com.google.android.material.snackbar.Snackbar;
import in.discountmart.R;
import in.discountmart.business.adapter.WeecklyIncentiveAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.IncomeServices;
import in.discountmart.business.listener.PaginationScrollListener;
import in.discountmart.business.model_business.requestmodel.WeeklyIncentiveRequest;
import in.discountmart.business.model_business.responsemodel.WeeklyIncentiveResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeeklyIncentiveFragment extends Fragment {

    RecyclerView recyclerViewWeeklyIncen;
    LinearLayout layoutRecord;
    LinearLayout layoutNoRecord;
    LinearLayout layoutTotal;
    TextView textViewTotal;
    View view;

    ArrayList<WeeklyIncentiveResponse.WeeklyIncentive> weeklyIncentiveArrayList;
    WeecklyIncentiveAdapter detailAdapter;

    ProgressDialog pDialog;
    boolean msgShown;
    int from;
    int to;
    int totalRecord;
    private static final int PAGE_START = 1;
    private static final int FROM_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_COUNT = 0;
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;

    Context context;
    String id;
    int j = 10;
    int k = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.business_weeklyincentive_fragment, container, false);
        try {
            view=getActivity().findViewById(android.R.id.content);
            context=getActivity();

            /*Define All views references*/
            recyclerViewWeeklyIncen=(RecyclerView)rootView.findViewById(R.id.weekly_incentive_fragment_recycler);
            layoutNoRecord=(LinearLayout)rootView.findViewById(R.id.weekly_incentive_fragment_layout_noRecord);
            layoutRecord=(LinearLayout)rootView.findViewById(R.id.weekly_incentive_fragment_layout_Record);
            layoutTotal=(LinearLayout)rootView.findViewById(R.id.weekly_incentive_fragment_layout_total);
            textViewTotal=(TextView)rootView.findViewById(R.id.weekly_incentive_fragment_txtView_record);


            /*Initialize Recycler view with layout orientation
            * and Recycler Adapter*/
            recyclerViewWeeklyIncen.setHasFixedSize(false);
            LinearLayoutManager llm = new LinearLayoutManager(context);
            //recyclerViewWeeklyIncen.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewWeeklyIncen.setLayoutManager(llm);

            detailAdapter =new WeecklyIncentiveAdapter(context);
            recyclerViewWeeklyIncen.setAdapter(detailAdapter);

            weeklyIncentiveArrayList=new ArrayList<WeeklyIncentiveResponse.WeeklyIncentive>();
            weeklyIncentiveArrayList.clear();

            from=1;
            to=10;

            /*Call Api*/
            if(!ConnectivityUtils.isNetworkEnabled(context)){
                Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
            else {
                getWeeklyIncentiveDetail();
            }

            /*Recycler on scroll load next data*/
            recyclerViewWeeklyIncen.addOnScrollListener(new PaginationScrollListener(llm) {
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
                            getWeeklyIncentiveDetail();
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


    /*Get Weekly Income Api */
    private void getWeeklyIncentiveDetail(){
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
        WeeklyIncentiveRequest IncomeRequest = new WeeklyIncentiveRequest();
        /*Set value in Entity class*/
        /*Set value in Entity class*/
        IncomeRequest.setReqtype(ApiConstants.REQUEST_WEEKLY_INCENTIVE);
        IncomeRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        IncomeRequest.setUserid(SharedPrefrence_Business.getUserID(context));

        IncomeRequest.setFrom(String.valueOf(from));
        IncomeRequest.setTo(String.valueOf(to));

        Call<WeeklyIncentiveResponse> calldailyIncentiveResponse=
                NetworkClient1.getInstance(context).create(IncomeServices.class).fetchWeeklyIncomeDetail(IncomeRequest);

        calldailyIncentiveResponse.enqueue(new Callback<WeeklyIncentiveResponse>() {
            @Override
            public void onResponse(Call<WeeklyIncentiveResponse> call, Response<WeeklyIncentiveResponse> response) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    WeeklyIncentiveResponse Response =new WeeklyIncentiveResponse();
                    Response = response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {
                            if(Response.getWeeklyincentive()!= null && Response.getWeeklyincentive().size() > 0){

                                layoutNoRecord.setVisibility(View.GONE);
                                layoutRecord.setVisibility(View.VISIBLE);
                                layoutTotal.setVisibility(View.VISIBLE);

                                weeklyIncentiveArrayList=new ArrayList<WeeklyIncentiveResponse.WeeklyIncentive>();
                                weeklyIncentiveArrayList=Response.getWeeklyincentive();
                                totalRecord = Integer.parseInt(Response.getRecordcount());
                                textViewTotal.setText("Record"+"\n"+String.valueOf(totalRecord));
                                //Toast.makeText(context,"Record:" +downlineDetailReeponse.getRecordcount(),Toast.LENGTH_SHORT).show();
                                // fillData(downlineDetails);

                                int totcount= Integer.parseInt(Response.getRecordcount());
                                if(totcount <= 10){
                                    TOTAL_PAGES= 1;
                                    detailAdapter.addAll(weeklyIncentiveArrayList);
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
                                        detailAdapter.addAll(weeklyIncentiveArrayList);

                                        if (currentPage <= TOTAL_PAGES)
                                            detailAdapter.addLoadingFooter();
                                        else
                                            isLastPage = true;
                                    }
                                    else
                                        loadNextPage(weeklyIncentiveArrayList);
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
                            String toast= Response.getResponse();
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
                        String toast= Response.getResponse() + "Something went wrong...";
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
            public void onFailure(Call<WeeklyIncentiveResponse> call, Throwable t) {
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
    }

    private void loadNextPage(ArrayList<WeeklyIncentiveResponse.WeeklyIncentive> list) {
        Log.d(TAG, "loadNextPage: " + currentPage);
        ArrayList<WeeklyIncentiveResponse.WeeklyIncentive> rechargeReports=new ArrayList<WeeklyIncentiveResponse.WeeklyIncentive>();
        rechargeReports=list;
        detailAdapter.removeLoadingFooter();
        isLoading = false;

        detailAdapter.addAll(rechargeReports);

        if (currentPage != TOTAL_PAGES)
            detailAdapter.addLoadingFooter();
        else{
            isLastPage = true;
            Toast.makeText(context,"No more record available",Toast.LENGTH_SHORT).show();
        }


    }
   /* public void fillData( WeeklyIncentiveResponse.WeeklyIncentive arrList[]) {
        //recyViewDesigLead.setVisibility(View.VISIBLE);

        if (from == 1  ) {


            weeklyIncentiveArrayList = new ArrayList<WeeklyIncentiveResponse.WeeklyIncentive>(Arrays.asList(arrList));

            weeklyIncentiveAdapter.setData(weeklyIncentiveArrayList,recyclerViewWeeklyIncen);

        } else {
            ArrayList<WeeklyIncentiveResponse.WeeklyIncentive> temList;
            temList= new ArrayList<WeeklyIncentiveResponse.WeeklyIncentive>(Arrays.asList(arrList));
            weeklyIncentiveArrayList.addAll(temList);
            weeklyIncentiveAdapter.setData( weeklyIncentiveArrayList, recyclerViewWeeklyIncen);

        }
        weeklyIncentiveAdapter.notifyDataSetChanged();
        // progressBarLoding.setVisibility(View.GONE);
        weeklyIncentiveAdapter.setLoaded();


        weeklyIncentiveAdapter .setOnLoadMoreListener(new WeeklyIncentiveAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                weeklyIncentiveArrayList.add(null);
                //leaderListAdapter.notifyDataSetChanged();//(desigLeadArrayList.size() );
                weeklyIncentiveAdapter.notifyItemInserted(weeklyIncentiveArrayList.size() );
                // progressBarLoding.setVisibility(View.VISIBLE);
                recyclerViewWeeklyIncen.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //   remove progress item
                        weeklyIncentiveArrayList.remove(weeklyIncentiveArrayList.size()-1 );
                        weeklyIncentiveAdapter.notifyItemRemoved(weeklyIncentiveArrayList.size());
                        weeklyIncentiveAdapter.notifyItemInserted(weeklyIncentiveArrayList.size() );
                        //leaderListAdapter.notifyDataSetChanged();

                        //dailyIncentiveArrayList.size();
                        //start=start+1;
                        //total=total+10;
                        from = from + 10;
                        to = to + 10;
                        int size=0;
                        size=size+weeklyIncentiveArrayList.size();
                        if (size < totalRecord) {

                            //new getWeeklyIncentiveDetails().execute();
                            getWeeklyIncentiveDetail();
                            //get more data
                            *//*getActivity().runOnUiThread(new Runnable() {
                                public void run() {

                                    //stringAction="SEARCHHOTELNEXT";

                                    if(!getActivity().isFinishing())
                                        // get more Hotel
                                        new getDesdignationLeaderDetails().execute();
                                }
                            });*//*
                        }

                        else if (msgShown == false) {
                            //show msg no more data available
                            Toast.makeText(context, "No More Record Available", Toast.LENGTH_SHORT).show();
                            msgShown = true;
                        }
                    }
                },2000);
            }
        });

    }*/





}
