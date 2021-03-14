package com.example.myapplication.jetpack_demo.liveData.view

import android.os.Bundle
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

    private val transferUser=MutableLiveData<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data_demo)
        // initViewModel
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        initListener()
        initData()
    }

    private fun initListener() {
        getUserData.setOnClickListener {
            userViewModel.requestUserData()
        }
        // 修改数据
        btnChange.setOnClickListener {
            userViewModel.userBean.postValue(User(name = "李四", age = 24))
        }


    }

    private fun initData() {
        userViewModel.userBean.observe(this, Observer {
            tvUserInfo.text = "名字是:${it.name},年龄是:${it.age}"

        })

    }
}