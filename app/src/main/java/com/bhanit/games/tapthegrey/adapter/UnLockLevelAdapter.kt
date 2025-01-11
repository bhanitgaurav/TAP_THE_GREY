package com.bhanit.games.tapthegrey.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bhanit.games.tapthegrey.R
import com.bhanit.games.tapthegrey.adapter.UnLockLevelAdapter.UnLockLevelViewHolder
import com.bhanit.games.tapthegrey.fragment.UnlockAllFragment
import com.bhanit.games.tapthegrey.helper.KeyUtils.Companion.convertIntoUpperCase
import com.bhanit.games.tapthegrey.utils.constants.TapTheGrey

class UnLockLevelAdapter(context: Context, levelToShow: Int, listner: OnItemClickListener) :
    RecyclerView.Adapter<UnLockLevelViewHolder>() {
    private val mContext: Context
    private val mLevelListToShow: Int
    private val mOnItemClickListener: OnItemClickListener
    private var mUnlockedLevelList = 0


    init {
        Log.d(TAG, "UnLockLevelAdapter: ")
        mContext = context
        mLevelListToShow = levelToShow
        mOnItemClickListener = listner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnLockLevelViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.unlock_adapter, parent, false)
        return UnLockLevelViewHolder(view)
    }

    override fun onBindViewHolder(holder: UnLockLevelViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: $position")
        if (position <= mUnlockedLevelList) {
            holder.cardView.getChildAt(0)
                .setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_level_passed))
            setImageAndNameOnHolder(holder, position, "")
            holder.cardView.isEnabled = true
        } else {
            holder.cardView.getChildAt(0)
                .setBackgroundColor(ContextCompat.getColor(mContext, R.color.card_background_color))
            setImageAndNameOnHolder(holder, position, "Locked")
            holder.cardView.isEnabled = false
        }
    }

    private fun setImageAndNameOnHolder(
        holder: UnLockLevelViewHolder,
        position: Int,
        locked: String
    ) {
        Log.d(TAG, "setImageAndNameOnHolder: $position")
        when (position + 1) {
            1 -> {
                Log.d(TAG, "setImageAndNameOnHolder: ")
                holder.levelImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        mContext,
                        R.drawable.level_one
                    )
                )
                holder.levelName.text = String.format(
                    "%s %s %s",
                    mContext.resources.getString(R.string.level),
                    convertIntoUpperCase(TapTheGrey.Level.ONE),
                    locked
                )
            }

            2 -> {
                Log.d(TAG, "setImageAndNameOnHolder: ")
                holder.levelImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        mContext,
                        R.drawable.level_two
                    )
                )
                holder.levelName.text = String.format(
                    "%s %s %s",
                    mContext.resources.getString(R.string.level),
                    convertIntoUpperCase(TapTheGrey.Level.TWO),
                    locked
                )
            }

            3 -> {
                Log.d(TAG, "setImageAndNameOnHolder: ")
                holder.levelImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        mContext,
                        R.drawable.level_three
                    )
                )
                holder.levelName.text = String.format(
                    "%s %s %s",
                    mContext.resources.getString(R.string.level),
                    convertIntoUpperCase(TapTheGrey.Level.THREE),
                    locked
                )
            }

            4 -> {
                Log.d(TAG, "setImageAndNameOnHolder: ")
                holder.levelImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        mContext,
                        R.drawable.level_three
                    )
                )
                holder.levelName.text = String.format(
                    "%s %s %s",
                    mContext.resources.getString(R.string.level),
                    convertIntoUpperCase(TapTheGrey.Level.FOUR),
                    locked
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return mLevelListToShow
    }

    fun updateUnlockList(unlockLevel: Int) {
        Log.d(TAG, "updateUnlockList: $unlockLevel")
        mUnlockedLevelList = unlockLevel
    }

    interface OnItemClickListener {
        fun onItemClicked(adapterPosition: Int)
    }

    inner class UnLockLevelViewHolder(itemView: View) : ViewHolder(itemView), View.OnClickListener {
        val levelImage: ImageView
        val levelName: TextView
        val cardView: CardView

        init {
            Log.d(TAG, "UnLockLevelViewHolder: ")
            levelImage = itemView.findViewById(R.id.level_image)
            levelName = itemView.findViewById(R.id.level_name)
            cardView = itemView.findViewById(R.id.background_head)
            cardView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d(TAG, "onClick: ")
            if (v.id == R.id.background_head) {
                Log.d(TAG, "onClick: ")
                mOnItemClickListener.onItemClicked(bindingAdapterPosition + 1)
            }
        }
    }

    companion object {
        private val TAG: String = UnlockAllFragment::class.java.simpleName
    }
}
