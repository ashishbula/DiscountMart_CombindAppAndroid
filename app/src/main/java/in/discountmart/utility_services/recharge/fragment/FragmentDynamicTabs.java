package in.discountmart.utility_services.recharge.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.recharge.adapter.RechargePlanDetailAdapter;
import in.discountmart.utility_services.recharge.call_recharge_api.RechargeApi;
import in.discountmart.utility_services.recharge.recharge_model.RechargePlanDetails;
import in.discountmart.utility_services.recharge.recharge_model.recharge_request_model.RechargePlanDetailRequest;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.RechargePlanDetail;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.RechargePlanResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.utilities.DividerItemDecoration;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDynamicTabs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDynamicTabs extends Fragment implements RechargePlanDetailAdapter.PlanDetalListener{

    View view;
    ProgressDialog progressDialog;
    Context context;
    TextView textView;
    LinearLayout layoutResult;
    LinearLayout layoutNoResult;
    RecyclerView recyclerView;
    PlanDetailListener listener;
    ArrayList<RechargePlanDetail> planDetailArrayList;
    ArrayList<RechargePlanDetail> tempPlanDetailArrayList;
    ArrayList<RechargePlanResponse.RechargePlansUnique> planArrayList;
    ArrayList<RechargePlanDetails> planList;
    ArrayList<RechargePlanDetails.PlanDetail> planDetailList;
    RechargePlanDetailAdapter planDetailAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String strCircle;
    private String strIpcode;
    private String strMobileNo;
    private String strPlan;
    int position;
    int planListSize=0;
    Handler handler;
    public FragmentDynamicTabs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param //param1 Parameter 1.
     * @param //param2 Parameter 2.
     * @return A new instance of fragment FragmentDynamicTabs.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDynamicTabs newInstance() {
       // FragmentDynamicTabs fragment = new FragmentDynamicTabs();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return new FragmentDynamicTabs();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
            strIpcode = getArguments().getString("IpCode");
            strPlan = getArguments().getString("Plan");
            strCircle = getArguments().getString("Circle");
            //tempPlanDetailArrayList=new ArrayList<RechargePlanDetail>();
            planList=new ArrayList<RechargePlanDetails>();
            //planArrayList=new ArrayList<RechargePlanResponse.RechargePlansUnique>();
            //planArrayList = (ArrayList<RechargePlanResponse.RechargePlansUnique>) getArguments().getSerializable("PlanList");
            planList = (ArrayList<RechargePlanDetails>) getArguments().getSerializable("PlanList");
            //tempPlanDetailArrayList = (ArrayList<RechargePlanDetail>) getArguments().getSerializable("PlanList");


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainview=inflater.inflate(R.layout.fragment_dynamic_tabs, container, false);
        view=getActivity().findViewById(android.R.id.content);
        context=getActivity();
        try {
           initViews(mainview);
        }catch (Exception e){
            e.printStackTrace();
        }
        return mainview;

    }
    // initialise the categories
    private void initViews(View view) {
        try {
            textView = view.findViewById(R.id.commonTextView);
            layoutNoResult = view.findViewById(R.id.frag_dy_plandetail_layout_noresult);
            layoutResult = view.findViewById(R.id.frag_dy_plandetail_layout_result);
            recyclerView = view.findViewById(R.id.frag_dy_plandetail_recycler);

            //if (serviceProviderArrayList.size() > 0) {
            planDetailArrayList = new ArrayList<RechargePlanDetail>();
            planDetailAdapter = new RechargePlanDetailAdapter(context, planDetailArrayList,FragmentDynamicTabs.this);

            /*REcycler View set divider item line*/
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST, 0));
            recyclerView.setAdapter(planDetailAdapter);
            assert getArguments() != null;

            if(planList != null && planList.size() > 0){
                textView.setText(String.valueOf("Plan :  " + planList.get(position).getPlanName()));
               // planDetailList=new ArrayList<RechargePlanDetails.PlanDetail>();
                ArrayList<RechargePlanDetail> tempList = null;
                for(int i=position; i <planList.size(); i++){
                       tempList=new ArrayList<>();
                    for(int j=0; j < planList.get(i).getPlandetails().size(); j++){

                        RechargePlanDetail detail=new RechargePlanDetail();
                        detail.setPlanName(planList.get(i).getPlandetails().get(j).getPlanName());
                        detail.setDetails(planList.get(i).getPlandetails().get(j).getDetails());
                        detail.setLast_updated_dt(planList.get(i).getPlandetails().get(j).getLast_updated_dt());
                        detail.setRecharge_description(planList.get(i).getPlandetails().get(j).getRecharge_description());
                        detail.setRecharge_validity(planList.get(i).getPlandetails().get(j).getRecharge_validity());
                        detail.setRecharge_short_description(planList.get(i).getPlandetails().get(j).getRecharge_short_description());
                        detail.setRecharge_talktime(planList.get(i).getPlandetails().get(j).getRecharge_talktime());
                        detail.setSp_circle(planList.get(i).getPlandetails().get(j).getSp_circle());
                        detail.setRecharge_value(planList.get(i).getPlandetails().get(j).getRecharge_value());
                        detail.setId(planList.get(i).getPlandetails().get(j).getId());
                        detail.setSp_key(planList.get(i).getPlandetails().get(j).getSp_key());
                        tempList.add(detail);
                    }
                    break;
                }

                if(tempList != null && tempList.size() > 0){

                    layoutNoResult.setVisibility(View.GONE);
                    layoutResult.setVisibility(View.VISIBLE);
                    planDetailArrayList.clear();
                    planDetailArrayList.addAll(tempList);
                    if(planDetailArrayList != null && planDetailArrayList.size() > 0){
// refreshing recycler view
                        planDetailAdapter.notifyDataSetChanged();

                    }
                }
                else {
                    layoutNoResult.setVisibility(View.GONE);
                    layoutResult.setVisibility(View.VISIBLE);
                }

            }

        }catch (Exception e){e.printStackTrace();}




    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            listener = (PlanDetailListener) parent;
        } else {
            listener = (PlanDetailListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    /* Request and Response Service Provider List*/
    public void getRechargePlanDetail(String plan,String ipcode,String circle) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String strApiRequest = "";
        JSONObject object = null;
        String strToken = TokenBase64.getToken();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse = new LoginPreferences_Utility(context).getLoggedinUser();
        String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);

        ApiRequest apiRequest = new ApiRequest();
        try {

            /*Main Request Model*/

            BaseHeaderRequest headerRequest = new BaseHeaderRequest();
            headerRequest.setUserName(loginResponse.getUserName());
            headerRequest.setPassword(loginResponse.getPasswd());
            headerRequest.setSponsorFormNo(companyId);
//            if(loginResponse.getMemMode().equals("D"))
//                headerRequest.setSponsorFormNo(companyId);
//            else
//                headerRequest.setSponsorFormNo("");

            /*Getcity List Request Model*/
            RechargePlanDetailRequest request = new RechargePlanDetailRequest();
            request.setCircle(circle);
            request.setPlan(plan);
            request.setIpCode(ipcode);


            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(request);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<BaseResponse> fetchServiceProviderListCall =
                NetworkClient_Utility_1.getInstance(context).create(RechargeApi.class).fetchRechargePlanDetail(apiRequest, strToken);

        fetchServiceProviderListCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    BaseResponse Response = new BaseResponse();
                    Response = response.body();

                    if (Response != null) {
                        if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                            if (Response.getRESP_VALUE().equals("") || Response.getRESP_VALUE().isEmpty()) {

                                String toast = Response.getRESP_MSG();
                                //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();
                            } else if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                String[] planDetails = Response.getRESPONSE().split("");
                                //RechargePlanDetail serviceList = new Gson().fromJson(Response.getRESP_VALUE(), RechargePlanDetail.class);
                                if (planDetails != null && planDetails.length > 0) {
                                    planListSize--;

                                    layoutNoResult.setVisibility(View.GONE);
                                    layoutResult.setVisibility(View.VISIBLE);
                                    RechargePlanDetail[] serviceList = new Gson().fromJson(Response.getRESP_VALUE(), RechargePlanDetail[].class);
                                    List<RechargePlanDetail> planDetailList = new ArrayList<RechargePlanDetail>(Arrays.asList(serviceList));
                                    //planDetailArrayList = new ArrayList<RechargePlanDetail>();
                                    planDetailArrayList.clear();
                                    planDetailArrayList.addAll(planDetailList);
                                    if(planDetailArrayList != null && planDetailArrayList.size() > 0){
// refreshing recycler view
                                        planDetailAdapter.notifyDataSetChanged();

                                    }

                                } else {
                                    layoutNoResult.setVisibility(View.VISIBLE);
                                    layoutResult.setVisibility(View.GONE);
                                    //String toast = " City List empty";
                                    //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                                    //showSnackbar(toast);
                                }
                            }
                        }
                        else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();

                        }
                        else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                            String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(context, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                            // showSnackbar(toast);

                        }
                    }
                    else {
                        //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                        String toast = "something wrong";
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
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                // Toast.makeText(FlightCityListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    public interface PlanDetailListener {
        void onPlanSelect(RechargePlanDetail item);
    }

    @Override
    public void onSelected(RechargePlanDetail item) {

        try {
            if(item != null){
                if (listener != null) {
                    listener.onPlanSelect(item);

                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }






}