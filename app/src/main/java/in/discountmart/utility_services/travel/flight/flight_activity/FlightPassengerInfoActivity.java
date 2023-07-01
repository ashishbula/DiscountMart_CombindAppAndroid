package in.discountmart.utility_services.travel.flight.flight_activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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

import in.discountmart.R;
import in.discountmart.utility_services.activity.MainActivity_utility;
import in.discountmart.utility_services.model.SettingModel;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.sharedpreferences.SettingPreference;
import in.discountmart.utility_services.travel.flight.adapter.FlightSelectAdapter;
import in.discountmart.utility_services.travel.flight.adapter.PassengerInfoAdapter;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.ErrorMsgModel;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PassengerFooterInfo;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PessengerInfo;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightSearchResponse;
import in.discountmart.utility_services.travel.flight.flight_sharedpreference.FlightSharedValues;
import in.discountmart.utility_services.utilities.DividerItemDecoration;
import in.discountmart.utility_services.utilities.Utilities;

public class FlightPassengerInfoActivity extends AppCompatActivity {
    Toolbar fToolbar;
    TextView txtFromCity;
    TextView txtToCity;
    TextView txtDepartDate;
    TextView txtAdult;
    TextView txtChild;
    TextView txtInfants;
    TextView txtTotalFlight;
    ImageView imgHome;
    ImageView imgEdit;

    TextView txtTotalPassenger;
    TextView txtTotalFareAmout;
    LinearLayout layoutFareAmount;
    LinearLayout layoutEdit;

    RecyclerView recyclerView;

    public static Button btnContinue;

    ArrayList<FlightSearchResponse> flightSelectList;
    ArrayList<FlightSearchResponse.FlightSearchSegment> flightSearchSegmentsList;
    FlightSelectAdapter adapter;
    PassengerInfoAdapter infoAdapter2;

    ArrayList<PessengerInfo> pessengerInfoArrayList;
    ArrayList<PessengerInfo> pessengerInfoArrayListTemp;
    ArrayList<PassengerFooterInfo> footerInfoList;
    ArrayList<ErrorMsgModel> errorList;

    ArrayList<FlightSearchResponse> selectFlightarrayList;
    FlightSearchResponse[] flightSearchResponse;

    String strFromCity="";
    String strToCity="";
    String strAdult="";
    String strChild="";
    String strInfant="";
    String strDepartDate="";
    String mobile="";
    String email="";
    String contact_no="";
    String comp_address="";
    String comp_no="";
    String gst_email="";
    String gst_no="";

