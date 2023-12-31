package in.discountmart.business.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 */
public class ConnectivityUtils {

	private static final String LOG_TAG = "ConnectivityUtils";

	/**
	 * @param pContext
	 * @return
	 */
	public static boolean isNetworkEnabled(Context pContext) {
		NetworkInfo activeNetwork = getActiveNetwork(pContext);
		return activeNetwork != null && activeNetwork.isConnected();
	}

	/**
	 * @param pContext
	 * @return
	 */
	public static void logNetworkState(Context pContext) {
		NetworkInfo activeNetwork = getActiveNetwork(pContext);
		if (activeNetwork == null) {
			Log.i(LOG_TAG, "No any active network found.");
			return;
		}
		Log.i(LOG_TAG, "Active Network. Type: " + activeNetwork.getTypeName());
		Log.i(LOG_TAG, "Active Network. isConnected: " + activeNetwork.isConnected());
		Log.i(LOG_TAG, "Active Network. isConnectedOrConnecting: " + activeNetwork.isConnectedOrConnecting());
		Log.i(LOG_TAG, "Active Network. N/W State Reason: " + activeNetwork.getReason());
	}

	/**
	 * @param pContext
	 * @return
	 */
	public static NetworkInfo getActiveNetwork(Context pContext) {
		ConnectivityManager conMngr = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		return conMngr == null ? null : conMngr.getActiveNetworkInfo();
	}

	/**
	 * @param pContext
	 * @return
	 */
	public static boolean isGpsEnabled(Context pContext) {
		LocationManager locationManager = (LocationManager) pContext.getSystemService(Context.LOCATION_SERVICE);
		return locationManager != null
				&& (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager
						.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
	}
	
	/**
     * Returns MAC address of the given interface name.
     * @param interfaceName eth0, wlan0 or NULL=use first interface 
     * @return  mac address or empty string
     * 
     * String wLanMac = ConnectivityUtils.getMACAddress("wlan0");
     * String ethMac = ConnectivityUtils.getMACAddress("eth0");
     */
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac==null) return "";
                StringBuilder buf = new StringBuilder();
                for (int idx=0; idx<mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
                return buf.toString();
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
        /*try {
            // this is so Linux hack
            return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
        } catch (IOException ex) {
            return null;
        }*/
    }
    
    @SuppressLint("MissingPermission")
	public static String getImeiNumber(Context context){
    	TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    	return tm.getDeviceId();
    }

}