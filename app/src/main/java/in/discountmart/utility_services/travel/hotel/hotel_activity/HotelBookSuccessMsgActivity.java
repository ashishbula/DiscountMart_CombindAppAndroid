package in.discountmart.utility_services.travel.hotel.hotel_activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import in.discountmart.R;
import in.discountmart.utility_services.activity.MainActivity_utility;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelBookResponse;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelOnlineBookResponse;

public class HotelBookSuccessMsgActivity extends AppCompatActivity {

    TextView txtStatus;
    TextView txtStatus1;
    TextView txtHotelName;
    TextView txtHotelName1;
    TextView txtRoomType;
    TextView txtCity;
    TextView txtCity1;
    TextView txtTransId;
    TextView txtBookingId;
    TextView txtBookingStatus;
    TextView txtConfirmNo;
    TextView txtTotAmount;
    TextView txtTotRoom;
    TextView txtCheckInDate;
    TextView txtCheckOutDate;
    TextView txtBookingDate;
    LinearLayout layoutOffline;
    LinearLayout layoutOnline;

    HotelBookResponse bookResponse;
    HotelOnlineBookResponse bookOnlineResponse;
    String status="";
    String city="";
    String hotelName="";
    String transId="";
    String msg="";
    String book="";
    String bookID="";
    String confirmID="";
    String checkInDate="";
    String checkOutDate="";
    String bookDate="";
    String roomType="";
    String amount="";
    String totRoom="";
    String convertCheckInDate="";
    String convertCheckOutDate="";
    String convertbookDate="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_hotel_book_success_msg);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        try {

            txtCity=(TextView)findViewById(R.id.hotel_book_success_msg_success_act_txt_city);
            txtHotelName=(TextView)findViewById(R.id.hotel_book_success_msg_act_txt_hotelname);
            txtStatus=(TextView)findViewById(R.id.hotel_book_success_msg_act_txt_status);
            txtTransId=(TextView)findViewById(R.id.hotel_book_success_msg_act_txt_transId);

            /*view of content for online book*/
            txtHotelName1=(TextView)findViewById(R.id.online_hotel_book_success_msg_act_txt_hotelname);
            txtCity1=(TextView)findViewById(R.id.online_hotel_book_success_msg_success_act_txt_city);
            txtRoomType=(TextView)findViewById(R.id.online_hotel_book_success_msg_success_act_txt_room);
            txtStatus1=(TextView)findViewById(R.id.online_hotel_book_success_msg_act_txt_status);
            txtBookingDate=(TextView)findViewById(R.id.online_hotel_book_success_msg_act_txt_bookdate);
            txtCheckInDate=(TextView)findViewById(R.id.online_hotel_book_success_msg_act_txt_checkIndate);
            txtCheckOutDate=(TextView)findViewById(R.id.online_hotel_book_success_msg_act_txt_checkOutdate);
            txtBookingId=(TextView)findViewById(R.id.online_hotel_book_success_msg_act_txt_bookId);
            txtConfirmNo=(TextView)findViewById(R.id.online_hotel_book_success_msg_act_txt_confNo);
            txtTotAmount=(TextView)findViewById(R.id.online_hotel_book_success_msg_success_act_txt_price);
            txtTotRoom=(TextView)findViewById(R.id.online_hotel_book_success_msg_success_act_txt_totRoom);
            layoutOffline=(LinearLayout)findViewById(R.id.layout_for_offline);
            layoutOnline=(LinearLayout)findViewById(R.id.layout_for_online);

            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                book=bundle.getString("Book");

                assert book != null;

                /*Set content for offline booking response*/
                if(book.contentEquals("Offline")){

                    layoutOffline.setVisibility(View.VISIBLE);
                    layoutOnline.setVisibility(View.GONE);
                    bookResponse=new HotelBookResponse();
                    bookResponse=(HotelBookResponse)bundle.getSerializable("SuccessBook");
                    msg=bundle.getString("Msg");

                    city=bookResponse.getCity();
                    hotelName=bookResponse.getHotelName();
                    status=bookResponse.getStatus();
                    transId=bookResponse.getTransactionID();

                    /*Set Vlaue in text*/
                    txtHotelName.setText("Hotel Name : "+hotelName);
                    txtCity.setText("City : "+city);
                    txtTransId.setText("Transaction ID : "+transId);
                    txtStatus.setText("Status : "+status);

                }

