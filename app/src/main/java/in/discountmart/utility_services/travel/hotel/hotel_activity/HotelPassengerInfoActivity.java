package in.discountmart.utility_services.travel.hotel.hotel_activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.activity.SettingActivity;
import in.discountmart.utility_services.model.SettingModel;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.sharedpreferences.SettingPreference;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.ErrorMsgModel;
import in.discountmart.utility_services.travel.hotel.hotel_adapter.HotelRoomPassengerInfoAdapter;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelPassengerFooterModel;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelPassengerModel;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelRoomModel;
import in.discountmart.utility_services.travel.hotel.hotel_shared_preferance.HotelSharedValues;
import in.discountmart.utility_services.utilities.DividerItemDecoration;

public class HotelPassengerInfoActivity extends AppCompatActivity {

    Toolbar fToolbar;
    TextView txtFromCity;
    TextView txtTitle;
    TextView txtToCity;
    TextView txtDepartDate;
    TextView txtAdult;
    TextView txtChild;
    TextView txtInfants;
    TextView txtTotalFlight;
    TextView txtHotelName;
    TextView txtHotelCity;
    TextView txtCheckInDate;
    TextView txtCheckOutDate;
    TextView txtTotalRoom;
    TextView txtRoomType;
    TextView txtRoom;
    TextView txtTotalGuest;
    AppCompatRatingBar ratingBar;


    TextView txtTotalFareAmout;
    LinearLayout layoutFareAmount;
    RecyclerView recyclerView;

    public static Button btnContinue;


    HotelRoomPassengerInfoAdapter infoAdapter2;

    ArrayList<HotelPassengerModel> pessengerInfoArrayList;
    ArrayList<HotelPassengerModel> pessengerInfoArrayListTemp;
    ArrayList<HotelPassengerFooterModel> footerInfoList;
    ArrayList<ErrorMsgModel> errorList;



    String strCity="";
    String strToCity="";
    String strAdult="";
    String strChild="";
    String strInfant="";
    String strCheckInDate="";
    String strCheckOurDate="";
    String strHotelCode="";
    String strTraceId="";
    String strResultIndex="";
    String strHotelName="";
    String strRoomType="";

    String strHotelCity="";
    String strRoom="";
    String strGuest="";
    String strCheckOutDate="";
    String strHotelRating="";
    ProgressDialog progressDialog;

    int totHeader=1;
    int totAdult=0;
    int totChild=0;
    int totRooms=0;
    int totPessenger=0;
    int totOther=1;

