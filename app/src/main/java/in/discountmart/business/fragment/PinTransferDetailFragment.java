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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.base.network.NetworkClient;
import in.base.network.NetworkClient1;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import in.discountmart.R;
import in.discountmart.business.activity.CommonReportActivity;
import in.discountmart.business.adapter.PackageListAdapter;
import in.discountmart.business.adapter.PinTransferDetailAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.EpinServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.PinTransferDetailRequest;
import in.discountmart.business.model_business.responsemodel.PackageListResponse;
import in.discountmart.business.model_business.responsemodel.PinTransferDetailReaponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinTransferDetailFragment extends Fragment {
    Context context;
    Spinner spinnerpackge;
    RecyclerView recyPinTransDetail;
    TextView textViewRecord;
    Button btnSubmit;

    LinearLayout layoutRecord;
    LinearLayout layoutNoRecord;
    LinearLayout layoutTotal;
    View view;

    ProgressDialog pDialog;

    String strLevelID="";
    String strLevelName="";
    String strApiKey="";

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

    /*Entity Class*/
    PackageListResponse.PackageList packageLists[];
    PinTransferDetailReaponse.PinTransferDetail pinTransferDetails[];

    /*Array List*/
    ArrayList<PackageListResponse.PackageList> packageListArrayList;
    ArrayList<PinTransferDetailReaponse.PinTransferDetail> pinTransDetailArrayList;

    /*Adapter*/
    PackageListAdapter listAdapter;
    PinTransferDetailAdapter detailAdapter;

    // Empty Constructor
    public PinTransferDetailFragment(){
        // Empty
    }


    int len;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.business_pintransferdetail, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            context=getActivity();
            view=getActivity().findViewById(android.R.id.content);
            spinnerpackge=(Spinner)rootView.findViewById(R.id.pin_transfer_detail_frag_spinPackage);
            recyPinTransDetail=(RecyclerView)rootView.findViewById(R.id.pin_transfer_detail_frag_recycler);
            textViewRecord=(TextView)rootView.findViewById(R.id.pin_transfer_detail_frag_txtView_total_record);
            layoutRecord=(LinearLayout)rootView.findViewById(R.id.pin_transfer_detail_frag_layout_record);
            layoutNoRecord=(LinearLayout)rootView.findViewById(R.id.pin_transfer_detail_frag_layout_noRecord);
            layoutTotal=(LinearLayout)rootView.findViewById(R.id.pin_transfer_detail_frag_layout_total);
            btnSubmit=(Button)rootView.findViewById(R.id.pin_transfer_detail_frag_btn_submit);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);

            /*Recycler View */
            recyPinTransDetail.setHasFixedSize(false);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            recyPinTransDetail.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyPinTransDetail.setLayoutManager(llm);
            recyPinTransDetail.setItemAnimator(new DefaultItemAnimator());
            detailAdapter=new PinTransferDetailAdapter(context);
            recyPinTransDetail.setAdapter(detailAdapter);

            /*Call Package List API*/
            if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
            else {
                //new getPackageListDetails().execute();
                getPackageList();
            }

            /*Spinner Package */
            spinnerpackge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    PackageListResponse.PackageList levelList1 =(PackageListResponse.PackageList)parent.getItemAtPosition(position);

                    if(levelList1!=null){
                        strLevelID=levelList1.getPkgid();
                        strLevelName=levelList1.getPkgname();

                        pinTransDetailArrayList = new ArrayList<PinTransferDetailReaponse.PinTransferDetail>();
                        pinTransDetailArrayList.clear();
                        // levelwisereportAdapter.setData( levelWiseReportArrayList, recyclerViewLevelReport);

                        currentPage=1;
                        pinTransDetailArrayList = new ArrayList<PinTransferDetailReaponse.PinTransferDetail>();
                        pinTransDetailArrayList.clear();
                        if(detailAdapter.getItemCount() > 0)
                            detailAdapter.clear();
                        // mocking network delay for API call
                        /*new Handler().postDelayed(new Runnable() {
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
                                    //getPinTransferDetail();
                                }


                            }
                        }, 500);*/
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

           /* recyPinTransDetail.addOnScrollListener(new PaginationScrollListener(llm) {
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
                            //getPinTransferDetail();
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

            /* Button Submit on click go next page
            * and show Pin transfer Detail*/
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context, CommonReportActivity.class);
                    intent.putExtra("Type","PinTransferDetail");
                    intent.putExtra("PckID",strLevelID);
                    intent.putExtra("Title","Pin Transfer Detail");
                    context.startActivity(intent);
                }
            });
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
        String Parameter=gson.toJson(baseRequest);
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
                        packageLists = listResponse.getPackages();
                        if(packageLists != null && packageLists.length > 0) {
                            packageListArrayList = new ArrayList<PackageListResponse.PackageList>(Arrays.asList(packageLists));
                            listAdapter = new PackageListAdapter(getActivity(), packageListArrayList);
                            spinnerpackge.setAdapter(listAdapter);
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

    /*Get PinTransfer Detail Api and Response*/
    private void getPinTransferDetail(){

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

        PinTransferDetailRequest Request = new PinTransferDetailRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_PIN_TRANSFER_DETAIL);
        Request.setPasswd(SharedPrefrence_Business.getPassword(context));
        Request.setUserid(SharedPrefrence_Business.getUserID(context));
        Request.setPkgid(strLevelID);
        Request.setFrom(String.valueOf(from));
        Request.setTo(String.valueOf(to));

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(Request);
        Log.e("ReqPinTransDetail:", Get_Paramenter);

        Call<PinTransferDetailReaponse> pinTransferDetailReaponseCall=
                NetworkClient1.getInstance(context).create(EpinServices.class).fetchPinTransferDetail(Request);

        pinTransferDetailReaponseCall.enqueue(new Callback<PinTransferDetailReaponse>() {
            @Override
            public void onResponse(Call<PinTransferDetailReaponse> call, Response<PinTransferDetailReaponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();

                try {
                    PinTransferDetailReaponse Response=new PinTransferDetailReaponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {
                            if(Response.getTransferdetail()!= null && Response.getTransferdetail().size() > 0){

                                layoutNoRecord.setVisibility(View.GONE);
                                layoutRecord.setVisibility(View.VISIBLE);
                                layoutTotal.setVisibility(View.VISIBLE);

                                pinTransDetailArrayList=new ArrayList<PinTransferDetailReaponse.PinTransferDetail>();
                                pinTransDetailArrayList=Response.getTransferdetail();
                                totalRecord = Integer.parseInt(Response.getRecordcount());
                                textViewRecord.setText("Record"+"\n"+String.valueOf(totalRecord));
                                //Toast.makeText(context,"Record:" +downlineDetailReeponse.getRecordcount(),Toast.LENGTH_SHORT).show();
                                // fillData(downlineDetails);

                                int totcount= Integer.parseInt(Response.getRecordcount());
                                if(totcount <= 10){
                                    TOTAL_PAGES= 1;
                                    detailAdapter.addAll(pinTransDetailArrayList);
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
                                        detailAdapter.addAll(pinTransDetailArrayList);

                                        if (currentPage <= TOTAL_PAGES)
                                            detailAdapter.addLoadingFooter();
                                        else
                                            isLastPage = true;
                                    }
                                    else
                                        loadNextPage(pinTransDetailArrayList);
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
            public void onFailure(Call<PinTransferDetailReaponse> call, Throwable t) {

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

    private void loadNextPage(ArrayList<PinTransferDetailReaponse.PinTransferDetail> list) {
        Log.d(TAG, "loadNextPage: " + currentPage);
        ArrayList<PinTransferDetailReaponse.PinTransferDetail> rechargeReports=new ArrayList<PinTransferDetailReaponse.PinTransferDetail>();
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

    /*public void fillData( PinTransferDetailReaponse.PinTransferDetail arrList[]) {
        //recyViewDesigLead.setVisibility(View.VISIBLE);

        if (from == 1  ) {

            pinTransDetailArrayList = new ArrayList<PinTransferDetailReaponse.PinTransferDetail>(Arrays.asList(arrList));
            detailAdapter.setData(pinTransDetailArrayList,recyPinTransDetail);


        } else {
            ArrayList<PinTransferDetailReaponse.PinTransferDetail> temList;
            temList= new ArrayList<PinTransferDetailReaponse.PinTransferDetail>(Arrays.asList(arrList));
            pinTransDetailArrayList.addAll(temList);
            detailAdapter.setData( pinTransDetailArrayList, recyPinTransDetail);

        }
        detailAdapter.notifyDataSetChanged();
        // progressBarLoding.setVisibility(View.GONE);
        detailAdapter.setLoaded();


        detailAdapter .setOnLoadMoreListener(new PinTransferDetailAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                pinTransDetailArrayList.add(null);
                //leaderListAdapter.notifyDataSetChanged();//(desigLeadArrayList.size() );
                detailAdapter.notifyItemInserted(pinTransDetailArrayList.size() );
                // progressBarLoding.setVisibility(View.VISIBLE);
                recyPinTransDetail.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //   remove progress item
                        pinTransDetailArrayList.remove(pinTransDetailArrayList.size()-1 );
                        detailAdapter.notifyItemRemoved(pinTransDetailArrayList.size());
                        detailAdapter.notifyItemInserted(pinTransDetailArrayList.size() );

                        from = from + 10;
                        to = to + 10;
                        int size=0;
                        size=size+pinTransDetailArrayList.size();

                        if (size < totalRecord) {

                            //new getPinTransferDetails().execute();
                            getPinTransferDetail();

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
