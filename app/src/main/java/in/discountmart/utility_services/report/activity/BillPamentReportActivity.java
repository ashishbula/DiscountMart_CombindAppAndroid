package in.discountmart.utility_services.report.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.listener.PaginationScrollListener;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.report.report_adapter.BillPaymentReportAdapter;
import in.discountmart.utility_services.report.report_api.ReportApi;
import in.discountmart.utility_services.report.report_model.request.BillPaymentReportRequest;
import in.discountmart.utility_services.report.report_model.response.BillPaymentReportResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.utilities.DividerItemDecoration;
import in.discountmart.utility_services.utilities.TokenBase64;
import in.discountmart.utility_services.utilities.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillPamentReportActivity extends AppCompatActivity implements BillPaymentReportAdapter.BillPaymentReportListener {
    RadioGroup rdgOption;
    RadioButton rdbAll;
    RadioButton rdbPrepaid;
    RadioButton rdbPostpaid;
    RadioButton rdbDth;
    public static EditText edTxtForm;
    public static EditText edTxtTo;
    RecyclerView recyclerList;
    AppCompatSpinner spinnerService;
    View view;

    String serviceType="";
    String serviceName="";
    String serviceId="";
    public static String fromDate="";
    public static String toDate="";
    public static int yyFromDate ;
    public static int mmFromDate ;
    public static int ddFromDate ;
    boolean valid;

    private static final String TAG = "BillPayReportActivity";

    BillPaymentReportAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    ProgressDialog progressDialog;

    private static final int PAGE_START = 0;
    private boolean isLoading ;
    private boolean isLastPage;
    private int TOTAL_PAGES;
    private int currentPage;
    public static String type="";
    String msg="";
    /*Model Object*/
    ArrayList<BillPaymentReportResponse.BillPayment> reportArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bill_pament_report);
        view=findViewById(android.R.id.content);
        try {
            edTxtForm=(EditText)findViewById(R.id.billpay_report_act_edtxt_from);
            edTxtTo=(EditText)findViewById(R.id.billpay_report_act_edtxt_to);
           spinnerService=(AppCompatSpinner)findViewById(R.id.billpay_report_act_spinner_services);
            recyclerList=(RecyclerView)findViewById(R.id.billpay_report_act_recycler_content);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Bill Payment Report");

            adapter = new BillPaymentReportAdapter(this,this);

            linearLayoutManager= new LinearLayoutManager(getApplicationContext());
            recyclerList.setLayoutManager(linearLayoutManager);
            recyclerList.setItemAnimator(new DefaultItemAnimator());
            recyclerList.addItemDecoration(new DividerItemDecoration(BillPamentReportActivity.this,DividerItemDecoration.VERTICAL_LIST,0));
            recyclerList.setAdapter(adapter);

            /*Select From date and to Date*/
            //Edit Text From date on click
            edTxtForm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectFromDateFragment();
                    newFragment.show(getSupportFragmentManager(), "DatePicker");
                }
            });

            //Edit Text To date on click
            edTxtTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectToDateFragment();
                    newFragment.show(getSupportFragmentManager(), "DatePicker");
                }
            });

            /*Edit text From date change listener*/
            edTxtForm.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(!s.toString().contentEquals("")){
                        if(edTxtTo.getText().toString().contentEquals(""))
                            Toast.makeText(BillPamentReportActivity.this,"Please select to date",Toast.LENGTH_SHORT).show();
                        else {
                            TOTAL_PAGES=1;
                            currentPage=TOTAL_PAGES;
                            isLastPage = false;
                            isLoading=false;
                            reportArrayList=new ArrayList<BillPaymentReportResponse.BillPayment>();
                            if(adapter.getItemCount() > 0)
                                adapter.clear();
                            recyclerList.setAdapter(adapter);
                            // mocking network delay for API call
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(chekcValidation())
                                        if(chekcValidation()){
                                            getBillPayReport();
                                        }
                                        else
                                            Toast.makeText(BillPamentReportActivity.this,msg,Toast.LENGTH_SHORT).show();

                                }
                            }, 500);
                        }
                    }
                    else {
                        Toast.makeText(BillPamentReportActivity.this,"Please select to date",Toast.LENGTH_SHORT).show();

                    }

                }
            });
            /*Edit text To date change listener*/
            edTxtTo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(!s.toString().contentEquals("")){
                        if(edTxtForm.getText().toString().contentEquals(""))
                            Toast.makeText(BillPamentReportActivity.this,"Please select from date",Toast.LENGTH_SHORT).show();
                        else {
                            TOTAL_PAGES=1;
                            currentPage=TOTAL_PAGES;
                            isLastPage = false;
                            isLoading=false;
                            reportArrayList=new ArrayList<BillPaymentReportResponse.BillPayment>();
                            if(adapter.getItemCount() > 0)
                                adapter.clear();
                            recyclerList.setAdapter(adapter);
                            // mocking network delay for API call
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(chekcValidation())
                                        if(chekcValidation()){
                                            getBillPayReport();
                                        }
                                        else
                                            Toast.makeText(BillPamentReportActivity.this,msg,Toast.LENGTH_SHORT).show();

                                }
                            }, 500);
                        }
                    }
                    else {
                        Toast.makeText(BillPamentReportActivity.this,"Please select from date",Toast.LENGTH_SHORT).show();

                    }

                }
            });

            /*Call spinner */
            initServiceSpinner();
            spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    serviceType = parent.getItemAtPosition(position).toString();
                    if(serviceType.contentEquals("All")){
                        serviceId="0";
                        serviceName="All";
                    }
                    else if(serviceType.contentEquals("Gas Bill")){
                        serviceId="2";
                        serviceName="Gas";
                    }

                    else if(serviceType.contentEquals("Insurance Bill")){
                        serviceId="3";
                        serviceName="Insurance";
                    }

                    else if(serviceType.contentEquals("Electricity Bill")){
                        serviceId="1";
                        serviceName="Electricity";
                    }

                    else if(serviceType.contentEquals("Credit Card Bill")){
                        serviceId="4";
                        serviceName="Credit";
                    }


                    TOTAL_PAGES=1;
                    currentPage=TOTAL_PAGES;
                    isLastPage = false;
                    isLoading=false;
                    reportArrayList=new ArrayList<BillPaymentReportResponse.BillPayment>();
                    if(adapter.getItemCount() > 0)
                        adapter.clear();
                    recyclerList.setAdapter(adapter);
                    // mocking network delay for API call
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(chekcValidation())
                                if(chekcValidation()){
                                    getBillPayReport();
                                }
                                else
                                    Toast.makeText(BillPamentReportActivity.this,msg,Toast.LENGTH_SHORT).show();

                        }
                    }, 500);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /*Recycler on scroll listener*/
            recyclerList.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
                @Override
                protected void loadMoreItems() {
                    isLoading = true;
                    currentPage += 1;

                    // mocking network delay for API call
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getBillPayReport();
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



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(BillPaymentReportResponse.BillPayment list) {
        try {
            if(list != null){
                if(!list.getUserStatus().contentEquals("SUCCESS")){
                    Toast.makeText(BillPamentReportActivity.this,"Transaction is "+list.getUserStatus(),Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent=new Intent(BillPamentReportActivity.this, BillPayReportDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.clear();
                    bundle.putSerializable("BillPay",list);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Initialize Spinner Account Type*/
    public void initServiceSpinner(){

        ArrayList<String> serviceList=new ArrayList<String>();
        serviceList.add("All");
        serviceList.add("Insurance Bill");
        serviceList.add("Gas Bill");
        serviceList.add("Electricity Bill");
        serviceList.add("Credit Card Bill");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, serviceList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerService.setAdapter(dataAdapter);

    }

    // date picker open for CheckInDate-Date and selected date set to edittext
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
            SetFromDate (yy, mm +1, dd);
        }
    }

    public static void SetFromDate(int year, int month, int day) {
        String Month = Utilities.convertMonth(month);
        String Day=Utilities.ConvertNumberIntoTwoDigit(String.valueOf(day));
        edTxtForm.setText(day + "-" + Month + "-" + year);
        fromDate=year+ "-" + Utilities.convertMonthNumber(month)+ "-" +Day;


        yyFromDate = year;
        mmFromDate = month;
        ddFromDate = day;

        Calendar mCalendarTo = Calendar.getInstance();
        mCalendarTo.set(yyFromDate, mmFromDate, ddFromDate);
        mCalendarTo.add(Calendar.DATE, 1);
        int yy = mCalendarTo.get(Calendar.YEAR);
        int mm = mCalendarTo.get(Calendar.MONTH);
        int dd = mCalendarTo.get(Calendar.DAY_OF_MONTH);
        String mon = Utilities.convertMonth(mm);
        String strMonth=Utilities.convertMonthNumber(mm);

        //stringToDate = (yy + "-" + Utilities.convertMonthNumber(mm) + "-" + Utilities.convertdayNumber(dd)).toString();
    }

    // date picker open for CheckOutDate and selected date set to edittext
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
        String Month = Utilities.convertMonth(month);
        String Day=Utilities.ConvertNumberIntoTwoDigit(String.valueOf(day));
        edTxtTo.setText(day + "-" + Month + "-" + year);
        toDate=year+ "-" + Utilities.convertMonthNumber(month)+ "-" +Day;

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
        //textViewToDate.setText(dd  + "-" + mon + "-" + yy);
        //stringToDate = (yy + "-" + Utilities.convertMonthNumber(mm) + "-" + Utilities.convertdayNumber(dd)).toString();
    }


    /* Request and Response Bus Search*/
    public void getBillPayReport(){
        try {

            if(currentPage == 1)
            {
                progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("Please wait");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
            else {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
            }

            String strApiRequest="";
            JSONObject object=null;
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(this).getLoggedinUser();
            String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);
            String formNo= loginResponse.getFormNo().substring(0,loginResponse.getFormNo().length()-2);

            String strToken= TokenBase64.getToken();

            /*Main Request Model*/
            ApiRequest apiRequest = new ApiRequest();

            try {

                /*Base Request Model*/
                BaseHeaderRequest headerRequest =new BaseHeaderRequest();
                headerRequest.setUserName(  new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
                headerRequest.setPassword( new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
                headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/

                /* Flight Search Request Model*/

                BillPaymentReportRequest request=new BillPaymentReportRequest();
                request.setFormNo(formNo);
                request.setFromDate(fromDate);
                request.setToDate(toDate);
                request.setPageIndex(String.valueOf(currentPage));
                request.setPageSize("10");
                request.setRecordCount("");
                request.setServiceType(serviceId);

                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(request);

                strApiRequest=new Gson().toJson(apiRequest);

                Log.e("Value",strApiRequest);
            }catch (Exception e){
                e.printStackTrace();
            }

            Call<BaseResponse> callReportDetail=
                    NetworkClient_Utility_1.getInstance(this).create(ReportApi.class).fetchBillPaymentReport(apiRequest,strToken);


            callReportDetail.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    try {

                        BaseResponse Response =new BaseResponse();
                        Response=response.body();

                        if(Response != null){
                            if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                if(Response.getRESP_VALUE().equals("") || Response.getRESP_VALUE().isEmpty()){
                                    String toast= Response.getRESP_MSG();
                                    //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                                    Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                            .show();
                                }
                                else if(!(Response.getRESP_VALUE()==null) ||  !(Response.getRESP_VALUE().equals(""))){

                                    BillPaymentReportResponse reportResponse = new Gson().fromJson(Response.getRESP_VALUE(),BillPaymentReportResponse.class);

                                    reportArrayList=reportResponse.getBillList();
                                    if(reportArrayList.size() > 0){

                                        int totcount= Integer.parseInt(reportResponse.getTotalCount());
                                        if(totcount <= 10){
                                            TOTAL_PAGES= 1;
                                            adapter.addAll(reportArrayList,serviceName);
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
                                                adapter.addAll(reportArrayList,serviceName);

                                                if (currentPage <= TOTAL_PAGES)
                                                    adapter.addLoadingFooter();
                                                else
                                                    isLastPage = true;
                                            }
                                            else
                                                loadNextPage(reportArrayList,serviceName);
                                        }

                                    }

                                    else {
                                        //adapter.removeLoadingFooter();
                                        isLastPage = true;
                                        isLoading = false;
                                        String toast= "record not found..";
                                        // Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
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

                            }
                            else if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR"))  {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                        .show();

                            }
                            else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                Toast.makeText(BillPamentReportActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(BillPamentReportActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(BillPamentReportActivity.this, toast, Toast.LENGTH_SHORT).show();
                                // showSnackbar(toast);

                            }
                        }
                        else {
                            //Toast.makeText(context,"something error may be server / other",Toast.LENGTH_SHORT).show();

                            String toast= "something wrong";
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
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
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

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadFirstPage(ArrayList<BillPaymentReportResponse.BillPayment> list) {
        Log.d(TAG, "loadFirstPage: ");
        ArrayList<BillPaymentReportResponse.BillPayment> rechargeReports=new ArrayList<BillPaymentReportResponse.BillPayment>();
        rechargeReports=list;
        //progressBar.setVisibility(View.GONE);
        adapter.addAll(rechargeReports,serviceName);

        if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
        else isLastPage = true;

    }

    private void loadNextPage(ArrayList<BillPaymentReportResponse.BillPayment> list,String type) {
        Log.d(TAG, "loadNextPage: " + currentPage);
        ArrayList<BillPaymentReportResponse.BillPayment> rechargeReports=new ArrayList<BillPaymentReportResponse.BillPayment>();
        rechargeReports=list;
        adapter.removeLoadingFooter();
        isLoading = false;

        adapter.addAll(rechargeReports,type);

        if (currentPage != TOTAL_PAGES)
            adapter.addLoadingFooter();
        else{
            isLastPage = true;
            Toast.makeText(BillPamentReportActivity.this,"No more record available",Toast.LENGTH_SHORT).show();
        }


    }

    public boolean chekcValidation(){
        if(edTxtForm.getText().toString().contentEquals("") &&
                edTxtTo.getText().toString().contentEquals("")){
            valid= true;
        }
        else if(!edTxtForm.getText().toString().contentEquals("") &&
                edTxtTo.getText().toString().contentEquals("")) {
            msg="Select to date";
            //Toast.makeText(RechargeReportActivity.this,"Select to date",Toast.LENGTH_SHORT).show();
            valid= false;
        }
        else if(edTxtForm.getText().toString().contentEquals("") &&
                !edTxtTo.getText().toString().contentEquals("")) {
            msg="Select from date";
            // Toast.makeText(RechargeReportActivity.this,"Select from date",Toast.LENGTH_SHORT).show();
            valid= false;
        }

        else if(!edTxtForm.getText().toString().contentEquals("") &&
                !edTxtTo.getText().toString().contentEquals("")) {
            valid= true;
        }
        return valid;
    }

    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        return true;
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {

            if(progressDialog.isShowing())
                progressDialog.dismiss();

            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
       /* if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }*/
        super.onBackPressed();
        finish();
        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }
}
