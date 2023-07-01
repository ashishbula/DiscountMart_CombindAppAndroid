package in.discountmart.utility_services.travel.bus.bus_activity;

import android.content.Context;
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
import in.discountmart.utility_services.travel.bus.bus_adapter.BusBoardingPointAdapter;
import in.discountmart.utility_services.travel.bus.bus_adapter.BusDropPointAdapter;
import in.discountmart.utility_services.travel.bus.bus_model.Boarding_DropModel;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusSearchListResponse;
import in.discountmart.utility_services.travel.bus.bus_sharedprference.BusSharedValues;
import in.discountmart.utility_services.utilities.DividerItemDecoration;

public class BusBoarding_DropPointActivity extends AppCompatActivity {


    public static TextView txtBoardPoint;
    public static TextView txtDropPoint;
    TextView txtFromCity;
    TextView txtToCity;
    TextView txtCancelPolicy;

    TextView txtTravelName;
    TextView txtBustype;
    TextView txtDepartDate;
    public static LinearLayout layoutBoarding;
    public static LinearLayout layoutDrop;


    public static ArrayList<Boarding_DropModel> dropModelList;
    public static LinearLayout layoutcontinue;

    RecyclerView recyClerDroping;
    RecyclerView recyClerBoarding;
    Context context;


    String strTripId = "";
    String strDepartDate = "";
    String strTravelName = "";
    String strBusType = "";
    String strSelectionType = "";

    BusSearchListResponse busSearchListResponse;
    ArrayList<BusSearchListResponse.BoardingPoints> boardingPointsArrayList;
    ArrayList<BusSearchListResponse.DropingPoints> dropingPointsArrayList;
    ArrayList<BusSearchListResponse.CancellationCharge> cancellationArrayList;

