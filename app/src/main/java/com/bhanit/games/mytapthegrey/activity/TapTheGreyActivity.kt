package com.bhanit.games.mytapthegrey.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.bhanit.games.mytapthegrey.R
import com.bhanit.games.mytapthegrey.fragment.LevelOneFragment
import com.bhanit.games.mytapthegrey.fragment.LevelThreeFragment
import com.bhanit.games.mytapthegrey.fragment.LevelTwoFragment
import com.bhanit.games.mytapthegrey.fragment.MeFragment
import com.bhanit.games.mytapthegrey.fragment.UnlockAllFragment
import com.bhanit.games.mytapthegrey.helper.Log
import com.bhanit.games.mytapthegrey.utils.constants.TapTheGrey

class TapTheGreyActivity : AppCompatActivity(), View.OnClickListener,
    LevelOneFragment.TapTheGreyActivityInteraction, LevelTwoFragment.TapTheGreyActivityInteraction,
    LevelThreeFragment.TapTheGreyActivityInteraction,
    UnlockAllFragment.TapTheGreyActivityInteraction,
    MeFragment.TapTheGreyActivityInteraction {
    private val mManager by lazy { supportFragmentManager }
    private lateinit var mBhanitGauravEmail: TextView
    private lateinit var mGameName: TextView
    private lateinit var mHeaderName: TextView
    private var mLevelOneFragment: LevelOneFragment? = null
    private var mLevelTwoFragment: LevelTwoFragment? = null
    private var mLevelThreeFragment: LevelThreeFragment? = null
    private var mUnlockAllFragment: UnlockAllFragment? = null
    private var mCurrentVisibleFragment: Fragment? = null
    private var mPreviousFragment: Fragment? = null
    private lateinit var mUnlockImage: ImageView
    private var mExit = false
    private var developerLockUnlock = 0
    private var mMeFragment: MeFragment? = null
    private lateinit var mOnBackPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_the_grey)
        Log.d(TAG, "onCreate: ")
        initViewsAndSetOnclickListener()
        openLevelOneFragment()
        handleLevelUnlock()
        setOnBackPressed()
    }

    private fun setOnBackPressed() {
        Log.d(TAG, "setOnBackPressed: ")
        mOnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d(TAG, "onBackPressed: ")
                if (mCurrentVisibleFragment is LevelOneFragment && !mExit) {
                    alertExit()
                    return
                }
                if (mCurrentVisibleFragment is LevelTwoFragment) {
                    openLevelOneFragment()
                    return
                }
                if (mCurrentVisibleFragment is LevelThreeFragment) {
                    if (mLevelTwoFragment == null) {
                        openLevelOneFragment()
                        return
                    }
                    launchLevelTwo()
                    return
                }
                if (mCurrentVisibleFragment is UnlockAllFragment || mCurrentVisibleFragment is MeFragment) {
                    openPreviousFragment()
                    mUnlockImage.visibility = View.VISIBLE
                    return
                }
                isEnabled = false // Disable callback before calling super
                onBackPressedDispatcher.onBackPressed()
            }
        }
        onBackPressedDispatcher.addCallback(this, mOnBackPressedCallback)
    }

    private fun openLevelOneFragment() {
        Log.d(TAG, "openLevelOneFragment: ")
        if (mCurrentVisibleFragment != null) hideFragment(mCurrentVisibleFragment)
        if (mLevelOneFragment == null) {
            mLevelOneFragment = LevelOneFragment.newInstance()
        }
        addShowFragment(mLevelOneFragment!!)
    }

    private fun handleLevelUnlock() {
        Log.d(TAG, "handleLevelUnlock: ")
        mUnlockImage.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                if (isLevelLockUnlocked) R.drawable.ic_unlock else R.drawable.ic_lock
            )
        )
    }

    private fun initViewsAndSetOnclickListener() {
        Log.d(TAG, "initViewsAndSetOnclickListener: ")
        mGameName = findViewById(R.id.game_name)
        mHeaderName = findViewById(R.id.textView)
        mBhanitGauravEmail = findViewById(R.id.bottom_name)
        mBhanitGauravEmail.setOnClickListener(this)
        mUnlockImage = findViewById(R.id.lock_unlock)
        mUnlockImage.setOnClickListener(this)
        mGameName.setOnClickListener(this)
    }

    /**
     * Method helps to add new fragment or to show hidden fragment.
     *
     * @param fragment fragment to show or add
     */
    private fun addShowFragment(fragment: Fragment) {
        Log.d(
            TAG,
            "addShowFragment: $fragment"
        )
        mCurrentVisibleFragment = fragment
        val fragmentTransaction =
            mManager.beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
        if (!fragment.isAdded) {
            Log.d(TAG, "addShowFragment: fragment Added")
            changeBackgroundAccordingToFragment(fragment)
            fragmentTransaction.add(R.id.main_layout, fragment).commit()
        } else showFragment(fragment)
    }

    /**
     * Method Helps to hide the fragment
     *
     * @param fragment fragment to hide
     */
    private fun hideFragment(fragment: Fragment?) {
        Log.d(TAG, "hideFragment: ")
        val ft = mManager.beginTransaction()
        ft.hide(fragment!!)
        ft.commit()
    }

    /**
     * Method helps to show the hidden fragment
     *
     * @param fragment fragment to show
     */
    private fun showFragment(fragment: Fragment) {
        Log.d(TAG, "showFragment: ")
        mCurrentVisibleFragment = fragment
        changeBackgroundAccordingToFragment(fragment)
        val ft = mManager.beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
        ft.show(fragment)
        ft.commit()
    }

    private fun changeBackgroundAccordingToFragment(fragment: Fragment?) {
        if (fragment is MeFragment) {
            mGameName.visibility = View.GONE
            mUnlockImage.visibility = View.GONE
        } else {
            if (fragment is UnlockAllFragment) {
                mGameName.visibility = View.GONE
                mHeaderName.visibility = View.VISIBLE
            } else {
                mGameName.visibility = View.VISIBLE
                mHeaderName.visibility = View.GONE
            }
        }

        if (fragment is LevelOneFragment) {
            findViewById<View>(R.id.main_layout).setBackgroundColor(
                ContextCompat.getColor(
                    this, R.color.default_background
                )
            )
            return
        }
        if (fragment is LevelTwoFragment) {
            findViewById<View>(R.id.main_layout).setBackgroundColor(
                ContextCompat.getColor(
                    this, R.color.card_background_color
                )
            )
            return
        }
        if (fragment is LevelThreeFragment) {
            findViewById<View>(R.id.main_layout).setBackgroundColor(
                ContextCompat.getColor(
                    this, R.color.color_organic_brown
                )
            )
            return
        }
        findViewById<View>(R.id.main_layout).setBackgroundColor(
            ContextCompat.getColor(
                this, R.color.cardview_shadow_start_color
            )
        )
    }

    /**
     * Method helps to hide the current fragment and shows the new fragment
     *
     * @param mCurrentFragment current Fragment
     * @param shownFragment    upcoming fragment to show
     */
    private fun hideShowFragment(mCurrentFragment: Fragment, shownFragment: Fragment) {
        hideFragment(mCurrentFragment)
        addShowFragment(shownFragment)
    }

