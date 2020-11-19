package com.example.myapplication.customView

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_indicator_demo.*

/**
 * @date 2019/10/15
 * @author qipeng
 * @desc
 */
class IndicatorDemoActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_indicator_demo)
        initView()
    }

    private fun initView() {
        val list= listOf(
            R.drawable.dog01,
            R.drawable.dog02,
            R.drawable.dog03,
            R.drawable.dog04,
            R.drawable.dog05
        )
        val adapter= ConsultantAdapter(list, this)
        vp_item_consultant.adapter=adapter
        vp_item_indicator_consultant.setListSize(list.size)
        vp_item_indicator_consultant.setUpWithViewpager(vp_item_consultant)
    }
}