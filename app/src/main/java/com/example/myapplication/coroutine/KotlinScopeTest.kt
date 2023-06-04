package com.example.myapplication.coroutine

import android.app.Activity
import android.os.Bundle
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException
import kotlin.coroutines.coroutineContext
import kotlin.system.measureTimeMillis

/**
 * @Author qipeng
 * @Date 2023/6/4
 * @Desc kotlin协程
 */
class KotlinScopeTest :Activity(){
    private val mainScope= MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainScope.launch {
            repeat(5){
                delay(1000L*it)
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}

fun main(){
        // simpleCoroutine()
        // runBlockMethod()
        // runBlockingMethodTwo()
        // coroutineScopeMethod()
        // supervisorScopeMethod()
        // customCoroutineMethod()
        //  coroutineBuilderLaunch()
        //  jobMethod()
        // asyncMethodTest()
        //  asyncMethodTestTwo()
        //  coroutineContextMethodOne()
        //  coroutineDispatcherMethod()
        // withContextMethod()
        // coroutineNameMethod()
       // cancelCoroutineMethod()
        // cancelCoroutineMethodTwo()
        // releaseCoroutine()
        nonCancelableCoroutine()
    }

/**
 * 不可取消的协程
 */
fun nonCancelableCoroutine() = runBlocking {
    log("start")

}

/**
 * finally 中释放资源
 */
fun releaseCoroutine() = runBlocking {
    val job = launch {
        try {
            repeat(1000){
                log("Job : I am sleeping $it")
                delay(500)
            }
        }catch (e: Throwable){
            log(e.message?:"")
        }finally {
            log("Job : i am running finally ")
        }
    }
    delay(1000)
    log("main: i am tired of waiting")
    job.cancelAndJoin()
    log("Main : now i am quit")
}

/**
 * 取消协程，同时判断协程存活状态
 */
fun cancelCoroutineMethodTwo() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextTime = startTime
        var i = 0
        while (i <5){
            if (isActive){
                if (System.currentTimeMillis()>=nextTime){
                    log("job: i am sleeping ${i++}")
                    nextTime += 500L
                }
            }else{
                return@launch
            }
        }
    }
    delay(1000)
    log("main: i am tired of waiting")
    job.cancelAndJoin()
    log("Main : now i am quit")
}

/**
* 取消协程
 */
fun cancelCoroutineMethod() = runBlocking {
    val job = launch {
        repeat(100){
            log("job: i am sleeping $it")
            delay(500)
        }
    }
    delay(2000)
    log("main: i am tired of waiting")
    // job.cancel()
    // job.join()
    // 通过job复合方法cancelAndJoin执行
    job.cancelAndJoin()
    log("Main : now i am quit")
}

// 指定协程名
fun coroutineNameMethod() = runBlocking <Unit>(CoroutineName("RunBlocking")){
    log("start")
    launch (CoroutineName("MainCoroutine")){
        launch(CoroutineName("Coroutine#A")) {
            delay(400)
            log("launch A")
        }
        launch(CoroutineName("Coroutine#B")) {
            delay(300)
            log("launch B")
        }
    }
    log("end")
}

fun withContextMethod() {
    suspend fun get(url:String){
        withContext(Dispatchers.IO){
            log("io线程执行")
        }
    }
}

/**
 * 协程调度器
 * Kotlin 协程库提供了四个 Dispatcher 用于指定在哪一类线程中执行协程：
* Dispatchers.Default。默认调度器，适合用于执行占用大量 CPU 资源的任务。例如：对列表排序和解析 JSON
* Dispatchers.IO。适合用于执行磁盘或网络 I/O 的任务。例如：使用 Room 组件、读写磁盘文件，执行网络请求
* Dispatchers.Unconfined。对执行协程的线程不做限制，可以直接在当前调度器所在线程上执行
* Dispatchers.Main。使用此调度程序可用于在 Android 主线程上运行协程，只能用于与界面交互和执行快速工作，例如：更新 UI、调用 LiveData.setValue
 */
fun coroutineDispatcherMethod() {
    runBlocking {
        launch {
            log("main runBlocking")
        }
        launch(Dispatchers.Default) {
            log("Default")
            launch(Dispatchers.Unconfined) {
                log("Unconfined 1")
            }
        }
        launch(Dispatchers.IO) {
            log("IO")
            launch(Dispatchers.Unconfined) {
                log("Unconfined 2")
            }
        }
        // 专用线程
        launch(newSingleThreadContext("MyOwnThread")) {
            log("newSingleThreadContext")
            launch(Dispatchers.Unconfined){
                log("Unconfined 4")
            }
        }
        launch(Dispatchers.Unconfined) {
            log("Unconfined 3")
        }
        GlobalScope.launch {
            log("GlobalScope")
        }
    }
}

// 结合coroutineContext和Job来动态取消协程中job
fun coroutineContextMethodOne() {
    val job = Job()
    val scope = CoroutineScope(job + Dispatchers.IO)
    runBlocking {
        log("job is $job")
        val job = scope.launch {
            try {
                delay(3000)
            }catch (e:CancellationException){
                log("job is cancelled")
                throw e
            }
            log("end")
        }
        delay(1000)
        log("scope job is ${scope.coroutineContext[Job]}")
        scope.coroutineContext[Job]?.cancel()
    }

}

/**
 * async并行分解
 */
