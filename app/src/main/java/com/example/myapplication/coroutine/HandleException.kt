package com.example.myapplication.coroutine

import kotlinx.coroutines.*
import java.io.IOException

/**
 * @date 2019/10/25
 * @author qipeng
 * @desc 异常处理
 */
fun cancelAndExceptionMethod() {
    runBlocking {
        val job = launch {
            val child = launch {
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    println("Child is cancelled")
                }
            }
            yield()
            println("Cancelling child")
            child.cancel()
            child.join()
            yield()
            println("Parent is not cancelled")
        }
        job.join()
    }
}

fun exceptionTestMethod() {
    runBlocking {
        val job = GlobalScope.launch {
            println("Throwing exception from launch")
            throw IndexOutOfBoundsException()
        }
        job.join()
        println("Joined failed job")
        val deferred = GlobalScope.async {
            println("Throwing exception from async")
            throw ArithmeticException()
        }
        try {
            deferred.await()
            println("Unreached")
        } catch (e: Exception) {
            println("Caught ArithmeticException or indexOutOfBoundException")
        }
    }
}

/**
 * 异常聚合
 */
fun exceptionAggregationMethod() {
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception with suppressed ${exception.suppressed.contentToString()}")
        }
        val job = GlobalScope.launch(handler) {
            launch {
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    throw ArithmeticException()
                }
            }
            launch {
                delay(100)
                throw IOException()
            }
            delay(Long.MAX_VALUE)
        }
        job.join()
    }
}

/**
 * 监督作业
 */
fun supervisorJobMethod() {
    runBlocking {
        val supervisor = SupervisorJob()
        // 启动第一个子作业
        with(CoroutineScope(coroutineContext + supervisor)) {
            val firstChild = launch(CoroutineExceptionHandler { _, _ -> }) {
                println("First child is failing")
                throw  AssertionError("First child is cancelled")
            }
            // 启动第二个字作业
            val secondChild = launch {
                firstChild.join()
                // 取消了第一个子作业并且没有传播给第二个子作业
                println("First child is cancelled: ${firstChild.isCancelled}")
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    println("Second child is cancelled because supervisor is cancelled")
                }
            }
            // 等待直到第一个子作业失败并且执行完成
            firstChild.join()
            println("Cancelling supervisor")
            supervisor.cancel()
            secondChild.join()
        }
    }
}

fun main(args: Array<String>) {
    // exceptionTestMethod()
    // cancelAndExceptionMethod()
    // exceptionAggregationMethod()
   // supervisorJobMethod()
    supervisorExceptionJobMethod()
}

fun supervisorExceptionJobMethod() {
    runBlocking {
        val handler= CoroutineExceptionHandler{_,exception ->
            println("Caught $exception")
        }
        supervisorScope {
            val child= launch(handler){
                println("child throws an exception")
                throw AssertionError()
            }
            println("Scope is completing")
        }
        println("Scope is completed")
    }
}




