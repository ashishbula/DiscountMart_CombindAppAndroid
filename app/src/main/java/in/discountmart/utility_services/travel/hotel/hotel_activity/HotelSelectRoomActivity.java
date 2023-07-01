package in.discountmart.utility_services.travel.hotel.hotel_activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.ErrorMsgModel;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PessengerInfo;
import in.discountmart.utility_services.travel.hotel.hotel_adapter.HotelSelectRoomAdpter;
import in.discountmart.utility_services.travel.hotel.hotel_model.Child_AgeModel;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelRoomModel;
import in.discountmart.utility_services.travel.hotel.hotel_shared_preferance.HotelSharedValues;
import in.discountmart.utility_services.utilities.DividerItemDecoration;

public class HotelSelectRoomActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imgClose;
    TextView txtCity;
    TextView txtDate;
    TextView txtAddRoom;
    Button btnApply;
    LinearLayout layoutChildAge;
    LinearLayout layoutAddRoom;
    RecyclerView recyclerView;
    Spinner spinnerRoom;

    int adult=0;
    int childinteger=0;
    int room=0;
    int flag=0;
    boolean edit;

    String selectRoom="";
    public static int roomNumber=1;

    ArrayList<HotelRoomModel> hotelRoomArrayList;
    ArrayList<Child_AgeModel> childAgeList;
    HotelSelectRoomAdpter selectRoomAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_hotel_select_room);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            toolbar=(Toolbar)findViewById(R.id.hotel_select_room_act_toolbar);
            imgClose=(ImageView)findViewById(R.id.hotel_select_room_act_img_close);
            txtCity=(TextView)findViewById(R.id.hotel_select_room_act_txt_city);
            txtDate=(TextView)findViewById(R.id.hotel_select_room_act_txt_date);
            txtAddRoom=(TextView)findViewById(R.id.hotel_select_room_act_txt_addroom);
            layoutAddRoom=(LinearLayout)findViewById(R.id.hotel_select_room_act_layout_addroom);
            btnApply=(Button)findViewById(R.id.hotel_select_room_act_btn_apply);

            recyclerView=(RecyclerView)findViewById(R.id.hotel_select_room_act_recycler);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(HotelSelectRoomActivity.this,DividerItemDecoration.VERTICAL_LIST,0));
            //recyclerView.setAdapter(selectRoomAdpter);


            /*update*/
            Bundle bundle=getIntent().getExtras();
            if(bundle != null){

                edit=bundle.getBoolean("Edit");

                if(edit){
                    hotelRoomArrayList=new ArrayList<HotelRoomModel>();
                    hotelRoomArrayList=(ArrayList<HotelRoomModel>)bundle.getSerializable("RoomGuestList");
                    selectRoomAdpter=new HotelSelectRoomAdpter(HotelSelectRoomActivity.this,hotelRoomArrayList);
                    recyclerView.setAdapter(selectRoomAdpter);
                }
                else {
                    hotelRoomArrayList=new ArrayList<HotelRoomModel>();
                    /*default room number*/
                    roomNumber=1;

                    /*Entity model class for Select room info model*/
                    HotelRoomModel roomModel=new HotelRoomModel();

                    roomModel.setChild("0");
                    roomModel.setAdult("1");
                    roomModel.setRoom(String.valueOf(roomNumber));
                    roomModel.setAdultcount(1);
                    roomModel.setChildcount(0);
                    roomModel.setAge(false);
                    roomModel.setFlag(0);
                    roomModel.setChildage1(0);
                    roomModel.setChildage2(0);
                    roomModel.setAddLayout(false);
                    //Entity model class for Select child age model in select room
                    /*Child_AgeModel child_ageModel=new Child_AgeModel();
                    child_ageModel.setAge("0");
                    child_ageModel.setChild("0");
                    child_ageModel.setRoom("1");

                    childAgeList=new ArrayList<Child_AgeModel>();
                    childAgeList.add(child_ageModel);*/
                    //roomModel.setChildAgeModels(childAgeList);

                    //roomModel.setChildAgeModels(childAgeModelArrayList);

                    hotelRoomArrayList.add(hotelRoomArrayList.size(),roomModel);
                    // hotelRoomArrayList=roomInfoList();
                    //selectRoomAdpter.notifyItemInserted(hotelRoomArrayList.size());
                    selectRoomAdpter=new HotelSelectRoomAdpter(HotelSelectRoomActivity.this,hotelRoomArrayList);
                    recyclerView.setAdapter(selectRoomAdpter);
                }
            }






            /*Layout Add room on click add room info*/
            txtAddRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    roomNumber++;
                    if(roomNumber > 4){
                        Toast.makeText(HotelSelectRoomActivity.this,"No More Select room",Toast.LENGTH_SHORT).show();
                    }
                    else if(roomNumber ==1 || roomNumber < 5){
                        HotelRoomModel roomModel=new HotelRoomModel();
                        roomModel.setChild("0");
                        roomModel.setAdult("1");
                        roomModel.setRoom(String.valueOf(roomNumber));
                        roomModel.setAdultcount(1);
                        roomModel.setChildcount(0);
                        roomModel.setAge(false);
                        roomModel.setFlag(0);
                        roomModel.setChildage1(0);
                        roomModel.setChildage2(0);
                        roomModel.setAddLayout(false);

                        //Entity model class for Select child age model in select room
                       /* Child_AgeModel child_ageModel=new Child_AgeModel();
                        child_ageModel.setAge("0");
                        child_ageModel.setChild("0");
                        child_ageModel.setRoom(String.valueOf(roomNumber));

                        childAgeList=new ArrayList<Child_AgeModel>();
                        childAgeList.add(child_ageModel);
                        roomModel.setChildAgeModels(childAgeList);*/

                        hotelRoomArrayList.add(hotelRoomArrayList.size(),roomModel);
                        selectRoomAdpter.notifyItemInserted(hotelRoomArrayList.size());
                        recyclerView.scrollToPosition(hotelRoomArrayList.size());
                    }

                }
            });

            /*update*/
            /*Button Apply on click get select room member information*/
            btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean isTrue = true;
                    ArrayList<HotelRoomModel> hotelRoomArrayList=new ArrayList<HotelRoomModel>();
                    hotelRoomArrayList=HotelSelectRoomAdpter.roomList;
                    HotelSharedValues.getInstance().hotelRoomInfoListShared=hotelRoomArrayList;


                    if(hotelRoomArrayList.size() > 0){

                        for(int i=0; i< hotelRoomArrayList.size(); i++){

                            if(hotelRoomArrayList.get(i).getChildAgeModels().size() > 0){
                                for(int chList=0; chList < hotelRoomArrayList.get(i).getChildAgeModels().size(); chList++){

                                    int age= Integer.parseInt(hotelRoomArrayList.get(i).getChildAgeModels().get(chList).getAge());
                                    if(age == 0){
                                        //Toast.makeText(HotelSelectRoomActivity.this,"select child age",Toast.LENGTH_SHORT).show();
                                       isTrue=false;
                                    }
                                    else {
                                        isTrue=true;
                                        //Toast.makeText(HotelSelectRoomActivity.this,"selected child age",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }

                        }

                        if(isTrue){
                            int totAdult=0;
                            int totChild=0;
                            String totMember="";
                            for (int i=0; i < hotelRoomArrayList.size(); i++ ){

                                int index=i+1;
                                String totRoom= String.valueOf(index);
                                totAdult= totAdult + hotelRoomArrayList.get(i).getAdultcount();
                                totChild= totChild + hotelRoomArrayList.get(i).getChildcount();
                                int totMemb=totAdult+totChild;
                                if(totChild > 0){
                                    totMember= String.valueOf(totAdult) +" Adult " +"," +String.valueOf(totChild )+" Child";
                                }
                                else {
                                    totMember= String.valueOf(totAdult) +" Adult " ;
                                }

                                HotelSearchActivity.edTxtRoom.setText(totRoom + " Room");
                                HotelSearchActivity.strRoom=totRoom;
                                HotelSearchActivity.strGuest=totMember;
                                HotelSearchActivity.edTxtGuest.setText(totMember);

                                HotelSharedValues.getInstance().totAdult=totAdult;
                                HotelSharedValues.getInstance().totChild=totChild;
                                HotelSharedValues.getInstance().totRoom= Integer.parseInt(totRoom);
                                HotelSharedValues.getInstance().totMember=totMemb;



                            }
                            finish();
                        }
                        else {
                            //isTrue=true;
                            Toast.makeText(HotelSelectRoomActivity.this,"select child age",Toast.LENGTH_SHORT).show();

                        }



                    }


                }
            });

            /*Image close on click*/
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createDataTableRow1(int childcount)  {
        LayoutInflater mInflator = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflator.inflate(R.layout.utility_hotel_select_child_age_content, null);
        if(flag==1){


            TextView txtchidAge	=(TextView)view.findViewById(R.id.hotel_select_room_content_txt_child);
            SeekBar  seekBar=(SeekBar)view.findViewById(R.id.hotel_select_room_content_seekbar);
            final TextView txtage=(TextView)view.findViewById(R.id.hotel_select_room_content_txt_age);



            txtchidAge.setText("Age of Child " +childcount);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressChangedValue = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressChangedValue = progress;
                    txtage.setText("Year old  "+progressChangedValue);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //txtage.setText(progressChangedValue + "/" + seekBar.getMax());
                    txtage.setText("Year old  "+progressChangedValue);
                }
            });

            //pos = i;



            layoutChildAge.addView(view);
        }
        else {
            layoutChildAge.removeView(view);
        }


    }
    public void createDataTableRow(int childcount)  {

        //int pos;
        int removePos=childcount;

        if(childcount > 0){
            if(flag==1){
                for (int i = 0; i < childcount; i++) {

                    LayoutInflater mInflator = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = mInflator.inflate(R.layout.utility_hotel_select_child_age_content, null);

                    TextView txtchidAge	=(TextView)view.findViewById(R.id.hotel_select_room_content_txt_child);
                    SeekBar  seekBar=(SeekBar)view.findViewById(R.id.hotel_select_room_content_seekbar);
                    final TextView txtage=(TextView)view.findViewById(R.id.hotel_select_room_content_txt_age);

                   int  pos = i+1;

                    txtchidAge.setText("Age of Child " +pos);
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        int progressChangedValue = 0;
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            progressChangedValue = progress;
                            txtage.setText("Year old  "+progressChangedValue);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            //txtage.setText(progressChangedValue + "/" + seekBar.getMax());
                            txtage.setText("Year old  "+progressChangedValue);
                        }
                    });

                     //pos = childcount;


                     if(pos==1){
                         layoutChildAge.addView(view);
                     }
                     else if(pos > 1) {
                         layoutChildAge.addView(view,pos,layoutChildAge.getLayoutParams());
                     }
                }
            }
            else if (flag == 0) {
                layoutChildAge.removeViewAt(removePos);
            }

        }
        else {
            layoutChildAge.removeAllViews();
        }

    }



    private ArrayList<HotelRoomModel> roomInfoList(){

       // errorList = new ArrayList<ErrorMsgModel>();
        ArrayList<HotelRoomModel> list = new ArrayList<>();

        int room=1;
        if(room > 0){
            ArrayList<PessengerInfo> infoAdultList = new ArrayList<>();
            for(int i = 0; i < room; i++){
                HotelRoomModel roomInfo = new HotelRoomModel();
                ErrorMsgModel msgModel=new ErrorMsgModel();
                int index=i;
                roomInfo.setAdult("1");
                roomInfo.setChild("0");
                roomInfo.setAdultcount(1);
                roomInfo.setChildcount(0);
                roomInfo.setRoom(String.valueOf(index+1));
                list.add(roomInfo);
            }
        }

        return list;
    }
}
