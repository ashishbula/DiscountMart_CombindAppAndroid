package in.discountmart.utility_services.travel.utility_cab.cab_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.travel.utility_cab.cab_model.cab_request_mdel.CabBookRequest;
import in.discountmart.utility_services.travel.utility_cab.cab_model.cab_response_model.CabSearchResponse;

public class CabBookFinalDetailActivity extends AppCompatActivity {

    TextView txtFromCity;
    TextView txtToCity;
    TextView txtCabName;
    TextView txtTotDistance;
    TextView txtCabType;
    TextView txtDepDate_Time;
    TextView txtName;
    TextView txtNoOfPax;
    TextView txtMobile;
    TextView txtEmail;
    TextView txtGender;
    TextView txtPicAddress;
    TextView txtDropAddress;
    TextView txtPaidAmount;
    TextView txtTotAmount;
    TextView txtTotPassenger;
    Button btnContinue;
    ImageView imgClose;
    View view;

    ArrayList<CabSearchResponse> cabtSelectList;
    CabBookRequest cabBookRequest;
    LoginResponse loginResponse;

    String name="";
    String mobile1="";
    String mobile2="";
    String email1="";
    String email2="";
    String gender="";
    String noOfpax="";
    String picAddress="";
    String dropAddress="";
    String strFromCity = "";
    String strToCity = "";
    String strAdult = "";
    String strChild = "";
    String strInfant = "";
    String strDepartDate = "";
    String strDepartTime = "";
    String airlineCode = "";
    String strPromocode="";
    String strPromoAmount="";
    String strPromoStatus="";
    String strPromoDate="";
    String strDiscount="";
    String otpId="";
    int  totlePaidamount;
    float marginAmount = 0;
    double totFareamount = 0;
    double commisionAmnt = 0;
    double totAmount = 0;
    int discountAmnt = 0;
    int sendMarginAmnt = 0;
    int totDiscountAmnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_cab_book_final_detail);
        try {
            view=findViewById(android.R.id.content);

            /* Get  value from bundle through Intent*/
            Bundle intent=getIntent().getExtras();
            if(intent != null){

                cabtSelectList = new ArrayList<CabSearchResponse>();
                cabtSelectList = (ArrayList<CabSearchResponse>) intent.getSerializable("SelectedCab");
                cabBookRequest=new CabBookRequest();
                cabBookRequest=(CabBookRequest)intent.getSerializable("CabRequest");

                strFromCity=intent.getString("FromCity");
                strToCity=intent.getString("ToCity");
                strDepartDate=intent.getString("DepartDate");
                strDepartTime=intent.getString("DepartTime");

                // Call Method of content view / references
                contentView();

            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void contentView(){
        txtCabName=(TextView)findViewById(R.id.cab_book_detail_act_txt_cabname);
        txtCabType=(TextView)findViewById(R.id.cab_book_detail_act_txt_cabtype);
        txtDepDate_Time=(TextView)findViewById(R.id.cab_book_detail_act_txt_datetime);
        txtTotDistance=(TextView)findViewById(R.id.cab_book_detail_act_txt_km);
        txtPicAddress=(TextView)findViewById(R.id.cab_book_detail_act_txt_pic_address);
        txtDropAddress=(TextView)findViewById(R.id.cab_book_detail_act_txt_drop_address);

        txtFromCity=(TextView)findViewById(R.id.cab_book_detail_act_txt_fromcity);
        txtToCity=(TextView)findViewById(R.id.cab_book_detail_act_txt_tocity);

        txtName=(TextView)findViewById(R.id.cab_book_detail_act_txt_passenger_name);
        txtNoOfPax=(TextView)findViewById(R.id.cab_book_detail_act_txt_passenger_no);
        txtMobile=(TextView)findViewById(R.id.cab_book_detail_act_txt_mobile);
        txtEmail=(TextView)findViewById(R.id.cab_book_detail_act_txt_email);
        txtGender=(TextView)findViewById(R.id.cab_book_detail_act_txt_passenger_gender);
        txtPaidAmount=(TextView)findViewById(R.id.cab_book_detail_act_txt_total_amnt);
        txtTotAmount=(TextView)findViewById(R.id.cab_book_detail_act_txt_total_amount);
        txtTotPassenger=(TextView)findViewById(R.id.cab_book_detail_act_txt_total_travelor);
        btnContinue=(Button)findViewById(R.id.cab_book_detail_act_btn_continue);
        imgClose=(ImageView) findViewById(R.id.cab_book_detail_act_imag_close);

        txtDepDate_Time.setText("Date:- "+strDepartDate +":" +strDepartTime);
        txtFromCity.setText(strFromCity);
        txtToCity.setText(strToCity);

        if (cabtSelectList.size() > 0) {
            for (int i = 0; i < cabtSelectList.size(); i++) {
                //airlineCode = cabtSelectList.get(i).getAirlineCode();
                totAmount = Double.parseDouble(cabtSelectList.get(i).getTotal());
                totFareamount = Double.parseDouble(cabtSelectList.get(i).getTotal());
                txtCabName.setText(cabtSelectList.get(i).getCar());
                txtCabType.setText(cabtSelectList.get(i).getType());
                txtTotDistance.setText(cabtSelectList.get(i).getDistance() +" KM");
                txtTotAmount.setText(getResources().getString(R.string.rs_symbol)+""+cabtSelectList.get(i).getTotal());
                txtPaidAmount.setText(getResources().getString(R.string.rs_symbol)+""+cabtSelectList.get(i).getTotal());
            }
        }

        if(cabBookRequest != null){
            txtTotPassenger.setText(cabBookRequest.getNoOfPax()+" Passenger");
            txtNoOfPax.setText("Total Passenger : - "+cabBookRequest.getNoOfPax());
            txtName.setText(cabBookRequest.getName());
            txtEmail.setText(cabBookRequest.getEmail());
            txtGender.setText(cabBookRequest.getTitle());
            txtMobile.setText(cabBookRequest.getMobileNo());
            txtPicAddress.setText(cabBookRequest.getPickupAddress());
            txtDropAddress.setText(cabBookRequest.getDropAddress());
        }

        /* Button continue on click*/
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CabBookFinalDetailActivity.this,Otp_CabBookPaymentActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("CabRequest",cabBookRequest);
                bundle.putSerializable("SelectedCab",cabtSelectList);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
            }
        });

        /* Image cross icon  on click*/
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
                //overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
            }
        });
    }
}