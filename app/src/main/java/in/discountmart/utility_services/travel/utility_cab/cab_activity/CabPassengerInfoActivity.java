package in.discountmart.utility_services.travel.utility_cab.cab_activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.travel.utility_cab.cab_model.cab_request_mdel.CabBookRequest;
import in.discountmart.utility_services.travel.utility_cab.cab_model.cab_response_model.CabSearchResponse;
import in.discountmart.utility_services.travel.utility_cab.cab_sharedpreferance.CabSharedValues;

public class CabPassengerInfoActivity extends AppCompatActivity {

    EditText edTxtName;
    EditText edTxtMobile1;
    EditText edTxtMobile2;
    EditText edTxtEmail1;
    EditText edTxtEmail2;
    EditText edTxtPax;
    EditText edTxtPicupAddress;
    EditText edTxtDropAddress;
    RadioGroup rdgGender;
    RadioButton rdbMale;
    RadioButton rdbFemale;
    TextView txtTotAmount;
    TextView txtCabName;
    TextView txtCabtype;
    TextView txtDepartDate;
    TextView txtFromCity;
    TextView txtToCity;
    TextView txtErrorMsg;
    TextView txtErrorMsg2;
    Button btnContinue;
    View view;
    ProgressDialog pDialog;

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
    String cabId="";
    int  totlePaidamount;
    float marginAmount = 0;
    double totFareamount = 0;
    double commisionAmnt = 0;
    double totAmount = 0;
    int discountAmnt = 0;
    int sendMarginAmnt = 0;
    int totDiscountAmnt = 0;

