package in.discountmart.utility_services.travel.hotel.hotel_activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.activity.NameListActivity;
import in.discountmart.utility_services.model.NameListModel;
import in.discountmart.utility_services.travel.bus.bus_model.BusTime_TypeModel;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PessengerInfo;
import in.discountmart.utility_services.travel.hotel.hotel_shared_preferance.HotelSharedValues;

public class HotelFilterActivity extends AppCompatActivity {

    TextView txtReset;
    ImageView imgClose;
    Button btnApply;

    AppCompatCheckBox checkBox5Star;
    AppCompatCheckBox checkBox4Star;
    AppCompatCheckBox checkBox3Star;
    AppCompatCheckBox checkBox2Star;

    LinearLayout layoutHotelName;
    LinearLayout layoutHotel;


    ArrayList<String> hotelNameList;
    ArrayList<String> hotelList;
    ArrayList<String> hotelStarList;

    ArrayList<NameListModel> nameListList;
    String type="";
    String fiveStar="";
    String fourStar="";
    String threeStar="";
    String twoStar="";

    boolean timeFlag;
    boolean typeFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_hotel_filter);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            txtReset=(TextView)findViewById(R.id.hotel_filter_act_txt_reset);
            imgClose=(ImageView)findViewById(R.id.hotel_filter_act_img_close);
            checkBox5Star=(AppCompatCheckBox)findViewById(R.id.hotel_filter_act_chekbox_5star);
            checkBox4Star=(AppCompatCheckBox)findViewById(R.id.hotel_filter_act_chekbox_4star);
            checkBox3Star=(AppCompatCheckBox)findViewById(R.id.hotel_filter_act_chekbox_3star);
            checkBox2Star=(AppCompatCheckBox)findViewById(R.id.hotel_filter_act_chekbox_2star);

            layoutHotelName=(LinearLayout)findViewById(R.id.hotel_filter_act_layout_hotelname);
            layoutHotel=(LinearLayout)findViewById(R.id.hotel_filter_act_layout_hotel);

            btnApply=(Button)findViewById(R.id.hotel_filter_act_btn_apply);

            /*Get List bus Travle Name, Boarding Point , DropPoint List*/
            hotelNameList=new ArrayList<>();
            hotelStarList=new ArrayList<>();
            nameListList=new ArrayList<NameListModel>();

            Bundle intent=new Bundle();
            intent.clear();
            intent=getIntent().getExtras();
            if(intent != null){
                hotelNameList=intent.getStringArrayList("HotelName");

            }


            /*Check box 5 Star Rating*/
            checkBox5Star.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        fiveStar="5";
                        timeFlag=true;
                        hotelStarList.add(fiveStar);
                    }
                    else {
                        fiveStar="0";
                        timeFlag=false;
                        if(hotelStarList.size() > 0)
                            hotelStarList.remove(fiveStar);

                    }

                }
            });

            /* Check box 4 Star Rating*/
            checkBox4Star.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    BusTime_TypeModel time_typeModel=new BusTime_TypeModel();
                    if(isChecked){
                        fourStar="4";
                        timeFlag=true;
                        hotelStarList.add(fourStar);

                    }
                    else {
                        fourStar="0";
                        timeFlag=false;
                        if(hotelStarList.size() > 0)
                            hotelStarList.remove(fourStar);
                    }
                }
            });


            /* Check box 3 Star Rating*/
            checkBox3Star.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        threeStar="3";
                        timeFlag=true;
                        hotelStarList.add(threeStar);
                    }
                    else {
                        threeStar="0";
                        timeFlag=false;
                        if(hotelStarList.size() > 0)
                            hotelStarList.remove(threeStar);
                    }
                }
            });

            /* Check box 2 Star Rating*/
            checkBox2Star.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        twoStar="2";
                        timeFlag=true;
                        hotelStarList.add(twoStar);
                    }
                    else {
                        twoStar="0";
                        timeFlag=false;
                        if(hotelStarList.size() > 0)
                            hotelStarList.remove(twoStar);
                    }
                }
            });

            /*text travel name on click get trave name list */
            layoutHotel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Add value in NameList model*/
                    type="Hotel";
                    ArrayList<NameListModel> nameListModel=new ArrayList<NameListModel>();
                    if(hotelNameList.size() > 0){
                        nameListModel=nameInfoList(hotelNameList);
                    }

                    if(nameListModel.size() > 0){

                        Intent intent1 = new Intent(HotelFilterActivity.this, NameListActivity.class);
                        Bundle bundle1=new Bundle();
                        bundle1.putSerializable("NameList",nameListModel);
                        bundle1.putString("Type",type);
                        bundle1.putString("Service","Hotel");
                        intent1.putExtras(bundle1);
                        startActivityForResult(intent1, 2);
                        overridePendingTransition(R.anim.slide_up,0);

                    }

                }
            });


            /*Button Apply on click*/
            btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        if(hotelNameList.size() > 0 || hotelStarList.size() > 0 ){

                            Intent filterIntent=new Intent(HotelFilterActivity.this, HotelSearchListActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("fiveStar",fiveStar);
                            bundle.putString("fourStar",fourStar);
                            bundle.putString("threeStar",threeStar);
                            bundle.putString("twoStar",twoStar);
                            bundle.putStringArrayList("NameList",hotelList);
                            bundle.putStringArrayList("StarList",hotelStarList);
                            filterIntent.putExtras(bundle);
                            setResult(RESULT_OK, filterIntent);

                            finish();

                        }
                        else {
                            Toast.makeText(HotelFilterActivity.this,"Please select any option for filter",Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            /*Text Reset Click Listener and Reset Flight Search list*/
            txtReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent resetIntent=new Intent(HotelFilterActivity.this,HotelSearchListActivity.class);
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
            e.printStackTrace();
        }
    }

    /*Array List of Object NameListModel*/
    private ArrayList<NameListModel> nameInfoList(ArrayList<String> arrayList ){


        ArrayList<NameListModel> list = new ArrayList<NameListModel>();

        if(arrayList.size() > 0){
            ArrayList<PessengerInfo> infoAdultList = new ArrayList<>();
            for(int i = 0; i < arrayList.size(); i++){
                NameListModel nameListModel=new NameListModel();
                nameListModel.setName(arrayList.get(i));

                list.add(nameListModel);
            }
        }
        return list;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        try {
            if(requestCode == 2){
                if(resultCode == RESULT_OK){
                    Bundle filterbundle=data.getExtras();
                    String str = data.getStringExtra("Type");
                    if(filterbundle != null){
                        if (str.contentEquals("Hotels")){
                            ArrayList<String> arrayNameList=filterbundle.getStringArrayList("HotelList");
                            assert arrayNameList != null;
                            if(arrayNameList.size() > 0){
                                HotelSharedValues.getInstance().hotelSelectNameList=arrayNameList;
                                hotelList=new ArrayList<String>();
                                hotelList.addAll(arrayNameList);
                                /*Create hotel name layout table*/
                                createHotelNameListView(arrayNameList);
                            }
                        }

                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Method for set Hotel name table*/
    public void createHotelNameListView(final ArrayList<String> list){
        /*Set Dynamically add Linear Layout,TextView,CheckBox*/

        if(layoutHotelName != null)
            layoutHotelName.removeAllViews();

        if(list.size() > 0){
            int pos;
            for (int i=0;i<list.size();i++){

//layoutAirlines.removeAllViews();
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.setMarginStart(10);
                }*/
                LinearLayout linearLayout=new LinearLayout(this);
                linearLayout.setPadding(5,5,5,5);
                linearLayout.setLayoutParams(layoutParams);

                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                TextView textView=new TextView(this);
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView.setText(list.get(i));
                textView.setTextColor(getResources().getColor(R.color.black));

                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_size_normal));

                layoutHotelName.addView(linearLayout);
                linearLayout.addView(textView);



            }

        }

    }
}
