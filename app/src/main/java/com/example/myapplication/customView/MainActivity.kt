package com.example.myapplication.customView

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        // btn_show_dialog.setOnClickListener {
        }

        val str = "Hello World"
       // str.obtainLength(str)
    }

    /**
     * 枚举类
     */
    enum class EnumClass {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    fun foo() {
        val adVariable = object {
            var x: Int = 0
            var y: Int = 1
        }
        print(adVariable.x + adVariable.y)
    }




/**
 * 取消协程的操作
 */
fun main() = runBlocking {
    val job = launch {
        repeat(1000) { i ->
            println("job: I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    job.join() // waits for job's completion
    println("main: Now I can quit.")
}

/**
 * async 使用进行异步执行
 */
fun asyncMethod() = runBlocking<Unit> {
    val time = measureTimeMillis {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

fun launchMethod() {
    var job: Job = GlobalScope.launch(start = CoroutineStart.LAZY) {
        println("协程开始运行，时间: " + System.currentTimeMillis())
    }

    Thread.sleep(1000L)
// 手动启动协程
    job.start()
}

fun suspendMethod() {
    /*  println("协程初始化开始，时间: " + System.currentTimeMillis())

      GlobalScope.launch(Dispatchers.Unconfined) {
          println( "协程初始化完成，时间: " + System.currentTimeMillis())
          for (i in 1..3) {
              println( "协程任务1打印第$i 次，时间: " + System.currentTimeMillis())
          }
          delay(500)
          for (i in 1..3) {
              println( "协程任务2打印第$i 次，时间: " + System.currentTimeMillis())
          }*/

    /* println("主线程 sleep ，时间: " + System.currentTimeMillis())
     Thread.sleep(1000)
     println("主线程运行，时间: " + System.currentTimeMillis())

     for (i in 1..3) {
         println( "主线程打印第$i 次，时间: " + System.currentTimeMillis())
     }*/


    // 单线程suspend使用
    suspend fun getToken(): String {
        delay(300)
        println("getToken 开始执行，时间:  ${System.currentTimeMillis()}")
        return "ask"
    }

    suspend fun getResponse(token: String): String {
        delay(100)
        println("getResponse 开始执行，时间:  ${System.currentTimeMillis()}")
        return "response"
    }

    fun setText(response: String) {
        println("setText 执行，时间:  ${System.currentTimeMillis()}")
    }

// 运行代码
    GlobalScope.launch(Dispatchers.Default) {
        println("协程 开始执行，时间:  ${System.currentTimeMillis()}")
        val token = getToken()
        val response = getResponse(token)
        setText(response)
    }

}

fun coroutineMethod() {
    GlobalScope.launch {
        // 在后台启动一个新的协程并继续
        delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
        println("World!") // 在延迟后打印输出
    }
    println("Hello,") // 协程已在等待时主线程还在继续
    Thread.sleep(2000L) // 阻塞主线程 2 秒钟来保证 JVM 存活
}


private fun unZip() {
    val numberPairs = listOf("one" to 1, "two" to 2, "three" to 3, "four" to 4)
    println(numberPairs.unzip())
}

private fun flatten() {
    val numberSets = listOf(setOf(1, 2, 3), setOf(4, 5, 6), setOf(1, 2))
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        println(numberSets.flatten().toString().chars())
    }
}

/**
 * let方法使用
 */
fun letMethod() {
    val numbers = mutableListOf("one", "two", "three", "four", "five")
    numbers.map { it.length }.filter { it > 3 }.let {
        println(it)
    }
}

private fun takeTest() {
    val oddNumbers = sequence {
        yield(1)
        yieldAll(listOf(3, 5))
        yieldAll(generateSequence(7) { it + 2 })
    }
    println(oddNumbers.take(5).toList())
}

suspend fun doSomethingOne(): Int {
    delay(1000)
    return 10
}

suspend fun doSomethingTwo(): Int {
    delay(1500)
    return 15
}

/**
 * 计算时间
 */
fun suspendMethodMeasureTimeMills() = runBlocking {
    val getTime = measureTimeMillis {
        // val one= doSomethingOne()
        // val two= doSomethingTwo()

        val one = async { doSomethingOne() }
        val two = async { doSomethingTwo() }

        println("the answer is ${one.await() + two.await()}")
    }
    println("Completed in $getTime ms")
}

/**
 * 惰性启动的async
 */
fun lazyAsyncMethod() = runBlocking {

    val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) {
            doSomethingOne()
        }
        val two = async(start = CoroutineStart.LAZY) {
            doSomethingTwo()
        }

        // 执行计算
        one.start()
        two.start()
        println("the answer is ${one.await() + two.await()}")
    }
    println("lazyAsyncMethod Completed in $time ms")
}

/**
 * 失败异常函数
 */
fun failedConcurrentSum() = runBlocking {
    val one = async<Int> {
        try {
            delay(Long.MAX_VALUE)
            42 // 模拟一个长时间运算
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw  ArithmeticException()
    }
    one.await() + two.await()
}

/**
 * launch方法测试
 */
fun launchMethodTest() = runBlocking {
    val a = async {
        println("I am computing a piece of the answer")
        6
    }

    val b = async {
        println("I am computing another piece of the answer")
        7
    }
    println("The answer is ${a.await() * b.await()}")
}

fun childCoroutineMethod() = runBlocking {
    val request = launch {
        GlobalScope.launch {
            println("job1: i run in GlobalScope and execute independently!")
            delay(1000)
            println("job1: i am not affected by cancellation of the request")
        }

        launch {
            delay(100)
            println("job2: i am a child of the request coroutine")
            delay(1000)
            println("job2: i will not execute this line if mt parent request is cancelled")
        }
    }
    delay(500)
    request.cancel()
    delay(1000)
    println("main: who has survived request cancellation?")
}

/**
 * 父协程方法
 * 一个父协程总是等待所有的子协程执行结束。父协程并不显式的跟踪所有子协程的启动，
 * 并且不必使用 Job.join
 */
fun fatherCoroutineMethod() = runBlocking {
    val request = launch {
        repeat(3) { i ->
            // 启动少量的子作业
            launch {
                delay((i + 1) * 200L) // 延迟200ms，400ms、600ms
                println("Coroutine $i is done")
            }
        }
        println(
            "request:i am done and i don't explicitly join my children that are " +
                    "still active"
        )
    }
    request.join() // 等待请求的完成，包括其所有子协程
    println("now processing of the request is complete")
}

/**
 * 命名协程
 */
fun renameCoroutineMethod() = runBlocking {
    println("Started main coroutine")
    val v1 = async(CoroutineName("v1Coroutine")) {
        delay(500)
        println("Computint v1")
        252
    }
    val v2 = async(CoroutineName("v2Coroutine")) {
        delay(1000)
        println("Computint v2")
        6
    }
    println("The answer for v1/v2=${v1.await() / v2.await()}")
}

/**
 * 组合上下文元素
 */
fun combineCoroutineMethod() = runBlocking {
    launch(Dispatchers.Default + CoroutineName("Test"))
    {
        println("i am working in thread ${Thread.currentThread().name}")
    }
}


fun main(args: Array<String>) {
    // takeTest()
    // unZip()
    // flatten()
    // letMethod()
    // coroutineMethod()
    // suspendMethod()
    // launchMethod()
    // main()
    // asyncMethod()
    // suspendMethodMeasureTimeMills()
    // lazyAsyncMethod()
    /*try {
        failedConcurrentSum()
    }catch (e:ArithmeticException){
        println("Computation failed with ArithmeticException")
    }*/
    // launchMethodTest()
    // childCoroutineMethod()
    // fatherCoroutineMethod()
    // renameCoroutineMethod()
    combineCoroutineMethod()
}








