package com.example.myapplication.touchablePop

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.Toast
import com.example.myapplication.R
import java.lang.reflect.Field
import kotlin.math.abs

/**
 * @date 2020/12/15
 * @author qipeng
 * @desc
 */
class TouchablePopUpWindow constructor(
    context: Context
) : PopupWindow(context) {

    private var mContext = context
    private var mStartY: Int = 0
    private var mStartYF: Float = 0f
    private var mDiffY: Int = 0

    init {
        width = WindowManager.LayoutParams.MATCH_PARENT
        height = WindowManager.LayoutParams.MATCH_PARENT
        setBackgroundDrawable(null)
        val mContentView = LayoutInflater.from(context).inflate(R.layout.touchable_pop, null)
        val mIvTop = mContentView.findViewById<ImageView>(R.id.iv_top)
        animationStyle = R.style.PopupWindowAnimation
        contentView = mContentView
        fitPopupWindowOverStatusBar()
        isClippingEnabled = false

        mIvTop.setOnTouchListener { _, event ->
            event.run {
                when (action) {
                    MotionEvent.ACTION_DOWN -> {
                        mStartY = rawY.toInt()
                        mStartYF = rawY
                    }
                    MotionEvent.ACTION_MOVE -> {
                        mDiffY = rawY.toInt() - mStartY
                        if (mDiffY < 0) {
                            var mAlpha =
                                1 - (abs(rawY - mStartYF) / (getDisplayMetrics(mContext).widthPixels) * 1f)
                            if (mAlpha < 0f) mAlpha = 0f
                            if (mAlpha > 1f) mAlpha = 1f
                            mIvTop.alpha = mAlpha
                            Log.d("peter", "mAlpha=== $mAlpha")
                        } else {
                            // 限制方向
                            mDiffY = 0
                        }
                        update(0, -mDiffY, -1, -1, true)
                    }
                    MotionEvent.ACTION_UP -> {
                        // click
                        if (abs(rawY.toInt() - mStartY) < 10) {
                            Toast.makeText(mContext, "clicked", Toast.LENGTH_SHORT).show()
                        } else {
                            dismiss()
                        }

                    }
                }
            }
            true
        }


    }

    private fun fitPopupWindowOverStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                val mLayoutInScreen: Field =
                    PopupWindow::class.java.getDeclaredField("mLayoutInScreen")
                mLayoutInScreen.isAccessible = true
                mLayoutInScreen.set(this, true)
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }

    private fun getDisplayMetrics(context: Context): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        (context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
            .defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }

}