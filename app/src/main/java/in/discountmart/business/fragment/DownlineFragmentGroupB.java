package in.discountmart.business.fragment;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.base.network.NetworkClient1;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import in.discountmart.R;
import in.discountmart.business.adapter.DownlineDetailAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.MyTeamService;
import in.discountmart.business.listener.PaginationScrollListener;
import in.discountmart.business.model_business.requestmodel.DownlineDetailRequest;
import in.discountmart.business.model_business.responsemodel.DownlineDetailResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DownlineFragmentGroupB extends Fragment {

    //Empty Constructor
    public DownlineFragmentGroupB(){}

    RecyclerView recyViewDownlineRight;
    LinearLayout layoutRecord;
    LinearLayout layoutNoRecord;
    LinearLayout layoutTotal;
    LinearLayout layoutProgress;
    ProgressDialog pDialog;
    ProgressBar progressBar;
    TextView textViewTotalRecord;

    String side = "0";
    int totalRecord ;
    int from =0;
    int to=0;
    String stringFromDate="";
    String stringToDate="";
    String stringStatus="";
    String strApiKey="";
    private static final int PAGE_START = 1;
    private static final int FROM_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_COUNT = 0;
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;

    Boolean msgShown = false;
    Context context;
    ArrayList<DownlineDetailResponse.DownlineDetails> downlineDetailsArrayList;
    DownlineDetailResponse.DownlineDetails downlineDetails[];
    DownlineDetailAdapter adapter;
    View view1;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.business_fragment_downlinerightlist, container, false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            context = getActivity();
            view1=getActivity().findViewById(android.R.id.content);
            recyViewDownlineRight = (RecyclerView) rootView.findViewById(R.id.right_downline_frag_recycle);
            textViewTotalRecord = (TextView) rootView.findViewById(R.id.right_downline_frag_txtView_record);
            layoutNoRecord = (LinearLayout) rootView.findViewById(R.id.right_downline_frag_layout_norecord);
            layoutRecord = (LinearLayout) rootView.findViewById(R.id.right_downline_frag_layout_record);
            layoutTotal = (LinearLayout) rootView.findViewById(R.id.right_downline_frag_layout_total);
            //layoutProgress = (LinearLayout) rootView.findViewById(R.id.right_downline_frag_Layout_progressbar);
            progressBar = (ProgressBar) rootView.findViewById(R.id.right_downline_frag_progressBar1);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);


            Bundle bundle = getArguments();
            if (bundle != null) {
                stringFromDate = bundle.getString("FromDate");
                stringToDate = bundle.getString("ToDate");
                stringStatus = bundle.getString("Status");
            } else {

            }


            adapter = new DownlineDetailAdapter(context);
            //recyViewDownlineRight.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            recyViewDownlineRight.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyViewDownlineRight.setLayoutManager(llm);

            recyViewDownlineRight.setAdapter(adapter);

            from = 1;
            to = 20;
            if (!ConnectivityUtils.isNetworkEnabled(getActivity())) {
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            } else {
                //new getDownlineRightDetails().execute();
                getDownlineRightDetail();
            }

            recyViewDownlineRight.addOnScrollListener(new PaginationScrollListener(llm) {
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
                            getDownlineRightDetail();
                        }
                    }, 1000);
                }

                @Override
                public int getTotalPageCount() {
                    return TOTAL_COUNT;
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



        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;

    }



    /* Get Right Downline Detail api*/
    private void getDownlineRightDetail(){
        //showProgressDialog();
       /* if(from==1){
            layoutProgress.setVisibility(View.GONE);
        }
        else {

            layoutProgress.setVisibility(View.VISIBLE);
        }*/

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
        DownlineDetailRequest downlineDetailRequest = new DownlineDetailRequest();
        /*Set value in Entity class*/
        downlineDetailRequest.setReqtype(ApiConstants.REQUEST_DOWNLINE);
        downlineDetailRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        downlineDetailRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        downlineDetailRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        downlineDetailRequest.setFrom(String.valueOf(from));
        downlineDetailRequest.setSide("2");
        downlineDetailRequest.setTo(String.valueOf(to));
        downlineDetailRequest.setStatus(stringStatus);
        downlineDetailRequest.setFromdate(stringFromDate);
        downlineDetailRequest.setTodate(stringToDate);

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(downlineDetailRequest);
        Log.e("Req-LeftDownlineDetail:", Get_Paramenter);

        Call<DownlineDetailResponse> downlineDetailResponseCall
                = NetworkClient1.getInstance(context).create(MyTeamService.class).fetchDownlineDetails(downlineDetailRequest,strApiKey);

        downlineDetailResponseCall.enqueue(new Callback<DownlineDetailResponse>() {
            @Override
            public void onResponse(Call<DownlineDetailResponse> call, Response<DownlineDetailResponse> response) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    /*Create  Entity Class Object */
                    DownlineDetailResponse Response = new  DownlineDetailResponse();

                    Response=response.body();
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {


                            if(Response.getDownline()!= null && Response.getDownline().size() > 0){

                                layoutNoRecord.setVisibility(View.GONE);
                                layoutRecord.setVisibility(View.VISIBLE);
                                layoutTotal.setVisibility(View.VISIBLE);

                                downlineDetailsArrayList=new ArrayList<DownlineDetailResponse.DownlineDetails>();
                                downlineDetailsArrayList=Response.getDownline();
                                totalRecord = Integer.parseInt(Response.getRecordcount());
                                //Toast.makeText(context,"Record:" +downlineDetailReeponse.getRecordcount(),Toast.LENGTH_SHORT).show();
                                // fillData(downlineDetails);

                                int totcount= Integer.parseInt(Response.getRecordcount());
                                if(totcount <= 10){
                                    TOTAL_PAGES= 1;
                                    adapter.addAll(downlineDetailsArrayList);
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
                                        adapter.addAll(downlineDetailsArrayList);

                                        if (currentPage <= TOTAL_PAGES){
                                            adapter.addLoadingFooter();

                                        }

                                        else
                                            isLastPage = true;
                                    }
                                    else
                                        loadNextPage(downlineDetailsArrayList);
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
                            //Toast.makeText(context, downlineDetailReeponse.getResponse(), Toast.LENGTH_SHORT).show();
                            String toast= Response.getResponse();
                            Snackbar.make(view1, toast, Snackbar.LENGTH_LONG)
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
                        Snackbar.make(view1, toast, Snackbar.LENGTH_LONG)
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
            public void onFailure(Call<DownlineDetailResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                //layoutProgress.setVisibility(View.GONE);
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

    private void loadNextPage(ArrayList<DownlineDetailResponse.DownlineDetails> list) {
        Log.d(TAG, "loadNextPage: " + currentPage);
        ArrayList<DownlineDetailResponse.DownlineDetails> rechargeReports=new ArrayList<DownlineDetailResponse.DownlineDetails>();
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


    }

    /*Method for fill data in recycler view*/
   /* public void fillData(DownlineDetailResponse.DownlineDetails arrList[]) {


        if (from == 1 && totalRecord > 0) {

            downlineDetailsArrayList = new ArrayList<DownlineDetailResponse.DownlineDetails>(Arrays.asList(arrList));
            downlineDetailAdapter.setData( downlineDetailsArrayList, recyViewDownlineRight);

            }
            else {
            ArrayList<DownlineDetailResponse.DownlineDetails> temList = new ArrayList<DownlineDetailResponse.DownlineDetails>(Arrays.asList(arrList));
            downlineDetailsArrayList.addAll(temList);
            downlineDetailAdapter.setData( downlineDetailsArrayList, recyViewDownlineRight);
            //downlineDetailAdapter.notifyItemInserted(downlineDetailsArrayList.size());

        }
        downlineDetailAdapter.notifyDataSetChanged();
        downlineDetailAdapter.setLoaded();

        //set load more listener for the RecyclerView LevelWiseDirectAdapter
        downlineDetailAdapter .setOnLoadMoreListener(new DownlineDetailAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the LevelWiseDirectAdapter will check view_type and show progress bar at bottom
                //downlineDetailsArrayList.add(null);
                //downlineDetailAdapter.notifyItemInserted(downlineDetailsArrayList.size());

                recyViewDownlineRight.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //   remove progress item
                        //downlineDetailsArrayList.remove(downlineDetailsArrayList.size()-1 );
                        //downlineDetailAdapter.notifyItemRemoved(downlineDetailsArrayList.size());
                        //downlineDetailAdapter.notifyItemInserted(downlineDetailsArrayList.size());

                        from = from + 20;
                        to = to + 20;
                        int size=0;
                        size=size+downlineDetailsArrayList.size();

                        if (size < totalRecord) {

                            if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //new getDownlineRightDetails().execute();
                                getDownlineRightDetail();
                            }

                        }

                        else  {
                            //show msg no more data available
                            Toast.makeText(context, "No More Record Available", Toast.LENGTH_SHORT).show();
                            //msgShown = true;
                            layoutProgress.setVisibility(View.GONE);
                        }
                    }
                },2000);
            }
        });
    }*/


}
