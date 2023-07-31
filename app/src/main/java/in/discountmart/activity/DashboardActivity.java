package in.discountmart.activity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import java.util.Objects;

import in.base.ui.BaseActivity;
import in.discountmart.R;
import in.discountmart.business.listener.constants.AppConstants;
import in.discountmart.fragment.HomeFragment;
import in.discountmart.fragment.WebFragment;
import in.discountmart.listener.AlertDialogButtonListener;
import in.discountmart.shared_pref_business.SharedPrefrence;
import in.discountmart.utility.AlertDialogUtils;
import in.discountmart.utility_services.activity.WebViewActivity;
import in.discountmart.utility_services.model.SettingModel;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.sharedpreferences.SettingPreference;

public class DashboardActivity extends BaseActivity {

    MaterialToolbar toolbar;
    LinearLayout bottomNavHome;
    LinearLayout bottomNavShare;
    LinearLayout bottomNavSupport;
    LinearLayout bottomNavLogout;
    public static BottomNavigationView bottomNavigationView;
    public static LinearLayout bottomNav;
    TextView txtId;
    View view;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(android.R.id.content);
        try {
            toolbar = (MaterialToolbar) findViewById(R.id.main_appbar_main_toolbar);
            //bottomNavHome=(LinearLayout)findViewById(R.id.homedash_bottomnav_home);
            //bottomNavShare=(LinearLayout)findViewById(R.id.homedash_bottomnav_share);
            //bottomNavSupport=(LinearLayout)findViewById(R.id.homedash_bottomnav_support);
            //bottomNavLogout=(LinearLayout)findViewById(R.id.homedash_bottomnav_logout);
            //bottomNav=(LinearLayout)findViewById(R.id.homedash_bottomnav);
            txtId = (TextView) findViewById(R.id.main_appbar_txt_id);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

            // call method for check update
            checkForAppUpdate();
            loadHomeFragment();
            //checkLocationPermission();
           /* if(checkPermission()){

            }
            else {
                requestPermission();
            }*/

            txtId.setText(SharedPrefrence.getUserID(DashboardActivity.this));

            bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_appbar_navigation_bottom);

