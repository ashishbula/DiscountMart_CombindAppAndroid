package in.discountmart.utility_services.recharge.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import in.discountmart.R;
import in.discountmart.utility_services.activity.MainActivity_utility;
import in.discountmart.utility_services.recharge.recharge_shared_preferance.RechargeSharedPreferance;

public class RechargeSucccessMsgActivity extends AppCompatActivity {

    TextView txtMobile;
    TextView txtAmount;
    TextView txtOpId;
    TextView txtTransId;
    TextView txtStatus;

    String serType="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_recharge_succcess_msg);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            txtAmount=(TextView)findViewById(R.id.recharge_success_act_txt_amount);
            txtMobile=(TextView)findViewById(R.id.recharge_success_act_txt_mobile);
            txtStatus=(TextView)findViewById(R.id.recharge_success_act_txt_status);
            txtOpId=(TextView)findViewById(R.id.recharge_success_act_txt_opratorId);
            txtTransId=(TextView)findViewById(R.id.recharge_success_act_txt_transId);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Recharge Success");
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

            serType= RechargeSharedPreferance.getServiceType(this);
            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                txtAmount.setText(" Recharge Amount of :-" +getResources().getString(R.string.rs_symbol) +" " +bundle.getString("Amount"));

                if(serType.equals("D")){
                    txtMobile.setText(" Registered Card/Mobile No. :- "+bundle.getString("Mobile"));
                }
                else {
                    txtMobile.setText(" Mobile number :- "+bundle.getString("Mobile"));
                }


                txtStatus.setText(" Recharge Starus :- "+bundle.getString("Status"));
                txtOpId.setText(" Operator ID :-  "+bundle.getString("OpId"));
                txtTransId.setText("Transaction Id :- "+bundle.getString("TId"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        Intent intent=new Intent(RechargeSucccessMsgActivity.this, MainActivity_utility.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
        Intent intent=new Intent(RechargeSucccessMsgActivity.this, MainActivity_utility.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        finish();
        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {

            onBackPressed();
            //Toast.makeText(BillPaymentSuccessMsgActivity.this,"Please Click on Top Close Button",Toast.LENGTH_LONG).show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
