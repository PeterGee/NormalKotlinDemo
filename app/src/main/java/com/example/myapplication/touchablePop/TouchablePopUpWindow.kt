package com.example.myapplication.touchablePop

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import com.example.myapplication.R

/**
 * @date 2020/12/4
 * @author qipeng
 * @desc
 */
class TouchablePopUpWindow @JvmOverloads constructor(
    context: Context
) : PopupWindow(context) {

    private var mStartY: Int = 0
    private var mDiffY: Int = 0

    init {
        width = WindowManager.LayoutParams.MATCH_PARENT
        height = WindowManager.LayoutParams.MATCH_PARENT
        // 设置bitmapDrawable 解决存在边缘问题
        setBackgroundDrawable(BitmapDrawable())
        val mContentView = LayoutInflater.from(context).inflate(R.layout.touchable_pop, null)
        val mTvBottom = mContentView.findViewById<TextView>(R.id.tv_bottom)
        animationStyle=R.style.PopupWindowAnimation
        contentView = mContentView
        isClippingEnabled = false

        mContentView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mStartY = event.y.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    mDiffY = event.rawY.toInt() - mStartY
                    if (mDiffY < -100) {
                        dismiss()
                    } else if (mDiffY < 0){
                        update(0, -mDiffY, -1, -1, true)
                    }
                }
            }
            true
        }
        mTvBottom.setOnClickListener {
            dismiss()
        }
    }

}