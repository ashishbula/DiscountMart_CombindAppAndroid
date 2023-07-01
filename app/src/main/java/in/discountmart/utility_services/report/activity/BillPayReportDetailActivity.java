package in.discountmart.utility_services.report.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.report.report_model.response.BillPaymentReportResponse;

public class BillPayReportDetailActivity extends AppCompatActivity {
    TextView txtStatus;
    TextView txtApprove;
    TextView txtStatusNote;
    TextView txtTransId;
    TextView txtOperatorId;
    TextView txtServiceType;
    TextView txtServiceName;
    TextView txtServiceOperator;
    TextView txtServiceNumber;
    TextView txtPrice;
    TextView txtDebitPrice;
    TextView txtDate;
    ImageView imgServiceType;
    ImageView imgWallet;
    ArrayList<BillPaymentReportResponse.BillPayment> reportArrayList;
    BillPaymentReportResponse.BillPayment billPayReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bill_pay_report_detail);

        try {
            txtStatusNote=(TextView)findViewById(R.id.billpay_detail_act_txt_statusnote);
            txtStatus=(TextView)findViewById(R.id.billpay_detail_act_txt_status);
            txtApprove=(TextView)findViewById(R.id.billpay_detail_act_txt_approve);
            //txtTransId=(TextView)findViewById(R.id.billpay_detail_act_txt_transid);
            txtOperatorId=(TextView)findViewById(R.id.billpay_detail_act_txt_operatorid);
            //txtServiceNumber=(TextView)findViewById(R.id.billpay_detail_act_txt_service_number);
            txtServiceOperator=(TextView)findViewById(R.id.billpay_detail_act_txt_service_operator);
            txtServiceType=(TextView)findViewById(R.id.billpay_detail_act_txt_service_type);
            txtPrice=(TextView)findViewById(R.id.billpay_detail_act_txt_price);
            txtDate=(TextView)findViewById(R.id.billpay_detail_act_txt_date);
            txtDebitPrice=(TextView)findViewById(R.id.billpay_detail_act_txt_debitamount);
            imgServiceType=(ImageView)findViewById(R.id.billpay_detail_act_img);
            imgWallet=(ImageView)findViewById(R.id.billpay_detail_act_img_wallet);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Bill Payment Detail");

            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                billPayReport=(BillPaymentReportResponse.BillPayment)bundle.getSerializable("BillPay");
                if(billPayReport != null){
                    if(billPayReport.getServiceType().contentEquals("Electricity")){
                        txtServiceType.setText("Electricity bill paid");
                        txtServiceOperator.setText(billPayReport.getService());
                        //txtServiceName.setText(billPayReport.getMobileOperator());
                        txtStatusNote.setText("Electricity bill pay :- ");
                        txtStatus.setText(billPayReport.getUserStatus());
                        txtApprove.setText(billPayReport.getIsApprove());
                        imgServiceType.setImageResource(R.mipmap.utility_ic_electricity);

                    }
                    else if(billPayReport.getServiceType().contentEquals("Insurance")){
                        txtServiceType.setText("Insurance bill Paid");
                        txtServiceOperator.setText(billPayReport.getService());
                        //txtServiceName.setText(billPayReport.getMobileOperator());
                        txtStatusNote.setText("Insurance Bill Pay :- ");
                        txtStatus.setText(billPayReport.getUserStatus());
                        txtApprove.setText(billPayReport.getIsApprove());
                        imgServiceType.setImageResource(R.mipmap.utility_ic_insurance);

                    }
                    else if(billPayReport.getService().contentEquals("Gas")){
                        txtServiceType.setText("Gas bill paid");
                        txtServiceOperator.setText(billPayReport.getService());
                        txtStatusNote.setText("Gas bill pay :- ");
                        txtStatus.setText(billPayReport.getUserStatus());
                        txtApprove.setText(billPayReport.getIsApprove());
                        imgServiceType.setImageResource(R.mipmap.utility_ic_gas);

                    }
                    else if(billPayReport.getService().contentEquals("Credit Card")){
                        txtServiceType.setText("Credit card bill paid");
                        txtServiceOperator.setText(billPayReport.getService());
                        txtStatusNote.setText("Credit card bill pay :- ");
                        txtStatus.setText(billPayReport.getUserStatus());
                        txtApprove.setText(billPayReport.getIsApprove());
                        imgServiceType.setImageResource(R.mipmap.utility_ic_credit);

                    }

                    //txtTransId.setText(billPayReport.getTID());
                    txtOperatorId.setText(billPayReport.getOperatorId());
                    txtDebitPrice.setText(getResources().getString(R.string.rs_symbol)+ " "+billPayReport.getAmount());
                    imgWallet.setImageResource(R.drawable.ic_wallet);
                    txtPrice.setText(getResources().getString(R.string.rs_symbol)+ " "+billPayReport.getAmount());
                    txtDate.setText(billPayReport.getReqDate());

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
