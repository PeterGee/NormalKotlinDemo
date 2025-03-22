package com.example.myapplication.mmkv

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.myapplication.databinding.ActivityEncryptedSpBinding
import kotlinx.android.synthetic.main.activity_encrypted_sp.queryData


/**
 * @Author qipeng
 * @Date 2025/3/22
 * @Desc 加密sharePreference
 */
class EncryptedSpActivity :AppCompatActivity(){
    lateinit var mBinding: ActivityEncryptedSpBinding
    private var  editor:SharedPreferences.Editor? = null
    private var  sharedPreferences:SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityEncryptedSpBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initSharePreference()
        initView()
    }

    private fun initSharePreference() {
        val masterKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        sharedPreferences = EncryptedSharedPreferences.create(
            this,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        editor = sharedPreferences?.edit()
    }

    private fun initView() {
        mBinding.addInt.setOnClickListener {
            editor?.putInt(KEY_INT,123)?.commit()
            queryData()
        }
        mBinding.addLong.setOnClickListener {
            editor?.putLong(KEY_LONG,123456789)?.commit()
            queryData()
        }
        mBinding.addBoolean.setOnClickListener {
            editor?.putBoolean(KEY_BOOLEAN,true)?.commit()
            queryData()
        }
        mBinding.addString.setOnClickListener {
            editor?.putString(KEY_STRING,"encrypted")?.commit()
            queryData()
        }
        mBinding.DeleteData.setOnClickListener {
            editor?.clear()?.commit()
            queryData()
        }

        mBinding.queryData.setOnClickListener {
            queryData()
        }


    }

    private fun queryData() {
        sharedPreferences?.apply {
            val buffer = StringBuffer()
            buffer.append(" int:  ")
            buffer.append(getInt(KEY_INT, 0))
            buffer.append(" long:  ")
            buffer.append(getLong(KEY_LONG, 0))
            buffer.append(" boolean:  ")
            buffer.append(getBoolean(KEY_BOOLEAN, false))
            buffer.append(" string:  ")
            buffer.append(getString(KEY_STRING, ""))
            mBinding.etContent.setText(buffer.toString())
        }
    }

    companion object{
        const val KEY_INT = "key_int"
        const val KEY_LONG = "key_long"
        const val KEY_BOOLEAN = "key_boolean"
        const val KEY_STRING = "key_string"
    }
}