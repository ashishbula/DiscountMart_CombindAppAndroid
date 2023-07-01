package in.discountmart.utility_services.travel.flight.flight_activity;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import in.base.ui.BaseActivity;
import in.discountmart.R;
import in.discountmart.utility_services.travel.flight.adapter.FlightSearchListAdapter;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightSearchResponse;
import in.discountmart.utility_services.travel.flight.flight_sharedpreference.FlightSharedValues;
import in.discountmart.utility_services.utilities.DividerItemDecoration;

public class FlightSearchListActivity extends BaseActivity implements FlightSearchListAdapter.FlightListAdapterListener {

    Toolbar fToolbar;
    TextView txtFromCity;
    TextView txtToCity;
    TextView txtDepartDate;
    TextView txtAdult;
    TextView txtChild;
    TextView txtInfants;
    TextView txtTotalFlight;
    TextView txtFilter;
    TextView txtShortBy;
    TextView txtNonStop;
    SwitchCompat switchCompatNonstop;
    RecyclerView recyclerView;
    LinearLayout layoutShort;
    LinearLayout layoutFilter;
    LinearLayout layoutNotstop;

    LinearLayout layoutBottom;
    CoordinatorLayout coordinatorLayout;

    ArrayList<FlightSearchResponse> flightSearchList;
    ArrayList<FlightSearchResponse> resetflightSearchList;
    ArrayList<FlightSearchResponse> filterFlightSearchList;
    ArrayList<String> filterNameList;

    ArrayList<String> flightNameList = new ArrayList<String>();
    FlightSearchResponse flightSearchResponse[];
    FlightSearchListAdapter adapter;

    String strFromCity="";
    String strToCity="";
    String strAdult="";
    String strChild="";
    String strInfant="";
    String strDepartDate="";
    String shortby="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_flight_search_list);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            fToolbar=(Toolbar)findViewById(R.id.flight_search_list_act_toolbar);
            setSupportActionBar(fToolbar);

            // toolbar fancy stuff
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            /*Window window = getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.TRANSPARENT); }*/

            txtFromCity=(TextView)findViewById(R.id.flight_search_list_act_toolbar_txtfromcity);
            txtToCity=(TextView)findViewById(R.id.flight_search_list_act_toolbar_txttocity);
            txtDepartDate=(TextView)findViewById(R.id.flight_search_list_act_toolbar_txt_dDate);
            txtAdult=(TextView)findViewById(R.id.flight_search_list_act_toolbar_txt_adult);
            txtChild=(TextView)findViewById(R.id.flight_search_list_act_toolbar_txt_child);
            txtInfants=(TextView)findViewById(R.id.flight_search_list_act_toolbar_txt_infants);
            txtTotalFlight=(TextView)findViewById(R.id.flight_search_list_actvity_txt_totalflight);
            txtShortBy=(TextView)findViewById(R.id.flight_search_list_actvity_txt_select_short);
            txtFilter=(TextView)findViewById(R.id.flight_search_list_actvity_txt_filter);
            txtNonStop=(TextView)findViewById(R.id.flight_search_list_actvity_txt_nonstop);
            layoutBottom=(LinearLayout)findViewById(R.id.flight_search_list_actvity_layout_bottom);
            layoutShort=(LinearLayout)findViewById(R.id.flight_search_list_actvity_layout_short);
            layoutNotstop=(LinearLayout)findViewById(R.id.flight_search_list_actvity_layout_nonStop);
            switchCompatNonstop=(SwitchCompat)findViewById(R.id.flight_search_list_actvity_switch_btn);
            //coordinatorLayout=(CoordinatorLayout) findViewById(R.id.flight_search_list_actvity_coordinator);

            /*Get Value from previous activity by bundle */
            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                flightSearchList=new ArrayList<FlightSearchResponse>();
                resetflightSearchList=new ArrayList<FlightSearchResponse>();
                 flightSearchResponse=(FlightSearchResponse[])bundle.getSerializable("FlightList");
                flightSearchList= new ArrayList<FlightSearchResponse>(Arrays.asList(flightSearchResponse));
                resetflightSearchList= new ArrayList<FlightSearchResponse>(Arrays.asList(flightSearchResponse));



                strFromCity=bundle.getString("FromCity");
                strToCity=bundle.getString("ToCity");
                strAdult=bundle.getString("Adult");
                strChild=bundle.getString("Child");
                strInfant=bundle.getString("Infants");
                strDepartDate=bundle.getString("DepartDate");


