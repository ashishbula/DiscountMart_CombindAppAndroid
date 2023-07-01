package in.discountmart.utility_services.travel.bus.bus_activity;

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
import in.discountmart.utility_services.travel.bus.bus_sharedprference.BusSharedValues;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PessengerInfo;

public class BusFilterActivity extends AppCompatActivity
{
    TextView txtReset;
    ImageView imgClose;
    Button btnApply;
    AppCompatCheckBox checkBoxBefore6am;
    AppCompatCheckBox checkBox6amTo12pm;
    AppCompatCheckBox checkBox12pmTo18pm;
    AppCompatCheckBox checkBoxAfter6pm;
    AppCompatCheckBox checkAc;
    AppCompatCheckBox checkNonAc;
    AppCompatCheckBox checkSleeper;
    AppCompatCheckBox checkSeat;
    LinearLayout layoutTravelName;
    LinearLayout layoutTravel;
    LinearLayout layoutBoardPoint;
    LinearLayout layoutBoardPointName;
    LinearLayout layoutDropPoint;
    LinearLayout layoutDropPointName;

    ArrayList<String> busNameList = new ArrayList<String>();
    ArrayList<String> bordingPointList = new ArrayList<String>();
    ArrayList<String> dropingPointList = new ArrayList<String>();

    ArrayList<NameListModel> nameListList;
    String type="";
    String before6Am="";
    String time6amTo12pm="";
    String time12pmTo18pm="";
    String after6pm="";
    String sleeper="";
    String seat="";
    String ac="";
    String nonAc="";
    boolean timeFlag;
    boolean typeFlag;
    ArrayList<BusTime_TypeModel> typeModelArrayList;
    ArrayList<String> departTimeList;
    ArrayList<String> tempTimeList;
    ArrayList<String> busTypeList;
    ArrayList<String> tempbusTypeList;

