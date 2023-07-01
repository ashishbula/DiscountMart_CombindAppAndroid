package in.discountmart.utility_services.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.adapter.NameWithCheckboxListAdapter;
import in.discountmart.utility_services.model.NameListModel;
import in.discountmart.utility_services.travel.bus.bus_activity.BusFilterActivity;
import in.discountmart.utility_services.travel.hotel.hotel_activity.HotelFilterActivity;
import in.discountmart.utility_services.utilities.DividerItemDecoration;


public class NameListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnApply;

    ArrayList<NameListModel> nameArrayList;
    NameListModel nameListModel;
    NameWithCheckboxListAdapter adapter;
    String type="";
    String service="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_list_name);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            recyclerView=(RecyclerView)findViewById(R.id.name_list_recycler);
            btnApply=(Button)findViewById(R.id.name_list_btn_apply);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            Bundle bundle=getIntent().getExtras();

            if(bundle != null){
                type=bundle.getString("Type");
                service=bundle.getString("Service");
                nameArrayList=(ArrayList<NameListModel>) bundle.getSerializable("NameList");

                /*Set title on tool bar*/
                if(type.contentEquals("Travel"))
                    getSupportActionBar().setTitle("Travels");

                else if(type.contentEquals("Board"))
                    getSupportActionBar().setTitle("Boarding Point");

                else if(type.contentEquals("Drop"))
                    getSupportActionBar().setTitle("Drop Point");

                else if(type.contentEquals("Hotel"))
                    getSupportActionBar().setTitle("Hotel Name");

            }

            if(nameArrayList.size() > 0){

                //flightSearchList = new ArrayList<>();
                adapter = new NameWithCheckboxListAdapter(NameListActivity.this, nameArrayList);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(NameListActivity.this, DividerItemDecoration.VERTICAL_LIST,0));
                recyclerView.setAdapter(adapter);
            }

            /*Button apply on click*/
            btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String data = "";
                    ArrayList<NameListModel> stList = ((NameWithCheckboxListAdapter) adapter)
                            .getNameList();
                    ArrayList<String> list=new ArrayList<String>();

                    for (int i = 0; i < stList.size(); i++) {
                        NameListModel nameList = stList.get(i);
                        if (nameList.isSelected() == true) {
                            //data = data + "\n" + nameList.getName().toString();
                            data =  nameList.getName().toString();
                            list.add(data);
                        }
                    }
                    if(list.size() > 0){

                        if(service.contentEquals("Bus")){
                            Intent travelIntent =new Intent(NameListActivity.this, BusFilterActivity.class);
                            Bundle travelBundle=new Bundle();

                            if(type.contentEquals("Travel")){
                                travelBundle.putString("Type","Travels");
                                travelBundle.putStringArrayList("TravelsList",list);
                            }

                            else if(type.contentEquals("Board")){
                                travelBundle.putString("Type","Board");
                                travelBundle.putStringArrayList("BoardList",list);
                            }
                            else if(type.contentEquals("Drop")){
                                travelBundle.putString("Type","Drop");
                                travelBundle.putStringArrayList("DropList",list);
                            }

                            travelIntent.putExtras(travelBundle);
                            setResult(RESULT_OK, travelIntent);
                            overridePendingTransition(R.anim.slide_down,0);
                            finish();
                        }

                        else if(service.contentEquals("Hotel")){
                            Intent hotelIntent =new Intent(NameListActivity.this, HotelFilterActivity.class);
                            Bundle travelBundle=new Bundle();

                            if(type.contentEquals("Hotel")){
                                travelBundle.putString("Type","Hotels");
                                travelBundle.putStringArrayList("HotelList",list);
                            }

                            hotelIntent.putExtras(travelBundle);
                            setResult(RESULT_OK, hotelIntent);
                            overridePendingTransition(R.anim.slide_down,0);
                            finish();
                        }


                    }

                    /*Toast.makeText(NameListActivity.this,
                            "Selected Name: \n" + data, Toast.LENGTH_LONG)
                            .show();*/
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
}