                strFromCity = strFromCity.replaceAll("\\(","");
                strFromCity = strFromCity.replaceAll("\\)","");
                String []from=strFromCity.split(" ");
                String fromcitycode="";
                String fromcity="";
                if(from.length == 2){
                     fromcitycode= from[0];
                     fromcity= from[1]+ "";
                }
                else if(from.length == 3){
                    fromcitycode= from[0];
                    fromcity= from[1]+" " +from[2];
                }
                else if(from.length == 4){
                    fromcitycode= from[0];
                    fromcity= from[1]+" " +from[2]+" "+from[3];
                }

                strFromCity=fromcity;
                txtFromCity.setText(strFromCity);

                strToCity = strToCity.replaceAll("\\(","");
                strToCity = strToCity.replaceAll("\\)","");
                String []tocity=strToCity.split(" ");
                String tocitycode= "";
                String tocityname= "";

                if(tocity.length == 2){
                     tocitycode= tocity[0];
                    tocityname= tocity[1];
                }
                else if(tocity.length == 3){
                    tocitycode= tocity[0];
                    tocityname= tocity[1]+" " +tocity[2];
                }
                else if(tocity.length == 4){
                    tocitycode= tocity[0];
                    tocityname= tocity[1]+" " +tocity[2]+" "+tocity[3];
                }
                strToCity=tocityname;
                txtToCity.setText(strToCity);

                txtAdult.setText("Adult "+strAdult);
                txtChild.setText("Child "+strChild);
                txtInfants.setText("Inf "+strInfant);
                txtDepartDate.setText("Dep "+strDepartDate);

