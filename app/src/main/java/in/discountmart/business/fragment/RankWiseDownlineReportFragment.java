package in.discountmart.business.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import in.base.network.NetworkClient1;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import in.discountmart.R;
import in.discountmart.business.activity.CommonReportActivity;
import in.discountmart.business.adapter.LevelWiseDirectAdapter;
import in.discountmart.business.adapter.RankListAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.MyTeamService;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.responsemodel.LevelWiseReportResponse;
import in.discountmart.business.model_business.responsemodel.RankListResponse;
import in.discountmart.business.model_business.responsemodel.RankReportResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankWiseDownlineReportFragment extends Fragment {

    Context context;
    Spinner spinnerRank;
    Spinner spinnerStatus;
    Spinner spinnerSearchBy;
    RecyclerView recyLevelReport;
    EditText edTxtMemberId;
    TextView textViewRecord;
    ProgressBar progressBar;
    LinearLayout layoutProgress;
    LinearLayout layoutNoRecord;
    LinearLayout layoutRecord;
    LinearLayout layoutLevel;
    Button btnSubmit;
    View view1;

    LevelWiseDirectAdapter groupAdapter;
    ProgressDialog pDialog;

    String strRankID="";
    String strRankName="";
    String strStatus="";
    String strSearch="";
    String strMemberId="";
    String strApiKey="";

    boolean msgShown;

    int from=0;
    int to=0;
    int totalRecord;

    /*Entity Class*/
   ArrayList<RankListResponse.RankList> rankLists;
    LevelWiseReportResponse.LevelWiseReport levelWiseReport[];

    /*Array List*/
    ArrayList<RankReportResponse> rankReportList;
    ArrayList<LevelWiseReportResponse.LevelWiseReport> levelWiseReportArrayList;

    /*Adapter*/
    RankListAdapter rankListAdapter;
    LevelWiseDirectAdapter levelWiseDirectAdapter;




    // Empty Constructor
    public RankWiseDownlineReportFragment(){
        // Empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.business_fragment_rank_wise_downline_report, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        try {
            view1=getActivity().findViewById(android.R.id.content);
            context=getActivity();

            spinnerRank=(Spinner)rootView.findViewById(R.id.rank_downline_report_frag_spin_rank);
            edTxtMemberId=(EditText) rootView.findViewById(R.id.rank_downline_report_frag_edtxt_memberid);
            //spinnerStatus=(Spinner)rootView.findViewById(R.id.level_detail_frag_spiner_status);
            spinnerSearchBy=(Spinner)rootView.findViewById(R.id.rank_downline_report_frag_spiner_search);
            //recyLevelReport=(RecyclerView)rootView.findViewById(R.id.level_detail_frag_recycler);
            //textViewRecord=(TextView)rootView.findViewById(R.id.rank_downline_report_frag_txtView_total_record);
            //progressBar=(ProgressBar)rootView.findViewById(R.id.level_detail_frag_progressBar1);
            //layoutProgress=(LinearLayout)rootView.findViewById(R.id.level_detail_frag_Layout_progressbar);
           // layoutNoRecord=(LinearLayout)rootView.findViewById(R.id.level_detail_frag_layout_norecord);
            //layoutRecord=(LinearLayout)rootView.findViewById(R.id.level_detail_frag_Layout_recycler);
            layoutLevel=(LinearLayout)rootView.findViewById(R.id.rank_downline_report_frag_layout_level);
            btnSubmit=(Button)rootView.findViewById(R.id.rank_downline_report_frag_btn_submit);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);


            /*Recycler View */
            /*recyLevelReport.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            recyLevelReport.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyLevelReport.setLayoutManager(llm);
            recyLevelReport.setItemAnimator(new DefaultItemAnimator());
            levelWiseDirectAdapter=new LevelWiseDirectAdapter(context);
            recyLevelReport.setAdapter(levelWiseDirectAdapter);*/

            /*Call Method for spinner search by*/
            initSpinnerSearchBy();

            /*spinner Search by item selected listener*/
            spinnerSearchBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String stringStatus = spinnerSearchBy.getSelectedItem().toString();

                    if(stringStatus.equals("Both")){
                        strSearch="0";
                    }
                    else if(stringStatus.equals("Left")) {
                        strSearch="1";

                    }
                    else if(stringStatus.equals("Right")) {
                        strSearch="2";


                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /*  *//*Call Rank List API*//*
              /*check network is enable on not */
            if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                Snackbar.make(view1, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();
            }
            else {
                //call Rank list api

                getLRankList();
            }


            /*spinner level item selected listener*/
            spinnerRank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    RankListResponse.RankList levelList1 =(RankListResponse.RankList)parent.getItemAtPosition(position);

                    if(levelList1!=null){
                        strRankID=levelList1.getRankid();
                        strRankName=levelList1.getRank();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /*Button submit on click get level wise detail*/
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                       /* if(edTxtMemberId.getText().toString().equals("")){
                            edTxtMemberId.setError("Enter Member Id");
                            edTxtMemberId.requestFocus();
                        }
                        else {

                            strMemberId=edTxtMemberId.getText().toString();
                            Intent intent=new Intent(context, CommonReportActivity.class);

                            intent.putExtra("Rank",strRankID);
                            intent.putExtra("Leg",strSearch);
                            intent.putExtra("Type","RankDownline");
                            intent.putExtra("MemberId",strMemberId);
                            intent.putExtra("Title","Downline Rank Report");
                            context.startActivity(intent);
                            getActivity(). overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        }*/
                        strMemberId=edTxtMemberId.getText().toString();
                        Intent intent=new Intent(context, CommonReportActivity.class);

                        intent.putExtra("Rank",strRankID);
                        intent.putExtra("Leg",strSearch);
                        intent.putExtra("Type","RankDownline");
                        intent.putExtra("MemberId",strMemberId);
                        intent.putExtra("Title","Downline Rank Report");
                        context.startActivity(intent);
                        getActivity(). overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);


                    } catch (Exception e){

                        e.printStackTrace();
                    }



                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


        return rootView;

    }

    public void initSpinnerStatus(){
        ArrayList<String> arrayList=new ArrayList<>();

        arrayList.add(0,"Active");
        arrayList.add(1,"Deactive");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,	android.R.layout.simple_spinner_item, arrayList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(dataAdapter);
    }

    /*Method for Initialize Spinner Search By*/
    public void initSpinnerSearchBy(){
        ArrayList<String> arrayList=new ArrayList<>();

        arrayList.add(0,"Both");
        arrayList.add(1,"Left");
        arrayList.add(2,"Right");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,	android.R.layout.simple_spinner_item, arrayList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSearchBy.setAdapter(dataAdapter);
    }

    /*Get Rank List Response Api*/
    private void getLRankList() {
       pDialog=new ProgressDialog(getActivity());
       pDialog.setMessage("please wait..");
       pDialog.setCancelable(false);
       pDialog.show();

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setReqtype(ApiConstants.REQUEST_RANK);
        //baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        //baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Parameter=gson.toJson(baseRequest);
        Log.e("RequestLevelList:", Parameter);

        Call<RankListResponse> responseCall = NetworkClient1.getInstance(context).create(MyTeamService.class).fetchRankList(baseRequest,strApiKey);

        responseCall.enqueue(new Callback<RankListResponse>() {
            @Override
            public void onResponse(Call<RankListResponse> call, Response<RankListResponse> response) {
               if(pDialog.isShowing())
                   pDialog.dismiss();
                RankListResponse listResponse = response.body();
                try {

                    if (listResponse.getResponse().equals("OK")) {


                        if(listResponse.getRank() != null && listResponse.getRank().size() > 0) {
                            rankLists = new ArrayList<RankListResponse.RankList>();
                            rankLists = listResponse.getRank();
                            rankListAdapter = new RankListAdapter(getActivity(), rankLists);
                            spinnerRank.setAdapter(rankListAdapter);
                        }
                        else {
                            Toast.makeText(context, "Rank List Is Empty", Toast.LENGTH_SHORT).show();
                            }

                    }
                    else {
                        Snackbar.make(view1, listResponse.getResponse(), Snackbar.LENGTH_LONG)
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
            public void onFailure(Call<RankListResponse> call, Throwable t) {
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

    @Override
    public void onResume(){
        super.onResume();
        //initSpinnerSearchBy();
        //initSpinnerStatus();

    }

}
