package com.stoyanov.onetap.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.stoyanov.onetap.R;

/**
 * Created by alexander on 6/1/16.
 */
public class PermissionManager {

    public static void showPermissionDialog(Activity activity, String permission, int permissionCode) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                Toast.makeText(activity, permission + " " + activity.getString(R.string.not_granted), Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission},
                        permissionCode);
            }
        }
    }
}
