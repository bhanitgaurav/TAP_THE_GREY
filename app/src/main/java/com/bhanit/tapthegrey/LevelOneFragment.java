package com.bhanit.tapthegrey;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import com.bhanit.tapthegrey.utils.constants.TapTheGrey;

public class LevelOneFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = LevelOneFragment.class.getSimpleName();
    private ImageView mImageOne, mImageTwo, mImageThree, mImageFour;
    private TextView mStart, mScoreBoard, mBhanitgauravEmail, mMaxScoreView;
    private static int mLastRandomNumber;
    private int mSpeedBooster = TapTheGrey.Count.TEN;
    private int UNIT_TIME = TapTheGrey.Time.ONE_SECOND;
    private boolean isRunning;
    private static int mScore = -1;
    private static int mMaxScore = 0;
    private TapTheGreyActivity mActivity;

    public static LevelOneFragment newInstance() {
        Log.d(TAG, "newInstance: ");
        LevelOneFragment levelOneFragment = new LevelOneFragment();
        return levelOneFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_level_one, container, false);
        initViewAndListener(view);
        resetTapTheGrey();
        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (TapTheGreyActivity) context;
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }

    private void initViewAndListener(View view) {
        Log.d(TAG, "initViewAndListener: ");
        mImageOne = view.findViewById(R.id.one);
        mImageTwo = view.findViewById(R.id.two);
        mImageThree = view.findViewById(R.id.three);
        mImageFour = view.findViewById(R.id.four);
        mStart = view.findViewById(R.id.start);
        mScoreBoard = view.findViewById(R.id.score);
        mBhanitgauravEmail = view.findViewById(R.id.bottom_name);
        mMaxScoreView = view.findViewById(R.id.max_score);
        mImageOne.setOnClickListener(this);
        mImageTwo.setOnClickListener(this);
        mImageThree.setOnClickListener(this);
        mImageFour.setOnClickListener(this);
        mStart.setOnClickListener(this);
        mBhanitgauravEmail.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        switch (v.getId()) {
            case R.id.bottom_name: {
                Log.d(TAG, "onClick: bhanitgaurav mail");
                mailTextSet();
                break;
            }
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
        }
    }

    private void mailTextSet() {
        Log.d(TAG, "mailTextSet: ");
        String URL = "https://www.linkedin.com/in/bhanitgaurav";
        mBhanitgauravEmail.setText(HtmlCompat.fromHtml(
                "<font color='#0062b0'> <a href=\"" + URL + "\">Bhanit Gaurav</a> </font>"
                , HtmlCompat.FROM_HTML_MODE_LEGACY));
        mBhanitgauravEmail.setMovementMethod(LinkMovementMethod.getInstance());
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
            chooseBoxToFillColor(getRandomBetweenRange(1, 4));
            handler.postDelayed(runnable, UNIT_TIME);
            updateScoreAndBoostSpeed();
            isRunning = false;
            mStart.setEnabled(false);
            mStart.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.button_disable));
            mStart.setTextColor(ContextCompat.getColor(mActivity, R.color.grey));
        } else showAlertBox();
        Log.d(TAG, "startPlay:isRunning " + isRunning);
    }

    private void updateScoreAndBoostSpeed() {
        Log.d(TAG, "updateScoreAndBoostSpeed:mUpdateScore " + mScore);
        Log.d(TAG, "updateScoreAndBoostSpeed: mSpeedBooster " + mSpeedBooster);
        Log.d(TAG, "updateScoreAndBoostSpeed: UNIT TIME " + UNIT_TIME);

        mScore = mScore + 1;
        mScoreBoard.setText(String.format("Score: %s", String.valueOf(mScore)));
        if (mScore == mSpeedBooster) {
            UNIT_TIME = UNIT_TIME - TapTheGrey.Count.TEN * TapTheGrey.Count.TEN;
            mSpeedBooster = mSpeedBooster + TapTheGrey.Count.TEN;
            Toast toast = Toast.makeText(mActivity, "Speed Boosted",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER, 00, 450);
            toast.show();
            Log.d(TAG, "updateScoreAndBoostSpeed: speed boosted");
        }
        if (mScore > mMaxScore)
            mMaxScore = mScore;
    }

    private void showAlertBox() {
        Log.d(TAG, "showAlertBox()");
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
        alertDialog.setTitle(getResources().getString(R.string.game_over));
        alertDialog.setMessage(getString(R.string.your_score) + String.valueOf(mScore));
        alertDialog.setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.play_again), (DialogInterface dialog, int which) -> {
                    alertDialog.setCancelable(true);
                })
                .setCancelable(true);
        AlertDialog alert = alertDialog.create();
        alert.show();
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
        }
    }

    public static int getRandomBetweenRange(int min, int max) {
        Log.d(TAG, "getRandomBetweenRange: ");
        int currentRandomNumber = (int) ((Math.random() * ((max - min) + 1)) + min);
        if (mLastRandomNumber == currentRandomNumber)
            return getRandomBetweenRange(1, 4);
        else {
            mLastRandomNumber = currentRandomNumber;
            return currentRandomNumber;
        }
    }


    private void chooseBoxToFillColor(int chooseBox) {
        Log.d(TAG, "chooseBoxToFillColor: ");
        switch (chooseBox) {
            case 1: {
                Log.d(TAG, "chooseBoxToFillColor:1 ");
                fillColorInBox(mImageOne, ContextCompat.getColor(mActivity, R.color.blue));
                break;
            }
            case 2: {
                Log.d(TAG, "chooseBoxToFillColor: 2");
                fillColorInBox(mImageTwo, ContextCompat.getColor(mActivity, R.color.red));
                break;
            }
            case 3: {
                Log.d(TAG, "chooseBoxToFillColor: 3");
                fillColorInBox(mImageThree, ContextCompat.getColor(mActivity, R.color.yellow));
                break;
            }
            case 4: {
                Log.d(TAG, "chooseBoxToFillColor: 4");
                fillColorInBox(mImageFour, ContextCompat.getColor(mActivity, R.color.green));
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

}
