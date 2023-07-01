package in.base.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;


public class PermissionUtil {

    private static Activity activity;
    private static PermissionUtil instance;
    private PermissionGrantedListener permissionGrantedListener;
    private int mRequestCode = 1026;
    private String message = "Please Grant Permissions";

    private PermissionUtil() {
        // do nothing
    }

    public static PermissionUtil with(Activity context) {
        activity = context;
        if (instance == null) {
            instance = new PermissionUtil();
        }
        return instance;
    }

    public PermissionUtil setCallback(PermissionGrantedListener permissionGrantedListener) {
        this.permissionGrantedListener = permissionGrantedListener;
        return instance;
    }

    public PermissionUtil setRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return instance;
    }

    public PermissionUtil setRationaleMessage(String message) {
        this.message = message;
        return instance;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == mRequestCode) {

            // for multiple permissions case
//            int permissionCheck = PackageManager.PERMISSION_GRANTED;
//            for (int permission : grantResults) {
//                permissionCheck = permissionCheck + permission;
//            }
//            if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {

                // Check if the only required permission has been granted
            if ((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                updateResult(true);
            } else {
                //Permission not granted
                updateResult(false);
                Snackbar.make(activity.findViewById(android.R.id.content), "Enable Permissions from settings",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                activity.startActivity(intent);
                            }
                        }).show();
            }

        }
    }

    public void validate(final String permission) {
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            // permission has not been granted.
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                //This is called if user has denied the permission before
                Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Okay", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ActivityCompat.requestPermissions(activity, new String[]{permission}, mRequestCode);
                            }
                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, mRequestCode);
            }
        } else {
            updateResult(true);
        }
        message = "Please Grant Permissions";
    }


    private void updateResult(boolean isGranted) {
        if (permissionGrantedListener != null) {
            permissionGrantedListener.onPermissionResult(isGranted, mRequestCode);
        }
    }

    public interface PermissionGrantedListener {
        void onPermissionResult(boolean isGranted, int requestCode);
    }
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
