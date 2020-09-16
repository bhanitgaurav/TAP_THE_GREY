package com.bhanit.tapthegrey.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.bhanit.tapthegrey.R;

import java.util.Objects;


public class KeyUtils {
    private static final String TAG = KeyUtils.class.getSimpleName();
    public static boolean isFavFetchFromNetwork;
    public static boolean isMemberFetchFromNetwork;
    public static long mLastAppointmentId;
    public static int mRatingPoint;
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

    public static void alertRectangularDialog(final Activity activity, String message, String smallMessage, String buttonOk, Drawable imageShown, final boolean isFromBookAppointment) {
        Log.d(TAG, "alertRectangularDialog()");
        final Dialog openDialog = new Dialog(activity);
        openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(openDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        openDialog.setContentView(R.layout.custom_permission_dialog);
        ImageView image = openDialog.findViewById(R.id.image_view);
        image.setImageDrawable(imageShown);
        image.setVisibility(View.VISIBLE);
        TextView dialogTextContent = openDialog.findViewById(R.id.heading);
        TextView smallText = openDialog.findViewById(R.id.description);
        openDialog.findViewById(R.id.not_allow).setVisibility(View.GONE);
        setLayout(openDialog.findViewById(R.id.main_layout), openDialog.findViewById(R.id.allow));
        dialogTextContent.setText(message);
        smallText.setText(smallMessage);

        TextView dialogBookButton = openDialog.findViewById(R.id.allow);
        openDialog.setCanceledOnTouchOutside(true);
        dialogBookButton.setText(buttonOk);

        dialogBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog.dismiss();
                if (isFromBookAppointment) {
                   /* activity.finish();
                    Intent intent = new Intent(activity, DashboardActivity.class);
//                    intent.putExtra(DashboardActivity.EXTRA_FLAG_KEY, true);
                    activity.startActivity(intent);*/
                }
            }
        });
        openDialog.show();
    }


    public static void setLayout(ConstraintLayout constraintLayout, TextView textView) {

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        constraintSet.connect(textView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 0);
        constraintSet.applyTo(constraintLayout);
    }

/*    public static void triggerClickEvent(FirebaseAnalytics firebaseAnalytics, String item) {
        Log.d(TAG, "triggerClickEvent(), Item is: " + item);
        Bundle bundle = new Bundle();
        firebaseAnalytics.logEvent(item, bundle);
    }*/

    /* public void setSnackBar(View viewGroup, String message) {
         Log.d(TAG, "setSnackBar(), Message is: " + message);

         Snackbar snackbar = Snackbar.make(viewGroup, message, Snackbar.LENGTH_LONG);
         View view = snackbar.getView();
         TextView textView = view.findViewById(com.google.android.material.R.id.snackbar_text);
         textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
         snackbar.setActionTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary)).show();
     }
 */
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

    public static void clearValues() {
        isFavFetchFromNetwork = false;
        isMemberFetchFromNetwork = false;
    }

    public void hideSoftKeypad(Context context, IBinder windowToken) {
        Log.d(TAG, "Method : hideSoftKeypad() is being called ");
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(windowToken, 0);
        // hideSystemUI();
    }
}
