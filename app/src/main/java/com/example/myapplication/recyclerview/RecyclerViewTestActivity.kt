package com.example.myapplication.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRecycvlerViewTestBinding
import com.example.myapplication.okHttp.util.LogUtil

/**
 * @Author qipeng
 * @Date 2022/8/25
 * @Desc
 */
class RecyclerViewTestActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRecycvlerViewTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recycvler_view_test)

        initView()
        printChildHeight()
    }

    private fun printChildHeight() {
        val childCount = mBinding.clRoot.childCount
        LogUtil.D("tag","childCount=== $childCount")
        mBinding.clRoot.children.iterator().forEach {
            view ->
             LogUtil.D("tag", "height=== ${view.height}")
        }

        for (i in 0..childCount) {
           // LogUtil.D("tag", "height=== ${mBinding.clRoot.getChildAt(i).measuredHeight}")
        }
    }

    private fun initView() {
        mBinding.rvTest.apply {
            layoutManager = LinearLayoutManager(this@RecyclerViewTestActivity)
            adapter = RecyclerTestAdapter(
                arrayListOf(
                    "测试数据01",
                    "测试数据02",
                    "测试数据03",
                    "测试数据04",
                    "测试数据05"
                )
            )
        }

        LogUtil.D(
            "tag", "measuredHeight=== ${mBinding.rvTest.measuredHeight}   " +
                    "  height===${mBinding.rvTest.height}" +
                    "  minHeight==${mBinding.rvTest.minimumHeight}"
        )


    }

    override fun onResume() {
        super.onResume()
        mBinding.rvTest.post {
            LogUtil.D(
                "tag", "   onResume measuredHeight=== ${mBinding.rvTest.measuredHeight}   " +
                        "  height===${mBinding.rvTest.height}" +
                        "  minHeight==${mBinding.rvTest.minimumHeight}"
            )
        }
        mBinding.rvTest.invalidate()

    }



}