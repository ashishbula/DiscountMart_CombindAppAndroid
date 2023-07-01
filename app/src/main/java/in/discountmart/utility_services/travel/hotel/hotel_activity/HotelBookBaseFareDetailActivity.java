package in.discountmart.utility_services.travel.hotel.hotel_activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.RoomDetail;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.RoomPriceDetail;
import in.discountmart.utility_services.travel.hotel.hotel_shared_preferance.HotelSharedValues;

public class HotelBookBaseFareDetailActivity extends AppCompatActivity {


    LinearLayout layoutAgent;
    LinearLayout layoutAgentcontent;
    LinearLayout layoutDiscount;
    LinearLayout layoutPromo;
    LinearLayout layoutPromoInvoice;


    TextView txtBaseFare;
    TextView txtTotRoom;
    TextView txtTotMember;
    TextView txtTaxes;
    TextView txtOtherCharge;
    TextView txtDiscount;
    TextView txtPromoDis;
    TextView txtMargin;
    TextView txtTotalFare;
    TextView txtTotalPaid;
    TextView txtAgDiscount;
    TextView txtAgPromo;
    TextView txtPromoDisNote;
    TextView txtDisNote;
    TextView txtInvoiceTotAmount;
    ImageView imgDown;


    int baseFareAmount=0;
    int taxesAmount=0;
    int otherChargeAmount=0;
    int totalFareAmount=0;
    int totalPaidAmount=0;
    float marginAmount=0;
    double commisionAmnt=0;
    int discountAmnt=0;
    int sendMarginAmnt=0;
    int totDiscountAmnt=0;
    int promoAmount=0;
    int totRoom=0;

    String strTotRoom="";
    String strTotMember="";

    String airlineCode="";

