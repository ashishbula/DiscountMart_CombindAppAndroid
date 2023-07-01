package in.discountmart.utility_services.travel.flight.flight_activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PessengerInfo;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightSearchResponse;
import in.discountmart.utility_services.travel.flight.flight_sharedpreference.FlightSharedValues;

public class FlightBookFinalDetailActivity extends AppCompatActivity {

    ImageView imageViewClose;
    ImageView imageEdit;

    LinearLayout layoutFlightDetail;
    LinearLayout layoutEdit;
    LinearLayout layoutPassengerDetail;

    Button btnContinue;

    ArrayList<PessengerInfo> pessengerInfoArrayList;
    ArrayList<FlightSearchResponse> selectFlightarrayList;
    ArrayList<PessengerInfo> pessengerFooterArrayList;
    int  totlePaidamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_flight_book_final_detail);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        try {

            imageViewClose=(ImageView)findViewById(R.id.flight_book_detail_act_imag_close);

            layoutPassengerDetail=(LinearLayout)findViewById(R.id.flight_book_detail_act_layout_passenger_detail);
            layoutFlightDetail=(LinearLayout)findViewById(R.id.flight_book_detail_act_layout_flight_detail);
            btnContinue=(Button)findViewById(R.id.flight_book_detail_act_btn_continue);

            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                pessengerInfoArrayList=new ArrayList<PessengerInfo>();
                pessengerInfoArrayList= (ArrayList<PessengerInfo>) bundle.getSerializable("PassengerInfo");


            }
            assert pessengerInfoArrayList != null;
            if(pessengerInfoArrayList.size() > 0){
                showPassengerDetail(pessengerInfoArrayList);
            }

            selectFlightarrayList= FlightSharedValues.getInstance().flightSelectArrayList;
            if(selectFlightarrayList.size() > 0){

                showSelectFlightDetail(selectFlightarrayList);
            }
            totlePaidamount=FlightSharedValues.getInstance().totPaidAmount;
            btnContinue.setText("Proceed to pay  " + getResources().getString(R.string.rs_symbol)+"" + String.valueOf(totlePaidamount));


            /*image close click listener*/
            imageViewClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

                }
            });

            /*Button Continue click listener*/
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(FlightBookFinalDetailActivity.this, FlightBookOtp_PaymentActivity.class);

                    Bundle bundle1=new Bundle();
                    bundle1.putSerializable("PassengerInfo",pessengerInfoArrayList);
                    intent.putExtras(bundle1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });





        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /*Method for passenger detail*/
    public void showPassengerDetail(ArrayList<PessengerInfo> infoList){

        if(layoutPassengerDetail != null)
            layoutPassengerDetail.removeAllViews();
        for (int i = 0; i < infoList.size(); i++) {

            LayoutInflater mInflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mInflator.inflate(R.layout.utility_flight_book_final_detail_passenger_item, null);

            TextView txtPassengerType;
            TextView txtPassengerCount;
            TextView txtPassengerName;
            TextView txtPassengerGender;
            TextView txtPassengerAge;

            txtPassengerType=(TextView)view.findViewById(R.id.flight_book_detail_act_txt_passenger_type);
            txtPassengerCount=(TextView)view.findViewById(R.id.flight_book_detail_act_txt_passenger_count);
            txtPassengerName=(TextView)view.findViewById(R.id.flight_book_detail_act_txt_passenger_name);
            txtPassengerGender=(TextView)view.findViewById(R.id.flight_book_detail_act_txt_passenger_gender);
            txtPassengerAge=(TextView)view.findViewById(R.id.flight_book_detail_act_txt_passenger_age);


           if(infoList.get(i).getPessengerType().equals("A")){
               txtPassengerType.setText("Adult");

               int adultcount=infoList.get(i).getCountAdults();
               txtPassengerCount.setText(String.valueOf(adultcount));
               txtPassengerName.setText(infoList.get(i).getNametitle()+"."+infoList.get(i).getName());
               txtPassengerGender.setText(infoList.get(i).getGender());
               txtPassengerAge.setText(infoList.get(i).getDob());
           }
           else if(infoList.get(i).getPessengerType().equals("C")){
                txtPassengerType.setText("Child");

                int childcount=infoList.get(i).getCountChilds();
                txtPassengerCount.setText(String.valueOf(childcount));
                txtPassengerName.setText(infoList.get(i).getNametitle()+"."+infoList.get(i).getName());
               txtPassengerGender.setText(infoList.get(i).getGender());
               txtPassengerAge.setText(infoList.get(i).getDob());
            }
           else if(infoList.get(i).getPessengerType().equals("I")){
               txtPassengerType.setText("Infant");

               int infantscount=infoList.get(i).getCountInfants();
               txtPassengerCount.setText(String.valueOf(infantscount));
               txtPassengerName.setText(infoList.get(i).getNametitle()+"."+infoList.get(i).getName());
               txtPassengerGender.setText(infoList.get(i).getGender());
               txtPassengerAge.setText(infoList.get(i).getDob());
           }
           else if(infoList.get(i).getPessengerType().equals("O")){
               break;
               //return;

           }



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
            txtFlightCode.setText(selectFlightarrayList.get(j).getAirlineCode()+ "-"+selectFlightarrayList.get(j).getFlightNumber());
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

    //Back Press Arrow o ToolBar
   /* @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        return true;
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
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

    }
}
