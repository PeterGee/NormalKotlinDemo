package com.example.myapplication.diffUtil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_diff.*

/**
 * @Author qipeng
 * @Date 2023/9/6
 * @Desc
 */
class DiffActivity : AppCompatActivity() {

    private var mOldList = mutableListOf<String>()
    private var mNewList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff)
        initData()
        initView()
    }

    private fun initData() {
        mOldList.apply {
            add("first")
            add("two")
            add("three")
            add("four")
        }

        mNewList.apply {
            add("first")
            add("three")
            add("two")
            add("six")
        }

    }

    private fun initView() {
        val mAdapter = DiffAdapter(mOldList,mOldList)
        rv_diff_list.apply {
            layoutManager = LinearLayoutManager(this@DiffActivity)
            adapter = mAdapter
        }

        btn_refresh.setOnClickListener {
           mAdapter.updateListData(mNewList)
        }
    }
}