    ArrayList<RoomDetail> selectRoomList;
    RoomPriceDetail priceDetail;
    RoomDetail roomDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_hotel_book_base_fare_detail);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Base Fare Detail");

            layoutAgent=(LinearLayout)findViewById(R.id.hotel_book_info_act_layout_agent);
            layoutAgentcontent=(LinearLayout)findViewById(R.id.hotel_book_info_act_layout_agent_content);
            layoutDiscount=(LinearLayout)findViewById(R.id.hotel_book_info_act_layout_discount);
            layoutPromo=(LinearLayout)findViewById(R.id.hotel_book_info_act_layout_promo_dis);
            layoutPromoInvoice=(LinearLayout)findViewById(R.id.hotel_book_info_act_layout_promo_dis_invoice);


            txtBaseFare=(TextView)findViewById(R.id.hotel_book_info_act_txt_basefare);
            txtOtherCharge=(TextView)findViewById(R.id.hotel_book_info_act_txt_other_charge);
            txtTaxes=(TextView)findViewById(R.id.hotel_book_info_act_txt_taxes);
            txtTotalFare=(TextView)findViewById(R.id.hotel_book_info_act_txt_total_fare);
            txtTotalPaid=(TextView)findViewById(R.id.hotel_book_info_act_txt_total_paid);
            txtDiscount=(TextView)findViewById(R.id.hotel_book_info_act_txt_discount);
            txtPromoDis=(TextView)findViewById(R.id.hotel_book_info_act_txt_promo_discount);
            txtPromoDisNote=(TextView)findViewById(R.id.hotel_book_info_act_txt_promo_discount_note);
            txtDisNote=(TextView)findViewById(R.id.hotel_book_info_act_txt_discount_note);
            txtAgDiscount=(TextView)findViewById(R.id.hotel_book_info_act_txt_agent_discount);
            txtAgPromo=(TextView)findViewById(R.id.hotel_book_info_act_txt_agent_promo_discount);
            txtMargin=(TextView)findViewById(R.id.hotel_book_info_act_txt_margin);
            txtTotRoom=(TextView)findViewById(R.id.hotel_book_info_act_txt_totroom);
            txtTotMember=(TextView)findViewById(R.id.hotel_book_info_act_txt_member);
            txtInvoiceTotAmount=(TextView)findViewById(R.id.hotel_book_info_act_txt_total_invoice_Amount);
            imgDown=(ImageView)findViewById(R.id.hotel_book_info_act_image_downicon);

            /*Get Value from Hotel Shared Preference*/
            selectRoomList=new ArrayList<RoomDetail>();
            roomDetails=HotelSharedValues.getInstance().roomDetail;

            selectRoomList= HotelSharedValues.getInstance().roomDetailList;

            txtTotMember.setText("Member "+String.valueOf(HotelSharedValues.getInstance().totMember));
            txtTotRoom.setText("Room "+String.valueOf(HotelSharedValues.getInstance().totRoom+", "));
            totRoom=HotelSharedValues.getInstance().totRoom;

            /*Show Promo or not*/
           /* LoginResponse loginPreferences=new LoginResponse();
            loginPreferences = new LoginPreferences_brand(this).getLoggedinUser();
            if(loginPreferences != null){
                if(loginPreferences.getMemMode().equals("D")){
                    layoutPromo.setVisibility(View.VISIBLE);
                    layoutPromoInvoice.setVisibility(View.VISIBLE);
                    //txtPromoDisNote.setVisibility(View.VISIBLE);
                }
                else {
                    layoutPromo.setVisibility(View.GONE);
                    layoutPromoInvoice.setVisibility(View.GONE);
                    //txtPromoDisNote.setVisibility(View.GONE);
                }
            }*/
            if(roomDetails != null){
                if(roomDetails.getPrice() != null){
                    priceDetail=roomDetails.getPrice();

                    baseFareAmount=priceDetail.getRoomPrice()*totRoom;
                    totalFareAmount=priceDetail.getPublishedPrice()*totRoom;
                    taxesAmount=priceDetail.getTax()*totRoom;
                    otherChargeAmount=priceDetail.getOtherCharges()*totRoom;
                }
            }


            int totBaseFare=0;
            int totFareamnt=0;
            totBaseFare=baseFareAmount;
            totFareamnt=totBaseFare+taxesAmount+otherChargeAmount;

            if(totalFareAmount == totFareamnt) {

                discountAmnt = HotelSharedValues.getInstance().Discount;
                sendMarginAmnt = HotelSharedValues.getInstance().margin;
                totDiscountAmnt = HotelSharedValues.getInstance().totDiscount;

                //totalPaidAmount = totalFareAmount - totDiscountAmnt ;
                totalPaidAmount = totalFareAmount ;

                txtTotalPaid.setText(getResources().getString(R.string.rs_symbol) + "" + String.valueOf(totalPaidAmount));
                HotelSharedValues.getInstance().totPaidAmount = totalPaidAmount;


                txtBaseFare.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(baseFareAmount));
                txtTaxes.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(taxesAmount));
                txtOtherCharge.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(otherChargeAmount));
                txtTotalFare.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(totalFareAmount));

                /*Get Promocode Discount*/
                if(HotelSharedValues.getInstance().hotelPromoAmount.equals("") || HotelSharedValues.getInstance().hotelPromoAmount == null){

                    txtPromoDis.setText(getResources().getString(R.string.rs_symbol) + " " +"0");
                    txtPromoDisNote.setVisibility(View.GONE);
                    promoAmount = 0;

                }
                else {
                    txtPromoDis.setText(getResources().getString(R.string.rs_symbol) + " " +HotelSharedValues.getInstance().hotelPromoAmount);
                    txtPromoDisNote.setVisibility(View.VISIBLE);
                    promoAmount = Integer.parseInt(HotelSharedValues.getInstance().hotelPromoAmount);
                }
            }
            else {
                discountAmnt = HotelSharedValues.getInstance().Discount;
                sendMarginAmnt = HotelSharedValues.getInstance().margin;
                totDiscountAmnt = HotelSharedValues.getInstance().totDiscount;

                //totalPaidAmount = totalFareAmount - totDiscountAmnt ;
                totalPaidAmount = totalFareAmount ;

                txtTotalPaid.setText(getResources().getString(R.string.rs_symbol) + "" + String.valueOf(totalPaidAmount));
                HotelSharedValues.getInstance().totPaidAmount = totalPaidAmount;


                txtBaseFare.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(baseFareAmount));
                txtTaxes.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(taxesAmount));
                txtOtherCharge.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(otherChargeAmount));
                txtTotalFare.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(totalFareAmount));



                /*Get Promocode Discount*/
                if(HotelSharedValues.getInstance().hotelPromoAmount.equals("") ||HotelSharedValues.getInstance().hotelPromoAmount == null){

                    txtPromoDis.setText(getResources().getString(R.string.rs_symbol) + " " +"0");
                    txtPromoDisNote.setVisibility(View.GONE);
                    promoAmount = 0;

                }
                else {
                    txtPromoDis.setText(getResources().getString(R.string.rs_symbol) + " " +HotelSharedValues.getInstance().hotelPromoAmount);
                    txtPromoDisNote.setVisibility(View.VISIBLE);
                    promoAmount = Integer.parseInt(HotelSharedValues.getInstance().hotelPromoAmount);
                }
            }

            /*Click Listener for Layaout Agent Invoice Detail*/
            layoutAgent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(layoutAgentcontent.getVisibility()== View.VISIBLE){
                        layoutAgentcontent.setVisibility(View.GONE);
                        imgDown.setImageResource(R.drawable.ic_arrow_down);
                        //Picasso.with(FlightBookFareDetailActivity.this).load(R.drawable.ic_arrow_down).into(imgDown);

                    }
                    else {
                        layoutAgentcontent.setVisibility(View.VISIBLE);
                        imgDown.setImageResource(R.drawable.ic_arrow_drop_up);
                        //Picasso.with(FlightBookFareDetailActivity.this).load(R.drawable.ic_arrow_drop_up).into(imgDown);

                        /*Get Promocode Discount*/
                        if(HotelSharedValues.getInstance().hotelPromoAmount.equals("") ||
                                HotelSharedValues.getInstance().hotelPromoAmount== null){

                            txtAgPromo.setText(getResources().getString(R.string.rs_symbol) + " " +"0");
                        }
                        else {
                            txtAgPromo.setText(getResources().getString(R.string.rs_symbol) + " " +HotelSharedValues.getInstance().hotelPromoAmount);
                        }

                        /*Get  Discount*/
                        if((discountAmnt != 0) || (discountAmnt > 0)){

                            txtAgDiscount.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(discountAmnt));
                        }
                        else {

                            txtAgDiscount.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(discountAmnt));
                        }
                        /*Get  Margin*/
                        if((sendMarginAmnt != 0) || (sendMarginAmnt > 0)){

                            txtMargin.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(sendMarginAmnt));
                        }
                        else {

                            txtMargin.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(sendMarginAmnt));
                        }


                        int tot=promoAmount+discountAmnt+sendMarginAmnt;
                        int totAmount=totalPaidAmount-tot;
                        txtInvoiceTotAmount.setText(String.valueOf(totAmount));
                        //txtMargin.setText(getResources().getString(R.string.rs_symbol) + " " +"0");


                    }
                }
            });

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
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
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);

    }
}
