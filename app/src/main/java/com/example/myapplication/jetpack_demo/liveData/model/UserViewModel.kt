package com.example.myapplication.jetpack_demo.liveData.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 *@date 2021-03-13
 *@author geqipeng
 *@desc userViewModel
 */
class UserViewModel : ViewModel() {

    val userBean = MutableLiveData<User>()


    /**
     * 获取用户数据
     */
    fun requestUserData() {
        // 模拟耗时100ms
        runBlocking {
            delay(100)
            userBean.value = User("张三", 18)
        }
    }



}


data class User(var name: String, var age: Int)