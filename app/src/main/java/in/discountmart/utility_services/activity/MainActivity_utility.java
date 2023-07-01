package in.discountmart.utility_services.activity;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import in.base.network.NetworkClient_Utility_1;
import in.base.ui.BaseActivity;
import in.discountmart.R;
import in.discountmart.activity.LoginActivity;
import in.discountmart.listener.AlertDialogButtonListener;
import in.discountmart.shared_pref_business.SharedPrefrence;
import in.discountmart.utility.AlertDialogUtils;
import in.discountmart.utility.ConnectivityUtils;
import in.discountmart.utility_services.adapter.ExpandableListAdapter;
import in.discountmart.utility_services.call_api.MainServices;
import in.discountmart.utility_services.constants.AppConstants;
import in.discountmart.utility_services.fragment.HomeFragment_utility;
import in.discountmart.utility_services.fund.activity.FundRequestActivity;
import in.discountmart.utility_services.model.NavigationItemModel;
import in.discountmart.utility_services.model.SettingModel;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.request_model.MainBalanceRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.model.response_model.MainBalanceResponse;
import in.discountmart.utility_services.report.activity.ReportListActivity;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.sharedpreferences.SettingPreference;
import in.discountmart.utility_services.utilities.TokenBase64;
import pl.openrnd.multilevellistview.MultiLevelListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity_utility extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private MultiLevelListView multiLevelListView;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<NavigationItemModel> listDataHeader;
    HashMap<NavigationItemModel, List<NavigationItemModel>> listDataChild;

    BottomNavigationView bottomNavigationView;
    SwipeRefreshLayout refreshLayout;

    //RequestParams params = new RequestParams();
    ImageView profilepic;
    String strTitle="";
    ProgressDialog pDialog;
    View headerView;
    View view1;
    ImageView imageViewProfilePic;
    ImageView imgEditPic;
    TextView txtUserName;
    TextView txtUserMailId;
    public static TextView txtDrawerWalltBal;
    //TextView txtDrawerAccounttype;
    TextView txtActionbarmYWallet;
    public static TextView txtActionbarWalltBal;
    ImageView imageViewRsSymbol;
    ImageView imageViewLogout;
    LinearLayout layoutToolbar;
    LinearLayout layoutTopside;
    ImageView imgMenuDrawerToggleBtn;

    DrawerLayout drawer;
    NavigationView navigationView;
    MaterialToolbar toolbar;

    View toolbarLayout;
    float mainBal = 0;
    ProgressDialog progressDialog;
    NestedScrollView nestedScrollView;

    public static final String ORANGE = "orange";
    public static final String GRAY = "gray";

    Fragment fragment=null;
    TextView textViewUserId;
    TextView textViewUserName;
    TextView txtRank;
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;
    View view;
    //DashboardResponse Response;
    LoginResponse loginResponse;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(getSavedTheme());
        setContentView(R.layout.utility_activity_main_new);
        view=findViewById(android.R.id.content);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        MainActivity_utility.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       // try {
            toolbar=(MaterialToolbar)findViewById(R.id.home_dash_appbar_main_toolbar) ;
            //nestedScrollView = (NestedScrollView) findViewById(R.id.content_main_nestedscrollview);
            //imgMenuDrawerToggleBtn = (ImageView) findViewById(R.id.custom_toolbat_img_toggle_btn);
            //refreshLayout = (SwipeRefreshLayout) findViewById(R.id.homedash_layout_swipe_refresh);



            //layoutToolbar = (LinearLayout) findViewById(R.id.custom_toolbat_layout_toolbar);
            //txtActionbarmYWallet = (TextView) findViewById(R.id.app_actionbar_txt_mywallet);

            //txtActionbarWalltBal = (TextView) findViewById(R.id.app_actionbar_txt_balance);
            //imageViewRsSymbol = (ImageView) findViewById(R.id.app_actionbar_img_rs);
            //imageViewLogout= (ImageView) findViewById(R.id.actionbar_image_logout);
            navigationView =(NavigationView) findViewById(R.id.utility_nav_view);
            headerView = navigationView.getHeaderView(0);

            textViewUserId=(TextView)headerView.findViewById(R.id.homedash_act_txt_userId);
            textViewUserName=(TextView)headerView.findViewById(R.id.homedash_act_txt_UserName);
        imageViewProfilePic=(ImageView)headerView.findViewById(R.id.imageView);
        profilepic=(ImageView)headerView.findViewById(R.id.homedash_act_img_profileImg);
        imgEditPic=(ImageView) headerView.findViewById(R.id.homedash_act_img_editimage);
            txtRank=(TextView)findViewById(R.id.textView_rank);

            navigationView.setNavigationItemSelectedListener(MainActivity_utility.this);
           profilepic.setImageResource(R.mipmap.logo);


            setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
            // call method for check update
            checkForAppUpdate();

           // txtUserName = (TextView) headerView.findViewById(R.id.nav_header_txt_name);
            //txtUserMailId = (TextView) headerView.findViewById(R.id.nav_header_txt_id);
           // txtDrawerWalltBal = (TextView) headerView.findViewById(R.id.nav_header_txt_balance);
           // txtDrawerAccounttype = (TextView) headerView.findViewById(R.id.nav_header_txt_acctype);
            

           /* refreshLayout.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {

                            // This method performs the actual data-refresh operation.
                            // The method calls setRefreshing(false) when it's finished.
                            getMainBalance();
                            checkForAppUpdate();
                            //onResume();
                        }
                    }
            );*/

            //textViewUserId.setText(SharedPrefrence_Business.getUserID(getApplicationContext()).toUpperCase());
            //textViewUserName.setText(SharedPrefrence_Business.getUsername(getApplicationContext()));

        loginResponse=new LoginResponse();
        loginResponse=new LoginPreferences_Utility(this).getLoggedinUser();
        String userid=loginResponse.getUserName();
        String pass=loginResponse.getMemName();
        textViewUserId.setText(userid);
        textViewUserName.setText(pass);
             //.setText(new LoginPreferences_Utility(MainActivity_utility.this).getLoggedinUser().getMemName());
            //String strWalletBal = new LoginPreferences_Utility(MainActivity_utility.this).getLoggedinUser().getBalance();
            /* Get Shared Preference value
               and Set Profile Pic*/
           /* if(SharedPrefrence_Business.getProfileIamge(this).equals("")){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Picasso.with(this)
                            .load(String.valueOf(this.getDrawable(R.mipmap.logo)))
                            .placeholder(R.mipmap.logo)
                            .error(R.mipmap.logo)
                            .into(profilepic);

                        *//*Glide.with(mContext)  //2
                                .load(String.valueOf(mContext.getDrawable(R.mipmap.home_icon_hotel))) //3
                                .centerCrop() //4
                                .placeholder(R.mipmap.home_icon_hotel) //5
                                .error(R.mipmap.home_icon_hotel) //6
                                .fallback(R.mipmap.home_icon_hotel) //7
                                .into(myViewHolder.imgHotelImg);*//* //8
                }
            }

            else{

                Picasso.with(this)
                        .load(SharedPrefrence_Business.getProfileIamge(MainActivity_utility.this))
                        .placeholder(R.mipmap.logo)
                        .error(R.mipmap.logo)
                        .into(profilepic);

                   *//* Glide.with(mContext)  //2
                            .load(hotelSearchList.get(position).getHotelPicture()) //3
                            //.centerCrop() //4
                            .placeholder(R.mipmap.home_icon_hotel) //5
                            .error(R.mipmap.home_icon_hotel) //6
                            .fallback(R.mipmap.home_icon_hotel) //7
                            .into(myViewHolder.imgHotelImg); //8*//*
            }*/

            drawer = findViewById(R.id.utility_drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
            drawer.setDrawerListener(toggle);
            toggle.syncState();



            //confMenu();
            loadHomeFragment();
           // getDashboardDetail();

            /*Custom Image Drawer Toggle Btn click listner for navegation drawer open*/
            /*imgMenuDrawerToggleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.openDrawer(GravityCompat.START);
                }
            });*/



        Menu menu =navigationView.getMenu();

        //MenuItem target = menu.findItem(R.id.nav_target);
        if(! new LoginPreferences_Utility(MainActivity_utility.this).getLoggedinUser().getUserName().equals("")
                && ! new LoginPreferences_Utility(this).getLoggedinUser().getPasswd().equals("")){
            menu.findItem(R.id.homedash_nav_logout).setVisible(true);
            menu.findItem(R.id.homedash_nav_login).setVisible(false);
        }
        else {
            menu.findItem(R.id.homedash_nav_logout).setVisible(false);
            menu.findItem(R.id.homedash_nav_login).setVisible(true);
        }

            /*txtActionbarmYWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentFund = new Intent(MainActivity_utility.this, MyWalletActivity.class);
                    startActivity(intentFund);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });*/

            /*imageViewRsSymbol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent addfundIntent=new Intent(MainActivity_utility.this, AddFundActivity.class);
                    addfundIntent.putExtra("GW_Bus","");
                    addfundIntent.putExtra("Home","true");
                    addfundIntent.putExtra("DeductAmount","");
                    addfundIntent.putExtra("ServiceType","");
                    startActivity(addfundIntent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });*/

           /* imageViewLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialogUtils.showDialogWithTwoButton(MainActivity_utility.this, new AlertDialogButtonListener() {
                                @Override
                                public void onButtontapped(String btnText) {
                                    if (btnText.equals("YES"))
                                        blankValueofSharePreference();
                                }
                            }, "Alert!", "Are you Sure, you want to logout?", "YES", "NO"
                    );

                }
            });*/
            //navigationView.setNavigationItemSelectedListener(MainActivity_utility_New.this);

            /*Navigation Header
             * profile picture change
             * and Editable*/
           /* imgEditPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission()) {

                        //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_utility.this);
                        LayoutInflater inflater = getLayoutInflater();

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
                                *//*Check permission for external storage and camera*//*

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
                                *//*Check permission for external storage and camera*//*

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
            });*/


            //txtDrawerAccounttype.setText(new LoginPreferences_Utility(MainActivity_utility_New.this).getLoggedinUser().getAcType());

            //Call api utility_main utility_wallet
            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            } else {
                getMainBalance();
            }

            //txtUserName.setText(strUserName);
           // txtUserMailId.setText(strUserMailId);
            // txtWalltBal.setText("Balance:- "+strWalletBal);


