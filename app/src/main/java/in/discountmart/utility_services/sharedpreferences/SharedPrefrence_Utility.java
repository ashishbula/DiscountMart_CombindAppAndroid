
package in.discountmart.utility_services.sharedpreferences;



import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import in.discountmart.utility_services.constants.SharedConstant;

public class SharedPrefrence_Utility {
	 /**
     * To get shared prefrences of Channel 9
     * 
     * @param context
     * @return
     */
    private static SharedPreferences getSharedPrefrences(Context context) {
        return context.getSharedPreferences(SharedConstant.UTILITY_SHARED, Context.MODE_PRIVATE);
    }


    public static void setUserMobileNumber(Context context, String userMobille) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.USERMOBILE_NO, userMobille);
        edit.commit();
    }
    public static String getUserMobileNumber(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.USERMOBILE_NO, "");
    }
    public static void setUsername(Context context, String username) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.USERNAME, username);
        edit.commit();
    }
    public static String getUsername(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.USERNAME, "");
    }

    public static void setUserID(Context context, String userID) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.USER_ID, userID);
        edit.commit();
    }
    public static String getUserID(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.USER_ID, "");
    }
    public static void setEmailId(Context context, String email) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.EMAIL_ID, email);
        edit.commit();
    }
    public static String getEmailId(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.EMAIL_ID, "");
    }

    public static void setPassword(Context context, String password) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.PASSWORD, password);
        edit.commit();
    }
    public static String getPassword(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.PASSWORD, "");
    }

    public static void setOtpNumber(Context context, String otpNum) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.OTP_Number, otpNum);
        edit.commit();
    }
    public static String getOtpNumber(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.OTP_Number, "");
    }

    public static void setOtpCode(Context context, String otpCode) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.OTP_Code, otpCode);
        edit.commit();
    }
    public static String getOtpcode(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.OTP_Code, "");
    }

    public static void setAddress(Context context, String strAddress) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.ADDRESS, strAddress);
        edit.commit();
    }
    public static String getAddress(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.ADDRESS, "");
    }


    public static void setServiceWalletBalance(Context context, String strServiceBal) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.SERVICE_WALLET_BAL, strServiceBal);
        edit.commit();
    }
    public static String getServiceWalletBalance(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.SERVICE_WALLET_BAL, "");
    }

    public static void setMainWalletBalance(Context context, String strMainBal) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.MAIN_WALLET_BAL, strMainBal);
        edit.commit();
    }
    public static String getMainWalletBalance(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.MAIN_WALLET_BAL, "");
    }

    public static void setPurchaseWalletBalance(Context context, String purchaseWBal) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.PURCHASE_WALLET_BAL, purchaseWBal);
        edit.commit();
    }
    public static String getPurchaseWalletBalance(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.PURCHASE_WALLET_BAL, "");
    }

    public static void setRewardWalletBalance(Context context, String strRewardBal) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.REWARD_WALLET_BAL, strRewardBal);
        edit.commit();
    }
    public static String getRewardWalletBalance(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.REWARD_WALLET_BAL, "");
    }

    public static void setRepurchaseWalletBalance(Context context, String strRepurchaseBal) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.REPURCHASE_WALLET_BAL, strRepurchaseBal);
        edit.commit();
    }

    public static String getShoppingWalletBalance(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.SHOPPING_WALLET_BAL, "");
    }

    public static void setShoppingWalletBalance(Context context, String strshoppingBal) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.SHOPPING_WALLET_BAL, strshoppingBal);
        edit.commit();
    }
    public static String getRepurchaseWalletBalance(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.REPURCHASE_WALLET_BAL, "");
    }

    public static void setStockPointWalletBalance(Context context, String strStockpointBal) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.STOCK_POINT_WALLET_BAL, strStockpointBal);
        edit.commit();
    }
    public static String getStockPointWalletBalance(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.STOCK_POINT_WALLET_BAL, "");
    }

    public static void setProfileIamge(Context context, String imageUrl) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.UPLOAD_IMAGE, imageUrl);
        edit.commit();
    }
    public static String getProfileIamge(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.UPLOAD_IMAGE, "");
    }

    public static void setIsFrenchise(Context context, String frenchise) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.FRENCHISE, frenchise);
        edit.commit();
    }
    public static String getIsFrenchise(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.FRENCHISE, "");
    }

    public static void setIsActive(Context context, String active) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.ISACTIVE, active);
        edit.commit();
    }
    public static String getIsActive(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.ISACTIVE, "");
    }

    public static void setCouponRequest(Context context, String coupon) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.COUPON, coupon);
        edit.commit();
    }
    public static String getCouponRequest(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.COUPON, "");
    }

    public static void setToken(Context context, String token) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.TOKEN, token);
        edit.commit();
    }
    public static String getToken(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.TOKEN, "");
    }

    public static void setService(Context context, String service) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.SERVICE_TYPE, service);
        edit.commit();
    }
    public static String getService(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.SERVICE_TYPE, "");
    }

    public static void setPromocode(Context context, String promocode) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.PROMOCODE, promocode);
        edit.commit();
    }
    public static String getPromocode(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.PROMOCODE, "");
    }

    public static void setPromoDiscount(Context context, String promodiscount) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.PROMO_DISCOUNT, promodiscount);
        edit.commit();
    }
    public static String getPromoDiscount(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.PROMO_DISCOUNT, "");
    }

    public static void setServicesId(Context context, String servicesId) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.SERVICES_ID, servicesId);
        edit.commit();
    }
    public static String getServicesId(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.SERVICES_ID, "");
    }


    public static void setServicesType(Context context, String servicesId) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.SERVICES_TYPE, servicesId);
        edit.commit();
    }
    public static String getServicesType(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.SERVICES_TYPE, "");
    }
    public static void setServicesName(Context context, String servicesId) {
        Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.SERVICES_NAME, servicesId);
        edit.commit();
    }
    public static String getServicesName(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.SERVICES_NAME, "");
    }



}
