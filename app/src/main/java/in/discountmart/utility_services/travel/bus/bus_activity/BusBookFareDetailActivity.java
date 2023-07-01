package in.discountmart.utility_services.travel.bus.bus_activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.travel.bus.bus_model.BusSeatModel;
import in.discountmart.utility_services.travel.bus.bus_sharedprference.BusSharedValues;

public class BusBookFareDetailActivity extends AppCompatActivity {

    TextView txtTotAmount;
    TextView txtTotPaidAmount;
    TextView txtdisAmount;
    TextView txtPromoAmount;
    TextView txtPromoNote;
    TextView txtdiscontNote;
    TextView txtSeats;
    TextView txtInvoiceTotAmount;
    TextView txtInvoiceDisAmount;
    TextView txtInvoiceMarginAmount;
    TextView txtInvoicePromoAmount;
    LinearLayout layoutInvoice;
    LinearLayout layoutInvoiceContent;
    ImageView imgDown;

    String strTotAmount="";
    String strInvoiceTotAmount="";
    String strInvoiceDisAmount="";
    String strInvoicePromoAmount="";
    String strDisAmout="";
    String strPromoAmout="";
    String strDisNote="";
    String strPromoNote="";
    double discount=0;
    double promoDis=0;
    double totAmount=0;
    ArrayList<BusSeatModel> busSeatModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bus_book_fare_detail);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Base Fare Detail");
        try {

            txtSeats=(TextView)findViewById(R.id.bus_book_info_act_txt_seat);
            txtTotAmount=(TextView)findViewById(R.id.bus_book_info_act_txt_tot_amount);
            txtTotPaidAmount=(TextView)findViewById(R.id.bus_book_info_act_txt_total_paid);
            txtdisAmount=(TextView)findViewById(R.id.bus_book_info_act_txt_discount);
            txtdiscontNote=(TextView)findViewById(R.id.bus_book_info_act_txt_discount_note);
            txtPromoAmount=(TextView)findViewById(R.id.bus_book_info_act_txt_promo_discount);
            txtPromoNote=(TextView)findViewById(R.id.bus_book_info_act_txt_promo_discount_note);
            txtInvoiceDisAmount=(TextView)findViewById(R.id.bus_book_info_act_txt_agent_discount);
            txtInvoiceMarginAmount=(TextView)findViewById(R.id.bus_book_info_act_txt_margin);
            txtInvoicePromoAmount=(TextView)findViewById(R.id.bus_book_info_act_txt_agent_promo_discount);
            txtInvoiceTotAmount=(TextView)findViewById(R.id.bus_book_info_act_txt_total_invoice_Amount);
            layoutInvoice=(LinearLayout)findViewById(R.id.bus_book_info_act_layout_agent);
            layoutInvoiceContent=(LinearLayout)findViewById(R.id.bus_book_info_act_layout_agent_content);
            imgDown=(ImageView)findViewById(R.id.bus_book_info_act_image_downicon);

            /*Get Value from bus shared preference*/
            busSeatModels=new ArrayList<BusSeatModel>();
            busSeatModels= BusSharedValues.getInstance().busSeatModelArrayList;
            if(busSeatModels.size() > 0){
                String seat="";
                for (int i=0; i < busSeatModels.size(); i++){

                    if(seat.contentEquals("")){
                        seat=busSeatModels.get(i).getSeat();
                    }
                    else {
                        seat=seat+ ","+busSeatModels.get(i).getSeat();
                    }
                    txtSeats.setText(seat);
                }
            }
            strTotAmount= String.valueOf(BusSharedValues.getInstance().TotalFare);
            totAmount= BusSharedValues.getInstance().TotalFare;

            strDisAmout= String.valueOf(BusSharedValues.getInstance().totDiscount);
            discount=BusSharedValues.getInstance().totDiscount;


            txtTotAmount.setText(getResources().getString(R.string.rs_symbol)+" "+strTotAmount);

            /*Get Promo discount value from bus shared preference*/
            if(BusSharedValues.getInstance().busPromoAmount.contentEquals("") || BusSharedValues.getInstance().busPromoAmount == null){
                txtPromoAmount.setText(getResources().getString(R.string.rs_symbol)+" "+"0");
                txtPromoNote.setVisibility(View.GONE);
                strPromoAmout="0";
                promoDis= 0;
            }
            else {
                txtPromoAmount.setText(getResources().getString(R.string.rs_symbol)+" "+BusSharedValues.getInstance().busPromoAmount);
                txtPromoNote.setVisibility(View.GONE);
                strPromoAmout= BusSharedValues.getInstance().busPromoAmount;
                promoDis= Double.parseDouble(BusSharedValues.getInstance().busPromoAmount);
            }
            txtTotPaidAmount.setText(getResources().getString(R.string.rs_symbol)+" "+strTotAmount);

            /*Text Agent invoide on click*/
            layoutInvoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(layoutInvoiceContent.getVisibility()== View.VISIBLE){
                        layoutInvoiceContent.setVisibility(View.GONE);
                        imgDown.setImageResource(R.drawable.ic_arrow_down);
                        //Picasso.with(FlightBookFareDetailActivity.this).load(R.drawable.ic_arrow_down).into(imgDown);

                    }
                    else {
                        layoutInvoiceContent.setVisibility(View.VISIBLE);
                        imgDown.setImageResource(R.drawable.ic_arrow_drop_up);
                        //Picasso.with(FlightBookFareDetailActivity.this).load(R.drawable.ic_arrow_drop_up).into(imgDown);

                        /*Get Promocode Discount*/
                        if(BusSharedValues.getInstance().busPromoAmount.equals("") ||
                                BusSharedValues.getInstance().busPromoAmount == null){
                            txtInvoicePromoAmount.setText(getResources().getString(R.string.rs_symbol) + " " +"0");
                        }
                        else {
                            txtInvoicePromoAmount.setText(getResources().getString(R.string.rs_symbol) + " " +BusSharedValues.getInstance().busPromoAmount);

                        }

                        /*Get  Discount*/
                        if((discount != 0) || (discount > 0)){

                            txtInvoiceDisAmount.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(discount));
                        }
                        else {

                            txtInvoiceDisAmount.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(discount));
                        }
                        double totDis=discount+promoDis;
                        double tot=totAmount-totDis;
                        txtInvoiceTotAmount.setText(String.valueOf(tot));

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
