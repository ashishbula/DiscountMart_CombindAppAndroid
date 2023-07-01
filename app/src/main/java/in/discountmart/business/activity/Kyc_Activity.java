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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.base.network.NetworkClient;
import in.base.util.ImageUtil;
import in.discountmart.R;
import in.discountmart.business.adapter.AccountTypeAdapter;
import in.discountmart.business.adapter.BankListAdapter;
import in.discountmart.business.adapter.IdProofAdapter;
import in.discountmart.business.adapter.PincodeAreaListAdapter;
import in.discountmart.business.adapter.StateListAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.business_constants.AppConstants;
import in.discountmart.business.call_api.ProfileServices;
import in.discountmart.business.call_api.UploadKYCServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.KycImageRequest;
import in.discountmart.business.model_business.requestmodel.PincodeDetailRequest;
import in.discountmart.business.model_business.requestmodel.SaveKvcRequest;
import in.discountmart.business.model_business.responsemodel.AccountTypeListResponse;
import in.discountmart.business.model_business.responsemodel.BankListResponse;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.model_business.responsemodel.IdProofListResponse;
import in.discountmart.business.model_business.responsemodel.KycDetailResponse;
import in.discountmart.business.model_business.responsemodel.KycImageResponse;
import in.discountmart.business.model_business.responsemodel.PincodeDetailRespose;
import in.discountmart.business.model_business.responsemodel.StateListResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Kyc_Activity extends AppCompatActivity {

    Context context;
    ImageView imgFrontDoc;
    ImageView imgBackDoc;
    ImageView imgBankDoc;
    ImageView imgPanDoc;
    EditText edTxtAddress;
    EditText edTxtDistrict;
    EditText edTxtCity;
    EditText edTxtState;
    EditText edTxtIdProofNo;
    EditText edTxtPincode;

    EditText edTxtAcNo;
    EditText edTxtBranch;
    EditText edTxtIFEC;
    EditText edTxtPanNo;
    EditText edTxtOtherBank;
    EditText edTxtOtheraAera;

    Spinner spinArea;
    Spinner spinAddressProof;
    Spinner spinBank;
    Spinner spinState;
    Spinner spinAcType;
    Button btnSubmit;
    TextView txtFrontDocName;
    TextView txtBackDocName;
    TextView txtBankDocName;
    TextView txtPanDocName;
    TextView textViewDocPath;
    TextView textViewDocPath2;
    TextView textViewVerify;
    TextView txtViewRejectRemrk;
    TextView txtViewRejectDate;
    TextView txtViewRejectReson;
    LinearLayout layoutSubmit;
    LinearLayout layoutOtherAera;
    LinearLayout layoutOtherBank;

    String strStateId="";
    String strStateName="";
    String strAddress="";
    String strDistrict="";
    String strDistrictId="0";
    String strCity="";
    String strCityId="0";
    String strPincode="";
    String strAreaId="";
    String strAreaName="";
    String strOtherArea="";
    String strOtherBank="";
    String strIdProofNo="";
    String strIdProof="";
    String strIdProofId="";
    String strimgDoc="";
    String strFilePath="";
    String strFilePath2="";
    String strFrontIdPath="";
    String strBackIdPath="";
    String strBankIdPath="";
     String strPanIdPath="";

    String strBankId="";
    String strBankName="";
    String strAcno="";
    String strBranch="";
    String strIfsc="";
    String strActype="";
    String strPanNo="";


    String myString="";
    // Boolean variable
    boolean frontId;
    boolean backId;
    boolean bankId;
    boolean panId;
    boolean addkyc;
    boolean bankkyc;
    boolean pankyc;

    //Array List of Object class and object class
    ArrayList<StateListResponse.StateList> stateListArrayList;
    StateListResponse.StateList stateList[];
    StateListAdapter stateListAdapter;
    ArrayList<PincodeDetailRespose.PincodeDetail> pincodeAreaArrayList;
    PincodeDetailRespose.PincodeDetail pincodeDetails[];
    PincodeAreaListAdapter pincodeAeraAdapter;

    ArrayList<BankListResponse.BankList> bankListArrayList;
    AccountTypeListResponse.AccountList accountList[];
    ArrayList<AccountTypeListResponse.AccountList> accountListArrayList;
    BankListResponse.BankList bankLists[];
    BankListAdapter bankAccountAdapter;
    AccountTypeAdapter accountTypeAdapter;

    ArrayList<IdProofListResponse.IdProofList> idProofListArrayList;
    IdProofListResponse.IdProofList idProofList[];
    IdProofAdapter idProofAdapter;

    KycDetailResponse.AddressDetail addressDetail;
    KycDetailResponse.BankDetail bankDetail;
    KycDetailResponse.PanDetail panDetail;


    ProgressDialog pDialog;
    View view;
    Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
    //For image upload
    private Dialog uploadOptionDialog;
    private static int RESULT_LOAD_IMG = 1;
    private static int RESULT_CLICK_IMG = 2;
    private Uri fileUri;
    private Uri fileUri2;
    private static final int REQUEST_CODE2 = 11;
    private static final int REQUEST_CODE = 0x11;
    private static final int PERMISSION_REQUEST_CODE = 200;
    Bitmap bitmapResizedImage;
    String imgPath;
    String imgPath2;
    private String mAttachedFilePath;
    private String mAttachedFilePath2;
    private String strApiKey="";

    int clickbtn=0;
    //int clickbtn2=0;
    private static String camImag="";
    private static String gallreyImag="";
    // int clickSubmitbtn=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_activity_kyc);

        try{
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            view=findViewById(android.R.id.content);

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("KYC Upload");

            edTxtAddress=(EditText)findViewById(R.id.kyc_activity_edTxt_address);
            edTxtDistrict=(EditText)findViewById(R.id.kyc_activity_edTxt_district);
            edTxtCity=(EditText)findViewById(R.id.kyc_activity_edTxt_city);
            edTxtPincode=(EditText)findViewById(R.id.kyc_activity_edTxt_pincode);
            edTxtIdProofNo=(EditText)findViewById(R.id.kyc_activity_edTxt_aadhar1);
            //edTxtState=(EditText)findViewById(R.id.kyc_activity_edTxt_state);
            edTxtAcNo=(EditText)findViewById(R.id.kyc_activity_edTxt_acno);
            edTxtBranch=(EditText)findViewById(R.id.kyc_activity_edTxt_branch);
            edTxtIFEC=(EditText)findViewById(R.id.kyc_activity_edTxt_ifsc);
            edTxtOtheraAera=(EditText)findViewById(R.id.kyc_activity_edTxt_other_area);
            edTxtOtherBank=(EditText)findViewById(R.id.kyc_activity_edTxt_other_bank);
            edTxtPanNo=(EditText)findViewById(R.id.kyc_activity_edTxt_panno);
            txtBackDocName=(TextView)findViewById(R.id.kyc_activity_txt_back_img_name);
            txtFrontDocName=(TextView)findViewById(R.id.kyc_activity_txt_front_img_name);
            txtBankDocName=(TextView)findViewById(R.id.kyc_activity_txt_bank_img_pah);
            txtPanDocName=(TextView)findViewById(R.id.kyc_activity_txt_pan_img_pah);

            textViewVerify=(TextView)findViewById(R.id.kyc_activity_txt_verify_status);
            txtViewRejectRemrk=(TextView)findViewById(R.id.kyc_activity_txt_rejected_mark);
            txtViewRejectDate=(TextView)findViewById(R.id.kyc_activity_txt_rejected_date);
            txtViewRejectReson=(TextView)findViewById(R.id.kyc_activity_txt_rejected_reason);

            imgFrontDoc=(ImageView)findViewById(R.id.kyc_activity_img_front_img);
            imgBackDoc=(ImageView)findViewById(R.id.kyc_activity_img_back_img);
            imgPanDoc=(ImageView)findViewById(R.id.kyc_activity_img_pan);
            imgBankDoc=(ImageView)findViewById(R.id.kyc_activity_img_bank_doc);
            //spinArea=(Spinner)findViewById(R.id.kyc_activity_spiner_area);
            spinAcType=(Spinner)findViewById(R.id.kyc_activity_spiner_account);
            spinBank=(Spinner)findViewById(R.id.kyc_activity_spiner_bank);
            spinState=(Spinner)findViewById(R.id.kyc_activity_spiner_state);
            spinAddressProof=(Spinner)findViewById(R.id.kyc_activity_spiner_proof);
            btnSubmit=(Button)findViewById(R.id.kyc_activity_btn_submit);
            layoutSubmit=(LinearLayout)findViewById(R.id.kyc_activity_layout_submit);
            layoutOtherAera=(LinearLayout)findViewById(R.id.kyc_activity_layout_other_area);
            layoutOtherBank=(LinearLayout)findViewById(R.id.kyc_activity_layout_other_bank);

//            AppConstants.Uri2="";
//            AppConstants.Uri="";
//            AppConstants.imgpath="";
//            AppConstants.imgpath2="";


            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(this);

            /*Check permission for external storage and camera*/
            if (checkPermission()) {

                //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();

            } else {
                requestPermission();
                //Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
            }

            /* Image on Front Address Id Proof on click*/

            imgFrontDoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission()) {
                        frontId=true;
                        backId=false;
                        bankId=false;
                        panId=false;
                        //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Kyc_Activity.this);
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

                    }
                    else {
                        requestPermission();
                        //Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
                    }

                }
            });

            /* Image on Bank Address Id Proof on click*/
            imgBackDoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission()) {
                        backId=true;
                        frontId=false;
                        bankId=false;
                        panId=false;
                        //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Kyc_Activity.this);
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

                    }
                    else {
                        requestPermission();
                        //Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
                    }

                }
            });

            /* Image on Bank Id Proof on click*/

            imgBankDoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission()) {
                        bankId=true;
                        frontId=false;
                        backId=false;
                        panId=false;
                        //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Kyc_Activity.this);
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

                    }
                    else {
                        requestPermission();
                        //Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
                    }

                }
            });

            /* Image on PAN Id Proof on click*/
            imgPanDoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission()) {
                        panId=true;
                        frontId=false;
                        backId=false;
                        bankId=false;
                        //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Kyc_Activity.this);
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

                    }
                    else {
                        requestPermission();
                        //Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
                    }

                }
            });

            /*Edit Text Pincode On Text Change Listner*/
            /*edTxtPincode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    strPincode=edTxtPincode.getText().toString();

               *//* if(strPincode.length() != 6){
                   Toast.makeText(context,"Pincode not complete",Toast.LENGTH_SHORT).show();
                }*//*
                    if(strPincode.length() ==6){
                        //Call Pincode Aera List Api
                        if(!ConnectivityUtils.isNetworkEnabled(Kyc_Activity.this)){
                            Toast.makeText(Kyc_Activity.this,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        }
                        else {

                            //getPincodeAreaList();
                        }
                    }

                }
            });*/

            //spinner Area selection
            /*spinArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent,
                                           View arg1, int position, long arg3) {
                    //String item = parent.getItemAtPosition(position).toString();

                    PincodeDetailRespose.PincodeDetail stateList1=(PincodeDetailRespose.PincodeDetail)parent.getItemAtPosition(position);
                    strAreaId = stateList1.getAreacode();
                    strAreaName=stateList1.getAreaname();

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });*/

            //Call Id Proof List APi
            if(!ConnectivityUtils.isNetworkEnabled(Kyc_Activity.this)){
                Toast.makeText(Kyc_Activity.this,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
            else {
                getStateList();
            }

            //Spinner Id Proof
            spinState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    StateListResponse.StateList stateList1=
                            (StateListResponse.StateList)parent.getItemAtPosition(position);

                    strStateId=stateList1.getStatecode();
                    strStateName=stateList1.getStatename();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //Spinner Id Proof
            spinAddressProof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    IdProofListResponse.IdProofList idProofList1=
                            (IdProofListResponse.IdProofList)parent.getItemAtPosition(position);

                    strIdProof=idProofList1.getIdtype();
                    strIdProofId=idProofList1.getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

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

            /*Button click listener for update detail */
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // call method
                    validation();

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume(){

        //Call Id Proof List APi
        /*if(!ConnectivityUtils.isNetworkEnabled(Kyc_Activity.this)){
            Toast.makeText(Kyc_Activity.this,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
        else {
            getIdProoftList();
        }*/
        super.onResume();
    }

    private void validation(){
        try {
            if(edTxtAddress.getText().toString().equals("")){
                Toast.makeText(Kyc_Activity.this,"Plz Enter Address", Toast.LENGTH_SHORT).show();
                edTxtAddress.requestFocus();
            }
            else if(edTxtPincode.getText().toString().equals("")){
                Toast.makeText(Kyc_Activity.this,"Plz Enter Your city Pincode", Toast.LENGTH_SHORT).show();
                edTxtPincode.requestFocus();
            }
            else if(strStateId.equals("0")){
                Toast.makeText(Kyc_Activity.this,"Plz Select State", Toast.LENGTH_SHORT).show();
            }
            else if(edTxtDistrict.getText().toString().equals("")){
                Toast.makeText(Kyc_Activity.this,"Plz Enter Your District", Toast.LENGTH_SHORT).show();
                edTxtDistrict.requestFocus();
            }

            else if(edTxtCity.getText().toString().equals("")){
                Toast.makeText(Kyc_Activity.this,"Plz Enter Your City", Toast.LENGTH_SHORT).show();
                edTxtCity.requestFocus();
            }
           /* else if(strAreaId.equals("0")){
                Toast.makeText(Kyc_Activity.this,"Plz Select Area", Toast.LENGTH_SHORT).show();
            }*/

            else if(strIdProofId.equals("0")){
                Toast.makeText(Kyc_Activity.this,"Plz Select Proper Id Proof", Toast.LENGTH_SHORT).show();
            }

            else if(edTxtIdProofNo.getText().toString().equals("")){
                Toast.makeText(Kyc_Activity.this,"Plz Enter Your Selected Id Proof No.", Toast.LENGTH_SHORT).show();
                edTxtIdProofNo.requestFocus();
            }

            else if(txtFrontDocName.getText().toString().equals("") ||txtFrontDocName.getText().toString().equals("No File Attach")){
                Toast.makeText(Kyc_Activity.this,"Plz Upload Front image of Address ID Proof.", Toast.LENGTH_SHORT).show();
            }
            else if(txtBackDocName.getText().toString().equals("") ||txtBackDocName.getText().toString().equals("No File Attach")){
                Toast.makeText(Kyc_Activity.this,"Plz Upload Back image of Address ID Proof.", Toast.LENGTH_SHORT).show();
            }

            // Bank detail validation
            else if(strActype.equals("0")){
                Toast.makeText(Kyc_Activity.this,"Plz Select Account Type",Toast.LENGTH_SHORT).show();
            }
            else if(edTxtAcNo.getText().toString().equals("")){
                edTxtAcNo.setError("Enter Your Account No.");
                //Toast.makeText(BankProofActivity.this,"Plz Enter Your Account No",Toast.LENGTH_SHORT).show();
                edTxtAcNo.requestFocus();
            }
            else if(strBankId.equals("0")){
                Toast.makeText(Kyc_Activity.this,"Plz Select Bank",Toast.LENGTH_SHORT).show();
            }
            else if(edTxtBranch.getText().toString().equals("")){
                edTxtBranch.setError("Enter Your Bank Branch");
                //Toast.makeText(BankProofActivity.this,"Plz Enter Your Bank Branch",Toast.LENGTH_SHORT).show();
                edTxtBranch.requestFocus();
            }

            else if(edTxtIFEC.getText().toString().equals("")){
                edTxtIFEC.setError("Enter Bank IFSC Code");
                //Toast.makeText(BankProofActivity.this,"Plz Enter Bank IFEC Code",Toast.LENGTH_SHORT).show();
                edTxtIFEC.requestFocus();
            }
            else if(txtBankDocName.getText().toString().equals("") ||txtBankDocName.getText().toString().equals("No File Attach")){
                Toast.makeText(Kyc_Activity.this,"Plz Upload image of Bank Proof.", Toast.LENGTH_SHORT).show();
            }
            else  if(edTxtPanNo.getText().toString().equals("")){
                Toast.makeText(Kyc_Activity.this,"Plz Enter Your PAN Card No", Toast.LENGTH_SHORT).show();
                edTxtPanNo.requestFocus();
            }
            else if(txtPanDocName.getText().toString().equals("") ||txtPanDocName.getText().toString().equals("No file Attach")){
                Toast.makeText(Kyc_Activity.this,"Plz Upload image of PAN Proof.", Toast.LENGTH_SHORT).show();
            }
            else {

                strAddress=edTxtAddress.getText().toString();
                strDistrict=edTxtDistrict.getText().toString();
                strCity=edTxtCity.getText().toString();
                strPincode=edTxtPincode.getText().toString();
                strIdProofNo=edTxtIdProofNo.getText().toString();
                strPincode=edTxtPincode.getText().toString();
                //strFrontIdPath=txtFrontDocName.getText().toString();
                //strBackIdPath=txtBackDocName.getText().toString();
                //strBankIdPath=txtBankDocName.getText().toString();
                //strPanIdPath=txtPanDocName.getText().toString();
                strAcno=edTxtAcNo.getText().toString();
                strBranch=edTxtBranch.getText().toString();
                strIfsc=edTxtIFEC.getText().toString();
                strPanNo=edTxtPanNo.getText().toString();
                //strimgDoc=textViewDocPath.getText().toString();

                View view = Kyc_Activity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(),
                            0);
                }
                Matcher matcher = pattern.matcher(strPanNo);

                if(matcher.matches()){
                    if(!ConnectivityUtils.isNetworkEnabled(Kyc_Activity.this))
                        Toast.makeText(Kyc_Activity.this,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    else

                        saveKycDetail();
                }
                else {
                    Toast.makeText(Kyc_Activity.this,"PAN Card Number Not Match", Toast.LENGTH_SHORT).show();
                }



            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /*Method of Spinner Set Index*/
    private int getIndexAreaSpinner(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){

            PincodeDetailRespose.PincodeDetail model = ( PincodeDetailRespose.PincodeDetail)spinner.getItemAtPosition(i);

            if (model.getAreacode().equalsIgnoreCase(myString)){
                index=i;
            }
        }
        return index;
    }

    /*Method of Spinner Set Index*/
    private int getIndexStateSpinner(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){

            StateListResponse.StateList model = ( StateListResponse.StateList)spinner.getItemAtPosition(i);

            if (model.getStatecode().equalsIgnoreCase(myString)){
                index=i;
            }
        }
        return index;
    }

    /*Method of ID Proof Spinner Set Index*/
    private int getIndexSpinIdProof(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){

            IdProofListResponse.IdProofList model = (IdProofListResponse.IdProofList)spinner.getItemAtPosition(i);

            if (model.getIdtype().equalsIgnoreCase(myString)){
                index=i;
            }
        }
        return index;
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

    /*StateList Api REquest and Response*/
    private void getStateList(){
        pDialog=new ProgressDialog(Kyc_Activity.this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_STATELIST);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(Kyc_Activity.this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(Kyc_Activity.this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(Kyc_Activity.this));
        baseRequest.setCountrycode("1");

        Call<StateListResponse> stateListResponseCall=
                NetworkClient.getInstance(Kyc_Activity.this).create(ProfileServices.class).fetchStateList(baseRequest,strApiKey);

        stateListResponseCall.enqueue(new Callback<StateListResponse>() {
            @Override
            public void onResponse(Call<StateListResponse> call, Response<StateListResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    StateListResponse Response =new StateListResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {

                        stateList=Response.getStates();
                        if(stateList.length > 0){
                            stateListArrayList=new ArrayList<StateListResponse.StateList>(Arrays.asList(stateList));
                            stateListAdapter=new StateListAdapter(context,stateListArrayList);
                            spinState.setAdapter(stateListAdapter);

                            getIdProoftList();
                        }
                        else {

                            String toast= Response.getResponse()+ ":" + Response.getMsg();
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
                        String toast= Response.getResponse()+ ":" + Response.getMsg();
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
            public void onFailure(Call<StateListResponse> call, Throwable t) {
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

    /*Id Proof List Api Request and Response*/
    private void getIdProoftList(){

        pDialog=new ProgressDialog(Kyc_Activity.this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest request=new BaseRequest();
        /*Set value in Entity class*/
        request.setReqtype(ApiConstants.REQUEST_IDPROOF);
        request.setPasswd(SharedPrefrence_Business.getPassword(Kyc_Activity.this));
        request.setUserid(SharedPrefrence_Business.getUserID(Kyc_Activity.this));
        request.setIslogin(SharedPrefrence_Business.getIsLogin(Kyc_Activity.this));

        Call<IdProofListResponse> idProofListResponseCall=
                NetworkClient.getInstance(Kyc_Activity.this).create(UploadKYCServices.class).fetchIdProofList(request,strApiKey);

        idProofListResponseCall.enqueue(new Callback<IdProofListResponse>() {
            @Override
            public void onResponse(Call<IdProofListResponse> call, Response<IdProofListResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    IdProofListResponse Response = new IdProofListResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {

                        idProofList=Response.getIdprooflist();
                        if(idProofList.length > 0){
                            idProofListArrayList=new ArrayList<IdProofListResponse.IdProofList>(Arrays.asList(idProofList));
                            idProofAdapter=new IdProofAdapter(Kyc_Activity.this,idProofListArrayList);
                            spinAddressProof.setAdapter(idProofAdapter);

                            //Call Account Type List APi
                            if(!ConnectivityUtils.isNetworkEnabled(Kyc_Activity.this)){
                                Toast.makeText(Kyc_Activity.this,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                getAccountTypeList();
                            }

                        }
                        else {
                            String toast= Response.getResponse()+ "Record is Null";
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
                        String toast= Response.getResponse()+ ":" + Response.getMsg();
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
            public void onFailure(Call<IdProofListResponse> call, Throwable t) {
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


    /*StateList Api REquest and Response*/
    private void getPincodeAreaList(){
        pDialog=new ProgressDialog(Kyc_Activity.this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(true);
        pDialog.show();
        PincodeDetailRequest baseRequest=new PincodeDetailRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_PINCODE_DETAIL);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(Kyc_Activity.this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(Kyc_Activity.this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(Kyc_Activity.this));
        baseRequest.setPincode(strPincode);
        //baseRequest.setCountrycode("1");

        Call<PincodeDetailRespose> pincodeDetailResposeCall=
                NetworkClient.getInstance(Kyc_Activity.this).create(UploadKYCServices.class).fetchPincodeAreaDetail(baseRequest,strApiKey);

        pincodeDetailResposeCall.enqueue(new Callback<PincodeDetailRespose>() {
            @Override
            public void onResponse(Call<PincodeDetailRespose> call, Response<PincodeDetailRespose> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    PincodeDetailRespose Response =new PincodeDetailRespose();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {

                        pincodeDetails=Response.getPincodedetail();
                        if(pincodeDetails.length > 0){
                            pincodeAreaArrayList=new ArrayList<PincodeDetailRespose.PincodeDetail>(Arrays.asList(pincodeDetails));
                            pincodeAeraAdapter=new PincodeAreaListAdapter(Kyc_Activity.this,pincodeAreaArrayList);
                            spinArea.setAdapter(pincodeAeraAdapter);
                            edTxtState.setText(Response.getStatename());
                            edTxtState.setTextColor(getResources().getColor(R.color.black));

                            edTxtCity.setText(Response.getCityname());
                            edTxtCity.setTextColor(getResources().getColor(R.color.black));

                            edTxtDistrict.setText(Response.getDistrictname());
                            edTxtDistrict.setTextColor(getResources().getColor(R.color.black));

                            strCityId=Response.getCitycode();
                            strCity=Response.getCityname();
                            strDistrictId=Response.getDistrictcode();
                            strDistrict=Response.getDistrictname();
                            strStateId=Response.getStatecode();
                            spinArea.setSelection(getIndexAreaSpinner(spinArea,strAreaId));

                        }
                        else {
                            String toast= Response.getResponse()+ "Area List is Null";
                            Toast.makeText(Kyc_Activity.this, toast, Toast.LENGTH_SHORT).show();
                            edTxtState.setText("");
                            edTxtCity.setText("");
                            edTxtDistrict.setText("");
                            strCityId="";
                            strDistrictId="";
                            strStateId="";
                        }
                    }
                    else {
                        String toast= Response.getResponse()+ ":" + Response.getMsg();
                        Toast.makeText(Kyc_Activity.this, toast, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PincodeDetailRespose> call, Throwable t) {
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

    /*AccountType List Api Request and Response*/
    private void getAccountTypeList(){

        //showProgressDialog();
        pDialog=new ProgressDialog(Kyc_Activity.this);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest baseRequest= new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_ACCOUNT_TYPE);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(Kyc_Activity.this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(Kyc_Activity.this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(Kyc_Activity.this));

        Call<AccountTypeListResponse> accountTypeListCall=
                NetworkClient.getInstance(Kyc_Activity.this).create(UploadKYCServices.class).fetchAccountTypeList(baseRequest,strApiKey);

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
                            if(!ConnectivityUtils.isNetworkEnabled(Kyc_Activity.this))
                                Toast.makeText(Kyc_Activity.this,getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                            else
                                getBankList();


                        }
                        else {
                            String toast= Response.getResponse()+ "Record is Null";
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
                        String toast= Response.getResponse()+ ": Somthing Wrong" ;
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
            public void onFailure(Call<AccountTypeListResponse> call, Throwable t) {

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
    /*Bank list Api Request and Response*/
    private void getBankList(){

        pDialog=new ProgressDialog(Kyc_Activity.this);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest baseRequest= new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_BANKLIST);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(Kyc_Activity.this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(Kyc_Activity.this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(Kyc_Activity.this));

        Call<BankListResponse> bankListCall=
                NetworkClient.getInstance(Kyc_Activity.this).create(UploadKYCServices.class).fetchBanklist(baseRequest,strApiKey);

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
                            if(!ConnectivityUtils.isNetworkEnabled(Kyc_Activity.this))
                                Toast.makeText(Kyc_Activity.this,getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                            else
                                getKycDetail();


                        }
                        else {
                            String toast= Response.getResponse()+ "Record is Null";
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
                        String toast= Response.getResponse()+ ": Somthing Wrong" ;
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
            public void onFailure(Call<BankListResponse> call, Throwable t) {

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

    /*Get KYC Detail Api Request and REsponse*/
    private void getKycDetail(){

        pDialog=new ProgressDialog(Kyc_Activity.this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest request=new BaseRequest();
        /*Set value in Entity class*/
        request.setReqtype(ApiConstants.REQUEST_GET_KYC);
        request.setPasswd(SharedPrefrence_Business.getPassword(this));
        request.setUserid(SharedPrefrence_Business.getUserID(this));
        request.setIslogin(SharedPrefrence_Business.getIsLogin(this));

        Call<KycDetailResponse> callAddressDetail=
                NetworkClient.getInstance(this).create(UploadKYCServices.class).fetchKycDetail(request,strApiKey);

        callAddressDetail.enqueue(new Callback<KycDetailResponse>() {
            @Override
            public void onResponse(Call<KycDetailResponse> call, Response<KycDetailResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    KycDetailResponse Response =new KycDetailResponse();
                    Response=response.body();
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {

                            getAndSetKycDetail(Response);
                        }
                        else {
                            String toast= Response.getResponse()+ ":" + Response.getMsg();
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
                        String toast= "Something went wrong..please try again.";
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
            public void onFailure(Call<KycDetailResponse> call, Throwable t) {

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

    /* KYC Detail Submit   Api Request and REsponse*/
    private void saveKycDetail(){

        pDialog=new ProgressDialog(Kyc_Activity.this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        SaveKvcRequest request=new SaveKvcRequest();
        /*Set value in Entity class*/

        request.setReqtype(ApiConstants.REQUEST_SAVE_KYC);
        request.setPasswd(SharedPrefrence_Business.getPassword(this));
        request.setUserid(SharedPrefrence_Business.getUserID(this));
        request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        // Address detail
        request.setAddress(strAddress);
        request.setCitycode(strCityId);
        request.setCityname(strCity);
        request.setStatecode(strStateId);
        request.setPincode(strPincode);
        request.setDistrictcode(strDistrictId);
        request.setDistrict(strDistrict);
        request.setAreacode(strAreaId);
        request.setAreaname(strAreaName);
        request.setOtherareaname(strOtherArea);
        request.setIdproofid(strIdProofId);
        request.setIdproofno(strIdProofNo);
        request.setFrontaddressproof(strFrontIdPath);
        request.setBackaddressproof(strBackIdPath);
        // bank detail
        request.setAccounttype(strActype);
        request.setAccountno(strAcno);
        request.setBankcode(strBankId);
        request.setBankname(strBankName);
        request.setOtherbankname(strOtherBank);
        request.setBranchname(strBranch);
        request.setIfsccode(strIfsc);
        request.setBankimage(strBankIdPath);
        // PAN detail
        request.setPanno(strPanNo);
        request.setPanimage(strPanIdPath);

        Call<BaseResponse> callAddressDetail=
                NetworkClient.getInstance(this).create(UploadKYCServices.class).fetchSaveKyc(request,strApiKey);

        callAddressDetail.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    BaseResponse Response =new BaseResponse();
                    Response=response.body();
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {
                            String toast= Response.getMsg();
                            Toast.makeText(Kyc_Activity.this,toast,Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Kyc_Activity.this, BusinessDashboardActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
                            finish();

                        }
                        else {
                            String toast= Response.getResponse()+ ":" + Response.getMsg();
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
                        String toast= "Something went wrong..please try again.";
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

    private void getAndSetKycDetail(KycDetailResponse Response){
        try {
            /* If kyc verify status getAddrsverf = Y
             * means Kyc Verify done then no changes */
            KycDetailResponse response=Response;
            if(response != null){
                if(response.getAddrsverf().equals("Y")) {

                    if (response.getAddressdetail() != null) {
                        addressDetail=response.getAddressdetail();

                        /* Get or set Front Address proof id pic*/
                        if (addressDetail.getAddrproof().equals("") || addressDetail.getAddrproof() == null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Picasso.with(Kyc_Activity.this)
                                        .load(String.valueOf(getDrawable(R.drawable.ic_file_upload)))
                                        .placeholder(R.drawable.ic_file_upload)
                                        .error(R.drawable.ic_file_upload)
                                        .into(imgFrontDoc);
                                txtFrontDocName.setText("No File Attach");
                                strFrontIdPath="";
                                //txtFrontDocName.setVisibility(View.VISIBLE);
                                //https://cpanel.life4ever.co.in/images/UploadImage/202106121724396291030.jpg
                            }

                        }
                        else {

                            Picasso.with(Kyc_Activity.this)
                                    .load(String.valueOf(addressDetail.getAddrproof()))
                                    .placeholder(R.drawable.ic_file_upload)
                                    .error(R.drawable.ic_file_upload)
                                    .into(imgFrontDoc);
                            strFrontIdPath=addressDetail.getAddrproof();
                            String path = addressDetail.getAddrproof();
                            String imageName = path.substring(path.lastIndexOf('/') + 1);
                            txtFrontDocName.setText(imageName);
                            imgFrontDoc.setEnabled(false);
                        }
                        /* Get or set Back side Address proof id pic*/
                        if (addressDetail.getBackaddressproof().equals("") || addressDetail.getBackaddressproof() == null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Picasso.with(Kyc_Activity.this)
                                        .load(String.valueOf(getDrawable(R.drawable.ic_file_upload)))
                                        .placeholder(R.drawable.ic_file_upload)
                                        .error(R.drawable.ic_file_upload)
                                        .into(imgBackDoc);
                                txtBackDocName.setText("No File Attach");
                                strBackIdPath="";
                            }

                        }
                        else {

                            Picasso.with(Kyc_Activity.this)
                                    .load(String.valueOf(addressDetail.getBackaddressproof()))
                                    .placeholder(R.drawable.ic_file_upload)
                                    .error(R.drawable.ic_file_upload)
                                    .into(imgBackDoc);
                            strBackIdPath=addressDetail.getBackaddressproof();
                            String path = addressDetail.getBackaddressproof();
                            String imageName = path.substring(path.lastIndexOf('/') + 1);
                            txtBackDocName.setText(imageName);
                            imgBackDoc.setEnabled(false);
                        }

                        edTxtIdProofNo.setText(addressDetail.getIdproofNo());
                        //edTxtAadhar2.setText(Response.getAadharno2());
                        String address=addressDetail.getAddress();
                        edTxtAddress.setText(address);

                        edTxtCity.setText(addressDetail.getCity());
                        //edTxtState.setText(addressDetail.getStatename());

                        edTxtDistrict.setText(addressDetail.getDistrict());
                        spinAddressProof.setSelection(getIndexSpinIdProof(spinAddressProof,addressDetail.getIdtype()));
                        //spinArea.setSelection(getIndexAreaSpinner(spinArea,addressDetail.getAreacode()));
                        spinState.setSelection(getIndexStateSpinner(spinState,addressDetail.getStatecode()));
                        //strGetAreaId=Response.getAreacode();
                        edTxtPincode.setText(addressDetail.getPincode());


                       // edTxtState.setEnabled(false);
                        edTxtCity.setEnabled(false);
                        edTxtDistrict.setEnabled(false);
                        //strDistrictId=Response.getDistrictcode();
                        //strCityId=Response.getCitycode();
                        strStateId=addressDetail.getStatecode();

                        edTxtIdProofNo.setEnabled(false);
                        //edTxtAadhar2.setText(Response.getAadharno2());
                        //edTxtAadhar3.setText(Response.getAadharno3());
                        edTxtAddress.setEnabled(false);
                        edTxtCity.setEnabled(false);
                       // edTxtState.setEnabled(false);
                        edTxtDistrict.setEnabled(false);
                        spinAddressProof.setEnabled(false);
                        //spinArea.setEnabled(false);
                        spinState.setEnabled(false);
                        edTxtPincode.setEnabled(false);

                    }
                    if(response.getBankdetail() != null){
                        bankDetail = response.getBankdetail();

                        /* Get or set Front Address proof id pic*/
                        if (bankDetail.getBankproof().equals("") || bankDetail.getBankproof() == null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Picasso.with(Kyc_Activity.this)
                                        .load(String.valueOf(getDrawable(R.drawable.ic_file_upload)))
                                        .placeholder(R.drawable.ic_file_upload)
                                        .error(R.drawable.ic_file_upload)
                                        .into(imgBankDoc);
                                txtBankDocName.setText("No File Attach");
                                strBankIdPath="";
                                imgBankDoc.setEnabled(true);
                            }
                        }
                        else {
                            Picasso.with(Kyc_Activity.this)
                                    .load(String.valueOf(bankDetail.getBankproof()))
                                    .placeholder(R.drawable.ic_file_upload)
                                    .error(R.drawable.ic_file_upload)
                                    .into(imgBankDoc);
                            strBankIdPath=bankDetail.getBankproof();
                            String path = bankDetail.getBankproof();
                            String imageName = path.substring(path.lastIndexOf('/') + 1);

                            txtBankDocName.setText(imageName);
                            //txtBankDocName.setText(bankDetail.getBankproof());
                            imgBankDoc.setEnabled(false);
                        }
                        spinAcType.setSelection(getIndexSpinAccountType(spinAcType, bankDetail.getAccounttype()));
                        edTxtAcNo.setText(bankDetail.getAcno());
                        spinBank.setSelection(getIndexSpinBank(spinBank, bankDetail.getBankid()));
                        edTxtBranch.setText(bankDetail.getBranchname());
                        edTxtIFEC.setText(bankDetail.getIfscode());

                        edTxtAcNo.setEnabled(false);

                        edTxtBranch.setEnabled(false);
                        edTxtIFEC.setEnabled(false);
                        spinBank.setEnabled(false);
                        spinAcType.setEnabled(false);
                        //layoutSubmit.setVisibility(View.GONE);
                    }
                    if(response.getPandetail() != null){
                        panDetail=response.getPandetail();
                        if (panDetail.getPanimage().equals("") || panDetail.getPanimage() == null)
                        {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Picasso.with(Kyc_Activity.this)
                                        .load(String.valueOf(getDrawable(R.drawable.ic_camera_black)))
                                        .placeholder(R.drawable.ic_camera_black)
                                        .error(R.drawable.ic_camera_black)
                                        .into(imgPanDoc);
                            }
                            txtPanDocName.setText("No file Attach");
                            imgPanDoc.setEnabled(true);
                        }

                        else{
                            Picasso.with(Kyc_Activity.this)
                                    .load(String.valueOf(panDetail.getPanimage()))
                                    .placeholder(R.drawable.ic_camera_black)
                                    .error(R.drawable.ic_camera_black)
                                    .into(imgPanDoc);
                            strPanIdPath=panDetail.getPanimage();
                            String path =panDetail.getPanimage();
                            String imageName = path.substring(path.lastIndexOf('/') + 1);

                            txtPanDocName.setText(imageName);

                            // txtPanDocName.setText(panDetail.getPanimage());
                            imgPanDoc.setEnabled(false);
                        }

                        edTxtPanNo.setText(panDetail.getPanno());
                        edTxtPanNo.setEnabled(false);

                    }
                    textViewVerify.setText("Status : "+response.getIdverf());
                    txtViewRejectDate.setText("Date : "+response.getVaerifydate());
                    //txtViewRejectRemrk.setText("Status : "+Response.getIdverf());
                    //txtViewRejectReson.setText(Response.getRejectreason());

                    txtViewRejectDate.setTextColor(getResources().getColor(R.color.Green));
                    txtViewRejectRemrk.setTextColor(getResources().getColor(R.color.Green));
                    txtViewRejectReson.setTextColor(getResources().getColor(R.color.Green));
                    layoutSubmit.setVisibility(View.GONE);
                }
                /* If kyc verify status getAddrsverf = N
                 * means Kyc Verify pending then if any field have value blank
                 * then user can edit */
                else if(response.getAddrsverf().equals("N")) {
                    /* Get and set Address Detail*/
                    if(response.getAddressdetail() != null){
                        addressDetail=response.getAddressdetail();
                        /* Get or set Front Address proof id pic*/
                        if (addressDetail.getAddrproof().equals("") || addressDetail.getAddrproof() == null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Picasso.with(Kyc_Activity.this)
                                        .load(String.valueOf(getDrawable(R.drawable.ic_camera_black)))
                                        .placeholder(R.drawable.ic_camera_black)
                                        .error(R.drawable.ic_camera_black)
                                        .into(imgFrontDoc);
                                txtFrontDocName.setText("No File Attach");
                                strFrontIdPath="";
                                imgFrontDoc.setEnabled(true);
                            }

                        }
                        else {

                            Picasso.with(Kyc_Activity.this)
                                    .load(String.valueOf(addressDetail.getAddrproof()))
                                    .placeholder(R.drawable.ic_camera_black)
                                    .error(R.drawable.ic_camera_black)
                                    .into(imgFrontDoc);

                            strFrontIdPath=addressDetail.getAddrproof();
                            String path =addressDetail.getAddrproof();
                            String imageName = path.substring(path.lastIndexOf('/') + 1);
                            txtFrontDocName.setText(imageName);
                            imgFrontDoc.setEnabled(false);

                        }
                        /* Get or set Back side Address proof id pic*/
                        if (addressDetail.getBackaddressproof().equals("") || addressDetail.getBackaddressproof() == null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Picasso.with(Kyc_Activity.this)
                                        .load(String.valueOf(getDrawable(R.drawable.ic_camera_black)))
                                        .placeholder(R.drawable.ic_camera_black)
                                        .error(R.drawable.ic_camera_black)
                                        .into(imgBackDoc);
                                txtBackDocName.setText("No File Attach");
                                imgBackDoc.setEnabled(true);
                                strBackIdPath="";
                            }

                        }
                        else {

                            Picasso.with(Kyc_Activity.this)
                                    .load(String.valueOf(addressDetail.getBackaddressproof()))
                                    .placeholder(R.drawable.ic_camera_black)
                                    .error(R.drawable.ic_camera_black)
                                    .into(imgBackDoc);

                            strBackIdPath=addressDetail.getBackaddressproof();
                            String path =addressDetail.getBackaddressproof();
                            String imageName = path.substring(path.lastIndexOf('/') + 1);
                            txtBackDocName.setText(imageName);
                            //txtBackDocName.setText(addressDetail.getBackaddressproof());
                            imgBackDoc.setEnabled(false);

                        }



                        //for pincode, city, state, district and area
                        if(addressDetail.getCity().equals("") || addressDetail.getDistrict().equals("")
                                || addressDetail.getAddress().equals("")||addressDetail.getAreacode().equals("")
                                 || addressDetail.getStatename().equals("")
                                || addressDetail.getPincode().equals("") ||addressDetail.getAddrproof().equals("")
                                || addressDetail.getBackaddressproof().equals("") || addressDetail.getIdtype().equals("")
                                || addressDetail.getIdtype().contains("--Choose Id Proof--")
                                || addressDetail.getPincode().equals("0") || addressDetail.getIdproofNo().equals("")
                                || addressDetail.getStatecode().equals("0") || addressDetail.getStatecode().equals(""))
                        {

                            edTxtAddress.setText(addressDetail.getAddress());
                            edTxtPincode.setText(addressDetail.getPincode());
                            //edTxtState.setText(addressDetail.getStatename());
                            edTxtDistrict.setText(addressDetail.getDistrict());
                            edTxtCity.setText(addressDetail.getCity());
                            //spinArea.setSelection(getIndexAreaSpinner(spinArea,addressDetail.getAreacode()));
                            spinState.setSelection(getIndexStateSpinner(spinState,addressDetail.getStatecode()));
                            edTxtIdProofNo.setText(addressDetail.getIdproofNo());
                            spinAddressProof.setSelection(getIndexSpinIdProof(spinAddressProof,addressDetail.getIdtype()));

                            edTxtPincode.setEnabled(true);
                            //edTxtState.setEnabled(true);
                            edTxtCity.setEnabled(true);
                            edTxtDistrict.setEnabled(true);
                            spinAddressProof.setEnabled(true);
                            //spinArea.setEnabled(true);
                            spinState.setEnabled(true);
                            edTxtAddress.setEnabled(true);
                            edTxtIdProofNo.setEnabled(true);
                            imgFrontDoc.setEnabled(true);
                            imgBackDoc.setEnabled(true);
                            addkyc=true;

                        }
                        else {

                            edTxtAddress.setText(addressDetail.getAddress());
                            edTxtPincode.setText(addressDetail.getPincode());
                            //edTxtState.setText(addressDetail.getStatename());
                            edTxtDistrict.setText(addressDetail.getDistrict());
                            edTxtCity.setText(addressDetail.getCity());
                            //spinArea.setSelection(getIndexAreaSpinner(spinArea,addressDetail.getAreacode()));
                            spinState.setSelection(getIndexStateSpinner(spinState,addressDetail.getStatecode()));
                            edTxtIdProofNo.setText(addressDetail.getIdproofNo());
                            spinAddressProof.setSelection(getIndexSpinIdProof(spinAddressProof,addressDetail.getIdtype()));

                            edTxtPincode.setEnabled(false);
                            //edTxtState.setEnabled(false);
                            edTxtCity.setEnabled(false);
                            edTxtDistrict.setEnabled(false);
                            spinAddressProof.setEnabled(false);
                            //spinArea.setEnabled(false);
                            spinState.setEnabled(false);
                            edTxtAddress.setEnabled(false);
                            edTxtIdProofNo.setEnabled(false);
                            imgFrontDoc.setEnabled(false);
                            imgBackDoc.setEnabled(false);
                            addkyc=false;

                        }
                        strStateId=addressDetail.getStatecode();
                        strAreaId=addressDetail.getAreacode();

                    }
                    /* Get and set Bank Detail*/
                    if(response.getBankdetail() != null){

                        bankDetail = response.getBankdetail();

                        /* Get or set Front Address proof id pic*/
                        if (bankDetail.getBankproof().equals("") || bankDetail.getBankproof() == null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Picasso.with(Kyc_Activity.this)
                                        .load(String.valueOf(getDrawable(R.drawable.ic_camera_black)))
                                        .placeholder(R.drawable.ic_camera_black)
                                        .error(R.drawable.ic_camera_black)
                                        .into(imgBankDoc);
                                txtBankDocName.setText("No File Attach");
                                imgBankDoc.setEnabled(true);
                                strBankIdPath="";
                            }
                        }
                        else {
                            Picasso.with(Kyc_Activity.this)
                                    .load(String.valueOf(bankDetail.getBankproof()))
                                    .placeholder(R.drawable.ic_camera_black)
                                    .error(R.drawable.ic_camera_black)
                                    .into(imgBankDoc);
                            strBankIdPath=bankDetail.getBankproof();
                            String path =bankDetail.getBankproof();
                            String imageName = path.substring(path.lastIndexOf('/') + 1);
                            txtBankDocName.setText(imageName);
                            //txtBankDocName.setText(bankDetail.getBankproof());
                            imgBankDoc.setEnabled(false);
                        }
                        strBankId=bankDetail.getBankid();
                        strActype=bankDetail.getAccounttype();

                        /*if(bankDetail.getAcno().equals("")){
                            edTxtAcNo.setEnabled(true);
                        }
                        else {
                            edTxtAcNo.setEnabled(false);
                            edTxtAcNo.setText(bankDetail.getAcno());
                        }

                        if(bankDetail.getBranchname().equals("")){
                            edTxtBranch.setEnabled(true);
                        }
                        else {
                            edTxtBranch.setEnabled(false);
                            edTxtBranch.setText(bankDetail.getBranchname());
                        }
                        if (bankDetail.getIfscode().equals("")){
                            edTxtIFEC.setEnabled(true);
                        }
                        else {
                            edTxtIFEC.setEnabled(false);
                            edTxtIFEC.setText(bankDetail.getIfscode());
                        }
                        if(bankDetail.getBankid().equals("")){
                            spinBank.setEnabled(true);
                        }
                        else {
                            spinBank.setSelection(getIndexSpinBank(spinBank,bankDetail.getBankid()));
                            if(bankDetail.getBankid().equals("0")){
                                spinBank.setEnabled(true);
                            }
                            else
                                spinBank.setEnabled(false);

                        }*/
                        /*if(bankDetail.getAccounttype().equals("")){
                            spinAcType.setEnabled(true);
                        }
                        else {
                            spinAcType.setEnabled(false);
                            spinAcType.setSelection(getIndexSpinAccountType(spinAcType,bankDetail.getAccounttype()));
                            if(bankDetail.getAccounttype().equals("0"))
                                spinAcType.setEnabled(true);
                            else
                                spinAcType.setEnabled(true);
                        }*/

                        if(bankDetail.getAcno().equals("") ||bankDetail.getAcno().equals("0")
                                || bankDetail.getAccounttype().equals("") || bankDetail.getAccounttype().contains("CHOOSE ACCOUNT TYPE")
                                || bankDetail.getBankid().equals("") ||bankDetail.getBankid().equals("0")
                                || bankDetail.getBankproof().equals("") || bankDetail.getBranchname().equals("")
                                || bankDetail.getIfscode().equals("")){

                            edTxtAcNo.setEnabled(true);
                            edTxtAcNo.setText(bankDetail.getAcno());
                            edTxtBranch.setEnabled(true);
                            edTxtBranch.setText(bankDetail.getBranchname());
                            edTxtIFEC.setEnabled(true);
                            edTxtIFEC.setText(bankDetail.getIfscode());
                            spinBank.setSelection(getIndexSpinBank(spinBank,bankDetail.getBankid()));
                            spinBank.setEnabled(true);
                            spinAcType.setEnabled(true);
                            spinAcType.setSelection(getIndexSpinAccountType(spinAcType,bankDetail.getAccounttype()));
                            bankkyc=true;

                        }
                        else {
                            edTxtAcNo.setEnabled(false);
                            edTxtAcNo.setText(bankDetail.getAcno());
                            edTxtBranch.setEnabled(false);
                            edTxtBranch.setText(bankDetail.getBranchname());
                            edTxtIFEC.setEnabled(false);
                            edTxtIFEC.setText(bankDetail.getIfscode());
                            spinBank.setSelection(getIndexSpinBank(spinBank,bankDetail.getBankid()));
                            spinBank.setEnabled(false);
                            spinAcType.setEnabled(false);
                            spinAcType.setSelection(getIndexSpinAccountType(spinAcType,bankDetail.getAccounttype()));
                            bankkyc=false;
                        }

                    }

                    /* Get and set PAN Detail*/
                    if(response.getPandetail() != null){
                        panDetail=response.getPandetail();
                        if (panDetail.getPanimage().equals("") || panDetail.getPanimage() == null)
                        {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Picasso.with(Kyc_Activity.this)
                                        .load(String.valueOf(getDrawable(R.drawable.ic_camera_black)))
                                        .placeholder(R.drawable.ic_camera_black)
                                        .error(R.drawable.ic_camera_black)
                                        .into(imgPanDoc);
                            }
                            txtPanDocName.setText("No file Attach");
                            imgPanDoc.setEnabled(true);
                            strPanIdPath="";
                        }

                        else {
                            Picasso.with(Kyc_Activity.this)
                                    .load(String.valueOf(panDetail.getPanimage()))
                                    .placeholder(R.drawable.ic_camera_black)
                                    .error(R.drawable.ic_camera_black)
                                    .into(imgPanDoc);

                            strPanIdPath=panDetail.getPanimage();
                            String path =panDetail.getPanimage();
                            String imageName = path.substring(path.lastIndexOf('/') + 1);
                            txtPanDocName.setText(imageName);

                            //txtPanDocName.setText(panDetail.getPanimage());
                            imgPanDoc.setEnabled(false);
                        }

                        if(panDetail.getPanno().equals("")){
                            edTxtPanNo.setEnabled(true);
                            edTxtPanNo.setText(panDetail.getPanno());
                        }
                        else {
                            edTxtPanNo.setEnabled(false);
                            edTxtPanNo.setText(panDetail.getPanno());
                        }

                        if(panDetail.getPanimage().equals("") || panDetail.getPanno().equals("")
                                || panDetail.getPanno().equals("0")){
                            imgPanDoc.setEnabled(true);
                            edTxtPanNo.setEnabled(true);
                            edTxtPanNo.setText(panDetail.getPanno());
                            pankyc=true;
                        }
                        else {
                            imgPanDoc.setEnabled(false);
                            edTxtPanNo.setEnabled(false);
                            edTxtPanNo.setText(panDetail.getPanno());
                            pankyc=false;
                        }



                    }

                    textViewVerify.setText("Status : "+response.getIdverf());
                    if(response.getVaerifydate().equals(""))
                        txtViewRejectDate.setText(response.getVaerifydate());
                    else
                        txtViewRejectDate.setText("Date : "+response.getVaerifydate());

                    txtViewRejectRemrk.setText(response.getRejectremark());
                    // txtViewRejectReson.setText("Remark : "+Response.getRejectreason());
                    txtViewRejectDate.setTextColor(getResources().getColor(R.color.blue));
                    txtViewRejectRemrk.setTextColor(getResources().getColor(R.color.blue));
                    txtViewRejectReson.setTextColor(getResources().getColor(R.color.blue));

                    if(!addkyc && !bankkyc && !pankyc)
                    {
                        btnSubmit.setEnabled(false);
                        btnSubmit.setVisibility(View.GONE);
                    }
                    else {
                        btnSubmit.setEnabled(true);
                        btnSubmit.setVisibility(View.VISIBLE);
                    }

                }

                /* If kyc verify status getAddrsverf = R
                 * means Kyc Verify Reject
                 * then user can edit and add new doc and data in all field */
                else {

                    /* Get Bank Detail Set value if Kyc verify = R means Rejected*/
                    if(response.getAddressdetail() != null) {
                        addressDetail=response.getAddressdetail();
                        /* Get or set Front Address proof id pic*/
                        if (addressDetail.getAddrproof().equals("") || addressDetail.getAddrproof() == null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Picasso.with(Kyc_Activity.this)
                                        .load(String.valueOf(getDrawable(R.drawable.ic_camera_black)))
                                        .placeholder(R.drawable.ic_camera_black)
                                        .error(R.drawable.ic_camera_black)
                                        .into(imgFrontDoc);
                                txtFrontDocName.setText("No File Attach");
                                imgFrontDoc.setEnabled(true);
                                strFrontIdPath="";
                            }

                        }
                        else {

                            Picasso.with(Kyc_Activity.this)
                                    .load(String.valueOf(addressDetail.getAddrproof()))
                                    .placeholder(R.drawable.ic_camera_black)
                                    .error(R.drawable.ic_camera_black)
                                    .into(imgFrontDoc);
                            txtFrontDocName.setText("No File Attach");
                            imgFrontDoc.setEnabled(true);
                            strFrontIdPath="";

                        }
                        /* Get or set Back side Address proof id pic*/
                        if (addressDetail.getBackaddressproof().equals("") || addressDetail.getBackaddressproof() == null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Picasso.with(Kyc_Activity.this)
                                        .load(String.valueOf(getDrawable(R.drawable.ic_camera_black)))
                                        .placeholder(R.drawable.ic_camera_black)
                                        .error(R.drawable.ic_camera_black)
                                        .into(imgBackDoc);
                                txtBackDocName.setText("No File Attach");
                                imgBackDoc.setEnabled(true);
                                strBackIdPath="";
                            }

                        }
                        else {

                            Picasso.with(Kyc_Activity.this)
                                    .load(String.valueOf(addressDetail.getBackaddressproof()))
                                    .placeholder(R.drawable.ic_camera_black)
                                    .error(R.drawable.ic_camera_black)
                                    .into(imgBackDoc);
                            txtBackDocName.setText("No File Attach");
                            imgBackDoc.setEnabled(true);
                            strBackIdPath="";

                        }

                        String address = addressDetail.getAddress();
                        strAreaId=addressDetail.getAreacode();
                        strStateId=addressDetail.getStatecode();
                        edTxtAddress.setText(address);

                        edTxtIdProofNo.setText(addressDetail.getIdproofNo());
                        edTxtCity.setText(addressDetail.getCity());
                        //edTxtState.setText(addressDetail.getStatename());
                        edTxtDistrict.setText(addressDetail.getDistrict());
                        spinAddressProof.setSelection(getIndexSpinIdProof(spinAddressProof, addressDetail.getIdtype()));
                        //spinArea.setSelection(getIndexAreaSpinner(spinArea, addressDetail.getAreacode()));
                        spinState.setSelection(getIndexStateSpinner(spinState, addressDetail.getStatecode()));
                        edTxtPincode.setText(addressDetail.getPincode());
                        imgFrontDoc.setEnabled(true);
                        imgBackDoc.setEnabled(true);
                        //edTxtState.setEnabled(true);
                        edTxtAddress.setEnabled(true);
                        edTxtCity.setEnabled(true);
                        edTxtDistrict.setEnabled(true);
                        spinAddressProof.setClickable(true);
                        spinAddressProof.setEnabled(true);
                        //spinArea.setClickable(true);
                        spinState.setEnabled(true);
                        edTxtPincode.setEnabled(true);
                    }

                    /* Get Bank Detail Set value if Kyc verify = R means Rejected*/
                    if(response.getBankdetail()!= null){

                        bankDetail=response.getBankdetail();
                        /* Get or set Front Address proof id pic*/
                        if (bankDetail.getBankproof().equals("") || bankDetail.getBankproof() == null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Picasso.with(Kyc_Activity.this)
                                        .load(String.valueOf(getDrawable(R.drawable.ic_camera_black)))
                                        .placeholder(R.drawable.ic_camera_black)
                                        .error(R.drawable.ic_camera_black)
                                        .into(imgBankDoc);
                                txtBankDocName.setText("No File Attach");
                                imgBankDoc.setEnabled(true);
                                strBankIdPath="";
                            }
                        }
                        else {
                            Picasso.with(Kyc_Activity.this)
                                    .load(String.valueOf(bankDetail.getBankproof()))
                                    .placeholder(R.drawable.ic_camera_black)
                                    .error(R.drawable.ic_camera_black)
                                    .into(imgBankDoc);
                            txtBankDocName.setText("No File Attach");
                            imgBankDoc.setEnabled(true);
                            strBankIdPath="";
                        }


                        strBankId=bankDetail.getBankid();
                        strActype=bankDetail.getAccounttype();

                        spinAcType.setSelection(getIndexSpinAccountType(spinAcType,bankDetail.getAccounttype()));
                        edTxtAcNo.setText(bankDetail.getAcno());
                        spinBank.setSelection(getIndexSpinBank(spinBank,bankDetail.getBankid()));
                        edTxtBranch.setText(bankDetail.getBranchname());
                        edTxtIFEC.setText(bankDetail.getIfscode());
                        imgBankDoc.setEnabled(true);
                        edTxtAcNo.setEnabled(true);
                        edTxtBranch.setEnabled(true);
                        edTxtIFEC.setEnabled(true);
                        spinBank.setEnabled(true);
                        spinAcType.setEnabled(true);

                    }
                    /* Get PAN Detail Set value if Kyc verify = R means Rejected*/
                    if(response.getPandetail() != null){
                        panDetail=response.getPandetail();
                        if (panDetail.getPanimage().equals("") || panDetail.getPanimage() == null)
                        {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Picasso.with(Kyc_Activity.this)
                                        .load(String.valueOf(getDrawable(R.drawable.ic_camera_black)))
                                        .placeholder(R.drawable.ic_camera_black)
                                        .error(R.drawable.ic_camera_black)
                                        .into(imgPanDoc);
                            }
                            imgPanDoc.setEnabled(true);
                            txtPanDocName.setText("No file Attach");
                            strPanIdPath="";
                        }

                        else{
                            Picasso.with(Kyc_Activity.this)
                                    .load(String.valueOf(panDetail.getPanimage()))
                                    .placeholder(R.drawable.ic_camera_black)
                                    .error(R.drawable.ic_camera_black)
                                    .into(imgPanDoc);
                            imgPanDoc.setEnabled(true);
                            txtPanDocName.setText("No File Attach");
                            strPanIdPath="";
                        }

                        edTxtPanNo.setText(panDetail.getPanno());
                        edTxtPanNo.setEnabled(true);
                        imgPanDoc.setEnabled(true);
                    }

                    textViewVerify.setText("Status : "+response.getIdverf());
                    if(response.getVaerifydate().equals(""))
                        txtViewRejectDate.setText(response.getVaerifydate());
                    else
                        txtViewRejectDate.setText("Date : "+response.getVaerifydate());

                    txtViewRejectRemrk.setText("Reject Remark: "+response.getRejectremark());
                    txtViewRejectReson.setText("Reject Reason : "+Response.getRejectreason());
                    txtViewRejectDate.setTextColor(getResources().getColor(R.color.red));
                    txtViewRejectRemrk.setTextColor(getResources().getColor(R.color.red));
                    txtViewRejectReson.setTextColor(getResources().getColor(R.color.red));
                    textViewVerify.setTextColor(getResources().getColor(R.color.red));
                    btnSubmit.setVisibility(View.VISIBLE);
                    btnSubmit.setEnabled(true);


                }
            }
            else {}


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /*For Profile Pic change*/
    /*Update Pan Card Detail Api Request and Response*/
    private void UpdateProfileImage(String strfile,String type){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        final KycImageRequest Request = new KycImageRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_KYC_PIC);
        Request.setPasswd(SharedPrefrence_Business.getPassword(this));
        Request.setUserid(SharedPrefrence_Business.getUserID(this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        Request.setType(type);

        File file1=new File(ImageUtil.compressImage(strfile));
        String str1="";
        str1=file1.getPath();

        MultipartBody.Part body;

        if(!str1.equals("")){
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file1);
            body = MultipartBody.Part.createFormData("file:", file1.getName(), reqFile);
            // RequestBody request = RequestBody.create(MediaType.parse("text/plain"), new Gson().toJson(Request));
            RequestBody request = RequestBody.create(MultipartBody.FORM, new Gson().toJson(Request));

            Call<KycImageResponse> callUpdatePanDetail=
                    NetworkClient.getInstance(this).create(UploadKYCServices.class).fetchKycImage(body,request,strApiKey);

            callUpdatePanDetail.enqueue(new Callback<KycImageResponse>() {
                @Override
                public void onResponse(Call<KycImageResponse> call, Response<KycImageResponse> response) {
                    if(pDialog.isShowing())
                        pDialog.dismiss();
                    try {

                        KycImageResponse Response =new KycImageResponse();
                        Response=response.body();

                        if (Response.getResponse().equals("OK")) {

                            String toast= Response.getResponse()+ ": Upload Sucesfully" ;
                            Toast.makeText(Kyc_Activity.this, toast, Toast.LENGTH_SHORT).show();

                            if(Response.getType().equals("frontId")){
                                if(Response.getImage().equals("")){
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        Picasso.with(Kyc_Activity.this)
                                                .load(String.valueOf(Kyc_Activity.this.getDrawable(R.drawable.ic_camera_black)))
                                                .placeholder(R.drawable.ic_camera_black)
                                                .error(R.drawable.ic_camera_black)
                                                .into(imgFrontDoc);
                                        txtFrontDocName.setText("No File Attach");
                                        strFrontIdPath="";
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

                                    Picasso.with(Kyc_Activity.this)
                                            .load(Response.getImage())
                                            .placeholder(R.drawable.ic_camera_black)
                                            .error(R.drawable.ic_camera_black)
                                            .into(imgFrontDoc);

                                    strFrontIdPath=Response.getImage();
                                    String path =Response.getImage();
                                    String imageName = path.substring(path.lastIndexOf('/') + 1);
                                    txtFrontDocName.setText(imageName);


                   /* Glide.with(mContext)  //2
                            .load(hotelSearchList.get(position).getHotelPicture()) //3
                            //.centerCrop() //4
                            .placeholder(R.mipmap.home_icon_hotel) //5
                            .error(R.mipmap.home_icon_hotel) //6
                            .fallback(R.mipmap.home_icon_hotel) //7
                            .into(myViewHolder.imgHotelImg); //8*/
                                }

                            }
                            else if(Response.getType().equals("backId")){
                                if(Response.getImage().equals("")){
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        Picasso.with(Kyc_Activity.this)
                                                .load(String.valueOf(Kyc_Activity.this.getDrawable(R.drawable.ic_camera_black)))
                                                .placeholder(R.drawable.ic_camera_black)
                                                .error(R.drawable.ic_camera_black)
                                                .into(imgBackDoc);
                                        txtBackDocName.setText("No File Attach");
                                        strBackIdPath="";

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

                                    Picasso.with(Kyc_Activity.this)
                                            .load(Response.getImage())
                                            .placeholder(R.drawable.ic_camera_black)
                                            .error(R.drawable.ic_camera_black)
                                            .into(imgBackDoc);

                                    strBackIdPath=Response.getImage();
                                    String path =Response.getImage();
                                    String imageName = path.substring(path.lastIndexOf('/') + 1);
                                    txtBackDocName.setText(imageName);

                                    /* Glide.with(mContext)  //2
                            .load(hotelSearchList.get(position).getHotelPicture()) //3
                            //.centerCrop() //4
                            .placeholder(R.mipmap.home_icon_hotel) //5
                            .error(R.mipmap.home_icon_hotel) //6
                            .fallback(R.mipmap.home_icon_hotel) //7
                            .into(myViewHolder.imgHotelImg); //8*/
                                }
                            }
                            else if(Response.getType().equals("bankId")){
                                if(Response.getImage().equals("")){
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        Picasso.with(Kyc_Activity.this)
                                                .load(String.valueOf(Kyc_Activity.this.getDrawable(R.drawable.ic_camera_black)))
                                                .placeholder(R.drawable.ic_camera_black)
                                                .error(R.drawable.ic_camera_black)
                                                .into(imgBankDoc);
                                        txtBankDocName.setText("No File Attach");
                                       strBankIdPath="";

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

                                    Picasso.with(Kyc_Activity.this)
                                            .load(Response.getImage())
                                            .placeholder(R.drawable.ic_camera_black)
                                            .error(R.drawable.ic_camera_black)
                                            .into(imgBankDoc);

                                    strBankIdPath=Response.getImage();
                                    String path =Response.getImage();
                                    String imageName = path.substring(path.lastIndexOf('/') + 1);
                                    txtBankDocName.setText(imageName);
                                    //txtBankDocName.setText(Response.getImage());


                   /* Glide.with(mContext)  //2
                            .load(hotelSearchList.get(position).getHotelPicture()) //3
                            //.centerCrop() //4
                            .placeholder(R.mipmap.home_icon_hotel) //5
                            .error(R.mipmap.home_icon_hotel) //6
                            .fallback(R.mipmap.home_icon_hotel) //7
                            .into(myViewHolder.imgHotelImg); //8*/
                                }
                            }
                            else if(Response.getType().equals("panId")){
                                if(Response.getImage().equals("")){
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        Picasso.with(Kyc_Activity.this)
                                                .load(String.valueOf(Kyc_Activity.this.getDrawable(R.drawable.ic_camera_black)))
                                                .placeholder(R.drawable.ic_camera_black)
                                                .error(R.drawable.ic_camera_black)
                                                .into(imgPanDoc);
                                       strPanIdPath="";
                                        txtPanDocName.setText("No File Attach");

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

                                    Picasso.with(Kyc_Activity.this)
                                            .load(Response.getImage())
                                            .placeholder(R.drawable.ic_camera_black)
                                            .error(R.drawable.ic_camera_black)
                                            .into(imgPanDoc);
                                    strPanIdPath=Response.getImage();
                                    String path =Response.getImage();
                                    String imageName = path.substring(path.lastIndexOf('/') + 1);
                                    txtPanDocName.setText(imageName);
                                    //txtPanDocName.setText(Response.getImage());


                   /* Glide.with(mContext)  //2
                            .load(hotelSearchList.get(position).getHotelPicture()) //3
                            //.centerCrop() //4
                            .placeholder(R.mipmap.home_icon_hotel) //5
                            .error(R.mipmap.home_icon_hotel) //6
                            .fallback(R.mipmap.home_icon_hotel) //7
                            .into(myViewHolder.imgHotelImg); //8*/
                                }
                            }


                        }
                        else {
                            String toast= Response.getResponse()+ ": Somthing wrong";

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
                public void onFailure(Call<KycImageResponse> call, Throwable t) {
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
            Toast.makeText(Kyc_Activity.this,"File not Select", Toast.LENGTH_SHORT).show();
        }


    }

    //image upload
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    private File getOutputMediaFile(int type) {
        File mediaFile = null;

        try {
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
            if (type == 1) {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
            } else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
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


                    //textViewDocPath.setText(AppConstants.Uri);
                    AppConstants.ImageUri= AppConstants.Uri;
                    ///profilepic.setImageBitmap(bitmap);

                    if(frontId){
                        //AppConstants.ImageUri= AppConstants.Uri;
                        imgFrontDoc.setImageBitmap(bitmap);
                        //txtFrontDocName.setText(AppConstants.Uri);
                        /*Call method for upload image*/
                        uploadAttachment("frontId");
                    }
                    else if(backId){
                        AppConstants.ImageUri= AppConstants.Uri;
                        imgBackDoc.setImageBitmap(bitmap);
                        // txtBackDocName.setText(AppConstants.Uri);
                        /*Call method for upload image*/
                        uploadAttachment("backId");
                    }
                    else if(bankId){
                        AppConstants.ImageUri= AppConstants.Uri;
                        imgBankDoc.setImageBitmap(bitmap);
                        //txtBankDocName.setText(AppConstants.Uri);
                        /*Call method for upload image*/
                        uploadAttachment("bankId");
                    }
                    else if(panId){
                        AppConstants.ImageUri= AppConstants.Uri;
                        imgPanDoc.setImageBitmap(bitmap);
                        //txtPanDocName.setText(AppConstants.Uri);
                        /*Call method for upload image*/
                        uploadAttachment("panId");
                    }


                } else if (resultCode ==RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(Kyc_Activity.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();

                } else {
                    // failed to capture image
                    Toast.makeText(Kyc_Activity.this, "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
                }


            }
            // When an Image is picked from gallery

            else if(requestCode == 1){

                AppConstants.Uri="";
                if (resultCode == RESULT_OK) {
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

                    // textViewDocPath.setText(imgPath);
                    //profilepic.setImageBitmap(bitmap);

                    AppConstants.imgpath = imgPath;
                    AppConstants.ImageUri=imgPath;
//call methodupload image
                    if(frontId){

                        imgFrontDoc.setImageBitmap(bitmap);
                        //txtFrontDocName.setText(AppConstants.Uri);
                        /*Call method for upload image*/
                        uploadAttachment("frontId");
                    }
                    else if(backId){

                        imgBackDoc.setImageBitmap(bitmap);
                        //txtBackDocName.setText(AppConstants.Uri);
                        /*Call method for upload image*/
                        uploadAttachment("backId");
                    }
                    else if(bankId){

                        imgBankDoc.setImageBitmap(bitmap);
                        //txtBankDocName.setText(AppConstants.Uri);
                        /*Call method for upload image*/
                        uploadAttachment("bankId");
                    }
                    else if(panId){

                        imgPanDoc.setImageBitmap(bitmap);
                        // txtPanDocName.setText(AppConstants.Uri);
                        /*Call method for upload image*/
                        uploadAttachment("panId");
                    }

                } else if (resultCode == RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(Kyc_Activity.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();
                }

            }


        }catch (Exception e) {
            Toast.makeText(Kyc_Activity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }


    // When Consult button is clicked
    public void uploadAttachment(String type) {

        if (AppConstants.Uri != null && !AppConstants.Uri.equals("") ) {
            // When Image is capturted from Camera
            sendImageUploadRequest(AppConstants.Uri,type);
        } else if (AppConstants.imgpath != null && !AppConstants.imgpath.isEmpty()) {
            // When Image is selected from Gallery
            sendImageUploadRequest(AppConstants.imgpath,type);
        }
        else {
            Toast.makeText(Kyc_Activity.this, "NO FILE SELECTED TO UPLOAD", Toast.LENGTH_LONG).show();
        }
    }

    private void sendImageUploadRequest(final String filePath,String type) {
        Handler mHandler = new Handler(this.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                UploadImageHandler1 uploadImageHandler = new UploadImageHandler1(filePath);
//
//                String stringParameter=sendUpdatPanCardDetailRequest();
//                uploadImageHandler.execute(ApiConstants.Baseurl, stringParameter);
                //uploadImageHandler.execute(AppConstants.uploadProfileImage, "");
                UpdateProfileImage(filePath,type);

            }
        });
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(this, CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String permissions[], int @NotNull [] grantResults) {
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
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
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

    @Override

    public void onStop(){
        super.onStop();
        //finish();
    }
    @Override
    public void onRestart(){
        super.onRestart();
       // call api
        //Call Id Proof List APi
        /*if(!ConnectivityUtils.isNetworkEnabled(Kyc_Activity.this)){
            Toast.makeText(Kyc_Activity.this,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
        else {
            getIdProoftList();
        }*/
    }
}
