package in.discountmart.business.fragment;


import static in.discountmart.business.utility.Utilities.convertMonth;
import static in.discountmart.business.utility.Utilities.convertdayNumber;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import in.base.network.NetworkClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import in.discountmart.R;
import in.discountmart.business.activity.CommonReportActivity;
import in.discountmart.business.adapter.LevelListAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.MyTeamService;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.responsemodel.LevelListResponse;
import in.discountmart.business.model_business.responsemodel.LevelWiseReportResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownlinePurchaseFragment extends Fragment {

    Context context;
    View view;
    Spinner spnrSerachBy;
    Spinner spnrLevel;
    Spinner spnrRequestBy;
    RadioGroup rdgChoose;
    RadioGroup rdgGroup;
    RadioButton rdbRepurchase;
    RadioButton rdbTopup;
    RadioButton rdbGroupA;
    RadioButton rdbGroupB;
    Button btnSubmit;
    LinearLayout layoutLevel;
    LinearLayout layoutGroup;
    public static TextView txtFromDate;
    public static TextView txtToDate;
    public static int yyFromDate ;
    public static int mmFromDate ;
    public static int ddFromDate ;

    public static int yyToDate ;
    public static int mmToDate ;
    public static int ddToDate;
    public static String strFromDate="";
    public static String strToDate="";
    ProgressDialog pDialog;
    String searchBy="";
    String levelId="";
    String levelName="";
    String strType="";
    String strGroup="";
    String strApiKey="";

    /*Entity Class*/
    LevelListResponse.LevelList levelList[];
    LevelWiseReportResponse.LevelWiseReport levelWiseReport[];

    /*Array List*/
    ArrayList<LevelListResponse.LevelList> levelListArrayList;
    ArrayList<LevelWiseReportResponse.LevelWiseReport> levelWiseReportArrayList;

    /*Adapter*/
    LevelListAdapter levelListAdapter;


    public DownlinePurchaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView=inflater.inflate(R.layout.fragment_downline_purchase, container, false);
        try {
            context = getActivity();
            view=getActivity().findViewById(android.R.id.content);

            spnrLevel=(Spinner)mainView.findViewById(R.id.downline_purchase_frag_spinlevel);
            rdgChoose=(RadioGroup)mainView.findViewById(R.id.downline_repurchase_frag_rdg_choose);
            rdgGroup=(RadioGroup)mainView.findViewById(R.id.downline_repurchase_frag_rdg_group);
            rdbGroupA=(RadioButton) mainView.findViewById(R.id.downline_purchase_frag_rdbtn_group_a);
            rdbGroupB=(RadioButton)mainView.findViewById(R.id.downline_purchase_frag_rdbtn_group_b);
            rdbRepurchase=(RadioButton)mainView.findViewById(R.id.downline_purchase_frag_rdbtn_repurchase);
            rdbTopup=(RadioButton)mainView.findViewById(R.id.downline_purchase_frag_rdbtn_topup);
            btnSubmit=(Button)mainView.findViewById(R.id.downline_purchase_frag_btn_submit);

            txtFromDate=(TextView)mainView.findViewById(R.id.downline_purchase_frag_txt_fromdate);
            txtToDate=(TextView)mainView.findViewById(R.id.downline_purchase_frag_txt_todate);
            layoutLevel=(LinearLayout)mainView.findViewById(R.id.downline_purchase_frag_layout_level);
            layoutGroup=(LinearLayout)mainView.findViewById(R.id.downline_purchase_frag_layout_group);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);


            /*Call Level Lisr Api method*/
            getLevelList();
            /*Radio Group Choose option*/
            if(rdbRepurchase.isChecked()){
                strType="R";
                layoutLevel.setVisibility(View.VISIBLE);
                layoutGroup.setVisibility(View.GONE);
            }
            else if(rdbTopup.isChecked()){
                strType="T";
                layoutLevel.setVisibility(View.GONE);
                layoutGroup.setVisibility(View.VISIBLE);
            }
            rdgChoose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radioButtonDownline = (RadioButton)group.findViewById(checkedId);
                    String type=radioButtonDownline.getText().toString();
                    if(type.equals("Repurchase")){
                        strType="R";
                        layoutLevel.setVisibility(View.VISIBLE);
                        layoutGroup.setVisibility(View.GONE);
                    }
                    else if (type.equals("TopUp")){
                        strType="T";
                        layoutLevel.setVisibility(View.GONE);
                        layoutGroup.setVisibility(View.VISIBLE);
                    }


                }
            });

            rdgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radioButtonDownline = (RadioButton)group.findViewById(checkedId);
                    String type=radioButtonDownline.getText().toString();
                    if(type.equals("Group A")){
                        strGroup="1";
                    }
                    else if (type.equals("Group B")){
                        strGroup="2";
                    }
                    else if (type.equals("Both")){
                        strGroup="0";
                    }


                }
            });

            /*Call Method for spinner search by*/
           // initSpinnerSearchBy();

            /*spinner Search by item selected listener*/
           /* spnrSerachBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String stringStatus = spnrSerachBy.getSelectedItem().toString();

                    if(stringStatus.equals("Level Wise")){
                        searchBy="0";

                        layoutLevel.setVisibility(View.VISIBLE);

                        *//*check network is enable on not *//*
                        if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                            Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();
                        }
                        else {
                            //call level list api
                            getLevelList();
                        }


                    }
                    else if(stringStatus.equals("Left")) {
                        searchBy="1";
                        layoutLevel.setVisibility(View.GONE);

                    }
                    else if(stringStatus.equals("Right")) {
                        searchBy="2";
                        layoutLevel.setVisibility(View.GONE);

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/

            /*spinner level item selected listener*/
            spnrLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    LevelListResponse.LevelList levelList1 =(LevelListResponse.LevelList)parent.getItemAtPosition(position);

                    if(levelList1!=null){
                        levelId=levelList1.getLevelid();
                        levelName=levelList1.getLevelname();


                        //levelWiseDirectAdapter.setData( levelWiseReportArrayList, recyLevelReport);

                        /*from=1;
                        to=20;*/

                       /* if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
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
                        }*/
                        // new getLevelWiseReportDetails().execute();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            // click event for date picker
            txtFromDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectFromDateFragment();
                    newFragment.show(getChildFragmentManager(), "DatePicker");
                }
            });
            txtToDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectToDateFragment();
                    newFragment.show(getChildFragmentManager(), "DatePicker");
                }
            });

            /*Button Submitt on click*/
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(rdbRepurchase.isChecked()){
                        if(strType.equals("")){
                            Toast.makeText(context,"Please select Service type",Toast.LENGTH_SHORT).show();
                        }
                        else if(levelId.equals("") ){
                            Toast.makeText(context,"Please select level",Toast.LENGTH_SHORT).show();
                        }
                        else if(txtFromDate.getText().toString().equals("")){
                            Toast.makeText(context,"Please select start date",Toast.LENGTH_SHORT).show();
                        }

                        else if(txtToDate.getText().toString().equals("")){
                            Toast.makeText(context,"Please select end date",Toast.LENGTH_SHORT).show();
                        }
                        else {
                           /* Bundle bundle=new Bundle();
                            bundle.putString("levelId",levelId);
                            bundle.putString("FromDate",strFromDate);
                            bundle.putString("ToDate",strToDate);
                            bundle.putString("Type",strType);
                            bundle.putString("Group","0");
                            Log.e("PurchDownline:-",bundle.toString());*/

                            Intent intent=new Intent(context, CommonReportActivity.class);
                            intent.putExtra("levelId",levelId);
                            intent.putExtra("FromDate",strFromDate);
                            intent.putExtra("ToDate",strToDate);
                            intent.putExtra("Type",strType);
                            intent.putExtra("Group","0");
                            intent.putExtra("Title","Downline Purchase");
                            //intent.putExtras(bundle);
                           context.startActivity(intent);
                        }
                    }
                    else {
                        if(strGroup.equals("") ){
                            Toast.makeText(context,"Please choose group option",Toast.LENGTH_SHORT).show();
                        }
                        else if(txtFromDate.getText().toString().equals("")){
                            Toast.makeText(context,"Please select start date",Toast.LENGTH_SHORT).show();
                        }

                        else if(txtToDate.getText().toString().equals("")){
                            Toast.makeText(context,"Please select end date",Toast.LENGTH_SHORT).show();
                        }
                        else {
                           /* Bundle bundle=new Bundle();
                            bundle.putString("levelId","0");
                            bundle.putString("FromDate",strFromDate);
                            bundle.putString("ToDate",strToDate);
                            bundle.putString("Type",strType);
                            bundle.putString("Group",strGroup);
                            Log.e("PurchDownline:-",bundle.toString());*/

                            Intent intent=new Intent(context, CommonReportActivity.class);
                            intent.putExtra("levelId","0");
                            intent.putExtra("FromDate",strFromDate);
                            intent.putExtra("ToDate",strToDate);
                            intent.putExtra("Type",strType);
                            intent.putExtra("Group",strGroup);
                            intent.putExtra("Title","Downline Purchase");
                            //intent.putExtras(bundle);
                            context.startActivity(intent);

                        }
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return mainView;


    }


    public void initSpinnerStatus(){
        ArrayList<String> arrayList=new ArrayList<>();

        arrayList.add(0,"Active");
        arrayList.add(1,"Deactive");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,	android.R.layout.simple_spinner_item, arrayList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrRequestBy.setAdapter(dataAdapter);
    }

    /*Method for Initialize Spinner Search By*/
    public void initSpinnerSearchBy(){
        ArrayList<String> arrayList=new ArrayList<>();

        arrayList.add(0,"Level Wise");
        arrayList.add(1,"Left");
        arrayList.add(2,"Right");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,	android.R.layout.simple_spinner_item, arrayList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrSerachBy.setAdapter(dataAdapter);
    }

    // date picker open for CHECKIN-Date and selected date set to textview
    public static class SelectFromDateFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar1 = Calendar.getInstance();
            int yy = calendar1.get(Calendar.YEAR);
            int mm = calendar1.get(Calendar.MONTH);
            int dd = calendar1.get(Calendar.DAY_OF_MONTH);

            //Disable date before today date
            DatePickerDialog dpd = new DatePickerDialog(getContext(), this, yy, mm, dd);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                int thisMonth = mm + 1;
                String stringTodayDate = dd + "/" + thisMonth + "/" + yy;
                Date todayDate = sdf.parse(stringTodayDate);
                dpd.getDatePicker().setMaxDate(todayDate.getTime());

            } catch (ParseException e) {
                //handle exception
            }
            return dpd;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            SetFromDate(yy, mm +1, dd);
        }
    }

    public static void SetFromDate(int year, int month, int day) {
        String Month = convertMonth(month);
        txtFromDate.setText(day + " " + Month + " " + year);
        strFromDate = (convertdayNumber(day) + "-" + Month + "-" +year);

        yyFromDate = year;
        mmFromDate = month;
        ddFromDate = day;

        Calendar mCalendarTo = Calendar.getInstance();
        mCalendarTo.set(yyFromDate, mmFromDate, ddFromDate);
        mCalendarTo.add(Calendar.DATE, 1);
        int yy = mCalendarTo.get(Calendar.YEAR);
        int mm = mCalendarTo.get(Calendar.MONTH);
        int dd = mCalendarTo.get(Calendar.DAY_OF_MONTH);
        String mon = Utilities.convertMonthtoText(mm);

        //set Next Date as Check out date
        //textViewToDate.setText(dd  + " " + mon + " " + yy);
        // stringToDate = (yy + "-" + Utilities.convertMonthNumber(mm) + "-" + Utilities.convertdayNumber(dd)).toString();
    }


    // date picker open for CHECKIN-Date and selected date set to textview
    public static class SelectToDateFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar1 = Calendar.getInstance();
            int yy = calendar1.get(Calendar.YEAR);
            int mm = calendar1.get(Calendar.MONTH);
            int dd = calendar1.get(Calendar.DAY_OF_MONTH);

            //Disable date before today date
            DatePickerDialog dpd = new DatePickerDialog(getContext(), this, yy, mm, dd);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                int thisMonth = mm + 1;
                String stringTodayDate = dd + "/" + thisMonth + "/" + yy;
                Date todayDate = sdf.parse(stringTodayDate);
                dpd.getDatePicker().setMaxDate(todayDate.getTime());

            } catch (ParseException e) {
                //handle exception
            }
            return dpd;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            SetToDate(yy, mm +1, dd);
        }
    }

    public static void SetToDate(int year, int month, int day) {
        String Month = convertMonth(month);
        txtToDate.setText(day + " " + Month + " " + year);
        strToDate = (convertdayNumber(day) + "-" + Month + "-" + year);

        yyToDate = year;
        mmToDate = month;
        ddToDate = day;

        Calendar mCalendarTo = Calendar.getInstance();
        mCalendarTo.set(yyToDate, mmToDate, ddToDate);
        mCalendarTo.add(Calendar.DATE, 1);
        int yy = mCalendarTo.get(Calendar.YEAR);
        int mm = mCalendarTo.get(Calendar.MONTH);
        int dd = mCalendarTo.get(Calendar.DAY_OF_MONTH);
        String mon = Utilities.convertMonthtoText(mm);

        //set Next Date as Check out date
        //textViewToDate.setText(dd  + " " + mon + " " + yy);
        //stringToDate = (yy + "-" + Utilities.convertMonthNumber(mm) + "-" + Utilities.convertdayNumber(dd)).toString();
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
                            spnrLevel.setAdapter(levelListAdapter);
                        }
                        else {
                            Toast.makeText(context, "Level List Is Empty", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Snackbar.make(view, levelListResponse.getResponse(), Snackbar.LENGTH_LONG)
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

    @Override
    public void onResume(){
        super.onResume();
        //initSpinnerSearchBy();
        //initSpinnerStatus();

    }

}
