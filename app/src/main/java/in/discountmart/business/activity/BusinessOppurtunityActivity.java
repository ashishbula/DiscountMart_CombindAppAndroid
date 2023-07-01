package in.discountmart.business.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import in.discountmart.R;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.utility.AlertDialogUtils;

public class BusinessOppurtunityActivity extends AppCompatActivity {
    Button btnPDF;
    Button btnGallery;
    Button btnMeeting;
    Button btnRank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_activity_business_oppurtunity);

        try{
            BusinessOppurtunityActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            btnPDF = (Button) findViewById(R.id.login_btn_pdf);
            btnGallery = (Button) findViewById(R.id.login_btn_gallery);

            btnMeeting = (Button) findViewById(R.id.login_btn_meeting);
            btnRank = (Button) findViewById(R.id.login_btn_rank);

            btnPDF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Intent i = new Intent(BusinessOppurtunityActivity.this, WebViewActivity.class);
                    i.putExtra("URL", ApiConstants.pdf);
                    i.putExtra("Type","PDF");
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/
                    AlertDialogUtils.showDialogWithNeutaral(BusinessOppurtunityActivity.this, "Notification!", "Coming Soon..");
                }
            });

            btnMeeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialogUtils.showDialogWithNeutaral(BusinessOppurtunityActivity.this, "Notification!", "Coming Soon..");
                }
            });
            btnRank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialogUtils.showDialogWithNeutaral(BusinessOppurtunityActivity.this, "Notification!", "Coming Soon..");
                }
            });

            btnGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(BusinessOppurtunityActivity.this, WebViewActivity.class);
                    i.putExtra("URL", ApiConstants.gallery);
                    i.putExtra("Type","Gallery");
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
