package com.example.myapplication.jetpackdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.observe
import androidx.lifecycle.switchMap
import com.example.myapplication.R
import com.example.myapplication.viewpager2.ViewPagerTwoActivity
import kotlinx.android.synthetic.main.activity_jetpack_test.tv_appbar

/**
 * @date 2020/9/15
 * @author qipeng
 * @desc jetPack 测试类
 */
class JetPackTestActivity : AppCompatActivity() {
    val mTag = "jetPack"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack_test)
        liveDataTest(mTag)
        lifecycleTest()
        viewModelFunction()
        initListener()
    }

    private fun initListener() {
        tv_appbar.setOnClickListener {
            startActivity(Intent().apply { setClass(this@JetPackTestActivity,ViewPagerTwoActivity::class.java) })
        }
        tv_appbar.text="lalallalalalallaallalaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
    }

    private fun viewModelFunction() {
        val model= ViewModelProvider(this)[ViewModelTest::class.java]
        model.getName().observe(this) { data ->
            Log.d(mTag, "viewModel data is :$data")
        }
    }

    /**
     * lifecycle基本使用
     */
    private fun lifecycleTest() {
        lifecycle.addObserver(LifecycleObserver())
    }

    override fun onResume() {
        super.onResume()
        Log.d(mTag,"onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(mTag,"onPause")
    }

    /**
     * liveData基本使用
     */
    private fun liveDataTest(Tag: String) {
        val mLiveData = MutableLiveData<String>()
        val mLiveDataTwo = MutableLiveData<String>()
        // switcher
        val mLiveDataSwitch = MutableLiveData<Boolean>()
        mLiveData.observe(this
        ) { t -> Log.d(Tag, "t is $t") }

        // postValue 工作线程使用
        // setValue 主线程使用
        mLiveData.postValue("firstLiveData")

        // Transformations.map()
        val mTransformations = mLiveData.map { t ->
            "it is my firstLiveData $t"
        }
        mTransformations.observe(this) { data ->
            Log.d(Tag, "transformed data is $data")
        }

        // 更新数据
        mLiveData.postValue("secondLiveData")

        // switchMap
        val mSwitchMapData = mLiveDataSwitch.switchMap{ transformedData ->
            if (transformedData) {
                mLiveData
            } else {
                mLiveDataTwo
            }
        }
        mSwitchMapData.observe(this) { switchMapData ->
            Log.d(Tag, "switchMap data is $switchMapData")
        }
        mLiveDataSwitch.postValue(true)
        mLiveData.postValue("mLiveData")
        mLiveDataTwo.postValue("mLiveDataTwo")

        mLiveDataSwitch.postValue(false)
        mLiveData.postValue("mLiveData")
        mLiveDataTwo.postValue("mLiveDataTwo")

        // 合并多个数据
        val mediatorData = MediatorLiveData<String>()
        mediatorData.addSource(
            mLiveData
        ) { t -> Log.d(Tag, "mLiveData change data is : $t") }

        mediatorData.addSource(
            mLiveDataTwo
        ) { t -> Log.d(Tag, "mLiveDataTwo change data is : $t") }

        mediatorData.observe(this) { mediatorData ->
            Log.d(Tag, "final mediatorData data is : $mediatorData")
        }

        mLiveData.postValue("mediatorData")
    }
}