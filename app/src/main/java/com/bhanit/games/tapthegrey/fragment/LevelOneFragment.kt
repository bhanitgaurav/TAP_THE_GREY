package com.bhanit.games.tapthegrey.fragment

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bhanit.games.tapthegrey.R
import com.bhanit.games.tapthegrey.activity.TapTheGreyActivity
import com.bhanit.games.tapthegrey.helper.Log
import com.bhanit.games.tapthegrey.utils.constants.TapTheGrey

class LevelOneFragment : Fragment(), View.OnClickListener {
    private var mImageOne: ImageView? = null
    private var mImageTwo: ImageView? = null
    private var mImageThree: ImageView? = null
    private var mImageFour: ImageView? = null
    private var mStart: TextView? = null
    private var mScoreBoard: TextView? = null
    private var mMaxScoreView: TextView? = null
    private var mSpeedBooster = TapTheGrey.Count.TEN
    private var UNIT_TIME = TapTheGrey.Time.ONE_SECOND
    private var isRunning = false
    private var mActivity: TapTheGreyActivity? = null
    private var mTapTheGreyActivityInteraction: TapTheGreyActivityInteraction? = null
    private var isLevelUp = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        val view = inflater.inflate(R.layout.fragment_level_one, container, false)
        initViewAndListener(view)
        resetTapTheGrey()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as TapTheGreyActivity
        mTapTheGreyActivityInteraction = context
        Log.d(TAG, "onStart: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivity = null
        mTapTheGreyActivityInteraction = null
    }

    private fun initViewAndListener(view: View) {
        Log.d(TAG, "initViewAndListener: ")
        mImageOne = view.findViewById(R.id.one)
        mImageTwo = view.findViewById(R.id.two)
        mImageThree = view.findViewById(R.id.three)
        mImageFour = view.findViewById(R.id.four)
        mStart = view.findViewById(R.id.start)
        mScoreBoard = view.findViewById(R.id.score)

        mMaxScoreView = view.findViewById(R.id.max_score)
        mImageOne!!.setOnClickListener(this)
        mImageTwo!!.setOnClickListener(this)
        mImageThree!!.setOnClickListener(this)
        mImageFour!!.setOnClickListener(this)
        mStart!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        Log.d(TAG, "onClick: ")
        val buttonId = v.id
        when (buttonId) {
            R.id.start -> {
                Log.d(TAG, "onClick: start")
                mTapTheGreyActivityInteraction!!.enableLock(true)
                startPlay()
            }

            R.id.one -> {
                Log.d(TAG, "onClick: one")
                hitGrey(1, v)
            }

            R.id.two -> {
                Log.d(TAG, "onClick: two")
                hitGrey(2, v)
            }

            R.id.three -> {
                Log.d(TAG, "onClick: three")
                hitGrey(3, v)
            }

            R.id.four -> {
                Log.d(TAG, "onClick: four")
                hitGrey(4, v)
            }
        }
    }

