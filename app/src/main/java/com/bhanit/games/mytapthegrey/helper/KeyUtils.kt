package com.bhanit.games.mytapthegrey.helper

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.bhanit.games.mytapthegrey.R
import com.bhanit.games.mytapthegrey.helper.Log.d
import java.util.Locale

class KeyUtils {

    companion object {
        private val TAG: String = KeyUtils::class.java.simpleName
        fun showAlertDialog(runnable: Runnable, message: String, context: Context): AlertDialog {
            d(TAG, "showAlertDialog(), Message is: $message")
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setMessage(message)
            alertDialog.setCancelable(false)
                .setPositiveButton(context.getString(R.string.yes)) { _, _ -> runnable.run() }
                .setIcon(R.drawable.ic_alert)
            val alert = alertDialog.create()
            alert.show()
            return alert
        }

        fun hideSoftKeyboard(activity: Activity, view: View) {
            d(TAG, "hideSoftKeyboard()")
            val inputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (activity.currentFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
            } else {
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        fun alertMessage(message: String, context: Context) {
            d(TAG, "alertMessage(), Message is: $message")
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setMessage(message)
            alertDialog.setCancelable(false)
                .setPositiveButton(context.getString(R.string.yes)) { _: DialogInterface?, _: Int ->
                    alertDialog.setCancelable(
                        true
                    )
                }
            val alert = alertDialog.create()
            alert.show()
        }

        @JvmStatic
        fun convertIntoUpperCase(text: String?): String { //CONVERT FIRST ALPHABET INTO CAPS OF TEXT
            d(TAG, "convertIntoUpperCase(), Text is: $text")
            if (text != null) {
                val stringBuilder = StringBuilder()
                val textStringArray =
                    text.trim { it <= ' ' }.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()
                for (getText in textStringArray) {
                    if (getText.length >= 2) {
                        val firstAlphabetCaps = getText.substring(0, 1)
                            .uppercase(Locale.getDefault()) + getText.substring(1)
                        stringBuilder.append(firstAlphabetCaps)
                        stringBuilder.append(" ")
                    } else if (getText.length == 1) {
                        stringBuilder.append(getText.uppercase(Locale.getDefault()))
                        stringBuilder.append(" ")
                    }
                }
                return stringBuilder.toString().trim { it <= ' ' }
            }
            return ""
        }
    }
}