    String mobile="";
    String email="";
    String contact_no="";
    String comp_address="";
    String comp_name="";
    String gst_email="";
    String gst_no="";
    boolean loadFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_hotel_passenger_info);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {

            fToolbar=(Toolbar)findViewById(R.id.room_passenger_info_activity_toolbar);
            setSupportActionBar(fToolbar);

            // toolbar fancy stuff
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            loadFirst=true;

            txtHotelName=(TextView)findViewById(R.id.room_passenger_info_actvity_txt_hotelname);
            txtHotelCity=(TextView)findViewById(R.id.room_passenger_info_actvity_txt_hotel_city);
            txtCheckInDate=(TextView)findViewById(R.id.room_passenger_info_actvity_txt_checkIn_date);
            txtCheckOutDate=(TextView)findViewById(R.id.room_passenger_info_actvity_txt_checkOut_date);
            txtTitle=(TextView)findViewById(R.id.room_passenger_info_activity_toolbar_txt_title);
            txtTotalRoom=(TextView)findViewById(R.id.room_passenger_info_activity_txt_total_room);
            txtRoomType=(TextView)findViewById(R.id.room_passenger_info_actvity_txt_room_type);
            txtRoom=(TextView)findViewById(R.id.room_passenger_info_actvity_txt_hotel_room);
            txtTotalGuest=(TextView)findViewById(R.id.room_passenger_info_actvity_txt_hotel_guest);
            ratingBar=(AppCompatRatingBar)findViewById(R.id.room_passenger_info_actvity_rating_bar);
            btnContinue=(Button)findViewById(R.id.room_passenger_info_activity_btn_continue);
            layoutFareAmount=(LinearLayout)findViewById(R.id.room_passenger_info_activity_layout_fareamout);
            txtTotalFareAmout=(TextView)findViewById(R.id.room_passenger_info_activity_txt_total_amnt);



                totAdult = HotelSharedValues.getInstance().totAdult;
                totChild = HotelSharedValues.getInstance().totChild;
                totRooms = HotelSharedValues.getInstance().totRoom;
                totPessenger = totAdult + totChild ;

                /*Gt value from Hotel Shared Preference and set on text*/
                strCheckInDate= HotelSharedValues.getInstance().chkInDate;
                strCheckOutDate= HotelSharedValues.getInstance().chkOutDate;
                strHotelName= HotelSharedValues.getInstance().hotelName;
                strHotelCity= HotelSharedValues.getInstance().city;
                strHotelRating= HotelSharedValues.getInstance().hotelRating;
                strRoom= String.valueOf(HotelSharedValues.getInstance().totRoom);
                strGuest= String.valueOf(HotelSharedValues.getInstance().totMember);
                strRoomType=HotelSharedValues.getInstance().roomName;
                strHotelCode=HotelSharedValues.getInstance().hotelCode;
                strResultIndex=HotelSharedValues.getInstance().resultIndex;
                strTraceId=HotelSharedValues.getInstance().traceID;

                /*Set Vlaue on text*/
                txtHotelCity.setText(strHotelCity);
                txtTotalGuest.setText("Guest "+strGuest);
                txtRoom.setText("Room "+ strRoom);
                txtRoomType.setText(strRoomType);
                txtCheckInDate.setText(strCheckInDate);
                txtCheckOutDate.setText(strCheckOutDate);
                if(strHotelRating.contentEquals("0") || strHotelRating.contentEquals(""))
                    ratingBar.setRating(0);
                else
                    ratingBar.setRating(Float.parseFloat(strHotelRating));
                txtHotelName.setText(strHotelName);
                txtTitle.setText("Member Detail");

            /*Update 2/11/2019*/
            /*Get Value from shared preference*/
            String mobile1="";
            String mobile2="";
            String email_1="";
            String email_2="";
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(this).getLoggedinUser();

            mobile1=loginResponse.getMobileNo();
            email_1=loginResponse.getEmailId();
            SettingModel settingModel=new SettingModel();
            settingModel=new SettingPreference(this).getSettingItem();
            mobile2=settingModel.getMobile();
            email_2=settingModel.getEmail();

            /*For mobile no*/
            if(!mobile1.equals("") && !mobile2.equals("")){
                if(mobile1.equals(mobile2))
                    mobile=mobile1;
                else
                    mobile=mobile1;
            }
            else if(!mobile1.equals("") && mobile2.equals("")){
                mobile=mobile1;
            }
            else if(mobile1.equals("") && !mobile2.equals("")){
                mobile=mobile2;
            }
            else
                mobile="";

            /*For Email id*/
            if(!email_1.equals("") && !email_2.equals("")){
                if(email_1.equals(email_2))
                    email=email_1;
                else
                    email=email_1;
            }
            else if(!email_1.equals("") && email_2.equals("")){
                email=email_1;
            }
            else if(email_1.equals("") && !email_2.equals("")){
                email=email_2;
            }
            else
                email="";

            gst_no=settingModel.getGstno();
            gst_email=settingModel.getGst_mail();
            comp_address=settingModel.getComp_address();
            comp_name=settingModel.getComp_name();
            contact_no=settingModel.getContact_no();

                if (HotelSharedValues.getInstance().perRoomAmount > 0) {

                    String totalamount = String.valueOf(HotelSharedValues.getInstance().perRoomAmount);
                    int amount= (int) HotelSharedValues.getInstance().perRoomAmount;
                    int totAmount=amount*totRooms;
                    HotelSharedValues.getInstance().totAmount=totAmount;
                    //txtTotalFareAmout.setText(this.getResources().getString(R.string.rs_symbol)+""+selectFlightarrayList.get(0).getGrossAmount());
                    txtTotalFareAmout.setText(this.getResources().getString(R.string.rs_symbol) + " " + totAmount);
                    txtTotalRoom.setText("Base Fare for " + String.valueOf(totPessenger) + " Rooms");
                }


                //int totalFlight=flightSelectList.size();
                //txtTotalFlight.setText("Total Flight:- "+String.valueOf(totalFlight));
                recyclerView = (RecyclerView) findViewById(R.id.room_passenger_info_activity_recycler);

                pessengerInfoArrayList = new ArrayList<HotelPassengerModel>();
                footerInfoList = new ArrayList<HotelPassengerFooterModel>();

                /*Call method and assign value in list*/
                //pessengerInfoArrayList = travelerInfoList();
                //footerInfoList=footerList();
                //infoAdapter = new PassengerInfoAdapter(this, pessengerInfoArrayList,footerInfoList,FlightPassengerInfoActivity.this);
                //infoAdapter2 = new HotelRoomPassengerInfoAdapter(this, pessengerInfoArrayList, errorList, HotelPassengerInfoActivity.this);
                // infoAdapter.setData(this,pessengerInfoArrayList);
                // infoAdapter.setDataFooter(this,footerInfoList);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(HotelPassengerInfoActivity.this, DividerItemDecoration.VERTICAL_LIST, 0));
                //recyclerView.setAdapter(infoAdapter2);

                // Button continue click listner
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        boolean fillalldone=false;
                        boolean fillallother = false;
                        int fillall=0;
                        int i =0;

                        ArrayList<HotelPassengerModel> pessengerInfoArrayList1=new ArrayList<HotelPassengerModel>();

                        pessengerInfoArrayList1=HotelRoomPassengerInfoAdapter.pessengerInfosList;

                        for ( i=0; i < HotelRoomPassengerInfoAdapter.pessengerInfosList.size();i++ ){

                            if(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getType()==1){

                                if(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getName().equals("")){

                                    errorList.get(i).setErrorMsg("Enter Name");
                                    infoAdapter2.notifyItemChanged(i,errorList);
                                    recyclerView.scrollToPosition(i);
                                    fillalldone=false;

                                    break;
                                }
                                else if(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getSurName().equals("")){

                                    errorList.get(i).setErrorMsg("Enter Last Name");
                                    infoAdapter2.notifyItemChanged(i,errorList);
                                    recyclerView.scrollToPosition(i);
                                    fillalldone=false;

                                    break;
                                }

                                else if(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getDob().equals("")){

                                    errorList.get(i).setErrorMsg("Date Of Birth");
                                    infoAdapter2.notifyItemChanged(i,errorList);
                                    recyclerView.scrollToPosition(i);
                                    fillalldone=false;
                                    //fillallother=false;
                                    break;
                                }
                                else {
                                    errorList.get(i).setErrorMsg("");
                                    infoAdapter2.notifyItemChanged(i,errorList);

                                    fillalldone=true;
                                    fillallother=false;
                                }
                            }
                            else if(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getType()==2){

                                if(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getMobile().equals("")){
                                    //HotelRoomPassengerInfoAdapter.FooterHolder.textErrorFooter.setText("Enter Mobile No.");
                                    errorList.get(i).setErrorMsg("Enter mobile no.");
                                    infoAdapter2.notifyItemChanged(i,errorList);
                                    break;
                                }

                                else if (HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getMobile().length() > 0
                                        && HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getMobile().length() < 10) {
                                    //HotelRoomPassengerInfoAdapter.FooterHolder.textErrorFooter.setText("enter complete mobile no.");
                                    errorList.get(i).setErrorMsg("Enter complete mobile no.");
                                    infoAdapter2.notifyItemChanged(i,errorList);
                                    break;
                                }
                                else if(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getEmail().equals("")){
                                    //HotelRoomPassengerInfoAdapter.FooterHolder.textErrorFooter.setText("Enter Email Id");
                                    errorList.get(i).setErrorMsg("Enter Email Id");
                                    infoAdapter2.notifyItemChanged(i,errorList);

                                    break;
                                }
                                else if(!SettingActivity.isValidEmail(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getEmail())
                                        && HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getEmail().length() > 0){
                                    //HotelRoomPassengerInfoAdapter.FooterHolder.textErrorFooter.setText("enter valid email id.");
                                    //HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).setErrorMsg("enter valid email id.");
                                    errorList.get(i).setErrorMsg("Enter valid email id.");
                                    infoAdapter2.notifyItemChanged(i,errorList);
                                    break;
                                }
                                else {
                                    if(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).isSelect()){

                                        if(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getGstno().equals("")){
                                            HotelRoomPassengerInfoAdapter.FooterHolder.textErrorGstNo.setText("Enter GST No.");
                                            break;
                                        }
                                        else if(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getCompanyName().equals("")){
                                            HotelRoomPassengerInfoAdapter.FooterHolder.textErrorCompName.setText("Enter Company Name");
                                            //recyclerView.scrollToPosition(i);
                                            break;
                                        }
                                        else if(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getCompanyAddress().equals("")){
                                            HotelRoomPassengerInfoAdapter.FooterHolder.textErrorCompAdd.setText("Enter Company Address");

                                            break;
                                        }
                                        else if(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getContactno().equals("")){
                                            HotelRoomPassengerInfoAdapter.FooterHolder.textErrorContact.setText("Enter Contact No.");

                                            break;
                                        }
                                        else  if(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getGstemail().equals("")){
                                            HotelRoomPassengerInfoAdapter.FooterHolder.textErrorGstMail.setText("Enter GST Mail Id");

                                            break;
                                        }
                                        else {
                                            errorList.get(i).setErrorMsg("");
                                            fillallother=true;

                                            SettingModel settingModel1=new SettingModel();
                                            settingModel1.setMobile(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getMobile());
                                            settingModel1.setEmail(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getEmail());
                                            settingModel1.setAccountno("");
                                            settingModel1.setIfsc("");
                                            settingModel1.setPanno("");
                                            settingModel1.setGstno(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getGstno());
                                            settingModel1.setGst_mail(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getGstemail());
                                            settingModel1.setComp_name(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getCompanyName());
                                            settingModel1.setContact_no(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getContactno());
                                            settingModel1.setComp_address(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getCompanyAddress());
                                            new SettingPreference(HotelPassengerInfoActivity.this).setSettinginfo(settingModel1);

                                        }

                                    }
                                    else {
                                        errorList.get(i).setErrorMsg("");
                                        pessengerInfoArrayList1=HotelRoomPassengerInfoAdapter.pessengerInfosList;

                                        SettingModel settingModel1=new SettingModel();
                                        settingModel1.setMobile(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getMobile());
                                        settingModel1.setEmail(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getEmail());
                                        settingModel1.setAccountno("");
                                        settingModel1.setIfsc("");
                                        settingModel1.setPanno("");
                                        settingModel1.setGstno(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getGstno());
                                        settingModel1.setGst_mail(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getGstemail());
                                        settingModel1.setComp_name(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getCompanyName());
                                        settingModel1.setContact_no(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getContactno());
                                        settingModel1.setComp_address(HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).getCompanyAddress());
                                        new SettingPreference(HotelPassengerInfoActivity.this).setSettinginfo(settingModel1);


                                        fillallother=true;
                                    }
                                }

                            }

                        }

                        if(fillalldone && fillallother){

                            Intent intent=new Intent(HotelPassengerInfoActivity.this, HotelBookFinalDetailActivity.class);

                            Bundle bundle1=new Bundle();
                            bundle1.putSerializable("PassengerInfo",pessengerInfoArrayList1);
                            intent.putExtras(bundle1);
                            pessengerInfoArrayListTemp=new ArrayList<HotelPassengerModel>();
                            pessengerInfoArrayListTemp.addAll(pessengerInfoArrayList1);
                            HotelSharedValues.getInstance().hotelPassengerModelArrayList.clear();
                            HotelSharedValues.getInstance().hotelPassengerModelArrayList.addAll(pessengerInfoArrayList1);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                            loadFirst=false;
                        }
                        else {
                            //infoAdapter2.setData(FlightPassengerInfoActivity.this,errorList);
                            infoAdapter2.notifyItemChanged(i,errorList);
                            //infoAdapter2.notifyDataSetChanged();
                            Toast.makeText(HotelPassengerInfoActivity.this,"Something missing field please fill",Toast.LENGTH_SHORT).show();
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


    private ArrayList<HotelPassengerModel> travelerInfoList(){

        errorList = new ArrayList<ErrorMsgModel>();
        ArrayList<HotelPassengerModel> list = new ArrayList<>();
        HotelRoomModel hotelRoomModel=new HotelRoomModel();
        /*Get Hotel Room Model Value from Hotel Shared  and assign in Arraylist*/
        ArrayList<HotelRoomModel> hotelRoomArrayList=new ArrayList<HotelRoomModel>();
        hotelRoomArrayList= HotelSharedValues.getInstance().hotelRoomInfoListShared;
        ArrayList<Integer> childAgeList= new ArrayList<Integer>();


        for (int h=0;h < hotelRoomArrayList.size(); h++){

            if(hotelRoomArrayList.get(h).getChildcount() > 0){
                for(int j=0; j < hotelRoomArrayList.get(h).getChildAgeModels().size(); j++){

                    int age= Integer.parseInt(hotelRoomArrayList.get(h).getChildAgeModels().get(j).getAge());
                    childAgeList.add(age);
                }
            }
        }

        if(totAdult > 0){
            ArrayList<HotelPassengerModel> infoAdultList = new ArrayList<>();
            for(int i = 0; i < totAdult; i++){
                HotelPassengerModel pessengerInfo1 = new HotelPassengerModel();
                ErrorMsgModel msgModel=new ErrorMsgModel();
                int index=i;
                pessengerInfo1.setName("");
                pessengerInfo1.setSurName("");
                //pessengerInfo.setMobile("");
                pessengerInfo1.setAge("");
                pessengerInfo1.setErrorMsg("");
                pessengerInfo1.setNametitle("");
                // pessengerInfo1.setChecked(true);
                pessengerInfo1.setType(1);
                pessengerInfo1.setDob("");
                pessengerInfo1.setGender("");
                pessengerInfo1.setPessengerType("A");
                pessengerInfo1.setAdult(totAdult);
                pessengerInfo1.setCountAdults(index+1);
                msgModel.setErrorMsg("");
                errorList.add(msgModel);
                list.add(pessengerInfo1);
            }
        }

        if(totChild > 0){

            for(int i = 0; i < totChild; i++){
                HotelPassengerModel pessengerInfo2 = new HotelPassengerModel();
                ErrorMsgModel msgModel2=new ErrorMsgModel();

                int indexchild=i;
                pessengerInfo2.setName("");
                pessengerInfo2.setSurName("");
                //pessengerInfo.setMobile("");
                if(childAgeList.size() > 0){
                    pessengerInfo2.setAge(childAgeList.get(i).toString());
                    pessengerInfo2.setDob(childAgeList.get(i).toString());
                }
                else {
                    pessengerInfo2.setAge("4");
                    pessengerInfo2.setDob("4");
                }

                pessengerInfo2.setErrorMsg("");
                pessengerInfo2.setType(1);
                pessengerInfo2.setNametitle("");
                //pessengerInfo2.setChecked(true);
                pessengerInfo2.setGender("");
                pessengerInfo2.setPessengerType("C");
                pessengerInfo2.setChild(totChild);
                //pessengerInfo2.setOther(0);
                pessengerInfo2.setCountChilds(indexchild+1);

                msgModel2.setErrorMsg("");
                errorList.add(msgModel2);
                list.add(pessengerInfo2);
            }
        }


        if(totOther > 0){

            for(int i = 0; i < totOther; i++){
                HotelPassengerModel pessengerInfo4 = new HotelPassengerModel();
                ErrorMsgModel msgModel4=new ErrorMsgModel();
                int indexOther=i;
                pessengerInfo4.setMobile(new LoginPreferences_Utility(this).getLoggedinUser().getMobileNo());
                pessengerInfo4.setEmail(new LoginPreferences_Utility(this).getLoggedinUser().getEmailId());
                pessengerInfo4.setGstno("");
                pessengerInfo4.setGstemail("");
                pessengerInfo4.setCompanyName("");
                pessengerInfo4.setPessengerType("O");
                pessengerInfo4.setType(2);
                pessengerInfo4.setCompanyAddress("");
                pessengerInfo4.setErrorMsg("");
                pessengerInfo4.setContactno("");
                pessengerInfo4.setOther(totOther);
                pessengerInfo4.setCountOther(indexOther+1);
                list.add(pessengerInfo4);

                msgModel4.setErrorMsg("");
                errorList.add(msgModel4);
            }
        }
        return list;
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
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }

    @Override
    public void onResume(){

        try {
            if(loadFirst){
                /*Call method and assign value in list*/
                pessengerInfoArrayList=new ArrayList<HotelPassengerModel>();
                pessengerInfoArrayList = travelerInfoList();
                //footerInfoList=footerList();
                //infoAdapter = new PassengerInfoAdapter(this, pessengerInfoArrayList,footerInfoList,FlightPassengerInfoActivity.this);
                infoAdapter2 = new HotelRoomPassengerInfoAdapter(this, pessengerInfoArrayList, errorList, HotelPassengerInfoActivity.this);
                recyclerView.setAdapter(infoAdapter2);
                infoAdapter2.notifyDataSetChanged();
            }
            else {
                //pessengerInfoArrayListTemp=new ArrayList<PessengerInfo>();
                // pessengerInfoArrayListTemp=  FlightSharedValues.getInstance().pessengerInfoArrayList;

                if(pessengerInfoArrayListTemp.size() == HotelSharedValues.getInstance().hotelPassengerModelArrayList.size()){
                    infoAdapter2 = new HotelRoomPassengerInfoAdapter(this, pessengerInfoArrayListTemp, errorList, HotelPassengerInfoActivity.this);
                    recyclerView.setAdapter(infoAdapter2);
                    infoAdapter2.notifyDataSetChanged();
                }
                else {
                    infoAdapter2 = new HotelRoomPassengerInfoAdapter(this, pessengerInfoArrayListTemp, errorList, HotelPassengerInfoActivity.this);
                    recyclerView.setAdapter(infoAdapter2);
                    infoAdapter2.notifyDataSetChanged();
                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }

        super.onResume();
    }
}
