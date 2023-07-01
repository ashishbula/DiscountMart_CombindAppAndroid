package in.discountmart.business.activity;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import in.base.network.NetworkClient;
import in.base.util.ImageUtil;
import in.discountmart.R;
import in.discountmart.business.adapter.AccountTypeAdapter;
import in.discountmart.business.adapter.BankListAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.business_constants.AppConstants;
import in.discountmart.business.call_api.UploadKYCServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.UpdateBankProofRequest;
import in.discountmart.business.model_business.responsemodel.AccountTypeListResponse;
import in.discountmart.business.model_business.responsemodel.BankListResponse;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.model_business.responsemodel.GetBankProofResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankProofActivity extends AppCompatActivity {


    Context context;
    ImageView imgViewDoc;
    EditText edTxtAcNo;
    EditText edTxtBranch;
    EditText edTxtIFEC;

    TextView textViewDocPath;
    TextView textViewUploadDoc;
    TextView textViewVerifyStatus;
    TextView textViewRejectReason;
    TextView textViewRejectDate;
    TextView textViewRejectRemark;

    Spinner spinBank;
    Spinner spinAcType;
    Button btnSubmit;
    LinearLayout layoutSubmit;

    String strBankId="";
    String strBankName="";
    String strAcno="";
    String strBranch="";
    String strIfsc="";
    String strActype="";

    String strimgDoc="";
    String myString="";
    String strApiKey="";


    //For image upload
    private Dialog uploadOptionDialog;
    private Uri fileUri;
    private static final int REQUEST_CODE = 0x11;
    private static int RESULT_LOAD_IMG = 1;
    private static int RESULT_CLICK_IMG = 2;
    private static final int PERMISSION_REQUEST_CODE = 200;
    Bitmap bitmapResizedImage;
    String imgPath;
    private String mAttachedFilePath;


    ArrayList<BankListResponse.BankList> bankListArrayList;
    AccountTypeListResponse.AccountList accountList[];
    ArrayList<AccountTypeListResponse.AccountList> accountListArrayList;
    BankListResponse.BankList bankLists[];
    BankListAdapter bankAccountAdapter;
    AccountTypeAdapter accountTypeAdapter;

    ProgressDialog pDialog;
    View view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_activity_bank_proof);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        view1= findViewById(android.R.id.content);
        context=BankProofActivity.this;
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            //for Action Bar
            // Enabling Up / Back navigation
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Bank Detail");

            edTxtAcNo=(EditText)findViewById(R.id.bank_proof_activity_edTxt_acno);
            edTxtBranch=(EditText)findViewById(R.id.bank_proof_activity_edTxt_branch);
            edTxtIFEC=(EditText)findViewById(R.id.bank_proof_activity_edTxt_ifsc);
            textViewVerifyStatus=(TextView)findViewById(R.id.bank_proof_activity_txtView_verify_status);
            textViewRejectRemark=(TextView)findViewById(R.id.bank_proof_activity_txtView_reject_remark);
            textViewRejectReason=(TextView)findViewById(R.id.bank_proof_activity_txtView_reject_reson);
            textViewRejectDate=(TextView)findViewById(R.id.bank_proof_activity_txtView_reject_date);
            textViewDocPath=(TextView)findViewById(R.id.bank_proof_activity_txtView_doc_pah);
            textViewUploadDoc=(TextView)findViewById(R.id.bank_proof_activity_txtView_uploadDoc);


            imgViewDoc=(ImageView)findViewById(R.id.bank_proof_activity_imgView_doc);
            spinBank=(Spinner)findViewById(R.id.bank_proof_activity_spiner_bank);
            spinAcType=(Spinner)findViewById(R.id.bank_proof_activity_spiner_actype);
            btnSubmit=(Button)findViewById(R.id.bank_proof_activity_btn_submit);
            layoutSubmit=(LinearLayout)findViewById(R.id.bank_proof_activity_layout_submitt);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(this);

            /*Check permission for external storage and camera*/
            if (checkPermission()) {

                //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();

            } else {
                requestPermission();
                //Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
            }

            /*Click Listner for Show Diload to pic Image from Gellary , cemra*/
            textViewUploadDoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission()) {

                        //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(BankProofActivity.this);
                        LayoutInflater inflater = BankProofActivity.this.getLayoutInflater();

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

            //call Account Type Spinner

            if(!ConnectivityUtils.isNetworkEnabled(BankProofActivity.this)){
                Snackbar.make(view1, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();
            }
            else {
                getAccountTypeList();
            }

            /*Select Account in sppiner account type */
            spinAcType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    AccountTypeListResponse.AccountList acType=(AccountTypeListResponse.AccountList)parent.getItemAtPosition(position);
                    strActype=acType.getAccountype();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            //spinner state selection
            spinBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent,
                                           View arg1, int position, long arg3) {
                    //String item = parent.getItemAtPosition(position).toString();

                    BankListResponse.BankList bankList=( BankListResponse.BankList)parent.getItemAtPosition(position);
                    strBankId = bankList.getBankcode();
                    strBankName=bankList.getBankname();


                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(edTxtAcNo.getText().toString().equals("")){
                        edTxtAcNo.setError("Enter Your Account No.");
                        //Toast.makeText(BankProofActivity.this,"Plz Enter Your Account No",Toast.LENGTH_SHORT).show();
                        edTxtAcNo.requestFocus();
                    }
                    else if(edTxtBranch.getText().toString().equals("")){
                        edTxtBranch.setError("Enter Your Bank Branch");
                        //Toast.makeText(BankProofActivity.this,"Plz Enter Your Bank Branch",Toast.LENGTH_SHORT).show();
                        edTxtBranch.requestFocus();
                    }

                    else if(edTxtIFEC.getText().toString().equals("")){
                        edTxtIFEC.setError("Enter Bank IFEC Code");
                        //Toast.makeText(BankProofActivity.this,"Plz Enter Bank IFEC Code",Toast.LENGTH_SHORT).show();
                        edTxtIFEC.requestFocus();
                    }
                    else if(strBankId.equals("0")){
                        Toast.makeText(BankProofActivity.this,"Plz Select Bank",Toast.LENGTH_SHORT).show();
                    }
                    else if(strActype.equals("0")){
                        Toast.makeText(BankProofActivity.this,"Plz Select Account Type",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        strAcno=edTxtAcNo.getText().toString();
                        strBranch=edTxtBranch.getText().toString();
                        strIfsc=edTxtIFEC.getText().toString();
                        strimgDoc=textViewDocPath.getText().toString();

                        if(strimgDoc.equals("No file Attach")){

                            Toast.makeText(BankProofActivity.this,"Please attach require document",Toast.LENGTH_SHORT).show();
                            //Call Api
                            /*if(!ConnectivityUtils.isNetworkEnabled(BankProofActivity.this)){
                                Toast.makeText(BankProofActivity.this,getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                            }
                            else {
                                updateBankProoftDetail();
                            }*/
                        }
                        else {
                            //Call Api
                            if(!ConnectivityUtils.isNetworkEnabled(BankProofActivity.this)){
                                Toast.makeText(BankProofActivity.this,getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                            }
                            else {
                                uploadAttachment();
                            }
                        }
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Method of Spinner Set Index*/
    private int getIndexSpinBank(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){

            BankListResponse.BankList model = (BankListResponse.BankList)spinner.getItemAtPosition(i);

            if (model.getBankcode().equalsIgnoreCase(myString)){
                index=i;
            }
        }
        return index;
    }

    /*Method of Spinner Set Index*/
    private int getIndexSpinAccountType(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){

            AccountTypeListResponse.AccountList model = ( AccountTypeListResponse.AccountList)spinner.getItemAtPosition(i);

            if (model.getAccountype().equalsIgnoreCase(myString)){
                index=i;
            }
        }
        return index;
    }




    /*AccountType List Api Request and Response*/
    private void getAccountTypeList(){

        //showProgressDialog();
        pDialog=new ProgressDialog(BankProofActivity.this);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest baseRequest= new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_ACCOUNT_TYPE);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(BankProofActivity.this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(BankProofActivity.this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(BankProofActivity.this));

        Call<AccountTypeListResponse> accountTypeListCall=
                NetworkClient.getInstance(BankProofActivity.this).create(UploadKYCServices.class).fetchAccountTypeList(baseRequest,strApiKey);

        accountTypeListCall.enqueue(new Callback<AccountTypeListResponse>() {
            @Override
            public void onResponse(Call<AccountTypeListResponse> call, Response<AccountTypeListResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    AccountTypeListResponse Response = new AccountTypeListResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {

                        accountList=Response.getAccounttype();
                        if(accountList.length > 0){
                            accountListArrayList=new ArrayList<AccountTypeListResponse.AccountList>(Arrays.asList(accountList));
                            accountTypeAdapter=new AccountTypeAdapter(context,accountListArrayList);
                            spinAcType.setAdapter(accountTypeAdapter);

                            //call Api
                            if(!ConnectivityUtils.isNetworkEnabled(BankProofActivity.this))
                                Toast.makeText(BankProofActivity.this,getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                            else
                                getBankList();


                        }
                        else {
                            String toast= Response.getResponse()+ "Record is Null";
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
                        String toast= Response.getResponse()+ ": Somthing Wrong" ;
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
            public void onFailure(Call<AccountTypeListResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
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

    /*Bank list Api Request and Response*/
    private void getBankList(){

        pDialog=new ProgressDialog(BankProofActivity.this);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest baseRequest= new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_BANKLIST);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(BankProofActivity.this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(BankProofActivity.this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(BankProofActivity.this));

        Call<BankListResponse> bankListCall=
                NetworkClient.getInstance(BankProofActivity.this).create(UploadKYCServices.class).fetchBanklist(baseRequest,strApiKey);

        bankListCall.enqueue(new Callback<BankListResponse>() {
            @Override
            public void onResponse(Call<BankListResponse> call, Response<BankListResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    BankListResponse Response = new BankListResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {

                        bankLists=Response.getBankers();
                        if(bankLists.length > 0){
                            bankListArrayList=new ArrayList<BankListResponse.BankList>(Arrays.asList(bankLists));
                            bankAccountAdapter=new BankListAdapter(context,bankListArrayList);
                            spinBank.setAdapter(bankAccountAdapter);

                            //call Api
                            if(!ConnectivityUtils.isNetworkEnabled(BankProofActivity.this))
                                Toast.makeText(BankProofActivity.this,getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                            else
                                getBankProofDetail();


                        }
                        else {
                            String toast= Response.getResponse()+ "Record is Null";
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
                        String toast= Response.getResponse()+ ": Somthing Wrong" ;
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
            public void onFailure(Call<BankListResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
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

    /*Get Bank Proof Detail Api Requestand Response*/
    /*Get Bank Proof Detail Api Requestand Response*/
    private void getBankProofDetail(){
        pDialog=new ProgressDialog(BankProofActivity.this);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_GET_BANK_PROOF_DETAIL);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(BankProofActivity.this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(BankProofActivity.this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(BankProofActivity.this));

        Call<GetBankProofResponse> callBankProofResponse=
                NetworkClient.getInstance(BankProofActivity.this).create(UploadKYCServices.class).fetchBankProofDetail(baseRequest,strApiKey);

        callBankProofResponse.enqueue(new Callback<GetBankProofResponse>() {
            @Override
            public void onResponse(Call<GetBankProofResponse> call, Response<GetBankProofResponse> response) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    GetBankProofResponse Response =new GetBankProofResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {
                        if (Response.getBankproof().equals("") || Response.getBankproof() == null)
                        {
                            imgViewDoc.setImageResource(R.drawable.ic_file_upload);
                        }

                        else{

                            Picasso.with(BankProofActivity.this).load(Response.getBankproof()).into(imgViewDoc);
                        }
                        spinAcType.setSelection(getIndexSpinAccountType(spinAcType,Response.getAccounttype()));
                        edTxtAcNo.setText(Response.getAcno());
                        spinBank.setSelection(getIndexSpinBank(spinBank,Response.getBankid()));
                        edTxtBranch.setText(Response.getBranchname());
                        edTxtIFEC.setText(Response.getIfscode());


                        if(Response.getIsbankverified().equals("Y")){

                            textViewVerifyStatus.setText("Verification Status:- "+Response.getBankverf());

                            if(Response.getBankproofdate().equals(""))
                                textViewRejectDate.setText("");

                            else
                                textViewRejectDate.setText("Rejected Date:- "+Response.getBankproofdate());

                            /*if(Response.getRejectreason().equals(""))
                                textViewRejectReason.setText("");
                            else
                                textViewRejectReason.setText("Rejected Reason:- "+Response.getRejectreason());*/

                            //textViewRejectRemark.setText("Rejected Remark:- "+Response.getRejectremark());

                            textViewVerifyStatus.setTextColor(getResources().getColor(R.color.Green));
                            textViewRejectDate.setTextColor(getResources().getColor(R.color.Green));
                            // textViewRejectReason.setTextColor(getResources().getColor(R.color.red));
                            //textViewRejectRemark.setTextColor(getResources().getColor(R.color.red));

                            textViewDocPath.setEnabled(false);
                            textViewUploadDoc.setEnabled(false);


                            edTxtAcNo.setEnabled(false);

                            edTxtBranch.setEnabled(false);
                            edTxtIFEC.setEnabled(false);
                            spinBank.setEnabled(false);
                            spinAcType.setEnabled(false);
                            layoutSubmit.setVisibility(View.GONE);
                        }

                        else if(Response.getIsbankverified().equals("N")) {
                            textViewVerifyStatus.setText("Verification Status:- "+Response.getBankverf());

                            if(Response.getBankproofdate().equals(""))
                                textViewRejectDate.setText("");
                            else
                                textViewRejectDate.setText("Rejected Date:- "+Response.getBankproofdate());

                            if(Response.getRejectreason().equals(""))
                                textViewRejectReason.setText("");
                            else
                                textViewRejectReason.setText("Rejected Reason:- "+Response.getRejectreason());
                            //textViewRejectRemark.setText("Rejected Remark:- "+Response.getRejectremark());

                            textViewVerifyStatus.setTextColor(getResources().getColor(R.color.blue));
                            textViewRejectDate.setTextColor(getResources().getColor(R.color.blue));
                            textViewRejectReason.setTextColor(getResources().getColor(R.color.blue));
                            //textViewRejectRemark.setTextColor(getResources().getColor(R.color.green9));\

                            strBankId=Response.getBankid();
                            strActype=Response.getAccounttype();

                            if(Response.getBankproof().equals("")){

                                textViewDocPath.setEnabled(true);
                                textViewUploadDoc.setEnabled(true);
                            }
                            else {

                                textViewDocPath.setEnabled(false);
                                textViewUploadDoc.setEnabled(false);
                            }

                            if(Response.getAcno().equals("")){
                                edTxtAcNo.setEnabled(true);
                            }
                            else {
                                edTxtAcNo.setEnabled(false);
                                edTxtAcNo.setText(Response.getAcno());
                            }

                            if(Response.getBranchname().equals("")){
                                edTxtBranch.setEnabled(true);
                            }
                            else {
                                edTxtBranch.setEnabled(false);
                                edTxtBranch.setText(Response.getBranchname());
                            }
                            if (Response.getIfscode().equals("")){
                                edTxtIFEC.setEnabled(true);
                            }
                            else {
                                edTxtIFEC.setEnabled(false);
                                edTxtIFEC.setText(Response.getIfscode());
                            }
                            if(Response.getBankid().equals("")){
                                spinBank.setEnabled(true);
                            }
                            else {
                                spinBank.setSelection(getIndexSpinBank(spinBank,Response.getBankid()));
                                if(Response.getBankid().equals("0")){
                                    spinBank.setEnabled(true);
                                }
                                else
                                    spinBank.setEnabled(false);

                            }
                            if(Response.getAccounttype().equals("")){
                                spinAcType.setEnabled(true);
                            }
                            else {
                                spinAcType.setEnabled(false);
                                spinAcType.setSelection(getIndexSpinAccountType(spinAcType,Response.getAccounttype()));
                                if(Response.getAccounttype().equals("0"))
                                    spinAcType.setEnabled(true);
                                else
                                    spinAcType.setEnabled(true);
                            }

                            //layoutSubmit.setVisibility(View.GONE);
                            if(Response.getAccounttype().equals("") || Response.getBankid().equals("") ||
                                    Response.getIfscode().equals("") || Response.getBranchname().equals("") ||
                                    Response.getAcno().equals("") || Response.getBankproof().equals("")
                                    || Response.getIsbankverified().equals(""))
                            {
                                layoutSubmit.setVisibility(View.VISIBLE);

                            }
                           else {
                                layoutSubmit.setVisibility(View.GONE);
                            }




                        }
                        else if(Response.getIsbankverified().equals("R")){
                            textViewVerifyStatus.setText("Verification Status:- "+Response.getBankverf());
                            if(Response.getBankproofdate().equals(""))
                                textViewRejectDate.setText("");
                            else
                                textViewRejectDate.setText("Rejected Date:- "+Response.getBankproofdate());

                            /*if(Response.getRejectreason().equals(""))
                                textViewRejectReason.setText("");
                            else
                                textViewRejectReason.setText("Rejected Reason:- "+Response.getRejectreason());*/
                            //textViewRejectRemark.setText("Rejected Remark:- "+Response.getRejectremark());

                            textViewVerifyStatus.setTextColor(getResources().getColor(R.color.red));
                            textViewRejectDate.setTextColor(getResources().getColor(R.color.red));

                            textViewDocPath.setEnabled(true);
                            textViewUploadDoc.setEnabled(true);
                            strBankId=Response.getBankid();
                            strActype=Response.getAccounttype();


                            edTxtAcNo.setEnabled(true);

                            edTxtBranch.setEnabled(true);
                            edTxtIFEC.setEnabled(true);
                            spinBank.setEnabled(true);
                            spinAcType.setEnabled(true);
                            layoutSubmit.setVisibility(View.VISIBLE);
                        }

                    }
                    else {
                        String toast= Response.getResponse()+ ":" + Response.getMsg();

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
            public void onFailure(Call<GetBankProofResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                String toast= t.getMessage();
                Snackbar.make(view1, toast, Snackbar.LENGTH_LONG)
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

    /*Update Bank Proof Detail api request and response*/
    private void updateBankProoftDetail(){
        pDialog=new ProgressDialog(BankProofActivity.this);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        UpdateBankProofRequest Request = new UpdateBankProofRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_UPDATE_BANK_PROOF_DETAIL);
        Request.setPasswd(SharedPrefrence_Business.getPassword(BankProofActivity.this));
        Request.setUserid(SharedPrefrence_Business.getUserID(BankProofActivity.this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(BankProofActivity.this));
        Request.setAccountno(strAcno);
        Request.setAccounttype(strActype);
        Request.setBankcode(strBankId);
        Request.setBranchname(strBranch);
        Request.setIfsccode(strIfsc);

        Call<BaseResponse> callUpdateBankDetail=
                NetworkClient.getInstance(BankProofActivity.this).create(UploadKYCServices.class).fetchUpdateBankProofDetail(Request,strApiKey);

        callUpdateBankDetail.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    BaseResponse Response =new BaseResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {

                        String toast= Response.getResponse()+ ": Upload Successfully" ;
                        Toast.makeText(BankProofActivity.this, toast, Toast.LENGTH_SHORT).show();

                        finish();
                    }
                    else {
                        String toast= Response.getResponse()+ ": Somthing Wrong" + Response.getMsg();

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
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                String toast= t.getMessage();
                Snackbar.make(view1, toast, Snackbar.LENGTH_LONG)
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

    /*Update Bank Proof Detail api request and response*/
    private void updateBankProoftDetailWithImage(String strfile){
        pDialog=new ProgressDialog(BankProofActivity.this);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        UpdateBankProofRequest Request = new UpdateBankProofRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_UPDATE_BANK_PROOF_DETAIL);
        Request.setPasswd(SharedPrefrence_Business.getPassword(BankProofActivity.this));
        Request.setUserid(SharedPrefrence_Business.getUserID(BankProofActivity.this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(BankProofActivity.this));
        Request.setAccountno(strAcno);
        Request.setAccounttype(strActype);
        Request.setBankcode(strBankId);
        Request.setBranchname(strBranch);
        Request.setIfsccode(strIfsc);


        final File file1=new File(ImageUtil.compressImage(strfile));
        String str1="";
        str1=file1.getPath();

        int compressionRatio=25;

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


        if(!str1.equals("")){

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file1);
            body = MultipartBody.Part.createFormData("file:", file1.getName(), reqFile);
            // RequestBody request = RequestBody.create(MediaType.parse("text/plain"), new Gson().toJson(Request));
            RequestBody request = RequestBody.create(MultipartBody.FORM, new Gson().toJson(Request));


            Call<BaseResponse> callUpdateDetailMultipart=
                    NetworkClient.getInstance(BankProofActivity.this).create(UploadKYCServices.class).fetchUpdateBankWithImageMultipart(body,request,strApiKey);

            callUpdateDetailMultipart.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    try {

                        BaseResponse Response =new BaseResponse();
                        Response=response.body();

                        if (Response.getResponse().equals("OK")) {

                            String toast= Response.getResponse()+ ": Upload Sucessfully";
                            Toast.makeText(BankProofActivity.this, toast, Toast.LENGTH_SHORT).show();
                            //getBankProofDetail();

                            finish();
                        }
                        else {
                            String toast= Response.getResponse()+ ":somthing wrong" + Response.getMsg();

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
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    //String toast= response1.getResponse();
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
        else {
            Toast.makeText(BankProofActivity.this,"No file Selected",Toast.LENGTH_SHORT).show();
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


                    textViewDocPath.setText(AppConstants.Uri);
                    AppConstants.ImageUri= AppConstants.Uri;
                    imgViewDoc.setImageBitmap(bitmap);

                    //uploadAttachment();


                } else if (resultCode ==this.RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(BankProofActivity.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();

                } else {
                    // failed to capture image
                    Toast.makeText(BankProofActivity.this, "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
                }


            }
            // When an Image is picked from gallery

            else if(requestCode == 1){

                AppConstants.Uri="";
                if (resultCode == this.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = BankProofActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgPath = cursor.getString(columnIndex);
                    cursor.close();

                    // Set the Image in ImageView
                    final Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
                    //bitmap.compress(Bitmap.CompressFormat.JPEG,2, new FileOutputStream(imgPath));

                    textViewDocPath.setText(imgPath);
                    imgViewDoc.setImageBitmap(bitmap);

                    AppConstants.imgpath = imgPath;
                    AppConstants.ImageUri=imgPath;

                    //upload image
                    // uploadAttachment();

                } else if (resultCode == this.RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(BankProofActivity.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();
                }

            }

        }catch (Exception e) {
            Toast.makeText(BankProofActivity.this, e.toString(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(BankProofActivity.this, "NO FILE SELECTED TO UPLOAD", Toast.LENGTH_LONG).show();
        }
    }
    private void sendImageUploadRequest(final String filePath) {
        Handler mHandler = new Handler(BankProofActivity.this.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                UploadImageHandler1 uploadImageHandler = new UploadImageHandler1(filePath);
//
//                String stringParameter=sendUpdateBankProofDetailRequest();
//                uploadImageHandler.execute(ApiConstants.Baseurl, stringParameter);
                //uploadImageHandler.execute(AppConstants.uploadProfileImage, "");
                updateBankProoftDetailWithImage(filePath);

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


    /* private MultipartEntity getMultipartEntity(String myJsonRequest) {
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
    //get permission to use gallery
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
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean externalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (externalStorage && cameraAccepted)
                        Snackbar.make(view1, "Permission Granted, Now you can write data storage and camera.", Snackbar.LENGTH_LONG).show();
                    else {

                        Snackbar.make(view1, "Permission Denied, You cannot write data in storage and camera.", Snackbar.LENGTH_LONG).show();

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
        new AlertDialog.Builder(BankProofActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onResume(){
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.business_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent intent=new Intent(BankProofActivity.this, BusinessDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
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

    public String getMyData() {
        return myString;
    }
}
