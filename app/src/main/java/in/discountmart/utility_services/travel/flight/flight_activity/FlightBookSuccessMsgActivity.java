package in.discountmart.utility_services.travel.flight.flight_activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import in.discountmart.R;
import in.discountmart.utility_services.activity.MainActivity_utility;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PessengerInfo;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightBookSuccessResponse;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightSearchResponse;
import in.discountmart.utility_services.travel.flight.flight_sharedpreference.FlightSharedValues;

public class FlightBookSuccessMsgActivity extends AppCompatActivity {

    LinearLayout layoutFlightDetail;
    LinearLayout layoutPassengerDetail;
    TextView txtMsg;
    TextView txtPnrNo;
    TextView txtBookNo;
    TextView txtTotalAmnt;
    TextView txtFlightCode;
    TextView txtFlightNo;
    TextView txtFlightName;
    TextView txtFlightFrom;
    TextView txtFlightTo;
    TextView txtDepartTime;
    TextView txtArriveTime;
    FloatingActionButton fabHome;
    ImageView imgClose;

    String msg="";


    Button btnContinue;

    ArrayList<PessengerInfo> pessengerInfoArrayList;
    ArrayList<FlightSearchResponse> selectFlightarrayList;
    ArrayList<FlightBookSuccessResponse> flightBookSuccessList;
    FlightBookSuccessResponse successResponse;
    FlightBookSuccessResponse.PassengerDetail passengerDetail[];
    ArrayList<FlightBookSuccessResponse.PassengerDetail> passengerDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_flight_book_success_msg);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        try{

            txtBookNo=(TextView)findViewById(R.id.flight_book_success_msg_act_txt_bookno);
            txtPnrNo=(TextView)findViewById(R.id.flight_book_success_msg_act_txt_pnr);
            txtTotalAmnt=(TextView)findViewById(R.id.flight_book_success_msg_act_txt_total_amnt);
            txtFlightNo=(TextView)findViewById(R.id.flight_book_success_msg_act_txt_flight_no);
            txtMsg=(TextView)findViewById(R.id.flight_book_success_msg_act_txt_msg);
            txtFlightName=(TextView)findViewById(R.id.flight_book_success_msg_act_txt_flight_name);
            txtDepartTime=(TextView)findViewById(R.id.flight_book_success_msg_act_txt_depart_time);
            txtArriveTime=(TextView)findViewById(R.id.flight_book_success_msg_act_txt_arrival_time);
            txtFlightFrom=(TextView)findViewById(R.id.flight_book_success_msg_act_txt_from);
            txtFlightTo=(TextView)findViewById(R.id.flight_book_success_msg_act_txt_to);
            fabHome=(FloatingActionButton)findViewById(R.id.flight_book_success_msg_act_floatbtn_print);
            imgClose=(ImageView)findViewById(R.id.flight_book_succes_act_imag_close);
            layoutFlightDetail=(LinearLayout)findViewById(R.id.flight_book_success_msg_act_layout_flight_detail);
            layoutPassengerDetail=(LinearLayout)findViewById(R.id.flight_book_success_msg_act_layout_passenger_detail);

            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                successResponse=new FlightBookSuccessResponse();
                successResponse=(FlightBookSuccessResponse)bundle.getSerializable("SuccessBook");
                msg=bundle.getString("Msg");
               // flightBookSuccessList= (ArrayList<FlightBookSuccessResponse>) bundle.getSerializable("PassengerInfo");
                passengerDetailsList= successResponse.getPassanger();
               // pessengerInfoArrayList=new ArrayList<PessengerInfo>();
                flightBookSuccessList= new ArrayList<FlightBookSuccessResponse>(Arrays.asList(successResponse));
                //passengerDetailsList=new ArrayList<FlightBookSuccessResponse.PassengerDetail>(Arrays.asList(passengerDetail));
                if(flightBookSuccessList.size() > 0){
                    for (int i=0;i < flightBookSuccessList.size(); i++){
                        String pnr=flightBookSuccessList.get(i).getPnrNo();
                        String bookNo=flightBookSuccessList.get(i).getBookingID();
                        String totAmount=flightBookSuccessList.get(i).getTotalAmount();
                        String flightNo=flightBookSuccessList.get(i).getFlightNumber();
                        String flightCode=flightBookSuccessList.get(i).getAirlineCode();
                        String origin=flightBookSuccessList.get(i).getOrigin();
                        String destination=flightBookSuccessList.get(i).getDestination();
                        String departeDate=flightBookSuccessList.get(i).getDepartureDate();
                        String arriveDate=flightBookSuccessList.get(i).getArrivalDate();


                        txtFlightNo.setText(getResources().getString(R.string.str_flight_no_code)+flightNo +""+flightCode);
                        txtTotalAmnt.setText("Booking Amount "+ getResources().getString(R.string.rs_symbol)+" "+totAmount);
                        txtPnrNo.setText("PNR No. - "+pnr);
                        txtBookNo.setText("Book No. - "+bookNo);
                        txtDepartTime.setText(getResources().getString(R.string.str_flight_depart_time_date)+departeDate);
                        txtArriveTime.setText(getResources().getString(R.string.str_flight_arrive_time_date)+arriveDate);
                        txtFlightFrom.setText("From - "+origin);
                        txtFlightTo.setText("To - "+destination);
                    }
                }
                txtMsg.setText(msg);

            }
           //pessengerInfoArrayList=FlightSharedValues.getInstance().pessengerInfoArrayList;
           /* if(flightBookSuccessList.size() > 0){

            }*/
            if(passengerDetailsList.size() > 0){
                showPassengerDetail(passengerDetailsList);
            }

            selectFlightarrayList= FlightSharedValues.getInstance().flightSelectArrayList;
            /*if(selectFlightarrayList.size() > 0){

                showSelectFlightDetail(selectFlightarrayList);
            }*/

            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(FlightBookSuccessMsgActivity.this, MainActivity_utility.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /*Method for passenger detail*/
    public void showPassengerDetail(ArrayList<FlightBookSuccessResponse.PassengerDetail> infoList){

        if(layoutPassengerDetail != null)
            layoutPassengerDetail.removeAllViews();
        for (int i = 0; i < infoList.size(); i++) {

            LayoutInflater mInflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mInflator.inflate(R.layout.utility_flight_book_success_passenger_detail, null);

            TextView txtPassengeTitle;
            TextView txtPassengerCount;
            TextView txtPassengerName;
            TextView txtPassengerLastName;

            txtPassengeTitle=(TextView)view.findViewById(R.id.flight_book_success_act_txt_passenger_title);
            txtPassengerName=(TextView)view.findViewById(R.id.flight_book_success_act_txt_passenger_name);
            txtPassengerLastName=(TextView)view.findViewById(R.id.flight_book_success_act_txt_passenger_lastname);

            txtPassengeTitle.setText(infoList.get(i).getTitle());
            txtPassengerName.setText(infoList.get(i).getFirstName());
            txtPassengerLastName.setText(infoList.get(i).getLastName());




            layoutPassengerDetail.addView(view);
        }
    }

    /*Method for passenger detail*/
    public void showSelectFlightDetail(ArrayList<FlightSearchResponse> selectFlightarrayList){
        if(layoutFlightDetail != null)
            layoutFlightDetail.removeAllViews();


        for (int j=0; j < selectFlightarrayList.size(); j++){
            LayoutInflater mInflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mInflator.inflate(R.layout.utility_flight_book_final_detail_select_flight_item, null);

            ImageView imageViewFlightLogo;
            TextView txtFlightName;
            TextView txtFlightCode;
            TextView txtFromCity;
            TextView txtToCity;
            TextView txtDepartureTime;
            TextView txtDepartureDate;
            TextView txtArrivalTime;
            TextView txtArrivalDate;
            TextView txtTotalTime;
            TextView txtStop;

            imageViewFlightLogo=(ImageView)view.findViewById(R.id.flight_book_detail_act_flight_logo);
            txtFlightName=(TextView)view.findViewById(R.id.flight_book_detail_act_txt_flight_name);
            txtFromCity=(TextView)view.findViewById(R.id.flight_book_detail_act_txt_from_city);
            txtToCity=(TextView)view.findViewById(R.id.flight_book_detail_act_txt_to_city);
            txtDepartureTime=(TextView)view.findViewById(R.id.flight_book_detail_act_txt_departure_time);
            txtDepartureDate=(TextView)view.findViewById(R.id.flight_book_detail_act_txt_departure_date);
            txtTotalTime=(TextView)view.findViewById(R.id.flight_book_detail_act_txt_total_hour);
            txtStop=(TextView)view.findViewById(R.id.flight_book_detail_act_txt_stop);
            txtArrivalDate=(TextView)view.findViewById(R.id.flight_book_detail_act_txt_arrivel_date);
            txtArrivalTime=(TextView)view.findViewById(R.id.flight_book_detail_act_txt_arrivel_time);
            txtFlightCode=(TextView)view.findViewById(R.id.flight_book_detail_act_txt_flight_code);

            txtFlightName.setText(selectFlightarrayList.get(j).getAirlineName());
            txtFlightCode.setText(selectFlightarrayList.get(j).getAirlineCode());
            //imageViewFlightLogo.setImageResource(Integer.parseInt(selectFlightarrayList.get(j).getAirlineLogo()));
            Picasso.with(this).load(selectFlightarrayList.get(j).getAirlineLogo()).into(imageViewFlightLogo);
            txtFromCity.setText(selectFlightarrayList.get(j).getOrigin());
            txtDepartureDate.setText(selectFlightarrayList.get(j).getDepartureDateTime());
            txtDepartureTime.setText(selectFlightarrayList.get(j).getDepartureTime());
            txtTotalTime.setText(selectFlightarrayList.get(j).getDifferenceTime());
            txtStop.setText(selectFlightarrayList.get(j).getStopage());
            txtToCity.setText(selectFlightarrayList.get(j).getDestination());
            txtArrivalTime.setText(selectFlightarrayList.get(j).getArrivaltime());
            txtArrivalDate.setText(selectFlightarrayList.get(j).getArrivalDatetime());

            layoutFlightDetail.addView(view);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        Intent intent=new Intent(FlightBookSuccessMsgActivity.this, MainActivity_utility.class);
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
        Intent intent=new Intent(FlightBookSuccessMsgActivity.this, MainActivity_utility.class);
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
