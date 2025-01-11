package com.bhanit.games.tapthegrey.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.bhanit.games.tapthegrey.R;


public class KeyUtils {
    private static final String TAG = KeyUtils.class.getSimpleName();
    Context mContext;

    public KeyUtils(Context mContext) {
        this.mContext = mContext;
    }

    public static AlertDialog showAlertDialog(final Runnable runnable, String message, Context context) {
        Log.d(TAG, "showAlertDialog(), Message is: " + message);
        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        runnable.run();
                    }
                }).setIcon(R.drawable.ic_alert);
        android.app.AlertDialog alert = alertDialog.create();
        alert.show();
        return alert;
    }


    public static void setLayout(ConstraintLayout constraintLayout, TextView textView) {

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        constraintSet.connect(textView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 0);
        constraintSet.applyTo(constraintLayout);
    }

    public static void hideSoftKeyboard(Activity activity, View view) {
        Log.d(TAG, "hideSoftKeyboard()");
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } else {
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public static void alertMessage(String message, Context context) {
        Log.d(TAG, "alertMessage(), Message is: " + message);
        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> alertDialog.setCancelable(true));
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public static ProgressDialog showProgressDialog(Activity activity, String message) {
        Log.d(TAG, "showProgressDialog(), Message is: " + message);
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(message);
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    /*public static byte[] getSymmetricKey(Context context) {
        Log.d(TAG, "getSymmetricKey()");
        Log.d("KeyUtils", " " + PreferencesHelper.getStringParameter(context, PreferencesHelper.SYMMETRIC_KEY_PREFRENCES, null));
        return Hex.decode(PreferencesHelper.getStringParameter(context, PreferencesHelper.SYMMETRIC_KEY_PREFRENCES, null));
    }*/

    public static String convertIntoUpperCase(String text) { //CONVERT FIRST ALPHABET INTO CAPS OF TEXT
        Log.d(TAG, "convertIntoUpperCase(), Text is: " + text);
        if (text != null) {
            StringBuilder stringBuilder = new StringBuilder();
            String[] textStringArray = text.trim().split(" ");
            for (String getText : textStringArray) {
                if (getText.length() >= 2) {
                    String firstAlphabetCaps = getText.substring(0, 1).toUpperCase() + getText.substring(1);
                    stringBuilder.append(firstAlphabetCaps);
                    stringBuilder.append(" ");
                } else if (getText.length() == 1) {
                    stringBuilder.append(getText.toUpperCase());
                    stringBuilder.append(" ");
                }
            }
            return stringBuilder.toString().trim();
        }
        return "";
    }

    public void hideSoftKeypad(Context context, IBinder windowToken) {
        Log.d(TAG, "Method : hideSoftKeypad() is being called ");
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(windowToken, 0);
        // hideSystemUI();
    }
}
