package in.discountmart.utility_services.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;

import in.discountmart.R;
import in.discountmart.utility_services.model.SettingModel;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.sharedpreferences.SettingPreference;

public class SettingActivity extends AppCompatActivity {

    EditText edTxtMobile;
    EditText edTxtEmail;
    EditText edTxtAcno;
    EditText edTxtIfsc;
    EditText edTxtPanNo;
    EditText edTxtGstNo;
    EditText edTxtCompName;
    EditText edTxtGstEmail;
    EditText edTxtContactNo;
    EditText edTxtCompAddress;
    Button btnSubmit;
    String mobile="";
    String strmobile="";
    String email="";
    String stremail="";
    String acno="";
    String ifsc="";
    String panno="";
    String gstno="";
    String compname="";
    String compaddress="";
    String contactno="";
    String gstmailid="";

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_setting);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            edTxtMobile=(EditText)findViewById(R.id.setting_act_edTxt_mobile);
            edTxtEmail=(EditText)findViewById(R.id.setting_act_edTxt_email);
            edTxtAcno=(EditText)findViewById(R.id.setting_act_edTxt_acno);
            edTxtPanNo=(EditText)findViewById(R.id.setting_act_edTxt_panno);
            edTxtIfsc=(EditText)findViewById(R.id.setting_act_edTxt_ifsc);
            edTxtGstNo=(EditText)findViewById(R.id.setting_act_edtxt_gstno);
            edTxtCompName=(EditText)findViewById(R.id.setting_act_edtxt_companyno);
            edTxtCompAddress=(EditText)findViewById(R.id.setting_act_edtxt_company_address);
            edTxtContactNo=(EditText)findViewById(R.id.setting_act_edtxt_contact_no);
            edTxtGstEmail=(EditText)findViewById(R.id.setting_act_edtxt_gst_email);
            btnSubmit=(Button)findViewById(R.id.setting_act_btn_submit);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Setting");


            /*Get Value from shared preference*/
            LoginResponse loginResponse=new LoginResponse();
            loginResponse=new LoginPreferences_Utility(this).getLoggedinUser();
            String strMobile="";
            String strEmail="";
            if(!loginResponse.getMobileNo().contentEquals("") && loginResponse.getMobileNo().length() > 10)
                strMobile=loginResponse.getMobileNo().substring(0,loginResponse.getMobileNo().length()-2);
            else if(!loginResponse.getMobileNo().contentEquals("") && loginResponse.getMobileNo().length() ==10)
                strMobile=loginResponse.getMobileNo();

            if(!loginResponse.getEmailId().contentEquals(""))
                strEmail=loginResponse.getEmailId();

            SettingModel settingModel=new SettingModel();
            settingModel=new SettingPreference(SettingActivity.this).getSettingItem();
            String strMobile1="";
            String strEmai11="";
            if(!settingModel.getMobile().contentEquals(""))
                strMobile1=settingModel.getMobile();
            if (!settingModel.getEmail().contentEquals(""))
                strEmai11=settingModel.getEmail();

            if (!settingModel.getAccountno().contentEquals(""))
                edTxtAcno.setText(settingModel.getAccountno());
            if (!settingModel.getIfsc().contentEquals(""))
                edTxtIfsc.setText(settingModel.getIfsc());

            if (!settingModel.getPanno().contentEquals(""))
                edTxtPanNo.setText(settingModel.getPanno());

            if (!settingModel.getGstno().contentEquals(""))
                edTxtGstNo.setText(settingModel.getGstno());
            if (!settingModel.getGst_mail().contentEquals(""))
                edTxtGstEmail.setText(settingModel.getGst_mail());

            if (!settingModel.getComp_address().contentEquals(""))
                edTxtCompAddress.setText(settingModel.getComp_address());
            if (!settingModel.getComp_name().contentEquals(""))
                edTxtCompName.setText(settingModel.getComp_name());
            if (!settingModel.getContact_no().contentEquals(""))
                edTxtContactNo.setText(settingModel.getContact_no());

            /*For Mobile number*/
            if(!strMobile.equals("") && !strMobile1.equals("")){
                if(strMobile.equals(strMobile1)){
                    strmobile=strMobile;
                    edTxtMobile.setText(strmobile);
                }
                else{
                    strmobile=strMobile;
                    edTxtMobile.setText(strmobile);
                }



            }
            else if(!strMobile.equals("") && strMobile1.equals("")){
                strmobile=strMobile;
                edTxtMobile.setText(strmobile);
            }
            else if(strMobile.equals("") && !strMobile1.equals("")){
                strmobile=strMobile1;
                edTxtMobile.setText(strmobile);
            }

            /*for Email id*/
            if(!strEmail.equals("") && !strEmai11.equals("")){
                if(strEmail.equals(strEmai11)){
                    stremail=strEmail;
                    edTxtEmail.setText(stremail);
                }
                else{
                    stremail=strEmail;
                    edTxtEmail.setText(stremail);
                }


            }
            else if(!strEmail.equals("") && strEmai11.equals("")){
                stremail=strEmail;
                edTxtEmail.setText(stremail);
            }
            else if(strEmail.equals("") && !strEmai11.equals("")){
                stremail=strEmai11;
                edTxtEmail.setText(stremail);
            }




            /*On btn Click*/

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mobile=edTxtMobile.getText().toString();
                    email=edTxtEmail.getText().toString();
                    acno=edTxtAcno.getText().toString();
                    ifsc=edTxtIfsc.getText().toString();
                    panno=edTxtPanNo.getText().toString();
                    gstno=edTxtGstNo.getText().toString();
                    gstmailid=edTxtGstEmail.getText().toString();
                    compaddress=edTxtCompAddress.getText().toString();
                    compname=edTxtCompName.getEditableText().toString();
                    contactno=edTxtContactNo.getText().toString();

                    SettingModel settingModel=new SettingModel();
                    if(mobile.toString().equals("")){
                        edTxtMobile.setError("Enter complete mobile no.");
                        edTxtMobile.requestFocus();
                    }
                    else if(mobile.length() < 10 && mobile.length() > 0){
                        edTxtMobile.setError("Enter complete mobile no.");
                        edTxtMobile.requestFocus();
                    }
                    else if(email.toString().equals("")){
                        edTxtEmail.setError("Enter email id");
                        edTxtEmail.requestFocus();
                    }
                    else if(!isValidEmail(email) && email.length() > 0){
                        edTxtEmail.setError("Enter complete email id");
                        edTxtEmail.requestFocus();
                    }
                    else if(edTxtGstNo.getText().toString().equals("")){
                        edTxtGstNo.setError("Enter valid GST no.");
                        edTxtGstNo.requestFocus();
                    }
                    else if(edTxtCompName.getText().toString().equals("")){
                        edTxtCompName.setError("Enter registered company name");
                        edTxtCompName.requestFocus();
                    }
                    else if(edTxtCompAddress.getText().toString().equals("")){
                        edTxtCompAddress.setError("Enter company address");
                        edTxtCompAddress.requestFocus();
                    }
                    else if(edTxtContactNo.getText().toString().equals("")){
                        edTxtContactNo.setError("Enter company contact no.");
                        edTxtContactNo.requestFocus();
                    }
                    else if(edTxtGstEmail.getText().toString().equals("")){
                        edTxtGstEmail.setError("Enter valid GST email id");
                        edTxtGstEmail.requestFocus();
                    }
                    else {
                        Toast.makeText(SettingActivity.this,"Save Sucessfully",Toast.LENGTH_SHORT).show();

                        settingModel.setMobile(mobile);
                        settingModel.setEmail(email);
                        settingModel.setAccountno(acno);
                        settingModel.setIfsc(ifsc);
                        settingModel.setPanno(panno);
                        settingModel.setGstno(gstno);
                        settingModel.setComp_address(compaddress);
                        settingModel.setContact_no(contactno);
                        settingModel.setComp_name(compname);
                        settingModel.setGst_mail(gstmailid);

                        /*Save in setting shared preference*/
                        new SettingPreference(SettingActivity.this).setSettinginfo(settingModel);

                        finish();
                    }

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /*Initiat Account Type list*/
    public ArrayList<String> getAccountReportList(){

        ArrayList<String> list=new ArrayList<>();
        list.add("Ledger Report");
        list.add("Review Detail");
        list.add("Recharge Detail");

        return list;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
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
}
