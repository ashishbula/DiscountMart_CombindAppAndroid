package in.discountmart.utility_services.fund.activity;

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
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import in.base.network.NetworkClient_Utility;
import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.billpayment.bill_pay_call_api.BillPayApi;
import in.discountmart.utility_services.billpayment.billpay_model.billpay_response_model.UploadDocumentResponse;
import in.discountmart.utility_services.constants.AppConstants;
import in.discountmart.utility_services.fund.fund_api.FundApi;
import in.discountmart.utility_services.fund.fund_model.request.FundRequest;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.TokenBase64;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FundRequestActivity extends AppCompatActivity {

    TextView txtName;
    TextView txtCompName;
    TextInputEditText editTextAmount;
    TextInputLayout textInputLayoutRemark;
    TextInputEditText editTextRemark;
    Spinner spinnerDepositMode;
    Button btnSend;
    TextView txtUploadDoc;
    TextView txtDocPath;
    ImageView imgUpload;
    String strAmount;
    String strName;
    String strCompName;
    String strRemark;
    String strMode;
    View view;
    ProgressDialog progressDialog;
    //For image upload
    private Dialog uploadOptionDialog;
    private static int RESULT_LOAD_IMG = 1;
    private static int RESULT_CLICK_IMG = 2;
    private Uri fileUri;
    private static final int PERMISSION_REQUEST_CODE = 200;
    Bitmap bitmapResizedImage;
    String imgPath="";
    String imgPath2;
    boolean img1;
    boolean img2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_fund_request);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view=findViewById(android.R.id.content);
        try {
            txtCompName=(TextView)findViewById(R.id.fund_req_act_txt_comp_name);
            txtName=(TextView)findViewById(R.id.fund_req_act_txt_name);
            txtUploadDoc=(TextView)findViewById(R.id.fund_req_act_txt_upload_doc);
            txtDocPath=(TextView)findViewById(R.id.fund_req_act_txt_img_pah);
            imgUpload=(ImageView) findViewById(R.id.fund_req_act_img_doc);
            editTextAmount=(TextInputEditText)findViewById(R.id.fund_req_act_edtxt_amount);
            editTextRemark=(TextInputEditText)findViewById(R.id.fund_req_act_edtxt_remark);
            spinnerDepositMode=(Spinner)findViewById(R.id.fund_req_act_spinner_deposit_mode);
            textInputLayoutRemark=(TextInputLayout)findViewById(R.id.fund_req_act_txtInput_remark);
            btnSend=(Button) findViewById(R.id.fund_req_act_btn_send);

            /*Set title on tool bar and back arrow*/
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Fund Request");

            /*Get Value from shared */
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(FundRequestActivity.this).getLoggedinUser();
            Log.e("name",loginResponse.getCompanyName());

            txtName.setText(loginResponse.getUserName());
            txtCompName.setText(loginResponse.getCompanyName());

            /*Chck permission for camera and stores
             * for image pick from gallery
             * and click image from camera*/
            if(checkPermission()){

            }
            else
                requestPermission();

            /*Intiate Spinner Deposit mode*/
            intiSpinnerMode();
            /*Spinner on item select listener*/
            spinnerDepositMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    strMode= spinnerDepositMode.getSelectedItem().toString();
                    if(strMode.contentEquals("CASH")){
                       // editTextRemark.setHint("");
                        textInputLayoutRemark.setHint("Enter Remark");


                    }
                    else if(strMode.contentEquals("NEFT/RTGS")){
                        // editTextRemark.setHint("");
                        textInputLayoutRemark.setHint("Enter NEFT/RTGS Transaction no.");


                    }
                    else if(strMode.contentEquals("IMPS")){
                        // editTextRemark.setHint("");
                        textInputLayoutRemark.setHint("Enter IMPS Transaction no.");


                    }
                    else if(strMode.contentEquals("CHEQUE")){
                        // editTextRemark.setHint("");
                        textInputLayoutRemark.setHint("Enter CHEQUE Number");
                    }
                    else if(strMode.contentEquals("UPI Transaction")){
                        // editTextRemark.setHint("");
                        textInputLayoutRemark.setHint("Enter Upi Transaction no.");
                    }
                    else {
                        //editTextRemark.setHint("");
                        textInputLayoutRemark.setHint("Enter Transaction/Ref. No.");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /*Button Send REquest on click*/
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(txtName.getText().toString().contentEquals("")){
                        Toast.makeText(FundRequestActivity.this,"Please enter name",Toast.LENGTH_SHORT).show();
                    }
                    else if(txtCompName.getText().toString().contentEquals("")){
                        Toast.makeText(FundRequestActivity.this,"Please enter company name",Toast.LENGTH_SHORT).show();
                    }
                    else if(editTextAmount.getText().toString().contentEquals("")){
                        editTextAmount.setError("Please enter amount");
                        editTextAmount.requestFocus();
                    }
                    else if(editTextRemark.getText().toString().contentEquals("")){
                        editTextRemark.setError("Please enter remark");
                        editTextRemark.requestFocus();
                    }
                    else {

                        strAmount=editTextAmount.getText().toString();
                        strRemark=editTextRemark.getText().toString();
                        View view = FundRequestActivity.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(),
                                    0);
                        }

                        /*Call Fund request api*/
                        if(!ConnectivityUtils.isNetworkEnabled(FundRequestActivity.this)){
                            Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();
                        }
                        else {
                            fundRequestServiceCall();
                        }
                    }
                }
            });

            imgUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission()) {

                        //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(FundRequestActivity.this);
                        LayoutInflater inflater = FundRequestActivity.this.getLayoutInflater();

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

                    }
                    else {
                        requestPermission();
                        //Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
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

        super.onBackPressed();
        finish();
        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }


    /*method for initialize spinner*/
    public void intiSpinnerMode(){
        ArrayList<String> list=new ArrayList<String>();
        list.add("NEFT/RTGS");
        list.add("IMPS");
        list.add("CHEQUE");
        list.add("UPI Transaction");
        list.add("CASH");


        ArrayAdapter arrayAdapter = new ArrayAdapter(FundRequestActivity.this,android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerDepositMode.setAdapter(arrayAdapter);    }

    // Add Fund API
    public void fundRequestServiceCall(){

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String strApiRequest_utility="";
        JSONObject object=null;
        String strToken= TokenBase64.getToken();
        LoginResponse loginResponse=new LoginResponse();
        loginResponse=new LoginPreferences_Utility(FundRequestActivity.this).getLoggedinUser();
        String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);
        String formNo=loginResponse.getFormNo().substring(0,loginResponse.getFormNo().length()-2);

        ApiRequest ApiRequest_utility = new ApiRequest();
        try {

            /*Main Request Model*/

            BaseHeaderRequest headerRequest =new BaseHeaderRequest();
            headerRequest.setUserName(loginResponse.getUserName());
            headerRequest.setPassword(loginResponse.getPasswd());
            headerRequest.setSponsorFormNo(companyId);


            /*Promocode Request Model*/
            FundRequest request_utility=new FundRequest();
            request_utility.setFormNo(formNo);
            request_utility.setAmount(strAmount);
            request_utility.setModeId(strMode);
            request_utility.setRemark(strRemark);
            request_utility.setFileName(imgPath);

            ApiRequest_utility.setHEADER(headerRequest);
            ApiRequest_utility.setDATA(request_utility);

            strApiRequest_utility=new Gson().toJson(ApiRequest_utility);

            Log.e("Value",strApiRequest_utility);
        }catch (Exception e){
            e.printStackTrace();
        }


        Call<BaseResponse> fundRquest= NetworkClient_Utility_1.getInstance(this).create(FundApi.class).fundRequest(ApiRequest_utility,strToken);

        fundRquest.enqueue(new Callback<BaseResponse>() {
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
                                Toast.makeText(FundRequestActivity.this, toast, Toast.LENGTH_SHORT).show();

                            }
                            else if(Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")){

                            /*AlertDialogUtils_shop.showDialogWithOneButton(FundRequestActivity.this, "Alert Dialog",
                                    Response.getRESP_MSG(), new AlertDialogButtonListener() {
                                        @Override
                                        public void onButtontapped(String btnText) {
                                            finish();
                                        }
                                    });*/
                                Toast.makeText(FundRequestActivity.this,Response.getRESP_MSG(),Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }
                        else  if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")){

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(FundRequestActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(FundRequestActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(FundRequestActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(FundRequestActivity.this, toast, Toast.LENGTH_SHORT).show();
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
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(FundRequestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

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
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
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

    // When Consult button is clicked
    public void uploadAttachment() {

        if (AppConstants.Uri != null && AppConstants.Uri != "") {
            // When Image is capturted from Camera
            sendImageUploadRequest(AppConstants.Uri);
        }
        else if (AppConstants.imgpath != null && !AppConstants.imgpath.isEmpty()) {
            // When Image is selected from Gallery
            sendImageUploadRequest(AppConstants.imgpath);
        }
        else {
            Toast.makeText(FundRequestActivity.this, "NO FILE SELECTED TO UPLOAD", Toast.LENGTH_LONG).show();
        }
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


        //File file1 = new File(ImageUtil.compressImage(strfile));
        File file1 = new File(strfile);
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

                            imgPath = Response.getName();

                            String toast = "Upload Sucessfully";
                            Toast.makeText(FundRequestActivity.this, toast, Toast.LENGTH_SHORT).show();

                            //getBankProofDetail();
                            //new getBankProofDetails().execute();
                            //((MainActivity_utility)context).replaceFragment(new DashboardFragment(),AppConstants.TAG_HOME,null);
                        } else {
                            String toast = "something wrong" + Response.getStatuscode();
                            Toast.makeText(FundRequestActivity.this, toast, Toast.LENGTH_SHORT).show();

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
            Toast.makeText(FundRequestActivity.this, "No file Selected", Toast.LENGTH_SHORT).show();
        }


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
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        try {

            // When an Image is  camera
            if (reqCode == 2) {
                AppConstants.imgpath = "";

                if (resultCode == this.RESULT_OK) {

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // down sizing image as it throws OutOfMemory Exception for larger images
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(AppConstants.Uri, options);
                    //bitmap.compress(Bitmap.CompressFormat.JPEG,2, new FileOutputStream(AppConstants.Uri));

                    txtDocPath.setText(AppConstants.Uri);
                    imgUpload.setImageBitmap(bitmap);
                    AppConstants.imgpath = imgPath;
                    // call image upload api
                    uploadAttachment();

                    //imgViewDoc.setImageBitmap(bitmap);


                } else if (resultCode == this.RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(FundRequestActivity.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();

                }
                else {
                    // failed to capture image
                    Toast.makeText(FundRequestActivity.this, "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
                }


            }
            // When an Image is picked from gallery
            else if (reqCode == 1) {

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

                    txtDocPath.setText(imgPath);
                    imgUpload.setImageBitmap(bitmap);

                    AppConstants.imgpath = imgPath;


                    //upload image
                    uploadAttachment();

                } else if (resultCode == this.RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(FundRequestActivity.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
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
                /*case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED||
                            grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
                        downloadImage();
                    } else {
                        // Permission Denied
                        Toast.makeText(FundRequestActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                    return;*/


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(FundRequestActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
