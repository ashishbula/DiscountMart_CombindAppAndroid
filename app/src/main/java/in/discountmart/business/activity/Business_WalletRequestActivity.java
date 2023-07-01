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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.base.network.NetworkClient;
import in.discountmart.R;
import in.discountmart.business.adapter.BankListAdapter;
import in.discountmart.business.adapter.PaymodeListAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.business_constants.AppConstants;
import in.discountmart.business.call_api.WalletServices;
import in.discountmart.business.listener.AlertDialogButtonListener;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.WalletReqRequest;
import in.discountmart.business.model_business.requestmodel.WalletRequestBankListRequest;
import in.discountmart.business.model_business.responsemodel.BankListResponse;
import in.discountmart.business.model_business.responsemodel.BaseResponseEntity;
import in.discountmart.business.model_business.responsemodel.PayModeListResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.AlertDialogUtils;
import in.discountmart.business.utility.ConnectivityUtils;
import in.discountmart.business.utility.Utilities;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Business_WalletRequestActivity extends AppCompatActivity {

    Spinner spinnerBank;
    Spinner spinnerPaymentMod;

    EditText editTextAmount;
    EditText editTextRemark;
    EditText editTextTransactionNo;
    EditText editTextDDNo;
    EditText editTextChequeNo;
    EditText editTextBranch;

    TextView textViewAcNo;
    TextView txtClickHere;
    TextView textViewChoosfile;
    TextView textViewTransactionDate;
    TextInputLayout txtTransactionNo;
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

    PayModeListResponse.PaymodeList paymodeList[];
    ArrayList<PayModeListResponse.PaymodeList> paymodeListArrayList;
    PaymodeListAdapter paymodeListAdapter;


    ArrayList<BankListResponse.BankList> bankListArrayList;
    BankListResponse.BankList bankLists[];
    BankListAdapter bankAccountAdapter;

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
    String strApiKey="";
    String imgPath;

    public static int yyFromDate;
    public static int mmFromDate;
    public static int ddFromDate;

    public static int yyToDate;
    public static int mmToDate;
    public static int ddToDate;

    int checkSpin=0;

    Boolean msgShown = false;
    Boolean bank ;
    Boolean branch ;
    Boolean transNo ;

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
        setContentView(R.layout.activity_business_wallet_request);

        try {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            view=findViewById(android.R.id.content);

            spinnerBank = (Spinner) findViewById(R.id.wallet_request_act_spiner_bank);
            spinnerPaymentMod = (Spinner) findViewById(R.id.wallet_request_act_spiner_paymode);

            editTextAmount = (EditText) findViewById(R.id.wallet_request_act_edTxt_amount);
            editTextTransactionNo = (EditText) findViewById(R.id.wallet_request_act_edtxt_transactionno);
            //editTextTransactionDate = (EditText) rootView.findViewById(R.id.wallet_request_fragment_edTxt_transactionDate);
            //txtInputLayTransDate=(TextInputLayout)rootView.findViewById(R.id.wallet_request_fragment_txtInput_transDate);
            //txtInputLayTransNo=(TextInputLayout)rootView.findViewById(R.id.wallet_request_fragment_txtInput_transno);
            // editTextChequeNo=(EditText)rootView.findViewById(R.id.wallet_request_fragment_edtxt_chequeno);
            editTextBranch=(EditText)findViewById(R.id.wallet_request_act_edTxt_branch);
            editTextRemark = (EditText) findViewById(R.id.wallet_request_act_edTxt_remark);

            //textViewDD_Date=(TextView)rootView.findViewById(R.id.wallet_request_fragment_txtView_ddDate);
            textViewTransDate=(TextView)findViewById(R.id.wallet_request_act_txtView_transactionDate);
            txtClickHere=(TextView)findViewById(R.id.wallet_request_act_txt_click);
            //textViewChequeDate=(TextView)rootView.findViewById(R.id.wallet_request_fragment_txtView_chequeDate);

            textViewChoosfile = (TextView) findViewById(R.id.wallet_request_act_TxtView_chooseFile);
            textViewTransactionDate=(TextView)findViewById(R.id.wallet_request_act_txt_trans_date);
            txtTransactionNo=(TextInputLayout) findViewById(R.id.wallet_request_act_txtinput_trans_no);

            buttonChoosFile = (Button) findViewById(R.id.wallet_request_act_btn_chooseFile);
            buttonSubmitt = (Button) findViewById(R.id.wallet_request_act_btn_submitt);

            //layoutCheck=(LinearLayout)rootView.findViewById(R.id.wallet_request_frag_layout_cheque);
            //layoutDD=(LinearLayout)rootView.findViewById(R.id.wallet_request_frag_layout_dd);
            layoutTransDate = (LinearLayout) findViewById(R.id.wallet_request_act_layout_trans_date);
            layoutTransNo = (LinearLayout) findViewById(R.id.wallet_request_act_layout_trans_no);
            layoutBank = (LinearLayout) findViewById(R.id.wallet_request_act_layout_bank);
            layoutBranch = (LinearLayout) findViewById(R.id.wallet_request_act_layout_branch);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(this);

            /*Check permission for external storage and camera*/
            if (checkPermission()) {

                //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();

            } else {
                requestPermission();
                //Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
            }

            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            } else {
                getPaymodeList();
            }

            /*Text Click her on click
             * go and see Wallet Request Detail Page*/
            txtClickHere.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Fragment fragment = new WalletRequestDetailFragment();
                   // ((BusinessDashboardActivity)context).replaceFragment(fragment, CURRENT_TAG, null);
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

                    if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    } else {
                        getBankList();
                    }

                    /*if(stringSpinPaymodeID.equals("0")){
                        layoutTransNo.setVisibility(View.GONE);
                        layoutTransDate.setVisibility(View.GONE);

                        editTextTransactionNo.setText("");
                        textViewTransDate.setText("");
                    }
                    else if(stringSpinPaymode.equals("Cash")){
                        layoutTransNo.setVisibility(View.GONE);
                        layoutTransDate.setVisibility(View.VISIBLE);

                        editTextTransactionNo.setText("");
                        textViewTransDate.setText("");
                    }

                    else {
                        layoutTransNo.setVisibility(View.VISIBLE);
                        layoutTransDate.setVisibility(View.VISIBLE);

                        if(paymodeList1.getIsBankDtl().equals("Y")){
                            layoutBank.setVisibility(View.VISIBLE);
                            bank=true;
                        }
                        else {
                            layoutBank.setVisibility(View.GONE);
                            bank=false;
                        }
                        if(paymodeList1.getIsBranchDtl().equals("Y")){
                            layoutBranch.setVisibility(View.VISIBLE);
                            branch=true;
                        }
                        else {
                            layoutBranch.setVisibility(View.GONE);
                            branch=false;
                        }
                        if(paymodeList1.getIsTransNo().equals("Y")){
                            layoutTransNo.setVisibility(View.VISIBLE);
                            transNo=true;

                        }
                        else {
                            layoutTransNo.setVisibility(View.GONE);
                            transNo=false;

                        }
                        editTextTransactionNo.setText("");
                        textViewTransDate.setText("");
                        textViewTransactionDate.setText(paymodeList1.getTransDateLbl());
                        textViewTransactionNo.setText(paymodeList1.getTransNoLbl());
                        if(paymodeList1.getTransNoLbl().equals("Mobile No.")){
                            editTextTransactionNo.setInputType(InputType.TYPE_CLASS_NUMBER);
                           // EditText et = new EditText(context);
                            int maxLength = 10;
                            InputFilter[] FilterArray = new InputFilter[1];
                            FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                            editTextTransactionNo.setFilters(FilterArray);
                        }
                    }*/



                    if (stringSpinPaymode.equals("--Choose Payment Mode--")) {
                        //Toast.makeText(context,"Plz Select Payment mode",Toast.LENGTH_SHORT).show();

                        layoutTransNo.setVisibility(View.GONE);
                        layoutTransDate.setVisibility(View.GONE);

                        editTextTransactionNo.setText("");
                        textViewTransDate.setText("");

                    } else if (stringSpinPaymode.equals("Cash")) {

                        layoutTransNo.setVisibility(View.GONE);
                        layoutTransDate.setVisibility(View.VISIBLE);

                        editTextTransactionNo.setText("");
                        txtTransactionNo.setHint("Transaction No");
                        textViewTransDate.setText("");
                        textViewTransactionDate.setText("Transaction Date");

                    } else if (stringSpinPaymode.equals("Cheque")) {


                        layoutTransNo.setVisibility(View.VISIBLE);
                        layoutTransDate.setVisibility(View.VISIBLE);

                        editTextTransactionNo.setText("");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            editTextTransactionNo.setAutofillHints("Cheque No.");
                        }
                        txtTransactionNo.setHint("Cheque No.");
                        textViewTransDate.setText("");
                        textViewTransactionDate.setText("Cheque Date");
                    } else if (stringSpinPaymode.equals("DD")) {

                        layoutTransNo.setVisibility(View.VISIBLE);
                        layoutTransDate.setVisibility(View.VISIBLE);

                        editTextTransactionNo.setText("");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            editTextTransactionNo.setAutofillHints("DD No.");
                        }
                        txtTransactionNo.setHint("DD No.");
                        textViewTransDate.setText("");
                        textViewTransactionDate.setText("DD Date");
                    } else if (stringSpinPaymode.equals("Credit Card")) {
                        layoutTransNo.setVisibility(View.VISIBLE);
                        layoutTransDate.setVisibility(View.VISIBLE);

                        editTextTransactionNo.setText("");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            editTextTransactionNo.setAutofillHints("Transaction No.");
                        }
                        txtTransactionNo.setHint("Transaction No.");
                        textViewTransDate.setText("");
                        textViewTransactionDate.setText("Transaction Date");
                    } else if (stringSpinPaymode.equals("Other")) {
                        layoutTransNo.setVisibility(View.GONE);
                        layoutTransDate.setVisibility(View.VISIBLE);

                        editTextTransactionNo.setText("");
                        txtTransactionNo.setHint("Transaction No.");
                        textViewTransDate.setText("");
                        textViewTransactionDate.setText("Transaction Date");
                    } else if (stringSpinPaymode.equals("NEFT")) {
                        layoutTransNo.setVisibility(View.VISIBLE);
                        layoutTransDate.setVisibility(View.VISIBLE);

                        editTextTransactionNo.setText("");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            editTextTransactionNo.setAutofillHints("Transaction No.");
                        }
                        txtTransactionNo.setHint("Transaction No.");
                        textViewTransDate.setText("");
                        textViewTransactionDate.setText("Transaction Date");
                    } else if (stringSpinPaymode.equals("RTGS")) {
                        layoutTransNo.setVisibility(View.VISIBLE);
                        layoutTransDate.setVisibility(View.VISIBLE);

                        editTextTransactionNo.setText("");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            editTextTransactionNo.setAutofillHints("Transaction No.");
                        }
                        txtTransactionNo.setHint("Transaction No.");
                        textViewTransDate.setText("");
                        textViewTransactionDate.setText("Transaction Date");
                    } else if (stringSpinPaymode.equals("IMPS")) {
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

                    /*else if (stringSpinPaymode.equals("PayUMoney")) {
                        layoutTransNo.setVisibility(View.VISIBLE);
                        layoutTransDate.setVisibility(View.VISIBLE);

                        editTextTransactionNo.setText("");
                        textViewTransactionNo.setText("Transaction No.");
                        textViewTransDate.setText("");
                        textViewTransactionDate.setText("Transaction Date");
                    }*/
                   /* else if (stringSpinPaymode.equals("PayTM")) {
                        layoutTransNo.setVisibility(View.VISIBLE);
                        layoutTransDate.setVisibility(View.VISIBLE);

                        editTextTransactionNo.setText("");
                        textViewTransactionNo.setText("Transaction No.");
                        textViewTransDate.setText("");
                        textViewTransactionDate.setText("Transaction Date");
                    }*/


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
            buttonChoosFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission()) {

                        //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Business_WalletRequestActivity.this);
                        LayoutInflater inflater = getLayoutInflater();

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
                            Toast.makeText(Business_WalletRequestActivity.this, "Plz Select Payment Mode", Toast.LENGTH_SHORT).show();
                        }
                        else if(stringSpinPaymode.equals("Cash")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(Business_WalletRequestActivity.this, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter Transaction Date", Toast.LENGTH_SHORT).show();
                            }
                            else if(stringSpinBankID.equals("0")){
                                Toast.makeText(context, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                            }
                            else if(editTextBranch.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                                editTextBranch.requestFocus();
                            }

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
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
                                if (stringChoosFile.equals("No File Attach")) {
                                    // call Api for Update Address Proof
                                    /*if (!ConnectivityUtils.isNetworkEnabled(getActivity())) {
                                        Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest();
                                    }*/
                                    Toast.makeText(Business_WalletRequestActivity.this,"Please upload document", Toast.LENGTH_SHORT).show();


                                } else {
                                    if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        uploadAttachment();
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("Cheque")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter Cheque No.", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter Cheque Date", Toast.LENGTH_SHORT).show();
                            }
                            else if(stringSpinBankID.equals("0")){
                                Toast.makeText(context, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                            }
                            else if(editTextBranch.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                                editTextBranch.requestFocus();
                            }

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
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
                                if (stringChoosFile.equals("No File Attach")) {
                                    // call Api for Update Address Proof
                                    /*if (!ConnectivityUtils.isNetworkEnabled(getActivity())) {
                                        Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest();
                                    }*/
                                    Toast.makeText(Business_WalletRequestActivity.this,"Please upload document", Toast.LENGTH_SHORT).show();

                                } else {
                                    if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        uploadAttachment();
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("DD")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter DD No.", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter DD Date", Toast.LENGTH_SHORT).show();
                            }
                            else if(stringSpinBankID.equals("0")){
                                Toast.makeText(context, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                            }
                            else if(editTextBranch.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                                editTextBranch.requestFocus();
                            }

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
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
                                if (stringChoosFile.equals("No File Attach")) {
                                    // call Api for Update Address Proof
                                    /*if (!ConnectivityUtils.isNetworkEnabled(getActivity())) {
                                        Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest();
                                    }*/
                                    Toast.makeText(Business_WalletRequestActivity.this,"Please upload document", Toast.LENGTH_SHORT).show();

                                } else {
                                    if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        uploadAttachment();
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("Credit Card")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter Credit Card No.", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter  Credit Card Date", Toast.LENGTH_SHORT).show();
                            }
                            else if(stringSpinBankID.equals("0")){
                                Toast.makeText(context, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                            }
                       /* else if(editTextBranch.getText().toString().equals("")){
                            Toast.makeText(context, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                            editTextBranch.requestFocus();
                        }*/

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
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
                                if (stringChoosFile.equals("No File Attach")) {
                                    // call Api for Update Address Proof
                                   /* if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest();
                                    }*/
                                    Toast.makeText(Business_WalletRequestActivity.this,"Please upload document", Toast.LENGTH_SHORT).show();

                                } else {
                                    if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        uploadAttachment();
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("Other")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                       /* else if(editTextTransactionNo.getText().toString().equals("")){
                            Toast.makeText(context, "Plz Enter "+textViewTransactionNo.getText().toString(), Toast.LENGTH_SHORT).show();
                            editTextTransactionNo.requestFocus();
                        }*/
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter Transaction Date"+textViewTransactionDate.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                       /* else if(stringSpinBankID.equals("0")){
                            Toast.makeText(context, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                        }*/
                       /* else if(editTextBranch.getText().toString().equals("")){
                            Toast.makeText(context, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                            editTextBranch.requestFocus();
                        }*/

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
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
                                if (stringChoosFile.equals("No File Attach")) {
                                    // call Api for Update Address Proof
                                    /*if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest();
                                    }*/
                                    Toast.makeText(Business_WalletRequestActivity.this,"Please upload document", Toast.LENGTH_SHORT).show();

                                } else {
                                    if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        uploadAttachment();
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("NEFT")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter Transaction Date", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter "+textViewTransactionDate.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                            else if(stringSpinBankID.equals("0")){
                                Toast.makeText(context, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                            }
                       /* else if(editTextBranch.getText().toString().equals("")){
                            Toast.makeText(context, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                            editTextBranch.requestFocus();
                        }*/

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
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
                                if (stringChoosFile.equals("No File Attach")) {
                                    // call Api for Update Address Proof
                                   /* if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest();
                                    }*/
                                    Toast.makeText(Business_WalletRequestActivity.this,"Please upload document", Toast.LENGTH_SHORT).show();

                                } else {
                                    if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        uploadAttachment();
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("RTGS")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter Transaction Date", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter "+textViewTransactionDate.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                            else if(stringSpinBankID.equals("0")){
                                Toast.makeText(context, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                            }
                       /* else if(editTextBranch.getText().toString().equals("")){
                            Toast.makeText(context, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                            editTextBranch.requestFocus();
                        }*/

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
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
                                if (stringChoosFile.equals("No File Attach")) {
                                    // call Api for Update Address Proof
                                   /* if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest();
                                    }*/
                                    Toast.makeText(Business_WalletRequestActivity.this,"Please upload document", Toast.LENGTH_SHORT).show();

                                } else {
                                    if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        uploadAttachment();
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("IMPS")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter Transaction Date", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter "+textViewTransactionDate.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                            else if(stringSpinBankID.equals("0")){
                                Toast.makeText(context, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                            }
                        /*else if(editTextBranch.getText().toString().equals("")){
                            Toast.makeText(context, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                            editTextBranch.requestFocus();
                        }*/

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
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
                                if (stringChoosFile.equals("No File Attach")) {
                                    // call Api for Update Address Proof
                                    /*if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest();
                                    }*/
                                    Toast.makeText(Business_WalletRequestActivity.this,"Please upload document", Toast.LENGTH_SHORT).show();

                                } else {
                                    if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        uploadAttachment();
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("PayUMoney")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter Transaction Date", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter "+textViewTransactionDate.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                       /* else if(stringSpinBankID.equals("0")){
                            Toast.makeText(context, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                        }*/
                        /*else if(editTextBranch.getText().toString().equals("")){
                            Toast.makeText(context, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                            editTextBranch.requestFocus();
                        }*/

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
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
                                if (stringChoosFile.equals("No File Attach")) {
                                    // call Api for Update Address Proof
                                   /* if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest();
                                    }*/
                                    Toast.makeText(Business_WalletRequestActivity.this,"Please upload document", Toast.LENGTH_SHORT).show();

                                } else {
                                    if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        uploadAttachment();
                                    }

                                }


                            }
                        }
                        else if(stringSpinPaymode.equals("PayTM")){
                            if (editTextAmount.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Amount", Toast.LENGTH_SHORT).show();
                                editTextAmount.requestFocus();

                            }
                            else if(editTextTransactionNo.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter Transaction Date", Toast.LENGTH_SHORT).show();
                                editTextTransactionNo.requestFocus();
                            }
                            else if(textViewTransDate.getText().toString().equals("")){
                                Toast.makeText(context, "Plz Enter "+textViewTransactionDate.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                       /* else if(stringSpinBankID.equals("0")){
                            Toast.makeText(context, "Plz Select Bank", Toast.LENGTH_SHORT).show();
                        }*/
                       /* else if(editTextBranch.getText().toString().equals("")){
                            Toast.makeText(context, "Plz Enter Branch Name", Toast.LENGTH_SHORT).show();
                            editTextBranch.requestFocus();
                        }*/

                            else if (editTextRemark.getText().toString().equals("")) {
                                Toast.makeText(context, "Plz Enter Remark", Toast.LENGTH_SHORT).show();
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
                                if (stringChoosFile.equals("No File Attach")) {
                                    // call Api for Update Address Proof
                                    if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        getWalletRequest();
                                    }

                                } else {
                                    if (!ConnectivityUtils.isNetworkEnabled(Business_WalletRequestActivity.this)) {
                                        Toast.makeText(Business_WalletRequestActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        uploadAttachment();
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
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

        ArrayAdapter aa = new ArrayAdapter(Business_WalletRequestActivity.this,android.R.layout.simple_spinner_item,spinPaymodeList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerPaymentMod.setAdapter(aa);
    }

    public void isTransactionDateValidation(){
        if(layoutTransDate.getVisibility()== View.VISIBLE){
            if(textViewTransDate.getText().toString().equals("")){
                Toast.makeText(context, "Plz Enter "+textViewTransactionDate.getText().toString(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Plz Enter Transaction No.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Plz Select Bank ", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Plz Enter Branch Name ", Toast.LENGTH_SHORT).show();
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

                if (resultCode == this.RESULT_OK) {

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // down sizing image as it throws OutOfMemory Exception for larger images
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(AppConstants.Uri,options);
                    //bitmap.compress(Bitmap.CompressFormat.JPEG,2, new FileOutputStream(AppConstants.Uri));


                    textViewChoosfile.setText(AppConstants.Uri);
                    AppConstants.ImageUri= AppConstants.Uri;
                    //imgViewDoc.setImageBitmap(bitmap);

                    //uploadAttachment();


                }
                else if (resultCode ==this.RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(context, "User cancelled image capture", Toast.LENGTH_SHORT).show();

                }
                else {
                    // failed to capture image
                    Toast.makeText(context, "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
                }


            }
            // When an Image is picked from gallery

            else if(requestCode == 1){

                AppConstants.Uri="";
                if (resultCode == this.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
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
                    // uploadAttachment();

                }
                else if (resultCode == this.RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(context, "User cancelled image capture", Toast.LENGTH_SHORT).show();
                }

            }

        }catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(context, "NO FILE SELECTED TO UPLOAD", Toast.LENGTH_LONG).show();
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
                getWalletRequestWithImage(filePath);

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
        int result = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(this, CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(Business_WalletRequestActivity.this, new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, PERMISSION_REQUEST_CODE);

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
        new AlertDialog.Builder(context)
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
            DatePickerDialog dpd = new DatePickerDialog(getContext(), this, yy, mm, dd);
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

        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        WalletRequestBankListRequest Request = new WalletRequestBankListRequest();

        /*Pos Method*/
        String Get_Paramenter = "";
        try {

            /*Set value in Entity class*/
            Request.setReqtype(ApiConstants.REQUEST_BANKLIST);
            Request.setPasswd(SharedPrefrence_Business.getPassword(context));
            Request.setUserid(SharedPrefrence_Business.getUserID(context));
            Request.setIslogin(SharedPrefrence_Business.getIsLogin(context));
            Request.setPaymodeid(stringSpinPaymodeID);
            // Request.setCountrycode("1");

            //1. Convert object to JSON string
            Gson gson = new Gson();
            Get_Paramenter = gson.toJson(Request);
            Log.e("ReqBankList:", Get_Paramenter);

        } catch (Exception e) {
        }

        Call<BankListResponse> bankListResponseCall=
                NetworkClient.getInstance(context).create(WalletServices.class).fetchWalletRequestBankList(Request,strApiKey);

        bankListResponseCall.enqueue(new Callback<BankListResponse>() {
            @Override
            public void onResponse(Call<BankListResponse> call, Response<BankListResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try{
                    BankListResponse listResponse=response.body();
                    if(listResponse.getResponse().equals("OK")){
                        bankLists=listResponse.getBankers();
                        if(bankLists.length > 0){
                            bankListArrayList=new ArrayList<BankListResponse.BankList>(Arrays.asList(bankLists));
                            bankAccountAdapter=new BankListAdapter(context,bankListArrayList);
                            spinnerBank.setAdapter(bankAccountAdapter);
                            //new getBankListDetails().execute();
                        }
                        else {
                            String toast= listResponse.getResponse()+ "Record is Empty";
                            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BankListResponse> call, Throwable t) {

                try{
                    if(pDialog.isShowing())
                        pDialog.dismiss();
                    Toast.makeText(Business_WalletRequestActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


    }

    /*Get Paymode List Api*/
    public void getPaymodeList(){

        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest Request = new BaseRequest();

        /*Pos Method*/
        String Get_Paramenter = "";
        try {

            /*Set value in Entity class*/
            Request.setReqtype(ApiConstants.REQUEST_PAYMODE_LIST);
            Request.setPasswd(SharedPrefrence_Business.getPassword(this));
            Request.setUserid(SharedPrefrence_Business.getUserID(this));
            Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));

            // Request.setCountrycode("1");

            //1. Convert object to JSON string
            Gson gson = new Gson();
            Get_Paramenter = gson.toJson(Request);
            Log.e("ReqPaymodeList:", Get_Paramenter);

        } catch (Exception e) {
        }

        Call<PayModeListResponse> payModeListResponseCall=
                NetworkClient.getInstance(this).create(WalletServices.class).fetchPaymodeList(Request,strApiKey);

        payModeListResponseCall.enqueue(new Callback<PayModeListResponse>() {
            @Override
            public void onResponse(Call<PayModeListResponse> call, Response<PayModeListResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try{
                    PayModeListResponse listResponse=response.body();
                    if(listResponse.getResponse().equals("OK")){
                        paymodeList=listResponse.getPaymode();
                        if(paymodeList.length > 0){
                            paymodeListArrayList=new ArrayList<PayModeListResponse.PaymodeList>(Arrays.asList(paymodeList));
                            paymodeListAdapter=new PaymodeListAdapter(Business_WalletRequestActivity.this,paymodeListArrayList);
                            spinnerPaymentMod.setAdapter(paymodeListAdapter);


                        }
                        else {
                            String toast= listResponse.getResponse()+ "Record is Empty";
                            Toast.makeText(Business_WalletRequestActivity.this, toast, Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PayModeListResponse> call, Throwable t) {

                try{
                    if(pDialog.isShowing())
                        pDialog.dismiss();
                    Toast.makeText(Business_WalletRequestActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


    }

    /*Wallet Request and Response Api*/

    public void getWalletRequest(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        final WalletReqRequest Request = new WalletReqRequest();

        /*Pos Method*/
        String Get_Paramenter = "";
        try {

            /*Set value in Entity class*/
            Request.setReqtype(ApiConstants.REQUEST_WALLET);

            Request.setPasswd(SharedPrefrence_Business.getPassword(this));
            Request.setUserid(SharedPrefrence_Business.getUserID(this));
            Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
            //Request.setUserid(SharedPrefrence.getUserMobileNumber(th
            // ));

            Request.setAcno(stringAccountNo);
            Request.setAmount(stringAmount);
            Request.setPaymode(strPaymodeName);
            Request.setPaymodeid(strPaymodeID);
            Request.setRemarks(stringRemark);
           // Request.setTransbank(strBankName);
            //Request.setTransbankid(strBankId);
            //Request.setTransbranch(stringBankBranch);
            Request.setTransdate(stringTransactionDate);
            Request.setTransno(stringTransactionNo);


            //1. Convert object to JSON string
            Gson gson = new Gson();
            Get_Paramenter = gson.toJson(Request);
            Log.e("RequestWallet:", Get_Paramenter);

        } catch (Exception e) {
        }

        Call<BaseResponseEntity> callWalletRequest=
                NetworkClient.getInstance(this).create(WalletServices.class).fetchWalletRequest(Request,strApiKey);

        callWalletRequest.enqueue(new Callback<BaseResponseEntity>() {
            @Override
            public void onResponse(Call<BaseResponseEntity> call, Response<BaseResponseEntity> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try{
                    BaseResponseEntity Response=response.body();
                    if(Response.getResponse().equals("OK")){
                        AlertDialogUtils.showDialogWithTwoButton(Business_WalletRequestActivity.this, new AlertDialogButtonListener() {
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

                                   finish();
                                }
                            }

                        },"Message", Response.getMsg() + "\n" + "Do you Want to Continue Wallet Request ?"  , "No", "Yes");
                    }
                    else {
                        Toast.makeText(Business_WalletRequestActivity.this, Response.getResponse() +": " + Response.getMsg(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Business_WalletRequestActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    /*Wallet Request and Response Api request and response*/
    private void getWalletRequestWithImage(String strfile){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        final WalletReqRequest Request = new WalletReqRequest();

        /*Pos Method*/
        String Get_Paramenter = "";
        try {

            /*Set value in Entity class*/
            Request.setReqtype(ApiConstants.REQUEST_WALLET);

            Request.setPasswd(SharedPrefrence_Business.getPassword(this));
            Request.setUserid(SharedPrefrence_Business.getUserID(this));
            Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
            //Request.setUserid(SharedPrefrence.getUserMobileNumber(this));

            Request.setAcno(stringAccountNo);
            Request.setAmount(stringAmount);
            Request.setPaymode(strPaymodeName);
            Request.setPaymodeid(strPaymodeID);
            Request.setRemarks(stringRemark);
            //Request.setTransbank(strBankName);
            //Request.setTransbankid(strBankId);
            //Request.setTransbranch(stringBankBranch);
            Request.setTransdate(stringTransactionDate);
            Request.setTransno(stringTransactionNo);


            //1. Convert object to JSON string
            Gson gson = new Gson();
            Get_Paramenter = gson.toJson(Request);
            Log.e("RequestWallet:", Get_Paramenter);



        } catch (Exception e) {
        }


        //File file1=new File(ImageUtil.compressImage(strfile));
        File file1=new File(strfile);
        String str1="";
        str1=file1.getPath();

        int compressionRatio=25;

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        MultipartBody.Part body;



        if(!str1.equals("")){

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file1);
            body = MultipartBody.Part.createFormData("file:", file1.getName(), reqFile);
            // RequestBody request = RequestBody.create(MediaType.parse("text/plain"), new Gson().toJson(Request));
            RequestBody request = RequestBody.create(MultipartBody.FORM, new Gson().toJson(Request));


            Call<BaseResponseEntity> callWalletRequestMulitpat=
                    NetworkClient.getInstance(this).create(WalletServices.class).fetchWalletRequestWithImage(body,request,strApiKey);

            callWalletRequestMulitpat.enqueue(new Callback<BaseResponseEntity>() {
                @Override
                public void onResponse(Call<BaseResponseEntity> call, Response<BaseResponseEntity> response) {
                    if(pDialog.isShowing())
                        pDialog.dismiss();
                    try{
                        BaseResponseEntity Response=response.body();
                        if(Response.getResponse().equals("OK")){
                            AlertDialogUtils.showDialogWithTwoButton(Business_WalletRequestActivity.this, new AlertDialogButtonListener() {
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

                                        finish();
                                    }
                                }

                            },"Message", Response.getMsg() + "\n" + "Do you Want to Continue Wallet Request ?"  , "No", "Yes");
                        }
                        else {
                            Toast.makeText(Business_WalletRequestActivity.this, Response.getResponse() +": " + Response.getMsg(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Business_WalletRequestActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        }
        else {
            Toast.makeText(Business_WalletRequestActivity.this,"No file Selected", Toast.LENGTH_SHORT).show();
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
}