            bottomNavigationView.setOnNavigationItemSelectedListener
                    (new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            Fragment selectedFragment = null;
                            //Bundle bundle = new Bundle();
                            switch (item.getItemId()) {
                                case R.id.main_bottom_nav_action_home:
                                    loadHomeFragment();
                                    break;

                                case R.id.main_bottom_nav_action_share:
                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                    shareIntent.setType("text/plain");
                                    String string = "\n Get the best deals on travel services and online Recharges and Shopping, Movie Ticket, Food Mart etc. All at one place by simply downloading Discount Mart App. \n" +
                                            "Visit us at\n" + "Referral ID : " + SharedPrefrence.getUserID(DashboardActivity.this) + "\n \n";
                                    string = string + "https://play.google.com/store/apps/details?id=" + DashboardActivity.this.getPackageName() + "\n\n";
                                    //   + "Sponser Code : " + userInfo_Shoppe.getPhone();

                                    shareIntent.putExtra(Intent.EXTRA_TEXT, string);
                                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Discount Mart");
                                    startActivity(Intent.createChooser(shareIntent, "SHARE_APP"));
                                    break;


                                case R.id.main_bottom_nav_action_support:
                                    WebFragment fragment = new WebFragment();

                                    String strUrl = "https://discountmart.in/contact_application.html";

                                    Bundle bundle = new Bundle();
                                    bundle.putString("Type", "Support");
                                    bundle.putString("Title", "Customer Support");
                                    bundle.putString("URL", strUrl);
                                    fragment.setArguments(bundle);
                                    replaceFragment(fragment, "Web", bundle);
                                    break;

                                case R.id.main_bottom_nav_action_policy:
                                    String policyUrl = "https://utility.zaradobit.com/PrivacyPolicy.aspx";
                                    //WebFragment fragment=new WebFragment();
                                    Bundle policybundle = new Bundle();
                                    policybundle.putString("Type", "Policy");
                                    policybundle.putString("Title", "Privacy Policy");
                                    policybundle.putString("From", "Activity");
                                    policybundle.putString("URL", policyUrl);
                                    //fragment.setArguments(bundle);
                                    //replaceFragment(fragment,"Policy",null);

                                    Intent i = new Intent(DashboardActivity.this, WebViewActivity.class);
                                    //i.putExtra("URL", policyUrl);

                                    //i.putExtra("From","Policy");
                                    //i.putExtra("Type","Privacy Policy");
                                    i.putExtras(policybundle);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                                    break;


                            }
                            return true;
                        }
                    });

            /* Bottom Navigation Tab
             * Home on click*/
            /*bottomNavHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadHomeFragment();
                }
            });*/

            /* Bottom Navigation Tab
             * Share on click*/
            /*bottomNavShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String string = "\n Get the best deals on travel services and online Recharges and Shopping, Movie Ticket, Food Mart etc. All at one place by simply downloading Discount Mart App. \n" +
                            "Visit us at\n"+"Referral ID : " + SharedPrefrence.getUserID(DashboardActivity.this)+"\n \n";
                    string = string + "https://play.google.com/store/apps/details?id=" + DashboardActivity.this.getPackageName() + "\n\n";
                    //   + "Sponser Code : " + userInfo_Shoppe.getPhone();

                    shareIntent.putExtra(Intent.EXTRA_TEXT, string);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Discount Mart");
                    startActivity(Intent.createChooser(shareIntent, "SHARE_APP"));
                }
            });*/

            /* Bottom Navigation Tab
             * Support on click*/
            /*bottomNavSupport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebFragment fragment=new WebFragment();

                    String strUrl="https://discountmart.in/contact_application.html";

                    Bundle bundle=new Bundle();
                    bundle.putString("Type","Support");
                    bundle.putString("Title","Customer Support");
                    bundle.putString("URL",strUrl);
                    fragment.setArguments(bundle);
                    replaceFragment(fragment,"Web",bundle);

                }
            });*/
            /* Bottom Navigation Tab
             * Logout on click*/
          /*  bottomNavLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showLogoutDialog();
                }
            });*/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void replaceFragment(Fragment fragment, String tag, Bundle bundle) {

        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, tag);
        fragment.setRetainInstance(true);
        /*to add fragment to stack*/
        ft.addToBackStack(tag);
        try {
            ft.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            ft.commitAllowingStateLoss();
        }
    }

    public void loadHomeFragment() {
        // setting home default_img checked
        //navigationView.getMenu().getItem(0).setChecked(true);
        //multiLevelListView.
        clearBackStack();
        /*if (SharedValues.getInstance().flagFromSearch) {
            SharedValues.getInstance().flagFromSearch = false;
            EShopDetailFragment searchFragment = new EShopDetailFragment();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, searchFragment, "StoreDetailFragment");
            ft.commit();
        } else {*/
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, homeFragment, AppConstants.TAG_HOME);
        ft.commit();
        //}
    }

    void showLogoutDialog() {
        try {
            AlertDialogUtils.showMaterialDialogWithTwoButton(DashboardActivity.this, new AlertDialogButtonListener() {
                        @Override
                        public void onButtontapped(String btnText) {
                            if (btnText.equals("YES"))
                                blankValueofSharePreference();
                        }
                    }, "Alert!", "Are you Sure, you want to logout?", "YES", "NO"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Method for delect Login shared preference value
     * of Business and shop*/
    public void blankValueofSharePreference() {

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

        new SettingPreference(DashboardActivity.this).setSettinginfo(settingModel);


        SharedPrefrence.setPassword(DashboardActivity.this, "");
        SharedPrefrence.setUserID(DashboardActivity.this, "");
        SharedPrefrence.setUsername(DashboardActivity.this, "");
        SharedPrefrence.setProfileIamge(DashboardActivity.this, "");
        SharedPrefrence.setIsActive(DashboardActivity.this, "");
        SharedPrefrence.setUserMobileNumber(DashboardActivity.this, "");

        Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    public void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    @Override
    protected void onResume() {
        //userInfo = new com.dreamtouchglobal.dtg.utility.LoginPrefrencesKeys(this).getLoggedinUser();
        //navigationView = (NavigationView) findViewById(R.id.nav_view);
        //Login_Logout();
        //fillUserInfo();
        checkNewAppVersionState();
        super.onResume();
    }

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

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Permission")
                        .setMessage("R.string.text_location_permission")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(DashboardActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
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
                    if (ContextCompat.checkSelfPermission(this,
                            ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        // LocationManager locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    //Get location
    public void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation == null)
        {
            myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        }
    }


    /*public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }*/

    /*public void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);

        //getLocation();
    }*/

   /* @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0) {

                    boolean externalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (externalStorage && cameraAccepted)
                        Snackbar.make(view, "Permission Granted, Now you can access location", Snackbar.LENGTH_LONG).show();
                    else {

                        Snackbar.make(view, "Permission Denied, You cannot access location.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION},
                                                            MY_PERMISSIONS_REQUEST_LOCATION);
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
    }*/

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}