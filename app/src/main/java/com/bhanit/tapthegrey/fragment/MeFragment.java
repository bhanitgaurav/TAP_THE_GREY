package com.bhanit.tapthegrey.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bhanit.tapthegrey.BuildConfig;
import com.bhanit.tapthegrey.R;

public class MeFragment extends Fragment {
    private static final String TAG = MeFragment.class.getSimpleName();
    private TextView mVersion;
    private TapTheGreyActivityInteraction mTapTheGreyActivityInteraction;


    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        setValuesIntoViews();
        return view;
    }

    private void setValuesIntoViews() {
        String versionName = BuildConfig.VERSION_NAME;
        String version = getResources().getString(R.string.version) + " " + versionName;
        mVersion.setText(version);
    }

    private void initView(View view) {
        mVersion = view.findViewById(R.id.version_no);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mTapTheGreyActivityInteraction = (TapTheGreyActivityInteraction) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
        mTapTheGreyActivityInteraction = null;
    }

    public interface TapTheGreyActivityInteraction {

    }
}
