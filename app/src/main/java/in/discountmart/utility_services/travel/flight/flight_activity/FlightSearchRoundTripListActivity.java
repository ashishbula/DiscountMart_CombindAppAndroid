package in.discountmart.utility_services.travel.flight.flight_activity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

import in.discountmart.R;
import in.discountmart.utility_services.travel.flight.adapter.OwnwardFlightSeachListAdapter;
import in.discountmart.utility_services.travel.flight.adapter.ReturnFlightSearchListAdapter;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightSearchReturnResponse;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.OwnwardFlightSearch;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.ReturnFlightSearch;
import in.discountmart.utility_services.travel.flight.flight_sharedpreference.FlightSharedValues;
import in.discountmart.utility_services.utilities.DividerItemDecoration;

public class FlightSearchRoundTripListActivity extends AppCompatActivity implements OwnwardFlightSeachListAdapter.OwnFlightListAdapterListener,
        ReturnFlightSearchListAdapter.ReturnFlightListAdapterListener {

    Toolbar fToolbar;
    TextView txtFromCity;
    TextView txtOwnFlightCity;
    TextView txtToCity;
    TextView txtRetFlightCity;
    TextView txtDepartDate;
    TextView txtOwnDepartDate;
    TextView txtRetDepartDate;
    TextView txtAdult;
    TextView txtChild;
    TextView txtInfants;
    TextView txtTotalFlight;
    TextView txtFilter;
    TextView txtShortBy;
    TextView txtNonStop;
    TextView txtOwnPrice;
    TextView txtReturnPrice;
    TextView txtTotPrice;
    SwitchCompat switchCompatNonstop;
    RecyclerView recyOwnFlight;
    RecyclerView recyReturnFlight;
    LinearLayout layoutShort;
    LinearLayout layoutFilter;
    LinearLayout layoutNotstop;
    Button btnBook;
    LinearLayout layoutBottom;
    LinearLayout layoutReturnNoRrcord;
    LinearLayout layoutOwnNoRrcord;
    CoordinatorLayout coordinatorLayout;
    ArrayList<OwnwardFlightSearch> ownflightSearchList;
    ArrayList<OwnwardFlightSearch> resetownflightSearchList;
    ArrayList<OwnwardFlightSearch> filterownflightSearchList;

    ArrayList<ReturnFlightSearch> returnflightSearchList;
    ArrayList<ReturnFlightSearch> tempReturnflightSearchList;
    ArrayList<ReturnFlightSearch> resetReturnflightSearchList;
    ArrayList<ReturnFlightSearch> filterReturnflightSearchList;

    ArrayList<String> filterOwnNameList;
    ArrayList<String> filterReturnNameList;

    ArrayList<String> ownflightNameList;
    ArrayList<String> retflightNameList;
    FlightSearchReturnResponse flightSearchReturnResponse;
    OwnwardFlightSeachListAdapter ownwardFlightAdapter;
    ReturnFlightSearchListAdapter returnFlightAdapter;
    ReturnFlightSearch returnFlightSearch;
    OwnwardFlightSearch ownwardFlightSearch;

    String strFromCity="";
    String strFromCityCode="";
    String strRetFromCityCode="";
    String strToCity="";
    String strToCityCode="";
    String strRetToCityCode="";
    String strAdult="";
    String strChild="";
    String strInfant="";
    String strRetDepartDate="";
    String strDepartDate="";
    String shortby="";
    String ownFlightPrice="";
    String retFlightPrice="";
    String totFlightPrice="";
    String ownflight="";
    String retflight="";
    int minOwnPrice=0;
    int minRetPrice=0;
    int totPrice=0;
    Date selectDate=null;
    Date selectTime=null;
    Date filterDate=null;
    Date filterTime=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.utility_activity_flight_search_round_trip_list);
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            fToolbar=(Toolbar)findViewById(R.id.return_flight_search_list_act_toolbar);
            setSupportActionBar(fToolbar);


            /*Window window = getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.TRANSPARENT); }*/

            txtFromCity=(TextView)findViewById(R.id.return_flight_search_list_act_toolbar_txtfromcity);
            txtOwnFlightCity=(TextView)findViewById(R.id.own_flight_search_list_actvity_txt_city);
            txtToCity=(TextView)findViewById(R.id.return_flight_search_list_act_toolbar_txttocity);
            txtRetFlightCity=(TextView)findViewById(R.id.return_flight_search_list_actvity_txt_city);
            txtDepartDate=(TextView)findViewById(R.id.return_flight_search_list_act_toolbar_txt_dDate);
            txtOwnDepartDate=(TextView)findViewById(R.id.own_flight_search_list_actvity_txt_date);
            txtRetDepartDate=(TextView)findViewById(R.id.return_flight_search_list_actvity_txt_date);
            txtAdult=(TextView)findViewById(R.id.return_flight_search_list_act_toolbar_txt_adult);
            txtChild=(TextView)findViewById(R.id.return_flight_search_list_act_toolbar_txt_child);
            txtInfants=(TextView)findViewById(R.id.return_flight_search_list_act_toolbar_txt_infants);
            txtOwnPrice=(TextView)findViewById(R.id.own_flight_search_list_actvity_txt_amount);
            txtReturnPrice=(TextView)findViewById(R.id.return_flight_search_list_actvity_txt_amount);
            txtShortBy=(TextView)findViewById(R.id.return_flight_search_list_actvity_txt_select_short);
            txtTotPrice=(TextView)findViewById(R.id.return_flight_search_list_actvity_txt_totamount);
            txtFilter=(TextView)findViewById(R.id.return_flight_search_list_actvity_txt_filter);
            txtNonStop=(TextView)findViewById(R.id.return_flight_search_list_actvity_txt_nonstop);
            layoutBottom=(LinearLayout)findViewById(R.id.return_flight_search_list_actvity_layout_bottom);
            layoutShort=(LinearLayout)findViewById(R.id.return_flight_search_list_actvity_layout_short);
            layoutNotstop=(LinearLayout)findViewById(R.id.return_flight_search_list_actvity_layout_nonStop);
            layoutReturnNoRrcord=(LinearLayout)findViewById(R.id.return_flight_search_list_act_norecord);
            layoutOwnNoRrcord=(LinearLayout)findViewById(R.id.own_flight_search_list_act_norecord);
            switchCompatNonstop=(SwitchCompat)findViewById(R.id.return_flight_search_list_actvity_switch_btn);
            coordinatorLayout=(CoordinatorLayout) findViewById(R.id.return_flight_search_list_actvity_coordinator);
            btnBook=(Button)findViewById(R.id.return_flight_search_list_actvity_btn_book);

            // toolbar fancy stuff
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            /*Get Value form previous activity by Bundle */
            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                flightSearchReturnResponse=new FlightSearchReturnResponse();
                flightSearchReturnResponse=(FlightSearchReturnResponse)bundle.getSerializable("FlightReturnList");

                if(flightSearchReturnResponse!= null){
                    if(flightSearchReturnResponse.getLstOneFlightResponse()!= null &&
                            flightSearchReturnResponse.getLstOneFlightResponse().size() > 0){
                        ownflightSearchList=new ArrayList<OwnwardFlightSearch>();
                        resetownflightSearchList=new ArrayList<OwnwardFlightSearch>();
                        ArrayList<OwnwardFlightSearch> tempSortOwnList=new ArrayList<OwnwardFlightSearch>();
                        //tempSortOwnList= (ArrayList<OwnwardFlightSearch>) bundle.getSerializable("OwnwardFlightList");
                        tempSortOwnList= flightSearchReturnResponse.getLstOneFlightResponse();

                        if(tempSortOwnList != null && tempSortOwnList.size() > 0)
                            ownflightSearchList=cheapestOwnwardFlight(tempSortOwnList);
                    }

                    if(flightSearchReturnResponse.getLstReturnFlightResponse() != null &&
                            flightSearchReturnResponse.getLstReturnFlightResponse().size()>0){


                        tempReturnflightSearchList=new ArrayList<ReturnFlightSearch>();
                        resetReturnflightSearchList=new ArrayList<ReturnFlightSearch>();
                        ArrayList<ReturnFlightSearch> tempSortReturnList=new ArrayList<ReturnFlightSearch>();
                        //tempSortReturnList= (ArrayList<ReturnFlightSearch>) bundle.getSerializable("ReturnFlightList");
                        tempSortReturnList= flightSearchReturnResponse.getLstReturnFlightResponse();

                        if(tempSortReturnList != null && tempSortReturnList.size() > 0)
                            tempReturnflightSearchList=cheapestReturnFlight(tempSortReturnList);
                    }
                }

                if(ownflightSearchList != null && ownflightSearchList.size() > 0 ){
                    resetownflightSearchList=ownflightSearchList;
                }
                /* Assign main return flight list
                 * value to another list */
                if(tempReturnflightSearchList != null && tempReturnflightSearchList.size() > 0  ){
                    resetReturnflightSearchList=tempReturnflightSearchList;
                }

                strFromCity=bundle.getString("FromCity");
                strFromCityCode=bundle.getString("FromCityCode");
                strRetFromCityCode=bundle.getString("RetFromCityCode");
                strToCity=bundle.getString("ToCity");
                strToCityCode=bundle.getString("ToCityCode");
                strRetToCityCode=bundle.getString("RetToCityCode");
                strAdult=bundle.getString("Adult");
                strChild=bundle.getString("Child");
                strInfant=bundle.getString("Infants");
                strDepartDate=bundle.getString("DepartDate");
                strRetDepartDate=bundle.getString("RetDepartDate");

                txtFromCity.setText(strFromCity);
                txtToCity.setText(strToCity);
                txtAdult.setText("Adult "+strAdult);
                txtChild.setText("Child "+strChild);
                txtInfants.setText("Inf "+strInfant);
                txtDepartDate.setText(strDepartDate+" - "+ strRetDepartDate);
                txtOwnFlightCity.setText(strFromCityCode +" - "+strToCityCode);
                txtRetFlightCity.setText(strRetFromCityCode +" - "+strRetToCityCode);
                txtOwnDepartDate.setText(strDepartDate);
                txtRetDepartDate.setText(strRetDepartDate);
                ownflight=strFromCityCode +" - "+strToCityCode;
                retflight=strRetFromCityCode +" - "+strRetToCityCode;

                //int totalFlight=flightSearchList.size();
                //txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));

            }


            /*get the lowest price of ownward and return flight and set in text */
            if(ownflightSearchList.size() > 0 && tempReturnflightSearchList.size() > 0){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // OwnwardFlight object which has lowest prict flight
                   // OwnwardFlightSearch ownwardFlight =  Collections.min(ownflightSearchList.stream(), Comparator.comparing(s -> s.getGrossAmount()));
                    //ownwardFlightSearch = ownflightSearchList.stream().min(Comparator.comparing(OwnwardFlightSearch::getGrossAmount)).get();
                    ownwardFlightSearch = Collections.min(ownflightSearchList, new OwnwardCheapestFlight());

                    /*Get lowest price*/
                    String mimOwnPrice = ownwardFlightSearch.getGrossAmount();
                    txtOwnPrice.setText(getResources().getString(R.string.rs_symbol)+ " " +mimOwnPrice);
                    /*Save  select Lowest Own ward flight in flight shared preference */
                    FlightSharedValues.getInstance().selectedOwnwardFlight=ownwardFlightSearch;
                    FlightSharedValues.getInstance().selectedOwnFlightList=new ArrayList<OwnwardFlightSearch>(Arrays.asList(ownwardFlightSearch));
                    FlightSharedValues.getInstance().ownflightPrice= Integer.parseInt(mimOwnPrice);



                   /* From Select ownwrod flight  */
                    SimpleDateFormat dateFormat1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);//24 hour time formet
                    // Please here set your event date//YYYY-MM-DD

                    /* add two hour in arrival date*/
                    Date sDate=dateFormat1.parse(ownwardFlightSearch.getArriDate());
                    //selectDate= parseFormat.parse(ownwardFlightSearch.getArrivalDatetime());
                    selectTime = new Date(sDate.getTime() + 2 * 60 * 60 * 1000 );


                    returnflightSearchList=new ArrayList<ReturnFlightSearch>();
                    //resetReturnflightSearchList=new ArrayList<ReturnFlightSearch>();

                    /* Get and set return flight list after two hour from arrival ownword flight date*/
                    for(int i=0; i < tempReturnflightSearchList.size(); i++){

                        filterDate=dateFormat1.parse(tempReturnflightSearchList.get(i).getDepartDate());
                        assert filterDate != null;
                        if(filterDate.after(selectTime)){
                            returnflightSearchList.add(tempReturnflightSearchList.get(i));
                        }
                        else {
                            returnflightSearchList.size();
                        }
                    }

                    //returnFlightSearch=new ReturnFlightSearch();
                    if(returnflightSearchList.size() > 0){
                       returnFlightSearch =  Collections.min(returnflightSearchList,new returnCheapestFlight());

                    /*Get lowest price*/
                    String mimRetPrice = returnFlightSearch.getGrossAmount();
                    txtReturnPrice.setText(getResources().getString(R.string.rs_symbol)+ " " +mimRetPrice);

                    /*Save  select Lowest Own ward flight in flight shared preference */
                    FlightSharedValues.getInstance().selectedReturnFlight=returnFlightSearch;
                    FlightSharedValues.getInstance().selectedReturnFlightList=new ArrayList<ReturnFlightSearch>(Arrays.asList(returnFlightSearch));
                    FlightSharedValues.getInstance().returnflightPrice= Integer.parseInt(mimRetPrice);

                    minOwnPrice= Integer.parseInt(mimOwnPrice);
                     minRetPrice= Integer.parseInt(mimRetPrice);
                    totPrice=minOwnPrice+minRetPrice;
                    txtTotPrice.setText(getResources().getString(R.string.rs_symbol)+ " " + String.valueOf(totPrice));
                    FlightSharedValues.getInstance().totflightPrice=totPrice;
                    }
                    else{
                        tempReturnflightSearchList.size();
                    }

                }
                else {

                    ownwardFlightSearch = Collections.min(ownflightSearchList, new OwnwardCheapestFlight());
                    /*Get lowest price*/
                    String mimOwnPrice = ownwardFlightSearch.getGrossAmount();
                    txtOwnPrice.setText(getResources().getString(R.string.rs_symbol)+ " " +mimOwnPrice);
                    /*Save  select Lowest Own ward flight in flight shared preference */
                    FlightSharedValues.getInstance().selectedOwnwardFlight=ownwardFlightSearch;
                    FlightSharedValues.getInstance().selectedOwnFlightList=new ArrayList<OwnwardFlightSearch>(Arrays.asList(ownwardFlightSearch));
                    FlightSharedValues.getInstance().ownflightPrice= Integer.parseInt(mimOwnPrice);

                    /* From Select ownwrod flight  */
                    SimpleDateFormat dateFormat1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);//24 hour time formet
                    // Please here set your event date//YYYY-MM-DD

                    /* add two hour in arrival date*/
                    Date sDate=dateFormat1.parse(ownwardFlightSearch.getArriDate());
                    //selectDate= parseFormat.parse(ownwardFlightSearch.getArrivalDatetime());
                    selectTime = new Date(sDate.getTime() + 2 * 60 * 60 * 1000 );

                    returnflightSearchList=new ArrayList<ReturnFlightSearch>();
                    //resetReturnflightSearchList=new ArrayList<ReturnFlightSearch>();

                    /* Get and set return flight list after two hour from arrival ownword flight date*/
                    for(int i=0; i < tempReturnflightSearchList.size(); i++){

                        filterDate=dateFormat1.parse(tempReturnflightSearchList.get(i).getDepartDate());
                        assert filterDate != null;
                        if(filterDate.after(selectTime)){
                            returnflightSearchList.add(tempReturnflightSearchList.get(i));
                        }
                        else {
                            returnflightSearchList.size();
                        }
                    }

                    // Return Flight object which has lowest prict flight
                    if(returnflightSearchList.size() > 0){
                        returnFlightSearch=new ReturnFlightSearch();
                        returnFlightSearch =  Collections.min(returnflightSearchList,new returnCheapestFlight());
                        /*Get lowest price*/
                        String mimRetPrice = returnFlightSearch.getGrossAmount();
                        txtReturnPrice.setText(getResources().getString(R.string.rs_symbol)+ " " +mimRetPrice);

                        /*Save  select Lowest Own ward flight in flight shared preference */
                        FlightSharedValues.getInstance().selectedReturnFlight=returnFlightSearch;
                        FlightSharedValues.getInstance().selectedReturnFlightList=new ArrayList<ReturnFlightSearch>(Arrays.asList(returnFlightSearch));
                        FlightSharedValues.getInstance().returnflightPrice= Integer.parseInt(mimRetPrice);

                        minOwnPrice= Integer.parseInt(mimOwnPrice);
                        minRetPrice= Integer.parseInt(mimRetPrice);
                        totPrice=minOwnPrice+minRetPrice;
                        txtTotPrice.setText(getResources().getString(R.string.rs_symbol)+ " " + String.valueOf(totPrice));
                        FlightSharedValues.getInstance().totflightPrice=totPrice;
                    }
                    else {
                        returnFlightSearch=new ReturnFlightSearch();
                    }

                }
            }

            /*Ownward flight list recycler and bind value in OwnwardFlightList Adapter*/
            recyOwnFlight=(RecyclerView)findViewById(R.id.own_flight_search_list_actvity_recycler);
            /*Return flight list recycler and bind value in ReturnFlightList Adapter*/
            recyReturnFlight=(RecyclerView)findViewById(R.id.return_flight_search_list_actvity_recycler);

            if(ownflightSearchList.size() > 0 && returnflightSearchList.size() > 0){
                recyOwnFlight.setVisibility(View.VISIBLE);
                recyReturnFlight.setVisibility(View.VISIBLE);
                layoutOwnNoRrcord.setVisibility(View.GONE);
                layoutReturnNoRrcord.setVisibility(View.GONE);
                btnBook.setVisibility(View.VISIBLE);
            }
            else {
                if(ownflightSearchList.size() > 0 && returnflightSearchList.size() == 0){
                    recyOwnFlight.setVisibility(View.VISIBLE);
                    recyReturnFlight.setVisibility(View.GONE);
                    layoutOwnNoRrcord.setVisibility(View.GONE);
                    layoutReturnNoRrcord.setVisibility(View.VISIBLE);
                    btnBook.setVisibility(View.GONE);
                }
                else if(ownflightSearchList.size() == 0 && returnflightSearchList.size() > 0){
                    recyOwnFlight.setVisibility(View.GONE);
                    recyReturnFlight.setVisibility(View.VISIBLE);
                    layoutOwnNoRrcord.setVisibility(View.VISIBLE);
                    layoutReturnNoRrcord.setVisibility(View.GONE);
                    btnBook.setVisibility(View.GONE);
                }
            }

            if(ownflightSearchList.size() > 0){
                ownwardFlightAdapter = new OwnwardFlightSeachListAdapter(FlightSearchRoundTripListActivity.this, ownflightSearchList, this);
            }
            else {
                ownwardFlightAdapter = new OwnwardFlightSeachListAdapter(FlightSearchRoundTripListActivity.this, ownflightSearchList, this);
            }
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyOwnFlight.setLayoutManager(mLayoutManager);
            recyOwnFlight.setItemAnimator(new DefaultItemAnimator());
            recyOwnFlight.addItemDecoration(new DividerItemDecoration(FlightSearchRoundTripListActivity.this, DividerItemDecoration.VERTICAL_LIST,0));
            recyOwnFlight.setAdapter(ownwardFlightAdapter);



            if(returnflightSearchList.size() > 0){

                returnFlightAdapter = new ReturnFlightSearchListAdapter(FlightSearchRoundTripListActivity.this, returnflightSearchList, this);
            }
            else {
                returnFlightAdapter = new ReturnFlightSearchListAdapter(FlightSearchRoundTripListActivity.this, returnflightSearchList, this);

            }
            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
            recyReturnFlight.setLayoutManager(mLayoutManager1);
            recyReturnFlight.setItemAnimator(new DefaultItemAnimator());
            recyReturnFlight.addItemDecoration(new DividerItemDecoration(FlightSearchRoundTripListActivity.this,DividerItemDecoration.VERTICAL_LIST,0));
            recyReturnFlight.setAdapter(returnFlightAdapter);

            /*Button book on click go to next activity*/
            btnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent =new Intent(FlightSearchRoundTripListActivity.this,SelectReturnFlightActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("OwnFlightList", ownwardFlightSearch);
                        bundle.putSerializable("RetFlightList", returnFlightSearch);
                        bundle.putString("FromCity", strFromCity);
                        bundle.putString("ToCity", strToCity);
                        bundle.putString("Adult", strAdult);
                        bundle.putString("Child", strChild);
                        bundle.putString("Infants", strInfant);
                        bundle.putString("DepartDate", strDepartDate);
                        bundle.putString("RetDate", strRetDepartDate);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        //overridePendingTransition(R.anim.slide_to_right,R.anim.slide_to_right);
                        //startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            /*Text Short Click Listener  open list by short */
            layoutShort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(FlightSearchRoundTripListActivity.this, FlightShortByActivity.class);
                    intent1.putExtra("Type","Return");
                    intent1.putExtra("OwnFlight",ownflight);
                    intent1.putExtra("ReturnFlight",retflight);
                    startActivityForResult(intent1, 1);
                    //openActivityfromBottom();
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

                        findnonStopOwnwardflight(str);
                        findnonStopReturnflight(str);

                    }
                    else {
                        switchCompatNonstop.setChecked(false);
                        if(ownflightSearchList.size() > 0 && returnflightSearchList.size() > 0){

                            //set data Ownward flight adapter
                            ownwardFlightAdapter.setData(FlightSearchRoundTripListActivity.this,ownflightSearchList,FlightSearchRoundTripListActivity.this,shortby);
                            recyOwnFlight.setAdapter(ownwardFlightAdapter);

                            //set data Return flight adapter
                            returnFlightAdapter.setData(FlightSearchRoundTripListActivity.this,returnflightSearchList,FlightSearchRoundTripListActivity.this,shortby);
                            recyReturnFlight.setAdapter(returnFlightAdapter);

                            int totalFlight=ownflightSearchList.size();
                            txtNonStop.setTextColor(getResources().getColor(R.color.black));
                            //txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));
                        }


                    }
                }
            });

            switchCompatNonstop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // The toggle is enabled
                        String str="Non Stop";

                        findnonStopOwnwardflight(str);
                        findnonStopReturnflight(str);
                    } else {
                        // The toggle is disabled
                        if(ownflightSearchList.size() > 0 && returnflightSearchList.size() > 0){

                            //set data Ownward flight adapter
                            ownwardFlightAdapter.setData(FlightSearchRoundTripListActivity.this,ownflightSearchList,FlightSearchRoundTripListActivity.this,shortby);
                            recyOwnFlight.setAdapter(ownwardFlightAdapter);

                            //set data Return flight adapter
                            returnFlightAdapter.setData(FlightSearchRoundTripListActivity.this,returnflightSearchList,FlightSearchRoundTripListActivity.this,shortby);
                            recyReturnFlight.setAdapter(returnFlightAdapter);

                            int totalFlight=ownflightSearchList.size();
                            txtNonStop.setTextColor(getResources().getColor(R.color.black));
                            //txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));
                        }

                    }
                }
            });

            /*Text Filter Click listener open filter list activity*/
            txtFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*calculate size of fligth search list and short alphabetically flight name wise
                    *  filter common airlines name form Ownward or Return flight
                    *  and arrange alphabetically list than send by intent to next
                     *  ReturnFlightFilterActivity
                     */
                    ArrayList<String> ownwardFlightNameList=new ArrayList<>();
                    ArrayList<String> returnFlightNameList=new ArrayList<>();
                    if(ownflightSearchList.size() > 0 && returnflightSearchList.size() > 0){
                        for (int i=0; i < ownflightSearchList.size();i++){
                            ownwardFlightNameList.add(ownflightSearchList.get(i).getAirlineName());
                        }

                        for (int i=0; i < returnflightSearchList.size();i++){
                            returnFlightNameList.add(returnflightSearchList.get(i).getAirlineName());
                        }

                        if(ownwardFlightNameList.size()> 0){
                            ownflightNameList=sortAscending(ownwardFlightNameList);

                        }
                        if(returnFlightNameList.size() > 0){
                            retflightNameList=sortAscending(returnFlightNameList);
                        }

                        if(ownflightNameList.size() > 0 && retflightNameList.size() > 0){

                            Set<String> set1 = new LinkedHashSet<>(ownflightNameList);
                            Set<String> set2 = new LinkedHashSet<>(retflightNameList);
                            ownflightNameList.clear();
                            ownflightNameList.addAll(set1);

                            retflightNameList.clear();
                            retflightNameList.addAll(set2);

                            Intent intent1 = new Intent(FlightSearchRoundTripListActivity.this, RetrunFlightFilterActivity.class);
                            Bundle bundle1=new Bundle();
                            bundle1.putStringArrayList("OwnFlightName",ownflightNameList);
                            bundle1.putStringArrayList("RetFlightName",retflightNameList);
                            bundle1.putString("FlightType","Return");
                            intent1.putExtras(bundle1);
                            startActivityForResult(intent1, 2);
                            //openActivityfromBottom();
                            System.out.println(set1 + " common name in the list1");
                            System.out.println(set2 + " common name in the list2");

                        }

                    }
                    else if(ownflightSearchList.size() > 0 && returnflightSearchList.size() == 0){

                        Toast.makeText(FlightSearchRoundTripListActivity.this,"Ownward flight only",Toast.LENGTH_SHORT).show();
                    }

                    else if(ownflightSearchList.size() == 0 && returnflightSearchList.size() > 0){
                        Toast.makeText(FlightSearchRoundTripListActivity.this,"Return flight only",Toast.LENGTH_SHORT).show();

                    }
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onOwnwardFlightSelected(OwnwardFlightSearch ownwardFlight) {
        try {
            if(ownwardFlight != null){

                /*Get lowest price*/
                String mimOwnPrice = ownwardFlight.getGrossAmount();
                ownwardFlightSearch=ownwardFlight;
                txtOwnPrice.setText(getResources().getString(R.string.rs_symbol)+ " " +mimOwnPrice);
                /*Save  select Lowest Own ward flight in flight shared preference */
                FlightSharedValues.getInstance().selectedOwnwardFlight=ownwardFlight;
                FlightSharedValues.getInstance().selectedOwnFlightList=new ArrayList<OwnwardFlightSearch>(Arrays.asList(ownwardFlight));
                FlightSharedValues.getInstance().ownflightPrice= Integer.parseInt(mimOwnPrice);

                minOwnPrice= Integer.parseInt(mimOwnPrice);
                totPrice=minOwnPrice+minRetPrice;
                txtTotPrice.setText(getResources().getString(R.string.rs_symbol)+ " " + String.valueOf(totPrice));
                FlightSharedValues.getInstance().totflightPrice=totPrice;

                /* From Select ownwrod flight  */
                SimpleDateFormat dateFormat1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);//24 hour time formet
                // Please here set your event date//YYYY-MM-DD

                /* add two hour in arrival date*/
                Date sDate=dateFormat1.parse(ownwardFlight.getArriDate());
                //selectDate= parseFormat.parse(ownwardFlightSearch.getArrivalDatetime());
                selectTime = new Date(sDate.getTime() + 2 * 60 * 60 * 1000 );


                //tempReturnflightSearchList.clear();
                tempReturnflightSearchList=resetReturnflightSearchList;
                returnflightSearchList=new ArrayList<ReturnFlightSearch>();

                /* Get and set return flight list after two hour from arrival ownword flight date*/
                for(int i=0; i < tempReturnflightSearchList.size(); i++){

                    filterDate=dateFormat1.parse(tempReturnflightSearchList.get(i).getDepartDate());
                    if(filterDate.after(selectTime)){
                        returnflightSearchList.add(tempReturnflightSearchList.get(i));
                    }
                    else {
                        returnflightSearchList.size();
                    }
                }



                if(returnflightSearchList.size() > 0){
                    recyReturnFlight.setVisibility(View.VISIBLE);
                    btnBook.setVisibility(View.VISIBLE);
                    layoutReturnNoRrcord.setVisibility(View.GONE);
                    returnFlightSearch =  Collections.min(returnflightSearchList,new returnCheapestFlight());

                /*Get lowest price*/
                String mimRetPrice = returnFlightSearch.getGrossAmount();
                txtReturnPrice.setText(getResources().getString(R.string.rs_symbol)+ " " +mimRetPrice);

                /*Save  select Lowest Own ward flight in flight shared preference */
                FlightSharedValues.getInstance().selectedReturnFlight=returnFlightSearch;
                FlightSharedValues.getInstance().selectedReturnFlightList=new ArrayList<ReturnFlightSearch>(Arrays.asList(returnFlightSearch));
                FlightSharedValues.getInstance().returnflightPrice= Integer.parseInt(mimRetPrice);

                minOwnPrice= Integer.parseInt(mimOwnPrice);
                minRetPrice= Integer.parseInt(mimRetPrice);
                totPrice=minOwnPrice+minRetPrice;
                txtTotPrice.setText(getResources().getString(R.string.rs_symbol)+ " " + String.valueOf(totPrice));
                FlightSharedValues.getInstance().totflightPrice=totPrice;

                //set data Return flight adapter
                returnFlightAdapter.setData(FlightSearchRoundTripListActivity.this,returnflightSearchList,FlightSearchRoundTripListActivity.this,"");
                recyReturnFlight.setAdapter(returnFlightAdapter);

                }
                else {
                    recyReturnFlight.setVisibility(View.GONE);
                    btnBook.setVisibility(View.GONE);
                    layoutReturnNoRrcord.setVisibility(View.VISIBLE);
                    returnFlightSearch = new ReturnFlightSearch();
                    /*Get lowest price*/
                    String mimRetPrice = "0";
                    txtReturnPrice.setText(getResources().getString(R.string.rs_symbol)+ " " +mimRetPrice);

                    /*Save  select Lowest Own ward flight in flight shared preference */
                    FlightSharedValues.getInstance().selectedReturnFlight=returnFlightSearch;
                    FlightSharedValues.getInstance().selectedReturnFlightList=new ArrayList<ReturnFlightSearch>(Arrays.asList(returnFlightSearch));
                    FlightSharedValues.getInstance().returnflightPrice= Integer.parseInt(mimRetPrice);

                    minOwnPrice= Integer.parseInt(mimOwnPrice);
                    minRetPrice= Integer.parseInt(mimRetPrice);
                    totPrice=minOwnPrice+minRetPrice;
                    txtTotPrice.setText(getResources().getString(R.string.rs_symbol)+ " " + String.valueOf(totPrice));
                    FlightSharedValues.getInstance().totflightPrice=totPrice;

                    //set data Return flight adapter
                    returnFlightAdapter.setData(FlightSearchRoundTripListActivity.this,returnflightSearchList,FlightSearchRoundTripListActivity.this,"");
                    recyReturnFlight.setAdapter(returnFlightAdapter);
                }



            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onReturnFlightSelected(ReturnFlightSearch returnFlight) {
        try {
            if(returnFlight != null){
                /*Get lowest price*/
                String mimRetPrice = returnFlight.getGrossAmount();
                returnFlightSearch=returnFlight;
                txtReturnPrice.setText(getResources().getString(R.string.rs_symbol)+ " " +mimRetPrice);

                /*Save  select Lowest Own ward flight in flight shared preference */
                FlightSharedValues.getInstance().selectedReturnFlight=returnFlight;
                FlightSharedValues.getInstance().selectedReturnFlightList=new ArrayList<ReturnFlightSearch>(Arrays.asList(returnFlight));
                FlightSharedValues.getInstance().returnflightPrice= Integer.parseInt(mimRetPrice);

                minRetPrice= Integer.parseInt(mimRetPrice);
                totPrice=minOwnPrice+minRetPrice;
                txtTotPrice.setText(getResources().getString(R.string.rs_symbol)+ " " + String.valueOf(totPrice));
                FlightSharedValues.getInstance().totflightPrice=totPrice;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        //finish();
        //overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        return true;
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {

            if(progressDialog.isShowing())
                progressDialog.dismiss();

            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
       /* if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }*/
        super.onBackPressed();
        finish();
        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }

    /*Receive Intent result form Flight filter activity */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if(resultCode == RESULT_OK) {

                String str = data.getStringExtra("ShortBy");
                String strRet = data.getStringExtra("RetShortBy");
                String type = data.getStringExtra("Type");
                if(!str.contentEquals("")){
                    /*flight short by cheapest value*/
                    if (str.contentEquals("Cheapest")){
                        shortby=str;
                        txtShortBy.setText(str);
                        ownwardFlightAdapter.setData(FlightSearchRoundTripListActivity.this,ownflightSearchList,this,shortby);
                        recyOwnFlight.setAdapter(ownwardFlightAdapter);
                        OwnwardFlightSearch ownward=new OwnwardFlightSearch();
                        ownward=ownflightSearchList.get(0);
                        defaultSelectOwnFlight(ownward);

                    }
                    /*flight short by Highest value*/
                    else if (str.contentEquals("Highest")){
                        shortby=str;
                        txtShortBy.setText(str);
                        ownwardFlightAdapter.setData(FlightSearchRoundTripListActivity.this,ownflightSearchList,this,shortby);
                        recyOwnFlight.setAdapter(ownwardFlightAdapter);
                        OwnwardFlightSearch ownward=new OwnwardFlightSearch();
                        ownward=ownflightSearchList.get(0);
                        defaultSelectOwnFlight(ownward);
                    }

                    /*flight short by Non stop flight*/
                    else if (str.contentEquals("Fastest")){
                        shortby=str;
                        txtShortBy.setText(str);
                        ownwardFlightAdapter.setData(FlightSearchRoundTripListActivity.this,ownflightSearchList,this,shortby);
                        recyOwnFlight.setAdapter(ownwardFlightAdapter);
                        OwnwardFlightSearch ownward=new OwnwardFlightSearch();
                        ownward=ownflightSearchList.get(0);
                        defaultSelectOwnFlight(ownward);

                    }
                    /*flight short by late take of  (11.00 am to 5.00 pm)*/
                    else if (str.contentEquals("Late Take off")){
                        shortby=str;
                        txtShortBy.setText(str);
                        ownwardFlightAdapter.setData(FlightSearchRoundTripListActivity.this,ownflightSearchList,this,shortby);
                        recyOwnFlight.setAdapter(ownwardFlightAdapter);
                        OwnwardFlightSearch ownward=new OwnwardFlightSearch();
                        ownward=ownflightSearchList.get(0);
                        defaultSelectOwnFlight(ownward);
                    }
                    /*flight short by late take of before (11.00 am)*/
                    else if (str.contentEquals("Early Take off")){
                        shortby=str;
                        txtShortBy.setText(str);
                        ownwardFlightAdapter.setData(FlightSearchRoundTripListActivity.this,ownflightSearchList,this,shortby);
                        recyOwnFlight.setAdapter(ownwardFlightAdapter);
                        OwnwardFlightSearch ownward=new OwnwardFlightSearch();
                        ownward=ownflightSearchList.get(0);
                        defaultSelectOwnFlight(ownward);
                    }
                    /*flight short by early landing*/
                    else if (str.contentEquals("Early Landing")){
                        shortby=str;
                        txtShortBy.setText(str);
                        ownwardFlightAdapter.setData(FlightSearchRoundTripListActivity.this,ownflightSearchList,this,shortby);
                        recyOwnFlight.setAdapter(ownwardFlightAdapter);
                        OwnwardFlightSearch ownward=new OwnwardFlightSearch();
                        ownward=ownflightSearchList.get(0);
                        defaultSelectOwnFlight(ownward);
                    }
                    /*flight short by late landing*/
                    else if (str.contentEquals("Late Landing")){
                        shortby=str;
                        txtShortBy.setText(str);
                        ownwardFlightAdapter.setData(FlightSearchRoundTripListActivity.this,ownflightSearchList,this,shortby);
                        recyOwnFlight.setAdapter(ownwardFlightAdapter);
                        OwnwardFlightSearch ownward=new OwnwardFlightSearch();
                        ownward=ownflightSearchList.get(0);
                        defaultSelectOwnFlight(ownward);
                    }

                    /*For Return flight*/
                    if(!strRet.contentEquals("")){
                        /*flight short by cheapest value*/
                        if (strRet.contentEquals("Cheapest")){
                            shortby=strRet;
                            txtShortBy.setText(strRet);
                            returnFlightAdapter.setData(FlightSearchRoundTripListActivity.this,returnflightSearchList,this,shortby);
                            recyReturnFlight.setAdapter(returnFlightAdapter);

                            ReturnFlightSearch retFlight=new ReturnFlightSearch();
                            retFlight=returnflightSearchList.get(0);
                            defaultSelectRetFlight(retFlight);

                        }
                        /*flight short by Highest value*/
                        else if (strRet.contentEquals("Highest")){
                            shortby=strRet;
                            txtShortBy.setText(strRet);
                            returnFlightAdapter.setData(FlightSearchRoundTripListActivity.this,returnflightSearchList,this,shortby);
                            recyReturnFlight.setAdapter(returnFlightAdapter);
                            ReturnFlightSearch retFlight=new ReturnFlightSearch();
                            retFlight=returnflightSearchList.get(0);
                            defaultSelectRetFlight(retFlight);
                        }

                        /*flight short by Non stop flight*/
                        else if (strRet.contentEquals("Fastest")){
                            shortby=strRet;
                            txtShortBy.setText(strRet);
                            returnFlightAdapter.setData(FlightSearchRoundTripListActivity.this,returnflightSearchList,this,shortby);
                            recyReturnFlight.setAdapter(returnFlightAdapter);
                            ReturnFlightSearch retFlight=new ReturnFlightSearch();
                            retFlight=returnflightSearchList.get(0);
                            defaultSelectRetFlight(retFlight);

                        }
                        /*flight short by late take of  (11.00 am to 5.00 pm)*/
                        else if (strRet.contentEquals("Late Take off")){
                            shortby=strRet;
                            txtShortBy.setText(strRet);
                            returnFlightAdapter.setData(FlightSearchRoundTripListActivity.this,returnflightSearchList,this,shortby);
                            recyReturnFlight.setAdapter(returnFlightAdapter);
                            ReturnFlightSearch retFlight=new ReturnFlightSearch();
                            retFlight=returnflightSearchList.get(0);
                            defaultSelectRetFlight(retFlight);
                        }
                        /*flight short by late take of before (11.00 am)*/
                        else if (strRet.contentEquals("Early Take off")){
                            shortby=strRet;
                            txtShortBy.setText(strRet);
                            returnFlightAdapter.setData(FlightSearchRoundTripListActivity.this,returnflightSearchList,this,shortby);
                            recyReturnFlight.setAdapter(returnFlightAdapter);
                            ReturnFlightSearch retFlight=new ReturnFlightSearch();
                            retFlight=returnflightSearchList.get(0);
                            defaultSelectRetFlight(retFlight);
                        }
                        /*flight short by early landing*/
                        else if (strRet.contentEquals("Early Landing")){
                            shortby=strRet;
                            txtShortBy.setText(strRet);
                            returnFlightAdapter.setData(FlightSearchRoundTripListActivity.this,returnflightSearchList,this,shortby);
                            recyReturnFlight.setAdapter(returnFlightAdapter);
                            ReturnFlightSearch retFlight=new ReturnFlightSearch();
                            retFlight=returnflightSearchList.get(0);
                            defaultSelectRetFlight(retFlight);
                        }
                        /*flight short by late landing*/
                        else if (strRet.contentEquals("Late Landing")){
                            shortby=strRet;
                            txtShortBy.setText(strRet);
                            returnFlightAdapter.setData(FlightSearchRoundTripListActivity.this,returnflightSearchList,this,shortby);
                            recyReturnFlight.setAdapter(returnFlightAdapter);
                            ReturnFlightSearch retFlight=new ReturnFlightSearch();
                            retFlight=returnflightSearchList.get(0);
                            defaultSelectRetFlight(retFlight);
                        }
                    }
                }

            }
           /* else if(resultCode== RESULT_CANCELED) {
                ownflightSearchList=resetownflightSearchList;
                ownwardFlightAdapter.setData(FlightSearchRoundTripListActivity.this,ownflightSearchList,this,"");
                recyOwnFlight.setAdapter(ownwardFlightAdapter);
                int totalFlight=ownflightSearchList.size();
                //txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));
            }*/



        }
        else if(requestCode == 2){
            if(resultCode == RESULT_OK){

                ArrayList<OwnwardFlightSearch> owntempfilterList=new ArrayList<OwnwardFlightSearch>();
                ArrayList<ReturnFlightSearch> rettempfilterList=new ArrayList<ReturnFlightSearch>();
                filterownflightSearchList=new ArrayList<OwnwardFlightSearch>();
                filterReturnflightSearchList=new ArrayList<ReturnFlightSearch>();
                Bundle filterbundle=data.getExtras();
                if(filterbundle != null){
                    ArrayList<String> nameListown = filterbundle.getStringArrayList("AirlinesListOwn");
                    ArrayList<String> nameListRet = filterbundle.getStringArrayList("AirlinesListRet");
                    String strAirlinesOwn=data.getStringExtra("Airlines");
                    String strAirlinesRet=data.getStringExtra("AirlinesRet");
                    String strRefundOwn=data.getStringExtra("RefundOwn");
                    String strRefundRet=data.getStringExtra("RefundRet");
                    String strStopOwn=data.getStringExtra("StopOwn");
                    String strStopRet=data.getStringExtra("StopRet");
                    String strDepTimeOwn=data.getStringExtra("DepTimeOwn");
                    String strDepTimeRet=data.getStringExtra("DepTimeRet");
                    String strFlightClassOwn=data.getStringExtra("FlightClassOwn");
                    String strFlightClassRet=data.getStringExtra("FlightClassRet");
                    filterOwnNameList=new ArrayList<String>();
                    filterReturnNameList=new ArrayList<String>();

                    /*For filter Own ward flight*/
                    if(nameListown.size() > 0 || nameListown.size()== 0){
                        filterOwnwardflight(strRefundOwn,strStopOwn,strDepTimeOwn,strFlightClassOwn,nameListown);
                    }
                    if(nameListRet.size() > 0 || nameListRet.size() == 0){
                        filterReturnflight(strRefundRet,strStopRet,strDepTimeRet,strFlightClassRet,nameListRet);

                    }

                }

            }
            else if(resultCode== RESULT_CANCELED) {
                ownflightSearchList=resetownflightSearchList;
                ownwardFlightAdapter.setData(FlightSearchRoundTripListActivity.this,ownflightSearchList,this,"");
                recyOwnFlight.setAdapter(ownwardFlightAdapter);
                int totalFlight=ownflightSearchList.size();

                OwnwardFlightSearch ownward=new OwnwardFlightSearch();
                ownward=ownflightSearchList.get(0);
                defaultSelectOwnFlight(ownward);

                returnflightSearchList=resetReturnflightSearchList;
                returnFlightAdapter.setData(FlightSearchRoundTripListActivity.this,returnflightSearchList,this,"");
                recyReturnFlight.setAdapter(returnFlightAdapter);
                int totalFlight1=returnflightSearchList.size();
                //txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));
                ReturnFlightSearch retFlight=new ReturnFlightSearch();
                retFlight=returnflightSearchList.get(0);
                defaultSelectRetFlight(retFlight);
            }
        }
    }

    /*Method for find lowest price flight*/
    public ArrayList<OwnwardFlightSearch> cheapestOwnwardFlight( ArrayList<OwnwardFlightSearch> list){
        //shorting
        Collections.sort(list, new Comparator<OwnwardFlightSearch>() {
            @Override
            public int compare(OwnwardFlightSearch o1, OwnwardFlightSearch o2) {
                Double totalAmount = Double.parseDouble(o1.getGrossAmount());

                Double totalAmount1 = Double.parseDouble(o2.getGrossAmount());

                return totalAmount.compareTo(totalAmount1);
            }
        });
        return list;
    }

    /*Method for find lowest price flight*/
    public ArrayList<ReturnFlightSearch> cheapestReturnFlight( ArrayList<ReturnFlightSearch> list){
        //shorting
        Collections.sort(list, new Comparator<ReturnFlightSearch>() {
            @Override
            public int compare(ReturnFlightSearch o1, ReturnFlightSearch o2) {
                Double totalAmount = Double.parseDouble(o1.getGrossAmount());

                Double totalAmount1 = Double.parseDouble(o2.getGrossAmount());

                return totalAmount.compareTo(totalAmount1);
            }
        });
        return list;
    }

    public class OwnwardCheapestFlight implements Comparator<OwnwardFlightSearch> {
        public int compare(OwnwardFlightSearch a, OwnwardFlightSearch b) {
            int priceA= Integer.parseInt(a.getGrossAmount());
            int priceB=Integer.parseInt(b.getGrossAmount());
            if (priceA < priceB)
                return -1; // lowest value first
            if (priceA == priceB)
                return 0;
            return 1;
        }
    }

    public class returnCheapestFlight implements Comparator<ReturnFlightSearch> {
        public int compare(ReturnFlightSearch a, ReturnFlightSearch b) {
            int priceA= Integer.parseInt(a.getGrossAmount());
            int priceB=Integer.parseInt(b.getGrossAmount());
            if (priceA < priceB)
                return -1; // lowest value first
            if (priceA == priceB)
                return 0;
            return 1;
        }
    }

    /*Method for find non stop Ownward flight*/
    private void findnonStopOwnwardflight(String itemToFind) {
        try {
            ArrayList<OwnwardFlightSearch> tempList=new ArrayList<OwnwardFlightSearch>();
            OwnwardFlightSearch findList=new OwnwardFlightSearch();
            if(ownflightSearchList.size() > 0){


                if (checkAvailabilityOwnFlight(ownflightSearchList,itemToFind)) {

                    // System.out.println(itemToFind + " was found in the list");
                    for (int i=0; i< ownflightSearchList.size(); i++){
                        findList=ownflightSearchList.get(i);
                        if(findList.getStopage().contentEquals(itemToFind)){

                            if(tempList.size() ==0){
                                tempList=new ArrayList<OwnwardFlightSearch>(Collections.singleton(findList));
                            }
                            else {
                                tempList.add(findList);

                            }
                        }
                    }

                    if(tempList.size() > 0){

                        ownwardFlightAdapter.setData(FlightSearchRoundTripListActivity.this,tempList,this,shortby);
                        recyOwnFlight.setAdapter(ownwardFlightAdapter);
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

    /*Method for find non stop return flight*/
    private void findnonStopReturnflight(String itemToFind) {
        try {
            ArrayList<ReturnFlightSearch> tempList=new ArrayList<ReturnFlightSearch>();
            ReturnFlightSearch findList=new ReturnFlightSearch();
            if(returnflightSearchList.size() > 0){


                if (checkAvailabilityReturnFlight(returnflightSearchList,itemToFind)) {

                    // System.out.println(itemToFind + " was found in the list");
                    for (int i=0; i< returnflightSearchList.size(); i++){
                        findList=returnflightSearchList.get(i);
                        if(findList.getStopage().contentEquals(itemToFind)){

                            if(tempList.size() ==0){
                                tempList=new ArrayList<ReturnFlightSearch>(Collections.singleton(findList));
                            }
                            else {
                                tempList.add(findList);

                            }
                        }
                    }

                    if(tempList.size() > 0){

                        returnFlightAdapter.setData(FlightSearchRoundTripListActivity.this,tempList,this,shortby);
                        recyReturnFlight.setAdapter(returnFlightAdapter);
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

    /*Method for Ownward flight for check item is available or not*/
    public static boolean checkAvailabilityOwnFlight(ArrayList<OwnwardFlightSearch> stopflight, String response) {
        for(OwnwardFlightSearch p: stopflight) {
            if (p.getStopage().equals(response)) {
                return true;
            }
        }
        return false;
    }

    /*Method for Return flight for check item is available or not*/
    public static boolean checkAvailabilityReturnFlight(ArrayList<ReturnFlightSearch> stopflight, String response) {
        for(ReturnFlightSearch p: stopflight) {
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

    /*method for auto set price form return flight witch is by select by default after sort*/
    public void defaultSelectRetFlight(ReturnFlightSearch returnFlight )    {

        try {
            if(returnFlight != null){
                /*Get lowest price*/
                String mimRetPrice = returnFlight.getGrossAmount();
                returnFlightSearch=returnFlight;
                txtReturnPrice.setText(getResources().getString(R.string.rs_symbol)+ " " +mimRetPrice);

                /*Save  select Lowest Own ward flight in flight shared preference */
                FlightSharedValues.getInstance().selectedReturnFlight=returnFlight;
                FlightSharedValues.getInstance().selectedReturnFlightList=new ArrayList<ReturnFlightSearch>(Arrays.asList(returnFlight));
                FlightSharedValues.getInstance().returnflightPrice= Integer.parseInt(mimRetPrice);

                minRetPrice= Integer.parseInt(mimRetPrice);
                totPrice=minOwnPrice+minRetPrice;
                txtTotPrice.setText(getResources().getString(R.string.rs_symbol)+ " " + String.valueOf(totPrice));
                FlightSharedValues.getInstance().totflightPrice=totPrice;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /*method for auto set price form Ownward flight witch is by select by default after sort*/
    public void defaultSelectOwnFlight(OwnwardFlightSearch ownwardFlight ){

        try {
            if(ownwardFlight != null){

                /*Get lowest price*/
                String mimOwnPrice = ownwardFlight.getGrossAmount();
                ownwardFlightSearch=ownwardFlight;
                txtOwnPrice.setText(getResources().getString(R.string.rs_symbol)+ " " +mimOwnPrice);
                /*Save  select Lowest Own ward flight in flight shared preference */
                FlightSharedValues.getInstance().selectedOwnwardFlight=ownwardFlight;
                FlightSharedValues.getInstance().selectedOwnFlightList=new ArrayList<OwnwardFlightSearch>(Arrays.asList(ownwardFlight));
                FlightSharedValues.getInstance().ownflightPrice= Integer.parseInt(mimOwnPrice);

                minOwnPrice= Integer.parseInt(mimOwnPrice);
                totPrice=minOwnPrice+minRetPrice;
                txtTotPrice.setText(getResources().getString(R.string.rs_symbol)+ " " + String.valueOf(totPrice));
                FlightSharedValues.getInstance().totflightPrice=totPrice;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Filter Own ward flight Method*/
    public void filterOwnwardflight(String refund,String stop,String departTime,String flightClass,ArrayList<String> nameList){
        // Flight Refundable wise filter
        ArrayList<OwnwardFlightSearch> owntempfilterList=new ArrayList<OwnwardFlightSearch>();
        if(refund.contentEquals("All")){
            filterownflightSearchList.addAll(ownflightSearchList);

        }
        else {
            for(int i=0; i <filterownflightSearchList.size(); i++) {

                if(refund.contentEquals(filterownflightSearchList.get(i).getFairType())){
                    owntempfilterList.add(filterownflightSearchList.get(i));

                }
                else {
                    filterownflightSearchList.size();
                }
            }
            if(owntempfilterList.size() > 0)
                filterownflightSearchList=owntempfilterList;
            else
                filterownflightSearchList.size();
        }

        //Flight stops wise filter
        if(stop.contentEquals("All")){
            filterownflightSearchList.size();
        }
        else {
            owntempfilterList.clear();
            for (int i=0;i < filterownflightSearchList.size(); i++){
                if(stop.contentEquals(filterownflightSearchList.get(i).getStopage())){

                    owntempfilterList.add(filterownflightSearchList.get(i));

                }
                else {
                    filterownflightSearchList.size();
                }
            }
            if(owntempfilterList.size() > 0){
                filterownflightSearchList=owntempfilterList;
                //tempfilterList.clear();
            }

            else{
                filterownflightSearchList.size();
            }

        }

        //Departure Date wise Filter
        if(departTime.contentEquals("All")){
            filterownflightSearchList.size();
        }
        else {

            // Before 11.00 am
            if(departTime.contentEquals("11.00")){
                String time="11:00";
                owntempfilterList=new ArrayList<OwnwardFlightSearch>();
                final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.UK);//24 hour time formet

                Date dateTime = null;
                Date receiveTime=null;
                try {
                    receiveTime= dateFormat.parse(time);
                }catch (Exception e){
                    e.printStackTrace();
                }

                for(int i=0; i < filterownflightSearchList.size(); i++){
                    try {
                        dateTime=dateFormat.parse(filterownflightSearchList.get(i).getDepartureTime());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    assert dateTime != null;
                    if(dateTime.before(receiveTime)){
                        owntempfilterList.add(filterownflightSearchList.get(i));

                    }
                    else {
                        filterownflightSearchList.size();
                    }
                }
                if(owntempfilterList.size() > 0){
                    filterownflightSearchList=owntempfilterList;
                    //tempfilterList.clear();
                }

                else{
                    filterownflightSearchList.size();
                }
            }
            // 11.00 Am to 5.00pm
            else if(departTime.contentEquals("11.00to17.00")){

                owntempfilterList=new ArrayList<OwnwardFlightSearch>();
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
                for (int i=0; i < filterownflightSearchList.size();i++){
                    try {

                        datetime=dateFormat.parse(filterownflightSearchList.get(i).getDepartureTime());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    assert datetime != null;
                    if(datetime.after(startDTime) && datetime.before(endDTime) ||datetime.equals(startDTime)){
                        owntempfilterList.add(filterownflightSearchList.get(i));
                    }
                    else {
                        filterownflightSearchList.size();
                    }
                }
                if(owntempfilterList.size() > 0){
                    filterownflightSearchList=owntempfilterList;
                    //tempfilterList.clear();
                }

                else{
                    filterownflightSearchList.size();
                }

            }
            // 5.00 pm to 9.00pm (17.00pm to 21.00pm )
            else if(departTime.contentEquals("17.00to21.00")){
                owntempfilterList=new ArrayList<OwnwardFlightSearch>();
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
                for (int i=0; i < filterownflightSearchList.size();i++){
                    try {

                        datetime=dateFormat.parse(filterownflightSearchList.get(i).getDepartureTime());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    assert datetime != null;
                    if(datetime.after(startDTime) && datetime.before(endDTime) ||datetime.equals(startDTime)){
                        owntempfilterList.add(filterownflightSearchList.get(i));
                    }
                    else {
                        filterownflightSearchList.size();
                    }
                }
                if(owntempfilterList.size() > 0){
                    filterownflightSearchList=owntempfilterList;
                    //tempfilterList.clear();
                }

                else{
                    filterownflightSearchList.size();
                }

            }
            //after 9.00pm
            else if(departTime.contentEquals("21.00")){
                String time="21:00";
                final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.UK);//24 hour time formet

                Date dateTime = null;
                Date receiveTime=null;
                try {
                    receiveTime= dateFormat.parse(time);
                }catch (Exception e){
                    e.printStackTrace();
                }

                for(int i=0; i < filterownflightSearchList.size(); i++){
                    try {
                        dateTime=dateFormat.parse(filterownflightSearchList.get(i).getDepartureTime());
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    assert dateTime != null;
                    if(dateTime.after(receiveTime)){
                        owntempfilterList.add(filterownflightSearchList.get(i));

                    }
                    else {
                        filterownflightSearchList.size();
                    }
                }
                if(owntempfilterList.size() > 0){
                    filterownflightSearchList=owntempfilterList;
                    //tempfilterList.clear();
                }

                else{
                    filterownflightSearchList.size();
                }
            }
        }

        // Flight Airlines name wise filter
        assert nameList != null;
        if(nameList.size() > 0){

            //filterNameList.addAll(filterNameList.size(),nameList);
            filterOwnNameList.addAll(nameList);

            //Add Airlines Name in String arraylst form Arraylist of object
            //tempfilterList.clear();
            owntempfilterList=new ArrayList<OwnwardFlightSearch>();
            for(int j=0;j < filterownflightSearchList.size();j++){
                // boolean ans= tempnameSet.containsAll(filterNameList);
                for(int k=0; k<filterOwnNameList.size();k++){

                    if(filterOwnNameList.get(k).equals(filterownflightSearchList.get(j).getAirlineName())){
                        owntempfilterList.add(filterownflightSearchList.get(j));
                    }
                    else {
                        filterownflightSearchList.size();
                    }
                }


            }
            if(owntempfilterList.size() > 0)
                filterownflightSearchList=owntempfilterList;
            else
                filterownflightSearchList.size();
        }
        else {
            filterOwnNameList.size();
            if(owntempfilterList.size() > 0)
                filterownflightSearchList=owntempfilterList;
            else
                filterownflightSearchList.size();

        }

        if(filterownflightSearchList.size() > 0){
            ownflightSearchList=filterownflightSearchList;
            ownwardFlightAdapter.setData(FlightSearchRoundTripListActivity.this,ownflightSearchList,this,"");
            recyOwnFlight.setAdapter(ownwardFlightAdapter);
            int totalFlight=ownflightSearchList.size();
            //txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));
        }

        OwnwardFlightSearch ownward=new OwnwardFlightSearch();
        ownward=ownflightSearchList.get(0);
        defaultSelectOwnFlight(ownward);

    }

    /*Filter Return flight Method*/
    public void filterReturnflight(String refund,String stop,String departTime,String flightClass,ArrayList<String> nameList){
        // Flight Refundable wise filter
        ArrayList<ReturnFlightSearch> retTempfilterList=new ArrayList<ReturnFlightSearch>();
        if(refund.contentEquals("All")){
            filterReturnflightSearchList.addAll(returnflightSearchList);

        }
        else {
            for(int i=0; i <filterReturnflightSearchList.size(); i++) {

                if(refund.contentEquals(filterownflightSearchList.get(i).getFairType())){
                    retTempfilterList.add(filterReturnflightSearchList.get(i));

                }
                else {
                    filterReturnflightSearchList.size();
                }
            }
            if(retTempfilterList.size() > 0)
                filterReturnflightSearchList=retTempfilterList;
            else
                filterReturnflightSearchList.size();
        }

        //Flight stops wise filter
        if(stop.contentEquals("All")){
            filterReturnflightSearchList.size();
        }
        else {
            retTempfilterList.clear();
            for (int i=0;i < filterReturnflightSearchList.size(); i++){
                if(stop.contentEquals(filterReturnflightSearchList.get(i).getStopage())){

                    retTempfilterList.add(filterReturnflightSearchList.get(i));

                }
                else {
                    filterReturnflightSearchList.size();
                }
            }
            if(retTempfilterList.size() > 0){
                filterReturnflightSearchList=retTempfilterList;
                //tempfilterList.clear();
            }

            else{
                filterReturnflightSearchList.size();
            }

        }

        //Departure Date wise Filter
        if(departTime.contentEquals("All")){
            filterReturnflightSearchList.size();
        }
        else {

            // Before 11.00 am
            if(departTime.contentEquals("11.00")){
                String time="11:00";
                retTempfilterList=new ArrayList<ReturnFlightSearch>();
                final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.UK);//24 hour time formet

                Date dateTime = null;
                Date receiveTime=null;
                try {
                    receiveTime= dateFormat.parse(time);
                }catch (Exception e){
                    e.printStackTrace();
                }

                for(int i=0; i < filterReturnflightSearchList.size(); i++){
                    try {
                        dateTime=dateFormat.parse(filterReturnflightSearchList.get(i).getDepartureTime());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    assert dateTime != null;
                    if(dateTime.before(receiveTime)){
                        retTempfilterList.add(filterReturnflightSearchList.get(i));

                    }
                    else {
                        filterReturnflightSearchList.size();
                    }
                }
                if(retTempfilterList.size() > 0){
                    filterReturnflightSearchList=retTempfilterList;
                    //tempfilterList.clear();
                }

                else{
                    filterReturnflightSearchList.size();
                }
            }
            // 11.00 Am to 5.00pm
            else if(departTime.contentEquals("11.00to17.00")){

                retTempfilterList=new ArrayList<ReturnFlightSearch>();
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
                for (int i=0; i < filterReturnflightSearchList.size();i++){
                    try {

                        datetime=dateFormat.parse(filterReturnflightSearchList.get(i).getDepartureTime());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    assert datetime != null;
                    if(datetime.after(startDTime) && datetime.before(endDTime) ||datetime.equals(startDTime)){
                        retTempfilterList.add(filterReturnflightSearchList.get(i));
                    }
                    else {
                        filterReturnflightSearchList.size();
                    }
                }
                if(retTempfilterList.size() > 0){
                    filterReturnflightSearchList=retTempfilterList;
                    //tempfilterList.clear();
                }

                else{
                    filterReturnflightSearchList.size();
                }

            }
            // 5.00 pm to 9.00pm (17.00pm to 21.00pm )
            else if(departTime.contentEquals("17.00to21.00")){
                retTempfilterList=new ArrayList<ReturnFlightSearch>();
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
                for (int i=0; i < filterReturnflightSearchList.size();i++){
                    try {

                        datetime=dateFormat.parse(filterReturnflightSearchList.get(i).getDepartureTime());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    assert datetime != null;
                    if(datetime.after(startDTime) && datetime.before(endDTime) ||datetime.equals(startDTime)){
                        retTempfilterList.add(filterReturnflightSearchList.get(i));
                    }
                    else {
                        filterReturnflightSearchList.size();
                    }
                }
                if(retTempfilterList.size() > 0){
                    filterReturnflightSearchList=retTempfilterList;
                    //tempfilterList.clear();
                }

                else{
                    filterReturnflightSearchList.size();
                }

            }
            //after 9.00pm
            else if(departTime.contentEquals("21.00")){
                String time="21:00";
                retTempfilterList=new ArrayList<ReturnFlightSearch>();
                final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.UK);//24 hour time formet

                Date dateTime = null;
                Date receiveTime=null;
                try {
                    receiveTime= dateFormat.parse(time);
                }catch (Exception e){
                    e.printStackTrace();
                }

                for(int i=0; i < filterReturnflightSearchList.size(); i++){
                    try {
                        dateTime=dateFormat.parse(filterReturnflightSearchList.get(i).getDepartureTime());
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    assert dateTime != null;
                    if(dateTime.after(receiveTime)){
                        retTempfilterList.add(filterReturnflightSearchList.get(i));

                    }
                    else {
                        filterownflightSearchList.size();
                    }
                }
                if(retTempfilterList.size() > 0){
                    filterReturnflightSearchList=retTempfilterList;
                    //tempfilterList.clear();
                }

                else{
                    filterReturnflightSearchList.size();
                }
            }
        }

        // Flight Airlines name wise filter
        assert nameList != null;
        if(nameList.size() > 0){

            //filterNameList.addAll(filterNameList.size(),nameList);
            filterReturnNameList.addAll(nameList);

            //Add Airlines Name in String arraylst form Arraylist of object
            //tempfilterList.clear();
            retTempfilterList=new ArrayList<ReturnFlightSearch>();
            for(int j=0;j < filterReturnflightSearchList.size();j++){
                // boolean ans= tempnameSet.containsAll(filterNameList);
                for(int k=0; k<filterReturnNameList.size();k++){

                    if(filterReturnNameList.get(k).equals(filterReturnflightSearchList.get(j).getAirlineName())){
                        retTempfilterList.add(filterReturnflightSearchList.get(j));
                    }
                    else {
                        filterReturnflightSearchList.size();
                    }
                }
            }
            if(retTempfilterList.size() > 0)
                filterReturnflightSearchList=retTempfilterList;
            else
                filterReturnflightSearchList.size();
        }
        else {
            filterReturnNameList.size();
            if(retTempfilterList.size() > 0)
                filterReturnflightSearchList=retTempfilterList;
            else
                filterReturnflightSearchList.size();

        }

        if(filterReturnflightSearchList.size() > 0){
            returnflightSearchList=filterReturnflightSearchList;
            returnFlightAdapter.setData(FlightSearchRoundTripListActivity.this,returnflightSearchList,this,"");
            recyReturnFlight.setAdapter(returnFlightAdapter);
            int totalFlight=returnflightSearchList.size();
            //txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));
        }
        ReturnFlightSearch retFlight=new ReturnFlightSearch();
        retFlight=returnflightSearchList.get(0);
        defaultSelectRetFlight(retFlight);

    }

}
