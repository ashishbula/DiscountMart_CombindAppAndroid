package in.discountmart.utility_services.travel.bus.bus_activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.travel.bus.bus_adapter.BusShortByListAdapter;
import in.discountmart.utility_services.utilities.DividerItemDecoration;

public class BusShortByActivity extends AppCompatActivity implements BusShortByListAdapter.BusShortAdapterListener {


    LinearLayout layoutHeader;
    RecyclerView recyclerViewList;
    BusShortByListAdapter adapter;
    ArrayList<String> shortList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bus_short_by);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        try{
            layoutHeader=(LinearLayout)findViewById(R.id.bus_shortby_layout_header);
            recyclerViewList=(RecyclerView)findViewById(R.id.bus_shortby_recycler_list);

            /*REcycler View set divider item line*/
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerViewList.setLayoutManager(mLayoutManager);
            recyclerViewList.setItemAnimator(new DefaultItemAnimator());
            recyclerViewList.addItemDecoration(new DividerItemDecoration(BusShortByActivity.this, DividerItemDecoration.VERTICAL_LIST, 0));

            shortList=new ArrayList<>();
            shortList=getshortList();
            if(shortList.size() > 0){
                //flightSearchList = new ArrayList<>();
                adapter = new BusShortByListAdapter(BusShortByActivity.this, shortList, this);
            }
            recyclerViewList.setAdapter(adapter);

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
        list.add("Sleeper");
        list.add("Seater");
        //list.add("AC");
        //list.add("NonAc");
        list.add("Early Start");
        list.add("Late Start");
        list.add("Early Arrival");
        list.add("Late Arrival");

        return list;
    }

    @Override
    public void onSelected(String item) {
        try {
            if(item != null){
                Intent intent = new Intent();

                intent.putExtra("ShortBy", item);

                setResult(RESULT_OK, intent);
                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