    ArrayList<CabSearchResponse> cabtSelectList;
    CabBookRequest cabBookRequest;
    LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_cab_passenger_info);
        try {
            view=findViewById(android.R.id.content);

            // Call Method of content view / references
            contentView();

        }catch (Exception e){e.printStackTrace();}

    }

    private void contentView(){
        edTxtName=(EditText)findViewById(R.id.cab_passenger_act_edtxt_name);
        edTxtEmail1=(EditText)findViewById(R.id.cab_passenger_act_edtxt_email);
        edTxtEmail2=(EditText)findViewById(R.id.cab_passenger_act_edtxt_email2);
        edTxtMobile1=(EditText)findViewById(R.id.cab_passenger_act_edtxt_mobile);
        edTxtMobile2=(EditText)findViewById(R.id.cab_passenger_act_edtxt_mobile2);
        edTxtDropAddress=(EditText)findViewById(R.id.cab_passenger_act_edtxt_drop_address);
        edTxtPicupAddress=(EditText)findViewById(R.id.cab_passenger_act_edtxt_pic_address);
        edTxtPax=(EditText)findViewById(R.id.cab_passenger_act_edtxt_pax);
        rdgGender=(RadioGroup)findViewById(R.id.cab_passenger_act_rdg_gender);
        rdbMale=(RadioButton)findViewById(R.id.cab_passenger_act_rdb_male);
        rdbFemale=(RadioButton)findViewById(R.id.cab_passenger_act_rdb_female);
        btnContinue=(Button)findViewById(R.id.cab_passenger_act_btn_continue);
        txtTotAmount=(TextView)findViewById(R.id.cab_passenger_act_txt_total_amnt);
        txtCabName=(TextView)findViewById(R.id.cab_passenger_act_txt_cab_name);
        txtCabtype=(TextView)findViewById(R.id.cab_passenger_act_txt_cab_type);
        txtDepartDate=(TextView)findViewById(R.id.cab_passenger_act_txt_dep_Date);
        txtErrorMsg=(TextView)findViewById(R.id.cab_passenger_act_txt_error);
        txtErrorMsg2=(TextView)findViewById(R.id.cab_passenger_act_txt_error2);
        txtFromCity=(TextView)findViewById(R.id.cab_passenger_act_txt_from_city);
        txtToCity=(TextView)findViewById(R.id.cab_passenger_act_txt_to_city);



        /* Get  value from bundle through Intent*/
        Bundle intent=getIntent().getExtras();
        if(intent != null){

            cabtSelectList = new ArrayList<CabSearchResponse>();
            cabtSelectList = (ArrayList<CabSearchResponse>) intent.getSerializable("SelectCab");

            strFromCity=intent.getString("FromCity");
            strToCity=intent.getString("ToCity");
            strDepartDate=intent.getString("DepartDate");
            strDepartTime=intent.getString("DepartTime");
            txtDepartDate.setText("Date:- "+strDepartDate +":" +strDepartTime);
            txtFromCity.setText(strFromCity);
            txtToCity.setText(strToCity);

        }

        totAmount= CabSharedValues.getInstance().TotalFare;
        totDiscountAmnt= CabSharedValues.getInstance().totDiscount;
        strPromocode=CabSharedValues.getInstance().cabPromocode;
        strPromoAmount=CabSharedValues.getInstance().cabPromoAmount;

        /* Login Response from Shared Preference Value*/
        loginResponse=new LoginResponse();
        loginResponse= new LoginPreferences_Utility(this).getLoggedinUser();
        if(loginResponse!= null){
            edTxtMobile2.setText(loginResponse.getMobileNo());
            edTxtEmail2.setText(loginResponse.getEmailId());
        }


        if (cabtSelectList.size() > 0) {
            for (int i = 0; i < cabtSelectList.size(); i++) {
                //airlineCode = cabtSelectList.get(i).getAirlineCode();
                totAmount = Double.parseDouble(cabtSelectList.get(i).getTotal());
                totFareamount = Double.parseDouble(cabtSelectList.get(i).getTotal());
                txtCabName.setText(cabtSelectList.get(i).getCar());
                txtCabtype.setText(cabtSelectList.get(i).getType());

                txtTotAmount.setText(cabtSelectList.get(i).getTotal());
                cabId=cabtSelectList.get(i).getCabID();
            }
        }

        /* Chekc if radion button gender is check or not*/
        if(rdbFemale.isChecked())
            gender="Female";
        else
            gender="Male";

        /*Radio Group Side on checked change listener*/
        rdgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectId=rdgGender.getCheckedRadioButtonId();
                RadioButton rb=(RadioButton)group.findViewById(checkedId);
                String strRbtnVlaue=rb.getText().toString();
                if(strRbtnVlaue.equals("Female")){
                    gender="Female";
                }
                else if(strRbtnVlaue.equals("Male")){
                    gender="Male";
                }
            }
        });

        /* Button Continue on click*/
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

    }

    private void checkValidation(){
        if(edTxtName.getText().toString().equals("")){
            edTxtName.setError("Enter Name");
            edTxtName.requestFocus();
        }
        else if(edTxtMobile1.getText().toString().equals("")){
            edTxtMobile1.setError("Enter Mobile No.");
            edTxtMobile1.requestFocus();
        }
        else if(edTxtMobile1.getText().toString().length() > 10 || edTxtMobile1.getText().toString().length() < 10){
            edTxtMobile1.setError("Enter Complete Mobile No.");
            edTxtMobile1.requestFocus();
        }
        else if(edTxtEmail1.getText().toString().equals("")){
            edTxtEmail1.setError("Enter Email Id.");
            edTxtEmail1.requestFocus();
        }
        else if(edTxtPax.getText().toString().equals("")){
            edTxtPax.setError("Enter No. of Pax");
            edTxtPax.requestFocus();
        }
        else if(edTxtPicupAddress.getText().toString().equals("")){
            edTxtPicupAddress.setError("Enter pic-up address");
            edTxtPicupAddress.requestFocus();
        }
        else if(edTxtDropAddress.getText().toString().equals("")){
            edTxtDropAddress.setError("Enter drop-off address");
            edTxtDropAddress.requestFocus();
        }
        else if(edTxtMobile2.getText().toString().equals("")){
            edTxtMobile2.setError("Enter Register Mobile No.");
            edTxtMobile2.requestFocus();
        }

        else if(edTxtEmail2.getText().toString().equals("")){
            edTxtEmail2.setError("Enter Register Email Id.");
            edTxtEmail2.requestFocus();
        }

        else {
            name=edTxtName.getText().toString();
            mobile1=edTxtMobile1.getText().toString();
            email1=edTxtEmail1.getText().toString();
            noOfpax=edTxtPax.getText().toString();
            picAddress=edTxtPicupAddress.getText().toString();
            dropAddress=edTxtDropAddress.getText().toString();
            mobile2=edTxtMobile2.getText().toString();
            email2=edTxtEmail2.getText().toString();

            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(this).getLoggedinUser();
            String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);
            String formno=loginResponse.getFormNo().substring(0,loginResponse.getFormNo().length()-2);
            String mobile=loginResponse.getMobileNo().substring(0,loginResponse.getMobileNo().length()-2);

            cabBookRequest=new CabBookRequest();
            cabBookRequest.setCity(strFromCity);
            cabBookRequest.setDestination(strToCity);
            cabBookRequest.setDiscount(String.valueOf(totDiscountAmnt));
            cabBookRequest.setDOJ(strDepartDate);
            cabBookRequest.setDropAddress(dropAddress);
            cabBookRequest.setEmail(email1);
            cabBookRequest.setFormno(formno);
            cabBookRequest.setID("0");
            cabBookRequest.setMemName(loginResponse.getMemName());
            cabBookRequest.setMobileNo(mobile1);
            cabBookRequest.setName(name);
            cabBookRequest.setTitle(gender);
            cabBookRequest.setNoOfPax(noOfpax);
            cabBookRequest.setPickupAddress(picAddress);
            cabBookRequest.setPickupTime(strDepartTime);
            cabBookRequest.setPromoCode(strPromocode);
            cabBookRequest.setPromoDiscount(strPromoAmount);
            if(!loginResponse.getEmailId().equals("") || loginResponse.getEmailId() != null)
                cabBookRequest.setRegEmailId(loginResponse.getEmailId());
            else
                cabBookRequest.setRegEmailId(email2);

            if(!loginResponse.getMobileNo().equals("") || loginResponse.getMobileNo() != null)
                cabBookRequest.setRegMobileNo(mobile);
            else
                cabBookRequest.setRegMobileNo(mobile2);
            cabBookRequest.setTotalAmount(String.valueOf(totAmount));
            cabBookRequest.setCabID(cabId);

            String cabRequest=new Gson().toJson(cabBookRequest);
            Log.d("Value", cabRequest);

            Intent intent =new Intent(CabPassengerInfoActivity.this,CabBookFinalDetailActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("CabRequest",cabBookRequest);
            bundle.putSerializable("SelectedCab",cabtSelectList);
            bundle.putString("FromCity", strFromCity);
            bundle.putString("ToCity", strToCity);
            bundle.putString("DepartDate", strDepartDate);
            bundle.putString("DepartTime", strDepartTime);
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

        }
    }
}