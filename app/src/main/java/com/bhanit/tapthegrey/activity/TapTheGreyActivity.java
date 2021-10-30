package com.bhanit.tapthegrey.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bhanit.tapthegrey.R;
import com.bhanit.tapthegrey.fragment.LevelOneFragment;
import com.bhanit.tapthegrey.fragment.LevelThreeFragment;
import com.bhanit.tapthegrey.fragment.LevelTwoFragment;
import com.bhanit.tapthegrey.fragment.MeFragment;
import com.bhanit.tapthegrey.fragment.UnlockAllFragment;
import com.bhanit.tapthegrey.helper.Log;
import com.bhanit.tapthegrey.utils.constants.TapTheGrey;

import java.util.Objects;


public class TapTheGreyActivity extends AppCompatActivity implements View.OnClickListener, LevelOneFragment.TapTheGreyActivityInteraction, LevelTwoFragment.TapTheGreyActivityInteraction, LevelThreeFragment.TapTheGreyActivityInteraction, UnlockAllFragment.TapTheGreyActivityInteraction, MeFragment.TapTheGreyActivityInteraction {
    private static final String TAG = TapTheGreyActivity.class.getSimpleName();
    private static int mMaxScoreOnTapTheGrey;
    private static String mPassedLevel;
    private static boolean isLevelLockUnlocked;
    private static int mLockUnlockLevel;
    private TextView mBhanitgauravEmail, mGameName, mHeaderName;
    private LevelOneFragment mLevelOneFragment;
    private LevelTwoFragment mLevelTwoFragment;
    private LevelThreeFragment mLevelThreeFragment;
    private UnlockAllFragment mUnlockAllFragment;
    private Fragment mCurrentVisibleFragment, mPreviousFragment;
    private final FragmentManager mManager = getSupportFragmentManager();
    private ImageView mUnlockImage;
    private boolean mExit;
    private int developerLockUnlock = 0;
    private MeFragment mMeFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_the_grey);
        Log.d(TAG, "onCreate: ");
        initViewsAndSetOnclickListener();
        openLevelOneFragment();
        handleLevelUnlock();
    }

    private void openLevelOneFragment() {
        Log.d(TAG, "openLevelOneFragment: ");
        if (mCurrentVisibleFragment != null)
            hideFragment(mCurrentVisibleFragment);
        if (mLevelOneFragment == null) {
            mLevelOneFragment = LevelOneFragment.newInstance();
        }
        addShowFragment(mLevelOneFragment);
    }

    private void handleLevelUnlock() {
        Log.d(TAG, "handleLevelUnlock: ");
        if (isLevelLockUnlocked)
            mUnlockImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_unlock));
        else mUnlockImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_lock));
    }

    private void initViewsAndSetOnclickListener() {
        Log.d(TAG, "initViewsAndSetOnclickListener: ");
        mGameName = findViewById(R.id.game_name);
        mHeaderName = findViewById(R.id.textView);
        mBhanitgauravEmail = findViewById(R.id.bottom_name);
        mBhanitgauravEmail.setOnClickListener(this);
        mUnlockImage = findViewById(R.id.lock_unlock);
        mUnlockImage.setOnClickListener(this);
        mGameName.setOnClickListener(this);

    }

    /**
     * Method helps to add new fragment or to show hidden fragment.
     *
     * @param fragment fragment to show or add
     */
    private void addShowFragment(Fragment fragment) {
        Log.d(TAG, "addShowFragment: " + fragment);
        mCurrentVisibleFragment = fragment;
        FragmentTransaction fragmentTransaction = mManager.beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        if (!fragment.isAdded()) {
            Log.d(TAG, "addShowFragment: fragment Added");
            changeBackgroundAccordingToFragment(fragment);
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
        Log.d(TAG, "hideFragment: ");
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
        Log.d(TAG, "showFragment: ");
        mCurrentVisibleFragment = fragment;
        changeBackgroundAccordingToFragment(fragment);
        FragmentTransaction ft = mManager.beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        ft.show(fragment);
        ft.commit();
    }

    private void changeBackgroundAccordingToFragment(Fragment fragment) {
        if (fragment instanceof MeFragment) {
            mGameName.setVisibility(View.GONE);
            mUnlockImage.setVisibility(View.GONE);
        } else {
            if (fragment instanceof UnlockAllFragment) {
                mGameName.setVisibility(View.GONE);
                mHeaderName.setVisibility(View.VISIBLE);
            } else {
                mGameName.setVisibility(View.VISIBLE);
                mHeaderName.setVisibility(View.GONE);

            }
        }

        if (fragment instanceof LevelOneFragment) {
            findViewById(R.id.main_layout).setBackgroundColor(ContextCompat.getColor(this, R.color.default_background));
            return;
        }
        if (fragment instanceof LevelTwoFragment) {
            findViewById(R.id.main_layout).setBackgroundColor(ContextCompat.getColor(this, R.color.card_background_color));
            return;
        }
        if (fragment instanceof LevelThreeFragment) {
            findViewById(R.id.main_layout).setBackgroundColor(ContextCompat.getColor(this, R.color.color_organic_brown));
            return;
        }
        findViewById(R.id.main_layout).setBackgroundColor(ContextCompat.getColor(this, R.color.cardview_shadow_start_color));

    }

    /**
     * Method helps to hide the current fragment and shows the new fragment
     *
     * @param mCurrentFragment current Fragment
     * @param shownFragment    upcoming fragment to show
     */
    private void hideShowFragment(Fragment mCurrentFragment, Fragment shownFragment) {
        hideFragment(mCurrentFragment);
        addShowFragment(shownFragment);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ");
        if (mCurrentVisibleFragment instanceof LevelOneFragment && !mExit) {
            alertExit();
            return;
        }
        if (mCurrentVisibleFragment instanceof LevelTwoFragment) {
            openLevelOneFragment();
            return;
        }
        if (mCurrentVisibleFragment instanceof LevelThreeFragment) {
            if (mLevelTwoFragment == null) {
                openLevelOneFragment();
                return;
            }
            launchLevelTwo();
            return;
        }
        if (mCurrentVisibleFragment instanceof UnlockAllFragment||mCurrentVisibleFragment instanceof MeFragment) {
            openPreviousFragment();
            mUnlockImage.setVisibility(View.VISIBLE);
            return;
        }
        super.onBackPressed();
    }

    private void openPreviousFragment() {
        Log.d(TAG, "openPreviousFragment: ");
        handleLevelUnlock();
        if (mPreviousFragment == null)
            openLevelOneFragment();
        else {
            hideFragment(mCurrentVisibleFragment);
            showFragment(mPreviousFragment);
        }
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

        openDialog.setCanceledOnTouchOutside(false);

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
            mUnlockImage.setVisibility(View.VISIBLE);
        });
        openDialog.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        switch (v.getId()) {
            case R.id.bottom_name: {
                Log.d(TAG, "onClick: bhanitgaurav mail");
                mailTextSet();
                break;
            }
            case R.id.lock_unlock: {
                Log.d(TAG, "onClick: lock_unlock");
                if (isLevelLockUnlocked || developerLockUnlock == 5) {
                    if (developerLockUnlock == 5)
                        mLockUnlockLevel = 4;
                    isLevelLockUnlocked = true;
                    handleLevelUnlock();
                    openUnlockFragment();
                } else {
                    if (developerLockUnlock == 0)
                        Toast.makeText(this, R.string.play_to_unlock_the_lock, Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> developerLockUnlock = 0, TapTheGrey.Time.TWO_SECOND);
                    developerLockUnlock++;
                }
                break;
            }
            case R.id.game_name: {
                openMeFragment();
                break;
            }
        }

    }

    private void openMeFragment() {
        mPreviousFragment = mCurrentVisibleFragment;
        hideFragment(mCurrentVisibleFragment);
        if (mMeFragment == null) {
            mMeFragment = MeFragment.newInstance();
        }
        addShowFragment(mMeFragment);
    }

    private void openUnlockFragment() {
        Log.d(TAG, "openUnlockFragment: ");
        mPreviousFragment = mCurrentVisibleFragment;
        hideFragment(mCurrentVisibleFragment);
        if (mUnlockAllFragment == null) {
            mUnlockAllFragment = UnlockAllFragment.newInstance();
        }
        addShowFragment(mUnlockAllFragment);
        if (mUnlockAllFragment != null)
            mUnlockAllFragment.unLockLevel(mLockUnlockLevel);
        mUnlockImage.setVisibility(View.GONE);
    }

    private void mailTextSet() {
        Log.d(TAG, "mailTextSet: ");
        String URL = "http://bhanitgaurav.com/";
        mBhanitgauravEmail.setText(HtmlCompat.fromHtml(
                "<font color='#0062b0'> <a href=\"" + URL + "\">Bhanit Gaurav</a> </font>"
                , HtmlCompat.FROM_HTML_MODE_LEGACY));
        mBhanitgauravEmail.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void launchLevelTwo() {
        Log.d(TAG, "launchLevelTwo: ");
        mPreviousFragment = mCurrentVisibleFragment;
        hideFragment(mCurrentVisibleFragment);
        if (mLevelTwoFragment == null) {
            mLevelTwoFragment = LevelTwoFragment.newInstance();
        }
        addShowFragment(mLevelTwoFragment);
//        updateMaxScore();
    }

    @Override
    public void unlockTheLock(int levelToUnlock) {
        Log.d(TAG, "unlockTheLock: " + levelToUnlock);
        isLevelLockUnlocked = true;
        if (mLockUnlockLevel < levelToUnlock)
            mLockUnlockLevel = levelToUnlock;
        handleLevelUnlock();
    }

    @Override
    public void enableLock(boolean isEnabled) {
        Log.d(TAG, "enableLock: " + isEnabled);
        if (!isEnabled)
            mUnlockImage.setVisibility(View.VISIBLE);
        else mUnlockImage.setVisibility(View.GONE);
    }

    @Override
    public void launchLevelThree() {
        Log.d(TAG, "launchLevelThree: maxScore ");
        mPreviousFragment = mCurrentVisibleFragment;
        hideFragment(mCurrentVisibleFragment);
        if (mLevelThreeFragment == null) {
            mLevelThreeFragment = LevelThreeFragment.newInstance();
        }
        addShowFragment(mLevelThreeFragment);
    }

    @Override
    public void launchLevelFour() {
        Log.d(TAG, "launchLevelFour: ");
//        onBackPressed();
        Toast.makeText(this, "We are working on next level. Kindly wait for the next update.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void openLevelFragment(int adapterPosition) {
        Log.d(TAG, "openLevelFragment: ");
        if (adapterPosition != 4)
            mUnlockImage.setVisibility(View.VISIBLE);
        switch (adapterPosition) {
            case 1: {
                Log.d(TAG, "onItemClicked: ");
                if (mCurrentVisibleFragment != null) {
                    hideFragment(mCurrentVisibleFragment);
                    mPreviousFragment = mCurrentVisibleFragment;
                }
                openLevelOneFragment();
                break;
            }
            case 2: {
                Log.d(TAG, "onItemClicked: ");
                launchLevelTwo();
                break;
            }
            case 3: {
                Log.d(TAG, "onItemClicked: ");
                launchLevelThree();
                break;
            }
            case 4: {
                Log.d(TAG, "onItemClicked: ");
                Toast.makeText(this, "We are working on next level. Kindly wait for the next update.", Toast.LENGTH_LONG).show();
                break;
            }
        }

    }

    @Override
    public void openTapTheGreyWebsite() {
     Log.d(TAG, "openTapTheGreyWebsite: ");
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(TapTheGrey.WEBSITE));
        startActivity(browserIntent);
    }
}