    ArrayList<String> tempTravelList;
    ArrayList<String> travelList;
    ArrayList<String> boardPointList;
    ArrayList<String> tempBoardPointList;
    ArrayList<String> tempDropPointList;
    ArrayList<String> dropPointList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bus_filter);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            txtReset=(TextView)findViewById(R.id.bus_filter_act_txt_reset);
            imgClose=(ImageView)findViewById(R.id.bus_filter_act_img_close);
            checkBoxBefore6am=(AppCompatCheckBox)findViewById(R.id.bus_filter_act_chekbox_bef6am);
            checkBox6amTo12pm=(AppCompatCheckBox)findViewById(R.id.bus_filter_act_chekbox_6amto12pm);
            checkBox12pmTo18pm=(AppCompatCheckBox)findViewById(R.id.bus_filter_act_chekbox_12amto18pm);
            checkBoxAfter6pm=(AppCompatCheckBox)findViewById(R.id.bus_filter_act_chekbox_aft6am);
            checkAc=(AppCompatCheckBox)findViewById(R.id.bus_filter_act_chekbox_ac);
            checkNonAc=(AppCompatCheckBox)findViewById(R.id.bus_filter_act_chekbox_nonac);
            checkSleeper=(AppCompatCheckBox)findViewById(R.id.bus_filter_act_chekbox_sleeper);
            checkSeat=(AppCompatCheckBox)findViewById(R.id.bus_filter_act_chekbox_seat);
            layoutTravel=(LinearLayout)findViewById(R.id.bus_filter_act_layout_travel);
            layoutTravelName=(LinearLayout)findViewById(R.id.bus_filter_act_layout_travelname);
            layoutBoardPoint=(LinearLayout)findViewById(R.id.bus_filter_act_layout_boardingPont);
            layoutBoardPointName=(LinearLayout)findViewById(R.id.bus_filter_act_layout_boarding_name);
            layoutDropPoint=(LinearLayout)findViewById(R.id.bus_filter_act_layout_dropPoint);
            layoutDropPointName=(LinearLayout)findViewById(R.id.bus_filter_act_layout_dropPoint_name);
            btnApply=(Button)findViewById(R.id.bus_filter_act_btn_apply);

            /*Get List bus Travle Name, Boarding Point , DropPoint List*/
            busNameList=new ArrayList<>();
            bordingPointList=new ArrayList<>();
            dropingPointList=new ArrayList<>();

            nameListList=new ArrayList<NameListModel>();
            departTimeList=new ArrayList<String>();
            tempTimeList=new ArrayList<String>();
            tempbusTypeList=new ArrayList<String>();
            busTypeList=new ArrayList<String>();
            typeModelArrayList=new ArrayList<BusTime_TypeModel>();

            Bundle intent=new Bundle();
            intent.clear();
            intent=getIntent().getExtras();
            if(intent != null){
                busNameList=intent.getStringArrayList("BusName");
                dropingPointList=intent.getStringArrayList("DropPointList");
                bordingPointList=intent.getStringArrayList("BoardPointList");
            }


            /*Check box Before 6am on click*/
            checkBoxBefore6am.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        before6Am="06.00am";
                        timeFlag=true;
                    }
                    else {
                        before6Am="";
                        timeFlag=false;
                    }
                    tempTimeList.add(before6Am);
                }
            });

            /*Check box time 6amto12pm on click*/
            checkBox6amTo12pm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    BusTime_TypeModel time_typeModel=new BusTime_TypeModel();
                    if(isChecked){
                        time6amTo12pm="06.00to12.00";
                        timeFlag=true;

                    }
                    else {
                        time6amTo12pm="";
                        timeFlag=false;
                    }
                    tempTimeList.add(time6amTo12pm);
                }
            });


            /*Check box time 12pmto18pm on click*/
            checkBox12pmTo18pm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        time12pmTo18pm="12.00to18.00";
                        timeFlag=true;
                    }
                    else {
                        time12pmTo18pm="";
                        timeFlag=false;
                    }
                   tempTimeList.add(time12pmTo18pm);
                }
            });

            /*Check box time after 6pm on click*/
            checkBoxAfter6pm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        after6pm="06.00pm";
                        timeFlag=true;
                    }
                    else {
                        after6pm="";
                        timeFlag=false;
                    }
                    tempTimeList.add(after6pm);
                }
            });

            /*Check box but type utility_sleeper click*/
            checkSleeper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        sleeper="Sleeper";
                        typeFlag=true;
                    }
                    else {
                        sleeper="";
                        typeFlag=false;
                    }
                   tempbusTypeList.add(sleeper);
                }
            });

            /*Check box but type seat click*/
            checkSeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        seat="Seat";
                        typeFlag=true;
                    }
                    else {
                        seat="";
                        typeFlag=false;
                    }
                    tempbusTypeList.add(seat);
                }
            });

            /*Check box but type Ac click*/
            checkAc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        ac="Ac";
                        typeFlag=true;
                    }
                    else {
                        ac="";
                        typeFlag=false;
                    }
                    tempbusTypeList.add(ac);
                }
            });

            /*Check box but type Ac click*/
            checkNonAc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        nonAc="NonAc";
                        typeFlag=true;
                    }
                    else {
                        nonAc="";
                        typeFlag=false;
                    }
                    tempbusTypeList.add(nonAc);
                }
            });


            /*text travel name on click get trave name list */
            layoutTravel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Add value in NameList model*/
                    type="Travel";
                    ArrayList<NameListModel> nameListModel=new ArrayList<NameListModel>();
                    if(busNameList.size() > 0){
                        nameListModel=nameInfoList(busNameList);
                    }

                    if(nameListModel.size() > 0){

                        Intent intent1 = new Intent(BusFilterActivity.this, NameListActivity.class);
                        Bundle bundle1=new Bundle();
                        bundle1.putSerializable("NameList",nameListModel);
                        bundle1.putString("Type",type);
                        bundle1.putString("Service","Bus");
                        intent1.putExtras(bundle1);
                        startActivityForResult(intent1, 1);
                        overridePendingTransition(R.anim.slide_up,0);

                    }

                }
            });


            /*text travel name on click get trave name list */
            layoutBoardPoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Add value in NameList model*/
                    type="Board";
                    ArrayList<NameListModel> nameListModel=new ArrayList<NameListModel>();
                    if(bordingPointList.size() > 0){
                        nameListModel=nameInfoList(bordingPointList);
                    }

                    if(nameListModel.size() > 0){

                        Intent intent1 = new Intent(BusFilterActivity.this, NameListActivity.class);
                        Bundle bundle1=new Bundle();
                        bundle1.putSerializable("NameList",nameListModel);
                        bundle1.putString("Type",type);
                        bundle1.putString("Service","Bus");
                        intent1.putExtras(bundle1);
                        startActivityForResult(intent1, 1);
                        overridePendingTransition(R.anim.slide_up,0);

                    }

                }
            });

            /*text travel name on click get trave name list */
            layoutDropPoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Add value in NameList model*/
                    type="Drop";
                    ArrayList<NameListModel> nameListModel=new ArrayList<NameListModel>();
                    if(dropingPointList.size() > 0){
                        nameListModel=nameInfoList(dropingPointList);
                    }

                    if(nameListModel.size() > 0){

                        Intent intent1 = new Intent(BusFilterActivity.this, NameListActivity.class);
                        Bundle bundle1=new Bundle();
                        bundle1.putSerializable("NameList",nameListModel);
                        bundle1.putString("Type",type);
                        bundle1.putString("Service","Bus");
                        intent1.putExtras(bundle1);
                        startActivityForResult(intent1, 1);
                        overridePendingTransition(R.anim.slide_up,0);

                    }

                }
            });

            /*Button Apply on click*/
            btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        departTimeList=new ArrayList<String>();
                        busTypeList=new ArrayList<String>();

                        /*Add departure time in list*/
                        if(tempTimeList.size() > 0){
                            for(int i=0; i< tempTimeList.size(); i++){
                                if(! tempTimeList.get(i).contentEquals("")){
                                    departTimeList.add(tempTimeList.get(i));
                                }
                            }

                        }
                        else {
                            departTimeList=new ArrayList<String>();
                        }
                        /*Add departure time in list*/
                        if(tempbusTypeList.size() > 0 ){
                            for(int i=0; i< tempbusTypeList.size(); i++){
                                if(! tempbusTypeList.get(i).contentEquals("")){
                                    busTypeList.add(tempbusTypeList.get(i));
                                }
                            }
                        }
                        else {
                            busTypeList=new ArrayList<String>();
                        }

                        if(busTypeList.size() > 0 || departTimeList.size() > 0 || travelList.size() > 0 ||
                               boardPointList.size() > 0 || dropPointList.size() > 0){

                           Intent filterIntent=new Intent(BusFilterActivity.this,BusSearchListActivity.class);
                           Bundle bundle=new Bundle();
                           bundle.putSerializable("BusTimeType",typeModelArrayList);
                           bundle.putString("Before6am",before6Am);
                           bundle.putString("Time6amTo12pm",time6amTo12pm);
                           bundle.putString("Time12pmTo18pm",time12pmTo18pm);
                           bundle.putString("After6pm",after6pm);
                           bundle.putString("Sleeper",sleeper);
                           bundle.putString("Seat",seat);
                           bundle.putString("Ac",ac);
                           bundle.putString("NonAc",nonAc);
                           bundle.putStringArrayList("TimeList",departTimeList);
                           bundle.putStringArrayList("TypeList",busTypeList);
                           bundle.putStringArrayList("DropPoint",dropPointList);
                           bundle.putStringArrayList("BoardPoint",boardPointList);
                           bundle.putStringArrayList("BusTravel",travelList);
                           filterIntent.putExtras(bundle);
                           setResult(RESULT_OK, filterIntent);

                           finish();


                       }
                       else {
                           Toast.makeText(BusFilterActivity.this,"Please select any option for filter",Toast.LENGTH_SHORT).show();
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
            e.printStackTrace();
        }


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        try {
            if(requestCode == 1){
                if(resultCode == RESULT_OK){
                    Bundle filterbundle=data.getExtras();
                    String str = data.getStringExtra("Type");
                    if(filterbundle != null){
                        if (str.contentEquals("Travels")){
                            ArrayList<String> arrayTravelList=filterbundle.getStringArrayList("TravelsList");
                            assert arrayTravelList != null;
                            if(arrayTravelList.size() > 0){
                                BusSharedValues.getInstance().selectTravelsList=arrayTravelList;
                                travelList=new ArrayList<String>();
                                travelList.addAll(arrayTravelList);
                                createTravelListView(arrayTravelList);
                            }
                        }
                        else if (str.contentEquals("Board")){
                            ArrayList<String> arrayBoardList=filterbundle.getStringArrayList("BoardList");
                            assert arrayBoardList != null;
                            if(arrayBoardList.size() > 0){
                                BusSharedValues.getInstance().selectBordPointList=arrayBoardList;
                                boardPointList=new ArrayList<String>();
                                boardPointList.addAll(arrayBoardList);
                                createBoardPointListView(arrayBoardList);
                            }
                        }

                        else if (str.contentEquals("Drop")){
                            ArrayList<String> arrayDropList=filterbundle.getStringArrayList("DropList");
                            assert arrayDropList != null;
                            if(arrayDropList.size() > 0){
                                BusSharedValues.getInstance().selectDropPointList=arrayDropList;
                                dropPointList=new ArrayList<String>();
                                dropPointList.addAll(arrayDropList);
                                createDropPointListView(arrayDropList);
                            }
                        }
                    }


                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createTravelListView(final ArrayList<String> list){
        /*Set Dynamically add Linear Layout,TextView,CheckBox*/

        if(layoutTravelName != null)
            layoutTravelName.removeAllViews();

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

                layoutTravelName.addView(linearLayout);
                linearLayout.addView(textView);



            }

        }

    }

    public void createBoardPointListView(final ArrayList<String> list){
        /*Set Dynamically add Linear Layout,TextView,CheckBox*/

        if(layoutBoardPointName != null)
            layoutBoardPointName.removeAllViews();

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

                layoutBoardPointName.addView(linearLayout);
                linearLayout.addView(textView);



            }

        }

    }

    public void createDropPointListView(final ArrayList<String> list){
        /*Set Dynamically add Linear Layout,TextView,CheckBox*/

        if(layoutDropPointName != null)
            layoutDropPointName.removeAllViews();

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

                layoutDropPointName.addView(linearLayout);
                linearLayout.addView(textView);



            }

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


}
