package com.example.myapplication.greendao

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.myapplication.R
import com.example.myapplication.greendao.entity.Note

/**
 * @Author qipeng
 * @Date 2023/5/20
 * @Desc
 */
class MyNoteAdapter(id:Int):BaseQuickAdapter<Note,BaseViewHolder>(id) {
    override fun convert(helper: BaseViewHolder, item: Note?) {
        val tvContent= helper.getView<TextView>(R.id.tv_content)
        tvContent.text=item?.text
    }
}