    private fun startPlay() {
        Log.d(TAG, "startPlay: ")
        val runnable: Runnable
        val handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                Log.d(TAG, "run: ")
                startPlay()
                handler.removeCallbacks(this)
            }
        }
        Log.d(
            TAG,
            "startPlay:isRunning $isRunning"
        )
        if (isRunning) {
            chooseBoxToFillColor(getRandomBetweenRange(1, 4))
            handler.postDelayed(runnable, UNIT_TIME.toLong())
            updateScoreAndBoostSpeed()
            isRunning = false
            mStart!!.isEnabled = false
            mStart!!.background = ContextCompat.getDrawable(mActivity!!, R.drawable.button_disable)
            mStart!!.setTextColor(ContextCompat.getColor(mActivity!!, R.color.grey))
        } else showAlertBox(isLevelUp)
        Log.d(
            TAG,
            "startPlay:isRunning $isRunning"
        )
    }

    private fun updateScoreAndBoostSpeed() {
        Log.d(TAG, "updateScoreAndBoostSpeed:mUpdateScore $mScore")
        Log.d(
            TAG,
            "updateScoreAndBoostSpeed: mSpeedBooster $mSpeedBooster"
        )
        Log.d(
            TAG,
            "updateScoreAndBoostSpeed: UNIT TIME $UNIT_TIME"
        )

        mScore += 1
        mScoreBoard!!.text = String.format("Score: %s", mScore)
        if (mScore == mSpeedBooster) {
            UNIT_TIME -= TapTheGrey.Count.SEVEN * TapTheGrey.Count.SEVEN
            mSpeedBooster += TapTheGrey.Count.TEN
            val toast = Toast.makeText(
                mActivity, "Speed Boosted",
                Toast.LENGTH_SHORT
            )
            toast.setGravity(Gravity.TOP or Gravity.CENTER, 0, 150)
            toast.show()
            Log.d(TAG, "updateScoreAndBoostSpeed: speed boosted")
        }
        if (mScore > mMaxScore) mMaxScore = mScore
        if (mScore == TapTheGrey.LevelChange.LEVEL_CHANGE_SCORE_FOR_LEVEL_ONE) {
            levelOnePassedAndOpenLevelTwo()
        }
    }

    private fun levelOnePassedAndOpenLevelTwo() {
        Log.d(TAG, "levelOnePassedAndOpenLevelTwo: ")
        isLevelUp = true
        mTapTheGreyActivityInteraction!!.unlockTheLock(TapTheGrey.LevelInInteger.ONE)
        alertDialog(getString(R.string.level_one_passed), getString(R.string.go_to_next_level))
    }

    private fun alertDialog(message: String, smallMessage: String) {
        Log.d(TAG, "alertDialog()")
        val openDialog = Dialog(mActivity!!)
        openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        openDialog.window!!
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        openDialog.setContentView(R.layout.custom_permission_dialog)

        val image = openDialog.findViewById<ImageView>(R.id.image_view)
        image.visibility = View.VISIBLE

        val layout = image.parent as ConstraintLayout
        layout.setBackgroundColor(ContextCompat.getColor(mActivity!!, R.color.color_level_passed))

        val dialogTextContent = openDialog.findViewById<TextView>(R.id.heading)
        val smallText = openDialog.findViewById<TextView>(R.id.description)

        dialogTextContent.text = message
        smallText.text = smallMessage

        val rightButton = openDialog.findViewById<TextView>(R.id.allow)
        rightButton.text = resources.getString(R.string.next_level)

        val leftButton = openDialog.findViewById<TextView>(R.id.not_allow)
        leftButton.text = resources.getString(R.string.play_again)
        leftButton.setTextColor(ContextCompat.getColor(mActivity!!, R.color.colorBlack))

        openDialog.setCanceledOnTouchOutside(false)

        rightButton.setOnClickListener {
            Log.d(
                TAG,
                "onClick: "
            )
            openDialog.dismiss()
            mTapTheGreyActivityInteraction!!.launchLevelTwo()
        }
        leftButton.setOnClickListener {
            Log.d(
                TAG,
                "onClick: "
            )
            openDialog.dismiss()
            resetTapTheGrey()
        }
        openDialog.show()
    }

    private fun showAlertBox(isLevelUp: Boolean) {
        Log.d(
            TAG,
            "showAlertBox() isLevelUp: $isLevelUp"
        )
        val alertDialog = AlertDialog.Builder(
            mActivity!!
        )
        alertDialog.setIcon(ContextCompat.getDrawable(mActivity!!, R.mipmap.ic_launcher_round))
        alertDialog.setTitle(resources.getString(R.string.game_over))
        alertDialog.setMessage(getString(R.string.your_score) + " " + mScore)
        alertDialog.setCancelable(false)
            .setPositiveButton(
                resources.getString(R.string.play_again)
            ) { _: DialogInterface?, _: Int ->
                alertDialog.setCancelable(true)
            }
            .setCancelable(true)
        if (!isLevelUp) {
            val alert = alertDialog.create()
            alert.show()
        }
        mTapTheGreyActivityInteraction!!.enableLock(false)
        resetTapTheGrey()
    }

    private fun resetTapTheGrey() {
        Log.d(TAG, "resetTapTheGrey: ")
        mScore = -1
        isRunning = true
        mScoreBoard!!.text = String.format("Score: %s", 0)
        mStart!!.isEnabled = true
        mStart!!.background = ContextCompat.getDrawable(mActivity!!, R.drawable.button)
        mStart!!.setTextColor(ContextCompat.getColor(mActivity!!, R.color.white))
        UNIT_TIME = TapTheGrey.Time.ONE_SECOND
        mSpeedBooster = TapTheGrey.Count.TEN
        mMaxScoreView!!.text = mMaxScore.toString()
        isLevelUp = false
    }

    private fun hitGrey(hitGrey: Int, v: View) {
        Log.d(
            TAG,
            "hitGrey: $hitGrey"
        )
        when (hitGrey) {
            1 -> {
                Log.d(TAG, "hitGrey: 1 " + v.tag)
                if (v.tag != null) isRunning = true
            }

            2 -> {
                Log.d(TAG, "hitGrey: 2" + v.tag)
                if (v.tag != null) isRunning = true
            }

            3 -> {
                Log.d(TAG, "hitGrey: 3" + v.tag)
                if (v.tag != null) isRunning = true
            }

            4 -> {
                Log.d(TAG, "hitGrey: 4" + v.tag)
                if (v.tag != null) isRunning = true
            }
        }
    }

    private fun chooseBoxToFillColor(chooseBox: Int) {
        Log.d(TAG, "chooseBoxToFillColor: ")
        when (chooseBox) {
            1 -> {
                Log.d(TAG, "chooseBoxToFillColor:1 ")
                fillColorInBox(mImageOne, ContextCompat.getColor(mActivity!!, R.color.blue))
            }

            2 -> {
                Log.d(TAG, "chooseBoxToFillColor: 2")
                fillColorInBox(mImageTwo, ContextCompat.getColor(mActivity!!, R.color.red))
            }

            3 -> {
                Log.d(TAG, "chooseBoxToFillColor: 3")
                fillColorInBox(mImageThree, ContextCompat.getColor(mActivity!!, R.color.yellow))
            }

            4 -> {
                Log.d(TAG, "chooseBoxToFillColor: 4")
                fillColorInBox(mImageFour, ContextCompat.getColor(mActivity!!, R.color.green))
            }

        }
    }

    private fun fillColorInBox(boxToColor: ImageView?, color: Int) {
        Log.d(TAG, "fillColorInBox: ")
        boxToColor!!.tag = TapTheGrey.Color.GREY
        boxToColor.setBackgroundColor(mActivity!!.getColor(R.color.darkgrey))
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                Log.d(TAG, "run: ")
                boxToColor.tag = null
                boxToColor.setBackgroundColor(color)
                handler.removeCallbacks(this)
            }
        }, UNIT_TIME.toLong())
    }

    interface TapTheGreyActivityInteraction {
        fun launchLevelTwo()

        fun unlockTheLock(levelToUnlock: Int)

        fun enableLock(isEnabled: Boolean)
    }

    companion object {
        private val TAG: String = LevelOneFragment::class.java.simpleName
        private var mLastRandomNumber = 0
        private var mScore = -1
        private var mMaxScore = 0
        fun newInstance(): LevelOneFragment {
            Log.d(TAG, "newInstance: ")
            val levelOneFragment = LevelOneFragment()
            return levelOneFragment
        }

        fun getRandomBetweenRange(min: Int, max: Int): Int {
            Log.d(TAG, "getRandomBetweenRange: ")
            val currentRandomNumber = ((Math.random() * ((max - min) + 1)) + min).toInt()
            if (mLastRandomNumber == currentRandomNumber) return getRandomBetweenRange(1, 4)
            else {
                mLastRandomNumber = currentRandomNumber
                return currentRandomNumber
            }
        }
    }
}