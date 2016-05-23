package com.surveydemoapp.Global;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings.Secure;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static String PREFS_ROOT = "survery_pref";


	/**
	 * for checking the network and wifi state for internet connectivity.
	 */
	public static boolean checkNetworkConnection(Context c) {
		ConnectivityManager connectivityManager = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	/**
	 * for printing the value.
	 */
	public static void SOP(String message) {
		if (message != null) {
			System.out.println(message);
		}
	}

	/**
	 * for hiding keyboard
	 */
	public static void hideSoftKeyboard(Activity activity) {
		try {
			if (activity != null) {
				InputMethodManager inputMethodManager = (InputMethodManager) activity
						.getSystemService(Activity.INPUT_METHOD_SERVICE);
				View v = activity.getCurrentFocus();
				if (v != null) {
					IBinder binder = activity.getCurrentFocus()
							.getWindowToken();
					if (binder != null) {
						inputMethodManager.hideSoftInputFromWindow(binder, 0);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/**
	 * for Short Toast
	 */
	public static void showToastS(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * for Long Toast
	 */
	public static void showToastL(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	/**
	 * Create static Save Preference method
	 */
	public static void savePreferenceValues(Context context, String key,String value) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_ROOT, context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();

	}

	/**
	 * Create static Get Preference method
	 */
	public static String getPreferenceValues(Context context, String key) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_ROOT, context.MODE_PRIVATE);
		return prefs.getString(key, "");

	}

	public static void ClearSharedPrefence(Context context) {

		SharedPreferences prefs = context.getSharedPreferences(PREFS_ROOT,context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.clear();
		editor.commit();

	}

    public static void Alert_Dialog(final Activity c, final String msg, final String receiver_id)

    {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(c);
        alertDialog.setMessage(msg);

        alertDialog.setPositiveButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.cancel();

                    }
                });

        alertDialog.setNegativeButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                         c.finish();
                    }
                });

        alertDialog.show();

    }

}
