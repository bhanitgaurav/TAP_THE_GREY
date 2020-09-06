package com.bhanit.tapthegrey;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;


public class TapTheGreyActivity extends AppCompatActivity {
    private static final String TAG = TapTheGreyActivity.class.getSimpleName();

    private LevelOneFragment mLevelOneFragment;
    private Fragment mCurrentVisibleFragment, mPreviousFragment;
    private FragmentManager mManager = getSupportFragmentManager();
    private boolean mExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_the_grey);
        if (mLevelOneFragment == null) {
            mLevelOneFragment = LevelOneFragment.newInstance();
            addShowFragment(mLevelOneFragment);
        }
    }


    /**
     * Method helps to add new fragment or to show hidden fragment.
     *
     * @param fragment fragment to show or add
     */
    private void addShowFragment(Fragment fragment) {
        android.util.Log.d(TAG, "addShowFragment: " + fragment);
        mCurrentVisibleFragment = fragment;
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        if (!fragment.isAdded()) {
            android.util.Log.d(TAG, "addShowFragment: fragment Added");
            fragmentTransaction.add(R.id.main_layout, fragment).commit();
        } else
            showFragment(fragment);
    }

    /**
     * Method Helps to hide the fragment
     *
     * @param fragment fragment to hide
     */
    private void hideFragment(Fragment fragment) {
        android.util.Log.d(TAG, "hideFragment: ");
        FragmentTransaction ft = mManager.beginTransaction();
        ft.hide(fragment);
        ft.commit();
    }

    /**
     * Method helps to show the hidden fragment
     *
     * @param fragment fragment to show
     */
    private void showFragment(Fragment fragment) {
        android.util.Log.d(TAG, "showFragment: ");
        FragmentTransaction ft = mManager.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ");
        if (mCurrentVisibleFragment.equals(mLevelOneFragment) && !mExit)
            alertExit();
        else
            super.onBackPressed();
    }

    private void alertExit() {
        Log.d(TAG, "alertExit: ");
        alertDialog(getString(R.string.exit), getString(R.string.are_you_sure_you_want_to_exit));
    }

    private void alertDialog(String message, String smallMessage) {
        Log.d(TAG, "alertDialog()");
        final Dialog openDialog = new Dialog(this);
        openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(openDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        openDialog.setContentView(R.layout.custom_permission_dialog);
        ImageView image = openDialog.findViewById(R.id.image_view);
        image.setVisibility(View.VISIBLE);

        TextView dialogTextContent = openDialog.findViewById(R.id.heading);
        TextView smallText = openDialog.findViewById(R.id.description);

        dialogTextContent.setText(message);
        smallText.setText(smallMessage);

        TextView rightButton = openDialog.findViewById(R.id.allow);
        rightButton.setText(getResources().getString(R.string.yes));

        TextView leftButton = openDialog.findViewById(R.id.not_allow);
        leftButton.setText(getResources().getString(R.string.no));
        leftButton.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));

        openDialog.setCanceledOnTouchOutside(true);

        rightButton.setOnClickListener(v -> {
            Log.d(TAG, "onClick: ");
            openDialog.dismiss();
            mExit = true;
            onBackPressed();
        });
        leftButton.setOnClickListener(v -> {
            Log.d(TAG, "onClick: ");
            openDialog.dismiss();
            mExit = false;
        });
        openDialog.show();
    }
}

