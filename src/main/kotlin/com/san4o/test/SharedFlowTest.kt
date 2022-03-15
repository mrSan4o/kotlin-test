package com.san4o.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

object SharedFlowTest {
    @JvmStatic
    fun main(args: Array<String>): Unit {


        val flow = MutableSharedFlow<Int>(
            replay = 0,
            extraBufferCapacity = 10,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

        CoroutineScope(Dispatchers.IO).launch {
            println("collect")
            flow
                .collect {
                delay(500)
                println(">>> collect $it")
            }
        }


        Thread.sleep(100)

        repeat(20) { emit(flow, it) }

        Thread.sleep(20000)
    }

    private fun emit(flow: MutableSharedFlow<Int>, i: Int) {
//        Thread.sleep(10)
//        println("launch emit $i")
        val emit = flow.tryEmit(i)
        println("emit $i - $emit")

    }

    private fun emit(flow: Channel<Int>, i: Int) {
        val emit = flow.trySend(i)
        println("emit $i - $emit")
        Thread.sleep(10)
    }
}