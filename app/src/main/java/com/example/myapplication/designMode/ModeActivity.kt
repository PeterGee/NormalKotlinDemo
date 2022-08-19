package com.example.myapplication.designMode

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.designMode.adapter.HorizontalListAdapter
import com.example.myapplication.designMode.bean.UserBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_mode.*

/**
 * @date 2021/12/16
 * @author qipeng
 * @desc
 */
class ModeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode)
        val sp = getSharedPreferences("serialVersion", MODE_PRIVATE)

        btn_save.setOnClickListener {
            sp.edit().putString("userBean", Gson().toJson(UserBean("张三"))).commit()
        }

        btn_read.setOnClickListener {
            val userBeanJson = sp.getString("userBean", "")
            if (userBeanJson != null) {
                val mBean = Gson().fromJson(userBeanJson, UserBean::class.java)
                Log.d(javaClass.canonicalName, "mBean  name=== " + mBean.name)
            }
        }

        rv_horizontal.apply {
            layoutManager =
                LinearLayoutManager(this@ModeActivity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(DividerItemDecoration(this@ModeActivity,DividerItemDecoration.VERTICAL))
            val mAdapter = HorizontalListAdapter(R.layout.item_custom_img)
            adapter = mAdapter
            mAdapter.setNewData(getData())
            scrollToPosition(2)
        }


    }

    private fun getData(): MutableList<UserBean> {
        val dataList = mutableListOf<UserBean>()
        for (i in 0..11) {
            dataList.add(UserBean("哈哈哈", 18, i == 2))
        }

        dataList.forEachIndexed{
                index,item->
            run {
                println("index=== $index  item== $item")
            }
        }

        return dataList
    }

}