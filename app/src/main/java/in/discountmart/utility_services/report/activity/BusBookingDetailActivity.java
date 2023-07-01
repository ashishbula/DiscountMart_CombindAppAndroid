package in.discountmart.utility_services.report.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.discountmart.R;
import in.discountmart.utility_services.report.report_model.response.BusBookReportResponse;

public class BusBookingDetailActivity extends AppCompatActivity {

    TextView txtBookDate;
    TextView txtStatus;
    TextView txtDepTime;
    TextView txtArrTime;
    TextView txtArrCity;
    TextView txtDepCity;
    TextView txtTravelName;
    TextView txtBusType;
    TextView txtTotSeat;
    TextView txtSeatNo;
    TextView txtTotAmnt;
    TextView txtPromoAmnt;
    TextView txtPNR;
    BusBookReportResponse.BusReportDetail busReportDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bus_booking_detail);
        try {
            txtBookDate=(TextView)findViewById(R.id.bus_reportdetail_act_txt_date);
            txtStatus=(TextView)findViewById(R.id.bus_reportdetail_act_txt_status);
            txtDepTime=(TextView)findViewById(R.id.bus_reportdetail_act_txt_dep_time);
            txtArrTime=(TextView)findViewById(R.id.bus_reportdetail_act_txt_arr_time);
            txtDepCity=(TextView)findViewById(R.id.bus_reportdetail_act_txt_dep_city);
            txtArrCity=(TextView)findViewById(R.id.bus_reportdetail_act_txt_arr_city);
            txtTravelName=(TextView)findViewById(R.id.bus_reportdetail_act_txt_travel_name);
            txtBusType=(TextView)findViewById(R.id.bus_reportdetail_act_txt_bus_type);
            txtTotSeat=(TextView)findViewById(R.id.bus_reportdetail_act_txt_totseat);
            txtSeatNo=(TextView)findViewById(R.id.bus_reportdetail_act_txt_seatno);
            txtTotAmnt=(TextView)findViewById(R.id.bus_reportdetail_act_txt_totamnt);
            txtPromoAmnt=(TextView)findViewById(R.id.bus_reportdetail_act_txt_pormo);
            txtPNR=(TextView)findViewById(R.id.bus_reportdetail_act_txt_pnr);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Bus Book Report Detail");

            /*get Value form Bundle intent*/
            Bundle bundle=getIntent().getExtras();

            if(bundle != null){
                busReportDetails=new BusBookReportResponse.BusReportDetail();
                busReportDetails= (BusBookReportResponse.BusReportDetail) bundle.getSerializable("BusReport");

                if(busReportDetails != null){
                    String strArrivalDate=convert(busReportDetails.getTDate());
                    txtStatus.setText("Booking Status : "+busReportDetails.getStatus());
                    txtBookDate.setText("Booking Date : "+strArrivalDate);
                    txtDepTime.setText("Departure Time : "+"\n"+busReportDetails.getDeptTime());
                    txtArrTime.setText("Arrival Time : "+"\n"+busReportDetails.getArrivalTime());
                    txtDepCity.setText("Source City : "+"\n"+busReportDetails.getSourceCity());
                    txtArrCity.setText("Destination City : " +"\n"+busReportDetails.getDestinationCity());
                    txtTravelName.setText(busReportDetails.getTravels());
                    txtBusType.setText(busReportDetails.getBusType());
                    if(busReportDetails.getPromoDiscount()!= null && !busReportDetails.getPromoDiscount().equals(""))
                        txtPromoAmnt.setText("Discount : "+getResources().getString(R.string.rs_symbol)+" " +busReportDetails.getPromoDiscount());
                    else
                        txtPromoAmnt.setText("");
                    txtTotAmnt.setText("Total Amount : "+getResources().getString(R.string.rs_symbol)+" " +busReportDetails.getTotalAmount());

                    if(busReportDetails.getPNR()!= null && !busReportDetails.getPNR().equals(""))
                        txtPNR.setText("PNR No : "+busReportDetails.getPNR());
                    else
                        txtPNR.setText("");
                    if(busReportDetails.getSeatName() != null && !busReportDetails.getSeatName().equals("")){
                        String setname[]=busReportDetails.getSeatName().split(",");
                        txtSeatNo.setText("Seat No. : "+busReportDetails.getSeatName());
                        if(setname != null &&setname.length > 0){
                            int count=0;
                            count=setname.length;
                            txtTotSeat.setText("Total Seats : "+String.valueOf(count));
                        }
                        else
                            txtTotSeat.setText("0");
                    }
                    else {
                        txtSeatNo.setText("");
                    }


                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String convert(String dateString) throws ParseException {
        System.out.println("Given date is " + dateString);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = sdf.parse(dateString);

       return new SimpleDateFormat("dd/MMM/yyyy").format(date);


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
