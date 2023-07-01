package in.discountmart.business.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import in.base.network.NetworkClient1;
import in.base.ui.BaseFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.activity.CommonReportActivity;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.MyTeamService;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.responsemodel.LevelWiseCountResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LevelWiseFragment extends BaseFragment {

    TableLayout tableLayoutWalletRecord;
    ProgressDialog pDialog;

    TextView textViewDepositBal;
    TextView textViewUsedBal;
    TextView textViewMainBal;
    TextView textViewNext;
    Button btnWalletReq;


    LinearLayout linearLayoutNext;
    LinearLayout linearLayoutNoRecord;
    LinearLayout linearLayoutTable;
    int from;
    int to;
    int totalRecord;
    String strApiKey="";
    ArrayList<LevelWiseCountResponse.LevelWiseCount> levelWiseList;
    LevelWiseCountResponse.LevelWiseCount[] levelWiseCounts;
    Context context;
    View view;

    /*Constructor*/
    public LevelWiseFragment(){

        //empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = null;
        try {
            v = inflater.inflate(R.layout.business_level_wise_fragment, container, false);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            tableLayoutWalletRecord = (TableLayout) v.findViewById(R.id.level_wise_frag_tablelayout_record);
            context = getActivity();
            view=getActivity().findViewById(android.R.id.content);
            //textViewDepositBal = (TextView) v.findViewById(R.id.shopping_wallet_report_frag_textView_deposit);
            //textViewUsedBal = (TextView) v.findViewById(R.id.shopping_wallet_report_frag_textView_used);
            //textViewMainBal = (TextView) v.findViewById(R.id.shopping_wallet_report_frag_textView_bal);
            //textViewNext = (TextView) v.findViewById(R.id.shopping_wallet_report_frag_textView_next);
            //btnWalletReq = (Button) v.findViewById(R.id.shopping_wallet_report_frag_btn_walletreq);
            //linearLayoutNext = (LinearLayout) v.findViewById(R.id.shopping_wallet_report_frag_layout_next);
            linearLayoutNoRecord = (LinearLayout) v.findViewById(R.id.level_wise_frag_layout_noRecord);
            linearLayoutTable = (LinearLayout) v.findViewById(R.id.level_wise_frag_layout_table);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);


            //Call LevelWiseCount  APi
            if (!ConnectivityUtils.isNetworkEnabled(context)) {
                Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            } else {
                getLevelWiseCount();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

       /*tableLayoutAchivedRecord.setVisibility(View.VISIBLE);

       if(tableLayoutAchivedRecord != null){
           tableLayoutAchivedRecord.removeAllViews();
           initTable(achivedRecognition);
       }*/

    }

    /* Get LevelWiseCount Report Api*/
    public void getLevelWiseCount(){
        showProgressDialog();
        BaseRequest request = new BaseRequest();

        /*Pos Method*/
        String Get_Paramenter = "";
        try {

            /*Set value in Entity class*/
            request.setReqtype(ApiConstants.REQUEST_LEVEL_REPORT_COUNT);
            request.setPasswd(SharedPrefrence_Business.getPassword(context));
            request.setUserid(SharedPrefrence_Business.getUserID(context));
            request.setIslogin(SharedPrefrence_Business.getIsLogin(context));
            //mainWalletReportRequest.setFrom(String.valueOf(from));
            //mainWalletReportRequest.setTo(String.valueOf(to));

            //1. Convert object to JSON string
            Gson gson = new Gson();
            Get_Paramenter = gson.toJson(request);
            Log.e("ReqShopWalletReport:", Get_Paramenter);

        } catch (Exception e) {
        }

        Call<LevelWiseCountResponse> walletReportResponseCall=
                NetworkClient1.getInstance(context).create(MyTeamService.class).fetchLevelWiseCount(request,strApiKey);

        walletReportResponseCall.enqueue(new Callback<LevelWiseCountResponse>() {
            @Override
            public void onResponse(Call<LevelWiseCountResponse> call, Response<LevelWiseCountResponse> response) {
                hideProgressDialog();
                try {

                    //string = jsonObject.toString();
                    LevelWiseCountResponse Response = new  LevelWiseCountResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {
                        if(Response.getLevelReportCountWise() != null && Response.getLevelReportCountWise().size() > 0){
                            levelWiseList=new ArrayList<LevelWiseCountResponse.LevelWiseCount>();
                            levelWiseList= Response.getLevelReportCountWise();
                            //linearLayoutNext.setVisibility(View.VISIBLE);
                            linearLayoutNoRecord.setVisibility(View.GONE);
                            linearLayoutTable.setVisibility(View.VISIBLE);
                            initTable(levelWiseList);
                        }
                        else {
                           // Toast.makeText(context, "No Shopping Wallet Report Record Found", Toast.LENGTH_SHORT).show();
                            //linearLayoutNext.setVisibility(View.GONE);
                            linearLayoutNoRecord.setVisibility(View.VISIBLE);
                            linearLayoutTable.setVisibility(View.GONE);
                            levelWiseList=new ArrayList<LevelWiseCountResponse.LevelWiseCount>();
                            levelWiseList= Response.getLevelReportCountWise();
                            levelWiseList.clear();
                            initTable(levelWiseList);
                        }
                    }

                    else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                        new BusinessDashboardActivity().blankValueFromSharePreference(context,Response.getMsg());
                    }
                    else if(Response.getResponse().equals("FAILED")) {
                        String msg=Response.getResponse()+ " "+"Something went wrong..";
                        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();

                    }
                    else {
                        Toast.makeText(context, Response.getResponse(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LevelWiseCountResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void initTable( ArrayList<LevelWiseCountResponse.LevelWiseCount> walletDetails) {
        //remove all rows if exist already

        tableLayoutWalletRecord.setVisibility(View.VISIBLE);

        tableLayoutWalletRecord.removeAllViews();

        TableRow tbrow0 = new TableRow(context);
        TextView tvh_1 = new TextView(context);

        tvh_1.setText(" ");
        tvh_1.setGravity(Gravity.CENTER);
        tvh_1.setPadding(10, 10, 10, 10);
        tbrow0.addView(tvh_1);

        /*Heading Text S No*/
        TextView tvh_2 = new TextView(context);
        tvh_2.setText("S.No\n ");

        tvh_2.setTextColor(getResources().getColor(R.color.black));
        tvh_2.setBackgroundResource(R.drawable.white_bg_box_black_border);
        tvh_2.setGravity(Gravity.CENTER);
        tvh_2.setPadding(10, 10, 10, 10);
        tvh_2.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tvh_2);

        /*Heading Text Date*/
        TextView tvh_3 = new TextView(context);
        tvh_3.setText("Level\n ");
        tvh_3.setTextColor(getResources().getColor(R.color.black));
        tvh_3.setBackgroundResource(R.drawable.white_bg_box_black_border);
        tvh_3.setGravity(Gravity.CENTER);
        tvh_3.setPadding(10, 10, 10, 10);
        tvh_3.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tvh_3);

        /*Heading Text Active Member*/
        TextView tvh_4 = new TextView(context);
        tvh_4.setText("Active \n Member");
        tvh_4.setTextColor(getResources().getColor(R.color.black));
        tvh_4.setBackgroundResource(R.drawable.white_bg_box_black_border);
        tvh_4.setGravity(Gravity.CENTER);
        tvh_4.setPadding(10, 10, 10, 10);
        tvh_4.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tvh_4);

        /*Heading Text Active Member*/
        TextView tvh_5 = new TextView(context);
        tvh_5.setText("De-Active\nMember");
        tvh_5.setTextColor(getResources().getColor(R.color.black));
        tvh_5.setBackgroundResource(R.drawable.white_bg_box_black_border);
        tvh_5.setGravity(Gravity.CENTER);
        tvh_5.setPadding(10, 10, 10, 10);
        tvh_5.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tvh_5);

        /*Heading Text Used*/
        TextView tvh_6 = new TextView(context);
        tvh_6.setText("Total\nMember");
        tvh_6.setTextColor(getResources().getColor(R.color.black));
        tvh_6.setBackgroundResource(R.drawable.white_bg_box_black_border);
        tvh_6.setGravity(Gravity.CENTER);
        tvh_6.setPadding(10, 10, 10, 10);
        tvh_6.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tvh_6);

        tableLayoutWalletRecord.addView(tbrow0);

        /*Add Data*/
        for (int i = 0; i < walletDetails.size(); i++) {

            TableRow tbrow1 = new TableRow(context);

            final int index = i;

            TextView tvd_1 = new TextView(context);
            //tvd_1.setHeight(100);
            tvd_1.setText(" ");

            tvd_1.setGravity(Gravity.CENTER);
            tvd_1.setPadding(10, 10, 10, 10);
            tbrow1.addView(tvd_1);

            /*Serial Numaber*/
            try{
                TextView tvd_2 = new TextView(context);
                tvd_2.setEllipsize(TextUtils.TruncateAt.END);
                tvd_2.setGravity(Gravity.CENTER);

                //tvd_2.setText(index);
                tvd_2.setBackgroundResource(R.drawable.white_bg_box_black_border);
                tvd_2.setPadding(10, 10, 10, 10);
                //tvd_2.setText(String.valueOf(index+1));
                tvd_2.setText(walletDetails.get(i).getSno());

                tbrow1.addView(tvd_2);
                }catch (Exception e){
                    e.printStackTrace();
                }



            /*Set Level*/
            TextView tvd_3 = new TextView(context);
            tvd_3.setEllipsize(TextUtils.TruncateAt.END);
            // tv12.setMaxLines(1);
            tvd_3.setText(walletDetails.get(i).getMlevel());
            tvd_3.setTextColor(Color.BLACK);
            tvd_3.setBackgroundResource(R.drawable.white_bg_box_black_border);
            tvd_3.setGravity(Gravity.LEFT);
            tvd_3.setPadding(10, 10, 10, 10);
            tbrow1.addView(tvd_3);

            /* Active Member*/
            TextView tvd_4 = new TextView(context);
            tvd_4.setEllipsize(TextUtils.TruncateAt.END);
            // tv12.setMaxLines(1);
            //tv13.setText(compList[i].getStatus());
            tvd_4.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvd_4.setBackgroundResource(R.drawable.white_bg_box_black_border);
            tvd_4.setGravity(Gravity.CENTER);
            tvd_4.setPadding(10, 10, 10, 10);
            tvd_4.setPaintFlags(tvd_4.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            //SpannableString spanString = new SpannableString(compList[i].getStatus());
            //spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
            //spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            tvd_4.setText(walletDetails.get(i).getActivecount());
            tbrow1.addView(tvd_4);

            /* De-Active Member*/
            TextView tvd_5= new TextView(context);
            tvd_5.setEllipsize(TextUtils.TruncateAt.END);
            // tv12.setMaxLines(1);
            tvd_5.setText(walletDetails.get(i).getDeactivecount());
            tvd_5.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvd_5.setBackgroundResource(R.drawable.white_bg_box_black_border);
            //tv14.setBackgroundColor(getResources().getColor(R.color.gray));
            tvd_5.setGravity(Gravity.CENTER );
            tvd_5.setPadding(10, 10, 10, 10);
            tvd_5.setPaintFlags(tvd_4.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            tbrow1.addView(tvd_5);

            /* Total Member*/
            TextView tvd_6= new TextView(context);
            tvd_6.setEllipsize(TextUtils.TruncateAt.END);
            // tv12.setMaxLines(1);
            tvd_6.setText(walletDetails.get(i).getTotal());
            tvd_6.setTextColor(Color.BLACK);
            tvd_6.setBackgroundResource(R.drawable.white_bg_box_black_border);
            //tv14.setBackgroundColor(getResources().getColor(R.color.gray));
            tvd_6.setGravity(Gravity.CENTER);
            tvd_6.setPadding(10, 10, 10, 10);

            tbrow1.addView(tvd_6);

            tableLayoutWalletRecord.addView(tbrow1);

            /*Text Active Member On click*/
            tvd_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Intent intent=new Intent(context, LevelWiseDirectDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("Level",walletDetails.get(index).getMlevel());
                    bundle.putString("Flag","act");
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    getActivity(). overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/

                    Intent intent=new Intent(context, CommonReportActivity.class);

                    intent.putExtra("Level",walletDetails.get(index).getMlevel());
                    intent.putExtra("Flag","act");
                    intent.putExtra("Type","LevelWise");
                    intent.putExtra("Title","Level Wise Direct");
                    context.startActivity(intent);
                    getActivity(). overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Text De-Active Member On click*/
            tvd_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Intent intent=new Intent(context, LevelWiseDirectDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("Level",walletDetails.get(index).getMlevel());
                    bundle.putString("Flag","deact");
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    getActivity(). overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/

                    Intent intent=new Intent(context, CommonReportActivity.class);

                    intent.putExtra("Level",walletDetails.get(index).getMlevel());
                    intent.putExtra("Flag","deact");
                    intent.putExtra("Type","LevelWise");
                    intent.putExtra("Title","Level Wise Direct");
                    context.startActivity(intent);
                    getActivity(). overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });


            }






    }
}
