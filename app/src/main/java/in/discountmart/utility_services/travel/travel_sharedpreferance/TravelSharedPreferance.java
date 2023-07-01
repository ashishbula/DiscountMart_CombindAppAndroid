package in.discountmart.utility_services.travel.travel_sharedpreferance;

import android.content.Context;
import android.content.SharedPreferences;

import in.discountmart.utility_services.constants.SharedConstant;

public class TravelSharedPreferance {
    private static SharedPreferences getSharedPrefrences(Context context) {
        return context.getSharedPreferences(SharedConstant.TRAVEL_SHARED, Context.MODE_PRIVATE);
    }

    public static void setFlightSources(Context context, String flightSources) {
        SharedPreferences.Editor edit = getSharedPrefrences(context).edit();
        edit.putString(SharedConstant.FLIGHT_SOURCES, flightSources);
        edit.commit();
    }
    public static String getFlightSources(Context context) {
        return getSharedPrefrences(context).getString(SharedConstant.FLIGHT_SOURCES, "");
    }

}
