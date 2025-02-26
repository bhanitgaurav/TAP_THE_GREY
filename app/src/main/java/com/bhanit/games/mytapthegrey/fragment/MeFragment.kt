package com.bhanit.games.mytapthegrey.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bhanit.games.mytapthegrey.BuildConfig
import com.bhanit.games.mytapthegrey.R

class MeFragment : Fragment(), View.OnClickListener {
    private var mVersion: TextView? = null
    private var mTapTheGreyActivityInteraction: TapTheGreyActivityInteraction? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        val view = inflater.inflate(R.layout.fragment_me, container, false)
        initView(view)
        setValuesIntoViews()
        return view
    }

    private fun setValuesIntoViews() {
        val versionName = BuildConfig.VERSION_NAME
        val version = resources.getString(R.string.version) + " " + versionName
        mVersion!!.text = version
    }

    private fun initView(view: View) {
        mVersion = view.findViewById(R.id.version_no)
        view.findViewById<View>(R.id.terms).setOnClickListener(this)
        view.findViewById<View>(R.id.privicy_policy).setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mTapTheGreyActivityInteraction = context as TapTheGreyActivityInteraction
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach: ")
        mTapTheGreyActivityInteraction = null
    }

    override fun onClick(v: View) {
        Log.d(TAG, "onClick: ")
        mTapTheGreyActivityInteraction!!.openTapTheGreyWebsite()
    }

    interface TapTheGreyActivityInteraction {
        fun openTapTheGreyWebsite()
    }

    companion object {
        private val TAG: String = MeFragment::class.java.simpleName
        fun newInstance(): MeFragment {
            return MeFragment()
        }
    }
}