    public static ArrayList<BusSearchListResponse.BoardingPoints> boardingPointsList;
    public static ArrayList<BusSearchListResponse.DropingPoints> dropingPointsList;
    BusDropPointAdapter dropPointAdapter;
    BusBoardingPointAdapter boardingPointAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bus_boarding_drop_point);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            //context=getActivity();

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.str_bus_drop_point));

            txtBoardPoint=(TextView)findViewById(R.id.droppoint_activity_txt_board_point);
            txtDropPoint=(TextView)findViewById(R.id.droppoint_activity_txt_drop_point);
            txtToCity=(TextView)findViewById(R.id.droppoint_activity_txt_tocity);
            txtFromCity=(TextView)findViewById(R.id.droppoint_activity_txt_fromcity);
            txtCancelPolicy=(TextView)findViewById(R.id.droppoint_activity_txt_bus_policy);
            txtTravelName=(TextView)findViewById(R.id.droppoint_activity_txt_travel_name);
            txtBustype=(TextView)findViewById(R.id.droppoint_activity_txt_bus_type);
            txtDepartDate=(TextView)findViewById(R.id.droppoint_activity_txt_dep_Date);
            recyClerBoarding=(RecyclerView) findViewById(R.id.droppoint_activity_recycler_boarding);
            recyClerDroping=(RecyclerView) findViewById(R.id.droppoint_activity_recycler_drop);
            layoutBoarding=(LinearLayout)findViewById(R.id.droppoint_activity_layout_boarding);
            layoutDrop=(LinearLayout)findViewById(R.id.droppoint_activity_layout_drop);
            layoutcontinue=(LinearLayout)findViewById(R.id.droppoint_activity_layout_continue);

            Bundle bundle=getIntent().getExtras();
            busSearchListResponse=new BusSearchListResponse();
            if(bundle != null){
                BusSearchListResponse busSearch=(BusSearchListResponse)bundle.getSerializable("BusSearch");
                strTravelName = bundle.getString("Travel");
                strDepartDate = bundle.getString("DepartTime");
                strBusType = bundle.getString("BusType");

                if (busSearch != null){

                    busSearchListResponse=busSearch;
                }

                /*Set Text name */
                String travelName = "";
                if (strTravelName.length() >= 25) {
                    travelName = strTravelName.substring(0, 25) + "...";
                    txtTravelName.setText(travelName);

                } else {
                    travelName = strTravelName;
                    txtTravelName.setText(travelName);

                }

                String busType = "";
                if (strBusType.length() >= 35) {
                    busType = strBusType.substring(0, 35) + "...";
                    txtBustype.setText(busType);

                } else {
                    busType = strBusType;
                    txtBustype.setText(busType);

                }
                /*Set value in text*/
                txtDepartDate.setText(strDepartDate);
                txtFromCity.setText(BusSharedValues.getInstance().busFromCityName);
                txtToCity.setText(BusSharedValues.getInstance().busToCityName);
            }

            boardingPointsList=new ArrayList<BusSearchListResponse.BoardingPoints>();
            dropingPointsList=new ArrayList<BusSearchListResponse.DropingPoints>();
            if(busSearchListResponse != null){
                boardingPointsArrayList=new ArrayList<BusSearchListResponse.BoardingPoints>(busSearchListResponse.getBoardingPoint());
                dropingPointsArrayList=new ArrayList<BusSearchListResponse.DropingPoints>(busSearchListResponse.getDroppingPoint());
                cancellationArrayList=new ArrayList<BusSearchListResponse.CancellationCharge>(busSearchListResponse.getCancelCharge());
            }

            BusSharedValues.getInstance().busDropPoint="";
            BusSharedValues.getInstance().busBoardPoint="";

            layoutBoarding.setVisibility(View.VISIBLE);
            layoutDrop.setVisibility(View.GONE);

            /*Recycler Boarding Point*/
            recyClerBoarding.setLayoutManager(new LinearLayoutManager(this));
            recyClerBoarding.setItemAnimator(new DefaultItemAnimator());
            recyClerBoarding.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,0));
            boardingPointAdapter=new BusBoardingPointAdapter(this,boardingPointsArrayList);
            recyClerBoarding.setAdapter(boardingPointAdapter);

            /*Recycler Drop Point*/
            recyClerDroping.setLayoutManager(new LinearLayoutManager(this));
            recyClerDroping.setItemAnimator(new DefaultItemAnimator());
            recyClerDroping.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,0));
            dropPointAdapter=new BusDropPointAdapter(this,dropingPointsArrayList);
            recyClerDroping.setAdapter(dropPointAdapter);

            /*Text Bording Point on click*/
            txtBoardPoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        txtDropPoint.setBackground(getResources().getDrawable(R.drawable.transparent_bg));
                        txtBoardPoint.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                        txtBoardPoint.setTextColor(getResources().getColor(R.color.white));
                        txtDropPoint.setTextColor(getResources().getColor(R.color.gray));

                    }


                    layoutBoarding.setVisibility(View.VISIBLE);
                    layoutDrop.setVisibility(View.GONE);
                    boardingPointAdapter.setBoardPointData(boardingPointsArrayList);
                    recyClerBoarding.setAdapter(boardingPointAdapter);
                }
            });

            //Text Bording Point on click
            txtDropPoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        txtBoardPoint.setBackground(getResources().getDrawable(R.drawable.transparent_bg));
                        txtDropPoint.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                        txtBoardPoint.setTextColor(getResources().getColor(R.color.gray));
                        txtDropPoint.setTextColor(getResources().getColor(R.color.white));

                    }
                    layoutBoarding.setVisibility(View.GONE);
                    layoutDrop.setVisibility(View.VISIBLE);
                    if(dropingPointsArrayList.size() > 0) {
                        dropPointAdapter.setDropPointData(dropingPointsArrayList);
                        recyClerDroping.setAdapter(dropPointAdapter);
                    }
                }
            });

             //chekc seat is select or not
            /*if(dropingPointsList.size() > 0 && boardingPointsList.size() > 0){
                layoutcontinue.setVisibility(View.VISIBLE);

            }
            else {
                layoutcontinue.setVisibility(View.GONE);
            }*/
            layoutcontinue.setVisibility(View.GONE);
            /*Button Select Seat on click*/
            layoutcontinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   if(dropingPointsList.size() > 0 && boardingPointsList.size() > 0){
                       Intent intent=new Intent(BusBoarding_DropPointActivity.this,BusPassengerInfoActivity.class);
                       intent.putExtra("TravelName",strTravelName);
                       intent.putExtra("BusType",strBusType);
                       intent.putExtra("DepartDate",strDepartDate);
                       startActivity(intent);
                       overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                   }
                   else {
                       Toast.makeText(BusBoarding_DropPointActivity.this,"Please boarding and drop point",Toast.LENGTH_SHORT).show();
                   }
                }
            });

            /*Text Cancel Policy on click*/
            txtCancelPolicy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(BusBoarding_DropPointActivity.this,BusCancellationPolicyActivity.class);
                    Bundle bundle1=new Bundle();
                    bundle1.putSerializable("Policy",cancellationArrayList);
                    intent.putExtras(bundle1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }

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
        /*if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }*/
        super.onBackPressed();
        finish();
        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }
}
