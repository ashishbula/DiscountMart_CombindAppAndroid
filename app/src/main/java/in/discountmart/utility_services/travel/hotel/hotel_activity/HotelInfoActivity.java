package in.discountmart.utility_services.travel.hotel.hotel_activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.base.network.NetworkClient_Utility_1;
import in.base.util.FileCache;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.travel.hotel.call_hotel_api.HotelApi;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelRoomModel;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model.HotelInfoRequest;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelAttraction;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelDetail;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelError;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelInfo;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelInfoResponse;
import in.discountmart.utility_services.travel.hotel.hotel_shared_preferance.HotelSharedValues;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelInfoActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    CollapsingToolbarLayout collapsingToolbar;
    Toolbar toolbar;
    SliderLayout sliderLayout;
    Button btnSelect;
    TextView txtHotelName;
    TextView txtHotelCity;
    TextView txtCheckInDate;
    TextView txtCheckOutDate;
    TextView txtTotalRoom;
    TextView txtTotalGuest;
    TextView txtFeature;
    TextView txtReadmore;
    TextView txtPolicyReadmore;
    TextView txtAtractionReadmore;
    TextView txtAttraction;
    TextView txtAddress;
    TextView txtContactNo;
    TextView txtPincode;
    TextView txtCountry;
    TextView txtPolicy;
    TextView txtFaxNo;
    AppCompatRatingBar ratingBar;
    LinearLayout layoutMap;
    LinearLayout layoutPhotos;
    LinearLayout layoutFacility;
    LinearLayout layoutAttraction;
    LinearLayout layoutPolicy;
    LinearLayout layoutEdit;
    ProgressDialog progressDialog;
    FileCache fileCache;
    View view;

    /*Model Class*/
    HotelInfoResponse hotelInfoResponse;
    HotelInfo hotelInfo;
    HotelError hotelError;
    HotelDetail hotelDetail;
    HotelAttraction hotelAttraction;
    ArrayList<HotelAttraction> hotelAttractionArrayList;
    ArrayList<String> facilityList;
    ArrayList<String> hotelImages;

    String strHotelCode="";
    String strTraceId="";
    String strResultIndex="";
    String hotelName="";
    String hotelecity="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_hotel_info);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view=findViewById(android.R.id.content);
        try {
            //collapsingToolbar=(CollapsingToolbarLayout)findViewById(R.id.hotel_info_act_collapsTool);
            toolbar=(Toolbar)findViewById(R.id.hotel_info_act_toolbar);
            sliderLayout=(SliderLayout)findViewById(R.id.hotel_info_act_image_slider);
            btnSelect=(Button)findViewById(R.id.hotel_info_act_btn_select);

            setSupportActionBar(toolbar);

            /*Get value from Intent previous activity*/

            Intent intent=getIntent();

            if(intent != null){
                strHotelCode=intent.getStringExtra("HotelCode");
                strTraceId=intent.getStringExtra("TraceId");
                strResultIndex=intent.getStringExtra("ResultIndex");
                hotelName=intent.getStringExtra("HotelName");
            }

            // toolbar fancy stuff
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Hotel Info");

            initCollapsingToolbar();

            txtHotelName=(TextView)findViewById(R.id.hotel_info_act_txt_hotelname);
            txtHotelCity=(TextView)findViewById(R.id.hotel_info_act_txt_hotel_city);
            txtCheckInDate=(TextView)findViewById(R.id.hotel_info_act_txt_checkIn_date);
            txtCheckOutDate=(TextView)findViewById(R.id.hotel_info_act_txt_checkOut_date);
            txtTotalRoom=(TextView)findViewById(R.id.hotel_info_act_txt_hotel_room);
            txtTotalGuest=(TextView)findViewById(R.id.hotel_info_act_txt_hotel_guest);
            txtFeature=(TextView)findViewById(R.id.hotel_info_act_txt_hotel_feature);
            txtReadmore=(TextView)findViewById(R.id.hotel_info_act_txt_hotel_readmore);
            txtPolicyReadmore=(TextView)findViewById(R.id.hotel_info_act_txt_hotel_policy_readmore);
            txtAtractionReadmore=(TextView)findViewById(R.id.hotel_info_act_txt_hotel_attraction_readmore);
            txtAttraction=(TextView)findViewById(R.id.hotel_info_act_txt_hotel_attraction);
            txtContactNo=(TextView)findViewById(R.id.hotel_info_act_txt_hotel_contactno);
            txtPincode=(TextView)findViewById(R.id.hotel_info_act_txt_hotel_pincode);
            txtCountry=(TextView)findViewById(R.id.hotel_info_act_txt_hotel_country_name);
            txtFaxNo=(TextView)findViewById(R.id.hotel_info_act_txt_hotel_fax);
            txtAddress=(TextView)findViewById(R.id.hotel_info_act_txt_hotel_address);
            txtPolicy=(TextView)findViewById(R.id.hotel_info_act_txt_hotel_policy);
            ratingBar=(AppCompatRatingBar)findViewById(R.id.hotel_info_act_rating_bar);
            layoutAttraction=(LinearLayout)findViewById(R.id.hotel_info_act_layout_attraction);
            layoutMap=(LinearLayout)findViewById(R.id.hotel_info_act_layout_map);
            layoutPhotos=(LinearLayout)findViewById(R.id.hotel_info_act_layout_photo);
            layoutFacility=(LinearLayout)findViewById(R.id.hotel_info_act_layout_facility);
            layoutPolicy=(LinearLayout)findViewById(R.id.hotel_info_act_layout_policy);
            layoutEdit=(LinearLayout)findViewById(R.id.hotel_info_act_layout_edit);



            /*Call Hotel Info api*/
            if(!ConnectivityUtils.isNetworkEnabled(this)){
                Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();
            }
            else {
                getHotelInfo();
            }



            /*Button Select Room on click */
            btnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentRoom=new Intent(HotelInfoActivity.this,HotelRoomActivity.class);
                    startActivity(intentRoom);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });





        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /*Slider 2*/
    public void intiatSlider2( ArrayList<String> image){
        HashMap<String,String> file_maps = new HashMap<String, String>();
        String strImage="";
        for (int i=0; i < image.size(); i++){
            strImage=image.get(i);
            file_maps.put("",strImage);
        }

        //file_maps.put("Mobile Recharge",R.mipmap.mobile_recharge);
        //file_maps.put("Cab", R.mipmap.taxi_image);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        // mSlider.setCustomAnimation(new D);
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);
           /* ListView l = (ListView)findViewById(R.id.transformers);
            l.setAdapter(new TransformerAdapter(this));
            l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mDemoSlider.setPresetTransformer(((TextView) view).getText().toString());
                    Toast.makeText(MainActivity_gift.this, ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });*/
    }

    //slider
    public void setBanners(ArrayList<String> Image) {

        try {
            sliderLayout.removeAllSliders();

            ArrayList<HashMap<String, String>> url_maps_array = new ArrayList<HashMap<String, String>>();

            for (int i = 0; i < Image.size(); i++) {
                HashMap<String, String> url_maps = new HashMap<String, String>();
                url_maps.put("", Image.get(i));
                url_maps_array.add(url_maps);
            }
            for (int i = 0; i < url_maps_array.size(); i++) {
                for (String name : url_maps_array.get(i).keySet()) {
                    DefaultSliderView textSliderView = new DefaultSliderView(this);
                    //TextSliderView.. USE THIS IF U WANT DESCRIPTION ON SLIDER
                    // initialize a SliderLayout
                    textSliderView
                            //.description(name)//FOR DESC
                            .image(url_maps_array.get(i).get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit);

                    textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            try {
                                int j = Integer.parseInt(slider.getBundle().get("extra").toString());
                       /* if(j==0)
                            Utilities.goToPage(HomeActivity.this, HotelSearchActivity.class, null);*/
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    });

                    //add your extra informatio
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle().putString("extra", name);

                    sliderLayout.addSlider(textSliderView);
                }
            }
            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            sliderLayout.setCustomAnimation(new DescriptionAnimation());
            sliderLayout.setDuration(4000);
            sliderLayout.addOnPageChangeListener(new ViewPagerEx.SimpleOnPageChangeListener());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /*Slider 1*/
    public void setBanners1(){
        // ArrayList<String> file_maps=new ArrayList<>();
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hotel",R.mipmap.utility_ic_hotel);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        // mSlider.setCustomAnimation(new D);
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);
    }

    /* Request and Response Hotel Info*/
    public void getHotelInfo(){
        try {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String strApiRequest="";
            JSONObject object=null;
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(this).getLoggedinUser();
            String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);
            String hotelGroupId=loginResponse.getHotelGroupId();

            String strToken= TokenBase64.getToken();

            /*Main Request Model*/
            ApiRequest apiRequest = new ApiRequest();

            try {

                /*Base Request Model*/
                BaseHeaderRequest headerRequest =new BaseHeaderRequest();
                headerRequest.setUserName(  new LoginPreferences_Utility(this).getLoggedinUser().getUserName());
                headerRequest.setPassword( new LoginPreferences_Utility(this).getLoggedinUser().getPasswd());
                headerRequest.setSponsorFormNo(companyId);
           /* if(loginResponse.getMemMode().equals("D"))
                headerRequest.setSponsorFormNo(companyId);
            else
                headerRequest.setSponsorFormNo("");*/

                /* Flight Search Request Model*/

                HotelInfoRequest hotelInfoRequest=new HotelInfoRequest();
                hotelInfoRequest.setEndUserIp("");
                hotelInfoRequest.setHotelCode(strHotelCode);
                hotelInfoRequest.setResultIndex(strResultIndex);
                hotelInfoRequest.setTokenId("");
                hotelInfoRequest.setTraceId(strTraceId);
                hotelInfoRequest.setHotelGroupId(hotelGroupId);



                /*Set Value in Main Request Model*/
                apiRequest.setHEADER(headerRequest);
                apiRequest.setDATA(hotelInfoRequest);

                strApiRequest=new Gson().toJson(apiRequest);

                Log.e("Value",strApiRequest);
            }catch (Exception e){
                e.printStackTrace();
            }

            Call<BaseResponse>fetchHotelInfo=
                    NetworkClient_Utility_1.getInstance(this).create(HotelApi.class).fetchHotelInfo(apiRequest,strToken);

            fetchHotelInfo.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    try {

                        BaseResponse Response =new BaseResponse();
                        Response=response.body();

                        if(Response != null){
                            if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                                if(Response.getRESP_VALUE().equals("") || Response.getRESP_VALUE().isEmpty()){
                                    String toast= Response.getRESP_MSG();
                                    //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                                    Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                            .show();
                                }
                                else if(!(Response.getRESP_VALUE()==null) ||  !(Response.getRESP_VALUE().equals(""))){
                                    String[] hotelListResponse=Response.getRESPONSE().split("");
                                    HotelInfoResponse hotelInfoResponse=new HotelInfoResponse();
                                    hotelInfoResponse=new Gson().fromJson(Response.getRESP_VALUE(),HotelInfoResponse.class);

                                    HotelSharedValues.getInstance().hotelInfoResponse=hotelInfoResponse;
                                    if(hotelInfoResponse != null){

                                        HotelInfo hotelInfo=new HotelInfo();
                                        hotelInfo=hotelInfoResponse.getHotelInfoResult();
                                        HotelSharedValues.getInstance().hotelInfo=hotelInfoResponse.getHotelInfoResult();
                                        HotelError hotelError=new HotelError();
                                        hotelError=hotelInfo.getError();
                                        HotelSharedValues.getInstance().hotelError=hotelError;

                                        if(hotelInfo.getResponseStatus().contentEquals("1") && hotelError.getErrorCode().contentEquals("0")){

                                            hotelDetail=new HotelDetail();
                                            hotelDetail=hotelInfo.getHotelDetails();
                                            HotelSharedValues.getInstance().hotelDetail=hotelInfo.getHotelDetails();

                                            if(hotelDetail != null){

                                                /*Get Image form response and set in stirng arraylist for show in slider*/
                                                hotelImages=new ArrayList<String>();
                                                ArrayList<String> imagList=new ArrayList<>();
                                                imagList=hotelDetail.getImages();
                                                if(imagList.size() > 0){
                                                    hotelImages.addAll(imagList);

                                                    ArrayList<String> image=new ArrayList<String>();
                                                    for (int i=0; i<imagList.size(); i++){

                                                        if( i < 8){
                                                            if(!imagList.get(i).contentEquals("") && imagList.get(i) != null)
                                                                image.add(imagList.get(i));
                                                        }


                                                    }
                                                    /*Save image in Shared*/
                                                    HotelSharedValues.getInstance().hotelImages=image;
                                                    if(HotelSharedValues.getInstance().hotelImages.size() > 0){
                                                        setBanners(HotelSharedValues.getInstance().hotelImages);
                                                    }
                                                    else {

                                                        setBanners1();
                                                    }


                                                }

                                                /*Get Facility Value form response and set in stirng arraylist for show */
                                               facilityList=new ArrayList<>();
                                                ArrayList<String> facilityArrayList=new ArrayList<>();
                                                facilityArrayList=hotelDetail.getHotelFacilities();
                                                if(facilityArrayList != null){
                                                    if(facilityArrayList.size() > 0){
                                                        facilityList.addAll(facilityArrayList);
                                                        HotelSharedValues.getInstance().facilityList=facilityList;
                                                    }
                                                }
                                                else {
                                                    facilityList=new ArrayList<>();
                                                }

                                                /*Get Attraction Value form response and set in stirng arraylist for show */

                                                hotelAttractionArrayList=new ArrayList<HotelAttraction>();
                                                ArrayList<HotelAttraction> attractionList=new ArrayList<HotelAttraction>();
                                                String attraction="";
                                                if(hotelDetail.getAttractions() !=null && hotelDetail.getAttractions().size() > 0){

                                                    layoutAttraction.setVisibility(View.VISIBLE);
                                                    attractionList=hotelDetail.getAttractions();

                                                    hotelAttractionArrayList=attractionList;
                                                    HotelSharedValues.getInstance().hotelAttractionArrayList=attractionList;

                                                    for (int i=0;i < attractionList.size(); i++){
                                                        attraction=attractionList.get(i).getValue();
                                                    }
                                                    txtAttraction.setText(Html.fromHtml(attraction));
                                                }
                                                else {
                                                    layoutAttraction.setVisibility(View.GONE);
                                                    hotelAttractionArrayList=new ArrayList<HotelAttraction>();
                                                    HotelSharedValues.getInstance().hotelAttractionArrayList=attractionList;
                                                    txtAttraction.setText("");
                                                }

                                                /*Text Read more for Attraction*/
                                                txtAtractionReadmore.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        if(txtAtractionReadmore.getText().toString().contentEquals("Read More....")){
                                                            //layoutAttraction.setVisibility(View.VISIBLE);
                                                            txtAttraction.setMaxLines(Integer.MAX_VALUE);
                                                            //txtAttraction.setText(Html.fromHtml(attraction));
                                                            txtAtractionReadmore.setText("Less..");
                                                        }
                                                        else if(txtAtractionReadmore.getText().toString().contentEquals("Less..")){
                                                            //layoutAttraction.setVisibility(View.GONE);
                                                            txtAttraction.setMaxLines(5);
                                                            //txtAttraction.setText(Html.fromHtml(attraction));
                                                            txtAtractionReadmore.setText("Read More....");
                                                        }
                                                    }
                                                });

                                                /*Set Value in text */
                                                txtHotelName.setText(hotelDetail.getHotelName());
                                                HotelSharedValues.getInstance().hotelName=hotelDetail.getHotelName();
                                                txtAddress.setText(hotelDetail.getAddress());
                                                txtCheckInDate.setText(HotelSharedValues.getInstance().chkInDate);
                                                txtCheckOutDate.setText(HotelSharedValues.getInstance().chkOutDate);
                                                txtContactNo.setText(hotelDetail.getHotelContactNo());
                                                txtCountry.setText(hotelDetail.getCountryName());
                                                txtFaxNo.setText(hotelDetail.getFaxNumber());
                                                txtPincode.setText(hotelDetail.getPinCode());
                                                txtHotelCity.setText(HotelSharedValues.getInstance().city);
                                                txtTotalRoom.setText("Room "+String.valueOf(HotelSharedValues.getInstance().totRoom));
                                                txtTotalGuest.setText("Guest "+String.valueOf(HotelSharedValues.getInstance().totMember));
                                                txtFeature.setText(Html.fromHtml(hotelDetail.getDescription()));

                                                if(hotelDetail.getStarRating().contentEquals("")){
                                                    ratingBar.setRating(0);
                                                    HotelSharedValues.getInstance().hotelRating="0";
                                                }
                                                else {
                                                    ratingBar.setRating(Float.parseFloat(hotelDetail.getStarRating()));
                                                    HotelSharedValues.getInstance().hotelRating=hotelDetail.getStarRating();
                                                }

                                                /*update*/
                                                layoutEdit.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        try {
                                                            Intent intent1=new Intent(HotelInfoActivity.this,HotelSearchActivity.class);
                                                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            ArrayList<HotelRoomModel> hotelRoomArrayList=new ArrayList<HotelRoomModel>();
                                                            hotelRoomArrayList= HotelSharedValues.getInstance().hotelRoomInfoListShared;
                                                            Bundle bundle=new Bundle();
                                                            bundle.putSerializable("EditRoomInfo",hotelRoomArrayList);
                                                            bundle.putString("City",HotelSharedValues.getInstance().city);
                                                            bundle.putString("ChkInDate",HotelSharedValues.getInstance().chkInDate);
                                                            bundle.putString("ChkOutDate",HotelSharedValues.getInstance().chkOutDate);
                                                            bundle.putInt("Adult",HotelSharedValues.getInstance().totAdult);
                                                            bundle.putInt("Child",HotelSharedValues.getInstance().totChild);
                                                            bundle.putInt("TotRoom",HotelSharedValues.getInstance().totRoom);
                                                            bundle.putBoolean("Edit",true);

                                                            intent1.putExtras(bundle);
                                                            startActivity(intent1);
                                                            finish();
                                                            overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_left);
                                                        }catch (Exception e){
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });


                                                /*text read more for hotel feature on click*/
                                                txtReadmore.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        if(txtReadmore.getText().toString().contentEquals("Read More....")){
                                                            //layoutAttraction.setVisibility(View.VISIBLE);
                                                            txtFeature.setMaxLines(Integer.MAX_VALUE);
                                                            //txtAttraction.setText(Html.fromHtml(attraction));
                                                            txtReadmore.setText("Less..");
                                                        }
                                                        else if(txtReadmore.getText().toString().contentEquals("Less..")){
                                                            //layoutAttraction.setVisibility(View.GONE);
                                                            txtFeature.setMaxLines(5);
                                                            //txtAttraction.setText(Html.fromHtml(attraction));
                                                            txtReadmore.setText("Read More....");
                                                        }
                                                    }
                                                });

                                                if(hotelDetail.getHotelPolicy() != null){
                                                    if(!hotelDetail.getHotelPolicy().equals("") ){
                                                        layoutPolicy.setVisibility(View.VISIBLE);
                                                        txtPolicy.setText(Html.fromHtml(hotelDetail.getHotelPolicy()));

                                                        /*Text Read more for policy*/
                                                        txtPolicyReadmore.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                if(txtPolicyReadmore.getText().toString().contentEquals("Read More....")){
                                                                    //layoutAttraction.setVisibility(View.VISIBLE);
                                                                    txtPolicy.setMaxLines(Integer.MAX_VALUE);
                                                                    txtPolicy.setText(Html.fromHtml(hotelDetail.getHotelPolicy()));
                                                                    txtPolicyReadmore.setText("Less..");
                                                                }
                                                                else if(txtPolicyReadmore.getText().toString().contentEquals("Less..")){
                                                                    //layoutAttraction.setVisibility(View.GONE);
                                                                    txtPolicy.setMaxLines(5);
                                                                    txtPolicy.setText(Html.fromHtml(hotelDetail.getHotelPolicy()));
                                                                    txtPolicyReadmore.setText("Read More....");
                                                                }
                                                            }
                                                        });
                                                    }
                                                    else {
                                                        layoutPolicy.setVisibility(View.GONE);
                                                        txtPolicy.setText("");
                                                    }
                                                }
                                                else {
                                                    layoutPolicy.setVisibility(View.GONE);
                                                    txtPolicy.setText("");
                                                }




                                            }
                                            else {
                                                String toast= "Hotel detail not available";
                                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                                        .setAction("CLOSE", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {

                                                            }
                                                        })
                                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                                        .show();
                                            }
                                        }
                                        else {
                                            Snackbar.make(view, hotelError.getErrorMessage()+ " / "+" Please search again..", Snackbar.LENGTH_LONG)
                                                    .setAction("CLOSE", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                        }
                                                    })
                                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                                    .show();
                                        }

                                    }
                                    else {
                                        String toast= "Hotel information not available, please try again";
                                        // Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                                        Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                                .setAction("CLOSE", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                    }
                                                })
                                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                                .show();
                                    }
                                }

                            }
                            else if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR"))  {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                        .show();

                            }
                            else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                                String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                                Toast.makeText(HotelInfoActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(HotelInfoActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                                Toast.makeText(HotelInfoActivity.this, toast, Toast.LENGTH_SHORT).show();
                                // showSnackbar(toast);

                            }
                        }
                        else {
                            //Toast.makeText(HotelInfoActivity.this,"something error may be server / other",Toast.LENGTH_SHORT).show();

                            String toast = "something wrong..";
                            //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
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

    private void initCollapsingToolbar() {

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.hotel_info_act_collapsTool);
        //collapsingToolbar.setTitle(" Profile ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.hotel_info_act_appbarlayout);
        //imageViewProfileImg=(ImageView)findViewById(R.id.activity_profile_imgView_image);
        appBarLayout.setExpanded(true);


        // hiding & showing the title when toolbar expanded & collapsed

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    //collapsingToolbar.setTitle(getString(R.string.app_name));
                    collapsingToolbar.setScrimAnimationDuration(3);
                    collapsingToolbar.setTitle("Hotel Info");
                    collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
                   collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
                    //Animations
                    //Animation show_fab_1 = AnimationUtils.loadAnimation(HotelInfoActivity.this, R.anim.fab_fade_out);
                    //fabEditProfile.startAnimation(show_fab_1);
                    //ViewCompat.animate(fabEditProfile).scaleY(0).scaleX(0).start();
                    ///new Fab_Hide_On_Scroll(ProfileActivity.this,fabEditProfile.setRippleColor(Color.GREEN));


                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(hotelName);
                    collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
                    collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
                    collapsingToolbar.setExpandedTitleGravity(Gravity.CENTER|Gravity.BOTTOM);
                    //Animation hide_fab_1 = AnimationUtils.loadAnimation(HotelInfoActivity.this, R.anim.fab_fade_in);
                    //ViewCompat.animate(fabEditProfile).scaleY(1).scaleX(1).start();
                   // fabEditProfile.startAnimation(hide_fab_1);
                    //new Fab_Hide_On_Scroll(ProfileActivity.this,hide_fab_1);
                    isShow = false;
                }


               /* if (mMaxScrollSize == 0)
                    mMaxScrollSize = appBarLayout.getTotalScrollRange();

                int currentScrollPercentage = (Math.abs(verticalOffset)) * 100
                        / mMaxScrollSize;

                if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
                    if (!mIsImageHidden) {
                        mIsImageHidden = true;

                        ViewCompat.animate(fabEditProfile).scaleY(0).scaleX(0).start();
                        *//**
                 * Realize your any behavior for FAB here!
                 **//*
                    }
                }

                if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
                    if (mIsImageHidden) {
                        mIsImageHidden = false;
                        ViewCompat.animate(fabEditProfile).scaleY(1).scaleX(1).start();
                        *//**
                 * Realize your any behavior for FAB here!
                 **//*
                    }
                }*/
            }
        });
    }


}
