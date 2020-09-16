package com.bhanit.tapthegrey.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhanit.tapthegrey.R;
import com.bhanit.tapthegrey.adapter.UnLockLevelAdapter;

public class UnlockAllFragment extends Fragment implements UnLockLevelAdapter.OnItemClickListener {
    private static final String TAG = UnlockAllFragment.class.getSimpleName();
    private RecyclerView mUnlockLevelRecyclerView;
    private Activity mActivity;
    private UnLockLevelAdapter mUnLockLevelAdapter;
    private int mUnLockLevel;
    private TapTheGreyActivityInteraction mTapTheGreyActivityInteraction;

    public static UnlockAllFragment newInstance() {
        Log.d(TAG, "newInstance: ");
        UnlockAllFragment unlockAllFragment = new UnlockAllFragment();
        return unlockAllFragment;
    }

    public void unLockLevel(int unlockLevel) {
        Log.d(TAG, "unLockLevel: " + unlockLevel);
        mUnLockLevel = unlockLevel;
        setAdapter();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_unlock_all, container, false);
        initViewAndSetAdapter(view);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mTapTheGreyActivityInteraction = (TapTheGreyActivityInteraction) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
        mActivity = null;
        mTapTheGreyActivityInteraction = null;
    }

    private void initViewAndSetAdapter(View view) {
        Log.d(TAG, "initViewAndListener: ");
        mUnlockLevelRecyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mUnlockLevelRecyclerView.setLayoutManager(linearLayoutManager);
        mUnLockLevelAdapter = new UnLockLevelAdapter(getContext(), 4, this);
        setAdapter();
    }

    private void setAdapter() {
        Log.d(TAG, "setAdapter: ");
        if (mUnLockLevelAdapter != null && mUnlockLevelRecyclerView != null) {
            mUnLockLevelAdapter.updateUnlockList(mUnLockLevel);
            mUnlockLevelRecyclerView.setAdapter(mUnLockLevelAdapter);
        }
    }

    @Override
    public void onItemClicked(int adapterPosition) {
        Log.d(TAG, "onItemClicked: " + adapterPosition);
        mTapTheGreyActivityInteraction.openLevelFragment(adapterPosition);
    }

    public interface TapTheGreyActivityInteraction {
        void openLevelFragment(int adapterPosition);
    }
}
