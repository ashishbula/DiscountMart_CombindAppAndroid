package in.discountmart.utility_services.recharge.recharge_shared_preferance;

import android.content.Context;
import android.content.SharedPreferences;

import in.discountmart.utility_services.constants.SharedConstant;

public class RechargeSharedPreferance {

    private static SharedPreferences getSharedPrefrences(Context context) {
        return context.getSharedPreferences(SharedConstant.RECHARGE_SHARED, Context.MODE_PRIVATE);
    }

    public static void setServiceType(Context context, String service) {
        SharedPreferences.Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.RECHARGE_SERVICE_TYPE, service);
        edit.commit();
    }
    public static String getServiceType(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.RECHARGE_SERVICE_TYPE, "");
    }
    public static void setServiceTypeId(Context context, String service) {
        SharedPreferences.Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.RECHARGE_SERVICE_TYPE_ID, service);
        edit.commit();
    }
    public static String getServiceTypeId(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.RECHARGE_SERVICE_TYPE_ID, "");
    }

    public static void setMobileServiceProvider(Context context, String service) {
        SharedPreferences.Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.RECHARGE_MOB_SERVICE_PROVIDER, service);
        edit.commit();
    }
    public static String getMobileServiceProvider(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.RECHARGE_MOB_SERVICE_PROVIDER, "");
    }
    public static void setDthServiceProvider(Context context, String service) {
        SharedPreferences.Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.RECHARGE_DTH_SERVICE_PROVIDER, service);
        edit.commit();
    }
    public static String getDthServiceProvider(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.RECHARGE_DTH_SERVICE_PROVIDER, "");
    }}
