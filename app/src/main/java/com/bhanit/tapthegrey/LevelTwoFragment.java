package com.bhanit.tapthegrey;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bhanit.tapthegrey.utils.constants.TapTheGrey;

import java.util.Objects;

public class LevelTwoFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = LevelTwoFragment.class.getSimpleName();
    private ImageView mImageOne, mImageTwo, mImageThree, mImageFour, mImageFive, mImageSix, mImageSeven, mImageEight, mImageNine;
    private TextView mStart, mScoreBoard, mMaxScoreView;
    private static int mLastRandomNumber;
    private int mSpeedBooster = TapTheGrey.Count.TEN;
    private int UNIT_TIME = TapTheGrey.Time.ONE_SECOND;
    private boolean isRunning;
    private static int mScore = -1;
    private static int mMaxScore = 0;
    private TapTheGreyActivity mActivity;
    private boolean isLevelUp;
    private TapTheGreyActivityInteraction mTapTheGreyActivityInteraction;

    public static LevelTwoFragment newInstance() {
        Log.d(TAG, "newInstance: ");
        LevelTwoFragment levelTwoFragment = new LevelTwoFragment();
        return levelTwoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_level_two, container, false);
        initViewAndListener(view);
        resetTapTheGrey();
        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onStart: ");
        mActivity = (TapTheGreyActivity) context;
        mTapTheGreyActivityInteraction = (TapTheGreyActivityInteraction) context;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
        mTapTheGreyActivityInteraction = null;
    }

    private void initViewAndListener(View view) {
        Log.d(TAG, "initViewAndListener: ");
        mImageOne = view.findViewById(R.id.one);
        mImageTwo = view.findViewById(R.id.two);
        mImageThree = view.findViewById(R.id.three);
        mImageFour = view.findViewById(R.id.four);
        mImageFive = view.findViewById(R.id.five);
        mImageSix = view.findViewById(R.id.six);
        mImageSeven = view.findViewById(R.id.seven);
        mImageEight = view.findViewById(R.id.eight);
        mImageNine = view.findViewById(R.id.nine);
        mStart = view.findViewById(R.id.start);
        mScoreBoard = view.findViewById(R.id.score);

        mMaxScoreView = view.findViewById(R.id.max_score);
        mImageOne.setOnClickListener(this);
        mImageTwo.setOnClickListener(this);
        mImageThree.setOnClickListener(this);
        mImageFour.setOnClickListener(this);
        mImageFive.setOnClickListener(this);
        mImageSix.setOnClickListener(this);
        mImageSeven.setOnClickListener(this);
        mImageEight.setOnClickListener(this);
        mImageNine.setOnClickListener(this);
        mStart.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        switch (v.getId()) {
            case R.id.start: {
                Log.d(TAG, "onClick: start");
                startPlay();
                break;
            }
            case R.id.one: {
                Log.d(TAG, "onClick: one");
                hitGrey(1, v);
                break;
            }
            case R.id.two: {
                Log.d(TAG, "onClick: two");
                hitGrey(2, v);
                break;
            }
            case R.id.three: {
                Log.d(TAG, "onClick: three");
                hitGrey(3, v);
                break;
            }
            case R.id.four: {
                Log.d(TAG, "onClick: four");
                hitGrey(4, v);
                break;
            }
            case R.id.five: {
                Log.d(TAG, "onClick: four");
                hitGrey(5, v);
                break;
            }
            case R.id.six: {
                Log.d(TAG, "onClick: four");
                hitGrey(6, v);
                break;
            }
            case R.id.seven: {
                Log.d(TAG, "onClick: four");
                hitGrey(7, v);
                break;
            }
            case R.id.eight: {
                Log.d(TAG, "onClick: four");
                hitGrey(8, v);
                break;
            }
            case R.id.nine: {
                Log.d(TAG, "onClick: four");
                hitGrey(9, v);
                break;
            }
        }
    }


    private void startPlay() {
        Log.d(TAG, "startPlay: ");
        Runnable runnable;
        Handler handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: ");
                startPlay();
                handler.removeCallbacks(this);
            }
        };
        Log.d(TAG, "startPlay:isRunning " + isRunning);
        if (isRunning) {
            chooseBoxToFillColor(getRandomBetweenRange(1, 9));
            handler.postDelayed(runnable, UNIT_TIME);
            updateScoreAndBoostSpeed();
            isRunning = false;
            mStart.setEnabled(false);
            mStart.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.button_disable));
            mStart.setTextColor(ContextCompat.getColor(mActivity, R.color.grey));
        } else showAlertBox(isLevelUp);
        Log.d(TAG, "startPlay:isRunning " + isRunning);
    }

    private void updateScoreAndBoostSpeed() {
        Log.d(TAG, "updateScoreAndBoostSpeed:mUpdateScore " + mScore);
        Log.d(TAG, "updateScoreAndBoostSpeed: mSpeedBooster " + mSpeedBooster);
        Log.d(TAG, "updateScoreAndBoostSpeed: UNIT TIME " + UNIT_TIME);

        mScore = mScore + 1;
        mScoreBoard.setText(String.format("Score: %s", String.valueOf(mScore)));
        if (mScore == mSpeedBooster) {
            UNIT_TIME = UNIT_TIME - TapTheGrey.Count.SEVEN * TapTheGrey.Count.SEVEN;
            mSpeedBooster = mSpeedBooster + TapTheGrey.Count.TEN;
            Toast toast = Toast.makeText(mActivity, "Speed Boosted",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER, 00, 150);
            toast.show();
            Log.d(TAG, "updateScoreAndBoostSpeed: speed boosted");
        }
        if (mScore > mMaxScore)
            mMaxScore = mScore;
        if (mScore == 90) {
            levelOnePassedAndOpenLevelTwo();
        }

    }

    private void levelOnePassedAndOpenLevelTwo() {
        Log.d(TAG, "levelOnePassedAndOpenLevelTwo: ");
        isLevelUp = true;
        alertDialog(getString(R.string.level_two_passed), getString(R.string.go_to_next_level));
    }

    private void alertDialog(String message, String smallMessage) {
        com.bhanit.tapthegrey.helper.Log.d(TAG, "alertDialog()");
        final Dialog openDialog = new Dialog(mActivity);
        openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(openDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        openDialog.setContentView(R.layout.custom_permission_dialog);

        ImageView image = openDialog.findViewById(R.id.image_view);
        image.setVisibility(View.VISIBLE);

        ConstraintLayout layout = (ConstraintLayout) image.getParent();
        layout.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.color_level_passed));

        TextView dialogTextContent = openDialog.findViewById(R.id.heading);
        TextView smallText = openDialog.findViewById(R.id.description);

        dialogTextContent.setText(message);
        smallText.setText(smallMessage);

        TextView rightButton = openDialog.findViewById(R.id.allow);
        rightButton.setText(getResources().getString(R.string.next_level));

        TextView leftButton = openDialog.findViewById(R.id.not_allow);
        leftButton.setText(getResources().getString(R.string.play_again));
        leftButton.setTextColor(ContextCompat.getColor(mActivity, R.color.colorBlack));

        openDialog.setCanceledOnTouchOutside(true);

        rightButton.setOnClickListener(v -> {
            com.bhanit.tapthegrey.helper.Log.d(TAG, "onClick: ");
            openDialog.dismiss();
            mTapTheGreyActivityInteraction.launchLevelThree(mScore,TapTheGrey.Level.TWO);
        });
        leftButton.setOnClickListener(v -> {
            com.bhanit.tapthegrey.helper.Log.d(TAG, "onClick: ");
            openDialog.dismiss();
            resetTapTheGrey();
        });
        openDialog.show();
    }

    private void showAlertBox(boolean isLevelUp) {
        Log.d(TAG, "showAlertBox()");
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
        alertDialog.setIcon(ContextCompat.getDrawable(mActivity, R.mipmap.ic_launcher_round));
        alertDialog.setTitle(getResources().getString(R.string.game_over));
        alertDialog.setMessage("" + getString(R.string.your_score) + " " + String.valueOf(mScore));
        alertDialog.setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.play_again), (DialogInterface dialog, int which) -> {
                    alertDialog.setCancelable(true);
                })
                .setCancelable(true);
        if (!isLevelUp) {
            AlertDialog alert = alertDialog.create();
            alert.show();
        }
        resetTapTheGrey();
    }

    private void resetTapTheGrey() {
        Log.d(TAG, "resetTapTheGrey: ");
        mScore = -1;
        isRunning = true;
        mScoreBoard.setText(String.format("Score: %s", String.valueOf(0)));
        mStart.setEnabled(true);
        mStart.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.button));
        mStart.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
        UNIT_TIME = TapTheGrey.Time.ONE_SECOND;
        mSpeedBooster = TapTheGrey.Count.TEN;
        mMaxScoreView.setText(String.valueOf(mMaxScore));
        isLevelUp = false;
    }

    private void hitGrey(int hitGrey, View v) {
        Log.d(TAG, "hitGrey: " + hitGrey);
        switch (hitGrey) {
            case 1: {
                Log.d(TAG, "hitGrey: 1 " + v.getTag());
                if (v.getTag() != null)
                    isRunning = true;
                break;
            }
            case 2: {
                Log.d(TAG, "hitGrey: 2" + v.getTag());
                if (v.getTag() != null)
                    isRunning = true;
                break;
            }
            case 3: {
                Log.d(TAG, "hitGrey: 3" + v.getTag());
                if (v.getTag() != null)
                    isRunning = true;
                break;
            }
            case 4: {
                Log.d(TAG, "hitGrey: 4" + v.getTag());
                if (v.getTag() != null)
                    isRunning = true;
                break;
            }
            case 5: {
                Log.d(TAG, "hitGrey: 5" + v.getTag());
                if (v.getTag() != null)
                    isRunning = true;
                break;
            }
            case 6: {
                Log.d(TAG, "hitGrey: 6" + v.getTag());
                if (v.getTag() != null)
                    isRunning = true;
                break;
            }
            case 7: {
                Log.d(TAG, "hitGrey: 7" + v.getTag());
                if (v.getTag() != null)
                    isRunning = true;
                break;
            }
            case 8: {
                Log.d(TAG, "hitGrey: 8" + v.getTag());
                if (v.getTag() != null)
                    isRunning = true;
                break;
            }
            case 9: {
                Log.d(TAG, "hitGrey: 9" + v.getTag());
                if (v.getTag() != null)
                    isRunning = true;
                break;
            }
        }
    }

    public static int getRandomBetweenRange(int min, int max) {
        Log.d(TAG, "getRandomBetweenRange: ");
        int currentRandomNumber = (int) ((Math.random() * ((max - min) + 1)) + min);
        Log.d(TAG, "getRandomBetweenRange: currentRandomNumber" + currentRandomNumber);
        if (mLastRandomNumber == currentRandomNumber) {
            return getRandomBetweenRange(1, 9);
        } else {
            mLastRandomNumber = currentRandomNumber;
            return currentRandomNumber;
        }
    }


    private void chooseBoxToFillColor(int chooseBox) {
        Log.d(TAG, "chooseBoxToFillColor: ");
        switch (chooseBox) {
            case 1: {
                Log.d(TAG, "chooseBoxToFillColor: 1");
                fillColorInBox(mImageOne, ContextCompat.getColor(mActivity, R.color.red));
                break;
            }
            case 2: {
                Log.d(TAG, "chooseBoxToFillColor: 2");
                fillColorInBox(mImageTwo, ContextCompat.getColor(mActivity, R.color.yellow));
                break;
            }
            case 3: {
                Log.d(TAG, "chooseBoxToFillColor: 3");
                fillColorInBox(mImageThree, ContextCompat.getColor(mActivity, R.color.tomato));
                break;
            }
            case 4: {
                Log.d(TAG, "chooseBoxToFillColor:4 ");
                fillColorInBox(mImageFour, ContextCompat.getColor(mActivity, R.color.blue));
                break;
            }
            case 5: {
                Log.d(TAG, "chooseBoxToFillColor: 5");
                fillColorInBox(mImageFive, ContextCompat.getColor(mActivity, R.color.green));
                break;
            }

            case 6: {
                Log.d(TAG, "chooseBoxToFillColor: 6");
                fillColorInBox(mImageSix, ContextCompat.getColor(mActivity, R.color.teal_green));
                break;
            }
            case 7: {
                Log.d(TAG, "chooseBoxToFillColor: 7");
                fillColorInBox(mImageSeven, ContextCompat.getColor(mActivity, R.color.voilet));
                break;
            }
            case 8: {
                Log.d(TAG, "chooseBoxToFillColor: 8");
                fillColorInBox(mImageEight, ContextCompat.getColor(mActivity, R.color.light_brownish));
                break;
            }
            case 9: {
                Log.d(TAG, "chooseBoxToFillColor: 9");
                fillColorInBox(mImageNine, ContextCompat.getColor(mActivity, R.color.pink));
                break;
            }

        }
    }

    private void fillColorInBox(ImageView boxToColor, int color) {
        Log.d(TAG, "fillColorInBox: ");
        boxToColor.setTag(TapTheGrey.Color.GREY);
        boxToColor.setBackgroundColor(mActivity.getColor(R.color.darkgrey));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: ");
                boxToColor.setTag(null);
                boxToColor.setBackgroundColor(color);
                handler.removeCallbacks(this);
            }
        }, UNIT_TIME);
    }

    public interface TapTheGreyActivityInteraction {
        void launchLevelThree(int maxScore, String passedLevel);
    }

}
