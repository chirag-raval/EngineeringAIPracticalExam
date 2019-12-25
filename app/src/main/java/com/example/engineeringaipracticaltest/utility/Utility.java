package com.example.engineeringaipracticaltest.utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.engineeringaipracticaltest.R;


/**
 * This class contains all the common methods used in application
 */

public class Utility {
    private static Dialog dialog;

    //log with tag and value
    public static void log(String tag, String value) {
        Log.e(tag + "", value + "");
    }


    /**
     * This function check internet connection availability in device.
     *
     * @param mContext
     * @return
     */

    public static synchronized boolean isNetworkAvailable(Context mContext) {
        NetworkInfo networkInfo = null;
        try {
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo = cm.getActiveNetworkInfo();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * This method is used to show progress bar when web service called
     *
     * @param context
     * @param cancellable
     */

    public static void showProgress(Context context, boolean cancellable) {
        if (context == null)
            return;

        if (checkProgressOpen())
            return;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_progress);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(cancellable);
        try {
            dialog.show();
        } catch (Exception e) {
            Utility.log("Exception ", e.getMessage());
        }
    }


    /**
     * This method first check if the progress bar already open or not
     *
     * @return
     */

    public static boolean checkProgressOpen() {
        if (dialog != null && dialog.isShowing())
            return true;
        else
            return false;
    }

    /**
     * This method is used to cancel progressbar
     */

    public static void cancelProgress() {
        if (checkProgressOpen()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                Utility.log("Exception ", e.getMessage());
            }
            dialog = null;
        }
    }
}