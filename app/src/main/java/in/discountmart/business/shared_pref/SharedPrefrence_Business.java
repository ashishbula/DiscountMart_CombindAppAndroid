
package in.discountmart.business.shared_pref;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.business_constants.AppConstants;


public class SharedPrefrence_Business {
	 /**
     * To get shared prefrences of Channel 9
     * 
     * @param context
     * @return
     */
    private static SharedPreferences getSharedPrefrences(Context context) {
        return context.getSharedPreferences(ApiConstants.MAPLIFE_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }


    public static void setUserMobileNumber(Context context, String userMobille) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.USERMOBILE_NO, userMobille);
        edit.commit();
    }
    public static String getUserMobileNumber(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.USERMOBILE_NO, "");
    }
    public static void setUsername(Context context, String username) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.USERNAME, username);
        edit.commit();
    }
    public static String getUsername(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.USERNAME, "");
    }

    public static void setUserID(Context context, String userID) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.USER_ID, userID);
        edit.commit();
    }
    public static String getUserID(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.USER_ID, "");
    }
    public static void setEmailId(Context context, String email) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.EMAIL_ID, email);
        edit.commit();
    }
    public static String getEmailId(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.EMAIL_ID, "");
    }

    public static void setPassword(Context context, String password) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.PASSWORD, password);
        edit.commit();
    }
    public static String getPassword(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.PASSWORD, "");
    }

    public static void setOtp(Context context, String strOtp) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.OTP, strOtp);
        edit.commit();
    }
    public static String getOtp(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.OTP, "");
    }

    public static void setAddress(Context context, String strAddress) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.ADDRESS, strAddress);
        edit.commit();
    }
    public static String getAddress(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.ADDRESS, "");
    }


    public static void setServiceWalletBalance(Context context, String strServiceBal) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.SERVICE_WALLET_BAL, strServiceBal);
        edit.commit();
    }
    public static String getServiceWalletBalance(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.SERVICE_WALLET_BAL, "");
    }

    public static void setMainWalletBalance(Context context, String strMainBal) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.MAIN_WALLET_BAL, strMainBal);
        edit.commit();
    }
    public static String getMainWalletBalance(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.MAIN_WALLET_BAL, "");
    }

    public static void setPurchaseWalletBalance(Context context, String purchaseWBal) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.PURCHASE_WALLET_BAL, purchaseWBal);
        edit.commit();
    }
    public static String getPurchaseWalletBalance(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.PURCHASE_WALLET_BAL, "");
    }

    public static void setRewardWalletBalance(Context context, String strRewardBal) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.REWARD_WALLET_BAL, strRewardBal);
        edit.commit();
    }
    public static String getRewardWalletBalance(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.REWARD_WALLET_BAL, "");
    }

    public static void setRepurchaseWalletBalance(Context context, String strRepurchaseBal) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.REPURCHASE_WALLET_BAL, strRepurchaseBal);
        edit.commit();
    }

    public static String getShoppingWalletBalance(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.SHOPPING_WALLET_BAL, "");
    }

    public static void setShoppingWalletBalance(Context context, String strshoppingBal) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.SHOPPING_WALLET_BAL, strshoppingBal);
        edit.commit();
    }
    public static String getRepurchaseWalletBalance(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.REPURCHASE_WALLET_BAL, "");
    }

    public static void setStockPointWalletBalance(Context context, String strStockpointBal) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.STOCK_POINT_WALLET_BAL, strStockpointBal);
        edit.commit();
    }
    public static String getStockPointWalletBalance(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.STOCK_POINT_WALLET_BAL, "");
    }

    public static void setProfileIamge(Context context, String imageUrl) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.UPLOAD_IMAGE, imageUrl);
        edit.commit();
    }
    public static String getProfileIamge(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.UPLOAD_IMAGE, "");
    }

    public static void setIsFrenchise(Context context, String frenchise) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.FRENCHISE, frenchise);
        edit.commit();
    }
    public static String getIsFrenchise(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.FRENCHISE, "");
    }

    public static void setIsActive(Context context, String active) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.ISACTIVE, active);
        edit.commit();
    }
    public static String getIsActive(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.ISACTIVE, "");
    }

    public static void setCouponRequest(Context context, String coupon) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.COUPON, coupon);
        edit.commit();
    }
    public static String getCouponRequest(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.COUPON, "");
    }
    public static void setNewRegisIdNo(Context context, String coupon) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.NEW_REGISTER_ID, coupon);
        edit.commit();
    }
    public static String getNewRegisIdNo(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.NEW_REGISTER_ID, "");
    }

    public static void setNewRegisterUrl(Context context, String coupon) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.NEW_REGISTER_URL, coupon);
        edit.commit();
    }
    public static String getNewRegisterUrl(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.NEW_REGISTER_URL, "");
    }

    public static void setNewRegisterFormNo(Context context, String coupon) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.NEW_REGISTER_FORMNO, coupon);
        edit.commit();
    }
    public static String getNewRegisterFormNo(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.NEW_REGISTER_FORMNO, "");
    }
    public static void setIsLogin(Context context, String isLogin) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.IS_LOGIN, isLogin);
        edit.commit();
    }

    public static String getIsLogin(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.IS_LOGIN, "");
    }

    public static void setApiKey(Context context, String strApikey) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.API_KEY, strApikey);
        edit.commit();
    }
    public static String getApiKey(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.API_KEY, "");
    }

    public static void setPackage(Context context, String strApikey) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(AppConstants.PACKAGE, strApikey);
        edit.commit();
    }
    public static String getPackage(Context context) {
        return getSharedPrefrences(context).getString(AppConstants.PACKAGE, "");
    }



}
