package in.discountmart.utility_services.travel.flight.flight_activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.sharedpreferences.SharedPrefrence_Utility;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightSearchResponse;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.OwnwardFlightSearch;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.ReturnFlightSearch;
import in.discountmart.utility_services.travel.flight.flight_sharedpreference.FlightSharedValues;

public class FlightBookFareDetailActivity extends AppCompatActivity {

    LinearLayout layoutAgent;
    LinearLayout layoutAgentcontent;
    LinearLayout layoutDiscount;
    LinearLayout layoutPromo;
    LinearLayout layoutPromoInvoice;

    TextView txtAdultBaseFare;
    TextView txtChildBaseFare;
    TextView txtInfantsBaseFare;
    TextView txtBaseFare;
    TextView txtTaxes;
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

    int adultFareAmount=0;
    int childFareAmount=0;
    int infantsFareAmount=0;
    int baseFareAmount=0;
    int taxesAmount=0;
    int totalFareAmount=0;
    int totalPaidAmount=0;
    float marginAmount=0;
    double commisionAmnt=0;
    int discountAmnt=0;
    int sendMarginAmnt=0;
    int totDiscountAmnt=0;
    int promoAmount=0;

    String airlineCode="";
    String airlineCodeOwn="";
    String airlineCodeRet="";
    String flightType="";