                /*Set content for online booking response*/
                else {
                    layoutOffline.setVisibility(View.GONE);
                    layoutOnline.setVisibility(View.VISIBLE);
                    bookOnlineResponse=new HotelOnlineBookResponse();
                    bookOnlineResponse=(HotelOnlineBookResponse)bundle.getSerializable("SuccessBook");
                    if(bookOnlineResponse != null){
                        city=bookOnlineResponse.getCity();
                        hotelName=bookOnlineResponse.getHotelName();
                        status=bookOnlineResponse.getHotelBookingStatus();
                        bookID=bookOnlineResponse.getBookingID();
                        roomType=bookOnlineResponse.getRoomType();
                        bookDate=bookOnlineResponse.getBookingDate();
                        checkInDate=bookOnlineResponse.getCheckInDate();
                        checkOutDate=bookOnlineResponse.getCheckOutDate();
                        amount=bookOnlineResponse.getPrice();
                        totRoom=bookOnlineResponse.getNoOfRooms();

                        DateFormat recformet=new SimpleDateFormat("mm/dd/yyyy HH:mm:ss", Locale.US);
                        DateFormat convertFormet=new SimpleDateFormat("dd-MMM-yyyy ",Locale.US);
                        Date date1=null;
                        Date date2=null;
                        Date date3=null;

                        try {
                            date1=recformet.parse(checkInDate);
                            date2=recformet.parse(checkOutDate);
                            date3=recformet.parse(bookDate);
                            convertCheckInDate=convertFormet.format(date1);
                            convertCheckOutDate=convertFormet.format(date2);
                            convertbookDate=convertFormet.format(date3);

                        }catch (Exception e){
                            e.printStackTrace();
                        }


                        if(roomType.contentEquals(""))
                            txtRoomType.setText("");
                        else
                            txtRoomType.setText("Room :- "+roomType);

                        if(totRoom.contentEquals(""))
                            txtTotRoom.setText("");
                        else
                            txtTotRoom.setText("No.Of Room :- "+totRoom);

                        if(hotelName.contentEquals(""))
                            txtHotelName1.setText("");
                        else
                            txtHotelName1.setText("Hotel Name :- "+hotelName);

                        if(city.contentEquals(""))
                            txtCity1.setText("");
                        else
                            txtCity1.setText("City :- "+city);

                        if(status.contentEquals(""))
                            txtStatus1.setText("");
                        else
                            txtStatus1.setText("Status :- "+status);

                        if(bookID.contentEquals(""))
                            txtBookingId.setText("");
                        else
                            txtBookingId.setText("Booking Id :- "+bookID);

                        if(confirmID.contentEquals(""))
                            txtConfirmNo.setText("");
                        else
                            txtConfirmNo.setText("Confirmation No. :- "+confirmID);

                        if(convertCheckInDate.contentEquals(""))
                            txtConfirmNo.setText("");
                        else
                            txtCheckInDate.setText("Check-In-Date :- "+convertCheckInDate);

                        if(convertCheckInDate.contentEquals(""))
                            txtCheckInDate.setText("");
                        else
                            txtCheckInDate.setText("Check-In-Date :- "+convertCheckInDate);

                        if(convertCheckOutDate.contentEquals(""))
                            txtCheckOutDate.setText("");
                        else
                            txtCheckOutDate.setText("Check-Out-Date :- "+convertCheckOutDate);

                        if(convertbookDate.contentEquals(""))
                            txtBookingDate.setText("");
                        else
                            txtBookingDate.setText("Booking Date :- "+convertbookDate);

                        if(convertbookDate.contentEquals(""))
                            txtBookingDate.setText("");
                        else
                            txtBookingDate.setText("Booking Date :- "+convertbookDate);

                        if(amount.contentEquals(""))
                            txtTotAmount.setText("");
                        else
                            txtTotAmount.setText("Booking Amount :- "+amount);

                    }

                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        Intent intent=new Intent(HotelBookSuccessMsgActivity.this, MainActivity_utility.class);
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
        Intent intent=new Intent(HotelBookSuccessMsgActivity.this, MainActivity_utility.class);
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
