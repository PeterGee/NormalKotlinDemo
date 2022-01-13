package com.example.myapplication.designMode.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.myapplication.R
import com.example.myapplication.designMode.bean.UserBean

/**
 * @date 2022/1/13
 * @author qipeng
 * @desc
 */
class HorizontalListAdapter(layoutId: Int) : BaseQuickAdapter<UserBean, BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder, item: UserBean?) {
        helper.getView<ImageView>(R.id.imgTitle).apply {
            if (item?.isSelected == true) {
                setBackgroundResource(R.mipmap.ic1)
            } else {
                setBackgroundResource(R.mipmap.ic2)
            }
        }
    }
}