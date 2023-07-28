package in.discountmart.business.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import in.base.network.NetworkClient;
import in.base.ui.BaseFragment;
import in.discountmart.R;
import in.discountmart.business.adapter.MyBusinessDownAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.IncomeServices;
import in.discountmart.business.model_business.requestmodel.DownBusinessRequest;
import in.discountmart.business.model_business.responsemodel.DownBusinessResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.utility.ConnectivityUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBusinessDownFragment extends BaseFragment {

    RecyclerView recyclerViewRecord;
    Context context;
    ProgressDialog pDialog;
    LinearLayout layoutRecord;
    LinearLayout layoutNoRecord;
    TextView textViewTotal;

    String strSessionId="";
    String strMemberId="";
    String strFromDate="";
    String strToDate="";
    String strType="";
    int totalRecord=0 ;
    String strApiKey="";
    int from =0;
    int to=0;

    //Entity
    DownBusinessResponse.DownBusiness downBusiness[];
    ArrayList<DownBusinessResponse.DownBusiness> businessArrayList;

    MyBusinessDownAdapter adapter;
    boolean msgShown;

    //Empty constructor
    public MyBusinessDownFragment(){
        //
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.mybusiness_down_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            context = getActivity();
            recyclerViewRecord=(RecyclerView)v.findViewById(R.id.mybusiness_down_recycler);
            layoutNoRecord=(LinearLayout)v.findViewById(R.id.mybusiness_down_fragt_layout_noRecord);
            layoutRecord=(LinearLayout)v.findViewById(R.id.mybusiness_down_fragt_layout_Record);
            textViewTotal=(TextView)v.findViewById(R.id.mybusiness_down_fragt_txtView_record);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);

            Bundle bundle=getArguments();

            if(bundle != null){
                strMemberId=bundle.getString("MemberID");
                strSessionId=bundle.getString("SessionId");
                strFromDate=bundle.getString("FromDate");
                strToDate=bundle.getString("ToDate");
                strType=bundle.getString("Type");
            }
            else {

            }

            recyclerViewRecord.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            recyclerViewRecord.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewRecord.setLayoutManager(llm);
            adapter=new MyBusinessDownAdapter(context,strType,strFromDate,strToDate);
            recyclerViewRecord.setAdapter(adapter);

            from=1;
            to=10;
            if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
            else {
                //new getBusinessDownDetails().execute();
                getBusinessDownDetail();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return v;
    }

    /*BusinessDown Api Request and Response*/

    private void getBusinessDownDetail(){
        if(from == 1)
            showProgressDialog();
        else
            hideProgressDialog();

        DownBusinessRequest downlineDetailRequest = new DownBusinessRequest();
        /*Set value in Entity class*/
        if(strType.equals("Date")){
            downlineDetailRequest.setReqtype(ApiConstants.REQUEST_MYGROUP_BUSINESS);
            //downlineDetailRequest.setReqtype(ApiConstants.REQUEST_MY_BUSINESS_DATEWISE);
            downlineDetailRequest.setSessid("0");
            downlineDetailRequest.setStartdate(strFromDate);
            downlineDetailRequest.setEnddate(strToDate);

        }
        else {
            downlineDetailRequest.setReqtype(ApiConstants.REQUEST_GROUP_BUSINESS);
           // downlineDetailRequest.setStartdate("");
            //downlineDetailRequest.setEnddate("");
            downlineDetailRequest.setSessid(strSessionId);
        }

        downlineDetailRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        downlineDetailRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        downlineDetailRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        downlineDetailRequest.setFrom(String.valueOf(from));
        downlineDetailRequest.setTo(String.valueOf(to));
        downlineDetailRequest.setMemberid(SharedPrefrence_Business.getUserID(context));


        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(downlineDetailRequest);
        Log.e("Req-DownBusiness:-", Get_Paramenter);

        Call<DownBusinessResponse> callDownBusinessResponse=
                NetworkClient.getInstance(context).create(IncomeServices.class).fetchDownBusinessDetail(downlineDetailRequest,strApiKey);

        callDownBusinessResponse.enqueue(new Callback<DownBusinessResponse>() {
            @Override
            public void onResponse(Call<DownBusinessResponse> call, Response<DownBusinessResponse> response) {
                hideProgressDialog();
                try {

                    DownBusinessResponse Response = new DownBusinessResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {
                        //DownlineDetailResponse.DownlineDetails downlineDetails[] = downlineDetailReeponse.getDownline();
                        downBusiness = Response.getDown();
                        totalRecord= Integer.parseInt(Response.getRecordcount());
                        if(totalRecord > 0 && downBusiness.length > 0){

                            textViewTotal.setText("Record: \n"+Response.getRecordcount());
                           layoutNoRecord.setVisibility(View.GONE);
                           layoutRecord.setVisibility(View.VISIBLE);

                           if(totalRecord < 10 && downBusiness.length < 10){
                               businessArrayList = new ArrayList<DownBusinessResponse.DownBusiness>(Arrays.asList(downBusiness));
                               adapter.setData1( businessArrayList, context);
                               adapter.notifyDataSetChanged();
                           }
                           else {
                               fillData(downBusiness);
                           }

                        }
                        else if(totalRecord == 0 && downBusiness.length == 0){
                            //Toast.makeText(context,"Record is empty"  +Response.getRecordcount(),Toast.LENGTH_SHORT).show();
                            businessArrayList = new ArrayList<DownBusinessResponse.DownBusiness>(Arrays.asList(downBusiness));
                            totalRecord= Integer.parseInt(Response.getRecordcount());
                            adapter.setData( businessArrayList, recyclerViewRecord);
                            adapter.notifyDataSetChanged();
                            layoutNoRecord.setVisibility(View.VISIBLE);
                            layoutRecord.setVisibility(View.GONE);
                        }
                    }
                    else {
                        Toast.makeText(context, Response.getResponse(), Toast.LENGTH_SHORT).show();
                        //editTextConfirmCode.setText("");
                        //Toast.makeText(LoginActivity.this, loginUserGetResponseEntity.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DownBusinessResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());

            }
        });
    }


    public void fillData(DownBusinessResponse.DownBusiness arrList[]) {


        if (from == 1 && totalRecord > 0) {

            businessArrayList = new ArrayList<DownBusinessResponse.DownBusiness>(Arrays.asList(arrList));
            adapter.setData( businessArrayList, recyclerViewRecord);

        }
        else {
            ArrayList<DownBusinessResponse.DownBusiness> temList =
                    new ArrayList<DownBusinessResponse.DownBusiness>(Arrays.asList(arrList));
            businessArrayList.addAll(temList);
            adapter.setData( businessArrayList, recyclerViewRecord);
            //downlineDetailAdapter.notifyItemInserted(downlineDetailsArrayList.size());

        }
        adapter.notifyDataSetChanged();
        adapter.setLoaded();

        //set load more listener for the RecyclerView adapter
        adapter .setOnLoadMoreListener(new MyBusinessDownAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                businessArrayList.add(null);
                adapter.notifyItemInserted(businessArrayList.size());

                recyclerViewRecord.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //   remove progress item
                        businessArrayList.remove(businessArrayList.size()-1 );
                        adapter.notifyItemRemoved(businessArrayList.size());
                        adapter.notifyItemInserted(businessArrayList.size());
                        int size=0;
                        size=size+businessArrayList.size();
                        from = from + 10;
                        to = to + 10;

                        if (size < totalRecord) {

                            if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                               // new getBusinessDownDetails().execute();
                                getBusinessDownDetail();
                            }

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
    }
}
