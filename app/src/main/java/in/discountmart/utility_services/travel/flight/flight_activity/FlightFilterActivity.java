package in.discountmart.utility_services.travel.flight.flight_activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

import in.discountmart.R;

public class FlightFilterActivity extends AppCompatActivity {

    CheckBox checkBoxNonRefund;
    CheckBox checkBoxIndigo;
    CheckBox checkBoxAirindia;
    CheckBox checkBoxSpicejet;
    CheckBox checkBoxAirAsia;
    ImageView imgClose;
    LinearLayout layoutAirlines;
    TextView txtReset;

    RadioGroup rdgStops;
    RadioGroup rdgDeparture;
    RadioGroup rdgClass;

    RadioButton rdbNonStp;
    RadioButton rdb1Stp;
    RadioButton rdball;
    RadioButton rdbBeforeAm;
    RadioButton rdbAfterNoon;
    RadioButton rdbEvening;
    RadioButton rdbAfter;
    RadioButton rdbFirst;
    RadioButton rdbEconomy;
    RadioButton rdbBusiness;
    ArrayList<String> filghtNameList;
    ArrayList<String> namelist;

    String strNonRefund="All";
    String strStop="All";
    String strDepTime="All";
    String strFlightClass="All";
    String strAirlines="All";
    String flightType="All";

