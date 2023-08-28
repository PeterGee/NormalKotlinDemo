package com.example.myapplication.animation

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.R
import com.example.myapplication.animation.util.CartAnimUtil


/**
 * @Author qipeng
 * @Date 2023/8/19
 * @Desc
 */
class CartActivity : FragmentActivity() {

    private lateinit var mAddOne: ImageView
    private lateinit var mAddTwo: ImageView
    private lateinit var mAddThree: ImageView
    private lateinit var mCart: ImageView
    private lateinit var mRootView: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        initView()
        initListener()
    }

    // 初始化控件
    private fun initView() {
        mRootView = window.decorView as ViewGroup
        mCart = findViewById(R.id.mCart)
        mAddOne = findViewById(R.id.mAddOne)
        mAddTwo = findViewById(R.id.mAddTwo)
        mAddThree = findViewById(R.id.mAddThree)
    }

    // 初始化监听
    private fun initListener() {
        mAddOne.setOnClickListener { view ->CartAnimUtil.playAnim(this,view,mCart,mRootView)
        // playAnim(view)
            // AnimatorUtils.doCartAnimator(this,mAddOne,mCart,mRootView,null)
        }
        mAddTwo.setOnClickListener { view -> CartAnimUtil.playAnim(this,view,mCart,mRootView) }
        mAddThree.setOnClickListener { view -> CartAnimUtil.playAnim(this,view,mCart,mRootView) }
    }


}