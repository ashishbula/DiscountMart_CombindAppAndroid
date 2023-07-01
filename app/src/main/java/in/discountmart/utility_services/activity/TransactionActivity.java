package in.discountmart.utility_services.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import in.discountmart.R;
import in.discountmart.utility_services.report.activity.LedgerReportActivity;

public class TransactionActivity extends AppCompatActivity {

    LinearLayout layoutUtility;
    LinearLayout layoutMain;
    LinearLayout layoutShop;
    LinearLayout layoutCashBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        try {
            layoutMain=(LinearLayout)findViewById(R.id.transaction_act_layout_main_wallet);
            layoutShop=(LinearLayout)findViewById(R.id.transaction_act_layout_shop_wallet);
            layoutUtility=(LinearLayout)findViewById(R.id.transaction_act_layout_utility_wallet);
            layoutCashBack=(LinearLayout)findViewById(R.id.transaction_act_layout_cashback);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Transaction History");

            /* Layout Main Wallet on click show main
            * wallet history*/
            layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent mainIntent = new Intent(TransactionActivity.this, CommonReportActivity.class);
//                    mainIntent.putExtra("Title", "Income Wallet Report");
//                    mainIntent.putExtra("Type","Wallet_M");
//                    startActivity(mainIntent);
//                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /* Layout Utility Wallet on click show Utility
             * wallet history*/
            layoutUtility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent fundIntent=new Intent(TransactionActivity.this, LedgerReportActivity.class);
                    startActivity(fundIntent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                }
            });

            /* Layout Shop Wallet on click show shop
             * wallet history*/
            layoutShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent shopIntent = new Intent(TransactionActivity.this, CommonReportActivity.class);
//                    shopIntent.putExtra("Title", "Main Wallet Report");
//                    shopIntent.putExtra("Type","Wallet_R");
//                    startActivity(shopIntent);
//                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /* Layout Shop Wallet on click show shop
             * wallet history*/
            layoutCashBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent shopIntent = new Intent(TransactionActivity.this, CommonReportActivity.class);
//                    shopIntent.putExtra("Title", "Referral Point Wallet");
//                    shopIntent.putExtra("Type","Wallet_P");
//                    startActivity(shopIntent);
//                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        return true;
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
       /* if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }*/
        super.onBackPressed();
        finish();
        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }
}