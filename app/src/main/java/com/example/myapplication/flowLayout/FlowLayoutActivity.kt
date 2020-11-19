package com.example.myapplication.flowLayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
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
        rv_flow.adapter=FlowLayoutAdapter(this,data)
    }
}