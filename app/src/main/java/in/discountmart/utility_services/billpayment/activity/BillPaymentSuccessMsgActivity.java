package in.discountmart.utility_services.billpayment.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import in.discountmart.R;
import in.discountmart.utility_services.activity.MainActivity_utility;


public class BillPaymentSuccessMsgActivity extends AppCompatActivity {

    TextView txtMsg;
    TextView txtAmount;
    TextView txtTrasId;
    TextView txtService;
    TextView txtServiceType;

    String amount="";
    String service="";
    String msg="";
    String transid="";
    String serviceType="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bill_payment_success_msg);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            txtMsg=(TextView)findViewById(R.id.billpay_success_txt_msg);
            txtAmount=(TextView)findViewById(R.id.billpay_success_txt_amount);
            txtTrasId=(TextView)findViewById(R.id.billpay_success_txt_transid);
            txtService=(TextView)findViewById(R.id.billpay_success_txt_serviceid);
            txtServiceType=(TextView)findViewById(R.id.billpay_success_txt_serviceType);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Bill Payment Success");
            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                txtAmount.setText(" Bill Payment Amount of :-  " +getResources().getString(R.string.rs_symbol) +" " +bundle.getString("Amount"));
                txtMsg.setText(bundle.getString("Msg"));
                txtTrasId.setText("Transaction id of :-  " +bundle.getString("TransId"));
                txtService.setText("Service of :-  " +bundle.getString("ServiceName"));
                txtServiceType.setText(bundle.getString("ServiceType"));
                Toast.makeText(this,"Your bill request submitted successful, will be processed in 24-48 hours, Thanks", Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        Intent intent=new Intent(BillPaymentSuccessMsgActivity.this, MainActivity_utility.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        return true;
    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent(BillPaymentSuccessMsgActivity.this, MainActivity_utility.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }
}
