package in.discountmart.business.fragment;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import in.discountmart.business.adapter.EpinDetailAdapter;
import in.discountmart.business.adapter.PackageListAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.EpinServices;
import in.discountmart.business.listener.PaginationScrollListener;
import in.discountmart.business.model_business.requestmodel.EPinDetailReqEntity;
import in.discountmart.business.model_business.responsemodel.EpinDetailResponse;
import in.discountmart.business.model_business.responsemodel.PackageListResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EpindetailFragment extends Fragment {

    Button btnsubmit;
    ProgressDialog pDialog;
    RecyclerView recyclerViewEpinList;
    LinearLayout layoutRecord;
    LinearLayout layoutNoRecord;
    LinearLayout layoutTotal;
    TextView txtTotal;
    Context context;
    View view1;

    Boolean msgShown = false;
    int from;
    int to ;
    int check=0;
    int totalRecord;

    String id,atmos;
    String stringPkgId;
    String stringCheckPin;
    String stringPkgType;
    private static final int PAGE_START = 1;
    private static final int FROM_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_COUNT = 0;
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;

    PackageListResponse.PackageList packageList[];
    ArrayList<PackageListResponse.PackageList> packageListArrayList;

    EpinDetailResponse.EpinDetail epinDetailList[];
    ArrayList<EpinDetailResponse.EpinDetail> detailArrayList;

    PackageListAdapter packageListAdapter;
    EpinDetailAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View rootView = null;
        try {
            view1=getActivity().findViewById(android.R.id.content);
            context = getActivity();
            rootView = inflater.inflate(R.layout.business_epindetail, container, false);
            layoutNoRecord = (LinearLayout) rootView.findViewById(R.id.epin_deatil_fragment_layout_noRecord);
            layoutRecord = (LinearLayout) rootView.findViewById(R.id.epin_deatil_fragment_layout_Record);
            layoutTotal= (LinearLayout) rootView.findViewById(R.id.epin_summery_frag_layout_total);
            txtTotal= (TextView) rootView.findViewById(R.id.epin_summery_frag_txtView_record);

            Bundle bundle = getArguments();
            stringPkgId = bundle.getString("pkgid");
            stringCheckPin = bundle.getString("checkPin");
            //stringPkgType=bundle.getString("PkgType");

            recyclerViewEpinList = (RecyclerView) rootView.findViewById(R.id.epin_summery_frag_recyView_epin_detail);


            recyclerViewEpinList.setHasFixedSize(false);
            LinearLayoutManager llm = new LinearLayoutManager(context);
            recyclerViewEpinList.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewEpinList.setLayoutManager(llm);
            //DesignationLeaderRes.DesignationLeader arrList[];
            adapter = new EpinDetailAdapter(context);
            recyclerViewEpinList.setAdapter(adapter);


            //new HttpAsyncTask().execute(Constants.Baseurl);

            /*Call Epin Detail APi*/
            if (!ConnectivityUtils.isNetworkEnabled(getActivity())) {
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            } else {
                from = 1;
                to = 10;

                //new getEpinDetailsDetails().execute();
                getEpinDetail();
            }


            recyclerViewEpinList.addOnScrollListener(new PaginationScrollListener(llm) {
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
                            getEpinDetail();
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


    /*Get Epin Detail Api Response Method */
    private void getEpinDetail(){
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
        EPinDetailReqEntity ePinDetailReqEntity = new EPinDetailReqEntity();

        /*Set value in Entity class*/
        ePinDetailReqEntity.setReqtype(ApiConstants.REQUEST_EPIN_DETAIL);
        ePinDetailReqEntity.setPasswd(SharedPrefrence_Business.getPassword(context));
        ePinDetailReqEntity.setUserid(SharedPrefrence_Business.getUserID(context));
        ePinDetailReqEntity.setFrom(String.valueOf(from));
        ePinDetailReqEntity.setTo(String.valueOf(to));
        ePinDetailReqEntity.setPkgid(stringPkgId);
        ePinDetailReqEntity.setPtype(stringCheckPin);

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(ePinDetailReqEntity);
        Log.e("EpinDetailRequest:", Get_Paramenter);

        Call<EpinDetailResponse> epinDetailResponseCall =
                NetworkClient1.getInstance(context).create(EpinServices.class).fetchPinDetail(ePinDetailReqEntity);

        epinDetailResponseCall.enqueue(new Callback<EpinDetailResponse>() {
            @Override
            public void onResponse(Call<EpinDetailResponse> call, Response<EpinDetailResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();

                try {

                    EpinDetailResponse Response = new EpinDetailResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {
                            if(Response.getEpindetail()!= null && Response.getEpindetail().size() > 0){

                                layoutNoRecord.setVisibility(View.GONE);
                                layoutRecord.setVisibility(View.VISIBLE);
                                layoutTotal.setVisibility(View.VISIBLE);

                                detailArrayList=new ArrayList<EpinDetailResponse.EpinDetail>();
                                detailArrayList=Response.getEpindetail();
                                totalRecord = Integer.parseInt(Response.getRecordcount());
                                txtTotal.setText("Record"+"\n"+String.valueOf(totalRecord));
                                //Toast.makeText(context,"Record:" +downlineDetailReeponse.getRecordcount(),Toast.LENGTH_SHORT).show();
                                // fillData(downlineDetails);

                                int totcount= Integer.parseInt(Response.getRecordcount());
                                if(totcount <= 10){
                                    TOTAL_PAGES= 1;
                                    adapter.addAll(detailArrayList);
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
                                        adapter.addAll(detailArrayList);

                                        if (currentPage <= TOTAL_PAGES)
                                            adapter.addLoadingFooter();
                                        else
                                            isLastPage = true;
                                    }
                                    else
                                        loadNextPage(detailArrayList);
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
            public void onFailure(Call<EpinDetailResponse> call, Throwable t) {

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

    private void loadNextPage(ArrayList<EpinDetailResponse.EpinDetail> list) {
        Log.d(TAG, "loadNextPage: " + currentPage);
        ArrayList<EpinDetailResponse.EpinDetail> rechargeReports=new ArrayList<EpinDetailResponse.EpinDetail>();
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


   /* public void fillData( EpinDetailResponse.EpinDetail arrList[]) {
        //recyViewDesigLead.setVisibility(View.VISIBLE);

        if (from == 1 ) {

            epinDetailListArrayList = new ArrayList<EpinDetailResponse.EpinDetail>(Arrays.asList(arrList));

            epinDetailAdapter.setData(epinDetailListArrayList,recyclerViewEpinList);


        } else {
            ArrayList<EpinDetailResponse.EpinDetail> temList = new ArrayList<EpinDetailResponse.EpinDetail>(Arrays.asList(arrList));
            epinDetailListArrayList.addAll(temList);
            epinDetailAdapter.setData( epinDetailListArrayList, recyclerViewEpinList);

        }
        epinDetailAdapter.notifyDataSetChanged();
        // progressBarLoding.setVisibility(View.GONE);
        epinDetailAdapter.setLoaded();


        epinDetailAdapter.setOnLoadMoreListener(new EpinDetailAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                epinDetailListArrayList.add(null);
                //leaderListAdapter.notifyDataSetChanged();//(desigLeadArrayList.size() );
                epinDetailAdapter.notifyItemInserted(epinDetailListArrayList.size() );
                // progressBarLoding.setVisibility(View.VISIBLE);
                recyclerViewEpinList.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //   remove progress item
                        epinDetailListArrayList.remove(epinDetailListArrayList.size()-1 );
                        epinDetailAdapter.notifyItemRemoved(epinDetailListArrayList.size());
                        epinDetailAdapter.notifyItemInserted(epinDetailListArrayList.size() );

                        from = from + 10;
                        to = to + 10;

                        int size=0;
                        size=size+epinDetailListArrayList.size();
                        if (size < intTotalRecord) {
                            //get more data
                            //new getEpinDetailsDetails().execute();
                            getEpinDetail();

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

    public void showtopup( String scratchno){

        Bundle args = new Bundle();
        args.putString("pinno", scratchno);



       /* TopupFragment newjoining3 = new TopupFragment();
        FragmentTransaction business_transaction = getActivity().getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the business_transaction to the back stack so the user can navigate back
        business_transaction.replace(R.id.content_frame, newjoining3);
        business_transaction.addToBackStack(null);
        // Commit the business_transaction
        business_transaction.commit();
        newjoining3.setArguments(args);*/


    }
    public void joinnow(String pinno, String scratchno){

        Bundle args = new Bundle();
        args.putString("pinno", pinno);
        args.putString("scratchno", scratchno);


       /* Newjoinig2pin newjoining3 = new Newjoinig2pin();
        FragmentTransaction business_transaction = getActivity().getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the business_transaction to the back stack so the user can navigate back
        business_transaction.replace(R.id.content_frame, newjoining3);
        business_transaction.addToBackStack(null);
        // Commit the business_transaction
        business_transaction.commit();
        newjoining3.setArguments(args);*/


    }






}
