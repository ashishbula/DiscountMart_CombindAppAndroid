package in.discountmart.utility_services.report.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.discountmart.R;
import in.discountmart.utility_services.activity.WebViewActivity;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.report.report_model.response.FlightReportResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;

public class FlightBookDetailActivity extends AppCompatActivity {
    TextView txtBookDate;
    TextView txtStatus;
    TextView txtDepTime;
    TextView txtDepDate;
    TextView txtArrTime;
    TextView txtArrDate;
    TextView txtArrCity;
    TextView txtDepCity;
    TextView txtTravelName;
    TextView txtflightType;
    TextView txtTotSeat;
    TextView txtSeatNo;
    TextView txtTotAmnt;
    TextView txtPromoAmnt;
    TextView txtBookId;
    TextView txtTransId;
    TextView txtTraceID;
    TextView txtMobileNo;
    TextView txtPassengerName;
    ImageView imgPrint;

    String bookingId="";

    ArrayList<FlightReportResponse.FlightReport> flightReportsList;
    FlightReportResponse.FlightReport flightReport;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_flight_book_detail);

        try {
            txtBookDate=(TextView)findViewById(R.id.flight_reportdetail_act_txt_date);
            txtStatus=(TextView)findViewById(R.id.flight_reportdetail_act_txt_status);
            txtDepTime=(TextView)findViewById(R.id.flight_reportdetail_act_txt_dep_time);
            txtDepDate=(TextView)findViewById(R.id.flight_reportdetail_act_txt_dep_date);
            txtArrTime=(TextView)findViewById(R.id.flight_reportdetail_act_txt_arr_time);
            txtArrDate=(TextView)findViewById(R.id.flight_reportdetail_act_txt_arr_date);
            txtDepCity=(TextView)findViewById(R.id.flight_reportdetail_act_txt_dep_city);
            txtArrCity=(TextView)findViewById(R.id.flight_reportdetail_act_txt_arr_city);
            txtTravelName=(TextView)findViewById(R.id.flight_reportdetail_act_txt_travel_name);
            txtflightType=(TextView)findViewById(R.id.flight_reportdetail_act_txt_flight_type);
            txtTotSeat=(TextView)findViewById(R.id.flight_reportdetail_act_txt_totseat);
            txtTotAmnt=(TextView)findViewById(R.id.flight_reportdetail_act_txt_totamnt);
            txtPromoAmnt=(TextView)findViewById(R.id.flight_reportdetail_act_txt_pormo);
            txtBookId=(TextView)findViewById(R.id.flight_reportdetail_act_txt_bookid);
            txtTransId=(TextView)findViewById(R.id.flight_reportdetail_act_txt_tid);
            txtTraceID=(TextView)findViewById(R.id.flight_reportdetail_act_txt_traceid);
            txtMobileNo=(TextView)findViewById(R.id.flight_reportdetail_act_txt_mobile);
            txtPassengerName=(TextView)findViewById(R.id.flight_reportdetail_act_txt_passenger_name);
            imgPrint=(ImageView)findViewById(R.id.flight_reportdetail_act_img_print);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("flight Book Report Detail");

            /*get Value form Bundle intent*/
            Bundle bundle=getIntent().getExtras();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss a");
            SimpleDateFormat receiveFormet = new SimpleDateFormat("hh:mm:ss");
           //String time = dateFormat.format(calander.getTime());
            if(bundle != null){
                flightReport=new FlightReportResponse.FlightReport();
                flightReport= (FlightReportResponse.FlightReport) bundle.getSerializable("FlightReport");

                if(flightReport != null){
                    //String strArrivalDate=convert(busReportDetails.getTDate());
                    Date depTime=receiveFormet.parse(flightReport.getDepartureTime());
                    Date arrTime=receiveFormet.parse(flightReport.getArrivalTime());
                    bookingId=flightReport.getBookingId();
                    txtStatus.setText(Html.fromHtml("<b>Booking Status : </b>"+flightReport.getStatus()));
                    txtBookDate.setText(Html.fromHtml("<b>Booking Date : </b>"+flightReport.getTrnsactionDate()));
                    txtBookId.setText(Html.fromHtml("<b>Booking ID : </b>"+flightReport.getBookingId()));
                    txtTransId.setText(Html.fromHtml("<b>Transaction ID : </b>"+flightReport.getTransId()));
                    txtDepTime.setText(Html.fromHtml("<b>Departure Time  </b>"+"\n\n <br>"+dateFormat.format(depTime)));
                    txtDepDate.setText(Html.fromHtml("<b>Departure Date  </b>"+"\n\n <br>"+flightReport.getDepartureDate()));
                    txtArrTime.setText(Html.fromHtml("<b>Arrival Time  </b>"+"\n\n <br>"+dateFormat.format(arrTime)));
                    txtArrDate.setText(Html.fromHtml("<b>Arrival Date  </b> "+"\n\n <br>"+flightReport.getArrivalDate()));
                    txtDepCity.setText(Html.fromHtml("<b>Source City  </b> " +"\n <br>"+flightReport.getOriginCity()+"("+flightReport.getOrigin()+")"));
                    txtArrCity.setText(Html.fromHtml("<b>Destination City  </b>" +"\n <br>"+flightReport.getDestinationCity()+"("+flightReport.getDestination()+")"));
                    txtMobileNo.setText(Html.fromHtml("<b>Mobile </b>" +"<br>"+flightReport.getMobileNo()));
                    txtPassengerName.setText(Html.fromHtml("<b>Passenger Name  </b>" +"\n <br>"+flightReport.getPassengerName()));
                    txtTravelName.setText(flightReport.getFlightName());
                    txtTotSeat.setText(Html.fromHtml("<b>No of Passenger : </b>"+flightReport.getNoOFPassenger()));
                    //txtflightType.setText(flightReport.getf());
//                    if(flightReport.getPromoDiscount()!= null && !flightReport.getPromoDiscount().equals(""))
//                        txtPromoAmnt.setText("Discount : "+getResources().getString(R.string.rs_symbol)+" " +flightReport.getPromoDiscount());
//                    else
//                        txtPromoAmnt.setText("");
                    txtTotAmnt.setText(Html.fromHtml("<b>Total Amount  :</b> "+getResources().getString(R.string.rs_symbol)+" " +flightReport.getFlightAmount()));




                }

                imgPrint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent intent=new Intent(FlightBookDetailActivity.this, WebViewActivity.class);

                            LoginResponse respon= new LoginPreferences_Utility(FlightBookDetailActivity.this).getLoggedinUser();
                            String strUrl=respon.getDomainName() +"/EticketPrintApp.aspx?BookingID="+flightReport.getBookingId();
                            Log.d("Flight Bill",strUrl);
                            //http://dtil.biztadka.com/EticketPrintApp.aspx?BookingID=51090348
                           /* Bundle bundle1=new Bundle();
                            bundle1.putString("Type","TicketF");
                            bundle1.putString("Title","Flight Ticket");
                            bundle1.putString("URL",strUrl);
                            //fragment.setArguments(bundle);
                            intent.putExtras(bundle1);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/

                            //String file=reportArrayList.get(position).getFileurl();
                            Uri path = Uri.parse(strUrl);
                            Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                           // pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            pdfOpenintent.setData(path);
                            try {
                                startActivity(pdfOpenintent);
                            }
                            catch (ActivityNotFoundException e) {
                                Toast.makeText(FlightBookDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }



                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

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