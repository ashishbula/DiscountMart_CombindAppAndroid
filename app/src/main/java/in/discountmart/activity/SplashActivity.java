package in.discountmart.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import in.discountmart.R;
import in.discountmart.shared_pref_business.SharedPrefrence;


public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try{

            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            progressBar=(ProgressBar)findViewById(R.id.splash_activity_progressbar);


            final String deviceId = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            //Toast.makeText(this, "Device Id" +deviceId, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, deviceId, Toast.LENGTH_SHORT).show();


            new android.os.Handler().postDelayed(new Runnable() {

                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity

                    // LoginUserGetResponseEntity.LoginUserDetailEntity loginUserGetResponseEntity=new LoginUserGetResponseEntity.LoginUserDetailEntity();
                    //loginUserGetResponseEntity=new LoginPreferences(getApplicationContext()).getLoggedinUser();
                    // if(loginUserGetResponseEntity.getId().equals("")||loginUserGetResponseEntity.getId()==null)
                    //{
                    // Intent i = new Intent(SplashActivity.this, MainMenuActivity.class);

                    //startActivity(i);
                    //finish();
                    // }
                    // else
                    // {
                    if(SharedPrefrence.getPassword(SplashActivity.this).toString().equals("")
                            || SharedPrefrence.getUserID(SplashActivity.this).toString().equals("")){

                        Intent i = new Intent(SplashActivity.this, LoginActivity.class);

                        startActivity(i);
                        finish();overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                    }
                    else {
                        Intent i = new Intent(SplashActivity.this, DashboardActivity.class);

                        startActivity(i);
                        finish();
                        finish();overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    }



                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
