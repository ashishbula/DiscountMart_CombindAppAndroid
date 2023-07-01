package in.discountmart.utility_services.billpayment.activity;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import in.base.network.NetworkClient_Utility;
import in.base.network.NetworkClient_Utility_1;
import in.base.util.ImageUtil;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.activity.PromocodeActivity;
import in.discountmart.utility_services.billpayment.bill_pay_call_api.BillPayApi;
import in.discountmart.utility_services.billpayment.billpay_model.billpay_request_model.BillPayRequest;
import in.discountmart.utility_services.billpayment.billpay_model.billpay_request_model.GetParameterRequest;
import in.discountmart.utility_services.billpayment.billpay_model.billpay_response_model.BillPayResponse;
import in.discountmart.utility_services.billpayment.billpay_model.billpay_response_model.BillPayServiceProviderListResonse;
import in.discountmart.utility_services.billpayment.billpay_model.billpay_response_model.GetParameterResponse;
import in.discountmart.utility_services.billpayment.billpay_model.billpay_response_model.UploadDocumentResponse;
import in.discountmart.utility_services.billpayment.billpay_shared_preferance.BillPaySharedPreferance;
import in.discountmart.utility_services.call_api.MainServices;
import in.discountmart.utility_services.constants.AppConstants;
import in.discountmart.utility_services.listener.AlertDialogButtonListener;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.request_model.MainBalanceRequest;
import in.discountmart.utility_services.model.request_model.SendOtpRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.model.response_model.MainBalanceResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.travel.flight.call_flight_api.FlightApi;
import in.discountmart.utility_services.travel.flight.flight_sharedpreference.FlightSharedValues;
import in.discountmart.utility_services.travel.travel_sharedpreferance.TravelSharedPreferance;
import in.discountmart.utility_services.utilities.AlertDialogUtils;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.TokenBase64;
import in.discountmart.utility_services.utilities.Utilities;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreditCardBillPayActivity extends AppCompatActivity {

    EditText edtxtReferenceNo;
    EditText edtxtBillAmount;
    EditText edtxtCustomerName;
    EditText edtxtMobile;
    EditText edtxtEmailId;
    EditText edtxtCardNo1;
    EditText edtxtCardNo2;
    EditText edtxtCardNo3;
    EditText edtxtCardNo4;
    EditText edtxtConfCardNo1;
    EditText edtxtConfCardNo2;
    EditText edtxtConfCardNo3;
    EditText edtxtConfCardNo4;
    EditText edtxtPromocode;
    EditText edtxtDiscount;
    TextView txtHavePromocode;
    TextView txtPromoDiscount;
    TextView txtPromoUsed;
    TextView txtFile;
    TextView txtWalletAmount;
    TextInputLayout textInputLayoutRef;
    public static TextView txtService;
    TextView btnCalPromodis;
    LinearLayout layoutCardType;
    LinearLayout layoutCardNo;
    LinearLayout layoutConfCardNo;
    LinearLayout layoutOperator;
    LinearLayout layoutPromo;
    LinearLayout layoutDiscount;
    LinearLayout layoutReferance;
    LinearLayout layoutBillAmount;
    LinearLayout layoutCustName;
    LinearLayout layoutMobile;
    LinearLayout layoutEmail;
    LinearLayout layoutBillPay;
    LinearLayout layoutFileUpload;
    LinearLayout layoutRemovePromo;
    Button btnBillPay;
    Button btnUploadDoc;
    RadioGroup rdgCardType;
    RadioButton rdbVisa;
    RadioButton rdbMaster;
    RadioButton rdbAS;
    RadioButton rdbCash;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    View view;

    String serviceType = "";
    String serviceTypeId = "";
    String strBillAmount = "";
    String strRefNo = "";
    String strBillerName = "";
    String strRegMobile = "";
    String strPanNo = "";
    String strEmailId = "";
    String strCardType = "";
    String strCardNo1 = "";
    String strCardNo2 = "";
    String strCardNo3 = "";
    String strCardNo4 = "";
    String strConfCardNo1 = "";
    String strConfCardNo2 = "";
    String strConfCardNo3 = "";
    String strConfCardNo4 = "";
    String strCardNum = "";
    String strConfCardNum = "";
    String strDob = "";
    String strPromocode = "";
    String strPromoAmount = "";
    String strPromoStatus = "";
    String strDiscount = "";
    String strFileImg = "";
    public static String serviceID = "";
    String home = "";
    ProgressDialog progressDialog;
    boolean flag;
    String walletAmount = "";
    float mainBal = 0;
    float totalPay = 0;

    String otpId = "";
    String service = "";
    boolean promoApply;

    /*Array List of Object*/
    ArrayList<BillPayServiceProviderListResonse> serviceProviderArrayList;
    ArrayList<GetParameterResponse> parameterResponseArrayList;

    //For image upload
    private Dialog uploadOptionDialog;
    private static int RESULT_LOAD_IMG = 1;
    private static int RESULT_CLICK_IMG = 2;
    private Uri fileUri;
    private static final int PERMISSION_REQUEST_CODE = 200;
    Bitmap bitmapResizedImage;
    String imgPath;
    private String mAttachedFilePath;

    private EditText searchText;
    private TextView resultText;
    private ProgressBar progressBar;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_credit_card_bill_pay);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view = findViewById(android.R.id.content);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        try {


            toolbar = (Toolbar) findViewById(R.id.credit_card_billpay_act_toolbar);
            edtxtReferenceNo = (EditText) findViewById(R.id.credit_card_billpay_act_edtxt_ref_no);
            edtxtBillAmount = (EditText) findViewById(R.id.credit_card_billpay_act_edtxt_bill_amnt);
            edtxtCustomerName = (EditText) findViewById(R.id.credit_card_billpay_act_edtxt_cust_name);
            //edtxtGroupNo=(EditText)findViewById(R.id.credit_card_billpay_act_edtxt_gasno);
            edtxtMobile = (EditText) findViewById(R.id.credit_card_billpay_act_edtxt_mobileno);
            edtxtEmailId = (EditText) findViewById(R.id.credit_card_billpay_act_edtxt_email);
            edtxtCardNo1 = (EditText) findViewById(R.id.credit_card_billpay_act_edtxt_cardno1);
            edtxtCardNo2 = (EditText) findViewById(R.id.credit_card_billpay_act_edtxt_cardno2);
            edtxtCardNo3 = (EditText) findViewById(R.id.credit_card_billpay_act_edtxt_cardno3);
            edtxtCardNo4 = (EditText) findViewById(R.id.credit_card_billpay_act_edtxt_cardno4);
            edtxtConfCardNo1 = (EditText) findViewById(R.id.credit_card_billpay_act_edtxt_conf_cardno1);
            edtxtConfCardNo2 = (EditText) findViewById(R.id.credit_card_billpay_act_edtxt_conf_cardno2);
            edtxtConfCardNo3 = (EditText) findViewById(R.id.credit_card_billpay_act_edtxt_conf_cardno3);
            edtxtConfCardNo4 = (EditText) findViewById(R.id.credit_card_billpay_act_edtxt_conf_cardno4);
            edtxtPromocode = (EditText) findViewById(R.id.credit_card_billpay_act_edtxt_promocode);
            edtxtDiscount = (EditText) findViewById(R.id.credit_card_billpay_act_edtxt_promo_dis);
            rdgCardType = (RadioGroup) findViewById(R.id.credit_card_billpay_act_rdg_card_type);
            rdbVisa = (RadioButton) findViewById(R.id.credit_card_billpay_act_rdb_visa);
            rdbMaster = (RadioButton) findViewById(R.id.credit_card_billpay_act_rdb_master);
            rdbAS = (RadioButton) findViewById(R.id.credit_card_billpay_act_rdb_amrican);
            rdbCash = (RadioButton) findViewById(R.id.credit_card_billpay_act_rdb_cash);
            txtService = (TextView) findViewById(R.id.credit_card_billpay_act_txt_oprator);
            txtFile = (TextView) findViewById(R.id.credit_card_billpay_act_txt_file_upload);
            txtWalletAmount = (TextView) findViewById(R.id.credit_card_billpay_act_txt_walletamount);
            layoutOperator = (LinearLayout) findViewById(R.id.credit_card_billpay_act_layout_oprator);
            layoutCardType = (LinearLayout) findViewById(R.id.credit_card_billpay_act_layout_card_type);
            layoutCardNo = (LinearLayout) findViewById(R.id.credit_card_billpay_act_layout_cardno);
            layoutConfCardNo = (LinearLayout) findViewById(R.id.credit_card_billpay_act_layout_conf_cardno);
            layoutPromo = (LinearLayout) findViewById(R.id.credit_card_billpay_act_layout_promo);
            layoutDiscount = (LinearLayout) findViewById(R.id.credit_card_billpay_act_layout_discount);
            layoutReferance = (LinearLayout) findViewById(R.id.credit_card_billpay_act_layout_refno);
            layoutBillAmount = (LinearLayout) findViewById(R.id.credit_card_billpay_act_layout_bill_amount);
            layoutCustName = (LinearLayout) findViewById(R.id.credit_card_billpay_act_layout_customer_name);
            layoutMobile = (LinearLayout) findViewById(R.id.credit_card_billpay_act_layout_mobile);
            layoutEmail = (LinearLayout) findViewById(R.id.credit_card_billpay_act_layout_email);
            layoutBillPay = (LinearLayout) findViewById(R.id.credit_card_billpay_act_layout_btn_billpay);
            layoutFileUpload = (LinearLayout) findViewById(R.id.credit_card_billpay_act_layout_file_upload);
            layoutRemovePromo = (LinearLayout) findViewById(R.id.credit_card_billpay_act_layout_remove_promo);

            btnBillPay = (Button) findViewById(R.id.credit_card_billpay_act_btn_billpay);
            btnUploadDoc = (Button) findViewById(R.id.credit_card_billpay_act_btn_upload);
            txtHavePromocode = (TextView) findViewById(R.id.credit_card_billpay_act_txt_getpromo);
            txtPromoDiscount = (TextView) findViewById(R.id.credit_card_billpay_act_txt_cal_dis);
            txtPromoUsed = (TextView) findViewById(R.id.credit_card_billpay_act_txt_used_promo);
            textInputLayoutRef = (TextInputLayout) findViewById(R.id.credit_card_billpay_act_txtchang_ref_no);

            /*for Tool bar*/
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setTitle("Credit Card Bill Pay");

            serviceType = BillPaySharedPreferance.getServiceType(this);
            serviceTypeId = BillPaySharedPreferance.getServiceTypeID(this);

            InputFilter filter = new InputFilter() {
                public CharSequence filter(CharSequence source, int start, int end,
                                           Spanned dest, int dstart, int dend) {
                    for (int i = start; i < end; i++) {
                        if (!Character.isLetterOrDigit(source.charAt(i))) {
                            return "";
                        }
                    }
                    return null;
                }
            };

            edtxtCustomerName.addTextChangedListener(new MyTextWatcher(edtxtCustomerName));
            edtxtEmailId.addTextChangedListener(new MyTextWatcher(edtxtEmailId));

            /*Chck permission for camera and stores
             * for image pick from gallery
             * and click image from camera*/
            if(checkPermission()){

            }
            else
                requestPermission();

            // if(progressDialog.isShowing())
            //   progressDialog.dismiss();

            //collapsingToolbarLayout.setTitle("Credit Card Bill Pay");
            /*Value get form Bill Service list activity When we click from home dashboard */
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                serviceID = bundle.getString("ServiceId");
                home = bundle.getString("Home");
                serviceProviderArrayList = new ArrayList<BillPayServiceProviderListResonse>();
                serviceProviderArrayList = (ArrayList<BillPayServiceProviderListResonse>) bundle.getSerializable("ServiceProviderList");

            }


            if (serviceProviderArrayList.size() > 0) {
                for (int i = 0; i < serviceProviderArrayList.size(); i++) {
                    txtService.setText(serviceProviderArrayList.get(i).getService());
                    service = serviceProviderArrayList.get(i).getService();

                }
            }

            /*Show Promo or not*/
           /* LoginResponse loginPreferences=new LoginResponse();
            loginPreferences = new LoginPreferences_brand(this).getLoggedinUser();
            if(loginPreferences != null){
                if(loginPreferences.getMemMode().equals("D")){
                    layoutPromo.setVisibility(View.VISIBLE);
                    layoutDiscount.setVisibility(View.VISIBLE);

                }
                else {
                    layoutPromo.setVisibility(View.GONE);
                    layoutDiscount.setVisibility(View.GONE);
                }
            }*/


            if (home.equals("true")) {

                /*Call Api Get Parameter*/
                if (!ConnectivityUtils.isNetworkEnabled(CreditCardBillPayActivity.this)) {
                    Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    getParameter();
                }
            }

            /*text choose operator o click get service list*/
            layoutOperator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CreditCardBillPayActivity.this, BillPayServiceListActivity.class);
                    intent.putExtra("ServiceType", serviceType);
                    intent.putExtra("ServiceTypeId", serviceTypeId);
                    intent.putExtra("Home", "false");
                    startActivityForResult(intent, 104);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });
            txtService.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (!s.toString().equals(getResources().getString(R.string.str_choose_oprator))) {
                        /*Call Api Get Parameter*/
                        if (!ConnectivityUtils.isNetworkEnabled(CreditCardBillPayActivity.this)) {
                            Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        } else {
                            getParameter();
                        }
                    }


                }
            });

            /*check if radio button is check or un check*/
            if (rdbVisa.isChecked()) {
                strCardType = "Visa";
            }
            else if (rdbCash.isChecked()) {
                strCardType = "Cash Select";
            }
            else if (rdbAS.isChecked()) {
                strCardType = "American";
            }
            else if (rdbMaster.isChecked()) {
                strCardType = "Master Card";
            }

            /*Radio group card type chek change listener*/
            rdgCardType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    int selectId = rdgCardType.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) group.findViewById(checkedId);
                    String strRbtnVlaue = rb.getText().toString();

                    if (rb.isChecked())
                        strCardType = strRbtnVlaue;
                }
            });
            // edtxtConfCardNo4.addTextChangedListener(searchTextWatcher);

            /*SEt focus next edit text*/
            edtxtCardNo1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edtxtCardNo1.getText().toString().length() == 4) {
                        edtxtCardNo2.setFocusable(true);
                        edtxtCardNo2.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });

            /*SEt focus next edit text*/
            edtxtCardNo2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edtxtCardNo2.getText().toString().length() == 4) {
                        edtxtCardNo3.setFocusable(true);
                        edtxtCardNo3.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });
            /*SEt focus next edit text*/
            edtxtCardNo3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edtxtCardNo3.getText().toString().length() == 4) {
                        edtxtCardNo4.setFocusable(true);
                        edtxtCardNo4.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });

            /*SEt focus next edit text*/
            edtxtCardNo4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edtxtCardNo4.getText().toString().length() == 4) {
                        edtxtConfCardNo1.setFocusable(true);
                        edtxtConfCardNo1.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });


            /*SEt focus next edit text*/
            edtxtConfCardNo1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edtxtConfCardNo1.getText().toString().length() == 4) {
                        edtxtConfCardNo2.setFocusable(true);
                        edtxtConfCardNo2.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });

            /*SEt focus next edit text*/
            edtxtConfCardNo2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edtxtConfCardNo2.getText().toString().length() == 4) {
                        edtxtConfCardNo3.setFocusable(true);
                        edtxtConfCardNo3.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });

            /*SEt focus next edit text*/
            edtxtConfCardNo3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edtxtConfCardNo3.getText().toString().length() == 4) {
                        edtxtConfCardNo4.setFocusable(true);
                        edtxtConfCardNo4.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });

            /*Edit text reference no on click get reference number*/
            edtxtReferenceNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtxtCardNo1.getText().toString().equals("")) {
                        edtxtCardNo1.setError("Enter credit card number");
                        edtxtCardNo1.requestFocus();
                    } else if (edtxtCardNo2.getText().toString().equals("")) {
                        edtxtCardNo2.setError("Enter credit card number");
                        edtxtCardNo2.requestFocus();
                    } else if (edtxtCardNo3.getText().toString().equals("")) {
                        edtxtCardNo3.setError("Enter credit card number");
                        edtxtCardNo3.requestFocus();
                    } else if (edtxtCardNo4.getText().toString().equals("")) {
                        edtxtCardNo4.setError("Enter credit card number");
                        edtxtCardNo4.requestFocus();
                    } else if (edtxtConfCardNo1.getText().toString().equals("")) {
                        edtxtConfCardNo1.setError("Re-enter credit card number");
                        edtxtConfCardNo1.requestFocus();
                    } else if (edtxtConfCardNo2.getText().toString().equals("")) {
                        edtxtConfCardNo2.setError("Re-enter credit card number");
                        edtxtConfCardNo2.requestFocus();
                    } else if (edtxtConfCardNo3.getText().toString().equals("")) {
                        edtxtConfCardNo3.setError("Re-enter credit card number");
                        edtxtConfCardNo3.requestFocus();
                    } else if (edtxtConfCardNo4.getText().toString().equals("")) {
                        edtxtConfCardNo4.setError("Re-enter credit card number");
                        edtxtConfCardNo4.requestFocus();
                    } else {
                        strCardNo1 = edtxtCardNo1.getText().toString();
                        strCardNo2 = edtxtCardNo2.getText().toString();
                        strCardNo3 = edtxtCardNo3.getText().toString();
                        strCardNo4 = edtxtCardNo4.getText().toString();

                        strConfCardNo1 = edtxtConfCardNo1.getText().toString();
                        strConfCardNo2 = edtxtConfCardNo2.getText().toString();
                        strConfCardNo3 = edtxtConfCardNo3.getText().toString();
                        strConfCardNo4 = edtxtConfCardNo4.getText().toString();
                        strPromocode = edtxtPromocode.getText().toString();
                        strDiscount = edtxtDiscount.getText().toString();

                        strCardNum = strCardNo1 + strCardNo2 + strCardNo3 + strCardNo4;
                        strConfCardNum = strConfCardNo1 + strConfCardNo2 + strConfCardNo3 + strConfCardNo4;

                        if (strCardNum.contentEquals(strConfCardNum)) {
                            edtxtReferenceNo.setText(strCardNum);
                            edtxtReferenceNo.setEnabled(false);
                            edtxtReferenceNo.setFocusable(false);
                        } else {
                            edtxtReferenceNo.setHint("");
                            edtxtReferenceNo.setText("Enter proper card no in card no field.");
                            edtxtReferenceNo.setEnabled(true);
                            edtxtReferenceNo.setFocusable(true);
                        }
                    }


                }
            });


            /*Click Listner for Show Diload to pic Image from Gellary , cemra*/
            txtFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission()) {

                        //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreditCardBillPayActivity.this);
                        LayoutInflater inflater = CreditCardBillPayActivity.this.getLayoutInflater();

                        View view = inflater.inflate(R.layout.utility_uploadimgdialog, null);
                        ImageView camera = (ImageView) view.findViewById(R.id.imgcamera);
                        ImageView gallery = (ImageView) view.findViewById(R.id.imggallery);
                        ImageView cross = (ImageView) view.findViewById(R.id.cross);


                        cross.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                uploadOptionDialog.dismiss();
                            }
                        });
                        gallery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                //String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                                //ActivityCompat.requestPermissions(CreditCardBillPayActivity.this, permissions, REQUEST_CODE);
                                /*Check permission for external storage and camera*/

                                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                                uploadOptionDialog.dismiss();
                            }
                        });
                        camera.setOnClickListener(new View.OnClickListener() {

                                                      @Override
                                                      public void onClick(View arg0) {
                                                          //String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                                                          //ActivityCompat.requestPermissions(CreditCardBillPayActivity.this, permissions, REQUEST_CODE); // without sdk version check
                                                          /*Check permission for external storage and camera*/

                                                          Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                          fileUri = getOutputMediaFileUri(1);
                                                          if (!AppConstants.Uri.equals(""))
                                                              AppConstants.Uri = "";

                                                          AppConstants.Uri = fileUri.getPath();
                                                          intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                                          startActivityForResult(intent, RESULT_CLICK_IMG);
                                                          uploadOptionDialog.dismiss();
                                                      }
                                                  }
                        );

                        builder.setView(view);

                        uploadOptionDialog = builder.create();
                        uploadOptionDialog.show();

                    } else {
                        requestPermission();
                        //Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
                    }

                }
            });

            /*Button upload doc on click upload image*/
            btnUploadDoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!txtFile.getText().toString().equals("")) {
                        if (!ConnectivityUtils.isNetworkEnabled(CreditCardBillPayActivity.this)) {
                            Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        }
                        else {
                            uploadAttachment();
                        }

                    } else {
                        Toast.makeText(CreditCardBillPayActivity.this, "Upload Document", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            /*Button Get Promo code on have promo button click*/
            txtHavePromocode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtxtBillAmount.getText().toString().contentEquals("")) {
                        Toast.makeText(CreditCardBillPayActivity.this, "Please enter bill amount",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        String strCredit = "C";
                        Intent intent1 = new Intent(CreditCardBillPayActivity.this, PromocodeActivity.class);
                        intent1.putExtra("ServiceType", strCredit);
                        intent1.putExtra("ServiceId", "12");

                        startActivityForResult(intent1, 12);
                        // startActivity(intent1);
                        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                    }

                }
            });

            //update
            /*Image close on click
             * remove get promo code*/
            layoutRemovePromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edtxtPromocode.setText("");
                    edtxtBillAmount.setEnabled(true);
                    //edtxtPromocode.setVisibility(View.GONE);
                    strPromoAmount="0";
                    strPromocode="";
                    strPromoStatus="";
                    layoutRemovePromo.setVisibility(View.GONE);

                    edtxtDiscount.setText("");
                    strDiscount="0";
                    strFileImg="";
                    txtFile.setText("");
                    promoApply=false;
                    //if(txtHavePromocode.getVisibility()==View.GONE)
                        //txtHavePromocode.setVisibility(View.VISIBLE);

                }
            });


            //update
            /*Calculate promo discount on calculate discount btn on click */
           /* txtPromoDiscount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int bilamount=0;
                    int promodis=0;
                    if(edtxtBillAmount.getText().toString().equals("")){
                        Toast.makeText(CreditCardBillPayActivity.this,"Please enter bill amount",
                                Toast.LENGTH_SHORT).show();

                    }
                    else {
                        if(edtxtPromocode.getText().toString().equals("")){
                            Toast.makeText(CreditCardBillPayActivity.this,"Please enter promocode first",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {

                            strPromocode=edtxtPromocode.getText().toString();
                            if(strPromoStatus.equals("Used")){
                                edtxtDiscount.setText("0");
                            }
                            else {

                                //update
                                strBillAmount=edtxtBillAmount.getText().toString();

                                *//*For check if *//*
                                if(isInteger(edtxtBillAmount.getText().toString())){

                                    int billAmount= Integer.parseInt(edtxtBillAmount.getText().toString());
                                    strBillAmount=String.valueOf(billAmount);
                                }
                                else if(isDouble(edtxtBillAmount.getText().toString())){

                                    int promo;
                                    double billAmount= Double.parseDouble(edtxtBillAmount.getText().toString());

                                    promo= (int) Math.round(billAmount);

                                    strBillAmount=String.valueOf(promo);
                                }

                                promodis=Integer.parseInt(strPromoAmount);
                                bilamount=Integer.parseInt(strBillAmount);

                                if(promodis > bilamount){

                                    edtxtDiscount.setText(String.valueOf(bilamount));
                                }
                                else if(promodis < bilamount){
                                    edtxtDiscount.setText(String.valueOf(promodis));
                                }
                                else if(promodis == bilamount){
                                    edtxtDiscount.setText(String.valueOf(bilamount));
                                }
                            }
                        }

                    }
                }
            });*/

            btnBillPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    strRefNo = edtxtReferenceNo.getText().toString();
                    strBillAmount = edtxtBillAmount.getText().toString();
                    strBillerName = edtxtCustomerName.getText().toString();
                    strRegMobile = edtxtMobile.getText().toString();
                    strEmailId = edtxtEmailId.getText().toString();

                    strPromocode = edtxtPromocode.getText().toString();
                    strDiscount = edtxtDiscount.getText().toString();

                    if (strRefNo.equals("") && layoutReferance.getVisibility() == View.VISIBLE) {
                           /* edtxtReferenceNo.setError("Please enter reference number");
                            edtxtReferenceNo.requestFocus();*/
                        Toast.makeText(CreditCardBillPayActivity.this,
                                "Please Enter Reference Number", Toast.LENGTH_SHORT).show();

                    } else if (strBillAmount.equals("") && layoutBillAmount.getVisibility() == View.VISIBLE) {

                        edtxtBillAmount.setError("Please enter bill amount");
                        edtxtBillAmount.requestFocus();
                    }
                    else if (strBillerName.equals("") && layoutCustName.getVisibility() == View.VISIBLE) {
                        edtxtCustomerName.setError("Please customer name");
                        edtxtCustomerName.requestFocus();
                    }
                    else if (strRegMobile.equals("") && layoutMobile.getVisibility() == View.VISIBLE) {
                        edtxtMobile.setError("Please enter regiser mobile no.");
                        edtxtMobile.requestFocus();
                    }
                    else if (strRegMobile.length() > 0 && strRegMobile.length() < 10 && layoutMobile.getVisibility() == View.VISIBLE) {
                        edtxtMobile.setError("enter complete mobile no.");
                        edtxtMobile.requestFocus();
                    }
                    else if (strEmailId.equals("") && layoutEmail.getVisibility() == View.VISIBLE) {

                        edtxtEmailId.setError("Please enter email id.");
                        edtxtEmailId.requestFocus();
                    }
                    else if (!Utilities.isValidEmail(strEmailId) && strEmailId.length() > 0 && layoutEmail.getVisibility() == View.VISIBLE) {
                        edtxtEmailId.setError("enter valid email id.");
                        edtxtEmailId.requestFocus();
                    }
                    else if (strFileImg.equals("") && layoutFileUpload.getVisibility() == View.VISIBLE) {
                        Toast.makeText(CreditCardBillPayActivity.this,
                                "Please Upload credit card image", Toast.LENGTH_SHORT).show();

                    }
                    /* else if (strPromocode.equals("") && layoutPromo.getVisibility() == View.VISIBLE) {
                     *//*edtxtPromocode.setError("Enter promocode.");
                            edtxtPromocode.requestFocus();*//*
                        Toast.makeText(CreditCardBillPayActivity.this,
                                "Please Enter promocode", Toast.LENGTH_SHORT).show();

                    }
                    else if (strDiscount.equals("") && layoutDiscount.getVisibility() == View.VISIBLE) {
                            *//*edtxtDiscount.setError("Click on calculate discount");
                            edtxtDiscount.requestFocus();*//*
                        Toast.makeText(CreditCardBillPayActivity.this,
                                "Click on calculate discount", Toast.LENGTH_SHORT).show();

                    } */
                    else {

                        if(promoApply){
                            if(strFileImg.equals("") && layoutFileUpload.getVisibility() == View.VISIBLE) {
                                Toast.makeText(CreditCardBillPayActivity.this,
                                        "Please Upload credit card image", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                totalPay = Float.parseFloat(edtxtBillAmount.getText().toString());
                                float totCheckBal = mainBal;
                                //if(totCheckBal >= totalPay){
                                /*Call api save bill pay api*/
                                if (!ConnectivityUtils.isNetworkEnabled(CreditCardBillPayActivity.this)) {
                                    Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                            .show();
                                }

                                else
                                    {
                                        /*update*/
                                        if(strDiscount.equals(""))
                                            strDiscount="0";
                                        /*Call api bill pay send otp*/
                                        //getBillPaymentDetail();
                                        //show dialog box for confirmation
                                        // showBillPayDialog();

                                        LoginResponse loginResponse = new LoginResponse();
                                        loginResponse = new LoginPreferences_Utility(CreditCardBillPayActivity.this).getLoggedinUser();
                                        String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
                                        String formno = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);

                                        String dob = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                                        BillPayRequest providerRequest = new BillPayRequest();
                                        providerRequest.setServiceId(serviceID);
                                        providerRequest.setFormNo(formno);
                                        providerRequest.setAmount(strBillAmount);
                                        providerRequest.setReferanceNumber(strRefNo);
                                        providerRequest.setBillerName(strBillerName);
                                        providerRequest.setRegMobileNo(strRegMobile);
                                        providerRequest.setPanNo(strPanNo);
                                        providerRequest.setEmailId(strEmailId);
                                        providerRequest.setCardType(strCardType);
                                        providerRequest.setServiceTypeId(serviceTypeId);
                                        providerRequest.setLocationCode("");
                                        providerRequest.setMeterNumber("");
                                        providerRequest.setBillUnit("");
                                        providerRequest.setProcessCycle("");
                                        providerRequest.setCycleNumber("");
                                        providerRequest.setMeterReadingDate("");
                                        providerRequest.setGroupNo("");
                                        providerRequest.setDistrict("0");
                                        providerRequest.setConsumerType("");
                                        providerRequest.setTypeOfPayment("");
                                        providerRequest.setERO("");
                                        providerRequest.setServicetype("");
                                        providerRequest.setDiviSionName("");
                                        providerRequest.setDob(dob);
                                        providerRequest.setBAnk("");
                                        providerRequest.setRRNo("");
                                        providerRequest.setMonthBill("");
                                        providerRequest.setCreditCard("");
                                        providerRequest.setSector("");
                                        providerRequest.setBlock("");
                                        providerRequest.setFlatNo("");
                                        providerRequest.setSubCode("");
                                        providerRequest.setSubdivisioncode("");
                                        if (strPromoStatus.equals("Used"))
                                            providerRequest.setPromoCode("");
                                        else
                                            providerRequest.setPromoCode(strPromocode);
                                        providerRequest.setDiscount(strDiscount);
                                        providerRequest.setFileImg(strFileImg);
                                        FlightSharedValues.getInstance().flightBookOtpId = otpId;

                                        Intent intent = new Intent(CreditCardBillPayActivity.this, BillPayFinalDetailActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("BillPayRequest", providerRequest);
                                        bundle.putString("OTPId", otpId);
                                        bundle.putString("ServiceType", "Credit Card Bill Pay");
                                        bundle.putString("ServiceName", service);
                                        bundle.putString("BillAmount", strBillAmount);
                                        bundle.putString("PromoAmount",strDiscount);//update
                                        bundle.putString("PromoCode",strPromocode);//update
                                        bundle.putString("Reference", strRefNo);
                                        bundle.putString("Service", "C");
                                        bundle.putString("ServiceId", serviceID);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        // finish();
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                                    }
                            }

                        }
                        else {
                            totalPay = Float.parseFloat(edtxtBillAmount.getText().toString());
                            float totCheckBal = mainBal;
                            //if(totCheckBal >= totalPay){
                            /*Call api save bill pay api*/
                            if (!ConnectivityUtils.isNetworkEnabled(CreditCardBillPayActivity.this)) {
                                Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();
                            }
                            else {

                                /*update*/
                                if(strDiscount.equals(""))
                                    strDiscount="0";
                                /*Call api bill pay send otp*/
                                //getBillPaymentDetail();
                                //show dialog box for confirmation
                                // showBillPayDialog();

                                LoginResponse loginResponse = new LoginResponse();
                                loginResponse = new LoginPreferences_Utility(CreditCardBillPayActivity.this).getLoggedinUser();
                                String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
                                String formno = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);

                                String dob = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                                BillPayRequest providerRequest = new BillPayRequest();
                                providerRequest.setServiceId(serviceID);
                                providerRequest.setFormNo(formno);
                                providerRequest.setAmount(strBillAmount);
                                providerRequest.setReferanceNumber(strRefNo);
                                providerRequest.setBillerName(strBillerName);
                                providerRequest.setRegMobileNo(strRegMobile);
                                providerRequest.setPanNo(strPanNo);
                                providerRequest.setEmailId(strEmailId);
                                providerRequest.setCardType(strCardType);
                                providerRequest.setServiceTypeId(serviceTypeId);
                                providerRequest.setLocationCode("");
                                providerRequest.setMeterNumber("");
                                providerRequest.setBillUnit("");
                                providerRequest.setProcessCycle("");
                                providerRequest.setCycleNumber("");
                                providerRequest.setMeterReadingDate("");
                                providerRequest.setGroupNo("");
                                providerRequest.setDistrict("0");
                                providerRequest.setConsumerType("");
                                providerRequest.setTypeOfPayment("");
                                providerRequest.setERO("");
                                providerRequest.setServicetype("");
                                providerRequest.setDiviSionName("");
                                providerRequest.setDob(dob);
                                providerRequest.setBAnk("");
                                providerRequest.setRRNo("");
                                providerRequest.setMonthBill("");
                                providerRequest.setCreditCard("");
                                providerRequest.setSector("");
                                providerRequest.setBlock("");
                                providerRequest.setFlatNo("");
                                providerRequest.setSubCode("");
                                providerRequest.setSubdivisioncode("");
                                if (strPromoStatus.equals("Used"))
                                    providerRequest.setPromoCode("");
                                else
                                    providerRequest.setPromoCode(strPromocode);
                                providerRequest.setDiscount(strDiscount);
                                providerRequest.setFileImg(strFileImg);
                                FlightSharedValues.getInstance().flightBookOtpId = otpId;

                                Intent intent = new Intent(CreditCardBillPayActivity.this, BillPayFinalDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("BillPayRequest", providerRequest);
                                bundle.putString("OTPId", otpId);
                                bundle.putString("ServiceType", "Credit Card Bill Pay");
                                bundle.putString("ServiceName", service);
                                bundle.putString("BillAmount", strBillAmount);
                                bundle.putString("PromoAmount",strDiscount);//update
                                bundle.putString("PromoCode",strPromocode);//update
                                bundle.putString("Reference", strRefNo);
                                bundle.putString("Service", "C");
                                bundle.putString("ServiceId", serviceID);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                // finish();
                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                            }
                        }


                       /* }
                        else {
                            AlertDialogUtils_shop.showDialog(CreditCardBillPayActivity.this,
                                    "Alert Dialog","Wallet amount not sufficient for bill pay");
                        }
*/

                    }

                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class MyTextWatcher implements TextWatcher {
        private EditText editText;

        public MyTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text = editText.getText().toString();
            if (text.startsWith(" ")) {
                editText.setText(text.trim());

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
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


    //image upload
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath(), "Android file upload");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            int compressionRatio = 4;

            if (requestCode == 104) {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    serviceProviderArrayList = new ArrayList<BillPayServiceProviderListResonse>();
                    serviceProviderArrayList = (ArrayList<BillPayServiceProviderListResonse>) bundle.getSerializable("ServiceProviderList");
                    serviceID = bundle.getString("ServiceId");
                    if (serviceProviderArrayList.size() > 0) {
                        for (int i = 0; i < serviceProviderArrayList.size(); i++) {
                            txtService.setText(serviceProviderArrayList.get(i).getService());
                            //serviceId=serviceProviderArrayList.get(i).getServiceId();

                        }
                    }

                }
            }

            //update
            else if (requestCode == 12) {
                if(resultCode == Activity.RESULT_OK){

                    strPromocode=data.getStringExtra("PromoCode");
                    strPromocode=data.getStringExtra("PromoCode");
                    strPromoStatus=data.getStringExtra("Status");

                    if(isInteger(data.getStringExtra("PromoAmount"))){

                        int promoAmount= Integer.parseInt(data.getStringExtra("PromoAmount"));
                        strPromoAmount=String.valueOf(promoAmount);
                    }
                    else if(isDouble(data.getStringExtra("PromoAmount"))){

                        int promo;
                        double promoAmount= Double.parseDouble(data.getStringExtra("PromoAmount"));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            promo= Math.toIntExact(Math.round(promoAmount));
                        }
                        else {
                            promo= (int) Math.round(promoAmount);
                        }
                        strPromoAmount=String.valueOf(promo);
                    }

                    if(strPromoStatus.equals("Unused"))                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            {
                        int bilamount=0;
                        int promodis=0;
                        layoutRemovePromo.setVisibility(View.VISIBLE);
                        //edtxtPromocode.setVisibility(View.VISIBLE);
                        edtxtPromocode.setText(strPromocode);
                        edtxtPromocode.setEnabled(false);
                        edtxtBillAmount.setEnabled(false);
                        edtxtPromocode.setFocusable(false);
                        txtHavePromocode.setVisibility(View.VISIBLE);
                        txtPromoUsed.setVisibility(View.GONE);
                        //txtPromoDiscount.setClickable(true);
                        //txtPromoDiscount.setEnabled(true);
                        promoApply=true;
                        //update
                        strBillAmount=edtxtBillAmount.getText().toString();

                        /*For check if */
                        if(isInteger(edtxtBillAmount.getText().toString())){

                            int billAmount= Integer.parseInt(edtxtBillAmount.getText().toString());
                            strBillAmount=String.valueOf(billAmount);
                        }
                        else if(isDouble(edtxtBillAmount.getText().toString())){

                            int promo;
                            double billAmount= Double.parseDouble(edtxtBillAmount.getText().toString());

                            promo= (int) Math.round(billAmount);

                            strBillAmount=String.valueOf(promo);
                        }

                        promodis=Integer.parseInt(strPromoAmount);
                        bilamount=Integer.parseInt(strBillAmount);

                        if(promodis > bilamount){

                            edtxtDiscount.setText(String.valueOf(bilamount));
                        }
                        else if(promodis < bilamount){
                            edtxtDiscount.setText(String.valueOf(promodis));
                        }
                        else if(promodis == bilamount){
                            edtxtDiscount.setText(String.valueOf(bilamount));
                        }
                    }

                    else {
                        edtxtPromocode.setText("");
                        edtxtPromocode.setEnabled(false);
                        edtxtPromocode.setFocusable(false);
                        edtxtBillAmount.setEnabled(true);

                        //txtHavePromocode.setVisibility(View.GONE);
                        //txtPromoUsed.setVisibility(View.VISIBLE);
                        //txtPromoUsed.setText("You have been already used this promo");
                        edtxtDiscount.setText("0");
                        promoApply=false;
                        //txtPromoDiscount.setClickable(false);
                        //txtPromoDiscount.setEnabled(false);
                    }
                }
            }

            // When an Image is
            else if (requestCode == 2) {
                AppConstants.imgpath = "";

                if (resultCode == this.RESULT_OK) {

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // down sizing image as it throws OutOfMemory Exception for larger images
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(AppConstants.Uri, options);
                    //bitmap.compress(Bitmap.CompressFormat.JPEG,2, new FileOutputStream(AppConstants.Uri));


                    txtFile.setText(AppConstants.Uri);
                    AppConstants.ImageUri = AppConstants.Uri;
                    //imgViewDoc.setImageBitmap(bitmap);


                } else if (resultCode == this.RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(CreditCardBillPayActivity.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();

                } else {
                    // failed to capture image
                    Toast.makeText(CreditCardBillPayActivity.this, "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
                }


            }
            // When an Image is picked from gallery
            else if (requestCode == 1) {

                AppConstants.Uri = "";
                if (resultCode == this.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgPath = cursor.getString(columnIndex);
                    cursor.close();

                    // Set the Image in ImageView
                    final Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
                    //bitmap.compress(Bitmap.CompressFormat.JPEG,2, new FileOutputStream(imgPath));

                    txtFile.setText(imgPath);
                    //imgViewDoc.setImageBitmap(bitmap);

                    AppConstants.imgpath = imgPath;
                    AppConstants.ImageUri = imgPath;

                    //upload image
                    //uploadAttachment();

                } else if (resultCode == this.RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(CreditCardBillPayActivity.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();
                }

            }


        } catch (Exception e) {
            Toast.makeText(CreditCardBillPayActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    //update
    /*Method for check is value is integer or not*/
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /*Method for check is value is double or not*/
    public static boolean isDouble(String str) {
        try {
            double v = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException nfe) {
        }
        return false;
    }

    // When Consult button is clicked
    public void uploadAttachment() {

        if (AppConstants.Uri != null && AppConstants.Uri != "") {
            // When Image is capturted from Camera
            sendImageUploadRequest(AppConstants.Uri);
        } else if (AppConstants.imgpath != null && !AppConstants.imgpath.isEmpty()) {
            // When Image is selected from Gallery
            sendImageUploadRequest(AppConstants.imgpath);
        } else {
            Toast.makeText(CreditCardBillPayActivity.this, "NO FILE SELECTED TO UPLOAD", Toast.LENGTH_LONG).show();
        }
    }

    private void sendImageUploadRequest(final String filePath) {
        Handler mHandler = new Handler(this.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                UploadImageHandler1 uploadImageHandler = new UploadImageHandler1(filePath);
//
//                String stringParameter=sendUpdateBankProofDetailRequest();
//                uploadImageHandler.execute(ApiConstants.Baseurl, stringParameter);
                //uploadImageHandler.execute(AppConstants.uploadProfileImage, "");
                uploadImage(filePath);

            }
        });
    }
    /*private String getMyJson() {
        String MyJson;

        String urlParameters="";
        String Get_Url="";

        MyJson= ApiConstants.Baseurl+urlParameters;

        return MyJson;
    }*/

    // Decodes image and scales it to reduce memory consumption
    private Bitmap decodeFileIntoBitmap(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 200;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }
    //get permission to use gallery

    @Override
    public void onResume() {
        super.onResume();
    }

    /* Request and Response Get Parameeter Api*/
    public void getParameter() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String strApiRequest = "";
        JSONObject object = null;
        String strToken = TokenBase64.getToken();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse = new LoginPreferences_Utility(this).getLoggedinUser();
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
            GetParameterRequest providerRequest = new GetParameterRequest();
            providerRequest.setServiceId(serviceID);


            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(providerRequest);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<BaseResponse> fetchServiceProviderListCall =
                NetworkClient_Utility_1.getInstance(this).create(BillPayApi.class).fetchBillPayParameter(apiRequest, strToken);

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

                                //call utility_wallet api
                                getMainBalance();

                                String[] serviceListResponse = Response.getRESPONSE().split("");
                                if (serviceListResponse.length > 0) {
                                    GetParameterResponse[] parameterList = new Gson().fromJson(Response.getRESP_VALUE(), GetParameterResponse[].class);
                                    List<GetParameterResponse> serviceArrayList = new ArrayList<GetParameterResponse>(Arrays.asList(parameterList));

                                    // adding contacts to contacts list
                                    parameterResponseArrayList = new ArrayList<GetParameterResponse>();
                                    parameterResponseArrayList.clear();
                                    parameterResponseArrayList.addAll(serviceArrayList);

                                    if (parameterResponseArrayList.size() > 0) {
                                        for (int i = 0; i < parameterResponseArrayList.size(); i++) {
                                            if (parameterResponseArrayList.get(i).getReferanceNumber().equals("Y")) {
                                                layoutReferance.setVisibility(View.VISIBLE);
                                                strRefNo = "";
                                            } else {
                                                layoutReferance.setVisibility(View.VISIBLE);
                                                strRefNo = "";
                                            }
                                            if (parameterResponseArrayList.get(i).getBillAmount().equals("Y")) {
                                                layoutBillAmount.setVisibility(View.VISIBLE);
                                                strBillAmount = "";
                                            } else {
                                                layoutBillAmount.setVisibility(View.VISIBLE);
                                                strBillAmount = "gone";
                                            }

                                            if (parameterResponseArrayList.get(i).getBillerName().equals("Y")) {
                                                layoutCustName.setVisibility(View.VISIBLE);
                                                strBillerName = "";
                                            } else {
                                                layoutCustName.setVisibility(View.GONE);
                                                strBillerName = "gone";
                                            }

                                            if (parameterResponseArrayList.get(i).getRegMobileNo().equals("Y")) {
                                                layoutMobile.setVisibility(View.VISIBLE);
                                                strRegMobile = "";
                                            } else {
                                                layoutMobile.setVisibility(View.GONE);
                                                strRegMobile = "gone";
                                            }
                                            if (parameterResponseArrayList.get(i).getEmailId().equals("Y")) {
                                                layoutEmail.setVisibility(View.VISIBLE);
                                                strEmailId = "";
                                            } else {
                                                layoutEmail.setVisibility(View.GONE);
                                                strEmailId = "gone";
                                            }
                                            if (parameterResponseArrayList.get(i).getCardType().equals("Y")) {
                                                layoutCardType.setVisibility(View.VISIBLE);
                                                // strCardType="";
                                                //layoutConfCardNo.setVisibility(View.VISIBLE);
                                            } else {
                                                layoutCardType.setVisibility(View.GONE);
                                                strCardType = "gone";
                                            }

                                            if (parameterResponseArrayList.get(i).getCreditCard().equals("Y")) {
                                                layoutCardNo.setVisibility(View.VISIBLE);
                                                layoutConfCardNo.setVisibility(View.VISIBLE);
                                                strCardNum = "";
                                                strConfCardNum = "";
                                            } else {
                                                layoutCardNo.setVisibility(View.GONE);
                                                layoutConfCardNo.setVisibility(View.GONE);
                                                strCardNum = "gone";
                                                strConfCardNum = "gone";
                                            }
                                            if (parameterResponseArrayList.get(i).getBillUpload().equals("Y")) {
                                                layoutFileUpload.setVisibility(View.VISIBLE);
                                                strFileImg = "";

                                            } else {
                                                layoutFileUpload.setVisibility(View.GONE);
                                                strFileImg = "gone";
                                            }


                                        }
                                        layoutPromo.setVisibility(View.VISIBLE);
                                        layoutDiscount.setVisibility(View.VISIBLE);
                                        layoutBillPay.setVisibility(View.VISIBLE);
                                        strPromocode = "";
                                        strDiscount = "";

                                    }

                                } else {
                                    String toast = " City List empty";
                                    Toast.makeText(CreditCardBillPayActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(CreditCardBillPayActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(CreditCardBillPayActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                    }
                    else {
                        //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                        String toast = "something wrong..";
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

    /* Request and Response Get Main Balance*/
    public void getMainBalance() {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String strApiRequest = "";
            JSONObject object = null;
            LoginResponse loginResponse = new LoginResponse();
            loginResponse = new LoginPreferences_Utility(this).getLoggedinUser();
            String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
            String formno = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);
            String mobile = loginResponse.getMobileNo().substring(0, loginResponse.getMobileNo().length() - 2);

            String strToken = TokenBase64.getToken();

            /*Main Request Model*/
            ApiRequest apiRequest = new ApiRequest();

            try {
                /*Base Request Model*/
                BaseHeaderRequest headerRequest = new BaseHeaderRequest();
                headerRequest.setUserName(new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
                headerRequest.setPassword(new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
                headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/
                MainBalanceRequest request = new MainBalanceRequest();
                request.setFormNo(formno);

                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(request);

                strApiRequest = new Gson().toJson(apiRequest);

                Log.e("Value", strApiRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Call<BaseResponse> fetchFlightBook =
                    NetworkClient_Utility_1.getInstance(this).create(MainServices.class).fetchGetBalance(apiRequest, strToken);

            fetchFlightBook.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    try {

                        BaseResponse Response = new BaseResponse();
                        Response = response.body();

                        if(Response != null){
                            if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                if (!Response.getRESP_VALUE().equals("null") || !Response.getRESP_VALUE().equals("")) {

                                    MainBalanceResponse balanceResponse =
                                            new Gson().fromJson(Response.getRESP_VALUE(), MainBalanceResponse.class);

                                    //String toast= Response.getRESP_MSG();
                                    //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    if (balanceResponse != null) {
                                        // get Main Wallet balance
                                        new LoginPreferences_Utility(CreditCardBillPayActivity.this).getLoggedinUser().setBalance(balanceResponse.getBalance());
                                        mainBal = Float.parseFloat(balanceResponse.getBalance());
                                        txtWalletAmount.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(mainBal));
                                    }

                                } else if (Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")) {
                                    String toast = Response.getRESP_MSG();

                                    Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                            .show();

                                }

                            }
                            else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Try Again ";
                                //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(CreditCardBillPayActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(CreditCardBillPayActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(CreditCardBillPayActivity.this, toast, Toast.LENGTH_SHORT).show();
                                // showSnackbar(toast);
                            }
                        }
                        else {
                            String toast = "something wrong..";
                            //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
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


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Request and Response Get Bill Payament sApi*/
    public void getBillPaymentDetail() {


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String strApiRequest = "";
        JSONObject object = null;
        String strToken = TokenBase64.getToken();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse = new LoginPreferences_Utility(this).getLoggedinUser();
        String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
        String formno = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);

        String dob = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
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
            BillPayRequest providerRequest = new BillPayRequest();
            providerRequest.setServiceId(serviceID);
            providerRequest.setFormNo(formno);
            providerRequest.setAmount(strBillAmount);
            providerRequest.setReferanceNumber(strRefNo);
            providerRequest.setBillerName(strBillerName);
            providerRequest.setRegMobileNo(strRegMobile);
            providerRequest.setPanNo(strPanNo);
            providerRequest.setEmailId(strEmailId);
            providerRequest.setCardType(strCardType);
            providerRequest.setServiceTypeId(serviceTypeId);
            providerRequest.setLocationCode("");
            providerRequest.setMeterNumber("");
            providerRequest.setBillUnit("");
            providerRequest.setProcessCycle("");
            providerRequest.setCycleNumber("");
            providerRequest.setMeterReadingDate("");
            providerRequest.setGroupNo("");
            providerRequest.setDistrict("");
            providerRequest.setConsumerType("");
            providerRequest.setTypeOfPayment("");
            providerRequest.setERO("");
            providerRequest.setServicetype("");
            providerRequest.setDiviSionName("");
            providerRequest.setDob(dob);
            providerRequest.setBAnk("");
            providerRequest.setRRNo("");
            providerRequest.setMonthBill("");
            providerRequest.setCreditCard("");
            providerRequest.setSector("");
            providerRequest.setBlock("");
            providerRequest.setFlatNo("");
            providerRequest.setSubCode("");
            providerRequest.setSubdivisioncode("");
            if (strPromoStatus.equals("Used"))
                providerRequest.setPromoCode("");
            else
                providerRequest.setPromoCode(strPromocode);
            providerRequest.setDiscount(strDiscount);
            providerRequest.setFileImg(strFileImg);

            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(providerRequest);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<BaseResponse> fetchBillPayCall =
                NetworkClient_Utility_1.getInstance(this).create(BillPayApi.class).fetchBillPayment(apiRequest, strToken);

        fetchBillPayCall.enqueue(new Callback<BaseResponse>() {
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
                                String serviceListResponse = Response.getRESPONSE();
                                BillPayResponse billPayResponse = new BillPayResponse();
                                billPayResponse = new Gson().fromJson(Response.getRESP_VALUE(), BillPayResponse.class);
                                if (billPayResponse != null) {

                                    Intent intent = new Intent(CreditCardBillPayActivity.this, BillPaymentSuccessMsgActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("Msg", Response.getRESP_MSG());
                                    bundle.putString("Amount", billPayResponse.getAmount());
                                    bundle.putString("TransId", billPayResponse.getId());
                                    if (serviceID.equals(billPayResponse.getServiceId()))
                                        bundle.putString("Service", txtService.getText().toString());
                                    else
                                        bundle.putString("Service", billPayResponse.getServiceId());

                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);


                                } else {
                                    String toast = " City List empty";
                                    Toast.makeText(CreditCardBillPayActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    //showSnackbar(toast);
                                }
                            }
                        } else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("FAILED")) {

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
                    } else {
                        //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                        String toast = "something error may be server / other";
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

    /* Request and Response BillPay Send Otp*/
    public void billPaySendOtp() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String strApiRequest = "";
        JSONObject object = null;
        LoginResponse loginResponse = new LoginResponse();
        loginResponse = new LoginPreferences_Utility(this).getLoggedinUser();

        String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
        String formno = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);
        String mobile = loginResponse.getMobileNo().substring(0, loginResponse.getMobileNo().length() - 2);

        String[] strResources = {"GDS", "FZ", "G8", "SG", "G9", "AK", "IX", "LB", "TR", "6E", "B3", "OP", "2T", "W5", "LV", "TZ", "ZO", "PY"};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String str = String.join(",", strResources);
            TravelSharedPreferance.setFlightSources(this, str);
        }

        JSONArray jsonArray = new JSONArray(Arrays.asList(strResources));
        String strToken = TokenBase64.getToken();

        /*Main Request Model*/
        ApiRequest apiRequest = new ApiRequest();

        try {

            /*Base Request Model*/
            BaseHeaderRequest headerRequest = new BaseHeaderRequest();
            headerRequest.setUserName(new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
            headerRequest.setPassword(new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
            headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/

            /* Send Otp Request Model*/

            SendOtpRequest sendOtpRequest = new SendOtpRequest();
            sendOtpRequest.setEmailID(new LoginPreferences_Utility(this).getLoggedinUser().getEmailId());
            sendOtpRequest.setFormNo(formno);
            sendOtpRequest.setMobileNo(mobile);
            sendOtpRequest.setSponsorFormNo(companyId);
            sendOtpRequest.setServiceName("Gas");
            sendOtpRequest.setUserName(new LoginPreferences_Utility(this).getLoggedinUser().getUserName());

            /*Set Value in Main Request Model*/
            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(sendOtpRequest);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<BaseResponse> fetchFlightOtp =
                NetworkClient_Utility_1.getInstance(CreditCardBillPayActivity.this).create(FlightApi.class).fetchFlightBookOtp(apiRequest, strToken);
        fetchFlightOtp.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    BaseResponse Response = new BaseResponse();
                    Response = response.body();
                    if(Response != null){
                        if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                            if (!Response.getRESP_VALUE().equals("null") || !Response.getRESP_VALUE().equals("")) {

                                // edTxtOtp.setEnabled(true);
                                String strResponse = Response.getRESP_VALUE();
                                otpId = strResponse;
                                // SendOtpResponse otpResponse= new Gson().fromJson(strResponse,SendOtpResponse.class);
                                //otpId=otpResponse.getId();

                                /*BillPay Request Model*/

                                LoginResponse loginResponse = new LoginResponse();
                                loginResponse = new LoginPreferences_Utility(CreditCardBillPayActivity.this).getLoggedinUser();
                                String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
                                String formno = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);

                                String dob = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                                BillPayRequest providerRequest = new BillPayRequest();
                                providerRequest.setServiceId(serviceID);
                                providerRequest.setFormNo(formno);
                                providerRequest.setAmount(strBillAmount);
                                providerRequest.setReferanceNumber(strRefNo);
                                providerRequest.setBillerName(strBillerName);
                                providerRequest.setRegMobileNo(strRegMobile);
                                providerRequest.setPanNo(strPanNo);
                                providerRequest.setEmailId(strEmailId);
                                providerRequest.setCardType(strCardType);
                                providerRequest.setServiceTypeId(serviceTypeId);
                                providerRequest.setLocationCode("");
                                providerRequest.setMeterNumber("");
                                providerRequest.setBillUnit("");
                                providerRequest.setProcessCycle("");
                                providerRequest.setCycleNumber("");
                                providerRequest.setMeterReadingDate("");
                                providerRequest.setGroupNo("");
                                providerRequest.setDistrict("");
                                providerRequest.setConsumerType("");
                                providerRequest.setTypeOfPayment("");
                                providerRequest.setERO("");
                                providerRequest.setServicetype("");
                                providerRequest.setDiviSionName("");
                                providerRequest.setDob(dob);
                                providerRequest.setBAnk("");
                                providerRequest.setRRNo("");
                                providerRequest.setMonthBill("");
                                providerRequest.setCreditCard("");
                                providerRequest.setSector("");
                                providerRequest.setBlock("");
                                providerRequest.setFlatNo("");
                                providerRequest.setSubCode("");
                                providerRequest.setSubdivisioncode("");
                                if (strPromoStatus.equals("Used"))
                                    providerRequest.setPromoCode("");
                                else
                                    providerRequest.setPromoCode(strPromocode);
                                providerRequest.setDiscount(strDiscount);
                                providerRequest.setFileImg(strFileImg);
                                FlightSharedValues.getInstance().flightBookOtpId = otpId;

                                Intent intent = new Intent(CreditCardBillPayActivity.this, OtpAndVerify_BillPayActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("BillPayRequest", providerRequest);
                                bundle.putString("OTPId", otpId);
                                bundle.putString("ServiceType", "Credit Card Bill Pay");
                                bundle.putString("ServiceName", service);
                                bundle.putString("ServiceId", serviceID);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                // finish();
                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                            } else if (Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")) {
                                String toast = Response.getRESP_MSG();
                                //edTxtOtp.setEnabled(false);
                                //Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();
                            }

                        }
                        else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {
                            //edTxtOtp.setEnabled(false);
                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            //Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(CreditCardBillPayActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(CreditCardBillPayActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else {
                            //edTxtOtp.setEnabled(false);
                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            //Toast.makeText(FlightBookOtp_PaymentActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();

                        }
                    }
                    else {

                        //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                        String toast = "something wrong..";
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

    /* Upload image api request and response*/
    private void uploadImage(final String strfile) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        /* UpdateBankProofRequest Request = new UpdateBankProofRequest();
         *//*Set value in Entity class*//*
        Request.setReqtype(ApiConstants.REQUEST_UPDATE_BANK_PROOF_DETAIL);
        Request.setPasswd(SharedPrefrence.getPassword(context));
        Request.setUserid(SharedPrefrence.getUserID(context));
        Request.setAccountno(strAcno);
        Request.setAccounttype(strActype);
        Request.setBankcode(strBankId);
        Request.setBranchname(strBranch);
        Request.setIfsccode(strIfsc);*/


        File file1 = new File(ImageUtil.compressImage(strfile));
        String str1 = "";
        str1 = file1.getPath();

        int compressionRatio = 25;

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        MultipartBody.Part body;
        /*try {

            Bitmap compbitmap = BitmapFactory.decodeFile (file1.getPath());
            compbitmap.compress (Bitmap.CompressFormat.JPEG, compressionRatio, new FileOutputStream(file1));
            }
            catch (Throwable t) {
            Log.e("ERROR", "Error compressing file." + t.toString ());
            t.printStackTrace ();
            }*/


        if (!str1.equals("")) {

            // Create a request body with file and image media type
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file1);

            // Create MultipartBody.Part using file request-body,file name and part name
            body = MultipartBody.Part.createFormData("file:", file1.getName(), reqFile);
            //Create request body with text description and text media type
            RequestBody request = RequestBody.create(MediaType.parse("text/plain"), "image-type");


            Call<UploadDocumentResponse> callUpdateDetailMultipart =
                    NetworkClient_Utility.getInstance(this).createCatalogue(BillPayApi.class).fetchUploadImageMultipart(body, request);

            callUpdateDetailMultipart.enqueue(new Callback<UploadDocumentResponse>() {
                @Override
                public void onResponse(Call<UploadDocumentResponse> call, Response<UploadDocumentResponse> response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    try {

                        UploadDocumentResponse Response = new UploadDocumentResponse();
                        Response = response.body();

                        assert Response != null;
                        if (Response.getStatuscode().equals("200")) {

                            strFileImg = Response.getName();
                            String toast = "Upload Sucessfully";
                            Toast.makeText(CreditCardBillPayActivity.this, toast, Toast.LENGTH_SHORT).show();

                            //getBankProofDetail();
                            //new getBankProofDetails().execute();
                            //((MainActivity_utility)context).replaceFragment(new DashboardFragment(),AppConstants.TAG_HOME,null);
                        } else {
                            String toast = "something wrong" + Response.getStatuscode();
                            Toast.makeText(CreditCardBillPayActivity.this, toast, Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<UploadDocumentResponse> call, Throwable t) {
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

        } else {
            Toast.makeText(CreditCardBillPayActivity.this, "No file Selected", Toast.LENGTH_SHORT).show();
        }


    }

    void showBillPayDialog() {
        try {
           /* StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("Amount of :- " + strAmount);
            sb.append("\n");
            sb.append("Mobile no:- " + strAccountNo);
            sb.append("\n");
            sb.append("Operator of:- " + strOpratorName);*/
            String alert1 = "Bill Amount of : " + strBillAmount;
            String alert2 = "Service For: " + "Credit Card Bill Pay";
            String alert3 = "Service Provider: " + service;

            String messageText = "Do you want to continue to bill pay." +
                    "\n\n" + alert1 +
                    "\n" + alert2 +
                    "\n" + alert3;

            AlertDialogUtils.showAlertdialogContext(CreditCardBillPayActivity.this, new AlertDialogButtonListener() {
                @Override
                public void onButtontapped(String btnText) {
                    if (btnText.equalsIgnoreCase("YES")) {

                        /*Call bill pay send otp api*/
                        billPaySendOtp();
                    }
                }
            }, "Bill Payment", messageText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* private void showProgressbar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }*/


    private TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(final Editable s) {
            // user typed: start the timer
           /* if (s.length() != 4) {
                Toast.makeText(CreditCardBillPayActivity.this, "Please enter complete card no",
                        Toast.LENGTH_SHORT).show();
            }*/
            // else {

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    final String cardno4 = s.toString();

                    if (cardno4.length() == 4) {

                        strCardNo1 = "";
                        strCardNo2 = "";
                        strCardNo3 = "";
                        strCardNo4 = "";

                        strConfCardNo1 = "";
                        strConfCardNo2 = "";
                        strConfCardNo3 = "";
                        strConfCardNo4 = "";



                       /* if (edtxtCardNo1.getText().toString().equals("")) {
                            edtxtCardNo1.setError("Please enter credit card number");
                            edtxtCardNo1.requestFocus();
                        } else if (edtxtCardNo2.getText().toString().equals("")) {
                            edtxtCardNo2.setError("Please enter credit card number");
                            edtxtCardNo2.requestFocus();
                        } else if (edtxtCardNo3.getText().toString().equals("")) {
                            edtxtCardNo3.setError("Please enter credit card number");
                            edtxtCardNo3.requestFocus();
                        } else if (edtxtCardNo4.getText().toString().equals("")) {
                             edtxtCardNo4.setError("Please enter credit card number");
                             edtxtCardNo4.requestFocus();
                         } else if (edtxtConfCardNo1.getText().toString().equals("")) {
                            edtxtConfCardNo1.setError("Please re-enter credit card number");
                            edtxtConfCardNo1.requestFocus();
                        } else if (edtxtConfCardNo2.getText().toString().equals("")) {
                            edtxtConfCardNo2.setError("Please enter credit card number");
                            edtxtConfCardNo2.requestFocus();
                        } else if (edtxtConfCardNo3.getText().toString().equals("")) {
                            edtxtConfCardNo3.setError("Please enter credit card number");
                            edtxtConfCardNo3.requestFocus();
                        } else if (edtxtConfCardNo4.getText().toString().equals("")) {
                         edtxtConfCardNo4.setError("Please enter credit card number");
                         edtxtConfCardNo4.requestFocus();
                        }*/
                        //else {
                        strCardNo1 = edtxtCardNo1.getText().toString();
                        strCardNo2 = edtxtCardNo2.getText().toString();
                        strCardNo3 = edtxtCardNo3.getText().toString();
                        strCardNo4 = edtxtCardNo4.getText().toString();

                        strConfCardNo1 = edtxtConfCardNo1.getText().toString();
                        strConfCardNo2 = edtxtConfCardNo2.getText().toString();
                        strConfCardNo3 = edtxtConfCardNo3.getText().toString();
                        strConfCardNo4 = edtxtConfCardNo4.getText().toString();


                        // }

                    }

                    CreditCardBillPayActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // showProgressbar(true);
                            progressDialog = new ProgressDialog(CreditCardBillPayActivity.this);
                            progressDialog.setMessage("please wait..");
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                            //resultText.setVisibility(View.INVISIBLE);
                        }
                    });

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    CreditCardBillPayActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            //showProgressbar(false);
                            //showResult();
                            if (strCardNo1.equals("") || strCardNo2.equals("") || strCardNo3.equals("")
                                    || strCardNo4.equals("") || strConfCardNo1.equals("") || strConfCardNo2.equals("")
                                    || strConfCardNo3.equals("") || strConfCardNo4.equals("")) {

                                Toast.makeText(CreditCardBillPayActivity.this, "please enter card no.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                if (strCardNo1.length() == 4 && strCardNo2.length() == 4 && strCardNo3.length() == 4
                                        && strCardNo4.length() == 4 && strConfCardNo1.length() == 4 && strConfCardNo2.length() == 4
                                        && strConfCardNo3.length() == 4 && strConfCardNo4.length() == 4) {


                                    strCardNum = strCardNo1 + strCardNo2 + strCardNo3 + strCardNo4;
                                    strConfCardNum = strConfCardNo1 + strConfCardNo2 + strConfCardNo3 + strConfCardNo4;
                                    if (strCardNum.equals(strConfCardNum)) {
                                        edtxtReferenceNo.setText(strCardNum);
                                        edtxtReferenceNo.setEnabled(false);
                                        edtxtReferenceNo.setFocusable(false);
                                    } else {
                                        edtxtReferenceNo.setHint("Enter proper card no in card no field.");
                                        edtxtReferenceNo.setEnabled(true);
                                        edtxtReferenceNo.setFocusable(true);
                                    }

                                } else {
                                    Toast.makeText(CreditCardBillPayActivity.this, "card no. not complete",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }


                        }
                    });

                    // hide keyboard as well?
                    // InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    // in.hideSoftInputFromWindow(searchText.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }, 600); // 600ms delay before the timer executes the "run" method from TimerTask
            //}
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // nothing to do here
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // user is typing: reset already started timer (if existing)
            if (timer != null) {
                timer.cancel();
            }
        }
    };

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean externalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (externalStorage && cameraAccepted)
                        Snackbar.make(view, "Permission Granted, Now you can write data storage and camera.", Snackbar.LENGTH_LONG).show();
                    else {

                        Snackbar.make(view, "Permission Denied, You cannot write data in storage and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(CreditCardBillPayActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


}