//    override fun onBackPressed() {
//        Log.d(TAG, "onBackPressed: ")
//        if (mCurrentVisibleFragment is LevelOneFragment && !mExit) {
//            alertExit()
//            return
//        }
//        if (mCurrentVisibleFragment is LevelTwoFragment) {
//            openLevelOneFragment()
//            return
//        }
//        if (mCurrentVisibleFragment is LevelThreeFragment) {
//            if (mLevelTwoFragment == null) {
//                openLevelOneFragment()
//                return
//            }
//            launchLevelTwo()
//            return
//        }
//        if (mCurrentVisibleFragment is UnlockAllFragment || mCurrentVisibleFragment is MeFragment) {
//            openPreviousFragment()
//            mUnlockImage.visibility = View.VISIBLE
//            return
//        }
//        super.onBackPressed()
//    }

    private fun openPreviousFragment() {
        Log.d(TAG, "openPreviousFragment: ")
        handleLevelUnlock()
        if (mPreviousFragment == null) openLevelOneFragment()
        else {
            hideFragment(mCurrentVisibleFragment)
            showFragment(mPreviousFragment!!)
        }
    }

    private fun alertExit() {
        Log.d(TAG, "alertExit: ")
        alertDialog(getString(R.string.exit), getString(R.string.are_you_sure_you_want_to_exit))
    }

    private fun alertDialog(message: String, smallMessage: String) {
        Log.d(TAG, "alertDialog()")
        val openDialog = Dialog(this)
        openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        openDialog.window!!
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        openDialog.setContentView(R.layout.custom_permission_dialog)
        val image = openDialog.findViewById<ImageView>(R.id.image_view)
        image.visibility = View.VISIBLE

        val dialogTextContent = openDialog.findViewById<TextView>(R.id.heading)
        val smallText = openDialog.findViewById<TextView>(R.id.description)

        dialogTextContent.text = message
        smallText.text = smallMessage

        val rightButton = openDialog.findViewById<TextView>(R.id.allow)
        rightButton.text = resources.getString(R.string.yes)

        val leftButton = openDialog.findViewById<TextView>(R.id.not_allow)
        leftButton.text = resources.getString(R.string.no)
        leftButton.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))

        openDialog.setCanceledOnTouchOutside(false)

        rightButton.setOnClickListener {
            Log.d(
                TAG,
                "onClick: "
            )
            openDialog.dismiss()
            mExit = true
            onBackPressed()
        }
        leftButton.setOnClickListener {
            Log.d(
                TAG,
                "onClick: "
            )
            openDialog.dismiss()
            mExit = false
            mUnlockImage.visibility = View.VISIBLE
        }
        openDialog.show()
    }

    @SuppressLint("NonConstantResourceId")
    override fun onClick(v: View) {
        Log.d(TAG, "onClick: ")
        val buttonId = v.id

        if (buttonId == R.id.bottom_name) {
            Log.d(TAG, "onClick: bhanitgaurav mail")
            mailTextSet()
        } else if (buttonId == R.id.lock_unlock) {
            Log.d(TAG, "onClick: lock_unlock")
            if (isLevelLockUnlocked || developerLockUnlock == 5) {
                if (developerLockUnlock == 5) mLockUnlockLevel = 4
                isLevelLockUnlocked = true
                handleLevelUnlock()
                openUnlockFragment()
            } else {
                if (developerLockUnlock == 0) Toast.makeText(
                    this,
                    R.string.play_to_unlock_the_lock,
                    Toast.LENGTH_SHORT
                ).show()
                Handler(Looper.getMainLooper()).postDelayed(
                    { developerLockUnlock = 0 },
                    TapTheGrey.Time.TWO_SECOND.toLong()
                )
                developerLockUnlock++
            }
        } else if (buttonId == R.id.game_name) {
            openMeFragment()
        }
    }

    private fun openMeFragment() {
        mPreviousFragment = mCurrentVisibleFragment
        hideFragment(mCurrentVisibleFragment)
        if (mMeFragment == null) {
            mMeFragment = MeFragment.newInstance()
        }
        addShowFragment(mMeFragment!!)
    }

    private fun openUnlockFragment() {
        Log.d(TAG, "openUnlockFragment: ")
        mPreviousFragment = mCurrentVisibleFragment
        hideFragment(mCurrentVisibleFragment)
        if (mUnlockAllFragment == null) {
            mUnlockAllFragment = UnlockAllFragment.newInstance()
        }
        addShowFragment(mUnlockAllFragment!!)
        if (mUnlockAllFragment != null) mUnlockAllFragment!!.unLockLevel(mLockUnlockLevel)
        mUnlockImage.visibility = View.GONE
    }

    private fun mailTextSet() {
        Log.d(TAG, "mailTextSet: ")
        val url = "https://www.bhanitgaurav.com/"
        mBhanitGauravEmail.text = HtmlCompat.fromHtml(
            "<font color='#0062b0'> <a href=\"$url\">Bhanit Gaurav</a> </font>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        mBhanitGauravEmail.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun launchLevelTwo() {
        Log.d(TAG, "launchLevelTwo: ")
        mPreviousFragment = mCurrentVisibleFragment
        hideFragment(mCurrentVisibleFragment)
        if (mLevelTwoFragment == null) {
            mLevelTwoFragment = LevelTwoFragment.newInstance()
        }
        addShowFragment(mLevelTwoFragment!!)
        //        updateMaxScore();
    }

    override fun unlockTheLock(levelToUnlock: Int) {
        Log.d(
            TAG,
            "unlockTheLock: $levelToUnlock"
        )
        isLevelLockUnlocked = true
        if (mLockUnlockLevel < levelToUnlock) mLockUnlockLevel = levelToUnlock
        handleLevelUnlock()
    }

    override fun enableLock(isEnabled: Boolean) {
        Log.d(
            TAG,
            "enableLock: $isEnabled"
        )
        if (!isEnabled) mUnlockImage.visibility = View.VISIBLE
        else mUnlockImage.visibility = View.GONE
    }

    override fun launchLevelThree() {
        Log.d(TAG, "launchLevelThree: maxScore ")
        mPreviousFragment = mCurrentVisibleFragment
        hideFragment(mCurrentVisibleFragment)
        if (mLevelThreeFragment == null) {
            mLevelThreeFragment = LevelThreeFragment.newInstance()
        }
        addShowFragment(mLevelThreeFragment!!)
    }

    override fun launchLevelFour() {
        Log.d(TAG, "launchLevelFour: ")
        //        onBackPressed();
        Toast.makeText(
            this,
            "We are working on next level. Kindly wait for the next update.",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun openLevelFragment(adapterPosition: Int) {
        Log.d(TAG, "openLevelFragment: ")
        if (adapterPosition != 4) mUnlockImage.visibility = View.VISIBLE
        when (adapterPosition) {
            1 -> {
                Log.d(TAG, "onItemClicked: ")
                if (mCurrentVisibleFragment != null) {
                    hideFragment(mCurrentVisibleFragment)
                    mPreviousFragment = mCurrentVisibleFragment
                }
                openLevelOneFragment()
            }

            2 -> {
                Log.d(TAG, "onItemClicked: ")
                launchLevelTwo()
            }

            3 -> {
                Log.d(TAG, "onItemClicked: ")
                launchLevelThree()
            }

            4 -> {
                Log.d(TAG, "onItemClicked: ")
                Toast.makeText(
                    this,
                    "We are working on next level. Kindly wait for the next update.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun openTapTheGreyWebsite() {
        Log.d(TAG, "openTapTheGreyWebsite: ")
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(TapTheGrey.WEBSITE))
        startActivity(browserIntent)
    }

    companion object {
        private val TAG: String = TapTheGreyActivity::class.java.simpleName
        private const val mMaxScoreOnTapTheGrey = 0
        private val mPassedLevel: String? = null
        private var isLevelLockUnlocked = false
        private var mLockUnlockLevel = 0
    }
}