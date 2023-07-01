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

public class RetrunFlightFilterActivity extends AppCompatActivity {


    CheckBox chkBoxNonRefundOwn;
    CheckBox chkBoxNonRefundRet;
    CheckBox chkBoxIndigo;
    CheckBox checkBoxAirindia;
    CheckBox checkBoxSpicejet;
    CheckBox checkBoxAirAsia;
    ImageView imgClose;
    LinearLayout layoutAirlines;
    LinearLayout layoutAirlinesRet;
    TextView txtReset;

    RadioGroup rdgStopsOwn;
    RadioGroup rdgStopsRet;
    RadioGroup rdgDepartureOwn;
    RadioGroup rdgDepartureRet;
    RadioGroup rdgClassOwn;
    RadioGroup rdgClassRet;

    RadioButton rdbNonStpOwn;
    RadioButton rdbNonStpRet;
    RadioButton rdb1StpOwn;
    RadioButton rdb1StpRet;
    RadioButton rdballOwn;
    RadioButton rdballRet;
    RadioButton rdbBeforeAm;
    RadioButton rdbAfterNoon;
    RadioButton rdbEvening;
    RadioButton rdbAfter;
    RadioButton rdbFirst;
    RadioButton rdbEconomy;
    RadioButton rdbBusiness;
    ArrayList<String> filghtNameListOwn;
    ArrayList<String> filghtNameListRet;
    ArrayList<String> namelistOwn;
    ArrayList<String> namelistRet;

    String strNonRefundOwn="All";
    String strNonRefundRet="All";
    String strStopOwn="All";
    String strStopRet="All";
    String strDepTimeOwn="All";
    String strDepTimeRet="All";
    String strFlightClassOwn="All";
    String strFlightClassRet="All";
    String strAirlinesOwn="All";
    String strAirlinesRet="All";
    String flightTypeOwn="All";
    String flightTypeRet="All";

