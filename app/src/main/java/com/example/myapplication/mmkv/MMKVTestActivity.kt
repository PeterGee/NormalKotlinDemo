package com.example.myapplication.mmkv

import android.os.Bundle
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMmkvBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mMmkv = MMKV.defaultMMKV()
        initView()
    }

    private fun initView() {
        mBinding.btnAddData.setOnClickListener {
            mMmkv.encode("stringOne", "stringOne")
            mMmkv.encode("intValue", 100)
            mMmkv.encode("booleanValue", true)
            mMmkv.encode("longValue", 1.1111)
            queryAndSetText()
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
        builder.append(strValue)
        builder.append(intValue)
        builder.append(boolValue)
        builder.append(longValue)

        mBinding.etData.setText(builder.toString())
    }
}