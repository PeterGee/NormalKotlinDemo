package com.example.myapplication.layerList

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.myapplication.R
import com.example.myapplication.widget.CapsuleIndicatorProgress

/**
 * @Author qipeng
 * @Date 2024/12/2
 * @Desc
 */
class ProgressAdapter(layoutId: Int):BaseQuickAdapter<Int,BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder, item: Int) {
        val mProgress = helper.getView<CapsuleIndicatorProgress>(R.id.progressBar)
        mProgress.progress = item
    }

}