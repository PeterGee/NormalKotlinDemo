package com.example.myapplication.greendao

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityGreenDaoTestBinding
import com.example.myapplication.greendao.entity.Note
import java.util.*

/**
 * @Author qipeng
 * @Date 2023/5/20
 * @Desc
 */
class FirstGreenDaoActivity :AppCompatActivity(){

    private lateinit var  mBinding:ActivityGreenDaoTestBinding

    private val TAG="FirstGreenDaoActivity"

    private val mDataList=ArrayList<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_green_dao_test)
        init()
    }

    private fun init() {
        val mAdapter=MyNoteAdapter(R.layout.item_note)
        mBinding.rvNote.apply {
            adapter = mAdapter
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }

        mBinding.btnAddNote.setOnClickListener {
            for (i in 0..10L){
                val note=Note().apply {
                    id = i
                    text = "第${i}条笔记"
                    date = Date()
                }
                mDataList.add(note)
                DaoManager.getInstance(application).insertDir(note)
                Log.d(TAG,"insertSuccess")
                mAdapter.setNewData(mDataList)
            }
        }

        mBinding.btnDelete.setOnClickListener {
            DaoManager.getInstance(application).deleteItem(mDataList[0])
            Log.d(TAG,"deleteSuccess")
            mAdapter.notifyDataSetChanged()
        }
        mBinding.btnDeleteAll.setOnClickListener {
            DaoManager.getInstance(application).deleteAllDir()
            Log.d(TAG,"deleteAllSuccess")
            mAdapter.notifyDataSetChanged()
        }

        mBinding.updateNote.setOnClickListener {
            val updatedItem= mDataList[0]
            updatedItem.text="修改过的笔记"
            DaoManager.getInstance(application).updateItem(updatedItem)
            Log.d(TAG,"updateSuccess")
            mAdapter.notifyDataSetChanged()
        }

        mBinding.queryNote.setOnClickListener {
            val mList=DaoManager.getInstance(application).queryList()
            mDataList.clear()
            mDataList.addAll(mList)
            Log.d(TAG,"querySuccess size is ${mDataList.size}")
            mAdapter.notifyDataSetChanged()
        }

        mBinding.queryNoteById.setOnClickListener {
            val mList=DaoManager.getInstance(application).queryList(1)
            mDataList.clear()
            mDataList.addAll(mList)
            Log.d(TAG,"querySuccess size is ${mDataList.size}")
            mAdapter.notifyDataSetChanged()
        }
    }
}