package in.discountmart.call_api.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;



import java.net.URLEncoder;

import in.discountmart.R;
import in.discountmart.activity.DashboardActivity;
import in.discountmart.shared_pref_business.SharedPrefrence;


public class WebBrandProdtFragment extends Fragment {

    WebView webView;
    Context context;
    LinearLayout layoutType;
    TextView txtTitle;
    TextView txtUrl;
    Spinner spinnerType;
    ProgressDialog progressDialog;
    String webUrl="";
    String type="";
    String treeType="";
    String from="";
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView=inflater.inflate(R.layout.fragment_web, container, false);

        try {
            context=getActivity();
            view= getActivity().findViewById(android.R.id.content);
            txtTitle=(TextView) mainView.findViewById(R.id.web_frag_txt_title);
            txtUrl=(TextView) mainView.findViewById(R.id.web_frag_txt_url);


            Bundle b = getArguments();

            if(b != null){
                // String from=b.getString("From");
                webUrl=b.getString("URL");
                type=b.getString("Type");
                from=b.getString("From");

                txtTitle.setText(type);


               /* if(from.contains("DashHome")){
                    webUrl=b.getString("URL");
                }
                else {
                    webUrl=b.getString(Constants_shop.WEB_URL);
                }*/
                webView=(WebView)mainView.findViewById(R.id.webview_frag_view);


                /* direct login for Utility portal */
                if(type.equals("Discount Mart Utility")){

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

                /* direct login for Shopping Mart */
                else if(type.equals("Shopping Mart")){
                    String token= "2a3c5e2fe51cfe045c5cccc25503de9d";
                    String mod = "interLogin";
                    String userid= SharedPrefrence.getUserID(context);
                    String password=SharedPrefrence.getPassword(context);
                    String getinfo = "getinfo="+"xyz";
                    String test = "test="+"Yes";


                    String info="token="+token+"&mod="+mod+"&userid="+userid+"&password="+password;
                    //String info1="mod="+mod+"&userid="+userid+"&password="+password+"&token="+token;
                    //String info=userid="+userid+"&password="+password+"&token="+token+"&method="+"Get";
                    String postData = "token="+ URLEncoder.encode(token, "UTF-8")
                            +"&userid="+URLEncoder.encode(userid,"UTF-8")+"&password="+URLEncoder.encode(password,"UTF-8");

                    Log.e("ShopMartinfo :",info );
                    Log.e("ShopMartUrl :",webUrl+info );
                    startWebView(webUrl,info);
                }


                /* direct login for Brand store portal */
                else if(type.equals("Brand Shopping")){
                    String token= "440420729fbfb41a529aadc582c3fd17";
                    String mod = "interLogin";
                    String test = "test"+"123";
                    String getinfo = "getinfo="+"xyz";
                    String userid= SharedPrefrence.getUserID(context);
                    String password=SharedPrefrence.getPassword(context);
                    String info="token="+token+"&mod="+mod+"&userid="+userid+"&password="+password;
                    //String info1="mod="+mod+"&userid="+userid+"&password="+password+"&token="+token;
                    //String info=userid="+userid+"&password="+password+"&token="+token+"&method="+"Get";
                    String postData = "token="+ URLEncoder.encode(token, "UTF-8")
                            +"&userid="+URLEncoder.encode(userid,"UTF-8")+"&password="+URLEncoder.encode(password,"UTF-8");

                    Log.e("BrandShopinfo :",info );
                    Log.e("BrandShopUrl :",webUrl+info );
                    startWebView(webUrl,info);
                }

                /* direct login for Brand store portal */
                else if(type.equals("Food Portal")){
                    byte[] info;
                    String log_key= "5BE2368E-6F44-46C5-9F44-86672E3CC766";
                    String mod = "interLogin";
                    String test = "test"+"123";
                    String userid= SharedPrefrence.getUserID(context);
                    String password=SharedPrefrence.getPassword(context);
                    String infolDetail=userid+";"+password;
                     info = infolDetail.getBytes("UTF-8");
                    String url=webUrl+"user_info="+infolDetail+"&"+"log_key="+log_key;
                    Log.e("FoodUrl :",url );


                    String info64 = android.util.Base64.encodeToString(info, android.util.Base64.DEFAULT);
                    String loginUrl="user_info="+info64+"&"+"log_key="+log_key;
                    String loginUrl1=webUrl+"user_info="+info64+"&"+"log_key="+log_key;
                    Log.e("FoodUrl64 :",loginUrl1 );


                    startWebView(webUrl,loginUrl);
                   // txtUrl.setText(loginUrl.trim());
                }

                /* direct login for Movie Ticket Portal */
                else if(type.equals("Movie Ticket Portal")){
                    byte[] info;
                    String log_key= "A40353C0-219A-4FF8-A382-05EC69723FE6";
                    String mod = "interLogin";
                    String test = "test"+"123";
                    String userid= SharedPrefrence.getUserID(context);
                    String password=SharedPrefrence.getPassword(context);
                    String infolDetail=userid+";"+password;
                    info = infolDetail.getBytes("UTF-8");
                    String url=webUrl+"user_info="+infolDetail+"&"+"log_key="+log_key;
                    Log.e("MovieUrl :",url );


                    String info64 = android.util.Base64.encodeToString(info, android.util.Base64.DEFAULT);
                    String loginUrl="user_info="+info64+"&"+"log_key="+log_key;
                    String loginUrl1=webUrl+"user_info="+info64+"&"+"log_key="+log_key;
                    Log.e("MovieUrl64 :",loginUrl1 );


                    startWebView(webUrl,loginUrl);
                    // txtUrl.setText(loginUrl.trim());
                }

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
        progressDialog.setCancelable(true);
        progressDialog.show();

        WebSettings settings = webView.getSettings();

        //settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        webView.getSettings().setBuiltInZoomControls(true);
        webView.clearCache(true);
        webView.clearFormData();
        webView.clearHistory();
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSaveFormData(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        //Inject WebAppInterface methods into Web page by having Interface name 'Android'
        // webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
       // webView.setWebViewClient(new HelloWebViewClient());



       /* webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
               //l view.loadUrl(url);
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
        //webView.loadData(info,"form-data","BASE64");
        //webView.postUrl(url, Base64.encode(info.getBytes(), Base64.DEFAULT));

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

               /* if (webView.canGoBack()) {
                    webView.goBack();
                    Log.d("CanGoBack","Yes");
                    return true;
                } else {
                    Log.d("CanGoBack", "No");
                    return false;
                }*/
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;
                if ( keyCode == KeyEvent.KEYCODE_BACK) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                        Log.d("CanGoBack","Yes");
                        //return true;
                    } else {
                        Log.d("CanGoBack","No");

                        ((DashboardActivity)context).loadHomeFragment();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

    }

    public void clearBackStack() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }




}