    Button btnApply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_flight_filter);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try{
            rdgStops=(RadioGroup)findViewById(R.id.flight_filter_rdg_stop);
            rdgDeparture=(RadioGroup)findViewById(R.id.flight_filter_rdg_departure_time);
            rdgClass=(RadioGroup)findViewById(R.id.flight_filter_rdg_class);
            rdball=(RadioButton)findViewById(R.id.flight_filter_rdb_all);
            rdbNonStp=(RadioButton)findViewById(R.id.flight_filter_rdb_non_stop);
            rdb1Stp=(RadioButton)findViewById(R.id.flight_filter_rdb_1stop);
            checkBoxNonRefund=(CheckBox)findViewById(R.id.flight_filter_act_chekbox_nonrefund);
            btnApply=(Button)findViewById(R.id.flight_filter_act_btn_apply);
            layoutAirlines=(LinearLayout)findViewById(R.id.flight_filter_act_layout_airlines);
            txtReset=(TextView)findViewById(R.id.flight_filter_act_txt_reset);
            imgClose=(ImageView)findViewById(R.id.flight_filter_act_img_close);
            //checkBoxNonRefund=(CheckBox)findViewById(R.id.flight_filter_act_chekbox_indigo);
            //checkBoxNonRefund=(CheckBox)findViewById(R.id.flight_filter_act_chekbox_airindia);
            //checkBoxNonRefund=(CheckBox)findViewById(R.id.flight_filter_act_chekbox_spicejet);
            //checkBoxNonRefund=(CheckBox)findViewById(R.id.flight_filter_act_chekbox_airasia);

            /*Get List Flight Name form FlightSearchListActivity*/
            filghtNameList=new ArrayList<>();
            Bundle intent=new Bundle();
            intent.clear();
            intent=getIntent().getExtras();
            if(intent != null){
                filghtNameList=intent.getStringArrayList("FlightName");
                flightType=intent.getString("FlightType");
            }
            System.out.println(intent+"Common List");

            if(filghtNameList.size() > 0){
                createAirlinesListView(filghtNameList);
            }

            /*Check Box Nonrefundable click listener*/
            checkBoxNonRefund.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        strNonRefund="Refundable";
                    }
                    else {
                        strNonRefund="All";
                    }
                }
            });

            /*Chekc radion button is check or not*/
            if(rdbNonStp.isChecked()){
                strStop="Non Stop";
            }
            else if(rdb1Stp.isChecked()){
                strStop="1 Stop";
            }
            else if(rdball.isChecked()){
                strStop="All";
            }

            /*RadioGroup Flight Stop checked change listener*/
            rdgStops.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = (RadioButton) group.findViewById(checkedId);
                    /* // get selected radio button from radioGroup
                    int selectedId = radioSexGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    radioSexButton = (RadioButton) findViewById(selectedId);*/
                    // Method 1 For Getting Index of RadioButton
                    if(rb.isChecked()){
                        strStop=rb.getText().toString();
                    }
                    int pos=rdgStops.indexOfChild(findViewById(checkedId));
                    switch (pos){
                        case 0:
                            strStop="Non Stop";
                            break;
                        case 1:
                            strStop="1 Stop";
                            break;
                        case 2:
                            strStop="All";
                            break;
                        default:
                            strStop="All";
                            break;
                    }
                }
            });

            /*RadioGroup Flight departure time checked change listener*/
            rdgDeparture.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = (RadioButton) group.findViewById(checkedId);
                    /* // get selected radio button from radioGroup
                    int selectedId = radioSexGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    radioSexButton = (RadioButton) findViewById(selectedId);*/
                    // Method 1 For Getting Index of RadioButton
                    /*if(rb.isChecked()){
                        strDepTime=rb.getText().toString();
                    }*/
                    int pos=rdgDeparture.indexOfChild(findViewById(checkedId));
                    switch (pos){
                        case 0:
                            strDepTime="11.00";
                            break;
                        case 1:
                            strDepTime="11.00to17.00";
                            break;
                        case 2:
                            strDepTime="17.00to21.00";
                            break;
                        case 3:
                            strDepTime="21.00";
                            break;
                        default:
                            strDepTime="All";
                            break;
                    }
                }
            });

           /* *//*RadioGroup Flight Class checked change listener*//*
            rdgClass.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = (RadioButton) group.findViewById(checkedId);
                    *//* // get selected radio button from radioGroup
                    int selectedId = radioSexGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    radioSexButton = (RadioButton) findViewById(selectedId);*//*
                    // Method 1 For Getting Index of RadioButton
                    if(rb.isChecked()){
                        strFlightClass=rb.getText().toString();
                    }
                    int pos=rdgClass.indexOfChild(findViewById(checkedId));
                    switch (pos){
                        case 0:
                            strFlightClass="first";
                            break;
                        case 1:
                            strFlightClass="Business";
                            break;
                        case 2:
                            strFlightClass="Economy";
                            break;
                        default:
                            strFlightClass="All";
                            break;
                    }
                }
            });*/

            /*Button apply Filter on click flight search list will be filter list*/

            btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent1=new Intent(FlightFilterActivity.this,FlightSearchListActivity.class);
                    Bundle filterBundle=new Bundle();

                    filterBundle.clear();
                    filterBundle.putStringArrayList("AirlinesList",namelist);
                    filterBundle.putString("Airlines",strAirlines);
                    filterBundle.putString("Refund",strNonRefund);
                    filterBundle.putString("Stop",strStop);
                    filterBundle.putString("DepTime",strDepTime);
                    filterBundle.putString("FlightClass",strFlightClass);

                    intent1.putExtras(filterBundle);
                    setResult(RESULT_OK, intent1);
                    finish();

                }
            });

            /*Text Reset Click Listener and Reset Flight Search list*/
            txtReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent resetIntent=new Intent();
                    setResult(RESULT_CANCELED,resetIntent);
                    finish();
                }
            });

            /*image cross on click finish activity*/
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                }
            });

        }catch (Exception e){

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

    public void createAirlinesListView(final ArrayList<String> list){
        /*Set Dynamically add Linear Layout,TextView,CheckBox*/


        if(list.size() > 0){
            int pos;
            for (int i=0;i<list.size();i++){

//layoutAirlines.removeAllViews();
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.setMarginStart(10);
                }
                LinearLayout linearLayout=new LinearLayout(this);
                linearLayout.setPadding(5,5,5,5);
                linearLayout.setLayoutParams(layoutParams);

                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setWeightSum(1);

                TextView textView=new TextView(this);
                textView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, .8f));
                textView.setText(filghtNameList.get(i));
                textView.setTextColor(getResources().getColor(R.color.black));

                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_size_normal));

                CheckBox checkBox=new CheckBox(this);
                checkBox.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, .2f));
                checkBox.setHeight((int) getResources().getDimension(R.dimen.margin_x_large));
                checkBox.setWidth((int) getResources().getDimension(R.dimen.margin_x_large));
                checkBox.setId(i);
                pos=i;

                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    //checkBox.setBackground(getResources().getDrawable(R.drawable.selector_custom_checkbox48dp));
                    checkBox.setButtonDrawable(getResources().getDrawable(R.drawable.selector_custom_check_box24dp));
                }*/
                layoutAirlines.addView(linearLayout);

                linearLayout.addView(textView);
                linearLayout.addView(checkBox);

                final int finalI = i;
                namelist=new ArrayList<String>();
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;

                        String name="";
                        if(cb.isChecked()){

                            //cb.setChecked(true);
                            if(namelist.size()==0){
                                namelist=new ArrayList<String>(Collections.singleton(list.get(finalI)));
                            }
                            else {
                                namelist.add(list.get(finalI));

                            }

                            name = name + "\n" + list.get(finalI);
                            Toast.makeText(FlightFilterActivity.this, name,Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //strAirlines="All";
                            namelist.remove(list.get(finalI));
                            //cb.setChecked(false);
                        }

                    }
                });

                namelist.size();


            }

        }

    }

    @Override
    public void onResume(){
        super.onResume();
        /* strNonRefund="All";
         strStop="All";
         strDepTime="All";
         strFlightClass="All";
         strAirlines="All";*/

    }


}
