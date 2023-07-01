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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import in.base.network.NetworkClient;
import in.base.util.ImageUtil;
import in.discountmart.R;
import in.discountmart.business.adapter.IdProofAdapter;
import in.discountmart.business.adapter.PincodeAreaListAdapter;
import in.discountmart.business.adapter.StateListAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.business_constants.AppConstants;
import in.discountmart.business.call_api.ProfileServices;
import in.discountmart.business.call_api.UploadKYCServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.PincodeDetailRequest;
import in.discountmart.business.model_business.requestmodel.UpdateAddressProofRequest;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.model_business.responsemodel.GetAddressProofResponse;
import in.discountmart.business.model_business.responsemodel.IdProofListResponse;
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

public class AddressProofActivity extends AppCompatActivity {


    Context context;
    ImageView imgViewDoc;
    ImageView imgViewDoc2;
    EditText edTxtAddress;
    EditText edTxtDistrict;
    EditText edTxtCity;
    EditText edTxtState;
    EditText edTxtIdProofNo;
    EditText edTxtPincode;
    Spinner spinArea;
    Spinner spinAddressProof;
    Button btnSubmit;
    TextView textViewUploddoc;
    TextView textViewUploddoc2;
    TextView textViewDocPath;
    TextView textViewDocPath2;
    TextView textViewVerify;
    TextView txtViewRejectRemrk;
    TextView txtViewRejectDate;
    TextView txtViewRejectReson;
    LinearLayout layoutSubmit;

    String strStateId="";
    String strStateName="";
    String strAddress="";
    String strDistrict="";
    String strDistrictId="";
    String strCity="";
    String strCityId="";
    String strPincode="";
    String strAreaId="";
    String strAreaName="";
    String strIdProofNo="";
    String strIdProof="";
    String strIdProofId="";
    String strimgDoc="";
    String strFilePath="";
    String strFilePath2="";

    //Array List of Object class and object class
    ArrayList<StateListResponse.StateList> stateListArrayList;
    StateListResponse.StateList stateList[];
    StateListAdapter stateListAdapter;
    ArrayList<PincodeDetailRespose.PincodeDetail> pincodeAreaArrayList;
    PincodeDetailRespose.PincodeDetail pincodeDetails[];
    PincodeAreaListAdapter pincodeAeraAdapter;

    ArrayList<IdProofListResponse.IdProofList> idProofListArrayList;
    IdProofListResponse.IdProofList idProofList[];
    IdProofAdapter idProofAdapter;

    ProgressDialog pDialog;
    View view1;

    //For image upload
    private Dialog uploadOptionDialog;
    private static int RESULT_LOAD_IMG = 1;
    //private static int RESULT_LOAD_IMG2 = 2;
    //private static int RESULT_LOAD_IMG = 1;
    private static int RESULT_CLICK_IMG = 2;
    private static final int REQUEST_CODE = 0x11;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private Uri fileUri;
    private Uri fileUri2;
   // private static final int REQUEST_CODE = 10;
   // private static final int REQUEST_CODE2 = 11;
    Bitmap bitmapResizedImage;
    String imgPath;
    String imgPath2;
    String strApiKey="";
    private String mAttachedFilePath;
    private String mAttachedFilePath2;
    int clickbtn=0;

    //int clickbtn2=0;
    private static String camImag="";
    private static String gallreyImag="";
    // int clickSubmitbtn=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_activity_address_proof);
        try{
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            view1=findViewById(android.R.id.content);

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Address Proof");

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(this);

            edTxtAddress=(EditText)findViewById(R.id.address_proof_activity_edTxt_address);
            edTxtDistrict=(EditText)findViewById(R.id.address_proof_activity_edTxt_district);
            edTxtCity=(EditText)findViewById(R.id.address_proof_activity_edTxt_city);
            edTxtPincode=(EditText)findViewById(R.id.address_proof_activity_edTxt_pincode);
            edTxtIdProofNo=(EditText)findViewById(R.id.address_proof_activity_edTxt_aadhar1);
            edTxtState=(EditText)findViewById(R.id.address_proof_activity_edTxt_state);
            //edTxtAadhar2=(EditText)v.findViewById(R.id.address_proof_frag_edTxt_aadhar2);
            //edTxtAadhar3=(EditText)v.findViewById(R.id.address_proof_frag_edTxt_aadhar3);
            textViewDocPath=(TextView)findViewById(R.id.address_proof_activity_txtView_doc_pah);
            textViewDocPath2=(TextView)findViewById(R.id.address_proof_activity_txtView_doc_pah2);
            textViewUploddoc=(TextView)findViewById(R.id.address_proof_activity_txtView_uploadDoc);
            textViewUploddoc2=(TextView)findViewById(R.id.address_proof_activity_txtView_uploadDoc2);
            textViewVerify=(TextView)findViewById(R.id.add_proof_activity_txtView_verify_status);
            txtViewRejectRemrk=(TextView)findViewById(R.id.add_proof_activity_txtView_rejected_mark);
            txtViewRejectDate=(TextView)findViewById(R.id.add_proof_activity_txtView_rejected_date);
            txtViewRejectReson=(TextView)findViewById(R.id.add_proof_activity_txtView_rejected_reason);

            imgViewDoc=(ImageView)findViewById(R.id.address_proof_activity_imgView_doc);
            imgViewDoc2=(ImageView)findViewById(R.id.address_proof_activity_imgView_doc2);
            spinArea=(Spinner)findViewById(R.id.address_proof_activity_spiner_area);
            spinAddressProof=(Spinner)findViewById(R.id.address_proof_activity_spiner_proof);
            btnSubmit=(Button)findViewById(R.id.address_proof_activity_btn_submit);
            layoutSubmit=(LinearLayout)findViewById(R.id.address_proof_activity_layout_submit);

            AppConstants.Uri2="";
            AppConstants.Uri="";
            AppConstants.imgpath="";
            AppConstants.imgpath2="";


            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(this);

            /*Check permission for external storage and camera*/
            if (checkPermission()) {

                //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();

            } else {
                requestPermission();
                //Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
            }

            textViewUploddoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission()) {
                        clickbtn=1;
                        //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddressProofActivity.this);
                        LayoutInflater inflater = AddressProofActivity.this.getLayoutInflater();

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



            textViewUploddoc2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission()) {
                        clickbtn=2;
                        //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddressProofActivity.this);
                        LayoutInflater inflater = AddressProofActivity.this.getLayoutInflater();

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
                                fileUri2 = getOutputMediaFileUri(2);
                                if(!AppConstants.Uri2.equals(""))
                                    AppConstants.Uri2="";

                                AppConstants.Uri2 = fileUri2.getPath();
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri2);
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



