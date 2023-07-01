package in.discountmart.business.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.base.network.NetworkClient;
import in.base.ui.BaseFragment;
import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.adapter.DateSessionAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.business_constants.AppConstants;
import in.discountmart.business.call_api.IncomeServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.MyBusinessRequest;
import in.discountmart.business.model_business.responsemodel.MonthSessionResponse;
import in.discountmart.business.model_business.responsemodel.MyBusinessResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;
import in.discountmart.utility.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBusinessFragment extends BaseFragment {

    TextView txtViewMemName;
    TextView txtViewMemId;
    TextView txtViewSponserId;
    TextView txtViewLevel;
    TextView txtViewGroup;
    TextView txtViewPV;
    TextView txtViewSelf;
    TextView txtViewTotal;
    MaterialButton btnDownline;
    Button btnSelf;
    Spinner spinnerSesson;
    LinearLayout layoutItem;
    LinearLayout layoutNOItem;
    LinearLayout layoutBtn;
    LinearLayout layoutSponserId;
    LinearLayout layoutDate;
    LinearLayout layoutSession;
    LinearLayout linearLayoutFormDate;
    LinearLayout linearLayoutToDate;
    RadioGroup rdgOption;
    RadioButton rdbDate;
    RadioButton rdbSession;
    public  static TextView textViewFromDate;
    public static TextView textViewToDate;
    static String stringFromDate="";
    static String stringToDate="";
    String stringNoOfRoom;
    String stringAdult;
    String stringChild;
    public static int yyFromDate ;
    public static int mmFromDate ;
    public static int ddFromDate ;

    public static int yyToDate ;
    public static int mmToDate ;
    public static int ddToDate;

    Context context;

    ProgressDialog pDialog;

    DateSessionAdapter adapter;

    String strSession="";
    String strSessionId="";
    String type="";
    String strApiKey="";

    MonthSessionResponse.MonthSession monthSession[];
    ArrayList<MonthSessionResponse.MonthSession> sessionArrayList;

    MyBusinessResponse.MyBusiness myBusiness[];
    ArrayList< MyBusinessResponse.MyBusiness> businessArrayList;
    //Empty constructor
    public MyBusinessFragment(){
        //
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.mybusiness_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            context = getActivity();
            txtViewMemName=(TextView)v.findViewById(R.id.mybusiness_frag_txtview_membername);
            txtViewMemId=(TextView)v.findViewById(R.id.mybusiness_frag_txtview_memberId);
            txtViewSponserId=(TextView)v.findViewById(R.id.mybusiness_frag_txtview_sponserID);
            txtViewTotal=(TextView)v.findViewById(R.id.mybusiness_frag_txtview_total);
            txtViewLevel=(TextView)v.findViewById(R.id.mybusiness_frag_txtview_level);
            txtViewSelf=(TextView)v.findViewById(R.id.mybusiness_frag_txtview_self);
            txtViewPV=(TextView)v.findViewById(R.id.mybusiness_frag_txtview_prepv);
            txtViewGroup=(TextView)v.findViewById(R.id.mybusiness_frag_txtview_group);
            btnDownline=(MaterialButton) v.findViewById(R.id.mybusiness_frag_btn_downline);
            btnSelf=(Button)v.findViewById(R.id.mybusiness_frag_btn_self);
            layoutBtn=(LinearLayout)v.findViewById(R.id.mybusiness_frag_layout_btn);
            layoutItem=(LinearLayout)v.findViewById(R.id.mybusiness_frag_layout_item);
            layoutNOItem=(LinearLayout)v.findViewById(R.id.mybusiness_frag_layout_noitem);
            layoutSponserId=(LinearLayout)v.findViewById(R.id.mybusiness_frag_layout_sponserId);
            layoutSponserId=(LinearLayout)v.findViewById(R.id.mybusiness_frag_layout_sponserId);
            layoutSession=(LinearLayout)v.findViewById(R.id.mybusiness_frag_layout_session);
            layoutDate=(LinearLayout)v.findViewById(R.id.mybusiness_frag_layout_date);
            linearLayoutFormDate=(LinearLayout)v.findViewById(R.id.mybusiness_frag_layout_fromDate);
            linearLayoutToDate=(LinearLayout)v.findViewById(R.id.mybusiness_frag_layout_toDate);
            spinnerSesson=(Spinner)v.findViewById(R.id.mybusiness_frag_spin_session);
            rdgOption=(RadioGroup)v.findViewById(R.id.mybusiness_frag_rdg_option);
            rdbDate=(RadioButton)v.findViewById(R.id.mybusiness_frag_rdb_date);
            rdbSession=(RadioButton)v.findViewById(R.id.mybusiness_frag_rdb_session);
            textViewFromDate=(TextView)v.findViewById(R.id.mybusiness_frag_txtView_fromDate);
            textViewToDate=(TextView)v.findViewById(R.id.mybusiness_frag_txtView_toDate);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);

            //set current from date
            /*Calendar c1 = Calendar.getInstance();
            SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
           // SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate1 = df1.format(c1.getTime());
           // String formattedDate2 = df2.format(c1.getTime());
            textViewFromDate.setText("01-jan-2018");
            stringFromDate="01-jan-2018";

            //set current to date
            Calendar c2 = Calendar.getInstance();
            SimpleDateFormat dt1 = new SimpleDateFormat("dd-MMM-yyyy");
            //SimpleDateFormat dt2 = new SimpleDateFormat("dd-MMM-yyyy");
            String formatDate1 = dt1.format(c2.getTime());
            //String formatDate2 = dt2.format(c2.getTime());
            textViewToDate.setText(formatDate1);
            stringToDate=formatDate1;*/

              //Check radion button is chekd or not
           /* if(rdbDate.isChecked()){
                type="Date";
                layoutDate.setVisibility(View.VISIBLE);
                layoutSession.setVisibility(View.GONE);

                //call api
                getSelfBusinessDetail();

            }
            else if(rdbSession.isChecked()){
                type="Session";
                layoutDate.setVisibility(View.GONE);
                layoutSession.setVisibility(View.VISIBLE);

                //call api
                getMonthSessionList();
            }*/

           // Radio Group check chenge listner
           /* rdgOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int selectId=rdgOption.getCheckedRadioButtonId();
                    RadioButton rb=(RadioButton)group.findViewById(checkedId);
                    String strRbtnVlaue=rb.getText().toString();
                    if(strRbtnVlaue.equals("Date Wise")){
                        type="Date";

                        layoutDate.setVisibility(View.VISIBLE);
                        layoutSession.setVisibility(View.GONE);

                        //call api
                        getSelfBusinessDetail();
                    }
                    else if(strRbtnVlaue.equals("Session Wise")){
                        type="Session";

                        layoutDate.setVisibility(View.GONE);
                        layoutSession.setVisibility(View.VISIBLE);

                        //call api
                        getMonthSessionList();
                    }
                }
            });*/


            // click event for date picker
           /* linearLayoutFormDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectFromDateFragment();
                    newFragment.show(getActivity().getSupportFragmentManager(), "DatePicker");
                }
            });
            linearLayoutToDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectToDateFragment();
                    newFragment.show(getActivity().getSupportFragmentManager(), "DatePicker");
                }
            });*/


            /*Button Self */
            btnSelf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(textViewFromDate.getText().toString().equals("")){
                        Toast.makeText(context,"Plz enter from date ",Toast.LENGTH_SHORT).show();
                    }
                    else if(textViewToDate.getText().toString().equals("")){
                        Toast.makeText(context,"Plz enter to date ",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        getSelfBusinessDetail();
                    }
                }
            });
            /*Call Month Session Api*/

            if(!ConnectivityUtils.isNetworkEnabled(context)){
                Toast.makeText(context, R.string.network_error,Toast.LENGTH_SHORT).show();
            }
            else {
                getMonthSessionList();
            }


            /*Spinner session item selected listener*/
            spinnerSesson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    MonthSessionResponse.MonthSession monthSession1=(MonthSessionResponse.MonthSession)parent.getItemAtPosition(position);
                    if(monthSession1 != null){
                        strSession=monthSession1.getYear();
                        strSessionId=monthSession1.getSessid();
                        AppConstants.SESSION_ID=strSessionId;

                        if(!ConnectivityUtils.isNetworkEnabled(context)){
                            Snackbar.make(getView(),R.string.initial_network_message,1);
                        }
                        else {
                            //new getSelfBusinessDetails().execute();
                            getSelfBusinessDetail();
                        }

                    }
                    else {

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /*Button Dwonline click listener*/
            btnDownline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(strSessionId.equals("")){
                        Toast.makeText(context,"Plz Select Session ",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        MyBusinessDownFragment fragment=new MyBusinessDownFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("SessionId",strSessionId);
                        bundle.putString("Type",type);
                        bundle.putString("FromDate","");
                        bundle.putString("ToDate","");
                        bundle.putString("MemberID",txtViewMemId.getText().toString());
                        fragment.setArguments(bundle);
                        ((BusinessDashboardActivity)context).replaceFragment_business(fragment,"MyBusinessDown",bundle);

                    }

                   /* if(rdbDate.isChecked()){
                       *//* if(textViewFromDate.getText().toString().equals("")){
                            Toast.makeText(context,"Plz enter from date ",Toast.LENGTH_SHORT).show();
                        }
                        else if(textViewToDate.getText().toString().equals("")){
                            Toast.makeText(context,"Plz enter to date ",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            MyBusinessDownFragment fragment=new MyBusinessDownFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("SessionId","");
                            bundle.putString("FromDate",stringFromDate);
                            bundle.putString("ToDate",stringToDate);
                            bundle.putString("Type",type);
                            bundle.putString("MemberID",txtViewMemId.getText().toString());
                            fragment.setArguments(bundle);
                            ((DashboardActivity)context).replaceFragment(fragment,"MyBusinessDown",bundle);
                        }*//*

                        MyBusinessDownFragment fragment=new MyBusinessDownFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("SessionId","");
                        bundle.putString("FromDate",stringFromDate);
                        bundle.putString("ToDate",stringToDate);
                        bundle.putString("Type",type);
                        bundle.putString("MemberID",txtViewMemId.getText().toString());
                        fragment.setArguments(bundle);
                        ((DashboardActivity)context).replaceFragment(fragment,"MyBusinessDown",bundle);
                    }
                    else {
                        if(strSessionId.equals("")){
                            Toast.makeText(context,"Plz Select Session ",Toast.LENGTH_SHORT).show();
                        }
                        else {

                            MyBusinessDownFragment fragment=new MyBusinessDownFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("SessionId",strSessionId);
                            bundle.putString("Type",type);
                            bundle.putString("FromDate","");
                            bundle.putString("ToDate","");
                            bundle.putString("MemberID",txtViewMemId.getText().toString());
                            fragment.setArguments(bundle);
                            ((DashboardActivity)context).replaceFragment(fragment,"MyBusinessDown",bundle);

                        }
                    }*/

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }



        return v;
    }


    /*Month Session List Api Request and Response*/
    private void getMonthSessionList(){

        showProgressDialog();
        BaseRequest request = new BaseRequest();
        //Set value in Entity class
        request.setReqtype(ApiConstants.REQUEST_MONTH_SESSION);
        request.setPasswd(SharedPrefrence_Business.getPassword(context));
        request.setUserid(SharedPrefrence_Business.getUserID(context));
        request.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        Call<MonthSessionResponse> callMonthSessionList=
                NetworkClient.getInstance(context).create(IncomeServices.class).fetchSessionList(request,strApiKey);

        callMonthSessionList.enqueue(new Callback<MonthSessionResponse>() {
            @Override
            public void onResponse(Call<MonthSessionResponse> call, Response<MonthSessionResponse> response) {
                hideProgressDialog();
                try {

                    MonthSessionResponse Response = new MonthSessionResponse();
                    Response=(MonthSessionResponse)response.body();

                    if (Response.getResponse().equals("OK")) {

                        monthSession=Response.getMonths();
                        if (monthSession.length > 0){

                            sessionArrayList=new ArrayList<MonthSessionResponse.MonthSession>(Arrays.asList(monthSession));

                            adapter=new DateSessionAdapter(context,sessionArrayList);
                            spinnerSesson.setAdapter(adapter);
                        }
                        else {
                            Toast.makeText(context, "No Sessoin Record", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<MonthSessionResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    /*Self Business Detail Api Request and Response*/
    private void getSelfBusinessDetail(){
        showProgressDialog();
        final MyBusinessRequest request = new MyBusinessRequest();
        /*Set value in Entity class*/
        /*if(type.equals("Date")){
            request.setReqtype(ApiConstants.REQUEST_MY_BUSINESS_DATEWISE);
           // downlineDetailRequest.setSessid("");
            request.setStartdate(stringFromDate);
            request.setEnddate(stringToDate);
            request.setMemberid("");
        }
        else {
            request.setReqtype(ApiConstants.REQUEST_MY_BUSINESS);
            request.setSessid(strSessionId);
        }*/
        request.setReqtype(ApiConstants.REQUEST_MY_BUSINESS);
        request.setSessid(strSessionId);

        request.setPasswd(SharedPrefrence_Business.getPassword(context));
        request.setUserid(SharedPrefrence_Business.getUserID(context));
        request.setIslogin(SharedPrefrence_Business.getIsLogin(context));


        Call<MyBusinessResponse> callMybusinessResponse=
                NetworkClient.getInstance(context).create(IncomeServices.class).fetchMyBusinessDetail(request,strApiKey);

        callMybusinessResponse.enqueue(new Callback<MyBusinessResponse>() {
            @Override
            public void onResponse(Call<MyBusinessResponse> call, Response<MyBusinessResponse> response) {
               hideProgressDialog();
                try {

                    MyBusinessResponse Response = new MyBusinessResponse();
                    Response=(MyBusinessResponse)response.body();

                    if (Response.getResponse().equals("OK")) {

                        myBusiness=Response.getSelf();
                        if (myBusiness.length > 0){

                            layoutItem.setVisibility(View.VISIBLE);
                            layoutBtn.setVisibility(View.VISIBLE);
                            layoutNOItem.setVisibility(View.GONE);

                            businessArrayList=new ArrayList<MyBusinessResponse.MyBusiness>(Arrays.asList(myBusiness));

                            for (int i=0; i< businessArrayList.size(); i++){

                                txtViewMemName.setText(businessArrayList.get(i).getMembername());
                                txtViewMemId.setText(businessArrayList.get(i).getIdno());
                                txtViewTotal.setText(businessArrayList.get(i).getTotalbv());
                                txtViewSelf.setText(businessArrayList.get(i).getSelfbv());
                                if(businessArrayList.get(i).getSponsorid().equals("")){
                                    layoutSponserId.setVisibility(View.GONE);
                                }

                                else {
                                    layoutSponserId.setVisibility(View.VISIBLE);
                                    txtViewSponserId.setText(businessArrayList.get(i).getSponsorid());
                                }
                                txtViewGroup.setText(businessArrayList.get(i).getGroupbv());
                                txtViewLevel.setText(businessArrayList.get(i).getLevel());
                                txtViewPV.setText(businessArrayList.get(i).getPreviousbv());
                            }

                        }
                        else {
                            //Toast.makeText(context, "No Self Business Record", Toast.LENGTH_SHORT).show();
                            layoutItem.setVisibility(View.GONE);
                            layoutBtn.setVisibility(View.GONE);
                            layoutNOItem.setVisibility(View.VISIBLE);
                        }

                    }

                    else {
                        Toast.makeText(context, Response.getResponse(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MyBusinessResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
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
                //dpd.getDatePicker().setMinDate(todayDate.getTime());

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
        String Month = Utilities.convertMonth(month);
        textViewFromDate.setText(day + "-" + Month + "-" + year);
        stringFromDate = (year + "-" + Utilities.convertMonthNumber(month) + "-" + Utilities.convertdayNumber(day)).toString();

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
        textViewToDate.setText(dd  + " " + mon + " " + yy);
        stringToDate = (yy + "-" + Utilities.convertMonthNumber(mm) + "-" + Utilities.convertdayNumber(dd)).toString();
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
                //dpd.getDatePicker().setMinDate(todayDate.getTime());

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
        String Month = Utilities.convertMonth(month);
        textViewToDate.setText(day + "-" + Month + "-" + year);
        stringToDate = (year + "-" + Utilities.convertMonthNumber(month) + "-" + Utilities.convertdayNumber(day)).toString();

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

}
