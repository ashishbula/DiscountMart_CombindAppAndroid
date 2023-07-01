package in.discountmart.business.fragment;

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
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import in.base.network.NetworkClient;
import in.base.util.ImageUtil;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.business_constants.AppConstants;
import in.discountmart.business.call_api.UploadKYCServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.UploadPanCardRequest;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.model_business.responsemodel.GetPanCardDetailResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PanCardFragment extends Fragment {
    Context context;
    ImageView imgViewDoc;
    EditText edTxtPanNo;
    TextView textViewDocPath;
    TextView textViewUploadDoc;
    TextView textViewVerify;
    TextView textViewRejectDate;
    TextView textViewRejectReson;
    TextView textViewRejectRemark;
    LinearLayout layoutSubmit;
    Button btnSubmit;

    String strPanNo="";
    String strBankName="";
    String strAcno="";
    String strBranch="";
    String strIfsc="";
    String strActype="";
    String strApiKey="";

    String strimgDoc="";

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
    int clickbtn=0;

    Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
    ProgressDialog pDialog;
    View view1;
    //Empty constructor
    public PanCardFragment(){
        //empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.business_pancard_detail_fragment, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        view1=getActivity().findViewById(android.R.id.content);
        context=getActivity();
        try {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            imgViewDoc=(ImageView)v.findViewById(R.id.pan_card_frag_imgView_doc);
            edTxtPanNo=(EditText)v.findViewById(R.id.pan_card_frag_edTxt_panno);
            textViewUploadDoc=(TextView)v.findViewById(R.id.pan_card_frag_txtView_uploadDoc);
            textViewDocPath=(TextView)v.findViewById(R.id.pan_card_frag_txtView_doc_pah);
            textViewVerify=(TextView)v.findViewById(R.id.pan_card_frag_txtView_verify);
            textViewRejectDate=(TextView)v.findViewById(R.id.pan_card_frag_txtView_reject_date);
            textViewRejectRemark=(TextView)v.findViewById(R.id.pan_card_frag_txtView_reject_remark);
            textViewRejectReson=(TextView)v.findViewById(R.id.pan_card_frag_txtView_reject_reson);
            btnSubmit=(Button)v.findViewById(R.id.pan_card_frag_btn_submit);
            layoutSubmit=(LinearLayout)v.findViewById(R.id.pan_card_frag_layout_submit);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);

            /*Check permission for external storage and camera*/
            if (checkPermission()) {

                //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();

            } else {
                requestPermission();
                //Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
            }

            //call api get pan card detail
            if(!ConnectivityUtils.isNetworkEnabled(context)){
                Toast.makeText(context,R.string.network_error, Toast.LENGTH_SHORT).show();
            }
            else {
                getPanCardDetail();
            }


            /*Text upload doc click listner*/
            textViewUploadDoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission()) {

                        //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        LayoutInflater inflater = getActivity().getLayoutInflater();

                        View view = inflater.inflate(R.layout.uploadimgdialog, null);
                        ImageView camera = (ImageView) view.findViewById(R.id.imgcamera);
                        LinearLayout layoutCamera=(LinearLayout)view.findViewById(R.id.layout_camera);
                        LinearLayout layoutGallery=(LinearLayout)view.findViewById(R.id.layout_gallery);
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

            /*Button submitt clicl listener*/
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(edTxtPanNo.getText().toString().equals("")){
                        Toast.makeText(context,"Plz Enter Your PAN Card No", Toast.LENGTH_SHORT).show();
                        edTxtPanNo.requestFocus();
                    }

                    else {
                        strPanNo=edTxtPanNo.getText().toString();
                        strimgDoc=textViewDocPath.getText().toString();
                        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        Matcher matcher = pattern.matcher(strPanNo);
// Check if pattern matches
                        if (matcher.matches() && strimgDoc.equals("No file Attach")  ) {
                            //Toast.makeText(context,"PAN Card Number Matched",Toast.LENGTH_SHORT).show();
                            // new getUpdatePanCardDetailsResponse().execute();

                            //UpdatePanCardDetail();
                            Toast.makeText(context,"Please attach require document",Toast.LENGTH_SHORT).show();

                        }
                        else if(matcher.matches() && !strimgDoc.equals("No file Attach")){
                            uploadAttachment();
                        }
                        else {
                            Toast.makeText(context,"PAN Card Number Not Match", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        }catch (Exception e){e.printStackTrace();}
        context = getActivity();


        return v;
    }


    /*Get Pan Card Detail api request and response*/
    private void getPanCardDetail(){
        pDialog=new ProgressDialog(context);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        final BaseRequest Request = new BaseRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_PAN_CARD_DETAIL);
        Request.setPasswd(SharedPrefrence_Business.getPassword(context));
        Request.setUserid(SharedPrefrence_Business.getUserID(context));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(context));


        Call<GetPanCardDetailResponse> panCardDetailResponseCall=
                NetworkClient.getInstance(context).create(UploadKYCServices.class).fetchPanCardDetail(Request,strApiKey);

        panCardDetailResponseCall.enqueue(new Callback<GetPanCardDetailResponse>() {
            @Override
            public void onResponse(Call<GetPanCardDetailResponse> call, Response<GetPanCardDetailResponse> response) {
             if(pDialog.isShowing())
                 pDialog.dismiss();
                try {

                    GetPanCardDetailResponse Response =new GetPanCardDetailResponse();
                    Response=response.body();

                    if (Response.getResponse().toString().equals("OK")) {
                        if (Response.getPanimage().equals("") || Response.getPanimage() == null)
                        {
                            imgViewDoc.setImageResource(R.drawable.ic_file_upload);
                            //textViewDocPath.setText("No file Attach");
                        }

                        else{
                            Picasso.with(context).load(Response.getPanimage()).into(imgViewDoc);
                            textViewDocPath.setText("No file Attach");
                        }

                        edTxtPanNo.setText(Response.getPanno());

                        if(Response.getIspanverified().equals("Y")){
                            textViewVerify.setText("Verification Status:- "+Response.getPanverf());

                            /*if(Response.getRejectremark().equals(""))
                                textViewRejectRemark.setText("");
                            else
                                textViewRejectRemark.setText("Reject Remark:- "+Response.getRejectremark());*/

                            if (Response.getPanverifydate().equals(""))
                                textViewRejectDate.setText("");
                            else
                                textViewRejectDate.setText("Reject Date:-"+Response.getPanverifydate());

                            /*if(Response.getRejectreason().equals(""))
                                textViewRejectReson.setText("");
                            else
                                textViewRejectReson.setText("Reject Reason:- "+Response.getRejectreason());*/

                            layoutSubmit.setVisibility(View.GONE);
                            edTxtPanNo.setEnabled(false);
                            textViewUploadDoc.setEnabled(false);
                        }

                        else if(Response.getIspanverified().equals("N")) {
                            textViewVerify.setText("Verification Status:- "+Response.getPanverf());

                            if(Response.getRejectremark().equals(""))
                                textViewRejectRemark.setText("");
                            else
                                textViewRejectRemark.setText("Reject Remark:- "+Response.getRejectremark());

                            if (Response.getPanverifydate().equals(""))
                                textViewRejectDate.setText("");
                            else
                                textViewRejectDate.setText("Reject Date:- "+Response.getPanverifydate());

                            /*if(Response.getRejectreason().equals(""))
                                textViewRejectReson.setText("");
                            else
                                textViewRejectReson.setText("Reject Reason:- "+Response.getRejectreason());*/

                            /*if(Response.getPanimage().equals("")){
                                textViewUploadDoc.setEnabled(true);
                                textViewDocPath.setEnabled(true);
                            }
                            else {
                                textViewUploadDoc.setEnabled(false);
                                textViewDocPath.setEnabled(false);
                            }*/
                            /*if(Response.getPanno().equals(""))
                                edTxtPanNo.setEnabled(true);
                            else
                                edTxtPanNo.setEnabled(false);*/
                            edTxtPanNo.setEnabled(true);
                            textViewVerify.setTextColor(getResources().getColor(R.color.blue));
                            textViewRejectRemark.setTextColor(getResources().getColor(R.color.blue));
                            textViewRejectDate.setTextColor(getResources().getColor(R.color.blue));
                            textViewRejectReson.setTextColor(getResources().getColor(R.color.blue));

                            layoutSubmit.setVisibility(View.VISIBLE);
                            textViewUploadDoc.setEnabled(true);
                            textViewDocPath.setEnabled(true);

                        }
                        else if(Response.getIspanverified().equals("R")) {
                            textViewVerify.setText("Verification Status:- "+Response.getPanverf());

                            if(Response.getRejectremark().equals(""))
                                textViewRejectRemark.setText("");
                            else
                                textViewRejectRemark.setText("Reject Remark:- "+Response.getRejectremark());

                            if (Response.getPanverifydate().equals(""))
                                textViewRejectDate.setText("");
                            else
                                textViewRejectDate.setText("Reject Date:- "+Response.getPanverifydate());

                            /*if(Response.getRejectreason().equals(""))
                                textViewRejectReson.setText("");
                            else
                                textViewRejectReson.setText("Reject Reason:- "+Response.getRejectreason());*/

                            textViewVerify.setTextColor(getResources().getColor(R.color.red));
                            textViewRejectRemark.setTextColor(getResources().getColor(R.color.red));
                            textViewRejectDate.setTextColor(getResources().getColor(R.color.red));
                            textViewRejectReson.setTextColor(getResources().getColor(R.color.red));

                            layoutSubmit.setVisibility(View.VISIBLE);
                            textViewUploadDoc.setEnabled(true);
                            textViewDocPath.setEnabled(true);
                            edTxtPanNo.setEnabled(true);

                        }



                    }
                    else {
                        String toast= Response.getResponse()+ ": Somthing wrong" + Response.getMsg();

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
            public void onFailure(Call<GetPanCardDetailResponse> call, Throwable t) {
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

    /*Update Pan Card Detail Api Request and Response*/
    private void UpdatePanCardDetail(){
        pDialog=new ProgressDialog(context);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        final UploadPanCardRequest Request = new UploadPanCardRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_UPDATE_PAN_CARD);
        Request.setPasswd(SharedPrefrence_Business.getPassword(context));
        Request.setUserid(SharedPrefrence_Business.getUserID(context));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        Request.setPanno(strPanNo);

        Call<BaseResponse> callUpdatePanDetail=
                NetworkClient.getInstance(context).create(UploadKYCServices.class).fetchUpdatePanCardDetail(Request,strApiKey);

        callUpdatePanDetail.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    BaseResponse Response =new BaseResponse();
                    Response=response.body();

                    if (Response.getResponse().equals("OK")) {

                        String toast= Response.getResponse()+ ": Upload Sucesfully" ;

                        Snackbar.make(view1, toast, Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();

                        //call api
                        //getPanCardDetail();
                        /*SuccessMsgFragment fragment=new SuccessMsgFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Msg","Upload Successfully");
                        fragment.setArguments(bundle);*/
                        ((BusinessDashboardActivity)context).loadHomeFragment_Business();

                    }
                    else {
                        String toast= Response.getResponse()+ ": Somthing wrong" + Response.getMsg();

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

    /*Update Pan Card Detail Api Request and Response*/
    private void UpdatePanCardDetailWithImage(String strfile){
        pDialog=new ProgressDialog(context);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        final UploadPanCardRequest Request = new UploadPanCardRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_UPDATE_PAN_CARD);
        Request.setPasswd(SharedPrefrence_Business.getPassword(context));
        Request.setUserid(SharedPrefrence_Business.getUserID(context));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        Request.setPanno(strPanNo);

        File file1=new File(ImageUtil.compressImage(strfile));
        String str1="";
        str1=file1.getPath();

        MultipartBody.Part body;

        if(!str1.equals("")){
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file1);
            body = MultipartBody.Part.createFormData("file:", file1.getName(), reqFile);
            // RequestBody request = RequestBody.create(MediaType.parse("text/plain"), new Gson().toJson(Request));
            RequestBody request = RequestBody.create(MultipartBody.FORM, new Gson().toJson(Request));

            Call<BaseResponse> callUpdatePanDetail=
                    NetworkClient.getInstance(context).create(UploadKYCServices.class).fetchUpdatePanDetailWithImageMultipart(body,request,strApiKey);

            callUpdatePanDetail.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if(pDialog.isShowing())
                        pDialog.dismiss();
                    try {

                        BaseResponse Response =new BaseResponse();
                        Response=response.body();

                        if (Response.getResponse().equals("OK")) {

                            String toast= Response.getResponse()+ ": Upload Sucesfully" ;
                            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                           // getPanCardDetail();
                            ((BusinessDashboardActivity)context).loadHomeFragment_Business();
                            //new getBankProofDetails().execute();
                            //((BusinessDashboardActivity)context).replaceFragment(new HomeFragment_gift(),AppConstants.TAG_HOME,null);
                        }
                        else {
                            String toast= Response.getResponse()+ ": Somthing wrong" + Response.getMsg();

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
        else {
            Toast.makeText(context,"File not Select", Toast.LENGTH_SHORT).show();
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

                if (resultCode == getActivity().RESULT_OK) {

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // down sizing image as it throws OutOfMemory Exception for larger images
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(AppConstants.Uri,options);
                    //bitmap.compress(Bitmap.CompressFormat.JPEG,2, new FileOutputStream(AppConstants.Uri));


                    textViewDocPath.setText(AppConstants.Uri);
                    AppConstants.ImageUri= AppConstants.Uri;
                    imgViewDoc.setImageBitmap(bitmap);

                    //uploadAttachment();


                } else if (resultCode ==getActivity().RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(context, "User cancelled image capture", Toast.LENGTH_SHORT).show();

                } else {
                    // failed to capture image
                    Toast.makeText(context, "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
                }


            }
            // When an Image is picked from gallery

            else if(requestCode == 1){

                AppConstants.Uri="";
                if (resultCode == getActivity().RESULT_OK) {
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

                    textViewDocPath.setText(imgPath);
                    imgViewDoc.setImageBitmap(bitmap);

                    AppConstants.imgpath = imgPath;
                    AppConstants.ImageUri=imgPath;

                    //upload image
                    // uploadAttachment();

                } else if (resultCode == getActivity().RESULT_CANCELED) {
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
        Handler mHandler = new Handler(getActivity().getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                UploadImageHandler1 uploadImageHandler = new UploadImageHandler1(filePath);
//
//                String stringParameter=sendUpdatPanCardDetailRequest();
//                uploadImageHandler.execute(ApiConstants.Baseurl, stringParameter);
                //uploadImageHandler.execute(AppConstants.uploadProfileImage, "");
                UpdatePanCardDetailWithImage(filePath);

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

  /*  private MultipartEntity getMultipartEntity(String myJsonRequest) {
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
    //get permission to use gallery
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(context, CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, PERMISSION_REQUEST_CODE);

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
        new AlertDialog.Builder(context)
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

}
