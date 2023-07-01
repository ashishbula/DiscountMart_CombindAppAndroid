package in.discountmart.business.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import in.base.network.NetworkClient;
import com.google.android.material.snackbar.Snackbar;
import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.activity.CommonReportActivity;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.MyTeamService;
import in.discountmart.business.model_business.requestmodel.DownlineStatusRequest;
import in.discountmart.business.model_business.responsemodel.DownlineStatusResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 13-12-2016.
 */

public class DownlineFragment extends Fragment {

    Context context;
    String userID = "";
    String stringPassw = "";
    Button btnGroupA;
    Button btnGroupB;
    Button btnSubmit;
    TextView textViewTotalJoinedGroupA,textViewTotaltopupgroupA;
    TextView textViewTotalLeftBV,textViewTotalRightBV;
    TextView textViewTotalJoinedB,textViewTotaltopupgroupB;
    Spinner spinnerStatus;

    public static LinearLayout linearLayoutFormDate;
    public static LinearLayout linearLayoutToDate;

    public  static TextView textViewFromDate;
    public static TextView textViewToDate;

    static String stringFromDate="";
    static String stringToDate="";
    String strRbtnReqValue="R";
    String strStatus="";
    String strApiKey="";

    public static int yyFromDate ;
    public static int mmFromDate ;
    public static int ddFromDate ;

    public static int yyToDate ;
    public static int mmToDate ;
    public static int ddToDate;

    ProgressDialog pDialog;
    View view1;


    //Empty constructor
    public DownlineFragment(){
        //
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.business_downline_status, container, false);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            context = getActivity();
            view1=getActivity().findViewById(android.R.id.content);
            //SharedPreferences prefs = context.getSharedPreferences(LoginActivity.MY_PREFS_NAME, MODE_PRIVATE);
            userID = SharedPrefrence_Business.getUserID(getActivity());//"No name defined" is the default value.
            stringPassw = SharedPrefrence_Business.getPassword(getActivity());

            textViewTotalJoinedGroupA = (TextView)v.findViewById(R.id.downline_fragment_txt_totaljoingroupa);
            textViewTotalJoinedB  = (TextView)v.findViewById(R.id.downline_fragment_txt_totaljoingroupb);
            textViewTotaltopupgroupA = (TextView)v.findViewById(R.id.downline_fragment_txt_totaltopupgroupa);
            textViewTotaltopupgroupB  = (TextView)v.findViewById(R.id.downline_fragment_txt_totaltopupgroupb);
            textViewTotalLeftBV  = (TextView)v.findViewById(R.id.downline_fragment_txt_totalbv_groupA);
            textViewTotalRightBV  = (TextView)v.findViewById(R.id.downline_fragment_txt_totalbv_groupB);

            btnGroupA = (Button)v.findViewById(R.id.downline_fragment_btn_groupa);
            btnGroupB = (Button) v.findViewById(R.id.downline_fragment_btn_groupb);


            strStatus= SharedPrefrence_Business.getIsActive(getActivity());
            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);

            // initSpinnerStatus();

            getDownlineStatus();




            // click event for date picker, spinner and button



            btnGroupA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    /*DownlineFragmentGroupA downlineFragmentGroupA = new DownlineFragmentGroupA();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the business_transaction to the back stack so the user can navigate back
                    Bundle args = new Bundle();
                    args.putString("Status", strStatus);
                    args.putString("FromDate", stringFromDate);
                    args.putString("ToDate", stringToDate);
                    downlineFragmentGroupA.setArguments(args);
                    transaction.replace(R.id.content_frame, downlineFragmentGroupA);
                    transaction.addToBackStack(null);
                    // Commit the business_transaction
                    transaction.commit();*/

                    Intent intent=new Intent(context, CommonReportActivity.class);
                    intent.putExtra("Status",strStatus);
                    intent.putExtra("FromDate",stringFromDate);
                    intent.putExtra("ToDate",stringToDate);
                    intent.putExtra("Side","1");
                    intent.putExtra("Type","DownDetail");
                    intent.putExtra("Title","Downline Detail");
                    context.startActivity(intent);
                    getActivity(). overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                }
            });


            btnGroupB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DownlineFragmentGroupB downlineFragmentGroupB = new DownlineFragmentGroupB();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the business_transaction to the back stack so the user can navigate back

                   /* Bundle args = new Bundle();
                    args.putString("Status", strStatus);
                    args.putString("FromDate", stringFromDate);
                    args.putString("ToDate", stringToDate);
                    downlineFragmentGroupB.setArguments(args);
                    transaction.replace(R.id.content_frame, downlineFragmentGroupB);
                    transaction.addToBackStack(null);
                    // Commit the business_transaction
                    transaction.commit();*/

                    Intent intent=new Intent(context, CommonReportActivity.class);
                    intent.putExtra("Status",strStatus);
                    intent.putExtra("FromDate",stringFromDate);
                    intent.putExtra("Type","DownDetail");
                    intent.putExtra("Title","Downline Detail");
                    intent.putExtra("ToDate",stringToDate);
                    intent.putExtra("Side","2");
                    context.startActivity(intent);
                    getActivity(). overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


        return v;
    }

    /*Initialise Spinner*/
    public void initSpinnerStatus(){
        ArrayList<String> arrayList=new ArrayList<>();

        arrayList.add(0,"Active");
        arrayList.add(1,"Deactive");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,	android.R.layout.simple_spinner_item, arrayList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnerStatus.setAdapter(dataAdapter);
    }


    /*GEt Downline status api request and response*/
    private void getDownlineStatus(){

        //showProgressDialog();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        DownlineStatusRequest downlineStatusRequest = new DownlineStatusRequest();
        /*Set value in Entity class*/
        downlineStatusRequest.setReqtype(ApiConstants.REQUEST_DOWNLINE_STATUS);
        downlineStatusRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        downlineStatusRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        downlineStatusRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        downlineStatusRequest.setFromdate(stringFromDate);
        downlineStatusRequest.setTodate(stringToDate);
        downlineStatusRequest.setStatus(strStatus);

        Call<DownlineStatusResponse> downlineStatusResponseCall
                = NetworkClient.getInstance(context).create(MyTeamService.class).fetchDownlineStatus(downlineStatusRequest,strApiKey);

        downlineStatusResponseCall.enqueue(new Callback<DownlineStatusResponse>() {
            @Override
            public void onResponse(Call<DownlineStatusResponse> call, Response<DownlineStatusResponse> response) {
                //hideProgressDialog();

                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                try {

                    DownlineStatusResponse Response= new DownlineStatusResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {
                            textViewTotalJoinedGroupA.setText(Response.getLeftjoin());
                            textViewTotalJoinedB.setText(Response.getRightjoin());
                            textViewTotaltopupgroupA.setText(Response.getLeftactive());
                            textViewTotaltopupgroupB.setText(Response.getRightactive());
                            textViewTotalLeftBV.setText(Response.getTotalleftbv());
                            textViewTotalRightBV.setText(Response.getTotalrightbv());
                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            new BusinessDashboardActivity().blankValueFromSharePreference(context,Response.getMsg());
                        }
                        else if(Response.getResponse().equals("FAILED")) {
                            String msg=Response.getResponse()+ " "+"Something went wrong..";
                            Snackbar.make(view1, msg, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();

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
                        String toast= "Something went wrong...";
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
            public void onFailure(Call<DownlineStatusResponse> call, Throwable t) {
                //hideProgressDialog();
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

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
        initSpinnerStatus();
        stringFromDate="";
        stringToDate="";
    }

}

