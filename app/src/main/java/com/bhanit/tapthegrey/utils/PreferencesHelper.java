package com.bhanit.tapthegrey.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bhanit.tapthegrey.helper.Log;
import com.bhanit.tapthegrey.utils.constants.TapTheGrey;


public class PreferencesHelper {
    private static final String TAG = PreferencesHelper.class.getSimpleName();
    public static final String SYMMETRIC_KEY_PREFRENCES = "symmetric_key";
    private static final String USER_PREFERENCES = "tap_the_grey_User";
    public static final String PREFERENCE_TOKEN = "token";
    public static final String SESSION = "session";
    private static final String RATING_DIRTY = "rating_dirty";


    public PreferencesHelper() {
    }

    public static void saveToken(Context context, String patientToken) {
        Log.d(TAG, "saveToken(), PatientToken is: " + patientToken);
        if (context != null) {
            SharedPreferences.Editor editor = getEditor(context);
            editor.putString(PREFERENCE_TOKEN, patientToken);
            editor.apply();
        }
    }

    public static String getLatitude(Context context) {
        Log.d(TAG, "getLatitude()");
        SharedPreferences sharedPreferences = PreferencesHelper.getSharedPreferences(context);
        return sharedPreferences.getString(TapTheGrey.SharedPreferences.LATITUDE, null);
    }

    public static String getLongitude(Context context) {
        Log.d(TAG, "getLongitude()");
        SharedPreferences sharedPreferences = PreferencesHelper.getSharedPreferences(context);
        return sharedPreferences.getString(TapTheGrey.SharedPreferences.LONGITUDE, null);
    }

    public static boolean getIsNearBySelected(Context context) {
        Log.d(TAG, "getIsNearBySelected()");
        SharedPreferences sharedPreferences = PreferencesHelper.getSharedPreferences(context);
        return sharedPreferences.getBoolean(TapTheGrey.SharedPreferences.IS_NEARBY_SELECTED, false);
    }

    public static void writeIsNearBySelected(Context context, boolean isNearBySelected) {
        Log.d(TAG, "writeIsNearBySelected(), IsNearBySelected: " + isNearBySelected);
        SharedPreferences.Editor editor = PreferencesHelper.getEditor(context);
        editor.putBoolean(TapTheGrey.SharedPreferences.IS_NEARBY_SELECTED, isNearBySelected);
        editor.apply();
    }

    public static void writeLatLong(Context context, String latitude, String longitude) {
        Log.d(TAG, "writeLatLong(), Latitude: " + latitude + ", Longitude: " + longitude);
        SharedPreferences.Editor editor = PreferencesHelper.getEditor(context);
        editor.putString(TapTheGrey.SharedPreferences.LATITUDE, latitude);
        editor.putString(TapTheGrey.SharedPreferences.LONGITUDE, longitude);
        editor.apply();
    }

    public static void writeForRating(Context context, boolean isRatingAllowed) {
        Log.d(TAG, "writeForRating(), RatingAllowed is: " + isRatingAllowed);
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(RATING_DIRTY, isRatingAllowed);
        editor.apply();
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        Log.d(TAG, "getSharedPreferences()");
        return context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static String getStringParameter(Context context, String key, String defaultValue) {
        Log.d(TAG, "getStringParameter(), Key is: " + key);
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getString(key, defaultValue);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        Log.d(TAG, "getEditor()");
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }


    public static void clearNotificationData(Context context) {
        Log.d(TAG, "clearNotificationData()");
        SharedPreferences.Editor editor = getEditor(context);
        assert editor != null;
        editor.putString(TapTheGrey.NotificationConstants.BUILDER, null);
        editor.apply();
    }

    public static void writeSymmetricKeyPreferences(Context context, String symmetricKey) {
        Log.d(TAG, "writeSymmetricKeyPreferences(), Context: " + context + "SymmetricKey: " + symmetricKey);
        if (context != null) {
            SharedPreferences.Editor editor = getEditor(context);
            editor.putString(SYMMETRIC_KEY_PREFRENCES, symmetricKey);
            editor.apply();
        }
    }

    /*public static void savePatientDetail(Context context, PatientDetail patientDetail) {
        Log.d(TAG, "savePatientDetail(), PatientDetail is: " + patientDetail);
        if (context != null) {
            SharedPreferences.Editor editor = getEditor(context);
            Gson gson = new Gson();
            String patient = gson.toJson(patientDetail);
            editor.putString(LuckySeven.SharedPreferences.PATIENT_SAVE, patient);
            editor.apply();
        }
    }*/

    /* public static PatientDetail getPatientDetail(Context context, String key, String defaultValue) {
         android.util.Log.d(TAG, "getPatientDetail: ");
         SharedPreferences preferences = getSharedPreferences(context);
         Gson gson = new Gson();
         String json = preferences.getString(key, defaultValue);
         return gson.fromJson(json, PatientDetail.class);
     }*/


    public static void clearSharedPreferences(Context context) {
        Log.d(TAG, "clearSharedPreferences()");
        getEditor(context).clear().commit();
    }

    public static void clearLocationSharedPreferences(Context context) {
        Log.d(TAG, "clearLocationSharedPreferences()");
        SharedPreferences preferences = getSharedPreferences(context);
        preferences.edit().clear().apply();
    }

    public static void writeLocationToSharedPreference(Context context, String cityName, String localityName) {
        Log.d(TAG, "writeLocationToSharedPreference(), City: " + cityName + ", Locality: " + localityName);
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(TapTheGrey.PREFERENCE_CITY_KEY, cityName);
        editor.putString(TapTheGrey.PREFERENCE_LOCATION_KEY, localityName);
        editor.apply();
    }
}
