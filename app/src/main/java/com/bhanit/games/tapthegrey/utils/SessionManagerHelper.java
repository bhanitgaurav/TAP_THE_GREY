package com.bhanit.games.tapthegrey.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

public class SessionManagerHelper {
    public static final String PREF_CELL_NUMBER = "CellNumber";
    private static final String TAG = SessionManagerHelper.class.getSimpleName();
    private static final String PREFERENCE_NAME = "UserLoginPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String PREF_USER_ID = "UserId";
    private final Context mContext;
    private final SharedPreferences mSharedPreferences;
    private final SharedPreferences.Editor mEditor;

    @SuppressLint("CommitPrefEdits")
    public SessionManagerHelper(Context context) {
        Log.d(TAG, "SessionManagerHelper: ");
        mContext = context;
        int PRIVATE_MODE = 0;
        mSharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();
    }

    /**
     * Method will create a new preference
     *
     * @param mobileNumber publicNumber
     * @param userId       UserId
     */
    public void createLoginSession(String mobileNumber, String userId) {
        Log.d(TAG, "createLoginSession: " + mobileNumber + " " + userId);
        // Storing login value as true
        mEditor.putBoolean(IS_LOGIN, true);
        mEditor.putString(PREF_CELL_NUMBER, mobileNumber);
        mEditor.putString(PREF_USER_ID, userId);
        // Commit chnages
        mEditor.commit();
    }

    /**
     * Method will get the user details from preference
     *
     * @return User Saved Details
     */
    public HashMap<String, String> getUserDetails() {
        Log.d(TAG, "getUserDetails: ");
        HashMap<String, String> user = new HashMap<>();
        user.put(PREF_CELL_NUMBER, mSharedPreferences.getString(PREF_CELL_NUMBER, null));
        user.put(PREF_USER_ID, mSharedPreferences.getString(PREF_USER_ID, null));
        return user;
    }

    /*   *//**
     * Clear the shared preference and logout the user and redirect the user to loginActivity
     *//*
    public void logOutUser() {
        Log.d(TAG, "logotUser: ");
        mEditor.clear(); //Clearing all data from shared preference
        mEditor.commit();
        Intent intent = new Intent(mContext, LoginActivity.class); // After logout redirecting user to LoginActivity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// Closing All the activities
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// Add new flag to start new activity
        intent.putExtra(KEY_ADD_MEMBER, false);
        mContext.startActivity(intent); // Start login Activity
    }*/

    /**
     * Method check is user is logged in
     *
     * @return true: if user is logged in
     */
    public boolean isLoggedIn() {
        Log.d(TAG, "isLoggedIn: " + IS_LOGIN);
        return mSharedPreferences.getBoolean(IS_LOGIN, false);
    }

}
