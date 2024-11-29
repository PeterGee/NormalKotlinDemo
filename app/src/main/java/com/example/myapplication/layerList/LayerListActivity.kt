package com.example.myapplication.layerList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityLayerListBinding

/**
 * @Author qipeng
 * @Date 2024/11/20
 * @Desc layerList
 */
class LayerListActivity:AppCompatActivity() {
    private lateinit var mBinding: ActivityLayerListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLayerListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
    }
    private fun initView() {
        mBinding.progressBar.progress = 30
        mBinding.progressBar2.progress = 80
        mBinding.progressBar3.progress = 90
        mBinding.progressBar4.progress = 100
        mBinding.progressBar5.progress = 10
    }
}