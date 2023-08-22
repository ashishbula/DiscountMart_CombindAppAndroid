package in.discountmart.call_api.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.webkit.GeolocationPermissions;
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
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import in.discountmart.R;
import in.discountmart.activity.DashboardActivity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.app.Activity.RESULT_OK;


public class WebFragment1 extends Fragment {

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
    String mGeoLocationRequestOrigin=null;
    GeolocationPermissions.Callback mGeoLocationCallback=null;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

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
                startWebView(webUrl);

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

    private void startWebView(String url) {

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
        // Below required for geolocation
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDatabasePath("/data/data/" + webView.getContext().getPackageName() + "/databases/");


        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
       // webView.setWebChromeClient(new GeoWebChromeClient());



        //Inject WebAppInterface methods into Web page by having Interface name 'Android'
        // webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new HelloWebViewClient());

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                // Always grant permission since the app itself requires location
                // permission and the user has therefore already granted it

                //getAllowed(origin, (ValueCallback<Boolean>) callback);

                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()),
                            Manifest.permission.ACCESS_FINE_LOCATION)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        new AlertDialog.Builder(context)
                                .setTitle("Permission")
                                .setMessage("R.string.text_location_permission")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Prompt the user once explanation has been shown
                                        ActivityCompat.requestPermissions(getActivity(),
                                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                                MY_PERMISSIONS_REQUEST_LOCATION);
                                    }
                                })
                                .create()
                                .show();


                    }
                    else {
                        mGeoLocationCallback = callback;
                        mGeoLocationRequestOrigin = origin;
                        // No explanation needed, we can request the permission.
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION);
                    }

                }
                else {
                    callback.invoke(origin, true, false);

                }

            }

            public void getAllowed(String origin,
                                   ValueCallback<Boolean> callback) {

            }

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

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                System.out.println("your current url when webpage loading.." + url);
                if(url.contains("https://payments.cashfree.com")){
                    DashboardActivity.bottomNav.setVisibility(View.GONE);
                }
                else {
                    DashboardActivity.bottomNav.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                System.out.println("when you click on any interlink on webview that time you got url :-" + url);
                if(url.contains("https://payments.cashfree.com")){
                    DashboardActivity.bottomNav.setVisibility(View.GONE);
                }
                else {
                    DashboardActivity.bottomNav.setVisibility(View.VISIBLE);
                }
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
        });
        webView.loadUrl(url);

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //This is the filter
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }


               /* if (webView.canGoBack()) {

                    Log.d("CanGoBack","Yes");
                    if(webView.getUrl().contains("https://payments.cashfree.com")){
                       // webView.canGoBack();
                        return false;
                    }
                    else {
                        webView.goBack();
                        return true;
                    }
                    //return true;
                }
                else {
                    Log.d("CanGoBack", "No");
                    return false;
                }*/
                if(webView.getUrl().contains("https://payments.cashfree.com")){
                    // webView.canGoBack();
                    if(event.getAction() == KeyEvent.ACTION_DOWN)
                    {
                        //WebView webView = (WebView) v;

                        switch(keyCode) {
                            case KeyEvent.KEYCODE_BACK:

                                if (webView.canGoBack()) {
                                    //webView.goBack();//
                                    if(webView.getUrl().contains("https://payments.cashfree.com")){
                                        // webView.canGoBack();
                                        return false;
                                    }
                                    else {
                                        webView.goBack();
                                        return true;
                                    }

                                    //return false;
                                } else {

                               /* AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Are you sure you want to exit?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finishAffinity(); // Close all activites
                                                System.exit(0);
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();*/
                                    Log.d("CanGoBack", "No");
                                    return false;
                                }

                        }
                    }
                }
                else {
                    if(event.getAction() == KeyEvent.ACTION_DOWN)
                    {
                        //WebView webView = (WebView) v;

                        switch(keyCode) {
                            case KeyEvent.KEYCODE_BACK:

                                if (webView.canGoBack()) {
                                    //webView.goBack();//
                                    if(webView.getUrl().contains("https://payments.cashfree.com")){
                                        // webView.canGoBack();
                                        return false;
                                    }
                                    else {
                                        webView.goBack();
                                        return true;
                                    }

                                    //return false;
                                } else {

                               /* AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Are you sure you want to exit?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finishAffinity(); // Close all activites
                                                System.exit(0);
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();*/
                                    Log.d("CanGoBack", "No");
                                    return false;
                                }
                        }
                    }
                    return false;
                }


                return true;
            }
        });
    }
    /**
     * WebChromeClient subclass handles UI-related calls
     * Note: think chrome as in decoration, not the Chrome browser
     */
    /*public class GeoWebChromeClient extends WebChromeClient {
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                                                       GeolocationPermissions.Callback callback) {
            // Always grant permission since the app itself requires location
            // permission and the user has therefore already granted it
            callback.invoke(origin, true, false);
        }
    }*/

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


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(context)
                        .setTitle("Permission")
                        .setMessage("R.string.text_location_permission")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(context,
                            ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGeoLocationCallback != null) {
                            mGeoLocationCallback.invoke(mGeoLocationRequestOrigin, true, true);
                        }

                        //Request location updates:
                        // LocationManager locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    //permission denied
                    if (mGeoLocationCallback != null) {
                        mGeoLocationCallback.invoke(mGeoLocationRequestOrigin, false, false);
                    }

                }
                return;
            }

        }
    }

}