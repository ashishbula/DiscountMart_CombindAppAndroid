package in.discountmart.utility_services.travel.hotel.hotel_activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.travel.hotel.hotel_adapter.SelectChildAgeAdapter2;
import in.discountmart.utility_services.utilities.DividerItemDecoration;

public class HotelSelectRoomChildAgeActivity extends AppCompatActivity implements SelectChildAgeAdapter2.ChildAgeAdapterListener{

    LinearLayout layoutHeader;
    RecyclerView recyclerViewList;
    SelectChildAgeAdapter2 adapter;
    ArrayList<Integer> shortList;
    public static TextView txtAge1;
    public static TextView txtAge2;
    String child;
    public static int age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_hotel_select_room_child_age);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        try{
            layoutHeader=(LinearLayout)findViewById(R.id.hotel_child_age_layout_header);
            recyclerViewList=(RecyclerView)findViewById(R.id.hotel_child_age_recycler_list);

            Intent intent=getIntent();
            child= intent.getStringExtra("child");



            /*REcycler View set divider item line*/
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerViewList.setLayoutManager(mLayoutManager);
            recyclerViewList.setItemAnimator(new DefaultItemAnimator());
            recyclerViewList.addItemDecoration(new DividerItemDecoration(HotelSelectRoomChildAgeActivity.this, DividerItemDecoration.VERTICAL_LIST, 0));

            shortList=new ArrayList<>();
            shortList=getChildAgeList();
            if(shortList.size() > 0){
                //flightSearchList = new ArrayList<>();
                adapter = new SelectChildAgeAdapter2(HotelSelectRoomChildAgeActivity.this, shortList,this);
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

    /*Initiat child age list*/
    public ArrayList<Integer> getChildAgeList(){

        ArrayList<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);

        return list;
    }

    @Override
    public void onSelected(Integer item) {

        if (item != null){

                //HotelSelectRoomAdpter.MyViewHolder.txtchildAge.setText(String.valueOf(item));
                age=item;

                finish();

        }
    }
}
