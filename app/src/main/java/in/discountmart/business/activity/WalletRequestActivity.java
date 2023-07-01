package in.discountmart.business.activity;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import in.base.network.NetworkClient;
import in.base.network.NetworkClient1;
import in.base.ui.BaseActivity;
import in.base.util.ImageUtil;
import in.discountmart.R;
import in.discountmart.business.adapter.BankListAdapter;
import in.discountmart.business.adapter.PaymodeListAdapter;
import in.discountmart.business.adapter.SpinnerSingleItemAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.business_constants.AppConstants;
import in.discountmart.business.call_api.UploadKYCServices;
import in.discountmart.business.call_api.WalletServices;
import in.discountmart.business.model_business.SpinnerSingleItemModel;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.UploadImageRequest;
import in.discountmart.business.model_business.requestmodel.WalletReqRequest;
import in.discountmart.business.model_business.requestmodel.WalletRequestBankListRequest;
import in.discountmart.business.model_business.responsemodel.BankListResponse;
import in.discountmart.business.model_business.responsemodel.BaseResponseEntity;
import in.discountmart.business.model_business.responsemodel.PayModeListResponse;
import in.discountmart.business.model_business.responsemodel.ToWalletListResponse;
import in.discountmart.business.model_business.responsemodel.UploadImageResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.listener.AlertDialogButtonListener;
import in.discountmart.utility.AlertDialogUtils;
import in.discountmart.utility.ConnectivityUtils;
import in.discountmart.utility.Utilities;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//implements PaymentResultWithDataListener
public class WalletRequestActivity extends BaseActivity  {

    private static final String TAG ="" ;
    Spinner spinnerBank;
    Spinner spinnerWallet;
    Spinner spinnerPaymentMod;

    EditText editTextAmount;
    EditText editTextRemark;
    EditText editTextTransactionNo;
    EditText editTextDDNo;
    EditText editTextChequeNo;
    EditText editTextBranch;
    ImageView imgDoc;

    TextView textViewAcNo;
    TextView textViewChoosfile;
    TextView textViewTransactionDate;
    TextView txtTransactionNo;
    public static TextView textViewDD_Date;
    public static TextView textViewChequeDate;
    public static TextView textViewTransDate;

    LinearLayout layoutTransNo;
    LinearLayout layoutTransDate;
    LinearLayout layoutBank;
    LinearLayout layoutBranch;

    Button buttonChoosFile;
    Button buttonSubmitt;
    View view;

    String stringSpinBank="";
    String strWalletType="";
    String strWalletName="";
    String stringSpinBankID="";
    String stringSpinPaymodeID="";
    String stringSpinPaymode="";
    String stringTransactionNo="";
    String stringTransactionDate="";
    String stringAmount="";
    String stringRemark="";
    String stringChoosFile="";
    String stringAccountNo="";
    String stringBankBranch="";
    String strEmail="";
    String strMobile="";

    PayModeListResponse.PaymodeList paymodeList[];
    ArrayList<PayModeListResponse.PaymodeList> paymodeListArrayList;
    ArrayList<ToWalletListResponse.ToWalletList> toWalletLists;
    PaymodeListAdapter paymodeListAdapter;


    ArrayList<BankListResponse.BankList> bankListArrayList;
    BankListResponse.BankList bankLists[];
    BankListAdapter bankAccountAdapter;
    SpinnerSingleItemAdapter walletAdapter;

    ProgressDialog pDialog;
    Context context;
    //For image upload
    private Dialog uploadOptionDialog;
    private static int RESULT_LOAD_IMG = 1;
    private static int RESULT_CLICK_IMG = 2;
    private Uri fileUri;
    private Uri fileUri2;
    private static final int REQUEST_CODE2 = 11;
    private static final int REQUEST_CODE = 0x11;
    private static final int PERMISSION_REQUEST_CODE = 200;

    static String stringDDDate;
    static String stringChequeDate;
    static String stringTransDate;
    String stringNoOfRoom;
    String stringAdult;
    String stringChild;
    String strBankId;
    String strBankName="";
    String strPaymodeID="";
    String strPaymodeName="";
    String imgPath;
    String strImgUrl="";
    int order_amoumt=0;
    int req_amoumt=0;

    // for payment gateway
    String orderId="";
    String strApiKey="";
    String keyID_test="rzp_test_0nE87tlcn32uGD";
    String secret_test="lELoCDjczrkXt2CvzLFC7G35";

    String keyID_live="rzp_live_d8vzimnTc56nYJ";
    String secret_live="2clVk9ZSZpVDGeVuMXGgSWWk";
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    public static int yyFromDate;
    public static int mmFromDate;
    public static int ddFromDate;

    public static int yyToDate;
    public static int mmToDate;
    public static int ddToDate;

    int checkSpin=0;

    Boolean msgShown = false;
    Boolean isSignature;
    Boolean bank ;
    Boolean branch ;
    Boolean transNo ;
    Boolean uploadDoc =false;

    int from;
    int to;

    int check = 0;
    Calendar calendar;
    static int yy;
    static int mm;
    static int dd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_request);
        try {
            /*Check permission for external storage and camera*/

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());


            view=findViewById(android.R.id.content);

            if (checkPermission()) {

                //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();

            } else {
                requestPermission();
                //Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
            }

            spinnerBank = (Spinner) findViewById(R.id.wallet_request_act_spiner_bank);
            spinnerWallet = (Spinner) findViewById(R.id.wallet_request_act_spiner_wallet);
            spinnerPaymentMod = (Spinner) findViewById(R.id.wallet_request_act_spiner_paymode);

            editTextAmount = (EditText) findViewById(R.id.wallet_request_act_edTxt_amount);
            editTextTransactionNo = (EditText) findViewById(R.id.wallet_request_act_edtxt_transactionno);
            //editTextTransactionDate = (EditText) rootView.findViewById(R.id.wallet_request_act_edTxt_transactionDate);
            //txtInputLayTransDate=(TextInputLayout)rootView.findViewById(R.id.wallet_request_act_txtInput_transDate);
            //txtInputLayTransNo=(TextInputLayout)rootView.findViewById(R.id.wallet_request_act_txtInput_transno);
            // editTextChequeNo=(EditText)rootView.findViewById(R.id.wallet_request_act_edtxt_chequeno);
            editTextBranch=(EditText)findViewById(R.id.wallet_request_act_edTxt_branch);
            editTextRemark = (EditText) findViewById(R.id.wallet_request_act_edTxt_remark);

            //textViewDD_Date=(TextView)rootView.findViewById(R.id.wallet_request_act_txtView_ddDate);
            textViewTransDate=(TextView)findViewById(R.id.wallet_request_act_txtView_transactionDate);
            //textViewChequeDate=(TextView)rootView.findViewById(R.id.wallet_request_act_txtView_chequeDate);

            textViewChoosfile = (TextView) findViewById(R.id.wallet_request_act_txt_img_pah);
            textViewTransactionDate=(TextView)findViewById(R.id.wallet_request_act_txt_trans_date);
            txtTransactionNo=(TextView) findViewById(R.id.wallet_request_act_txt_trans_no);

            imgDoc = (ImageView) findViewById(R.id.wallet_request_act_img_doc);
            buttonSubmitt = (Button) findViewById(R.id.wallet_request_act_btn_submitt);

            //layoutCheck=(LinearLayout)rootView.findViewById(R.id.wallet_request_frag_layout_cheque);
            //layoutDD=(LinearLayout)rootView.findViewById(R.id.wallet_request_frag_layout_dd);
            layoutTransDate = (LinearLayout) findViewById(R.id.wallet_request_act_layout_trans_date);
            layoutTransNo = (LinearLayout) findViewById(R.id.wallet_request_act_layout_trans_no);
            layoutBank = (LinearLayout) findViewById(R.id.wallet_request_act_layout_bank);
            layoutBranch = (LinearLayout) findViewById(R.id.wallet_request_act_layout_branch);

            //for Action Bar
            // Enabling Up / Back navigation
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Wallet Request");

            /* Get api key value from Shared Preference */
            strApiKey= SharedPrefrence_Business.getApiKey(this);


            if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            } else {
                //getWalletList();
                getPaymodeList();
            }
            /*Spinner Bank */
            /*spinnerWallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   SpinnerSingleItemModel list = (SpinnerSingleItemModel) parent.getItemAtPosition(position);
                    strWalletType = list.getId();
                    strWalletName = list.getName();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/

            //initspinPaymode();
            /*Spinner Payment Mode*/
            spinnerPaymentMod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                if(checkSpin==0)