suspend fun fetchTwoDocs() {
    // 单独等待方式
    log("start")
   coroutineScope {
       val deferOne = async { delay(100) }
       val deferTwo = async { delay(200) }
       deferOne.await()
       deferTwo.await()
   }
    log("end")
}

/**
 * 调用awaitAll方法
 */
suspend fun fetchMethod3() {
    // 使用list 调用awaitAll方法
    log("start")
    coroutineScope {
        val defer = listOf(
            async { delay(100) },
            async { delay(200) })
        defer.awaitAll()
    }
    log("end")
}




/**
 * async 错误示范
 */
fun asyncMethodTestTwo() {
    val time = measureTimeMillis {
        runBlocking {
           // CoroutineStart.LAZY 不会主动启动协程，而是直到调用async.await()或者async.satrt()后才会启动
            // （即懒加载模式）

            // 异步任务A
            val asyncA = async(start = CoroutineStart.LAZY) {
                delay(300)
                1
            }
            // 异步任务B
            val asyncB = async(start = CoroutineStart.LAZY) {
                delay(400)
                2
            }
            val asyncC = async {
                delay(500)
                3
            }
            log(asyncA.await()+asyncB.await()+asyncC.await())
        }
    }
    // 耗时
    log(time)
}

fun asyncMethodTest() {
    //  执行短耗时任务时总耗时接近多个耗时任务之和，执行长耗时任务时约等于最长耗时任务所消耗时间
    val time = measureTimeMillis {
        runBlocking {
            // 异步任务A
            val asyncA = async {
                delay(300)
                1
            }
            // 异步任务B
            val asyncB = async {
                delay(400)
                2
            }
            val asyncC = async {
                delay(500)
                3
            }
            log(asyncA.await()+asyncB.await()+asyncC.await())
        }
    }
    // 耗时
    log(time)
}

fun jobMethod() {
    // 延迟启动协程
    val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
        for (i in 0..10){
            delay(100)
        }
    }

    job.invokeOnCompletion {
        log("invokeOnCompletion $it")
    }
    log("1 job isActive ${job.isActive}")
    log("1 job isCancelled ${job.isCancelled}")
    log("1 job isCompleted ${job.isCompleted}")

    // 启动job
    job.start()
    log("2 job isActive ${job.isActive}")
    log("2 job isCancelled ${job.isCancelled}")
    log("2 job isCompleted ${job.isCompleted}")

    // 休眠400ms
    Thread.sleep(400)
    job.cancel(CancellationException("test"))

    //休眠四百毫秒防止JVM过快停止导致 invokeOnCompletion 来不及回调
    Thread.sleep(400)

    log("3 job isActive ${job.isActive}")
    log("3 job isCancelled ${job.isCancelled}")
    log("3 job isCompleted ${job.isCompleted}")

}

// launch方法轮流执行
fun coroutineBuilderLaunch() {
    runBlocking {
        val launchA =  launch {
            repeat(3){
                delay(100)
                log("LaunchA - $it")
            }
        }

        val launchB = launch {
            repeat(3){
                delay(100)
                log("LaunchB - $it")

            }
        }
    }
}


// supervisorScope 函数用于创建一个使用了 SupervisorJob
// 的 coroutineScope，该作用域的特点就是抛出的异常不会连锁取消同级协程和父协程
fun supervisorScopeMethod() {
    runBlocking {
        launch {
            delay(100)
            log("Task from runBlocking")
        }

        // supervisor 函数抛出异常后不会阻断协程执行
        supervisorScope {
            launch {
                delay(100)
                log("Task throw exception")
                throw Exception("failed")
            }
        }

        launch {
            delay(600)
            log("Task from nested launch")
        }

    }
    log("Coroutine scope is over")
}

// coroutineScope用于创建独立的协程作用域
fun coroutineScopeMethod() {
    // 普通函数
   runBlocking {
       launch {
           delay(100)
           log("Task from blocking")
       }

       // 挂起函数
       coroutineScope {
           launch {
               delay(500)
               log("Task from nested launch")
           }

           delay(50)
           log("Task from coroutine scope")
       }
       log("Coroutine scope is over")
   }
}

fun runBlockingMethodTwo() {
    // 全局非阻塞
    GlobalScope.launch(Dispatchers.IO) {
        delay(100)
        log("GlobalScope")
    }

    // 阻塞
    runBlocking {
        delay(500)
        log("runBlocking")
    }

    Thread.sleep(200)
    log("after sleep")
}

// runBlocking 本身带有阻塞线程的意味，但其内部运行的协程又是非阻塞的
fun runBlockMethod() {
    log("start")
    runBlocking {
        launch {
            repeat(3){
                delay(100)
                log("Launch A - $it ")
            }
        }

        launch {
            repeat(3){
                delay(100)
                log("LaunchB - $it")
            }
        }

        GlobalScope.launch {
            repeat(3){
                delay(100)
                log("GlobalScope - $it")
            }
        }
    }
    log("end")
}

private fun simpleCoroutine() {
    // context 指定作用域
    log("start")
    val scope=GlobalScope.launch(context = Dispatchers.IO) {
        delay(1000)
        log("launch")

        launch {
            delay(400)
            log("Launch A")
        }

        launch {
            delay(300)
            log("Launch B")
        }

    }
    Thread.sleep(2000)
    log("end")
}

private fun log(msg:Any) = println("[${Thread.currentThread().name}] $msg")

