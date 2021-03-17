package com.example.myapplication.lamada

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

/**
 * @date 2021/3/17
 * @author qipeng
 * @desc lamada 测试类
 */
class LamadaTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lamada_test)

        val myLamada: (Int) -> Unit = { s: Int -> println(s) }
       // addNumber(10, 11, myLamada)

        // functionInline { println("调用内联函数") }

        // functionInlineTwo({ println("调用内联函数")return},{ println("内联函数中的下一个参数")} )

       // functionThree({ println("this is first inline func")},{ println("second inline func")})
       // functionFour({println("this is first func")return}, { println("this is second func")})
        functionFour({ println("调用内联函数")},{ println("内联函数中的下一个参数")})

    }

    fun addNumber(a: Int, b: Int, mLamada: (Int) -> Unit) {
        val add = a + b
        mLamada(add)
    }


    inline fun functionInline(mFun:()->Unit){
        mFun()
        println("内联函数内代码")
    }

    inline fun functionInlineTwo(myFun:()->Unit,nxFun:()->Unit){
        myFun()
        nxFun()
        println("非局部控制流程")
    }

    inline fun functionThree(crossinline myFun: () -> Unit,nxFun: () -> Unit){
        myFun()
        nxFun()
        println("code inside inline function")
    }

    inline  fun functionFour(myFun: () -> Unit,noinline nxFun: () -> Unit){
        myFun()
        nxFun()
        println("this is function four")
    }
}