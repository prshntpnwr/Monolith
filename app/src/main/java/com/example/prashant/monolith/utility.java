package com.example.prashant.monolith;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class utility {
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) return false;
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile_info = conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi_info = conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mobile_info == null || wifi_info == null) return false;
        if (mobile_info.getState() == NetworkInfo.State.CONNECTED
                || wifi_info.getState() == NetworkInfo.State.CONNECTED) {

            return true;

        } else if (mobile_info.getState() == NetworkInfo.State.DISCONNECTED
                || wifi_info.getState() == NetworkInfo.State.DISCONNECTED) {

            return false;
        }
        return false;
    }
}
