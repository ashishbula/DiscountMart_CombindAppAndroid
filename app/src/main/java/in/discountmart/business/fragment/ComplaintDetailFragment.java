package in.discountmart.business.fragment;

import static android.content.ContentValues.TAG;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.base.network.NetworkClient1;
import com.google.android.material.snackbar.Snackbar;
import in.discountmart.R;
import in.discountmart.business.adapter.ComplaintDetailAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.ComplaintServices;
import in.discountmart.business.listener.PaginationScrollListener;
import in.discountmart.business.model_business.requestmodel.BaseFromToRequest;
import in.discountmart.business.model_business.responsemodel.ComplaintDetailResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintDetailFragment extends Fragment {

    RecyclerView recyViewComplaintList;
    LinearLayout layoutRecord;
    LinearLayout layoutNoRecord;
    LinearLayout layoutTotal;
    TextView textViewTotal;

    String side = "0";
    String strApiKey = "";
    int totalRecord;

    int from = 0;
    int to = 0;
    private static final int PAGE_START = 1;
    private static final int FROM_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_COUNT = 0;
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;
    Boolean msgShown = false;
    ProgressDialog pDialog;

    Context context;
    ArrayList<ComplaintDetailResponse.ComplaintDetails> detailsArrayList;

    ComplaintDetailResponse.ComplaintDetails compaintDetails[];
    ComplaintDetailAdapter detailAdapter;
    View view;

    //Empty constructor
    public ComplaintDetailFragment() {
        //empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = null;
        try {

            rootView = inflater.inflate(R.layout.business_complain_detail_fragment, container, false);
            view = getActivity().findViewById(android.R.id.content);
            context = getActivity();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            recyViewComplaintList = (RecyclerView) rootView.findViewById(R.id.complaint_detail_fragment_recycler);
            textViewTotal = (TextView) rootView.findViewById(R.id.complaint_detail_fragment_txtView_record);
            layoutNoRecord = (LinearLayout) rootView.findViewById(R.id.complaint_detail_fragment_layout_noRecord);
            layoutRecord = (LinearLayout) rootView.findViewById(R.id.complaint_detail_fragment_layout_Record);
            layoutTotal = (LinearLayout) rootView.findViewById(R.id.complaint_detail_fragment_layout_total);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);
           /* Bundle bundle = getArguments();
            if (bundle != null) {
                stringFromDate = bundle.getString("FromDate");
                stringToDate = bundle.getString("ToDate");
                stringStatus = bundle.getString("Status");
            } else {

            }*/

            // click event for date picker, spinner and button


            recyViewComplaintList.setHasFixedSize(false);
            LinearLayoutManager llm = new LinearLayoutManager(context);
            recyViewComplaintList.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyViewComplaintList.setLayoutManager(llm);
            detailAdapter = new ComplaintDetailAdapter(context);
            recyViewComplaintList.setAdapter(detailAdapter);

            from = 1;
            to = 10;
            if (!ConnectivityUtils.isNetworkEnabled(context)) {
                Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            } else {

                //new getDownlineLeftDetails().execute();
                getComplaintDetail();
            }

            recyViewComplaintList.addOnScrollListener(new PaginationScrollListener(llm) {
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
                            getComplaintDetail();
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }


    /* Get Complaint Detail Api*/
    private void getComplaintDetail() {

        if (currentPage == 1) {
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait");
            pDialog.setCancelable(false);
            pDialog.show();
        } else {
            //if(pDialog.isShowing())
            pDialog.dismiss();
        }
        BaseFromToRequest Request = new BaseFromToRequest();

        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_COMPLAINT_DETAIL);
        Request.setPasswd(SharedPrefrence_Business.getPassword(context));
        Request.setUserid(SharedPrefrence_Business.getUserID(context));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        Request.setFrom(String.valueOf(from));
        Request.setTo(String.valueOf(to));
        //downlineDetailRequest.setFromdate(stringFromDate);
        //downlineDetailRequest.setTodate(stringToDate);
        //downlineDetailRequest.setStatus(stringStatus);

        Call<ComplaintDetailResponse> downlineDetailResponseCall
                = NetworkClient1.getInstance(context).create(ComplaintServices.class).fetchComplaintDetail(Request,strApiKey);

        downlineDetailResponseCall.enqueue(new Callback<ComplaintDetailResponse>() {
            @Override
            public void onResponse(Call<ComplaintDetailResponse> call, Response<ComplaintDetailResponse> response) {

                if (pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    ComplaintDetailResponse Response = new ComplaintDetailResponse();
                    Response = response.body();

                    if (Response != null) {
                        if (Response.getResponse().equals("OK")) {
                            if (Response.getComplaintdetail() != null && Response.getComplaintdetail().size() > 0) {

                                layoutNoRecord.setVisibility(View.GONE);
                                layoutRecord.setVisibility(View.VISIBLE);
                                layoutTotal.setVisibility(View.VISIBLE);

                                detailsArrayList = new ArrayList<ComplaintDetailResponse.ComplaintDetails>();
                                detailsArrayList = Response.getComplaintdetail();
                                totalRecord = Integer.parseInt(Response.getRecordcount());
                                textViewTotal.setText("Record" + "\n" + String.valueOf(totalRecord));
                                //Toast.makeText(context,"Record:" +downlineDetailReeponse.getRecordcount(),Toast.LENGTH_SHORT).show();
                                // fillData(downlineDetails);

                                int totcount = Integer.parseInt(Response.getRecordcount());
                                if (totcount <= 10) {
                                    TOTAL_PAGES = 1;
                                    detailAdapter.addAll(detailsArrayList);
                                    //adapter.removeLoadingFooter();
                                    isLastPage = true;
                                    isLoading = false;

                                } else {

                                    int quotient = totcount / 10;
                                    int remainder = totcount % 10;
                                    if (remainder > 0) {
                                        TOTAL_PAGES = quotient + 1;
                                    } else {
                                        TOTAL_PAGES = quotient;
                                    }

                                    if (currentPage == 1) {
                                        detailAdapter.addAll(detailsArrayList);

                                        if (currentPage <= TOTAL_PAGES)
                                            detailAdapter.addLoadingFooter();
                                        else
                                            isLastPage = true;
                                    } else
                                        loadNextPage(detailsArrayList);
                                }
                            } else {
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


                        } else {
                            String toast = Response.getResponse();
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();

                        }
                    } else {
                        String toast = Response.getResponse() + "Something went wrong...";
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
            public void onFailure(Call<ComplaintDetailResponse> call, Throwable t) {

                if (pDialog.isShowing())
                    pDialog.dismiss();
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

    private void loadNextPage(ArrayList<ComplaintDetailResponse.ComplaintDetails> list) {
        Log.d(TAG, "loadNextPage: " + currentPage);
        ArrayList<ComplaintDetailResponse.ComplaintDetails> rechargeReports = new ArrayList<ComplaintDetailResponse.ComplaintDetails>();
        rechargeReports = list;
        detailAdapter.removeLoadingFooter();
        isLoading = false;

        detailAdapter.addAll(rechargeReports);

        if (currentPage != TOTAL_PAGES)
            detailAdapter.addLoadingFooter();
        else {
            isLastPage = true;
            Toast.makeText(context, "No more record available", Toast.LENGTH_SHORT).show();
        }


    }
}
    /*public void fillData(ArrayList<ComplaintDetailResponse.ComplaintDetails> arrList) {

        if (from == 1) {

            detailsArrayList = new ArrayList<ComplaintDetailResponse.ComplaintDetails>(arrList);
            detailAdapter.setData( detailsArrayList, recyViewComplaintList);
            //recyViewDownlineLeft.setAdapter(downlineDetailAdapter);

        } else {
            ArrayList<ComplaintDetailResponse.ComplaintDetails> temList = new ArrayList<ComplaintDetailResponse.ComplaintDetails>(arrList);
            detailsArrayList.addAll(temList);
            detailAdapter.setData( detailsArrayList, recyViewComplaintList);
            //downlineDetailAdapter.notifyItemInserted(downlineDetailsArrayList.size());

        }
        detailAdapter.notifyDataSetChanged();
        detailAdapter.setLoaded();

        //set load more listener for the RecyclerView adapter
        detailAdapter .setOnLoadMoreListener(new ComplaintDetailAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                detailsArrayList.add(null);
                detailAdapter.notifyItemInserted(detailsArrayList.size());

                recyViewComplaintList.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //   remove progress item
                        detailsArrayList.remove(detailsArrayList.size()-1 );
                        detailAdapter.notifyItemRemoved(detailsArrayList.size());
                        detailAdapter.notifyItemInserted(detailsArrayList.size());

                        from = from + 20;
                        to = to + 20;
                        int size=0;
                        size=size+detailsArrayList.size();

                        if (size < totalRecord) {

                            if(!ConnectivityUtils.isNetworkEnabled(context)){
                                Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //new getDownlineLeftDetails().execute();
                                getComplaintDetail();
                            }
                            //new getDownlineLeftDetails().execute();
                            //}
                            //});
                        }

                        else  {
                            //show msg no more data available
                            Toast.makeText(context, "No More Record Available", Toast.LENGTH_SHORT).show();
                            msgShown = true;
                        }
                    }
                },2000);
            }
        });
    }*/





