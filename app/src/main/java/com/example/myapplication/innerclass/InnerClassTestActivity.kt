package com.example.myapplication.innerclass

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

/**
 * @date 2021/4/8
 * @author qipeng
 * @desc
 */
class InnerClassTestActivity : AppCompatActivity() {
    val iFace = object : InnerClassTwo.IFaceOne {
        override fun faceClicked() {
            printString()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inner_class)
    }


    fun printString() {
        Log.d("tag", "printString")
    }
}