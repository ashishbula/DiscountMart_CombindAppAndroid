package in.discountmart.utility_services.report.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.report.report_model.response.RechargeReportResponse;

public class RechargeReportDetailActivity extends AppCompatActivity {

    TextView txtStatus;
    TextView txtStatusNote;
    TextView txtTransId;
    TextView txtOperatorId;
    TextView txtServiceType;
    TextView txtServiceName;
    TextView txtServiceNumber;
    TextView txtPrice;
    TextView txtDebitPrice;
    TextView txtDate;
    ImageView imgServiceType;
    ImageView imgWallet;
    /*Model Object*/
    ArrayList<RechargeReportResponse.RechargeReport> reportArrayList;
    RechargeReportResponse.RechargeReport rechargeReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_recharge_report_detail);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            txtStatusNote=(TextView)findViewById(R.id.recharge_detail_act_txt_statusnote);
            txtStatus=(TextView)findViewById(R.id.recharge_detail_act_txt_status);
            txtTransId=(TextView)findViewById(R.id.recharge_detail_act_txt_transid);
            txtOperatorId=(TextView)findViewById(R.id.recharge_detail_act_txt_operatorid);
            txtServiceNumber=(TextView)findViewById(R.id.recharge_detail_act_txt_service_number);
            txtServiceName=(TextView)findViewById(R.id.recharge_detail_act_txt_service_name);
            txtServiceType=(TextView)findViewById(R.id.recharge_detail_act_txt_service_type);
            txtPrice=(TextView)findViewById(R.id.recharge_detail_act_txt_price);
            txtDate=(TextView)findViewById(R.id.recharge_detail_act_txt_date);
            txtDebitPrice=(TextView)findViewById(R.id.recharge_detail_act_txt_debitamount);
            imgServiceType=(ImageView)findViewById(R.id.recharge_detail_act_img);
            imgWallet=(ImageView)findViewById(R.id.recharge_detail_act_img_wallet);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Recharge Detail");

            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                rechargeReport=(RechargeReportResponse.RechargeReport)bundle.getSerializable("Recharge");
                if(rechargeReport != null){
                   if(rechargeReport.getService().contentEquals("PREPAID")){
                        txtServiceType.setText("Mobile recharge");
                        txtServiceNumber.setText(rechargeReport.getMobileNo());
                        txtServiceName.setText(rechargeReport.getMobileOperator());
                        txtStatusNote.setText("Mobile Recharge :- ");
                        txtStatus.setText(rechargeReport.getStatus());
                        imgServiceType.setImageResource(R.mipmap.utility_ic_prepaid);

                   }
                   else if(rechargeReport.getService().contentEquals("POSTPAID")){
                       txtServiceType.setText("Postpaid Bill Pay");
                       txtServiceNumber.setText(rechargeReport.getMobileNo());
                       txtServiceName.setText(rechargeReport.getMobileOperator());
                       txtStatusNote.setText("Postpaid Bill Pay :- ");
                       txtStatus.setText(rechargeReport.getStatus());
                       imgServiceType.setImageResource(R.mipmap.utility_ic_postpaid);

                   }
                   else if(rechargeReport.getService().contentEquals("DTH")){
                       txtServiceType.setText("DTH recharge");
                       txtServiceNumber.setText(rechargeReport.getMobileNo());
                       txtServiceName.setText(rechargeReport.getMobileOperator());
                       txtStatusNote.setText("DTH Recharge :- ");
                       txtStatus.setText(rechargeReport.getStatus());
                       imgServiceType.setImageResource(R.mipmap.utility_ic_dth);

                   }

                   txtTransId.setText(rechargeReport.getTID());
                   txtOperatorId.setText(rechargeReport.getOperatorID());
                   txtDebitPrice.setText(getResources().getString(R.string.rs_symbol)+ " "+rechargeReport.getAmount());
                   imgWallet.setImageResource(R.drawable.ic_wallet);
                   txtPrice.setText(getResources().getString(R.string.rs_symbol)+ " "+rechargeReport.getAmount());
                   txtDate.setText(rechargeReport.getRechargeDate());

                }
            }



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

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {

            if(progressDialog.isShowing())
                progressDialog.dismiss();

            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }*/

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
