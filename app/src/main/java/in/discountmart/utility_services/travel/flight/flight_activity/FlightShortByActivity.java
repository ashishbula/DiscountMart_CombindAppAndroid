package in.discountmart.utility_services.travel.flight.flight_activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.travel.flight.adapter.FlightShortbyListAdapter;
import in.discountmart.utility_services.travel.flight.adapter.ReturnFlightSortbyListAdapter;
import in.discountmart.utility_services.utilities.DividerItemDecoration;

public class FlightShortByActivity extends AppCompatActivity implements FlightShortbyListAdapter.FlightShortAdapterListener,
        ReturnFlightSortbyListAdapter.RetFlightShortAdapterListener {

    LinearLayout layoutHeader;
    LinearLayout layoutReturn;
    LinearLayout layoutSingle;
    TextView txtOwnFlight;
    TextView txtRetFlight;
    RecyclerView recyclerViewList;
    RecyclerView recyclerRetList;
    FlightShortbyListAdapter adapter;
    ReturnFlightSortbyListAdapter retAdapter;
    ArrayList<String> shortList;
    ArrayList<String> shortListRet;
    String flighttype="";
    String ownflight="";
    String retflight="";
    String type="";
    String sortby="";
    String retSortby="";

    boolean own=false;
    boolean ret=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_flight_short_by);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        try{
            layoutHeader=(LinearLayout)findViewById(R.id.flight_shortby_layout_header);
            layoutSingle=(LinearLayout)findViewById(R.id.flight_shortby_layout_single);
            layoutReturn=(LinearLayout)findViewById(R.id.flight_shortby_layout_return);
            txtOwnFlight=(TextView)findViewById(R.id.flight_shortby_txt_ownward);
            txtRetFlight=(TextView)findViewById(R.id.flight_shortby_txt_return);
            recyclerViewList=(RecyclerView)findViewById(R.id.flight_shortby_recycler_list);
            recyclerRetList=(RecyclerView)findViewById(R.id.flight_shortby_recycler_returnlist);

            Intent intent=getIntent();
            if(intent != null){
                flighttype=intent.getStringExtra("Type");
                ownflight=intent.getStringExtra("OwnFlight");
                retflight=intent.getStringExtra("ReturnFlight");
            }

            /*If only Ownward Flight*/
            if(flighttype.contentEquals("Ownward")){

                layoutReturn.setVisibility(View.GONE);
                layoutSingle.setVisibility(View.VISIBLE);
                recyclerRetList.setVisibility(View.GONE);
                /*REcycler View set divider item line*/
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerViewList.setLayoutManager(mLayoutManager);
                recyclerViewList.setItemAnimator(new DefaultItemAnimator());
                recyclerViewList.addItemDecoration(new DividerItemDecoration(FlightShortByActivity.this, DividerItemDecoration.VERTICAL_LIST, 0));

                shortList=new ArrayList<>();
                shortList=getshortList();
                if(shortList.size() > 0){
                    //flightSearchList = new ArrayList<>();
                    adapter = new FlightShortbyListAdapter(FlightShortByActivity.this, shortList, this);
                }
                recyclerViewList.setAdapter(adapter);
            }

            /*If Both Ownward and return Flight*/
            else if (flighttype.contentEquals("Return")){

                layoutReturn.setVisibility(View.VISIBLE);
                layoutSingle.setVisibility(View.GONE);
                recyclerRetList.setVisibility(View.VISIBLE);
                txtOwnFlight.setText(ownflight);
                txtRetFlight.setText(retflight);

                /* Recycler for Ownward flight adapter
                    REcycler View set divider item line
                                                    */
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerRetList.setLayoutManager(mLayoutManager);
                recyclerRetList.setItemAnimator(new DefaultItemAnimator());
                recyclerRetList.addItemDecoration(new DividerItemDecoration(FlightShortByActivity.this, DividerItemDecoration.VERTICAL_LIST, 0));

                shortListRet=new ArrayList<>();
                shortListRet=getshortListRet();
                if(shortListRet.size() > 0){
                    //flightSearchList = new ArrayList<>();
                    retAdapter = new ReturnFlightSortbyListAdapter(FlightShortByActivity.this, shortListRet, this);
                }
                recyclerRetList.setAdapter(retAdapter);


                /* Recycler for return flight adapter
                    Recycler View set divider item line
                                                    */
                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                recyclerViewList.setLayoutManager(mLayoutManager1);
                recyclerViewList.setItemAnimator(new DefaultItemAnimator());
                recyclerViewList.addItemDecoration(new DividerItemDecoration(FlightShortByActivity.this, DividerItemDecoration.VERTICAL_LIST, 0));

                shortList=new ArrayList<>();
                shortList=getshortList();
                if(shortList.size() > 0){
                    adapter = new FlightShortbyListAdapter(FlightShortByActivity.this, shortList, this);
                }
                recyclerViewList.setAdapter(adapter);
            }


            layoutHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Initiat short list*/
    public ArrayList<String> getshortList(){

        ArrayList<String> list=new ArrayList<>();
        list.add("Cheapest");
        list.add("Highest");
        list.add("Fastest");
        list.add("Late Take off");
        list.add("Early Take off");
        list.add("Early Landing");
        list.add("Late Landing");

        return list;
    }

    /*Initiat short list for Return flight*/
    public ArrayList<String> getshortListRet(){

        ArrayList<String> list=new ArrayList<>();
        list.add("Cheapest");
        list.add("Highest");
        list.add("Fastest");
        list.add("Late Take off");
        list.add("Early Take off");
        list.add("Early Landing");
        list.add("Late Landing");

        return list;
    }

    @Override
    public void onSelected(String item) {
        own=true;
        type="Own";
        sortby=item;
        try {
            if(flighttype.contentEquals("Ownward")){
                Intent intent = new Intent();
                intent.putExtra("ShortBy", item);
                intent.putExtra("Type", "Own");
                setResult(RESULT_OK, intent);
                finish();
            }
            else {
                sendSortData(sortby,retSortby,own,ret);
            }
        }catch (Exception e){
            e.printStackTrace();
        }







        //finish();

    }

    @Override
    public void onSortSelected(String item) {
        if(item != null && !item.contentEquals("")){

            ret=true;
            type="Return";
            retSortby=item;

            sendSortData(sortby,retSortby,own,ret);
            //finish();
        }
    }

    /*Method for send sort data*/
    public void sendSortData(String sortOwnFlightitem,String sortRetFlightitem, boolean ownFlight,boolean retFlight ){

        try {
            if(ownFlight && !retFlight  ){

                Toast.makeText(FlightShortByActivity.this,"Please select return flight sort item",Toast.LENGTH_SHORT).show();
            }
            else if(retFlight && !ownFlight ){

                Toast.makeText(FlightShortByActivity.this,"Please select ownward flight sort item",Toast.LENGTH_SHORT).show();
            }
            else if(ownFlight && retFlight){
                Intent intent = new Intent();
                intent.putExtra("RetShortBy", sortRetFlightitem);
                intent.putExtra("ShortBy", sortOwnFlightitem);

                setResult(RESULT_OK, intent);
                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
