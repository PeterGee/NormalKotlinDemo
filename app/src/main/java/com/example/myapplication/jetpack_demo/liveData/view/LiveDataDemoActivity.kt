package com.example.myapplication.jetpack_demo.liveData.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.example.myapplication.R
import com.example.myapplication.jetpack_demo.liveData.model.User
import com.example.myapplication.jetpack_demo.liveData.model.UserViewModel
import kotlinx.android.synthetic.main.activity_live_data_demo.*

/**
 *@date 2021-03-13
 *@author geqipeng
 *@desc
 */
class LiveDataDemoActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel


    private var mUser = MutableLiveData<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data_demo)
        // initViewModel
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        initData()
        initListener()
    }

    private fun initListener() {
        getUserData.setOnClickListener {
            userViewModel.requestUserData()
        }
        // 修改数据
        btnChange.setOnClickListener {
            userViewModel.userBean.postValue(User(name = "李四", age = 24))
        }


        val mNameAll = MutableLiveData<String>()
        // 设置数据
        mNameAll.postValue("张三")

        // map操作 对存储在 LiveData 对象中的值应用函数，并将结果传播到下游。

        val resultNameAll:LiveData<String> = mNameAll.map {
                nameAll -> "$nameAll, 李四"
        }

        val transformData=mUser.map {
            it.name + "${it.age}"
        }

        transformData.observe(this){
            Log.d("tag", "transformData $it")
        }


        var mUser = MutableLiveData<User>()
        val mMapUser = MutableLiveData<User>()
        val mSwitchMapUser = MutableLiveData<User>()
        val mIsMap = MutableLiveData<Boolean>()
        // switchMap 将结果解封和分派到下游。传递给 switchMap() 的函数必须返回 LiveData 对象
        val mSwitchMapData = mIsMap.switchMap { mIsMap ->
            if (mIsMap) {
                mMapUser.postValue(mUser.value)
                mMapUser
            } else {
                mSwitchMapUser
            }
        }

        mSwitchMapData.observe(this) {
            Log.d("tag", "mSwitchMapData ${it.name}   ${it.age}")
            // D/tag: mSwitchMapData 李四   24
        }
        // switchMap
        btnSwitchMap.setOnClickListener {
            mSwitchMapUser.postValue(User("李四", 24))
            mIsMap.postValue(false)
        }

        // mapChange
        btnMapChange.setOnClickListener {
            mIsMap.postValue(true)
        }



        mIsMap.observe(this) {
            if (true) {
                tvUserInfo.text = mMapUser.value.toString()
            } else {
                tvUserInfo.text = mSwitchMapUser.value.toString()
            }

        }


        val mNameOne = MutableLiveData<String>()
        val mNameTwo = MutableLiveData<String>()
        val mediatorLiveData = MediatorLiveData<String>()
        // 依次给mNameOne和mNameTwo设置值
        mNameOne.postValue("张三")
        mNameTwo.postValue("李四")

        mediatorLiveData.addSource(mNameOne) {
            Log.d("tag", "mNameOne $it")
            //  D/tag: mNameOne 张三
            mediatorLiveData.value = it
        }
        mediatorLiveData.addSource(mNameTwo) {
            Log.d("tag", "mNameTwo $it")
            // D/tag: mNameTwo 李四
            mediatorLiveData.value = it
        }
        mediatorLiveData.observe(this) {
            Log.d("tag", "mediatorLiveData  is $it")
        }

        // D/tag: mNameOne 张三
        // D/tag: mediatorLiveData  is 张三
        // D/tag: mNameTwo 李四
        // D/tag: mediatorLiveData  is 李四

        // 通过日志可以看到，因为mNameOne和mNameTwo作为mediatorLiveData的source,
        // mediatorLiveData可以监听到两者的值

        // https://www.itranslater.com/qa/details/2583127127965041664

    }

    private fun initData() {
        userViewModel.userBean.observe(this, Observer {
            mUser.postValue(it)
            tvUserInfo.text = "名字是:${it.name},年龄是:${it.age}"
        })

    }
}