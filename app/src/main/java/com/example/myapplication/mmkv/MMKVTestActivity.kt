package com.example.myapplication.mmkv

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMmkvBinding
import com.tencent.mmkv.MMKV
import java.lang.StringBuilder


/**
 * @Author qipeng
 * @Date 2024/10/15
 * @Desc mmkv
 */
class MMKVTestActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMmkvBinding
    private lateinit var mMmkv: MMKV
    private lateinit var mPreference:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMmkvBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
       //  mMmkv = MMKV.defaultMMKV()
        // 设置指定id
        mMmkv = MMKV.mmkvWithID("MMKVActivityId")
        initSharedPreference()
        initView()
    }

    private fun initSharedPreference() {
        mPreference = getSharedPreferences("testPreferences", Context.MODE_PRIVATE)
        printTime()
        mPreference.edit().putString("preferenceString", "preference").apply()
        printTime()
    }

    private fun initView() {
        mBinding.btnAddData.setOnClickListener {
            printTime()
            mMmkv.encode("stringOne", "stringOne")
            printTime()
            mMmkv.encode("intValue", 100)
            printTime()
            mMmkv.encode("booleanValue", true)
            printTime()
            mMmkv.encode("longValue", 1.1111)
            printTime()
            queryAndSetText()
            printTime()
        }

        mBinding.btnUpdateData.setOnClickListener {
            mMmkv.encode("stringOne", "updateStringOneUpdate")
            mMmkv.encode("intValue", 201)
            mMmkv.encode("booleanValue", true)
            mMmkv.encode("longValue", 1.2222)
            queryAndSetText()
        }

        mBinding.btnDeleteData.setOnClickListener {
            mMmkv.removeValueForKey("stringOne")
            queryAndSetText()
        }

        mBinding.btnClearData.setOnClickListener {
            mMmkv.clearAll()
            queryAndSetText()
        }

        mBinding.btnMigration.setOnClickListener {
            // sharePreference数据迁移
            mMmkv.importFromSharedPreferences(mPreference)
            mPreference.edit().clear().commit()
            queryAndSetText()
        }

        mBinding.btnQueryData.setOnClickListener {
            queryAndSetText()
        }

    }

    private fun queryAndSetText() {
        val builder = StringBuilder()
        val strValue = mMmkv.decodeString("stringOne")
        val intValue = mMmkv.decodeInt("intValue")
        val boolValue = mMmkv.decodeBool("booleanValue").toString()
        val longValue = mMmkv.decodeLong("longValue")
        val preferenceValue = mMmkv.decodeString("preferenceString")
        builder.append(strValue)
        builder.append(intValue)
        builder.append(boolValue)
        builder.append(longValue)
        builder.append(preferenceValue)

        mBinding.etData.setText(builder.toString())
    }

    private fun printTime(){
        Log.d("tag", "time=== "+System.currentTimeMillis())
    }
}