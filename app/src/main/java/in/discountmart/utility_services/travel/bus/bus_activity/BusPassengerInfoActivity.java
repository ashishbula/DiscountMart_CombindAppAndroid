package in.discountmart.utility_services.travel.bus.bus_activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.travel.bus.bus_adapter.BusPassengerInfoAdapter;
import in.discountmart.utility_services.travel.bus.bus_model.BusPassengerModel;
import in.discountmart.utility_services.travel.bus.bus_model.BusSeatModel;
import in.discountmart.utility_services.travel.bus.bus_sharedprference.BusSharedValues;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.ErrorMsgModel;
import in.discountmart.utility_services.utilities.DividerItemDecoration;
import in.discountmart.utility_services.utilities.Utilities;

public class BusPassengerInfoActivity extends AppCompatActivity {


    TextView txtBoardPoint;
    TextView txtDropPoint;
    TextView txtFromCity;
    TextView txtToCity;
    TextView txtFareAmount;
    TextView txtTotalSeat;
    LinearLayout layoutContinue;

    TextView txtTravelName;
    TextView txtBustype;
    TextView txtDepartDate;
    RecyclerView recyclerView;
    Button btnContinue;


    String strTripId = "";
    String strDepartDate = "";
    String strTravelName = "";
    String strBusType = "";
    String strSelectionType = "";

    BusPassengerInfoAdapter adapter;
    int totalSeat=0;
    int totalOther=1;
    boolean loadFirst;

