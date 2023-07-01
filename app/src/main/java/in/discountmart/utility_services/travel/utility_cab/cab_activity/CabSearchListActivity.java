package in.discountmart.utility_services.travel.utility_cab.cab_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import in.discountmart.R;
import in.discountmart.utility_services.activity.MainActivity_utility;
import in.discountmart.utility_services.travel.bus.bus_activity.BusShortByActivity;
import in.discountmart.utility_services.travel.utility_cab.cab_adapter.CabSearchListAdapter;
import in.discountmart.utility_services.travel.utility_cab.cab_model.cab_response_model.CabSearchResponse;
import in.discountmart.utility_services.travel.utility_cab.cab_sharedpreferance.CabSharedValues;
import in.discountmart.utility_services.utilities.DividerItemDecoration;

public class CabSearchListActivity extends AppCompatActivity implements CabSearchListAdapter.CabListAdapterListener{
    Toolbar cabToolbar;
    TextView txtFromCity;
    TextView txtToCity;
    TextView txtDepartDate;
    TextView txtDepartTime;
    TextView txtArrivalDateTime;
    TextView txtTotalBus;
    TextView txtFilter;
    TextView txtShortBy;
    TextView txtAc;
    ImageView imgHome;
    SwitchCompat switchCompatNonAc;
    RecyclerView recyclerView;
    LinearLayout layoutShort;
    LinearLayout layoutAcCab;

    LinearLayout layoutBottom;
    CoordinatorLayout coordinatorLayout;

    ArrayList<CabSearchResponse> cabSearchList;
    ArrayList<CabSearchResponse> resetCabSearchList;
    ArrayList<CabSearchResponse> filterCabSearchList;

    ArrayList<String> busNameList = new ArrayList<String>();
    ArrayList<String> bordingPointList = new ArrayList<String>();
    ArrayList<String> dropPointList = new ArrayList<String>();
    CabSearchResponse cabSearchResponse[];
    CabSearchListAdapter adapter;

    String strFromCity="";
    String strToCity="";
    String strDepartDate="";
    String strDepartTime="";
    String shortby="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_cab_search_list);

