package in.discountmart.utility_services.billpayment.billpay_shared_preferance;

import android.content.Context;
import android.content.SharedPreferences;

import in.discountmart.utility_services.constants.SharedConstant;

public class BillPaySharedPreferance {

    private static SharedPreferences getSharedPrefrences(Context context) {
        return context.getSharedPreferences(SharedConstant.BILLPAY_SHARED, Context.MODE_PRIVATE);
    }

    public static void setServiceType(Context context, String service) {
        SharedPreferences.Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.BILLPAY_SERVICE_TYPE, service);
        edit.commit();
    }
    public static String getServiceType(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.BILLPAY_SERVICE_TYPE, "");
    }

    public static void setServiceTypeID(Context context, String serviceid) {
        SharedPreferences.Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.BILLPAY_SERVICE_TYPE_ID, serviceid);
        edit.commit();
    }
    public static String getServiceTypeID(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.BILLPAY_SERVICE_TYPE_ID, "");
    }


}