            /*Edit Text Pincode On Text Change Listner*/
            edTxtPincode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    strPincode=edTxtPincode.getText().toString();

               /* if(strPincode.length() != 6){
                   Toast.makeText(context,"Pincode not complete",Toast.LENGTH_SHORT).show();
                }*/
                    if(strPincode.length() ==6){
                        //Call Pincode Aera List Api
                        if(!ConnectivityUtils.isNetworkEnabled(AddressProofActivity.this)){
                            Toast.makeText(AddressProofActivity.this,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        }
                        else {

                            getPincodeAreaList();
                        }
                    }

                }
            });

            //spinner Area selection
            spinArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
            });

            //Call State List APi
            if(!ConnectivityUtils.isNetworkEnabled(AddressProofActivity.this)){
                Toast.makeText(AddressProofActivity.this,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
            else {
                getIdProoftList();
            }
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

            /*Button click listener for update detail */
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(edTxtAddress.getText().toString().equals("")){
                        Toast.makeText(AddressProofActivity.this,"Plz Enter Address", Toast.LENGTH_SHORT).show();
                        edTxtAddress.requestFocus();
                    }
                    else if(edTxtDistrict.getText().toString().equals("")){
                        Toast.makeText(AddressProofActivity.this,"Plz Enter Your District", Toast.LENGTH_SHORT).show();
                        edTxtDistrict.requestFocus();
                    }

                    else if(edTxtCity.getText().toString().equals("")){
                        Toast.makeText(AddressProofActivity.this,"Plz Enter Your City", Toast.LENGTH_SHORT).show();
                        edTxtCity.requestFocus();
                    }
                    else if(strStateId.equals("0")){
                        Toast.makeText(AddressProofActivity.this,"Plz Select State", Toast.LENGTH_SHORT).show();
                    }

                    else if(strIdProofId.equals("0")){
                        Toast.makeText(AddressProofActivity.this,"Plz Select Proper Id Proof", Toast.LENGTH_SHORT).show();
                    }
                    else if(edTxtPincode.getText().toString().equals("")){
                        Toast.makeText(AddressProofActivity.this,"Plz Enter Your city Pincode", Toast.LENGTH_SHORT).show();
                        edTxtPincode.requestFocus();
                    }

                    else if(edTxtIdProofNo.getText().toString().equals("")){
                        Toast.makeText(AddressProofActivity.this,"Plz Enter Your Selected Id Proof No.", Toast.LENGTH_SHORT).show();
                        edTxtIdProofNo.requestFocus();
                    }

                    else {

                        strAddress=edTxtAddress.getText().toString();
                        strDistrict=edTxtDistrict.getText().toString();
                        strCity=edTxtCity.getText().toString();
                        strPincode=edTxtPincode.getText().toString();
                        strIdProofNo=edTxtIdProofNo.getText().toString();
                        strPincode=edTxtPincode.getText().toString();
                        strFilePath=textViewDocPath.getText().toString();
                        strFilePath2=textViewDocPath2.getText().toString();

                        if(strFilePath.equals("No File Attach") && strFilePath2.equals("No File Attach")){
                            // call Api for Update Address Proof
                            /*if(!ConnectivityUtils.isNetworkEnabled(AddressProofActivity.this))
                                Toast.makeText(AddressProofActivity.this,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            else

                                UpdateAddProofDetail();*/
                            Toast.makeText(AddressProofActivity.this,"Please attach require document",Toast.LENGTH_SHORT).show();

                        }

                        else {
                            if(!ConnectivityUtils.isNetworkEnabled(AddressProofActivity.this))
                                Toast.makeText(AddressProofActivity.this,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            else

                                uploadAttachment();
                        }

                    }
                }
            });


        }catch (Exception e){

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

    /*StateList Api REquest and Response*/
    private void getStateList(){
        pDialog=new ProgressDialog(AddressProofActivity.this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_STATELIST);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(AddressProofActivity.this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(AddressProofActivity.this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(AddressProofActivity.this));
        baseRequest.setCountrycode("1");

        Call<StateListResponse> stateListResponseCall=
                NetworkClient.getInstance(AddressProofActivity.this).create(ProfileServices.class).fetchStateList(baseRequest,strApiKey);

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
                            //spinState.setAdapter(stateListAdapter);
                            //getIdProoftList();
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
            public void onFailure(Call<StateListResponse> call, Throwable t) {
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

    /*Id Proof List Api Request and Response*/
    private void getIdProoftList(){

        pDialog=new ProgressDialog(AddressProofActivity.this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest request=new BaseRequest();
        /*Set value in Entity class*/
        request.setReqtype(ApiConstants.REQUEST_IDPROOF);
        request.setPasswd(SharedPrefrence_Business.getPassword(AddressProofActivity.this));
        request.setUserid(SharedPrefrence_Business.getUserID(AddressProofActivity.this));
        request.setIslogin(SharedPrefrence_Business.getIsLogin(AddressProofActivity.this));

        Call<IdProofListResponse> idProofListResponseCall=
                NetworkClient.getInstance(AddressProofActivity.this).create(UploadKYCServices.class).fetchIdProofList(request,strApiKey);

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
                            idProofAdapter=new IdProofAdapter(AddressProofActivity.this,idProofListArrayList);
                            spinAddressProof.setAdapter(idProofAdapter);

                            //new getAddProofDetails().execute();
                            getAddressDetail();
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
            public void onFailure(Call<IdProofListResponse> call, Throwable t) {
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

    /*Get Address Detail Api Request and REsponse*/
    private void getAddressDetail(){

        pDialog=new ProgressDialog(AddressProofActivity.this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest request=new BaseRequest();
        /*Set value in Entity class*/
        request.setReqtype(ApiConstants.REQUEST_GET_ADD_PROOF_DETAIL);
        request.setPasswd(SharedPrefrence_Business.getPassword(AddressProofActivity.this));
        request.setUserid(SharedPrefrence_Business.getUserID(AddressProofActivity.this));
        request.setIslogin(SharedPrefrence_Business.getIsLogin(AddressProofActivity.this));

        Call<GetAddressProofResponse> callAddressDetail=
                NetworkClient.getInstance(AddressProofActivity.this).create(UploadKYCServices.class).fetchAddressDetail(request,strApiKey);

        callAddressDetail.enqueue(new Callback<GetAddressProofResponse>() {
            @Override
            public void onResponse(Call<GetAddressProofResponse> call, Response<GetAddressProofResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    GetAddressProofResponse Response =new GetAddressProofResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {

                        if(Response.getAddrsverf().equals("Y")){

                            if (Response.getAddrproof().equals("") || Response.getAddrproof() == null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    Picasso.with(AddressProofActivity.this)
                                            .load(String.valueOf(getDrawable(R.drawable.ic_file_upload)))
                                            .placeholder(R.drawable.ic_file_upload)
                                            .error(R.drawable.ic_file_upload)
                                            .into(imgViewDoc);
                                    textViewDocPath.setText("No File Attach");
                                }

                            }
                            else {

                                Picasso.with(AddressProofActivity.this)
                                        .load(String.valueOf(Response.getAddrproof()))
                                        .placeholder(R.drawable.ic_file_upload)
                                        .error(R.drawable.ic_file_upload)
                                        .into(imgViewDoc);

                            }

                            if (Response.getBackaddressproof().equals("") || Response.getBackaddressproof() == null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    Picasso.with(AddressProofActivity.this)
                                            .load(String.valueOf(getDrawable(R.drawable.ic_file_upload)))
                                            .placeholder(R.drawable.ic_file_upload)
                                            .error(R.drawable.ic_file_upload)
                                            .into(imgViewDoc2);
                                    textViewDocPath2.setText("No File Attach");
                                }

                            }
                            else {

                                Picasso.with(AddressProofActivity.this)
                                        .load(String.valueOf(Response.getBackaddressproof()))
                                        .placeholder(R.drawable.ic_file_upload)
                                        .error(R.drawable.ic_file_upload)
                                        .into(imgViewDoc2);

                            }

                            edTxtIdProofNo.setText(Response.getIdproofNo());
                            //edTxtAadhar2.setText(Response.getAadharno2());
                            String address=Response.getAddress();
                            edTxtAddress.setText(address);

                            edTxtCity.setText(Response.getCity());
                            edTxtState.setText(Response.getStatename());

                            edTxtDistrict.setText(Response.getDistrict());
                            spinAddressProof.setSelection(getIndexSpinIdProof(spinAddressProof,Response.getIdtype()));
                            spinArea.setSelection(getIndexAreaSpinner(spinArea,Response.getAreacode()));
                            //strGetAreaId=Response.getAreacode();
                            edTxtPincode.setText(Response.getPincode());

                            //textViewVerify.setText(Response.getIdverf());
                            txtViewRejectDate.setText(Response.getAddrproofdate());
                            txtViewRejectRemrk.setText("Verification Approved");
                            txtViewRejectReson.setText(Response.getRejectreason());

                            txtViewRejectDate.setTextColor(getResources().getColor(R.color.Green));
                            txtViewRejectRemrk.setTextColor(getResources().getColor(R.color.Green));
                            txtViewRejectReson.setTextColor(getResources().getColor(R.color.Green));
                            edTxtState.setEnabled(false);
                            edTxtCity.setEnabled(false);
                            edTxtDistrict.setEnabled(false);
                            //strDistrictId=Response.getDistrictcode();
                            //strCityId=Response.getCitycode();
                            strStateId=Response.getStatecode();

                            edTxtIdProofNo.setEnabled(false);
                            //edTxtAadhar2.setText(Response.getAadharno2());
                            //edTxtAadhar3.setText(Response.getAadharno3());
                            edTxtAddress.setEnabled(false);
                            edTxtCity.setEnabled(false);
                            edTxtState.setEnabled(false);
                            edTxtDistrict.setEnabled(false);
                            spinAddressProof.setEnabled(false);
                            spinArea.setEnabled(false);
                            edTxtPincode.setEnabled(false);
                            btnSubmit.setVisibility(View.GONE);

                        }
                        else if(Response.getAddrsverf().equals("N")) {


                            if (Response.getAddrproof().equals("") || Response.getAddrproof() == null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    Picasso.with(AddressProofActivity.this)
                                            .load(String.valueOf(getDrawable(R.drawable.ic_file_upload)))
                                            .placeholder(R.drawable.ic_file_upload)
                                            .error(R.drawable.ic_file_upload)
                                            .into(imgViewDoc);
                                    textViewDocPath.setText("No File Attach");

                                    textViewDocPath.setText("No File Attach");

                                }

                            }
                            else {

                                Picasso.with(AddressProofActivity.this)
                                        .load(String.valueOf(Response.getAddrproof()))
                                        .placeholder(R.drawable.ic_file_upload)
                                        .error(R.drawable.ic_file_upload)
                                        .into(imgViewDoc);




                            }

                            if (Response.getBackaddressproof().equals("") || Response.getBackaddressproof() == null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    Picasso.with(AddressProofActivity.this)
                                            .load(String.valueOf(getDrawable(R.drawable.ic_file_upload)))
                                            .placeholder(R.drawable.ic_file_upload)
                                            .error(R.drawable.ic_file_upload)
                                            .into(imgViewDoc2);
                                    textViewDocPath2.setText("No File Attach");

                                }

                            }
                            else {

                                Picasso.with(AddressProofActivity.this)
                                        .load(String.valueOf(Response.getBackaddressproof()))
                                        .placeholder(R.drawable.ic_file_upload)
                                        .error(R.drawable.ic_file_upload)
                                        .into(imgViewDoc2);



                            }

                            String address=Response.getAddress();
                            edTxtAddress.setText(address);
                            edTxtPincode.setText(Response.getPincode());
                            edTxtIdProofNo.setText(Response.getIdproofNo());
                            edTxtCity.setText(Response.getCity());
                            edTxtState.setText(Response.getStatename());
                            edTxtDistrict.setText(Response.getDistrict());
                            spinAddressProof.setSelection(getIndexSpinIdProof(spinAddressProof,Response.getIdtype()));
                            spinArea.setSelection(getIndexAreaSpinner(spinArea,Response.getAreacode()));

                            strAreaId=Response.getAreacode();

                            edTxtState.setEnabled(true);
                            edTxtAddress.setEnabled(true);
                            edTxtCity.setEnabled(true);
                            edTxtDistrict.setEnabled(true);
                            spinAddressProof.setClickable(true);
                            spinAddressProof.setEnabled(true);
                            spinArea.setClickable(true);
                            spinArea.setEnabled(true);
                            edTxtPincode.setEnabled(true);
                            textViewDocPath2.setEnabled(true);
                            textViewUploddoc2.setEnabled(true);
                            textViewDocPath.setEnabled(true);
                            textViewUploddoc.setEnabled(true);
                            btnSubmit.setVisibility(View.VISIBLE);


                           /* *//*Set Address Field value*//*
                            if(Response.getAddress().equals("")){

                                edTxtAddress.setEnabled(true);
                                edTxtAddress.setText("");
                                //edTxtAddress.setHint(getResources().getString(R.string.str_address));
                                //edTxtAddress.setText(Response.getAddress());

                            }
                            else {

                               String address=Response.getAddress();
                                edTxtAddress.setText(address);
                                edTxtAddress.setEnabled(false);
                            }*/



                            /*for pincode, city, state, district and area*/
                            /*if(Response.getCity().equals("") || Response.getDistrict().equals("")
                               || Response.getStatename().equals("")){

                                edTxtCity.setText(Response.getCity());
                                edTxtState.setText(Response.getStatename());
                                edTxtDistrict.setText(Response.getDistrict());
                                edTxtPincode.setText(Response.getPincode());

                                edTxtPincode.setEnabled(true);

                                edTxtState.setEnabled(false);
                                edTxtCity.setEnabled(false);
                                edTxtDistrict.setEnabled(false);
                                spinArea.setClickable(true);
                                spinArea.setEnabled(true);


                            }
                            else {
                                edTxtCity.setText(Response.getCity());
                                edTxtState.setText(Response.getStatename());
                                edTxtDistrict.setText(Response.getDistrict());
                                edTxtPincode.setText(Response.getPincode());
                                spinArea.setSelection(getIndexAreaSpinner(spinArea,Response.getAreacode()));

                                edTxtState.setEnabled(false);
                                edTxtCity.setEnabled(false);
                                edTxtDistrict.setEnabled(false);
                                edTxtPincode.setEnabled(true);

                                spinArea.setClickable(false);
                                spinArea.setEnabled(false);
                            }*/

                            /*Set spinner aera code Field value*/
                            /*if(Response.getAreacode().equals("")){
                                spinArea.setClickable(true);
                                spinArea.setEnabled(true);
                            }
                            else {
                                spinArea.setSelection(getIndexAreaSpinner(spinArea,Response.getAreacode()));
                                spinArea.setClickable(false);
                                spinArea.setEnabled(false);
                            }*/

                            /*Set pincode number Field value*/
                           /* if(Response.getPincode().equals("")){


                                edTxtPincode.setEnabled(true);
                                edTxtPincode.setText("");
                                //edTxtPincode.setHint(getResources().getString(R.string.str_pin));

                            }
                            else {
                                edTxtPincode.setEnabled(false);
                                //edTxtAddress.setHint("");
                                edTxtPincode.setText(Response.getPincode());
                            }*/




                            /*Set id proof number Field value*/
                           /* if(Response.getIdproofNo().equals("")){

                                edTxtIdProofNo.setEnabled(true);
                                edTxtIdProofNo.setText("");
                                //edTxtIdProofNo.setHint(getResources().getString(R.string.str_idproof_no));
                                //edTxtAddress.setText(Response.getAddress());

                            }
                            else {
                                edTxtIdProofNo.setEnabled(false);
                                //edTxtAddress.setHint("");
                                edTxtIdProofNo.setText(Response.getIdproofNo());
                            }*/

                            /*Set spinner Address proof id code Field value*/
                           /* if(Response.getIdtype().equals("")){
                                spinAddressProof.setClickable(true);
                                spinAddressProof.setEnabled(true);
                            }
                            else {
                                spinAddressProof.setSelection(getIndexSpinIdProof(spinAddressProof,Response.getIdtype()));
                                spinAddressProof.setClickable(false);
                                spinAddressProof.setEnabled(false);
                            }*/


                            //textViewVerify.setText(Response.getIdverf());
                            txtViewRejectDate.setText(Response.getAddrproofdate());
                            txtViewRejectRemrk.setText("Verification due");
                            txtViewRejectReson.setText(Response.getRejectreason());
                            txtViewRejectDate.setTextColor(getResources().getColor(R.color.blue));
                            txtViewRejectRemrk.setTextColor(getResources().getColor(R.color.blue));
                            txtViewRejectReson.setTextColor(getResources().getColor(R.color.blue));

                           /* strStateId=Response.getStatecode();
                            if(Response.getAddress().equals("") || Response.getIdproofNo().equals("")||
                               Response.getPincode().equals("") || Response.getIdtype().equals("")||
                               Response.getAreacode().equals("") || Response.getStatename().equals("")||
                                Response.getCity().equals("") || Response.getDistrict().equals(""))

                            {
                                btnSubmit.setVisibility(View.VISIBLE);
                            }
                            else {
                                btnSubmit.setVisibility(View.GONE);
                            }*/


                        }
                        else {
                            if (Response.getAddrproof().equals("") || Response.getAddrproof() == null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    Picasso.with(AddressProofActivity.this)
                                            .load(String.valueOf(getDrawable(R.drawable.ic_file_upload)))
                                            .placeholder(R.drawable.ic_file_upload)
                                            .error(R.drawable.ic_file_upload)
                                            .into(imgViewDoc);
                                    textViewDocPath.setText("No File Attach");
                                }

                            }
                            else {

                                Picasso.with(AddressProofActivity.this)
                                        .load(Response.getAddrproof())
                                        .placeholder(R.drawable.ic_file_upload)
                                        .error(R.drawable.ic_file_upload)
                                        .into(imgViewDoc);


                            }

                            if (Response.getBackaddressproof().equals("") || Response.getBackaddressproof() == null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    Picasso.with(AddressProofActivity.this)
                                            .load(String.valueOf(getDrawable(R.drawable.ic_file_upload)))
                                            .placeholder(R.drawable.ic_file_upload)
                                            .error(R.drawable.ic_file_upload)
                                            .into(imgViewDoc2);
                                    textViewDocPath2.setText("No File Attach");
                                }

                            }
                            else {
                                Picasso.with(AddressProofActivity.this)
                                        .load(String.valueOf(Response.getBackaddressproof()))
                                        .placeholder(R.drawable.ic_file_upload)
                                        .error(R.drawable.ic_file_upload)
                                        .into(imgViewDoc2);
                            }

                            String address=Response.getAddress();
                            edTxtAddress.setText(address);
                            edTxtPincode.setText(Response.getPincode());
                            edTxtIdProofNo.setText(Response.getIdproofNo());
                            edTxtCity.setText(Response.getCity());
                            edTxtState.setText(Response.getStatename());
                            edTxtDistrict.setText(Response.getDistrict());
                            spinAddressProof.setSelection(getIndexSpinIdProof(spinAddressProof,Response.getIdtype()));
                            spinArea.setSelection(getIndexAreaSpinner(spinArea,Response.getAreacode()));

                            edTxtState.setEnabled(true);
                            edTxtAddress.setEnabled(true);
                            edTxtCity.setEnabled(true);
                            edTxtDistrict.setEnabled(true);
                            spinAddressProof.setClickable(true);
                            spinAddressProof.setEnabled(true);
                            spinArea.setClickable(true);
                            spinArea.setEnabled(true);
                            edTxtPincode.setEnabled(true);
                            btnSubmit.setVisibility(View.VISIBLE);

                            //textViewVerify.setText(Response.getIdverf());
                            txtViewRejectDate.setText(Response.getAddrproofdate());
                            txtViewRejectRemrk.setText("Verification Rejected");
                            txtViewRejectReson.setText(Response.getRejectreason());
                            txtViewRejectDate.setTextColor(getResources().getColor(R.color.red));
                            txtViewRejectRemrk.setTextColor(getResources().getColor(R.color.red));
                            txtViewRejectReson.setTextColor(getResources().getColor(R.color.red));

                            strAreaId=Response.getAreacode();
                            strStateId=Response.getStatecode();

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
            public void onFailure(Call<GetAddressProofResponse> call, Throwable t) {

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

    /*StateList Api REquest and Response*/
    private void getPincodeAreaList(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        PincodeDetailRequest baseRequest=new PincodeDetailRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_PINCODE_DETAIL);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(AddressProofActivity.this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(AddressProofActivity.this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(AddressProofActivity.this));
        baseRequest.setPincode(strPincode);
        //baseRequest.setCountrycode("1");

        Call<PincodeDetailRespose> pincodeDetailResposeCall=
                NetworkClient.getInstance(this).create(UploadKYCServices.class).fetchPincodeAreaDetail(baseRequest,strApiKey);

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
                            pincodeAeraAdapter=new PincodeAreaListAdapter(AddressProofActivity.this,pincodeAreaArrayList);
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
                            Toast.makeText(AddressProofActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AddressProofActivity.this, toast, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PincodeDetailRespose> call, Throwable t) {
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

    /*UpdateAddressProof Detail With Iamge Api Request and Response*/
    private void UpdateAddProofDetailWithImage(String strfile1, String strFile2){
        pDialog=new ProgressDialog(AddressProofActivity.this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        final UpdateAddressProofRequest Request = new UpdateAddressProofRequest();

        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_UPDATE_ADD_PROOF_DETAIL);
        Request.setPasswd(SharedPrefrence_Business.getPassword(AddressProofActivity.this));
        Request.setUserid(SharedPrefrence_Business.getUserID(AddressProofActivity.this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(AddressProofActivity.this));
        Request.setIdproofid(strIdProofId);
        Request.setIdproofno(strIdProofNo);
        Request.setAddress1(strAddress);
        Request.setCity(strCity);
        Request.setDistrict(strDistrict);
        Request.setPincode(strPincode);
        Request.setStatecode(strStateId);

        if(strFilePath.equals("No File Attach") && strFilePath2.equals("No File Attach")){
            Request.setBackimg("N");
            Request.setFrontimg("N");
        }
        else {
            if (!strFilePath.equals("No File Attach") && !strFilePath2.equals("No File Attach")){
                Request.setBackimg("B");
                Request.setFrontimg("F");
            }
            else if( !strFilePath.equals("No File Attach") && strFilePath2.equals("No File Attach")){
                Request.setBackimg("N");
                Request.setFrontimg("F");
            }

            else if( strFilePath.equals("No File Attach") && !strFilePath2.equals("No File Attach")){
                Request.setBackimg("B");
                Request.setFrontimg("N");
            }
        }

        File file1 = null;
        File file2=null;
        String str1="";
        String str2="";

        if(!strfile1.equals("")){
            file1=new File(ImageUtil.compressImage(strfile1));
            str1=file1.getPath();
        }
        else {
            file1 = new File(strfile1);
            str1 = file1.getPath();
        }

        if (strFile2.equals("")){
            file2=new File(strFile2);
            str2=file2.getPath();

        }
        else {

            file2=new File(ImageUtil.compressImage(strFile2));
            str2=file2.getPath();
        }



        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        if(!str1.equals("") && str2.equals("")){
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
            builder.addFormDataPart("image1", file1.getName(), requestBody);
            builder.addFormDataPart("MyJson",new Gson().toJson(Request));

        }
        else if(str1.equals("") && !str2.equals("")){
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
            builder.addFormDataPart("image1", file2.getName(), requestBody1);
            builder.addFormDataPart("MyJson",new Gson().toJson(Request));
        }
        else if(!str1.equals("") && !str2.equals("")){
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
            builder.addFormDataPart("image1", file1.getName(), requestBody);
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
            builder.addFormDataPart("image1", file2.getName(), requestBody1);
            builder.addFormDataPart("MyJson",new Gson().toJson(Request));
        }

        RequestBody requestBody=builder.build();


        Call<BaseResponse> callUpdateAddressDetail=
                NetworkClient.getInstance(AddressProofActivity.this).create(UploadKYCServices.class).fetchUpdateAddressWithImage(requestBody,strApiKey);

        callUpdateAddressDetail.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    BaseResponse Response =new BaseResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {

                        String toast= Response.getResponse()+ ": Update Sucessfully" ;
                        Toast.makeText(AddressProofActivity.this, toast, Toast.LENGTH_SHORT).show();
                        textViewDocPath.setText("No File Attach");
                        textViewDocPath2.setText("No File Attach");

                        AppConstants.Uri2="";
                        AppConstants.Uri="";
                        AppConstants.imgpath="";
                        AppConstants.imgpath2="";
                        //call api
                       // getAddressDetail();
                        finish();
                    }
                    else {
                        String toast= Response.getResponse()+" / " + Response.getMsg();
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

    /*UpdateAddressProof Detail Without Image  Api Request and Response*/
    private void UpdateAddProofDetail(){
        pDialog=new ProgressDialog(AddressProofActivity.this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        final UpdateAddressProofRequest Request = new UpdateAddressProofRequest();

        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_UPDATE_ADD_PROOF_DETAIL);
        Request.setPasswd(SharedPrefrence_Business.getPassword(AddressProofActivity.this));
        Request.setUserid(SharedPrefrence_Business.getUserID(AddressProofActivity.this));
        Request.setIslogin(SharedPrefrence_Business.getUserID(AddressProofActivity.this));
        Request.setIdproofid(strIdProofId);
        Request.setIdproofno(strIdProofNo);
        Request.setAddress1(strAddress);
        Request.setCity(strCity);
        Request.setDistrict(strDistrict);
        Request.setPincode(strPincode);
        Request.setStatecode(strStateId);

        if(strFilePath.equals("No file Attach") && strFilePath2.equals("No file Attach")){
            Request.setBackimg("N");
            Request.setFrontimg("N");
        }
        else {
            if (!strFilePath.equals("No file Attach") && !strFilePath2.equals("No file Attach")){
                Request.setBackimg("B");
                Request.setFrontimg("F");
            }
            else if( !strFilePath.equals("No file Attach") && strFilePath2.equals("No file Attach")){
                Request.setBackimg("N");
                Request.setFrontimg("F");
            }

            else if( strFilePath.equals("No file Attach") && !strFilePath2.equals("No file Attach")){
                Request.setBackimg("B");
                Request.setFrontimg("N");
            }
        }


        Call<BaseResponse> callUpdateAddressDetail=
                NetworkClient.getInstance(AddressProofActivity.this).create(UploadKYCServices.class).fetchUpdateAddress(Request,strApiKey);

        callUpdateAddressDetail.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
               if(pDialog.isShowing())
                   pDialog.dismiss();
                try {

                    BaseResponse Response =new BaseResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {

                        String toast= Response.getResponse()+ ":" + "Update Sucessfully";
                        Toast.makeText(AddressProofActivity.this, toast, Toast.LENGTH_SHORT).show();
                        //getAddressDetail();

                        finish();

                    }
                    else {
                        String toast= Response.getResponse()+ ":" + "somthing Wrong "+Response.getMsg();
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
        File mediaFile2;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if(type == 2){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        }
        else {
            return null;
        }

        return mediaFile;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        try {

            if(clickbtn==1){
                // When an Image is click from camera
                if (requestCode == 2) {
                    AppConstants.imgpath="";

                    if (resultCode == this.RESULT_OK) {

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        // down sizing image as it throws OutOfMemory Exception for larger images
                        options.inSampleSize = 8;
                        final Bitmap bitmap = BitmapFactory.decodeFile(AppConstants.Uri,options);
                        textViewDocPath.setText(AppConstants.Uri);
                        imgViewDoc.setImageBitmap(bitmap);
                        //AppConstants.ImageUri=AppConstants.Uri;
                        camImag= AppConstants.Uri;


                    } else if (resultCode ==this.RESULT_CANCELED) {
                        // user cancelled Image capture
                        Toast.makeText(AddressProofActivity.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();

                    } else {
                        // failed to capture image
                        Toast.makeText(AddressProofActivity.this, "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
                    }


                }

                // When an Image is picked from gallery
                else if(requestCode == 1){

                    AppConstants.Uri="";
                    if (resultCode == this.RESULT_OK) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        // Get the cursor
                        Cursor cursor = AddressProofActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imgPath = cursor.getString(columnIndex);
                        cursor.close();

                        // Set the Image in ImageView
                        final Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
                        textViewDocPath.setText(imgPath);
                        imgViewDoc.setImageBitmap(bitmap);

                        AppConstants.imgpath = imgPath;
                        // AppConstants.ImageUri=imgPath;
                        gallreyImag=imgPath;

                        //upload image
                        // uploadAttachment();

                    } else if (resultCode == this.RESULT_CANCELED) {
                        // user cancelled Image capture
                        Toast.makeText(AddressProofActivity.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            else if(clickbtn==2) {
                // When an Image is
                if (requestCode == 2) {
                    AppConstants.imgpath2="";

                    if (resultCode == this.RESULT_OK) {

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        // down sizing image as it throws OutOfMemory Exception for larger images
                        options.inSampleSize = 8;
                        final Bitmap bitmap = BitmapFactory.decodeFile(AppConstants.Uri2,options);
                        textViewDocPath2.setText(AppConstants.Uri2);
                        imgViewDoc2.setImageBitmap(bitmap);


                    } else if (resultCode ==this.RESULT_CANCELED) {
                        // user cancelled Image capture
                        Toast.makeText(AddressProofActivity.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();

                    } else {
                        // failed to capture image
                        Toast.makeText(AddressProofActivity.this, "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
                    }


                }
                // When an Image is picked from gallery
                else if(requestCode == 1){
                    AppConstants.Uri2="";

                    if (resultCode == this.RESULT_OK) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        // Get the cursor
                        Cursor cursor = AddressProofActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imgPath2 = cursor.getString(columnIndex);
                        cursor.close();

                        // Set the Image in ImageView
                        final Bitmap bitmap = BitmapFactory.decodeFile(imgPath2);
                        textViewDocPath2.setText(imgPath2);
                        imgViewDoc2.setImageBitmap(bitmap);

                        AppConstants.imgpath2 = imgPath2;

                    } else if (resultCode == this.RESULT_CANCELED) {
                        // user cancelled Image capture
                        Toast.makeText(AddressProofActivity.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();
                    }

                }

            }

        }catch (Exception e) {
            Toast.makeText(AddressProofActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    // When Consult button is clicked
    public void uploadAttachment() {
        // When Image is capturted from Camera
        if ( !AppConstants.Uri.equals("") && !AppConstants.Uri2.equals("") ){

            sendImageUploadRequest(AppConstants.Uri, AppConstants.Uri2);
            // When Image is selected from Gallery
        }

        else if ( !AppConstants.imgpath.equals("") && !AppConstants.imgpath2.equals("")){

            sendImageUploadRequest(AppConstants.imgpath, AppConstants.imgpath2);
            // When Image is selected from Gallery
        }
        else if (! AppConstants.Uri.equals("") && !AppConstants.imgpath2.equals("")){

            sendImageUploadRequest(AppConstants.Uri, AppConstants.imgpath2);
        }
        else if (! AppConstants.imgpath.equals("") && !AppConstants.Uri2.equals("")){

            sendImageUploadRequest(AppConstants.imgpath, AppConstants.Uri2);
        }

        else if (! AppConstants.Uri2.equals("") && AppConstants.imgpath.equals("")
                && AppConstants.imgpath2.equals("") && AppConstants.Uri.equals("")){

            sendImageUploadRequest(AppConstants.imgpath, AppConstants.Uri2);
        }
        else if (! AppConstants.Uri.equals("") && AppConstants.imgpath.equals("")
                && AppConstants.imgpath2.equals("") && AppConstants.Uri2.equals("")){

            sendImageUploadRequest(AppConstants.Uri, AppConstants.imgpath);
        }
        else if (AppConstants.Uri.equals("") && !AppConstants.imgpath.equals("")
                && AppConstants.imgpath2.equals("") && AppConstants.Uri2.equals("")){

            sendImageUploadRequest(AppConstants.imgpath, AppConstants.Uri2);
        }
        else if (AppConstants.Uri.equals("") && AppConstants.imgpath.equals("")
                && !AppConstants.imgpath2.equals("") && AppConstants.Uri2.equals("")){

            sendImageUploadRequest(AppConstants.Uri, AppConstants.imgpath2);
        }



        else  {
            Toast.makeText(context, "NO FILE SELECTED TO UPLOAD", Toast.LENGTH_LONG).show();
        }
    }

    private void sendImageUploadRequest(final String filePath1, final String filePath2) {
        Handler mHandler = new Handler(AddressProofActivity.this.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                /*Call Api Method*/
                if(!ConnectivityUtils.isNetworkEnabled(AddressProofActivity.this))
                    Toast.makeText(AddressProofActivity.this,getString(R.string.network_error), Toast.LENGTH_SHORT).show();

                else
                    UpdateAddProofDetailWithImage(filePath1,filePath2);

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
        new AlertDialog.Builder(AddressProofActivity.this)
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

}
