package in.discountmart.utility_services.travel.bus.bus_activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import in.discountmart.R;
import in.discountmart.utility_services.activity.MainActivity_utility;
import in.discountmart.utility_services.travel.bus.bus_adapter.BusSearchListAdapter;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusSearchListResponse;
import in.discountmart.utility_services.travel.bus.bus_sharedprference.BusSharedValues;
import in.discountmart.utility_services.utilities.DividerItemDecoration;

public class BusSearchListActivity extends AppCompatActivity implements BusSearchListAdapter.BusListAdapterListener{

    Toolbar busToolbar;
    TextView txtFromCity;
    TextView txtToCity;
    TextView txtDepartDateTime;
    TextView txtArrivalDateTime;
    TextView txtTotalBus;
    TextView txtFilter;
    TextView txtShortBy;
    TextView txtAc;
    ImageView imgHome;
    SwitchCompat switchCompatNonAc;
    RecyclerView recyclerView;
    LinearLayout layoutShort;
    LinearLayout layoutAcBus;

    LinearLayout layoutBottom;
    CoordinatorLayout coordinatorLayout;

    ArrayList<BusSearchListResponse> busSearchList;
    ArrayList<BusSearchListResponse> resetBusSearchList;
    ArrayList<BusSearchListResponse> filterBusSearchList;

    ArrayList<String> busNameList = new ArrayList<String>();
    ArrayList<String> bordingPointList = new ArrayList<String>();
    ArrayList<String> dropPointList = new ArrayList<String>();
    BusSearchListResponse busSearchResponse[];
    BusSearchListAdapter adapter;

    String strFromCity="";
    String strToCity="";
    String strDepartDate="";
    String shortby="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bus_search_list);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            busToolbar=(Toolbar)findViewById(R.id.bus_search_list_act_toolbar);
            setSupportActionBar(busToolbar);

            // toolbar fancy stuff
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            /*Window window = getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.TRANSPARENT); }*/

            txtFromCity=(TextView)findViewById(R.id.bus_search_list_act_toolbar_txtfromcity);
            txtToCity=(TextView)findViewById(R.id.bus_search_list_act_toolbar_txttocity);
            txtDepartDateTime=(TextView)findViewById(R.id.bus_search_list_act_toolbar_txt_dDate);
            txtArrivalDateTime=(TextView)findViewById(R.id.bus_search_list_act_toolbar_txt_arrDate);


            txtTotalBus=(TextView)findViewById(R.id.bus_search_list_actvity_txt_totalbus);
            txtShortBy=(TextView)findViewById(R.id.bus_search_list_actvity_txt_select_short);
            txtFilter=(TextView)findViewById(R.id.bus_search_list_actvity_txt_filter);
            txtAc=(TextView)findViewById(R.id.bus_search_list_actvity_txt_nonstop);
            layoutBottom=(LinearLayout)findViewById(R.id.bus_search_list_actvity_layout_bottom);
            layoutShort=(LinearLayout)findViewById(R.id.bus_search_list_actvity_layout_short);
            layoutAcBus=(LinearLayout)findViewById(R.id.bus_search_list_actvity_layout_switch_btn);
            switchCompatNonAc=(SwitchCompat)findViewById(R.id.bus_search_list_actvity_switch_btn);
            coordinatorLayout=(CoordinatorLayout) findViewById(R.id.bus_search_list_actvity_coordinator);
            imgHome=(ImageView)findViewById(R.id.bus_search_list_act_toolbar_img_home);



            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                busSearchList=new ArrayList<BusSearchListResponse>();
                resetBusSearchList=new ArrayList<BusSearchListResponse>();
                busSearchResponse=(BusSearchListResponse[])bundle.getSerializable("BusList");
                busSearchList= new ArrayList<BusSearchListResponse>(Arrays.asList(busSearchResponse));
                resetBusSearchList= new ArrayList<BusSearchListResponse>(Arrays.asList(busSearchResponse));

                strFromCity=bundle.getString("FromCity");
                strToCity=bundle.getString("ToCity");

                strDepartDate=bundle.getString("DepartDate");
                txtFromCity.setText(strFromCity);
                txtToCity.setText(strToCity);

                txtDepartDateTime.setText("Dep "+strDepartDate);

