package com.san4o.test

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object SlowConsumer {
    @JvmStatic
    fun main(args: Array<String>): Unit = runBlocking {

        val click = MutableSharedFlow<Int>(
                replay = 1,
                onBufferOverflow = BufferOverflow.DROP_LATEST
        )


        launch {
            click
                    .distinctUntilChanged()
                    .onEach { println("Each $it") }
                    .collect {
                        println("collect $it start")
                        delay(1000)
                        println("collect $it end")
                    }
        }

        launch {
            emit(click, 1, 100)
            emit(click, 1, 500)
            emit(click, 1, 400)
            emit(click, 1, 200)
            emit(click, 1, 800)
            emit(click, 2, 500)
        }


        delay(2000)
    }

    private suspend fun emit(click: MutableSharedFlow<Int>, value: Int, delayMsc:Int) {
        click.tryEmit(value)
        println("tap $value delay=$delayMsc")
        delay(delayMsc.toLong())
    }
}

fun <T> Flow<T>.onBackpressureDrop(): Flow<T> {
    return channelFlow {
        collect { this.trySend(it).isSuccess }
    }.buffer(capacity = 0)
}
fun <T> Flow<T>.onBackpressureDropDistinct(): Flow<T> {
    return channelFlow {
        distinctUntilChanged()
        collect { this.trySend(it).isSuccess }
    }.buffer(capacity = 0)
}