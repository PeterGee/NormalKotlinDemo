package com.example.myapplication.customView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_bezier_line.*

/**
 * @date 2019/10/30
 * @author qipeng
 * @desc 贝塞尔曲线
 */
class BezierLineActivity : AppCompatActivity(),
    CustomSeekBar.IRangeChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bezier_line)
        initView()
    }

    private fun initView() {
        btn_move_one.setOnClickListener {
            gift_view.addImageView()
        }
        custom_slide_bar.setOnRangeListener(this)

    }

    override fun onRange(low: Float, high: Float) {
        tv_progress.text="进度值是：$low - $high"
    }

}