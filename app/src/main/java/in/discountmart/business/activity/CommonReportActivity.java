package in.discountmart.business.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;

import in.base.network.NetworkClient1;
import in.base.ui.BaseActivity;
import in.discountmart.R;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.IncomeServices;
import in.discountmart.business.call_api.MyTeamService;
import in.discountmart.business.call_api.WalletServices;
import in.discountmart.business.model_business.requestmodel.BaseFromToRequest;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.DailyIncentiveRequest;
import in.discountmart.business.model_business.requestmodel.DownlineDetailRequest;
import in.discountmart.business.model_business.requestmodel.DownlinePurchaseRequest;
import in.discountmart.business.model_business.requestmodel.MyDirectRequest;
import in.discountmart.business.model_business.requestmodel.WalletReportRequest;
import in.discountmart.business.model_business.requestmodel.WeeklyIncentiveRequest;
import in.discountmart.business.model_business.responsemodel.DailyIncentiveResponse;
import in.discountmart.business.model_business.responsemodel.DownlineDetailResponse;
import in.discountmart.business.model_business.responsemodel.DownlinePurchaseResponse;
import in.discountmart.business.model_business.responsemodel.Dy_CommunityDetailResponse;
import in.discountmart.business.model_business.responsemodel.Dy_DailyIncentiveResponse;
import in.discountmart.business.model_business.responsemodel.Dy_DownlineDetailResponse;
import in.discountmart.business.model_business.responsemodel.Dy_DownlinePurchaseResponse;
import in.discountmart.business.model_business.responsemodel.Dy_MFADetailResponse;
import in.discountmart.business.model_business.responsemodel.Dy_MonthlyRewardPoints;
import in.discountmart.business.model_business.responsemodel.Dy_MyDirectResponse;
import in.discountmart.business.model_business.responsemodel.Dy_MyRewardResponse;
import in.discountmart.business.model_business.responsemodel.Dy_UpgradeReportResponse;
import in.discountmart.business.model_business.responsemodel.Dy_WalletReportReponse;
import in.discountmart.business.model_business.responsemodel.Dy_WalletRequesDetailResponse;
import in.discountmart.business.model_business.responsemodel.Dy_WeeklyIncentiveResponse;
import in.discountmart.business.model_business.responsemodel.Dy_WithdrawalDetailResponse;
import in.discountmart.business.model_business.responsemodel.LevelWiseReportResponse;
import in.discountmart.business.model_business.responsemodel.MainWalletReportResponse;
import in.discountmart.business.model_business.responsemodel.MatchingRecognitionResponse;
import in.discountmart.business.model_business.responsemodel.MyPurchaseDetailResponse;
import in.discountmart.business.model_business.responsemodel.PinReceiveDetailResponse;
import in.discountmart.business.model_business.responsemodel.PinTransferDetailReaponse;
import in.discountmart.business.model_business.responsemodel.RankReportResponse;
import in.discountmart.business.model_business.responsemodel.WeeklyIncentiveResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;
import in.discountmart.business.utility.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonReportActivity extends BaseActivity {
    TableLayout tableLayout;
    ProgressDialog pDialog;

    TextView txtRecord;
    TextView txtLBV;
    TextView txtRBV;
    TextView textViewUsedBal;
    TextView textViewMainBal;
    TextView txtNext;
    TextView textViewRecord;
    TextView txtTotDirectLeft;
    TextView txtTotDirectRight;
    TextView txtTotDirectTotal;
    TextView txtActDirectRight;
    TextView txtActDirectLeft;
    TextView txtActDirectTotal;
    TextView txtDirectBVLeft;
    TextView txtDirectBVRight;
    TextView txtMfaNote1;
    TextView txtMfaNote2;
    TextView txtMonthRewrdPointTot;
    public static EditText edTxtDate;


    LinearLayout linearLayoutNext;
    LinearLayout layoutMonthlyRewardPointTotal;
    LinearLayout layoutWalletStatus;
    LinearLayout linearLayoutNoRecord;
    LinearLayout linearLayoutTable;
    LinearLayout layoutMyDirect;
    LinearLayout layoutMFANotes;
    LinearLayout layoutMyDirectSummery;
    int from;
    int to;
    int totalRecord;
    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms


    ArrayList<MainWalletReportResponse.MainWallet> mainWalletArrayList;
    ArrayList<MyPurchaseDetailResponse.MyPurchaseDetail> myPurchaseDetailList;
    ArrayList<DownlinePurchaseResponse.DownlinePurchaseItem> downlineRepurchaseList;
    ArrayList<WeeklyIncentiveResponse.WeeklyIncentive> weeklyIncentiveArrayList;
    ArrayList<DailyIncentiveResponse.DailyIncentive> dailyIncentiveArrayList;
    ArrayList<PinTransferDetailReaponse.PinTransferDetail> pinTransDetailArrayList;
    ArrayList<PinReceiveDetailResponse.PinReceiveDetail> pinReceiveDetailArrayList;
    //ArrayList<MyDirectResponse.MyDirect> directArrayList;
    ArrayList<LevelWiseReportResponse.LevelWiseReport> levelWiseReportArrayList;
    ArrayList<DownlineDetailResponse.DownlineDetails> downlineDetailsArrayList;
    ArrayList<RankReportResponse.DownlineRank> downlineRankArrayList;

    MatchingRecognitionResponse matchingRecognitionResponse;
    MainWalletReportResponse.MainWallet[] mainWallets;

    Context context;
    View view;
    String type="";
    String title="";
    String strLevelID="";
    String strFromDate="";
    String strToDate="";
    String strSide="";
    String strPckgId="";
    String strMemberId="";
    String strRankId="";
    String strStatus="";
    String strSearch="";
    String strFlag="";
    String strGroup="";
    String strApiKey="";
    public static String strDate="";
    public static int yyFromDate ;
    public static int mmFromDate ;
    public static int ddFromDate ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_report);

        try {

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            tableLayout = (TableLayout) findViewById(R.id.report_act_tablelayout_wallet_record);

            view=findViewById(android.R.id.content);
            txtLBV = (TextView) findViewById(R.id.report_act_txt_lbv);
            txtRBV = (TextView) findViewById(R.id.report_act_txt_rbv);
            txtRecord = (TextView) findViewById(R.id.report_act_txt_record);

            txtNext = (TextView) findViewById(R.id.report_act_txt_next);
            txtMfaNote1 = (TextView) findViewById(R.id.report_act_txt_mfa_note1);
            txtMfaNote2 = (TextView) findViewById(R.id.report_act_txt_mfa_note2);
            txtMonthRewrdPointTot = (TextView) findViewById(R.id.report_act_txt_rewardpoint_total);
            linearLayoutNext = (LinearLayout) findViewById(R.id.report_act_layout_next);
            linearLayoutNoRecord = (LinearLayout) findViewById(R.id.report_act_layout_noRecord);
            layoutMFANotes = (LinearLayout) findViewById(R.id.report_act_layout_mfa_notes);
            linearLayoutTable = (LinearLayout) findViewById(R.id.report_act_layout_table);
            //layoutMyDirect = (LinearLayout) findViewById(R.id.report_act_layout_mydirect);
            layoutMyDirectSummery = (LinearLayout) findViewById(R.id.report_act_layout_mydirect_summery);
            layoutWalletStatus = (LinearLayout) findViewById(R.id.report_act_layout_wallet_status);
            layoutMonthlyRewardPointTotal = (LinearLayout) findViewById(R.id.report_act_layout_rewardpoint_total);

//            txtTotDirectLeft=(TextView)findViewById(R.id.report_act_txt_totdirect_left);
//            txtTotDirectRight=(TextView)findViewById(R.id.report_act_txt_totdirect_right);
//            txtTotDirectTotal=(TextView)findViewById(R.id.report_act_txt_totdirect_total);
//            txtActDirectLeft=(TextView)findViewById(R.id.report_act_txt_actdirect_left);
//            txtActDirectRight=(TextView)findViewById(R.id.report_act_txt_actdirect_right);
//            txtActDirectTotal=(TextView)findViewById(R.id.report_act_txt_actdirect_total);
//            txtDirectBVLeft=(TextView)findViewById(R.id.report_act_txt_directbv_left);
//            txtDirectBVRight=(TextView)findViewById(R.id.report_act_txt_directbv_right);
//            txtDirectBVTotal=(TextView)findViewById(R.id.report_act_txt_directbv_total);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(this);

            /*Get value from Intent  */
            Intent intent=getIntent();
            if(intent != null){
                type=intent.getStringExtra("Type");
                title=intent.getStringExtra("Title");

                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(title);

                from = 1;
                to = 10;

                /* show My Purchase Detail  Value*/
                if(type.equals("MyDirect")){

                    strLevelID=intent.getStringExtra("Level");
                    strSearch=intent.getStringExtra("Search");
                    strStatus=intent.getStringExtra("Status");
                    //Call Save Complaint  APi
                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getDynamicMyDirectReportDetail();
                    }
                }
                else if(type.equals("LevelWiseDirect")){

                    strLevelID=intent.getStringExtra("Level");
                    strSearch=intent.getStringExtra("Search");
                    strStatus=intent.getStringExtra("Status");
                    //Call Save Complaint  APi
                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        getDynamicLevelWiseDirectReport();
                    }
                }

                /* show Downline detail Value*/

                else if(type.equals("DownDetail")){

                    strToDate=intent.getStringExtra("ToDate");
                    strFromDate=intent.getStringExtra("FromDate");
                    strStatus=intent.getStringExtra("Status");
                    strSide=intent.getStringExtra("Side");

                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getDynamicDownlineDetail();
                    }

                }

                /* show Weekly Incentive  Value*/
                else if(type.equals("Weekly")){

                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getDynamicWeeklyIncentive();
                    }

                }
                /* show Wallet Report  Value*/
                else if(type.equals("WalletReqDetail")){

                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {

                        getWalletReqReportDetail();
                    }

                }
                /* show Weekly Incentive  Value*/
                else if(title.contains("Wallet")){

                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getDynamicWalletReportDetail(type);
                    }

                }

                /* show Daily Incentive  Value*/
                else if(type.equals("Daily")){

                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getDynamicDailyIncentive();
                    }

                }

                /* show Daily Incentive  Value*/
                else if(type.equals("Monthly")){

                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getDynamicDailyIncentive();
                        //getDyn
                    }

                }

               /* else if(type.equals("Member Count Level")){

                    //strToDate=intent.getStringExtra("ToDate");Upgrade
                    //strFromDate=intent.getStringExtra("FromDate");
                    //strStatus=intent.getStringExtra("Status");
                    //strSide=intent.getStringExtra("Side");

                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getMemberCountLevelWise();
                    }

                }*/

                else if(type.equals("Upgrade")){

                    //strToDate=intent.getStringExtra("ToDate");
                    //strFromDate=intent.getStringExtra("FromDate");
                    //strStatus=intent.getStringExtra("Status");
                    //strSide=intent.getStringExtra("Side");

                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getDynamicUpgradeRewards();
                    }

                }

                /*else if(type.equals("GPS")){

                    //strToDate=intent.getStringExtra("ToDate");
                    //strFromDate=intent.getStringExtra("FromDate");
                    //strStatus=intent.getStringExtra("Status");
                    //strSide=intent.getStringExtra("Side");

                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getGPSCountReport();
                    }

                }*/

               /* else if(type.equals("Invoice")){

                    //strToDate=intent.getStringExtra("ToDate");
                    //strFromDate=intent.getStringExtra("FromDate");
                    //strStatus=intent.getStringExtra("Status");
                    //strSide=intent.getStringExtra("Side");

                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getInvoiceReport();
                    }

                }*/

                else if(type.equals("Withdrawal Detail")){

                    //strToDate=intent.getStringExtra("ToDate");
                    //strFromDate=intent.getStringExtra("FromDate");
                    //strStatus=intent.getStringExtra("Status");
                    //strSide=intent.getStringExtra("Side");

                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getWithdrawalDetail();
                    }

                }

                else if(title.equals("Downline Purchase")){

                    strToDate=intent.getStringExtra("ToDate");
                    strFromDate=intent.getStringExtra("FromDate");
                    //strStatus=intent.getStringExtra("Status");
                    //strSide=intent.getStringExtra("Side");
                    strLevelID=intent.getStringExtra("levelId");
                    strGroup=intent.getStringExtra("Group");

                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getDynamicDownlinePurchaseDetail();
                    }

                }

                else if(type.equals("MyReward")){

                    //strToDate=intent.getStringExtra("ToDate");
                    //strFromDate=intent.getStringExtra("FromDate");
                    //strStatus=intent.getStringExtra("Status");
                    //strSide=intent.getStringExtra("Side");
                    //strLevelID=intent.getStringExtra("levelId");
                    //strGroup=intent.getStringExtra("Group");

                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getMyRewardsDetail();
                    }

                }
                else if(type.equals("MFA")){

                    //strToDate=intent.getStringExtra("ToDate");
                    //strFromDate=intent.getStringExtra("FromDate");
                    //strStatus=intent.getStringExtra("Status");
                    //strSide=intent.getStringExtra("Side");
                    //strLevelID=intent.getStringExtra("levelId");
                    //strGroup=intent.getStringExtra("Group");

                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getMFADetail();
                    }

                }
                else if(type.equals("Reward Points")){

                    //strToDate=intent.getStringExtra("ToDate");
                    //strFromDate=intent.getStringExtra("FromDate");
                    //strStatus=intent.getStringExtra("Status");
                    //strSide=intent.getStringExtra("Side");
                    //strLevelID=intent.getStringExtra("levelId");
                    //strGroup=intent.getStringExtra("Group");

                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getMonthlyRewardPoints();
                    }

                }

                else if(type.equals("CommunityDetail")){

                    //strToDate=intent.getStringExtra("ToDate");
                    //strFromDate=intent.getStringExtra("FromDate");
                    //strStatus=intent.getStringExtra("Status");
                    //strSide=intent.getStringExtra("Side");
                    //strLevelID=intent.getStringExtra("levelId");
                    //strGroup=intent.getStringExtra("Group");

                    if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                        Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getMonthlyRewardPoints();
                    }

                }
            }

            /* button on next click get next data*/
            linearLayoutNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /* For My Purchase detail*/
                    if(type.equals("MyDirect")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getDynamicMyDirectReportDetail();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }
                    else if(type.equals("LevelWiseDirect")){

                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getDynamicLevelWiseDirectReport();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }

                    /* For Wallet Request detail*/
                    else if(type.equals("WalletReqDetail")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getWalletReqReportDetail();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }

                    /* For Downline Purchase detail*/
                    else if(type.equals("OrderStatus")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // getMyOrderStatusDetail();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }

                    /* For Weekly Incentive detail*/
                    else if(type.equals("Weekly")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getDynamicWeeklyIncentive();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }
                    /* For Daily Incentive detail*/
                    else if(type.equals("Daily")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getDynamicDailyIncentive();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }

                    /* For Daily Incentive detail*/
                    else if(type.equals("Monthly")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getDynamicDailyIncentive();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }

                    /* For Wallet Report detail*/
                    else if(title.contains("Wallet")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getDynamicWalletReportDetail(type);
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }

                    /* For Wallet Report detail*/
                    else if(title.contains("Downline Purchase")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getDynamicDownlinePurchaseDetail();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }

                    /* For Wallet Report detail*/
                    else if(type.contains("MyReward")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getMyRewardsDetail();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }
                    //
                    /* For Wallet Report detail*/
                    else if(type.contains("DownDetail")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getDynamicDownlineDetail();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }
                    /* For Wallet Report detail*/
                    else if(type.contains("MFA")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getMFADetail();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }

                    /* For Monthly Reward points Deail*/
                    else if(type.contains("Reward Points")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getMonthlyRewardPoints();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }



                    /* For Member Count Level Wise detail*/
                    /*else if(type.contains("Member Count Level")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getMemberCountLevelWise();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }*/

                    /* For Upgrade Reward  detail*/
                    else if(type.contains("Upgrade")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getDynamicUpgradeRewards();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }

                    /* For GPS Count Report  detail*/
                    /*else if(type.contains("GPS")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getGPSCountReport();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }*/

                    /* For GPS Count Report  detail*/
                    /*else if(type.contains("Invoice")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getInvoiceReport();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }*/
                    /* For GPS Count Report  detail*/
                    /*else if(type.contains("Withdrawal Detail")){
                        from = from + 10;
                        to = to + 10;
                        if (from <= totalRecord) {

                            //Call SAve Complaint  APi
                            if (!ConnectivityUtils.isNetworkEnabled(CommonReportActivity.this)) {
                                Toast.makeText(CommonReportActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getWithdrawalDetail();
                            }
                        } else {
                            Toast.makeText(CommonReportActivity.this, "Record complete", Toast.LENGTH_SHORT).show();
                            linearLayoutNext.setVisibility(View.GONE);
                        }
                    }*/

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*Get My Direct Report Response Api*/
    private void getDynamicMyDirectReportDetail(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();


        MyDirectRequest Request=new MyDirectRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_MY_DIRECT_DY);
        Request.setPasswd(SharedPrefrence_Business.getPassword(this));
        Request.setUserid(SharedPrefrence_Business.getUserID(this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        Request.setLevel(strLevelID);
        Request.setSearch(strStatus);
        Request.setLegno(strSearch);
        Request.setFrom(String.valueOf(from));
        Request.setTo(String.valueOf(to));

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(Request);
        Log.e("ReqLevelWiseReport:", Get_Paramenter);

        Call<Dy_MyDirectResponse> reportResponseCall
                = NetworkClient1.getInstance(this).create(MyTeamService.class).fetchDynamicMyDirectDetail(Request,strApiKey);

        reportResponseCall.enqueue(new Callback<Dy_MyDirectResponse>() {
            @Override
            public void onResponse(Call<Dy_MyDirectResponse> call, Response<Dy_MyDirectResponse> response) {
                //hideProgressDialog();
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    //DMyDirectResponse Response=new DMyDirectResponse();
                    Dy_MyDirectResponse dMyDirectResponse=new Dy_MyDirectResponse();

                    dMyDirectResponse=response.body();
                    if(dMyDirectResponse != null){
                        if (dMyDirectResponse.getResponse().equals("OK")) {


                            if(dMyDirectResponse.getDirects()!= null && dMyDirectResponse.getDirects().size() > 0){

                                linearLayoutNoRecord.setVisibility(View.GONE);
                                linearLayoutTable.setVisibility(View.VISIBLE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.VISIBLE);
                                linearLayoutNext.setVisibility(View.VISIBLE);

                                totalRecord = Integer.parseInt(dMyDirectResponse.getRecordcount());
                                txtRecord.setText(" Total Record : - " +String.valueOf(totalRecord));
                                //textViewRecord.setTextSize(12);
                                ArrayList<Map<String ,String>> directmaps= dMyDirectResponse.getDirects();
                                ArrayList<Map<String ,String>> mapsSummery=dMyDirectResponse.getDirectssummary();


                                int totcount= Integer.parseInt(dMyDirectResponse.getRecordcount());
                                // GetHashMapDirectValue(directmaps);
                                //GetHashMapSummeryValue(maps1);
                                if(from == 1){
                                    addDirectTableHeaders(directmaps);
                                    addDirectTableData(directmaps);
                                    createMyDirectSummeryDetail(mapsSummery);
                                }
                                else {
                                    addDirectTableData(directmaps);
                                }

                            }
                            else {
                                // isLastPage = true;
                                //isLoading = false;
                                // Toast.makeText(context,"Record is empty"  +downlineDetailReeponse.getRecordcount(), Toast.LENGTH_SHORT).show();
                                //downlineDetailsArrayList = new ArrayList<DownlineDetailResponse.DownlineDetails>(Arrays.asList(downlineDetails));
                                //downlineDetailsArrayList.clear();
                                //downlineDetailAdapter.setData( downlineDetailsArrayList, recyViewDownlineLeft);
                                linearLayoutNoRecord.setVisibility(View.VISIBLE);
                                linearLayoutTable.setVisibility(View.GONE);
                                layoutMyDirectSummery.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.GONE);

                            }

                        }
                        else if(dMyDirectResponse.getResponse().equals("FAILED") && dMyDirectResponse.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(CommonReportActivity.this,dMyDirectResponse.getMsg());
                        }
                        else if(dMyDirectResponse.getResponse().equals("FAILED")) {
                            String msg=dMyDirectResponse.getResponse()+ " "+"Something went wrong..";
                            Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
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
                        String msg="Something went wrong. Please try again.";
                        blankValueFromSharePreference(CommonReportActivity.this,msg);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Dy_MyDirectResponse> call, Throwable t) {

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

    /*Get Level Wise Report Response Api*/
    private void getDynamicLevelWiseDirectReport(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();


        MyDirectRequest Request=new MyDirectRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_MY_DIRECT_DY);
        Request.setPasswd(SharedPrefrence_Business.getPassword(this));
        Request.setUserid(SharedPrefrence_Business.getUserID(this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        Request.setLevel(strLevelID);
        Request.setSearch(strStatus);
        Request.setLegno(strSearch);
        Request.setFrom(String.valueOf(from));
        Request.setTo(String.valueOf(to));

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(Request);
        Log.e("ReqLevelWiseReport:", Get_Paramenter);

        Call<Dy_MyDirectResponse> reportResponseCall
                = NetworkClient1.getInstance(this).create(MyTeamService.class).fetchDynamicMyDirectDetail(Request,strApiKey);

        reportResponseCall.enqueue(new Callback<Dy_MyDirectResponse>() {
            @Override
            public void onResponse(Call<Dy_MyDirectResponse> call, Response<Dy_MyDirectResponse> response) {
                //hideProgressDialog();
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    //DMyDirectResponse Response=new DMyDirectResponse();
                    Dy_MyDirectResponse dMyDirectResponse=new Dy_MyDirectResponse();

                    dMyDirectResponse=response.body();
                    if(dMyDirectResponse != null){
                        if (dMyDirectResponse.getResponse().equals("OK")) {


                            if(dMyDirectResponse.getDirects()!= null && dMyDirectResponse.getDirects().size() > 0){

                                linearLayoutNoRecord.setVisibility(View.GONE);
                                linearLayoutTable.setVisibility(View.VISIBLE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.VISIBLE);
                                linearLayoutNext.setVisibility(View.VISIBLE);

                                totalRecord = Integer.parseInt(dMyDirectResponse.getRecordcount());
                                txtRecord.setText(" Total Record : - " +String.valueOf(totalRecord));
                                //textViewRecord.setTextSize(12);
                                ArrayList<Map<String ,String>> levelDirectmaps= dMyDirectResponse.getDirects();
                                ArrayList<Map<String ,String>> mapsLevelDirectSummery=dMyDirectResponse.getDirectssummary();


                                int totcount= Integer.parseInt(dMyDirectResponse.getRecordcount());
                                // GetHashMapDirectValue(directmaps);
                                //GetHashMapSummeryValue(maps1);
                                if(from == 1){
                                    addLevelWiseDirectTableHeaders(levelDirectmaps);
                                    addLevelWiseDirectTableData(levelDirectmaps);
                                    createMyDirectSummeryDetail(mapsLevelDirectSummery);
                                }
                                else {
                                    addLevelWiseDirectTableData(levelDirectmaps);
                                }

                            }
                            else {
                                // isLastPage = true;
                                //isLoading = false;
                                // Toast.makeText(context,"Record is empty"  +downlineDetailReeponse.getRecordcount(), Toast.LENGTH_SHORT).show();
                                //downlineDetailsArrayList = new ArrayList<DownlineDetailResponse.DownlineDetails>(Arrays.asList(downlineDetails));
                                //downlineDetailsArrayList.clear();
                                //downlineDetailAdapter.setData( downlineDetailsArrayList, recyViewDownlineLeft);
                                linearLayoutNoRecord.setVisibility(View.VISIBLE);
                                linearLayoutTable.setVisibility(View.GONE);
                                layoutMyDirectSummery.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.GONE);

                            }

                        }
                        else if(dMyDirectResponse.getResponse().equals("FAILED") && dMyDirectResponse.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(CommonReportActivity.this,dMyDirectResponse.getMsg());
                        }
                        else if(dMyDirectResponse.getResponse().equals("FAILED")) {
                            String msg=dMyDirectResponse.getResponse()+ " "+"Something went wrong..";
                            Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
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
                        String msg="Something went wrong. Please try again.";
                        blankValueFromSharePreference(CommonReportActivity.this,msg);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Dy_MyDirectResponse> call, Throwable t) {

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

    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        //tv.setOnClickListener(context);
        return tv;
    }


    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.VERTICAL);
    }

    /* This function for show mydirect summery
     * dynamically
     * */
    public void createMyDirectSummeryDetail(final ArrayList<Map<String,String>> myDirectsummery) {
        /*Set Dynamically add Linear Layout,TextView,CheckBox*/

        ArrayList<Map<String, String>> mySummerylist = new ArrayList<Map<String, String>>();
        mySummerylist=myDirectsummery;
        layoutMyDirectSummery.removeAllViews();


        for (int i = 0; i < mySummerylist.size(); i++)
        {
            Map<String, String> map =mySummerylist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(this);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(4);

            for(Object obj : map.keySet()){
                if(i==0){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);


                    TextView txtHeader=new TextView(this);
                    txtHeader.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    txtHeader.setText(String.valueOf(obj));
                    if(obj.equals("Total"))
                        txtHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    else
                        txtHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    txtHeader.setTypeface(null, Typeface.BOLD);
                    txtHeader.setTextColor(getResources().getColor(R.color.black));
                    txtHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));
                    txtHeader.setGravity(Gravity.LEFT);

                    linearLayout.setLayoutParams(getLayoutParams());
                    linearLayout.addView(txtHeader);
                }
            }

            layoutMyDirectSummery.addView(linearLayout);

        }


        for (int i = 0; i < mySummerylist.size(); i++)
        {
            Map<String, String> map =mySummerylist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(this);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(4);

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(this);

                TextView txtName=new TextView(this);
                txtName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

                txtName.setText(String.valueOf(objKey));
                if(obj.equals("Total"))
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                else
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                txtName.setTextColor(getResources().getColor(R.color.black));
                txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));
                txtName.setGravity(Gravity.LEFT);

                linearLayout.setLayoutParams(getLayoutParams());
                linearLayout.addView(txtName);

            }

            layoutMyDirectSummery.addView(linearLayout);

        }

    }

    /**
     * This function add and show the headers to the table
     **/
    public void addDirectTableHeaders(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        tableLayout.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;
        String key="";
        String value="";
        // int i;

        int count=0;
        Map<String, String> map =mylist1.get(0);
            /*Map<String, String> map =mylist1.get(0);

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Key - "+Sunlevel);
                for(Object obj : map.keySet()){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(getLayoutParams());
                    tr.addView(getTextView(0, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                    //tr.addView();
                    tableLayoutRecord.addView(tr, getTblLayoutParams());
                }*/
        TextView[] textArray = new TextView[mylist1.size()];
        TableRow[] tr_head = new TableRow[mylist1.size()];
        Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < map.size(); i++)
        {
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                if(i==0){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    tableRow.addView(getTextView(i, String.valueOf(obj), Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
                    //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

                    // tableLayoutRecord.addView(tableRow);
                }
               /* else {
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    //tableRow.addView(getTextView(i, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));
                }*/


                //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                //tr.addView();
                // tableLayoutRecord.addView(tr, getTblLayoutParams());
                //tableLayoutRecord.addView(tableRow);
            }

           /* for (int j = i; j<=i; j++)
            {

                Object objKeySet=map.keySet().toArray()[j];
                Object objKey=map.keySet();
                Log.d("Key - ", String.valueOf(objKeySet));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKeySet), Color.BLACK, Typeface.BOLD, Color.BLUE));






                //textView.setPadding(50, 50, 50, 50);

               // tableRow.addView(textView);

            }*/

            // add the TableRow to the TableLayout

            tableLayout.addView(tableRow);
            // table.addView(row, new TableLayout.LayoutParams(
            // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }


        /*for (int i = 0; i < 5; i++) {

            TableRow row = new TableRow(this);

            //Create the tablerows
            tr_head[i] = new TableRow(this);
            tr_head[i].setId(i+1);
            tr_head[i].setBackgroundColor(Color.GRAY);
            tr_head[i].setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // Here create the TextView dynamically

            textArray[i] = new TextView(this);
            textArray[i].setId(i+111);
            textArray[i].setText("Coloum");
            textArray[i].setTextColor(Color.BLACK);
            textArray[i].setPadding(5, 5, 5, 5);
           // tr_head[i].addView(textArray[i]);
            tr_head[i].addView(textArray[i]);

            //TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            //row.setLayoutParams(lp);
            //TextView textView = new TextView(this);
            //textView.setText("Name" + " " + i);
           // EditText editText = new EditText(this);
           // row.addView(i+"");
            //tableLayoutRecord.addView(row, i);

            tableLayoutRecord.addView(tr_head[i]);
        }*/






           /* for(Map<String, String> map: mylist1) {

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Position Map - "+Sunlevel);
                for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                    //
                    key = mapEntry.getKey();
                    value = mapEntry.getValue();

                    // Log.d("Direct", "Key: -"+key+"Value: -"+value + " Position - "+mapEntryIndex);
                    for( int i=0; i<map.entrySet().size(); i++) {
                        int mapEntryIndex= map.entrySet().size();
                        TableRow tr = new TableRow(this);
                       int index=mapEntry.getKey().indexOf(i);
                        if ( i == mapEntryIndex ) {

                            tr.setLayoutParams(getLayoutParams());
                            tr.addView(getTextView(0, key, Color.BLACK, Typeface.BOLD, Color.BLUE));
                            //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                            //tr.addView();
                            tableLayoutRecord.addView(tr, getTblLayoutParams());
                             //break;
                        } else {
                            break;
                        }

                    }
                    break;

                }
                //break;
            }*/


    }

    /**
     * This function add and show the dynamic data of
     * the Directs in the table
     *
     **/
    public void addDirectTableData(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;

        // Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        //Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

            }
            tableLayout.addView(tableRow);

        }

    }



    /**
     * This function add and show the headers to the table
     * In Level wise Direct Table
     **/
    public void addLevelWiseDirectTableHeaders(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        tableLayout.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;
        String key="";
        String value="";
        // int i;

        int count=0;
        Map<String, String> map =mylist1.get(0);
            /*Map<String, String> map =mylist1.get(0);

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Key - "+Sunlevel);
                for(Object obj : map.keySet()){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(getLayoutParams());
                    tr.addView(getTextView(0, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                    //tr.addView();
                    tableLayoutRecord.addView(tr, getTblLayoutParams());
                }*/
        TextView[] textArray = new TextView[mylist1.size()];
        TableRow[] tr_head = new TableRow[mylist1.size()];
        Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < map.size(); i++)
        {
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                if(i==0){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    tableRow.addView(getTextView(i, String.valueOf(obj), Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
                    //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

                    // tableLayoutRecord.addView(tableRow);
                }
               /* else {
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    //tableRow.addView(getTextView(i, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));
                }*/


                //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                //tr.addView();
                // tableLayoutRecord.addView(tr, getTblLayoutParams());
                //tableLayoutRecord.addView(tableRow);
            }

           /* for (int j = i; j<=i; j++)
            {

                Object objKeySet=map.keySet().toArray()[j];
                Object objKey=map.keySet();
                Log.d("Key - ", String.valueOf(objKeySet));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKeySet), Color.BLACK, Typeface.BOLD, Color.BLUE));






                //textView.setPadding(50, 50, 50, 50);

               // tableRow.addView(textView);

            }*/

            // add the TableRow to the TableLayout

            tableLayout.addView(tableRow);
            // table.addView(row, new TableLayout.LayoutParams(
            // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }


        /*for (int i = 0; i < 5; i++) {

            TableRow row = new TableRow(this);

            //Create the tablerows
            tr_head[i] = new TableRow(this);
            tr_head[i].setId(i+1);
            tr_head[i].setBackgroundColor(Color.GRAY);
            tr_head[i].setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // Here create the TextView dynamically

            textArray[i] = new TextView(this);
            textArray[i].setId(i+111);
            textArray[i].setText("Coloum");
            textArray[i].setTextColor(Color.BLACK);
            textArray[i].setPadding(5, 5, 5, 5);
           // tr_head[i].addView(textArray[i]);
            tr_head[i].addView(textArray[i]);

            //TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            //row.setLayoutParams(lp);
            //TextView textView = new TextView(this);
            //textView.setText("Name" + " " + i);
           // EditText editText = new EditText(this);
           // row.addView(i+"");
            //tableLayoutRecord.addView(row, i);

            tableLayoutRecord.addView(tr_head[i]);
        }*/






           /* for(Map<String, String> map: mylist1) {

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Position Map - "+Sunlevel);
                for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                    //
                    key = mapEntry.getKey();
                    value = mapEntry.getValue();

                    // Log.d("Direct", "Key: -"+key+"Value: -"+value + " Position - "+mapEntryIndex);
                    for( int i=0; i<map.entrySet().size(); i++) {
                        int mapEntryIndex= map.entrySet().size();
                        TableRow tr = new TableRow(this);
                       int index=mapEntry.getKey().indexOf(i);
                        if ( i == mapEntryIndex ) {

                            tr.setLayoutParams(getLayoutParams());
                            tr.addView(getTextView(0, key, Color.BLACK, Typeface.BOLD, Color.BLUE));
                            //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                            //tr.addView();
                            tableLayoutRecord.addView(tr, getTblLayoutParams());
                             //break;
                        } else {
                            break;
                        }

                    }
                    break;

                }
                //break;
            }*/


    }

    /**
     * This function add and show the dynamic data of
     * the Level wise Directs in the table
     *
     **/
    public void addLevelWiseDirectTableData(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;

        // Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        //Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

            }
            tableLayout.addView(tableRow);

        }

    }

    /*Get Downline detail Response Api*/
    private void getDynamicDownlineDetail(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();


        DownlineDetailRequest Request=new DownlineDetailRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_DOWNLINE_DETAIL_DY);
        Request.setPasswd(SharedPrefrence_Business.getPassword(this));
        Request.setUserid(SharedPrefrence_Business.getUserID(this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        Request.setLegno(strSide);
        Request.setFromdate(strFromDate);
        Request.setTodate(strToDate);
        Request.setStatus(strStatus);
        Request.setFrom(String.valueOf(from));
        Request.setTo(String.valueOf(to));


        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(Request);
        Log.e("ReqLevelWiseReport:", Get_Paramenter);

        Call<Dy_DownlineDetailResponse> reportResponseCall
                = NetworkClient1.getInstance(this).create(MyTeamService.class).fetchDy_DownlineDetails(Request,strApiKey);

        reportResponseCall.enqueue(new Callback<Dy_DownlineDetailResponse>() {
            @Override
            public void onResponse(Call<Dy_DownlineDetailResponse> call, Response<Dy_DownlineDetailResponse> response) {
                //hideProgressDialog();
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    //DMyDirectResponse Response=new DMyDirectResponse();
                    Dy_DownlineDetailResponse  dy_downdetailResponse=new Dy_DownlineDetailResponse();

                    dy_downdetailResponse=response.body();
                    if(dy_downdetailResponse != null){
                        if (dy_downdetailResponse.getResponse().equals("OK")) {


                            if(dy_downdetailResponse.getDownline()!= null && dy_downdetailResponse.getDownline().size() > 0){

                                linearLayoutNoRecord.setVisibility(View.GONE);
                                linearLayoutTable.setVisibility(View.VISIBLE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.VISIBLE);

                                totalRecord = Integer.parseInt(dy_downdetailResponse.getRecordcount());
                                txtRecord.setText("Record : - " +String.valueOf(totalRecord));
                                //textViewRecord.setTextSize(12);
                                ArrayList<Map<String ,String>> downlineDtailmaps= dy_downdetailResponse.getDownline();
                                //ArrayList<Map<String ,String>> mapsLevelDirectSummery=dy_downdetailResponse.getDirectssummary();


                                int totcount= Integer.parseInt(dy_downdetailResponse.getRecordcount());
                                // GetHashMapDirectValue(directmaps);
                                //GetHashMapSummeryValue(maps1);
                                if(from == 1){
                                    addDownlineDetailTableHeaders(downlineDtailmaps);
                                    addDownlineDetailTableData(downlineDtailmaps);

                                }
                                else {
                                    addLevelWiseDirectTableData(downlineDtailmaps);
                                }

                            }


                        }
                        else if(dy_downdetailResponse.getResponse().equals("FAILED") && dy_downdetailResponse.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(CommonReportActivity.this,dy_downdetailResponse.getMsg());
                        }
                        else if(dy_downdetailResponse.getResponse().equals("FAILED")) {
                            String msg=dy_downdetailResponse.getResponse()+ " "+"Something went wrong..";
                            Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
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
                        String msg="Something went wrong. Please try again.";
                        blankValueFromSharePreference(CommonReportActivity.this,msg);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Dy_DownlineDetailResponse> call, Throwable t) {

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


    /**
     * This function add and show the headers to the table
     * content of Downline Detail
     **/
    public void addDownlineDetailTableHeaders(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        tableLayout.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;
        String key="";
        String value="";
        // int i;

        int count=0;
        Map<String, String> map =mylist1.get(0);
            /*Map<String, String> map =mylist1.get(0);

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Key - "+Sunlevel);
                for(Object obj : map.keySet()){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(getLayoutParams());
                    tr.addView(getTextView(0, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                    //tr.addView();
                    tableLayoutRecord.addView(tr, getTblLayoutParams());
                }*/
        TextView[] textArray = new TextView[mylist1.size()];
        TableRow[] tr_head = new TableRow[mylist1.size()];
        Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < map.size(); i++)
        {
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                if(i==0){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    tableRow.addView(getTextView(i, String.valueOf(obj).toUpperCase(), Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
                    //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

                    // tableLayoutRecord.addView(tableRow);
                }
               /* else {
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    //tableRow.addView(getTextView(i, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));
                }*/


                //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                //tr.addView();
                // tableLayoutRecord.addView(tr, getTblLayoutParams());
                //tableLayoutRecord.addView(tableRow);
            }

           /* for (int j = i; j<=i; j++)
            {

                Object objKeySet=map.keySet().toArray()[j];
                Object objKey=map.keySet();
                Log.d("Key - ", String.valueOf(objKeySet));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKeySet), Color.BLACK, Typeface.BOLD, Color.BLUE));






                //textView.setPadding(50, 50, 50, 50);

               // tableRow.addView(textView);

            }*/

            // add the TableRow to the TableLayout

            tableLayout.addView(tableRow);
            // table.addView(row, new TableLayout.LayoutParams(
            // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }


        /*for (int i = 0; i < 5; i++) {

            TableRow row = new TableRow(this);

            //Create the tablerows
            tr_head[i] = new TableRow(this);
            tr_head[i].setId(i+1);
            tr_head[i].setBackgroundColor(Color.GRAY);
            tr_head[i].setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // Here create the TextView dynamically

            textArray[i] = new TextView(this);
            textArray[i].setId(i+111);
            textArray[i].setText("Coloum");
            textArray[i].setTextColor(Color.BLACK);
            textArray[i].setPadding(5, 5, 5, 5);
           // tr_head[i].addView(textArray[i]);
            tr_head[i].addView(textArray[i]);

            //TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            //row.setLayoutParams(lp);
            //TextView textView = new TextView(this);
            //textView.setText("Name" + " " + i);
           // EditText editText = new EditText(this);
           // row.addView(i+"");
            //tableLayoutRecord.addView(row, i);

            tableLayoutRecord.addView(tr_head[i]);
        }*/






           /* for(Map<String, String> map: mylist1) {

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Position Map - "+Sunlevel);
                for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                    //
                    key = mapEntry.getKey();
                    value = mapEntry.getValue();

                    // Log.d("Direct", "Key: -"+key+"Value: -"+value + " Position - "+mapEntryIndex);
                    for( int i=0; i<map.entrySet().size(); i++) {
                        int mapEntryIndex= map.entrySet().size();
                        TableRow tr = new TableRow(this);
                       int index=mapEntry.getKey().indexOf(i);
                        if ( i == mapEntryIndex ) {

                            tr.setLayoutParams(getLayoutParams());
                            tr.addView(getTextView(0, key, Color.BLACK, Typeface.BOLD, Color.BLUE));
                            //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                            //tr.addView();
                            tableLayoutRecord.addView(tr, getTblLayoutParams());
                             //break;
                        } else {
                            break;
                        }

                    }
                    break;

                }
                //break;
            }*/


    }

    /**
     * This function add and show the dynamic data of
     * the Downline Detail in the table
     *
     **/
    public void addDownlineDetailTableData(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;

        // Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        //Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

            }
            tableLayout.addView(tableRow);

        }

    }



    /* Method for get Daily incentive Report
     *  from Api Request and Response */
    private void getDynamicDailyIncentive(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();


        DailyIncentiveRequest Request=new DailyIncentiveRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_DAILY_INCOME_DY);
        Request.setPasswd(SharedPrefrence_Business.getPassword(this));
        Request.setUserid(SharedPrefrence_Business.getUserID(this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        Request.setFrom(String.valueOf(from));
        Request.setTo(String.valueOf(to));


        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(Request);
        Log.e("ReqLevelWiseReport:", Get_Paramenter);

        Call<Dy_DailyIncentiveResponse> reportResponseCall
                = NetworkClient1.getInstance(this).create(IncomeServices.class).fetchDynamicDailyIncentive(Request,strApiKey);

        reportResponseCall.enqueue(new Callback<Dy_DailyIncentiveResponse>() {
            @Override
            public void onResponse(Call<Dy_DailyIncentiveResponse> call, Response<Dy_DailyIncentiveResponse> response) {
                //hideProgressDialog();
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    //DMyDirectResponse Response=new DMyDirectResponse();
                    Dy_DailyIncentiveResponse Response=new Dy_DailyIncentiveResponse();

                    Response=response.body();
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {


                            if(Response.getDailyincentive()!= null && Response.getDailyincentive().size() > 0){

                                linearLayoutNoRecord.setVisibility(View.GONE);
                                linearLayoutTable.setVisibility(View.VISIBLE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.VISIBLE);
                                linearLayoutNext.setVisibility(View.VISIBLE);

                                totalRecord = Integer.parseInt(Response.getRecordcount());
                                txtRecord.setText(" Total Record : - " +String.valueOf(totalRecord));
                                //textViewRecord.setTextSize(12);
                                ArrayList<Map<String ,String>> dailyincentive= Response.getDailyincentive();
                                //ArrayList<Map<String ,String>> mapsLevelDirectSummery=dy_downdetailResponse.getDirectssummary();


                                int totcount= Integer.parseInt(Response.getRecordcount());
                                // GetHashMapDirectValue(directmaps);
                                //GetHashMapSummeryValue(maps1);
                                if(from == 1){
                                    addDailyIncentiveTableHeaders(dailyincentive);
                                    addDailyIncentiveTableData(dailyincentive);

                                }
                                else {
                                    addDailyIncentiveTableData(dailyincentive);
                                }

                            }


                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(CommonReportActivity.this,Response.getMsg());
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
                    }
                    else {
                        String msg="Something went wrong.";
                        Toast.makeText(CommonReportActivity.this,msg,Toast.LENGTH_SHORT).show();
                        blankValueFromSharePreference(CommonReportActivity.this,msg);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Dy_DailyIncentiveResponse> call, Throwable t) {

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


    /**
     * This function add and show the headers to the table
     * content of Daily incentive Report
     **/
    public void addDailyIncentiveTableHeaders(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        tableLayout.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;
        String key="";
        String value="";
        // int i;

        int count=0;
        Map<String, String> map =mylist1.get(0);
            /*Map<String, String> map =mylist1.get(0);

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Key - "+Sunlevel);
                for(Object obj : map.keySet()){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(getLayoutParams());
                    tr.addView(getTextView(0, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                    //tr.addView();
                    tableLayoutRecord.addView(tr, getTblLayoutParams());
                }*/
        TextView[] textArray = new TextView[mylist1.size()];
        TableRow[] tr_head = new TableRow[mylist1.size()];
        Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < map.size(); i++)
        {
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                if(i==0){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    tableRow.addView(getTextView(i, String.valueOf(obj).toUpperCase(), Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
                    //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

                    // tableLayoutRecord.addView(tableRow);
                }
               /* else {
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    //tableRow.addView(getTextView(i, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));
                }*/


                //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                //tr.addView();
                // tableLayoutRecord.addView(tr, getTblLayoutParams());
                //tableLayoutRecord.addView(tableRow);
            }

           /* for (int j = i; j<=i; j++)
            {

                Object objKeySet=map.keySet().toArray()[j];
                Object objKey=map.keySet();
                Log.d("Key - ", String.valueOf(objKeySet));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKeySet), Color.BLACK, Typeface.BOLD, Color.BLUE));






                //textView.setPadding(50, 50, 50, 50);

               // tableRow.addView(textView);

            }*/

            // add the TableRow to the TableLayout

            tableLayout.addView(tableRow);
            // table.addView(row, new TableLayout.LayoutParams(
            // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }


        /*for (int i = 0; i < 5; i++) {

            TableRow row = new TableRow(this);

            //Create the tablerows
            tr_head[i] = new TableRow(this);
            tr_head[i].setId(i+1);
            tr_head[i].setBackgroundColor(Color.GRAY);
            tr_head[i].setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // Here create the TextView dynamically

            textArray[i] = new TextView(this);
            textArray[i].setId(i+111);
            textArray[i].setText("Coloum");
            textArray[i].setTextColor(Color.BLACK);
            textArray[i].setPadding(5, 5, 5, 5);
           // tr_head[i].addView(textArray[i]);
            tr_head[i].addView(textArray[i]);

            //TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            //row.setLayoutParams(lp);
            //TextView textView = new TextView(this);
            //textView.setText("Name" + " " + i);
           // EditText editText = new EditText(this);
           // row.addView(i+"");
            //tableLayoutRecord.addView(row, i);

            tableLayoutRecord.addView(tr_head[i]);
        }*/






           /* for(Map<String, String> map: mylist1) {

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Position Map - "+Sunlevel);
                for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                    //
                    key = mapEntry.getKey();
                    value = mapEntry.getValue();

                    // Log.d("Direct", "Key: -"+key+"Value: -"+value + " Position - "+mapEntryIndex);
                    for( int i=0; i<map.entrySet().size(); i++) {
                        int mapEntryIndex= map.entrySet().size();
                        TableRow tr = new TableRow(this);
                       int index=mapEntry.getKey().indexOf(i);
                        if ( i == mapEntryIndex ) {

                            tr.setLayoutParams(getLayoutParams());
                            tr.addView(getTextView(0, key, Color.BLACK, Typeface.BOLD, Color.BLUE));
                            //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                            //tr.addView();
                            tableLayoutRecord.addView(tr, getTblLayoutParams());
                             //break;
                        } else {
                            break;
                        }

                    }
                    break;

                }
                //break;
            }*/


    }

    /**
     * This function add and show the dynamic data of
     * the Daily incentive Report in the table
     *
     **/
    public void addDailyIncentiveTableData(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;

        // Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        //Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

            }
            tableLayout.addView(tableRow);

        }

    }


    /* Method for get Monthly incentive Report
     *  from Api Request and Response */
    private void getDynamicMonthlyIncentive(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();


        DailyIncentiveRequest Request=new DailyIncentiveRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_DAILY_INCOME_DY);
        Request.setPasswd(SharedPrefrence_Business.getPassword(this));
        Request.setUserid(SharedPrefrence_Business.getUserID(this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        Request.setFrom(String.valueOf(from));
        Request.setTo(String.valueOf(to));


        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(Request);
        Log.e("ReqLevelWiseReport:", Get_Paramenter);

        Call<Dy_DailyIncentiveResponse> reportResponseCall
                = NetworkClient1.getInstance(this).create(IncomeServices.class).fetchDynamicDailyIncentive(Request,strApiKey);

        reportResponseCall.enqueue(new Callback<Dy_DailyIncentiveResponse>() {
            @Override
            public void onResponse(Call<Dy_DailyIncentiveResponse> call, Response<Dy_DailyIncentiveResponse> response) {
                //hideProgressDialog();
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    //DMyDirectResponse Response=new DMyDirectResponse();
                    Dy_DailyIncentiveResponse Response=new Dy_DailyIncentiveResponse();

                    Response=response.body();
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {


                            if(Response.getDailyincentive()!= null && Response.getDailyincentive().size() > 0){

                                linearLayoutNoRecord.setVisibility(View.GONE);
                                linearLayoutTable.setVisibility(View.VISIBLE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.VISIBLE);
                                linearLayoutNext.setVisibility(View.VISIBLE);

                                totalRecord = Integer.parseInt(Response.getRecordcount());
                                txtRecord.setText(" Total Record : - " +String.valueOf(totalRecord));
                                //textViewRecord.setTextSize(12);
                                ArrayList<Map<String ,String>> dailyincentive= Response.getDailyincentive();
                                //ArrayList<Map<String ,String>> mapsLevelDirectSummery=dy_downdetailResponse.getDirectssummary();


                                int totcount= Integer.parseInt(Response.getRecordcount());
                                // GetHashMapDirectValue(directmaps);
                                //GetHashMapSummeryValue(maps1);
                                if(from == 1){
                                    addDailyIncentiveTableHeaders(dailyincentive);
                                    addDailyIncentiveTableData(dailyincentive);

                                }
                                else {
                                    addDailyIncentiveTableData(dailyincentive);
                                }

                            }


                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(CommonReportActivity.this,Response.getMsg());
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
                    }
                    else {
                        String msg="Something went wrong.";
                        Toast.makeText(CommonReportActivity.this,msg,Toast.LENGTH_SHORT).show();
                        blankValueFromSharePreference(CommonReportActivity.this,msg);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Dy_DailyIncentiveResponse> call, Throwable t) {

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


    /**
     * This function add and show the headers to the table
     * content of Monthly incentive Report
     **/
    public void addMonthlyIncentiveTableHeaders(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        tableLayout.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;
        String key="";
        String value="";
        // int i;

        int count=0;
        Map<String, String> map =mylist1.get(0);
            /*Map<String, String> map =mylist1.get(0);

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Key - "+Sunlevel);
                for(Object obj : map.keySet()){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(getLayoutParams());
                    tr.addView(getTextView(0, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                    //tr.addView();
                    tableLayoutRecord.addView(tr, getTblLayoutParams());
                }*/
        TextView[] textArray = new TextView[mylist1.size()];
        TableRow[] tr_head = new TableRow[mylist1.size()];
        Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < map.size(); i++)
        {
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                if(i==0){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    tableRow.addView(getTextView(i, String.valueOf(obj).toUpperCase(), Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
                    //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

                    // tableLayoutRecord.addView(tableRow);
                }
               /* else {
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    //tableRow.addView(getTextView(i, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));
                }*/


                //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                //tr.addView();
                // tableLayoutRecord.addView(tr, getTblLayoutParams());
                //tableLayoutRecord.addView(tableRow);
            }

           /* for (int j = i; j<=i; j++)
            {

                Object objKeySet=map.keySet().toArray()[j];
                Object objKey=map.keySet();
                Log.d("Key - ", String.valueOf(objKeySet));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKeySet), Color.BLACK, Typeface.BOLD, Color.BLUE));






                //textView.setPadding(50, 50, 50, 50);

               // tableRow.addView(textView);

            }*/

            // add the TableRow to the TableLayout

            tableLayout.addView(tableRow);
            // table.addView(row, new TableLayout.LayoutParams(
            // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }


        /*for (int i = 0; i < 5; i++) {

            TableRow row = new TableRow(this);

            //Create the tablerows
            tr_head[i] = new TableRow(this);
            tr_head[i].setId(i+1);
            tr_head[i].setBackgroundColor(Color.GRAY);
            tr_head[i].setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // Here create the TextView dynamically

            textArray[i] = new TextView(this);
            textArray[i].setId(i+111);
            textArray[i].setText("Coloum");
            textArray[i].setTextColor(Color.BLACK);
            textArray[i].setPadding(5, 5, 5, 5);
           // tr_head[i].addView(textArray[i]);
            tr_head[i].addView(textArray[i]);

            //TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            //row.setLayoutParams(lp);
            //TextView textView = new TextView(this);
            //textView.setText("Name" + " " + i);
           // EditText editText = new EditText(this);
           // row.addView(i+"");
            //tableLayoutRecord.addView(row, i);

            tableLayoutRecord.addView(tr_head[i]);
        }*/






           /* for(Map<String, String> map: mylist1) {

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Position Map - "+Sunlevel);
                for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                    //
                    key = mapEntry.getKey();
                    value = mapEntry.getValue();

                    // Log.d("Direct", "Key: -"+key+"Value: -"+value + " Position - "+mapEntryIndex);
                    for( int i=0; i<map.entrySet().size(); i++) {
                        int mapEntryIndex= map.entrySet().size();
                        TableRow tr = new TableRow(this);
                       int index=mapEntry.getKey().indexOf(i);
                        if ( i == mapEntryIndex ) {

                            tr.setLayoutParams(getLayoutParams());
                            tr.addView(getTextView(0, key, Color.BLACK, Typeface.BOLD, Color.BLUE));
                            //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                            //tr.addView();
                            tableLayoutRecord.addView(tr, getTblLayoutParams());
                             //break;
                        } else {
                            break;
                        }

                    }
                    break;

                }
                //break;
            }*/


    }

    /**
     * This function add and show the dynamic data of
     * the Monthly incentive Report in the table
     *
     **/
    public void addMonthlyIncentiveTableData(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;

        // Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        //Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

            }
            tableLayout.addView(tableRow);

        }

    }

    /* Method for get Weekly incentive Report
     *  from Api Request and Response */
    private void getDynamicWeeklyIncentive(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();


        WeeklyIncentiveRequest Request=new WeeklyIncentiveRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_WEEKLY_INCOME);
        Request.setPasswd(SharedPrefrence_Business.getPassword(this));
        Request.setUserid(SharedPrefrence_Business.getUserID(this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        Request.setFrom(String.valueOf(from));
        Request.setTo(String.valueOf(to));


        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(Request);
        Log.e("ReqLevelWiseReport:", Get_Paramenter);

        Call<Dy_WeeklyIncentiveResponse> reportResponseCall
                = NetworkClient1.getInstance(this).create(IncomeServices.class).fetchDynamicWeeklyIncentive(Request,strApiKey);

        reportResponseCall.enqueue(new Callback<Dy_WeeklyIncentiveResponse>() {
            @Override
            public void onResponse(Call<Dy_WeeklyIncentiveResponse> call, Response<Dy_WeeklyIncentiveResponse> response) {
                //hideProgressDialog();
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    //DMyDirectResponse Response=new DMyDirectResponse();
                    Dy_WeeklyIncentiveResponse Response=new Dy_WeeklyIncentiveResponse();

                    Response=response.body();
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {


                            if(Response.getWeeklyincentive()!= null && Response.getWeeklyincentive().size() > 0){

                                linearLayoutNoRecord.setVisibility(View.GONE);
                                linearLayoutTable.setVisibility(View.VISIBLE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.VISIBLE);
                                linearLayoutNext.setVisibility(View.VISIBLE);

                                totalRecord = Integer.parseInt(Response.getRecordcount());
                                txtRecord.setText(" Total Record : - " +String.valueOf(totalRecord));
                                //textViewRecord.setTextSize(12);
                                ArrayList<Map<String ,String>> weeklyincentive= Response.getWeeklyincentive();
                                //ArrayList<Map<String ,String>> mapsLevelDirectSummery=dy_downdetailResponse.getDirectssummary();


                                int totcount= Integer.parseInt(Response.getRecordcount());
                                // GetHashMapDirectValue(directmaps);
                                //GetHashMapSummeryValue(maps1);
                                if(from == 1){
                                    addWeeklyIncentiveTableHeaders(weeklyincentive);
                                    addWeeklyIncentiveTableData(weeklyincentive);

                                }
                                else {
                                    addWeeklyIncentiveTableData(weeklyincentive);
                                }

                            }


                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(CommonReportActivity.this,Response.getMsg());
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
                    }
                    else {
                        String msg="Something went wrong.";
                        Toast.makeText(CommonReportActivity.this,msg,Toast.LENGTH_SHORT).show();
                        blankValueFromSharePreference(CommonReportActivity.this,msg);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Dy_WeeklyIncentiveResponse> call, Throwable t) {

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

    /**
     * This function add and show the headers to the table
     * content of Weekly incentive Report
     **/
    public void addWeeklyIncentiveTableHeaders(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        tableLayout.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;
        String key="";
        String value="";
        // int i;

        int count=0;
        Map<String, String> map =mylist1.get(0);
            /*Map<String, String> map =mylist1.get(0);

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Key - "+Sunlevel);
                for(Object obj : map.keySet()){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(getLayoutParams());
                    tr.addView(getTextView(0, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                    //tr.addView();
                    tableLayoutRecord.addView(tr, getTblLayoutParams());
                }*/
        TextView[] textArray = new TextView[mylist1.size()];
        TableRow[] tr_head = new TableRow[mylist1.size()];
        Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < map.size(); i++)
        {
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                if(i==0){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    tableRow.addView(getTextView(i, String.valueOf(obj).toUpperCase(), Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
                    //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

                    // tableLayoutRecord.addView(tableRow);
                }
               /* else {
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    //tableRow.addView(getTextView(i, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));
                }*/


                //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                //tr.addView();
                // tableLayoutRecord.addView(tr, getTblLayoutParams());
                //tableLayoutRecord.addView(tableRow);
            }

           /* for (int j = i; j<=i; j++)
            {

                Object objKeySet=map.keySet().toArray()[j];
                Object objKey=map.keySet();
                Log.d("Key - ", String.valueOf(objKeySet));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKeySet), Color.BLACK, Typeface.BOLD, Color.BLUE));






                //textView.setPadding(50, 50, 50, 50);

               // tableRow.addView(textView);

            }*/

            // add the TableRow to the TableLayout

            tableLayout.addView(tableRow);
            // table.addView(row, new TableLayout.LayoutParams(
            // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }


        /*for (int i = 0; i < 5; i++) {

            TableRow row = new TableRow(this);

            //Create the tablerows
            tr_head[i] = new TableRow(this);
            tr_head[i].setId(i+1);
            tr_head[i].setBackgroundColor(Color.GRAY);
            tr_head[i].setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // Here create the TextView dynamically

            textArray[i] = new TextView(this);
            textArray[i].setId(i+111);
            textArray[i].setText("Coloum");
            textArray[i].setTextColor(Color.BLACK);
            textArray[i].setPadding(5, 5, 5, 5);
           // tr_head[i].addView(textArray[i]);
            tr_head[i].addView(textArray[i]);

            //TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            //row.setLayoutParams(lp);
            //TextView textView = new TextView(this);
            //textView.setText("Name" + " " + i);
           // EditText editText = new EditText(this);
           // row.addView(i+"");
            //tableLayoutRecord.addView(row, i);

            tableLayoutRecord.addView(tr_head[i]);
        }*/






           /* for(Map<String, String> map: mylist1) {

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Position Map - "+Sunlevel);
                for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                    //
                    key = mapEntry.getKey();
                    value = mapEntry.getValue();

                    // Log.d("Direct", "Key: -"+key+"Value: -"+value + " Position - "+mapEntryIndex);
                    for( int i=0; i<map.entrySet().size(); i++) {
                        int mapEntryIndex= map.entrySet().size();
                        TableRow tr = new TableRow(this);
                       int index=mapEntry.getKey().indexOf(i);
                        if ( i == mapEntryIndex ) {

                            tr.setLayoutParams(getLayoutParams());
                            tr.addView(getTextView(0, key, Color.BLACK, Typeface.BOLD, Color.BLUE));
                            //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                            //tr.addView();
                            tableLayoutRecord.addView(tr, getTblLayoutParams());
                             //break;
                        } else {
                            break;
                        }

                    }
                    break;

                }
                //break;
            }*/


    }

    /**
     * This function add and show the dynamic data of
     * the Weekly incentive Report in the table
     *
     **/
    public void addWeeklyIncentiveTableData(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;

        // Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        //Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

            }
            tableLayout.addView(tableRow);

        }

    }



    /* Method for get Upgrade Report
     *  from Api Request and Response */
    private void getDynamicUpgradeRewards(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();


        BaseRequest Request=new BaseRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_UPGRADE_REPORT);
        Request.setPasswd(SharedPrefrence_Business.getPassword(this));
        Request.setUserid(SharedPrefrence_Business.getUserID(this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
       // Request.setFrom(String.valueOf(from));
        //Request.setTo(String.valueOf(to));


        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(Request);
        Log.e("ReqLevelWiseReport:", Get_Paramenter);

        Call<Dy_UpgradeReportResponse> reportResponseCall
                = NetworkClient1.getInstance(this).create(MyTeamService.class).fetchDynamicUpgradeReport(Request,strApiKey);

        reportResponseCall.enqueue(new Callback<Dy_UpgradeReportResponse>() {
            @Override
            public void onResponse(Call<Dy_UpgradeReportResponse> call, Response<Dy_UpgradeReportResponse> response) {
                //hideProgressDialog();
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    //DMyDirectResponse Response=new DMyDirectResponse();
                    Dy_UpgradeReportResponse Response=new Dy_UpgradeReportResponse();

                    Response=response.body();
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {


                            if(Response.getUpgradereport()!= null && Response.getUpgradereport().size() > 0){

                                linearLayoutNoRecord.setVisibility(View.GONE);
                                linearLayoutTable.setVisibility(View.VISIBLE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.VISIBLE);
                                linearLayoutNext.setVisibility(View.VISIBLE);

                                //totalRecord = Integer.parseInt(Response.getRecordcount());
                                //txtRecord.setText(" Total Record : - " +String.valueOf(totalRecord));
                                //textViewRecord.setTextSize(12);
                                ArrayList<Map<String ,String>> weeklyincentive= Response.getUpgradereport();
                                //ArrayList<Map<String ,String>> mapsLevelDirectSummery=dy_downdetailResponse.getDirectssummary();


                               // int totcount= Integer.parseInt(Response.getRecordcount());
                                // GetHashMapDirectValue(directmaps);
                                //GetHashMapSummeryValue(maps1);
                                if(from == 1){
                                    addUpgradeReportTableHeaders(weeklyincentive);
                                    addUpgradeReportTableData(weeklyincentive);

                                }
                                else {
                                    addUpgradeReportTableData(weeklyincentive);
                                }

                            }
                            else {

                                linearLayoutNoRecord.setVisibility(View.VISIBLE);
                                linearLayoutTable.setVisibility(View.GONE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.GONE);

                                //totalRecord = Integer.parseInt(Response.getRecordcount());
                                //txtRecord.setText(" Total Record : - " +String.valueOf(totalRecord));
                            }


                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(CommonReportActivity.this,Response.getMsg());
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
                    }
                    else {
                        String msg="Something went wrong.";
                        Toast.makeText(CommonReportActivity.this,msg,Toast.LENGTH_SHORT).show();
                        blankValueFromSharePreference(CommonReportActivity.this,msg);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Dy_UpgradeReportResponse> call, Throwable t) {

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

    /**
     * This function add and show the headers to the table
     * content of Upgrade Report
     **/
    public void addUpgradeReportTableHeaders(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        tableLayout.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;
        String key="";
        String value="";
        // int i;

        int count=0;
        Map<String, String> map =mylist1.get(0);
            /*Map<String, String> map =mylist1.get(0);

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Key - "+Sunlevel);
                for(Object obj : map.keySet()){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(getLayoutParams());
                    tr.addView(getTextView(0, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                    //tr.addView();
                    tableLayoutRecord.addView(tr, getTblLayoutParams());
                }*/
        TextView[] textArray = new TextView[mylist1.size()];
        TableRow[] tr_head = new TableRow[mylist1.size()];
        Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < map.size(); i++)
        {
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                if(i==0){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    tableRow.addView(getTextView(i, String.valueOf(obj).toUpperCase(), Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
                    //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

                    // tableLayoutRecord.addView(tableRow);
                }
               /* else {
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    //tableRow.addView(getTextView(i, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));
                }*/


                //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                //tr.addView();
                // tableLayoutRecord.addView(tr, getTblLayoutParams());
                //tableLayoutRecord.addView(tableRow);
            }

           /* for (int j = i; j<=i; j++)
            {

                Object objKeySet=map.keySet().toArray()[j];
                Object objKey=map.keySet();
                Log.d("Key - ", String.valueOf(objKeySet));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKeySet), Color.BLACK, Typeface.BOLD, Color.BLUE));






                //textView.setPadding(50, 50, 50, 50);

               // tableRow.addView(textView);

            }*/

            // add the TableRow to the TableLayout

            tableLayout.addView(tableRow);
            // table.addView(row, new TableLayout.LayoutParams(
            // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }


        /*for (int i = 0; i < 5; i++) {

            TableRow row = new TableRow(this);

            //Create the tablerows
            tr_head[i] = new TableRow(this);
            tr_head[i].setId(i+1);
            tr_head[i].setBackgroundColor(Color.GRAY);
            tr_head[i].setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // Here create the TextView dynamically

            textArray[i] = new TextView(this);
            textArray[i].setId(i+111);
            textArray[i].setText("Coloum");
            textArray[i].setTextColor(Color.BLACK);
            textArray[i].setPadding(5, 5, 5, 5);
           // tr_head[i].addView(textArray[i]);
            tr_head[i].addView(textArray[i]);

            //TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            //row.setLayoutParams(lp);
            //TextView textView = new TextView(this);
            //textView.setText("Name" + " " + i);
           // EditText editText = new EditText(this);
           // row.addView(i+"");
            //tableLayoutRecord.addView(row, i);

            tableLayoutRecord.addView(tr_head[i]);
        }*/






           /* for(Map<String, String> map: mylist1) {

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Position Map - "+Sunlevel);
                for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                    //
                    key = mapEntry.getKey();
                    value = mapEntry.getValue();

                    // Log.d("Direct", "Key: -"+key+"Value: -"+value + " Position - "+mapEntryIndex);
                    for( int i=0; i<map.entrySet().size(); i++) {
                        int mapEntryIndex= map.entrySet().size();
                        TableRow tr = new TableRow(this);
                       int index=mapEntry.getKey().indexOf(i);
                        if ( i == mapEntryIndex ) {

                            tr.setLayoutParams(getLayoutParams());
                            tr.addView(getTextView(0, key, Color.BLACK, Typeface.BOLD, Color.BLUE));
                            //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                            //tr.addView();
                            tableLayoutRecord.addView(tr, getTblLayoutParams());
                             //break;
                        } else {
                            break;
                        }

                    }
                    break;

                }
                //break;
            }*/


    }

    /**
     * This function add and show the dynamic data of
     * the Upgrade Report in the table
     *
     **/
    public void addUpgradeReportTableData(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;

        // Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        //Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

            }
            tableLayout.addView(tableRow);

        }

    }

    /* GetWallet Report Detail Api*/
    private void getDynamicWalletReportDetail(String walletType){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        WalletReportRequest Request = new WalletReportRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_WALLET_REPORT_Dy);
        Request.setPasswd(SharedPrefrence_Business.getPassword(this));
        Request.setUserid(SharedPrefrence_Business.getUserID(this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        Request.setFromno(String.valueOf(from));
        Request.setTono(String.valueOf(to));
        Request.setWallettype(walletType);

        Call<Dy_WalletReportReponse> downlineDetailResponseCall
                = NetworkClient1.getInstance(this).create(WalletServices.class).fetchDynamicWalletReport(Request,strApiKey);

        downlineDetailResponseCall.enqueue(new Callback<Dy_WalletReportReponse>() {
            @Override
            public void onResponse(Call<Dy_WalletReportReponse> call, Response<Dy_WalletReportReponse> response) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    Dy_WalletReportReponse Response=new Dy_WalletReportReponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {

                            if(Response.getWallet() != null && Response.getWallet().size() > 0){
                                linearLayoutNoRecord.setVisibility(View.GONE);
                                layoutWalletStatus.setVisibility(View.VISIBLE);
                                //layoutDate.setVisibility(View.GONE);
                                linearLayoutTable.setVisibility(View.VISIBLE);
                                linearLayoutNext.setVisibility(View.VISIBLE);
                                //txtRecord.setText("Record-"+Response.getRecordcount());
                                totalRecord= Integer.parseInt(Response.getRecordcount());
                                txtRecord.setText(" Total Record : - " +String.valueOf(totalRecord));
                                //textViewRecord.setTextSize(12);
                                ArrayList<Map<String ,String>> walletList= Response.getWallet();
                                ArrayList<Map<String ,String>> walletStatus= Response.getStatus();

                                // Call Method for show Wallet STatus
                                createWalletReportStausDetail(walletStatus);

                                if(from == 1){
                                    addWalletReportTableHeaders(walletList);
                                    addWalletReportTableData(walletList);
                                    //;createMyDirectSummeryDetail(mapsSummery);
                                }
                                else {
                                    addWalletReportTableData(walletList);
                                }

                            }
                            else {
                                layoutWalletStatus.setVisibility(View.GONE);

                                linearLayoutNoRecord.setVisibility(View.VISIBLE);
                                linearLayoutTable.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.GONE);
                                txtRecord.setText("Record-"+Response.getRecordcount());
                            }

                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(CommonReportActivity.this,Response.getMsg());
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
                    }
                    else {
                        String msg="Something went wrong.";
                        Toast.makeText(CommonReportActivity.this,msg,Toast.LENGTH_SHORT).show();
                        blankValueFromSharePreference(CommonReportActivity.this,msg);
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Dy_WalletReportReponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
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

    /* This function for show Wallet Report Status
     * dynamically
     * */
    public void createWalletReportStausDetail(final ArrayList<Map<String,String>> walletStatus) {
        /*Set Dynamically add Linear Layout,TextView,CheckBox*/

        ArrayList<Map<String, String>> myWalletStatuslist = walletStatus;
        Map<String, String> map = new HashMap<String, String>();
        //myTeamlist=myTeam;
        layoutWalletStatus.removeAllViews();

        for (int i = 0; i < myWalletStatuslist.size(); i++) {
            map = myWalletStatuslist.get(i);
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(this);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(2);


            TextView txtkey=new TextView(this);
            txtkey.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            txtkey.setText(String.valueOf( entry.getKey()).toUpperCase());

            txtkey.setTypeface(null, Typeface.NORMAL);
            txtkey.setTextColor(getResources().getColor(R.color.black));
            txtkey.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_default));
            txtkey.setGravity(Gravity.LEFT);

            linearLayout.setLayoutParams(getLayoutParams());
            linearLayout.addView(txtkey);

            TextView txtvalue=new TextView(this);
            txtvalue.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            txtvalue.setText(String.valueOf( entry.getValue()).toUpperCase());

            txtvalue.setTypeface(null, Typeface.NORMAL);
            txtvalue.setTextColor(getResources().getColor(R.color.black));
            txtvalue.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_default));
            txtvalue.setGravity(Gravity.RIGHT);

            linearLayout.setLayoutParams(getLayoutParams());
            linearLayout.addView(txtvalue);

            layoutWalletStatus.addView(linearLayout);

        }

        /*for (int i = 0; i < myTeamlist.size(); i++)
        {
            //Map<String, String> map =myTeamlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(2);

            for(Map.Entry<String, String> obj : myTeamlist.entrySet()){
                if(i==0){
                    //Object objKey=map.get(obj);
                    //obj.getKey()
                    Log.d("Key - ", String.valueOf(obj.getKey()));
                    TableRow tr = new TableRow(context);


                    TextView txtHeader=new TextView(context);
                    txtHeader.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    txtHeader.setText("Particular");

                    txtHeader.setTypeface(null, Typeface.BOLD);
                    txtHeader.setTextColor(getResources().getColor(R.color.black));
                    txtHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));
                    txtHeader.setGravity(Gravity.LEFT);

                    linearLayout.setLayoutParams(getLayoutParams());
                    linearLayout.addView(txtHeader);
                }
            }

            layoutMyTeamDetail.addView(linearLayout);

        }*/


      /*  for (int i = 0; i < myTeamlist.size(); i++)
        {
            //Map<String, String> map =myTeamlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(2);

            for(Map.Entry<String, String> map : myTeamlist.entrySet()){

                Object objKey=map.getValue();
                //Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(context);

                TextView txtName=new TextView(context);
                txtName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

                txtName.setText(String.valueOf(objKey));
                *//*if(obj.equals("Total"))
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                else
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);*//*
                txtName.setTextColor(getResources().getColor(R.color.black));
                txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));
                txtName.setGravity(Gravity.LEFT);

                linearLayout.setLayoutParams(getLayoutParams());
                linearLayout.addView(txtName);

            }

            layoutMyTeamDetail.addView(linearLayout);

        }*/

    }

    /**
     * This function add and show the headers to the dynamic
     * Wallet Report Table Data
     **/
    public void addWalletReportTableHeaders(ArrayList<Map<String, String>> walletList ) {
        tableLayout.setVisibility(View.VISIBLE);
        tableLayout.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=walletList;
        String key="";
        String value="";
        // int i;

        int count=0;
        Map<String, String> map =mylist1.get(0);
            /*Map<String, String> map =mylist1.get(0);

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Key - "+Sunlevel);
                for(Object obj : map.keySet()){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(getLayoutParams());
                    tr.addView(getTextView(0, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                    //tr.addView();
                    tableLayoutRecord.addView(tr, getTblLayoutParams());
                }*/
        TextView[] textArray = new TextView[mylist1.size()];
        TableRow[] tr_head = new TableRow[mylist1.size()];
        Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < map.size(); i++)
        {
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            /*TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));*/

            for(Object obj : map.keySet()){

                if(i==0){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    tableRow.addView(getTextView(i, String.valueOf(obj).toUpperCase(), Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
                    //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

                    // tableLayoutRecord.addView(tableRow);
                }
               /* else {
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    //tableRow.addView(getTextView(i, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));
                }*/


                //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                //tr.addView();
                // tableLayoutRecord.addView(tr, getTblLayoutParams());
                //tableLayoutRecord.addView(tableRow);
            }

           /* for (int j = i; j<=i; j++)
            {

                Object objKeySet=map.keySet().toArray()[j];
                Object objKey=map.keySet();
                Log.d("Key - ", String.valueOf(objKeySet));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKeySet), Color.BLACK, Typeface.BOLD, Color.BLUE));






                //textView.setPadding(50, 50, 50, 50);

               // tableRow.addView(textView);

            }*/

            // add the TableRow to the TableLayout

            tableLayout.addView(tableRow);
            // table.addView(row, new TableLayout.LayoutParams(
            // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }


        /*for (int i = 0; i < 5; i++) {

            TableRow row = new TableRow(this);

            //Create the tablerows
            tr_head[i] = new TableRow(this);
            tr_head[i].setId(i+1);
            tr_head[i].setBackgroundColor(Color.GRAY);
            tr_head[i].setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // Here create the TextView dynamically

            textArray[i] = new TextView(this);
            textArray[i].setId(i+111);
            textArray[i].setText("Coloum");
            textArray[i].setTextColor(Color.BLACK);
            textArray[i].setPadding(5, 5, 5, 5);
           // tr_head[i].addView(textArray[i]);
            tr_head[i].addView(textArray[i]);

            //TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            //row.setLayoutParams(lp);
            //TextView textView = new TextView(this);
            //textView.setText("Name" + " " + i);
           // EditText editText = new EditText(this);
           // row.addView(i+"");
            //tableLayoutRecord.addView(row, i);

            tableLayoutRecord.addView(tr_head[i]);
        }*/






           /* for(Map<String, String> map: mylist1) {

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Position Map - "+Sunlevel);
                for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                    //
                    key = mapEntry.getKey();
                    value = mapEntry.getValue();

                    // Log.d("Direct", "Key: -"+key+"Value: -"+value + " Position - "+mapEntryIndex);
                    for( int i=0; i<map.entrySet().size(); i++) {
                        int mapEntryIndex= map.entrySet().size();
                        TableRow tr = new TableRow(this);
                       int index=mapEntry.getKey().indexOf(i);
                        if ( i == mapEntryIndex ) {

                            tr.setLayoutParams(getLayoutParams());
                            tr.addView(getTextView(0, key, Color.BLACK, Typeface.BOLD, Color.BLUE));
                            //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                            //tr.addView();
                            tableLayoutRecord.addView(tr, getTblLayoutParams());
                             //break;
                        } else {
                            break;
                        }

                    }
                    break;

                }
                //break;
            }*/


    }

    /**
     * This function add and show the dynamic data of
     * the Wallet Report in the table Content
     *
     **/
    public void addWalletReportTableData(ArrayList<Map<String, String>> walletList ) {
        tableLayout.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=walletList;

        // Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        //Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            /*TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));*/

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

            }
            tableLayout.addView(tableRow);

        }

    }


    /* Get Wallet Request Detail Api*/
    private void getWalletReqReportDetail(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseFromToRequest Request = new BaseFromToRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_WALLET_REQUEST_DETAIL_DY);
        Request.setPasswd(SharedPrefrence_Business.getPassword(this));
        Request.setUserid(SharedPrefrence_Business.getUserID(this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        Request.setFromno(String.valueOf(from));
        Request.setTono(String.valueOf(to));
        //Request.setWallettype(type);

        Call<Dy_WalletRequesDetailResponse> downlineDetailResponseCall
                = NetworkClient1.getInstance(this).create(WalletServices.class).fetchDynamicWalletRequestDetail(Request,strApiKey);

        downlineDetailResponseCall.enqueue(new Callback<Dy_WalletRequesDetailResponse>() {
            @Override
            public void onResponse(Call<Dy_WalletRequesDetailResponse> call, Response<Dy_WalletRequesDetailResponse> response) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    Dy_WalletRequesDetailResponse Response=new Dy_WalletRequesDetailResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {

                            if(Response.getWalletrequestdetail() != null && Response.getWalletrequestdetail().size() > 0){
                                linearLayoutNoRecord.setVisibility(View.GONE);
                                layoutWalletStatus.setVisibility(View.VISIBLE);
                                //layoutDate.setVisibility(View.GONE);
                                linearLayoutTable.setVisibility(View.VISIBLE);
                                linearLayoutNext.setVisibility(View.VISIBLE);
                                //txtRecord.setText("Record-"+Response.getRecordcount());
                                totalRecord= Integer.parseInt(Response.getRecordcount());
                                txtRecord.setText(" Total Record : - " +String.valueOf(totalRecord));
                                //textViewRecord.setTextSize(12);
                                ArrayList<Map<String ,String>> walletReqList= Response.getWalletrequestdetail();
                                // ArrayList<Map<String ,String>> walletStatus= Response.getStatus();



                                if(from == 1){
                                    addWalletRequestDetailTableHeaders(walletReqList);
                                    addWalletRequestDetailTableData(walletReqList);
                                    //;createMyDirectSummeryDetail(mapsSummery);
                                }
                                else {
                                    addWalletRequestDetailTableData(walletReqList);
                                }

                            }
                            else {
                                layoutWalletStatus.setVisibility(View.GONE);
                                //layoutDate.setVisibility(View.GONE);
                                linearLayoutNoRecord.setVisibility(View.VISIBLE);
                                linearLayoutTable.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.GONE);
                                txtRecord.setText("Record-"+Response.getRecordcount());
                            }

                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(CommonReportActivity.this,Response.getMsg());
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
                    }
                    else {
                        String msg="Something went wrong.";
                        Toast.makeText(CommonReportActivity.this,msg,Toast.LENGTH_SHORT).show();
                        blankValueFromSharePreference(CommonReportActivity.this,msg);
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Dy_WalletRequesDetailResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
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


    /**
     * This function add and show the headers to the dynamic
     * Wallet Request Detail Table Data
     **/
    public void addWalletRequestDetailTableHeaders(ArrayList<Map<String, String>> walletList ) {
        tableLayout.setVisibility(View.VISIBLE);
        tableLayout.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=walletList;
        String key="";
        String value="";
        // int i;

        int count=0;
        Map<String, String> map =mylist1.get(0);
            /*Map<String, String> map =mylist1.get(0);

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Key - "+Sunlevel);
                for(Object obj : map.keySet()){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(getLayoutParams());
                    tr.addView(getTextView(0, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                    //tr.addView();
                    tableLayoutRecord.addView(tr, getTblLayoutParams());
                }*/
        TextView[] textArray = new TextView[mylist1.size()];
        TableRow[] tr_head = new TableRow[mylist1.size()];
        Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < map.size(); i++)
        {
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            /*TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));*/

            for(Object obj : map.keySet()){

                if(i==0){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    tableRow.addView(getTextView(i, String.valueOf(obj).toUpperCase(), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                    //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

                    // tableLayoutRecord.addView(tableRow);
                }
               /* else {
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    //tableRow.addView(getTextView(i, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));
                }*/


                //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                //tr.addView();
                // tableLayoutRecord.addView(tr, getTblLayoutParams());
                //tableLayoutRecord.addView(tableRow);
            }

           /* for (int j = i; j<=i; j++)
            {

                Object objKeySet=map.keySet().toArray()[j];
                Object objKey=map.keySet();
                Log.d("Key - ", String.valueOf(objKeySet));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKeySet), Color.BLACK, Typeface.BOLD, Color.BLUE));






                //textView.setPadding(50, 50, 50, 50);

               // tableRow.addView(textView);

            }*/

            // add the TableRow to the TableLayout

            tableLayout.addView(tableRow);
            // table.addView(row, new TableLayout.LayoutParams(
            // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }


        /*for (int i = 0; i < 5; i++) {

            TableRow row = new TableRow(this);

            //Create the tablerows
            tr_head[i] = new TableRow(this);
            tr_head[i].setId(i+1);
            tr_head[i].setBackgroundColor(Color.GRAY);
            tr_head[i].setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // Here create the TextView dynamically

            textArray[i] = new TextView(this);
            textArray[i].setId(i+111);
            textArray[i].setText("Coloum");
            textArray[i].setTextColor(Color.BLACK);
            textArray[i].setPadding(5, 5, 5, 5);
           // tr_head[i].addView(textArray[i]);
            tr_head[i].addView(textArray[i]);

            //TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            //row.setLayoutParams(lp);
            //TextView textView = new TextView(this);
            //textView.setText("Name" + " " + i);
           // EditText editText = new EditText(this);
           // row.addView(i+"");
            //tableLayoutRecord.addView(row, i);

            tableLayoutRecord.addView(tr_head[i]);
        }*/






           /* for(Map<String, String> map: mylist1) {

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Position Map - "+Sunlevel);
                for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                    //
                    key = mapEntry.getKey();
                    value = mapEntry.getValue();

                    // Log.d("Direct", "Key: -"+key+"Value: -"+value + " Position - "+mapEntryIndex);
                    for( int i=0; i<map.entrySet().size(); i++) {
                        int mapEntryIndex= map.entrySet().size();
                        TableRow tr = new TableRow(this);
                       int index=mapEntry.getKey().indexOf(i);
                        if ( i == mapEntryIndex ) {

                            tr.setLayoutParams(getLayoutParams());
                            tr.addView(getTextView(0, key, Color.BLACK, Typeface.BOLD, Color.BLUE));
                            //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                            //tr.addView();
                            tableLayoutRecord.addView(tr, getTblLayoutParams());
                             //break;
                        } else {
                            break;
                        }

                    }
                    break;

                }
                //break;
            }*/


    }

    /**
     * This function add and show the dynamic data of
     * the Wallet Request Detail in the table Content
     *
     **/
    public void addWalletRequestDetailTableData(ArrayList<Map<String, String>> walletList ) {
        tableLayout.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=walletList;

        // Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        //Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            /*TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));*/

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));

                if(obj.equals("Imagepath")){
                    final ImageView imageView= new ImageView(this);

                    // imageView.setImageResource(Integer.parseInt(achivedDetails[i].getStatus()));
                    if(String.valueOf(objKey).equals("")){
                        imageView.setImageResource(R.mipmap.login_logo);
                    }
                    else {
                        Picasso.with(this).load(String.valueOf(objKey)).placeholder(R.mipmap.login_logo).into(imageView);

                    }

                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(80, 100);
                    layoutParams.gravity= Gravity.CENTER;
                    layoutParams.setMargins(5,5,5,5);
                    imageView.setLayoutParams(layoutParams);



                    //tv14.setBackgroundColor(getResources().getColor(R.color.gray));

                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageView.setBackgroundColor(getResources().getColor(R.color.gray3));

                    imageView.setPadding(10, 10, 10, 10);
                    tableRow.setLayoutParams(getLayoutParams());
                    tableRow.addView(imageView,80,100);

                    final int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(imageView.getDrawable()==null){

                            }
                            else {
                                final Dialog dialog = new Dialog(CommonReportActivity.this);
                                // Include dialog.xml file
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.showimage_dialog);
                                // Set dialog title


                                // set values for custom dialog components - text, image and
                                // button

                                ImageView image = (ImageView) dialog
                                        .findViewById(R.id.crossexpand);
                                ImageView expandimage = (ImageView) dialog
                                        .findViewById(R.id.expandedimage);
                                Picasso.with(context).load(String.valueOf(objKey)).placeholder(R.mipmap.login_logo).into(expandimage);

                                // if decline button is clicked, close the custom dialog
                                dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);


                                image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Close dialog

                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }



                        }
                    });

                }
                else{
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                    //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));
                }

            }
            tableLayout.addView(tableRow);

        }

    }

    /*Get Bank Withdrawal Detail Response Api*/
    private void getWithdrawalDetail(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();


        BaseFromToRequest Request=new BaseFromToRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_WITHDRAWAL_DETAIL);
        Request.setPasswd(SharedPrefrence_Business.getPassword(this));
        Request.setUserid(SharedPrefrence_Business.getUserID(this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        Request.setFromno(String.valueOf(from));
        Request.setTono(String.valueOf(to));


        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(Request);
        Log.e("ReqLevelWiseReport:", Get_Paramenter);

        Call<Dy_WithdrawalDetailResponse> reportResponseCall
                = NetworkClient1.getInstance(this).create(WalletServices.class).fetchDynamicWithdrawalDetail(Request,strApiKey);

        reportResponseCall.enqueue(new Callback<Dy_WithdrawalDetailResponse>() {
            @Override
            public void onResponse(Call<Dy_WithdrawalDetailResponse> call, Response<Dy_WithdrawalDetailResponse> response) {
                //hideProgressDialog();
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    //DMyDirectResponse Response=new DMyDirectResponse();
                    Dy_WithdrawalDetailResponse reportResponse=new Dy_WithdrawalDetailResponse();

                    reportResponse=response.body();
                    if(reportResponse != null){
                        if (reportResponse.getResponse().equals("OK")) {


                            if(reportResponse.getBankwithdrawaldetail()!= null && reportResponse.getBankwithdrawaldetail().size() > 0){

                                linearLayoutNoRecord.setVisibility(View.GONE);
                                linearLayoutTable.setVisibility(View.VISIBLE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.VISIBLE);

                                //totalRecord = Integer.parseInt(reportResponse.getRecordcount());
                                //txtRecord.setText("Record : - " +String.valueOf(totalRecord));
                                //textViewRecord.setTextSize(12);
                                ArrayList<Map<String ,String>> downlineDtailmaps= reportResponse.getBankwithdrawaldetail();
                                //ArrayList<Map<String ,String>> mapsLevelDirectSummery=dy_downdetailResponse.getDirectssummary();


                                //int totcount= Integer.parseInt(reportResponse.getRecordcount());
                                // GetHashMapDirectValue(directmaps);
                                //GetHashMapSummeryValue(maps1);
                                if(from == 1){
                                    addWithdrawalDetailTableHeaders(downlineDtailmaps);
                                    addWithdrawlDtailTableData(downlineDtailmaps);

                                }
                                else {
                                    addWithdrawlDtailTableData(downlineDtailmaps);
                                }

                            }
                            else {
                                linearLayoutNoRecord.setVisibility(View.VISIBLE);
                                linearLayoutTable.setVisibility(View.GONE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.GONE);
                            }


                        }
                        else if(reportResponse.getResponse().equals("FAILED") && reportResponse.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(CommonReportActivity.this,reportResponse.getMsg());
                        }
                        else if(reportResponse.getResponse().equals("FAILED")) {
                            String msg=reportResponse.getResponse()+ " "+"Something went wrong..";
                            Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
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
                        String msg="Something went wrong. Please try again.";
                        blankValueFromSharePreference(CommonReportActivity.this,msg);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Dy_WithdrawalDetailResponse> call, Throwable t) {

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

    /**
     * This function add and show the headers to the table
     * content of Withdrawal detail
     **/
    public void addWithdrawalDetailTableHeaders(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        tableLayout.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;
        String key="";
        String value="";
        // int i;

        int count=0;
        Map<String, String> map =mylist1.get(0);
            /*Map<String, String> map =mylist1.get(0);

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Key - "+Sunlevel);
                for(Object obj : map.keySet()){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(getLayoutParams());
                    tr.addView(getTextView(0, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                    //tr.addView();
                    tableLayoutRecord.addView(tr, getTblLayoutParams());
                }*/
        TextView[] textArray = new TextView[mylist1.size()];
        TableRow[] tr_head = new TableRow[mylist1.size()];
        Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < map.size(); i++)
        {
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                if(i==0){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    tableRow.addView(getTextView(i, String.valueOf(obj).toUpperCase(), Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
                    //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

                    // tableLayoutRecord.addView(tableRow);
                }
               /* else {
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    //tableRow.addView(getTextView(i, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));
                }*/


                //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                //tr.addView();
                // tableLayoutRecord.addView(tr, getTblLayoutParams());
                //tableLayoutRecord.addView(tableRow);
            }

           /* for (int j = i; j<=i; j++)
            {

                Object objKeySet=map.keySet().toArray()[j];
                Object objKey=map.keySet();
                Log.d("Key - ", String.valueOf(objKeySet));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKeySet), Color.BLACK, Typeface.BOLD, Color.BLUE));






                //textView.setPadding(50, 50, 50, 50);

               // tableRow.addView(textView);

            }*/

            // add the TableRow to the TableLayout

            tableLayout.addView(tableRow);
            // table.addView(row, new TableLayout.LayoutParams(
            // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }


        /*for (int i = 0; i < 5; i++) {

            TableRow row = new TableRow(this);

            //Create the tablerows
            tr_head[i] = new TableRow(this);
            tr_head[i].setId(i+1);
            tr_head[i].setBackgroundColor(Color.GRAY);
            tr_head[i].setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // Here create the TextView dynamically

            textArray[i] = new TextView(this);
            textArray[i].setId(i+111);
            textArray[i].setText("Coloum");
            textArray[i].setTextColor(Color.BLACK);
            textArray[i].setPadding(5, 5, 5, 5);
           // tr_head[i].addView(textArray[i]);
            tr_head[i].addView(textArray[i]);

            //TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            //row.setLayoutParams(lp);
            //TextView textView = new TextView(this);
            //textView.setText("Name" + " " + i);
           // EditText editText = new EditText(this);
           // row.addView(i+"");
            //tableLayoutRecord.addView(row, i);

            tableLayoutRecord.addView(tr_head[i]);
        }*/






           /* for(Map<String, String> map: mylist1) {

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Position Map - "+Sunlevel);
                for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                    //
                    key = mapEntry.getKey();
                    value = mapEntry.getValue();

                    // Log.d("Direct", "Key: -"+key+"Value: -"+value + " Position - "+mapEntryIndex);
                    for( int i=0; i<map.entrySet().size(); i++) {
                        int mapEntryIndex= map.entrySet().size();
                        TableRow tr = new TableRow(this);
                       int index=mapEntry.getKey().indexOf(i);
                        if ( i == mapEntryIndex ) {

                            tr.setLayoutParams(getLayoutParams());
                            tr.addView(getTextView(0, key, Color.BLACK, Typeface.BOLD, Color.BLUE));
                            //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                            //tr.addView();
                            tableLayoutRecord.addView(tr, getTblLayoutParams());
                             //break;
                        } else {
                            break;
                        }

                    }
                    break;

                }
                //break;
            }*/


    }

    /**
     * This function add and show the dynamic data of
     * the Withdrawal in the table
     *
     **/
    public void addWithdrawlDtailTableData(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;

        // Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        //Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

            }
            tableLayout.addView(tableRow);

        }

    }


    //  Downline Purchase Api Calling
    //  Method for fetching data and get Details
    private void getDynamicDownlinePurchaseDetail(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();


        DownlinePurchaseRequest downlinePurchaseRequest = new DownlinePurchaseRequest();

        /*Set value in Entity class*/
        downlinePurchaseRequest.setReqtype(ApiConstants.REQUEST_DOWNLINE_PURCHASE_DY);
        downlinePurchaseRequest.setPasswd(SharedPrefrence_Business.getPassword(this));
        downlinePurchaseRequest.setUserid(SharedPrefrence_Business.getUserID(this));
        downlinePurchaseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(this));

        downlinePurchaseRequest.setFromno(String.valueOf(from));
        downlinePurchaseRequest.setTono(String.valueOf(to));
        downlinePurchaseRequest.setLegno(strGroup);
        downlinePurchaseRequest.setLevel(strLevelID);
        downlinePurchaseRequest.setType(type);
        downlinePurchaseRequest.setFromdate(String.valueOf(strFromDate));
        downlinePurchaseRequest.setTodate(String.valueOf(strToDate));
        //downlinePurchaseRequest.setType(stringType);

        Call<Dy_DownlinePurchaseResponse> downlinePurchaseResponseCall=
                NetworkClient1.getInstance(this).create(MyTeamService.class).fetchDynamicDownlinePurchaseDetail(downlinePurchaseRequest,strApiKey);

        downlinePurchaseResponseCall.enqueue(new Callback<Dy_DownlinePurchaseResponse>() {
            @Override
            public void onResponse(Call<Dy_DownlinePurchaseResponse> call, Response<Dy_DownlinePurchaseResponse> response) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {
                    Dy_DownlinePurchaseResponse Response=new Dy_DownlinePurchaseResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {


                            if(Response.getDownlinepurchase()!= null && Response.getDownlinepurchase().size() > 0){

                                linearLayoutNoRecord.setVisibility(View.GONE);
                                linearLayoutTable.setVisibility(View.VISIBLE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.VISIBLE);

                                totalRecord = Integer.parseInt(Response.getRecordcount());
                                txtRecord.setText("Record : - " +String.valueOf(totalRecord));
                                //textViewRecord.setTextSize(12);
                                ArrayList<Map<String ,String>> downlineDtailmaps= Response.getDownlinepurchase();
                                //ArrayList<Map<String ,String>> mapsLevelDirectSummery=dy_downdetailResponse.getDirectssummary();


                                //int totcount= Integer.parseInt(reportResponse.getRecordcount());
                                // GetHashMapDirectValue(directmaps);
                                //GetHashMapSummeryValue(maps1);
                                if(from == 1){
                                    addDownlinePurchaseTableHeaders(downlineDtailmaps);
                                    addDownlinePurchaseTableData(downlineDtailmaps);

                                }
                                else {
                                    addDownlinePurchaseTableData(downlineDtailmaps);
                                }

                            }
                            else {
                                linearLayoutNoRecord.setVisibility(View.VISIBLE);
                                linearLayoutTable.setVisibility(View.GONE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.GONE);
                            }


                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(CommonReportActivity.this,Response.getMsg());
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
                    }
                    else {
                        String msg="Something went wrong. Please try again.";
                        blankValueFromSharePreference(CommonReportActivity.this,msg);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Dy_DownlinePurchaseResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(CommonReportActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    /**
     * This function add and show the headers to the table
     * content of Withdrawal detail
     **/
    public void addDownlinePurchaseTableHeaders(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        tableLayout.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;
        String key="";
        String value="";
        // int i;

        int count=0;
        Map<String, String> map =mylist1.get(0);
            /*Map<String, String> map =mylist1.get(0);

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Key - "+Sunlevel);
                for(Object obj : map.keySet()){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(getLayoutParams());
                    tr.addView(getTextView(0, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                    //tr.addView();
                    tableLayoutRecord.addView(tr, getTblLayoutParams());
                }*/
        TextView[] textArray = new TextView[mylist1.size()];
        TableRow[] tr_head = new TableRow[mylist1.size()];
        Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < map.size(); i++)
        {
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                if(i==0){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    tableRow.addView(getTextView(i, String.valueOf(obj).toUpperCase(), Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
                    //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

                    // tableLayoutRecord.addView(tableRow);
                }
               /* else {
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    //tableRow.addView(getTextView(i, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));
                }*/


                //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                //tr.addView();
                // tableLayoutRecord.addView(tr, getTblLayoutParams());
                //tableLayoutRecord.addView(tableRow);
            }

           /* for (int j = i; j<=i; j++)
            {

                Object objKeySet=map.keySet().toArray()[j];
                Object objKey=map.keySet();
                Log.d("Key - ", String.valueOf(objKeySet));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKeySet), Color.BLACK, Typeface.BOLD, Color.BLUE));






                //textView.setPadding(50, 50, 50, 50);

               // tableRow.addView(textView);

            }*/

            // add the TableRow to the TableLayout

            tableLayout.addView(tableRow);
            // table.addView(row, new TableLayout.LayoutParams(
            // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }


        /*for (int i = 0; i < 5; i++) {

            TableRow row = new TableRow(this);

            //Create the tablerows
            tr_head[i] = new TableRow(this);
            tr_head[i].setId(i+1);
            tr_head[i].setBackgroundColor(Color.GRAY);
            tr_head[i].setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // Here create the TextView dynamically

            textArray[i] = new TextView(this);
            textArray[i].setId(i+111);
            textArray[i].setText("Coloum");
            textArray[i].setTextColor(Color.BLACK);
            textArray[i].setPadding(5, 5, 5, 5);
           // tr_head[i].addView(textArray[i]);
            tr_head[i].addView(textArray[i]);

            //TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            //row.setLayoutParams(lp);
            //TextView textView = new TextView(this);
            //textView.setText("Name" + " " + i);
           // EditText editText = new EditText(this);
           // row.addView(i+"");
            //tableLayoutRecord.addView(row, i);

            tableLayoutRecord.addView(tr_head[i]);
        }*/






           /* for(Map<String, String> map: mylist1) {

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Position Map - "+Sunlevel);
                for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                    //
                    key = mapEntry.getKey();
                    value = mapEntry.getValue();

                    // Log.d("Direct", "Key: -"+key+"Value: -"+value + " Position - "+mapEntryIndex);
                    for( int i=0; i<map.entrySet().size(); i++) {
                        int mapEntryIndex= map.entrySet().size();
                        TableRow tr = new TableRow(this);
                       int index=mapEntry.getKey().indexOf(i);
                        if ( i == mapEntryIndex ) {

                            tr.setLayoutParams(getLayoutParams());
                            tr.addView(getTextView(0, key, Color.BLACK, Typeface.BOLD, Color.BLUE));
                            //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                            //tr.addView();
                            tableLayoutRecord.addView(tr, getTblLayoutParams());
                             //break;
                        } else {
                            break;
                        }

                    }
                    break;

                }
                //break;
            }*/


    }

    /**
     * This function add and show the dynamic data of
     * the Withdrawal in the table
     *
     **/
    public void addDownlinePurchaseTableData(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;

        // Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        //Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

            }
            tableLayout.addView(tableRow);

        }

    }


    // My Rewards Api Calling
    //  Method for fetching data and get Details
    private void getMyRewardsDetail(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest request = new BaseRequest();
        request.setReqtype(ApiConstants.REQUEST_REWARD_ACHIEVER_DY);
        request.setPasswd(SharedPrefrence_Business.getPassword(this));
        request.setUserid(SharedPrefrence_Business.getUserID(this));
        request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        //downlinePurchaseRequest.setType(stringType);

        Call<Dy_MyRewardResponse> downlinePurchaseResponseCall=
                NetworkClient1.getInstance(this).create(MyTeamService.class).fetchReward(request,strApiKey);

        downlinePurchaseResponseCall.enqueue(new Callback<Dy_MyRewardResponse>() {
            @Override
            public void onResponse(Call<Dy_MyRewardResponse> call, Response<Dy_MyRewardResponse> response) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {
                    Dy_MyRewardResponse Response=new Dy_MyRewardResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {


                            if(Response.getRewards()!= null && Response.getRewards().size() > 0){

                                linearLayoutNoRecord.setVisibility(View.GONE);
                                linearLayoutTable.setVisibility(View.VISIBLE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.VISIBLE);

                                //totalRecord = Integer.parseInt(reportResponse.getRecordcount());
                                //txtRecord.setText("Record : - " +String.valueOf(totalRecord));
                                //textViewRecord.setTextSize(12);
                                ArrayList<Map<String ,String>> downlineDtailmaps= Response.getRewards();
                                //ArrayList<Map<String ,String>> mapsLevelDirectSummery=dy_downdetailResponse.getDirectssummary();


                                //int totcount= Integer.parseInt(reportResponse.getRecordcount());
                                // GetHashMapDirectValue(directmaps);
                                //GetHashMapSummeryValue(maps1);
                                if(from == 1){
                                    addMyrewardTableHeaders(downlineDtailmaps);
                                    addMyRewardsTableData(downlineDtailmaps);

                                }
                                else {
                                    addMyRewardsTableData(downlineDtailmaps);
                                }

                            }
                            else {
                                linearLayoutNoRecord.setVisibility(View.VISIBLE);
                                linearLayoutTable.setVisibility(View.GONE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.GONE);
                            }


                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(CommonReportActivity.this,Response.getMsg());
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
                    }
                    else {
                        String msg="Something went wrong. Please try again.";
                        blankValueFromSharePreference(CommonReportActivity.this,msg);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Dy_MyRewardResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(CommonReportActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    /**
     * This function add and show the headers to the table
     * content of My Rewards
     **/
    public void addMyrewardTableHeaders(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        tableLayout.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;
        String key="";
        String value="";
        // int i;

        int count=0;
        Map<String, String> map =mylist1.get(0);
            /*Map<String, String> map =mylist1.get(0);

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Key - "+Sunlevel);
                for(Object obj : map.keySet()){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(getLayoutParams());
                    tr.addView(getTextView(0, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                    //tr.addView();
                    tableLayoutRecord.addView(tr, getTblLayoutParams());
                }*/
        TextView[] textArray = new TextView[mylist1.size()];
        TableRow[] tr_head = new TableRow[mylist1.size()];
        Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < map.size(); i++)
        {
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                if(i==0){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    tableRow.addView(getTextView(i, String.valueOf(obj).toUpperCase(), Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
                    //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

                    // tableLayoutRecord.addView(tableRow);
                }
               /* else {
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    //tableRow.addView(getTextView(i, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));
                }*/


                //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                //tr.addView();
                // tableLayoutRecord.addView(tr, getTblLayoutParams());
                //tableLayoutRecord.addView(tableRow);
            }

           /* for (int j = i; j<=i; j++)
            {

                Object objKeySet=map.keySet().toArray()[j];
                Object objKey=map.keySet();
                Log.d("Key - ", String.valueOf(objKeySet));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKeySet), Color.BLACK, Typeface.BOLD, Color.BLUE));






                //textView.setPadding(50, 50, 50, 50);

               // tableRow.addView(textView);

            }*/

            // add the TableRow to the TableLayout

            tableLayout.addView(tableRow);
            // table.addView(row, new TableLayout.LayoutParams(
            // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }


        /*for (int i = 0; i < 5; i++) {

            TableRow row = new TableRow(this);

            //Create the tablerows
            tr_head[i] = new TableRow(this);
            tr_head[i].setId(i+1);
            tr_head[i].setBackgroundColor(Color.GRAY);
            tr_head[i].setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // Here create the TextView dynamically

            textArray[i] = new TextView(this);
            textArray[i].setId(i+111);
            textArray[i].setText("Coloum");
            textArray[i].setTextColor(Color.BLACK);
            textArray[i].setPadding(5, 5, 5, 5);
           // tr_head[i].addView(textArray[i]);
            tr_head[i].addView(textArray[i]);

            //TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            //row.setLayoutParams(lp);
            //TextView textView = new TextView(this);
            //textView.setText("Name" + " " + i);
           // EditText editText = new EditText(this);
           // row.addView(i+"");
            //tableLayoutRecord.addView(row, i);

            tableLayoutRecord.addView(tr_head[i]);
        }*/






           /* for(Map<String, String> map: mylist1) {

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Position Map - "+Sunlevel);
                for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                    //
                    key = mapEntry.getKey();
                    value = mapEntry.getValue();

                    // Log.d("Direct", "Key: -"+key+"Value: -"+value + " Position - "+mapEntryIndex);
                    for( int i=0; i<map.entrySet().size(); i++) {
                        int mapEntryIndex= map.entrySet().size();
                        TableRow tr = new TableRow(this);
                       int index=mapEntry.getKey().indexOf(i);
                        if ( i == mapEntryIndex ) {

                            tr.setLayoutParams(getLayoutParams());
                            tr.addView(getTextView(0, key, Color.BLACK, Typeface.BOLD, Color.BLUE));
                            //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                            //tr.addView();
                            tableLayoutRecord.addView(tr, getTblLayoutParams());
                             //break;
                        } else {
                            break;
                        }

                    }
                    break;

                }
                //break;
            }*/


    }

    /**
     * This function add and show the dynamic data of
     * the My Rewards in the table
     *
     **/
    public void addMyRewardsTableData(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;

        // Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        //Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

            }
            tableLayout.addView(tableRow);

        }

    }


    // My Rewards Api Calling
    //  Method for fetching data and get Details
    private void getMonthlyRewardPoints(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseFromToRequest request = new BaseFromToRequest();
        request.setReqtype(ApiConstants.REQUEST_MONTHLY_REWARD_POINTS);
        request.setPasswd(SharedPrefrence_Business.getPassword(this));
        request.setUserid(SharedPrefrence_Business.getUserID(this));
        request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        request.setFrom(String.valueOf(from));
        request.setTo(String.valueOf(to));
        //downlinePurchaseRequest.setType(stringType);

        Call<Dy_MonthlyRewardPoints> downlinePurchaseResponseCall=
                NetworkClient1.getInstance(this).create(IncomeServices.class).fetchDynamicMonthlyRewardPoint(request,strApiKey);

        downlinePurchaseResponseCall.enqueue(new Callback<Dy_MonthlyRewardPoints>() {
            @Override
            public void onResponse(Call<Dy_MonthlyRewardPoints> call, Response<Dy_MonthlyRewardPoints> response) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {
                    Dy_MonthlyRewardPoints Response=new Dy_MonthlyRewardPoints();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {


                            if(Response.getMonthlyrewardpoints()!= null && Response.getMonthlyrewardpoints().size() > 0){

                                linearLayoutNoRecord.setVisibility(View.GONE);
                                linearLayoutTable.setVisibility(View.VISIBLE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.VISIBLE);
                                layoutMonthlyRewardPointTotal.setVisibility(View.VISIBLE);
                                totalRecord = Integer.parseInt(Response.getRecordcount());
                                txtRecord.setText("Record : - " +String.valueOf(totalRecord));
                                txtMonthRewrdPointTot.setText("Total Rewards Point : - " +Response.getTotalrewardpoints());
                                //textViewRecord.setTextSize(12);
                                ArrayList<Map<String ,String>> downlineDtailmaps= Response.getMonthlyrewardpoints();
                                //ArrayList<Map<String ,String>> mapsLevelDirectSummery=dy_downdetailResponse.getDirectssummary();


                                //int totcount= Integer.parseInt(reportResponse.getRecordcount());
                                // GetHashMapDirectValue(directmaps);
                                //GetHashMapSummeryValue(maps1);
                                if(from == 1){
                                    addMonthlyRewardPointsTableHeaders(downlineDtailmaps);
                                    addMonthlyRewardPointsTableData(downlineDtailmaps);

                                }
                                else {
                                    addMonthlyRewardPointsTableData(downlineDtailmaps);
                                }

                            }
                            else {
                                linearLayoutNoRecord.setVisibility(View.VISIBLE);
                                linearLayoutTable.setVisibility(View.GONE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.GONE);
                                layoutMonthlyRewardPointTotal.setVisibility(View.GONE);
                            }


                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(CommonReportActivity.this,Response.getMsg());
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
                    }
                    else {
                        String msg="Something went wrong. Please try again.";
                        blankValueFromSharePreference(CommonReportActivity.this,msg);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Dy_MonthlyRewardPoints> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(CommonReportActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    /**
     * This function add and show the headers to the table
     * content of My Rewards
     **/
    public void addMonthlyRewardPointsTableHeaders(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        tableLayout.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;
        String key="";
        String value="";
        // int i;

        int count=0;
        Map<String, String> map =mylist1.get(0);
            /*Map<String, String> map =mylist1.get(0);

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Key - "+Sunlevel);
                for(Object obj : map.keySet()){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(getLayoutParams());
                    tr.addView(getTextView(0, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                    //tr.addView();
                    tableLayoutRecord.addView(tr, getTblLayoutParams());
                }*/
        TextView[] textArray = new TextView[mylist1.size()];
        TableRow[] tr_head = new TableRow[mylist1.size()];
        Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < map.size(); i++)
        {
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                if(i==0){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    tableRow.addView(getTextView(i, String.valueOf(obj).toUpperCase(), Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
                    //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

                    // tableLayoutRecord.addView(tableRow);
                }
               /* else {
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    //tableRow.addView(getTextView(i, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));
                }*/


                //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                //tr.addView();
                // tableLayoutRecord.addView(tr, getTblLayoutParams());
                //tableLayoutRecord.addView(tableRow);
            }

           /* for (int j = i; j<=i; j++)
            {

                Object objKeySet=map.keySet().toArray()[j];
                Object objKey=map.keySet();
                Log.d("Key - ", String.valueOf(objKeySet));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKeySet), Color.BLACK, Typeface.BOLD, Color.BLUE));






                //textView.setPadding(50, 50, 50, 50);

               // tableRow.addView(textView);

            }*/

            // add the TableRow to the TableLayout

            tableLayout.addView(tableRow);
            // table.addView(row, new TableLayout.LayoutParams(
            // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }


        /*for (int i = 0; i < 5; i++) {

            TableRow row = new TableRow(this);

            //Create the tablerows
            tr_head[i] = new TableRow(this);
            tr_head[i].setId(i+1);
            tr_head[i].setBackgroundColor(Color.GRAY);
            tr_head[i].setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // Here create the TextView dynamically

            textArray[i] = new TextView(this);
            textArray[i].setId(i+111);
            textArray[i].setText("Coloum");
            textArray[i].setTextColor(Color.BLACK);
            textArray[i].setPadding(5, 5, 5, 5);
           // tr_head[i].addView(textArray[i]);
            tr_head[i].addView(textArray[i]);

            //TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            //row.setLayoutParams(lp);
            //TextView textView = new TextView(this);
            //textView.setText("Name" + " " + i);
           // EditText editText = new EditText(this);
           // row.addView(i+"");
            //tableLayoutRecord.addView(row, i);

            tableLayoutRecord.addView(tr_head[i]);
        }*/






           /* for(Map<String, String> map: mylist1) {

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Position Map - "+Sunlevel);
                for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                    //
                    key = mapEntry.getKey();
                    value = mapEntry.getValue();

                    // Log.d("Direct", "Key: -"+key+"Value: -"+value + " Position - "+mapEntryIndex);
                    for( int i=0; i<map.entrySet().size(); i++) {
                        int mapEntryIndex= map.entrySet().size();
                        TableRow tr = new TableRow(this);
                       int index=mapEntry.getKey().indexOf(i);
                        if ( i == mapEntryIndex ) {

                            tr.setLayoutParams(getLayoutParams());
                            tr.addView(getTextView(0, key, Color.BLACK, Typeface.BOLD, Color.BLUE));
                            //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                            //tr.addView();
                            tableLayoutRecord.addView(tr, getTblLayoutParams());
                             //break;
                        } else {
                            break;
                        }

                    }
                    break;

                }
                //break;
            }*/


    }

    /**
     * This function add and show the dynamic data of
     * the My Rewards in the table
     *
     **/
    public void addMyCommunityDetailTableData(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;

        // Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        //Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

            }
            tableLayout.addView(tableRow);

        }

    }


    // My Community Detail Api Calling
    //  Method for fetching data and get Details
    private void getMyCommunityDetailPoints(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseFromToRequest request = new BaseFromToRequest();
        request.setReqtype(ApiConstants.REQUEST_COMMUNITY_DETAIL);
        request.setPasswd(SharedPrefrence_Business.getPassword(this));
        request.setUserid(SharedPrefrence_Business.getUserID(this));
        request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        request.setFrom(String.valueOf(from));
        request.setTo(String.valueOf(to));
        //downlinePurchaseRequest.setType(stringType);

        Call<Dy_CommunityDetailResponse> downlinePurchaseResponseCall=
                NetworkClient1.getInstance(this).create(MyTeamService.class).fetchDynamicCommunityDetail(request,strApiKey);

        downlinePurchaseResponseCall.enqueue(new Callback<Dy_CommunityDetailResponse>() {
            @Override
            public void onResponse(Call<Dy_CommunityDetailResponse> call, Response<Dy_CommunityDetailResponse> response) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {
                    Dy_CommunityDetailResponse Response=new Dy_CommunityDetailResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {


                            if(Response.getReentrycommunitypayout()!= null && Response.getReentrycommunitypayout().size() > 0){

                                linearLayoutNoRecord.setVisibility(View.GONE);
                                linearLayoutTable.setVisibility(View.VISIBLE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.VISIBLE);
                                layoutMonthlyRewardPointTotal.setVisibility(View.VISIBLE);
                                totalRecord = Integer.parseInt(Response.getRecordcount());
                                txtRecord.setText("Record : - " +String.valueOf(totalRecord));
                                //txtMonthRewrdPointTot.setText("Total Rewards Point : - " +Response.getTotalrewardpoints());
                                //textViewRecord.setTextSize(12);
                                ArrayList<Map<String ,String>> details= Response.getReentrycommunitypayout();
                                //ArrayList<Map<String ,String>> mapsLevelDirectSummery=dy_downdetailResponse.getDirectssummary();


                                //int totcount= Integer.parseInt(reportResponse.getRecordcount());
                                // GetHashMapDirectValue(directmaps);
                                //GetHashMapSummeryValue(maps1);
                                if(from == 1){
                                    addMyCommunityDetailTableHeaders(details);
                                    addMyCommunityDetailTableData(details);

                                }
                                else {
                                    addMyCommunityDetailTableData(details);
                                }

                            }
                            else {
                                linearLayoutNoRecord.setVisibility(View.VISIBLE);
                                linearLayoutTable.setVisibility(View.GONE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.GONE);
                                layoutMonthlyRewardPointTotal.setVisibility(View.GONE);
                            }


                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(CommonReportActivity.this,Response.getMsg());
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
                    }
                    else {
                        String msg="Something went wrong. Please try again.";
                        blankValueFromSharePreference(CommonReportActivity.this,msg);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Dy_CommunityDetailResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(CommonReportActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    /**
     * This function add and show the headers to the table
     * content of My Community Detail
     **/
    public void addMyCommunityDetailTableHeaders(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        tableLayout.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;
        String key="";
        String value="";
        // int i;

        int count=0;
        Map<String, String> map =mylist1.get(0);
            /*Map<String, String> map =mylist1.get(0);

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Key - "+Sunlevel);
                for(Object obj : map.keySet()){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(getLayoutParams());
                    tr.addView(getTextView(0, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                    //tr.addView();
                    tableLayoutRecord.addView(tr, getTblLayoutParams());
                }*/
        TextView[] textArray = new TextView[mylist1.size()];
        TableRow[] tr_head = new TableRow[mylist1.size()];
        Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < map.size(); i++)
        {
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                if(i==0){
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    tableRow.addView(getTextView(i, String.valueOf(obj).toUpperCase(), Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
                    //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

                    // tableLayoutRecord.addView(tableRow);
                }
               /* else {
                    Object objKey=map.get(obj);
                    Log.d("Key - ", String.valueOf(obj));
                    TableRow tr = new TableRow(this);
                    tableRow.setLayoutParams(getLayoutParams());
                    //tableRow.addView(getTextView(i, String.valueOf(obj), Color.BLACK, Typeface.BOLD, Color.BLUE));
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));
                }*/


                //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                //tr.addView();
                // tableLayoutRecord.addView(tr, getTblLayoutParams());
                //tableLayoutRecord.addView(tableRow);
            }

           /* for (int j = i; j<=i; j++)
            {

                Object objKeySet=map.keySet().toArray()[j];
                Object objKey=map.keySet();
                Log.d("Key - ", String.valueOf(objKeySet));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKeySet), Color.BLACK, Typeface.BOLD, Color.BLUE));






                //textView.setPadding(50, 50, 50, 50);

               // tableRow.addView(textView);

            }*/

            // add the TableRow to the TableLayout

            tableLayout.addView(tableRow);
            // table.addView(row, new TableLayout.LayoutParams(
            // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }


        /*for (int i = 0; i < 5; i++) {

            TableRow row = new TableRow(this);

            //Create the tablerows
            tr_head[i] = new TableRow(this);
            tr_head[i].setId(i+1);
            tr_head[i].setBackgroundColor(Color.GRAY);
            tr_head[i].setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // Here create the TextView dynamically

            textArray[i] = new TextView(this);
            textArray[i].setId(i+111);
            textArray[i].setText("Coloum");
            textArray[i].setTextColor(Color.BLACK);
            textArray[i].setPadding(5, 5, 5, 5);
           // tr_head[i].addView(textArray[i]);
            tr_head[i].addView(textArray[i]);

            //TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            //row.setLayoutParams(lp);
            //TextView textView = new TextView(this);
            //textView.setText("Name" + " " + i);
           // EditText editText = new EditText(this);
           // row.addView(i+"");
            //tableLayoutRecord.addView(row, i);

            tableLayoutRecord.addView(tr_head[i]);
        }*/






           /* for(Map<String, String> map: mylist1) {

                Object mpi =map.keySet().toArray();
                Object Sunlevel=map.get(mpi);
                Log.d(" Position - ","Position Map - "+Sunlevel);
                for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                    //
                    key = mapEntry.getKey();
                    value = mapEntry.getValue();

                    // Log.d("Direct", "Key: -"+key+"Value: -"+value + " Position - "+mapEntryIndex);
                    for( int i=0; i<map.entrySet().size(); i++) {
                        int mapEntryIndex= map.entrySet().size();
                        TableRow tr = new TableRow(this);
                       int index=mapEntry.getKey().indexOf(i);
                        if ( i == mapEntryIndex ) {

                            tr.setLayoutParams(getLayoutParams());
                            tr.addView(getTextView(0, key, Color.BLACK, Typeface.BOLD, Color.BLUE));
                            //tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
                            //tr.addView();
                            tableLayoutRecord.addView(tr, getTblLayoutParams());
                             //break;
                        } else {
                            break;
                        }

                    }
                    break;

                }
                //break;
            }*/


    }

    /**
     * This function add and show the dynamic data of
     * the My Community Detail in the table
     *
     **/
    public void addMonthlyRewardPointsTableData(ArrayList<Map<String, String>> directList ) {
        tableLayout.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=directList;

        // Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        //Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

            }
            tableLayout.addView(tableRow);

        }

    }


    // MFA  Incentive REport Api Calling
    //  Method for fetching data and get Details
    private void getMFADetail(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest request = new BaseRequest();
        request.setReqtype(ApiConstants.REQUEST_MFA_REPORT_DY);
        request.setPasswd(SharedPrefrence_Business.getPassword(this));
        request.setUserid(SharedPrefrence_Business.getUserID(this));
        request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        //downlinePurchaseRequest.setType(stringType);

        Call<Dy_MFADetailResponse> downlinePurchaseResponseCall=
                NetworkClient1.getInstance(this).create(IncomeServices.class).fetchDynamicMFAReport(request,strApiKey);

        downlinePurchaseResponseCall.enqueue(new Callback<Dy_MFADetailResponse>() {
            @Override
            public void onResponse(Call<Dy_MFADetailResponse> call, Response<Dy_MFADetailResponse> response) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {
                    Dy_MFADetailResponse Response=new Dy_MFADetailResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {


                            if(Response.getMfareport()!= null && Response.getMfareport().size() > 0){

                                linearLayoutNoRecord.setVisibility(View.GONE);
                                linearLayoutTable.setVisibility(View.VISIBLE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.GONE);
                                layoutMFANotes.setVisibility(View.VISIBLE);
                                linearLayoutNext.setVisibility(View.VISIBLE);
                                txtMfaNote1.setText(Response.getCycleclosingdate1()+ " : "+Response.getCycleclosingdate2());
                                txtMfaNote2.setText(Response.getCyclepaymentdate1()+ " : "+Response.getCyclepaymentdate2());

                                //totalRecord = Integer.parseInt(reportResponse.getRecordcount());
                                //txtRecord.setText("Record : - " +String.valueOf(totalRecord));
                                //textViewRecord.setTextSize(12);
                                ArrayList<Map<String ,String>> downlineDtailmaps= Response.getMfareport();
                                //ArrayList<Map<String ,String>> mapsLevelDirectSummery=dy_downdetailResponse.getDirectssummary();

                                addMFADetailTableData(downlineDtailmaps);
                                //int totcount= Integer.parseInt(reportResponse.getRecordcount());
                                // GetHashMapDirectValue(directmaps);
                                //GetHashMapSummeryValue(maps1);
                               /* if(from == 1){
                                    addMFADetailTableData

                                }
                                else {
                                    addMyRewardsTableData(downlineDtailmaps);
                                }*/

                            }
                            else {
                                linearLayoutNoRecord.setVisibility(View.VISIBLE);
                                linearLayoutTable.setVisibility(View.GONE);
                                //tableLayoutRecord.setVisibility(View.VISIBLE);
                                layoutMyDirectSummery.setVisibility(View.GONE);
                                linearLayoutNext.setVisibility(View.GONE);
                                layoutMFANotes.setVisibility(View.GONE);
                            }


                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(CommonReportActivity.this,Response.getMsg());
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
                    }
                    else {
                        String msg="Something went wrong. Please try again.";
                        blankValueFromSharePreference(CommonReportActivity.this,msg);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Dy_MFADetailResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(CommonReportActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * This function add and show the dynamic data of
     * the MFA Incentive Report in the table Content
     *
     **/
    public void addMFADetailTableData(ArrayList<Map<String, String>> walletList ) {
        tableLayout.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=walletList;

        // Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        //Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(this);

            /*TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));*/


            for(Object obj : map.keySet()){


                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
                if(from==1){
                    if (i==0){
                        tableRow.addView(getTextView(i, String.valueOf(objKey), Color.WHITE, Typeface.BOLD, getResources().getColor(R.color.colorPrimary)));
                    }
                    else
                        tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                    //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

                }
                else {
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.gray3)));
                }

            }
            tableLayout.addView(tableRow);

        }

    }



    // date picker open for CHECKIN-Date and selected date set to textview
    public static class SelectDateFragment extends DialogFragment implements
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
            SetDOBDate(yy, mm +1, dd);

        }
    }

    public static void SetDOBDate(int year, int month, int day) {
        String Month = Utilities.convertMonth(month);
        edTxtDate.setText(day + "-" + Month + "-" + year);
        strDate = (Utilities.convertdayNumber(day) + "-" + Utilities.convertMonthNumber(month) + "-" + year ).toString();

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

    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        return true;
    }

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