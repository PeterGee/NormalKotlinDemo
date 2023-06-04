package com.example.myapplication.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/**
 * @date 2019/10/23
 * @author qipeng
 * @desc  异步流
 */
class AsyncStream {
}

/**
 * 挂起函数 suspend关键字
 */
fun methodThree() {
    suspend fun foo(): List<Int> {
        delay(1000)
        return listOf(1, 2, 3)
    }
    runBlocking {
        foo().forEach { value ->
            println(value)
        }
    }
}

/**
 * 序列
 */
fun methodTwo() {
    fun foo(): Sequence<Int> = sequence {
        for (i in 1..3) {
            Thread.sleep(100)// 模拟耗时操作
            yield(i) // 产生下一个值
        }
    }

    foo().forEach { value -> println(value) }
}

/**
 * 表示多个值
 */
private fun methodOne() {
    fun foo(): List<Int> = listOf(1, 2, 3)
    foo().forEach { value ->
        println(value)
    }
}

/**
 * 流操作
 */
fun streamMethod() {
    /* fun foo():Flow<Int> = flow {
         for (i in 1..3){
             delay(100)
             emit(i)
         }
     }*/
}


fun closeChannelMethod() {
    val channel = Channel<Int>()
    runBlocking {
        launch {
            for (i in 1..5) channel.send(i * i)
            channel.close() // 发送结束指令
        }
        for (y in channel) println(y) // 通过for循环来打印所有被接收到的元素，直到通道被关闭
        println("Done!")
    }
}

fun channelMethod() {
    val channel = Channel<Int>()
    runBlocking {
        launch {
            for (i in 1..5) channel.send(i * i)
        }
        repeat(5) {
            println(channel.receive())
        }
        println("Done!")
    }

}

/**
 * 管道
 */
fun pipelineMethod() {
    fun CoroutineScope.produceNumbers() = produce<Int> {
        var x = 1
        while (true) send(x++)
    }

    fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
        for (i in numbers) send(i * i)
    }

    fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce<Int> {
        for (i in numbers) if (i % prime != 0) send(i)
    }

    fun CoroutineScope.numbersFrom(start:Int) = produce <Int>{
        var x= start
        while (true) send(x++)
    }

    runBlocking {
        val numbers = produceNumbers()
        val squares = square(numbers)
        for (i in 1..5) println(squares.receive())
        println("Done") // 操作结束
        coroutineContext.cancelChildren() // 取消子协程
    }

    runBlocking {
        var vur= numbersFrom(2)
        for (i in 1..10){
            val prime=vur.receive()
            println(prime)
            vur=filter(vur,prime)
        }
        coroutineContext.cancelChildren()
    }

}

/**
 * 构建通道生产者
 */
fun producerMethod() {
    fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
        for (i in 1..5) send(i * i)
    }
    runBlocking {
        val squares = produceSquares()
        squares.consumeEach {
            println(it)
        }
        println("Done!")
    }
}

/**
 * 扇出
 */
fun launchProcessorMethod() {
    fun CoroutineScope.launchProcessor(id:Int,channel:ReceiveChannel<Int>) = launch {
        for (msg in channel){
            println("Processor #$id received $msg")
        }
    }
    fun CoroutineScope.produceNumbers() = produce<Int> {
        var x = 1
        while (true) send(x++)
        delay(100)
    }

    runBlocking {
        val producer= produceNumbers()
        repeat(5) { launchProcessor(it,producer)}
        delay(950)
        producer.cancel() // 取消协程生产者从而将他们全部杀死
    }
}

/**
 * 扇入协程
 */
fun launchInProcessorMethod(){
    suspend fun sendString(channel: SendChannel<String>,s:String,time:Long){
        while (true){
            delay(time)
            channel.send(s)
        }
    }

    runBlocking {
        val channel =Channel<String>()
        launch {
            sendString(channel,"Foo",200L)
        }
        launch {
            sendString(channel,"Bar",500L)
        }
        repeat(6){
            println(channel.receive())
        }
        coroutineContext.cancelChildren()
    }
}


fun bufferCoroutineMethod(){
    runBlocking <Unit>{
        val channel=Channel<Int>(4) // 创建buffer大小
        val sender=launch {
            repeat(10){
                println("Sending $it")
                channel.send(it)
            }
        }
        delay(1000)
        sender.cancel()
    }
}

fun fairCoroutineMethod(){
    data class Ball(var hits:Int)

    suspend fun player(name:String,table:Channel<Ball>){
        for(ball in table){
            ball.hits++
            println("$name $ball")
            delay(300)
            table.send(ball)  // 发球
        }
    }
    runBlocking {
        val table=Channel<Ball>()
        launch { player("ping",table) }
        launch { player("pang",table) }
        table.send(Ball(0))
        delay(2000)  // 延迟2s
        coroutineContext.cancelChildren() // 游戏结束，取消
    }
}

fun main(args: Array<String>) {
    // methodOne()
    // methodTwo()
    methodThree()
    // streamMethod()
    // channelMethod()
    // closeChannelMethod()
    // producerMethod()
    // pipelineMethod()
    // launchProcessorMethod()
    // launchInProcessorMethod()
    // bufferCoroutineMethod()
    // fairCoroutineMethod()
    // counterMethod()
}

fun counterMethod() {
    runBlocking {
        // 创建计时器
        val tickerChannel= ticker(delayMillis = 100,initialDelayMillis = 0)
        var nextElement= withTimeoutOrNull(1){tickerChannel.receive()}
        println("initial element is available immediately : $nextElement")
        nextElement= withTimeoutOrNull(50){tickerChannel.receive()}
        println("initial element is available immediately : $nextElement")
        nextElement= withTimeoutOrNull(60){tickerChannel.receive()}
        println("initial element is available immediately : $nextElement")

        println("Consumer pause for 150ms")
        delay(150)
        nextElement= withTimeoutOrNull(1){tickerChannel.receive()}
        println("Next element is available immediately after large consumer delay: $nextElement")
        nextElement= withTimeoutOrNull(60){tickerChannel.receive()}
        println("next element is ready in 50ms after consumer pause in 150ms:$nextElement")
        tickerChannel.cancel()
    }
}









