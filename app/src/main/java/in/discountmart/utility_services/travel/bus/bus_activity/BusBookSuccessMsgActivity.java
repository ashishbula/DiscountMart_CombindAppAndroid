package in.discountmart.utility_services.travel.bus.bus_activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

import in.discountmart.R;
import in.discountmart.utility_services.activity.MainActivity_utility;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusBookSuccessResponse;

public class BusBookSuccessMsgActivity extends AppCompatActivity {

    LinearLayout layoutFlightDetail;
    LinearLayout layoutPassengerDetail;
    TextView txtMsg;
    TextView txtPnrNo;
    TextView txtBookId;
    TextView txtTotalAmnt;
    TextView txtTravelName;
    TextView txtBusType;
    TextView txtFlightName;
    TextView txtBusFrom;
    TextView txtBusTo;
    TextView txtDepartTime;
    TextView txtArriveTime;
    FloatingActionButton fabHome;
    ImageView imgClose;

    String msg="";


    Button btnContinue;
    BusBookSuccessResponse successResponse;
    ArrayList<BusBookSuccessResponse> busBooksuccessList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bus_book_success_msg);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        try{

            txtBookId=(TextView)findViewById(R.id.bus_book_success_msg_act_txt_bookid);
            txtPnrNo=(TextView)findViewById(R.id.bus_book_success_msg_act_txt_pnr);
            txtTotalAmnt=(TextView)findViewById(R.id.bus_book_success_msg_act_txt_total_amnt);
            txtTravelName=(TextView)findViewById(R.id.bus_book_success_msg_act_txt_travel_name);
            txtMsg=(TextView)findViewById(R.id.bus_book_success_msg_act_txt_msg);
            txtBusType=(TextView)findViewById(R.id.bus_book_success_msg_act_txt_bus_type);
            txtDepartTime=(TextView)findViewById(R.id.bus_book_success_msg_act_txt_depart_time);
            txtArriveTime=(TextView)findViewById(R.id.bus_book_success_msg_act_txt_arrival_time);
            txtBusFrom=(TextView)findViewById(R.id.bus_book_success_msg_act_txt_from);
            txtBusTo=(TextView)findViewById(R.id.bus_book_success_msg_act_txt_to);
            fabHome=(FloatingActionButton)findViewById(R.id.bus_book_success_msg_act_floatbtn_print);
            imgClose=(ImageView)findViewById(R.id.bus_book_succes_act_imag_close);
            layoutFlightDetail=(LinearLayout)findViewById(R.id.bus_book_success_msg_act_layout_bus_detail);
            layoutPassengerDetail=(LinearLayout)findViewById(R.id.bus_book_success_msg_act_layout_passenger_detail);

            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                successResponse=new BusBookSuccessResponse();
                successResponse=(BusBookSuccessResponse)bundle.getSerializable("SuccessBook");
                msg=bundle.getString("Msg");

                busBooksuccessList= new ArrayList<BusBookSuccessResponse>(Arrays.asList(successResponse));
                //passengerDetailsList=new ArrayList<FlightBookSuccessResponse.PassengerDetail>(Arrays.asList(passengerDetail));
                if(busBooksuccessList.size() > 0){
                    for (int i=0;i < busBooksuccessList.size(); i++){
                        String pnr=busBooksuccessList.get(i).getPNR();
                        String bookId=busBooksuccessList.get(i).getBookingID();
                        String totAmount=busBooksuccessList.get(i).getTotalAmount();
                        String TravelName=busBooksuccessList.get(i).getTravels();
                        String BusType=busBooksuccessList.get(i).getBusType();
                        String origin=busBooksuccessList.get(i).getSource();
                        String destination=busBooksuccessList.get(i).getDestination();
                        String departeDate=busBooksuccessList.get(i).getDepartureDateTime();
                        String bookDate=busBooksuccessList.get(i).getBookedDate();
                        String arriveDate=busBooksuccessList.get(i).getDepartureDateTime();
                        String status=busBooksuccessList.get(i).getStatus();


                        txtBookId.setText(getResources().getString(R.string.str_bus_book_id) + " " +bookId);
                        txtPnrNo.setText("PNR No. - "+pnr);
                        txtTotalAmnt.setText(getResources().getString(R.string.str_bus_book_amount)+" "+ getResources().getString(R.string.rs_symbol)+" "+totAmount);
                        txtTravelName.setText(TravelName);
                        txtMsg.setText(msg);
                        txtBusType.setText(BusType);
                        txtDepartTime.setText(departeDate);
                        txtArriveTime.setText(arriveDate);
                        txtBusFrom.setText("From - "+origin);
                        txtBusTo.setText("To - "+destination);
                    }
                }
                txtMsg.setText(msg);

            }


            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(BusBookSuccessMsgActivity.this, MainActivity_utility.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        Intent intent=new Intent(BusBookSuccessMsgActivity.this, MainActivity_utility.class);
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
        Intent intent=new Intent(BusBookSuccessMsgActivity.this, MainActivity_utility.class);
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
