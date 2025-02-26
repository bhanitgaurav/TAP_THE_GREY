package com.bhanit.games.mytapthegrey.helper

import android.util.Log
import com.bhanit.games.mytapthegrey.utils.constants.BaseConstants


object Log {
    private const val LOG = BaseConstants.LOG_FLAG

    fun i(tag: String?, string: String?) {
        if (LOG) Log.i(tag, string!!)
    }

    fun e(tag: String?, string: String?) {
        if (LOG) Log.e(tag, string!!)
    }

    @JvmStatic
    fun d(tag: String?, string: String?) {
        if (LOG) Log.d(tag, string!!)
    }

    fun v(tag: String?, string: String?) {
        if (LOG) Log.v(tag, string!!)
    }

    fun w(tag: String?, string: String?) {
        if (LOG) Log.w(tag, string!!)
    }

    fun w(tag: String?, string: String?, e: Exception?) {
        if (LOG) Log.w(tag, string, e)
    }

    fun d(tag: String?, string: String?, e: Exception?) {
        if (LOG) Log.d(tag, string, e)
    }
}
