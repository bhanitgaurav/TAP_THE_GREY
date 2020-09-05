package com.bhanit.tapthegrey;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.bhanit.tapthegrey.utils.constants.TapTheGrey;

public class TapTheGreyActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = TapTheGreyActivity.class.getSimpleName();
    private static int mLastRandomNumber;
    private int mSpeedBooster = TapTheGrey.Count.TEN;
    private int UNIT_TIME = TapTheGrey.Time.ONE_SECOND;
    private ImageView mImageOne, mImageTwo, mImageThree, mImageFour;
    private TextView mStart, mScoreBoard, mBhanitgauravEmail, mMaxScoreView;
    private boolean isRunning;
    private static int mScore = -1;
    private static int mMaxScore = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_the_grey);
        initViewAndListener();
    }

    private void initViewAndListener() {
        Log.d(TAG, "initViewAndListener: ");
        mImageOne = findViewById(R.id.one);
        mImageTwo = findViewById(R.id.two);
        mImageThree = findViewById(R.id.three);
        mImageFour = findViewById(R.id.four);
        mStart = findViewById(R.id.start);
        mScoreBoard = findViewById(R.id.score);
        mBhanitgauravEmail = findViewById(R.id.bottom_name);
        mMaxScoreView = findViewById(R.id.max_score);
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
            mStart.setBackground(ContextCompat.getDrawable(this, R.drawable.button_disable));
            mStart.setTextColor(ContextCompat.getColor(this, R.color.grey));
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
            Toast toast = Toast.makeText(this, "Speed Boosted",
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
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
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
        mStart.setBackground(ContextCompat.getDrawable(this, R.drawable.button));
        mStart.setTextColor(ContextCompat.getColor(this, R.color.white));
        UNIT_TIME = TapTheGrey.Time.ONE_SECOND;
        mSpeedBooster = TapTheGrey.Count.TEN;
        mMaxScoreView.setText(String.valueOf(mMaxScore));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        resetTapTheGrey();
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
                fillColorInBox(mImageOne, ContextCompat.getColor(this, R.color.blue));
                break;
            }
            case 2: {
                Log.d(TAG, "chooseBoxToFillColor: 2");
                fillColorInBox(mImageTwo, ContextCompat.getColor(this, R.color.red));
                break;
            }
            case 3: {
                Log.d(TAG, "chooseBoxToFillColor: 3");
                fillColorInBox(mImageThree, ContextCompat.getColor(this, R.color.yellow));
                break;
            }
            case 4: {
                Log.d(TAG, "chooseBoxToFillColor: 4");
                fillColorInBox(mImageFour, ContextCompat.getColor(this, R.color.green));
                break;
            }

        }
    }

    private void fillColorInBox(ImageView boxToColor, int color) {
        Log.d(TAG, "fillColorInBox: ");
        boxToColor.setTag(TapTheGrey.Color.GREY);
        boxToColor.setBackgroundColor(getColor(R.color.darkgrey));
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

