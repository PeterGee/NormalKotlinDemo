package com.example.myapplication.touchablePop

import android.content.Context
import android.view.View
import com.example.myapplication.R
import razerdp.basepopup.BasePopupWindow

/**
 * @date 2021/7/1
 * @author qipeng
 * @desc
 */
class SearchPop(context: Context):BasePopupWindow(context) {
    override fun onCreateContentView(): View {
        return createPopupById(R.layout.search_pop)
    }
}