        try {
            cabToolbar=(Toolbar)findViewById(R.id.cab_search_list_act_toolbar);
            setSupportActionBar(cabToolbar);

            // toolbar fancy stuff
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            /*Window window = getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.TRANSPARENT); }*/

            txtFromCity=(TextView)findViewById(R.id.cab_search_list_act_toolbar_txtfromcity);
            txtToCity=(TextView)findViewById(R.id.cab_search_list_act_toolbar_txttocity);
            txtDepartDate=(TextView)findViewById(R.id.cab_search_list_act_toolbar_txt_dDate);
            txtDepartTime=(TextView)findViewById(R.id.cab_search_list_act_toolbar_txt_deptime);
            //txtArrivalDateTime=(TextView)findViewById(R.id.cab_search_list_act_toolbar_txt_arrDate);


            txtTotalBus=(TextView)findViewById(R.id.cab_search_list_actvity_txt_totalcab);
            txtShortBy=(TextView)findViewById(R.id.cab_search_list_actvity_txt_select_short);
            txtFilter=(TextView)findViewById(R.id.cab_search_list_actvity_txt_filter);
            txtAc=(TextView)findViewById(R.id.cab_search_list_actvity_txt_nonstop);
            layoutBottom=(LinearLayout)findViewById(R.id.cab_search_list_actvity_layout_bottom);
            layoutShort=(LinearLayout)findViewById(R.id.cab_search_list_actvity_layout_short);
            layoutAcCab=(LinearLayout)findViewById(R.id.cab_search_list_actvity_layout_switch_btn);
            switchCompatNonAc=(SwitchCompat)findViewById(R.id.cab_search_list_actvity_switch_btn);
            coordinatorLayout=(CoordinatorLayout) findViewById(R.id.cab_search_list_actvity_coordinator);
            imgHome=(ImageView)findViewById(R.id.cab_search_list_act_toolbar_img_home);



            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                cabSearchList=new ArrayList<CabSearchResponse>();
                resetCabSearchList=new ArrayList<CabSearchResponse>();
                cabSearchResponse=(CabSearchResponse[])bundle.getSerializable("CabList");
                cabSearchList= new ArrayList<CabSearchResponse>(Arrays.asList(cabSearchResponse));
                resetCabSearchList= new ArrayList<CabSearchResponse>(Arrays.asList(cabSearchResponse));

                strFromCity=bundle.getString("FromCity");
                strToCity=bundle.getString("ToCity");

                strDepartDate=bundle.getString("DepartDate");
                strDepartTime=bundle.getString("DepartTime");
                txtFromCity.setText(strFromCity);
                txtToCity.setText(strToCity);

                txtDepartDate.setText("Dep "+strDepartDate);
                txtDepartTime.setText("Time "+strDepartTime);

                int totalBus=cabSearchList.size();
                txtTotalBus.setText("Total Cab:- "+String.valueOf(totalBus));


            }
            /*Tool bar top home icon on click go to HomeMainActivity_utility*/
            imgHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intenthome=new Intent(CabSearchListActivity.this, MainActivity_utility.class);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intenthome);
                    finish();
                }
            });

            //CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) layoutBottom.getLayoutParams();
            //layoutParams.setBehavior(new LinearLayoutBehavior());
            recyclerView=(RecyclerView)findViewById(R.id.cab_search_list_actvity_recycler);

            //flightSearchList = new ArrayList<>();
            adapter = new CabSearchListAdapter(CabSearchListActivity.this, cabSearchList, this);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(CabSearchListActivity.this,DividerItemDecoration.VERTICAL_LIST,0));
            recyclerView.setAdapter(adapter);

            //Recyclerview On scroll listener
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    switch (newState) {
                        case RecyclerView.SCROLL_STATE_IDLE:
                            //if(layoutBottom.getVisibility()== View.GONE)
                               // layoutBottom.setVisibility(View.VISIBLE);
                            layoutBottom.setVisibility(View.GONE);
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
                    Intent intent1 = new Intent(CabSearchListActivity.this, BusShortByActivity.class);
                    startActivityForResult(intent1, 1);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });

            /*Text Filter Click listener open filter list activity*/
            /*txtFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    *//*calculate size of fligth search list and short alphabetically flight name wise*//*
                    ArrayList<String> tempNameList=new ArrayList<>();
                    ArrayList<String> tempBordingPointList=new ArrayList<>();
                    ArrayList<String> tempDropPointList=new ArrayList<>();
                    if(cabSearchList.size() > 0){
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

                            Intent intent1 = new Intent(CabSearchListActivity.this, BusFilterActivity.class);
                            Bundle bundle1=new Bundle();
                            bundle1.putStringArrayList("CabName",busNameList);
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
            });*/

            /*click on layout of Switch Compact Bottun */
            layoutAcCab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean switchBtn=switchCompatNonAc.isChecked();
                    if(!switchBtn){

                        switchCompatNonAc.setChecked(true);
                        String str="AC";

                        //findnAcBus(str);

                    }
                    else {
                        switchCompatNonAc.setChecked(false);
                        if(cabSearchList.size() > 0){
                            txtAc.setTextColor(getResources().getColor(R.color.black));
                            adapter.setData(CabSearchListActivity.this,cabSearchList,CabSearchListActivity.this,shortby);
                            recyclerView.setAdapter(adapter);
                            int totalFlight=cabSearchList.size();
                            txtTotalBus.setText("Total Cab:- "+String.valueOf(totalFlight));
                        }
                    }
                }
            });

            /*click on Switch button for show only ac bus*/
           /* switchCompatNonAc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
            });*/

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onCabSelected(CabSearchResponse cabList) {
        try {

            if(cabList != null){


                CabSharedValues.getInstance().cabSelectArrayList=new ArrayList<CabSearchResponse>(Collections.singleton(cabList));
               // BusSharedValues.getInstance().busSearchResponse=busSearchList;
                CabSharedValues.getInstance().TotalFare= Double.parseDouble(cabList.getTotal());
                Bundle bundle =new Bundle();
                bundle.putString("DepartTime",strDepartTime);
                bundle.putString("DepartDate",strDepartDate);
                bundle.putString("FromCity",strFromCity);
                bundle.putString("ToCity",strToCity);
                bundle.putSerializable("CabSearch",cabList);
                Intent intent=new Intent(CabSearchListActivity.this, CabSelectActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
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