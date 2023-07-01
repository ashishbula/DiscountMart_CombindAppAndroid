package in.discountmart.business.activity;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static in.discountmart.business.business_constants.AppConstants.CHANGE_PASS;
import static in.discountmart.business.business_constants.AppConstants.CHANGE_TRANSACTIO_PASS;
import static in.discountmart.business.business_constants.AppConstants.COMPLAINT;
import static in.discountmart.business.business_constants.AppConstants.COMPLAINT_DETAIL;
import static in.discountmart.business.business_constants.AppConstants.CURRENT_TAG;
import static in.discountmart.business.business_constants.AppConstants.E_PIN;
import static in.discountmart.business.business_constants.AppConstants.FREE_PRODUCT_VOUCHER;
import static in.discountmart.business.business_constants.AppConstants.LEVEL_WISE_DIRECT_REPORT;
import static in.discountmart.business.business_constants.AppConstants.MAIN_WALLET_REPORT;
import static in.discountmart.business.business_constants.AppConstants.MEMBER_DOWNLINE;
import static in.discountmart.business.business_constants.AppConstants.MY_DIRECT;
import static in.discountmart.business.business_constants.AppConstants.PIN_RECEIVE_DETAIL;
import static in.discountmart.business.business_constants.AppConstants.PIN_TRANSFER;
import static in.discountmart.business.business_constants.AppConstants.PIN_TRANSFER_DETAIL;
import static in.discountmart.business.business_constants.AppConstants.REWARDS;
import static in.discountmart.business.business_constants.AppConstants.UPLOAD_PAN_CARD_PROOF;
import static in.discountmart.business.business_constants.AppConstants.WEEKLY_INCENTIVE;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
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
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.base.network.NetworkClient;
import in.base.network.NetworkClient1;
import in.base.ui.BaseActivity;
import in.base.util.ImageUtil;
import in.discountmart.R;
import in.discountmart.activity.DashboardActivity;
import in.discountmart.activity.LoginActivity;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.business_constants.AppConstants;
import in.discountmart.business.call_api.DocumentService;
import in.discountmart.business.call_api.MyTeamService;
import in.discountmart.business.call_api.ProfileServices;
import in.discountmart.business.data.BaseItem;
import in.discountmart.business.data.CustomDataProvider;
import in.discountmart.business.fragment.BusinessHomeFragment;
import in.discountmart.business.fragment.ChangepasswordFragment;
import in.discountmart.business.fragment.ChangetransactionPassword;
import in.discountmart.business.fragment.ComplaintDetailFragment;
import in.discountmart.business.fragment.ComplaintDetailViewFragment;
import in.discountmart.business.fragment.DashboardFragment;
import in.discountmart.business.fragment.DownlineFragment;
import in.discountmart.business.fragment.DownlinePurchaseFragment;
import in.discountmart.business.fragment.E_PinFragment;
import in.discountmart.business.fragment.LevelWiseDirectFragment;
import in.discountmart.business.fragment.MyBusinessFragment;
import in.discountmart.business.fragment.MyDirectFragment;
import in.discountmart.business.fragment.MyPurchaseDetailFragment;
import in.discountmart.business.fragment.PanCardFragment;
import in.discountmart.business.fragment.PinReceiveDetailFragment;
import in.discountmart.business.fragment.PinTransferDetailFragment;
import in.discountmart.business.fragment.PinTransferFragment;
import in.discountmart.business.fragment.RewardFragment;
import in.discountmart.business.fragment.SubServiceFragment;
import in.discountmart.business.fragment.WalletTransferFragment;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.UploadImageRequest;
import in.discountmart.business.model_business.responsemodel.DashboardResponse;
import in.discountmart.business.model_business.responsemodel.GeneologyResponse;
import in.discountmart.business.model_business.responsemodel.UploadImageResponse;
import in.discountmart.business.model_business.responsemodel.WelcomeLetterResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.view.LevelBeamView;
import in.discountmart.listener.AlertDialogButtonListener;
import in.discountmart.shared_pref_business.SharedPrefrence;
import in.discountmart.utility.AlertDialogUtils;
import in.discountmart.utility_services.model.SettingModel;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.sharedpreferences.SettingPreference;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.openrnd.multilevellistview.ItemInfo;
import pl.openrnd.multilevellistview.MultiLevelListAdapter;
import pl.openrnd.multilevellistview.MultiLevelListView;
import pl.openrnd.multilevellistview.OnItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class BusinessDashboardActivity extends BaseActivity {

    private MultiLevelListView multiLevelListView;
    ImageView imageViewProfilePic;

    TextView textViewUserId;
    TextView textViewUserName;
    TextView txtRank;
    LinearLayout layoutBottomHome;
    LinearLayout layoutBottomNews;
    LinearLayout layoutBottomTrans;
    LinearLayout layoutBottomDetail;
    Fragment fragment=null;
    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawer;
    //String imgPath, fileName, profilepicpath;

    //RequestParams params = new RequestParams();
    ImageView profilepic;
    ImageView imgEditPic;
    BottomNavigationView bottomNavigationView;
    String strTitle="";
    ProgressDialog pDialog;
    View headerView;
    View view;

    /*For auto update*/
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;

    //For image upload
    private Dialog uploadOptionDialog;
    private static int RESULT_LOAD_IMG = 1;
    private static int RESULT_CLICK_IMG = 2;
    private Uri fileUri;
    private Uri fileUri2;
    private static final int REQUEST_CODE2 = 11;
    private static final int REQUEST_CODE = 0x11;
    private static final int PERMISSION_REQUEST_CODE = 200;
    String imgPath;
    String strRefLink="";
    String strApiKey="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_activity_dashboard);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        try {

            view=findViewById(android.R.id.content);
            navigationView=(NavigationView)findViewById(R.id.business_nav_view) ;
            headerView=navigationView.getHeaderView(0);
            //imageViewProfilePic=(ImageView)findViewById(R.id.imageView);
            profilepic=(ImageView)findViewById(R.id.bus_homedash_act_img_profileImg);
            imgEditPic=(ImageView) findViewById(R.id.bus_homedash_act_img_editimage);

            textViewUserId=(TextView)findViewById(R.id.bus_homedash_act_txt_userId);
            textViewUserName=(TextView)findViewById(R.id.bus_homedash_act_txt_UserName);
            txtRank=(TextView)findViewById(R.id.bus_homedash_act_txt_rank);
            toolbar = (Toolbar) findViewById(R.id.business_app_bar_main_toolbar);
            layoutBottomHome=(LinearLayout)findViewById(R.id.homedash_bottomnav_home);
            layoutBottomNews=(LinearLayout)findViewById(R.id.homedash_bottomnav_news);
            layoutBottomTrans=(LinearLayout)findViewById(R.id.homedash_bottomnav_trans);
            layoutBottomDetail=(LinearLayout)findViewById(R.id.homedash_bottomnav_detail);
            //toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
            setSupportActionBar(toolbar);
            //toolbar.setTitle("VISION ROOTS");
            //getSupportActionBar().setDisplayShowTitleEnabled(true);

            //getSupportActionBar().setTitle("VISION ROOTS");
            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(this);

            textViewUserId.setText(SharedPrefrence_Business.getUserID(getApplicationContext()).toUpperCase());
            textViewUserName.setText(SharedPrefrence_Business.getUsername(getApplicationContext()));
            txtRank.setText(SharedPrefrence_Business.getPackage(getApplicationContext()));
            String fren= SharedPrefrence_Business.getIsFrenchise(getApplicationContext());
            String cop= SharedPrefrence_Business.getCouponRequest(getApplicationContext());
           /* Get Shared Preference value
               and Set Profile Pic*/
            if(SharedPrefrence_Business.getProfileIamge(this).equals("")){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Picasso.with(this)
                            .load(String.valueOf(this.getDrawable(R.mipmap.profile_icon)))
                            .placeholder(R.mipmap.profile_icon)
                            .error(R.mipmap.profile_icon)
                            .into(profilepic);

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

                Picasso.with(this)
                        .load(SharedPrefrence_Business.getProfileIamge(BusinessDashboardActivity.this))
                        .placeholder(R.mipmap.profile_icon)
                        .error(R.mipmap.profile_icon)
                        .into(profilepic);

                   /* Glide.with(mContext)  //2
                            .load(hotelSearchList.get(position).getHotelPicture()) //3
                            //.centerCrop() //4
                            .placeholder(R.mipmap.home_icon_hotel) //5
                            .error(R.mipmap.home_icon_hotel) //6
                            .fallback(R.mipmap.home_icon_hotel) //7
                            .into(myViewHolder.imgHotelImg); //8*/
            }
            drawer = (DrawerLayout) findViewById(R.id.business_drawer);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            //toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            /* Call Method for show
            navigation drawer menu item in list*/
            confMenu();

            /*Call method for check is
            * app updated is available or not*/
           // checkForAppUpdate();

            /*Call method for load home dashboard defult*/
            loadHomeFragment_Business();

            /*Bottom Services on click*/
            //Home
           /* layoutBottomHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadHomeFragment();
                }
            });*/
            /*news on click*/
            /*layoutBottomNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
            //        //replaceFragment(new NewsFragment(), AppConstants.NEWS_EVENTS,null);
                }
            });*/
            /*Transaction*/
           /* layoutBottomTrans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //replaceFragment(new MainWalletReportFragment(), AppConstants.MAIN_WALLET_REPORT,null);

                }
            });*/
            /*View Detail*/
           /* layoutBottomDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // replaceFragment(new PinFragment(),"Pin Detail",null);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_from_right);
                }
            });*/

            /*Navigation Header
             * profile picture change
             * and Editable*/
            imgEditPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission()) {

                        //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(BusinessDashboardActivity.this);
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

            bottomNavigationView = (BottomNavigationView) findViewById(R.id.bus_homedash_navigation_bottom);

            bottomNavigationView.setOnNavigationItemSelectedListener
                    (new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            Fragment selectedFragment = null;
                            Bundle bundle = new Bundle();
                            switch (item.getItemId()) {
                                case R.id.bus_bottom_nav_action_home:
                                  // loadHomeFragment();
                                    Intent mainIntent = new Intent(BusinessDashboardActivity.this, BusinessDashboardActivity.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                    startActivity(mainIntent);
                                    finish();

                                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
                                    break;
                              /*  case R.id.bottom_nav_action_addfund:
                                    Intent intentFund = new Intent(BusinessDashboardActivity.this, ReportListActivity.class);
                                    intentFund.putExtra("Report", TAG_FUND);
                                    startActivity(intentFund);
                                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                                    break;*/

                                case R.id.bus_bottom_nav_action_wallet:
                                    /*Intent walletIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                                    walletIntent.putExtra("Title", "Shopping Wallet" );
                                    walletIntent.putExtra("Type","R");
                                    startActivity(walletIntent);
                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/

                                    SubServiceFragment fragmentW = new SubServiceFragment();
                                    // fragment = new WalletReportFragment();
                                    String strTitle="Main Wallet Report";
                                    Bundle walletBundle=new Bundle();
                                    walletBundle.putString("From","Act");
                                    walletBundle.putString("Service","Wallet");
                                    fragmentW.setArguments(walletBundle);
                                    replaceFragment_business(fragmentW,"Wallet Report",walletBundle);

                                    break;

                                case R.id.bus_bottom_nav_action_income:
                                    Intent dailyIncIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                                    dailyIncIntent.putExtra("Title", "Incentive Detail" );
                                    dailyIncIntent.putExtra("Type","Daily");
                                    startActivity(dailyIncIntent);
                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                                    //Toast.makeText(BusinessDashboardActivity.this,"Coming soon..",Toast.LENGTH_SHORT).show();

                                    break;
                              /*  case R.id.bottom_nav_action_report:
                                    Intent intentReport = new Intent(BusinessDashboardActivity.this, ReportListActivity.class);
                                    intentReport.putExtra("Report", TAG_REPORT);
                                    startActivity(intentReport);
                                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);*/

                                case R.id.bus_bottom_nav_action_share:
                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                    shareIntent.setType("text/plain");
                                    String sponsor= "Sponser ID :- "+SharedPrefrence_Business.getUserID(BusinessDashboardActivity.this);
                                    String string = "\nGet Business Marketing ZaraDoBit App\n" + "Visit us at\n\n";
                                    string = string + "https://play.google.com/store/apps/details?id=" + BusinessDashboardActivity.this.getPackageName() + "\n\n"+sponsor;

                                    shareIntent.putExtra(Intent.EXTRA_TEXT, string);
                                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "ZaraDoBit App");
                                    startActivity(Intent.createChooser(shareIntent, "SHARE_APP"));
                                    break;
                                case R.id.bus_bottom_nav_action_support:
                                    Intent ComplaintIntent = new Intent(BusinessDashboardActivity.this, ComplaintActivity.class);
                                    //intentComplaint.putExtra("Report", TAG_REPORT);
                                    startActivity(ComplaintIntent);
                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                                    break;

                            }
                            return true;
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        //userInfo = new com.dreamtouchglobal.dtg.utility.LoginPrefrencesKeys(this).getLoggedinUser();
        //navigationView = (NavigationView) findViewById(R.id.nav_view);
        //Login_Logout();
        //fillUserInfo();
       // checkNewAppVersionState();
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.business_drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
    }

    private void confMenu() {
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
                        fragment = new BusinessHomeFragment();
                        loadHomeFragment_Business();
                        break;
                    case "Referral Link":
                       //getDashboardDetail();
                        break;
                    case "Dashboard":
                        fragment = new DashboardFragment();
                        CURRENT_TAG="DASHBOARD";
                        strTitle="DASHBOARD";
                        replaceFragment_business(fragment, CURRENT_TAG, null);
                        break;


                //Document
                    case "Welcome Letter":
                       /* fragment = new WelcomeLetterFragment();
                        CURRENT_TAG=WELCOME_LETTER;
                        strTitle="Welcome Letter";
                        replaceFragment(fragment, CURRENT_TAG, null);*/
                       getWelcomeLetter();
                        break;
                    case "Invoice":
                        //fragment = new BusinessPlanFragment();
                        //CURRENT_TAG=BUSINESS_PLAN;
                        //strTitle="Business Plan";
                        //replaceFragment(fragment, CURRENT_TAG, null);
                        //Call business api
                        //getBusinessPlan();
                        getTreeDetail("Invoice");

                        break;

                /*Profile Menu */
                    case "New Registration":
                        Intent i = new Intent(BusinessDashboardActivity.this, RegistrationActivity.class);
                        i.putExtra("From","Business");
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;

                    case "Profile":
                        Intent profile = new Intent(BusinessDashboardActivity.this, EditProfileActivity.class);
                        startActivity(profile);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;

                    case "Upload KYC":
                        Intent kyc = new Intent(BusinessDashboardActivity.this, Kyc_Activity.class);
                        startActivity(kyc);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;

                    case "Change Password":
                        fragment = new ChangepasswordFragment();
                        CURRENT_TAG=CHANGE_PASS;
                        strTitle="Change Password";
                        //replaceFragment(fragment, CURRENT_TAG, null);
                        replaceFragment_business(fragment, CHANGE_PASS, null);
                        break;


                    case "Change Transaction Password":
                        fragment = new ChangetransactionPassword();
                        CURRENT_TAG=CHANGE_TRANSACTIO_PASS;
                        strTitle="Change Transaction Password";
                        replaceFragment_business(fragment, CURRENT_TAG, null);

                        break;

                // Upload KYC
                    case "Address Proof":
                        /*fragment = new AddressProofFragment();
                        //fragment = new AddressFragment();
                        CURRENT_TAG=UPLOAD_ADDRESS_PROOF;
                        strTitle="Address Proof";
                        replaceFragment(fragment, CURRENT_TAG, null);*/
                        Intent intentAddress=new Intent(BusinessDashboardActivity.this, AddressProofActivity.class);
                        //startActivity(intentAddress);
                        //overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                        break;
                    case "Bank Detail":
                        /*CURRENT_TAG=UPLOAD_BANK_PROOF;
                        fragment=new BankProofFragment();
                        strTitle="Bank Detail";
                        replaceFragment(fragment, CURRENT_TAG, null);*/
                        //Intent bankIntent=new Intent(BusinessDashboardActivity.this,BankProofActivity.class);
                        //startActivity(bankIntent);
                        //overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;
                    case "Pan Card Detail":
                        CURRENT_TAG=UPLOAD_PAN_CARD_PROOF;
                        fragment=new PanCardFragment();
                        strTitle="Pan Card Detail";
                        //replaceFragment(fragment, CURRENT_TAG, null);
                        break;

                //WebviewFragment / My Network// Team

                    case "Team Detail"://member tree(binary tree)
                        CURRENT_TAG=MY_DIRECT;
                        Bundle bundle1=new Bundle();
                        fragment = new LevelWiseDirectFragment();
                        strTitle="MyDirect";
                        replaceFragment_business(fragment, CURRENT_TAG, null);
                        break;

                    case "Generation View"://member tree(binary tree)
                       getTreeDetail("Generation View");
                        break;
                    case "Global Pool"://member tree(binary tree)
                        getTreeDetail("Global Pool");
                        break;

                    case "ID Activation":
                        Intent activationIntent = new Intent(BusinessDashboardActivity.this, IDActivationActivity.class);
                         startActivity(activationIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;//Id Activation/Upgrade

                    case "Level Wise Count":
                        Intent leveCountIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        leveCountIntent.putExtra("Type","LevelWise");
                        leveCountIntent.putExtra("Title","Level Wise Count Report");
                        startActivity(leveCountIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;//Id Activation/Upgrade
                    case "My Direct"://member tree(binary tree)
                        CURRENT_TAG=MY_DIRECT;
                        //Bundle bundle1=new Bundle();
                        fragment = new MyDirectFragment();
                        strTitle="MyDirect";
                        replaceFragment_business(fragment, CURRENT_TAG, null);
                        //getTreeDetail("My Direct");

                       /* Intent intent=new Intent(DashboardActivity.this,WebActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/

                        break;
                    case "Geneology": //sponser geneology referal tree
                        /*CURRENT_TAG=MY_DIRECT;
                        Bundle bundle2=new Bundle();
                        fragment=new WebviewFragment();
                        strTitle="My Directs";
                        bundle2.putString("Title","Generation View");
                        bundle2.putString("Type","reftree");
                        replaceFragment(fragment, CURRENT_TAG, bundle2);*/
                        /* Intent webIntent=new Intent(DashboardActivity.this,WebActivity.class);
                        startActivity(webIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/


                         getTreeDetail("Geneology");
                        break;

                    case "Level Wise Direct":
                        CURRENT_TAG=LEVEL_WISE_DIRECT_REPORT;
                        fragment = new LevelWiseDirectFragment();
                        //fragment = new MyGroupFragment2();
                        strTitle="Level Wise Report";
                        replaceFragment_business(fragment, CURRENT_TAG, null);
                        break;

                    case "Member Count Level Wise":
                        CURRENT_TAG=LEVEL_WISE_DIRECT_REPORT;
                       // fragment = new MemberReportFragment();
                        //fragment = new MyGroupFragment2();
                        strTitle="Member Count Level Wise";
                        //replaceFragment(fragment, CURRENT_TAG, null);
                        break;

                    case "Downline Detail"://Downline Report
                        CURRENT_TAG=MEMBER_DOWNLINE;
                        fragment = new DownlineFragment();
                        strTitle="Member Downline";
                        replaceFragment_business(fragment, CURRENT_TAG, null);
                        break;
                    case "Upgrade Report":
                        Intent upgradeIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        upgradeIntent.putExtra("Title", "Upgrade Report");
                        upgradeIntent.putExtra("Type","Upgrade");
                        startActivity(upgradeIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;
                    case "My Business"://Downline Report
                        CURRENT_TAG=MEMBER_DOWNLINE;
                        fragment = new MyBusinessFragment();
                        strTitle="Business Report";
                        replaceFragment_business(fragment, CURRENT_TAG, null);
                        break;//

                    case "My Community Detail":
                        Intent cmDetailIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        cmDetailIntent.putExtra("Title", "My Community Detail");

                        cmDetailIntent.putExtra("Type","CommunityDetail");
                        startActivity(cmDetailIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;//

                    case "Multi Topup Report":
                        Intent treeIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        treeIntent.putExtra("Title", "Multi Topup Report");

                        treeIntent.putExtra("Type","MutliTopup");
                        startActivity(treeIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;//

                    case "My Order Status":
                        Intent orderIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        orderIntent.putExtra("Title", "My Order Status");
                        orderIntent.putExtra("Type","OrderStatus");
                        startActivity(orderIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;

                    case "Pool Tree":
                        /*CURRENT_TAG=MY_DIRECT;
                        Bundle bundle2=new Bundle();
                        fragment=new WebviewFragment();
                        strTitle="My Directs";
                        bundle2.putString("Title","Generation View");
                        bundle2.putString("Type","reftree");
                        replaceFragment(fragment, CURRENT_TAG, bundle2);*/
                        /* Intent webIntent=new Intent(DashboardActivity.this,WebActivity.class);
                        startActivity(webIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/

                        //getTreeDetail("Pool Tree");
//                        Intent treeIntent = new Intent(DashboardActivity.this, WebViewActivity.class);
//                        treeIntent.putExtra("URL", "");
//
//                        treeIntent.putExtra("Type","Pool Tree");
//                        startActivity(treeIntent);
//                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;

                // E-pin Menu

                    case "Pin-Detail":
                        CURRENT_TAG=E_PIN;
                        fragment=new E_PinFragment();
                        strTitle="Pin-Detail";
                        replaceFragment_business(fragment, CURRENT_TAG, null);
                        break;

                    case "Pin Transfer":
                        CURRENT_TAG=PIN_TRANSFER;
                        fragment=new PinTransferFragment();
                        strTitle="Pin Transfer";
                        replaceFragment_business(fragment, CURRENT_TAG, null);
                        break;

                    case "Pin Transfer Detail":
                        CURRENT_TAG=PIN_TRANSFER_DETAIL;
                        fragment=new PinTransferDetailFragment();
                        strTitle="Pin Transfer Detail";
                        replaceFragment_business(fragment, CURRENT_TAG, null);
                        break;
                    case "Pin Receive Detail":
                        CURRENT_TAG=PIN_RECEIVE_DETAIL;
                        fragment=new PinReceiveDetailFragment();
                        strTitle="Pin Received Detail";
                        replaceFragment_business(fragment, CURRENT_TAG, null);
                        break;

                /* My Incentive/ Income*/

                    case "Payout Detail":
                        Intent dailyIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        dailyIntent.putExtra("Title", "Daily Incentive");
                        dailyIntent.putExtra("Type","Daily");
                        startActivity(dailyIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;

                    case "Bank Withdrawal":

                        Intent withdrl = new Intent(BusinessDashboardActivity.this, BankWithdrawalActivity.class);
                        //dailyIntent.putExtra("Title", "Daily Incentive");
                        //dailyIntent.putExtra("Type","Daily");
                        startActivity(withdrl);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        /*BankWithdrawalFragment bankWithdrawl=new BankWithdrawalFragment();
                         //fragment = new WalletReportFragment();
                        Bundle bankBundle=new Bundle();
                        bankBundle.putString("Type","C");
                        bankWithdrawl.setArguments(bankBundle);
                        strTitle="Main Wallet Report";*/
                        //replaceFragment(bankWithdrawl, CURRENT_TAG, null);
                        break;

                    case "Bank Withdrawal Detail":
                        //BankWithdrawalReportFragment bankWithdrawalReport=new BankWithdrawalReportFragment();
                         //fragment = new WalletReportFragment();
                        //Bundle bankBundle=new Bundle();
                        //bankBundle.putString("Type","C");
                        //bankWithdrawl.setArguments(bankBundle);
                        //strTitle="Bank Withdrawal Detail";
                        //replaceFragment(bankWithdrawalReport, CURRENT_TAG, null);

                        Intent withIntent=new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        withIntent.putExtra("Type","Withdrawal Detail");
                        withIntent.putExtra("Title","Bank Withdrawal Detail");
                        startActivity(withIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;

                    case "Monthly Incentive": //Weekly Income Performance & MFA Detail
                        CURRENT_TAG=WEEKLY_INCENTIVE;
                        //fragment = new MonthlyIncentiveFragment();
                        strTitle="Binary Income";
                       // replaceFragment(fragment, CURRENT_TAG, null);

                        Intent monthIncIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        monthIncIntent.putExtra("Title", "Monthly Incentive Detail");
                        monthIncIntent.putExtra("Type","Monthly");
                        startActivity(monthIncIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;
                    case "Weekly Incentive": //Weekly Income Performance & MFA Detail
                        CURRENT_TAG=WEEKLY_INCENTIVE;
                        //fragment = new MonthlyIncentiveFragment();
                        strTitle="Binary Income";
                        // replaceFragment(fragment, CURRENT_TAG, null);
                        Intent weekIncIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        weekIncIntent.putExtra("Title", "Weekly Incentive Detail");
                        weekIncIntent.putExtra("Type","Weekly");
                        startActivity(weekIncIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;
                    case "Monthly Reward Points": //Weekly Income Performance & MFA Detail
                        CURRENT_TAG=WEEKLY_INCENTIVE;
                        //fragment = new MonthlyIncentiveFragment();
                        strTitle="Binary Income";
                        // replaceFragment(fragment, CURRENT_TAG, null);
                        Intent monthRewardIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        monthRewardIntent.putExtra("Title", "Monthly Reward Points");
                        monthRewardIntent.putExtra("Type","Reward Points");
                        startActivity(monthRewardIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;

                    case "Daily Incentive": //Monthly Income
                        Intent dailyIncIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        dailyIncIntent.putExtra("Title", "Daily Incentive Detail");
                        dailyIncIntent.putExtra("Type","Daily");
                        startActivity(dailyIncIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;

                    case "My Reward":
                        CURRENT_TAG=REWARDS;
                        fragment = new RewardFragment();
                        strTitle="My Reward";
                        replaceFragment_business(fragment, CURRENT_TAG, null);
                       /* Intent rewardIncIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        rewardIncIntent.putExtra("Title", "My Rewards");
                        rewardIncIntent.putExtra("Type","MyReward");
                        startActivity(rewardIncIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/
                        break;

                    case "Performance & MFA Detail": //Weekly Income
                        CURRENT_TAG=WEEKLY_INCENTIVE;
                        //fragment = new MonthlyIncentiveFragment();
                        strTitle="Binary Income";
                        // replaceFragment(fragment, CURRENT_TAG, null);

                        Intent mfaIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        mfaIntent.putExtra("Title", "Performance & MFA Detail");
                        mfaIntent.putExtra("Type","MFA");
                        startActivity(mfaIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;

                    /*Wallet*/
                    case "Wallet Transaction Detail":
                        CURRENT_TAG=MAIN_WALLET_REPORT;
                        SubServiceFragment fragmentW = new SubServiceFragment();
                        // fragment = new WalletReportFragment();
                        String strTitle="Main Wallet Report";
                        Bundle walletBundle=new Bundle();
                        walletBundle.putString("From","Act");
                        walletBundle.putString("Service","Wallet");
                        fragmentW.setArguments(walletBundle);
                        replaceFragment_business(fragmentW, CURRENT_TAG, walletBundle);
                        break;

                    case "Income Wallet": //Main Wallet report
                        CURRENT_TAG=MAIN_WALLET_REPORT;
                        Intent mainIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        //mainIntent.putExtra("Title", "Main Wallet Report");
                        mainIntent.putExtra("Title", "Income Wallet Report");
                        mainIntent.putExtra("Type","Wallet_M");
                        startActivity(mainIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                       /* WalletReportFragment walletFragment1=new WalletReportFragment();
                        // fragment = new WalletReportFragment();
                        Bundle walletBundle1=new Bundle();
                        walletBundle1.putString("Type","R");
                        walletFragment1.setArguments(walletBundle1);
                        strTitle="Main Wallet Report";
                        replaceFragment(walletFragment1, CURRENT_TAG, walletBundle1);*/
                        break;
                    case "Main Wallet": //Shopping Wallet report Referral Point Wallet
                        Intent shopIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        //shopIntent.putExtra("Title", "Subscription Wallet Report");
                        shopIntent.putExtra("Title", "Main Wallet Report");
                        shopIntent.putExtra("Type","R");
                        startActivity(shopIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;
                    case "Referral Point Wallet": //Shopping Wallet report Referral Point Wallet
                        Intent refIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        refIntent.putExtra("Title", "Referral Point Report");
                        refIntent.putExtra("Type","Wallet_P");
                        startActivity(refIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;
                    case "Wallet Request":
//                        CURRENT_TAG=WALLET_REQUEST;
//                        fragment = new WalletRequestFragment();
//                        strTitle="Wallet Request";
//                        replaceFragment(fragment, CURRENT_TAG, null);
                        //Toast.makeText(DashboardActivity.this,"Coming soon",Toast.LENGTH_SHORT).show();

                        Intent walletReq = new Intent(BusinessDashboardActivity.this, WalletRequestActivity.class);
                        //walletReq.putExtra("Home","true");
                       startActivity(walletReq);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;
                    case "Wallet Request Detail":
                       /* CURRENT_TAG=WALLET_REQUEST_DETAIL;
                        fragment = new WalletRequestDetailFragment();
                        strTitle="Wallet Request Detail";
                        replaceFragment(fragment, CURRENT_TAG, null);*/

                        Intent walletReqIntent = new Intent(BusinessDashboardActivity.this, CommonReportActivity.class);
                        walletReqIntent.putExtra("Title", "Wallet Request Detail");
                        walletReqIntent.putExtra("Type","WalletReqDetail");
                        startActivity(walletReqIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;

                    case "Wallet Transfer":
                       // CURRENT_TAG=WALLET_TRANSFER;
                        fragment = new WalletTransferFragment();
                        strTitle="Wallet Transfer";
                       // replaceFragment(fragment, CURRENT_TAG, null);
                        break;
                    case "Cashback Wallet":
                       // WalletReportFragment cashbackWallet=new WalletReportFragment();
                        // fragment = new WalletReportFragment();
                        Bundle cashBundle1=new Bundle();
                        cashBundle1.putString("Type","S");
                        //cashbackWallet.setArguments(cashBundle1);
                        strTitle="CashBack Wallet Report";
                       // replaceFragment(cashbackWallet, CURRENT_TAG, cashBundle1);
                        break;

                /*My Shopping*/
                    case "Product Dispatch":
                        //Intent disIntent=new Intent(BusinessDashboardActivity.this, ProductDispatchActivity.class);
                        //startActivity(disIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;

                    case "My Purchase Detail": //Joining Reward
                        CURRENT_TAG=FREE_PRODUCT_VOUCHER;
                        fragment = new MyPurchaseDetailFragment();
                        strTitle="Free Product Voucher";
                        replaceFragment_business(fragment, CURRENT_TAG, null);
                        break;

                    case "Purchase Request":
                        Toast.makeText(BusinessDashboardActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();
                        break;
                    case "Downline Purchase": //Joining Reward
                        CURRENT_TAG=FREE_PRODUCT_VOUCHER;
                        fragment = new DownlinePurchaseFragment();
                        strTitle="Downline Purchase";
                        replaceFragment_business(fragment, CURRENT_TAG, null);


                        break;


                //Complaint
                    case "Raise Ticket"://Complaint
                        CURRENT_TAG=COMPLAINT;
                        Intent intent=new Intent(BusinessDashboardActivity.this, ComplaintActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                        break;
                    case "Ticket Status"://Complaint Detail
                        CURRENT_TAG=COMPLAINT_DETAIL;
                        fragment=new ComplaintDetailFragment();
                        strTitle="Complaint Detail";
                        replaceFragment_business(fragment, CURRENT_TAG, null);
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

        /*profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment1 = new UpdateprofilepicFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                replaceFragment(fragment1,AppConstants.UPLOAD_PROFILE,null);
                fragmentTransaction.commit();
                drawer.closeDrawer(GravityCompat.START);
            }
        });*/
        //onBackPressed();

        /*private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

            private void showItemDescription(Object object, ItemInfo itemInfo) {
                StringBuilder builder = new StringBuilder("\"");
                builder.append(((BaseItem) object).getName());
                builder.append("\" clicked!\n");
                builder.append(getItemInfoDsc(itemInfo));

                Toast.makeText(DashboardActivity.this, builder.toString(), Toast.LENGTH_SHORT).show();


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
                //Toast.makeText(DashboardActivity.this,stringGroupItem,Toast.LENGTH_SHORT).show();


            }


        };*/

    }

    private class ListAdapter extends MultiLevelListAdapter {

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
            new CustomDataProvider(BusinessDashboardActivity.this);
            return CustomDataProvider.getSubItems((BaseItem)object);
        }

        @Override
        public boolean isExpandable(Object object) {
            //return CustomDataProvider.isExpandable((BaseItem) object);
            new CustomDataProvider(BusinessDashboardActivity.this);
            return CustomDataProvider.isExpandable((BaseItem)object);
        }

        @Override
        public View getViewForObject(Object object, View convertView, ItemInfo itemInfo) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(BusinessDashboardActivity.this).inflate(R.layout.expendablelist_data_item, null);
                //viewHolder.infoView = (TextView) convertView.findViewById(R.id.dataItemInfo);
                viewHolder.nameView = (TextView) convertView.findViewById(R.id.dataItemName);
                viewHolder.arrowView = (ImageView) convertView.findViewById(R.id.dataItemArrow);
                viewHolder.levelBeamView = (LevelBeamView) convertView.findViewById(R.id.dataItemLevelBeam);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
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
    }



    private String getItemInfoDsc(ItemInfo itemInfo) {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("level[%d], idx in level[%d/%d]",
                itemInfo.getLevel() + 1, /*Indexing starts from 0*/
                itemInfo.getIdxInLevel() + 1 /*Indexing starts from 0*/,
                itemInfo.getLevelSize()));

        if (itemInfo.isExpandable()) {
            builder.append(String.format(", expanded[%b]", itemInfo.isExpanded()));
        }
        return builder.toString();
    }

    public void loadHomeFragment_Main() {
        // setting home default_img checked
        //navigationView.getMenu().getItem(0).setChecked(true);
        //multiLevelListView.
       // clearBackStack();
       /* if (SharedPrefrence_Business.getInstance().flagFromSearch) {
            SharedValues.getInstance().flagFromSearch = false;
            EShopDetailFragment searchFragment = new EShopDetailFragment();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, searchFragment, "StoreDetailFragment");
            ft.commit();
        } else {*/
        BusinessHomeFragment businessHomeFragment = new BusinessHomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.business_content_frame, businessHomeFragment, AppConstants.TAG_HOME);
        ft.commit();
        //}
    }

    /*public void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }*/

    @Override
    protected void onResume() {
        //userInfo = new com.dreamtouchglobal.dtg.utility.LoginPrefrencesKeys(this).getLoggedinUser();
        navigationView = (NavigationView) findViewById(R.id.business_nav_view);
        //Login_Logout();
        //fillUserInfo();
        //checkNewAppVersionState();
        super.onResume();
    }

    @Override
    protected void onStop(){
        //unregisterInstallStateUpdListener();
        super.onStop();
    }
    @Override
    protected void onRestart(){
        //checkNewAppVersionState();
        super.onRestart();
    }
    @Override
    protected void onDestroy() {
       // unregisterInstallStateUpdListener();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.business_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home_menu_home) {
            Intent mainIntent = new Intent(BusinessDashboardActivity.this, DashboardActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);



            //shopIntent.putExtra("Title", "Shop Wallet Report");
            //shopIntent.putExtra("Type","S");
            startActivity(mainIntent);
            finish();
        }
        if (id == R.id.home_menu_logout) {
            AlertDialogUtils.showMaterialDialogWithTwoButton(BusinessDashboardActivity.this, new AlertDialogButtonListener() {
                        @Override
                        public void onButtontapped(String btnText) {
                            if (btnText.equals("YES"))
                                blankValueFromSharedPreference(BusinessDashboardActivity.this);
                        }
                    }, "Alert!", "Are you Sure, you want to logout?", "YES", "NO"
            );
        }


        return super.onOptionsItemSelected(item);
    }

   /* public void replaceFragment(Fragment fragment, String tag, Bundle bundle) {

        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.business_content_frame, fragment, tag);
        fragment.setRetainInstance(true);
        *//*to add fragment to stack*//*
        ft.addToBackStack(tag);
        try {
            ft.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            ft.commitAllowingStateLoss();
        }
    }*/

    public void attachFragments(Fragment mFrgObj) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.business_content_frame, mFrgObj).addToBackStack(mFrgObj.toString());
        fragmentTransaction.commit();
    }


    /*public void blankValueofSharePreference() {
       *//* LoginResponse loginUserGetResponseEntity = new LoginResponse();

        LoginRes
        loginUserGetResponseEntity.setFirstname("");
        loginUserGetResponseEntity.setLastName("");
        loginUserGetResponseEntity.setPassword(" ");
        loginUserGetResponseEntity.setEmail("");
        loginUserGetResponseEntity.setUserName("");
        loginUserGetResponseEntity.setMobileNo("");
        loginUserGetResponseEntity.setFax("");
        loginUserGetResponseEntity.setFormNo("");
        loginUserGetResponseEntity.setActiveStatus("");
        loginUserGetResponseEntity.setAddress("");
        loginUserGetResponseEntity.setCity("");
        loginUserGetResponseEntity.setCityCode("");
        loginUserGetResponseEntity.setDistrict("");
        loginUserGetResponseEntity.setDistrictCode("");
        loginUserGetResponseEntity.setFormNo("");
        loginUserGetResponseEntity.setCountryName("");
        loginUserGetResponseEntity.setCountryId("");
        loginUserGetResponseEntity.setStateCode("");
        loginUserGetResponseEntity.setRandomId("");
        loginUserGetResponseEntity.setStatusCode("");
        loginUserGetResponseEntity.setId("");
        loginUserGetResponseEntity.setMessage("");*//*


        //new LoginPreferences_Utility(getApplicationContext()).setLoggedinUser(loginUserGetResponseEntity);

        SharedPrefrence_Business.setPassword(BusinessDashboardActivity.this, "");
        SharedPrefrence_Business.setUserID(BusinessDashboardActivity.this, "");
        SharedPrefrence_Business.setUsername(BusinessDashboardActivity.this, "");
        SharedPrefrence_Business.setProfileIamge(BusinessDashboardActivity.this,"");
        SharedPrefrence_Business.setIsActive(BusinessDashboardActivity.this,"");
        SharedPrefrence_Business.setUserMobileNumber(BusinessDashboardActivity.this,"");

        Intent i = new Intent(BusinessDashboardActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }*/
    public  void blankValueFromSharedPreference(Context context) {
        SharedPrefrence_Business.setPassword(context, "");
        SharedPrefrence_Business.setUserID(context, "");
        SharedPrefrence_Business.setUsername(context, "");
        SharedPrefrence_Business.setProfileIamge(context,"");
        SharedPrefrence_Business.setIsActive(context,"");
        SharedPrefrence_Business.setUserMobileNumber(context,"");
        SharedPrefrence_Business.setIsLogin(context,"N");
        SharedPrefrence_Business.setApiKey(context,"");

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

        new SettingPreference(context).setSettinginfo(settingModel);


        SharedPrefrence.setPassword(context, "");
        SharedPrefrence.setUserID(context, "");
        SharedPrefrence.setUsername(context, "");
        SharedPrefrence.setProfileIamge(context,"");
        SharedPrefrence.setIsActive(context,"");
        SharedPrefrence.setUserMobileNumber(context,"");

        String toast= "Invalid login detail. Please login again";
        Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        finish();
    }

    public void showComplaintDetalViewDialog(String strComplaintId) {
        ComplaintDetailViewFragment messageReplyDialog = new ComplaintDetailViewFragment();

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EXTRA_STIRNG_COMPLAINT_ID, strComplaintId);


        messageReplyDialog.setArguments(bundle);
        messageReplyDialog.setRetainInstance(true);

        messageReplyDialog.show(this.getSupportFragmentManager(),AppConstants.COMPLAINT_DETAIL_VIEW);
    }

    /*Business plan Letter API*/
    private void getBusinessPlan(){

        pDialog=new ProgressDialog(this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.isShowing();
        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_BUSINESS_PLAN);

        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(this));

        Call<WelcomeLetterResponse> geneologyResponseCall=
                NetworkClient.getInstance(this).create(DocumentService.class).fetchWelcomeLetter(baseRequest,strApiKey);

        geneologyResponseCall.enqueue(new Callback<WelcomeLetterResponse>() {
            @Override
            public void onResponse(Call<WelcomeLetterResponse> call, Response<WelcomeLetterResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {
                    WelcomeLetterResponse geneologyResponse=new WelcomeLetterResponse();
                    geneologyResponse=response.body();
                    if (geneologyResponse.getResponse().equals("OK")){

                        /*File file = new File(Environment.getExternalStorageDirectory(),
                        "Report.pdf");*/
                        String file=geneologyResponse.getUrl().toString();
                        Uri path = Uri.parse(file);
                        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pdfOpenintent.setDataAndType(path, "application/pdf");
                        try {
                            startActivity(pdfOpenintent);
                        }
                        catch (ActivityNotFoundException e) {
                            Toast.makeText(BusinessDashboardActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(BusinessDashboardActivity.this, geneologyResponse.getResponse(), Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<WelcomeLetterResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(BusinessDashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getWelcomeLetter(){

        pDialog=new ProgressDialog(this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_WELCOME_LETTER);

        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(this));

        Call<WelcomeLetterResponse> geneologyResponseCall=
                NetworkClient.getInstance(this).create(DocumentService.class).fetchWelcomeLetter(baseRequest,strApiKey);

        geneologyResponseCall.enqueue(new Callback<WelcomeLetterResponse>() {
            @Override
            public void onResponse(Call<WelcomeLetterResponse> call, Response<WelcomeLetterResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {
                    WelcomeLetterResponse geneologyResponse=new WelcomeLetterResponse();
                    geneologyResponse=response.body();
                    if (geneologyResponse.getResponse().equals("OK")){
                        Intent i = new Intent(BusinessDashboardActivity.this, WebViewActivity.class);
                        i.putExtra("URL", geneologyResponse.getUrl());
                        i.putExtra("Type","Welcome Letter");
                        i.putExtra("From","Welcome Letter");
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    }
                    else{
                        Toast.makeText(BusinessDashboardActivity.this, geneologyResponse.getResponse(), Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<WelcomeLetterResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(BusinessDashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    /*Geneology  API*/
    private void getTreeDetail(final String type){

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/

        if(type.contains("Geneology"))
            baseRequest.setReqtype(ApiConstants.REQUEST_GENEOLOGY);
        else  if(type.contains("Generation View"))
            baseRequest.setReqtype(ApiConstants.REQUEST_SPONSER_GENEOLOGY);
        else  if(type.contains("Global Pool"))
            baseRequest.setReqtype(ApiConstants.REQUEST_POOL_TREE);
        else  if(type.contains("Invoice"))
            baseRequest.setReqtype(ApiConstants.REQUEST_INVOICE);
        /*else{
            baseRequest.setReqtype(ApiConstants.REQUEST_POOL_TREE);
            baseRequest.setType("");
        }*/


        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(this));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(this));

        Call<GeneologyResponse> geneologyResponseCall=
                NetworkClient.getInstance(this).create(MyTeamService.class).fetchGeneology(baseRequest,strApiKey);

        geneologyResponseCall.enqueue(new Callback<GeneologyResponse>() {
            @Override
            public void onResponse(Call<GeneologyResponse> call, Response<GeneologyResponse> response) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                try {
                    GeneologyResponse geneologyResponse=new GeneologyResponse();
                    geneologyResponse=response.body();
                    if (geneologyResponse.getResponse().equals("OK")){
                        Intent i = new Intent(BusinessDashboardActivity.this, WebViewActivity.class);
                        i.putExtra("URL", geneologyResponse.getUrl());

                        i.putExtra("From","Generation View");
                        i.putExtra("Type",type);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    }
                    else{
                        String toast= geneologyResponse.getResponse();
                        Toast.makeText(BusinessDashboardActivity.this, toast, Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GeneologyResponse> call, Throwable t) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(BusinessDashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    /*Get Address Detail Api Request and REsponse*/
    /*Get Address Detail Api Request and REsponse*/
    private void getDashboardDetail(){

        pDialog=new ProgressDialog(this);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest request=new BaseRequest();
        /*Set value in Entity class*/
        request.setReqtype(ApiConstants.REQUEST_DASHBOARD);
        request.setPasswd(SharedPrefrence_Business.getPassword(this));
        request.setUserid(SharedPrefrence_Business.getUserID(this));
        request.setIslogin(SharedPrefrence_Business.getIsLogin(this));

        Call<DashboardResponse> callAddressDetail=
                NetworkClient1.getInstance(this).create(ProfileServices.class).fetchDashboardDetail(request,strApiKey);

        callAddressDetail.enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                     DashboardResponse Response =new DashboardResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {

                            if(Response.getReferalurl().equals("") && Response.getReferalurl() == null){
                                //layoutLink.setVisibility(View.GONE);
                            }
                            else {
                                strRefLink=Response.getReferalurl();
                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setType("text/plain");
                                //String string = txtRefLink.getText().toString();
                                //   + "Sponser Code : " + userInfo_Shoppe.getPhone();

                                shareIntent.putExtra(Intent.EXTRA_TEXT, strRefLink);
                                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Discount Mania");
                                startActivity(Intent.createChooser(shareIntent, "SHARE_APP"));

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
    }

    /*this method check for
     * new update available*/
    public void checkForAppUpdate() {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(this);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Create a listener to track request state updates.
       /* installStateUpdatedListener = new InstallStateUpdatedListener() {
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

        // Checks that the platform will allow the specified type of update.
       /* appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                // Request the update.
                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

                    // Before starting an update, register a listener for updates.
                    appUpdateManager.registerListener(installStateUpdatedListener);
                    // Start an update.
                    startAppUpdateFlexible(appUpdateInfo);
                } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) ) {
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
        Snackbar snackbar =
                Snackbar.make(view, "Update App", Snackbar.LENGTH_INDEFINITE);
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
     * Checks that the update is not stalled during 'onResume()'.
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
                            /*if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                                popupSnackbarForCompleteUpdateAndUnregister();
                            }*/

                            //IMMEDIATE:
                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                startAppUpdateImmediate(appUpdateInfo);
                                // After the update is downloaded, show a notification
                                // and request user confirmation to restart the app.
                                //popupSnackbarForCompleteUpdateAndUnregister();
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



    /*For Profile Pic change*/
    /*Update Pan Card Detail Api Request and Response*/
    private void UpdateProfileImage(String strfile){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        final UploadImageRequest Request = new UploadImageRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_UPLOAD_IMAGE);
        Request.setPasswd(SharedPrefrence_Business.getPassword(this));
        Request.setUserid(SharedPrefrence_Business.getUserID(this));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        Request.setType("profilepic");
        File file1=new File(ImageUtil.compressImage(strfile));
        String str1="";
        str1=file1.getPath();

        MultipartBody.Part body;

        if(!str1.equals("")){
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file1);
            body = MultipartBody.Part.createFormData("file:", file1.getName(), reqFile);
            // RequestBody request = RequestBody.create(MediaType.parse("text/plain"), new Gson().toJson(Request));
            RequestBody request = RequestBody.create(MultipartBody.FORM, new Gson().toJson(Request));

            Call<UploadImageResponse> callUpdatePanDetail=
                    NetworkClient.getInstance(this).create(ProfileServices.class).fetchProfileImageMultipart(body,request,strApiKey);

            callUpdatePanDetail.enqueue(new Callback<UploadImageResponse>() {
                @Override
                public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                    if(pDialog.isShowing())
                        pDialog.dismiss();
                    try {

                        UploadImageResponse Response =new UploadImageResponse();
                        Response=response.body();

                        if (Response.getResponse().equals("OK")) {

                            String toast= Response.getResponse()+ ": Upload Sucesfully" ;
                            Toast.makeText(BusinessDashboardActivity.this, toast, Toast.LENGTH_SHORT).show();
                            SharedPrefrence_Business.setProfileIamge(BusinessDashboardActivity.this,Response.getImage());

                            if(Response.getImage().equals("")){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    Picasso.with(BusinessDashboardActivity.this)
                                            .load(String.valueOf(BusinessDashboardActivity.this.getDrawable(R.mipmap.profile_icon)))
                                            .placeholder(R.mipmap.profile_icon)
                                            .error(R.mipmap.profile_icon)
                                            .into(profilepic);

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

                                Picasso.with(BusinessDashboardActivity.this)
                                        .load(Response.getImage())
                                        .placeholder(R.mipmap.profile_icon)
                                        .error(R.mipmap.profile_icon)
                                        .into(profilepic);

                   /* Glide.with(mContext)  //2
                            .load(hotelSearchList.get(position).getHotelPicture()) //3
                            //.centerCrop() //4
                            .placeholder(R.mipmap.home_icon_hotel) //5
                            .error(R.mipmap.home_icon_hotel) //6
                            .fallback(R.mipmap.home_icon_hotel) //7
                            .into(myViewHolder.imgHotelImg); //8*/
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
                public void onFailure(Call<UploadImageResponse> call, Throwable t) {
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
            Toast.makeText(BusinessDashboardActivity.this,"File not Select", Toast.LENGTH_SHORT).show();
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


                    //textViewDocPath.setText(AppConstants.Uri);
                    AppConstants.ImageUri= AppConstants.Uri;
                   // profilepic.setImageBitmap(bitmap);
                    Glide.with(this)  //2
                                .load(AppConstants.Uri) //3
                                .centerCrop() //4
                                .placeholder(R.mipmap.logo) //5
                                .error(R.mipmap.logo) //6
                                .fallback(R.mipmap.logo) //7
                                .into(profilepic); //8

                   /* Glide.with(mContext)  //2
                            .load(hotelSearchList.get(position).getHotelPicture()) //3
                            //.centerCrop() //4
                            .placeholder(R.mipmap.home_icon_hotel) //5
                            .error(R.mipmap.home_icon_hotel) //6
                            .fallback(R.mipmap.home_icon_hotel) //7
                            .into(myViewHolder.imgHotelImg);*/

                    /*Call method for upload image*/
                    uploadAttachment();


                } else if (resultCode ==this.RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(BusinessDashboardActivity.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();

                } else {
                    // failed to capture image
                    Toast.makeText(BusinessDashboardActivity.this, "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
                }


            }
            // When an Image is picked from gallery

            else if(requestCode == 1){

                AppConstants.Uri="";
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

                    AppConstants.imgpath = imgPath;
                    AppConstants.ImageUri=imgPath;

                    //call methodupload image
                    uploadAttachment();

                } else if (resultCode == this.RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(BusinessDashboardActivity.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();
                }

            }

            else if (requestCode == REQ_CODE_VERSION_UPDATE) {
                if (resultCode != RESULT_OK) {
                    //Log.e ("Update flow failed! Result code: " + resultCode);
                    Log.e("Update flow failed! " ,String.valueOf(resultCode));
                    // If the update is cancelled or fails,
                    // you can request to start the update again.
                    //checkForAppUpdate();
                }
            }
        }catch (Exception e) {
            Toast.makeText(BusinessDashboardActivity.this, e.toString(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(BusinessDashboardActivity.this, "NO FILE SELECTED TO UPLOAD", Toast.LENGTH_LONG).show();
        }
    }

    private void sendImageUploadRequest(final String filePath) {
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
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



   /* public void showComplaintDetalViewDialog(String strComplaintId) {
        ComplaintDetailViewFragment messageReplyDialog = new ComplaintDetailViewFragment();

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EXTRA_STIRNG_COMPLAINT_ID, strComplaintId);


        messageReplyDialog.setArguments(bundle);
        messageReplyDialog.setRetainInstance(true);

        messageReplyDialog.show(this.getSupportFragmentManager(),AppConstants.COMPLAINT_DETAIL_VIEW);
    }*/

   /* public void freeProductDialog(ArrayList<FreeProduct> object1, ArrayList<RepurchaseProductRequestEntity> object2,Bundle bundle) {
        FreeProductDialog messageReplyDialog = new FreeProductDialog();

        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.EXTRA_SERIALIZABLE,object1);
        bundle.putSerializable(AppConstants.EXTRA_SERIALIZABLE2,object2);


        messageReplyDialog.setArguments(bundle);
        messageReplyDialog.setRetainInstance(true);
        messageReplyDialog.show(getSupportFragmentManager(), "FreeProductDialog");
    }*/

   /* public void freeProductDialog(Bundle bundle) {
        FreeProductDialog messageReplyDialog = new FreeProductDialog();

        //Bundle bundle = new Bundle();
        // bundle.putSerializable(AppConstants.EXTRA_SERIALIZABLE,object1);
        //bundle.putSerializable(AppConstants.EXTRA_SERIALIZABLE2,object2);


        messageReplyDialog.setArguments(bundle);
        messageReplyDialog.setRetainInstance(true);
        messageReplyDialog.show(getSupportFragmentManager(), "FreeProductDialog");
    }*/
}
