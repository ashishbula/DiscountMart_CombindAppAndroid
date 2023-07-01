package in.discountmart.business.fragment;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import in.base.network.NetworkClient;
import in.base.network.NetworkClient1;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import in.discountmart.R;
import in.discountmart.business.activity.CommonReportActivity;
import in.discountmart.business.adapter.PackageListAdapter;
import in.discountmart.business.adapter.PinReceiveDetailAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.EpinServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.PinReceiveDetailRequest;
import in.discountmart.business.model_business.responsemodel.PackageListResponse;
import in.discountmart.business.model_business.responsemodel.PinReceiveDetailResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinReceiveDetailFragment extends Fragment {

    Spinner spinnerPackage;
    RecyclerView recyclerViewPinReceive;
    ProgressDialog pDialog;
    Button btnSubmit;
    View view;

    LinearLayout layoutRecord;
    LinearLayout layoutNoRecord;
    LinearLayout layoutTotal;
    TextView txtTotalRecord;
    private static final int PAGE_START = 1;
    private static final int FROM_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_COUNT = 0;
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;

    Context context;

    String stringPakgeId;
    String stringPakgeName;
    String strApiKey="";

    int from;
    int to;
    int totalRecord;
    boolean msgShown;


    PackageListResponse.PackageList packageList[];
    ArrayList<PackageListResponse.PackageList> packageListArrayList;
    PackageListAdapter packageListAdapter;

    ArrayList<PinReceiveDetailResponse.PinReceiveDetail> pinReceiveDetailArrayList;
    PinReceiveDetailResponse.PinReceiveDetail pinReceiveDetail[];
    PinReceiveDetailAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.business_pin_recievedetail_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            context=getActivity();
            view=getActivity().findViewById(android.R.id.content);
            //recyclerViewPinReceive=(RecyclerView)rootView.findViewById(R.id.pin_receive_detail_frag_recycler);
            txtTotalRecord=(TextView)rootView.findViewById(R.id.pin_receive_detail_frag_txt_total_record);
            layoutRecord=(LinearLayout)rootView.findViewById(R.id.pin_receive_detail_frag_layout_record);
            layoutNoRecord=(LinearLayout)rootView.findViewById(R.id.pin_receive_detail_frag_layout_noRecord);
            layoutTotal=(LinearLayout)rootView.findViewById(R.id.pin_receive_detail_frag_layout_total);


            spinnerPackage = (Spinner) rootView.findViewById(R.id.pin_receive_detail_frag_frag_spinPackage);
            btnSubmit = (Button) rootView.findViewById(R.id.pin_receive_detail_frag_btn_submit);

        /*mTxtvActionBarTitle=(TextView)getActivity().findViewById(R.id.title_text);
        mTxtvActionBarTitle.setText("Right Downline");*/
           /* recyclerViewPinReceive.setHasFixedSize(false);
            LinearLayoutManager llm = new LinearLayoutManager(context);
            recyclerViewPinReceive.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewPinReceive.setLayoutManager(llm);
            //DesignationLeaderRes.DesignationLeader arrList[];
            adapter =new PinReceiveDetailAdapter(context);
            recyclerViewPinReceive.setAdapter(adapter);*/

            pinReceiveDetailArrayList=new ArrayList<PinReceiveDetailResponse.PinReceiveDetail>();
            pinReceiveDetailArrayList.clear();

            /*Call api*/
            if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
            else {
                //new getPackageListDetails().execute();
                getPackageList();
            }


            /*Spinner package item selected linstner*/
            spinnerPackage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent,
                                           View arg1, int position, long arg3) {
                    //String item = parent.getItemAtPosition(position).toString();
                    PackageListResponse.PackageList packageList1= (PackageListResponse.PackageList)parent.getItemAtPosition(position);

                    if(packageList1 != null){
                        stringPakgeId = packageList1.getPkgid();
                        stringPakgeName=packageList1.getPkgname();

                        pinReceiveDetailArrayList=new ArrayList<PinReceiveDetailResponse.PinReceiveDetail>();
                        pinReceiveDetailArrayList.clear();

                        currentPage=1;
                        pinReceiveDetailArrayList=new ArrayList<PinReceiveDetailResponse.PinReceiveDetail>();
                        pinReceiveDetailArrayList.clear();
                        //if(adapter.getItemCount() > 0)
                         //   adapter.clear();
                        // mocking network delay for API call
                       /* new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                isLastPage = false;
                                isLoading=false;
                                from=1;
                                to=10;
                                if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                                    Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    // new getPinReceiveDetails().execute();
                                    getPinReceiveDetail();
                                }


                            }
                        }, 500);*/




                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

            /* Button on submit click go next page
            * for show pin receive detail*/
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, CommonReportActivity.class);
                    intent.putExtra("Type","PinReceiveDetail");
                    intent.putExtra("PckID",stringPakgeId);
                    intent.putExtra("Title","Pin Receive Detail");
                    context.startActivity(intent);
                    getActivity(). overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

          /*  recyclerViewPinReceive.addOnScrollListener(new PaginationScrollListener(llm) {
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
                            getPinReceiveDetail();
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
            });*/
        }catch (Exception e){
            e.printStackTrace();
        }


        return rootView;
    }


    /*Get Package List Api Response*/
    private void getPackageList(){
        pDialog=new ProgressDialog(context);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        String stringRequestType="";
        stringRequestType= ApiConstants.REQUEST_EPIN_PACKAGE_LIST;
        baseRequest.setReqtype(stringRequestType);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Parameter = gson.toJson(baseRequest);
        Log.e("RequestPackageList:", Parameter);

        Call<PackageListResponse> listResponseCall=
                NetworkClient.getInstance(context).create(EpinServices.class).fetchPackageList(baseRequest,strApiKey);

        listResponseCall.enqueue(new Callback<PackageListResponse>() {
            @Override
            public void onResponse(Call<PackageListResponse> call, Response<PackageListResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                PackageListResponse listResponse=new PackageListResponse();
                try {

                    listResponse = response.body();

                    if (listResponse.getResponse().equals("OK")) {
                        packageList = listResponse.getPackages();
                        if(packageList != null && packageList.length > 0) {
                            packageListArrayList = new ArrayList<PackageListResponse.PackageList>(Arrays.asList(packageList));
                            packageListAdapter = new PackageListAdapter(getActivity(), packageListArrayList);
                            spinnerPackage.setAdapter(packageListAdapter);
                        }
                        else {
                            Toast.makeText(context, "Package List Is Empty", Toast.LENGTH_SHORT).show();

                        }

                    }
                    else {
                        Toast.makeText(context, listResponse.getResponse(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<PackageListResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*Pin Receive Detail Api*/
    private void getPinReceiveDetail(){

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
        PinReceiveDetailRequest Request=new PinReceiveDetailRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_PIN_RECEIVE_DETAIL);
        Request.setPasswd(SharedPrefrence_Business.getPassword(context));
        Request.setUserid(SharedPrefrence_Business.getUserID(context));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        Request.setFrom(String.valueOf(from));
        Request.setTo(String.valueOf(to));
        Request.setPkgid(stringPakgeId);

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(Request);
        Log.e("RequestPinReceive:", Get_Paramenter);

        Call<PinReceiveDetailResponse> callpinReceiveDetail=
                NetworkClient1.getInstance(context).create(EpinServices.class).fetchPinReceiveDetail(Request);

        callpinReceiveDetail.enqueue(new Callback<PinReceiveDetailResponse>() {
            @Override
            public void onResponse(Call<PinReceiveDetailResponse> call, Response<PinReceiveDetailResponse> response) {
              if(pDialog.isShowing())
                  pDialog.dismiss();
                try {

                   PinReceiveDetailResponse Response = new PinReceiveDetailResponse();
                   Response= (PinReceiveDetailResponse) response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {
                            if(Response.getReceivedetail()!= null && Response.getReceivedetail().size() > 0){

                                layoutNoRecord.setVisibility(View.GONE);
                                layoutRecord.setVisibility(View.VISIBLE);
                                layoutTotal.setVisibility(View.VISIBLE);

                                pinReceiveDetailArrayList=new ArrayList<PinReceiveDetailResponse.PinReceiveDetail>();
                                pinReceiveDetailArrayList=Response.getReceivedetail();
                                totalRecord = Integer.parseInt(Response.getRecordcount());
                                txtTotalRecord.setText("Record"+"\n"+String.valueOf(totalRecord));
                                //Toast.makeText(context,"Record:" +downlineDetailReeponse.getRecordcount(),Toast.LENGTH_SHORT).show();
                                // fillData(downlineDetails);

                                int totcount= Integer.parseInt(Response.getRecordcount());
                                if(totcount <= 10){
                                    TOTAL_PAGES= 1;
                                    adapter.addAll(pinReceiveDetailArrayList);
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
                                        adapter.addAll(pinReceiveDetailArrayList);

                                        if (currentPage <= TOTAL_PAGES)
                                            adapter.addLoadingFooter();
                                        else
                                            isLastPage = true;
                                    }
                                    else
                                        loadNextPage(pinReceiveDetailArrayList);
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
            public void onFailure(Call<PinReceiveDetailResponse> call, Throwable t) {

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

    private void loadNextPage(ArrayList<PinReceiveDetailResponse.PinReceiveDetail> list) {
        Log.d(TAG, "loadNextPage: " + currentPage);
        ArrayList<PinReceiveDetailResponse.PinReceiveDetail> rechargeReports=new ArrayList<PinReceiveDetailResponse.PinReceiveDetail>();
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
    /*public void fillData( PinReceiveDetailResponse.PinReceiveDetail arrList[]) {
        //recyViewDesigLead.setVisibility(View.VISIBLE);

        if (from == 1 ) {

            pinReceiveDetailArrayList = new ArrayList<PinReceiveDetailResponse.PinReceiveDetail>(Arrays.asList(arrList));
            pinReceiveDetailAdapter.setData(pinReceiveDetailArrayList,recyclerViewPinReceive);


        } else {
            ArrayList<PinReceiveDetailResponse.PinReceiveDetail> temList = new ArrayList<PinReceiveDetailResponse.PinReceiveDetail>(Arrays.asList(arrList));
            pinReceiveDetailArrayList.addAll(temList);
            pinReceiveDetailAdapter.setData( pinReceiveDetailArrayList, recyclerViewPinReceive);

        }
        pinReceiveDetailAdapter.notifyDataSetChanged();
        // progressBarLoding.setVisibility(View.GONE);
        pinReceiveDetailAdapter.setLoaded();


        pinReceiveDetailAdapter .setOnLoadMoreListener(new PinReceiveDetailAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                pinReceiveDetailArrayList.add(null);
                //leaderListAdapter.notifyDataSetChanged();//(desigLeadArrayList.size() );
                pinReceiveDetailAdapter.notifyItemInserted(pinReceiveDetailArrayList.size() );
                // progressBarLoding.setVisibility(View.VISIBLE);
                recyclerViewPinReceive.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //   remove progress item
                        pinReceiveDetailArrayList.remove(pinReceiveDetailArrayList.size()-1 );
                        pinReceiveDetailAdapter.notifyItemRemoved(pinReceiveDetailArrayList.size());
                        pinReceiveDetailAdapter.notifyItemInserted(pinReceiveDetailArrayList.size() );

                        from = from + 10;
                        to = to + 10;

                        int size=0;
                        size=size+pinReceiveDetailArrayList.size();

                        if (size < totalRecord) {
                            //get more data
                           // new getPinReceiveDetails().execute();
                           getPinReceiveDetail();
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
}
