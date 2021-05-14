package com.example.myapplication.flowLayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityFlowLayoutBinding
import com.example.myapplication.flowLayout.model.ModelTest
import kotlinx.android.synthetic.main.activity_flow_layout.*

/**
 * @date 2019/11/28
 * @author qipeng
 * @desc 流式布局
 */
class FlowLayoutActivity :AppCompatActivity(){
    private lateinit var binding:ActivityFlowLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding=DataBindingUtil.setContentView(this,R.layout.activity_flow_layout)
        val user=ModelTest("peter",18)
        binding.user=user


        val data= listOf(R.drawable.dog01,R.drawable.dog02,R.drawable.dog03,R.drawable.dog04,R.drawable.dog05,
            R.drawable.dog01,R.drawable.dog02,R.drawable.dog03,R.drawable.dog04,R.drawable.dog05,
            R.drawable.dog01,R.drawable.dog02,R.drawable.dog03,R.drawable.dog04,R.drawable.dog05,
            R.drawable.dog01,R.drawable.dog02,R.drawable.dog03,R.drawable.dog04,R.drawable.dog05)
        val layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        rv_flow.layoutManager=layoutManager
        val mAdapter=FlowLayoutAdapter(this,data)
        rv_flow.adapter=mAdapter
        val result=DiffUtil.calculateDiff(MyCallBack(data, data))
        // 刷新diff条目
       // mAdapter.news = news
        result.dispatchUpdatesTo(mAdapter)
    }

    inner class MyCallBack(oldList:List<Int>,newList:List<Int>):DiffUtil.Callback(){
        private val mOldList=oldList
        private val mNewList=newList
        override fun getOldListSize(): Int {
         return mOldList.size
        }

        override fun getNewListSize(): Int {
            return mNewList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
           return mOldList[oldItemPosition]==mNewList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return mOldList[oldItemPosition]==mNewList[newItemPosition]
        }
    }
}