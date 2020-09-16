package com.bhanit.tapthegrey.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bhanit.tapthegrey.R;
import com.bhanit.tapthegrey.fragment.UnlockAllFragment;
import com.bhanit.tapthegrey.helper.KeyUtils;
import com.bhanit.tapthegrey.utils.constants.TapTheGrey;

public class UnLockLevelAdapter extends RecyclerView.Adapter<UnLockLevelAdapter.UnLockLevelViewHolder> {
    private static final String TAG = UnlockAllFragment.class.getSimpleName();
    private Context mContext;
    private int mLevelListToShow;
    private OnItemClickListener mOnItemClickListener;
    private int mUnlockedLevelList;


    public UnLockLevelAdapter(Context context, int levelToShow, OnItemClickListener listner) {
        Log.d(TAG, "UnLockLevelAdapter: ");
        mContext = context;
        mLevelListToShow = levelToShow;
        mOnItemClickListener = listner;
    }

    @NonNull
    @Override
    public UnLockLevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unlock_adapter, parent, false);
        return new UnLockLevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnLockLevelViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);
        if (position <= mUnlockedLevelList) {
            holder.cardView.getChildAt(0).setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_level_passed));
            setImageAndNameOnHolder(holder, position, "");
            holder.cardView.setEnabled(true);
        } else {
            holder.cardView.getChildAt(0).setBackgroundColor(ContextCompat.getColor(mContext, R.color.card_background_color));
            setImageAndNameOnHolder(holder, position, "Locked");
            holder.cardView.setEnabled(false);
        }
    }

    private void setImageAndNameOnHolder(UnLockLevelViewHolder holder, int position, String locked) {
        Log.d(TAG, "setImageAndNameOnHolder: " + position);
        switch (position + 1) {
            case 1: {
                Log.d(TAG, "setImageAndNameOnHolder: ");
                holder.levelImage.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.level_one));
                holder.levelName.setText(String.format("%s %s %s", mContext.getResources().getString(R.string.level), KeyUtils.convertIntoUpperCase(TapTheGrey.Level.ONE), locked));
                break;
            }
            case 2: {
                Log.d(TAG, "setImageAndNameOnHolder: ");
                holder.levelImage.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.level_two));
                holder.levelName.setText(String.format("%s %s %s", mContext.getResources().getString(R.string.level), KeyUtils.convertIntoUpperCase(TapTheGrey.Level.TWO), locked));
                break;
            }
            case 3: {
                Log.d(TAG, "setImageAndNameOnHolder: ");
                holder.levelImage.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.level_three));
                holder.levelName.setText(String.format("%s %s %s", mContext.getResources().getString(R.string.level), KeyUtils.convertIntoUpperCase(TapTheGrey.Level.THREE), locked));
                break;
            }
            case 4: {
                Log.d(TAG, "setImageAndNameOnHolder: ");
                holder.levelImage.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.level_three));
                holder.levelName.setText(String.format("%s %s %s", mContext.getResources().getString(R.string.level), KeyUtils.convertIntoUpperCase(TapTheGrey.Level.FOUR), locked));
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return mLevelListToShow;
    }

    public void updateUnlockList(int unlockLevel) {
        Log.d(TAG, "updateUnlockList: " + unlockLevel);
        mUnlockedLevelList = unlockLevel;
    }

    public interface OnItemClickListener {
        void onItemClicked(int adapterPosition);
    }

    class UnLockLevelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView levelImage;
        private TextView levelName;
        private CardView cardView;

        public UnLockLevelViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "UnLockLevelViewHolder: ");
            levelImage = itemView.findViewById(R.id.level_image);
            levelName = itemView.findViewById(R.id.level_name);
            cardView = itemView.findViewById(R.id.background_head);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: ");
            switch (v.getId()) {
                case R.id.background_head: {
                    Log.d(TAG, "onClick: ");
                    mOnItemClickListener.onItemClicked(getAdapterPosition() + 1);
                    break;
                }
            }
        }
    }
}
