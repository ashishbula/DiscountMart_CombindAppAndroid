package in.discountmart.business.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import in.discountmart.R;
import in.discountmart.activity.LoginActivity;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;


public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_activity_splash);
        try{

            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            progressBar=(ProgressBar)findViewById(R.id.splash_activity_progressbar);


            final String deviceId = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            //Toast.makeText(this, "Device Id" +deviceId, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, deviceId, Toast.LENGTH_SHORT).show();


            new android.os.Handler().postDelayed(new Runnable() {

                /* * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company*/

                @Override
                public void run() {

                    getNextScreen();
                }
            }, SPLASH_TIME_OUT);


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void getNextScreen()
    {

        boolean loginBusniess;

        if(SharedPrefrence_Business.getUserID(this).equals("") || SharedPrefrence_Business.getUserID(this) == null){
            loginBusniess=false;
        }
        else {
            loginBusniess=true;
        }


        if(loginBusniess){
            toDashBoard();

        }
        else {
            toLogin();
        }
    }


    private void toDashBoard() {
        Intent intent = new Intent(SplashActivity.this, BusinessDashboardActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
    }

    private void toLogin() {

        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        ActivityOptions options = null;

        startActivity(intent);

        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

    }


}