                int totalBus=busSearchList.size();
                txtTotalBus.setText("Total Bus:- "+String.valueOf(totalBus));


            }
            /*Tool bar top home icon on click go to HomeMainActivity_utility*/
            imgHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intenthome=new Intent(BusSearchListActivity.this, MainActivity_utility.class);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intenthome);
                    finish();
                }
            });

            //CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) layoutBottom.getLayoutParams();
            //layoutParams.setBehavior(new LinearLayoutBehavior());
            recyclerView=(RecyclerView)findViewById(R.id.bus_search_list_actvity_recycler);

            //flightSearchList = new ArrayList<>();
            adapter = new BusSearchListAdapter(BusSearchListActivity.this, busSearchList, this);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(BusSearchListActivity.this,DividerItemDecoration.VERTICAL_LIST,0));
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
                    Intent intent1 = new Intent(BusSearchListActivity.this, BusShortByActivity.class);
                    startActivityForResult(intent1, 1);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });

            /*Text Filter Click listener open filter list activity*/
            txtFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*calculate size of fligth search list and short alphabetically flight name wise*/
                    ArrayList<String> tempNameList=new ArrayList<>();
                    ArrayList<String> tempBordingPointList=new ArrayList<>();
                    ArrayList<String> tempDropPointList=new ArrayList<>();
                    if(busSearchList.size() > 0){
                        for (int i=0; i < busSearchList.size();i++){
                            tempNameList.add(busSearchList.get(i).getTravels());

                            for (int b=0; b< busSearchList.get(i).getBoardingPoint().size(); b++){
                                String board=busSearchList.get(i).getBoardingPoint().get(b).getBpName();
                                tempBordingPointList.add(board);
                            }
                            for (int d=0; d< busSearchList.get(i).getDroppingPoint().size(); d++){
                                String draop=busSearchList.get(i).getDroppingPoint().get(d).getBpName();
                                tempDropPointList.add(draop);
                            }

                        }

                        if(tempNameList.size()> 0){
                            busNameList=sortAscending(tempNameList);
                        }
                        if(tempBordingPointList.size() > 0){
                            bordingPointList=sortAscending(tempBordingPointList);
                        }

                        if(tempDropPointList.size() > 0){
                            dropPointList=sortAscending(tempDropPointList);
                        }

                        if(busNameList.size() > 0){

                            Set<String> set = new LinkedHashSet<>(busNameList);
                            Set<String> setDropPoint = new LinkedHashSet<>(dropPointList);
                            Set<String> setBoardingPoint = new LinkedHashSet<>(bordingPointList);
                            busNameList.clear();
                            dropPointList.clear();
                            bordingPointList.clear();
                            busNameList.addAll(set);
                            dropPointList.addAll(setDropPoint);
                            bordingPointList.addAll(setBoardingPoint);

                            Intent intent1 = new Intent(BusSearchListActivity.this, BusFilterActivity.class);
                            Bundle bundle1=new Bundle();
                            bundle1.putStringArrayList("BusName",busNameList);
                            bundle1.putStringArrayList("DropPointList",dropPointList);
                            bundle1.putStringArrayList("BoardPointList",bordingPointList);
                            intent1.putExtras(bundle1);
                            startActivityForResult(intent1, 2);
                            overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                            System.out.println(set + " common name in the list");
                            System.out.println(setDropPoint + " common Drop Point in the list");
                            System.out.println(setBoardingPoint + " common Board Point in the list");

                        }
                        else {

                        }
                    }
                }
            });

            /*click on layout of Switch Compact Bottun */
            layoutAcBus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean switchBtn=switchCompatNonAc.isChecked();
                    if(!switchBtn){

                        switchCompatNonAc.setChecked(true);
                        String str="AC";

                        findnAcBus(str);

                    }
                    else {
                        switchCompatNonAc.setChecked(false);
                        if(busSearchList.size() > 0){
                            txtAc.setTextColor(getResources().getColor(R.color.black));
                            adapter.setData(BusSearchListActivity.this,busSearchList,BusSearchListActivity.this,shortby);
                            recyclerView.setAdapter(adapter);
                            int totalFlight=busSearchList.size();
                            txtTotalBus.setText("Total Bus:- "+String.valueOf(totalFlight));
                        }
                    }
                }
            });

            /*click on Switch button for show only ac bus*/
            switchCompatNonAc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // The toggle is enabled
                        String str="AC";

                        findnAcBus(str);
                    } else {
                        // The toggle is disabled
                        if(busSearchList.size() > 0){
                            txtAc.setTextColor(getResources().getColor(R.color.black));
                            adapter.setData(BusSearchListActivity.this,busSearchList,BusSearchListActivity.this,shortby);
                            recyclerView.setAdapter(adapter);

                            int totalFlight=busSearchList.size();
                            txtTotalBus.setText("Total Bus:- "+String.valueOf(totalFlight));
                        }

                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBusSelected(BusSearchListResponse busSearchList) {

        try {

            if(busSearchList != null){
                int seat= Integer.parseInt(busSearchList.getAvailableSeats());
                if(seat == 0){
                    Toast.makeText(BusSearchListActivity.this,"No Seat Available in this bus, please select other",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    BusSharedValues.getInstance().busTripId=busSearchList.getTripID();
                    BusSharedValues.getInstance().busSelectArrayList=new ArrayList<BusSearchListResponse>(Collections.singleton(busSearchList));
                    BusSharedValues.getInstance().busSearchResponse=busSearchList;
                    Bundle bundle =new Bundle();
                    bundle.putString("TripId",busSearchList.getTripID());
                    bundle.putString("Travel",busSearchList.getTravels());
                    bundle.putString("SelectionType","Seat");
                    bundle.putString("DepartTime",busSearchList.getDepartureDateTime());
                    bundle.putString("BusType",busSearchList.getBusType());
                    bundle.putString("FromCity",strFromCity);
                    bundle.putString("ToCity",strToCity);
                    bundle.putString("DepartDate",strDepartDate);
                    bundle.putSerializable("BusSearch",busSearchList);
                    Intent intent=new Intent(BusSearchListActivity.this,BusSeatsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }

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
                if (str.contentEquals("Sleeper")){
                    shortby=str;
                    txtShortBy.setText(str);
                    findBusIsSleeper(shortby);

                }
                /*flight short by Highest value*/
                else if (str.contentEquals("Seater")){
                    shortby=str;
                    txtShortBy.setText(str);
                    findBusIsSeater(shortby);
                }

                /*flight short by Non stop flight*/
                else if (str.contentEquals("AC")){//NonAc
                    shortby=str;
                    txtShortBy.setText(str);
                    findnAcBus1(shortby);
                }
                /*flight short by Non stop flight*/
                else if (str.contentEquals("NonAc")){//NonAc
                    shortby=str;
                    txtShortBy.setText(str);
                    findnNonAcBus(shortby);
                }
                /*bus short by early start*/
                else if (str.contentEquals("Early Start")){
                    shortby=str;
                    txtShortBy.setText(str);
                    adapter.setData(BusSearchListActivity.this,busSearchList,this,shortby);
                    recyclerView.setAdapter(adapter);
                }

                else if (str.contentEquals("Late Start")){
                    shortby=str;
                    txtShortBy.setText(str);
                    adapter.setData(BusSearchListActivity.this,busSearchList,this,shortby);
                    recyclerView.setAdapter(adapter);
                }
                /*bus short by early arrival*/
                else if (str.contentEquals("Early Arrival")){
                    shortby=str;
                    txtShortBy.setText(str);
                    adapter.setData(BusSearchListActivity.this,busSearchList,this,shortby);
                    recyclerView.setAdapter(adapter);
                }
                /*bus short by late arrival*/
                else if (str.contentEquals("Late Arrival")){
                    shortby=str;
                    txtShortBy.setText(str);
                    adapter.setData(BusSearchListActivity.this,busSearchList,this,shortby);
                    recyclerView.setAdapter(adapter);
                }

            }
        }

        /*For filter list*/
        else if(requestCode == 2){
            if(resultCode == RESULT_OK){
                ArrayList<BusSearchListResponse> tempfilterList=new ArrayList<BusSearchListResponse>();
                filterBusSearchList=new ArrayList<BusSearchListResponse>();
                Bundle filterbundle=data.getExtras();
                String timeBefore6am="";
                String time6amTo12pm="";
                String time12pmTo18pm="";
                String after18pm="";
                String sleeper="";
                String seat="";
                String ac="";
                String nonAc="";

                if(filterbundle != null){
                    ArrayList<String> tempTimeList=new ArrayList<String>();
                    ArrayList<String> tempTypeList=new ArrayList<String>();
                    ArrayList<String> tempTravelList=new ArrayList<String>();
                    ArrayList<String> tempBoardPointList=new ArrayList<String>();
                    ArrayList<String> tempDropPointList=new ArrayList<String>();
                    tempTimeList=(ArrayList<String>) filterbundle.getStringArrayList("TimeList");
                    tempTypeList=(ArrayList<String>) filterbundle.getStringArrayList("TypeList");
                    tempTravelList=(ArrayList<String>) filterbundle.getStringArrayList("BusTravel");
                    tempBoardPointList=(ArrayList<String>) filterbundle.getStringArrayList("BoardPoint");
                    tempDropPointList=(ArrayList<String>) filterbundle.getStringArrayList("DropPoint");

                    timeBefore6am=filterbundle.getString("Before6am");
                    time6amTo12pm=filterbundle.getString("Time6amTo12pm");
                    time12pmTo18pm=filterbundle.getString("Time12pmTo18pm");
                    after18pm=filterbundle.getString("After6pm");
                    sleeper=filterbundle.getString("Sleeper");
                    seat=filterbundle.getString("Seat");
                    ac=filterbundle.getString("Ac");
                    nonAc=filterbundle.getString("NonAc");

                    filterBusSearchList.addAll(busSearchList);
                   // final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.UK);//24 hour time formet
                    Date dateTime = null;
                    Date receiveTime=null;
                    String time="";
                    boolean filtertime;
                    boolean filterType;
                    String type="";
                    assert tempTypeList != null;

                    if(tempTimeList.size() > 0){
                        filtertime= true;
                        filterType= true;

                        for(int i=0; i < tempTimeList.size(); i++){

                                time=tempTimeList.get(i);
                                //filtertime=tempTypeList.get(i).getTime();

                                for(int j=0;j < filterBusSearchList.size(); j++){

                                    //Filter for departure time
                                    if(time.contentEquals("06.00am")){

                                        String strTime="06:00:00";
                                        String strListTime="";
                                        Date listDate=null;
                                        Date receDate=null;

                                        final DateFormat dateFormat=new SimpleDateFormat("HH:mm:ss",Locale.US);//24 hour time formet
                                        try {

                                            receiveTime= dateFormat.parse(strTime);
                                            String[] strDepartDate=filterBusSearchList.get(j).getDepartureDateTime().split(",");
                                            //strListTime=strDepartDate[2].substring(0,strDepartDate[2].length()-2);
                                            strListTime=strDepartDate[2];
                                            dateTime=dateFormat.parse(strListTime);

                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        assert dateTime != null;
                                        if(dateTime.before(receiveTime)){
                                            tempfilterList.add(filterBusSearchList.get(j));
                                        }
                                        else {
                                            filterBusSearchList.size();
                                        }
                                    }
                                    else if(time.contentEquals("06.00to12.00")){
                                        final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss",Locale.US);//24 hour time formet

                                        String startTime="06:00:00";
                                        String endTime="12:00:00";
                                        Date startDTime=null;
                                        Date endDTime=null;
                                        Date datetime=null;
                                        try {
                                            startDTime=dateFormat.parse(startTime);
                                            endDTime=dateFormat.parse(endTime);
                                            String[] strDepartDate=filterBusSearchList.get(j).getDepartureDateTime().split(",");
                                            datetime=dateFormat.parse(strDepartDate[2]);

                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                        assert datetime != null;
                                        if(datetime.after(startDTime) && datetime.before(endDTime) ||datetime.equals(startDTime)){
                                            tempfilterList.add(filterBusSearchList.get(j));
                                        }
                                        else {
                                            filterBusSearchList.size();
                                        }
                                    }

                                    else if(time.contentEquals("12.00to18.00")){
                                        final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss", Locale.US);//24 hour time formet

                                        String startTime="12:00:00";
                                        String endTime="18:00:00";
                                        Date startDTime=null;
                                        Date endDTime=null;
                                        Date datetime=null;
                                        try {
                                            startDTime=dateFormat.parse(startTime);
                                            endDTime=dateFormat.parse(endTime);
                                            String[] strDepartDate=filterBusSearchList.get(j).getDepartureDateTime().split(",");
                                            datetime=dateFormat.parse(strDepartDate[2]);

                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                        assert datetime != null;
                                        if(datetime.after(startDTime) && datetime.before(endDTime) ||datetime.equals(startDTime)){
                                            tempfilterList.add(filterBusSearchList.get(j));
                                        }
                                        else {
                                            filterBusSearchList.size();
                                        }
                                    }
                                    else if(time.contentEquals("06.00pm")){
                                        String strTime="18:00:00";
                                        final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss", Locale.US);//24 hour time formet

                                        try {
                                            receiveTime= dateFormat.parse(strTime);
                                            String[] strDepartDate=filterBusSearchList.get(j).getDepartureDateTime().split(",");
                                            dateTime=dateFormat.parse(strDepartDate[2]);

                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        assert dateTime != null;
                                        if(dateTime.after(receiveTime)){
                                            tempfilterList.add(filterBusSearchList.get(j));
                                        }
                                        else {
                                            filterBusSearchList.size();
                                        }
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }
                                }

                        }
                        //filter list departure time and bus type
                            if(tempfilterList.size() > 0){
                                filterBusSearchList=tempfilterList;
                                //tempfilterList.clear();
                            }
                            else{
                                filterBusSearchList.size();
                            }

                    }
                    else {
                        filterBusSearchList.size();
                    }

                    /*Filter bus as Bus type like seat , Ac or nonAc etc*/

                    if(tempTypeList.size() > 0){
                        tempfilterList=new ArrayList<BusSearchListResponse>();


                            for (int j=0; j < filterBusSearchList.size(); j++){


                                assert sleeper != null;
                                /*filter by Sleeper*/
                                if(sleeper.equals("Sleeper") && seat.equals("") && ac.equals("") && nonAc.equals("")){
                                    //tempfilterList=new ArrayList<BusSearchListResponse>();

                                    if(filterBusSearchList.get(j).getSleeper().contentEquals("true")){
                                        tempfilterList.add(filterBusSearchList.get(j));
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }

                                    if(tempfilterList.size() > 0){
                                        filterBusSearchList=tempfilterList;
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }
                                }

                                /*filter by Seat*/
                                else if(sleeper.equals("") && seat.equals("Seat") && ac.equals("") && nonAc.equals("")){
                                    // tempfilterList=new ArrayList<BusSearchListResponse>();
                                    if(filterBusSearchList.get(j).getSeater().contentEquals("true")){
                                        tempfilterList.add(filterBusSearchList.get(j));
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }

                                    if(tempfilterList.size() > 0){
                                        filterBusSearchList=tempfilterList;
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }
                                }

                                /*filter by Ac*/
                                else if(sleeper.equals("") && seat.equals("") && ac.equals("Ac") && nonAc.equals("")){
                                    // tempfilterList=new ArrayList<BusSearchListResponse>();
                                    if(filterBusSearchList.get(j).getAC().contentEquals("true")){
                                        tempfilterList.add(filterBusSearchList.get(j));
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }

                                    if(tempfilterList.size() > 0){
                                        filterBusSearchList=tempfilterList;
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }
                                }
                                /*filter by nonAc*/
                                else if(sleeper.equals("") && seat.equals("") && ac.equals("") && nonAc.equals("NonAc")){
                                    //tempfilterList=new ArrayList<BusSearchListResponse>();
                                    if(filterBusSearchList.get(j).getNonAc().contentEquals("true")){
                                        tempfilterList.add(filterBusSearchList.get(j));
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }

                                    if(tempfilterList.size() > 0){
                                        filterBusSearchList=tempfilterList;
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }
                                }
                                /*filter Sleeper and Ac*/
                                else if(sleeper.equals("Sleeper") && seat.equals("") && ac.equals("Ac") && nonAc.equals("")){
                                    //tempfilterList=new ArrayList<BusSearchListResponse>();
                                    if(filterBusSearchList.get(j).getSleeper().contentEquals("true") &&
                                            filterBusSearchList.get(j).getAC().contentEquals("true") ){
                                        tempfilterList.add(filterBusSearchList.get(j));
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }
                                    if(tempfilterList.size() > 0){
                                        filterBusSearchList=tempfilterList;
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }
                                }
                                /*filter Sleeper and nonAc*/
                                else if(sleeper.equals("Sleeper") && seat.equals("") && ac.equals("") && nonAc.equals("NonAc")){
                                    //tempfilterList=new ArrayList<BusSearchListResponse>();
                                    if(filterBusSearchList.get(j).getSleeper().contentEquals("true") &&
                                            filterBusSearchList.get(j).getNonAc().contentEquals("true") ){
                                        tempfilterList.add(filterBusSearchList.get(j));
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }

                                    if(tempfilterList.size() > 0){
                                        filterBusSearchList=tempfilterList;
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }
                                }

                                /*filter utility_sleeper and seat*/
                                else if(sleeper.equals("Sleeper") && seat.equals("Seat") && ac.equals("") && nonAc.equals("")){
                                    // tempfilterList=new ArrayList<BusSearchListResponse>();
                                    if(filterBusSearchList.get(j).getSleeper().contentEquals("true") &&
                                            filterBusSearchList.get(j).getSeater().contentEquals("true") ){
                                        tempfilterList.add(filterBusSearchList.get(j));
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }

                                    if(tempfilterList.size() > 0){
                                        filterBusSearchList=tempfilterList;
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }
                                }

                                /*filter Ac and nonAc*/
                                else if(sleeper.equals("") && seat.equals("") && ac.equals("Ac") && nonAc.equals("NonAc")){
                                    //tempfilterList=new ArrayList<BusSearchListResponse>();
                                    if(filterBusSearchList.get(j).getAC().contentEquals("true") &&
                                            filterBusSearchList.get(j).getNonAc().contentEquals("true") ){
                                        tempfilterList.add(filterBusSearchList.get(j));
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }

                                    if(tempfilterList.size() > 0){
                                        filterBusSearchList=tempfilterList;
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }
                                }

                                /*filter Seat and nonAc*/
                                else if(sleeper.equals("") && seat.equals("Seat") && ac.equals("") && nonAc.equals("NonAc")){
                                    //tempfilterList=new ArrayList<BusSearchListResponse>();
                                    if(filterBusSearchList.get(j).getSeater().contentEquals("true") &&
                                            filterBusSearchList.get(j).getNonAc().contentEquals("true") ){
                                        tempfilterList.add(filterBusSearchList.get(j));
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }

                                    if(tempfilterList.size() > 0){
                                        filterBusSearchList=tempfilterList;
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }
                                }

                                /*filter Seat and Ac*/
                                else if(sleeper.equals("") && seat.equals("Seat") && ac.equals("Ac") && nonAc.equals("")){
                                    //tempfilterList=new ArrayList<BusSearchListResponse>();
                                    if(filterBusSearchList.get(j).getSeater().contentEquals("true") &&
                                            filterBusSearchList.get(j).getAC().contentEquals("true") ){
                                        tempfilterList.add(filterBusSearchList.get(j));
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }

                                    if(tempfilterList.size() > 0){
                                        filterBusSearchList=tempfilterList;
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }
                                }

                            }

                    }
                    else {
                        filterBusSearchList.size();
                    }

                    /*Filter by travel name list*/
                    if(tempTravelList != null){
                        if(tempTravelList.size() > 0){
                            tempfilterList=new ArrayList<BusSearchListResponse>();
                            for (int i=0;i< filterBusSearchList.size(); i++){
                                for (int j=0; j< tempTravelList.size(); j++){
                                    if (tempTravelList.get(j).equals(filterBusSearchList.get(i).getTravels())){
                                        tempfilterList.add(filterBusSearchList.get(i));
                                    }
                                    else {
                                        filterBusSearchList.size();
                                    }
                                }
                            }
                            /*filter list travel name according*/
                            if(tempfilterList.size() > 0){
                                filterBusSearchList=tempfilterList;
                                //tempfilterList.clear();
                            }
                            else{
                                filterBusSearchList.size();
                            }
                        }
                        else {
                            filterBusSearchList.size();
                        }
                    }
                    else {
                        filterBusSearchList.size();
                    }

                    /*Filter for boarding */

                    if( tempBoardPointList != null){
                        if(tempBoardPointList.size() > 0){

                            tempfilterList=new ArrayList<BusSearchListResponse>();
                            for (int i=0;i< filterBusSearchList.size(); i++){
                                ArrayList<BusSearchListResponse.BoardingPoints> tempBoardingPointList=new ArrayList<BusSearchListResponse.BoardingPoints>();

                                tempBoardingPointList=filterBusSearchList.get(i).getBoardingPoint();
                                for (int j=0; j< tempBoardPointList.size(); j++){

                                    for(int k=0; k <tempBoardingPointList.size(); k++){
                                        if (tempBoardPointList.get(j).equals(tempBoardingPointList.get(k).getBpName())){
                                            tempfilterList.add(filterBusSearchList.get(i));
                                        }
                                        else {
                                            filterBusSearchList.size();
                                        }
                                    }

                                }
                            }
                            /*filter list travel name according*/
                            if(tempfilterList.size() > 0){
                                filterBusSearchList=tempfilterList;
                                //tempfilterList.clear();
                            }

                            else{
                                filterBusSearchList.size();
                            }
                        }
                        else {
                            filterBusSearchList.size();
                        }
                    }


                    /*Filter for Drop Point */
                    if( tempDropPointList != null){
                        if(tempDropPointList.size() > 0){

                            tempfilterList=new ArrayList<BusSearchListResponse>();
                            for (int i=0;i< filterBusSearchList.size(); i++){
                                ArrayList<BusSearchListResponse.DropingPoints> tempDropingPointList=new ArrayList<BusSearchListResponse.DropingPoints>();

                                tempDropingPointList=filterBusSearchList.get(i).getDroppingPoint();
                                for (int j=0; j< tempDropPointList.size(); j++){

                                    for(int k=0; k <tempDropingPointList.size(); k++){
                                        if (tempDropPointList.get(j).equals(tempDropingPointList.get(k).getBpName())){
                                            tempfilterList.add(filterBusSearchList.get(i));
                                        }
                                        else {
                                            filterBusSearchList.size();
                                        }
                                    }

                                }
                            }
                            /*filter list travel name according*/
                            if(tempfilterList.size() > 0){
                                filterBusSearchList=tempfilterList;
                                //tempfilterList.clear();
                            }
                            else
                                {
                                filterBusSearchList.size();
                            }
                        }
                        else {
                            filterBusSearchList.size();
                        }
                    }

                    /*Filter list set in adapter*/
                    if(filterBusSearchList.size() > 0){
                        busSearchList=filterBusSearchList;
                        adapter.setData(BusSearchListActivity.this,busSearchList,this,"");
                        recyclerView.setAdapter(adapter);
                        int totalFlight=busSearchList.size();
                        txtTotalBus.setText("Total Bus:- "+String.valueOf(totalFlight));
                    }
                }
            }
            else if(resultCode== RESULT_CANCELED) {
                busSearchList=resetBusSearchList;
                adapter.setData(BusSearchListActivity.this,busSearchList,this,"");
                recyclerView.setAdapter(adapter);
                int totalFlight=busSearchList.size();
                txtTotalBus.setText("Total Bus:- "+String.valueOf(totalFlight));
            }
        }


    }

    /*find fastest flight in utility_main flight search list*/
    private void findItemInTheList(String itemToFind) {
        try {
            ArrayList<BusSearchListResponse> tempList=new ArrayList<BusSearchListResponse>();
            BusSearchListResponse findList=new BusSearchListResponse();
            if(busSearchList.size() > 0){


                if (checkAvailability(busSearchList,itemToFind)) {

                    // System.out.println(itemToFind + " was found in the list");
                    for (int i=0; i< busSearchList.size(); i++){
                        findList=busSearchList.get(i);
                        if(findList.getAC().contentEquals("true")){

                            if(tempList.size() ==0){
                                tempList=new ArrayList<BusSearchListResponse>(Collections.singleton(findList));
                            }
                            else {
                                tempList.add(findList);

                            }
                        }
                    }

                    if(tempList.size() > 0){

                        adapter.setData(BusSearchListActivity.this,tempList,this,shortby);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    System.out.println(itemToFind + " bus not found in the list");
                    Toast.makeText(this,itemToFind + " bus not found in the list",Toast.LENGTH_SHORT).show();
                }
            }
            else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }


   /* public static boolean checkAvailability(ArrayList<BusSearchListResponse> stopflight, String response) {
        for(BusSearchListResponse p: stopflight) {
            if (p.getStopage().equals(response)) {
                return true;
            }
        }
        return false;
    }*/

    /*Short array list alphabetic assending*/
    public ArrayList<String> sortAscending(ArrayList<String> list) {
        Collections.sort(list);
        return list;
    }

    /*Short array list with common name*/
    public ArrayList<String> findcommomElement(ArrayList<String> list,ArrayList<String> list2) {

        return list;
    }

    /*Method for find Ac Bus*/
    private void findnAcBus1(String itemToFind) {
        try {
            ArrayList<BusSearchListResponse> tempList=new ArrayList<BusSearchListResponse>();
            BusSearchListResponse findList=new BusSearchListResponse();
            if(busSearchList.size() > 0){


                if (checkAvailability(busSearchList,itemToFind)) {

                    // System.out.println(itemToFind + " was found in the list");
                    for (int i=0; i< busSearchList.size(); i++){
                        findList=busSearchList.get(i);
                        if(findList.getAC().contentEquals("true")){

                            if(tempList.size() ==0){
                                tempList=new ArrayList<BusSearchListResponse>(Collections.singleton(findList));
                            }
                            else {
                                tempList.add(findList);

                            }
                        }
                    }

                    if(tempList.size() > 0){

                        adapter.setData(BusSearchListActivity.this,tempList,this,shortby);
                        recyclerView.setAdapter(adapter);
                        //txtAc.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                       // switchCompatNonAc.setChecked(true);
                        int totalFlight=tempList.size();
                        txtTotalBus.setText("Total Bus:- "+String.valueOf(totalFlight));
                    }
                } else {
                    //txtAc.setTextColor(getResources().getColor(R.color.black));
                   // switchCompatNonAc.setChecked(false);
                    System.out.println(itemToFind + " bus not found in the list");
                    Toast.makeText(this,itemToFind + " bus not found in the list",Toast.LENGTH_SHORT).show();
                }
            }
            else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /*Method for find Ac Bus*/
    private void findnAcBus(String itemToFind) {
        try {
            ArrayList<BusSearchListResponse> tempList=new ArrayList<BusSearchListResponse>();
            BusSearchListResponse findList=new BusSearchListResponse();
            if(busSearchList.size() > 0){


                if (checkAvailability(busSearchList,itemToFind)) {

                    // System.out.println(itemToFind + " was found in the list");
                    for (int i=0; i< busSearchList.size(); i++){
                        findList=busSearchList.get(i);
                        if(findList.getAC().contentEquals("true")){

                            if(tempList.size() ==0){
                                tempList=new ArrayList<BusSearchListResponse>(Collections.singleton(findList));
                            }
                            else {
                                tempList.add(findList);

                            }
                        }
                    }

                    if(tempList.size() > 0){

                        adapter.setData(BusSearchListActivity.this,tempList,this,shortby);
                        recyclerView.setAdapter(adapter);
                        txtAc.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        switchCompatNonAc.setChecked(true);
                        int totalFlight=tempList.size();
                        txtTotalBus.setText("Total Bus:- "+String.valueOf(totalFlight));
                    }
                } else {
                    txtAc.setTextColor(getResources().getColor(R.color.black));
                    switchCompatNonAc.setChecked(false);
                    System.out.println(itemToFind + " bus not found in the list");
                    Toast.makeText(this,itemToFind + " bus not found in the list",Toast.LENGTH_SHORT).show();
                }
            }
            else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /*Method for find non stop flight*/
    private void findnNonAcBus(String itemToFind) {
        try {
            ArrayList<BusSearchListResponse> tempList=new ArrayList<BusSearchListResponse>();
            BusSearchListResponse findList=new BusSearchListResponse();
            if(busSearchList.size() > 0){


                if (checkAvailability(busSearchList,itemToFind)) {

                    // System.out.println(itemToFind + " was found in the list");
                    for (int i=0; i< busSearchList.size(); i++){
                        findList=busSearchList.get(i);
                        if(findList.getNonAc().contentEquals("true")){

                            if(tempList.size() ==0){
                                tempList=new ArrayList<BusSearchListResponse>(Collections.singleton(findList));
                            }
                            else {
                                tempList.add(findList);

                            }
                        }
                    }

                    if(tempList.size() > 0){

                        adapter.setData(BusSearchListActivity.this,tempList,this,shortby);
                        recyclerView.setAdapter(adapter);
                        int totalFlight=tempList.size();
                        txtTotalBus.setText("Total Bus:- "+String.valueOf(totalFlight));
                    }
                } else {

                    System.out.println(itemToFind + " bus not found in the list");
                    Toast.makeText(this,itemToFind + " bus not found in the list",Toast.LENGTH_SHORT).show();
                }
            }
            else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /*Method for find non stop flight*/
    private void findBusIsSeater(String itemToFind) {
        try {
            ArrayList<BusSearchListResponse> tempList=new ArrayList<BusSearchListResponse>();
            BusSearchListResponse findList=new BusSearchListResponse();
            if(busSearchList.size() > 0){


                if (checkSeatAvailability(busSearchList,itemToFind)) {

                    // System.out.println(itemToFind + " was found in the list");
                    for (int i=0; i< busSearchList.size(); i++){
                        //findList=busSearchList.get(i);
                        if(busSearchList.get(i).getSeater().contentEquals("true")){

                            if(tempList.size() ==0){
                                tempList=new ArrayList<BusSearchListResponse>(Collections.singleton(busSearchList.get(i)));
                            }
                            else {
                                tempList.add(busSearchList.get(i));

                            }
                        }
                    }

                    if(tempList.size() > 0){

                        adapter.setData(BusSearchListActivity.this,tempList,this,shortby);
                        recyclerView.setAdapter(adapter);
                        int totalFlight=tempList.size();
                        txtTotalBus.setText("Total Bus:- "+String.valueOf(totalFlight));
                    }
                } else {
                    //txtAc.setTextColor(getResources().getColor(R.color.white));
                    //switchCompatNonAc.setChecked(false);
                    System.out.println(itemToFind + " bus not found in the list");
                    Toast.makeText(this,itemToFind + " bus not found in the list",Toast.LENGTH_SHORT).show();
                }
            }
            else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /*Method for find non stop flight*/
    private void findBusIsSleeper(String itemToFind) {
        try {
            ArrayList<BusSearchListResponse> tempList=new ArrayList<BusSearchListResponse>();
            BusSearchListResponse findList=new BusSearchListResponse();
            if(busSearchList.size() > 0){


                if (checkSleeperAvailability(busSearchList,itemToFind)) {

                    // System.out.println(itemToFind + " was found in the list");
                    for (int i=0; i< busSearchList.size(); i++){
                        findList=busSearchList.get(i);
                        if(findList.getSleeper().contentEquals("true")){

                            if(tempList.size() ==0){
                                tempList=new ArrayList<BusSearchListResponse>(Collections.singleton(findList));
                            }
                            else {
                                tempList.add(findList);

                            }
                        }
                    }

                    if(tempList.size() > 0){

                        adapter.setData(BusSearchListActivity.this,tempList,this,shortby);
                        recyclerView.setAdapter(adapter);
                        int totalFlight=tempList.size();
                        txtTotalBus.setText("Total Bus:- "+String.valueOf(totalFlight));
                    }
                } else {
                    //txtAc.setTextColor(getResources().getColor(R.color.white));
                    //switchCompatNonAc.setChecked(false);
                    System.out.println(itemToFind + " bus not found in the list");
                    Toast.makeText(this,itemToFind + " bus not found in the list",Toast.LENGTH_SHORT).show();
                }
            }
            else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static boolean checkAvailability(ArrayList<BusSearchListResponse> stopflight, String response) {
        for(BusSearchListResponse p: stopflight) {
            if (p.getAC().equals("true")) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkSleeperAvailability(ArrayList<BusSearchListResponse> stopflight, String response) {
        for(BusSearchListResponse p: stopflight) {
            if (p.getSleeper().equals("true")) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkSeatAvailability(ArrayList<BusSearchListResponse> stopflight, String response) {
        for(BusSearchListResponse p: stopflight) {
            if (p.getSeater().equals("true")) {
                return true;
            }
        }
        return false;
    }

    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        return true;
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            onBackPressed();
            *//*if(progressDialog.isShowing())
                progressDialog.dismiss();
            fetchFlightSearch.cancel();*//*
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
}