    /*Array list of object*/
    ArrayList<FlightSearchResponse> selectFlightarrayList;
    FlightSearchResponse[] flightSearchResponse;
    ArrayList<OwnwardFlightSearch> selectOwnFlightArrayList;
    ArrayList<ReturnFlightSearch> selectReturnFlightArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_flight_book_info);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Base Fare Detail");

            layoutAgent=(LinearLayout)findViewById(R.id.flight_book_info_act_layout_agent);
            layoutAgentcontent=(LinearLayout)findViewById(R.id.flight_book_info_act_layout_agent_content);
            layoutDiscount=(LinearLayout)findViewById(R.id.flight_book_info_act_layout_discount);
            layoutPromo=(LinearLayout)findViewById(R.id.flight_book_info_act_layout_promo_dis);
            layoutPromoInvoice=(LinearLayout)findViewById(R.id.flight_book_info_act_layout_promo_dis_invoice);

            txtAdultBaseFare=(TextView)findViewById(R.id.flight_book_info_act_txt_adult_basefare);
            txtChildBaseFare=(TextView)findViewById(R.id.flight_book_info_act_txt_child_basefare);
            txtInfantsBaseFare=(TextView)findViewById(R.id.flight_book_info_act_txt_infants_basefare);
            txtBaseFare=(TextView)findViewById(R.id.flight_book_info_act_txt_basefare);
            txtTaxes=(TextView)findViewById(R.id.flight_book_info_act_txt_taxes);
            txtTotalFare=(TextView)findViewById(R.id.flight_book_info_act_txt_total_fare);
            txtTotalPaid=(TextView)findViewById(R.id.flight_book_info_act_txt_total_paid);
            txtDiscount=(TextView)findViewById(R.id.flight_book_info_act_txt_discount);
            txtPromoDis=(TextView)findViewById(R.id.flight_book_info_act_txt_promo_discount);
            txtPromoDisNote=(TextView)findViewById(R.id.flight_book_info_act_txt_promo_discount_note);
            txtDisNote=(TextView)findViewById(R.id.flight_book_info_act_txt_discount_note);
            txtAgDiscount=(TextView)findViewById(R.id.flight_book_info_act_txt_agent_discount);
            txtAgPromo=(TextView)findViewById(R.id.flight_book_info_act_txt_agent_promo_discount);
            txtMargin=(TextView)findViewById(R.id.flight_book_info_act_txt_margin);
            txtInvoiceTotAmount=(TextView)findViewById(R.id.flight_book_info_act_txt_total_invoice_Amount);
            imgDown=(ImageView)findViewById(R.id.flight_book_info_act_image_downicon);

            /*Get Value from Intent*/
            Intent intent=getIntent();
            if(intent != null){
                flightType=intent.getStringExtra("FlightType");
            }


            /*Show Promo or not*/
            LoginResponse loginPreferences=new LoginResponse();
            loginPreferences = new LoginPreferences_Utility(this).getLoggedinUser();
           /* if(loginPreferences != null){
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
           if(flightType.contentEquals("Ownward")){
                /* Onw way flight
            Get select flight value form flight shared preference*/
               selectFlightarrayList=new ArrayList<FlightSearchResponse>();
               selectFlightarrayList= FlightSharedValues.getInstance().flightSelectArrayList;

               if (selectFlightarrayList.size() > 0){

                   for (int i=0; i< selectFlightarrayList.size();i++){

                       if(selectFlightarrayList.get(i).getInfantbaseFare()==null || selectFlightarrayList.get(i).getInfantbaseFare().equals(""))
                           infantsFareAmount=0;
                       else
                           infantsFareAmount=Integer.parseInt(selectFlightarrayList.get(i).getInfantbaseFare());

                       if(selectFlightarrayList.get(i).getChildbaseFare()==null || selectFlightarrayList.get(i).getChildbaseFare().equals(""))
                           childFareAmount=0;
                       else
                           childFareAmount=Integer.parseInt(selectFlightarrayList.get(i).getChildbaseFare());

                       if(selectFlightarrayList.get(i).getAdultbaseFare()==null || selectFlightarrayList.get(i).getAdultbaseFare().equals(""))
                           adultFareAmount=0;
                       else
                           adultFareAmount=Integer.parseInt(selectFlightarrayList.get(i).getAdultbaseFare());

                       if(selectFlightarrayList.get(i).getBaseFare()==null || selectFlightarrayList.get(i).getBaseFare().equals(""))
                           baseFareAmount=0;
                       else
                           baseFareAmount=Integer.parseInt(selectFlightarrayList.get(i).getBaseFare());

                       if(selectFlightarrayList.get(i).getTaxAmount()==null || selectFlightarrayList.get(i).getTaxAmount().equals(""))
                           taxesAmount=0;
                       else
                           taxesAmount=Integer.parseInt(selectFlightarrayList.get(i).getTaxAmount());

                       if(selectFlightarrayList.get(i).getGrossAmount()==null || selectFlightarrayList.get(i).getGrossAmount().equals(""))
                           totalFareAmount=0;
                       else
                           totalFareAmount=Integer.parseInt(selectFlightarrayList.get(i).getGrossAmount());

                       if(selectFlightarrayList.get(i).getAirlineCode()==null || selectFlightarrayList.get(i).getAirlineCode().equals(""))
                           airlineCode="";
                       else
                           airlineCode=selectFlightarrayList.get(i).getAirlineCode();


                   }
               }
           }
           else {
                 /* Return flight
            Get select flight value form flight shared preference*/

                int adultFareOwn=0;
                int childFareOwn=0;
                int infantsFareOwn=0;
                int baseFareOwn=0;
                int taxesAmountOwn=0;
                int fareAmountOwn=0;

                int adultFareRet=0;
                int childFareRet=0;
                int infantsFareRet=0;
                int baseFareRet=0;
                int taxesAmountRet=0;
                int fareAmountRet=0;
                selectOwnFlightArrayList=new ArrayList<OwnwardFlightSearch>();
                selectOwnFlightArrayList=FlightSharedValues.getInstance().selectedOwnFlightList;
                selectReturnFlightArrayList=new ArrayList<ReturnFlightSearch>();
                selectReturnFlightArrayList=FlightSharedValues.getInstance().selectedReturnFlightList;

                if(selectOwnFlightArrayList.size() > 0){
                    for (int i=0; i< selectOwnFlightArrayList.size();i++){

                        if(selectOwnFlightArrayList.get(i).getInfantbaseFare()==null || selectOwnFlightArrayList.get(i).getInfantbaseFare().equals(""))
                            infantsFareOwn=0;
                        else
                            infantsFareOwn=Integer.parseInt(selectOwnFlightArrayList.get(i).getInfantbaseFare());

                        if(selectOwnFlightArrayList.get(i).getChildbaseFare()==null || selectOwnFlightArrayList.get(i).getChildbaseFare().equals(""))
                            childFareOwn=0;
                        else
                            childFareOwn=Integer.parseInt(selectOwnFlightArrayList.get(i).getChildbaseFare());

                        if(selectOwnFlightArrayList.get(i).getAdultbaseFare()==null || selectOwnFlightArrayList.get(i).getAdultbaseFare().equals(""))
                            adultFareOwn=0;
                        else
                            adultFareOwn=Integer.parseInt(selectOwnFlightArrayList.get(i).getAdultbaseFare());

                        if(selectOwnFlightArrayList.get(i).getBaseFare()==null || selectOwnFlightArrayList.get(i).getBaseFare().equals(""))
                            baseFareOwn=0;
                        else
                            baseFareOwn=Integer.parseInt(selectOwnFlightArrayList.get(i).getBaseFare());

                        if(selectOwnFlightArrayList.get(i).getTaxAmount()==null || selectOwnFlightArrayList.get(i).getTaxAmount().equals(""))
                            taxesAmountOwn=0;
                        else
                            taxesAmountOwn=Integer.parseInt(selectOwnFlightArrayList.get(i).getTaxAmount());

                        if(selectOwnFlightArrayList.get(i).getGrossAmount()==null || selectOwnFlightArrayList.get(i).getGrossAmount().equals(""))
                            fareAmountOwn=0;
                        else
                            fareAmountOwn=Integer.parseInt(selectOwnFlightArrayList.get(i).getGrossAmount());

                        if(selectOwnFlightArrayList.get(i).getAirlineCode()==null || selectOwnFlightArrayList.get(i).getAirlineCode().equals(""))
                            airlineCodeOwn="";
                        else
                            airlineCodeOwn=selectOwnFlightArrayList.get(i).getAirlineCode();


                    }
                }
                if(selectReturnFlightArrayList.size() > 0){
                    for (int i=0; i< selectReturnFlightArrayList.size();i++){

                        if(selectReturnFlightArrayList.get(i).getInfantbaseFare()==null || selectReturnFlightArrayList.get(i).getInfantbaseFare().equals(""))
                            infantsFareRet=0;
                        else
                            infantsFareRet=Integer.parseInt(selectReturnFlightArrayList.get(i).getInfantbaseFare());

                        if(selectReturnFlightArrayList.get(i).getChildbaseFare()==null || selectReturnFlightArrayList.get(i).getChildbaseFare().equals(""))
                            childFareRet=0;
                        else
                            childFareRet=Integer.parseInt(selectReturnFlightArrayList.get(i).getChildbaseFare());

                        if(selectReturnFlightArrayList.get(i).getAdultbaseFare()==null || selectReturnFlightArrayList.get(i).getAdultbaseFare().equals(""))
                            adultFareRet=0;
                        else
                            adultFareRet=Integer.parseInt(selectReturnFlightArrayList.get(i).getAdultbaseFare());

                        if(selectReturnFlightArrayList.get(i).getBaseFare()==null || selectReturnFlightArrayList.get(i).getBaseFare().equals(""))
                            baseFareRet=0;
                        else
                            baseFareRet=Integer.parseInt(selectReturnFlightArrayList.get(i).getBaseFare());

                        if(selectReturnFlightArrayList.get(i).getTaxAmount()==null || selectReturnFlightArrayList.get(i).getTaxAmount().equals(""))
                            taxesAmountRet=0;
                        else
                            taxesAmountRet=Integer.parseInt(selectReturnFlightArrayList.get(i).getTaxAmount());

                        if(selectReturnFlightArrayList.get(i).getGrossAmount()==null || selectReturnFlightArrayList.get(i).getGrossAmount().equals(""))
                            fareAmountRet=0;
                        else
                            fareAmountRet=Integer.parseInt(selectReturnFlightArrayList.get(i).getGrossAmount());

                        if(selectReturnFlightArrayList.get(i).getAirlineCode()==null || selectReturnFlightArrayList.get(i).getAirlineCode().equals(""))
                            airlineCodeRet="";
                        else
                            airlineCodeRet=selectReturnFlightArrayList.get(i).getAirlineCode();


                    }
                }

                childFareAmount=childFareOwn+childFareRet;
                adultFareAmount=adultFareOwn+adultFareRet;
                infantsFareAmount=infantsFareOwn+infantsFareRet;
                totalFareAmount=fareAmountOwn+fareAmountRet;
                taxesAmount=taxesAmountOwn+taxesAmountRet;
                baseFareAmount=baseFareOwn+baseFareRet;


            }

            int totBaseFare=0;
            int totFareamnt=0;
            totBaseFare=childFareAmount+adultFareAmount+infantsFareAmount;
            totFareamnt=totBaseFare+taxesAmount;

            if(totalFareAmount == totFareamnt) {

                discountAmnt = FlightSharedValues.getInstance().flightCommsion;
                sendMarginAmnt = FlightSharedValues.getInstance().flightMargin;
                totDiscountAmnt = FlightSharedValues.getInstance().totDiscount;

                //totalPaidAmount = totalFareAmount - totDiscountAmnt ;
                totalPaidAmount = totalFareAmount ;

                txtTotalPaid.setText(getResources().getString(R.string.rs_symbol) + "" + String.valueOf(totalPaidAmount));
                FlightSharedValues.getInstance().totPaidAmount = totalPaidAmount;
                FlightSharedValues.getInstance().totBaseFare=baseFareAmount;
                FlightSharedValues.getInstance().totTax=taxesAmount;

                txtChildBaseFare.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(childFareAmount));
                txtAdultBaseFare.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(adultFareAmount));
                txtInfantsBaseFare.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(infantsFareAmount));
                txtBaseFare.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(baseFareAmount));
                txtTaxes.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(taxesAmount));
                txtTotalFare.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(totalFareAmount));

                /*Get  Discount*/
                /*if(totDiscountAmnt != 0 || totDiscountAmnt > 0){

                    //txtDisNote.setVisibility(View.VISIBLE);
                    txtDiscount.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(totDiscountAmnt));

                }
                else {
                    //txtDisNote.setVisibility(View.GONE);
                    txtDiscount.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(totDiscountAmnt));

                }*/


                /*Get Promocode Discount*/
                if(SharedPrefrence_Utility.getPromocode(this).equals("") && SharedPrefrence_Utility.getPromoDiscount(this).equals("")){

                    txtPromoDis.setText(getResources().getString(R.string.rs_symbol) + " " +"0");
                    txtPromoDisNote.setVisibility(View.GONE);
                    promoAmount = 0;

                }
                else {
                    txtPromoDis.setText(getResources().getString(R.string.rs_symbol) + " " + SharedPrefrence_Utility.getPromoDiscount(this));
                    txtPromoDisNote.setVisibility(View.VISIBLE);
                    promoAmount = Integer.parseInt(SharedPrefrence_Utility.getPromoDiscount(this));
                }
            }
            else {
                discountAmnt = FlightSharedValues.getInstance().flightCommsion;
                sendMarginAmnt = FlightSharedValues.getInstance().flightMargin;
                totDiscountAmnt = FlightSharedValues.getInstance().totDiscount;


                //totalPaidAmount = totalFareAmount - totDiscountAmnt ;
                totalPaidAmount = totalFareAmount ;

                txtTotalPaid.setText(getResources().getString(R.string.rs_symbol) + "" + String.valueOf(totalPaidAmount));
                FlightSharedValues.getInstance().totPaidAmount = totalPaidAmount;
                FlightSharedValues.getInstance().totBaseFare=baseFareAmount;
                FlightSharedValues.getInstance().totTax=taxesAmount;

                txtChildBaseFare.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(childFareAmount));
                txtAdultBaseFare.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(adultFareAmount));
                txtInfantsBaseFare.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(infantsFareAmount));
                txtBaseFare.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(baseFareAmount));
                txtTaxes.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(taxesAmount));
                txtTotalFare.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(totalFareAmount));

                /*Get  Discount*/
                /*if(totDiscountAmnt != 0 || totDiscountAmnt > 0){

                    txtDisNote.setVisibility(View.VISIBLE);
                    txtDiscount.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(totDiscountAmnt));

                }
                else {
                    txtDisNote.setVisibility(View.GONE);
                    txtDiscount.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(totDiscountAmnt));

                }*/


                /*Get Promocode Discount*/
                if(SharedPrefrence_Utility.getPromocode(this).equals("") && SharedPrefrence_Utility.getPromoDiscount(this).equals("")){

                    txtPromoDis.setText(getResources().getString(R.string.rs_symbol) + " " +"0");
                    txtPromoDisNote.setVisibility(View.GONE);
                    promoAmount = 0;

                }
                else {
                    txtPromoDis.setText(getResources().getString(R.string.rs_symbol) + " " + SharedPrefrence_Utility.getPromoDiscount(this));
                    txtPromoDisNote.setVisibility(View.VISIBLE);
                    promoAmount = Integer.parseInt(SharedPrefrence_Utility.getPromoDiscount(this));
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
                        if(SharedPrefrence_Utility.getPromocode(FlightBookFareDetailActivity.this).equals("") ||
                                SharedPrefrence_Utility.getPromoDiscount(FlightBookFareDetailActivity.this)== null){

                            txtAgPromo.setText(getResources().getString(R.string.rs_symbol) + " " +"0");

                        }
                        else {
                            txtAgPromo.setText(getResources().getString(R.string.rs_symbol) + " " + SharedPrefrence_Utility.getPromoDiscount(FlightBookFareDetailActivity.this));


                        }

                        /*Get  Discount*/
                        if((totDiscountAmnt != 0) || (totDiscountAmnt > 0)){

                            txtAgDiscount.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(totDiscountAmnt));
                        }
                        else {

                            txtAgDiscount.setText(getResources().getString(R.string.rs_symbol) + " " + String.valueOf(totDiscountAmnt));
                        }
                        int tot=promoAmount+totDiscountAmnt;
                        int totAmount=totalPaidAmount-tot;
                        txtInvoiceTotAmount.setText(String.valueOf(totAmount));

                        txtMargin.setText(getResources().getString(R.string.rs_symbol) + " " +"0");


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
