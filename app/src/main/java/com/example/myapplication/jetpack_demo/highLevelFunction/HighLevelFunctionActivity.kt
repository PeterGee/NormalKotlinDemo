package com.example.myapplication.jetpack_demo.highLevelFunction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityJetpackComposeBinding
import com.example.myapplication.okHttp.util.LogUtil
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * @Author qipeng
 * @Date 2024/10/18
 * @Desc 高阶函数
 */
class HighLevelFunctionActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityJetpackComposeBinding
    private val addData = fun(a: Int, b: Int): Int = a + b
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityJetpackComposeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {
        mBinding.tvProcess.setOnClickListener {
            test()
        }
    }

    // 普通函数
    private fun multiplyByTwo(a: Int, b: Int): Int {
        return a * b
    }

    // 普通函数，函数作为参数
    private fun processNumber(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
        return operation(a, b)
    }

    // 返回函数类型
    private fun backFun(a: Int, b: Int): (Int) -> Int {
        return { number -> number * a * b }
    }

    // 内联函数，函数作为参数
    private inline fun performOperation(operation: (Int, Int) -> Int, a: Int, b: Int): Int {
        return operation(a, b)
    }

    // 高阶函数拓展
    private fun String.processString(action: (String) -> String): String {
        return action(this)
    }

    //  suspend 协程结合高阶函数
    private fun doSomethingAsync(): String {
        return "Hello"
    }

    private fun suspendTest() = runBlocking {
        delay(1000L)
        val result = doSomethingAsync()
        LogUtil.D("HighLevelFunctionActivity", "协程result:$result")
    }

    // 泛型高阶函数
    private fun <T> transform(block: suspend () -> T, transformer: (T) -> Unit) {
        GlobalScope.launch {
            val result = block()
            transformer(result)
        }
    }

    // suspend函数
    private suspend fun fetchData(): String {
        delay(1000L)
        return "Data fetched"
    }

    // 协程结合高阶函数
    @OptIn(DelicateCoroutinesApi::class)
    private fun fetchData(callback: (String) -> Unit) {
        GlobalScope.launch {
            delay(1000L)
            callback("Data fetched")
        }
    }

    fun test() {
        val result = processNumber(2, 3, ::multiplyByTwo)
        LogUtil.D("HighLevelFunctionActivity", "result:$result")

        val numbers = listOf(1, 2, 3)
        val lambdaResult = numbers.map { it * 2 }
        LogUtil.D("HighLevelFunctionActivity", "lambdaResult:$lambdaResult")
        LogUtil.D("HighLevelFunctionActivity", "匿名函数结果：${addData(3, 4)}")
        LogUtil.D("HighLevelFunctionActivity", "高阶返回函数：${backFun(2, 3)(4)}")
        LogUtil.D(
            "HighLevelFunctionActivity",
            "内联函数：${performOperation({ a, b -> a + b }, 2, 3)}"
        )
        LogUtil.D(
            "HighLevelFunctionActivity",
            "高阶函数拓展：${"hello world".processString { it.uppercase() }}"
        )
        fetchData { data ->
            LogUtil.D("HighLevelFunctionActivity", "协程：Received data: $data")
        }
        suspendTest()

        transform({ fetchData() }) { data ->
            LogUtil.D("HighLevelFunctionActivity", "协程：${data}")
        }
    }

}