    int totHeader=1;
    int totAdult=0;
    int totChild=0;
    int totInfants=0;
    int totPessenger=0;
    int totOther=1;
    boolean loadFirst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_flight_passenger_info);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {

            fToolbar=(Toolbar)findViewById(R.id.passenger_info_activity_toolbar);
            setSupportActionBar(fToolbar);

            // toolbar fancy stuff
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            loadFirst=true;


            txtFromCity=(TextView)findViewById(R.id.passenger_info_activity_toolbar_txtfromcity);
            txtToCity=(TextView)findViewById(R.id.passenger_info_activity_toolbar_txttocity);
            txtDepartDate=(TextView)findViewById(R.id.passenger_info_activity_toolbar_txt_dDate);
            txtAdult=(TextView)findViewById(R.id.passenger_info_activity_toolbar_txt_adult);
            txtChild=(TextView)findViewById(R.id.passenger_info_activity_toolbar_txt_child);
            txtInfants=(TextView)findViewById(R.id.passenger_info_activity_toolbar_txt_infants);
            btnContinue=(Button)findViewById(R.id.passenger_info_activity_btn_continue);
            layoutFareAmount=(LinearLayout)findViewById(R.id.passenger_info_activity_layout_fareamout);
            layoutEdit=(LinearLayout)findViewById(R.id.passenger_info_activity_toolbar_layout_edit);
            txtTotalFareAmout=(TextView)findViewById(R.id.passenger_info_activity_txt_total_amnt);
            txtTotalPassenger=(TextView)findViewById(R.id.passenger_info_activity_txt_total_travelor);
            imgHome = (ImageView) findViewById(R.id.passenger_info_activity_toolbar_img_home);
            imgEdit = (ImageView) findViewById(R.id.passenger_info_activity_toolbar_img_edit);

            final Bundle bundle=getIntent().getExtras();
            if(bundle != null){


                strFromCity=bundle.getString("FromCity");
                strToCity=bundle.getString("ToCity");
                strAdult=bundle.getString("Adult");
                strChild=bundle.getString("Child");
                strInfant=bundle.getString("Infants");
                strDepartDate=bundle.getString("DepartDate");

                if(strChild.contentEquals("0")){
                    txtChild.setText("");
                }
                else {
                    txtChild.setText("Child "+strChild);
                }
                if(strAdult.contentEquals("0")){
                    txtAdult.setText("");
                }
                else {
                    txtAdult.setText("Adult "+strAdult+" - ");
                }
                if(strInfant.contentEquals("0")){
                    txtInfants.setText("");
                }
                else {
                    txtInfants.setText(" - "+"Inf "+strInfant);
                }

                txtFromCity.setText(strFromCity);
                txtToCity.setText(strToCity);

                txtDepartDate.setText(strDepartDate+" * ");

                totAdult= Integer.parseInt(strAdult);
                totChild=Integer.parseInt(strChild);
                totInfants=Integer.parseInt(strInfant);
                totPessenger=totAdult+totChild+totInfants;


                totAdult= Integer.parseInt(strAdult);
                totChild=Integer.parseInt(strChild);
                totInfants=Integer.parseInt(strInfant);
                totPessenger=totAdult+totChild+totInfants;

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
               comp_no=settingModel.getComp_name();
               contact_no=settingModel.getContact_no();


            }

            if(FlightSharedValues.getInstance().totPaidAmount > 0){
                String totalamount= String.valueOf(FlightSharedValues.getInstance().totPaidAmount);
                //txtTotalFareAmout.setText(this.getResources().getString(R.string.rs_symbol)+""+selectFlightarrayList.get(0).getGrossAmount());
                txtTotalFareAmout.setText(this.getResources().getString(R.string.rs_symbol)+" "+totalamount);
                txtTotalPassenger.setText("Base Fare for " +String.valueOf(totPessenger)+" Travelers");
            }

            recyclerView=(RecyclerView)findViewById(R.id.passenger_info_activity_recycler);

             pessengerInfoArrayList=new ArrayList<PessengerInfo>();
            //pessengerInfoArrayListTemp=new ArrayList<PessengerInfo>();
            //footerInfoList=new ArrayList<PassengerFooterInfo>();

            /*Call method and assign value in list*/
            // pessengerInfoArrayList = travelerInfoList();
            //infoAdapter2 = new PassengerInfoAdapter(this,pessengerInfoArrayList,errorList,FlightPassengerInfoActivity.this);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(FlightPassengerInfoActivity.this, DividerItemDecoration.VERTICAL_LIST,0));
           // recyclerView.setAdapter(infoAdapter2);


            // Button continue click listner
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        boolean fillalldone=false;
                        boolean fillallother = false;
                        int fillall=0;
                        int i =0;


                        ArrayList<PessengerInfo> pessengerInfoArrayList1=new ArrayList<PessengerInfo>();

                        pessengerInfoArrayList1= PassengerInfoAdapter.pessengerInfosList;

                        //infoAdapter.notifyDataSetChanged();

                        for (i=0; i < PassengerInfoAdapter.pessengerInfosList.size(); i++ ){
                            //PessengerInfo pessengerInfo=new PessengerInfo();

                            if(PassengerInfoAdapter.pessengerInfosList.get(i).getType()==1){


                                if(PassengerInfoAdapter.pessengerInfosList.get(i).getName().equals("")){

                                    //pessengerInfoArrayList1.get(i).setErrorMsg("Enter Name");
                                    errorList.get(i).setErrorMsg("Enter Name");
                                    infoAdapter2.notifyItemChanged(i,errorList);

                                    recyclerView.scrollToPosition(i);
                                    fillalldone=false;

                                    break;
                                }
                                else if(PassengerInfoAdapter.pessengerInfosList.get(i).getSurName().equals("")){

                                    errorList.get(i).setErrorMsg("Enter Last Name");
                                    infoAdapter2.notifyItemChanged(i,errorList);
                                    recyclerView.scrollToPosition(i);

                                    fillalldone=false;

                                    break;
                                }

                                else if(PassengerInfoAdapter.pessengerInfosList.get(i).getDob().equals("")){

                                    errorList.get(i).setErrorMsg("Please enter age");
                                    infoAdapter2.notifyItemChanged(i,errorList);
                                    recyclerView.scrollToPosition(i);

                                    fillalldone=false;

                                    break;
                                }
                                else {
                                    errorList.get(i).setErrorMsg("");
                                    infoAdapter2.notifyItemChanged(i,errorList);

                                    fillalldone=true;
                                    fillallother=false;
                                }

                            }

                            else if(PassengerInfoAdapter.pessengerInfosList.get(i).getType()==2){

                                if(PassengerInfoAdapter.pessengerInfosList.get(i).getMobile().equals("")){
                                    //PassengerInfoAdapter.FooterHolder.textErrorFooter.setText("Enter Mobile No.");

                                    errorList.get(i).setErrorMsg("Enter mobile no.");
                                    infoAdapter2.notifyItemChanged(i,errorList);
                                    break;
                                }
                                else if (PassengerInfoAdapter.pessengerInfosList.get(i).getMobile().length() > 0
                                        && PassengerInfoAdapter.pessengerInfosList.get(i).getMobile().length() < 10) {
                                    //HotelRoomPassengerInfoAdapter.FooterHolder.textErrorFooter.setText("enter complete mobile no.");
                                    errorList.get(i).setErrorMsg("Enter complete mobile no.");
                                    infoAdapter2.notifyItemChanged(i,errorList);
                                    break;
                                }
                                else  if(PassengerInfoAdapter.pessengerInfosList.get(i).getEmail().equals("")){
                                    //PassengerInfoAdapter.FooterHolder.textErrorFooter.setText("Enter Email Id");
                                    errorList.get(i).setErrorMsg("Enter Email Id");
                                    infoAdapter2.notifyItemChanged(i,errorList);
                                    break;
                                }
                                else if(!Utilities.isValidEmail(PassengerInfoAdapter.pessengerInfosList.get(i).getEmail())
                                        && PassengerInfoAdapter.pessengerInfosList.get(i).getEmail().length() > 0){
                                    //HotelRoomPassengerInfoAdapter.FooterHolder.textErrorFooter.setText("enter valid email id.");
                                    //HotelRoomPassengerInfoAdapter.pessengerInfosList.get(i).setErrorMsg("enter valid email id.");
                                    errorList.get(i).setErrorMsg("Enter valid email id.");
                                    infoAdapter2.notifyItemChanged(i,errorList);
                                    break;
                                }
                                else {
                                    if(PassengerInfoAdapter.pessengerInfosList.get(i).isSelect()){

                                        if(PassengerInfoAdapter.pessengerInfosList.get(i).getGstno().equals("")){
                                            PassengerInfoAdapter.FooterHolder.textErrorGstNo.setText("Enter GST No.");
                                            // recyclerView.scrollToPosition(i);
                                            break;
                                        }

                                        else  if(PassengerInfoAdapter.pessengerInfosList.get(i).getCompanyName().equals("")){
                                            PassengerInfoAdapter.FooterHolder.textErrorCompName.setText("Enter Company Name");
                                            //recyclerView.scrollToPosition(i);
                                            break;
                                        }
                                        else  if(PassengerInfoAdapter.pessengerInfosList.get(i).getCompanyAddress().equals("")){
                                            PassengerInfoAdapter.FooterHolder.textErrorCompAdd.setText("Enter Company Address");
                                            // recyclerView.scrollToPosition(i);
                                            break;
                                        }
                                        else  if(PassengerInfoAdapter.pessengerInfosList.get(i).getContactno().equals("")){
                                            PassengerInfoAdapter.FooterHolder.textErrorContact.setText("Enter Contact No.");
                                            //recyclerView.scrollToPosition(i);
                                            break;
                                        }
                                        else  if(PassengerInfoAdapter.pessengerInfosList.get(i).getGstemail().equals("")){
                                            PassengerInfoAdapter.FooterHolder.textErrorGstMail.setText("Enter GST Mail Id");
                                            //recyclerView.scrollToPosition(i);
                                            break;
                                        }
                                        else {
                                            errorList.get(i).setErrorMsg("");
                                            /*Save Additional data in SettingPreference*/
                                            SettingModel settingModel1=new SettingModel();
                                            settingModel1.setMobile(PassengerInfoAdapter.pessengerInfosList.get(i).getMobile());
                                            settingModel1.setEmail(PassengerInfoAdapter.pessengerInfosList.get(i).getEmail());
                                            settingModel1.setAccountno("");
                                            settingModel1.setIfsc("");
                                            settingModel1.setPanno("");
                                            settingModel1.setGstno(PassengerInfoAdapter.pessengerInfosList.get(i).getGstno());
                                            settingModel1.setGst_mail(PassengerInfoAdapter.pessengerInfosList.get(i).getGstemail());
                                            settingModel1.setComp_name(PassengerInfoAdapter.pessengerInfosList.get(i).getCompanyName());
                                            settingModel1.setContact_no(PassengerInfoAdapter.pessengerInfosList.get(i).getContactno());
                                            settingModel1.setComp_address(PassengerInfoAdapter.pessengerInfosList.get(i).getCompanyAddress());
                                            new SettingPreference(FlightPassengerInfoActivity.this).setSettinginfo(settingModel1);

                                            fillallother=true;
                                        }

                                    }
                                    else {
                                        errorList.get(i).setErrorMsg("");
                                        pessengerInfoArrayList1= PassengerInfoAdapter.pessengerInfosList;

                                        /*Save Additional data in SettingPreference*/
                                        SettingModel settingModel1=new SettingModel();
                                        settingModel1.setMobile(PassengerInfoAdapter.pessengerInfosList.get(i).getMobile());
                                        settingModel1.setEmail(PassengerInfoAdapter.pessengerInfosList.get(i).getEmail());
                                        settingModel1.setAccountno("");
                                        settingModel1.setIfsc("");
                                        settingModel1.setPanno("");
                                        settingModel1.setGstno(PassengerInfoAdapter.pessengerInfosList.get(i).getGstno());
                                        settingModel1.setGst_mail(PassengerInfoAdapter.pessengerInfosList.get(i).getGstemail());
                                        settingModel1.setComp_name(PassengerInfoAdapter.pessengerInfosList.get(i).getCompanyName());
                                        settingModel1.setContact_no(PassengerInfoAdapter.pessengerInfosList.get(i).getContactno());
                                        settingModel1.setComp_address(PassengerInfoAdapter.pessengerInfosList.get(i).getCompanyAddress());
                                        new SettingPreference(FlightPassengerInfoActivity.this).setSettinginfo(settingModel1);

                                        fillallother=true;
                                    }


                                }

                            }
                            else {

                            }

                        }
                        // infoAdapter2.notifyDataSetChanged();


                        if(fillalldone && fillallother){

                            View view = FlightPassengerInfoActivity.this.getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(),
                                        0);
                            }

                            Intent intent=new Intent(FlightPassengerInfoActivity.this,FlightBookFinalDetailActivity.class);

                            Bundle bundle1=new Bundle();
                            bundle1.putSerializable("PassengerInfo",pessengerInfoArrayList1);
                            intent.putExtras(bundle1);
                            pessengerInfoArrayListTemp=new ArrayList<PessengerInfo>();
                            pessengerInfoArrayListTemp.addAll(pessengerInfoArrayList1);

                           /* if(FlightSharedValues.getInstance().pessengerInfoArrayList.size() > 0){
                                FlightSharedValues.getInstance().pessengerInfoArrayList.clear();
                            }*/
                            FlightSharedValues.getInstance().pessengerInfoArrayList.clear();
                            FlightSharedValues.getInstance().pessengerInfoArrayList.addAll(pessengerInfoArrayList1);

                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                            loadFirst=false;
                        }
                        else {

                            infoAdapter2.notifyItemChanged(i,errorList);
                            //infoAdapter2.notifyDataSetChanged();
                            Toast.makeText(FlightPassengerInfoActivity.this,"Something missing field please fill",Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

            /*Layout for Base Fare Amount*/
            layoutFareAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1=new Intent(FlightPassengerInfoActivity.this, FlightBookFareDetailActivity.class);
                    intent1.putExtra("FlightType","Ownward");
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });

            /*Tool bar top home icon on click go to HomeMainActivity_utility*/
            imgHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //loadFirst=false;
                    FlightSharedValues.getInstance().pessengerInfoArrayList.clear();
                    Intent intenthome=new Intent(FlightPassengerInfoActivity.this, MainActivity_utility.class);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intenthome);
                    finish();
                }
            });
            /*Tool bar top edit icon on click go to FlightSearchActivity*/
            layoutEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // loadFirst=false;
                    FlightSharedValues.getInstance().pessengerInfoArrayList.clear();
                    Intent intenthome=new Intent(FlightPassengerInfoActivity.this, FlightSearchActivity.class);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intenthome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intenthome.putExtra("Edit","true");
                    startActivity(intenthome);
                    finish();
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ArrayList<PessengerInfo> travelerInfoList(){

         errorList = new ArrayList<ErrorMsgModel>();
        ArrayList<PessengerInfo> list = new ArrayList<>();
        LoginResponse loginResponse=new LoginResponse();
        loginResponse=new LoginPreferences_Utility(FlightPassengerInfoActivity.this).getLoggedinUser();

       /* if(totHeader > 0){
            for(int i = 0; i < totHeader; i++){
                PessengerInfo pessengerInfo = new PessengerInfo();
                int index=i;
                pessengerInfo.setName("");
                pessengerInfo.setSurName("");
                //pessengerInfo.setMobile("");
                pessengerInfo.setAge("");
                pessengerInfo.setErrorMsg("");
                pessengerInfo.setNametitle("");
                pessengerInfo.setType(0);
                pessengerInfo.setDob("");
                pessengerInfo.setPessengerType("");
               // pessengerInfo.set(totAdult);
               // pessengerInfo.setCountAdults(index+1);

                list.add(pessengerInfo);
            }
        }*/

        if(totAdult > 0){
            ArrayList<PessengerInfo> infoAdultList = new ArrayList<>();
            for(int i = 0; i < totAdult; i++){
                PessengerInfo pessengerInfo1 = new PessengerInfo();
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
                pessengerInfo1.setDob("30");
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
                PessengerInfo pessengerInfo2 = new PessengerInfo();
                ErrorMsgModel msgModel2=new ErrorMsgModel();

                int indexchild=i;
                pessengerInfo2.setName("");
                pessengerInfo2.setSurName("");
                //pessengerInfo.setMobile("");
                pessengerInfo2.setAge("");
                pessengerInfo2.setDob("8");
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

        if(totInfants > 0){

            for(int i = 0; i < totInfants; i++){
                PessengerInfo pessengerInfo3 = new PessengerInfo();
                ArrayList<PessengerInfo> infoInfantsList = new ArrayList<>();
                ErrorMsgModel msgModel3=new ErrorMsgModel();
                int indexInfants=i;
                pessengerInfo3.setName("");
                pessengerInfo3.setSurName("");
                //pessengerInfo.setMobile("");
                pessengerInfo3.setAge("");
                pessengerInfo3.setDob("1");
                pessengerInfo3.setErrorMsg("");
                pessengerInfo3.setNametitle("");
                //pessengerInfo3.setChecked(true);
                pessengerInfo3.setGender("");
                pessengerInfo3.setType(1);
                pessengerInfo3.setPessengerType("I");
                pessengerInfo3.setInfants(totInfants);
               // pessengerInfo3.setOther(0);
                pessengerInfo3.setCountInfants(indexInfants+1);

                msgModel3.setErrorMsg("");
                errorList.add(msgModel3);
                list.add(pessengerInfo3);
            }
        }
        if(totOther > 0){

            for(int i = 0; i < totOther; i++){
                PessengerInfo pessengerInfo4 = new PessengerInfo();
            ErrorMsgModel msgModel4=new ErrorMsgModel();
                int indexOther=i;

                pessengerInfo4.setMobile(mobile);
                pessengerInfo4.setEmail(email);
                pessengerInfo4.setGstno(gst_no);
                pessengerInfo4.setGstemail(gst_email);
                pessengerInfo4.setCompanyName(comp_no);
                pessengerInfo4.setPessengerType("O");
                pessengerInfo4.setType(2);
                pessengerInfo4.setCompanyAddress(comp_address);
                pessengerInfo4.setErrorMsg("");
                pessengerInfo4.setContactno(contact_no);
                pessengerInfo4.setOther(totOther);
                pessengerInfo4.setCountOther(indexOther+1);
                list.add(pessengerInfo4);

                msgModel4.setErrorMsg("");
                errorList.add(msgModel4);
            }
        }
        return list;
    }

    private ArrayList<PassengerFooterInfo> footerList(){

        ArrayList<PessengerInfo> infoArrayList = new ArrayList<>();
        ArrayList<PassengerFooterInfo> footerlist = new ArrayList<>();


        if(totOther > 0){

            for(int i = 0; i < totOther; i++){
                PassengerFooterInfo footerInfo = new PassengerFooterInfo();
                //ArrayList<PessengerInfo> infoOtherList = new ArrayList<>();
                int indexOther=i;
                footerInfo.setMobile(new LoginPreferences_Utility(this).getLoggedinUser().getMobileNo());
                footerInfo.setEmail(new LoginPreferences_Utility(this).getLoggedinUser().getEmailId());
                footerInfo.setGstno("");
                footerInfo.setGstemail("");
                footerInfo.setCompanyName("");
                footerInfo.setInfoType("");
                footerInfo.setCompanyAddress("");
                footerInfo.setContactno("");
                footerInfo.setCountOther(indexOther+1);
                footerlist.add(footerInfo);
            }
        }
        return footerlist;
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
    public void onStart(){

        /*Call method and assign value in list*/

        super.onStart();
    }
    @Override
    protected void onResume() {
        try {
            if(loadFirst){
                pessengerInfoArrayList=new ArrayList<PessengerInfo>();
                pessengerInfoArrayList = travelerInfoList();
                infoAdapter2 = new PassengerInfoAdapter(this,pessengerInfoArrayList,errorList,FlightPassengerInfoActivity.this);
                recyclerView.setAdapter(infoAdapter2);
                infoAdapter2.notifyDataSetChanged();
            }
            else {
                //pessengerInfoArrayListTemp=new ArrayList<PessengerInfo>();
                // pessengerInfoArrayListTemp=  FlightSharedValues.getInstance().pessengerInfoArrayList;

                if(pessengerInfoArrayListTemp.size() == FlightSharedValues.getInstance().pessengerInfoArrayList.size()){
                    infoAdapter2 = new PassengerInfoAdapter(this,pessengerInfoArrayListTemp,errorList,FlightPassengerInfoActivity.this);
                    //PassengerInfoAdapter.pessengerInfosList=FlightSharedValues.getInstance().pessengerInfoArrayList;
                    recyclerView.setAdapter(infoAdapter2);
                    infoAdapter2.notifyDataSetChanged();
                }
                else {
                    infoAdapter2 = new PassengerInfoAdapter(this,pessengerInfoArrayListTemp,errorList,FlightPassengerInfoActivity.this);
                    //PassengerInfoAdapter.pessengerInfosList=FlightSharedValues.getInstance().pessengerInfoArrayList;
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