    Button btnApply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_return_flight_filter);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try{
            rdgStopsOwn=(RadioGroup)findViewById(R.id.own_flight_filter_rdg_stop);
            rdgStopsRet=(RadioGroup)findViewById(R.id.ret_flight_filter_rdg_stop);
            rdgDepartureOwn=(RadioGroup)findViewById(R.id.own_flight_filter_rdg_departure_time);
            rdgDepartureRet=(RadioGroup)findViewById(R.id.ret_flight_filter_rdg_departure_time);
            rdgClassOwn=(RadioGroup)findViewById(R.id.own_flight_filter_rdg_class);
            rdgClassRet=(RadioGroup)findViewById(R.id.ret_flight_filter_rdg_class);
            rdballOwn=(RadioButton)findViewById(R.id.own_flight_filter_rdb_all);
            rdballRet=(RadioButton)findViewById(R.id.ret_flight_filter_rdb_all);
            rdbNonStpOwn=(RadioButton)findViewById(R.id.own_flight_filter_rdb_non_stop);
            rdbNonStpRet=(RadioButton)findViewById(R.id.ret_flight_filter_rdb_non_stop);
            rdb1StpOwn=(RadioButton)findViewById(R.id.own_flight_filter_rdb_1stop);
            rdb1StpRet=(RadioButton)findViewById(R.id.ret_flight_filter_rdb_1stop);
            chkBoxNonRefundOwn=(CheckBox)findViewById(R.id.own_flight_filter_act_chekbox_nonrefund);
            chkBoxNonRefundRet=(CheckBox)findViewById(R.id.ret_flight_filter_act_chekbox_nonrefund);
            btnApply=(Button)findViewById(R.id.flight_filter_act_btn_apply);
            layoutAirlines=(LinearLayout)findViewById(R.id.own_flight_filter_act_layout_airlines);
            layoutAirlinesRet=(LinearLayout)findViewById(R.id.ret_flight_filter_act_layout_airlines);
            txtReset=(TextView)findViewById(R.id.flight_filter_act_txt_reset);
            imgClose=(ImageView)findViewById(R.id.flight_filter_act_img_close);
            //checkBoxNonRefund=(CheckBox)findViewById(R.id.flight_filter_act_chekbox_indigo);
            //checkBoxNonRefund=(CheckBox)findViewById(R.id.flight_filter_act_chekbox_airindia);
            //checkBoxNonRefund=(CheckBox)findViewById(R.id.flight_filter_act_chekbox_spicejet);
            //checkBoxNonRefund=(CheckBox)findViewById(R.id.flight_filter_act_chekbox_airasia);

            /*Get List Flight Name form FlightSearchListActivity*/
            filghtNameListOwn=new ArrayList<>();
            filghtNameListRet=new ArrayList<>();
            Bundle intent=new Bundle();
            intent.clear();
            intent=getIntent().getExtras();
            if(intent != null){
                filghtNameListOwn=intent.getStringArrayList("OwnFlightName");
                filghtNameListRet=intent.getStringArrayList("RetFlightName");

            }
            System.out.println(intent+"Common List");

            if(filghtNameListOwn.size() > 0){
                createOwnwardAirlinesListView(filghtNameListOwn);
            }

            if(filghtNameListRet.size() > 0){
                createReturnAirlinesListView(filghtNameListRet);
            }

            /*Check Box for Ownward flight Nonrefundable click listener*/
            chkBoxNonRefundOwn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        strNonRefundOwn="Refundable";
                    }
                    else {
                        strNonRefundOwn="All";
                    }
                }
            });

            /*Check Box for Return flight Nonrefundable click listener*/
            chkBoxNonRefundRet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        strNonRefundRet="Refundable";
                    }
                    else {
                        strNonRefundRet="All";
                    }
                }
            });

            /*Chekc radion button for return flight is check or not*/
            if(rdbNonStpRet.isChecked()){
                strStopRet="Non Stop";
            }
            else if(rdb1StpRet.isChecked()){
                strStopRet="1 Stop";
            }
            else if(rdballRet.isChecked()){
                strStopRet="All";
            }

            /*RadioGroup for return flight Stop checked change listener*/
            rdgStopsRet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = (RadioButton) group.findViewById(checkedId);
                    /* // get selected radio button from radioGroup
                    int selectedId = radioSexGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    radioSexButton = (RadioButton) findViewById(selectedId);*/
                    // Method 1 For Getting Index of RadioButton
                    if(rb.isChecked()){
                        strStopRet=rb.getText().toString();
                    }
                    int pos=rdgStopsRet.indexOfChild(findViewById(checkedId));
                    switch (pos){
                        case 0:
                            strStopRet="Non Stop";
                            break;
                        case 1:
                            strStopRet="1 Stop";
                            break;
                        case 2:
                            strStopRet="All";
                            break;
                        default:
                            strStopRet="All";
                            break;
                    }
                }
            });

            /*Check radion button for ownward flight is check or not*/
            if(rdbNonStpOwn.isChecked()){
                strStopOwn="Non Stop";
            }
            else if(rdb1StpOwn.isChecked()){
                strStopOwn="1 Stop";
            }
            else if(rdballOwn.isChecked()){
                strStopOwn="All";
            }

            /*RadioGroup for ownward flight Stop checked change listener*/
            rdgStopsOwn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = (RadioButton) group.findViewById(checkedId);
                    /* // get selected radio button from radioGroup
                    int selectedId = radioSexGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    radioSexButton = (RadioButton) findViewById(selectedId);*/
                    // Method 1 For Getting Index of RadioButton
                    if(rb.isChecked()){
                        strStopOwn=rb.getText().toString();
                    }
                    int pos=rdgStopsOwn.indexOfChild(findViewById(checkedId));
                    switch (pos){
                        case 0:
                            strStopOwn="Non Stop";
                            break;
                        case 1:
                            strStopOwn="1 Stop";
                            break;
                        case 2:
                            strStopOwn="All";
                            break;
                        default:
                            strStopOwn="All";
                            break;
                    }
                }
            });

            /*RadioGroup ownward Flight departure time checked change listener*/
            rdgDepartureOwn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
                    int pos=rdgDepartureOwn.indexOfChild(findViewById(checkedId));
                    switch (pos){
                        case 0:
                            strDepTimeOwn="11.00";
                            break;
                        case 1:
                            strDepTimeOwn="11.00to17.00";
                            break;
                        case 2:
                            strDepTimeOwn="17.00to21.00";
                            break;
                        case 3:
                            strDepTimeOwn="21.00";
                            break;
                        default:
                            strDepTimeOwn="All";
                            break;
                    }
                }
            });

            /*RadioGroup return Flight departure time checked change listener*/
            rdgDepartureRet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
                    int pos=rdgDepartureRet.indexOfChild(findViewById(checkedId));
                    switch (pos){
                        case 0:
                            strDepTimeRet="11.00";
                            break;
                        case 1:
                            strDepTimeRet="11.00to17.00";
                            break;
                        case 2:
                            strDepTimeRet="17.00to21.00";
                            break;
                        case 3:
                            strDepTimeRet="21.00";
                            break;
                        default:
                            strDepTimeRet="All";
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

                    ArrayList<String> ownFilterItem=new ArrayList<String>();
                    ArrayList<String> retFilterItem=new ArrayList<String>();

                    ownFilterItem.add(strAirlinesOwn);
                    ownFilterItem.add(strNonRefundOwn);
                    ownFilterItem.add(strStopOwn);
                    ownFilterItem.add(strDepTimeOwn);
                    ownFilterItem.add(strFlightClassOwn);

                    retFilterItem.add(strAirlinesRet);
                    retFilterItem.add(strNonRefundRet);
                    retFilterItem.add(strStopRet);
                    retFilterItem.add(strDepTimeRet);
                    retFilterItem.add(strFlightClassRet);


                    Intent intent1=new Intent(RetrunFlightFilterActivity.this,FlightSearchListActivity.class);
                    Bundle filterBundle=new Bundle();

                    filterBundle.clear();
                    filterBundle.putStringArrayList("AirlinesListOwn",namelistOwn);
                    filterBundle.putStringArrayList("AirlinesListRet",namelistRet);
                    filterBundle.putString("Airlines",strAirlinesOwn);
                    filterBundle.putString("AirlinesRet",strAirlinesRet);
                    filterBundle.putString("RefundOwn",strNonRefundOwn);
                    filterBundle.putString("RefundRet",strNonRefundRet);
                    filterBundle.putString("StopOwn",strStopOwn);
                    filterBundle.putString("StopRet",strStopRet);
                    filterBundle.putString("DepTimeOwn",strDepTimeOwn);
                    filterBundle.putString("DepTimeRet",strDepTimeRet);
                    filterBundle.putString("FlightClassOwn",strFlightClassOwn);
                    filterBundle.putString("FlightClassRet",strFlightClassRet);

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

    public void createOwnwardAirlinesListView(final ArrayList<String> list){
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
                textView.setText(filghtNameListOwn.get(i));
                textView.setTextColor(getResources().getColor(R.color.black));

                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_size_normal));

                CheckBox checkBox=new CheckBox(this);
                checkBox.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, .2f));
                checkBox.setHeight((int) getResources().getDimension(R.dimen.margin_x_large));
                checkBox.setWidth((int) getResources().getDimension(R.dimen.margin_x_large));
                checkBox.setId(i);
                pos=i;

               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    //checkBox.setBackground(getResources().getDrawable(R.drawable.selector_custom_checkbox48dp));
                    checkBox.setButtonDrawable(getResources().getDrawable(R.drawable.selector_custom_check_box24dp));
                }*/
                layoutAirlines.addView(linearLayout);

                linearLayout.addView(textView);
                linearLayout.addView(checkBox);

                final int finalI = i;
                namelistOwn=new ArrayList<String>();
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        String name="";
                        if(cb.isChecked()){

                            //cb.setChecked(true);
                            if(namelistOwn.size()==0){
                                namelistOwn=new ArrayList<String>(Collections.singleton(list.get(finalI)));
                            }
                            else {
                                namelistOwn.add(list.get(finalI));
                            }

                            name = name + "\n" + list.get(finalI);
                            Toast.makeText(RetrunFlightFilterActivity.this, name,Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //strAirlines="All";
                            namelistOwn.remove(list.get(finalI));
                            //cb.setChecked(false);
                        }

                    }
                });

                namelistOwn.size();
            }

        }

    }
    public void createReturnAirlinesListView(final ArrayList<String> list){
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
                textView.setText(filghtNameListRet.get(i));
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
                layoutAirlinesRet.addView(linearLayout);

                linearLayout.addView(textView);
                linearLayout.addView(checkBox);

                final int finalI = i;
                namelistRet=new ArrayList<String>();
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        String name="";
                        if(cb.isChecked()){
                            //cb.setChecked(true);
                            if(namelistRet.size()==0){
                                namelistRet=new ArrayList<String>(Collections.singleton(list.get(finalI)));
                            }
                            else {
                                namelistRet.add(list.get(finalI));
                            }
                            name = name + "\n" + list.get(finalI);
                            Toast.makeText(RetrunFlightFilterActivity.this, name,Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //strAirlines="All";
                            namelistRet.remove(list.get(finalI));
                            //cb.setChecked(false);
                            }
                    }
                });

                namelistRet.size();
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
