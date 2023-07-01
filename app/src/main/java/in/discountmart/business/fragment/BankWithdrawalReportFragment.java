package in.discountmart.business.fragment;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.base.network.NetworkClient1;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import in.discountmart.R;
import in.discountmart.business.adapter.WithdrawalDetailAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.WalletServices;
import in.discountmart.business.listener.PaginationScrollListener;
import in.discountmart.business.model_business.requestmodel.BaseFromToRequest;
import in.discountmart.business.model_business.responsemodel.WithdrawalDetailResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankWithdrawalReportFragment extends Fragment {

    Context context;

    TableLayout tableLayoutRecord;
    LinearLayout layoutRecord;
    LinearLayout layoutNoRecord;
    LinearLayout layoutTotal;
    TextView txtTotal;
    RecyclerView recyclerView;
    ProgressDialog pDialog;
     int total=0;
    int from =0;
    int to=0;
    private static final int PAGE_START = 1;
    private static final int FROM_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_COUNT = 0;
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;
    View view;

    String strApiKey="";
     WithdrawalDetailResponse.WithdrawalDetail withdrawalDetails[];
     ArrayList<WithdrawalDetailResponse.WithdrawalDetail> DetailArrayList;

     /*Adapter*/
    WithdrawalDetailAdapter adapter;

    //Empty Constructor
    public BankWithdrawalReportFragment(){
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.bank_withdrawal_detail, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        view=getActivity().findViewById(android.R.id.content);

        try {
            tableLayoutRecord=(TableLayout)rootView.findViewById(R.id.bank_withdrawal_detail_frag_tablelayout_record);
            layoutRecord=(LinearLayout)rootView.findViewById(R.id.bank_withdrawal_detail_frag_layout_record);
            recyclerView=(RecyclerView) rootView.findViewById(R.id.bank_withdrawal_detail_frag_recycle);
            txtTotal=(TextView) rootView.findViewById(R.id.bank_withdrawal_detail_frag_txtView_record);
            layoutTotal=(LinearLayout)rootView.findViewById(R.id.bank_withdrawal_detail_frag_layout_total);
            layoutNoRecord=(LinearLayout)rootView.findViewById(R.id.bank_withdrawal_detail_frag_layout_noRecord);
            context = getActivity();

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);

            adapter=new WithdrawalDetailAdapter(context);
            LinearLayoutManager linearLayoutManager= new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(adapter);

            from = 1;
            to = 10;
            if(!ConnectivityUtils.isNetworkEnabled(context)){
                Toast.makeText(context,getString(R.string.network_error),Toast.LENGTH_SHORT).show();
            }
            else {
                // new getWithdrawalReportDetails().execute();
                getBankWithdrawlReport();
            }
            recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
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
                            getBankWithdrawlReport();
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


        return rootView;
    }


   /* @Override
    public void onResume() {
        super.onResume();

    }*/


   /*BankWithdrawlReportDetail Api Request and Response*/
    private void getBankWithdrawlReport(){
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
        BaseFromToRequest Request = new BaseFromToRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_WITHDRAWAL_DETAIL);
        Request.setPasswd(SharedPrefrence_Business.getPassword(context));
        Request.setUserid(SharedPrefrence_Business.getUserID(context));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        Request.setFrom(String.valueOf(from));
        Request.setTo(String.valueOf(to));

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(Request);
        Log.e("ReqWithDrawalReport--->", Get_Paramenter);

        Call<WithdrawalDetailResponse> fundReqDetailResponseCall=
                NetworkClient1.getInstance(context).create(WalletServices.class).fetchWithdrawalDetail(Request,strApiKey);

        fundReqDetailResponseCall.enqueue(new Callback<WithdrawalDetailResponse>() {
            @Override
            public void onResponse(Call<WithdrawalDetailResponse> call, Response<WithdrawalDetailResponse> response) {
                try {
                    if(pDialog.isShowing())
                        pDialog.dismiss();

                    WithdrawalDetailResponse Response = new WithdrawalDetailResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {


                            if(Response.getBankwithdrawldetail()!= null && Response.getBankwithdrawldetail().size() > 0){

                                layoutNoRecord.setVisibility(View.GONE);
                                layoutRecord.setVisibility(View.VISIBLE);
                                layoutTotal.setVisibility(View.VISIBLE);

                                DetailArrayList=new ArrayList<WithdrawalDetailResponse.WithdrawalDetail>();
                                DetailArrayList=Response.getBankwithdrawldetail();
                                total = Integer.parseInt(Response.getRecordcount());
                                txtTotal.setText("Record"+"\n"+String.valueOf(total));
                                //Toast.makeText(context,"Record:" +downlineDetailReeponse.getRecordcount(),Toast.LENGTH_SHORT).show();
                                // fillData(downlineDetails);

                                int totcount= Integer.parseInt(Response.getRecordcount());
                                if(totcount <= 10){
                                    TOTAL_PAGES= 1;
                                    adapter.addAll(DetailArrayList);
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
                                        adapter.addAll(DetailArrayList);

                                        if (currentPage <= TOTAL_PAGES)
                                            adapter.addLoadingFooter();
                                        else
                                            isLastPage = true;
                                    }
                                    else
                                        loadNextPage(DetailArrayList);
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
                            //Toast.makeText(context, downlineDetailReeponse.getResponse(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<WithdrawalDetailResponse> call, Throwable t) {
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

    private void loadNextPage(ArrayList<WithdrawalDetailResponse.WithdrawalDetail> list) {
        Log.d(TAG, "loadNextPage: " + currentPage);
        ArrayList<WithdrawalDetailResponse.WithdrawalDetail> rechargeReports=new ArrayList<WithdrawalDetailResponse.WithdrawalDetail>();
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

    /*public void initTable(final ArrayList<FundReqDetailResponse.FundReqDetail> wallet) {
        //remove all rows if exist already

        tableLayoutRecord.setVisibility(View.VISIBLE);

        tableLayoutRecord.removeAllViews();


        TableRow tbrow0 = new TableRow(context);

        TextView tvh_1 = new TextView(context);

        tvh_1.setText(" ");

        tvh_1.setGravity(Gravity.CENTER);

        tvh_1.setPadding(10, 10, 10, 10);

        tbrow0.addView(tvh_1, TableRow.LayoutParams.WRAP_CONTENT,100);

        *//*Heading Text S No*//*
        TextView tvh_2 = new TextView(context);

        tvh_2.setText("S.No");

        tvh_2.setTextColor(getResources().getColor(R.color.black));

        tvh_2.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_2.setGravity(Gravity.CENTER);

        tvh_2.setPadding(10, 10, 10, 10);

        tvh_2.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_2, TableRow.LayoutParams.WRAP_CONTENT,100);

        *//*Heading Text Req.No*//*
        TextView tvh_3 = new TextView(context);


        tvh_3.setText("Req.Id");

        tvh_3.setTextColor(getResources().getColor(R.color.black));

        tvh_3.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_3.setGravity(Gravity.CENTER);

        tvh_3.setPadding(10, 10, 10, 10);

        tvh_3.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_3, TableRow.LayoutParams.WRAP_CONTENT,100);

        *//*Heading Text Payee Name*//*
        TextView tvh_4 = new TextView(context);

        tvh_4.setText("Payee Name");

        tvh_4.setTextColor(getResources().getColor(R.color.black));

        tvh_4.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_4.setGravity(Gravity.CENTER);

        tvh_4.setPadding(10, 10, 10, 10);

        tvh_4.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_4, TableRow.LayoutParams.WRAP_CONTENT,100);

        *//*Heading Text Account No.*//*
        TextView tvh_5 = new TextView(context);

        tvh_5.setText("Account No.");

        tvh_5.setTextColor(getResources().getColor(R.color.black));

        tvh_5.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_5.setGravity(Gravity.CENTER);

        tvh_5.setPadding(10, 10, 10, 10);

        tvh_5.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_5, TableRow.LayoutParams.WRAP_CONTENT,100);

        *//*Heading Text Bank Name*//*
        TextView tvh_6 = new TextView(context);

        tvh_6.setText("Bank Name");

        tvh_6.setTextColor(getResources().getColor(R.color.black));

        tvh_6.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_6.setGravity(Gravity.CENTER);

        tvh_6.setPadding(10, 10, 10, 10);

        tvh_6.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_6, TableRow.LayoutParams.WRAP_CONTENT,100);

        *//*Heading Text Bank Branch*//*
        TextView tvh_7 = new TextView(context);

        tvh_7.setText("Bank Branch");

        tvh_7.setTextColor(getResources().getColor(R.color.black));

        tvh_7.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_7.setGravity(Gravity.CENTER);

        tvh_7.setPadding(10, 10, 10, 10);

        tvh_7.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_7, TableRow.LayoutParams.WRAP_CONTENT,100);

        *//*Heading Text IFSC Code*//*
        TextView tvh_8 = new TextView(context);

        tvh_8.setText("IFSC Code");

        tvh_8.setTextColor(getResources().getColor(R.color.black));

        tvh_8.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_8.setGravity(Gravity.CENTER);

        tvh_8.setPadding(10, 10, 10, 10);

        tvh_8.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_8, TableRow.LayoutParams.WRAP_CONTENT,100);

        *//*Heading Text RequestAmount*//*
        TextView tvh_9 = new TextView(context);

        tvh_9.setText("RequestAmount");

        tvh_9.setTextColor(getResources().getColor(R.color.black));

        tvh_9.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_9.setGravity(Gravity.CENTER);

        tvh_9.setPadding(10, 10, 10, 10);

        tvh_9.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_9, TableRow.LayoutParams.WRAP_CONTENT,100);

        *//*Heading Text  RequestDate*//*
        TextView tvh_10 = new TextView(context);

        tvh_10.setText("RequestDate");

        tvh_10.setTextColor(getResources().getColor(R.color.black));

        tvh_10.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_10.setGravity(Gravity.CENTER);

        tvh_10.setPadding(10, 10, 10, 10);

        tvh_10.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_10, TableRow.LayoutParams.WRAP_CONTENT,100);

        *//*Heading Text IssueDate*//*
        TextView tvh_11 = new TextView(context);

        tvh_11.setText("IssueDate");

        tvh_11.setTextColor(getResources().getColor(R.color.black));

        tvh_11.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_11.setGravity(Gravity.CENTER);

        tvh_11.setPadding(10, 10, 10, 10);

        tvh_11.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_11, TableRow.LayoutParams.WRAP_CONTENT,100);

        *//*Heading Text Status*//*
        TextView tvh_12 = new TextView(context);

        tvh_12.setText("Status");

        tvh_12.setTextColor(getResources().getColor(R.color.black));

        tvh_12.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_12.setGravity(Gravity.CENTER);

        tvh_12.setPadding(10, 10, 10, 10);

        tvh_12.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_12, TableRow.LayoutParams.WRAP_CONTENT,100);

        tableLayoutRecord.addView(tbrow0);

        *//*Add Data*//*
        for (int i = 0; i < wallet.size(); i++) {

            TableRow tbrow1 = new TableRow(context);

            final int index = i;

            TextView tvd_1 = new TextView(context);
            tvd_1.setText(" ");
            tvd_1.setGravity(Gravity.CENTER);
            tvd_1.setPadding(10, 10, 10, 10);
            tbrow1.addView(tvd_1, TableRow.LayoutParams.WRAP_CONTENT,80);

            *//*Serial Numaber*//*
            try{
                TextView tvd_2 = new TextView(context);
                tvd_2.setEllipsize(TextUtils.TruncateAt.END);
                tvd_2.setGravity(Gravity.CENTER| Gravity.LEFT);
                tvd_2.setBackgroundResource(R.drawable.white_bg_box_black_border);
                tvd_2.setPadding(10, 10, 10, 10);
                //tvd_2.setText(index);
                tvd_2.setText(String.valueOf(index+1));
                RelativeLayout.LayoutParams layoutParams2=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 80);
                //layoutParams1.=Gravity.CENTER;
                layoutParams2.addRule(RelativeLayout.ALIGN_LEFT,tvd_2.getId() );
                //tvd_2.setText(wallet.get(i).getAcno());
                tbrow1.addView(tvd_2, TableRow.LayoutParams.WRAP_CONTENT,80);
            }catch (Exception e){
                e.printStackTrace();
            }




            *//*Request Id*//*
            TextView tvd_3 = new TextView(context);

            tvd_3.setEllipsize(TextUtils.TruncateAt.END);

            tvd_3.setTextColor(Color.BLACK);

            tvd_3.setBackgroundResource(R.drawable.white_bg_box_black_border);

            tvd_3.setGravity(Gravity.CENTER);

            tvd_3.setPadding(10, 10, 10, 10);
            tvd_3.setText(wallet.get(i).getReqid());

            tbrow1.addView(tvd_3, TableRow.LayoutParams.WRAP_CONTENT,80);

            *//*Payee Name*//*

            TextView tvd_4 = new TextView(context);

            tvd_4.setEllipsize(TextUtils.TruncateAt.END);

            tvd_4.setTextColor(Color.BLACK);

            tvd_4.setBackgroundResource(R.drawable.white_bg_box_black_border);

            tvd_4.setGravity(Gravity.CENTER);

            tvd_4.setPadding(10, 10, 10, 10);


            //SpannableString spanString = new SpannableString(compList[i].getStatus());

            //spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);

            //spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);

            tvd_4.setText(wallet.get(i).getPayeename());

            tbrow1.addView(tvd_4, TableRow.LayoutParams.WRAP_CONTENT,80);


            *//*Account No*//*

            TextView tvd_5= new TextView(context);

            tvd_5.setEllipsize(TextUtils.TruncateAt.END);

            tvd_5.setBackgroundResource(R.drawable.white_bg_box_black_border);
            //tv14.setBackgroundColor(getResources().getColor(R.color.gray));

            tvd_5.setGravity(Gravity.CENTER);

            tvd_5.setPadding(10, 10, 10, 10);

            tvd_5.setText(wallet.get(i).getAcno());

            tvd_5.setTextColor(Color.BLACK);

            tbrow1.addView(tvd_5, TableRow.LayoutParams.WRAP_CONTENT,80);

            *//*Bank NAme*//*

            TextView tvd_6= new TextView(context);

            tvd_6.setEllipsize(TextUtils.TruncateAt.END);

            // tv12.setMaxLines(1);

            tvd_6.setTextColor(Color.BLACK);
            tvd_6.setBackgroundResource(R.drawable.white_bg_box_black_border);
            //tv14.setBackgroundColor(getResources().getColor(R.color.gray));

            tvd_6.setGravity(Gravity.CENTER | Gravity.LEFT);

            tvd_6.setPadding(10, 10, 10, 10);

            tvd_6.setText(wallet.get(i).getBankname());
            RelativeLayout.LayoutParams layoutParams6=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 80);
            //layoutParams1.=Gravity.CENTER;
            layoutParams6.addRule(RelativeLayout.ALIGN_LEFT,tvd_6.getId() );

            tbrow1.addView(tvd_6, TableRow.LayoutParams.WRAP_CONTENT,80);

            *//* Bank Branch*//*

            TextView tvd_7= new TextView(context);

            tvd_7.setEllipsize(TextUtils.TruncateAt.END);

            tvd_7.setBackgroundResource(R.drawable.white_bg_box_black_border);
            //tv14.setBackgroundColor(getResources().getColor(R.color.gray));
            tvd_7.setPadding(10, 10, 10, 10);

            tvd_7.setGravity(Gravity.CENTER);
            tvd_7.setText(wallet.get(i).getBranch());

            tvd_7.setTextColor(Color.BLACK);

            tbrow1.addView(tvd_7, TableRow.LayoutParams.WRAP_CONTENT,80);

            *//* Ifsc Code*//*

            TextView tvd_8= new TextView(context);

            tvd_8.setEllipsize(TextUtils.TruncateAt.END);
            tvd_8.setBackgroundResource(R.drawable.white_bg_box_black_border);
            //tv14.setBackgroundColor(getResources().getColor(R.color.gray));

            tvd_8.setGravity(Gravity.CENTER);

            tvd_8.setPadding(10, 10, 10, 10);
            tvd_8.setTextColor(Color.BLACK);

            tvd_8.setText(wallet.get(i).getIfsccode());

            tbrow1.addView(tvd_8, TableRow.LayoutParams.WRAP_CONTENT,80);

            *//* Request Amount*//*

            TextView tvd_9= new TextView(context);

            tvd_9.setEllipsize(TextUtils.TruncateAt.END);
            tvd_9.setBackgroundResource(R.drawable.white_bg_box_black_border);
            //tv14.setBackgroundColor(getResources().getColor(R.color.gray));

            tvd_9.setGravity(Gravity.CENTER | Gravity.RIGHT);

            tvd_9.setPadding(10, 10, 10, 10);
            tvd_9.setText(wallet.get(i).getRequestamount());

            tvd_9.setTextColor(Color.BLACK);
            RelativeLayout.LayoutParams layoutParams9=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 80);
            //layoutParams1.=Gravity.CENTER;
            layoutParams9.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,tvd_9.getId() );


            tbrow1.addView(tvd_9, TableRow.LayoutParams.WRAP_CONTENT,80);

            *//* Request Date*//*

            TextView tvd_10= new TextView(context);

            tvd_10.setEllipsize(TextUtils.TruncateAt.END);
            tvd_10.setBackgroundResource(R.drawable.white_bg_box_black_border);

            tvd_10.setGravity(Gravity.CENTER | Gravity.LEFT);

            tvd_10.setPadding(10, 10, 10, 10);

            tvd_10.setText(wallet.get(i).getReqdate());

            tvd_10.setTextColor(Color.BLACK);
            RelativeLayout.LayoutParams layoutParams10=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 80);
            //layoutParams1.=Gravity.CENTER;

            layoutParams10.addRule(RelativeLayout.ALIGN_LEFT,tvd_10.getId() );

            tbrow1.addView(tvd_10, TableRow.LayoutParams.WRAP_CONTENT,80);

            *//* Issue Date*//*

            TextView tvd_11= new TextView(context);

            tvd_11.setEllipsize(TextUtils.TruncateAt.END);

            // tv12.setMaxLines(1);

            tvd_11.setBackgroundResource(R.drawable.white_bg_box_black_border);

            tvd_11.setGravity(Gravity.CENTER | Gravity.LEFT);

            tvd_11.setPadding(10, 10, 10, 10);
            tvd_11.setText(wallet.get(i).getIssueddate());

            tvd_11.setTextColor(Color.BLACK);
            RelativeLayout.LayoutParams layoutParams11=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 80);
            //layoutParams1.=Gravity.CENTER;
            layoutParams11.addRule(RelativeLayout.ALIGN_LEFT,tvd_11.getId() );
            tbrow1.addView(tvd_11, TableRow.LayoutParams.WRAP_CONTENT,80);

            *//* Status*//*

            TextView tvd_12= new TextView(context);

            tvd_12.setEllipsize(TextUtils.TruncateAt.END);

            tvd_12.setBackgroundResource(R.drawable.white_bg_box_black_border);
            //tv14.setBackgroundColor(getResources().getColor(R.color.gray));
            tvd_12.setGravity(Gravity.CENTER | Gravity.LEFT);

            tvd_12.setPadding(10, 10, 10, 10);

            tvd_12.setText(wallet.get(i).getStatus());

            tvd_12.setTextColor(Color.BLACK);
            RelativeLayout.LayoutParams layoutParams12=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 80);
            //layoutParams1.=Gravity.CENTER;
            layoutParams12.addRule(RelativeLayout.ALIGN_LEFT,tvd_12.getId() );
            tbrow1.addView(tvd_12, TableRow.LayoutParams.WRAP_CONTENT,80);
            tableLayoutRecord.addView(tbrow1);


        }







    }*/
}
