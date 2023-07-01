package in.discountmart.utility_services.sharedpreferences;

/**
 * Created by Lenovo on 12-09-2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import in.discountmart.utility_services.model.response_model.LoginResponse;


public class LoginPreferences_Utility {

    public static String Sno ="Sno";
    public static String MemId ="MemId";
    //public static String Id ="Id";
    public static String UserName="UserName";
    public static String Passwd="Passwd";
    public static String MemName= "MemName";
    public static String PanNo ="PanNo";
    public static String Address ="Address";
    public static String City="City";
    public static String MobileNo="MobileNo";
    public static String EmailId ="EmailId";
    public static String RegDate="RegDate";
    public static String ActiveStatus="ActiveStatus";
    public static String Amount="Amount";
    public static String FromNo="FormNo";
    public static String SponsorFromNo="SponsorFormNo";
    public static String CreditLimit="creditlimit";
    public static String MemberMode="MemMode";
    public static String GroupId="GroupId";
    public static String CompanyMailId="CmpMailId";
    public static String MobileNo2="MobileNo2";
    public static String LastName="LastName";
    public static String IsAdmin="IsAdmin";
    public static String CompanyName= "CompanyName";
    public static String RechargeGroupId="RechargeGroupId";
    public static String HotelGroupId="HotelGroupId";
    public static String BillGroupId= "BillGroupId";
    public static String BusGroupId= "BusGroupId";
    public static String CabGroupId= "CabGroupId";
    public static String CompanyMobileNo="CompanyMobileNo";
    public static String Balance = "Balance";
    public static String DomainName = "DomainName";
    public static String AccountType = "AccountType";
    public static String TokenKey = "TokenKey";
    public static String IsLogin = "IsLogin";


    SharedPreferences loginPreferences;

    public LoginPreferences_Utility(Context context) {
        loginPreferences = context.getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
    }

	/*----get set  user login information----*/

    public LoginResponse getLoggedinUser() {
        try {
            LoginResponse m= new LoginResponse();

            m.setSno(getString(Sno));
            m.setMemId(getString(MemId));
            //m.setId(getString(Id));
            m.setUserName(getString(UserName));
            m.setPasswd(getString(Passwd));
            m.setMemName(getString(MemName));
            m.setPanNo(getString(PanNo));
            m.setAddress(getString(Address));
            m.setCity(getString(City));
            m.setMobileNo(getString(MobileNo));
            m.setEmailId(getString(EmailId));
            m.setRegDate(getString(RegDate));
            m.setActiveStatus(getString(ActiveStatus));
            m.setAmount(getString(Amount));
            m.setFormNo(getString(FromNo));
            m.setSponsorFormNo(getString(SponsorFromNo));
            m.setCreditlimit(getString(CreditLimit));
            m.setMemMode(getString(MemberMode));
            m.setGroupId(getString(GroupId));
            m.setCmpMailId(getString(CompanyMailId));
            m.setMobileNo2(getString(MobileNo2));
            m.setLastName(getString(LastName));
            m.setIsAdmin(getString(IsAdmin));
            m.setCompanyName(getString(CompanyName));
            m.setRechargeGroupId(getString(RechargeGroupId));
            m.setHotelGroupId(getString(HotelGroupId));
            m.setBillGroupId(getString(BillGroupId));
            m.setBusGroupId(getString(BusGroupId));
            m.setCabGroupId(getString(CabGroupId));
            m.setCompanyMobileNo(getString(CompanyMobileNo));
            m.setBalance(getString(Balance));
            m.setDomainName(getString(DomainName));
            m.setAcType(getString(AccountType));
            m.setTokenKey(getString(TokenKey));
            m.setIsLogin(getString(IsLogin));


            return m;
        } catch (Exception e) {
            Log.d("LOGPREF", e.toString());
            return new LoginResponse();

        }
    }

    public void setLoggedinUser(LoginResponse loginUserGetResponseEntity) {
        try {

            setString(Sno, loginUserGetResponseEntity.getSno());
            setString(MemId, loginUserGetResponseEntity.getMemId());
            setString(UserName, loginUserGetResponseEntity.getUserName());
            setString(Passwd, loginUserGetResponseEntity.getPasswd());
            setString(MemName, loginUserGetResponseEntity.getMemName());
            setString(PanNo, loginUserGetResponseEntity.getPanNo());
            setString(Address, loginUserGetResponseEntity.getAddress());
            setString(City, loginUserGetResponseEntity.getCity());
            setString(MobileNo, loginUserGetResponseEntity.getMobileNo());
            setString(EmailId, loginUserGetResponseEntity.getEmailId());
            setString(RegDate, loginUserGetResponseEntity.getRegDate());
            setString(ActiveStatus, loginUserGetResponseEntity.getActiveStatus());
            setString(Amount, loginUserGetResponseEntity.getAmount());
            setString(FromNo, loginUserGetResponseEntity.getFormNo());
            setString(SponsorFromNo, loginUserGetResponseEntity.getSponsorFormNo());
            setString(CreditLimit, loginUserGetResponseEntity.getCreditlimit());
            setString(MemberMode, loginUserGetResponseEntity.getMemMode());
            setString(GroupId, loginUserGetResponseEntity.getGroupId());
            setString(CompanyMailId, loginUserGetResponseEntity.getCmpMailId());
            setString(MobileNo2, loginUserGetResponseEntity.getMobileNo2());
            setString(LastName, loginUserGetResponseEntity.getLastName());
            setString(IsAdmin, loginUserGetResponseEntity.getIsAdmin());
            setString(CompanyName, loginUserGetResponseEntity.getCompanyName());
            setString(RechargeGroupId, loginUserGetResponseEntity.getRechargeGroupId());
            setString(HotelGroupId, loginUserGetResponseEntity.getHotelGroupId());
            setString(BillGroupId, loginUserGetResponseEntity.getBillGroupId());
            setString(CabGroupId, loginUserGetResponseEntity.getCabGroupId());
            setString(BusGroupId, loginUserGetResponseEntity.getBusGroupId());
            setString(CompanyMobileNo, loginUserGetResponseEntity.getCompanyMobileNo());
            setString(Balance, loginUserGetResponseEntity.getBalance());
            setString(DomainName, loginUserGetResponseEntity.getDomainName());
            setString(AccountType, loginUserGetResponseEntity.getAcType());
            setString(TokenKey, loginUserGetResponseEntity.getTokenKey());
            setString(IsLogin, loginUserGetResponseEntity.getIsLogin());


        } catch (Exception e) {
            Log.d("LOGPREF", e.toString());
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
        String value = loginPreferences.getString(key, "");
        return value;
    }

    public void setString(String key, String val) {
        Editor edit = loginPreferences.edit();
        edit.putString(key, val);
        edit.commit();
    }

    public void setInt(String key, int value) {
        Editor edit = loginPreferences.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public int getInt(String key) {
        int value = loginPreferences.getInt(key, 0);
        return value;
    }

    public void removeKey(String key) {
        Editor edit = loginPreferences.edit();
        edit.remove(key);
        edit.commit();
    }

    public void setBoolean(String key, boolean val) {
        Editor edit = loginPreferences.edit();
        edit.putBoolean(key, val);
        edit.commit();
    }

    public boolean getBoolean(String key) {
        return loginPreferences.getBoolean(key, false);
    }
}