                int totalFlight=flightSearchList.size();
                txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));

            }

            //CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) layoutBottom.getLayoutParams();
            //layoutParams.setBehavior(new LinearLayoutBehavior());
            recyclerView=(RecyclerView)findViewById(R.id.flight_search_list_actvity_recycler);

            //flightSearchList = new ArrayList<>();
            adapter = new FlightSearchListAdapter(FlightSearchListActivity.this, flightSearchList, this);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(FlightSearchListActivity.this, DividerItemDecoration.VERTICAL_LIST,0));
            recyclerView.setAdapter(adapter);

            //Recyclerview On scroll listener
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    switch (newState) {
                        case RecyclerView.SCROLL_STATE_IDLE:
                            if(layoutBottom.getVisibility()== View.GONE)
                                layoutBottom.setVisibility(View.VISIBLE);
                            break;
                        /*case RecyclerView.SCROLL_STATE_DRAGGING:
                            if(layoutBottom.getVisibility()==View.GONE)
                                layoutBottom.setVisibility(View.VISIBLE);
                            break;
                        case RecyclerView.SCROLL_STATE_SETTLING:
                            System.out.println("Scroll Settling");
                            break;*/

                    }

                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if (dy > 0 ) {

                        //hideBottomNavigationView(layoutBottom);
                        layoutBottom.setVisibility(View.GONE);


                    } else if (dy < 0 ) {

                        //hideBottomNavigationView(layoutBottom);
                        layoutBottom.setVisibility(View.GONE);


                    }
                }
            });

            /*Text Short Click Listener  open list by short */
            layoutShort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(FlightSearchListActivity.this, FlightShortByActivity.class);
                    intent1.putExtra("Type","Ownward");
                    intent1.putExtra("OwnFlight","");
                    intent1.putExtra("ReturnFlight","");
                    startActivityForResult(intent1, 1);
                    openActivityfromBottom();
                }
            });

            /*Text Filter Click listener open filter list activity*/
            txtFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*calculate size of fligth search list and short alphabetically flight name wise*/
                    ArrayList<String> tempNameList=new ArrayList<>();
                    if(flightSearchList.size() > 0){
                        for (int i=0; i < flightSearchList.size();i++){
                            tempNameList.add(flightSearchList.get(i).getAirlineName());
                        }

                        if(tempNameList.size()> 0){
                            flightNameList=sortAscending(tempNameList);

                        }
                        if(flightNameList.size() > 0){

                            Set<String> set = new LinkedHashSet<>(flightNameList);
                            flightNameList.clear();
                            flightNameList.addAll(set);
                            Intent intent1 = new Intent(FlightSearchListActivity.this, FlightFilterActivity.class);
                            Bundle bundle1=new Bundle();
                            bundle1.putStringArrayList("FlightName",flightNameList);
                            bundle1.putString("FlightType","Ownward");
                            intent1.putExtras(bundle1);
                            startActivityForResult(intent1, 2);
                            openActivityfromBottom();
                            System.out.println(set + " common name in the list");

                        }
                        else {

                        }
                    }
                }
            });

            /*Switch Compact Bottun checked change linstener*/
            layoutNotstop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean switchBtn=switchCompatNonstop.isChecked();
                    if(!switchBtn){

                        switchCompatNonstop.setChecked(true);
                        String str="Non Stop";

                        findnonStopflight(str);

                    }
                    else {
                        switchCompatNonstop.setChecked(false);
                        if(flightSearchList.size() > 0){
                            txtNonStop.setTextColor(getResources().getColor(R.color.black));
                            adapter.setData(FlightSearchListActivity.this,flightSearchList,FlightSearchListActivity.this,shortby);
                            recyclerView.setAdapter(adapter);
                            int totalFlight=flightSearchList.size();
                            txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));

                        }
                    }
                }
            });

            switchCompatNonstop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // The toggle is enabled
                        String str="Non Stop";

                        findnonStopflight(str);
                    } else {
                        // The toggle is disabled
                        if(flightSearchList.size() > 0){
                            txtNonStop.setTextColor(getResources().getColor(R.color.black));
                            adapter.setData(FlightSearchListActivity.this,flightSearchList,FlightSearchListActivity.this,shortby);
                            recyclerView.setAdapter(adapter);
                            int totalFlight=flightSearchList.size();
                            txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));
                        }

                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void hideBottomNavigationView(LinearLayout view) {
        view.animate().translationY(view.getHeight());
        view.setVisibility(View.GONE);
    }

    private void showBottomNavigationView(LinearLayout view) {
        view.animate().translationY(0);
    }


    /*Back Press Arrow o ToolBar*/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    @Override
    public void onFlightSelected(FlightSearchResponse contact) {

        try{
           // FlightSearchResponse[]flightList= new FlightSearchResponse[contact];
            ArrayList<FlightSearchResponse> arrayFlightList=new ArrayList<FlightSearchResponse>(Arrays.asList(contact));
            if(arrayFlightList.size() > 0) {

                FlightSharedValues.getInstance().flightSelectArrayList=new ArrayList<FlightSearchResponse>(Arrays.asList(contact));
                FlightSharedValues.getInstance().flightSearchResponse=contact;
                Bundle bundle = new Bundle();
                bundle.putSerializable("FlightList", contact);
                bundle.putString("FromCity", strFromCity);
                bundle.putString("ToCity", strToCity);
                bundle.putString("Adult", strAdult);
                bundle.putString("Child", strChild);
                bundle.putString("Infants", strInfant);
                bundle.putString("DepartDate", strDepartDate);
                Intent intent = new Intent(FlightSearchListActivity.this, SelectFlightActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /*Receive Intent result form Flight filter activity */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String str = data.getStringExtra("ShortBy");

                /*flight short by cheapest value*/
                if (str.contentEquals("Cheapest")){
                    shortby=str;
                    txtShortBy.setText(str);
                    adapter.setData(FlightSearchListActivity.this,flightSearchList,this,shortby);
                    recyclerView.setAdapter(adapter);

                }
                /*flight short by Highest value*/
                else if (str.contentEquals("Highest")){
                    shortby=str;
                    txtShortBy.setText(str);
                    adapter.setData(FlightSearchListActivity.this,flightSearchList,this,shortby);
                    recyclerView.setAdapter(adapter);
                }
                /*flight short by Fastest value*/
                else if (str.contentEquals("Fastest")){
                    shortby=str;
                    txtShortBy.setText(str);
                    adapter.setData(FlightSearchListActivity.this,flightSearchList,this,shortby);
                    recyclerView.setAdapter(adapter);
                }

                /*flight short by Non stop flight*/
                /*else if (str.contentEquals("Non Stop")){
                    shortby=str;
                    txtShortBy.setText(str);
                    findItemInTheList(shortby);
                }*/
                /*flight short by late take of  (11.00 am to 5.00 pm)*/
                else if (str.contentEquals("Late Take off")){
                    shortby=str;
                    txtShortBy.setText(str);
                    adapter.setData(FlightSearchListActivity.this,flightSearchList,this,shortby);
                    recyclerView.setAdapter(adapter);
                }
                /*flight short by late take of before (11.00 am)*/
                else if (str.contentEquals("Early Take off")){
                    shortby=str;
                    txtShortBy.setText(str);
                    adapter.setData(FlightSearchListActivity.this,flightSearchList,this,shortby);
                    recyclerView.setAdapter(adapter);
                }
                /*flight short by early landing*/
                else if (str.contentEquals("Early Landing")){
                    shortby=str;
                    txtShortBy.setText(str);
                    adapter.setData(FlightSearchListActivity.this,flightSearchList,this,shortby);
                    recyclerView.setAdapter(adapter);
                }
                /*flight short by late landing*/
                else if (str.contentEquals("Late Landing")){
                    shortby=str;
                    txtShortBy.setText(str);
                    adapter.setData(FlightSearchListActivity.this,flightSearchList,this,shortby);
                    recyclerView.setAdapter(adapter);
                }
            }
            /*else if(resultCode== RESULT_CANCELED) {
                flightSearchList=resetflightSearchList;
                adapter.setData(FlightSearchListActivity.this,flightSearchList,this,"");
                recyclerView.setAdapter(adapter);
                int totalFlight=flightSearchList.size();
                txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));
            }*/
        }
        else if(requestCode == 2){
            if(resultCode == RESULT_OK){

                ArrayList<FlightSearchResponse> tempfilterList=new ArrayList<FlightSearchResponse>();
                filterFlightSearchList=new ArrayList<FlightSearchResponse>();
                Bundle filterbundle=data.getExtras();
                if(filterbundle != null){
                    ArrayList<String> nameList = filterbundle.getStringArrayList("AirlinesList");
                    String strAirlines=data.getStringExtra("Airlines");
                    String strRefund=data.getStringExtra("Refund");
                    String strStop=data.getStringExtra("Stop");
                    String strDepTime=data.getStringExtra("DepTime");
                    String strFlightClass=data.getStringExtra("FlightClass");
                    filterNameList=new ArrayList<String>();

                    filterFlightSearchList.addAll(flightSearchList);
                    /*Flight Refundable wise filter*/
                    if(strRefund.contentEquals("All")){
                        filterFlightSearchList.size();

                    }
                    else {
                        for(int i=0; i <filterFlightSearchList.size(); i++) {

                            if(strRefund.contentEquals(filterFlightSearchList.get(i).getFairType())){
                                tempfilterList.add(filterFlightSearchList.get(i));

                            }
                            else {
                                filterFlightSearchList.size();
                            }
                        }
                        if(tempfilterList.size() > 0)
                            filterFlightSearchList=tempfilterList;
                        else
                            filterFlightSearchList.size();
                    }

                    /*Flight stops wise filter*/
                    if(strStop.contentEquals("All")){
                        filterFlightSearchList.size();
                    }
                    else {
                        tempfilterList.clear();
                        for (int i=0;i < filterFlightSearchList.size(); i++){
                            if(strStop.contentEquals(filterFlightSearchList.get(i).getStopage())){

                                tempfilterList.add(filterFlightSearchList.get(i));

                            }
                            else {
                                filterFlightSearchList.size();
                            }
                        }
                        if(tempfilterList.size() > 0){
                            filterFlightSearchList=tempfilterList;
                            //tempfilterList.clear();
                        }

                        else{
                            filterFlightSearchList.size();
                        }

                    }

                   /*Departure Date wise Filter*/
                    if(strDepTime.contentEquals("All")){
                        filterFlightSearchList.size();
                    }
                    else {

                        /*Before 11.00 am*/
                        if(strDepTime.contentEquals("11.00")){
                            String time="11:00";
                            tempfilterList=new ArrayList<FlightSearchResponse>();
                            final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.UK);//24 hour time formet

                            Date dateTime = null;
                            Date receiveTime=null;
                            try {
                                receiveTime= dateFormat.parse(time);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            for(int i=0; i < filterFlightSearchList.size(); i++){
                                try {
                                    dateTime=dateFormat.parse(filterFlightSearchList.get(i).getDepartureTime());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                assert dateTime != null;
                                if(dateTime.before(receiveTime)){
                                    tempfilterList.add(filterFlightSearchList.get(i));

                                }
                                else {
                                    filterFlightSearchList.size();
                                }
                            }
                            if(tempfilterList.size() > 0){
                                filterFlightSearchList=tempfilterList;
                                //tempfilterList.clear();
                            }

                            else{
                                filterFlightSearchList.size();
                            }
                        }
                        /*11.00 Am to 5.00pm*/
                        else if(strDepTime.contentEquals("11.00to17.00")){

                            tempfilterList=new ArrayList<FlightSearchResponse>();
                            final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.UK);//24 hour time formet
                            String startTime="11:00";
                            String endTime="17:00";
                            Date startDTime=null;
                            Date endDTime=null;
                            Date datetime=null;
                            try {
                                startDTime=dateFormat.parse(startTime);
                                endDTime=dateFormat.parse(endTime);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            for (int i=0; i < filterFlightSearchList.size();i++){
                                try {

                                    datetime=dateFormat.parse(filterFlightSearchList.get(i).getDepartureTime());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                assert datetime != null;
                                if(datetime.after(startDTime) && datetime.before(endDTime) ||datetime.equals(startDTime)){
                                    tempfilterList.add(filterFlightSearchList.get(i));
                                }
                                else {
                                    filterFlightSearchList.size();
                                }
                            }
                            if(tempfilterList.size() > 0){
                                filterFlightSearchList=tempfilterList;
                                //tempfilterList.clear();
                            }

                            else{
                                filterFlightSearchList.size();
                            }

                        }
                        /*5.00 pm to 9.00pm (17.00pm to 21.00pm )*/
                        else if(strDepTime.contentEquals("17.00to21.00")){
                            tempfilterList=new ArrayList<FlightSearchResponse>();
                            final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.UK);//24 hour time formet
                            String startTime="17:00";
                            String endTime="21:00";
                            Date startDTime=null;
                            Date endDTime=null;
                            Date datetime=null;
                            try {
                                startDTime=dateFormat.parse(startTime);
                                endDTime=dateFormat.parse(endTime);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            for (int i=0; i < filterFlightSearchList.size();i++){
                                try {

                                    datetime=dateFormat.parse(filterFlightSearchList.get(i).getDepartureTime());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                assert datetime != null;
                                if(datetime.after(startDTime) && datetime.before(endDTime) ||datetime.equals(startDTime)){
                                    tempfilterList.add(filterFlightSearchList.get(i));
                                }
                                else {
                                    filterFlightSearchList.size();
                                }
                            }
                            if(tempfilterList.size() > 0){
                                filterFlightSearchList=tempfilterList;
                                //tempfilterList.clear();
                            }

                            else{
                                filterFlightSearchList.size();
                            }

                        }
                        /*after 9.00pm*/
                        else if(strDepTime.contentEquals("21.00")){
                            String time="21:00";
                            final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.UK);//24 hour time formet

                            Date dateTime = null;
                            Date receiveTime=null;
                            try {
                                receiveTime= dateFormat.parse(time);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            for(int i=0; i < filterFlightSearchList.size(); i++){
                                try {
                                    dateTime=dateFormat.parse(filterFlightSearchList.get(i).getDepartureTime());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }


                                assert dateTime != null;
                                if(dateTime.after(receiveTime)){
                                    tempfilterList.add(filterFlightSearchList.get(i));

                                }
                                else {
                                    filterFlightSearchList.size();
                                }
                            }
                            if(tempfilterList.size() > 0){
                                filterFlightSearchList=tempfilterList;
                                //tempfilterList.clear();
                            }

                            else{
                                filterFlightSearchList.size();
                            }
                        }
                    }

                    /*Flight Airlines name wise filter*/
                    assert nameList != null;
                    if(nameList.size() > 0){

                        //filterNameList.addAll(filterNameList.size(),nameList);
                        filterNameList.addAll(nameList);

                        /*Add Airlines Name in String arraylst form Arraylist of object*/
                            //tempfilterList.clear();
                        tempfilterList=new ArrayList<>();
                        for(int j=0;j < filterFlightSearchList.size();j++){
                           // boolean ans= tempnameSet.containsAll(filterNameList);
                            for(int k=0; k<filterNameList.size();k++){

                                if(filterNameList.get(k).equals(filterFlightSearchList.get(j).getAirlineName())){
                                    tempfilterList.add(filterFlightSearchList.get(j));
                                }
                                else {
                                    filterFlightSearchList.size();
                                }
                            }


                        }
                        if(tempfilterList.size() > 0)
                            filterFlightSearchList=tempfilterList;
                        else
                            filterFlightSearchList.size();
                    }
                    else {
                        filterNameList.size();
                        if(tempfilterList.size() > 0)
                            filterFlightSearchList=tempfilterList;
                        else
                            filterFlightSearchList.size();

                    }

                    if(filterFlightSearchList.size() > 0){
                        flightSearchList=filterFlightSearchList;
                        adapter.setData(FlightSearchListActivity.this,flightSearchList,this,"");
                        recyclerView.setAdapter(adapter);
                        int totalFlight=flightSearchList.size();
                        txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));
                    }
                    else {

                        adapter.setData(FlightSearchListActivity.this,filterFlightSearchList,this,"");
                        recyclerView.setAdapter(adapter);
                        int totalFlight=filterFlightSearchList.size();
                        txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));
                        Toast.makeText(FlightSearchListActivity.this,"No flight found",Toast.LENGTH_SHORT).show();
                    }

                }

            }
            else if(resultCode== RESULT_CANCELED) {
                flightSearchList=resetflightSearchList;
                adapter.setData(FlightSearchListActivity.this,flightSearchList,this,"");
                recyclerView.setAdapter(adapter);
                int totalFlight=flightSearchList.size();
                txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));
            }
        }
    }

    /*find fastest flight in utility_main flight search list*/
    private void findItemInTheList(String itemToFind) {
        try {
            ArrayList<FlightSearchResponse> tempList=new ArrayList<FlightSearchResponse>();
            FlightSearchResponse findList=new FlightSearchResponse();
            if(flightSearchList.size() > 0){


                if (checkAvailability(flightSearchList,itemToFind)) {

                    // System.out.println(itemToFind + " was found in the list");
                    for (int i=0; i< flightSearchList.size(); i++){
                        findList=flightSearchList.get(i);
                        if(findList.getStopage().contentEquals(itemToFind)){

                            if(tempList.size() ==0){
                                tempList=new ArrayList<FlightSearchResponse>(Collections.singleton(findList));
                            }
                            else {
                                tempList.add(findList);

                            }
                        }
                    }

                    if(tempList.size() > 0){

                        adapter.setData(FlightSearchListActivity.this,tempList,this,shortby);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    System.out.println(itemToFind + " flight not found in the list");
                    Toast.makeText(this,itemToFind + " flight not found in the list",Toast.LENGTH_SHORT).show();
                }
            }
            else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /*Method for find non stop flight*/
    private void findnonStopflight(String itemToFind) {
        try {
            ArrayList<FlightSearchResponse> tempList=new ArrayList<FlightSearchResponse>();
            FlightSearchResponse findList=new FlightSearchResponse();
            if(flightSearchList.size() > 0){

                if (checkAvailability(flightSearchList,itemToFind)) {

                    // System.out.println(itemToFind + " was found in the list");
                    for (int i=0; i< flightSearchList.size(); i++){
                        findList=flightSearchList.get(i);
                        if(findList.getStopage().contentEquals(itemToFind)){

                            if(tempList.size() ==0){
                                tempList=new ArrayList<FlightSearchResponse>(Collections.singleton(findList));
                            }
                            else {
                                tempList.add(findList);

                            }
                        }
                    }

                    if(tempList.size() > 0){

                        adapter.setData(FlightSearchListActivity.this,tempList,this,shortby);
                        recyclerView.setAdapter(adapter);
                        txtNonStop.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        switchCompatNonstop.setChecked(true);
                        int totalFlight=tempList.size();
                        txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));
                    }
                } else {
                    txtNonStop.setTextColor(getResources().getColor(R.color.black));
                    switchCompatNonstop.setChecked(false);
                    System.out.println(itemToFind + " flight not found in the list");
                    Toast.makeText(this,itemToFind + " flight not found in the list",Toast.LENGTH_SHORT).show();
                }
            }
            else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static boolean checkAvailability(ArrayList<FlightSearchResponse> stopflight, String response) {
        for(FlightSearchResponse p: stopflight) {
            if (p.getStopage().equals(response)) {
                return true;
            }
        }
        return false;
    }

    /*Short array list alphabetic assending*/
    public ArrayList<String> sortAscending(ArrayList<String> list) {
        Collections.sort(list);
        return list;
    }

    /*Short array list with common name*/
    public ArrayList<String> findcommomElement(ArrayList<String> list,ArrayList<String> list2) {

        return list;
    }

    /*Method for filter flight according to brfore 11.00am(time wise) */
    public void before11am(){
        //shorting

        final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.UK);//24 hour time formet
        final int[] date = new int[1];
        Collections.sort(flightSearchList, new Comparator<FlightSearchResponse>() {
            @Override
            public int compare(FlightSearchResponse o1, FlightSearchResponse o2) {
                try{
                    String depTime1 = o1.getDepartureTime();

                    String depTime2 = o2.getDepartureTime();

                    return dateFormat.parse(depTime1).compareTo(dateFormat.parse(depTime2));
                }catch (Exception e){
                    e.printStackTrace();
                }

                return 0;
            }
        });
    }
}
