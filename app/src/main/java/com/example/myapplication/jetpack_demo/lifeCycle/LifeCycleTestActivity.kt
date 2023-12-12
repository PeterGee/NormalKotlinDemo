package com.example.myapplication.jetpack_demo.lifeCycle

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.myapplication.R
import com.example.myapplication.customView.CustomDragWidget
import com.example.myapplication.okHttp.util.LogUtil
import kotlinx.android.synthetic.main.activity_life_cycle.cdwWidget
import kotlinx.android.synthetic.main.activity_life_cycle.iv_animal
import kotlinx.android.synthetic.main.activity_life_cycle.iv_cart
import kotlinx.android.synthetic.main.activity_life_cycle.view.iv_animal
import kotlinx.android.synthetic.main.activity_life_cycle.view.iv_cart


/**
 * @Author qipeng
 * @Date 2023/11/28
 * @Desc
 */
class LifeCycleTestActivity : AppCompatActivity() {
    private val TAG = this.javaClass.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycle)
        LogUtil.D(TAG, "onCreate()")
        cdwWidget.setChildClickListener(object :CustomDragWidget.IChildClickListener{
            override fun onChildClicked(child: View) {
                if (child == iv_cart){
                    Toast.makeText(this@LifeCycleTestActivity,"购物车点击",Toast.LENGTH_SHORT).show()
                }else if (child == iv_animal){
                    Toast.makeText(this@LifeCycleTestActivity,"动物点击",Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    override fun onStart() {
        super.onStart()
        LogUtil.D(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.D(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.D(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.D(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.D(TAG, "onDestroy()")
    }
}