    /*Array List*/
    ArrayList<BusSeatModel> busSeatModelsList;
    ArrayList<BusPassengerModel> busPassengerList;
    ArrayList<BusPassengerModel> busPassengerListTemp;
    ArrayList<ErrorMsgModel> errorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bus_passenger_info);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {


            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.str_bus_book_review));

            txtTravelName=(TextView)findViewById(R.id.bus_passenger_act_txt_travel_name);
            txtBustype=(TextView)findViewById(R.id.bus_passenger_act_txt_bus_type);
            txtDepartDate=(TextView)findViewById(R.id.bus_passenger_act_txt_dep_Date);
            txtFareAmount=(TextView)findViewById(R.id.bus_passenger_act_txt_total_amnt);
            txtTotalSeat=(TextView)findViewById(R.id.bus_passenger_act_txt_total_seat);
            btnContinue=(Button)findViewById(R.id.bus_passenger_act_btn_continue);
            recyclerView=(RecyclerView)findViewById(R.id.bus_passenger_act_recycler);
            layoutContinue=(LinearLayout)findViewById(R.id.bus_passenger_act_layout_continue);

           // loadFirst=true;
            /*Get value from Intent*/
            Intent intent=getIntent();
            if(intent != null){
                strBusType=intent.getStringExtra("BusType");
                strDepartDate=intent.getStringExtra("DepartDate");
                strTravelName=intent.getStringExtra("TravelName");

                loadFirst=true;

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
                txtDepartDate.setText(strDepartDate);
            }
            /*Get value form Bus Shard Preference*/
            totalSeat= BusSharedValues.getInstance().busSeatModelArrayList.size();
            txtTotalSeat.setText("Total Seat- "+String.valueOf(totalSeat));
            txtFareAmount.setText(getResources().getString(R.string.rs_symbol)+" "+String.valueOf(BusSharedValues.getInstance().TotalFare));

            /*Get select seat from bus*/
            if(BusSharedValues.getInstance().busSeatModelArrayList.size() > 0){
                busSeatModelsList=BusSharedValues.getInstance().busSeatModelArrayList;
            }

           // busPassengerList=new ArrayList<BusPassengerModel>();

            /*Call method and assign value in list*/
           // busPassengerList=travelerInfoList();

            //footerInfoList=footerList();
            //infoAdapter = new PassengerInfoAdapter(this, pessengerInfoArrayList,footerInfoList,FlightPassengerInfoActivity.this);
            //adapter = new BusPassengerInfoAdapter(this,busPassengerList,errorList, BusPassengerInfoActivity.this);
            // infoAdapter.setData(this,pessengerInfoArrayList);
            // infoAdapter.setDataFooter(this,footerInfoList);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(BusPassengerInfoActivity.this,DividerItemDecoration.VERTICAL_LIST,0));
            //recyclerView.setAdapter(adapter);

            // Button continue click listner
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        boolean fillalldone=false;
                        boolean fillallother = false;
                        int fillall=0;
                        int i =0;

                        ArrayList<BusPassengerModel> pessengerInfoArrayList1=new ArrayList<BusPassengerModel>();
                        pessengerInfoArrayList1=BusPassengerInfoAdapter.pessengerInfosList;

                        for ( i=0; i < BusPassengerInfoAdapter.pessengerInfosList.size();i++ ){

                            if(BusPassengerInfoAdapter.pessengerInfosList.get(i).getType()==1){

                                if(BusPassengerInfoAdapter.pessengerInfosList.get(i).getName().equals("")){

                                    errorList.get(i).setErrorMsg("Enter Name");
                                    adapter.notifyItemChanged(i,errorList);

                                    recyclerView.scrollToPosition(i);
                                    fillalldone=false;

                                    break;
                                }
                                else if(BusPassengerInfoAdapter.pessengerInfosList.get(i).getMobile().equals("")){

                                    errorList.get(i).setErrorMsg("Enter Mobile Number");
                                    adapter.notifyItemChanged(i,errorList);
                                    recyclerView.scrollToPosition(i);
                                    fillalldone=false;

                                    break;
                                }
                                else if (BusPassengerInfoAdapter.pessengerInfosList.get(i).getMobile().length() > 0
                                        && BusPassengerInfoAdapter.pessengerInfosList.get(i).getMobile().length() < 10) {
                                    errorList.get(i).setErrorMsg("Enter complete mobile no.");
                                    adapter.notifyItemChanged(i,errorList);
                                    recyclerView.scrollToPosition(i);
                                    fillalldone=false;

                                    break;
                                }
                                else if(BusPassengerInfoAdapter.pessengerInfosList.get(i).getEmail().equals("")){

                                    errorList.get(i).setErrorMsg("Enter Email");
                                    adapter.notifyItemChanged(i,errorList);

                                    recyclerView.scrollToPosition(i);
                                    fillalldone=false;

                                    break;
                                }
                                else  if(!Utilities.isValidEmail(BusPassengerInfoAdapter.pessengerInfosList.get(i).getEmail())
                                        && BusPassengerInfoAdapter.pessengerInfosList.get(i).getEmail().length() > 0){
                                    errorList.get(i).setErrorMsg("enter valid email id.");
                                    adapter.notifyItemChanged(i,errorList);

                                    recyclerView.scrollToPosition(i);
                                    fillalldone=false;

                                    break;
                                }

                                else if(BusPassengerInfoAdapter.pessengerInfosList.get(i).getAge().equals("")){

                                    // pessengerInfoArrayList1.get(i).setErrorMsg("Enter Date Of Birth");
                                    errorList.get(i).setErrorMsg("Enter Age");
                                    adapter.notifyItemChanged(i,errorList);
                                    recyclerView.scrollToPosition(i);
                                    fillalldone=false;
                                    //fillallother=false;
                                    break;
                                }
                                else {
                                    errorList.get(i).setErrorMsg("");
                                    adapter.notifyItemChanged(i,errorList);

                                    fillalldone=true;
                                    fillallother=false;
                                }

                            }

                            else if(BusPassengerInfoAdapter.pessengerInfosList.get(i).getType()==2){

                                if(BusPassengerInfoAdapter.pessengerInfosList.get(i).getMobile().equals("")){
                                    //BusPassengerInfoAdapter.FooterHolder.textErrorFooter.setText("Enter Mobile No.");
                                    errorList.get(i).setErrorMsg("Enter mobile no.");
                                    adapter.notifyItemChanged(i,errorList);
                                    // recyclerView.scrollToPosition(i);
                                    break;
                                }
                                else if (BusPassengerInfoAdapter.pessengerInfosList.get(i).getMobile().length() > 0
                                        && BusPassengerInfoAdapter.pessengerInfosList.get(i).getMobile().length() < 10) {
                                    //BusPassengerInfoAdapter.FooterHolder.textErrorFooter.setText("Enter complete mobile no.");

                                    errorList.get(i).setErrorMsg("Enter complete mobile no.");
                                    adapter.notifyItemChanged(i,errorList);

                                    break;
                                }
                                else  if(BusPassengerInfoAdapter.pessengerInfosList.get(i).getEmail().equals("")){
                                    //BusPassengerInfoAdapter.FooterHolder.textErrorFooter.setText("Enter Email Id");
                                    // recyclerView.scrollToPosition(i);
                                    errorList.get(i).setErrorMsg("Enter email id.");
                                    adapter.notifyItemChanged(i,errorList);
                                    break;
                                }
                                else  if(!Utilities.isValidEmail(BusPassengerInfoAdapter.pessengerInfosList.get(i).getEmail())
                                        && BusPassengerInfoAdapter.pessengerInfosList.get(i).getEmail().length() > 0){
                                    // BusPassengerInfoAdapter.FooterHolder.textErrorFooter.setText("Enter valid email id.");
                                    errorList.get(i).setErrorMsg("Enter valid email id.");
                                    adapter.notifyItemChanged(i,errorList);

                                    break;
                                }
                                else {
                                    errorList.get(i).setErrorMsg("");
                                    pessengerInfoArrayList1=BusPassengerInfoAdapter.pessengerInfosList;

                                    fillallother=true;
                                }

                            }

                        }

                        if(fillalldone && fillallother){

                            View view = BusPassengerInfoActivity.this.getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(),
                                        0);
                            }

                            Intent intent=new Intent(BusPassengerInfoActivity.this, BusBookFinalDetailActivity.class);

                            Bundle bundle1=new Bundle();
                            bundle1.putSerializable("PassengerInfo",pessengerInfoArrayList1);
                            intent.putExtras(bundle1);
                            busPassengerListTemp=new ArrayList<BusPassengerModel>();
                            busPassengerListTemp.addAll(busPassengerList);
                            BusSharedValues.getInstance().buspessengerInfoList.clear();
                           // BusSharedValues.getInstance().buspessengerInfoList.addAll(pessengerInfoArrayList1);
                            BusSharedValues.getInstance().buspessengerInfoList=pessengerInfoArrayList1;
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                            loadFirst=false;
                        }

                        else {
                            //infoAdapter2.setData(FlightPassengerInfoActivity.this,errorList);
                            adapter.notifyItemChanged(i,errorList);
                            //infoAdapter2.notifyDataSetChanged();
                            Toast.makeText(BusPassengerInfoActivity.this,"Something missing field please fill",Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ArrayList<BusPassengerModel> travelerInfoList(){

        errorList = new ArrayList<ErrorMsgModel>();
        ArrayList<BusPassengerModel> list = new ArrayList<BusPassengerModel>();



        if(totalSeat > 0){

            for(int i = 0; i < totalSeat; i++){
                BusPassengerModel pessengerInfo1 = new BusPassengerModel();
                ErrorMsgModel msgModel=new ErrorMsgModel();
                int index=i;
                pessengerInfo1.setName("");
                pessengerInfo1.setGender("");
                //pessengerInfo.setMobile("");
                pessengerInfo1.setAge("");
                pessengerInfo1.setSeatType(busSeatModelsList.get(i).getSeatType());

                if(busSeatModelsList.get(i).getSeatType().equals("1")){
                    pessengerInfo1.setSeat(busSeatModelsList.get(i).getSeat()+ "- Seat");

                }
                else {
                    pessengerInfo1.setSeat(busSeatModelsList.get(i).getSeat()+ "- Sleeper");

                }
                pessengerInfo1.setType(1);
                pessengerInfo1.setTotalSeat(index+1);

                pessengerInfo1.setErrorMsg("");
                pessengerInfo1.setEmail("");
                pessengerInfo1.setMobile("");
                msgModel.setErrorMsg("");
                errorList.add(msgModel);
                list.add(pessengerInfo1);
            }
        }

        if(totalOther > 0){

            for(int i = 0; i < totalOther; i++){
                BusPassengerModel pessengerInfo4 = new BusPassengerModel();
                ErrorMsgModel msgModel4=new ErrorMsgModel();
                int indexOther=i;
                pessengerInfo4.setMobile(new LoginPreferences_Utility(this).getLoggedinUser().getMobileNo());
                pessengerInfo4.setEmail(new LoginPreferences_Utility(this).getLoggedinUser().getEmailId());
                pessengerInfo4.setType(2);
                pessengerInfo4.setErrorMsg("");
                list.add(pessengerInfo4);

                msgModel4.setErrorMsg("");
                errorList.add(msgModel4);
            }
        }
        return list;
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

    @Override
    public void onResume(){

        try {
            if(loadFirst){
                busPassengerList=new ArrayList<BusPassengerModel>();
                busPassengerList = travelerInfoList();
                adapter = new BusPassengerInfoAdapter(this,busPassengerList,errorList, BusPassengerInfoActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            else {
                //pessengerInfoArrayListTemp=new ArrayList<PessengerInfo>();
                // pessengerInfoArrayListTemp=  FlightSharedValues.getInstance().pessengerInfoArrayList;

                if(busPassengerListTemp.size() == BusSharedValues.getInstance().buspessengerInfoList.size()){
                    adapter = new BusPassengerInfoAdapter(this,busPassengerListTemp,errorList, BusPassengerInfoActivity.this);
                    //PassengerInfoAdapter.pessengerInfosList=FlightSharedValues.getInstance().pessengerInfoArrayList;
                    recyclerView.setAdapter(adapter);
                }
                else {
                    adapter = new BusPassengerInfoAdapter(this,busPassengerListTemp,errorList, BusPassengerInfoActivity.this);
                    //PassengerInfoAdapter.pessengerInfosList=FlightSharedValues.getInstance().pessengerInfoArrayList;
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }

        super.onResume();
    }
}
