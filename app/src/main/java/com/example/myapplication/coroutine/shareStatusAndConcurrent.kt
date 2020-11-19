package com.example.myapplication.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * @date 2019/10/25
 * @author qipeng
 * @desc 共享状态与并发
 */
@Volatile
var counter = AtomicInteger()
var counter2 =0
val counterContext = newSingleThreadContext("CounterContext")
fun main(args:Array<String>){
    // testMethodOne()
    // mutexMethodTwo()
    actorMethod()
}

sealed class CounterMsg
object IncCounter: CounterMsg() //递增计数器的单项消息
// 携带回复的请求
class GetCounter(val response:CompletableDeferred<Int>):
    CounterMsg()
fun actorMethod() {
    fun CoroutineScope.counterActor()=actor <CounterMsg>{
        var counter =0
        for (msg in channel){
            when(msg){
                is IncCounter -> counter++
                is GetCounter -> msg.response.complete(counter)
            }
        }
    }

    runBlocking {
        val counter= counterActor() // 创建actor
        withContext(Dispatchers.Default){
            massiveRun { counter.send(IncCounter) }
        }
        val response= CompletableDeferred<Int>()
        counter.send(GetCounter(response))
        println("Counter= ${response.await()}")
        counter.close()
    }
}

fun mutexMethodTwo() {
    val mutex= Mutex()
    var counter=0
    runBlocking {
        withContext(Dispatchers.Default){
            massiveRun {
                //使用锁保护每次自增
                mutex.withLock {
                    counter++
                }
            }
        }
        println("Counter = $counter")
    }
}

fun testMethodOne() {
    runBlocking {
        withContext(Dispatchers.Default){
           // massiveRun { counter.incrementAndGet()}
            massiveRun {
                withContext(coroutineContext) {
                    counter2++
                }
            }
        }
    }
    println("Counter = $counter2")
}

suspend fun massiveRun(action:suspend() -> Unit){
    val n=100 //启动的协程数
    val k=1000 // 每个协程重复执行同一动作的次数
    val time= measureTimeMillis {
        coroutineScope {
            repeat(n){
                launch {
                    repeat(k){
                        action()
                    }
                }
            }
        }
    }
    println("Completed ${n*k} action in $time ms")
}