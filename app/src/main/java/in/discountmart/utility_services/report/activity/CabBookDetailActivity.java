package in.discountmart.utility_services.report.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import in.discountmart.R;
import in.discountmart.utility_services.report.report_model.response.CabReportResponse;

public class CabBookDetailActivity extends AppCompatActivity {

    TextView txtBookDate;
    TextView txtStatus;
    TextView txtDepTime;
    TextView txtArrTime;
    TextView txtArrCity;
    TextView txtDepCity;
    TextView txtTravelName;
    TextView txtcabType;
    TextView txtMobile;
    TextView txtMemberName;
    TextView txtPicAddress;
    TextView txtTotAmnt;
    TextView txtPromoAmnt;
    TextView txtBookId;
    TextView txtTransId;
    CabReportResponse.CabReport cabReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_cab_book_detail);

        try {
            txtBookDate=(TextView)findViewById(R.id.cab_reportdetail_act_txt_date);
            txtStatus=(TextView)findViewById(R.id.cab_reportdetail_act_txt_status);
            txtDepTime=(TextView)findViewById(R.id.cab_reportdetail_act_txt_dep_time);
            txtDepCity=(TextView)findViewById(R.id.cab_reportdetail_act_txt_dep_city);
            txtArrCity=(TextView)findViewById(R.id.cab_reportdetail_act_txt_arr_city);
            txtMobile=(TextView)findViewById(R.id.cab_reportdetail_act_txt_mobileno);
            txtMemberName=(TextView)findViewById(R.id.cab_reportdetail_act_txt_member_name);
            txtTravelName=(TextView)findViewById(R.id.cab_reportdetail_act_txt_travel_name);
            txtcabType=(TextView)findViewById(R.id.cab_reportdetail_act_txt_cab_type);
            txtMemberName=(TextView)findViewById(R.id.cab_reportdetail_act_txt_member_name);
            txtMobile=(TextView)findViewById(R.id.cab_reportdetail_act_txt_mobileno);
            txtTotAmnt=(TextView)findViewById(R.id.cab_reportdetail_act_txt_totamnt);
            txtPromoAmnt=(TextView)findViewById(R.id.cab_reportdetail_act_txt_pormo);
            txtBookId=(TextView)findViewById(R.id.cab_reportdetail_act_txt_pnr);
            txtTransId=(TextView)findViewById(R.id.cab_reportdetail_act_txt_tid);
            txtPicAddress=(TextView)findViewById(R.id.cab_reportdetail_act_txt_pic_address);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Cab Book Report Detail");

            /*get Value form Bundle intent*/
            Bundle bundle=getIntent().getExtras();

            if(bundle != null){
                cabReport=new CabReportResponse.CabReport();
                cabReport= (CabReportResponse.CabReport) bundle.getSerializable("CabReport");

                if(cabReport != null){
                    //String strArrivalDate=convert(busReportDetails.getTDate());
                    txtStatus.setText("Booking Status : "+cabReport.getStatus());
                    txtBookDate.setText("Booking Date : "+cabReport.getTransactionDate());
                    txtDepTime.setText("Departure Time : "+"\n"+cabReport.getDOJ());
                    //txtArrTime.setText("Arrival Time : "+"\n"+busReportDetails.getArrivalTime());
                    txtDepCity.setText("Source City : "+"\n"+cabReport.getSource());
                    txtArrCity.setText("Destination City : " +"\n"+cabReport.getDestination());
                    txtTravelName.setText(cabReport.getCabID());
                    txtcabType.setText(cabReport.getCabNo());
                    if(cabReport.getPromoDiscount()!= null && !cabReport.getPromoDiscount().equals(""))
                        txtPromoAmnt.setText("Discount : "+getResources().getString(R.string.rs_symbol)+" " +cabReport.getPromoDiscount());
                    else
                        txtPromoAmnt.setText("");
                    txtTotAmnt.setText("Total Amount : "+getResources().getString(R.string.rs_symbol)+" " +cabReport.getTotalAmount());

                    txtBookId.setText("Booking Ref. No : "+cabReport.getBookingRefNo());
                    txtTransId.setText("Booking Id. No : "+cabReport.getBookingId());
                    txtMobile.setText("Mobile No : "+cabReport.getMobileNo());
                    txtMemberName.setText("Mobile No : "+cabReport.getMemName());
                    txtPicAddress.setText("Pik-up Address : "+cabReport.getPickupAddress());
                    txtPicAddress.setText("Pik-up Address : "+cabReport.getPickupAddress());

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