//                    checkSpin++;
                    PayModeListResponse.PaymodeList paymodeList1 = (PayModeListResponse.PaymodeList) parent.getItemAtPosition(position);
                    stringSpinPaymodeID = paymodeList1.getPaymodeid();
                    stringSpinPaymode = paymodeList1.getPaymodename();



                    if(stringSpinPaymode.equals("UPI")){
                        layoutBank.setVisibility(View.GONE);
                        layoutBranch.setVisibility(View.GONE);
                        layoutTransNo.setVisibility(View.VISIBLE);
                        layoutTransDate.setVisibility(View.VISIBLE);
                        strBankId="0";
                        strBankName="";
                        editTextTransactionNo.setText("");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            editTextTransactionNo.setAutofillHints("Transaction No.");
                        }
                        txtTransactionNo.setHint("Transaction No.");
                        textViewTransDate.setText("");
                        textViewTransactionDate.setText("Transaction Date");
                    }
                    else if(stringSpinPaymode.equals("Net Banking")){
                        layoutBank.setVisibility(View.GONE);
                        layoutBranch.setVisibility(View.GONE);
                        layoutTransNo.setVisibility(View.VISIBLE);
                        layoutTransDate.setVisibility(View.VISIBLE);
                        strBankId="0";
                        strBankName="";
                        editTextTransactionNo.setText("");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            editTextTransactionNo.setAutofillHints("Transaction No.");
                        }
                        txtTransactionNo.setHint("Transaction No.");
                        textViewTransDate.setText("");
                        textViewTransactionDate.setText("Transaction Date");
                    }
                    else {

                        //layoutBank.setVisibility(View.GONE);
                       // layoutBranch.setVisibility(View.GONE);

                        if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                            Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        } else {
                            getBankList();
                        }

                        if (stringSpinPaymode.equals("--Choose Payment Mode--")) {
                            //Toast.makeText(context,"Plz Select Payment mode",Toast.LENGTH_SHORT).show();

                            layoutTransNo.setVisibility(View.GONE);
                            layoutTransDate.setVisibility(View.GONE);

                            editTextTransactionNo.setText("");
                            textViewTransDate.setText("");

                        }
                        else if (stringSpinPaymode.equals("Cash")) {

                            layoutTransNo.setVisibility(View.GONE);
                            layoutTransDate.setVisibility(View.VISIBLE);

                            editTextTransactionNo.setText("");
                            txtTransactionNo.setHint("Transaction No");
                            textViewTransDate.setText("");
                            textViewTransactionDate.setText("Transaction Date");

                        }
                        else if (stringSpinPaymode.equals("Cheque")) {

                            layoutBank.setVisibility(View.VISIBLE);
                            layoutBranch.setVisibility(View.VISIBLE);
                            layoutTransNo.setVisibility(View.VISIBLE);
                            layoutTransDate.setVisibility(View.VISIBLE);

                            editTextTransactionNo.setText("");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                editTextTransactionNo.setAutofillHints("Cheque No.");
                            }
                            txtTransactionNo.setHint("Cheque No.");
                            textViewTransDate.setText("");
                            textViewTransactionDate.setText("Cheque Date");
                        }
                        else if (stringSpinPaymode.equals("DD")) {
                            layoutBank.setVisibility(View.VISIBLE);
                            layoutBranch.setVisibility(View.VISIBLE);
                            layoutTransNo.setVisibility(View.VISIBLE);
                            layoutTransDate.setVisibility(View.VISIBLE);

                            editTextTransactionNo.setText("");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                editTextTransactionNo.setAutofillHints("DD No.");
                            }
                            txtTransactionNo.setHint("DD No.");
                            textViewTransDate.setText("");
                            textViewTransactionDate.setText("DD Date");
                        }
                        else if (stringSpinPaymode.equals("Cash in Bank")) {
                            layoutBank.setVisibility(View.VISIBLE);
                            layoutBranch.setVisibility(View.VISIBLE);
                            //layoutTransNo.setVisibility(View.VISIBLE);
                            layoutTransDate.setVisibility(View.VISIBLE);

                            editTextTransactionNo.setText("");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                editTextTransactionNo.setAutofillHints("Transaction No.");
                            }
                            txtTransactionNo.setHint("Transaction No.");
                            textViewTransDate.setText("");
                            textViewTransactionDate.setText("Transaction Date");
                        }
                        else if (stringSpinPaymode.equals("Credit Card")) {
                            layoutTransNo.setVisibility(View.VISIBLE);
                            layoutTransDate.setVisibility(View.VISIBLE);

                            editTextTransactionNo.setText("");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                editTextTransactionNo.setAutofillHints("Transaction No.");
                            }
                            txtTransactionNo.setHint("Transaction No.");
                            textViewTransDate.setText("");
                            textViewTransactionDate.setText("Transaction Date");
                        }
                        else if (stringSpinPaymode.equals("Other")) {
                            layoutTransNo.setVisibility(View.GONE);
                            layoutTransDate.setVisibility(View.VISIBLE);

                            editTextTransactionNo.setText("");
                            txtTransactionNo.setHint("Transaction No.");
                            textViewTransDate.setText("");
                            textViewTransactionDate.setText("Transaction Date");
                        }
                        else if (stringSpinPaymode.equals("NEFT")) {
                            layoutTransNo.setVisibility(View.VISIBLE);
                            layoutTransDate.setVisibility(View.VISIBLE);

                            editTextTransactionNo.setText("");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                editTextTransactionNo.setAutofillHints("Transaction No.");
                            }
                            txtTransactionNo.setHint("Transaction No.");
                            textViewTransDate.setText("");
                            textViewTransactionDate.setText("Transaction Date");
                        }
                        /*else if (stringSpinPaymode.equals("RTGS")) {
                            layoutTransNo.setVisibility(View.VISIBLE);
                            layoutTransDate.setVisibility(View.VISIBLE);

                            editTextTransactionNo.setText("");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                editTextTransactionNo.setAutofillHints("Transaction No.");
                            }
                            txtTransactionNo.setHint("Transaction No.");
                            textViewTransDate.setText("");
                            textViewTransactionDate.setText("Transaction Date");
                        }*/
                       /* else if (stringSpinPaymode.equals("IMPS")) {
                            layoutTransNo.setVisibility(View.VISIBLE);
                            layoutTransDate.setVisibility(View.VISIBLE);

                            editTextTransactionNo.setText("");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                editTextTransactionNo.setAutofillHints("Transaction No.");
                            }
                            txtTransactionNo.setHint("Transaction No.");
                            textViewTransDate.setText("");
                            textViewTransactionDate.setText("Transaction Date");
                        }*/

                    /*else if (stringSpinPaymode.equals("PayUMoney")) {
                        layoutTransNo.setVisibility(View.VISIBLE);
                        layoutTransDate.setVisibility(View.VISIBLE);

                        editTextTransactionNo.setText("");
                        textViewTransactionNo.setText("Transaction No.");
                        textViewTransDate.setText("");
                        textViewTransactionDate.setText("Transaction Date");
                    }*/
                        else if (stringSpinPaymode.equals("Paytm")) {
                            layoutTransNo.setVisibility(View.VISIBLE);
                            layoutTransDate.setVisibility(View.VISIBLE);

                            editTextTransactionNo.setText("");
                            txtTransactionNo.setHint("Transaction No.");
                            textViewTransDate.setText("");
                            textViewTransactionDate.setText("Transaction Date");
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /*Spinner Bank */
            spinnerBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    BankListResponse.BankList bankList = (BankListResponse.BankList) parent.getItemAtPosition(position);
                    stringSpinBankID = bankList.getBankcode();
                    stringSpinBank = bankList.getBankname();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            textViewTransDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectTransDateFragment();
                    newFragment.show(getSupportFragmentManager(), "DatePicker");
                }
            });

       /* textViewDD_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDD_DateFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "DatePicker");
            }
        });

        textViewChequeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectCheckDateFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "DatePicker");
            }
        });*/
            /*Text upload doc click listner*/
            imgDoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission()) {

                        //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(WalletRequestActivity.this);
                        LayoutInflater inflater = WalletRequestActivity.this.getLayoutInflater();

                        View view = inflater.inflate(R.layout.business_uploadimgdialog, null);
                        ImageView camera = (ImageView) view.findViewById(R.id.imgcamera);
                        LinearLayout layoutCamera=(LinearLayout)view.findViewById(R.id.upload_dialog_layout_camera);
                        LinearLayout layoutGallery=(LinearLayout)view.findViewById(R.id.upload_dialog_layout_gallery);
                        ImageView gallery = (ImageView) view.findViewById(R.id.imggallery);
                        ImageView cross = (ImageView) view.findViewById(R.id.cross);


                        cross.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                uploadOptionDialog.dismiss();
                            }
                        });
                        layoutGallery.setOnClickListener(new View.OnClickListener() {
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
                        layoutCamera.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                //String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                                //ActivityCompat.requestPermissions(CreditCardBillPayActivity.this, permissions, REQUEST_CODE); // without sdk version check
                                /*Check permission for external storage and camera*/

                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                fileUri = getOutputMediaFileUri(1);
                                if(!AppConstants.Uri.equals(""))
                                    AppConstants.Uri="";

                                AppConstants.Uri = fileUri.getPath();
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                startActivityForResult(intent, RESULT_CLICK_IMG);
                                uploadOptionDialog.dismiss();
                            }}
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

            /*Button Submitt*/
            buttonSubmitt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(stringSpinPaymodeID.equals("0")){

                            Toast.makeText(WalletRequestActivity.this, "Plz Select Payment Mode", Toast.LENGTH_SHORT).show();
                            /*if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
                                editTextRemark.requestFocus();


                            }
                            else {
                                //Toast.makeText(WalletRequestActivity.this, "Plz Select Payment Mode", Toast.LENGTH_SHORT).show();
                                stringRemark = editTextRemark.getText().toString();
                                stringAmount = editTextAmount.getText().toString();

                               // getOrderID_PaymentGateway(keyID_live,secret_live,stringAmount);
                            }*/


                        }
                        /*else if(strWalletName.equals("---Select Wallet---")){
                            Toast.makeText(WalletRequestActivity.this, "Plz Select Wallet", Toast.LENGTH_SHORT).show();
                        }*/
                        else if(stringSpinPaymode.equals("Cash")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Transaction Date", Toast.LENGTH_SHORT).show();
                            }
                           /* else if(stringSpinBankID.equals("0")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                            }
                            else if(editTextBranch.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                                editTextBranch.requestFocus();
                            }*/

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
                                editTextRemark.requestFocus();


                            }


                            else {
                                stringAmount = editTextAmount.getText().toString();
                                stringBankBranch=editTextBranch.getText().toString();
                                stringTransactionNo="0";
                                stringTransactionDate=textViewTransDate.getText().toString();
                                strBankId = "0";
                                strBankName = stringSpinBank;
                                strPaymodeID = stringSpinPaymodeID;
                                strPaymodeName = stringSpinPaymode;
                                stringAccountNo = "0";
                                stringChoosFile = textViewChoosfile.getText().toString();
                                stringRemark = editTextRemark.getText().toString();
                                if (!uploadDoc) {
                                    // call Api for Update Address Proof
                                       /* if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                            Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                        } else {
                                            getWalletRequest();
                                        }*/
                                    Toast.makeText(WalletRequestActivity.this,"Please attach doc / image according to select paymode option  ", Toast.LENGTH_SHORT).show();


                                }
                                else {
                                    if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                        Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest("");
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("Cheque")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Cheque No.", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Cheque Date", Toast.LENGTH_SHORT).show();
                            }
                            else if(stringSpinBankID.equals("0")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                            }
                            else if(editTextBranch.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                                editTextBranch.requestFocus();
                            }

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
                                editTextRemark.requestFocus();


                            }


                            else {
                                stringAmount = editTextAmount.getText().toString();
                                stringBankBranch=editTextBranch.getText().toString();
                                stringTransactionNo=editTextTransactionNo.getText().toString();
                                stringTransactionDate=textViewTransDate.getText().toString();
                                strBankId = stringSpinBankID;
                                strBankName = stringSpinBank;
                                strPaymodeID = stringSpinPaymodeID;
                                strPaymodeName = stringSpinPaymode;
                                stringAccountNo = "0";
                                stringChoosFile = textViewChoosfile.getText().toString();
                                stringRemark = editTextRemark.getText().toString();
                                if (!uploadDoc) {
                                    // call Api for Update Address Proof
                                       /* if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                            Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                        } else {
                                            getWalletRequest();
                                        }*/
                                    Toast.makeText(WalletRequestActivity.this,"Please attach doc / image according to select paymode option  ", Toast.LENGTH_SHORT).show();


                                }
                                else {
                                    if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                        Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest("");
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("DD")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter DD No.", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter DD Date", Toast.LENGTH_SHORT).show();
                            }
                            else if(stringSpinBankID.equals("0")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                            }
                            else if(editTextBranch.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                                editTextBranch.requestFocus();
                            }

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
                                editTextRemark.requestFocus();


                            }


                            else {
                                stringAmount = editTextAmount.getText().toString();
                                stringBankBranch=editTextBranch.getText().toString();
                                stringTransactionNo=editTextTransactionNo.getText().toString();
                                stringTransactionDate=textViewTransDate.getText().toString();
                                strBankId = stringSpinBankID;
                                strBankName = stringSpinBank;
                                strPaymodeID = stringSpinPaymodeID;
                                strPaymodeName = stringSpinPaymode;
                                stringAccountNo = "0";
                                stringChoosFile = textViewChoosfile.getText().toString();
                                stringRemark = editTextRemark.getText().toString();
                                if (!uploadDoc) {
                                    // call Api for Update Address Proof
                                       /* if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                            Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                        } else {
                                            getWalletRequest();
                                        }*/
                                    Toast.makeText(WalletRequestActivity.this,"Please attach doc / image according to select paymode option  ", Toast.LENGTH_SHORT).show();


                                }
                                else {
                                    if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                        Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest("");
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("Credit Card")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Credit Card No.", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter  Credit Card Date", Toast.LENGTH_SHORT).show();
                            }
                            else if(stringSpinBankID.equals("0")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                            }
                       /* else if(editTextBranch.getText().toString().equals("")){
                            Toast.makeText(WalletRequestActivity.this, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                            editTextBranch.requestFocus();
                        }*/

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
                                editTextRemark.requestFocus();


                            }


                            else {
                                stringAmount = editTextAmount.getText().toString();
                                stringBankBranch="";
                                stringTransactionNo=editTextTransactionNo.getText().toString();
                                stringTransactionDate=textViewTransDate.getText().toString();
                                strBankId = stringSpinBankID;
                                strBankName = stringSpinBank;
                                strPaymodeID = stringSpinPaymodeID;
                                strPaymodeName = stringSpinPaymode;
                                stringAccountNo = "";
                                stringChoosFile = textViewChoosfile.getText().toString();
                                stringRemark = editTextRemark.getText().toString();
                                if (!uploadDoc) {
                                    // call Api for Update Address Proof
                                       /* if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                            Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                        } else {
                                            getWalletRequest();
                                        }*/
                                    Toast.makeText(WalletRequestActivity.this,"Please attach doc / image according to select paymode option  ", Toast.LENGTH_SHORT).show();


                                }
                                else {
                                    if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                        Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest("");
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("Other")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                       /* else if(editTextTransactionNo.getText().toString().equals("")){
                            Toast.makeText(WalletRequestActivity.this, "Plz Enter "+textViewTransactionNo.getText().toString(), Toast.LENGTH_SHORT).show();
                            editTextTransactionNo.requestFocus();
                        }*/
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Transaction Date"+textViewTransactionDate.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                       /* else if(stringSpinBankID.equals("0")){
                            Toast.makeText(WalletRequestActivity.this, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                        }*/
                       /* else if(editTextBranch.getText().toString().equals("")){
                            Toast.makeText(WalletRequestActivity.this, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                            editTextBranch.requestFocus();
                        }*/

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
                                editTextRemark.requestFocus();


                            }


                            else {
                                stringAmount = editTextAmount.getText().toString();
                                stringBankBranch="";
                                stringTransactionNo="0";
                                stringTransactionDate=textViewTransDate.getText().toString();
                                strBankId = "0";
                                strBankName = "";
                                strPaymodeID = stringSpinPaymodeID;
                                strPaymodeName = stringSpinPaymode;
                                stringAccountNo = "0";
                                stringChoosFile = textViewChoosfile.getText().toString();
                                stringRemark = editTextRemark.getText().toString();
                                if (!uploadDoc) {
                                    // call Api for Update Address Proof
                                       /* if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                            Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                        } else {
                                            getWalletRequest();
                                        }*/
                                    Toast.makeText(WalletRequestActivity.this,"Please attach doc / image according to select paymode option  ", Toast.LENGTH_SHORT).show();


                                }
                                else {
                                    if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                        Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest("");
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("NEFT")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Transaction Date", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter "+textViewTransactionDate.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                            else if(stringSpinBankID.equals("0")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                            }
                       /* else if(editTextBranch.getText().toString().equals("")){
                            Toast.makeText(WalletRequestActivity.this, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                            editTextBranch.requestFocus();
                        }*/

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
                                editTextRemark.requestFocus();


                            }


                            else {
                                stringAmount = editTextAmount.getText().toString();
                                stringBankBranch="";
                                stringTransactionNo=editTextTransactionNo.getText().toString();
                                stringTransactionDate=textViewTransDate.getText().toString();
                                strBankId = stringSpinBankID;
                                strBankName = stringSpinBank;
                                strPaymodeID = stringSpinPaymodeID;
                                strPaymodeName = stringSpinPaymode;
                                stringAccountNo = "0";
                                stringChoosFile = textViewChoosfile.getText().toString();
                                stringRemark = editTextRemark.getText().toString();
                                if (!uploadDoc) {
                                    // call Api for Update Address Proof
                                       /* if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                            Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                        } else {
                                            getWalletRequest();
                                        }*/
                                    Toast.makeText(WalletRequestActivity.this,"Please attach doc / image according to select paymode option  ", Toast.LENGTH_SHORT).show();


                                }
                                else {
                                    if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                        Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest("");
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("RTGS")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Transaction Date", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter "+textViewTransactionDate.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                            else if(stringSpinBankID.equals("0")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                            }
                       /* else if(editTextBranch.getText().toString().equals("")){
                            Toast.makeText(WalletRequestActivity.this, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                            editTextBranch.requestFocus();
                        }*/

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
                                editTextRemark.requestFocus();


                            }


                            else {
                                stringAmount = editTextAmount.getText().toString();
                                stringBankBranch="";
                                stringTransactionNo=editTextTransactionNo.getText().toString();
                                stringTransactionDate=textViewTransDate.getText().toString();
                                strBankId = stringSpinBankID;
                                strBankName = stringSpinBank;
                                strPaymodeID = stringSpinPaymodeID;
                                strPaymodeName = stringSpinPaymode;
                                stringAccountNo = "0";
                                stringChoosFile = textViewChoosfile.getText().toString();
                                stringRemark = editTextRemark.getText().toString();
                                if (!uploadDoc) {
                                    // call Api for Update Address Proof
                                       /* if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                            Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                        } else {
                                            getWalletRequest();
                                        }*/
                                    Toast.makeText(WalletRequestActivity.this,"Please attach doc / image according to select paymode option  ", Toast.LENGTH_SHORT).show();


                                }
                                else {
                                    if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                        Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest("");
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("IMPS")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Transaction Date", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter "+textViewTransactionDate.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                            else if(stringSpinBankID.equals("0")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                            }
                        /*else if(editTextBranch.getText().toString().equals("")){
                            Toast.makeText(WalletRequestActivity.this, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                            editTextBranch.requestFocus();
                        }*/

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
                                editTextRemark.requestFocus();


                            }


                            else {
                                stringAmount = editTextAmount.getText().toString();
                                stringBankBranch="";
                                stringTransactionNo=editTextTransactionNo.getText().toString();
                                stringTransactionDate=textViewTransDate.getText().toString();
                                strBankId = stringSpinBankID;
                                strBankName = stringSpinBank;
                                strPaymodeID = stringSpinPaymodeID;
                                strPaymodeName = stringSpinPaymode;
                                stringAccountNo = "0";
                                stringChoosFile = textViewChoosfile.getText().toString();
                                stringRemark = editTextRemark.getText().toString();
                                if (!uploadDoc) {
                                    // call Api for Update Address Proof
                                       /* if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                            Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                        } else {
                                            getWalletRequest();
                                        }*/
                                    Toast.makeText(WalletRequestActivity.this,"Please attach doc / image according to select paymode option  ", Toast.LENGTH_SHORT).show();


                                }
                                else {
                                    if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                        Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest("");
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("PayUMoney")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Transaction Date", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter "+textViewTransactionDate.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                       /* else if(stringSpinBankID.equals("0")){
                            Toast.makeText(WalletRequestActivity.this, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                        }*/
                        /*else if(editTextBranch.getText().toString().equals("")){
                            Toast.makeText(WalletRequestActivity.this, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                            editTextBranch.requestFocus();
                        }*/

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
                                editTextRemark.requestFocus();


                            }


                            else {
                                stringAmount = editTextAmount.getText().toString();
                                stringBankBranch="";
                                stringTransactionNo=editTextTransactionNo.getText().toString();
                                stringTransactionDate=textViewTransDate.getText().toString();
                                strBankId = "0";
                                strBankName = "";
                                strPaymodeID = stringSpinPaymodeID;
                                strPaymodeName = stringSpinPaymode;
                                stringAccountNo = "0";
                                stringChoosFile = textViewChoosfile.getText().toString();
                                stringRemark = editTextRemark.getText().toString();
                                if (!uploadDoc) {
                                    // call Api for Update Address Proof
                                       /* if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                            Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                        } else {
                                            getWalletRequest();
                                        }*/
                                    Toast.makeText(WalletRequestActivity.this,"Please attach doc / image according to select paymode option  ", Toast.LENGTH_SHORT).show();


                                }
                                else {
                                    if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                        Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest("");
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("PayTM")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Transaction Date", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter "+textViewTransactionDate.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                       /* else if(stringSpinBankID.equals("0")){
                            Toast.makeText(WalletRequestActivity.this, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                        }*/
                       /* else if(editTextBranch.getText().toString().equals("")){
                            Toast.makeText(WalletRequestActivity.this, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                            editTextBranch.requestFocus();
                        }*/

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
                                editTextRemark.requestFocus();


                            }
                            else {
                                stringAmount = editTextAmount.getText().toString();
                                stringBankBranch="";
                                stringTransactionNo=editTextTransactionNo.getText().toString();
                                stringTransactionDate=textViewTransDate.getText().toString();
                                strBankId ="0";
                                strBankName ="";
                                strPaymodeID = stringSpinPaymodeID;
                                strPaymodeName = stringSpinPaymode;
                                stringAccountNo = "0";
                                stringChoosFile = textViewChoosfile.getText().toString();
                                stringRemark = editTextRemark.getText().toString();
                                if (!uploadDoc) {
                                    // call Api for Update Address Proof
                                       /* if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                            Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                        } else {
                                            getWalletRequest();
                                        }*/
                                    Toast.makeText(WalletRequestActivity.this,"Please attach doc / image according to select paymode option  ", Toast.LENGTH_SHORT).show();


                                }
                                else {
                                    if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                        Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest("");
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("UPI")){

                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Transaction no.", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter "+textViewTransactionDate.getText().toString(), Toast.LENGTH_SHORT).show();
                            }

                            else if (!uploadDoc) {

                                Toast.makeText(WalletRequestActivity.this,"Please attach upi transaction image", Toast.LENGTH_SHORT).show();
                            }
                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
                                editTextRemark.requestFocus();


                            }

                            else {

                                stringAmount = editTextAmount.getText().toString();
                                stringBankBranch="";
                                stringTransactionNo=editTextTransactionNo.getText().toString();
                                stringTransactionDate=textViewTransDate.getText().toString();
                                strBankId ="0";
                                strBankName ="";
                                strPaymodeID = stringSpinPaymodeID;
                                strPaymodeName = stringSpinPaymode;
                                stringAccountNo = "0";
                                stringChoosFile = textViewChoosfile.getText().toString();
                                stringRemark = editTextRemark.getText().toString();
                                if (!uploadDoc) {
                                    // call Api for Update Address Proof
                                       /* if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                            Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                        } else {
                                            getWalletRequest();
                                        }*/
                                    Toast.makeText(WalletRequestActivity.this,"Please attach doc / image according to select paymode option  ", Toast.LENGTH_SHORT).show();


                                }
                                else {
                                    if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                        Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest("");
                                    }

                                }

                            }
                        }
                        else if(stringSpinPaymode.equals("Net Banking")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Transaction no.", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter "+textViewTransactionDate.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                            else if (!uploadDoc) {

                                Toast.makeText(WalletRequestActivity.this,"Please attach net banking transaction doc.", Toast.LENGTH_SHORT).show();
                            }

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
                                editTextRemark.requestFocus();

                            }


                            else {

                                stringAmount = editTextAmount.getText().toString();
                                stringBankBranch="";
                                stringTransactionNo=editTextTransactionNo.getText().toString();
                                stringTransactionDate=textViewTransDate.getText().toString();
                                strBankId ="0";
                                strBankName ="";
                                strPaymodeID = stringSpinPaymodeID;
                                strPaymodeName = stringSpinPaymode;
                                stringAccountNo = "0";
                                stringChoosFile = textViewChoosfile.getText().toString();
                                stringRemark = editTextRemark.getText().toString();

                                if (!uploadDoc) {
                                    // call Api for Update Address Proof
                                       /* if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                            Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                        } else {
                                            getWalletRequest();
                                        }*/
                                    Toast.makeText(WalletRequestActivity.this,"Please attach doc / image according to select paymode option  ", Toast.LENGTH_SHORT).show();


                                } else {
                                    if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                        Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest("");
                                    }

                                }

                            }
                        }
                        else if(stringSpinPaymode.equals("Cash in Bank")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                           /* else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Cheque No.", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }*/
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Cheque Date", Toast.LENGTH_SHORT).show();
                            }
                            else if(stringSpinBankID.equals("0")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                            }
                            else if(editTextBranch.getText().toString().equals("")){
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                                editTextBranch.requestFocus();
                            }

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(WalletRequestActivity.this, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
                                editTextRemark.requestFocus();


                            }


                            else {
                                stringAmount = editTextAmount.getText().toString();
                                stringBankBranch=editTextBranch.getText().toString();
                                stringTransactionNo="0";
                                stringTransactionDate=textViewTransDate.getText().toString();
                                strBankId = stringSpinBankID;
                                strBankName = stringSpinBank;
                                strPaymodeID = stringSpinPaymodeID;
                                strPaymodeName = stringSpinPaymode;
                                stringAccountNo = "0";
                                stringChoosFile = textViewChoosfile.getText().toString();
                                stringRemark = editTextRemark.getText().toString();
                                if (!uploadDoc) {
                                    // call Api for Update Address Proof
                                       /* if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                            Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                        } else {
                                            getWalletRequest();
                                        }*/
                                    Toast.makeText(WalletRequestActivity.this,"Please attach doc / image according to select paymode option  ", Toast.LENGTH_SHORT).show();


                                }
                                else {
                                    if (!ConnectivityUtils.isNetworkEnabled(WalletRequestActivity.this)) {
                                        Toast.makeText(WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest("");
                                    }

                                }


                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(WalletRequestActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void initspinPaymode(){

        ArrayList<String> spinPaymodeList;
        spinPaymodeList = new ArrayList<String>();

        spinPaymodeList.add(0,"Choose Payment Mode");
        spinPaymodeList.add(1,"Cash");
        spinPaymodeList.add(2,"Cheque");
        spinPaymodeList.add(3,"DD");
        spinPaymodeList.add(4,"Credit Card");
        spinPaymodeList.add(5,"Other");

        ArrayAdapter aa = new ArrayAdapter(WalletRequestActivity.this,android.R.layout.simple_spinner_item,spinPaymodeList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerPaymentMod.setAdapter(aa);
    }

    public void isTransactionDateValidation(){
        if(layoutTransDate.getVisibility()== View.VISIBLE){
            if(textViewTransDate.getText().toString().equals("")){
                Toast.makeText(WalletRequestActivity.this, "Plz Enter "+textViewTransactionDate.getText().toString(), Toast.LENGTH_SHORT).show();
                textViewTransDate.requestFocus();
            }else {
                stringTransactionDate=textViewTransDate.getText().toString();
            }
        }
        else{
            stringTransactionDate="none";
        }
    }

    public void isTransactionNoValidation(){
        if(layoutTransNo.getVisibility()== View.VISIBLE){
            if(editTextTransactionNo.getText().toString().equals("")){
                Toast.makeText(WalletRequestActivity.this, "Plz Enter Transaction No.", Toast.LENGTH_SHORT).show();
                editTextTransactionNo.requestFocus();
            }else {
                stringTransactionNo=editTextTransactionNo.getText().toString();
            }
        }
        else{
            stringTransactionNo="0";
        }
    }

    public void isBankValidation(){
        if(layoutBank.getVisibility()== View.VISIBLE){
            if(stringSpinBankID.equals("0")){
                Toast.makeText(WalletRequestActivity.this, "Plz Select Bank ", Toast.LENGTH_SHORT).show();
                ///editTextTransactionNo.requestFocus();
            }else {
                strBankId=stringSpinBankID;
            }
        }
        else{
            strBankId="0";
        }
    }

    public void isBankBranshValidation(){
        if(layoutBranch.getVisibility()== View.VISIBLE){
            if(editTextBranch.getText().toString().equals("")){
                Toast.makeText(WalletRequestActivity.this, "Plz Enter Branch Name ", Toast.LENGTH_SHORT).show();
                editTextBranch.requestFocus();
            }else {
                stringBankBranch=editTextBranch.getText().toString();
            }
        }
        else{
            stringBankBranch="none";
        }
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
        super.onActivityResult(requestCode,resultCode,data);
        try {
            int compressionRatio=4;

            // When an Image is
            if (requestCode == 2) {
                AppConstants.imgpath="";

                if (resultCode == RESULT_OK) {

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // down sizing image as it throws OutOfMemory Exception for larger images
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(AppConstants.Uri,options);
                    //bitmap.compress(Bitmap.CompressFormat.JPEG,2, new FileOutputStream(AppConstants.Uri));


                    textViewChoosfile.setText(AppConstants.Uri);
                    AppConstants.ImageUri= AppConstants.Uri;
                    //imgViewDoc.setImageBitmap(bitmap);

                    uploadAttachment();


                } else if (resultCode ==RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(WalletRequestActivity.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();

                } else {
                    // failed to capture image
                    Toast.makeText(WalletRequestActivity.this, "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
                }


            }
            // When an Image is picked from gallery

            else if(requestCode == 1){

                AppConstants.Uri="";
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = WalletRequestActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgPath = cursor.getString(columnIndex);
                    cursor.close();

                    // Set the Image in ImageView
                    final Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
                    //bitmap.compress(Bitmap.CompressFormat.JPEG,2, new FileOutputStream(imgPath));

                    textViewChoosfile.setText(imgPath);
                    //imgViewDoc.setImageBitmap(bitmap);

                    AppConstants.imgpath = imgPath;
                    AppConstants.ImageUri=imgPath;

                    //upload image
                     uploadAttachment();

                } else if (resultCode ==RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(WalletRequestActivity.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();
                }

            }

        }catch (Exception e) {
            Toast.makeText(WalletRequestActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    // When Consult button is clicked
    public void uploadAttachment() {

        if (AppConstants.Uri != null && AppConstants.Uri != "") {
            // When Image is capturted from Camera
            sendImageUploadRequest(AppConstants.Uri);
        } else if (AppConstants.imgpath != null && !AppConstants.imgpath.isEmpty()) {
            // When Image is selected from Gallery
            sendImageUploadRequest(AppConstants.imgpath);
        }
        else {
            Toast.makeText(WalletRequestActivity.this, "NO FILE SELECTED TO UPLOAD", Toast.LENGTH_LONG).show();
        }
    }
    private void sendImageUploadRequest(final String filePath) {
        Handler mHandler = new Handler(WalletRequestActivity.this.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                UploadImageHandler1 uploadImageHandler = new UploadImageHandler1(filePath);
//
//                String stringParameter=sendUpdateBankProofDetailRequest();
//                uploadImageHandler.execute(ApiConstants.Baseurl, stringParameter);
                //uploadImageHandler.execute(AppConstants.uploadProfileImage, "");
                UpdateProfileImage(filePath);

            }
        });
    }
    private String getMyJson() {
        String MyJson;

        String urlParameters="";
        String Get_Url="";

        MyJson= ApiConstants.Baseurl+urlParameters;

        return MyJson;
    }


    /*private MultipartEntity getMultipartEntity(String myJsonRequest) {
        try {
            MultipartEntity multipartEntity = new MultipartEntity();

            if (mAttachedFilePath != null && !mAttachedFilePath.equals("")) {
                int end = mAttachedFilePath.toString().lastIndexOf("/");
                String str1 = mAttachedFilePath.toString().substring(0, end);
                String str2 = mAttachedFilePath.toString().substring(end + 1, mAttachedFilePath.length());

                str1 = str1.replaceAll(" ", "%20");

                str2 = str2.replaceAll(" ", "%20");

                File file = new File(new URI("file://" + str1 + "/" + str2));

                try {
                    FileBody fileBody = new FileBody(file);
                    multipartEntity.addPart("file1", fileBody);
                    multipartEntity.addPart("MyJson", new StringBody(myJsonRequest));

                } catch (Exception e) {
                    e.printStackTrace();
                }

               *//* bitmapResizedImage = decodeFileIntoBitmap(file);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmapResizedImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteData = byteArrayOutputStream.toByteArray();
                ByteArrayBody byteArrayBody = new ByteArrayBody(byteData, "image.png"); // second parameter is the name of the image (//TODO HOW DO I MAKE IT USE THE IMAGE FILENAME?)

                try {
                    multipartEntity.addPart("profile_image", byteArrayBody);
                    multipartEntity.addPart("MyJson", new StringBody(fileTypeString));

                } catch (Exception e) {
                    e.printStackTrace();
                }*//*

            }
            return multipartEntity;

        } catch (Exception e) {
            e.printStackTrace();


            return null;
        }
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
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(WalletRequestActivity.this, WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(WalletRequestActivity.this, CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
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
        new AlertDialog.Builder(WalletRequestActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    // date picker open for CHECKIN-Date and selected date set to textview

    /*TRansaction DAte*/
    public static class SelectTransDateFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar1 = Calendar.getInstance();
            int yy = calendar1.get(Calendar.YEAR);
            int mm = calendar1.get(Calendar.MONTH);
            int dd = calendar1.get(Calendar.DAY_OF_MONTH);

            //Disable date before today date
            DatePickerDialog dpd = new DatePickerDialog(getContext(),R.style.AlertDialogTheme, this, yy, mm, dd);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                int thisMonth = mm + 1;
                String stringTodayDate = dd + "/" + thisMonth + "/" + yy;
                Date todayDate = sdf.parse(stringTodayDate);
                //dpd.getDatePicker().setMaxDate(todayDate.getTime());

            } catch (ParseException e) {
                //handle exception
            }
            return dpd;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            SetTRansDate(yy, mm +1, dd);
        }
    }

    public static void SetTRansDate(int year, int month, int day) {
        String Month = Utilities.convertMonth(month);
        // text.setText(day + " " + Month + " " + year);

        textViewTransDate.setText(day + "-" + Month + "-" + year);
        stringTransDate = (day+"-"+ Utilities.convertMonth(month) +"-"+ year ).toString();


        yyToDate = year;
        mmToDate = month;
        ddToDate = day;

        Calendar mCalendarTo = Calendar.getInstance();
        mCalendarTo.set( yyToDate,mmToDate,ddToDate);
        mCalendarTo.add(Calendar.DATE, 1);
        int yy = mCalendarTo.get(Calendar.YEAR);
        int mm = mCalendarTo.get(Calendar.MONTH);
        int dd = mCalendarTo.get(Calendar.DAY_OF_MONTH);
        String mon = Utilities.convertMonthtoText(mm);

        //set Next Date as Check out date
        //textViewToDate.setText(dd  + " " + mon + " " + yy);
        //stringToDate = (yy + "-" + Utilities.convertMonthNumber(mm) + "-" + Utilities.convertdayNumber(dd)).toString();
    }


    /*Get Bank List Api*/
    public void getBankList(){

        //showProgressDialog();
        pDialog=new ProgressDialog(WalletRequestActivity.this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        WalletRequestBankListRequest Request = new WalletRequestBankListRequest();

        /*Pos Method*/
        String Get_Paramenter = "";
        try {

            /*Set value in Entity class*/
            Request.setReqtype(ApiConstants.REQUEST_BANKLIST);
            Request.setPasswd(SharedPrefrence_Business.getPassword(WalletRequestActivity.this));
            Request.setUserid(SharedPrefrence_Business.getUserID(WalletRequestActivity.this));
            Request.setIslogin(SharedPrefrence_Business.getIsLogin(WalletRequestActivity.this));
            Request.setPaymodeid(stringSpinPaymodeID);
            // Request.setCountrycode("1");

            //1. Convert object to JSON string
            Gson gson = new Gson();
            Get_Paramenter = gson.toJson(Request);
            Log.e("ReqBankList:", Get_Paramenter);

        } catch (Exception e) {
        }

        Call<BankListResponse> bankListResponseCall=
                NetworkClient.getInstance(WalletRequestActivity.this).create(WalletServices.class).fetchWalletRequestBankList(Request,strApiKey);

        bankListResponseCall.enqueue(new Callback<BankListResponse>() {
            @Override
            public void onResponse(Call<BankListResponse> call, Response<BankListResponse> response) {
                //hideProgressDialog();
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try{
                    BankListResponse Response=response.body();
                    if(Response != null){
                        if(Response.getResponse().equals("OK")){

                            bankLists=Response.getBankers();
                            if(bankLists.length > 0){
                            bankListArrayList=new ArrayList<BankListResponse.BankList>(Arrays.asList(bankLists));
                            bankAccountAdapter=new BankListAdapter(WalletRequestActivity.this,bankListArrayList);
                            spinnerBank.setAdapter(bankAccountAdapter);
                            //new getBankListDetails().execute();
                            }
                            else {
                            String toast= Response.getResponse()+ "Record is Empty";
                            Toast.makeText(WalletRequestActivity.this, toast, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                        blankValueFromSharePreference(WalletRequestActivity.this,Response.getMsg());
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
                        String msg="Something went wrong. Please try again";
                        blankValueFromSharePreference(WalletRequestActivity.this,msg);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BankListResponse> call, Throwable t) {

                try{
                    //hideProgressDialog();
                    if(pDialog.isShowing())
                        pDialog.dismiss();
                    //showToast(t.getMessage());
                    Toast.makeText(WalletRequestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


    }

    /*Get Paymode List Api*/
    public void getPaymodeList(){

        // showProgressDialog();
        pDialog=new ProgressDialog(WalletRequestActivity.this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest Request = new BaseRequest();

        /*Pos Method*/
        String Get_Paramenter = "";
        try {

            /*Set value in Entity class*/
            Request.setReqtype(ApiConstants.REQUEST_PAYMODE_LIST);
            Request.setPasswd(SharedPrefrence_Business.getPassword(WalletRequestActivity.this));
            Request.setUserid(SharedPrefrence_Business.getUserID(WalletRequestActivity.this));
            Request.setIslogin(SharedPrefrence_Business.getIsLogin(WalletRequestActivity.this));

            // Request.setCountrycode("1");

            //1. Convert object to JSON string
            Gson gson = new Gson();
            Get_Paramenter = gson.toJson(Request);
            Log.e("ReqPaymodeList:", Get_Paramenter);

        } catch (Exception e) {
        }

        Call<PayModeListResponse> payModeListResponseCall=
                NetworkClient.getInstance(WalletRequestActivity.this).create(WalletServices.class).fetchPaymodeList(Request,strApiKey);

        payModeListResponseCall.enqueue(new Callback<PayModeListResponse>() {
            @Override
            public void onResponse(Call<PayModeListResponse> call, Response<PayModeListResponse> response) {
                //hideProgressDialog();
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try{
                    PayModeListResponse Response=response.body();
                    if(Response != null){
                        if(Response.getResponse().equals("OK")){
                            paymodeList=Response.getPaymode();
                            if(paymodeList.length > 0){
                                paymodeListArrayList=new ArrayList<PayModeListResponse.PaymodeList>(Arrays.asList(paymodeList));
                                paymodeListAdapter=new PaymodeListAdapter(WalletRequestActivity.this,paymodeListArrayList);
                                spinnerPaymentMod.setAdapter(paymodeListAdapter);

                            }
                            else {
                                String toast= Response.getResponse()+ "Record is Empty";
                                Toast.makeText(WalletRequestActivity.this, toast, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(WalletRequestActivity.this,Response.getMsg());
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
                        String msg="Something went wrong. Please try again";
                        blankValueFromSharePreference(WalletRequestActivity.this,msg);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PayModeListResponse> call, Throwable t) {

                try{
                    // hideProgressDialog();
                    if(pDialog.isShowing())
                        pDialog.dismiss();
                    // showToast(t.getMessage());
                    Toast.makeText(WalletRequestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


    }

    /*Get Wallet List Api*/
    public void getWalletList(){

        // showProgressDialog();
        pDialog=new ProgressDialog(WalletRequestActivity.this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest Request = new BaseRequest();

        /*Pos Method*/
        String Get_Paramenter = "";
        try {

            /*Set value in Entity class*/
            Request.setReqtype(ApiConstants.REQUEST_TO_WALLET_LIST);
            Request.setPasswd(SharedPrefrence_Business.getPassword(WalletRequestActivity.this));
            Request.setUserid(SharedPrefrence_Business.getUserID(WalletRequestActivity.this));
            Request.setIslogin(SharedPrefrence_Business.getIsLogin(WalletRequestActivity.this));

            // Request.setCountrycode("1");

            //1. Convert object to JSON string
            Gson gson = new Gson();
            Get_Paramenter = gson.toJson(Request);
            Log.e("ReqPaymodeList:", Get_Paramenter);

        } catch (Exception e) {
        }

        Call<ToWalletListResponse> payModeListResponseCall=
                NetworkClient1.getInstance(WalletRequestActivity.this).create(WalletServices.class).fetchTo_WalletList(Request,strApiKey);

        payModeListResponseCall.enqueue(new Callback<ToWalletListResponse>() {
            @Override
            public void onResponse(Call<ToWalletListResponse> call, Response<ToWalletListResponse> response) {
                //hideProgressDialog();
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try{
                    ToWalletListResponse Response=response.body();
                    if(Response != null){
                        if(Response.getResponse().equals("OK")){

                            if(Response.getTowallet()!= null && Response.getTowallet().size() > 0){
                                toWalletLists=new ArrayList<ToWalletListResponse.ToWalletList>();
                                ArrayList<SpinnerSingleItemModel> singleItemList=new ArrayList<SpinnerSingleItemModel>();
                                toWalletLists=Response.getTowallet();
                                for(int i=0; i < toWalletLists.size(); i++){
                                    SpinnerSingleItemModel singleItemModel=new SpinnerSingleItemModel();

                                    singleItemModel.setId(toWalletLists.get(i).getWallettype());
                                    singleItemModel.setName(toWalletLists.get(i).getWalletname());
                                    //singleItemModel.setAmount(pckArrayList.get(i).getKitamount());
                                    singleItemList.add(singleItemModel);
                                }
                                if(singleItemList != null && singleItemList.size() > 0) {
                                    walletAdapter = new SpinnerSingleItemAdapter(WalletRequestActivity.this, singleItemList);
                                    spinnerWallet.setAdapter(walletAdapter);
                                }
                                // paymode api
                                getPaymodeList();

                            }
                            else {
                                String toast= Response.getResponse()+ "Record is Empty";
                                Toast.makeText(WalletRequestActivity.this, toast, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(WalletRequestActivity.this,Response.getMsg());
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
                        String msg="Something went wrong. Please try again";
                        blankValueFromSharePreference(WalletRequestActivity.this,msg);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ToWalletListResponse> call, Throwable t) {

                try{
                    // hideProgressDialog();
                    if(pDialog.isShowing())
                        pDialog.dismiss();
                    // showToast(t.getMessage());
                    Toast.makeText(WalletRequestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


    }


    /* Wallet Request and Response Api*/
    /*public void getWalletRequest(){
    pDialog=new ProgressDialog(this);
    pDialog.setMessage("Please wait..");
    pDialog.setCancelable(false);
    pDialog.show();
        final WalletReqRequest Request = new WalletReqRequest();

        *//*Pos Method*//*
        String Get_Paramenter = "";
        try {

            *//*Set value in Entity class*//*
            Request.setReqtype(ApiConstants.REQUEST_WALLET);

            Request.setPasswd(SharedPrefrence_Business.getPassword(WalletRequestActivity.this));
            Request.setUserid(SharedPrefrence_Business.getUserID(WalletRequestActivity.this));
            Request.setIslogin(SharedPrefrence_Business.getIsLogin(WalletRequestActivity.this));
            //Request.setUserid(SharedPrefrence_Business.getUserMobileNumber(WalletRequestActivity.this));

            Request.setAcno(stringAccountNo);
            Request.setAmount(stringAmount);
            Request.setPaymode(strPaymodeName);
            Request.setPaymodeid(strPaymodeID);
            Request.setRemarks(stringRemark);
            Request.setTransbank(strBankName);
            Request.setTransbankid(strBankId);
            Request.setTransbranch(stringBankBranch);
            Request.setTransdate(stringTransactionDate);
            Request.setTransno(stringTransactionNo);


            //1. Convert object to JSON string
            Gson gson = new Gson();
            Get_Paramenter = gson.toJson(Request);
            Log.e("RequestWallet:", Get_Paramenter);

        } catch (Exception e) {
        }

        Call<BaseResponseEntity> callWalletRequest=
                NetworkClient.getInstance(WalletRequestActivity.this).create(WalletServices.class).fetchWalletRequest(Request,strApiKey);

        callWalletRequest.enqueue(new Callback<BaseResponseEntity>() {
            @Override
            public void onResponse(Call<BaseResponseEntity> call, Response<BaseResponseEntity> response) {
               if(pDialog.isShowing())
                   pDialog.dismiss();
                try{
                    BaseResponseEntity Response=response.body();
                    if(Response != null){
                        if(Response.getResponse().equals("OK")){
                            AlertDialogUtils.showDialogWithTwoButton(WalletRequestActivity.this, new AlertDialogButtonListener() {
                                @Override
                                public void onButtontapped(String btnText) {
                                    if (btnText.equalsIgnoreCase("yes")) {

                                        editTextAmount.setText("");
                                        spinnerBank.setSelection(0);
                                        spinnerPaymentMod.setSelection(0);
                                        editTextBranch.setText("");
                                        //editTextChequeNo.setText("");
                                        textViewTransactionDate.setText("");
                                        editTextTransactionNo.setText("");
                                        textViewChoosfile.setText("No File Attach");
                                        editTextRemark.setText("");

                                        //onResume();

                                    }
                                    else {

                                        Intent i = new Intent(WalletRequestActivity.this, DashboardActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        finish();
                                    }
                                }

                            },"Message", Response.getMsg() + "\n" + "Do you Want to Continue Wallet Request ?"  , "No", "Yes");
                        }
                        else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                            blankValueFromSharePreference(WalletRequestActivity.this,Response.getMsg());
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
                        String msg="Something went wrong. Please try again";
                        blankValueFromSharePreference(WalletRequestActivity.this,msg);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<BaseResponseEntity> call, Throwable t) {
                try {
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
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }*/


     /* Get OrderID from Order Api
    in RazorPay PaymentGateway and get Response*/

    /*public void getOrderID_PaymentGateway(String key_id, String secret_key,String strAmount){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        final GatewayOrderIdRequest Request = new GatewayOrderIdRequest();

        // concatenate username and password with colon for authentication
        String credentials = key_id + ":" + secret_key;
        // create Base64 encodet string
        final String basic =
                "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        String receipt="Receipt#"+System.currentTimeMillis();
        // rounding off the amount.
        int amount = Math.round(Float.parseFloat(strAmount) * 100);

        *//*Pos Method*//*
        String Get_Paramenter = "";
        try {

            *//*Set value in Entity class*//*
            Request.setAmount(String.valueOf(amount));
            Request.setCurrency("INR");
            Request.setReceipt(receipt);

            //1. Convert object to JSON string
            Gson gson = new Gson();
            Get_Paramenter = gson.toJson(Request);
            Log.e("RequestWallet:", Get_Paramenter);

        } catch (Exception e) {
        }

        Call<GatwayOrderIdResponse> callWalletRequest=
                NetworkClient_Pay.getInstance(WalletRequestActivity.this).create(WalletServices.class).fetchOrder(Request,basic);

        callWalletRequest.enqueue(new Callback<GatwayOrderIdResponse>() {
            @Override
            public void onResponse(Call<GatwayOrderIdResponse> call, Response<GatwayOrderIdResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try{

                    if(response.body()!= null){
                        GatwayOrderIdResponse Response=response.body();
                        if(Response != null){
                            if(Response.getStatus().equals("created")){
                                orderId=Response.getId();
                                order_amoumt= Integer.parseInt(Response.getAmount());
                                req_amoumt= Math.round(Float.parseFloat(strAmount) * 100);
                                strEmail=SharedPrefrence_Business.getEmailId(WalletRequestActivity.this);
                                strMobile=SharedPrefrence_Business.getUserMobileNumber(WalletRequestActivity.this);

                                if(order_amoumt == req_amoumt){
                                    Checkout.clearUserData(WalletRequestActivity.this);
                                    Checkout.preload(getApplicationContext());
                                    startPayment(strAmount,strEmail,strMobile,orderId);
                                }



                            }
                            else if(Response.getStatus().equals("attempted")){}
                            else if(Response.getStatus().equals("paid")){}
                            else {
                                String tost="Something went wrong please check and try again.";
                                Toast.makeText(WalletRequestActivity.this, tost , Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    else if(response.errorBody() != null){
                        ResponseBody errorResponse=response.errorBody();
                       // MyOrderResponse[]orderResponse= new Gson().fromJson(Response.getRESP_VALUE(),MyOrderResponse[].class);
                        GatwayOrderIdResponse errorResponse1=new Gson().fromJson(response.errorBody().charStream(),GatwayOrderIdResponse.class);
                        if(errorResponse1.getError().getCode().equals("BAD_REQUEST_ERROR")){
                            String toast= errorResponse1.getError().getDescription();
                            Toast.makeText(WalletRequestActivity.this, toast , Toast.LENGTH_SHORT).show();
                        }

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<GatwayOrderIdResponse> call, Throwable t) {
                try {
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
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }*/

    /*public void startPayment(String strAmount,String email, String mobile,String order_Id){
        //orderId = "OR"+System.currentTimeMillis() + "";
        String samount = editTextAmount.getText().toString();

        // rounding off the amount.
        int amount = Math.round(Float.parseFloat(strAmount) * 100);

        // initialize Razorpay account.
        Checkout checkout = new Checkout();
       // if it is possible that call to support technical staff team for get information for payment gateway integration in android

        // set your id as below
        checkout.setKeyID(keyID_live);

        // set image
         checkout.setImage(R.mipmap.logo);

        // initialize json object
        JSONObject options = new JSONObject();
        try {
            options.put("name", "Oloo Global");
            //options.put("description", "Reference No. #123456");
            options.put("image", getResources().getDrawable(R.mipmap.logo));
            options.put("order_id", order_Id);//from response of step 3.
            options.put("theme.color", "#000000");
            options.put("currency", "INR");
            options.put("amount", amount);//pass amount in currency subunits
            options.put("prefill.email", email);
            options.put("prefill.contact",mobile);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            //retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(WalletRequestActivity.this, options);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Response",e.getMessage());
        }
    }*/

    /*@Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();
        Log.e("Status",s);
        String response= "OrerId:-"+paymentData.getOrderId()+"\n"+"PaymentId:-"+ paymentData.getPaymentId()+"\n"+"Signature:-"+paymentData.getSignature();
        Log.e("Response",response);


        String rece_signature=paymentData.getSignature();
        String create_signature= null;
        String data="";
        String responsedata=new Gson().toJson(paymentData);
        if(orderId.equals(paymentData.getOrderId()))
           data = orderId+"|"+paymentData.getPaymentId();
        else
            data="";

        Log.e("Data Response",data);
        Log.e("Full Data ",responsedata);
        try {
            create_signature = hmacSha(secret_live,data,HMAC_SHA256_ALGORITHM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("Rece_Signature",rece_signature);
        Log.e("Create_Signature",create_signature);

       *//* try {
           isSignature= Utils.verifyPaymentSignature(paymentData.getData(), secret_live);
        } catch (RazorpayException e) {
            e.printStackTrace();
        }*//*



      if(rece_signature.equals(create_signature)){
          Toast.makeText(this, " Signature match Payment is successful : " + s, Toast.LENGTH_SHORT).show();
      }
      else {
          Toast.makeText(this, " Signature not match Payment is failed : " + s, Toast.LENGTH_SHORT).show();
      }
    }*/

    /*@Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

        String status="";
        if(i==Checkout.NETWORK_ERROR){
            status="There was a network error, loss of internet connectivity.";
        }
        else if(i==Checkout.INVALID_OPTIONS){

            status ="An issue with options passed in";
        }
        else if(i==Checkout.PAYMENT_CANCELED){
            status ="Payment canceled by user";

        }
        else if(i==Checkout.TLS_ERROR){
            status ="For this payment request device no support";
        }
        Toast.makeText(this, "Payment is failed : " + status, Toast.LENGTH_SHORT).show();
        Log.e("Status",s);
       // String response= "OrerId:-"+paymentData.getOrderId()+"\n"+"PaymentId:-"+ paymentData.getPaymentId()+"\n"+"Signature:-"+paymentData.getSignature();
        String errData= new Gson().toJson(paymentData);
        Log.e("ErrorResponse:",errData);
    }*/


    private String hmacSha(String KEY, String VALUE, String SHA_TYPE) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(KEY.getBytes("UTF-8"), SHA_TYPE);
            Mac mac = Mac.getInstance(SHA_TYPE);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(VALUE.getBytes("UTF-8"));
            byte[] hexArray = {(byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f'};
            byte[] hexChars = new byte[rawHmac.length * 2];
            for (int j = 0; j < rawHmac.length; j++) {
                int v = rawHmac[j] & 0xFF;
                hexChars[j * 2] = hexArray[v >>> 4];
                hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            }
            return new String(hexChars);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    private String generateHashWithHmac256(String message, String key) {
         String messageDigest="";
        /*try {
            final String hashingAlgorithm = "HmacSHA256"; //or "HmacSHA1", "HmacSHA512" HmacSHA256

            byte[] bytes = hmac(hashingAlgorithm, key.getBytes(), message.getBytes());

            messageDigest   = bytesToHex(bytes);

            Log.i(TAG, "message digest: " + messageDigest);
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        return messageDigest;

    }



    public static byte[] hmac(String algorithm, byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(message);
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /*Wallet Request and Response Api request and response*/
    private void getWalletRequest(String strfile){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        final WalletReqRequest Request = new WalletReqRequest();

        /*Pos Method*/
        String Get_Paramenter = "";


            /*Set value in Entity class*/
            Request.setReqtype(ApiConstants.REQUEST_WALLET_REQUEST);

            Request.setPasswd(SharedPrefrence_Business.getPassword(WalletRequestActivity.this));
            Request.setUserid(SharedPrefrence_Business.getUserID(WalletRequestActivity.this));
            Request.setIslogin(SharedPrefrence_Business.getIsLogin(WalletRequestActivity.this));
            //Request.setUserid(SharedPrefrence_Business.getUserMobileNumber(WalletRequestActivity.this));

            Request.setAcno(stringAccountNo);
            Request.setAmount(stringAmount);
            Request.setPaymode(strPaymodeName);
            Request.setPaymodeid(strPaymodeID);
            Request.setRemarks(stringRemark);
           // Request.setTransbank(strBankName);
            //Request.setTransbankid(strBankId);
           // Request.setTransbranch(stringBankBranch);
            Request.setTransdate(stringTransactionDate);
            Request.setTransno(stringTransactionNo);
            Request.setBankid(strBankId);
            Request.setBankname(strBankName);
            Request.setBranchname(stringBankBranch);
            Request.setImgpath(strImgUrl);
            Request.setWallettype(strWalletType);


            //1. Convert object to JSON string
            Gson gson = new Gson();
            Get_Paramenter = gson.toJson(Request);
            Log.e("RequestWallet:", Get_Paramenter);



            Call<BaseResponseEntity> callWalletRequestMulitpat=
                    NetworkClient.getInstance(WalletRequestActivity.this).create(WalletServices.class).fetchWalletRequest(Request,strApiKey);

            callWalletRequestMulitpat.enqueue(new Callback<BaseResponseEntity>() {
                @Override
                public void onResponse(Call<BaseResponseEntity> call, Response<BaseResponseEntity> response) {
                    if(pDialog.isShowing())
                        pDialog.dismiss();
                    try{
                        BaseResponseEntity Response=response.body();
                        if(Response != null){
                            if(Response.getResponse().equals("OK")){
                                AlertDialogUtils.showDialogWithOneButton(WalletRequestActivity.this, new AlertDialogButtonListener() {
                                            @Override
                                            public void onButtontapped(String btnText) {
                                                if (btnText.equals("OK")) {
                                                    Intent i = new Intent(WalletRequestActivity.this, BusinessDashboardActivity.class);
                                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(i);
                                                    finish();
                                                }


                                            }
                                        },  "Alert Dialog", Response.getMsg(),"OK" );



                            /*AlertDialogUtils.showDialogWithTwoButton(WalletRequestActivity.this, new AlertDialogButtonListener() {
                                @Override
                                public void onButtontapped(String btnText) {
                                    if (btnText.equalsIgnoreCase("yes")) {

                                        editTextAmount.setText("");
                                        spinnerBank.setSelection(0);
                                        spinnerPaymentMod.setSelection(0);
                                        editTextBranch.setText("");
                                        //editTextChequeNo.setText("");
                                        textViewTransactionDate.setText("");
                                        editTextTransactionNo.setText("");
                                        textViewChoosfile.setText("No File Attach");
                                        editTextRemark.setText("");

                                        //onResume();

                                    }
                                    else {

                                        ((DashboardActivity)WalletRequestActivity.this).loadHomeFragment();
                                    }
                                }

                            },"Message", Response.getMsg() + "\n" + "Do you Want to Continue Wallet Request ?"  , "No", "Yes");*/
                            }
                            else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                                blankValueFromSharePreference(WalletRequestActivity.this,Response.getMsg());
                            }
                            else if(Response.getResponse().equals("FAILED")) {
                                String msg=Response.getResponse()+": "+ Response.getMsg();
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
                                String msg=Response.getResponse()+ " \n"+Response.getMsg();
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
                        String msg="Something went wrong. Please try again";
                        blankValueFromSharePreference(WalletRequestActivity.this,msg);
                    }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponseEntity> call, Throwable t) {
                    try {
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
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });




    }

    /*For Profile Pic change*/
    /*Update Pan Card Detail Api Request and Response*/
    private void UpdateProfileImage(String strfile){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        final UploadImageRequest Request = new UploadImageRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_UPLOAD_IMAGE);
        Request.setPasswd(SharedPrefrence_Business.getPassword(this));
        Request.setUserid(SharedPrefrence_Business.getUserID(this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        Request.setType("WalletRequest");

        File file1=new File(ImageUtil.compressImage(strfile));
        //File file1=new File(strfile);
        String str1="";
        str1=file1.getPath();

        MultipartBody.Part body;

        if(!str1.equals("")){
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file1);
            body = MultipartBody.Part.createFormData("file:", file1.getName(), reqFile);
            // RequestBody request = RequestBody.create(MediaType.parse("text/plain"), new Gson().toJson(Request));
            RequestBody request = RequestBody.create(MultipartBody.FORM, new Gson().toJson(Request));

            Call<UploadImageResponse> callUpdatePanDetail=
                    NetworkClient.getInstance(this).create(UploadKYCServices.class).fetchUploadImage(body,request,strApiKey);

            callUpdatePanDetail.enqueue(new Callback<UploadImageResponse>() {
                @Override
                public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                    if(pDialog.isShowing())
                        pDialog.dismiss();
                    try {

                        UploadImageResponse Response =new UploadImageResponse();
                        Response=response.body();
                        if(Response != null){
                            if (Response.getResponse().equals("OK")) {

                                String toast= Response.getResponse()+ ": Upload Sucesfully" ;
                                Toast.makeText(WalletRequestActivity.this, toast, Toast.LENGTH_SHORT).show();

                                uploadDoc=true;
                                if(Response.getImage().equals("")){
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        Picasso.with(WalletRequestActivity.this)
                                                .load(String.valueOf(WalletRequestActivity.this.getDrawable(R.drawable.ic_camera_black)))
                                                .placeholder(R.drawable.ic_camera_black)
                                                .error(R.drawable.ic_camera_black)
                                                .into(imgDoc);
                                        textViewChoosfile.setText("No File Attach");
                                        strImgUrl="";
                        /*Glide.with(mContext)  //2
                                .load(String.valueOf(mContext.getDrawable(R.mipmap.home_icon_hotel))) //3
                                .centerCrop() //4
                                .placeholder(R.mipmap.home_icon_hotel) //5
                                .error(R.mipmap.home_icon_hotel) //6
                                .fallback(R.mipmap.home_icon_hotel) //7
                                .into(myViewHolder.imgHotelImg);*/ //8
                                    }
                                }

                                else{

                                    Picasso.with(WalletRequestActivity.this)
                                            .load(Response.getImage())
                                            .placeholder(R.drawable.ic_camera_black)
                                            .error(R.drawable.ic_camera_black)
                                            .into(imgDoc);

                                    strImgUrl=Response.getImage();
                                    String path =Response.getImage();
                                    String imageName = path.substring(path.lastIndexOf('/') + 1);
                                    textViewChoosfile.setText(imageName);


                   /* Glide.with(mContext)  //2
                            .load(hotelSearchList.get(position).getHotelPicture()) //3
                            //.centerCrop() //4
                            .placeholder(R.mipmap.home_icon_hotel) //5
                            .error(R.mipmap.home_icon_hotel) //6
                            .fallback(R.mipmap.home_icon_hotel) //7
                            .into(myViewHolder.imgHotelImg); //8*/
                                }


                            }

                            else if(Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")){
                                uploadDoc=false;
                                blankValueFromSharePreference(WalletRequestActivity.this,Response.getMsg());
                            }
                            else if(Response.getResponse().equals("FAILED")) {
                                uploadDoc=false;
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
                            uploadDoc=false;
                            String toast= "Something went wrong..please try again.";
                            blankValueFromSharePreference(WalletRequestActivity.this,toast);
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                    if(pDialog.isShowing())
                        pDialog.dismiss();
                    String toast= t.getMessage();
                    Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
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
        else {
            Toast.makeText(WalletRequestActivity.this,"File not Select", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onResume(){
        super.onResume();
       /* editTextAmount.setText("");
        spinnerBank.setSelection(0);
        spinnerPaymentMod.setSelection(0);
        editTextBranch.setText("");
        editTextChequeNo.setText("");
        editTextDDNo.setText("");
        editTextTransactionNo.setText("");
        textViewChequeDate.setText("");
        textViewDD_Date.setText("");
        textViewTransDate.setText("");
        textViewChoosfile.setText("No File Attach");
        editTextRemark.setText("");*/
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