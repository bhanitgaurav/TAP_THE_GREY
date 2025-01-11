package com.bhanit.games.tapthegrey.utils

import android.content.Context
import android.content.SharedPreferences
import com.bhanit.games.tapthegrey.helper.Log.d

object PreferencesHelper {
    private const val SYMMETRIC_KEY_PREFRENCES: String = "symmetric_key"
    private const val PREFERENCE_TOKEN: String = "token"
    const val SESSION: String = "session"
    private val TAG: String = PreferencesHelper::class.java.simpleName
    private const val USER_PREFERENCES = "tap_the_grey_User"
    private const val RATING_DIRTY = "rating_dirty"


    fun saveToken(context: Context?, patientToken: String) {
        d(TAG, "saveToken(), PatientToken is: $patientToken")
        if (context != null) {
            val editor = getEditor(context)
            editor.putString(PREFERENCE_TOKEN, patientToken)
            editor.apply()
        }
    }

    fun getSharedPreferences(context: Context): SharedPreferences {
        d(TAG, "getSharedPreferences()")
        return context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getStringParameter(context: Context, key: String, defaultValue: String?): String? {
        d(TAG, "getStringParameter(), Key is: $key")
        val preferences = getSharedPreferences(context)
        return preferences.getString(key, defaultValue)
    }

    private fun getEditor(context: Context): SharedPreferences.Editor {
        d(TAG, "getEditor()")
        val preferences = getSharedPreferences(context)
        return preferences.edit()
    }


    fun writeSymmetricKeyPreferences(context: Context?, symmetricKey: String) {
        d(
            TAG,
            "writeSymmetricKeyPreferences(), Context: " + context + "SymmetricKey: " + symmetricKey
        )
        if (context != null) {
            val editor = getEditor(context)
            editor.putString(SYMMETRIC_KEY_PREFRENCES, symmetricKey)
            editor.apply()
        }
    }


    fun clearSharedPreferences(context: Context) {
        d(TAG, "clearSharedPreferences()")
        getEditor(context).clear().commit()
    }

    fun clearLocationSharedPreferences(context: Context) {
        d(TAG, "clearLocationSharedPreferences()")
        val preferences = getSharedPreferences(context)
        preferences.edit().clear().apply()
    }

}
