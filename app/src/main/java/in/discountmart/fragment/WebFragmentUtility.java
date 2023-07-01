package in.discountmart.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.discountmart.R;
import in.discountmart.shared_pref_business.SharedPrefrence;

import static android.app.Activity.RESULT_OK;


public class WebFragmentUtility extends Fragment {

    WebView webView;
    Context context;
    LinearLayout layoutType;
    TextView txtTitle;
    Spinner spinnerType;
    ProgressDialog progressDialog;
    String webUrl="";
    String type="";
    String treeType="";
    View view;

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessages;
    private Uri mCapturedImageURI = null;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int FILECHOOSER_RESULTCODE = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView=inflater.inflate(R.layout.fragment_web, container, false);

        try {
            context=getActivity();
            view= getActivity().findViewById(android.R.id.content);
            txtTitle=(TextView) mainView.findViewById(R.id.web_frag_txt_title);



            Bundle b = getArguments();

            if(b != null){
                // String from=b.getString("From");
                webUrl=b.getString("URL");
                type=b.getString("Type");

                txtTitle.setText(type);
               /* if(from.contains("DashHome")){
                    webUrl=b.getString("URL");
                }
                else {
                    webUrl=b.getString(Constants_shop.WEB_URL);
                }*/


                webView=(WebView)mainView.findViewById(R.id.webview_frag_view);

                String token= "76CF170F-3F2A-4876-804B-954605447DEC";
                String mod = "interLogin";
                String userid= SharedPrefrence.getUserID(context);
                String password=SharedPrefrence.getPassword(context);
                String info="token="+token+"&userid="+userid+"&password="+password;
                //String info=userid="+userid+"&password="+password+"&token="+token+"&method="+"Get";
                String postData = "token="+ URLEncoder.encode(token, "UTF-8")
                        +"&userid="+URLEncoder.encode(userid,"UTF-8")+"&password="+URLEncoder.encode(password,"UTF-8");

                Log.e("UtilityUrl :",webUrl+" "+info );
                startWebView(webUrl,info);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return mainView;


    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void startWebView(String url,String info) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDatabasePath("/data/data/" + webView.getContext().getPackageName() + "/databases/");


        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());




        //Inject WebAppInterface methods into Web page by having Interface name 'Android'
        // webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //webView.setWebViewClient(new HelloWebViewClient());



        webView.setWebChromeClient(new WebChromeClient() {

            // For api level bellow 24
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("http")) {
                    // Return false means, web view will handle the link
                    return false;
                }

                return false;
            }


            // From api level 24
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                /*Toast.makeText(mcontext, "New Method",Toast.LENGTH_SHORT).show();*/

                // Get the tel: url
                String url = request.getUrl().toString();

                if (url.startsWith("http")) {
                    // Return false means, web view will handle the link
                    return false;
                }

                return false;
            }

            // openFileChooser for Android 3.0+

           /* public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                openImageChooser();
            }*/

            // For Lollipop 5.0+ Devices

            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadMessages = filePathCallback;
                openImageChooser();
                return true;
            }

            // openFileChooser for Android < 3.0

           /* public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser(uploadMsg, "");
            }

            //openFileChooser for other Android versions

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooser(uploadMsg, acceptType);
            }*/
           @Override
           public void onPermissionRequest(final PermissionRequest request) {
               Log.d("onPermissionRequest","");
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                   request.grant(request.getResources());
               }
           }
        });

      /*  webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(context, "Error:" + description, Toast.LENGTH_SHORT).show();
            }
        });*/
        //webView.loadUrl(url);
        webView.postUrl(url, info.getBytes());
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //This is the filter
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                if (webView.canGoBack()) {
                    webView.goBack();
                    Log.d("CanGoBack","Yes");
                    return true;
                } else {
                    Log.d("CanGoBack", "No");
                    return false;
                }
                /*if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;
                if ( keyCode == KeyEvent.KEYCODE_BACK) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                        Log.d("CanGoBack","Yes");
                    } else {
                        Log.d("CanGoBack","No");
                        if(SharedPrefrence.getUserID(context).equals("")){
                            Intent intent=new Intent(context, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        }
                        else {
                            Intent intent=new Intent(context, DashboardActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        }
                    }
                    return true;
                }*/

            }
        });
    }

    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

    }

    private void openImageChooser() {
        try {

            File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "FolderName");

            if (!imageStorageDir.exists()) {
                imageStorageDir.mkdirs();
            }
            File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
            mCapturedImageURI = Uri.fromFile(file);

            final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");

            Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});

            startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);

            //mFilePathCallback = filePath;
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mCapturedImageURI = Uri.fromFile(photoFile);
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI
                );
            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == FILECHOOSER_RESULTCODE) {

            if (null == this.mUploadMessage && null == this.mUploadMessages) {
                return;
            }

//            Uri result;
//            if (requestCode != RESULT_OK){
//                result = null;
//            }else {
//                result = intent == null ? this.mCapturedImageURI : intent.getData();
//            }
//            this.mUploadMessage.onReceiveValue(result);
//            this.mUploadMessage = null;
            if (null != mUploadMessage) {
                handleUploadMessage(requestCode, resultCode, intent);

            } else if (mUploadMessages != null) {
                handleUploadMessages(requestCode, resultCode, intent);
            }
        }
    }

    private void handleUploadMessage(int requestCode, int resultCode, Intent intent) {
        Uri result = null;
        try {
            if (resultCode != RESULT_OK) {
                result = null;
            } else {
                // retrieve from the private variable if the intent is null

                result = intent == null ? mCapturedImageURI : intent.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mUploadMessage.onReceiveValue(result);
        mUploadMessage = null;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void handleUploadMessages(int requestCode, int resultCode, Intent intent) {
        Uri[] results = null;
        try {
            if (resultCode != RESULT_OK) {
                results = null;
            } else {
                if (intent != null) {
                    String dataString = intent.getDataString();
                    ClipData clipData = intent.getClipData();
                    if (clipData != null) {
                        results = new Uri[clipData.getItemCount()];
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            ClipData.Item item = clipData.getItemAt(i);
                            results[i] = item.getUri();
                        }
                    }
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                } else {
                    results = new Uri[]{mCapturedImageURI};
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mUploadMessages.onReceiveValue(results);
        mUploadMessages = null;
    }




}