package com.example.myapplication.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDeepLinkBuilder
import com.example.myapplication.R

/**
 * @date 2020/4/7
 * @author qipeng
 * @desc 导航组件
 */
class NavigationActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
       // val deepLinkBuilder=NavDeepLinkBuilder(applicationContext).setDestination(R.id.android_pay)
            //.setArguments(args).createPendingIntent().setGraph()
    }
}