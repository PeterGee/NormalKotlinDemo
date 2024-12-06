package com.example.myapplication.layerList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLayerListBinding
import kotlinx.android.synthetic.main.activity_layer_list.numberProgress
import java.util.ArrayList


/**
 * @Author qipeng
 * @Date 2024/11/20
 * @Desc layerList
 */
class LayerListActivity:AppCompatActivity() {
    private lateinit var mBinding: ActivityLayerListBinding
    private val progressList= ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLayerListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
    }
    private fun initView() {
        numberProgress.setData("100", 100)
        progressList.add(10)
        progressList.add(90)
        val mAdapter = ProgressAdapter(R.layout.item_progress)
        mBinding.rvProgress.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter = mAdapter
        }
        mAdapter.setNewData(progressList)



    }
}