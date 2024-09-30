package com.example.myapplication.viewbinding

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.databinding.ActivityViewBingdingBinding

/**
 * @Author qipeng
 * @Date 2024/9/30
 * @Desc viewBinding使用
 */
class ViewBindingActivity:FragmentActivity() {
    private lateinit var mBinding:ActivityViewBingdingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityViewBingdingBinding.inflate(layoutInflater)
        // 注意使用inflate()方法，返回的是DataBinding实例
        setContentView(mBinding.root)
        addFragment()
        initView()
    }

    private fun addFragment() {
        supportFragmentManager.beginTransaction()
           .add(mBinding.flContainer.id, ViewBindingFragment())
           .commitAllowingStateLoss()
    }

    private fun initView() {
        mBinding.btnFirst.setOnClickListener {
           Toast.makeText(this, "firstButtonClicked",Toast.LENGTH_SHORT).show()
        }
    }
}