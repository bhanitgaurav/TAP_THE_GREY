package com.bhanit.games.tapthegrey.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhanit.games.tapthegrey.R
import com.bhanit.games.tapthegrey.adapter.UnLockLevelAdapter
import com.bhanit.games.tapthegrey.helper.Log

class UnlockAllFragment : Fragment(), UnLockLevelAdapter.OnItemClickListener {
    private lateinit var mUnlockLevelRecyclerView: RecyclerView
    private var mActivity: Activity? = null
    private var mUnLockLevelAdapter: UnLockLevelAdapter? = null
    private var mUnLockLevel = 0
    private var mTapTheGreyActivityInteraction: TapTheGreyActivityInteraction? = null

    fun unLockLevel(unlockLevel: Int) {
        Log.d(TAG, "unLockLevel: $unlockLevel")
        mUnLockLevel = unlockLevel
        setAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        val view = inflater.inflate(R.layout.fragment_unlock_all, container, false)
        initViewAndSetAdapter(view)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
        mTapTheGreyActivityInteraction = context as TapTheGreyActivityInteraction
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach: ")
        mActivity = null
        mTapTheGreyActivityInteraction = null
    }

    private fun initViewAndSetAdapter(view: View) {
        Log.d(TAG, "initViewAndListener: ")
        mUnlockLevelRecyclerView = view.findViewById(R.id.recycler_view)
        val linearLayoutManager = LinearLayoutManager(mActivity)
        mUnlockLevelRecyclerView.setLayoutManager(linearLayoutManager)
        mUnLockLevelAdapter = UnLockLevelAdapter(requireContext(), 4, this)
        setAdapter()
    }

    private fun setAdapter() {
        Log.d(TAG, "setAdapter: ")
        if (mUnLockLevelAdapter != null) {
            mUnLockLevelAdapter!!.updateUnlockList(mUnLockLevel)
            mUnlockLevelRecyclerView.adapter = mUnLockLevelAdapter
        }
    }

    override fun onItemClicked(adapterPosition: Int) {
        Log.d(TAG, "onItemClicked: $adapterPosition")
        mTapTheGreyActivityInteraction!!.openLevelFragment(adapterPosition)
    }

    interface TapTheGreyActivityInteraction {
        fun openLevelFragment(adapterPosition: Int)
    }

    companion object {
        private val TAG: String = UnlockAllFragment::class.java.simpleName
        fun newInstance(): UnlockAllFragment {
            Log.d(TAG, "newInstance: ")
            val unlockAllFragment = UnlockAllFragment()
            return unlockAllFragment
        }
    }
}