//            LayoutInflater mInflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = mInflator.inflate(R.layout.utility_fragment_home1, null);
//            layoutTopside = (LinearLayout) view.findViewById(R.id.homedash_top_layout);


            /*Nested ScrollView  Scroll listener*/
            /*nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    Rect scrollBounds = new Rect();
                    layoutTopside.getHitRect(scrollBounds);

                    if (scrollY > oldScrollY) {
                        //scroll down
                    }
                    if (scrollY < oldScrollY) {

                    }
                    if (scrollY == 0) {

                       *//* if (!layoutTopside.getLocalVisibleRect(scrollBounds)
                                || scrollBounds.height() > layoutTopside.getHeight()) {
                            // imageView is not within or only partially within the visible window
                            layoutToolbar.setBackgroundResource(R.mipmap.app_action_bar_bg);
                            changeStatusBarColor("#ed8323");
                            headerView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            //setTheme(R.style.DashBoardTheme);
                        }*//*
                    }
                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {

                       *//* //bottom scroll
                        if (!layoutTopside.getLocalVisibleRect(scrollBounds)
                                || scrollBounds.height() < layoutTopside.getHeight()) {
                            // imageView is not within or only partially within the visible window
                            layoutToolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            changeStatusBarColor("#4d4d4d");
                            headerView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        }*//*
                    }
                }
            });*/

            bottomNavigationView = (BottomNavigationView) findViewById(R.id.homedash_navigation_bottom);
            
            bottomNavigationView.setOnNavigationItemSelectedListener
                    (new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            Fragment selectedFragment = null;
                            Bundle bundle = new Bundle();
                            switch (item.getItemId()) {
                                case R.id.utility_bottom_nav_action_home:
                                    loadHomeFragment();
                                    break;

                                case R.id.utility_bottom_nav_action_share:
                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                    shareIntent.setType("text/plain");
                                    String string = "\n Get the best deals on travel,recharge, bill payment and booking services . All at one place by simply downloading discountmart App. \n" +
                                            "Visit us at\n"+"Referral ID : " + loginResponse.getUserName()+"\n \n";
                                    string = string + "https://play.google.com/store/apps/details?id=" + MainActivity_utility.this.getPackageName() + "\n\n";
                                    //   + "Sponser Code : " + userInfo_Shoppe.getPhone();

                                    shareIntent.putExtra(Intent.EXTRA_TEXT, string);
                                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "discountmart");
                                    startActivity(Intent.createChooser(shareIntent, "SHARE_APP"));
                                    break;

                                /*case R.id.utility_bottom_nav_action_business:
//                                    Intent intentBusActivity = new Intent(MainActivity_utility.this, MainActivity_Brand.class);
//                                    intentBusActivity.putExtra("Report", AppConstants.TAG_SHARE);
//                                    startActivity(intentBusActivity);
//                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                                    //Toast.makeText(MainActivity_utility.this,"Coming soon..",Toast.LENGTH_SHORT).show();
                                    break;*/
                                case R.id.utility_bottom_nav_action_history:
                                    Intent intentBusActivity = new Intent(MainActivity_utility.this, ReportListActivity.class);
                                    intentBusActivity.putExtra("Report", AppConstants.TAG_REPORT);
                                    startActivity(intentBusActivity);
                                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                                    //Toast.makeText(MainActivity_utility.this,"Coming soon..",Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.utility_bottom_nav_action_support:
                                    Intent intent=new Intent(MainActivity_utility.this,WebViewActivity.class);

                                    String strUrl="https://discountmart.com/contact.asp";

                                    Bundle bundle1=new Bundle();
                                    bundle1.putString("Type","Support");
                                    bundle1.putString("Title","Customer Support");
                                    bundle1.putString("URL",strUrl);
                                    //fragment.setArguments(bundle);
                                    intent.putExtras(bundle1);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                                    //replaceFragment(fragment,"Web",bundle);
                                   // Toast.makeText(MainActivity_utility.this,"Coming soon..",Toast.LENGTH_SHORT).show();
                                    break;



                            }
                            return true;
                        }
                    });
        //} catch (Exception e) {
           // e.printStackTrace();
        //}
    }

    /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.utility_homemenu_action_home) {
            loadHomeFragment();
        }


       *//* if (id == R.id.home_menu_action_cart) {
            // NotificationCountSetClass.setAddToCart(DTIShoppingDashBoardActivity.this, item,notificationCountCart);
            //DTIShoppingAddCartBuyNowFragment cartFragment= new DTIShoppingAddCartBuyNowFragment();
            //replaceFragment(cartFragment, AppConstant.VIEW_CART_BUY_NOW_FRAGMENT,null);

            if (!loginResponse.getPassword().equals("") && !loginResponse.getUserName().equals("")) {
                Intent intent = new Intent(this, ShopCartActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
            } else {

                String toast = " Please login account first";
                Toast.makeText(HomeDashboardActivity.this, toast, Toast.LENGTH_SHORT).show();
            }


            // return true;
        }*//*
        //finish();
        if (id == R.id.utility_homemenu_action_logout){
            showLogoutDialog();
        }

        if (id == R.id.utility_homemenu_action_login){
            Intent intent=new Intent(this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }*/

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.homedash_nav_home) {
            // Handle the camera action
        } else if (id == R.id.homedash_nav_profile) {

        }
       /* else if (id == R.id.homedash_nav_business) {
            Intent intentBusActivity = new Intent(MainActivity_utility.this, BusinessDashboardActivity.class);
            //intentBusActivity.putExtra("Report", TAG_SHARE);
            startActivity(intentBusActivity);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
        }*/
        else if (id == R.id.homedash_nav_logout) {
            showLogoutDialog();

        } else if (id == R.id.homedash_nav_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String string = "\n Get the best deals on travel,recharge, bill payment and booking services . All at one place by simply downloading discountmart App. \n" +
                    "Visit us at\n"+"Referral ID : " + loginResponse.getUserName()+"\n \n";
            string = string + "https://play.google.com/store/apps/details?id=" + MainActivity_utility.this.getPackageName() + "\n\n";
            //   + "Sponser Code : " + userInfo_Shoppe.getPhone();

            shareIntent.putExtra(Intent.EXTRA_TEXT, string);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "discountmart");
            startActivity(Intent.createChooser(shareIntent, "SHARE_APP"));

           // finish();
        } else if (id == R.id.homedash_nav_setting) {
            Intent settingIntent=new Intent(this, SettingActivity.class);
            startActivity(settingIntent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

        } else if (id == R.id.homedash_nav_send) {

        }else if (id == R.id.homedash_nav_fund_req) {
            Intent fundIntent=new Intent(this, FundRequestActivity.class);
            startActivity(fundIntent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

        }
        else if (id == R.id.homedash_nav_bank) {
            Intent fundIntent=new Intent(this, BankInfoActivity.class);
            startActivity(fundIntent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

        }
        else if (id == R.id.homedash_nav_promo) {
            Intent fundIntent=new Intent(this, PromocodeListActivity.class);
            startActivity(fundIntent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

        }
        /*else if (id == R.id.homedash_nav_Eng) {

            return true;

        }
        else if (id == R.id.homedash_nav_hindi) {

            return true;
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.utility_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*Laod Home fragmet on load dashboard activity*/
    public void loadHomeFragment() {
        // setting home default_img checked

        clearBackStack();
        HomeFragment_utility homeFragment = new HomeFragment_utility();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.utility_content_frame, homeFragment, "Home_Brand");
        //ft.addToBackStack(Constants_shop.HOME);
        ft.commit();

    }

    public void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public void clearBackStack1() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 1; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    @Override
    protected void onResume() {
        //userInfo = new com.dreamtouchglobal.dtg.utility.LoginPrefrencesKeys(this).getLoggedinUser();
        navigationView = (NavigationView) findViewById(R.id.utility_nav_view);
        //Login_Logout();
        //fillUserInfo();
        checkNewAppVersionState();
        
        super.onResume();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.utility_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.utility_homemenu_action_home) {
            loadHomeFragment();
        }
        if (id == R.id.utility_homemenu_action_logout) {
            AlertDialogUtils.showMaterialDialogWithTwoButton(MainActivity_utility.this, new AlertDialogButtonListener() {
                        @Override
                        public void onButtontapped(String btnText) {
                            if (btnText.equals("YES"))
                                blankValueofSharePreference();
                        }
                    }, "Alert!", "Are you Sure, you want to logout?", "YES", "NO"
            );
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem item = menu.findItem(R.id.utility_homemenu_action_logout);
        if(! new LoginPreferences_Utility(MainActivity_utility.this).getLoggedinUser().getUserName().equals("")
                && ! new LoginPreferences_Utility(this).getLoggedinUser().getPasswd().equals("")){
            menu.findItem(R.id.utility_homemenu_action_logout).setVisible(true);
            menu.findItem(R.id.utility_homemenu_action_login).setVisible(false);
        }
        else {
            menu.findItem(R.id.utility_homemenu_action_logout).setVisible(false);
            menu.findItem(R.id.utility_homemenu_action_login).setVisible(true);
        }
        int count=0;
//        count= Integer.parseInt(Brand_SharedPrefrence.getCart(ShopDashboardActivity.this));
       /* if(Brand_SharedPrefrence.getCart(this).equals(""))
            count= 0;
        else
            count= Integer.parseInt(String.valueOf(Brand_SharedPrefrence.getCart(this)));
        if(count == 0){
            NotificationCountSetClass.setAddToCart(HomeDashboardActivity.this, item, count);
        }
        else if(count > 0) {
            //NotificationCountSetClass.setAddToCart(DTIShoppingDashBoardActivity.this, item,notificationCountCart);
            NotificationCountSetClass.setAddToCart(HomeDashboardActivity.this, item, count);
        }*/


        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    public void replaceFragment(Fragment fragment, String tag, Bundle bundle) {

        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.utility_content_frame, fragment, tag);
        fragment.setRetainInstance(true);
        ft.addToBackStack(tag);
        try {
            ft.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            ft.commitAllowingStateLoss();
        }
    }

    void showLogoutDialog() {
        try {
            AlertDialogUtils.showDialogWithTwoButton(MainActivity_utility.this, new AlertDialogButtonListener() {
                @Override
                public void onButtontapped(String btnText) {
                    if (btnText.equalsIgnoreCase("yes")) {
                        LoginResponse loginUserGetResponseEntity = new LoginResponse();
                       blankValueofSharePreference();
                       //blankValueofSharePreference1();
                    }
                }
            }, "Logout", "Do you want to logout?", "No", "Yes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void blankValueofSharePreference() {
//        SharedPrefrence_Business.setPassword(MainActivity_utility.this, "");
//        SharedPrefrence_Business.setUserID(MainActivity_utility.this, "");
//        SharedPrefrence_Business.setUsername(MainActivity_utility.this, "");
//        SharedPrefrence_Business.setProfileIamge(MainActivity_utility.this,"");
//        SharedPrefrence_Business.setIsActive(MainActivity_utility.this,"");
//        SharedPrefrence_Business.setUserMobileNumber(MainActivity_utility.this,"");
//
//
//        LoginBrandShop loginBrandShop=new LoginBrandShop();
//        new LoginPreferences_brand(MainActivity_utility.this).SetLoginDetail_Brand(loginBrandShop);

        LoginResponse loginUserGetResponseEntity = new LoginResponse();


        loginUserGetResponseEntity.setUserName("");
        loginUserGetResponseEntity.setMemId(" ");
        loginUserGetResponseEntity.setActiveStatus("");
        loginUserGetResponseEntity.setAddress("");
        loginUserGetResponseEntity.setAmount("");
        loginUserGetResponseEntity.setBalance("");
        loginUserGetResponseEntity.setBillGroupId("");
        loginUserGetResponseEntity.setCity("");
        loginUserGetResponseEntity.setCmpMailId("");
        loginUserGetResponseEntity.setCompanyMobileNo("");
        loginUserGetResponseEntity.setCompanyName("");
        loginUserGetResponseEntity.setCreditlimit("");
        loginUserGetResponseEntity.setEmailId("");
        loginUserGetResponseEntity.setFormNo("");
        loginUserGetResponseEntity.setGroupId("");
        loginUserGetResponseEntity.setHotelGroupId("");
        loginUserGetResponseEntity.setIsAdmin("");
        loginUserGetResponseEntity.setLastName("");
        loginUserGetResponseEntity.setMemMode("");
        loginUserGetResponseEntity.setMemName("");
        loginUserGetResponseEntity.setMobileNo("");
        loginUserGetResponseEntity.setMobileNo2("");
        loginUserGetResponseEntity.setPanNo("");
        loginUserGetResponseEntity.setPasswd("");
        loginUserGetResponseEntity.setRechargeGroupId("");
        loginUserGetResponseEntity.setRegDate("");
        loginUserGetResponseEntity.setSno("");
        loginUserGetResponseEntity.setSponsorFormNo("");

        new LoginPreferences_Utility(this).setLoggedinUser(loginUserGetResponseEntity);
        //loginUserGetResponseEntity.set("");

        /*Blank Additional record*/
        SettingModel settingModel = new SettingModel();
        settingModel.setMobile("");
        settingModel.setEmail("");
        settingModel.setAccountno("");
        settingModel.setIfsc("");
        settingModel.setPanno("");
        settingModel.setGstno("");
        settingModel.setGst_mail("");
        settingModel.setComp_address("");
        settingModel.setContact_no("");
        settingModel.setComp_name("");

        new SettingPreference(MainActivity_utility.this).setSettinginfo(settingModel);


        SharedPrefrence.setPassword(MainActivity_utility.this, "");
        SharedPrefrence.setUserID(MainActivity_utility.this, "");
        SharedPrefrence.setUsername(MainActivity_utility.this, "");
        SharedPrefrence.setProfileIamge(MainActivity_utility.this,"");
        SharedPrefrence.setIsActive(MainActivity_utility.this,"");
        SharedPrefrence.setUserMobileNumber(MainActivity_utility.this,"");
        SharedPrefrence.setIsLogin(MainActivity_utility.this,"N");
        SharedPrefrence.setApiKey(MainActivity_utility.this,"");

        Intent i = new Intent(MainActivity_utility.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

        finish();
        super.onBackPressed();
        this.finish();
    }

    /* Request and Response Get Main Balance*/
    public void getMainBalance() {
        try {

            progressDialog = new ProgressDialog(MainActivity_utility.this);
            progressDialog.setMessage("please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String strApiRequest = "";
            JSONObject object = null;
            LoginResponse loginResponse = new LoginResponse();
            loginResponse = new LoginPreferences_Utility(this).getLoggedinUser();
            String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
            String formno = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);
            String mobile = loginResponse.getMobileNo().substring(0, loginResponse.getMobileNo().length() - 2);

            String strToken = TokenBase64.getToken();

            /*Main Request Model*/
            ApiRequest apiRequest = new ApiRequest();

            try {
                /*Base Request Model*/
                BaseHeaderRequest headerRequest = new BaseHeaderRequest();
                headerRequest.setUserName(new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
                headerRequest.setPassword(new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
                headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/
                MainBalanceRequest request = new MainBalanceRequest();
                request.setFormNo(formno);

                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(request);

                strApiRequest = new Gson().toJson(apiRequest);

                Log.e("Value", strApiRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Call<BaseResponse> fetchFlightBook =
                    NetworkClient_Utility_1.getInstance(this).create(MainServices.class).fetchGetBalance(apiRequest, strToken);

            fetchFlightBook.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    /*if (refreshLayout.isRefreshing())
                        refreshLayout.setRefreshing(false);*/
                    try {

                        BaseResponse Response = new BaseResponse();
                        Response = response.body();

                        if(Response != null){
                            if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                if (!Response.getRESP_VALUE().equals("null") || !Response.getRESP_VALUE().equals("")) {

                                    MainBalanceResponse balanceResponse =
                                            new Gson().fromJson(Response.getRESP_VALUE(), MainBalanceResponse.class);


                                    //String toast= Response.getRESP_MSG();
                                    //Toast.makeText(OtpVerify_FlightBookActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    if (balanceResponse != null) {
                                        // get Main Wallet balance
                                       // new LoginPreferences_Utility(MainActivity_utility.this).getLoggedinUser().setBalance(balanceResponse.getBalance());
                                        mainBal = Float.parseFloat(balanceResponse.getBalance());
                                        /*get Main Wallet balance*/

                                        new LoginPreferences_Utility(MainActivity_utility.this).getLoggedinUser().setBalance(String.valueOf(mainBal));
                                        //txtDrawerWalltBal.setText(String.valueOf(mainBal));
                                        //textview_balance.setText(String.valueOf(mainBal));
                                        //txtActionbarWalltBal.setText(String.valueOf(mainBal));
                                    }

                                }
                                else if (Response.getRESP_VALUE().isEmpty() || Response.getRESP_VALUE().equals("")) {
                                    String toast = Response.getRESP_MSG();
                                    Toast.makeText(MainActivity_utility.this, toast, Toast.LENGTH_SHORT).show();
                                    //showSnackbar(toast);

                                }

                            }
                            else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Try Again ";
                                Toast.makeText(MainActivity_utility.this, toast, Toast.LENGTH_SHORT).show();
                                //showSnackbar(toast);
                            }

                            else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                Toast.makeText(MainActivity_utility.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MainActivity_utility.this,LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                finish();
                                //intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }
                            else {

                                String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                                Toast.makeText(MainActivity_utility.this, toast, Toast.LENGTH_SHORT).show();
                                //showSnackbar(toast);

                            }
                        }
                        else {
                            String toast = "Something went wrong..";
                            Toast.makeText(MainActivity_utility.this, toast, Toast.LENGTH_SHORT).show();
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                   /* if (refreshLayout.isRefreshing())
                        refreshLayout.setRefreshing(false);*/
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(MainActivity_utility.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private void confMenu() {
        multiLevelListView = (MultiLevelListView) findViewById(R.id.business_multiLevelMenu);

        // custom ListAdapter
        ListAdapter listAdapter = new ListAdapter();

        multiLevelListView.setAdapter(listAdapter);
        //multiLevelListView.setOnItemClickListener(mOnItemClickListener);

        listAdapter.setDataItems(CustomDataProvider.getInitialItems());
        multiLevelListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {
                String stringItem = ((BaseItem) item).getName().toString();

                //stringItem=String.valueOf(item);
                switch (stringItem) {
                    case "Home":
                        loadHomeFragment();
                        break;

                    case "Business - Dashboard":
                        clearBackStack1();
                        fragment = new BusinessHomeFragment();
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.TAG_HOME, null);
                        break;

                    case "Logout":
                        AlertDialogUtils.showDialogWithTwoButton(MainActivity_utility.this, new AlertDialogButtonListener() {
                                    @Override
                                    public void onButtontapped(String btnText) {
                                        if (btnText.equals("YES"))
                                            blankValueofSharePreference();
                                    }
                                }, "Alert!", "Are you Sure, you want to logout?", "YES", "NO"
                        );

                        break;

                    case "My Business":
                        fragment = new DashboardFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("From","Utility");
                        fragment.setArguments(bundle);
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, bundle);
                        break;


                    //Document
                    case "Welcome Letter":
                   *//* fragment = new WelcomeLetterFragment();
                    CURRENT_TAG=WELCOME_LETTER;
                    strTitle="Welcome Letter";
                    replaceFragment(fragment, CURRENT_TAG, null);*//*
                        getWelcomeLetter();
                        break;
                    case "Business Plan":
                    *//*fragment = new BusinessPlanFragment();
                    CURRENT_TAG=BUSINESS_PLAN;
                    strTitle="Business Plan";
                    replaceFragment(fragment, CURRENT_TAG, null);
                    //Call business api
                    getBusinessPlan();*//*

                        break;

                    *//*Profile Menu *//*
                    case "New Registration":
                        Intent i = new Intent(MainActivity_utility.this, RegistrationActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;

                    case "Profile":
                        Intent profile = new Intent(MainActivity_utility.this, EditProfileActivity.class);
                        startActivity(profile);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;

                    case "Change Password":
                        fragment = new ChangepasswordFragment();
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.CHANGE_PASS;
                        strTitle="Change Password";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                       // replaceFragment(fragment, CHANGE_PASS, null);
                        break;


                    case "Change Transaction Password":
                        fragment = new ChangetransactionPassword();
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.CHANGE_TRANSACTIO_PASS;
                        strTitle="Change Transaction Password";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);

                        break;

                    // Upload KYC
                    case "Address Proof":
                    *//*fragment = new AddressProofFragment();
                    //fragment = new AddressFragment();
                    CURRENT_TAG=UPLOAD_ADDRESS_PROOF;
                    strTitle="Address Proof";
                    replaceFragment(fragment, CURRENT_TAG, null);*//*
                        Intent intentAddress=new Intent(MainActivity_utility.this, AddressProofActivity.class);
                        startActivity(intentAddress);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                        break;
                    case "Bank Detail":
                    *//*CURRENT_TAG=UPLOAD_BANK_PROOF;
                    fragment=new BankProofFragment();
                    strTitle="Bank Detail";
                    replaceFragment(fragment, CURRENT_TAG, null);*//*
                        Intent bankIntent=new Intent(MainActivity_utility.this, BankProofActivity.class);
                        startActivity(bankIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;
                    case "Pan Card Detail":
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.UPLOAD_PAN_CARD_PROOF;
                        fragment=new PanCardFragment();
                        strTitle="Pan Card Detail";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                        break;

                    //WebviewFragment / My Network

                    case "My Direct"://member tree(binary tree)
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.MY_DIRECT;
                        Bundle bundle1=new Bundle();
                        fragment = new MyDirectFragment();
                        strTitle="MyDirect";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);

                   *//* Intent intent=new Intent(MainActivity_utility.this,WebActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*//*

                        break;
                    case "Geneology": //sponser geneology referal tree
                    *//*CURRENT_TAG=MY_DIRECT;
                    Bundle bundle2=new Bundle();
                    fragment=new WebviewFragment();
                    strTitle="My Directs";
                    bundle2.putString("Title","Generation View");
                    bundle2.putString("Type","reftree");
                    replaceFragment(fragment, CURRENT_TAG, bundle2);*//*
                    *//* Intent webIntent=new Intent(MainActivity_utility.this,WebActivity.class);
                    startActivity(webIntent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*//*

                        getGeneologyDetail();
                        break;

                    case "Level Wise Direct":
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.LEVEL_WISE_DIRECT_REPORT;
                        fragment = new LevelWiseFragment();
                        //fragment = new MyGroupFragment2();
                        strTitle="Level Wise Report";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                        break;

                    case "Downline Detail"://Downline Report
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.MEMBER_DOWNLINE;
                        fragment = new DownlineFragment();
                        strTitle="Member Downline";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                        break;

                    case "Downline Rank Report"://Downline Report
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.MEMBER_DOWNLINE;
                        fragment = new RankWiseDownlineReportFragment();
                        strTitle="Downline Rank Report";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                        break;


                    // E-pin Menu

                    case "Pin-Detail":
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.E_PIN;
                        fragment=new E_PinFragment();
                        strTitle="Pin-Detail";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                        break;

                    case "Pin Transfer":
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.PIN_TRANSFER;
                        fragment=new PinTransferFragment();
                        strTitle="Pin Transfer";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                        break;

                    case "Pin Transfer Detail":
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.PIN_TRANSFER_DETAIL;
                        fragment=new PinTransferDetailFragment();
                        strTitle="Pin Transfer Detail";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                        break;
                    case "Pin Receive Detail":
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.PIN_RECEIVE_DETAIL;
                        fragment=new PinReceiveDetailFragment();
                        strTitle="Pin Received Detail";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                        break;

                    case "Pin Generate":
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.PIN_GENERATE;
                        fragment=new PinGenerateFRagment();
                        strTitle="Pin Generate";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                        break;

                    *//* My Commission/ Income*//*

                    case "Weekly Incentive": //Weekly Income
                        *//*CURRENT_TAG=WEEKLY_INCENTIVE;
                        fragment = new WeeklyIncentiveFragment();
                        strTitle="Binary Income";
                        replaceFragment(fragment, CURRENT_TAG, null);*//*

                        Intent weeklyIntent=new Intent(MainActivity_utility.this, CommonReportActivity.class);
                        weeklyIntent.putExtra("Type","Weekly");
                        weeklyIntent.putExtra("Title","Weekly Incentive");
                        startActivity(weeklyIntent);
                        break;

                    case "Daily Incentive": //Monthly Income
                       *//* CURRENT_TAG=DAIRECT_INCOME;
                        fragment = new DailyIncentiveFragment();
                        strTitle="Daily Income";
                        replaceFragment(fragment, CURRENT_TAG, null);*//*

                        Intent dailyIntent=new Intent(MainActivity_utility.this, CommonReportActivity.class);
                        dailyIntent.putExtra("Type","Daily");
                        dailyIntent.putExtra("Title","Daily Payout Detail");
                        startActivity(dailyIntent);
                        break;

                    *//*Wallet*//*
                    case "My Wallet": //Main Wallet report
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.MAIN_WALLET_REPORT;
                        fragment = new WalletAndFundServiceFragment();
                        Bundle bundleWallet=new Bundle();
                        bundleWallet.putString("Service","Wallet");
                        strTitle="Main Wallet Report";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, bundleWallet);
                        break;
                    case "Fund Transfer":
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.MAIN_WALLET_REPORT;
                        fragment = new WalletAndFundServiceFragment();
                        Bundle bundleFund=new Bundle();
                        bundleFund.putString("Service","Fund");
                        strTitle="Main Wallet Report";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, bundleFund);
                        break;
                    case "Main Wallet": //Main Wallet report
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.MAIN_WALLET_REPORT;
                        fragment = new MainWalletReportFragment();
                        strTitle="Main Wallet Report";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                        break;
                    case "Service Wallet": //Shopping Wallet report
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.SHOPPING_WALLET_REPORT;
                        fragment = new ShoppingWalletReportFragment();
                        strTitle="Shopping Wallet Report";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                        break;
                    case "Wallet Request":
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.WALLET_REQUEST;
                        fragment = new WalletRequestFragment();
                        strTitle="Wallet Request";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                        break;
                    case "Wallet Request Detail":
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.WALLET_REQUEST_DETAIL;
                        fragment = new WalletRequestDetailFragment();
                        strTitle="Wallet Request Detail";
                       // replaceFragment(fragment, CURRENT_TAG, null);
                        break;

                    case "Main to Service Wallet Transfer":
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.WALLET_REQUEST_DETAIL;
                        fragment = new WalletTransferFragment();
                        strTitle="Wallet Transfer";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                        break;
                    case "Wallet Transfer":
                        Intent intent1=new Intent(MainActivity_utility.this, WalletTransferActivity.class);
                        startActivity(intent1);
                        break;
                    case "Bank Withdrawal":
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.WALLET_REQUEST_DETAIL;
                        fragment = new BankWithdrawalFragment();
                        strTitle="Bank Withdrawal";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                        break;
                    case "Bank Withdrawal Detail":
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.WALLET_REQUEST_DETAIL;
                        fragment = new BankWithdrawalReportFragment();
                        strTitle="Bank Withdrawal Detail";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                        break;

                    *//*My Shopping*//*

                    case "My Purchase Detail": //Joining Reward
                        *//*CURRENT_TAG=FREE_PRODUCT_VOUCHER;
                        fragment = new MyPurchaseDetailFragment();
                        strTitle="Free Product Voucher";
                        replaceFragment(fragment, CURRENT_TAG, null);*//*

                        Intent myPurchaseIntent=new Intent(MainActivity_utility.this, CommonReportActivity.class);
                        myPurchaseIntent.putExtra("Type","MyPurchase");
                        myPurchaseIntent.putExtra("Title","My Purchase Detail");
                        startActivity(myPurchaseIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;
                    case "Downline Purchase": //Joining Reward
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.FREE_PRODUCT_VOUCHER;
                        fragment = new DownlinePurchaseFragment();
                        strTitle="Downline Purchase";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);


                        break;


                    //Complaint
                    case "Raise Ticket"://Complaint
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.COMPLAINT;
                        Intent intent=new Intent(MainActivity_utility.this, ComplaintActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;
                    case "My Ticket Status"://Complaint Detail
                        in.discountmart.business.business_constants.AppConstants.CURRENT_TAG= in.discountmart.business.business_constants.AppConstants.COMPLAINT_DETAIL;
                        fragment=new ComplaintDetailFragment();
                        strTitle="Complaint Detail";
                        replaceFragment(fragment, in.discountmart.business.business_constants.AppConstants.CURRENT_TAG, null);
                        break;


                    default:
                        fragment=new BusinessHomeFragment();
                        //replaceFragment(fragment, CURRENT_TAG, null);
                        if(drawer.isDrawerOpen(GravityCompat.START)){
                            drawer.closeDrawer(GravityCompat.START);
                        }

                        break;


                }
                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START);
                }

            }

            @Override
            public void onGroupItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {

            }

        });

    *//*profilepic.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Fragment fragment1 = new UpdateprofilepicFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            replaceFragment(fragment1,AppConstants.UPLOAD_PROFILE,null);
            fragmentTransaction.commit();
            drawer.closeDrawer(GravityCompat.START);
        }
    });*//*
        //onBackPressed();

    *//*private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        private void showItemDescription(Object object, ItemInfo itemInfo) {
            StringBuilder builder = new StringBuilder("\"");
            builder.append(((BaseItem) object).getName());
            builder.append("\" clicked!\n");
            builder.append(getItemInfoDsc(itemInfo));

            Toast.makeText(MainActivity_utility.this, builder.toString(), Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {
            //showItemDescription(item, itemInfo);
            String stringItem = ((BaseItem) item).getName().toString();

            //stringItem=String.valueOf(item);

        }

        @Override
        public void onGroupItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {
            //showItemDescription(item, itemInfo);
            //String stringGroupItem= (String) item;
            //Toast.makeText(MainActivity_utility.this,stringGroupItem,Toast.LENGTH_SHORT).show();


        }


    };*//*

    }*/

    /*private class ListAdapter extends MultiLevelListAdapter {

        private class ViewHolder {
            TextView nameView;
            TextView infoView;
            ImageView arrowView;
            LevelBeamView levelBeamView;
        }

        @Override
        public List<?> getSubObjects(Object object) {
            // DIEKSEKUSI SAAT KLIK PADA GROUP-ITEM
            //return CustomDataProvider.getSubItems((BaseItem) object);
            new CustomDataProvider(MainActivity_utility.this);
            return CustomDataProvider.getSubItems((BaseItem)object);
        }

        @Override
        public boolean isExpandable(Object object) {
            //return CustomDataProvider.isExpandable((BaseItem) object);
            new CustomDataProvider(MainActivity_utility.this);
            return CustomDataProvider.isExpandable((BaseItem)object);
        }

        @Override
        public View getViewForObject(Object object, View convertView, ItemInfo itemInfo) {
            MainActivity_utility.ListAdapter.ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new MainActivity_utility.ListAdapter.ViewHolder();
                convertView = LayoutInflater.from(MainActivity_utility.this).inflate(R.layout.expendablelist_data_item, null);
                //viewHolder.infoView = (TextView) convertView.findViewById(R.id.dataItemInfo);
                viewHolder.nameView = (TextView) convertView.findViewById(R.id.dataItemName);
                viewHolder.arrowView = (ImageView) convertView.findViewById(R.id.dataItemArrow);
                viewHolder.levelBeamView = (LevelBeamView) convertView.findViewById(R.id.dataItemLevelBeam);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (MainActivity_utility.ListAdapter.ViewHolder) convertView.getTag();
            }

            viewHolder.nameView.setText(((BaseItem) object).getName());
            //viewHolder.infoView.setText(getItemInfoDsc(itemInfo));

            if (itemInfo.isExpandable()) {
                viewHolder.arrowView.setVisibility(View.VISIBLE);
                viewHolder.arrowView.setImageResource(itemInfo.isExpanded() ?
                        R.drawable.ic_expand_less : R.drawable.ic_expand_more);
            } else {
                viewHolder.arrowView.setVisibility(View.GONE);
            }

            viewHolder.levelBeamView.setLevel(itemInfo.getLevel());

            return convertView;
        }
    }*/
    
    //////////
    /*private void getWelcomeLetter(){

        pDialog=new ProgressDialog(this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest baseRequest=new BaseRequest();
        *//*Set value in Entity class*//*
        baseRequest.setReqtype(ApiConstants.REQUEST_WELCOME_LETTER);

        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(this));

        Call<WelcomeLetterResponse> geneologyResponseCall=
                NetworkClient.getInstance(this).create(DocumentService.class).fetchWelcomeLetter(baseRequest);

        geneologyResponseCall.enqueue(new Callback<WelcomeLetterResponse>() {
            @Override
            public void onResponse(Call<WelcomeLetterResponse> call, Response<WelcomeLetterResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {
                    WelcomeLetterResponse geneologyResponse=new WelcomeLetterResponse();
                    geneologyResponse=response.body();
                    if (geneologyResponse.getResponse().equals("OK")){
                        Intent i = new Intent(MainActivity_utility.this, WebViewActivity.class);
                        i.putExtra("URL", geneologyResponse.getUrl());
                        i.putExtra("Type","Welcome Letter");
                        i.putExtra("From","Utility");
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    }
                    else{
                        Toast.makeText(MainActivity_utility.this, geneologyResponse.getResponse(), Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<WelcomeLetterResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(MainActivity_utility.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }*/

    /*Geneology  API*/
   /* private void getGeneologyDetail(){

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest baseRequest=new BaseRequest();
        *//*Set value in Entity class*//*
        baseRequest.setReqtype(ApiConstants.REQUEST_GENEOLOGY);

        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(this));

        Call<GeneologyResponse> geneologyResponseCall=
                NetworkClient.getInstance(this).create(MyTeamService.class).fetchGeneology(baseRequest);

        geneologyResponseCall.enqueue(new Callback<GeneologyResponse>() {
            @Override
            public void onResponse(Call<GeneologyResponse> call, Response<GeneologyResponse> response) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                try {
                    GeneologyResponse geneologyResponse=new GeneologyResponse();
                    geneologyResponse=response.body();
                    if (geneologyResponse.getResponse().equals("OK")){
                        Intent i = new Intent(MainActivity_utility.this, WebViewActivity.class);
                        i.putExtra("URL", geneologyResponse.getUrl());
                        i.putExtra("Type","Geneology");
                        i.putExtra("From","Utility");
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    }
                    else{
                        String toast= geneologyResponse.getResponse();
                        Toast.makeText(MainActivity_utility.this, toast, Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GeneologyResponse> call, Throwable t) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(MainActivity_utility.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
    }



   /* public void showComplaintDetalViewDialog(String strComplaintId) {
        ComplaintDetailViewFragment messageReplyDialog = new ComplaintDetailViewFragment();

        Bundle bundle = new Bundle();
        bundle.putString(in.discountmart.business.business_constants.AppConstants.EXTRA_STIRNG_COMPLAINT_ID, strComplaintId);


        messageReplyDialog.setArguments(bundle);
        messageReplyDialog.setRetainInstance(true);

        messageReplyDialog.show(this.getSupportFragmentManager(), in.discountmart.business.business_constants.AppConstants.COMPLAINT_DETAIL_VIEW);
    }*/

    /*Business plan Letter API*/
   /* private void getBusinessPlan(){

        pDialog=new ProgressDialog(this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.isShowing();
        BaseRequest baseRequest=new BaseRequest();
        *//*Set value in Entity class*//*
        baseRequest.setReqtype(ApiConstants.REQUEST_BUSINESS_PLAN);

        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(this));

        Call<WelcomeLetterResponse> geneologyResponseCall=
                NetworkClient.getInstance(this).create(DocumentService.class).fetchWelcomeLetter(baseRequest);

        geneologyResponseCall.enqueue(new Callback<WelcomeLetterResponse>() {
            @Override
            public void onResponse(Call<WelcomeLetterResponse> call, Response<WelcomeLetterResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {
                    WelcomeLetterResponse geneologyResponse=new WelcomeLetterResponse();
                    geneologyResponse=response.body();
                    if (geneologyResponse.getResponse().equals("OK")){

                        *//*File file = new File(Environment.getExternalStorageDirectory(),
                        "Report.pdf");*//*
                        String file=geneologyResponse.getUrl().toString();
                        Uri path = Uri.parse(file);
                        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pdfOpenintent.setDataAndType(path, "application/pdf");
                        try {
                            startActivity(pdfOpenintent);
                        }
                        catch (ActivityNotFoundException e) {
                            Toast.makeText(MainActivity_utility.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(MainActivity_utility.this, geneologyResponse.getResponse(), Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<WelcomeLetterResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(MainActivity_utility.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }*/

    /*@Override
    protected void onResume() {
        super.onResume();
        checkNewAppVersionState();
    }*/
    @Override
    protected void onDestroy() {
        unregisterInstallStateUpdListener();
        super.onDestroy();
    }
   /* @Override
    public void onStart(){
        super.onStart();
        checkNewAppVersionState();
    }*/

    /*this method check for
     * new update available*/
    public void checkForAppUpdate() {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(this);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Create a listener to track request state updates.
        /*installStateUpdatedListener = new InstallStateUpdatedListener() {
            @Override
            public void onStateUpdate(InstallState installState) {
                // Show module progress, log state, or install the update.
                if (installState.installStatus() == InstallStatus.DOWNLOADED)
                    // After the update is downloaded, show a notification
                    // and request user confirmation to restart the app.
                    popupSnackbarForCompleteUpdateAndUnregister();
            }
        };*/

        // Checks that the platform will allow the specified type of update.
        // for immediate update
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                startAppUpdateImmediate(appUpdateInfo);
            }
        });

       /* appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                // Request the update.
                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

                    // Before starting an update, register a listener for updates.
                    appUpdateManager.registerListener(installStateUpdatedListener);
                    // Start an update.
                    startAppUpdateFlexible(appUpdateInfo);
                }
                else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) ) {
                    // Start an update.
                    startAppUpdateImmediate(appUpdateInfo);
                }
            }

        });*/


    }

    private void startAppUpdateImmediate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void startAppUpdateFlexible(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            unregisterInstallStateUpdListener();
        }
    }

    /**
     * Displays the snackbar notification and call to action.
     * Needed only for Flexible app update
     */
    private void popupSnackbarForCompleteUpdateAndUnregister() {
        Snackbar snackbar = Snackbar.make(view, "Update App", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Restart", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();

        unregisterInstallStateUpdListener();
    }

    /**
     * Checks that the update is not installed during 'onResume()'.
     * However, you should execute this check at all app entry points.
     */
    public void checkNewAppVersionState() {
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {
                            //FLEXIBLE:
                            // If the update is downloaded but not installed,
                            // notify the user to complete the update.
                           /* if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                                popupSnackbarForCompleteUpdateAndUnregister();
                            }*/

                            //IMMEDIATE:
                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                startAppUpdateImmediate(appUpdateInfo);
                            }
                        });




    }

    /**
     * Needed only for FLEXIBLE update
     */
    private void unregisterInstallStateUpdListener() {
        if (appUpdateManager != null && installStateUpdatedListener != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
    }

    /*private void getDashboardDetail(){

        pDialog=new ProgressDialog(MainActivity_utility.this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest request=new BaseRequest();
        *//*Set value in Entity class*//*
        request.setReqtype(ApiConstants.REQUEST_DASHBOARD);
        request.setPasswd(SharedPrefrence_Business.getPassword(MainActivity_utility.this));
        request.setUserid(SharedPrefrence_Business.getUserID(MainActivity_utility.this));

        Call<DashboardResponse> callAddressDetail=
                NetworkClient1.getInstance(MainActivity_utility.this).create(ProfileServices.class).fetchDashboardDetail(request);

        callAddressDetail.enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, retrofit2.Response<DashboardResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    Response =new DashboardResponse();
                    Response=response.body();

                    if(Response != null){
                      *//* if( Response.getMyteam() != null){

                       }
                       else {
                           String toast="No record found";
                           //Toast.makeText(MainActivity_utility.this,toast,Toast.LENGTH_SHORT).show();
                       }

                        if(Response.getRank() != null){
                            if(Response.getRank().getRank().equals(""))
                                txtRank.setVisibility(View.GONE);
                            else {
                                txtRank.setVisibility(View.VISIBLE);
                                txtRank.setText(Response.getRank().getRank());
                            }
                        }*//*
                    }
                    else {
                        String toast="Something wrong..server error";
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
            public void onFailure(Call<DashboardResponse> call, Throwable t) {

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
    }*/


    /*For Profile Pic change*/
    /*Update Pan Card Detail Api Request and Response*/
    /*private void UpdateProfileImage(String strfile){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        final UploadPanCardRequest Request = new UploadPanCardRequest();
        *//*Set value in Entity class*//*
        Request.setReqtype(ApiConstants.REQUEST_PROFILE_PIC);
        Request.setPasswd(SharedPrefrence_Business.getPassword(this));
        Request.setUserid(SharedPrefrence_Business.getUserID(this));

        File file1=new File(ImageUtil.compressImage(strfile));
        String str1="";
        str1=file1.getPath();

        MultipartBody.Part body;

        if(!str1.equals("")){
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file1);
            body = MultipartBody.Part.createFormData("file:", file1.getName(), reqFile);
            // RequestBody request = RequestBody.create(MediaType.parse("text/plain"), new Gson().toJson(Request));
            RequestBody request = RequestBody.create(MultipartBody.FORM, new Gson().toJson(Request));

            Call<ProfileImageResponse> callUpdatePanDetail=
                    NetworkClient.getInstance(this).create(ProfileServices.class).fetchProfileImageMultipart(body,request);

            callUpdatePanDetail.enqueue(new Callback<ProfileImageResponse>() {
                @Override
                public void onResponse(Call<ProfileImageResponse> call, Response<ProfileImageResponse> response) {
                    if(pDialog.isShowing())
                        pDialog.dismiss();
                    try {

                        ProfileImageResponse Response =new ProfileImageResponse();
                        Response=response.body();

                        if (Response.getResponse().equals("OK")) {

                            String toast= Response.getResponse()+ ": Upload Sucesfully" ;
                            Toast.makeText(MainActivity_utility.this, toast, Toast.LENGTH_SHORT).show();
                            SharedPrefrence_Business.setProfileIamge(MainActivity_utility.this,Response.getProfilepic());

                            if(Response.getProfilepic().equals("")){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    Picasso.with(MainActivity_utility.this)
                                            .load(String.valueOf(MainActivity_utility.this.getDrawable(R.mipmap.logo)))
                                            .placeholder(R.mipmap.logo)
                                            .error(R.mipmap.logo)
                                            .into(profilepic);

                        *//*Glide.with(mContext)  //2
                                .load(String.valueOf(mContext.getDrawable(R.mipmap.home_icon_hotel))) //3
                                .centerCrop() //4
                                .placeholder(R.mipmap.home_icon_hotel) //5
                                .error(R.mipmap.home_icon_hotel) //6
                                .fallback(R.mipmap.home_icon_hotel) //7
                                .into(myViewHolder.imgHotelImg);*//* //8
                                }
                            }

                            else{

                                Picasso.with(MainActivity_utility.this)
                                        .load(Response.getProfilepic())
                                        .placeholder(R.mipmap.logo)
                                        .error(R.mipmap.logo)
                                        .into(profilepic);

                   *//* Glide.with(mContext)  //2
                            .load(hotelSearchList.get(position).getHotelPicture()) //3
                            //.centerCrop() //4
                            .placeholder(R.mipmap.home_icon_hotel) //5
                            .error(R.mipmap.home_icon_hotel) //6
                            .fallback(R.mipmap.home_icon_hotel) //7
                            .into(myViewHolder.imgHotelImg); //8*//*
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
                public void onFailure(Call<ProfileImageResponse> call, Throwable t) {
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
            Toast.makeText(MainActivity_utility.this,"File not Select", Toast.LENGTH_SHORT).show();
        }


    }*/

    //image upload
   /* public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }*/
  /*  private File getOutputMediaFile(int type) {

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
    }*/

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        try {
            int compressionRatio=4;

            // When an Image is
          /*  if (requestCode == 2) {
                in.discountmart.business.business_constants.AppConstants.imgpath="";

                if (resultCode == this.RESULT_OK) {

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // down sizing image as it throws OutOfMemory Exception for larger images
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(in.discountmart.business.business_constants.AppConstants.Uri,options);
                    //bitmap.compress(Bitmap.CompressFormat.JPEG,2, new FileOutputStream(AppConstants.Uri));


                    //textViewDocPath.setText(AppConstants.Uri);
                    in.discountmart.business.business_constants.AppConstants.ImageUri= in.discountmart.business.business_constants.AppConstants.Uri;
                    profilepic.setImageBitmap(bitmap);

                    *//*Call method for upload image*//*
                    uploadAttachment();


                } else if (resultCode ==this.RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(MainActivity_utility.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();

                } else {
                    // failed to capture image
                    Toast.makeText(MainActivity_utility.this, "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
                }


            }*/
            // When an Image is picked from gallery

           /* else if(requestCode == 1){

                in.discountmart.business.business_constants.AppConstants.Uri="";
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

                    // textViewDocPath.setText(imgPath);
                    profilepic.setImageBitmap(bitmap);

                    in.discountmart.business.business_constants.AppConstants.imgpath = imgPath;
                    in.discountmart.business.business_constants.AppConstants.ImageUri=imgPath;

                    //call methodupload image
                    uploadAttachment();

                } else if (resultCode == this.RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(MainActivity_utility.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();
                }

            }*/

            if (requestCode == REQ_CODE_VERSION_UPDATE) {
                if (resultCode != RESULT_OK) {
                   //Log.e ("Update flow failed! Result code: " + resultCode);
                   Log.e("Update flow failed! " ,String.valueOf(resultCode));
                    // If the update is cancelled or fails,
                    // you can request to start the update again.
                    checkForAppUpdate();
                }
            }
        }catch (Exception e) {
            Toast.makeText(MainActivity_utility.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    // When Consult button is clicked
   /* public void uploadAttachment() {

        if (in.discountmart.business.business_constants.AppConstants.Uri != null && in.discountmart.business.business_constants.AppConstants.Uri != "") {
            // When Image is capturted from Camera
            sendImageUploadRequest(in.discountmart.business.business_constants.AppConstants.Uri);
        } else if (in.discountmart.business.business_constants.AppConstants.imgpath != null && !in.discountmart.business.business_constants.AppConstants.imgpath.isEmpty()) {
            // When Image is selected from Gallery
            sendImageUploadRequest(in.discountmart.business.business_constants.AppConstants.imgpath);
        }
        else {
            Toast.makeText(MainActivity_utility.this, "NO FILE SELECTED TO UPLOAD", Toast.LENGTH_LONG).show();
        }
    }*/

    /*private void sendImageUploadRequest(final String filePath) {
        Handler mHandler = new Handler(this.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                UploadImageHandler1 uploadImageHandler = new UploadImageHandler1(filePath);
//
//                String stringParameter=sendUpdatPanCardDetailRequest();
//                uploadImageHandler.execute(ApiConstants.Baseurl, stringParameter);
                //uploadImageHandler.execute(AppConstants.uploadProfileImage, "");
                UpdateProfileImage(filePath);

            }
        });
    }*/


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(this, CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
       // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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


}
