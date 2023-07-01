package in.discountmart.utility_services.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import in.discountmart.utility_services.model.SettingModel;


public class SettingPreference {

    public static String name ="name";
    public static String mobile ="mobile";
    //public static String Id ="Id";
    public static String email="email";
    public static String accountno="accountno";
    public static String ifsc= "ifsc";
    public static String panno ="panno";
    public static String gstno ="gstno";
    public static String gst_mail="gst_mail";
    public static String comp_no="comp_no";
    public static String comp_address ="comp_address";
    public static String contact_no="contact_no";



    SharedPreferences settingPreferences;

    public SettingPreference(Context context) {
        settingPreferences = context.getSharedPreferences("SettingPref", Context.MODE_PRIVATE);
    }

    /*----get set  user login information----*/

    public SettingModel getSettingItem() {
        try {
            SettingModel m= new SettingModel();

            m.setName(getString(name));
            m.setMobile(getString(mobile));
            //m.setId(getString(Id));
            m.setAccountno(getString(accountno));
            m.setEmail(getString(email));
            m.setPanno(getString(panno));
            m.setIfsc(getString(ifsc));
            m.setGstno(getString(gstno));
            m.setGst_mail(getString(gst_mail));
            m.setComp_name(getString(comp_no));
            m.setContact_no(getString(contact_no));
            m.setComp_address(getString(comp_address));

            return m;
        } catch (Exception e) {
            Log.d("SETTPREF", e.toString());
            return new SettingModel();

        }
    }

    public void setSettinginfo(SettingModel settingmodal) {
        try {

            setString(name, settingmodal.getName());
            setString(mobile, settingmodal.getMobile());
            setString(email, settingmodal.getEmail());
            setString(accountno, settingmodal.getAccountno());
            setString(ifsc, settingmodal.getIfsc());
            setString(panno, settingmodal.getPanno());
            setString(gstno, settingmodal.getGstno());
            setString(gst_mail, settingmodal.getGst_mail());
            setString(comp_no, settingmodal.getComp_name());
            setString(contact_no, settingmodal.getContact_no());
            setString(comp_address, settingmodal.getComp_address());


        } catch (Exception e) {
            Log.d("SETTPREF", e.toString());
        }
    }

    /*----get set  user login already----*//*

    public void setLoggedAlredy(Boolean flag) {
        try {
            setBoolean(Login_Alredy, flag);

        } catch (Exception e) {
            Log.d("LOGPREF", e.toString());

        }
    }

    public boolean getLoggedAlredy() {
        boolean flag=false;
        try {
            flag=getBoolean(Login_Alredy);
            return flag;
        } catch (Exception e) {
            Log.d("LOGPREF", e.toString());
            return flag;

        }
    }
*/
    //get set OTP information
/*
    public void setOTP(String otp) {
        try {
            setString(OTP, otp);

        } catch (Exception e) {
            Log.d("LOGPREF", e.toString());

        }
    }

    public String getOTP() {
        String otp="";
        try {
            otp=getString(OTP);
        } catch (Exception e) {
            Log.d("LOGPREF", e.toString());
        }
        return otp;
    }

    public void setOtp_expire(String otp_expire) {

        try {
            setString(OTPExpire, otp_expire);
        }catch (Exception e){
            Log.d("LogPrf",e.toString());
        }

    }

    public String getOtp_expire() {
        String otpExpire="";
        try{
                otpExpire=getString(OTPExpire);
    }catch(Exception e){
            Log.d("LogPref",e.toString());

        }

        return otpExpire;
    }

    public void setOtp_attempt(String otp_attempt) {

        try {
            setString(OTPAttemp, otp_attempt);
        }catch (Exception e){
            Log.d("LogPrf",e.toString());
        }

    }

    public String getOtp_attempt() {
        String otpAttempt="";
        try{
            otpAttempt=getString(OTPAttemp);
        }catch(Exception e){
            Log.d("LogPref",e.toString());

        }

        return otpAttempt;
    }*/


    /*---set or get or remove srting and interger key/values---*/


    public String getString(String key) {
        String value = settingPreferences.getString(key, "");
        return value;
    }

    public void setString(String key, String val) {
        SharedPreferences.Editor edit = settingPreferences.edit();
        edit.putString(key, val);
        edit.commit();
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor edit = settingPreferences.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public int getInt(String key) {
        int value = settingPreferences.getInt(key, 0);
        return value;
    }

    public void removeKey(String key) {
        SharedPreferences.Editor edit = settingPreferences.edit();
        edit.remove(key);
        edit.commit();
    }

    public void setBoolean(String key, boolean val) {
        SharedPreferences.Editor edit = settingPreferences.edit();
        edit.putBoolean(key, val);
        edit.commit();
    }

    public boolean getBoolean(String key) {
        return settingPreferences.getBoolean(key, false);
    }
}
