package in.discountmart.utility_services.travel.hotel.hotel_activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import in.discountmart.R;
import in.discountmart.utility_services.activity.MainActivity_utility;
import in.discountmart.utility_services.travel.hotel.hotel_adapter.HotelSearchListAdapter;
import in.discountmart.utility_services.travel.hotel.hotel_adapter.HotelSortByAdapter;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelSearchResponse;
import in.discountmart.utility_services.travel.hotel.hotel_shared_preferance.HotelSharedValues;

public class HotelSearchListActivity extends AppCompatActivity implements HotelSearchListAdapter.HotelListAdapterListener,
        HotelSortByAdapter.HotelShortAdapterListener {

    Toolbar toolbar;
    TextView txtCity;
    TextView txtChkInDate;
    TextView txtChkOutDate;
    TextView txtAdult;
    TextView txtChild;
    TextView txtTotHotel;
    TextView txtSortLowRate;
    TextView txtSortHighRate;
    TextView txtSortHighPrice;
    TextView txtSortLowPrice;
    RecyclerView recyclerHotelList;
    RecyclerView recyclerSortList;
    LinearLayout layoutFilter;
    ImageView imgHome;

    String strCity;
    String strChkInDate;
    String strChkOutDate;
    String strAdult;
    String strChild;
    String strTotHotel;


    ArrayList<HotelSearchResponse> hotelSearchList;
    ArrayList<HotelSearchResponse> resetHotelSearchList;
    ArrayList<HotelSearchResponse> filterHotelSearchList;
    ArrayList<String> filterNameList;
    ArrayList<String> shortList;

    ArrayList<String> hotelNameList = new ArrayList<String>();
    HotelSearchResponse hotelSearchResponse[];
    HotelSearchListAdapter adapter;
    HotelSortByAdapter sortByAdapter;

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_hotel_search_list);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            view=findViewById(R.id.content);
            toolbar=(Toolbar)findViewById(R.id.hotel_search_list_act_toolbar);

            setSupportActionBar(toolbar);

            // toolbar fancy stuff
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            txtAdult=(TextView)findViewById(R.id.hotel_search_list_act_toolbar_txt_adult);
            txtChild=(TextView)findViewById(R.id.hotel_search_list_act_toolbar_txt_child);
            txtChkInDate=(TextView)findViewById(R.id.hotel_search_list_act_toolbar_txt_chkin_Date);
            txtChkOutDate=(TextView)findViewById(R.id.hotel_search_list_act_toolbar_txt_chkout_Date);
            txtCity=(TextView)findViewById(R.id.hotel_search_list_act_toolbar_txt_city);
            txtTotHotel=(TextView)findViewById(R.id.hotel_search_list_act_toolbar_txt_total_hotel);
           /* txtSortLowRate=(TextView)findViewById(R.id.hotel_search_list_actvity_txt_lowrate);
            txtSortHighRate=(TextView)findViewById(R.id.hotel_search_list_actvity_txt_highrate);
            txtSortLowPrice=(TextView)findViewById(R.id.hotel_search_list_actvity_txt_lowprice);
            txtSortHighPrice=(TextView)findViewById(R.id.hotel_search_list_actvity_txt_highprice);*/
            recyclerHotelList=(RecyclerView) findViewById(R.id.hotel_search_list_actvity_recycler);
            recyclerSortList=(RecyclerView) findViewById(R.id.hotel_search_list_actvity_recycler_sort);
            layoutFilter=(LinearLayout)findViewById(R.id.hotel_search_list_actvity_layout_filter);
            imgHome=(ImageView)findViewById(R.id.hotel_search_list_act_toolbar_img_home);

            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                hotelSearchList=new ArrayList<HotelSearchResponse>();
                resetHotelSearchList=new ArrayList<HotelSearchResponse>();
                hotelSearchResponse=(HotelSearchResponse[])bundle.getSerializable("Hotel");
                hotelSearchList= new ArrayList<HotelSearchResponse>(Arrays.asList(hotelSearchResponse));
                resetHotelSearchList= new ArrayList<HotelSearchResponse>(Arrays.asList(hotelSearchResponse));

                strCity=bundle.getString("City");
                strChkInDate=bundle.getString("ChkInDate");
                strChkOutDate=bundle.getString("ChkOutDate");

                txtCity.setText(strCity);
                txtChkInDate.setText(strChkInDate);
                txtChkOutDate.setText(strChkOutDate);

                strAdult= String.valueOf(HotelSharedValues.getInstance().totAdult);
                strChild= String.valueOf(HotelSharedValues.getInstance().totChild);

                txtAdult.setText("Adult "+strAdult);
                txtChild.setText("Child "+strChild);
                int totalFlight=hotelSearchList.size();
                txtTotHotel.setText(String.valueOf(totalFlight)+" Hotels ");


            }



            //flightSearchList = new ArrayList<>();
            adapter = new HotelSearchListAdapter(HotelSearchListActivity.this, hotelSearchList, HotelSearchListActivity.this);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerHotelList.setLayoutManager(mLayoutManager);
            recyclerHotelList.setItemAnimator(new DefaultItemAnimator());
            //recyclerHotelList.addItemDecoration(new DividerItemDecoration(HotelSearchListActivity.this,DividerItemDecoration.VERTICAL_LIST,0));
            recyclerHotelList.setAdapter(adapter);

            /*Sorting Adapter*/

            recyclerSortList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
            recyclerSortList.setItemAnimator(new DefaultItemAnimator());

            shortList=new ArrayList<>();
            shortList=getshortList();
            if(shortList.size() > 0){

                sortByAdapter = new HotelSortByAdapter(HotelSearchListActivity.this, shortList, this);
            }
            recyclerSortList.setAdapter(sortByAdapter);

            /*Text Filter Click listener open filter list activity*/
            layoutFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*calculate size of hotel search list and short alphabetically hotel name wise*/
                    ArrayList<String> tempNameList=new ArrayList<>();

                    if(hotelSearchList.size() > 0){
                        for (int i=0; i < hotelSearchList.size();i++){

                            tempNameList.add(hotelSearchList.get(i).getHotelName());
                        }

                        if(tempNameList.size()> 0){
                            hotelNameList=sortAscending(tempNameList);
                        }

                        if(hotelNameList.size() > 0){
                            Set<String> setHotelName = new LinkedHashSet<>(hotelNameList);
                            hotelNameList.clear();
                            hotelNameList.addAll(setHotelName);

                            Intent intent1 = new Intent(HotelSearchListActivity.this, HotelFilterActivity.class);
                            Bundle bundle1=new Bundle();
                            bundle1.putStringArrayList("HotelName",hotelNameList);
                            intent1.putExtras(bundle1);
                            startActivityForResult(intent1, 2);
                            overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                            System.out.println(setHotelName + " common name in the list");

                        }
                        else {

                        }
                    }
                }
            });

            /*Tool bar top home icon on click go to HomeMainActivity_utility*/
            imgHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intenthome=new Intent(HotelSearchListActivity.this, MainActivity_utility.class);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intenthome);
                    finish();
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
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
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
        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }

    @Override
    public void onHotelSelected(HotelSearchResponse hotelResponse) {

        try {
            if(hotelResponse != null){

                HotelSharedValues.getInstance().hotelCode=hotelResponse.getHotelCode();
                HotelSharedValues.getInstance().traceID=hotelResponse.getTraceID();
                HotelSharedValues.getInstance().resultIndex=hotelResponse.getResultIndex();
                HotelSharedValues.getInstance().hotelName=hotelResponse.getHotelName();

                Intent intent=new Intent(this,HotelInfoActivity.class);
                intent.putExtra("TraceId",hotelResponse.getTraceID());
                intent.putExtra("HotelCode",hotelResponse.getHotelCode());
                intent.putExtra("ResultIndex",hotelResponse.getResultIndex());
                intent.putExtra("HotelName",hotelResponse.getHotelName());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Initiat short list*/
    public ArrayList<String> getshortList(){

        ArrayList<String> list=new ArrayList<>();
        list.add("Lowest Price");
        list.add("Highest Price");
        list.add("Highest Rating");
        list.add("Lowest Rating");
        return list;
    }

    @Override
    public void onTextSelected(String item) {

        try {
            if(item != null){
                String shortby="";
                if(item.contentEquals("Lowest Price")){
                    shortby=item;
                    //txtShortBy.setText(str);
                    adapter.setData(HotelSearchListActivity.this,hotelSearchList,this,shortby);
                    recyclerHotelList.setAdapter(adapter);
                }
                else if(item.contentEquals("Highest Price")){
                    shortby=item;
                    //txtShortBy.setText(str);
                    adapter.setData(HotelSearchListActivity.this,hotelSearchList,this,shortby);
                    recyclerHotelList.setAdapter(adapter);
                }
                else if(item.contentEquals("Highest Rating")){
                    shortby=item;
                    //txtShortBy.setText(str);
                    adapter.setData(HotelSearchListActivity.this,hotelSearchList,this,shortby);
                    recyclerHotelList.setAdapter(adapter);
                }
                else if(item.contentEquals("Lowest Rating")){
                    shortby=item;
                    //txtShortBy.setText(str);
                    adapter.setData(HotelSearchListActivity.this,hotelSearchList,this,shortby);
                    recyclerHotelList.setAdapter(adapter);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /*Short array list alphabetic assending*/
    public ArrayList<String> sortAscending(ArrayList<String> list) {
        Collections.sort(list);
        return list;
    }


    /*Receive Intent result form Flight filter activity */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        /*For filter list*/
         if(requestCode == 2){
            if(resultCode == RESULT_OK){
                ArrayList<HotelSearchResponse> tempfilterList=new ArrayList<HotelSearchResponse>();
                filterHotelSearchList=new ArrayList<HotelSearchResponse>();
                Bundle filterbundle=data.getExtras();
                String fiveStar="";
                String fourStar="";
                String threeStar="";
                String twoStar="";

                if(filterbundle != null){
                    ArrayList<String> tempNameList=new ArrayList<String>();
                    ArrayList<String> tempStarList=new ArrayList<String>();

                    tempNameList=(ArrayList<String>) filterbundle.getStringArrayList("NameList");
                    tempStarList=(ArrayList<String>) filterbundle.getStringArrayList("StarList");

                    fiveStar=filterbundle.getString("fiveStar");
                    fourStar=filterbundle.getString("fourStar");
                    threeStar=filterbundle.getString("threeStar");
                    twoStar=filterbundle.getString("twoStar");

                    filterHotelSearchList.addAll(hotelSearchList);
                    // final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.UK);//24 hour time formet
                    Date dateTime = null;
                    Date receiveTime=null;
                    String time="";
                    boolean filtertime;
                    boolean filterType;
                    String type="";

                    /*Filter by travel name list*/
                    if(tempStarList != null){
                        if(tempStarList.size() > 0){
                            tempfilterList=new ArrayList<HotelSearchResponse>();
                            for (int i=0;i< tempStarList.size(); i++){
                                for (int j=0; j< filterHotelSearchList.size(); j++){
                                    if (tempStarList.get(i).equals(filterHotelSearchList.get(j).getStarRating())){
                                        tempfilterList.add(filterHotelSearchList.get(j));
                                    }
                                    else {
                                        //filterHotelSearchList.size();
                                        tempfilterList.size();
                                    }
                                }
                            }
                            /*filter list travel name according*/
                            if(tempfilterList.size() > 0){
                                filterHotelSearchList=tempfilterList;
                                //tempfilterList.clear();
                            }
                            else{
                                //filterHotelSearchList.size();
                                tempfilterList.size();
                            }
                        }
                        else {
                            filterHotelSearchList.size();
                           // tempfilterList.size();
                        }
                    }
                    else {
                        filterHotelSearchList.size();
                        //tempfilterList.size();
                    }


                        /*Filter for Hotel Name */
                        if( tempNameList != null){
                            if(tempNameList.size() > 0 && filterHotelSearchList.size() > 0){
                                tempfilterList=new ArrayList<HotelSearchResponse>();
                                for (int i=0;i< tempNameList.size(); i++){
                                    for (int j=0; j< filterHotelSearchList.size(); j++){
                                        if (tempNameList.get(i).equals(filterHotelSearchList.get(j).getHotelName())){
                                            tempfilterList.add(filterHotelSearchList.get(j));
                                        }
                                        else {
                                            // filterHotelSearchList.size();
                                            tempfilterList.size();
                                        }
                                    }
                                }
                                /*filter list travel name according*/
                                if(tempfilterList.size() > 0){
                                    filterHotelSearchList=tempfilterList;
                                    //tempfilterList.clear();
                                }
                                else{
                                    //filterHotelSearchList.size();
                                    tempfilterList.size();
                                }

                            }
                            else {
                                filterHotelSearchList.size();
                                tempfilterList.size();
                            }
                        }
                        else {
                            filterHotelSearchList.size();
                            tempfilterList.size();
                        }


                        /*Filter list set in adapter*/
                    if(filterHotelSearchList.size() > 0){
                        hotelSearchList=filterHotelSearchList;
                        adapter.setData(HotelSearchListActivity.this,hotelSearchList,this,"");
                        recyclerHotelList.setAdapter(adapter);
                        int totalHotel=hotelSearchList.size();
                        txtTotHotel.setText(String.valueOf(totalHotel)+" Hotels ");
                    }
                    else {
                        Toast.makeText(HotelSearchListActivity.this,"No result found..",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            else if(resultCode== RESULT_CANCELED) {
                hotelSearchList=resetHotelSearchList;
                adapter.setData(HotelSearchListActivity.this,hotelSearchList,this,"");
                recyclerHotelList.setAdapter(adapter);
                int totalHotel=hotelSearchList.size();
                txtTotHotel.setText(String.valueOf(totalHotel)+" Hotels ");
            }
        }


    }
}
