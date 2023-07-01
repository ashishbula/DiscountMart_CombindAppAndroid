package in.discountmart.utility_services.report.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import in.discountmart.R;
import in.discountmart.utility_services.report.report_model.response.LedgerReportResponse;

public class LedgerReportDetailActivity extends AppCompatActivity {

    TextView txtDate;
    TextView txtStatus;
    TextView txtUserName;
    TextView txtMemName;
    TextView txtServiceType;
    TextView txtRef_AcNo;
    TextView txtRef_AcName;
    TextView txtCompanyName;
    TextView txtDrCr;
    TextView txtBal;
    TextView txtRemark;
    TextView txtTransId;
    LinearLayout layoutServie;
    LedgerReportResponse.LedgerReport ledgerReport;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledger_report_detail);

        try {
            view=findViewById(android.R.id.content);
            txtDate=(TextView)findViewById(R.id.ledger_detail_act_txt_date);
            txtStatus=(TextView)findViewById(R.id.ledger_detail_act_txt_status);
            txtUserName=(TextView)findViewById(R.id.ledger_detail_act_txt_username);
            txtMemName=(TextView)findViewById(R.id.ledger_detail_act_txt_memname);
            txtServiceType=(TextView)findViewById(R.id.ledger_detail_act_txt_service_type);
            txtRef_AcNo=(TextView)findViewById(R.id.ledger_detail_act_txt_refno);
            txtRef_AcName=(TextView)findViewById(R.id.ledger_detail_act_txt_refname);
            txtCompanyName=(TextView)findViewById(R.id.ledger_detail_act_txt_comp_name);
            txtDrCr=(TextView)findViewById(R.id.ledger_detail_act_txt_drcr);
            txtBal=(TextView)findViewById(R.id.ledger_detail_act_txt_balance);
            txtRemark=(TextView)findViewById(R.id.ledger_detail_act_txt_remark);
            txtTransId=(TextView)findViewById(R.id.ledger_detail_act_txt_transid);
            layoutServie=(LinearLayout)findViewById(R.id.ledger_detail_act_layout_service);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Ledger Report Detail");

            /*Get Value from bundle*/
            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                ledgerReport=new LedgerReportResponse.LedgerReport();
                ledgerReport=(LedgerReportResponse.LedgerReport)bundle.getSerializable("Ledger");

                if(ledgerReport != null){
                    txtDate.setText(ledgerReport.getTransactionDTime());
                    txtStatus.setText(ledgerReport.getStatus());
                    txtUserName.setText(ledgerReport.getUserName());
                    txtMemName.setText(ledgerReport.getMemName());
                    txtCompanyName.setText(ledgerReport.getCompany());


                    if(!ledgerReport.getServiceType().equals("")){
                        layoutServie.setVisibility(View.VISIBLE);
                        txtServiceType.setText(ledgerReport.getServiceType());
                        if(ledgerReport.getServiceType().contains("PREPAID") ||ledgerReport.getServiceType().contains("POSTPAID")){
                            txtRef_AcName.setText("Mobile No.");
                            txtRef_AcNo.setText(ledgerReport.getAccountNo());
                        }
                        else {
                            txtRef_AcName.setText("Ref/Ac No.");
                            txtRef_AcNo.setText(ledgerReport.getAccountNo());
                        }
                    }
                    else {
                        layoutServie.setVisibility(View.GONE);
                    }

                    if(ledgerReport.getStatus().contains("Debit")){
                        txtDrCr.setText("Debit : - "+getResources().getString(R.string.rs_symbol)+" "+ledgerReport.getDebit());
                    }
                    else {
                        txtDrCr.setText("Credit : - "+getResources().getString(R.string.rs_symbol)+" "+ledgerReport.getCredit());
                    }

                    txtBal.setText("Balance :- "+getResources().getString(R.string.rs_symbol)+" "+ledgerReport.getBalalnce());
                    txtRemark.setText(ledgerReport.getTransactionRemark());
                    txtTransId.setText(ledgerReport.getTID());
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
