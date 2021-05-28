package com.example.myapplication.refreshlayout

import com.chad.library.adapter.base.entity.MultiItemEntity

/*
 * @date 2021/5/28 
 * @author qipeng
 * @desc
 */
data class ItemBean(val beanType: Int, val name: String) : MultiItemEntity {
    override fun getItemType(): Int {
        return beanType
    }

    companion object {
        const val TYPE_ONE = 1
        const val TYPE_TWO = 2
        const val TYPE_THREE = 3
        const val TYPE_FOUR = 4
    }

}
