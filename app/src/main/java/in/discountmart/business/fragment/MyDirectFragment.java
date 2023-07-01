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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import in.base.network.NetworkClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import in.discountmart.R;
import in.discountmart.business.activity.CommonReportActivity;
import in.discountmart.business.adapter.LevelListAdapter;
import in.discountmart.business.adapter.LevelWiseDirectAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.MyTeamService;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.responsemodel.LevelListResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDirectFragment extends Fragment {

    Context context;
    Spinner spinnerLevel;
    Spinner spinnerStatus;
    Spinner spinnerSearchBy;
    RecyclerView recyLevelReport;
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

    String strLevelID="";
    String strLevelName="";
    String strStatus="";
    String strSearch="";
    String strApiKey="";

    boolean msgShown;

    int from=0;
    int to=0;
    int totalRecord;

    /*Entity Class*/
    LevelListResponse.LevelList levelList[];
    //MyDirectResponse.MyDirect levelWiseReport[];

    /*Array List*/
    ArrayList<LevelListResponse.LevelList> levelListArrayList;
    //ArrayList<MyDirectResponse.MyDirect> levelWiseReportArrayList;

    /*Adapter*/
    LevelListAdapter levelListAdapter;
    LevelWiseDirectAdapter levelWiseDirectAdapter;




    // Empty Constructor
    public MyDirectFragment(){
        // Empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.business_fragment_my_direct, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        try {
            view1=getActivity().findViewById(android.R.id.content);
            context=getActivity();

            spinnerLevel=(Spinner)rootView.findViewById(R.id.mydirect_frag_spinlevel);
            spinnerStatus=(Spinner)rootView.findViewById(R.id.mydirect_frag_spiner_status);
            spinnerSearchBy=(Spinner)rootView.findViewById(R.id.mydirect_frag_spiner_search);
            //recyLevelReport=(RecyclerView)rootView.findViewById(R.id.level_detail_frag_recycler);
            //textViewRecord=(TextView)rootView.findViewById(R.id.mydirect_frag_txtView_total_record);
            //progressBar=(ProgressBar)rootView.findViewById(R.id.level_detail_frag_progressBar1);
            //layoutProgress=(LinearLayout)rootView.findViewById(R.id.level_detail_frag_Layout_progressbar);
           // layoutNoRecord=(LinearLayout)rootView.findViewById(R.id.level_detail_frag_layout_norecord);
            //layoutRecord=(LinearLayout)rootView.findViewById(R.id.level_detail_frag_Layout_recycler);
            layoutLevel=(LinearLayout)rootView.findViewById(R.id.mydirect_frag_layout_level);
            btnSubmit=(Button)rootView.findViewById(R.id.mydirect_frag_btn_submit);

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
                        layoutLevel.setVisibility(View.GONE);

                    }
                    else if(stringStatus.equals("Right")) {
                        strSearch="2";
                        layoutLevel.setVisibility(View.GONE);

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            /*Call Method*/
            initSpinnerStatus();

            /*spinner status item selected listener*/
            spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String stringStatus = spinnerStatus.getSelectedItem().toString();

                    if(stringStatus.equals("All")){
                        strStatus="A";

                    }
                    if(stringStatus.equals("Active")){
                        strStatus="Y";

                    }
                    else if(stringStatus.equals("Deactive")) {
                        strStatus = "N";

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

          /*  *//*Call Level List API*//*
            if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
            else {
                //new getLevelListDetails().execute();
                getLevelList();
            }*/


            /*spinner level item selected listener*/
           /* spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    LevelListResponse.LevelList levelList1 =(LevelListResponse.LevelList)parent.getItemAtPosition(position);

                    if(levelList1!=null){
                        strLevelID=levelList1.getLevelid();
                        strLevelName=levelList1.getLevelname();


                        //levelWiseDirectAdapter.setData( levelWiseReportArrayList, recyLevelReport);

                        *//*from=1;
                        to=20;*//*

                       *//* if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                            Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //new getLevelReportDetails().execute();
                            levelWiseReportArrayList = new ArrayList<LevelWiseReportResponse.LevelWiseReport>();
                            levelWiseReportArrayList.clear();

                            levelWiseDirectAdapter.setData( levelWiseReportArrayList, recyLevelReport);
                            levelWiseDirectAdapter.setData1( levelWiseReportArrayList);
                            levelWiseDirectAdapter.notifyDataSetChanged();
                            //getLevelWiseReportDetail();
                        }*//*
                        // new getLevelWiseReportDetails().execute();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/

            /*Button submit on click get level wise detail*/
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        /*MyDirectDetailFragment fragment=new MyDirectDetailFragment();
                        Bundle bundle=new Bundle();
                        bundle.clear();
                        bundle.putString("Status",strStatus);
                        bundle.putString("Search",strSearch);
                        bundle.putString("Level","1");

                        fragment.setArguments(bundle);*/
                       // ((BusinessDashboardActivity)getActivity()).replaceFragment(fragment,"Level Detail",bundle);
                        Intent intent=new Intent(context, CommonReportActivity.class);
                        intent.putExtra("Status",strStatus);
                        intent.putExtra("Search",strSearch);
                        intent.putExtra("Level","1");
                        intent.putExtra("Type","MyDirect");
                        intent.putExtra("Title","My Direct Detail");
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

        arrayList.add(0,"All");
        arrayList.add(1,"Active");
        arrayList.add(2,"Deactive");
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

    /*Get Package List Response Api*/
    private void getLevelList() {
       pDialog=new ProgressDialog(getActivity());
       pDialog.setMessage("please wait..");
       pDialog.setCancelable(false);
       pDialog.show();

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setReqtype(ApiConstants.REQUEST_LevelList);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Parameter=gson.toJson(baseRequest);
        Log.e("RequestLevelList:", Parameter);

        Call<LevelListResponse> responseCall = NetworkClient.getInstance(context).create(MyTeamService.class).fetchLevelList(baseRequest,strApiKey);

        responseCall.enqueue(new Callback<LevelListResponse>() {
            @Override
            public void onResponse(Call<LevelListResponse> call, Response<LevelListResponse> response) {
               if(pDialog.isShowing())
                   pDialog.dismiss();
                LevelListResponse levelListResponse = response.body();
                try {

                    if (levelListResponse.getResponse().equals("OK")) {
                        levelList = levelListResponse.getLevels();
                        levelListResponse.getLevels();
                        if(levelList != null && levelList.length > 0) {
                            levelListArrayList = new ArrayList<LevelListResponse.LevelList>(Arrays.asList(levelList));
                            levelListAdapter = new LevelListAdapter(getActivity(), levelListArrayList);
                            spinnerLevel.setAdapter(levelListAdapter);
                        }
                        else {
                            Toast.makeText(context, "Level List Is Empty", Toast.LENGTH_SHORT).show();
                            }

                    }
                    else {
                        Snackbar.make(view1, levelListResponse.getResponse(), Snackbar.LENGTH_LONG)
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
            public void onFailure(Call<LevelListResponse> call, Throwable t) {
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
