package in.discountmart.utility_services.travel.utility_cab.cab_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import in.discountmart.R;
import in.discountmart.utility_services.activity.MainActivity_utility;
import in.discountmart.utility_services.travel.utility_cab.cab_model.cab_response_model.CabBookResponse;

public class CabBookSuccessMsgActivity extends AppCompatActivity {
    LinearLayout layoutFlightDetail;
    LinearLayout layoutPassengerDetail;
    TextView txtMsg;
    TextView txtPnrNo;
    TextView txtBookId;
    TextView txtTotalAmnt;
    TextView txtTravelName;
    TextView txtcabType;
    TextView txtFlightName;
    TextView txtcabFrom;
    TextView txtcabTo;
    TextView txtDepartDate;
    TextView txtDepartTime;
    TextView txtArriveTime;
    FloatingActionButton fabHome;
    ImageView imgClose;

    String msg="";


    Button btnContinue;
    CabBookResponse successResponse;
   // ArrayList<cabBookSuccessResponse> cabBooksuccessList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_cab_book_success_msg);

        try{

            txtBookId=(TextView)findViewById(R.id.cab_book_success_msg_act_txt_bookid);
            txtPnrNo=(TextView)findViewById(R.id.cab_book_success_msg_act_txt_refno);
            txtTotalAmnt=(TextView)findViewById(R.id.cab_book_success_msg_act_txt_total_amnt);
            txtTravelName=(TextView)findViewById(R.id.cab_book_success_msg_act_txt_cab_name);
            txtMsg=(TextView)findViewById(R.id.cab_book_success_msg_act_txt_msg);
            txtcabType=(TextView)findViewById(R.id.cab_book_success_msg_act_txt_cab_type);
            txtDepartTime=(TextView)findViewById(R.id.cab_book_success_msg_act_txt_depart_time);
            txtDepartDate=(TextView)findViewById(R.id.cab_book_success_msg_act_txt_depart_date);
            txtcabFrom=(TextView)findViewById(R.id.cab_book_success_msg_act_txt_from);
            txtcabTo=(TextView)findViewById(R.id.cab_book_success_msg_act_txt_to);
            fabHome=(FloatingActionButton)findViewById(R.id.cab_book_success_msg_act_floatbtn_print);
            imgClose=(ImageView)findViewById(R.id.cab_book_succes_act_imag_close);
            layoutFlightDetail=(LinearLayout)findViewById(R.id.cab_book_success_msg_act_layout_cab_detail);
            layoutPassengerDetail=(LinearLayout)findViewById(R.id.cab_book_success_msg_act_layout_passenger_detail);

            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                successResponse=new CabBookResponse();
                successResponse=(CabBookResponse)bundle.getSerializable("SuccessBook");
                msg=bundle.getString("Msg");


                //passengerDetailsList=new ArrayList<FlightBookSuccessResponse.PassengerDetail>(Arrays.asList(passengerDetail));
                if(successResponse != null){

                        String pnr=successResponse.getBookingRefNo();
                        String bookId=successResponse.getID();
                        String totAmount=successResponse.getTotalAmount();
                        String TravelName=successResponse.getCar();
                        String cabType=successResponse.getType();
                        String origin=successResponse.getSource();
                        String destination=successResponse.getDestination();
                        String picuptime=successResponse.getPickupTime();
                        String bookDate=successResponse.getDOJ();
                       // String arriveDate=successResponse.getDepartureDateTime();
                        //String status=successResponse.getStatus();


                        txtBookId.setText("Book Id - "+ " " +bookId);
                        txtPnrNo.setText("Book Ref.No. - "+pnr);
                        txtTotalAmnt.setText(" "+ getResources().getString(R.string.rs_symbol)+" "+totAmount);
                        txtTravelName.setText(TravelName);
                        txtMsg.setText(msg);
                        txtcabType.setText(cabType);
                        txtDepartTime.setText(picuptime);
                        txtDepartDate.setText(bookDate);
                        txtcabFrom.setText("From - "+origin);
                        txtcabTo.setText("To - "+destination);

                }
                txtMsg.setText(msg);

            }


            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(CabBookSuccessMsgActivity.this, MainActivity_utility.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

        Intent intent=new Intent(CabBookSuccessMsgActivity.this, MainActivity_utility.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        Intent intent=new Intent(CabBookSuccessMsgActivity.this, MainActivity_utility.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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