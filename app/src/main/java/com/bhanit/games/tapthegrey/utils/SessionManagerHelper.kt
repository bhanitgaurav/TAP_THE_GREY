package com.bhanit.games.tapthegrey.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SessionManagerHelper @SuppressLint("CommitPrefEdits") constructor(context: Context) {
    private val mContext: Context
    private val mSharedPreferences: SharedPreferences
    private val mEditor: SharedPreferences.Editor

    init {
        Log.d(TAG, "SessionManagerHelper: ")
        mContext = context
        val PRIVATE_MODE = 0
        mSharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)
        mEditor = mSharedPreferences.edit()
    }

    /**
     * Method will create a new preference
     *
     * @param mobileNumber publicNumber
     * @param userId       UserId
     */
    fun createLoginSession(mobileNumber: String, userId: String) {
        Log.d(TAG, "createLoginSession: $mobileNumber $userId")
        // Storing login value as true
        mEditor.putBoolean(IS_LOGIN, true)
        mEditor.putString(PREF_CELL_NUMBER, mobileNumber)
        mEditor.putString(PREF_USER_ID, userId)
        // Commit chnages
        mEditor.commit()
    }

    val userDetails: HashMap<String, String?>
        /**
         * Method will get the user details from preference
         *
         * @return User Saved Details
         */
        get() {
            Log.d(TAG, "getUserDetails: ")
            val user = HashMap<String, String?>()
            user[PREF_CELL_NUMBER] = mSharedPreferences.getString(PREF_CELL_NUMBER, null)
            user[PREF_USER_ID] =
                mSharedPreferences.getString(PREF_USER_ID, null)
            return user
        }

    /*   */
    /**
     * Clear the shared preference and logout the user and redirect the user to loginActivity
     */
    /*
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
    val isLoggedIn: Boolean
        /**
         * Method check is user is logged in
         *
         * @return true: if user is logged in
         */
        get() {
            Log.d(TAG, "isLoggedIn: " + IS_LOGIN)
            return mSharedPreferences.getBoolean(IS_LOGIN, false)
        }

    companion object {
        const val PREF_CELL_NUMBER: String = "CellNumber"
        private val TAG: String = SessionManagerHelper::class.java.simpleName
        private const val PREFERENCE_NAME = "UserLoginPref"
        private const val IS_LOGIN = "IsLoggedIn"
        private const val PREF_USER_ID = "UserId"
    }
}
