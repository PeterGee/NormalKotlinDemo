package com.example.myapplication.touchablePop

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.customView.BlurUtil
import kotlinx.android.synthetic.main.activity_custom_pop.*

/**
 * @date 2020/12/4
 * @author qipeng
 * @desc 自定义popUpWindow activity
 */
class CustomPopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_pop)
        showPop()
        blurPicture()
    }

    private fun blurPicture() {
        val mBitmap = BitmapFactory.decodeResource(resources, R.drawable.dog01)
        // blur
        btn_blur.setOnClickListener {
            val desBitmap = BlurUtil.blur(this, mBitmap, 10f)
            img_shown.setImageBitmap(desBitmap)
        }

    }

    private fun showPop() {
        btn_pop_jump.setOnClickListener {
            val mPop = TouchablePopUpWindow(this)
            mPop.showAtLocation(
                window.decorView, Gravity.BOTTOM, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
        }
    }
}