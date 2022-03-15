package com.san4o.test

import javafx.application.Application.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
object ChannelDebounce {
    fun main() = runBlocking {
        val channel = Channel<String>()

        launch {
            for (i in 1..5) {
                delay(i * 100L)
                channel.send("text$i")
            }
        }

        launch {
            channel.debounce(300)
        }
    }

}

fun <E> ReceiveChannel<E>.debounce(
        wait: Long,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
): ReceiveChannel<E> = coroutineScope.produce {
    var lastTimeout: Job? = null
    consumeEach {
        lastTimeout?.cancel()
        lastTimeout = launch {
            delay(wait)
            send(it)
        }
    }
    lastTimeout?.join()
}